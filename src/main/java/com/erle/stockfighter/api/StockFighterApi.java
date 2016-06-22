package com.erle.stockfighter.api;

import com.erle.stockfighter.model.Order;
import com.erle.stockfighter.model.OrderResponse;
import com.erle.stockfighter.model.Quote;
import com.erle.stockfighter.model.StartLevelResponse;
import com.erle.stockfighter.model.StopLevelResponse;

public interface StockFighterApi {
	StartLevelResponse startLevel(String levelName) throws Exception;
	
	StopLevelResponse stopLevel(int instanceId) throws Exception;
	
	Quote quote(String venue, String stock) throws Exception;
	
	OrderResponse order(String venue, String stock, Order order) throws Exception;
	
	OrderResponse orderStatus(String venue, String stock, int orderId) throws Exception;
	
	OrderResponse cancelOrder(String venue, String stock, int orderId) throws Exception;
}
