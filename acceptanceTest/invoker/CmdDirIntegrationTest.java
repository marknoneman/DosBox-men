package invoker;

import junit.framework.TestCase;

public class CmdDirIntegrationTest extends IntegrationTestBase {

	/**
	 * Tests whether dir /w outputs the files in []
	 */
	public void testOptionWOutput() {
		String output = null;

		this.drive.changeCurrentDirectory(this.dirRoot);
		this.commandInvoker.executeCommand("DIR /W", this.testOutput);
		output = this.testOutput.toString().toLowerCase();
		TestCase.assertTrue(output.length() != 0);
		TestCase.assertTrue(output.contains("[temp]") == true);
		TestCase.assertTrue(output.contains("[Windows]") == true);
		TestCase.assertTrue(output.contains("[ProgramFiles]") == true);
		TestCase.assertTrue(output.contains("3 Dir(s)") == true);
	}

	/**
	 * Tests whether the output of dir /w is smaller than the output of a normal
	 * dir command.
	 */
	public void testOptionWForShortOutput() {
		String outputWithOptionW = null;
		String outputWithoutOption = null;

		this.drive.changeCurrentDirectory(this.dirRoot);
		this.commandInvoker.executeCommand("DIR /W", this.testOutput);
		outputWithOptionW = this.testOutput.toString().toLowerCase();

		this.testOutput.reset();
		this.commandInvoker.executeCommand("DIR", this.testOutput);
		outputWithoutOption = this.testOutput.toString().toLowerCase();

		TestCase.assertTrue(outputWithOptionW.length() < outputWithoutOption
				.length());
	}

	/**
	 * Tests whether dir /s outputs directories and subdirectories
	 */
	public void testOptionSForTempDirCheckListedDirectories() {
		String output = null;

		this.drive.changeCurrentDirectory(this.dirTemp);
		this.commandInvoker.executeCommand("DIR /S", this.testOutput);
		output = this.testOutput.toString().toLowerCase();
		TestCase.assertTrue(output.length() != 0);
		TestCase.assertTrue(output.contains("directory of c:\\temp") == true);
		TestCase
				.assertTrue(output.contains("directory of c:\\temp\\testdir1") == true);
		TestCase
				.assertTrue(output.contains("directory of c:\\temp\\testdir2") == true);
	}

	/**
	 * Tests whether dir /s outputs the files contained in the directory and the
	 * subdirectories
	 */
	public void testOptionSForTempDirCheckListedFiles() {
		String output = null;

		this.drive.changeCurrentDirectory(this.dirTemp);
		this.commandInvoker.executeCommand("DIR /S", this.testOutput);
		output = this.testOutput.toString().toLowerCase();
		TestCase.assertTrue(output.length() != 0);
		TestCase.assertTrue(output.contains(this.fileTest1.getName()
				.toLowerCase()) == true);
		TestCase.assertTrue(output.contains(this.fileGaga.getName()
				.toLowerCase()) == true);
	}

	/**
	 * Tests whether dir /s outputs a summary at the end like<br>
	 * <br>
	 * Total Files Listed:<br>
	 * 6 File(s) XX bytes<br>
	 * 3 Dir(s)
	 */
	public void testOptionSForTempDirCheckSummaryAtEnd() {
		String output = null;

		this.drive.changeCurrentDirectory(this.dirTemp);
		this.commandInvoker.executeCommand("DIR /S", this.testOutput);
		output = this.testOutput.toString().toLowerCase();
		TestCase.assertTrue(output.length() != 0);
		TestCase.assertTrue(output.contains("total files listed") == true);
		// Test number of total files in \temp and subdirs
		TestCase.assertTrue(output.contains("6 File(s)") == true);
	}

	/**
	 * Tests whether dir /w also outputs correct format when executed on a
	 * directory without any subdirectories.
	 */
	public void testOptionSForAnEmptyDirectory() {
		String output = null;

		this.drive.changeCurrentDirectory(this.dirTestDir2);
		this.commandInvoker.executeCommand("DIR /S", this.testOutput);
		output = this.testOutput.toString().toLowerCase();
		TestCase.assertTrue(output.length() != 0);
		TestCase.assertTrue(output.contains("0 Files") == true);
	}

	public void testDirForTask003Point3() {
		this.testOptionWForShortOutput();
	}

	public void testDirForTask003Point4() {
		this.testOptionWForShortOutput();
	}

	public void testDirForTask003Point5() {
		this.testOptionWOutput();
	}

	public void testDirForTask003Point6() {
		this.testOptionWOutput();
	}

	public void testDirForTask004Point5() {
		this.testOptionSForAnEmptyDirectory();
	}

	public void testDirForTask004Point6() {
		this.testOptionSForTempDirCheckListedDirectories();
	}

	public void testDirForTask004Point7() {
		this.testOptionSForTempDirCheckListedFiles();
	}

	public void testDirForTask004Point8() {
		this.testOptionSForTempDirCheckSummaryAtEnd();
	}
}
