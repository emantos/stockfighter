package com.erle.stockfighter.model;

import java.util.Collection;
import java.util.Date;
import java.util.StringJoiner;

public class OrderResponse {
  private boolean ok;
  private String symbol;
  private String venue;
  private String direction;
  private int originalQty;
  private int qty;
  private int price;
  private String orderType;
  private int id;
  private String account;
  private Date ts;
  private Collection<OrderFill> fills;
  private int totalFilled;
  private boolean open;

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

  public String getDirection() {
    return direction;
  }

  public void setDirection(String direction) {
    this.direction = direction;
  }

  public int getOriginalQty() {
    return originalQty;
  }

  public void setOriginalQty(int originalQty) {
    this.originalQty = originalQty;
  }

  public int getQty() {
    return qty;
  }

  public void setQty(int qty) {
    this.qty = qty;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public String getOrderType() {
    return orderType;
  }

  public void setOrderType(String orderType) {
    this.orderType = orderType;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public Date getTs() {
    return ts;
  }

  public void setTs(Date ts) {
    this.ts = ts;
  }

  public Collection<OrderFill> getFills() {
    return fills;
  }

  public void setFills(Collection<OrderFill> fills) {
    this.fills = fills;
  }

  public int getTotalFilled() {
    return totalFilled;
  }

  public void setTotalFilled(int totalFilled) {
    this.totalFilled = totalFilled;
  }

  public boolean isOpen() {
    return open;
  }

  public void setOpen(boolean open) {
    this.open = open;
  }

  @Override
  public String toString() {
    return "OrderResponse [ok=" + ok + ", symbol=" + symbol + ", venue=" + venue + ", direction=" + direction
        + ", originalQty=" + originalQty + ", qty=" + qty + ", price=" + price + ", orderType=" + orderType + ", id="
        + id + ", account=" + account + ", ts=" + ts + ", fills=" + fillsToString(fills) + ", totalFilled=" + totalFilled + ", open="
        + open + "]";
  }

private String fillsToString(Collection<OrderFill> fills2) {
	StringJoiner sj = new StringJoiner(":", "[", "]");
	fills2.stream().forEach(s -> sj.add(s.toString()));
	return sj.toString();
}
}
