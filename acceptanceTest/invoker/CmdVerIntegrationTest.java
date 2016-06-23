package invoker;

import junit.framework.TestCase;

public class CmdVerIntegrationTest extends IntegrationTestBase {

	protected void setUp() throws Exception {
		super.setUp();
	}
	
	public void testVerNoOptions() {
		String output = null;

		this.commandInvoker.executeCommand("VER", this.testOutput);
		output = this.testOutput.toString().toLowerCase();
		TestCase.assertTrue(output.contains("microsoft windows xp") == true);
	}
	
	public void testVerOptionWAllUpperCase() {
		this.runAndTestVerWithOptionW("VER /W");
	}
	
	public void testVerOptionWOptionLowerCase() {
		this.runAndTestVerWithOptionW("VER /w");
	}
	
	public void testVerOptionWAllOptionUpperCase() {
		this.runAndTestVerWithOptionW("ver /W");
	}
	
	public void testVerOptionWAllLowerCase() {
		this.runAndTestVerWithOptionW("ver /w");
	}
	
	public void testVerForPoint6()
	{
		testVerNoOptions();
	}
	
	private void runAndTestVerWithOptionW(String cmd)
	{
		String output = null;
		String[] test = null;

		this.commandInvoker.executeCommand("VER /w", this.testOutput);
		output = this.testOutput.toString().toLowerCase();
		TestCase.assertTrue(output.contains("microsoft windows xp") == true);
		TestCase.assertTrue(output.contains("\n") == true);
		TestCase.assertTrue(output.contains("invalid") == false);
		TestCase.assertTrue(output.contains("incorrect") == false);
		test = output.split("\n");
		TestCase.assertTrue(test.length>1);
		TestCase.assertTrue(test[0].trim().length()>0);
	}
}
