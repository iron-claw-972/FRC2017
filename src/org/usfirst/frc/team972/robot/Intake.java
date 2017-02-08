package org.usfirst.frc.team972.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Intake {

	static boolean intakeMotorOn = false;
	static boolean intakeButtonPressedLastTime = false;
	
	public static void init() {
		Robot.intakeMotor.enableBrakeMode(true);
	}

	public static void manage() {
		boolean intakeMotorButton = Robot.operatorJoystick.getRawButton(Constants.INTAKE_MOTOR_TOGGLE_BUTTON);
		if (intakeMotorButton && !intakeButtonPressedLastTime) {
			Intake.start();
		} else {
			Intake.stop();
		}
		intakeButtonPressedLastTime = intakeMotorButton;
	}
	
	public static void start() {
		Robot.intakeMotor.set(Constants.INTAKE_MOTOR_SPEED);
		intakeMotorOn = true;
	}

	public static void stop() {
		Robot.intakeMotor.set(0);
		intakeMotorOn = false;
	}
	
	public static void updateSmartDashboard() {
		SmartDashboard.putBoolean("Intake Motor", intakeMotorOn);
	}
}
