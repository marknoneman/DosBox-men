package invoker;

import interfaces.IOutputter;

public class TestOutputter implements IOutputter {

	private StringBuffer output;
	private char characterThatIsRead = 0;
	private boolean characterWasRead = false;
	
	public TestOutputter() {
		this.output = new StringBuffer();
	}

	@Override
	public void newLine() {
		output.append("\n");
	}

	@Override
	public void print(String text) {
		output.append(text);
	}

	@Override
	public void printLine(String line) {
		output.append(line);
		newLine();
	}
	
	public void reset() {
		this.output = new StringBuffer();
		this.characterWasRead = false;
		this.characterThatIsRead = 0;
		
	}
	
	public String getOutput() {
		return this.toString();
	}
	
	@Override
	public String toString() {
		return output.toString();
	}

	public boolean isEmpty() {
		if(this.output.length() == 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public char readSingleCharacter() {
		this.characterWasRead = true;
		return this.characterThatIsRead;
	}
	
	public void setCharacterThatIsRead(char character) {
		this.characterThatIsRead = character;
		this.characterWasRead = false;
	}

	public boolean characterWasRead() {
		return characterWasRead;
	}

	@Override
	public int numberOfCharactersPrinted() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasCharactersPrinted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void resetStatistics() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String readString() {
		// TODO Auto-generated method stub
		return null;
	}
}
