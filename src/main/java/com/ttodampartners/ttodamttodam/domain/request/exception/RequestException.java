package com.ttodampartners.ttodamttodam.domain.request.exception;

import com.ttodampartners.ttodamttodam.global.error.ErrorCode;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestException extends RuntimeException{
  private ErrorCode errorCode;
  private String errorMessage;

  public RequestException(ErrorCode errorCode) {
    this.errorCode = errorCode;
    this.errorMessage = errorCode.getDescription();
  }
}
