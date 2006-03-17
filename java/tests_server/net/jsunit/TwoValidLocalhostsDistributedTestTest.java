package net.jsunit;

import junit.framework.TestResult;
import net.jsunit.configuration.ConfigurationSource;
import net.jsunit.model.DistributedTestRunResult;
import net.jsunit.model.ResultType;

public class TwoValidLocalhostsDistributedTestTest extends EndToEndTestCase {

    protected ConfigurationSource farmSource() {
        return new StubConfigurationSource() {
            public String remoteMachineURLs() {
                return "http://localhost:"+port+", http://localhost:"+port;
            }
            public String port() {
            	return String.valueOf(port);
            }

        };
    }

    protected StubConfigurationSource serverSource() {
        return new StubConfigurationSource() {

            public String browserFileNames() {
                return BrowserLaunchSpecification.DEFAULT_SYSTEM_BROWSER;
            }

            public String url() {
                return "http://localhost:"+port+"/jsunit/testRunner.html?"
                        + "testPage=http://localhost:"+port+"/jsunit/tests/jsUnitUtilityTests.html&autoRun=true&submitresults=true";
            }
            public String port() {
            	return String.valueOf(port);
            }
        };
    }

    public void testSuccessfulRun() {
        DistributedTest test = new DistributedTest(serverSource(), farmSource());
        TestResult testResult = test.run();
        assertTrue(testResult.wasSuccessful());
        DistributedTestRunResult distributedTestRunResult = test.getDistributedTestRunManager().getDistributedTestRunResult();
        assertEquals(ResultType.SUCCESS, distributedTestRunResult.getResultType());
        assertEquals(2, distributedTestRunResult.getTestRunResults().size());

        assertNull(test.getTemporaryStandardServer());
    }

}