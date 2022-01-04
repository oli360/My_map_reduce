package main;

public class TextReader {
	
	String text;
	
	
	public TextReader(String txt) {
		this.text = txt;
		
	}
	
	public static int countWordsUsingSplit(String input) { 
		if (input == null || input.isEmpty()) { return 0; } 
		String[] words = input.split("\\s+"); 
		return words.length; }

}
