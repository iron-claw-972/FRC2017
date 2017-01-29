package org.usfirst.frc.team9972.robot;

public class Intake {
	public static void start() {
		Robot.intakeMotor.set(Constants.INTAKE_MOTOR_SPEED);
	}
	
	public static void stop() {
		Robot.intakeMotor.set(0);
	}
}
