package invoker;

import junit.framework.TestCase;
import filesystem.File;
import filesystem.FileSystemItem;

public class CmdDelIntegrationTest extends IntegrationTestBase {
	
	/**Tests whether del C:\ProgramFiles\WinWord.exe deletes this file
	 */
	public void testDeleteSingleFileAbsolutePath() {
		int numberOfFiles = this.dirProgramFiles.getNumberOfContainedFiles();
		
		this.commandInvoker.executeCommand("del " + this.fileWinWord.getPath(), this.testOutput);
		
		TestCase.assertTrue(this.testOutput != null);
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains("not find") == false);
		
		FileSystemItem fi = this.drive.getItemFromPath("C:\\ProgramFiles\\WinWord.exe");
		TestCase.assertTrue(fi == null);
		TestCase.assertTrue(this.dirProgramFiles.getNumberOfContainedFiles() == numberOfFiles-1);
		TestCase.assertTrue(this.fileWinWord.getParent() == null);
	}

	/**Test whether del WinWord.exe deletes the file if it is in the current directory
	 */
	public void testDeleteSingleFileRelativePath() {
		int numberOfFiles = this.dirProgramFiles.getNumberOfContainedFiles();
		
		this.drive.changeCurrentDirectory(this.dirProgramFiles);
		this.commandInvoker.executeCommand("del " + this.fileWinWord.getName(), this.testOutput);
		
		TestCase.assertTrue(this.testOutput != null);
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains("not find") == false);
		
		FileSystemItem fi = this.drive.getItemFromPath("C:\\ProgramFiles\\WinWord.exe");
		TestCase.assertTrue(fi == null);
		TestCase.assertTrue(this.dirProgramFiles.getNumberOfContainedFiles() == numberOfFiles-1);
		TestCase.assertTrue(this.fileWinWord.getParent() == null);
	}

	/**Tests whether del c:\temp\testdir1 deletes the entire directory, but asks before deleting all files.
	 */
	public void testDeleteDirectoryAbsolutePath() {
		int numberOfDirs = this.dirTemp.getNumberOfContainedDirectories();
		
		this.testOutput.setCharacterThatIsRead('y');
		this.commandInvoker.executeCommand("del " + this.dirTestDir1, this.testOutput);
		
		TestCase.assertTrue(this.testOutput != null);
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains("are you sure"));
		TestCase.assertTrue(this.testOutput.characterWasRead() == true);
		TestCase.assertTrue(this.dirTemp.getNumberOfContainedDirectories() == numberOfDirs-1);
		TestCase.assertTrue(this.dirTestDir1.getPath() == null);
	}
	
	/**Tests whether del testfile.txt /s deletes all files in the current directory and its subdirectories
	 * Tests in addition, if a 4th file which is in a different directory is not deleted.
	 */
	public void testDeleteFilesFromSubdirectory() {
		String testFileName = "gaga.txt";
		File test1 = new File(testFileName, "");
		File test2 = new File(testFileName, "");
		File test3 = new File(testFileName, "");
		File test4 = new File(testFileName, "");
		
		this.dirTemp.add(test1);
		this.dirTestDir1.add(test2);
		this.dirTestDir1.add(test3);
		this.dirRoot.add(test4);

		String test1Path = test1.getPath();
		String test2Path = test2.getPath();
		String test3Path = test3.getPath();
		String test4Path = test4.getPath();

		this.drive.changeCurrentDirectory(this.dirTemp);
		this.commandInvoker.executeCommand("del /s " + testFileName, this.testOutput);
		
		TestCase.assertTrue(this.testOutput != null);
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains("deleted file - " + test1Path.toLowerCase()));
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains("deleted file - " + test2Path.toLowerCase()));
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains("deleted file - " + test3Path.toLowerCase()));
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains(test4Path.toLowerCase()) == false);
		TestCase.assertTrue(test1.getParent() == null);
		TestCase.assertTrue(test2.getParent() == null);
		TestCase.assertTrue(test3.getParent() == null);
		TestCase.assertTrue(test4.getParent() == this.dirRoot);
	}
	
	public void testDeleteFilesFromSubdirectory1() {
		testDeleteFilesFromSubdirectory();
	}
	
	public void testMoveForPoint6() {
		this.testDeleteSingleFileAbsolutePath();
	}

	public void testMoveForPoint7() {
		this.testDeleteSingleFileRelativePath();
	}

	public void testMoveForPoint8() {
		this.testDeleteDirectoryAbsolutePath();
	}

	public void testMoveForPoint9() {
		this.testDeleteFilesFromSubdirectory();
	}

	public void testMoveForPoint10() {
		this.testDeleteFilesFromSubdirectory1();
	}
}
