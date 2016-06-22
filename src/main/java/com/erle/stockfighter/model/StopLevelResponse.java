package com.erle.stockfighter.model;

public class StopLevelResponse {
	private boolean ok;
	private String error;

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "StopLevelResponse [ok=" + ok + ", error=" + error + "]";
	}
}
