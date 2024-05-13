package com.ttodampartners.ttodamttodam.global.util;

import com.ttodampartners.ttodamttodam.domain.user.exception.UserException;
import com.ttodampartners.ttodamttodam.global.error.ErrorCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthenticationUtil {

  public static Authentication getAuthentication() {
    Authentication authentication =
        SecurityContextHolder.getContext().getAuthentication();

    if (!(authentication.getPrincipal() instanceof UserDetails)) {
      throw new UserException(ErrorCode.SIGNIN_TIME_OUT);
    }

    return authentication;
  }
}
