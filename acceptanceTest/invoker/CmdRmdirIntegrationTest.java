package invoker;

import filesystem.Directory;
import junit.framework.TestCase;

public class CmdRmdirIntegrationTest extends IntegrationTestBase {
	
	/** Tests whether "rmdir TestDir2" removes this directory since TestDir2 is empty.
	 */
	public void testRemoveEmptyDirectory() {
		int numberOfDirs = this.dirTemp.getNumberOfContainedDirectories();
		
		drive.changeCurrentDirectory(this.dirTemp);
		this.commandInvoker.executeCommand("rmdir " + this.dirTestDir2.getName(), this.testOutput);
		
		TestCase.assertTrue(this.testOutput != null);
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains("not empty") == false);
		TestCase.assertTrue(this.dirTestDir2.getParent() == null);
		TestCase.assertTrue(this.dirTemp.getNumberOfContainedDirectories() == numberOfDirs-1);
	}

	/** Tests whether "rmdir TestDir1" DOES NOT remove this directory since TestDir1 is not empty.
	 *  "The directory is not empty." must be returned as error string.
	 */
	public void testRemoveNotEmptyDirectory() {
		int numberOfDirs = this.dirTemp.getNumberOfContainedDirectories();
		
		drive.changeCurrentDirectory(this.dirTemp);
		this.commandInvoker.executeCommand("rmdir " + this.dirTestDir1.getName(), this.testOutput);

		TestCase.assertTrue(this.testOutput != null);
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains("not empty") == true);
		TestCase.assertTrue(this.dirTestDir1.getParent() == this.dirTemp);
		TestCase.assertTrue(this.dirTemp.getNumberOfContainedDirectories() == numberOfDirs);
	}

	/** Tests whether "rmdir /s TestDir1" removes this directory
	 *  Prior to delete the directory, the user is asked.
	 */
	public void testRemoveNotEmptyDirectoryWithOptionS() {
		int numberOfDirs = this.dirTemp.getNumberOfContainedDirectories();

		this.testOutput.setCharacterThatIsRead('y');
		drive.changeCurrentDirectory(this.dirTemp);
		this.commandInvoker.executeCommand("rmdir /s " + this.dirTestDir1.getName(), this.testOutput);

		TestCase.assertTrue(this.testOutput != null);
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains("are you sure") == true);
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains("y/n") == true);
		TestCase.assertTrue(this.testOutput.characterWasRead() == true);
		TestCase.assertTrue(this.dirTestDir1.getParent() == null);
		TestCase.assertTrue(this.dirTemp.getNumberOfContainedDirectories() == numberOfDirs-1);
	}
	
	/** Tests whether "rmdir TestDir1 /s" does not delete the directy when 'N' is passed
	 *  at the safety question.
	 */
	public void testDoNotRemoveNotEmptyDirectoryWithOptionS() {
		int numberOfDirs = this.dirTemp.getNumberOfContainedDirectories();

		this.testOutput.setCharacterThatIsRead('n');
		drive.changeCurrentDirectory(this.dirTemp);
		this.commandInvoker.executeCommand("rmdir /s " + this.dirTestDir1.getName(), this.testOutput);

		TestCase.assertTrue(this.testOutput != null);
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains("are you sure") == true);
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains("y/n") == true);
		TestCase.assertTrue(this.testOutput.characterWasRead() == true);
		TestCase.assertTrue(this.dirTestDir1.getParent() == this.dirTemp);
		TestCase.assertTrue(this.dirTemp.getNumberOfContainedDirectories() == numberOfDirs);
	}

	/** Check whether rmdir cannot remove the current directory itself, e.g. "rmdir ."
	 */
	public void testRemoveCurrentDirectory() {
		this.drive.changeCurrentDirectory(this.dirTestDir2);
		this.commandInvoker.executeCommand("rmdir /s /q " + this.dirTestDir2.getPath(), this.testOutput);
		
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains("cannot access the file"));
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains("being used by another process"));
		TestCase.assertTrue(this.dirTestDir2.getParent() != null);
	}
	
	public void testRemoveParentDirectory() {
		Directory parent = this.dirTestDir2.getParent();
		this.drive.changeCurrentDirectory(this.dirTestDir2);
		this.commandInvoker.executeCommand("rmdir /s /q " + this.dirTestDir2.getParent().getPath(), this.testOutput);
		
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains("cannot access the file"));
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains("being used by another process"));
		TestCase.assertTrue(this.dirTestDir2.getParent() != null);
		TestCase.assertTrue(parent.getParent() != null);
	}

	/** Tests whether "rmdir TestDir1 /s /q" does not ask the safety question and
	 *  deletes the directory with all its content.
	 */
	public void testRemoveWithOptionsSandQ() {
		callAndTestRemoveWithOptionSandQ("rmdir /s /q " + this.dirTestDir1.getName(), this.dirTestDir1);
	}

	/** Tests options /s and /q in different positions
	 *  Note: Normally, every option is tested in a separate Test Case. Here, on Test Case is used,
	 *  otherwise, the number of points would get very large.
	 */
	public void testRemoveWithOptionOnDifferentPositions() throws Exception {
		this.setUp();
		callAndTestRemoveWithOptionSandQ("rmdir /s /q " + this.dirTestDir1.getName(), this.dirTestDir1);

		this.setUp();
		callAndTestRemoveWithOptionSandQ("rmdir /q /s " + this.dirTestDir1.getName(), this.dirTestDir1);

		this.setUp();
		callAndTestRemoveWithOptionSandQ("rmdir " + this.dirTestDir1.getName() + " /s /q", this.dirTestDir1);

		this.setUp();
		callAndTestRemoveWithOptionSandQ("rmdir " + this.dirTestDir1.getName() + " /q /s", this.dirTestDir1);

		this.setUp();
		callAndTestRemoveWithOptionSandQ("rmdir /s " + this.dirTestDir1.getName() + " /q", this.dirTestDir1);

		this.setUp();
		callAndTestRemoveWithOptionSandQ("rmdir /q " + this.dirTestDir1.getName() + " /s", this.dirTestDir1);
	}

	/** Helper method
	 * @param commandLine
	 * @param testDir
	 */
	private void callAndTestRemoveWithOptionSandQ(String commandLine, Directory testDir) {
		Directory parent = testDir.getParent();
		TestCase.assertTrue(parent != null);
		int numberOfDirs = parent.getNumberOfContainedDirectories();
		
		this.drive.changeCurrentDirectory(parent);
		this.testOutput.setCharacterThatIsRead('y');
		this.commandInvoker.executeCommand(commandLine, this.testOutput);
		
		TestCase.assertTrue(this.testOutput != null);
		TestCase.assertTrue(this.testOutput.characterWasRead() == false);
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains("are you sure") == false);
		TestCase.assertTrue(testDir.getParent() == null);
		TestCase.assertTrue(parent.getNumberOfContainedDirectories() == numberOfDirs-1);
	}
	
	
	
}
