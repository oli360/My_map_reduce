package MASTER;

import main.NetworkHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import DEPLOY.Deploy_helper;

public class Slave {
	
	private static final String SLAVE_JAR_DIR      = "/tmp/omaxwell-20/SLAVE.jar";
	private static final String SLAVE_FILE_DIR     = "/tmp/omaxwell-20/";
	private static final String LOCAL_DATA_DIR     = "/Users/oliviermaxwell/Desktop/SR_TEMP/data/";
	private static final String LOCAL_JAR_DIR      = "/Users/oliviermaxwell/Desktop/SR_TEMP/SLAVE.jar";
	private static final String LOCAL_IP_TEXT_DIR  = "/Users/oliviermaxwell/eclipse-workspace/INF727-Map_reduce/IP_adresse.txt";
	
	String name;
	String file;
	Boolean alive;
	Boolean deploy_jar;
	NetworkHelper helper;
	String slaveId;
	String maxSlaves;
	
	public Slave(String ip, String file,Boolean deploy_jar, int maxSlaves) {
		this.name = ip;
		this.file = file;
		this.alive = true;
		this.deploy_jar = deploy_jar;
		this.slaveId = String.format("%02d",Integer.parseInt(file.replaceAll("[^0-9]", "")));//
		this.maxSlaves = String.valueOf(maxSlaves);
	}
	
	public void kill() {this.alive = false;}
	public boolean isAlive() {return this.alive;}
	
	
	
	
	public void sendFile() throws InterruptedException{
		String[] sendtxtFileCommand = {"scp",
				LOCAL_DATA_DIR+file,
				String.format("omaxwell-20@%s:",this.name)+SLAVE_FILE_DIR};
		String[] makeDir_omax = {"ssh",
				"-o StrictHostKeyChecking=no ",
				String.format("omaxwell-20@%s",this.name),
				"mkdir /tmp/omaxwell-20"};
		String[] makeDir_map = {"ssh",
				"-o StrictHostKeyChecking=no ",
				String.format("omaxwell-20@%s",this.name),
				"mkdir /tmp/omaxwell-20/map"};
		String[] makeDir_shuf = {"ssh",
				"-o StrictHostKeyChecking=no ",
				String.format("omaxwell-20@%s",this.name),
				"mkdir /tmp/omaxwell-20/shuffle"};

		String[] sendMultipleFilesCommand = {"scp",
				LOCAL_JAR_DIR,
				LOCAL_IP_TEXT_DIR,
				LOCAL_DATA_DIR+file,
				String.format("omaxwell-20@%s:",this.name)+SLAVE_FILE_DIR };
		NetworkHelper helper;
		try {
			if (this.deploy_jar) {
				helper = NetworkHelper.run(sendMultipleFilesCommand,this.name);
			}else {
				helper = NetworkHelper.run(sendtxtFileCommand,this.name);}
			helper.getSysOut();
			//return String.format("PC %s deployed successfully",pc_ip);
		} catch (IOException e) {
			try {
				helper = NetworkHelper.run(makeDir_omax,this.name);
				TimeUnit.MILLISECONDS.sleep(500);
				helper = NetworkHelper.run(makeDir_map,this.name);
				helper = NetworkHelper.run(makeDir_shuf,this.name);
				if (this.deploy_jar) {
					helper = NetworkHelper.run(sendMultipleFilesCommand,this.name);
				}else {
					helper = NetworkHelper.run(sendtxtFileCommand,this.name);}
				helper.getSysOut();
			}catch (IOException ee) {
				ee.printStackTrace();
				this.alive =false;
			}
		}
	}
	
	public void sendFilejc() throws InterruptedException{
		String[] sendtxtFileCommand = {"scp",
				LOCAL_DATA_DIR+file,
				String.format("omaxwell-20@%s:",this.name)+SLAVE_FILE_DIR};
		String[] makeDir_omax = {"ssh",
				"-o StrictHostKeyChecking=no ",
				String.format("omaxwell-20@%s",this.name),
				"mkdir /tmp/omaxwell-20"};
		String[] makeDir_map = {"ssh",
				"-o StrictHostKeyChecking=no ",
				String.format("omaxwell-20@%s",this.name),
				"mkdir /tmp/omaxwell-20/map"};
		String[] makeDir_shuf = {"ssh",
				"-o StrictHostKeyChecking=no ",
				String.format("omaxwell-20@%s",this.name),
				"mkdir /tmp/omaxwell-20/shuffle"};

		String[] sendMultipleFilesCommand = {"scp",
				LOCAL_JAR_DIR,
				LOCAL_IP_TEXT_DIR,
				LOCAL_DATA_DIR+file,
				String.format("omaxwell-20@%s:",this.name)+SLAVE_FILE_DIR };
		NetworkHelper helper;
			try {
				helper = NetworkHelper.run(makeDir_omax,this.name);
				TimeUnit.MILLISECONDS.sleep(500);
				helper = NetworkHelper.run(makeDir_map,this.name);
				helper = NetworkHelper.run(makeDir_shuf,this.name);
				if (this.deploy_jar) {
					this.helper = NetworkHelper.run(sendMultipleFilesCommand,this.name);
				}else {
					this.helper = NetworkHelper.run(sendtxtFileCommand,this.name);}
				//helper.getSysOut();
			}catch (IOException ee) {
				ee.printStackTrace();
				this.alive =false;
			}
		
	}
	
