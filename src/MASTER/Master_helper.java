package MASTER;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import main.NetworkHelper;
import DEPLOY.Deploy_helper;

public class Master_helper {
	
	private static final String slave_dir = "/tmp/omaxwell-20/SLAVE_TEST.jar";
	private static final String slave_file_dir = "/tmp/omaxwell-20/";
	
	public static NetworkHelper runJar(String pc_ip) throws IOException {
		
	
		String[] runJarCommand = {"ssh",
				String.format("omaxwell-20@%s",pc_ip),
				String.format("java -jar %s",slave_dir)};
		
		NetworkHelper helper = NetworkHelper.run(runJarCommand,pc_ip);
		
		return helper;
	}
	
	public static NetworkHelper runJar(String pc_ip,String file) throws IOException {
		
		
		String[] runJarCommand = {"ssh",
				String.format("omaxwell-20@%s",pc_ip),
				String.format("java -jar %s %s",slave_dir,slave_file_dir+file)};
		
		NetworkHelper helper = NetworkHelper.run(runJarCommand,pc_ip);
		
		return helper;
	}
	
	public static String[] getSLaveIP(int number_slaves) throws IOException {
	
	// Creating an object of BufferedReader class
    BufferedReader br
        = new BufferedReader(new FileReader("IP_adresse.txt"));
    String st;
    
    List<String> slaves=new ArrayList<String>(); 
    while ((st = br.readLine()) != null && slaves.size()<number_slaves ) {
    	slaves.add(st);
    
    }
    String[] slave_IP = slaves.stream().toArray(String[]::new);
    System.out.println(Arrays.toString(slave_IP));
    return slave_IP;
	}
	
	public static boolean isUserHappy(String mainFIle, String[] IPs) {
		Scanner sc= new Scanner(System.in);
		System.out.println("Start MaprReduce of "+mainFIle); 
		System.out.print("...with machines: "+Arrays.toString(IPs));  
		System.out.print(" [y/n] ?  "); 
		String str= sc.nextLine();  
		if (str.equals("y")) {
			return true;
		}else {
			return false;
		}
		
	}
	
	public static void joinReduce() throws IOException {
		
		final File folder = new File("/Users/oliviermaxwell/Desktop/SR_TEMP/reduced");
		Map<String, Integer> wordCount = new HashMap<String, Integer>();
		
		for (final File fileEntry : folder.listFiles()) {
			BufferedReader br = new BufferedReader(new FileReader(fileEntry));
			try {
			    String line = br.readLine(); 
			    while (line != null) {
			    	String[] words = line.split(":");
			    	try {
			    		wordCount.put(words[0], Integer.parseInt(words[1]));
			    	}catch(java.lang.ArrayIndexOutOfBoundsException e) {
			    		//System.out.println("Woah I dont like this: ->"+line);
			    	}
			        line = br.readLine();
			    }
			} finally {
			    br.close();
			}
	    }
		
		//LinkedHashMap preserve the ordering of elements in which they are inserted
		LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();
		 
		wordCount.entrySet()
		    .stream()
		    .sorted(Map.Entry.comparingByValue())
		    .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
		
		//TreeMap<String, Integer> treeMap = new TreeMap<String, Integer>();
		//treeMap.putAll(wordCount);
		
		//Multimap<String, String> Test = sortedByDescendingFrequency(Multimap<String, String> multimap)
		
		
		BufferedWriter bf =  new BufferedWriter(new FileWriter("/Users/oliviermaxwell/Desktop/SR_TEMP/reduced/final_reduced.txt"));	  
        for (Map.Entry<String, Integer> entry : sortedMap.entrySet()) {
            // put key and value separated by a colon
        	if (entry.getKey()==" ") {continue;}
            bf.write(entry.getKey() + ":"
                     + entry.getValue()); 
            // new line
            bf.newLine();
        }
        bf.flush();
        bf.close();
	}
	
}
