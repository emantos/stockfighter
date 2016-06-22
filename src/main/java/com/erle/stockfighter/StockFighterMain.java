package com.erle.stockfighter;

import java.util.Objects;

import org.apache.http.impl.client.HttpClients;

import com.erle.stockfighter.api.DefaultStockFighterApi;
import com.erle.stockfighter.api.StockFighterApi;
import com.erle.stockfighter.model.StartLevelResponse;
import com.erle.stockfighter.model.StopLevelResponse;
import com.erle.stockfighter.strategy.StockFighterStrategy;

public class StockFighterMain {
	
  protected StockFighterApi api = DefaultStockFighterApi.newInstance("https", "api.stockfighter.io",
			"b2f26636bc86641224d4b26e38e83c5e562de8ed", null, HttpClients.createDefault());

	public void run(String level, StockFighterStrategy fighter) throws Exception {
		Objects.requireNonNull(level, "Level should not be null.");
		Objects.requireNonNull(fighter, "Stockfighter Strategy should not be null.");
		
		System.out.println("StockFighterMain::Trying to start level " + level);
		StartLevelResponse startResponse = api.startLevel(level);
		if (startResponse.isOk()) {
			System.out.println("StockFighterMain::Level " + level + " STARTED. " + startResponse.toString());

			try {
				final long startTime = System.currentTimeMillis();
				
				fighter.runStrategy(api, startResponse.getAccount(), startResponse.getVenues()[0], startResponse.getTickers()[0]);
				
				final long endTime = System.currentTimeMillis();

				System.out.println("StockFighterMain::Total execution time: " + (endTime - startTime)/1000.0 + " seconds.");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("StockFighterMain::Most likely you ran out of time. Try again!");
			}

			System.out.println("StockFighterMain::Trying to STOP level " + level + " with instanceId = " + startResponse.getInstanceId());
			StopLevelResponse stopResponse = api.stopLevel(startResponse.getInstanceId());
			if (stopResponse.isOk()) {
				System.out.println("StockFighterMain::Level stopped SUCCESSFULLY. " + stopResponse.toString());
			} else {
				System.out.println("StockFighterMain::Level not successfully stopped. " + stopResponse.toString());
			}
		}
	}
}
