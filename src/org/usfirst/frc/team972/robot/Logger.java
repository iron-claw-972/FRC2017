package org.usfirst.frc.team972.robot;

import java.io.*;
import java.util.*;

public class Logger {

	static String directory = "";
	static Scanner scanner;

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
	
	public static double[] getLocationAtTime(String fileName, int time) {
		String x_String = "", y_String = "";
		double x, y;
		try {
			scanner = new Scanner(new File(Constants.LOGGER_LOCATION + "/" + directory + "/" + fileName + ".txt"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		while (scanner.hasNext() && x_String.equals("") && y_String.equals("")) {
			String next = scanner.nextLine();
			String t = next.substring(next.indexOf("t=") + 2, next.indexOf("x="));
			if (t.equals(String.valueOf(time))) {
				x_String = next.substring(next.indexOf("x=") + 2, next.indexOf("y="));
				y_String = next.substring(next.indexOf("y=") + 2);
				System.out.println(x_String + "," + y_String);
				// TODO: Comment out print after testing
			} else {
			}
		}
		try {
			x = Double.parseDouble(x_String);
		} catch (Exception e) {
			x = -1;
			System.out.println("X is not a double");
		}
		try {
			y = Double.parseDouble(y_String);
		} catch (Exception e) {
			y = -1;
			System.out.println("Y is not a double");
		}
		double[] location = {x,y};
		return location;
	}
}
