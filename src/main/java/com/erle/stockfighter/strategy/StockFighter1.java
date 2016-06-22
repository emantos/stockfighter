package com.erle.stockfighter.strategy;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.erle.stockfighter.api.StockFighterApi;
import com.erle.stockfighter.model.Order;
import com.erle.stockfighter.model.OrderResponse;
import com.erle.stockfighter.model.OrderType;
import com.erle.stockfighter.model.Quote;
import com.google.common.collect.EvictingQueue;

public class StockFighter1 implements StockFighterStrategy {

  public static int RISK_APPETITE = 100;
  public static int MIN_SPREAD = 50;
  
	public void runStrategy(StockFighterApi api, String account, String venue, String ticker) throws Exception {
	  int profitToBeEarned = 1000000;
    int profitEarned = 0;

    EvictingQueue<Quote> lastQuotes = initialQuotes(api, venue, ticker);
    
    Map<Integer, Integer> inventory = new HashMap<Integer, Integer>();
    while (profitEarned < profitToBeEarned) {
      Quote quote = api.quote(venue, ticker);
      
      if (!contains(lastQuotes, quote)) {
        lastQuotes.add(quote);
      }
      
      int buyingPrice = average(lastQuotes);
      
      System.out.println("Buying: " + buyingPrice + " Bid: " + quote.getBid() + " Ask: " + quote.getAsk() +  " Last: "+ quote.getLast() + " Last Size: " + quote.getLastSize() + " Last Trade: " + quote.getLastTrade());
      
      int totalInventorySize = totalSize(inventory);
      if (quote.getAsk() < buyingPrice && totalInventorySize < RISK_APPETITE) {
        int buyAllowance = RISK_APPETITE - totalInventorySize;
        int size = quote.getAskDepth() < buyAllowance ? quote.getAskDepth() : buyAllowance;
        OrderResponse orderResult = api
            .order(venue, ticker, Order.buy(account, venue, ticker, quote.getAsk(), size, OrderType.IMMEDIATE_OR_CANCEL));
        if (orderResult.getTotalFilled() > 0) {
          if (inventory.containsKey(orderResult.getPrice())) {
            inventory.put(orderResult.getPrice(), inventory.get(orderResult.getPrice()) + orderResult.getTotalFilled());
          } else {
            inventory.put(orderResult.getPrice(), orderResult.getTotalFilled());
          }
          
          System.out.println("Bought " + orderResult.getTotalFilled() + " at " + orderResult.getPrice() + " total inventory = " + totalSize(inventory));
        }
      }
      
      Iterator<Map.Entry<Integer, Integer>> iter = inventory.entrySet().iterator();
      while (iter.hasNext()) {
        Map.Entry<Integer, Integer> item = iter.next();
        if ((item.getKey() + MIN_SPREAD) < quote.getBid()) {
          int size = item.getValue() < quote.getBidDepth() ? item.getValue() : quote.getBidDepth();
          OrderResponse orderResult = api
              .order(venue, ticker, Order.sell(account, venue, ticker, quote.getBid(), size, OrderType.IMMEDIATE_OR_CANCEL));
          if (orderResult.getTotalFilled() > 0) {
            profitEarned += (orderResult.getTotalFilled() * (orderResult.getPrice() - item.getKey()));
            if (orderResult.getTotalFilled() >= item.getValue()) {
              iter.remove();
            } else {
              item.setValue(item.getValue() - orderResult.getTotalFilled());
            }
            System.out.println("Sold " + orderResult.getTotalFilled() + " at " + orderResult.getPrice() + " bought at " + item.getKey() + " total inventory = " + totalSize(inventory));
            System.out.println("Profit Earned so far : " + profitEarned);
          }
        }
      }
      
      Thread.sleep(500);
    }
	}
	
	private int average(EvictingQueue<Quote> lastQuotes) {
    int runningTotal = 0;
    for(Quote quote : lastQuotes) {
      runningTotal += quote.getLast();
    }
    return runningTotal/lastQuotes.size();
  }
	
	private int totalSize(Map<Integer, Integer> inventory) {
    int totalSize = 0;
    for(Map.Entry<Integer, Integer> entry : inventory.entrySet()) {
      totalSize += entry.getValue();
    }
    return totalSize;
  }
	
	private boolean contains(EvictingQueue<Quote> lastQuotes, Quote quote) {
    for(Quote oldQuote : lastQuotes) {
      if (oldQuote.getLastTrade().equals(quote.getLastTrade())) {
        return true;
      }
    }
    return false;
  }
	
	private EvictingQueue<Quote> initialQuotes(StockFighterApi api, String venue, String ticker) throws Exception {
    EvictingQueue<Quote> fifo = EvictingQueue.create(20); 
    
    int totalSamples = 0;
    Quote lastQuote = null;
    while(totalSamples < 20) {
      Quote quote = api.quote(venue, ticker);
      System.out.println("Samples = " + totalSamples + " - " + quote.toString());
      if (quote.getLast() > 0 && (lastQuote == null || !lastQuote.getLastTrade().equals(quote.getLastTrade()))) {
        fifo.add(quote);
        totalSamples++;
        lastQuote = quote;
      }
    }
    
    return fifo;
  }

}
