package com.github.pact.test.example;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.dius.pact.consumer.MessagePactBuilder;
import au.com.dius.pact.consumer.MessagePactProviderRule;
import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.model.v3.messaging.MessagePact;
import au.com.dius.pact.provider.junit.PactRunner;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit.loader.PactBrokerAuth;
import au.com.dius.pact.provider.junit.target.HttpTarget;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;

@RunWith(PactRunner.class)
@Provider("ArticlesProvider")
// @Consumer("ArticlesConsumer")
@PactBroker(host = "", protocol = "https", port = "443", authentication = @PactBrokerAuth(username = "", password = ""))
// @PactFolder("src/test/resources/pacts")
public class FirstTest {

	@Rule
	public MessagePactProviderRule mockProvider = new MessagePactProviderRule("ArticlesProvider", this);

	// Map<String, String> headers = MapUtils.putAll(new HashMap<String, String>(),
	// new String[] { "Content-Type", "application/json" });
	@TestTarget
	public final Target target = new HttpTarget(8080);

	@State("default")
	public void stateChange() {
	}

	// @Pact(provider = "ArticlesProvider", consumer = "ArticlesConsumer")
	// public RequestResponsePact articlesFragment(PactDslWithProvider builder) {
	// return builder.given("Pact for Issue 313").uponReceiving("retrieving article
	// data").path("/service1.json")
	// .method("GET").willRespondWith().headers(headers).status(200)
	// .body(new PactDslJsonBody().minArrayLike("service1",
	// 1).object("variants").eachKeyLike("0030")
	// .stringType("description", "sample
	// description").closeObject().closeObject().closeObject()
	// .closeArray())
	// .toPact();
	// }

	@Pact(provider = "ArticlesProvider", consumer = "ArticlesConsumer")
	public MessagePact createPact(MessagePactBuilder builder) {
		PactDslJsonBody body = new PactDslJsonBody();
		body.stringValue("testParam1", "value1");
		body.stringValue("testParam2", "value2");

		Map<String, String> metadata = new HashMap<String, String>();
		metadata.put("contentType", "application/json");

		return builder.given("SomeProviderState").expectsToReceive("a test message").withMetadata(metadata)
				.withContent(body).toPact();
	}

	@PactVerification(value = "ArticlesProvider", fragment = "createPact")
	@Test
	public void testArticles() throws IOException {
		byte[] currentMessage = mockProvider.getMessage();
		assertEquals("{\n" + "  \"someData\": { \"mir\": \"hello world\" }\n" + "}", new String(currentMessage));
	}

}
