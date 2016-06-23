package invoker;

import filesystem.Directory;
import junit.framework.TestCase;

public class CmdRenameIntegrationTest extends IntegrationTestBase {

	public void testRen() {
		this.renameTest("Ren");
	}

	public void testRename() {
		this.renameTest("Rename");
	}

	private void renameTest(String commandName) {
		String newFileName = "anotherName.tst";
		Directory previousParent = this.fileBullet.getParent();
		this.commandInvoker.executeCommand(commandName + " "
				+ this.fileBullet.getPath() + " " + newFileName,
				this.testOutput);

		TestCase
				.assertTrue(this.fileBullet.getName().compareTo(newFileName) == 0);
		TestCase.assertTrue(this.fileBullet.getParent() == previousParent);
		TestCase.assertTrue(this.testOutput.toString().length() < 2);
	}

	public void testRenameDirectory() {
		String newDirName = "MyTemp";
		Directory previousParent = this.dirTemp.getParent();
		this.commandInvoker.executeCommand("ren " + this.dirTemp.getPath()
				+ " " + newDirName, this.testOutput);

		TestCase.assertTrue(this.dirTemp.getName().compareTo(newDirName) == 0);
		TestCase.assertTrue(this.dirTemp.getParent() == previousParent);
		TestCase.assertTrue(this.testOutput.toString().length() < 2);
	}

	public void testRenameWhenNewFileAlreadyExists() {
		String newFileName = this.fileWinWord.getName();
		String previousFileName = this.fileExcel.getName();
		this.drive.changeCurrentDirectory(this.dirProgramFiles);
		this.commandInvoker.executeCommand("ren " + this.fileExcel.getName()
				+ " " + newFileName, this.testOutput);

		TestCase.assertTrue(this.fileExcel.getName()
				.compareTo(previousFileName) == 0);
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains(
				"duplicate file name"));
	}

	public void testRenameWithInvalidNewFileName() {
		this.renameWithInvalildNewFileName("Files with spaces are invalid");
		this.renameWithInvalildNewFileName("Backslash\\AreNotAllowed");
		this.renameWithInvalildNewFileName("Forwardslash/AreNotAllowed");
		this.renameWithInvalildNewFileName("Commas,AreNotAllowed");
	}

	private void renameWithInvalildNewFileName(String newFileName) {
		String previousFileName = this.fileBullet.getName();
		this.commandInvoker.executeCommand("ren " + this.fileBullet.getName()
				+ " " + newFileName, this.testOutput);

		TestCase.assertTrue(this.fileBullet.getName().compareTo(
				previousFileName) == 0);
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains("syntax"));
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains("incorrect"));
	}

	public void testForPoint6() {
		this.testRen();
	}

	public void testForPoint7() {
		this.testRenameDirectory();
	}
}
