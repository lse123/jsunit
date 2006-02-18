package net.jsunit;

import net.jsunit.configuration.Configuration;
import net.jsunit.configuration.ConfigurationSource;
import net.jsunit.model.ResultType;

public class TwoValidLocalhostsDistributedTestTest extends DistributedTest {
    private JsUnitServer server;

    public TwoValidLocalhostsDistributedTestTest(String name) {
        super(name);
    }

    protected ConfigurationSource configurationSource() {
        return new StubConfigurationSource() {
            public String remoteMachineURLs() {
                return "http://localhost:8080, http://localhost:8080";
            }
        };
    }

    public void setUp() throws Exception {
        super.setUp();
        server = new JsUnitServer(new Configuration(new StubConfigurationSource() {
            public String browserFileNames() {
                return JsUnitServer.DEFAULT_SYSTEM_BROWSER;
            }

            public String url() {
                return "http://localhost:8080/jsunit/testRunner.html?"
                + "testPage=http://localhost:8080/jsunit/tests/jsUnitUtilityTests.html&autoRun=true&submitresults=true";
            }
        }));
        server.start();
    }

    public void testCollectResults() {
        super.testCollectResults();
        assertEquals(ResultType.SUCCESS, manager.getTestRunResult().getResultType());
    }

    public void tearDown() throws Exception {
        server.dispose();
        super.tearDown();
    }

}