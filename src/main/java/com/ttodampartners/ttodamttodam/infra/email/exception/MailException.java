package com.ttodampartners.ttodamttodam.infra.email.exception;

import com.ttodampartners.ttodamttodam.global.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MailException extends RuntimeException {
  private ErrorCode errorCode;
  private String errorMessage;

  public MailException (ErrorCode errorCode) {
    this.errorCode = errorCode;
    this.errorMessage = errorCode.getDescription();
  }
}