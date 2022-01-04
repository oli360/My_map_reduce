package DEPLOY;

import java.util.ArrayList;
import main.NetworkHelper;


public class DEPLOY_main {
	
	public static void main(String[] args) throws InterruptedException {
		String txtFiles = "/Users/oliviermaxwell/Desktop/SR_TEMP/data/file00.txt";
		ArrayList<String> slaves = new ArrayList<String>();
	
		for(int i = 0; i < 3; i++) {
			slaves.add(String.format("tp-4b01-%s",i+20));
		}
		int i = 0;
		for (String slave : slaves) {
			String filedir = null;
			if (i<10) {
				filedir = String.format("/Users/oliviermaxwell/Desktop/SR_TEMP/data/file0%s.txt", Integer.toString(i));
			}else {
				filedir = String.format("/Users/oliviermaxwell/Desktop/SR_TEMP/data/file%s.txt", Integer.toString(i));
			}
			String output = Deploy_helper.deploy(slave);
			System.out.println(output);
			i+=1;
		}
			
			
	
	}
}
