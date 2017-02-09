package org.usfirst.frc.team972.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Time {
	private static long initTimeMillis = 0;
	
	public static void init() {
		initTimeMillis = System.currentTimeMillis();
	}
	
	/**
	 * 
	 * @return Time in seconds since init was called
	 */
	public static double get() {
		return ((System.currentTimeMillis() - initTimeMillis) / 1000);
	}
	
	public static void updateSmartDashboard() {
		SmartDashboard.putNumber("Current Time", get());
	}
}