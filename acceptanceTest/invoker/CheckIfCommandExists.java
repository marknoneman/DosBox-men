package invoker;

public class CheckIfCommandExists extends IntegrationTestBase {

	protected void setUp() throws Exception {
		super.setUp();
	}
	
	public void testCdCmd() {
		this.checkIfCommandExists("Cd");
	}
	
	public void testChdirCmd() {
		this.checkIfCommandExists("Chdir");
	}
	
	public void testCompCmd() {
		this.checkIfCommandExists("Comp");
	}
	
	public void testDateCmd() {
		this.checkIfCommandExists("Date");
	}
	
	public void testDelCmd() {
		this.checkIfCommandExists("Del");
	}
	
	public void testDirCmd() {
		this.checkIfCommandExists("Dir");
	}
	
	public void testExitCmd() {
		this.checkIfCommandExists("Exit");
	}
	
	public void testFindCmd() {
		this.checkIfCommandExists("Find");
	}
	
	public void testFormatCmd() {
		this.checkIfCommandExists("Format");
	}
	
	public void testHelpCmd() {
		this.checkIfCommandExists("Help");
	}
	
	public void testLabelCmd() {
		this.checkIfCommandExists("Label");
	}

	public void testMdCmd() {
		this.checkIfCommandExists("Md");
	}

	public void testMfCmd() {
		this.checkIfCommandExists("Mf");
	}
	
	public void testMkdirCmd() {
		this.checkIfCommandExists("Mkdir");
	}
	
	public void testMkFileCmd() {
		this.checkIfCommandExists("Mkfile");
	}
	
	public void testMoveCmd() {
		this.checkIfCommandExists("Move");
	}
	
	public void testPopdCmd() {
		this.checkIfCommandExists("Popd");
	}
	
	public void testPushdCmd() {
		this.checkIfCommandExists("Pushd");
	}
	
	public void testRdCmd() {
		this.checkIfCommandExists("Rd");
	}
	
	public void testRenCmd() {
		this.checkIfCommandExists("Ren");
	}
	
	public void testRenameCmd() {
		this.checkIfCommandExists("Rename");
	}
	
	public void testRmdirCmd() {
		this.checkIfCommandExists("Rmdir");
	}
	
	public void testTimeCmd() {
		this.checkIfCommandExists("Time");
	}
	
	public void testTreeCmd() {
		this.checkIfCommandExists("Tree");
	}
	
	public void testTypeCmd() {
		this.checkIfCommandExists("Type");
	}
	
	public void testVerCmd() {
		this.checkIfCommandExists("Ver");
	}
	
	public void testVolCmd() {
		this.checkIfCommandExists("Vol");
	}
}
