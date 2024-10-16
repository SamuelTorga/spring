package br.com.samueltorga.spring.config;

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

    private static final int MAX_PAGE_SIZE = 100;

    @NonNull
    @Override
    public Pageable resolveArgument(MethodParameter methodParameter, @Nullable ModelAndViewContainer mavContainer,
                                    NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) {
        Pageable pageable = super.resolveArgument(methodParameter, mavContainer, webRequest, binderFactory);
        if (pageable.getPageSize() > MAX_PAGE_SIZE) {
            return Pageable.ofSize(MAX_PAGE_SIZE).withPage(pageable.getPageNumber());
        }
        return pageable;
    }

    @Override
    public void setFallbackPageable(Pageable fallbackPageable) {
        if (fallbackPageable.getPageSize() > MAX_PAGE_SIZE) {
            fallbackPageable = Pageable.ofSize(MAX_PAGE_SIZE).withPage(fallbackPageable.getPageNumber());
        }
        super.setFallbackPageable(fallbackPageable);
    }
}
