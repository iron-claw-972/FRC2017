package org.usfirst.frc.team972.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.*;

public class Autonomous {
	/*
	 * init() and periodic() are functions that will be called in Robot.java.
	 * All autonomous code should be placed in those two functions in order to
	 * make Robot.java easier to navigate (by shortening its length).
	 * 
	 * If you need to reference a non-static variable or function from
	 * Robot.java, such as isEnabled() or isAutonomous(), use the r Robot
	 * object. Example: if(r.isEnabled())
	 */

	private static double prevTime = 0.0;
	
	static SendableChooser autoChooser = new SendableChooser();

	/**
	 * Create Autonomous chooser for SmartDashboard.
	 */
	public static void createChooser() {
		// TODO: Update to reflect Motion Profiling
		autoChooser.addDefault("Do Nothing", AutonomousRoutine.DO_NOTHING);
		autoChooser.addObject("Cross Baseline", AutonomousRoutine.CROSS_BASELINE);
		SmartDashboard.putData("Autonomous Mode Chooser", autoChooser);
	}

	/**
	 * Initialization code for autonomous mode. This method for initialization code will be called
	 * each time the robot enters autonomous mode.
	 * 
	 * @see Robot.autonomousInit()
	 */
	public static void init(Robot r) {
		r.init();
//		MotionProfiling.init(x_init, y_init); // TODO: figure out what these inits
		updateSmartDashboard();
	}

	/**
	 * Periodic code for autonomous mode. This method will be called each time a new packet is
	 * received from the driver station and the robot is in autonomous mode.
	 * <p>
	 * Packets are received approximately every 20ms. Fixed loop timing is not guaranteed due to
	 * network timing variability and the function may not be called at all if the Driver Station is
	 * disconnected. For most use cases the variable timing will not be an issue.
	 * 
	 * @see Robot.autonomousPeriodic()
	 */
	public static void periodic(Robot r) {
		double currTime = Time.get();
		double loopTime = currTime - prevTime;
		Drive.updateModel(loopTime);
		updateSmartDashboard();
		prevTime = currTime;
	}

	/**
	 * Updates SmartDashboard values for Autonomous by calling other update functions.
	 */
	public static void updateSmartDashboard() {
		SmartDashboard.putString("Autonomous Routine", autoChooser.getSelected().toString());
		Drive.updateSmartDashboard();
		Shooter.updateSmartDashboard();
		ShooterAlignment.updateSmartDashboard();
		Winch.updateSmartDashboard();
		Intake.updateSmartDashboard();
		MotionProfiling.updateSmartDashboard();
		Time.updateSmartDashboard();
	}
}
