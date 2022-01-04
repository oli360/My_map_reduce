package SLAVE;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import main.NetworkHelper;

public class Slave_helper {
	
	private static final String SLAVE_FILE_DIR     = "/tmp/omaxwell-20/map";
	private static final String SEND_FILE_DIR     = "/tmp/omaxwell-20/shuffle";
	
	public static NetworkHelper sendFile(File fileEntry, String ip) throws IOException {
			String[] sendtxtFileCommand = {"scp",
			fileEntry.toString(),
			String.format("omaxwell-20@%s:",ip)+SEND_FILE_DIR};
			NetworkHelper helper;
			helper = NetworkHelper.run(sendtxtFileCommand ,"sending file form slave to slave");
			return helper;
	}
	
	public static void checkVitals(List<NetworkHelper> helpers) throws IOException {
		for (NetworkHelper helper :helpers) {
			helper.getSysOut();
		}
	}
}
