package com.erle.stockfighter.api;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;

import com.google.gson.Gson;

public class JsonToModelResponseHandler<T> implements ResponseHandler<T> {
	private static Gson gson = new Gson();
	private Class<T> returnObjClass;
	
	public static <T> ResponseHandler<T> instance(Class<T> returnClass) {
		return new JsonToModelResponseHandler<T>(returnClass); 
	}
	
	private JsonToModelResponseHandler(Class<T> returnClass) {
		this.returnObjClass = returnClass;
	}
	
	public T handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
		int statusCode = response.getStatusLine().getStatusCode();
		String responseStr = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
		//System.out.println(statusCode + " " + responseStr);
		if (statusCode >= 200 && statusCode < 300) {
			return gson.fromJson(responseStr, returnObjClass);
		} else {
			// throw exception that we got an error status code
			return null;
		}
	}

}
