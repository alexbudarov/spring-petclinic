package org.springframework.samples.petclinic.rest.rasupport;

import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.IllegalFormatException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RaRangeArgumentResolver implements HandlerMethodArgumentResolver {
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return RaRange.class.equals(parameter.getParameterType());
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,
								  ModelAndViewContainer mavContainer,
								  NativeWebRequest webRequest,
								  WebDataBinderFactory binderFactory) {
		String range = webRequest.getParameter("range");
		if (range == null || range.equals("[]")) {
			return RaRange.unpaged();
		}

		Pattern pattern = Pattern.compile("\\[\\s*(\\d+)\\s*,\\s*(\\d+)\\s*]");
		Matcher matcher = pattern.matcher(range);
		if (matcher.matches()) {
			int rangeStart = Integer.parseInt(matcher.group(1));
			int rangeEnd = Integer.parseInt(matcher.group(2));
			return new RaRange(rangeStart, rangeEnd);
		}

		throw new IllegalArgumentException("Invalid range format: " + range);
	}
}
