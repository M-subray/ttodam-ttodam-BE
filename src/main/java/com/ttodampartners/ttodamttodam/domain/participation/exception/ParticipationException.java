package com.ttodampartners.ttodamttodam.domain.participation.exception;

import com.ttodampartners.ttodamttodam.global.error.ErrorCode;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParticipationException extends RuntimeException{
  private ErrorCode errorCode;
  private String errorMessage;

  public ParticipationException(ErrorCode errorCode) {
    this.errorCode = errorCode;
    this.errorMessage = errorCode.getDescription();
  }
}
