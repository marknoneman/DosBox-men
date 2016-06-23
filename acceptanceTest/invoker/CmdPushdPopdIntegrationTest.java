package invoker;

import junit.framework.TestCase;

public class CmdPushdPopdIntegrationTest extends IntegrationTestBase {

	public void testPushAndPop() {
		this.drive.changeCurrentDirectory(this.dirTemp);
		this.commandInvoker.executeCommand("pushd .", this.testOutput);
		TestCase.assertTrue(this.testOutput.toString().length() < 2);
		TestCase.assertTrue(this.drive.getCurrentDirectory() == this.dirTemp);
		
		this.drive.changeCurrentDirectory(this.dirRoot);
		this.commandInvoker.executeCommand("popd", this.testOutput);
		TestCase.assertTrue(this.testOutput.toString().length() < 2);
		TestCase.assertTrue(this.drive.getCurrentDirectory() == this.dirTemp);
	}
	
	public void testPushAndPopWithDifferentDirectory() {
		this.drive.changeCurrentDirectory(this.dirTemp);
		this.commandInvoker.executeCommand("pushd " + this.dirWindowsSystem32.getPath(), this.testOutput);
		TestCase.assertTrue(this.testOutput.toString().length() < 2);
		TestCase.assertTrue(this.drive.getCurrentDirectory() == this.dirWindowsSystem32);
		
		this.drive.changeCurrentDirectory(this.dirRoot);
		this.commandInvoker.executeCommand("popd", this.testOutput);
		TestCase.assertTrue(this.testOutput.toString().length() < 2);
		TestCase.assertTrue(this.drive.getCurrentDirectory() == this.dirWindowsSystem32);
	}
	
	public void testPopdPostCondition() {
		this.testPushAndPop();
		TestCase.assertTrue(this.drive.getCurrentDirectory() != this.dirWindows);

		this.drive.changeCurrentDirectory(this.dirWindows);
		this.commandInvoker.executeCommand("popd", this.testOutput);
		TestCase.assertTrue(this.testOutput.toString().length() < 2);
		TestCase.assertTrue(this.drive.getCurrentDirectory() == this.dirWindows);
	}
	
	public void testPushdPopdForPoint4() {
		this.testPushAndPop();
	}

	public void testPushdPopdForPoint5() {
		this.testPushAndPopWithDifferentDirectory();
	}
	
	public void testForPoint6() {
		this.testPushAndPop();
	}
	
	public void testForPoint7() {
		this.testPushAndPopWithDifferentDirectory();
	}
}
