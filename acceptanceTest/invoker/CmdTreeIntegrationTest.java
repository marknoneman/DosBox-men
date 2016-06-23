package invoker;

import junit.framework.TestCase;

public class CmdTreeIntegrationTest extends IntegrationTestBase {
	public void testTree() {
		this.drive.changeCurrentDirectory(this.dirTemp);
		this.commandInvoker.executeCommand("tree .", this.testOutput);
		
		String output = this.testOutput.toString();
		this.checkStandardConditions(output);
	}
	
	public void testTreeWithOptionF() {
		this.drive.changeCurrentDirectory(this.dirTemp);
		this.commandInvoker.executeCommand("tree . /f", this.testOutput);
		
		String output = this.testOutput.toString();
		this.checkStandardConditions(output);
		TestCase.assertTrue(output.contains(this.fileGaga.getName()));
		TestCase.assertTrue(output.contains(this.fileLog.getName()));
		TestCase.assertTrue(output.contains(this.fileMyStuff.getName()));
		TestCase.assertTrue(output.contains(this.fileTest1.getName()));
		TestCase.assertTrue(output.contains(this.fileTest2.getName()));
		TestCase.assertTrue(output.contains(this.fileTest3.getName()));
	}
	
	public void testTreeWithoutPath() {
		this.drive.changeCurrentDirectory(this.dirTemp);
		this.commandInvoker.executeCommand("tree", this.testOutput);
		
		String output = this.testOutput.toString();
		this.checkStandardConditions(output);
	}
	
	private void checkStandardConditions(String output) {
		TestCase.assertTrue(output.contains(this.dirTemp.getPath()));
		TestCase.assertTrue(output.contains("+---" + this.dirTestDir1.getName()));
		TestCase.assertTrue(output.contains("\\---" + this.dirTestDir2.getName()));
	}
	
	public void testTreeWithOtherPath() {
		this.drive.changeCurrentDirectory(this.dirProgramFiles);
		this.commandInvoker.executeCommand("tree " + this.dirTemp.getPath(), this.testOutput);
		
		String output = this.testOutput.toString();
		this.checkStandardConditions(output);
	}
	
	public void testForPoint5() {
		this.testTree();
	}
	
	public void testForPoint6() {
		this.testTree();
	}

}
