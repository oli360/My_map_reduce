package SLAVE;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Scanner;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import main.NetworkHelper;

public class SLAVE_main {

	public static void main(String[] args) throws InterruptedException, IOException {
		
		
		final String SLAVE_FILE_DIR = "/tmp/omaxwell-20/";
		
		final String intCode = args[0];
		final String computername=InetAddress.getLocalHost().getHostName();
		final String fileName = args[1];
	
		final String fileDir = SLAVE_FILE_DIR+fileName;
		final String mapDir = SLAVE_FILE_DIR+"/map/";
		final String shuffleDir = SLAVE_FILE_DIR+"/shuffle";
		final String ip_text_Dir = SLAVE_FILE_DIR+"/IP_adresse.txt";
		final String numberOnly= fileName.replaceAll("[^0-9]", "");

		final int NUMBER_OF_IPS = Integer.parseInt(args[2]);
		
		
		Scanner s = new Scanner(new File(ip_text_Dir));
		ArrayList<String> IP_Adress = new ArrayList<String>();
		while (s.hasNext()){
			IP_Adress.add(s.next());
		}
		s.close();
		

		
		////// code for MAP
		if (Integer.parseInt(intCode)==0) {
			BufferedReader br = new BufferedReader(new FileReader(fileDir));
			Multimap<Integer, String> multiMap = ArrayListMultimap.create();				
			try {
			    String line = br.readLine(); 
			    while (line != null) {
			    	String[] words = line.split("\\W+");
			        for (String word : words) {
			        	int wordHash = Math.abs(word.hashCode())%NUMBER_OF_IPS;
			        	multiMap.put(wordHash, word);	
			        }
			        line = br.readLine();
			    }
			} finally {
			    br.close();
			}
		    
			for(Integer key : multiMap.keySet()) {
				String filetoSaveName = "SP"+numberOnly+"SH"+key+".txt";
				File file = new File(mapDir+filetoSaveName);
				BufferedWriter bf = new BufferedWriter( new FileWriter(file) );
				bf.write( multiMap.get(key).toString());
				bf.flush();
				bf.close();
				}
			
		////// code for SHUFFLE	
		}else if (Integer.parseInt(intCode)==1) {
			
			final File folder = new File(mapDir);
			List<NetworkHelper> helpers = new ArrayList<NetworkHelper>();
			
			for (final File fileEntry : folder.listFiles()) {
				String map_fileName = fileEntry.toString().replace(mapDir, "");
				String fileKey = map_fileName.substring(map_fileName.indexOf("H") + 1, map_fileName.indexOf("."));
				String to_ip = IP_Adress.get(Integer.parseInt(fileKey));
				helpers.add(Slave_helper.sendFile(fileEntry,to_ip));
		    }
			Slave_helper.checkVitals(helpers);
			
			
		////// code for Reduce
		}else if (Integer.parseInt(intCode)==2) {
			
			final File folder = new File(shuffleDir);
			HashMap<String, Integer> wordCount = new HashMap<String, Integer>();
			
			for (final File fileEntry : folder.listFiles()) {
				BufferedReader br = new BufferedReader(new FileReader(fileEntry));
				try {
				    String line = br.readLine(); 
				    while (line != null) {
				    	String[] words = line.split("\\W+");
				        for (String word : words) {
				        	int count = wordCount.containsKey(word) ? wordCount.get(word) : 0;
				        	wordCount.put(word, count + 1);
				        }
				        line = br.readLine();
				    }
				} finally {
				    br.close();
				}
		    }
			
			BufferedWriter bf =  new BufferedWriter(new FileWriter("/tmp/omaxwell-20/reduce"+numberOnly+".txt"));	  
            for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
                // put key and value separated by a colon
            	if (entry.getKey()==" ") {continue;}
                bf.write(entry.getKey() + ":"
                         + entry.getValue()); 
                // new line
                bf.newLine();
            }
            bf.flush();
            bf.close();
			
			//option 1
//			File reduceFile=new File("/tmp/omaxwell-20/reduce"+numberOnly+".javaobject");
//	        FileOutputStream reduceFileos=new FileOutputStream(reduceFile);
//	        ObjectOutputStream reduceFileoos=new ObjectOutputStream(reduceFileos);
//	        reduceFileoos.writeObject(wordCount);
//	        reduceFileoos.flush();
//	        reduceFileoos.close();
//	        reduceFileos.close();
			
			
			//option 2
//			Properties properties = new Properties();
//			for (Entry<String, Integer> entry : wordCount.entrySet()) {
//			    properties.put(entry.getKey(), entry.getValue());
//			}
//			properties.store(new FileOutputStream("/tmp/omaxwell-20/reduce"+numberOnly+".properties"), null);
			
			
		}

		
		
		System.out.println(String.format("Jar executes succefully on %s",computername  ));

	}

}
