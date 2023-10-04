package org.springframework.samples.petclinic.system;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.samples.petclinic.rest.rasupport.RaProtocolUtil;
import org.springframework.samples.petclinic.rest.rasupport.RaRangeSortArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.util.List;

@Configuration
@EnableWebMvc
class ApplicationWebMvcConfigurer implements WebMvcConfigurer {

    @Value("${app.cors.allowed-origins}")
    private String allowedCorsOrigins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/rest/**")
			.allowedOrigins(allowedCorsOrigins)
			.exposedHeaders(RaProtocolUtil.CONTENT_RANGE);

		registry.addMapping("/**")
			.allowedOrigins("*")
			.allowedMethods("GET")
			.maxAge(3600);
    }

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**")
				.addResourceLocations("classpath:/static/resources/")
				.setCachePeriod(3600)
				.resourceChain(true)
				.addResolver(new PathResourceResolver());
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new RaRangeSortArgumentResolver());
	}
}
