package org.springframework.samples.petclinic.system;

import org.springframework.context.annotation.Configuration;
import org.springframework.samples.petclinic.rest.rasupport.RaFilterArgumentResolver;
import org.springframework.samples.petclinic.rest.rasupport.RaRangeArgumentResolver;
import org.springframework.samples.petclinic.rest.rasupport.RaRangeSortArgumentResolver;
import org.springframework.samples.petclinic.rest.rasupport.RaSortArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
class ApplicationWebMvcConfigurer implements WebMvcConfigurer {

	private final RaFilterArgumentResolver raFilterArgumentResolver;
	private final RaRangeSortArgumentResolver raRangeSortArgumentResolver;
	private final RaRangeArgumentResolver raRangeArgumentResolver;
	private final RaSortArgumentResolver raSortArgumentResolver;

	public ApplicationWebMvcConfigurer(RaFilterArgumentResolver raFilterArgumentResolver, RaRangeSortArgumentResolver raRangeSortArgumentResolver, RaRangeArgumentResolver raRangeArgumentResolver, RaSortArgumentResolver raSortArgumentResolver) {
		this.raFilterArgumentResolver = raFilterArgumentResolver;
		this.raRangeSortArgumentResolver = raRangeSortArgumentResolver;
		this.raRangeArgumentResolver = raRangeArgumentResolver;
		this.raSortArgumentResolver = raSortArgumentResolver;
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(raFilterArgumentResolver);
		resolvers.add(raRangeSortArgumentResolver);
		resolvers.add(raRangeArgumentResolver);
		resolvers.add(raSortArgumentResolver);
	}
}
