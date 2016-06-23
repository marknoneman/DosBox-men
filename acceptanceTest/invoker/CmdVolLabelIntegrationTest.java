package invoker;

import junit.framework.TestCase;

public class CmdVolLabelIntegrationTest extends IntegrationTestBase {

	public void testSetVolumeName() {
		String testLabel = "testLabel";

		this.drive.setLabel("");
		this.commandInvoker.executeCommand("label c: " + testLabel,
				this.testOutput);
		TestCase.assertTrue(this.testOutput.toString().length() < 2);
		TestCase.assertTrue(this.drive.getLabel().compareTo(testLabel) == 0);
	}

	public void testGetVolumeName() {
		String testLabel = "Test Label";

		this.drive.setLabel(testLabel);
		this.commandInvoker.executeCommand("vol", this.testOutput);
		TestCase.assertTrue(this.testOutput.toString().contains(testLabel));
	}

	public void testDeleteCurrentVolumeLabel() {
		String defaultLabel = "Something";
		this.drive.setLabel(defaultLabel);
		this.testOutput.setCharacterThatIsRead('Y');
		this.commandInvoker.executeCommand("label", this.testOutput);

		TestCase.assertTrue(this.testOutput.characterWasRead() == true);
		TestCase.assertTrue(this.drive.getLabel().compareTo("") == 0);
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains(
				"delete current volume label"));
	}

	public void testSayNoToDeleteLabel() {
		String defaultLabel = "Something";
		this.drive.setLabel(defaultLabel);
		this.testOutput.setCharacterThatIsRead('n');
		this.commandInvoker.executeCommand("label", this.testOutput);

		TestCase.assertTrue(this.testOutput.characterWasRead() == true);
		TestCase.assertTrue(this.drive.getLabel().compareTo(defaultLabel) == 0);
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains(
				"delete current volume label"));
	}

	public void testForPoint5() {
		this.testGetVolumeName();
	}

	public void testForPoint6() {
		this.testSayNoToDeleteLabel();
	}

	public void testForPoint7() {
		this.testSetVolumeName();
	}
}
