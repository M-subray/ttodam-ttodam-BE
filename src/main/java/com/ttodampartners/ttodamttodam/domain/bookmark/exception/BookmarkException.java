package com.ttodampartners.ttodamttodam.domain.bookmark.exception;

import com.ttodampartners.ttodamttodam.global.error.ErrorCode;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookmarkException extends RuntimeException{
  private ErrorCode errorCode;
  private String errorMessage;

  public BookmarkException(ErrorCode errorCode) {
    this.errorCode = errorCode;
    this.errorMessage = errorCode.getDescription();
  }
}
