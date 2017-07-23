package com.ttk.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class FtpUploadFile {

	private static final String server = "localhost";
	private static final int port = 21;
	private static final String user = "laca";
	private static final String pass = "123";
	private static final String DIR = "responseFiles";

	public void fileUpload(String fileName) {

		FTPClient ftpClient = new FTPClient();
		try {

			ftpClient.connect(server, port); // open the connection
			ftpClient.login(user, pass);
			ftpClient.enterLocalPassiveMode();

			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

			File localFile = new File(DIR + "/" + fileName); // creating file

			String remoteFile = fileName;
			InputStream inputStream = new FileInputStream(localFile);

			System.out.println("Start uploading responseFile file");
			boolean done = ftpClient.storeFile(remoteFile, inputStream);
			inputStream.close();
			if (done) {
				System.out.println("The responsefile is uploaded successfully.");
			}

			inputStream.close();

		} catch (IOException ex) {
			System.out.println("Error: " + ex.getMessage());
			ex.printStackTrace();
		} finally {
			try {
				if (ftpClient.isConnected()) { // close the connection
					ftpClient.logout();
					ftpClient.disconnect();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

}
