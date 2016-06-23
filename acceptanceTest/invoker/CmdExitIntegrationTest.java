package invoker;

import junit.framework.TestCase;

public class CmdExitIntegrationTest extends IntegrationTestBase {

	public void testNoErrorOutput() {
		this.commandInvoker.executeCommand("exit", this.testOutput);

		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains(
						"is not recognized as an internal or external command") == false);
	}

	public void testForPoint2() {
		this.testNoErrorOutput();
	}

	public void testForPoint3() {
		this.testNoErrorOutput();
	}

	public void testForPoint4() {
		this.testNoErrorOutput();
	}

	public void testForPoint5() {
		this.testNoErrorOutput();
	}
}
