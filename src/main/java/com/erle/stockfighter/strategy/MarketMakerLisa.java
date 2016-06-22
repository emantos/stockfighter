package com.erle.stockfighter.strategy;

import java.util.ArrayList;
import java.util.List;

import com.erle.stockfighter.api.StockFighterApi;
import com.erle.stockfighter.model.Order;
import com.erle.stockfighter.model.OrderResponse;
import com.erle.stockfighter.model.OrderType;
import com.erle.stockfighter.model.Quote;
import com.erle.stockfighter.utils.AccountManager;

public class MarketMakerLisa implements StockFighterStrategy {

  private static final int BET_SPREAD = 2;
  private static final int TRANCH = 10;
  private int FAVORABLE_SPREAD = 50;
  
  public void runStrategy(StockFighterApi api, String account, String venue, String ticker) throws Exception {
	  int profitToBeEarned = 1000000;
    
    AccountManager manager = new AccountManager();
    
    List<OrderResponse> previousOrders = new ArrayList<OrderResponse>();
    while (true) {
      Quote quote = api.quote(venue, ticker);
      if (manager.totalPosition(quote.getLast()) < profitToBeEarned) {
        System.out.println(quote.toString());
        for (OrderResponse order : previousOrders) {
          manager.update(order);
          if (order.getQty() > 0) {
            OrderResponse cancelResult = api.cancelOrder(venue, ticker, order.getId());
            System.out.println("Cancelled Order " + cancelResult.getId() + " with direction = " + cancelResult.getDirection());
          }
        }
        if (previousOrders.size() > 0) {
          System.out.println("Position : " + manager.getInventory() + " Cash : " + manager.getCash());
        }
        previousOrders.clear();
        if (favorableSpread(quote)) {
          for (int i = 0; i < 2; i++) {
            int bidPrice = quote.getBid() - (BET_SPREAD * i);
            int sellPrice = quote.getAsk() + (BET_SPREAD * i);
            Order buyOrder = Order.buy(account, venue, ticker, bidPrice, TRANCH, OrderType.LIMIT);
            OrderResponse buyOrderResult = api.order(venue, ticker, buyOrder);
            if (buyOrderResult.isOk()) {
              System.out.println("Buying at " + bidPrice);
              previousOrders.add(buyOrderResult);
            }
            Order sellOrder = Order.sell(account, venue, ticker, sellPrice, TRANCH, OrderType.LIMIT);
            OrderResponse sellOrderResult = api.order(venue, ticker, sellOrder);
            if (sellOrderResult.isOk()) {
              System.out.println("Selling at " + sellPrice);
              previousOrders.add(sellOrderResult);
            }
          }
        }
        
        Thread.sleep(2000);
      } else {
        break;
      }
    }
	}

  private boolean favorableSpread(Quote quote) {
    return quote.getAsk() - quote.getBid() > FAVORABLE_SPREAD;
  }
}
