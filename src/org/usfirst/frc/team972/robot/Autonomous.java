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
	
	private static boolean visionData = false;
	
	private static boolean auton7step1done = false;
	private static boolean auton7step2done = false;
	
	static SendableChooser autoChooser = new SendableChooser();
	static AutonomousRoutine selectedAutoRoutine;

	/**
	 * Create Autonomous chooser for SmartDashboard.
	 */
	public static void createChooser() {
		autoChooser.addDefault("Do Nothing", AutonomousRoutine.DO_NOTHING);
		autoChooser.addObject("Cross Baseline", AutonomousRoutine.CROSS_BASELINE);
		autoChooser.addObject("Middle Gear", AutonomousRoutine.MIDDLE_GEAR);
		autoChooser.addObject("Test 7 - Move back and forward continuously", AutonomousRoutine.TEST_7);
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
		//selectedAutoRoutine = (AutonomousRoutine) autoChooser.getSelected(); //TODO fix auton chooser
		selectedAutoRoutine = AutonomousRoutine.TEST_7;
		switch (selectedAutoRoutine) {
			case DO_NOTHING:
				MotionProfiling.init(0.0, 0.0); //TODO: set all of these for actual competition
				break;
			case CROSS_BASELINE:
				MotionProfiling.init(0.0, 0.0);
				break;
			case MIDDLE_GEAR:
				MotionProfiling.init(Constants.MIDDLE_GEAR_START_X, Constants.MIDDLE_GEAR_START_Y);
				Jetson.startGearVision();
			case TEST_7:
				MotionProfiling.init(0.0, 0.0);
				break;
		}
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
		switch (selectedAutoRoutine) {
			case DO_NOTHING:
				Drive.stopDrive(); //TODO: set all of these for actual competition
				break;
			case CROSS_BASELINE:
				Drive.tankDrive(0.5, 0.5); //eventually will change to autonDrive
				break;
			case MIDDLE_GEAR:
				if (visionData) {
					double gear_x;
					double gear_y;
					if (Jetson.newData()) {
						double distance = Jetson.getDistance();
						double angle = Jetson.getAngle();
						double data_time = Jetson.getTime();
						double[] framePostition = Logger.readLog("Motion_Profiling_Data", data_time);
						gear_x = Math.sin(angle) * distance;
					}
				}
				break;
			case TEST_7:
				if (!auton7step1done) {
					auton7step1done = Drive.autonDrive(0.0, 2.5, 180, loopTime);
				} else if (!auton7step2done) {
					auton7step2done = Drive.autonDrive(0.0, 0.0, 0.0, loopTime);
				} else {
					auton7step1done = false;
					auton7step2done = false;
				}
				break;
		}
		Drive.updateModel(loopTime);
		updateSmartDashboard();
		prevTime = currTime;
	}

	/**
	 * Updates SmartDashboard values for Autonomous by calling other update functions.
	 */
	public static void updateSmartDashboard() {
//		SmartDashboard.putString("Autonomous Routine", selectedAutoRoutine.toString());
		Drive.updateSmartDashboard();
//		Shooter.updateSmartDashboard();
//		Winch.updateSmartDashboard();
//		Intake.updateSmartDashboard();
		MotionProfiling.updateSmartDashboard();
		Time.updateSmartDashboard();
	}
}
