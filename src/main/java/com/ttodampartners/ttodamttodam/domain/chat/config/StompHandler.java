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

    // 메시지 오면 JWT 유효성 검사
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        try {
            // Stomp 메시지 객체의 헤더 접근
            StompHeaderAccessor accessor = StompHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
            // 이름이 "Authorization"인 헤더
            String authorizationHeader = String.valueOf(accessor.getNativeHeader("Authorization"));
            StompCommand command = accessor.getCommand();

            log.info("HEADER: {}, COMMAND: {}", authorizationHeader, command);

            // [구독 취소, 메시지 수신, 메시지 송신, 연결 완료] 시에는 JWT 검사 X
            if (command.equals(StompCommand.UNSUBSCRIBE) || command.equals(StompCommand.MESSAGE) ||
                    command.equals(StompCommand.CONNECTED) || command.equals(StompCommand.SEND)) {
                return message;
            } else if (command.equals(StompCommand.ERROR)) {
                // 추후 예외 처리 필요!!
                throw new IllegalArgumentException("ERROR");
            }

            if (authorizationHeader == null) {
                log.info("헤더가 없는 요청입니다.");
                ErrorCode code = ErrorCode.HEADER_NOT_FOUND;

                throw new StompException(code, ChatExceptionResponse.res(HttpStatus.BAD_REQUEST, code.getDescription(), StompExceptionResponse.builder().command(command.toString()).build()));
            } else {
                Long userId = Long.valueOf(tokenProvider.parseClaims(authorizationHeader).getId());
                log.info("userId: {}", userId);
            }

            /*
                authorizationHeader token 분리 필요할 수도 있음.
            */
            if (command.equals(StompCommand.SUBSCRIBE) || command.equals(StompCommand.CONNECT)) {
                if (!tokenProvider.validateToken(authorizationHeader)) {
                    // 추가적인 예외 처리 필요!!
                    throw new IllegalArgumentException("jwt");
                }
            }
        } catch (RuntimeException e) {
            log.error("JWT 에러");

            ErrorCode code = ErrorCode.INVALID_MESSAGE;

            throw new StompException(code, ChatExceptionResponse.res(HttpStatus.BAD_REQUEST, code.getDescription(), StompExceptionResponse.builder().build()));
        }
        return message;
    }
}
