package org.usfirst.frc.team9972.robot;

import edu.wpi.first.wpilibj.*;

public class Drive {
	
	public static void tankDrive(double driveSpeedLeft, double driveSpeedRight) {
		Robot.robotDrive.tankDrive(driveSpeedLeft, driveSpeedRight);
	}
	
	public static void inverseDrive(double driveSpeedLeft, double driveSpeedRight) {
		Robot.robotDrive.tankDrive(-driveSpeedRight, -driveSpeedLeft);
	}
}
