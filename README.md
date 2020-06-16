Technologies:
- Java
- Spring
- Maven
- Rest-Assured
- Kinesis Java Client
- Allure

To launch tests you need to specify the host and kinesisEndpoint \
for tests in tests.properties file or pass env variables. \
Launch tests with maven: `mvn test` \
Generate report: `mvn allure:report` \
Generate and open report: `mvn allure:serve`

Report contains all steps and attachments with rest-assured requests and response \
in tearDown step you can find attached log file

TestData class contains all testDataProviders methods with positive and negative cases.
 