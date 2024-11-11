package br.com.samueltorga.spring.config;

import br.com.samueltorga.spring.annotation.MaxPageSize;
import br.com.samueltorga.spring.exceptions.MaxPageSizeException;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class CustomPageableHandlerMethodArgumentResolver extends PageableHandlerMethodArgumentResolver {

    private static final int DEFAULT_MAX_PAGE_SIZE = 100;

    @NonNull
    @Override
    public Pageable resolveArgument(MethodParameter methodParameter, @Nullable ModelAndViewContainer mavContainer,
                                    NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) {
        Pageable pageable = super.resolveArgument(methodParameter, mavContainer, webRequest, binderFactory);
        int maxPageSize = getMaxPageSize(methodParameter);
        if (pageable.getPageSize() > maxPageSize) {
            throw new MaxPageSizeException();
        }
        return pageable;
    }

    private int getMaxPageSize(MethodParameter methodParameter) {
        MaxPageSize maxPageSizeAnnotation = methodParameter.getParameterAnnotation(MaxPageSize.class);
        if (maxPageSizeAnnotation != null) {
            return Math.min(maxPageSizeAnnotation.value(), maxPageSizeAnnotation.max());
        }
        return DEFAULT_MAX_PAGE_SIZE;
    }

    @Override
    public void setFallbackPageable(Pageable fallbackPageable) {
        if (fallbackPageable.getPageSize() > DEFAULT_MAX_PAGE_SIZE) {
            fallbackPageable = Pageable.ofSize(DEFAULT_MAX_PAGE_SIZE).withPage(fallbackPageable.getPageNumber());
        }
        super.setFallbackPageable(fallbackPageable);
    }
}
