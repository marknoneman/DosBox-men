package invoker;

import filesystem.File;
import filesystem.FileSystemItem;
import junit.framework.TestCase;

public class CmdMkFileIntegrationTest extends IntegrationTestBase {

	public void testCreationWithNoContent() {
		String newFileName = "testFile";
		this.drive.changeCurrentDirectory(this.drive.getRootDirectory());
		int nbOfFilesBeforeTest = this.drive.getCurrentDirectory().getNumberOfContainedFiles();
		
		this.commandInvoker.executeCommand("mkfile " + newFileName, this.testOutput);
		TestCase.assertTrue(this.testOutput.getOutput().length() == 0);
		TestCase.assertTrue(this.drive.getCurrentDirectory().getNumberOfContainedFiles() == nbOfFilesBeforeTest+1);
		String newFilePath = this.drive.getCurrentDirectory().getPath() + "\\" + newFileName;
		FileSystemItem newItem = this.drive.getItemFromPath(newFilePath);
		TestCase.assertTrue(newItem != null);
		TestCase.assertTrue(newItem.isDirectory() == false);
		TestCase.assertTrue(newItem.getName().compareTo(newFileName) == 0);
		TestCase.assertTrue(((File)newItem).getFileContent().compareTo("") == 0);
	}
	
	public void testWrongParameters1() {
		this.drive.changeCurrentDirectory(this.drive.getRootDirectory());
		int nbOfFilesBeforeTest = this.drive.getCurrentDirectory().getNumberOfContainedFiles();
		
		this.commandInvoker.executeCommand("mkfile", this.testOutput);
		
		// No file shall be created
		TestCase.assertTrue(this.drive.getCurrentDirectory().getNumberOfContainedFiles() == nbOfFilesBeforeTest);
		// The following string must be output
		TestCase.assertTrue(this.testOutput.getOutput().contains("syntax of the command is incorrect") == true);
	}
	
	public void testForPoint3() {
		testCreationWithNoContent();
	}

	public void testForPoint4() {
		testWrongParameters1();
	}

	public void testForPoint5() {
		testCreationWithNoContent();
	}

	public void testForPoint6() {
		testWrongParameters1();
	}

	public void testForPoint7() {
		testCreationWithNoContent();
	}

	public void testForPoint8() {
		testWrongParameters1();
	}
}
