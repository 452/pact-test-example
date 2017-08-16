package com.github.pact.test.example;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BackendSomeServiceController {

	@RequestMapping("/service1.json")
	public String service1() {
		return "{\n" + "  \"someData\": { \"mir\": \"hello world\" }\n" + "}";
	}

}