package com.ttodampartners.ttodamttodam.domain.notification.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Getter
@Setter
public class TokenSseEmitter extends SseEmitter {
  private Long expiredDate;

  public TokenSseEmitter(Long timeout) {
    super(timeout);
  }
}