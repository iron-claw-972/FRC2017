package org.usfirst.frc.team972.robot;

import java.io.*;

public class Logger {
	
	static String location = "/FRC2017Logs/";
	static String name = "DefaultLogName";
	
	public static void setFileName(String name) {
		Logger.name = name;
	}
	
	public static void setFileName() {
		setFileName(Robot.getTime());
	}
	
	public static void log(String message, boolean error) {
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter
					(location + name + ".txt", true)));
			out.println(message);
			out.close();
			
			PrintWriter errorOut = new PrintWriter(new BufferedWriter(new FileWriter
					(location + "/ErrorLog.txt", true)));
			errorOut.println(message);
			errorOut.close();
		} catch (Exception e) {
			System.out.println("Failed print!");
			e.printStackTrace();
		}
	}
}
