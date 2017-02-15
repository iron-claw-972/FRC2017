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

	private static double prevTime = 0.0;

	/**
	 * Initialization code for teleop mode. This method for initialization code will be called each
	 * time the robot enters teleop mode.
	 * 
	 * @see Robot.teleopInit()
	 */
	public static void init(Robot r) {
		r.init();
		MotionProfiling.init(0.0, 0.0);
		updateSmartDashboard();
	}

	/**
	 * Periodic code for teleop mode. This method will be called each time a new packet is received
	 * from the driver station and the robot is in teleop mode.
	 * <p>
	 * Packets are received approximately every 20ms. Fixed loop timing is not guaranteed due to
	 * network timing variability and the function may not be called at all if the Driver Station is
	 * disconnected. For most use cases the variable timing will not be an issue.
	 * 
	 * @see Robot.teleopPeriodic()
	 */
	public static void periodic(Robot r) {
		//Winch.manage();
		//Intake.manage();
		Drive.teleopDrive();
		//Shooter.align();
		//Shooter.shoot();
		
		double currTime = Time.get();
		double loopTime = currTime - prevTime;
		Drive.updateModel(loopTime);
		updateSmartDashboard();
		prevTime = currTime;
		SmartDashboard.putNumber("Loop Time", loopTime);
	}

	/**
	 * Updates SmartDashboard values for Teleop by calling other update functions.
	 */
	public static void updateSmartDashboard() {
		Drive.updateSmartDashboard();
//		Shooter.updateSmartDashboard();
//		Winch.updateSmartDashboard();
//		Intake.updateSmartDashboard();
		MotionProfiling.updateSmartDashboard();
		Time.updateSmartDashboard();
	}

}
