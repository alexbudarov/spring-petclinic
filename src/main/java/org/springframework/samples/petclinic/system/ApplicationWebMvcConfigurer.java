package org.springframework.samples.petclinic.system;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.samples.petclinic.rest.rasupport.RaFilterArgumentResolver;
import org.springframework.samples.petclinic.rest.rasupport.RaProtocolUtil;
import org.springframework.samples.petclinic.rest.rasupport.RaRangeSortArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
class ApplicationWebMvcConfigurer implements WebMvcConfigurer {

    @Value("${app.cors.allowed-origins}")
    private String allowedCorsOrigins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/rest/**")
			.allowedOrigins(allowedCorsOrigins)
			.exposedHeaders(RaProtocolUtil.CONTENT_RANGE);
    }

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new RaRangeSortArgumentResolver());
		resolvers.add(new RaFilterArgumentResolver());
	}
}
