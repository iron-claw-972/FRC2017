package org.usfirst.frc.team972.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Intake {

	static boolean intakeMotorOn = false;
	
	public static void init() {
		Robot.intakeMotor.enableBrakeMode(true);
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
