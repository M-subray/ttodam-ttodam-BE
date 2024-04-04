package com.ttodampartners.ttodamttodam.infra.email.service;

import com.ttodampartners.ttodamttodam.global.error.ErrorCode;
import com.ttodampartners.ttodamttodam.global.util.RedisUtil;
import com.ttodampartners.ttodamttodam.infra.email.exception.MailException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailSendService {

  @Value("${spring.mail.auth-code-expiration-millis}")
  private long authCodeExpirationMillis;

  private static final int CODE_LENGTH = 6;

  private final JavaMailSender javaMailSender;

  private final RedisUtil redisUtil;

  @Value("${spring.mail.username}")
  private String senderEmail;

  public void sendEmail(String email) {
    MimeMessage message = createMail(email);
    javaMailSender.send(message);
  }

  public MimeMessage createMail(String mail){
    String authCode = createCode();

    redisUtil.setValues(mail,
        authCode, Duration.ofMillis(this.authCodeExpirationMillis));

    MimeMessage message = javaMailSender.createMimeMessage();

    try {
      message.setFrom(senderEmail);
      message.setRecipients(MimeMessage.RecipientType.TO, mail);
      message.setSubject("[또담또담] 회원가입을 위한 이메일 인증번호");
      message.setText(authCode,"UTF-8", "html");
    } catch (MessagingException e) {
      throw new MailException(ErrorCode.MAIL_CREATION_ERROR);
    }

    return message;
  }

  public String createCode() {
    try {
      SecureRandom random = SecureRandom.getInstanceStrong();
      StringBuilder builder = new StringBuilder();
      for (int i = 0; i < CODE_LENGTH; i++) {
        builder.append(random.nextInt(10));
      }
      return builder.toString();
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("예기치 않은 암호화 알고리즘 오류 발생", e);
    }
  }
}
