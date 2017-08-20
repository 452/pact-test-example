# pact-test-example

1 Run backend server
	1.1 Import Maven Project to Eclipse IDE
	1.2 Open com.github.pact.test.example.DemoApplication
	1.3 Right click Run As -> Java Application
	1.4 Go to http://localhost:8080/service1.json and check response with "hello world" json
	1.5 mvn clean test
	

## How to publish pact files to pact broker from some folder with mvn plugin:
```sh
mvn pact:publish
```
## How to publish pact files with JUnit:

## How to generate pact files
	1. You can use pact-proxy for capture request response from client to server in auto generated pact files
		1.1 git clone https://github.com/ddebree/pact-proxy.git
		1.2 mvn install
		1.3 java -jar target/pact-proxy-1.0.1-SNAPSHOT.jar --port=5555 --remote="http://localhost:8080"
	2. You can make Pact file with Pact DSL (DirectDSLConsumerPactTest.java)

#### Resources
https://github.com/DiUS/pact-jvm/tree/master/pact-jvm-provider-maven
https://github.com/DiUS/pact-jvm/tree/master/pact-jvm-consumer-junit/src/test/java/au/com/dius/pact/consumer/exampleclients