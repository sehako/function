package com.sehako.playground.common.message;

import java.util.Locale;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MessageUtil implements ApplicationContextAware {
    private static MessageSource messageSource;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.messageSource = applicationContext.getBean(MessageSource.class);
    }

    public static String getMessage(String code) {
        Locale locale = LocaleContextHolder.getLocale();  // 현재 스레드의 locale을 자동으로 가져옴
        return messageSource.getMessage(code, null, locale);
    }

    public static String getMessage(String code, Object[] args) {
        Locale locale = LocaleContextHolder.getLocale();  // 현재 스레드의 locale을 자동으로 가져옴
        return messageSource.getMessage(code, args, locale);
    }

}
