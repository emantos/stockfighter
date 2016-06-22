package com.erle.stockfighter.model;

import java.util.Arrays;

public class StartLevelResponse {
	private boolean ok;
	private int instanceId;
	private String account;
	private String[] tickers;
	private String[] venues;
	private int secondsPerTradingDay;
	private Balances balances;

	public static class Balances {
		private int USD;

		public int getUSD() {
			return USD;
		}

		public void setUSD(int uSD) {
			USD = uSD;
		}

		@Override
		public String toString() {
			return "Balances [USD=" + USD + "]";
		}
	}

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public int getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(int instanceId) {
		this.instanceId = instanceId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String[] getTickers() {
		return tickers;
	}

	public void setTickers(String[] tickers) {
		this.tickers = tickers;
	}

	public String[] getVenues() {
		return venues;
	}

	public void setVenues(String[] venues) {
		this.venues = venues;
	}

	public int getSecondsPerTradingDay() {
		return secondsPerTradingDay;
	}

	public void setSecondsPerTradingDay(int secondsPerTradingDay) {
		this.secondsPerTradingDay = secondsPerTradingDay;
	}

	public Balances getBalances() {
		return balances;
	}

	public void setBalances(Balances balances) {
		this.balances = balances;
	}

	@Override
	public String toString() {
		return "StartLevelResponse [ok=" + ok + ", instanceId=" + instanceId + ", account=" + account + ", tickers="
				+ Arrays.toString(tickers) + ", venues=" + Arrays.toString(venues) + ", secondsPerTradingDay="
				+ secondsPerTradingDay + ", balances=" + balances + "]";
	}
}
