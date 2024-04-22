package com.ttodampartners.ttodamttodam.domain.chat.config;

import com.ttodampartners.ttodamttodam.global.config.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.support.ChannelInterceptor;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class StompHandler implements ChannelInterceptor {
    private final TokenProvider tokenProvider;
}
