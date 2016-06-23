package invoker;

import junit.framework.TestCase;

public class CmdFormatIntegrationTest extends IntegrationTestBase {
	public void testFormat() {
		this.drive.changeCurrentDirectory(this.dirRoot);
		this.commandInvoker.executeCommand("format c:", this.testOutput);

		TestCase.assertTrue(this.drive.getRootDirectory()
				.getNumberOfContainedDirectories() == 0);
		TestCase
				.assertTrue(this.drive.getRootDirectory().getNumberOfContainedFiles() == 0);
	}

	public void testFormatWrongDriveLetter() {
		this.drive.changeCurrentDirectory(this.dirTemp);
		int nbOfDirs = this.drive.getCurrentDirectory()
				.getNumberOfContainedDirectories();
		int nbOfFiles = this.drive.getCurrentDirectory().getNumberOfContainedFiles();
		this.commandInvoker.executeCommand("format z:", this.testOutput);

		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains(
				"specified drive does not exist"));
		TestCase.assertTrue(this.drive.getCurrentDirectory() == this.dirTemp);
		TestCase.assertTrue(this.drive.getCurrentDirectory()
				.getNumberOfContainedDirectories() == nbOfDirs);
		TestCase
				.assertTrue(this.drive.getCurrentDirectory().getNumberOfContainedFiles() == nbOfFiles);
	}

	public void testFormatResetOfCurrrentDirectory() {
		this.drive.changeCurrentDirectory(this.dirTemp);
		this.commandInvoker.executeCommand("format c:", this.testOutput);

		TestCase.assertTrue(this.drive.getCurrentDirectory().getPath()
				.toLowerCase().compareTo("c:") == 0);
	}

	public void testForPoint4() {
		this.testFormat();
	}

	public void testForPoint5() {
		this.testFormatResetOfCurrrentDirectory();
	}
}
