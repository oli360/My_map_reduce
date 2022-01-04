package DEPLOY;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CLEAN_main {

	public static void main(String[] args) throws InterruptedException, IOException {
		// Creating an object of BufferedReader class
	    BufferedReader br
	        = new BufferedReader(new FileReader("IP_adresse.txt"));
	    String st;
	    
	    List<String> slaves=new ArrayList<String>(); 
	    while ((st = br.readLine()) != null) {
	    	slaves.add(st);
	    
	    }
	    String[] slave_IP = slaves.stream().toArray(String[]::new);
//		for(int i = 0; i < 5; i++) {
//			slaves.add(String.format("tp-4b01-%s",i+20));
//		}
		
		for (String slave : slave_IP) {
			String output = Deploy_helper.clean(slave);
			System.out.println(output);
		}
			

	}

}
