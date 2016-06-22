package com.erle.stockfighter.model;

public enum OrderType {

  LIMIT("limit"), MARKET("market"), FILL_OR_KILL("fill-or-kill"), IMMEDIATE_OR_CANCEL("immediate-or-cancel");

  private String str;

  OrderType(String str) {
    this.str = str;
  }

  public String getStr() {
    return str;
  }

}
