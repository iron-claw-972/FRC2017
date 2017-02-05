package org.usfirst.frc.team972.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Teleop {
	/*
	 * init() and periodic() are functions that will be called in Robot.java.
	 * All teleop code should be placed in those two functions in order to make
	 * Robot.java easier to navigate (by shortening its length).
	 * 
	 * If you need to reference a non-static variable or function from
	 * Robot.java, such as isEnabled() or isAutonomous(), use the r Robot
	 * object. Example: if(r.isEnabled())
	 */

	static boolean winchButtonPressedLastTime = false;
	static boolean intakeButtonPressedLastTime = false;

	public static void init(Robot r) {
		r.init();
		updateSmartDashboard();
	}

	public static void periodic(Robot r) {
		// Winch
		boolean winchMotorButton = Robot.operatorJoystick.getRawButton(Constants.WINCH_MOTOR_TOGGLE_BUTTON);
		if (winchMotorButton && !winchButtonPressedLastTime) {
			Winch.start();
		} else {
			Winch.stop();
		}
		winchButtonPressedLastTime = winchMotorButton;

		// Intake
		boolean intakeMotorButton = Robot.operatorJoystick.getRawButton(Constants.INTAKE_MOTOR_TOGGLE_BUTTON);
		if (intakeMotorButton && !intakeButtonPressedLastTime) {
			Intake.start();
		} else {
			Intake.stop();
		}
		intakeButtonPressedLastTime = intakeMotorButton;

		Drive.run();
		
		Drive.updateModel();
		updateSmartDashboard();
	}

	public static void updateSmartDashboard() {
		Drive.updateSmartDashboard();
		Shooter.updateSmartDashboard();
		Winch.updateSmartDashboard();
		Intake.updateSmartDashboard();
		MotionProfiling.updateSmartDashboard();
	}

}
