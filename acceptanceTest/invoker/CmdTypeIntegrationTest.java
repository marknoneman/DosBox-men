package invoker;

import junit.framework.TestCase;

public class CmdTypeIntegrationTest extends IntegrationTestBase {

	public void testOutput() {
		this.commandInvoker.executeCommand("type " + this.fileTest1.getPath(),
				this.testOutput);

		TestCase.assertTrue(this.testOutput.toString().contains(
				this.fileTest1.getFileContent()));
	}

	public void testNotExistingFile() {
		this.commandInvoker.executeCommand("type NotExistingFile.gugus",
				this.testOutput);

		TestCase.assertTrue(this.testOutput.toString().contains(
				"cannot find the file specified"));
	}

	public void testForPoint3() {
		this.testOutput();
	}

	public void testForPoint4() {
		this.testNotExistingFile();
	}
}
