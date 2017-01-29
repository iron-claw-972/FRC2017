package org.usfirst.frc.team972.robot;

public class Intake {
	
	public static void init() {
		Robot.intakeMotor.enableBrakeMode(true);
	}
	
	public static void start() {
		Robot.intakeMotor.set(Constants.INTAKE_MOTOR_SPEED);
	}
	
	public static void stop() {
		Robot.intakeMotor.set(0);
	}
}
