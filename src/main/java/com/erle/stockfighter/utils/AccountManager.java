package com.erle.stockfighter.utils;

import com.erle.stockfighter.model.OrderFill;
import com.erle.stockfighter.model.OrderResponse;

public class AccountManager {
  private int inventory = 0;
  private int cash = 0;

  public void update(OrderResponse orderResult) {
    if(orderResult.getDirection().equals("sell")) {
      inventory -= orderResult.getTotalFilled();
      if(orderResult.getFills().size() > 0) {
        for(OrderFill orderFill : orderResult.getFills()) {
          cash += orderFill.getQty() * orderFill.getPrice();
        }
      } else {
        cash += orderResult.getTotalFilled() * orderResult.getPrice();
      }
    } else {
      inventory += orderResult.getTotalFilled();
      if(orderResult.getFills().size() > 0) {
        for(OrderFill orderFill : orderResult.getFills()) {
          cash -= orderFill.getQty() * orderFill.getPrice();
        }
      } else {
        cash -= orderResult.getTotalFilled() * orderResult.getPrice();
      }
    }
  }

  public int totalPosition(int currentPrice) {
    return cash + (inventory * currentPrice);
  }
  
  public int getInventory() {
    return inventory;
  }

  public int getCash() {
    return cash;
  }
}
