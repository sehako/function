package com.sehako.playground.client_request.presentation;

import com.sehako.playground.client_request.presentation.request.MultipartRequest;
import com.sehako.playground.client_request.presentation.request.MultipartRequestNoRecord;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api/user-request")
public class ClientRequestController {
    @PostMapping(value = "/upload/record")
    public void upload(
            @RequestPart(value = "data", required = false) MultipartRequest request,
            @RequestPart("image") List<MultipartFile> images
    ) {
        log.info("request = {}", request);
        log.info("images = {}", images);
    }

    @PostMapping(value = "/upload/model-attribute")
    public void uploadModelAttribute(
            @ModelAttribute MultipartRequestNoRecord request,
            @RequestPart("image") List<MultipartFile> images
    ) {
        log.info("request = {}", request);
        log.info("images = {}", images);
    }
}
