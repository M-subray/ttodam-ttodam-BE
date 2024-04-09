package com.ttodampartners.ttodamttodam.domain.user.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SignupRequestDto {
  @NotBlank(message = "이메일을 입력해 주세요.")
  @Column(unique = true)
  @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
      message = "올바른 이메일 형식을 입력해 주세요")
  private String email;

  @NotBlank(message = "비밀번호를 입력해 주세요.")
  @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$",
      message = "비밀번호는 최소 8자에서 20자 사이의 문자, 숫자, 특수 문자를 포함해야 합니다.")
  private String password;

  @NotBlank(message = "비밀번호를 한 번 더 입력해 주세요.")
  private String confirmPassword;

  @AssertTrue(message = "비밀번호가 일치하지 않습니다.")
  private boolean isPasswordConfirmed() {
    return password.equals(confirmPassword);
  }
}
