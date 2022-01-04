import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collectors;

//import com.google.common.collect.ArrayListMultimap;
//import com.google.common.collect.ImmutableMultimap;
//import com.google.common.collect.Multimap;
//import com.google.common.collect.Multiset;
//import com.google.common.collect.Ordering;
//import com.google.common.primitives.Ints;


public class playground {
	
//	   private static Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap, final boolean order)
//	    {
//
//	        List<Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>(unsortMap.entrySet());
//
//	        // Sorting the list based on values
//	        Collections.sort(list, new Comparator<Entry<String, Integer>>()
//	        {
//	            public int compare(Entry<String, Integer> o1,
//	                    Entry<String, Integer> o2)
//	            {
//	                if (order)
//	                {
//	                    return o1.getValue().compareTo(o2.getValue());
//	                }
//	                else
//	                {
//	                    return o2.getValue().compareTo(o1.getValue());
//
//	                }
//	            }
//	        });
//
//	        // Maintaining insertion order with the help of LinkedList
//	        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
//	        for (Entry<String, Integer> entry : list)
//	        {
//	            sortedMap.put(entry.getKey(), entry.getValue());
//	        }
//
//	        return sortedMap;
//	    }
//	
	
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
//		String s = "I want to walk my dog, dog   cat, and tarantula; maybe even my dog tortoise.";
//		String[] words = s.split("\\W+");
//		
//		Multimap<Integer, String> multiMap = ArrayListMultimap.create();
//		
//		for (String word : words) {
//        	int wordHash = Math.abs(word.hashCode())%3;
//        	
//        	multiMap.put(wordHash, word);	
//        		
//        }
//		
//		for (String word : words) {
//			System.out.println(word+"   "+Math.abs(word.hashCode()%3));
//		}
//		for(Integer key : multiMap.keySet()) {
//			Collection<String> test= multiMap.get(key);
//			  System.out.println(key+": "+multiMap.get(key));
//			}
//		String mapDir = "/Users/oliviermaxwell/Desktop";
//		final File folder = new File(mapDir);
//		for (final File fileEntry : folder.listFiles()) {
//			System.out.println(fileEntry);
//			//String fileKey = fileEntry.substring(fileEntry.indexOf("H") + 1, fileEntry.indexOf("."));
//	    }
//		
		
//		int number_of_files = 3;
//		String text = "franken";
//		String cmd = "wc -l /Users/oliviermaxwell/Desktop/SR_TEMP/data/"+text+".txt";
//		Runtime run = Runtime.getRuntime();
//		Process pr = run.exec(cmd);
//		pr.waitFor();
//		BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
//		String line = buf.readLine();		
//		String numer_of_lines = line.split(" ")[4];
//		//int lines_per_file = Integer.parseInt()
//		
//		
//		
//		System.out.println(numer_of_lines);
//		buf.close();
//	}
//		String size = "100k"; //b for kites m for mb and k for kb
		
//		String LOCAL_DATA_DIR     = "/Users/oliviermaxwell/Desktop/SR_TEMP/data/";
//		//String LOCAL_DATA_DIR     = "data/";
//		
//		File f = new File(LOCAL_DATA_DIR);
//		String[] pathnames;
//		pathnames = f.list((dir, name) -> !name.equals(".DS_Store"));
//		
//
//		System.out.println(Arrays.toString(pathnames));
//		for (String pathname : pathnames) {
//            // Print the names of files and directories
//            System.out.println(pathname);
//        }
//        if (pathnames.length != 1) {throw new IOException("input file incorrect");}
//        
//        String command = String.format("gsplit -d -b %s --additional-suffix=.txt %sfranken.txt %sS",size ,LOCAL_DATA_DIR,LOCAL_DATA_DIR);
//        
//        
//        //String[] cmd= new String[] {"gsplit",String.format("-d -b %s --additional-suffix=.txt %sfranken.txt %sS",size ,LOCAL_DATA_DIR,LOCAL_DATA_DIR)};
//        String[] cmd= new String[] {"/usr/local/bin/gsplit",
//        		"-d",
//        		String.format("-b %s",size),
//        		"--additional-suffix=.txt",
//        		String.format("%sfranken.txt",LOCAL_DATA_DIR),
//        		String.format("%sS",LOCAL_DATA_DIR)};
//        
//        System.out.println(Arrays.toString(cmd));
//        
//       //Process p = Runtime.getRuntime().exec(new String[]{"bash","-c","ls /home/XXX"});
//        //String[] arguments = command.split(" ") ;
//        
//        //System.out.println(Arrays.toString(arguments));
//        //System.out.println(Arrays.toString(cmd));
//        
//        //command = "pwd";
//        
//        Runtime run = Runtime.getRuntime();
//        Process pr = run.exec(cmd);
//        pr.waitFor();
//        BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
//        String line = "";
//        while ((line=buf.readLine())!=null) {
//        System.out.println(line);
//        }
        
        
//        String[] cmd= new String[] { "/bin/bash", "-c", command };
//        System.out.println(Arrays.toString(cmd));
//        Runtime.getRuntime().exec("/bin/bash -c "+command);
        
        //gsplit -d -b 100k --additional-suffix=.txt franken.txt S
        
        
//  
//		String LOCAL_DATA_DIR     = "/Users/oliviermaxwell/Desktop/SR_TEMP/data/";
//		
//		File f = new File(LOCAL_DATA_DIR);
//		String[] pathnames;
//		pathnames = f.list((dir, name) -> !name.equals(".DS_Store"));
//		
//		List<String> Splits=new ArrayList<String>();  
//        // For each pathname in the pathnames array
//        for (String pathname : pathnames) {
//            // Print the names of files and directories
//        	if(pathname.startsWith("S")) {
//        		Splits.add(pathname);
//        	}     
//        }
//        
//        String[] Splts = Splits.stream().toArray(String[]::new);
//        System.out.println(Arrays.toString(Splts));
        
        
//		// Creating an object of BufferedReader class
//        BufferedReader br
//            = new BufferedReader(new FileReader("IP_adresse.txt"));
// 
//        // Declaring a string variable
//        String st;
//        // Consition holds true till
//        // there is character in a string
//        int number_slaves = 5;
//        List<String> slaves=new ArrayList<String>(); 
//        
//        while ((st = br.readLine()) != null && slaves.size()<number_slaves ) {
//        	slaves.add(st);
//        
//        }
//        String[] slave_IP = slaves.stream().toArray(String[]::new);
//        System.out.println(Arrays.toString(slave_IP));
//        
//        
//        
//       	
		
		
		
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
