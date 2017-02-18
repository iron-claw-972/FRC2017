package org.usfirst.frc.team972.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Intake {

	static boolean intakeMotorOn = false;
	static boolean intakeButtonPressedLastTime = false;
	
	/**
	 * Intake initiation sequence.
	 */
	public static void init() {
		Robot.intakeMotor.enableBrakeMode(true);
	}
	/**

	 * Manages the Intake using joystick toggle input.
	 */
	public static void manage() {
		boolean intakeMotorButton = Robot.operatorJoystick.getRawButton(Constants.INTAKE_MOTOR_TOGGLE_BUTTON);
		if (intakeMotorButton && !intakeButtonPressedLastTime) {
			Intake.start();
		} else {
			Intake.stop();
		}
		intakeButtonPressedLastTime = intakeMotorButton;
	}
	
	/**
	 * Starts Intake.
	 */
	public static void start() {
		Robot.intakeMotor.set(Constants.INTAKE_MOTOR_SPEED);
		intakeMotorOn = true;
	}

	/**
	 * Stops Intake.
	 */
	public static void stop() {
		Robot.intakeMotor.set(0);
		intakeMotorOn = false;
	}

	/**
	 * Updates SmartDashboard values for Intake.
	 */
	public static void updateSmartDashboard() {
		SmartDashboard.putBoolean("Intake Motor", intakeMotorOn);
	}
}
