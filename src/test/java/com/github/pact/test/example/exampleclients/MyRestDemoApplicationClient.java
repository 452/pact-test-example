package com.github.pact.test.example.exampleclients;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;

public class MyRestDemoApplicationClient {
	public HttpResponse getSomeResponseFromServiceN(String baseUrl) throws IOException {
		return Request.Get(baseUrl + "/service1.json").execute().returnResponse();
	}
}