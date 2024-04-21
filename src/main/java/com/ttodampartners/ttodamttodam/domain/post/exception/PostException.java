package com.ttodampartners.ttodamttodam.domain.post.exception;

import com.ttodampartners.ttodamttodam.global.error.ErrorCode;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostException extends RuntimeException{
  private ErrorCode errorCode;
  private String errorMessage;

  public PostException(ErrorCode errorCode) {
    this.errorCode = errorCode;
    this.errorMessage = errorCode.getDescription();
  }
}
