package org.springframework.samples.petclinic.system;

import org.springframework.context.annotation.Configuration;
import org.springframework.samples.petclinic.rest.rasupport.RaFilterArgumentResolver;
import org.springframework.samples.petclinic.rest.rasupport.RaRangeSortArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
class ApplicationWebMvcConfigurer implements WebMvcConfigurer {

	private final RaFilterArgumentResolver raFilterArgumentResolver;
	private final RaRangeSortArgumentResolver raRangeSortArgumentResolver;

	public ApplicationWebMvcConfigurer(RaFilterArgumentResolver raFilterArgumentResolver, RaRangeSortArgumentResolver raRangeSortArgumentResolver) {
		this.raFilterArgumentResolver = raFilterArgumentResolver;
		this.raRangeSortArgumentResolver = raRangeSortArgumentResolver;
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(raFilterArgumentResolver);
		resolvers.add(raRangeSortArgumentResolver);
	}
}
