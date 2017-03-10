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
	private static boolean gearAlign = false;

	/**
	 * Initialization code for teleop mode. This method for initialization code
	 * will be called each time the robot enters teleop mode.
	 * 
	 * @see Robot.teleopInit()
	 */
	public static void init(Robot r) {
		r.init();
		//MotionProfiling.init(0.0, 0.0); //In use only for testing!
		Intake.hopperIntake();
		updateSmartDashboard();
		
		Robot.isBlueAlliance = (boolean) Autonomous.allianceChooser.getSelected();
		
		Robot.frontLeftDriveMotor.EnableCurrentLimit(true);
		Robot.frontRightDriveMotor.EnableCurrentLimit(true);
		Robot.backLeftDriveMotor.EnableCurrentLimit(true);
		Robot.backRightDriveMotor.EnableCurrentLimit(true);
	}

	/**
	 * Periodic code for teleop mode. This method will be called each time a new
	 * packet is received from the driver station and the robot is in teleop
	 * mode.
	 * <p>
	 * Packets are received approximately every 20ms. Fixed loop timing is not
	 * guaranteed due to network timing variability and the function may not be
	 * called at all if the Driver Station is disconnected. For most use cases
	 * the variable timing will not be an issue.
	 * 
	 * @see Robot.teleopPeriodic()
	 */
	public static void periodic(Robot r) {
		Drive.teleopDrive();

		double currTime = Time.get();
		double loopTime = currTime - prevTime;
		Drive.updateModel(loopTime);
		updateSmartDashboard();
		prevTime = currTime;
		SmartDashboard.putNumber("Loop Time", loopTime);
		
		double currentLimit = Constants.CURRENT_LIMIT;
		for(int i = 5; i < 15; i++) {
			currentLimit -= Robot.pdp.getCurrent(i);
		}
		int currentLimitPerDriveMotor = (int) (currentLimit / 4); // rounds down because Java
		// 4 is the number of drive motors
		
		Robot.frontLeftDriveMotor.setCurrentLimit(currentLimitPerDriveMotor);
		Robot.backLeftDriveMotor.setCurrentLimit(currentLimitPerDriveMotor);
		Robot.frontRightDriveMotor.setCurrentLimit(currentLimitPerDriveMotor);
		Robot.backRightDriveMotor.setCurrentLimit(currentLimitPerDriveMotor);
		Robot.frontLeftDriveMotor.EnableCurrentLimit(true);
		Robot.frontRightDriveMotor.EnableCurrentLimit(true);
		Robot.backLeftDriveMotor.EnableCurrentLimit(true);
		Robot.backRightDriveMotor.EnableCurrentLimit(true);
		
		Intake.manage();
		Shooter.shoot();
		Shooter.align();
		Winch.manage();
		
		if (Robot.leftJoystick(Constants.GEAR_ALIGN_BUTTON)) {
			gearAlign = true;
		}
		
		if (gearAlign) {
			Drive.gearTeleopAlign(loopTime);
		} else {
			Drive.teleopDrive();
		}
	}
  
	/**
	 * Updates SmartDashboard values for Teleop by calling other update
	 * functions.
	 */
	public static void updateSmartDashboard() {
		Drive.updateSmartDashboard();
		Shooter.updateSmartDashboard();
		Winch.updateSmartDashboard();
		Intake.updateSmartDashboard();
		MotionProfiling.updateSmartDashboard();
		Time.updateSmartDashboard();
	}

}
