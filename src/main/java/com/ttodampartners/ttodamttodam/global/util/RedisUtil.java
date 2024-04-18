package com.ttodampartners.ttodamttodam.global.util;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RedisUtil {

  private final RedisTemplate<String, String> redisTemplate;

  @Transactional
  public void setValues(String key, String value, Duration duration){
    redisTemplate.opsForValue().set(key, value, duration);
  }

  public String getValue(String key) {
    return redisTemplate.opsForValue().get(key);
  }
}