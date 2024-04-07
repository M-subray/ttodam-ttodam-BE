package com.ttodampartners.ttodamttodam.domain.user.util;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CoordinateFinderUtil {

  @Value("${spring.kakao.key}")
  private String API_KEY;

  @Value("${spring.kakao.url}")
  private String BASE_URL;

  public double[] getCoordinates(String address) throws IOException {

    String url = buildUrl(address);
    CloseableHttpClient httpClient = HttpClients.createDefault();
    HttpGet httpGet = new HttpGet(url);
    httpGet.addHeader("Authorization", "KakaoAK " + API_KEY);
    CloseableHttpResponse response = httpClient.execute(httpGet);

    if (response.getStatusLine().getStatusCode() != 200) {
      throw new RuntimeException("API 요청 실패: " + response.getStatusLine().getStatusCode());
    }

    HttpEntity entity = response.getEntity();
    String jsonStr = EntityUtils.toString(entity, StandardCharsets.UTF_8);

    JSONObject jsonObject = new JSONObject(jsonStr);
    double[] coordinates = new double[2];

    if (jsonObject.has("documents")) {
      JSONArray documents = jsonObject.getJSONArray("documents");

      if (!documents.isEmpty()) {
        JSONObject document = documents.getJSONObject(0);
        coordinates[0] = document.getDouble("y"); // 위도
        coordinates[1] = document.getDouble("x"); // 경도
      } else {
        throw new RuntimeException("주소 정보를 찾을 수 없습니다.");
      }
    }

    response.close();
    httpClient.close();

    return coordinates;
  }

  private String buildUrl(String address) {
    Map<String, String> params = new HashMap<>();
    params.put("query", URLEncoder.encode(address, StandardCharsets.UTF_8));

    StringBuilder urlBuilder = new StringBuilder(BASE_URL);
    urlBuilder.append("?");
    for (Map.Entry<String, String> param : params.entrySet()) {
      urlBuilder.append(param.getKey()).append("=").append(param.getValue());
    }

    return urlBuilder.toString();
  }
}
