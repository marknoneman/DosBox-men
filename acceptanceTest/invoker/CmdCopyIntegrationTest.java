package invoker;

import filesystem.File;
import filesystem.FileSystemItem;
import junit.framework.TestCase;

public class CmdCopyIntegrationTest extends IntegrationTestBase {
	
	/** Tests whether copy C:\ProgramFiles\WinWord.exe C:\Temp works
	 */
	public void testSimpleCopyWithAbsolutePaths() {
		int numberOfFilesSource = this.dirProgramFiles.getNumberOfContainedFiles();
		int numberOfFilesDest   = this.dirTemp.getNumberOfContainedFiles();
		
		this.commandInvoker.executeCommand("copy " + this.fileWinWord.getPath() + " " + this.dirTemp.getPath(), this.testOutput);
		
		TestCase.assertTrue(this.testOutput != null);
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains("1 file(s) copied"));
		
		FileSystemItem fi = this.drive.getItemFromPath("C:\\Temp\\WinWord.exe");
		TestCase.assertTrue(fi != null);
		TestCase.assertTrue(fi.isDirectory() == false);
		File copiedFile = (File)fi;
		TestCase.assertSame(copiedFile.getParent(), this.dirTemp);
		TestCase.assertSame(this.fileWinWord.getParent(), this.dirProgramFiles);
		TestCase.assertNotSame(this.fileWinWord, copiedFile);
		TestCase.assertTrue(this.fileWinWord.getFileContent().compareTo(copiedFile.getFileContent()) == 0);
		TestCase.assertTrue(this.dirProgramFiles.getNumberOfContainedFiles() == numberOfFilesSource);
		TestCase.assertTrue(this.dirTemp.getNumberOfContainedFiles() == numberOfFilesDest + 1);
	}

	/** Tests whether copy WinWord.exe ..\Temp works.
	 *  Current directory is C:\ProgramFiles
	 */
	public void testSimpleCopyWithRelativePaths() {
		int numberOfFilesSource = this.dirProgramFiles.getNumberOfContainedFiles();
		int numberOfFilesDest   = this.dirTemp.getNumberOfContainedFiles();

		this.drive.changeCurrentDirectory(this.dirProgramFiles);
		this.commandInvoker.executeCommand("copy " + this.fileWinWord.getName() + " ..\\Temp\\", this.testOutput);
		
		TestCase.assertTrue(this.testOutput != null);
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains("1 file(s) copied"));
		
		FileSystemItem fi = this.drive.getItemFromPath("C:\\Temp\\WinWord.exe");
		TestCase.assertTrue(fi != null);
		TestCase.assertTrue(fi.isDirectory() == false);
		File copiedFile = (File)fi;
		TestCase.assertSame(copiedFile.getParent(), this.dirTemp);
		TestCase.assertSame(this.fileWinWord.getParent(), this.dirProgramFiles);
		TestCase.assertNotSame(this.fileWinWord, copiedFile);
		TestCase.assertTrue(this.fileWinWord.getFileContent().compareTo(copiedFile.getFileContent()) == 0);
		TestCase.assertTrue(this.dirProgramFiles.getNumberOfContainedFiles() == numberOfFilesSource);
		TestCase.assertTrue(this.dirTemp.getNumberOfContainedFiles() == numberOfFilesDest + 1);
		TestCase.assertSame(drive.getCurrentDirectory(), this.dirProgramFiles);
	}

	/** Tests whether
	 * - copy WinWord.exe
	 * - copy C:\Temp\NotExistingFile.txt C:\
	 * - copy WinWord.exe SkiChallenge.exe C:\Temp
	 * - copy
	 * - copy *.* C:\Temp
	 * return errors
	 */
	public void testMovewithWrongParameters() {
		this.drive.changeCurrentDirectory(this.dirProgramFiles);
		
		this.testOutput.reset();
		this.commandInvoker.executeCommand("copy WinWord.exe", this.testOutput);
		TestCase.assertTrue(   this.testOutput.toString().toLowerCase().contains("cannot find")
				            || this.testOutput.toString().toLowerCase().contains("is incorrect"));
		
		this.testOutput.reset();
		this.commandInvoker.executeCommand("copy C:\\Temp\\NotExistingFile.txt C:\\", this.testOutput);
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains("cannot find"));
		
		this.testOutput.reset();
		this.commandInvoker.executeCommand("copy WinWord.exe SkiChallenge.exe C:\\Temp", this.testOutput);
		TestCase.assertTrue(   this.testOutput.toString().toLowerCase().contains("syntax of the command is incorrect")
				            || this.testOutput.toString().toLowerCase().contains("cannot find the file specified"));
		
		this.testOutput.reset();
		this.commandInvoker.executeCommand("copy", this.testOutput);
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains("syntax of the command is incorrect"));
		
		this.testOutput.reset();
		this.commandInvoker.executeCommand("copy *.* C:\\Temp", this.testOutput);
		TestCase.assertTrue(   this.testOutput.toString().toLowerCase().contains("syntax of the command is incorrect")
	                        || this.testOutput.toString().toLowerCase().contains("cannot find the file specified"));
	}

	/** Copies a file to a location where a file with the same name already exists.
	 *  Tests whether copy /y C:\WinWord.exe C:\ProgramFiles\
	 *  copies the file without asking to overwrite
	 */
	public void testCopyWithOptionY() {
		String newFileContent = "This is the new file";
		File newFile = new File("WinWord.exe", newFileContent);
		this.dirRoot.add(newFile);
		int filesAtSourceBeforeMove = this.dirRoot.getNumberOfContainedFiles();
		int filesAtDestBeforeMove   = this.dirProgramFiles.getNumberOfContainedFiles();
		
		this.commandInvoker.executeCommand("copy /y C:\\WinWord.exe C:\\ProgramFiles\\", this.testOutput);
		
		TestCase.assertTrue(this.testOutput != null);
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains("1 file(s) copied"));
		FileSystemItem fi = this.drive.getItemFromPath("C:\\ProgramFiles\\WinWord.exe");
		TestCase.assertTrue(fi != null);
		TestCase.assertTrue(fi.isDirectory() == false);
		File copiedFile = (File)fi;
		TestCase.assertNotSame(newFile, copiedFile);
		TestCase.assertTrue(copiedFile.getFileContent().compareTo(newFileContent) == 0);
		TestCase.assertSame(copiedFile.getParent(), this.dirProgramFiles);
		TestCase.assertSame(newFile.getParent(), this.dirRoot);
		TestCase.assertTrue(this.fileWinWord.getParent() == null);
		TestCase.assertTrue(this.dirRoot.getNumberOfContainedFiles() == filesAtSourceBeforeMove);
		TestCase.assertTrue(this.dirProgramFiles.getNumberOfContainedFiles() == filesAtDestBeforeMove);  // A file was overwritten
	}

	/** Copies a file to a location where a file with the same name already exists.
	 *  Tests whether copy C:\WinWord.exe C:\ProgramFiles\
	 *  copies the file after asking to overwrite
	 */
	public void testCopyWithOverwrite() {
		String newFileContent = "This is the new file";
		File newFile = new File("WinWord.exe", newFileContent);
		this.dirRoot.add(newFile);
		int filesAtSourceBeforeCopy = this.dirRoot.getNumberOfContainedFiles();
		int filesAtDestBeforeCopy   = this.dirProgramFiles.getNumberOfContainedFiles();
		this.testOutput.setCharacterThatIsRead('Y');
		
		this.commandInvoker.executeCommand("copy C:\\WinWord.exe C:\\ProgramFiles\\", this.testOutput);

		TestCase.assertTrue(this.testOutput != null);
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains("overwrite") == true);
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains("yes/no") == true);
		TestCase.assertTrue(this.testOutput.characterWasRead() == true);
		FileSystemItem fi = this.drive.getItemFromPath("C:\\ProgramFiles\\WinWord.exe");
		TestCase.assertTrue(fi != null);
		TestCase.assertTrue(fi.isDirectory() == false);
		File copiedFile = (File)fi;
		TestCase.assertNotSame(newFile, copiedFile);
		TestCase.assertTrue(copiedFile.getFileContent().compareTo(newFileContent) == 0);
		TestCase.assertSame(copiedFile.getParent(), this.dirProgramFiles);
		TestCase.assertSame(newFile.getParent(), this.dirRoot);
		TestCase.assertTrue(this.fileWinWord.getParent() == null);
		TestCase.assertTrue(this.dirRoot.getNumberOfContainedFiles() == filesAtSourceBeforeCopy);
		TestCase.assertTrue(this.dirProgramFiles.getNumberOfContainedFiles() == filesAtDestBeforeCopy);  // A file was overwritten
	}
	
	public void testDirectoryCopyWithAbsolutePaths() {
		int dirsAtDestBeforeCopy = this.dirRoot.getNumberOfContainedDirectories();
		
		this.commandInvoker.executeCommand("copy C:\\Temp C:\\CopyOfTemp", this.testOutput);
		
		TestCase.assertTrue(this.testOutput != null);
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains("TestDir1\\test1.txt"));
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains("TestDir1\\test2.txt"));
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains("gaga.txt"));
		TestCase.assertTrue(this.dirRoot.getNumberOfContainedDirectories() == dirsAtDestBeforeCopy + 1);
		TestCase.assertTrue(this.drive.getItemFromPath("C:\\CopyOfTemp\\Gaga.txt") != null);
		TestCase.assertTrue(this.drive.getItemFromPath("C:\\CopyOfTemp\\TestDir2") != null);
		TestCase.assertTrue(this.drive.getItemFromPath("C:\\CopyOfTemp\\TestDir1\\test1.txt") != null);
		TestCase.assertTrue(this.drive.getItemFromPath("C:\\CopyOfTemp\\TestDir2\\test2.txt") != null);
	}

	public void testDirectoryCopyWithRelativePaths() {
		TestCase.assertTrue(false);
	}

	public void testCopyForPoint8() {
		testCopyWithOptionY();
	}

	public void testCopyForPoint9() {
		testCopyWithOverwrite();
	}

	public void testCopyForPoint10() {
		testSimpleCopyWithAbsolutePaths();
	}
}
