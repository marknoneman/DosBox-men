package invoker;

import filesystem.File;
import filesystem.FileSystemItem;
import junit.framework.TestCase;

/**Tests command move
 * 
 *	Missing tests:
 *	- Move a file to a location where a file with that name already exists, but pass a N for No
 *	- move /Y C:\WinWord.exe C:\ProgramFiles --> upper case Y
 */
public class CmdMoveIntegrationTest extends IntegrationTestBase {

	/** Tests whether move C:\ProgramFiles\WinWord.exe C:\Temp works
	 */
	public void testSimpleMoveWithAbsolutePaths() {
		int numberOfFilesSource = this.dirProgramFiles.getNumberOfContainedFiles();
		int numberOfFilesDest   = this.dirTemp.getNumberOfContainedFiles();
		
		this.commandInvoker.executeCommand("move " + this.fileWinWord.getPath() + " " + this.dirTemp.getPath(), this.testOutput);
		
		TestCase.assertTrue(this.testOutput != null);
		TestCase.assertSame(this.fileWinWord.getParent(), this.dirTemp);
		TestCase.assertTrue(this.dirProgramFiles.getNumberOfContainedFiles() == numberOfFilesSource - 1);
		TestCase.assertTrue(this.dirTemp.getNumberOfContainedFiles() == numberOfFilesDest + 1);
	}

	/** Tests whether move WinWord.exe ..\Temp works.
	 *  Current directory is C:\ProgramFiles
	 */
	public void testSimpleMoveWithRelativePaths() {
		int numberOfFilesSource = this.dirProgramFiles.getNumberOfContainedFiles();
		int numberOfFilesDest   = this.dirTemp.getNumberOfContainedFiles();
	
		this.drive.changeCurrentDirectory(this.dirProgramFiles);
		this.commandInvoker.executeCommand("move " + this.fileWinWord.getName() + " ..\\Temp\\", this.testOutput);
		
		TestCase.assertTrue(this.testOutput != null);
		TestCase.assertSame(this.fileWinWord.getParent(), this.dirTemp);
		TestCase.assertTrue(this.dirProgramFiles.getNumberOfContainedFiles() == numberOfFilesSource - 1);
		TestCase.assertTrue(this.dirTemp.getNumberOfContainedFiles() == numberOfFilesDest + 1);
		TestCase.assertSame(drive.getCurrentDirectory(), this.dirProgramFiles);
	}

	/** Tests whether move C:\Temp\TestDir2 C:\Windows works.
	 */
	public void testMoveDirectory() {
		int numberOfDirSource = this.dirTemp.getNumberOfContainedFiles();
		int numberOfDirDest   = this.dirWindows.getNumberOfContainedFiles();
	
		this.commandInvoker.executeCommand("move " + this.dirTestDir2.getPath() + " " + this.dirWindows.getPath(), this.testOutput);
		
		TestCase.assertTrue(this.testOutput != null);
		TestCase.assertSame(this.dirTestDir2.getParent(), this.dirWindows);
		TestCase.assertTrue(this.dirTemp.getNumberOfContainedDirectories() == numberOfDirSource - 1);
		TestCase.assertTrue(this.dirWindows.getNumberOfContainedDirectories() == numberOfDirDest + 1);
		TestCase.assertSame(drive.getCurrentDirectory(), this.dirProgramFiles);
	}
	
	/** Tests whether
	 * - move WinWord.exe
	 * - move C:\Temp\NotExistingFile.txt C:\
	 * - move WinWord.exe SkiChallenge.exe C:\Temp
	 * - move
	 * - move *.* C:\Temp
	 * return errors
	 */
	public void testMovewithWrongParameters() {
		this.drive.changeCurrentDirectory(this.dirProgramFiles);
		
		this.testOutput.reset();
		this.commandInvoker.executeCommand("move WinWord.exe", this.testOutput);
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains("error"));
		
