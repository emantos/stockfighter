package com.erle.stockfighter.strategy;

import com.erle.stockfighter.api.StockFighterApi;
import com.erle.stockfighter.model.Order;
import com.erle.stockfighter.model.OrderResponse;
import com.erle.stockfighter.model.OrderType;
import com.erle.stockfighter.model.Quote;

public class StockFighterTest implements StockFighterStrategy {

  public void runStrategy(StockFighterApi api, String account, String venue, String ticker) throws Exception {
	  
	  Quote quote = api.quote(venue, ticker);
	  
	  while(quote.getAsk() <= 0) {
		  quote = api.quote(venue, ticker);
		  Thread.sleep(1000);
	  }
	  
	  Order buyOrder = Order.buy(account, venue, ticker, quote.getAsk(), 1000, OrderType.LIMIT);
	  OrderResponse buyOrderResult = api.order(venue, ticker, buyOrder);
	  while(buyOrderResult.isOpen()) {
		  System.out.println(buyOrderResult);
		  Thread.sleep(1000);
		  buyOrderResult = api.orderStatus(venue, ticker, buyOrderResult.getId());
	  }
  }

}
