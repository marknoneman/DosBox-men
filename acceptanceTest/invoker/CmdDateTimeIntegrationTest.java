package invoker;

import junit.framework.TestCase;

public class CmdDateTimeIntegrationTest extends IntegrationTestBase {
	public void testGetTime() {
		this.commandInvoker.executeCommand("Time", this.testOutput);

		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains(
				"the current time is"));
	}

	public void testSetAndGetTime() {
		String time = "21:30:10";

		this.commandInvoker.executeCommand("Time " + time, this.testOutput);
		TestCase.assertTrue(this.testOutput.toString().length() < 2);

		this.commandInvoker.executeCommand("time", this.testOutput);
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains(
				time));
	}

	public void testSetTimeSpecialFormat() {

		// Fails
		this.testOutput.reset();
		this.commandInvoker.executeCommand("Time gaga", this.testOutput);
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains(
				"system cannot accept"));
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains(
				"time"));

		this.testOutput.reset();
		this.commandInvoker.executeCommand("Time 25", this.testOutput);
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains(
				"system cannot accept"));
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains(
				"time"));

		this.testOutput.reset();
		this.commandInvoker.executeCommand("Time 12:70", this.testOutput);
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains(
				"system cannot accept"));
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains(
				"time"));

		// Succeeds
		this.testOutput.reset();
		this.commandInvoker.executeCommand("Time 12", this.testOutput);
		TestCase.assertTrue(this.testOutput.toString().length() < 2);
		this.testOutput.reset();
		this.commandInvoker.executeCommand("Time", this.testOutput);
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains(
				"12:00"));
	}

	public void testGetDate() {
		this.commandInvoker.executeCommand("Date", this.testOutput);

		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains(
				"the current date is"));
	}

	public void testSetAndGetDate() {
		String date = "11.03.2007";

		this.commandInvoker.executeCommand("Date " + date, this.testOutput);
		TestCase.assertTrue(this.testOutput.toString().length() < 2);

		this.commandInvoker.executeCommand("time", this.testOutput);
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains(
				date));
	}

	public void testSetDateSpecialFormat() {

		// Fails
		this.testOutput.reset();
		this.commandInvoker.executeCommand("Date gaga", this.testOutput);
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains(
				"system cannot accept"));
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains(
				"date"));

		this.testOutput.reset();
		this.commandInvoker.executeCommand("Date 29.2.2007", this.testOutput);
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains(
				"system cannot accept"));
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains(
				"date"));

		this.testOutput.reset();
		this.commandInvoker.executeCommand("Date 50", this.testOutput);
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains(
				"system cannot accept"));
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains(
				"date"));

		// Succeeds
		// None
	}

	public void testForPoint7() {
		this.testGetDate();
	}

	public void testForPoint8() {
		this.testSetTimeSpecialFormat();
	}
}
