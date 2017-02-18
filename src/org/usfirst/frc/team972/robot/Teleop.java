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
		// Winch.manage();
		// Intake.manage();
		Drive.teleopDrive();
		// Shooter.align();
		// Shooter.shoot();

		double currTime = Time.get();
		double loopTime = currTime - prevTime;
		Drive.updateModel(loopTime);
		updateSmartDashboard();
		updateCurrentLimit();
		prevTime = currTime;
		SmartDashboard.putNumber("Loop Time", loopTime);

		Shooter.align();
	}

	public static void updateCurrentLimit() {
		switch (teleopState) {
			case INTAKE:
				intakeStateCurrentLimit();
				break;
			case ALIGN:
				alignStateCurrentLimit();
				break;
			case SHOOT:
				shootStateCurrentLimit();
				break;
			case CLIMB:
				climbStateCurrentLimit();
				break;
			default:
				Logger.logError("TeleopState switch statement reached default!");
				System.out.println("TeleopState switch statement reached default!");
				break;
		}
	}

	public static void intakeStateCurrentLimit() {
		Robot.frontLeftDriveMotor.setCurrentLimit(43);
		Robot.frontRightDriveMotor.setCurrentLimit(43);
		Robot.backLeftDriveMotor.setCurrentLimit(43);
		Robot.backRightDriveMotor.setCurrentLimit(43);
		Robot.leftShooterMotorA.setCurrentLimit(0);
		Robot.leftShooterMotorB.setCurrentLimit(0);
		Robot.rightShooterMotorA.setCurrentLimit(0);
		Robot.rightShooterMotorB.setCurrentLimit(0);
		Robot.leftLoaderMotor.setCurrentLimit(0);
		Robot.rightLoaderMotor.setCurrentLimit(0);
		Robot.intakeMotor.setCurrentLimit(21);
		Robot.winchMotor.setCurrentLimit(0);
		Robot.leftAzimuthMotor.setCurrentLimit(0);
		Robot.rightAzimuthMotor.setCurrentLimit(0);

		Robot.frontLeftDriveMotor.EnableCurrentLimit(true);
		Robot.frontRightDriveMotor.EnableCurrentLimit(true);
		Robot.backLeftDriveMotor.EnableCurrentLimit(true);
		Robot.backRightDriveMotor.EnableCurrentLimit(true);
		Robot.leftShooterMotorA.EnableCurrentLimit(true);
		Robot.leftShooterMotorB.EnableCurrentLimit(true);
		Robot.rightShooterMotorA.EnableCurrentLimit(true);
		Robot.rightShooterMotorB.EnableCurrentLimit(true);
		Robot.leftLoaderMotor.EnableCurrentLimit(true);
		Robot.rightLoaderMotor.EnableCurrentLimit(true);
		Robot.intakeMotor.EnableCurrentLimit(true);
		Robot.winchMotor.EnableCurrentLimit(true);
		Robot.leftAzimuthMotor.EnableCurrentLimit(true);
		Robot.rightAzimuthMotor.EnableCurrentLimit(true);
	}

	public static void shootStateCurrentLimit() {
		Robot.frontLeftDriveMotor.setCurrentLimit(23);
		Robot.frontRightDriveMotor.setCurrentLimit(23);
		Robot.backLeftDriveMotor.setCurrentLimit(23);
		Robot.backRightDriveMotor.setCurrentLimit(23);
		Robot.leftShooterMotorA.setCurrentLimit(14);
		Robot.leftShooterMotorB.setCurrentLimit(14);
		Robot.rightShooterMotorA.setCurrentLimit(14);
		Robot.rightShooterMotorB.setCurrentLimit(14);
		Robot.leftLoaderMotor.setCurrentLimit(15);
		Robot.rightLoaderMotor.setCurrentLimit(15);
		Robot.intakeMotor.setCurrentLimit(0);
		Robot.winchMotor.setCurrentLimit(0);
		Robot.leftAzimuthMotor.setCurrentLimit(2);
		Robot.rightAzimuthMotor.setCurrentLimit(2);

		Robot.frontLeftDriveMotor.EnableCurrentLimit(true);
		Robot.frontRightDriveMotor.EnableCurrentLimit(true);
		Robot.backLeftDriveMotor.EnableCurrentLimit(true);
		Robot.backRightDriveMotor.EnableCurrentLimit(true);
		Robot.leftShooterMotorA.EnableCurrentLimit(true);
		Robot.leftShooterMotorB.EnableCurrentLimit(true);
		Robot.rightShooterMotorA.EnableCurrentLimit(true);
		Robot.rightShooterMotorB.EnableCurrentLimit(true);
		Robot.leftLoaderMotor.EnableCurrentLimit(true);
		Robot.rightLoaderMotor.EnableCurrentLimit(true);
		Robot.intakeMotor.EnableCurrentLimit(true);
		Robot.winchMotor.EnableCurrentLimit(true);
		Robot.leftAzimuthMotor.EnableCurrentLimit(true);
		Robot.rightAzimuthMotor.EnableCurrentLimit(true);
	}

	public static void alignStateCurrentLimit() {
		// TODO: CHANGE TO CORRECT LIMITS
		Robot.frontLeftDriveMotor.setCurrentLimit(43);
		Robot.frontRightDriveMotor.setCurrentLimit(43);
		Robot.backLeftDriveMotor.setCurrentLimit(43);
		Robot.backRightDriveMotor.setCurrentLimit(43);
		Robot.leftShooterMotorA.setCurrentLimit(0);
		Robot.leftShooterMotorB.setCurrentLimit(0);
		Robot.rightShooterMotorA.setCurrentLimit(0);
		Robot.rightShooterMotorB.setCurrentLimit(0);
		Robot.leftLoaderMotor.setCurrentLimit(0);
		Robot.rightLoaderMotor.setCurrentLimit(0);
		Robot.intakeMotor.setCurrentLimit(21);
		Robot.winchMotor.setCurrentLimit(0);
		Robot.leftAzimuthMotor.setCurrentLimit(0);
		Robot.rightAzimuthMotor.setCurrentLimit(0);

		Robot.frontLeftDriveMotor.EnableCurrentLimit(true);
		Robot.frontRightDriveMotor.EnableCurrentLimit(true);
		Robot.backLeftDriveMotor.EnableCurrentLimit(true);
		Robot.backRightDriveMotor.EnableCurrentLimit(true);
		Robot.leftShooterMotorA.EnableCurrentLimit(true);
		Robot.leftShooterMotorB.EnableCurrentLimit(true);
		Robot.rightShooterMotorA.EnableCurrentLimit(true);
		Robot.rightShooterMotorB.EnableCurrentLimit(true);
		Robot.leftLoaderMotor.EnableCurrentLimit(true);
		Robot.rightLoaderMotor.EnableCurrentLimit(true);
		Robot.intakeMotor.EnableCurrentLimit(true);
		Robot.winchMotor.EnableCurrentLimit(true);
		Robot.leftAzimuthMotor.EnableCurrentLimit(true);
		Robot.rightAzimuthMotor.EnableCurrentLimit(true);
	}

	public static void climbStateCurrentLimit() {
		// TODO: CHANGE TO CORRECT LIMITS
		Robot.frontLeftDriveMotor.setCurrentLimit(43);
		Robot.frontRightDriveMotor.setCurrentLimit(43);
		Robot.backLeftDriveMotor.setCurrentLimit(43);
		Robot.backRightDriveMotor.setCurrentLimit(43);
		Robot.leftShooterMotorA.setCurrentLimit(0);
		Robot.leftShooterMotorB.setCurrentLimit(0);
		Robot.rightShooterMotorA.setCurrentLimit(0);
		Robot.rightShooterMotorB.setCurrentLimit(0);
		Robot.leftLoaderMotor.setCurrentLimit(0);
		Robot.rightLoaderMotor.setCurrentLimit(0);
		Robot.intakeMotor.setCurrentLimit(21);
		Robot.winchMotor.setCurrentLimit(0);
		Robot.leftAzimuthMotor.setCurrentLimit(0);
		Robot.rightAzimuthMotor.setCurrentLimit(0);

		Robot.frontLeftDriveMotor.EnableCurrentLimit(true);
		Robot.frontRightDriveMotor.EnableCurrentLimit(true);
		Robot.backLeftDriveMotor.EnableCurrentLimit(true);
		Robot.backRightDriveMotor.EnableCurrentLimit(true);
		Robot.leftShooterMotorA.EnableCurrentLimit(true);
		Robot.leftShooterMotorB.EnableCurrentLimit(true);
		Robot.rightShooterMotorA.EnableCurrentLimit(true);
		Robot.rightShooterMotorB.EnableCurrentLimit(true);
		Robot.leftLoaderMotor.EnableCurrentLimit(true);
		Robot.rightLoaderMotor.EnableCurrentLimit(true);
		Robot.intakeMotor.EnableCurrentLimit(true);
		Robot.winchMotor.EnableCurrentLimit(true);
		Robot.leftAzimuthMotor.EnableCurrentLimit(true);
		Robot.rightAzimuthMotor.EnableCurrentLimit(true);
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
