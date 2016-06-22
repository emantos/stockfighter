package com.erle.stockfighter.model;

public class Order {
  private String account;
  private String venue;
  private String stock;
  private int price;
  private int qty;
  private String direction;
  private String orderType;

  public Order() {
  }

  public static Order buy(String account, String venue, String stock, int price, int qty, OrderType type) {
    return new Order(account, venue, stock, price, qty, "buy", type.getStr());
  }

  public static Order sell(String account, String venue, String stock, int price, int qty, OrderType type) {
    return new Order(account, venue, stock, price, qty, "sell", type.getStr());
  }

  private Order(String account, String venue, String stock, int price, int qty, String direction, String orderType) {
    this.account = account;
    this.venue = venue;
    this.stock = stock;
    this.price = price;
    this.qty = qty;
    this.direction = direction;
    this.orderType = orderType;
  }

  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public String getVenue() {
    return venue;
  }

  public void setVenue(String venue) {
    this.venue = venue;
  }

  public String getStock() {
    return stock;
  }

  public void setStock(String stock) {
    this.stock = stock;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public int getQty() {
    return qty;
  }

  public void setQty(int qty) {
    this.qty = qty;
  }

  public String getDirection() {
    return direction;
  }

  public void setDirection(String direction) {
    this.direction = direction;
  }

  public String getOrderType() {
    return orderType;
  }

  public void setOrderType(String orderType) {
    this.orderType = orderType;
  }

  @Override
  public String toString() {
    return "Order [account=" + account + ", venue=" + venue + ", stock=" + stock + ", price=" + price + ", qty=" + qty
        + ", direction=" + direction + ", orderType=" + orderType + "]";
  }

}
