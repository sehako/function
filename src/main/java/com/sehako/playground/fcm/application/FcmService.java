package com.sehako.playground.fcm.application;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.sehako.playground.fcm.application.exception.GoogleServerException;
import com.sehako.playground.fcm.dto.FcmInfoDto;
import com.sehako.playground.global.code.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FcmService {
    public void sendMessage(FcmInfoDto info) {
        try {
//            WebpushNotification notification = WebpushNotification.builder()
//                    .setTitle(info.title())
//                    .setBody(info.body())
//                    .build();
//
//            WebpushConfig webpushConfig = WebpushConfig.builder()
//                    .setNotification(notification)
//                    .build();

            Notification notification = Notification.builder().setTitle(info.title()).setBody(info.body()).build();

            Message message = Message.builder()
                    .setToken(info.token())
                    .setNotification(notification)
//                    .setWebpushConfig(webpushConfig)
                    .putData("title", "Fore ground Message")
                    .putData("body", "success")
                    .build();

            FirebaseMessaging.getInstance().send(message);

//            log.info("FCM Response: {}", response);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
            throw new GoogleServerException(ErrorCode.GOOGLE_SERVER_ERROR);
        }
    }
}
