package com.erle.stockfighter.model;

import java.util.Date;

public class OrderFill {
  private int price;
  private int qty;
  private Date ts;

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

  public Date getTs() {
    return ts;
  }

  public void setTs(Date ts) {
    this.ts = ts;
  }

  @Override
  public String toString() {
    return "OrderFill [price=" + price + ", qty=" + qty + ", ts=" + ts + "]";
  }

}
