package invoker;

import junit.framework.TestCase;
import filesystem.File;

public class CmdCompIntegrationTest extends IntegrationTestBase {
	
	private File file1;
	private File file2;
	private File file3;
	private File file4;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		
		String content1 = "This is Content A";
		String content2 = "This is Content B";
		String content3 = "this is content b";  // same as content2, but lower case 
		this.file1 = new File("file1_Content1", content1);
		this.file2 = new File("file2_Content1", content1);
		this.file3 = new File("file3_Content2", content2);
		this.file4 = new File("file4_Content3", content3);
				
		this.drive.getRootDirectory().add(this.file1);
		this.drive.getRootDirectory().add(this.file2);
		this.drive.getRootDirectory().add(this.file3);
		this.drive.getRootDirectory().add(this.file4);		
	}
	public void testCompareSingleFiles() {
		this.drive.changeCurrentDirectory(this.drive.getRootDirectory());
		this.commandInvoker.executeCommand("comp " + this.file1.getName() + " " + this.file2.getName(), this.testOutput);
		
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains("files compare ok"));
	}
	
	public void testCompareSingleFilesNoMatch() {
		this.drive.changeCurrentDirectory(this.drive.getRootDirectory());
		this.commandInvoker.executeCommand("comp " + this.file1.getName() + " " + this.file3.getName(), this.testOutput);
		
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains("compare error"));
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains("files compare ok") == false);
	}
	
	public void testCompareDirectories() {
		//TODO
		testCompareSingleFiles();
	}
	
	public void testParameterSlashC() {
		this.drive.changeCurrentDirectory(this.drive.getRootDirectory());
		this.commandInvoker.executeCommand("comp /c " + this.file3.getName() + " " + this.file4.getName(), this.testOutput);
		
		TestCase.assertTrue(this.testOutput.toString().toLowerCase().contains("files compare ok"));
	}
}
