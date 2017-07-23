package com.ttk.main;

import com.ttk.ftp.FtpUploadFile;
import com.ttk.io.CsvFileReader;
import com.ttk.io.CsvFileWriter;

public class Main {

	private static String fileName = "responseFile";
	private static final String CSV_TYPE = ".csv";

	public static void main(String[] args) {

		CsvFileReader reader = new CsvFileReader();
		reader.readCsvFile();
		reader.addItemsIntoDb();
		CsvFileWriter writer = new CsvFileWriter();
		fileName = new StringBuilder().append(fileName).append(System.currentTimeMillis()).append(CSV_TYPE).toString();
		writer.responseCsvWrite(reader.getResponseDataList(), fileName);
		FtpUploadFile upload = new FtpUploadFile();
		upload.fileUpload(fileName);

	}

}
