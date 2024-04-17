package com.ttodampartners.ttodamttodam.domain.user.dto;

import com.ttodampartners.ttodamttodam.domain.user.entity.UserEntity;
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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileDto {

  @Column(unique = true)
  @NotBlank(message = "닉네임을 입력해 주세요.")
  @Size(min = 3, max = 8, message = "닉네임은 최소 세 글자, 최대 여덟 글자 사이어야 합니다.")
  private String nickname;

  @Column(nullable = true)
  private String profileImgUrl;

  @Column(nullable = true)
  @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$",
      message = "비밀번호는 최소 8자에서 20자 사이의 문자, 숫자, 특수 문자를 포함해야 합니다.")
  private String password;

  @Column(nullable = true)
  private String confirmPassword;

  @NotBlank(message = "주소를 입력해 주세요.")
  private String location;

  @Column(unique = true)
  @NotBlank(message = "핸드폰 번호를 입력해 주세요.")
  @Pattern(regexp = "^010-\\d{4}-\\d{4}$",
      message = "핸드폰 번호 형식을 지켜주세요 (010-0000-0000)")
  private String phone;

  @AssertTrue(message = "비밀번호를 변경하려면 한 번 더 입력해 주세요.")
  private boolean isConfirmPasswordRequired() {
    return password == null || confirmPassword != null;
  }

  @AssertTrue(message = "비밀번호가 일치하지 않습니다.")
  private boolean isPasswordConfirmed() {
    return password == null || password.equals(confirmPassword);
  }

  public static ProfileDto getProfile(UserEntity user) {
    return ProfileDto.builder()
        .nickname(user.getNickname())
        .profileImgUrl(user.getProfileImgUrl())
        .location(user.getLocation())
        .phone(user.getPhone())
        .build();
  }
}
