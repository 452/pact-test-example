package com.github.pact.test.example;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.http.HttpResponse;
import org.junit.Rule;
import org.junit.Test;

import com.github.pact.test.example.exampleclients.MyRestDemoApplicationClient;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;

public class ServerDSLConsumerPactDSLJsonPayloadPactTest {

	private static final int SERVER_PORT = 8081;

	private static final String CONSUMER_NAME = "ArticlesConsumer";

	private static final String PROVIDER_NAME = "ArticlesProvider";

	private static final String DESCRIPTION_AND_JIRA_TICKET = "Pact for Issue 313";

	private static final String SOME_SERVICE_N_ENDPOINT = "/service1.json";

	@SuppressWarnings("unchecked")
	Map<String, String> headers = MapUtils.putAll(new HashMap<String, String>(),
			new String[] { "Content-Type", "application/json" });

	@Rule
	public PactProviderRuleMk2 provider = new PactProviderRuleMk2(PROVIDER_NAME, "localhost", SERVER_PORT, this);

	@Pact(provider = PROVIDER_NAME, consumer = CONSUMER_NAME)
	public RequestResponsePact articlesFragment(PactDslWithProvider builder) {
		return builder.given(DESCRIPTION_AND_JIRA_TICKET).uponReceiving("retrieving article data")
				.path(SOME_SERVICE_N_ENDPOINT).method("GET").willRespondWith().headers(headers).status(200)
				.body(new PactDslJsonBody().object("someData").stringValue("mir", "hello world")).toPact();
	}

	@PactVerification(PROVIDER_NAME)
	@Test
	public void testArticles() throws IOException {
		MyRestDemoApplicationClient providerRestClient = new MyRestDemoApplicationClient();
		HttpResponse response = providerRestClient.getSomeResponseFromServiceN("http://localhost:" + SERVER_PORT);
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		assertEquals("{\"someData\":{\"mir\":\"hello world\"}}", result.toString());
	}

}