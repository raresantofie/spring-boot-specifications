package com.store.controller;

import com.store.repository.SpecificationGenerator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@AllArgsConstructor
public class SpecificationArgumentResolver implements HandlerMethodArgumentResolver {
    private final SpecificationGenerator specificationGenerator;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return isAnnotationPresent(parameter);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Map<String, String> params = getParamsAsMapWithSingleValues(webRequest.getParameterMap());
        return specificationGenerator.generate(params);
    }

    private boolean isAnnotationPresent(MethodParameter parameter) {
        IsProductSpecification annotation = parameter.getParameterAnnotation(IsProductSpecification.class);
        if (annotation != null) {
            return true;
        }
        Annotation[] annotationsToSearch = parameter.getParameterAnnotations();

        for (Annotation toSearch : annotationsToSearch) {
            annotation = AnnotationUtils.findAnnotation(toSearch.annotationType(),
                    IsProductSpecification.class);
            if (annotation != null) {
                return true;
            }
        }
        return false;
    }

    private Map<String, String> getParamsAsMapWithSingleValues(Map<String, String[]> parameterMap) {
        Map<String, String> singleValueParameterMap = new HashMap<>();
        parameterMap
                .forEach((k,v) -> singleValueParameterMap.put(k, v[0]));
        return singleValueParameterMap;
    }
}
