package bddTests.automationTests;

import java.util.ArrayList;

import interfaces.IDrive;
import invoker.CommandInvoker;
import static org.junit.Assert.*;
import command.framework.TestOutput;
//import command.library.CmdMkDir;
import filesystem.Directory;
import filesystem.File;
import filesystem.FileSystemItem;

public class DosBoxTest {
	// utilities classes
	private CommandInvoker commandInvoker;
	private TestOutput commandOutput;

	// Standard directory structure
	private Directory rootDir;
	private Directory subDir1;
	private Directory subDir2;
	private File file1InSubDir1;
	private File file2InSubDir1;
	private IDrive drive;

	public void createStandardDirectory() {
		createRootDirectory();
		this.subDir1 = new Directory("subDir1");
		rootDir.add(this.subDir1);
		this.subDir2 = new Directory("subDir2");
		rootDir.add(this.subDir2);
		this.file1InSubDir1 = new File("file1InSubDir1", "content1");
		subDir1.add(this.file1InSubDir1);
		this.file2InSubDir1 = new File("file2InSubDir1", "content2");
		subDir1.add(this.file2InSubDir1);
	}

	public void createRootDirectory() {
		commandOutput = new TestOutput();
		commandInvoker = new CommandInvoker();
		this.rootDir = new Directory("root");
	}

	public void testACommand(String incommand) {
		commandInvoker = new CommandInvoker();
		commandInvoker.executeCommand(incommand, commandOutput);
	}

	public void checkFileOrDirectoryExists(String itemToSearchFor) {
		boolean fileOrDirectoryFound = false;
		ArrayList<FileSystemItem> resultList = rootDir.getContent();
		for (FileSystemItem fileSystemItem : resultList) {
			if (itemToSearchFor.equals(fileSystemItem.getName())) {
				fileOrDirectoryFound = true;
				break;
			}
		}
		assertTrue("directory or file ("+itemToSearchFor+") not found ", fileOrDirectoryFound);
	}

	public void checkFileOrDirectoryDoesnotExist(String itemToSearchFor) {
		boolean fileOrDirectoryFound = false;
		ArrayList<FileSystemItem> directoryContent = rootDir.getContent();
		for (FileSystemItem fileSystemItem : directoryContent) {
			if (itemToSearchFor.equals(fileSystemItem.getName())) {
				fileOrDirectoryFound = true;
				break;
			}
		}
		assertFalse("directory or file ("+itemToSearchFor+") found but should not be", fileOrDirectoryFound);
	}

	public void CheckDirectoryIsEmpty() {
		ArrayList<FileSystemItem> directoryContent = rootDir.getContent();
		assertEquals("directory is not empty",directoryContent.size(), 0);
	}

	public void checkType(String nameLookingFor, String typeLookingFor) {
		boolean correctType = false;

		ArrayList<FileSystemItem> directoryContent = rootDir.getContent();
		for (FileSystemItem fileSystemItem : directoryContent) {
			if (nameLookingFor.equals(fileSystemItem.getName())) {
				if (typeLookingFor.equalsIgnoreCase("directory")
						&& fileSystemItem.isDirectory()) {
					correctType = true;
					break;
				}
				if (typeLookingFor.equalsIgnoreCase("file")
						&& !fileSystemItem.isDirectory()) {
					correctType = false;
					break;

				}
			}
			break;
		}

		assertTrue(correctType);
	}

}
