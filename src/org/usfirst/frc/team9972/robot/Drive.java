package org.usfirst.frc.team9972.robot;

import edu.wpi.first.wpilibj.*;

public class Drive {
	
	//static boolean lastPressed = false;
	
	public static void tankDrive(double driveSpeedLeft, double driveSpeedRight) {
		Robot.robotDrive.tankDrive(driveSpeedLeft, driveSpeedRight);
	}
	
	public static void invertDrive(double driveSpeedLeft, double driveSpeedRight) {
		if(&& !lastPressed) {
			Robot.robotDrive.tankDrive(-driveSpeedRight, -driveSpeedLeft);
		}
		//lastPressed = Robot.leftJoystick.getRawButton(Constants.INVERT_DRIVE_BUTTON);
	}
	
}
