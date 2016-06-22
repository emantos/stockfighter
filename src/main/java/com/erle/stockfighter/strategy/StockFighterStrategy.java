package com.erle.stockfighter.strategy;

import com.erle.stockfighter.api.StockFighterApi;

public interface StockFighterStrategy {
	void runStrategy(StockFighterApi api, String account, String venue, String ticker) throws Exception;
}
