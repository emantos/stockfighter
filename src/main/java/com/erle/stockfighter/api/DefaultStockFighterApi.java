package com.erle.stockfighter.api;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.RequestBuilder;

import com.erle.stockfighter.model.Order;
import com.erle.stockfighter.model.OrderResponse;
import com.erle.stockfighter.model.Quote;
import com.erle.stockfighter.model.StartLevelResponse;
import com.erle.stockfighter.model.StopLevelResponse;
import com.google.gson.Gson;

public class DefaultStockFighterApi implements StockFighterApi {

  private String scheme;
  private String host;
  private String apiKey;
  private RequestConfig requestConfig;
  private HttpClient httpClient;
  
  private Gson gson = new Gson();

  public static DefaultStockFighterApi newInstance(String scheme, String host, String apiKey,
      RequestConfig requestConfig, HttpClient client) {
    return new DefaultStockFighterApi(scheme, host, apiKey, requestConfig, client);
  }

  private DefaultStockFighterApi(String scheme, String host, String apiKey, RequestConfig config, HttpClient client) {
    this.scheme = scheme;
    this.host = host;
    this.apiKey = apiKey;
    this.requestConfig = config;
    this.httpClient = client;
  }

  public StartLevelResponse startLevel(String levelName) throws Exception {
    return executeRequest(Api.START_LEVEL, new String[] { levelName }, null, StartLevelResponse.class);
  }

  public StopLevelResponse stopLevel(int instanceId) throws Exception {
    return executeRequest(Api.STOP_LEVEL, new Object[] { instanceId }, null, StopLevelResponse.class);
  }

  public Quote quote(String venue, String stock) throws Exception {
    return executeRequest(Api.QUOTE, new String[] { venue, stock }, null, Quote.class);
  }

  public OrderResponse order(String venue, String stock, Order order) throws Exception {
    return executeRequest(Api.ORDER, new String[] { venue, stock }, order, OrderResponse.class);
  }
  
  public OrderResponse orderStatus(String venue, String stock, int orderId) throws Exception {
    return executeRequest(Api.ORDER_STATUS, new Object[] { venue, stock, orderId }, null, OrderResponse.class);
  }
  
  public OrderResponse cancelOrder(String venue, String stock, int orderId) throws Exception {
    return executeRequest(Api.CANCEL_ORDER, new Object[] { venue, stock, orderId }, null, OrderResponse.class);
  }

  private <T> T executeRequest(Api api, Object[] urlArguments, Object bodyArgument, Class<T> returnType)
      throws Exception {
    //System.out.println(getUrl(api.getFormattedString(), urlArguments));
    RequestBuilder builder = 
        RequestBuilderBuilder.instanceWithMethod(api.getMethod())
                         .withRequestConfig(requestConfig)
                         .withApiKey(apiKey)
                         .withUri(getUrl(api.getFormattedString(), urlArguments))
                         .withPayload(gson.toJson(bodyArgument))
                         .build();
    
    return httpClient.execute(builder.build(), JsonToModelResponseHandler.instance(returnType));
  }

  private String getUrl(String urlFormattedString, Object[] arguments) {
    return new StringBuilder().append(scheme).append("://").append(host)
        .append(String.format(urlFormattedString, arguments)).toString();
  }
}
