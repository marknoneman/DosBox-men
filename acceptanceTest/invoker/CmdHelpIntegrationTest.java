package invoker;

import junit.framework.TestCase;

public class CmdHelpIntegrationTest extends IntegrationTestBase {

	public void testWithoutArgument() {
		this.commandInvoker.executeCommand("help", this.testOutput);
		
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains("dir"));
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains("mkdir"));
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains("mkfile"));
	}
	
	public void testWithArgument() {
		this.commandInvoker.executeCommand("help dir", this.testOutput);
		
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains("dir"));
	}
	
	public void testForPoint3() {
		this.testWithoutArgument();
	}

	public void testForPoint4() {
		this.testWithoutArgument();
	}

	public void testForPoint5() {
		this.testWithArgument();
	}

	public void testForPoint6() {
		this.testWithArgument();
	}
}
