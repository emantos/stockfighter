package com.erle.stockfighter.utils;

public class PositionManager {

	private AccountManager accountManager;
	private int inventoryTolerance;
	private int tranchSize;

	public PositionManager(AccountManager accountManager, int inventoryTolerance,
			int tranchSize) {
		this.accountManager = accountManager;
		this.inventoryTolerance = inventoryTolerance;
		this.tranchSize = tranchSize;
	}

	public int getBidDepth() {
		return (inventoryTolerance - accountManager.getInventory())/tranchSize;
	}

	public int getAskDepth() {
		return (inventoryTolerance + accountManager.getInventory())/tranchSize;
	}

	public int getInventoryTolerance() {
		return inventoryTolerance;
	}

	public int getTranchSize() {
		return tranchSize;
	}
}
