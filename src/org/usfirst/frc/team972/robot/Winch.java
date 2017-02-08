package org.usfirst.frc.team972.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Winch {

	static boolean winchMotorOn = false;
	static boolean winchButtonPressedLastTime = false;
	
	public static void init() {
		Robot.winchMotor.enableBrakeMode(true);
	}
	
	public static void manage() {
		boolean winchMotorButton = Robot.operatorJoystick.getRawButton(Constants.WINCH_MOTOR_TOGGLE_BUTTON);
		if (winchMotorButton && !winchButtonPressedLastTime) {
			Winch.start();
		} else {
			Winch.stop();
		}
		winchButtonPressedLastTime = winchMotorButton;
	}

	public static void start() {
		Robot.winchMotor.set(Constants.WINCH_MOTOR_SPEED);
		winchMotorOn = true;
	}

	public static void stop() {
		Robot.winchMotor.set(0);
		winchMotorOn = false;
	}
	
	public static void updateSmartDashboard() {
		SmartDashboard.putBoolean("Winch Motor", winchMotorOn);
	}
}
