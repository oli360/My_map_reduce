package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.oracle.tools.packager.IOUtils;

public class NetworkHelper {
	
	String name;
	BufferedReader stdInput;
	BufferedReader stdError;
	int exitVlaue;
	Process proc;
	String command;
	
	
	
	public NetworkHelper(Process proc,String name) {
		this.name = name;
		stdInput = new BufferedReader(new 
			     InputStreamReader(proc.getInputStream()));
		stdError = new BufferedReader(new 
			     InputStreamReader(proc.getErrorStream()));
		this.proc = proc;
	}
	
	public NetworkHelper(Process proc,String name,String command) {
		this.name = name;
		stdInput = new BufferedReader(new 
			     InputStreamReader(proc.getInputStream()));
		stdError = new BufferedReader(new 
			     InputStreamReader(proc.getErrorStream()));
		this.proc = proc;
		this.command =command;
	}
	
	public void getComs() throws IOException {
		
		String s = null;	
		String result = "";
		// Read the output from the command
		System.out.println("Here is the standard output of the command:");
		while ((s = stdInput.readLine()) != null) {
			System.out.println(s);
			result = result +'\n'+ s;
		}
		System.out.println("test:");
		System.out.println(result);
		// Read any errors from the attempted command
		System.out.println("Here is the standard error of the command (if any):");
		while ((s = stdError.readLine()) != null) {
			System.out.println(s);
		}
	}
	
	public String getSysOut() throws IOException{
		Boolean error =false;
		
		try {
			if(!proc.waitFor(400, TimeUnit.SECONDS)) {
			    //timeout - kill the process. 
				              
			    proc.destroy(); // consider using destroyForcibly instead
			    return String.format("Timeout on process %s ",this.name);
			    
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return String.format("InterruptedException on process %s ",this.name);
		}
		
		String s = null;	
		String result = "";
		
		
		while ((s = stdInput.readLine()) != null) {
			if (result == "") {result ='\t'+s;}
			else {result = result +'\n'+'\t'+ s;}
		}
		while ((s = stdError.readLine()) != null) {
			if (result == "") {result =s;}
			else {result = result +'\n'+'\t'+ s;}
			error = true;
		}
		if (error && this.command != null) {throw new IOException(String.format("Failed to run %s Error is :\n",this.name) 
				+"the command was "+this.command+result);}
		else if (error) {throw new IOException(String.format("Failed to run %s Error is :\n",this.name) 
				+result);}
		return result;
	}
	
	

	public static NetworkHelper run(String[] commands,String name) throws IOException {
		Runtime rt = Runtime.getRuntime();
		String command = Arrays.toString(commands);
		System.out.println(Arrays.toString(commands));
		Process proc = rt.exec(commands);
	    return new NetworkHelper(proc,name,command);

	}

		
}
