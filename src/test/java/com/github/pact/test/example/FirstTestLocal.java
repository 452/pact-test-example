package com.github.pact.test.example;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.dius.pact.consumer.MessagePactProviderRule;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.provider.junit.PactRunner;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit.target.HttpTarget;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;

@RunWith(PactRunner.class)
@Provider("ArticlesProvider")
@PactFolder("src/test/resources/pacts")
public class FirstTestLocal {

	@Rule
	public MessagePactProviderRule mockProvider = new MessagePactProviderRule("ArticlesProvider", this);

	@TestTarget
	public final Target target = new HttpTarget(5555);

	@State("default")
	public void stateChange() {
	}

	@PactVerification(value = "ArticlesProvider", fragment = "createPact")
	@Test
	public void testArticles() throws IOException {
		byte[] currentMessage = mockProvider.getMessage();
		assertEquals("{\n" + "  \"someData\": { \"mir\": \"hello world\" }\n" + "}", new String(currentMessage));
	}

}
