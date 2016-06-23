package com.erle.stockfighter.strategy;

import java.util.ArrayList;
import java.util.List;

import com.erle.stockfighter.api.StockFighterApi;
import com.erle.stockfighter.model.Order;
import com.erle.stockfighter.model.OrderResponse;
import com.erle.stockfighter.model.OrderType;
import com.erle.stockfighter.model.Quote;
import com.erle.stockfighter.utils.AccountManager;
import com.erle.stockfighter.utils.PositionManager;

public class MarketMakerLisa implements StockFighterStrategy {

	private static final int BET_SPREAD = 5;
	private static final int TRANCH_SIZE = 5;
	private int FAVORABLE_SPREAD = 50;

	public void runStrategy(StockFighterApi api, String account, String venue, String ticker) throws Exception {
		int profitToBeEarned = 1000000;

		AccountManager accountManager = new AccountManager();
		PositionManager positionManager = new PositionManager(accountManager, 50, TRANCH_SIZE);

		List<OrderResponse> previousOrders = new ArrayList<OrderResponse>();
		while (true) {
			Quote quote = api.quote(venue, ticker);

			if (accountManager.totalPosition(quote.getLast()) < profitToBeEarned) {

				System.out.println(quote.toString());

				for (OrderResponse order : previousOrders) {
					OrderResponse statusResponse = api.orderStatus(venue, ticker, order.getId());
					accountManager.update(statusResponse);
					if (statusResponse.getQty() > 0) {
						OrderResponse cancelResult = api.cancelOrder(venue, ticker, order.getId());
						System.out.println("Cancelled Order " + cancelResult.getId() + " with direction = "
								+ cancelResult.getDirection());
					}
				}

				if (previousOrders.size() > 0) {
					System.out.println("Position : " + accountManager.getInventory() + " Cash : " + accountManager.getCash());
					previousOrders.clear();
				}

				if (favorableSpread(quote)) {
					int startBidPrice = quote.getBid();
					int startSellPrice = quote.getAsk();

					for (int i = 0; i < positionManager.getBidDepth(); i++) {
						int bidPrice = startBidPrice + (BET_SPREAD * i);
						Order buyOrder = Order.buy(account, venue, ticker, bidPrice, positionManager.getTranchSize(), OrderType.LIMIT);
						OrderResponse buyOrderResult = api.order(venue, ticker, buyOrder);
						if (buyOrderResult.isOk()) {
							System.out.println("Buying at " + bidPrice);
							previousOrders.add(buyOrderResult);
						}
					}

					for (int i = 0; i < positionManager.getAskDepth(); i++) {
						int sellPrice = startSellPrice + (BET_SPREAD * i);
						Order sellOrder = Order.sell(account, venue, ticker, sellPrice, positionManager.getTranchSize(), OrderType.LIMIT);
						OrderResponse sellOrderResult = api.order(venue, ticker, sellOrder);
						if (sellOrderResult.isOk()) {
							System.out.println("Selling at " + sellPrice);
							previousOrders.add(sellOrderResult);
						}
					}
				}

				Thread.sleep(5000);
			} else {
				break;
			}
		}
	}

	private boolean favorableSpread(Quote quote) {
		return quote.getAsk() - quote.getBid() > FAVORABLE_SPREAD;
	}
}
