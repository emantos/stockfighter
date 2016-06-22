package com.erle.stockfighter.model;

import java.util.Date;

public class Quote {
	private boolean ok;
	private String symbol;
	private String venue;
	private int bid;
	private int bidSize;
	private int bidDepth;
	private int ask;
	private int askSize;
	private int askDepth;
	private int last;
	private int lastSize;
	private Date lastTrade;
	private Date quoteTime;

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getVenue() {
		return venue;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}

	public int getBid() {
		return bid;
	}

	public void setBid(int bid) {
		this.bid = bid;
	}

	public int getBidSize() {
		return bidSize;
	}

	public void setBidSize(int bidSize) {
		this.bidSize = bidSize;
	}

	public int getBidDepth() {
		return bidDepth;
	}

	public void setBidDepth(int bidDepth) {
		this.bidDepth = bidDepth;
	}

	public int getAsk() {
		return ask;
	}

	public void setAsk(int ask) {
		this.ask = ask;
	}

	public int getAskSize() {
		return askSize;
	}

	public void setAskSize(int askSize) {
		this.askSize = askSize;
	}

	public int getAskDepth() {
		return askDepth;
	}

	public void setAskDepth(int askDepth) {
		this.askDepth = askDepth;
	}

	public int getLast() {
		return last;
	}

	public void setLast(int last) {
		this.last = last;
	}

	public int getLastSize() {
		return lastSize;
	}

	public void setLastSize(int lastSize) {
		this.lastSize = lastSize;
	}

	public Date getLastTrade() {
		return lastTrade;
	}

	public void setLastTrade(Date lastTrade) {
		this.lastTrade = lastTrade;
	}

	public Date getQuoteTime() {
		return quoteTime;
	}

	public void setQuoteTime(Date quoteTime) {
		this.quoteTime = quoteTime;
	}

	@Override
	public String toString() {
		return "Quote [ok=" + ok + ", symbol=" + symbol + ", venue=" + venue + ", bid=" + bid + ", bidSize=" + bidSize
				+ ", bidDepth=" + bidDepth + ", ask=" + ask + ", askSize=" + askSize + ", askDepth=" + askDepth
				+ ", last=" + last + ", lastSize=" + lastSize + ", lastTrade=" + lastTrade + ", quoteTime=" + quoteTime
				+ "]";
	}
}
