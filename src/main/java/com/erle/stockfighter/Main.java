package com.erle.stockfighter;

import com.erle.stockfighter.strategy.MarketMakerLisa;

public class Main {
	public static void main(String[] args) {
		try {
			new StockFighterMain().run("sell_side", new MarketMakerLisa());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
