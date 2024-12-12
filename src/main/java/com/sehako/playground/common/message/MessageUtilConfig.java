package com.sehako.playground.common.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageUtilConfig {
    @Autowired
    public void configureMessageUtil(MessageSource messageSource) {
        MessageUtil.init(messageSource);
    }
}
