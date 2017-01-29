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
	
	static boolean inverseDriveMode = false;
	static boolean inverseDriveButtonPressed = false;
	static boolean inverseDriveButtonLastPressed = false;
	
	static boolean brakeMode = false;
	static boolean brakeModeButtonPressed = false;
	static boolean brakeModeButtonLastPressed = false;
	
	static boolean winchButtonPressedLastTime = false;
	static boolean intakeButtonPressedLastTime = false;
	
	public static void init(Robot r) {
		Robot.updateSmartDashboard();
	}
	
	public static void periodic(Robot r) {
		leftDriveSpeed = Robot.leftJoystick.getY();
		rightDriveSpeed = Robot.rightJoystick.getY();
		
		inverseDriveButtonPressed = Robot.leftJoystick.getRawButton(Constants.INVERSE_DRIVE_TOGGLE_BUTTON);
		if(inverseDriveButtonPressed && !inverseDriveButtonLastPressed) {
			inverseDriveMode = !inverseDriveMode;
		}
		inverseDriveButtonLastPressed = inverseDriveButtonPressed;
		
		if (Robot.leftJoystick.getRawButton(Constants.STOP_DRIVE_BUTTON)) {
			Drive.stopDrive();
		} else {
			if (Robot.leftJoystick.getRawButton(Constants.SQUARED_DRIVE_BUTTON)) {
				// Taking absolute value of one preserves the positive or negative result (normally squaring makes it positive)
				leftDriveSpeed = Math.abs(leftDriveSpeed) * leftDriveSpeed;
				rightDriveSpeed = Math.abs(rightDriveSpeed) * rightDriveSpeed;
			}
			
			if(inverseDriveMode) {
				Drive.inverseDrive(leftDriveSpeed, rightDriveSpeed);
			} else {
				Drive.tankDrive(leftDriveSpeed, rightDriveSpeed);
			}
		}
		
		brakeModeButtonPressed = Robot.rightJoystick.getRawButton(Constants.BRAKE_MODE_TOGGLE_BUTTON);
		if(brakeModeButtonPressed && !brakeModeButtonLastPressed) {
			brakeMode = !brakeMode;
		}
		brakeModeButtonLastPressed = brakeModeButtonPressed;
		
		Drive.toggleBrakeMode(brakeMode);
		
		// Winch
		boolean winchMotorButton = Robot.operatorJoystick.getRawButton(Constants.WINCH_MOTOR_BUTTON);
		if (winchMotorButton && !winchButtonPressedLastTime) {
			Winch.start();
		} else {
			Winch.stop();
		}
		winchButtonPressedLastTime = winchMotorButton;
		
		// Intake
		boolean intakeMotorButton = Robot.operatorJoystick.getRawButton(Constants.INTAKE_MOTOR_BUTTON);
		if (intakeMotorButton && !intakeButtonPressedLastTime) {
			Intake.start();
		} else {
			Intake.stop();
		}
		intakeButtonPressedLastTime = intakeMotorButton;
		
		Robot.updateSmartDashboard();
	}
	
	public static void updateSmartDashboardTeleop() {
		SmartDashboard.putNumber("Left Drive Speed", leftDriveSpeed);
		SmartDashboard.putNumber("Right Drive Speed", rightDriveSpeed);
		SmartDashboard.putNumber("Left Encoder", Robot.leftDriveEncoder.get());
		SmartDashboard.putNumber("Right Encoder", Robot.rightDriveEncoder.get());
	}
	
}
