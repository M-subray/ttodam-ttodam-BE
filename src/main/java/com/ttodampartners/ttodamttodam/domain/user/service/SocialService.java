package com.ttodampartners.ttodamttodam.domain.user.service;

import com.ttodampartners.ttodamttodam.domain.user.entity.UserEntity;
import com.ttodampartners.ttodamttodam.domain.user.repository.UserRepository;
import com.ttodampartners.ttodamttodam.global.config.security.TokenProvider;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class SocialService {

  private final Environment environment;
  private final UserRepository userRepository;
  private final TokenProvider tokenProvider;
  private final RestTemplate restTemplate = new RestTemplate();
  private String email;

  public String socialLogin(String code, String registrationId) {
    String accessToken = getAccessToken(code, registrationId);
    JsonNode userResourceNode = getUserResource(accessToken, registrationId);

    if (registrationId.equals("kakao")) {
      email = userResourceNode.get("kakao_account").get("email").asText();
    } else if (registrationId.equals("google")) {
      email = userResourceNode.get("email").asText();
    }

    Optional<UserEntity> byEmail = userRepository.findByEmail(email);

    // 가져온 이메일이 DB에 없다면 회원가입
    if (byEmail.isEmpty()) {
      userRepository.save(UserEntity.builder().email(email).build());
      log.info("소셜 회원가입 성공, 이메일 : " + email);
    }

    return tokenProvider.generateToken(email);
  }

  private String getAccessToken(String authorizationCode, String registrationId) {
    String clientId = environment.getProperty("oauth2." + registrationId + ".client-id");
    String clientSecret = environment.getProperty("oauth2." + registrationId + ".client-secret");
    String redirectUri = environment.getProperty("oauth2." + registrationId + ".redirect-uri");
    String tokenUri = environment.getProperty("oauth2." + registrationId + ".token-uri");

    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("code", authorizationCode);
    params.add("client_id", clientId);
    params.add("client_secret", clientSecret);
    params.add("redirect_uri", redirectUri);
    params.add("grant_type", "authorization_code");

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    HttpEntity entity = new HttpEntity(params, headers);

    ResponseEntity<JsonNode> responseNode =
        restTemplate.exchange(tokenUri, HttpMethod.POST, entity, JsonNode.class);
    JsonNode accessTokenNode = responseNode.getBody();
    return accessTokenNode.get("access_token").asText();
  }

  private JsonNode getUserResource(String accessToken, String registrationId) {
    String resourceUri = environment.getProperty("oauth2."+registrationId+".resource-uri");

    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer " + accessToken);
    HttpEntity entity = new HttpEntity(headers);
    return restTemplate.exchange(resourceUri, HttpMethod.GET, entity, JsonNode.class).getBody();
  }
}