package invoker;

import java.util.ArrayList;
import java.util.Iterator;

import filesystem.Directory;
import filesystem.Drive;
import filesystem.File;

import command.framework.Command;
import command.library.CommandFactory;

import junit.framework.TestCase;

/**Setup a directory structure as follows:
 * C:\
 * +---ProgramFiles       Files WinWord.exe, Excel.exe, SkiChallenge.exe
 * +---Temp               Files gaga.txt, log.log, myStuff.doc
 *     +---TestDir1       test1.txt, test2.txt, test3.txt
 *     +---TestDir2       empty
 * +---Windows            Files command.com, clock.avi, explorer.exe, TASKMAN.exe
 *     +---system32       Files $winnt$.inf, mfc40.dll mfc40u.dll, mfc42.dll
 *     +---web            Files bullet.gif
 *     +---Microsoft.NET  Files sbs_diasymreader.dll, sbs_iehost.dll
 *
 */
public abstract class IntegrationTestBase extends TestCase {

	private static final int SLEEP_TIME_AFTER_TEST_CASE = 0;

	protected CommandInvoker commandInvoker;
	protected Drive drive;
	protected TestOutputter testOutput;
	protected Directory dirRoot;
	protected Directory dirTemp;
	protected Directory dirWindows;
	protected Directory dirProgramFiles;
	protected Directory dirWindowsSystem32;
	protected Directory dirWindowsWeb;
	protected Directory dirWindowsNet;
	protected Directory dirTestDir1;
	protected Directory dirTestDir2;

	protected File fileWinWord;
	protected File fileExcel;
	protected File fileSkiChallenge;
	protected File fileGaga;
	protected File fileLog;
	protected File fileMyStuff;
	protected File fileTest1;
	protected File fileTest2;
	protected File fileTest3;
	protected File fileCommand;
	protected File fileClock;
	protected File fileExplorer;
	protected File fileTaskman;
	protected File fileWinnt;
	protected File fileMFC40;
	protected File fileMFC40u;
	protected File fileMFC42;
	protected File fileBullet;
	protected File fileSbsDiasymreader;
	protected File fileSbsIehost;

	private CommandFactory factory;
	
	protected void setUp() throws Exception {
		super.setUp();
		
		// Setup Drive
		this.drive = new Drive("C");
		this.dirRoot = this.drive.getRootDirectory();
		
		// Create Directories
		this.dirTemp = new Directory("Temp");
		this.dirWindows = new Directory("Windows");
		this.dirProgramFiles = new Directory("ProgramFiles");
		this.dirWindowsSystem32 = new Directory("system32");
		this.dirWindowsWeb = new Directory("web");
		this.dirWindowsNet = new Directory("Microsoft.NET");
		this.dirTestDir1 = new Directory("TestDir1");
		this.dirTestDir2 = new Directory("TestDir2");
		
		// Setup Directory Structure
		this.dirRoot.add(this.dirTemp);
		this.dirRoot.add(this.dirWindows);
		this.dirRoot.add(this.dirProgramFiles);
		this.dirTemp.add(this.dirTestDir1);
		this.dirTemp.add(this.dirTestDir2);
		this.dirWindows.add(this.dirWindowsSystem32);
		this.dirWindows.add(this.dirWindowsWeb);
		this.dirWindows.add(this.dirWindowsNet);
		
		// Create Files
		this.fileWinWord = new File("WinWord.exe", "File WinWord.exe");
		this.fileExcel = new File("Excel.exe", "File Excel.exe");
		this.fileSkiChallenge = new File("SkiChallenge.exe", "File SkiChallenge.exe");
		this.fileGaga = new File("gaga.txt", "gaga.txt");
		this.fileLog = new File("log.log", "log.log");
		this.fileMyStuff = new File("myStuff.doc", "myStuff.doc");
		this.fileTest1 = new File("test1.txt", "test1.txt");
		this.fileTest2 = new File("test2.txt", "test2.txt");
		this.fileTest3 = new File("test3.txt", "test3.txt");
		this.fileCommand = new File("command.com", "command.com");
		this.fileClock = new File("clock.avi", "clock.avi");
		this.fileExplorer = new File("explorer.exe", "explorer.exe");
		this.fileTaskman = new File("TASKMAN.exe", "TASKMAN.exe");
		this.fileWinnt = new File("$winnt$.inf", "$winnt$.inf");
		this.fileMFC40 = new File("mfc40.dll", "mfc40.dll");
		this.fileMFC40u = new File("mfc40u.dll", "mfc40u.dll");
		this.fileMFC42 = new File("mfc42.dll", "mfc42.dll");
		this.fileBullet = new File("bullet.gif", "bullet.gif");
		this.fileSbsDiasymreader = new File("sbs_diasymreader.dll", "sbs_diasymreader.dll");
		this.fileSbsIehost = new File("sbs_iehost.dll", "sbs_iehost.dll");
		
		// Add files to directory structure
		this.dirProgramFiles.add(this.fileWinWord);
		this.dirProgramFiles.add(this.fileExcel);
		this.dirProgramFiles.add(this.fileSkiChallenge);
		this.dirTemp.add(this.fileGaga);
		this.dirTemp.add(this.fileLog);
		this.dirTemp.add(this.fileMyStuff);
		this.dirTestDir1.add(this.fileTest1);
		this.dirTestDir1.add(this.fileTest2);
		this.dirTestDir1.add(this.fileTest3);
		this.dirWindows.add(this.fileCommand);
		this.dirWindows.add(this.fileClock);
		this.dirWindows.add(this.fileExplorer);
		this.dirWindows.add(this.fileTaskman);
		this.dirWindowsSystem32.add(this.fileWinnt);
		this.dirWindowsSystem32.add(this.fileMFC40);
		this.dirWindowsSystem32.add(this.fileMFC40u);
		this.dirWindowsSystem32.add(this.fileMFC42);
		this.dirWindowsNet.add(this.fileBullet);
		this.dirWindowsNet.add(this.fileSbsDiasymreader);
		this.dirWindowsNet.add(this.fileSbsIehost);
		
		// Setup Command Environment
		this.factory = new CommandFactory(drive);
		this.commandInvoker = new CommandInvoker();
		this.commandInvoker.setCommands(factory.getCommandList());
		
		this.testOutput = new TestOutputter();
}

	protected void tearDown() throws Exception {
		super.tearDown();
		
		this.sleepAfterTest();
	}
	
	private void sleepAfterTest() {
		try {
			Thread.sleep(SLEEP_TIME_AFTER_TEST_CASE);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void checkIfCommandExists(String cmd) {
		ArrayList<Command> cmdList = this.factory.getCommandList();
		
		Iterator<Command> it = cmdList.iterator();
		while(it.hasNext()) {
			if(it.next().compareCmdName(cmd.toLowerCase()) == true)
				return;
		}
		
		TestCase.fail("Command \"" + cmd + "\" does not exist");
	}
}
