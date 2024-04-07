package com.ttodampartners.ttodamttodam.infra.email.service;

import com.ttodampartners.ttodamttodam.global.error.ErrorCode;
import com.ttodampartners.ttodamttodam.infra.email.exception.MailException;
import com.ttodampartners.ttodamttodam.global.util.RedisUtil;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CodeConfirmService {
  private final RedisUtil redisUtil;

  @Value("${spring.mail.confirm-expiration-millis-millis}")
  private long confirmExpirationMillis;

  public void codeConfirm (String email, String code) {
    String savedCode = redisUtil.getValue(email);

    if (!code.equals(savedCode)) {
      throw new MailException(ErrorCode.AUTH_KEY_MISMATCH);
    }

    mailConfirm(email);
  }

  private void mailConfirm(String email) {
    redisUtil.setValues(email, "confirmed",
        Duration.ofMillis(this.confirmExpirationMillis));
  }
}
