package DEPLOY;

import java.io.IOException;

import main.NetworkHelper;

public class Deploy_helper {
	
	private static final String host_dir  = "/Users/oliviermaxwell/Desktop/SR_TEMP/SLAVE_TEST.jar";
	private static final String slave_dir = "/tmp/omaxwell-20/";
			
	
	
	public static String deploy(String pc_ip) throws InterruptedException{
		String[] sendJarFileCommand = {"scp",
				host_dir,
				String.format("omaxwell-20@%s:",pc_ip)+slave_dir};
		String[] checkDirExistCommand = {"ssh",
				"-o StrictHostKeyChecking=no ", 
				String.format("omaxwell-20@%s",pc_ip),
				"[ -d \"/tmp/omaxwell-20/\" ] && echo \"1\""};
		String[] makeDir = {"ssh",
				"-o StrictHostKeyChecking=no ",
				String.format("omaxwell-20@%s",pc_ip),
				"mkdir /tmp/omaxwell-20"};
		
		try {
			//NetworkHelper helper = NetworkHelper.run(makeDir,pc_ip);
			NetworkHelper helper = NetworkHelper.run(sendJarFileCommand,pc_ip);
			System.out.println(helper.getSysOut());
			return String.format("PC %s deployed successfully",pc_ip);
		} catch (IOException e) {
			try {
				NetworkHelper helper = NetworkHelper.run(makeDir,pc_ip);
				helper = NetworkHelper.run(sendJarFileCommand,pc_ip);
				return String.format("PC %s deployed successfully had to create directory /omaxwell-20",pc_ip);
				
			}catch (IOException ee) {
				ee.printStackTrace();
				return String.format("FAILED /omaxwell-20",pc_ip);
			}
		}
	}
	
	public static String deploy(String pc_ip,String file) throws InterruptedException{
		String[] sendJarFileCommand = {"scp",
				host_dir,
				String.format("omaxwell-20@%s:",pc_ip)+slave_dir};
		String[] sendtxtFileCommand = {"scp",
				file,
				String.format("omaxwell-20@%s:",pc_ip)+slave_dir};
		String[] checkDirExistCommand = {"ssh",
				"-o StrictHostKeyChecking=no ",
				String.format("omaxwell-20@%s",pc_ip),
				"[ -d \"/tmp/omaxwell-20/\" ] && echo \"1\""};
		String[] makeDir = {"ssh",
				"-o StrictHostKeyChecking=no ",
				String.format("omaxwell-20@%s",pc_ip),
				"mkdir /tmp/omaxwell-20"};
		
		try {
			//NetworkHelper helper = NetworkHelper.run(makeDir,pc_ip);
			NetworkHelper helper = NetworkHelper.run(sendJarFileCommand,pc_ip);
			helper = NetworkHelper.run(sendtxtFileCommand,pc_ip);
			System.out.println(helper.getSysOut());
			return String.format("PC %s deployed successfully",pc_ip);
		} catch (IOException e) {
			try {
				NetworkHelper helper = NetworkHelper.run(makeDir,pc_ip);
				helper = NetworkHelper.run(sendJarFileCommand,pc_ip);
				helper = NetworkHelper.run(sendtxtFileCommand,pc_ip);
				return String.format("PC %s deployed successfully had to create directory /omaxwell-20",pc_ip);
				
			}catch (IOException ee) {
				ee.printStackTrace();
				return String.format("FAILED /omaxwell-20",pc_ip);
			}
		}
	}
	
	public static String sendFile(String pc_ip,String file) throws InterruptedException{
		String[] sendJarFileCommand = {"scp",
				host_dir,
				String.format("omaxwell-20@%s:",pc_ip)+slave_dir};
		String[] sendtxtFileCommand = {"scp",
				file,
				String.format("omaxwell-20@%s:",pc_ip)+slave_dir};
		String[] checkDirExistCommand = {"ssh",
				"-o StrictHostKeyChecking=no ",
				String.format("omaxwell-20@%s",pc_ip),
				"[ -d \"/tmp/omaxwell-20/\" ] && echo \"1\""};
		String[] makeDir = {"ssh",
				"-o StrictHostKeyChecking=no ",
				String.format("omaxwell-20@%s",pc_ip),
				"mkdir /tmp/omaxwell-20"};
		
		try {
			//NetworkHelper helper = NetworkHelper.run(makeDir,pc_ip);
			NetworkHelper helper = NetworkHelper.run(sendJarFileCommand,pc_ip);
			helper = NetworkHelper.run(sendtxtFileCommand,pc_ip);
			System.out.println(helper.getSysOut());
			return String.format("PC %s deployed successfully",pc_ip);
		} catch (IOException e) {
			try {
				NetworkHelper helper = NetworkHelper.run(makeDir,pc_ip);
				helper = NetworkHelper.run(sendJarFileCommand,pc_ip);
				helper = NetworkHelper.run(sendtxtFileCommand,pc_ip);
				return String.format("PC %s deployed successfully had to create directory /omaxwell-20",pc_ip);
				
			}catch (IOException ee) {
				ee.printStackTrace();
				return String.format("FAILED /omaxwell-20",pc_ip);
			}
		}
	}
	
	
	public static String clean(String pc_ip) throws InterruptedException{
		String[] delDir = {"ssh",
				"-o StrictHostKeyChecking=no ",
				String.format("omaxwell-20@%s",pc_ip),
				"rm -R /tmp/omaxwell-20"};
		
		try {
			NetworkHelper helper = NetworkHelper.run(delDir,pc_ip);
			System.out.println(helper.getSysOut());
			return String.format("PC %s cleaned successfully",pc_ip);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return String.format("%s not clean",pc_ip);
	}
}