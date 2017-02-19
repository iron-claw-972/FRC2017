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
	static TeleopState teleopState = TeleopState.INTAKE;

	/**
	 * Initialization code for teleop mode. This method for initialization code
	 * will be called each time the robot enters teleop mode.
	 * 
	 * @see Robot.teleopInit()
	 */
	public static void init(Robot r) {
		r.init();
		MotionProfiling.init(0.0, 0.0);
		updateSmartDashboard();
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
		teleopStateMachine();
		prevTime = currTime;
		SmartDashboard.putNumber("Loop Time", loopTime);
	}

	public static void teleopStateMachine() {
		switch (teleopState) {
			case INTAKE:
				intakeStateCurrentLimit();
				break;
			case SHOOT:
				shootStateCurrentLimit();
				break;
			case CLIMB:
				climbStateCurrentLimit();
				break;
			case MANUAL_OVERRIDE:
				manualOverrideCurrentLimit();
				break;
			default:
				Logger.logError("Teleop State switch statement reached default!");
				System.out.println("Teleop State switch statement reached default!");
				break;
		}
		
		// Manage calls will switch states
		Intake.manage();
		Shooter.shoot();
		Shooter.align();
		Winch.manage();
		Drive.teleopDrive();
	}

	public static void intakeStateCurrentLimit() {
		driveCurrentLimit(Constants.INTAKE_DRIVE_CURRENT_LIMIT);
		shooterCurrentLimit(Constants.INTAKE_SHOOTER_CURRENT_LIMIT);
		loaderCurrentLimit(Constants.INTAKE_LOADER_CURRENT_LIMIT);
		intakeCurrentLimit(Constants.INTAKE_INTAKE_CURRENT_LIMIT);
		winchCurrentLimit(Constants.INTAKE_WINCH_CURRENT_LIMIT);
		azimuthCurrentLimit(Constants.INTAKE_AZIMUTH_CURRENT_LIMIT);
		enableCurrentLimit(true);
	}

	public static void shootStateCurrentLimit() {
		driveCurrentLimit(Constants.SHOOT_DRIVE_CURRENT_LIMIT);
		shooterCurrentLimit(Constants.SHOOT_SHOOTER_CURRENT_LIMIT);
		loaderCurrentLimit(Constants.SHOOT_LOADER_CURRENT_LIMIT);
		intakeCurrentLimit(Constants.SHOOT_INTAKE_CURRENT_LIMIT);
		winchCurrentLimit(Constants.SHOOT_WINCH_CURRENT_LIMIT);
		azimuthCurrentLimit(Constants.SHOOT_AZIMUTH_CURRENT_LIMIT);
		enableCurrentLimit(true);
	}

	public static void climbStateCurrentLimit() {
		driveCurrentLimit(Constants.CLIMB_DRIVE_CURRENT_LIMIT);
		shooterCurrentLimit(Constants.CLIMB_SHOOTER_CURRENT_LIMIT);
		loaderCurrentLimit(Constants.CLIMB_LOADER_CURRENT_LIMIT);
		intakeCurrentLimit(Constants.CLIMB_INTAKE_CURRENT_LIMIT);
		winchCurrentLimit(Constants.CLIMB_WINCH_CURRENT_LIMIT);
		azimuthCurrentLimit(Constants.CLIMB_AZIMUTH_CURRENT_LIMIT);
		enableCurrentLimit(true);
	}
	
	public static void manualOverrideCurrentLimit() {
		enableCurrentLimit(false);
	}
	
	public static void driveCurrentLimit(int amps) {
		Robot.frontLeftDriveMotor.setCurrentLimit(amps);
		Robot.frontRightDriveMotor.setCurrentLimit(amps);
		Robot.backLeftDriveMotor.setCurrentLimit(amps);
		Robot.backRightDriveMotor.setCurrentLimit(amps);
	}
	
	public static void shooterCurrentLimit(int amps) {
		Robot.leftShooterMotorA.setCurrentLimit(amps);
		Robot.leftShooterMotorB.setCurrentLimit(amps);
		Robot.rightShooterMotorA.setCurrentLimit(amps);
		Robot.rightShooterMotorB.setCurrentLimit(amps);
	}
	
	public static void loaderCurrentLimit(int amps) {
		Robot.leftLoaderMotor.setCurrentLimit(amps);
		Robot.rightLoaderMotor.setCurrentLimit(amps);
	}
	
	public static void intakeCurrentLimit(int amps) {
		Robot.intakeMotor.setCurrentLimit(amps);
	}
	
	public static void winchCurrentLimit(int amps) {
		Robot.winchMotor.setCurrentLimit(amps);
	}
	
	public static void azimuthCurrentLimit(int amps) {
		Robot.leftAzimuthMotor.setCurrentLimit(amps);
		Robot.rightAzimuthMotor.setCurrentLimit(amps);
	}
	
	public static void enableCurrentLimit(boolean flag) {
		Robot.frontLeftDriveMotor.EnableCurrentLimit(flag);
		Robot.frontRightDriveMotor.EnableCurrentLimit(flag);
		Robot.backLeftDriveMotor.EnableCurrentLimit(flag);
		Robot.backRightDriveMotor.EnableCurrentLimit(flag);
		Robot.leftShooterMotorA.EnableCurrentLimit(flag);
		Robot.leftShooterMotorB.EnableCurrentLimit(flag);
		Robot.rightShooterMotorA.EnableCurrentLimit(flag);
		Robot.rightShooterMotorB.EnableCurrentLimit(flag);
		Robot.leftLoaderMotor.EnableCurrentLimit(flag);
		Robot.rightLoaderMotor.EnableCurrentLimit(flag);
		Robot.intakeMotor.EnableCurrentLimit(flag);
		Robot.winchMotor.EnableCurrentLimit(flag);
		Robot.leftAzimuthMotor.EnableCurrentLimit(flag);
		Robot.rightAzimuthMotor.EnableCurrentLimit(flag);
	}

	/**
	 * Updates SmartDashboard values for Teleop by calling other update
	 * functions.
	 */
	public static void updateSmartDashboard() {
		Drive.updateSmartDashboard();
		// Shooter.updateSmartDashboard();
		// Winch.updateSmartDashboard();
		// Intake.updateSmartDashboard();
		MotionProfiling.updateSmartDashboard();
		Time.updateSmartDashboard();
		SmartDashboard.putString("Teleop State", teleopState.toString());
	}

}
