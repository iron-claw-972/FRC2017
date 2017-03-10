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
	private static double gear_x;
	private static double gear_y;
	private static double gear_theta;
	
	private static boolean auton7step1done = false;
	private static boolean auton7step2done = false;
	
	static SendableChooser autoChooser = new SendableChooser();
	static SendableChooser allianceChooser = new SendableChooser();
	static AutonomousRoutine selectedAutoRoutine;

	/**
	 * Create Autonomous chooser for SmartDashboard.
	 */
	public static void createChooser() {
		autoChooser.addDefault("Do Nothing", AutonomousRoutine.DO_NOTHING);
		autoChooser.addObject("Cross Baseline", AutonomousRoutine.CROSS_BASELINE);
		autoChooser.addObject("Middle Gear", AutonomousRoutine.MIDDLE_GEAR);
		autoChooser.addObject("Test 7 - Move back and forward continuously", AutonomousRoutine.TEST_7);
		autoChooser.addObject("Test 0 - Hopefully move in a straight line", AutonomousRoutine.TEST_0);
		SmartDashboard.putData("Autonomous Mode Chooser", autoChooser);

		allianceChooser.addDefault("RED ALLIANCE", false);
		allianceChooser.addObject("BLUE ALLIANCE", true);
		SmartDashboard.putData("Alliance Chooser", allianceChooser);
	}
	
	/**
	 * Initialization code for autonomous mode. This method for initialization code will be called
	 * each time the robot enters autonomous mode.
	 * 
	 * @see Robot.autonomousInit()
	 */
	public static void init(Robot r) {
		r.init();
		
		Robot.isBlueAlliance = (boolean) allianceChooser.getSelected();
		
		//selectedAutoRoutine = (AutonomousRoutine) autoChooser.getSelected(); //TODO fix auton chooser
		selectedAutoRoutine = AutonomousRoutine.TEST_7;
		switch (selectedAutoRoutine) {
			case DO_NOTHING:
				MotionProfiling.init(Constants.DO_NOTHING_AUTO_STARTX, Constants.DO_NOTHING_AUTO_STARTY);
				break;
			case CROSS_BASELINE:
				MotionProfiling.init(Constants.CROSS_BASELINE_AUTO_STARTX, Constants.DO_NOTHING_AUTO_STARTY);
				break;
			case MIDDLE_GEAR:
				MotionProfiling.init(Constants.MIDDLE_GEAR_AUTO_STARTX, Constants.MIDDLE_GEAR_AUTO_STARTY);
				Vision.startGearVision();
			case TEST_7:
				MotionProfiling.init(0.0, 0.0);
				break;
			case TEST_0:
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
				Drive.stopDrive();
				break;
			case CROSS_BASELINE:
				Drive.autonDrive(Constants.CROSS_BASELINE_AUTO_X, Constants.CROSS_BASELINE_AUTO_Y, Constants.CROSS_BASELINE_AUTO_THETA, loopTime);
				break;
			case MIDDLE_GEAR:
				boolean done = false;
				if (visionData) {
					if (Vision.newData()) {
						double distance = Vision.getDistance();
						double angle = Vision.getAngle() - 90; //change from 0 to 180 to -90 to 90
						double data_time = Vision.getTime();
						double[] framePosition = Logger.readLog("Motion_Profiling_Data", data_time);
						if (framePosition.length == 3) {
							gear_x = Math.sin(angle) * distance + Math.sin(framePosition[2]) * (Constants.ROBOT_LENGTH / 2) + framePosition[0];
							gear_y = Math.cos(angle) * distance + Math.cos(framePosition[2]) * (Constants.ROBOT_LENGTH / 2) + framePosition[1];
							gear_theta = framePosition[2] + angle;
							if (framePosition[2] > 90 && angle > 90) {
								gear_theta = gear_theta - 360; 
							} else if (framePosition[2] < -90 && angle < -90) {
								gear_theta = gear_theta + 360;
							}
						} else {
							Logger.logError("Failed to determine the position of the robot from the logs.");
						}
					}
					done = Drive.autonDrive(gear_x, gear_y - Constants.LENGTH_GEAR_PEG - (Constants.ROBOT_LENGTH / 2), gear_theta, loopTime);
				} else {
					visionData = Vision.newData();
					done = Drive.autonDrive(Constants.MIDDLE_GEAR_AUTO_X, Constants.MIDDLE_GEAR_AUTO_Y - (Constants.ROBOT_LENGTH / 2), Constants.MIDDLE_GEAR_AUTO_THETA, loopTime);
				}
				if (done) {
					MotionProfiling.reset(Constants.MIDDLE_GEAR_AUTO_X, Constants.MIDDLE_GEAR_AUTO_Y - (Constants.ROBOT_LENGTH / 2), Constants.MIDDLE_GEAR_AUTO_THETA);
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
			case TEST_0:
				Drive.autonDrive(0.0, 2.5, 0, loopTime);
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
