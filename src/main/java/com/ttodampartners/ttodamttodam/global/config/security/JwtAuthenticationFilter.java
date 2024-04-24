package com.ttodampartners.ttodamttodam.global.config.security;

import com.ttodampartners.ttodamttodam.global.error.ErrorCode;
import com.ttodampartners.ttodamttodam.global.util.RedisUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final TokenProvider tokenProvider;
  private final RedisUtil redisUtil;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String token = tokenProvider.resolveTokenFromRequest(request);

    if (StringUtils.hasText(token)) {
      if (tokenProvider.validateToken(token) && !redisUtil.keyExists(token)) {  // 유효성 검사 + 블랙리스트 확인
        Authentication auth = tokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(auth);
      } else {
        handleInvalidToken(response,
            // 토큰의 유효기간이 남아있다면 로그아웃 상태, 남아있지 않다면 재로그인이 필요한 상태라고 안내
            tokenProvider.validateToken(token) ? ErrorCode.ALREADY_LOGOUT : ErrorCode.SIGNIN_TIME_OUT);
        return;  // 에러 처리 후 리턴
      }
    }

    filterChain.doFilter(request, response);
  }

  private void handleInvalidToken(HttpServletResponse response, ErrorCode errorCode)
      throws IOException {
    log.error("에러코드: {}, 에러 메시지: {}", errorCode, errorCode.getDescription());
    response.setStatus(HttpStatus.BAD_REQUEST.value());
    response.setCharacterEncoding("UTF-8");
    response.getWriter().write(errorCode.getDescription());
  }
}