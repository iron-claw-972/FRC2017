package org.usfirst.frc.team972.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Intake {

	static boolean intakeMotorOn = false;
	static boolean intakeButtonPressedLastTime = false;
	static boolean hopperRampButtonPressedLastTime = false;
	static boolean hopperPistonBoolean = true; // Starts at true because you want it to go forward the first time
	
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
		Robot.feederPiston.set(false);
		intakeMotorOn = true;
	}

	/**
	 * Stops Intake.
	 */
	public static void stop() {
		Robot.intakeMotor.set(0);
		intakeMotorOn = false;
	}
	
	public static void hopperIntake() {
		boolean hopperRampButtonPressed = Robot.operatorJoystick.getRawButton(Constants.HOPPER_INTAKE_RAMP_TOGGLE_BUTTON); 
		if (hopperRampButtonPressed && !hopperRampButtonPressedLastTime) {
			Robot.hopperIntakePiston.set(hopperPistonBoolean);
			hopperPistonBoolean = !hopperPistonBoolean;
		}
		hopperRampButtonPressedLastTime = hopperRampButtonPressed;
	}

	/**
	 * Updates SmartDashboard values for Intake.
	 */
	public static void updateSmartDashboard() {
		SmartDashboard.putBoolean("Intake Motor", intakeMotorOn);
	}
}
