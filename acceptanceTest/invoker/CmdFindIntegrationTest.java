package invoker;

import filesystem.File;
import junit.framework.TestCase;

public class CmdFindIntegrationTest extends IntegrationTestBase {
	
	private File searchFile;
	private String searchFileText;
	private File dirSearchFile1;
	private File dirSearchFile2;
	private File dirSearchFile3;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		
		this.searchFileText = "Rainer Grau\nDaniel Tobler\nZuehlke Engineering AG\nWiesenstrasse 10a\n8952 Schlieren";
		
		this.searchFile = new File("SearchFile.txt", this.searchFileText);
		this.dirRoot.add(this.searchFile);
		this.dirSearchFile1 = new File("dirSearchFile1.txt", searchFileText);
		this.dirTemp.add(this.dirSearchFile1);
		this.dirSearchFile2 = new File("dirSearchFile2.txt", searchFileText);
		this.dirTemp.add(this.dirSearchFile2);
		this.dirSearchFile3 = new File("dirSearchFile3.txt", searchFileText);
		this.dirProgramFiles.add(this.dirSearchFile3);
	}
	
	public void testFindInAFile() {
		this.drive.changeCurrentDirectory(this.dirRoot);
		this.commandInvoker.executeCommand("Find \"Daniel\" " + searchFile.getName(), this.testOutput);
		
		TestCase.assertTrue(this.testOutput.toString().contains("-- " + this.searchFile.getName()));
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains("daniel tobler"));
	}
	
	public void testFindInAFileOptionI() {
		this.drive.changeCurrentDirectory(this.dirRoot);
		this.commandInvoker.executeCommand("Find /i \"daniel\" " + searchFile.getName(), this.testOutput);
		
		TestCase.assertTrue(this.testOutput.toString().contains("-- " + this.searchFile.getName()));
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains("daniel tobler"));
	}
	
	public void testFindNotInAFile() {
		this.drive.changeCurrentDirectory(this.dirRoot);
		this.commandInvoker.executeCommand("Find \"Peter\" " + searchFile.getName(), this.testOutput);
		TestCase.assertTrue(this.testOutput.toString().contains("-- " + this.searchFile.getName()));
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains("daniel tobler") == false);

		this.testOutput.reset();
		this.drive.changeCurrentDirectory(this.dirRoot);
		this.commandInvoker.executeCommand("Find \"daniel\" " + searchFile.getName(), this.testOutput);
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains("-- " + this.searchFile.getName().toLowerCase()));
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains("peter") == false);
	}
	
	public void testFindInDirectory() {
		this.drive.changeCurrentDirectory(this.dirTemp);
		this.commandInvoker.executeCommand("Find \"Daniel\" .", this.testOutput);
		
		TestCase.assertTrue(this.testOutput.toString().contains("-- " + this.dirSearchFile1.getName()));
		TestCase.assertTrue(this.testOutput.toString().contains("-- " + this.dirSearchFile2.getName()));
	}
	
	public void testFindMultipleInSubdirectory() {
		this.drive.changeCurrentDirectory(this.dirRoot);
		this.commandInvoker.executeCommand("Find \"Daniel\" .", this.testOutput);
		
		TestCase.assertTrue(this.testOutput.toString().contains("-- " + this.searchFile.getName()));
		TestCase.assertTrue(this.testOutput.toString().contains("-- " + this.dirSearchFile1.getName()));
		TestCase.assertTrue(this.testOutput.toString().contains("-- " + this.dirSearchFile2.getName()));
		TestCase.assertTrue(this.testOutput.toString().contains("-- " + this.dirSearchFile3.getName()));
	}
}
