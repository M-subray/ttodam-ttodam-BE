package com.ttodampartners.ttodamttodam.domain.chat.config;

import com.ttodampartners.ttodamttodam.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.StompSubProtocolErrorHandler;

import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Component
public class StompExceptionHandler extends StompSubProtocolErrorHandler {
    @Override
    public Message<byte[]> handleClientMessageProcessingError(Message<byte[]>clientMessage, Throwable ex)
    {
        if(ex.getCause().getMessage().equals("JWT")){
            return handleJwtException(clientMessage, ex);
        }

        if(ex.getCause().getMessage().equals("error")) {
            return handleMessageException(clientMessage, ex);
        }

        return super.handleClientMessageProcessingError(clientMessage, ex);
    }

    // JWT 예외
    private Message<byte[]> handleJwtException(Message<byte[]> clientMessage, Throwable ex){

        return prepareErrorMessage("토큰이 유효하지 않습니다.", "JWT_INVALIDATE");
    }

    private Message<byte[]> handleMessageException(Message<byte[]> clientMessage, Throwable ex) {
        return prepareErrorMessage("메시지가 올바르지 않습니다.", "MESSAGE_INVALIDATE");
    }

    // 메세지 생성
    private Message<byte[]> prepareErrorMessage(String message, String code)
    {
        StompHeaderAccessor accessor = StompHeaderAccessor.create(StompCommand.ERROR); // 에러 메시지 생성 커맨드

        accessor.setMessage(code);
        accessor.setLeaveMutable(true);

        return MessageBuilder.createMessage(message.getBytes(StandardCharsets.UTF_8), accessor.getMessageHeaders());
    }


}
