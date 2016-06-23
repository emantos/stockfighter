package com.erle.stockfighter.api;

public enum Api {
	API_IS_UP(HttpMethod.GET, "https://api.stockfighter.io/ob/api/heartbeat"),
	START_LEVEL(HttpMethod.POST, "/gm/levels/%s"),
	RESTART_LEVEL(HttpMethod.POST, "/gm/instances/%s/restart"),
	STOP_LEVEL(HttpMethod.POST, "/gm/instances/%s/stop"),
	QUOTE(HttpMethod.GET, "/ob/api/venues/%s/stocks/%s/quote"),
	ORDER(HttpMethod.POST, "/ob/api/venues/%s/stocks/%s/orders"),
	ORDER_STATUS(HttpMethod.GET, "/ob/api/venues/%s/stocks/%s/orders/%d"),
	CANCEL_ORDER(HttpMethod.DELETE, "/ob/api/venues/%s/stocks/%s/orders/%d");

	private String formattedString;
	private HttpMethod method;

	private Api(HttpMethod method, String formattedString) {
		this.method = method;
		this.formattedString = formattedString;
	}

	public String getFormattedString() {
		return formattedString;
	}

	public HttpMethod getMethod() {
		return method;
	}
}
