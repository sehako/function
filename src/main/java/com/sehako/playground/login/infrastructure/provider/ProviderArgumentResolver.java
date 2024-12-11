package com.sehako.playground.login.infrastructure.provider;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProviderArgumentResolver implements HandlerMethodArgumentResolver {
    private final ProviderFactory providerFactory;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter
                .withContainingClass(OAuthProvider.class)
                .hasParameterAnnotation(AuthProviderPicker.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        // path variable이라 / 기준으로 나누고 마지막 문자열을 가져오도록 함
        // 이 방식은 쿼리 파라미터가 더 나을 것 같다.
//        String[] pathVariableFromRequest = request.getRequestURI().split("/");
//        String providerName = pathVariableFromRequest[pathVariableFromRequest.length - 1];

        String providerName = request.getParameter("provider");

        log.info(providerName);
        return providerFactory.mapping(providerName);
    }
}