		this.testOutput.reset();
		this.commandInvoker.executeCommand("move C:\\Temp\\NotExistingFile.txt C:\\", this.testOutput);
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains("error"));
		
		this.testOutput.reset();
		this.commandInvoker.executeCommand(" move WinWord.exe SkiChallenge.exe C:\\Temp", this.testOutput);
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains("syntax of the command is incorrect"));
		
		this.testOutput.reset();
		this.commandInvoker.executeCommand(" move", this.testOutput);
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains("syntax of the command is incorrect"));
		
		this.testOutput.reset();
		this.commandInvoker.executeCommand("move *.* C:\\Temp", this.testOutput);
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains("error"));
	}

	/** Moves a file to a location where a file with the same name already exists.
	 *  Tests whether move /y C:\WinWord.exe C:\ProgramFiles\
	 *  moves the file without asking to overwrite
	 */
	public void testMoveWithOptionY() {
		String newFileContent = "This is the new file";
		File newFile = new File("WinWord.exe", newFileContent);
		this.dirRoot.add(newFile);
		int filesAtSourceBeforeMove = this.dirRoot.getNumberOfContainedFiles();
		int filesAtDestBeforeMove   = this.dirProgramFiles.getNumberOfContainedFiles();
		
		this.commandInvoker.executeCommand("move /y C:\\WinWord.exe C:\\ProgramFiles\\", this.testOutput);
		
		FileSystemItem fi = this.drive.getItemFromPath("C:\\ProgramFiles\\WinWord.exe");
		TestCase.assertTrue(fi != null);
		TestCase.assertTrue(fi.isDirectory() == false);
		File movedFile = (File)fi;
		TestCase.assertSame(newFile, movedFile);
		TestCase.assertTrue(movedFile.getFileContent().compareTo(newFileContent) == 0);
		TestCase.assertSame(movedFile.getParent(), this.dirProgramFiles);
		TestCase.assertTrue(this.fileWinWord.getParent() == null);
		TestCase.assertTrue(this.dirRoot.getNumberOfContainedFiles() == filesAtSourceBeforeMove - 1);
		TestCase.assertTrue(this.dirProgramFiles.getNumberOfContainedFiles() == filesAtDestBeforeMove);  // A file was overwritten
	}

	/** Moves a file to a location where a file with the same name already exists.
	 *  Tests whether move C:\WinWord.exe C:\ProgramFiles\
	 *  moves the file after asking to overwrite
	 */
	public void testMoveWithOverwrite() {
		String newFileContent = "This is the new file";
		File newFile = new File("WinWord.exe", newFileContent);
		this.dirRoot.add(newFile);
		int filesAtSourceBeforeMove = this.dirRoot.getNumberOfContainedFiles();
		int filesAtDestBeforeMove   = this.dirProgramFiles.getNumberOfContainedFiles();
		this.testOutput.setCharacterThatIsRead('Y');
		
		this.commandInvoker.executeCommand("move C:\\WinWord.exe C:\\ProgramFiles\\", this.testOutput);

		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains("overwrite") == true);
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains("yes/no") == true);
		TestCase.assertTrue(this.testOutput.characterWasRead() == true);
		FileSystemItem fi = this.drive.getItemFromPath("C:\\ProgramFiles\\WinWord.exe");
		TestCase.assertTrue(fi != null);
		TestCase.assertTrue(fi.isDirectory() == false);
		File movedFile = (File)fi;
		TestCase.assertSame(newFile, movedFile);
		TestCase.assertTrue(movedFile.getFileContent().compareTo(newFileContent) == 0);
		TestCase.assertSame(movedFile.getParent(), this.dirProgramFiles);
		TestCase.assertTrue(this.fileWinWord.getParent() == null);
		TestCase.assertTrue(this.dirRoot.getNumberOfContainedFiles() == filesAtSourceBeforeMove - 1);
		TestCase.assertTrue(this.dirProgramFiles.getNumberOfContainedFiles() == filesAtDestBeforeMove);  // A file was overwritten
	}
	
	public void testMoveForPoint7() {
		this.testSimpleMoveWithAbsolutePaths();
	}
	
	public void testMoveForPoint8() {
		this.testSimpleMoveWithRelativePaths();
	}
	
	public void testMoveForPoint9() {
		this.testMoveDirectory();
	}
	
	public void testMoveForPoint10() {
		this.testMoveWithOverwrite();
	}
}
