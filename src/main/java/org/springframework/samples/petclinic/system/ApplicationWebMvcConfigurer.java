package org.springframework.samples.petclinic.system;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.samples.petclinic.rest.rasupport.RaProtocolUtil;
import org.springframework.samples.petclinic.rest.rasupport.RaRangeSortArgumentResolver;
import org.springframework.samples.petclinic.rest.rasupport.RaFilterArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
class ApplicationWebMvcConfigurer implements WebMvcConfigurer {

	@Value("${app.cors.allowed-origins}")
	private String allowedCorsOrigins;

	private final RaFilterArgumentResolver raFilterArgumentResolver;

	public ApplicationWebMvcConfigurer(RaFilterArgumentResolver raFilterArgumentResolver) {
		this.raFilterArgumentResolver = raFilterArgumentResolver;
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/rest/**")
				.allowedOrigins(allowedCorsOrigins)
				.allowedMethods(HttpMethod.GET.name(), HttpMethod.POST.name(),
						HttpMethod.PUT.name(), HttpMethod.DELETE.name())
				.exposedHeaders(RaProtocolUtil.CONTENT_RANGE);
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new RaRangeSortArgumentResolver());
		resolvers.add(raFilterArgumentResolver);
	}
}
