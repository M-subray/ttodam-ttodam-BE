package com.ttodampartners.ttodamttodam.domain.user.dto;


import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SigninRequestDto {

  @NotBlank(message = "이메일을 입력해 주세요.")
  @Column(unique = true)
  private String email;

  @NotBlank(message = "비밀번호를 입력해 주세요.")
  private String password;
}
