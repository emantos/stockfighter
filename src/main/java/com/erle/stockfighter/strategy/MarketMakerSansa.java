package com.erle.stockfighter.strategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.erle.stockfighter.api.StockFighterApi;
import com.erle.stockfighter.model.Order;
import com.erle.stockfighter.model.OrderFill;
import com.erle.stockfighter.model.OrderResponse;
import com.erle.stockfighter.model.OrderType;
import com.erle.stockfighter.model.Quote;
import com.erle.stockfighter.utils.AccountManager;
import com.erle.stockfighter.utils.PositionManager;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class MarketMakerSansa implements StockFighterStrategy {

	private StockFighterApi api;
	private String account;
	private String venue;
	private String ticker;
	
	AccountManager accountManager = new AccountManager();
	
	private static final int TOLERABLE_POSITION = 100;
	private static final int BET_SPREAD = 5;
	private static final int TRANCH_SIZE = 5;
	private int FAVORABLE_SPREAD = 50;

	public void runStrategy(StockFighterApi api, String account, String venue, String ticker) throws Exception {
		this.api = api;
		this.account = account;
		this.venue = venue;
		this.ticker = ticker;
		
		int profitToBeEarned = 1000000;

		

		List<OrderResponse> longSells= new ArrayList<OrderResponse>();
		List<OrderResponse> longBuys= new ArrayList<OrderResponse>();
		// Price is Key, Qty is Value
		Map<Integer, Integer> longPositions = Maps.newHashMap();
		while (true) {
			Quote quote = api.quote(venue, ticker);

			if (accountManager.totalPosition(quote.getLast()) < profitToBeEarned) {
				
				merge(longPositions, updateSellStatusAndCancelUnfilled(longSells));
				
				merge(longPositions, updateBuyStatusAndCancelUnfilled(longBuys));
				
				Map<Integer, Integer> sellablePositions = sellablePositions(longPositions, quote);
				
				longSells.addAll(sellSellablePositions(sellablePositions));
				
				if (riskTolerable(longPositions, TOLERABLE_POSITION)) {
					OrderResponse buyResult = postBuy(quote);
					if (buyResult != null) {
						longBuys.add(buyResult);
					}
				}
				

				Thread.sleep(1000);
			} else {
				break;
			}
		}
	}

	private Map<Integer, Integer> updateSellStatusAndCancelUnfilled(List<OrderResponse> longSells) {
		// TODO Auto-generated method stub
		return null;
	}

	private void merge(Map<Integer, Integer> longPositions, Map<Integer, Integer> newPositions) {
		for(Map.Entry<Integer, Integer> entry :  newPositions.entrySet()) {
			longPositions.merge(entry.getKey(), entry.getValue(), (Integer a, Integer b) -> { return (a + b); });
		}
	}

	private Map<Integer, Integer> updateBuyStatusAndCancelUnfilled(List<OrderResponse> longBuys) throws Exception {
		Map<Integer, Integer> newPositions = Maps.newHashMap();
		
		for(OrderResponse longBuy : longBuys) {
			OrderResponse statusResponse = api.orderStatus(longBuy.getVenue(), longBuy.getSymbol(), longBuy.getId());
			if (statusResponse.isOk()) {
				accountManager.update(statusResponse);
				
				for(OrderFill fill : statusResponse.getFills()) {
					newPositions.merge(fill.getPrice(), fill.getQty(), (Integer a, Integer b) -> { return (a + b); });
				}
				
				if (statusResponse.getQty() > 0) {
					OrderResponse cancelResult = api.cancelOrder(longBuy.getVenue(), longBuy.getSymbol(), longBuy.getId());
					if (cancelResult.isOk()) {
						System.out.println("Cancelled " + longBuy.getId() + " with " + statusResponse.getTotalFilled() + " FILLED and " + statusResponse.getQty() + " UNFILLED.");
					}
				}
			}
		}
		
		return newPositions;
	}

	private Collection<OrderResponse> sellSellablePositions(Map<Integer, Integer> sellablePositions) throws Exception {
		Collection<OrderResponse> sellOrderResults = Lists.newArrayList();
		
		for(Map.Entry<Integer, Integer> entry :  sellablePositions.entrySet()) {
			int sellPrice = entry.getKey() + FAVORABLE_SPREAD;
			Order sellOrder = Order.sell(account, venue, ticker, sellPrice, entry.getValue(), OrderType.LIMIT);
			OrderResponse sellOrderResult = api.order(venue, ticker, sellOrder);
			if (sellOrderResult.isOk()) {
				System.out.println("Selling " + entry.getValue() + " at " + sellPrice + ". Bought at " + entry.getKey());
				sellOrderResults.add(sellOrderResult);
			}
		}
		
		return sellOrderResults;
	}

	private Map<Integer, Integer> sellablePositions(Map<Integer, Integer> longPositions, Quote quote) {
		Map<Integer, Integer> sellablePositions = Maps.newHashMap();
		
		for(Map.Entry<Integer, Integer> entry :  longPositions.entrySet()) {
			if ((entry.getKey() + FAVORABLE_SPREAD) <= quote.getBid()) {
				sellablePositions.put(entry.getKey(), entry.getValue());
			}
		}
		
		return sellablePositions;
	}

	private OrderResponse postBuy(Quote quote) throws Exception {
		int bidPrice = quote.getBid();
		if (bidPrice <= 0) {
			bidPrice = quote.getLast() - FAVORABLE_SPREAD;
		}
		
		Order buyOrder = Order.buy(account, venue, ticker, bidPrice, TRANCH_SIZE, OrderType.LIMIT);
		OrderResponse buyOrderResult = api.order(venue, ticker, buyOrder);
		if (buyOrderResult.isOk()) {
			System.out.println("Buying at " + bidPrice);
			return buyOrderResult;
		}
		
		return null;
	}

	private boolean riskTolerable(Map<Integer, Integer> longPositions, int tolerablePosition) {
		int totalPosition = 0;
		for(Map.Entry<Integer, Integer> entry :  longPositions.entrySet()) {
			totalPosition += entry.getValue();
		}
		return tolerablePosition >= totalPosition;
	}
	
	
}
