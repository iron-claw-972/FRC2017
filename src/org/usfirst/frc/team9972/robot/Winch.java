package org.usfirst.frc.team9972.robot;

public class Winch {
	
	public static void start() {
		Robot.winchMotor.set(Constants.WINCH_MOTOR_SPEED);
	}
	
	public static void stop() {
		Robot.winchMotor.set(0);
	}
}
