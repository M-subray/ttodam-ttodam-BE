package com.ttodampartners.ttodamttodam.domain.chat.config;

import com.ttodampartners.ttodamttodam.domain.chat.dto.ChatExceptionResponse;
import com.ttodampartners.ttodamttodam.domain.chat.dto.response.StompExceptionResponse;
import com.ttodampartners.ttodamttodam.domain.chat.exception.StompException;
import com.ttodampartners.ttodamttodam.global.config.security.TokenProvider;
import com.ttodampartners.ttodamttodam.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class StompHandler implements ChannelInterceptor {
    private final TokenProvider tokenProvider;
    private static final String BEARER_PREFIX = "Bearer ";

    // 메시지 오면 JWT 유효성 검사
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        // Stomp 메시지 객체의 헤더 접근
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        StompCommand command = accessor.getCommand();

        log.info("COMMAND: {}, HEADER: {}", command, accessor.getMessageHeaders());

        // [구독 취소, 메시지 수신, 메시지 송신, 연결 완료] 시에는 JWT 검사 X
        assert command != null;
        if (command.equals(StompCommand.CONNECT) || command.equals(StompCommand.SUBSCRIBE)) {
            log.info("{}", accessor.getNativeHeader("Authorization"));
            // 예) nativeHeaders={Authorization=[Bearer asklgj11n], accept_version=[1.1, 1.0]}
            String authorizationHeader = String.valueOf(accessor.getNativeHeader("Authorization"));

            if (authorizationHeader == null) {
                log.info("헤더가 없는 요청입니다.");
                ErrorCode code = ErrorCode.HEADER_NOT_FOUND;

                throw new StompException(code, ChatExceptionResponse.res(HttpStatus.BAD_REQUEST, code.getDescription(), StompExceptionResponse.builder().command(command.toString()).build()));
            }

            String token = authorizationHeader.substring(BEARER_PREFIX.length()); // 앞에 Bearer 제거
            if (!tokenProvider.validateToken(token)) {
                    // 추가적인 예외 처리 필요!!
                throw new IllegalArgumentException("jwt");
            }

            // 서비스 안정되면 삭제 필요
            if (command.equals(StompCommand.CONNECT)) {
                log.info("웹소켓 연결");
            } else if (command.equals(StompCommand.SUBSCRIBE)) {
                log.info("{} 구독", accessor.getNativeHeader("id"));
            }
        }

        return message;
    }
}
