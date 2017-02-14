package org.usfirst.frc.team972.robot;

import java.io.*;

public class Logger {

	static String directory = "";

	public static void init() {
		setNewDirectory();
	}
	
	public static void setNewDirectory(String directoryName) {
		new File(Constants.LOGGER_LOCATION).mkdir();
		new File(Constants.LOGGER_LOCATION + "/" + directoryName).mkdir();
		directory = directoryName;
	}

	public static void setNewDirectory() {
		setNewDirectory(Robot.getTime());
	}

	public static void log(String fileName, String message, boolean error) {
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(
					new FileWriter(Constants.LOGGER_LOCATION + "/" + directory + "/" + fileName + ".txt", true)));
			out.println(message);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (error) {
			logError(fileName + ": " + message);
		}
	}

	public static void log(String fileName, String message) {
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(
					new FileWriter(Constants.LOGGER_LOCATION + "/" + directory + "/" + fileName + ".txt", true)));
			out.println(message);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void logError(String message) {
		try {
			PrintWriter errorOut = new PrintWriter(
					new BufferedWriter(new FileWriter(Constants.LOGGER_LOCATION + "/ErrorLog.txt", true)));
			errorOut.println(message);
			errorOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String[] readLog(String fileName, String time) {
		try {
			String[] tokens;
			String line;
			@SuppressWarnings("resource")
			BufferedReader fileReader = 
	                new BufferedReader(new FileReader(Constants.LOGGER_LOCATION + "/" + directory + "/" + fileName + ".txt"));
			while((line = fileReader.readLine()) != null) {
				tokens = line.split("x=");
				if(tokens[0].equals("t=" + time)) {
					tokens = tokens[1].split("y=");
					return tokens;
				}
			}
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
