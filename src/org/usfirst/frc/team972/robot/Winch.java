package org.usfirst.frc.team972.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Winch {

	static boolean winchMotorOn = false;
	static boolean winchButtonPressedLastTime = false;
	
	/**
	 * Winch initiation sequence.
	 */
	public static void init() {
		Robot.winchMotor.enableBrakeMode(true);
	}
	
	/**
	 * Manages Winch using joystick toggle input.
	 */
	public static void manage() {
		boolean winchMotorButton = Robot.operatorJoystick.getRawButton(Constants.WINCH_MOTOR_TOGGLE_BUTTON);
		if (winchMotorButton) {
			Winch.start();
		} else {
			Winch.stop();
		}
//		winchButtonPressedLastTime = winchMotorButton;
	}

	/**
	 * Starts Winch.
	 */
	public static void start() {
		Robot.winchMotor.set(Constants.WINCH_MOTOR_SPEED);
		winchMotorOn = true;
	}

	/**
	 * Stops Winch.
	 */
	public static void stop() {
		Robot.winchMotor.set(0);
		winchMotorOn = false;
	}

	/**
	 * Updates SmartDashboard values for Winch.
	 */
	public static void updateSmartDashboard() {
		SmartDashboard.putBoolean("Winch Motor", winchMotorOn);
	}
}
