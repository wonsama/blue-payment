package dev.wonsama.payment.util;

import java.io.IOException;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;

import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.stereotype.Component;

import dev.wonsama.payment.dto.HttpResponseDto;

@Component
public class HttpUtil {

  // public static void main(String[] args) {
  // HttpUtil httpUtil = new HttpUtil();
  // try {

  // final String url = "http://localhost:8081/api/card";
  // CardCreateDto jsonTestDto =
  // CardCreateDto.builder().ci("wonsama").cardNo("4234567890123456").build();
  // ObjectMapper objectMapper = new ObjectMapper();
  // String json = objectMapper.writeValueAsString(jsonTestDto);
  // HttpResponseDto response = httpUtil.postJson(url, json);
  // System.out.println(response.getStatus());
  // System.out.println(response.getContent());

  // } catch (JsonProcessingException e) {
  // e.printStackTrace();
  // } catch (IOException e) {
  // e.printStackTrace();
  // }
  // }

  public HttpResponseDto postJson(String url, String json) throws IOException {
    HttpResponseDto response = null;
    try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
      HttpPost httpPost = new HttpPost(url);
      httpPost.addHeader("Content-Type", "application/json");
      StringEntity entity = new StringEntity(json);
      httpPost.setEntity(entity);

      // 응답 메시지 설정 - HttpResponseDto 에서 status 로 처리 ( 응답코드 : 200~299 )
      response = httpClient.execute(httpPost, httpResponse -> {
        int status = httpResponse.getCode();
        String content = httpResponse.getEntity() != null
            ? new String(httpResponse.getEntity().getContent().readAllBytes())
            : null;
        return HttpResponseDto.builder().status(status).content(content).build();
      });
    }
    return response;
  }
}
