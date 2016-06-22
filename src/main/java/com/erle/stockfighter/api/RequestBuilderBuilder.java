package com.erle.stockfighter.api;

import java.io.UnsupportedEncodingException;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;

public class RequestBuilderBuilder {
  private RequestBuilder requestBuilder = null;
  
  public static RequestBuilderBuilder instanceWithMethod(HttpMethod method) {
    return new RequestBuilderBuilder(method);
  }
  
  private RequestBuilderBuilder(HttpMethod method) {
    switch (method) {
      case POST:
        requestBuilder = RequestBuilder.post();
        break;
      case DELETE:
        requestBuilder = RequestBuilder.delete();
        break;
      default:
        requestBuilder = RequestBuilder.get();
    }
  }

  public RequestBuilderBuilder withRequestConfig(RequestConfig config) {
    if (config != null) {
      requestBuilder.setConfig(config);
    }
    
    return this;
  }
  
  public RequestBuilderBuilder withApiKey(String apiKey) {
    if (apiKey != null && !apiKey.isEmpty()) {
      requestBuilder.setHeader("X-Starfighter-Authorization", apiKey);
    }
    
    return this;
  }
  
  public RequestBuilderBuilder withUri(String uri) {
    requestBuilder.setUri(uri);
    
    return this;
  }
  
  public RequestBuilderBuilder withPayload(String payload) throws UnsupportedEncodingException {
    requestBuilder.setEntity(new StringEntity(payload));
    requestBuilder.setHeader("Content-type", "application/json");
    
    return this;
  }
  
  public RequestBuilder build() {
    return requestBuilder;
  }
}
