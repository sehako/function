package com.sehako.playground.client_request.presentation.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class MultipartRequestNoRecord {
    private String title;
    private String content;
}
