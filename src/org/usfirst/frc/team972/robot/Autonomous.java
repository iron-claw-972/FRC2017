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
	static AutonomousRoutine selectedAutoRoutine;

	/**
	 * Create Autonomous chooser for SmartDashboard.
	 */
	public static void createChooser() {
		autoChooser.addDefault("Do Nothing", AutonomousRoutine.DO_NOTHING);
		autoChooser.addObject("Cross Baseline", AutonomousRoutine.CROSS_BASELINE);
		autoChooser.addObject("Test 0 - stay in place", AutonomousRoutine.TEST_0);
		autoChooser.addObject("Test 1 - rotate 90 degrees", AutonomousRoutine.TEST_1);
		autoChooser.addObject("Test 2 - rotate 180 degrees", AutonomousRoutine.TEST_2);
		autoChooser.addObject("Test 3 - move forward 3 meters", AutonomousRoutine.TEST_3);
		autoChooser.addObject("Test 4 - move forward 3 meters and turn 90 degrees", AutonomousRoutine.TEST_4);
		autoChooser.addObject("Test 5 - move to the right 3 meters and end up turned 90 degrees", AutonomousRoutine.TEST_5);
		autoChooser.addObject("Test 6 - move backwards to x=-1, y=-2", AutonomousRoutine.TEST_6);
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
		selectedAutoRoutine = (AutonomousRoutine) autoChooser.getSelected();
		switch (selectedAutoRoutine) {
			case DO_NOTHING:
				MotionProfiling.init(0.0, 0.0); //TODO: set all of these for actual competition
				break;
			case CROSS_BASELINE:
				MotionProfiling.init(0.0, 0.0);
				break;
			case TEST_0:
				MotionProfiling.init(0.0, 0.0);
				break;
			case TEST_1:
				MotionProfiling.init(0.0, 0.0);
				break;
			case TEST_2:
				MotionProfiling.init(0.0, 0.0);
				break;
			case TEST_3:
				MotionProfiling.init(0.0, 0.0);
				break;
			case TEST_4:
				MotionProfiling.init(0.0, 0.0);
				break;
			case TEST_5:
				MotionProfiling.init(0.0, 0.0);
				break;
			case TEST_6:
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
			case TEST_0:
				Drive.autonDrive(0.0, 0.0, 0.0, loopTime);
				break;
			case TEST_1:
				Drive.autonDrive(0.0, 0.0, 90, loopTime);
				break;
			case TEST_2:
				Drive.autonDrive(0.0, 0.0, 180, loopTime);
				break;
			case TEST_3:
				Drive.autonDrive(0.0, 3.0, 0.0, loopTime);
				break;
			case TEST_4:
				Drive.autonDrive(0.0, 3.0, 90.0, loopTime);
				break;
			case TEST_5:
				Drive.autonDrive(3.0, 0.0, 90.0, loopTime);
				break;
			case TEST_6:
				Drive.autonDrive(-1.0, -2.0, 0.0, loopTime);
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
//		Drive.updateSmartDashboard();
//		Shooter.updateSmartDashboard();
//		Winch.updateSmartDashboard();
//		Intake.updateSmartDashboard();
//		MotionProfiling.updateSmartDashboard();
//		Time.updateSmartDashboard();
	}
}
