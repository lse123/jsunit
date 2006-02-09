package net.jsunit;

import net.jsunit.model.BrowserResult;

public class TestRunNotifierServer implements TestRunListener {

	public static final String TEST_RUN_FINISHED = "testRunFinished";
	public static final String TEST_RUN_STARTED = "testRunStarted";
	public static final String BROWSER_TEST_RUN_FINISHED = "browserTestRunFinished";
	public static final String BROWSER_TEST_RUN_STARTED = "browserTestRunStarted";
	public static final String END_XML = "endXml";
	private ServerSideConnection serverSideConnection;

	public TestRunNotifierServer(BrowserTestRunner runner, int port) {
		serverSideConnection = new ServerSideConnection(new StopMessageReceiver(runner), port);
	}
	
	public void browserTestRunStarted(String browserFileName) {
		serverSideConnection.sendMessage(BROWSER_TEST_RUN_STARTED);
		serverSideConnection.sendMessage(browserFileName);
	}

	public void browserTestRunFinished(String browserFileName, BrowserResult result) {
		serverSideConnection.sendMessage(BROWSER_TEST_RUN_FINISHED);
		serverSideConnection.sendMessage(browserFileName);
		serverSideConnection.sendMessage(Utility.asString(result.asXmlDocument()));
		serverSideConnection.sendMessage(END_XML);
	}
		
	public void testRunStarted() {
		serverSideConnection.connect();
		serverSideConnection.sendMessage(TEST_RUN_STARTED);
	}

	public void testRunFinished() {
		serverSideConnection.sendMessage(TEST_RUN_FINISHED);
		serverSideConnection.shutDown();
	}

	public boolean isReady() {
		return serverSideConnection.isConnected();
	}

	static class StopMessageReceiver implements MessageReceiver {

		private final BrowserTestRunner runner;

		public StopMessageReceiver(BrowserTestRunner runner) {
			this.runner = runner;
		}

		public void messageReceived(String message) {
			if ("stop".equals(message)) {
				runner.logStatus("Stopping Test Run");
				runner.dispose();
			}
		}
		
	}
	
}
