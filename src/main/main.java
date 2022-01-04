package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class main {

	public static void main(String[] args) {


		try {
			ArrayList<NetworkHelper> helpers = new ArrayList<NetworkHelper>();
			
			for(int i = 0; i < 5; i++) {
				String[] commands = {"ssh",
						"-o StrictHostKeyChecking=no ",
						String.format("omaxwell-20@tp-4b01-%s",i+20), 			
					    ";sleep 5; hostname"};
				
				System.out.println(Arrays.toString(commands));
				
				NetworkHelper helper = NetworkHelper.run(commands,String.format("tp-4b01-%s",i+20));
				helpers.add(helper);
				
			}
			for(int i = 0; i < 5; i++) {
				System.out.println(helpers.get(i).getSysOut());
			}
			
		
		}catch(IOException e) {
			e.printStackTrace();
		}
		System.out.println("Code finished");
	}

}