	public void runMAP() throws IOException {
		String[] runJarCommand = {"ssh",
				String.format("omaxwell-20@%s",this.name),
				String.format("java -Xmx6000M -jar %s",SLAVE_JAR_DIR+" 0 "+this.file+ " "+this.maxSlaves)};
		
		NetworkHelper helper = NetworkHelper.run(runJarCommand,this.name);
		
		this.helper = helper;
	}
	
	public void runShuffle() throws IOException {
		String[] runJarCommand = {"ssh",
				String.format("omaxwell-20@%s",this.name),
				String.format("java -Xmx6000M -jar %s",SLAVE_JAR_DIR+" 1 "+this.file+ " "+this.maxSlaves)};
		
		NetworkHelper helper = NetworkHelper.run(runJarCommand,this.name);
		
		this.helper = helper;
	}
	
	public void runReduce() throws IOException {
		String[] runJarCommand = {"ssh",
				String.format("omaxwell-20@%s",this.name),
				String.format("java -Xmx6000M  -jar %s",SLAVE_JAR_DIR+" 2 "+this.file+ " "+this.maxSlaves)};
		
		NetworkHelper helper = NetworkHelper.run(runJarCommand,this.name);
		
		this.helper = helper;
	}
	
	public void fetchReduce() {
		String[] getReduceCommand = {"scp",
				"omaxwell-20@"+this.name+":/tmp/omaxwell-20/reduce"+this.slaveId+".txt",
				"/Users/oliviermaxwell/Desktop/SR_TEMP/reduced"};
		NetworkHelper helper = null;
		
		try {
			helper = NetworkHelper.run(getReduceCommand,"fetching reduce");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		this.helper = helper;
		
		
	}
	
	public static String checkSlaveStatus(List<Slave> slaves){
		String answer = "";
		for (Slave slave:slaves) {
			answer += "Status of slave "+ slave.name +" is "+slave.alive+"\n";
		}
		return answer;
	}
	
	public static void runSlavesMAP(List<Slave> slaves) throws IOException{
		for (Slave slave:slaves) {
			slave.runMAP();
		}
	}
	
	public static void runSlavesShuffle(List<Slave> slaves) throws IOException{
		for (Slave slave:slaves) {
			slave.runShuffle();
		}
	}
	
	public static void runSlavesReduce(List<Slave> slaves) throws IOException{
		for (Slave slave:slaves) {
			slave.runReduce();
		}
	}
	
	public static void runfectchReduce(List<Slave> slaves) throws IOException{
		for (Slave slave:slaves) {
			slave.fetchReduce();
		}
	}
	
	
	public static void checkSlaveResponse(List<Slave> slaves){
		for (Slave slave:slaves) {
			try {
				System.out.println(slave.helper.getSysOut());
			} catch (IOException e) {
				slave.alive = false;
				e.printStackTrace();
			}
		}      
	}
	
	public static void splitFile(String size) throws IOException, InterruptedException {
		//String size = "100k"; //b for kites m for mb and k for kb
		File f = new File(LOCAL_DATA_DIR);
		String[] pathnames;
		pathnames = f.list((dir, name) -> !name.equals(".DS_Store"));
		

        // For each pathname in the pathnames array
        for (String pathname : pathnames) {
            // Print the names of files and directories
            System.out.println(pathname);
        }
        if (pathnames.length != 1) {throw new IOException("input file incorrect");}
        String[] cmd= new String[] {"/usr/local/bin/gsplit",
        		"-d",
        		String.format("-b %s",size),
        		"--additional-suffix=.txt",
        		String.format("%s",LOCAL_DATA_DIR+pathnames[0]),
        		String.format("%sS",LOCAL_DATA_DIR)};
        
        Runtime run = Runtime.getRuntime();
        Process pr = run.exec(cmd);
        pr.waitFor();
        BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        String line = "";
        while ((line=buf.readLine())!=null) {
        System.out.println(line);
        }
	}
	
	
	public static String[] getSFiles() {
		File f = new File(LOCAL_DATA_DIR);
		String[] pathnames;
		pathnames = f.list((dir, name) -> !name.equals(".DS_Store"));
		
		List<String> Splits=new ArrayList<String>();  
        // For each pathname in the pathnames array
        for (String pathname : pathnames) {
            // Print the names of files and directories
        	if(pathname.startsWith("S")) {
        		Splits.add(pathname);
        	}     
        }
        
        String[] splts = Splits.stream().toArray(String[]::new);
        return splts;
	}
	
	public static String[] getMFile() {
		File f = new File(LOCAL_DATA_DIR);
		String[] pathnames;
		pathnames = f.list((dir, name) -> !name.equals(".DS_Store"));
		
		List<String> Splits=new ArrayList<String>();  
        // For each pathname in the pathnames array
        for (String pathname : pathnames) {
            // Print the names of files and directories
        	if(!pathname.startsWith("S")) {
        		Splits.add(pathname);
        	}     
        }
        
        String[] splts = Splits.stream().toArray(String[]::new);
        return splts;
	}
	//scp omaxwell-20@tp-4b01-20:/tmp/omaxwell-20/reduce0.txt /Users/oliviermaxwell/Desktop/SR_TEMP/reduced
	
}
