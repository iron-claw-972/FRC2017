package org.usfirst.frc.team9972.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Teleop {
	/*
	 * init() and periodic() are functions that will be called in Robot.java.
	 * All teleop code should be placed in those two functions in order to
	 * make Robot.java easier to navigate (by shortening its length).
	 * 
	 * If you need to reference a non-static variable or function from Robot.java,
	 * such as isEnabled() or isAutonomous(), use the r Robot object.
	 * Example: if(r.isEnabled())
	 */
	
	static double leftDriveSpeed = 0;
	static double rightDriveSpeed = 0;
	
	public static void init(Robot r) {
		Robot.updateSmartDashboard();
	}
	
	public static void periodic(Robot r) {
		leftDriveSpeed = Robot.leftJoystick.getY();
		rightDriveSpeed = Robot.rightJoystick.getY();
		Drive.tankDrive(leftDriveSpeed, rightDriveSpeed);
		Robot.updateSmartDashboard();
	}
	
	public static void updateSmartDashboardTeleop() {
		SmartDashboard.putNumber("Left Drive Speed", leftDriveSpeed);
		SmartDashboard.putNumber("Right Drive Speed", rightDriveSpeed);
		SmartDashboard.putNumber("Left Encoder", Robot.leftDriveEncoder.get());
		SmartDashboard.putNumber("Right Encoder", Robot.rightDriveEncoder.get());
	}
	
}
