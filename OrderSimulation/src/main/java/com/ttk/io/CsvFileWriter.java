package com.ttk.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.ttk.pojo.ResponseData;

public class CsvFileWriter {

	private static final String COMMA_DELIMITER = ";";
	private static final String NEW_LINE_SEPARATOR = "\n";
	private static final String FILE_HEADER = "LineNumber;Status;Message";
	private static final String DIR = "responseFiles";

	public void responseCsvWrite(List<ResponseData> responseData, String fileName) {
		FileWriter fileWriter = null;

		try {

			fileWriter = new FileWriter(new File(DIR, fileName));

			// Write the CSV file header
			fileWriter.append(FILE_HEADER.toString());

			// Add a new line separator after the header
			fileWriter.append(NEW_LINE_SEPARATOR);

			// Write a new student object list to the CSV file
			for (ResponseData data : responseData) {
				fileWriter.append(String.valueOf(data.getLineNumber()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(data.getStatus().toString());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(data.getMessage());
				fileWriter.append(NEW_LINE_SEPARATOR);
			}

			System.out.println("responseFile was created successfully.");

		} catch (Exception e) {
			System.out.println("Error in CsvFileWriter.");
			e.printStackTrace();
		} finally {

			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter.");
				e.printStackTrace();
			}

		}

	}

}
