package MASTER;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import main.NetworkHelper;

public class MASTER_main {
	
	public static void main(String[] args) throws Exception {
		
		List<String> times = new ArrayList<String>();
//		for(int ii = 1; ii <= 10; ii++)
//		{
			
		
		
		long startTime = System.currentTimeMillis();
		///////////////////////////
		///////////// IMPORTANT //////////////
		//////DEPLOY JAR FILES?????
		boolean deploy_jar = true;
		boolean split_file = true;
		boolean justCleared = true;
		
		String size = "70m"; //b for bites, m for mb and k for kb
		
		String[] fileToMap = Slave.getMFile(); 
		
		if (split_file) {Slave.splitFile(size);}
		
		List<Integer> tmies=new ArrayList<Integer>();  
		
		// create list with file names 
		//String[] files_to_send  = {"S0.txt","S1.txt","S2.txt"};//,"S3.txt","S4.txt"};
		String[] files_to_send  = Slave.getSFiles();
		 
		
		// create list with IPnames names 
		//String[] slave_ips = {"tp-4b01-20","tp-4b01-21","tp-4b01-22"};//,"tp-4b01-23","tp-4b01-24"};
		String[] slave_ips = Master_helper.getSLaveIP(files_to_send.length);
		
		
		if (files_to_send.length != slave_ips.length) {
			throw new Exception("File list and Ip list must match in lenght");
		}
		
		System.out.println("Starting map reduce with "+files_to_send.length+" nodes"+" time is "+( System.currentTimeMillis() - startTime)+"ms");
		tmies.add((int) (System.currentTimeMillis() - startTime));
//		if (!Master_helper.isUserHappy(fileToMap[0], slave_ips)) {
//			throw new Exception("User teriminated run");
//		}
		
		//long startTime = System.currentTimeMillis();
		
		// create list of slaves
		List<Slave> slaves=new ArrayList<Slave>();  
		for(int i=0;i<slave_ips.length;i++) {
			slaves.add(new Slave(slave_ips[i],files_to_send[i],deploy_jar,slave_ips.length));
		}
		System.out.println(Slave.checkSlaveStatus(slaves));
		
		//long startTime = System.currentTimeMillis();
		
		System.out.println("////////Sending Files"+" time is "+( System.currentTimeMillis() - startTime)+"ms");
		tmies.add((int) (System.currentTimeMillis() - startTime));
		for(Slave slave:slaves) {
			if (justCleared) {
				slave.sendFilejc();
			}else {
				slave.sendFile();
			}
		}
		
		
		//Slave.checkSlaveResponse(slaves)
		Slave.checkSlaveResponse(slaves);
		System.out.println(Slave.checkSlaveStatus(slaves));
		 
		//long startTime = System.currentTimeMillis();
		
		System.out.println("////////Running MAP"+" time is "+( System.currentTimeMillis() - startTime)+"ms");
		tmies.add((int) (System.currentTimeMillis() - startTime));
		Slave.runSlavesMAP(slaves);
		Slave.checkSlaveResponse(slaves);
		System.out.println(Slave.checkSlaveStatus(slaves));
		
		
		System.out.println("////////Running Shuffle"+" time is "+( System.currentTimeMillis() - startTime)+"ms");
		tmies.add((int) (System.currentTimeMillis() - startTime));
		Slave.runSlavesShuffle(slaves);
		Slave.checkSlaveResponse(slaves);
		System.out.println(Slave.checkSlaveStatus(slaves));
		
		System.out.println("////////Running Reduce"+" time is "+( System.currentTimeMillis() - startTime)+"ms");
		tmies.add((int) (System.currentTimeMillis() - startTime));
		Slave.runSlavesReduce(slaves);
		Slave.checkSlaveResponse(slaves);
		System.out.println(Slave.checkSlaveStatus(slaves));
		
		long finishTime = System.currentTimeMillis() ;
		System.out.println("////////Fetching files"+" time is "+( System.currentTimeMillis() - startTime)+"ms");
		tmies.add((int) (System.currentTimeMillis() - startTime));
		Slave.runfectchReduce(slaves);
		Slave.checkSlaveResponse(slaves);
		System.out.println(Slave.checkSlaveStatus(slaves));
		     
		System.out.println("////////Concatinating Reduce"+" time is "+( System.currentTimeMillis() - startTime)+"ms");
		tmies.add((int) (System.currentTimeMillis() - startTime));
		Master_helper.joinReduce();
		
		System.out.println("////////Finished in "+((finishTime - startTime))+"ms");
		tmies.add((int) (System.currentTimeMillis() - startTime));
		System.out.print(tmies.toString());
		//times.add(((finishTime - startTime)/1000)+"s");
//		}
//		for(int i=0;i<times.size();i++){
//		    System.out.println(times.get(i));
//		}
	}
//  5s
//	4s
//	5s
//	4s
//	4s
//	4s
//	5s
//	4s
//	5s
//	5s

}
