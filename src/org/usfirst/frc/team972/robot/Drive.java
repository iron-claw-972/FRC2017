package org.usfirst.frc.team972.robot;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive {

	static double leftDriveSpeed = 0;
	static double rightDriveSpeed = 0;

	static boolean inverseDriveMode = false;
	static boolean inverseDriveButtonPressed = false;
	static boolean inverseDriveButtonLastPressed = false;

	static boolean brakeMode = false;
	static boolean brakeModeButtonPressed = false;
	static boolean brakeModeButtonLastPressed = false;

	static double[] leftModel = new double[3]; //x_k, v_k, a_k
	static double[] rightModel = new double[3]; //x_k, v_k, a_k
	static double[] systemModel = new double[4]; //x_k, y_k, v_k, theta_k
	
	public static void init() {
		Robot.frontLeftDriveMotor.setInverted(true);
		Robot.frontRightDriveMotor.setInverted(true);
		Robot.centerLeftDriveMotor.setInverted(true);
		Robot.centerRightDriveMotor.setInverted(true);
		Robot.backLeftDriveMotor.setInverted(true);
		Robot.backRightDriveMotor.setInverted(true);

		Robot.frontLeftDriveMotor.enableBrakeMode(true);
		Robot.frontRightDriveMotor.enableBrakeMode(true);
		Robot.centerLeftDriveMotor.enableBrakeMode(true);
		Robot.centerRightDriveMotor.enableBrakeMode(true);
		Robot.backLeftDriveMotor.enableBrakeMode(true);
		Robot.backRightDriveMotor.enableBrakeMode(true);

		Robot.centerLeftDriveMotor.changeControlMode(TalonControlMode.Follower);
		Robot.centerRightDriveMotor.changeControlMode(TalonControlMode.Follower);
		Robot.backLeftDriveMotor.changeControlMode(TalonControlMode.Follower);
		Robot.backRightDriveMotor.changeControlMode(TalonControlMode.Follower);

		Robot.centerLeftDriveMotor.set(Constants.FRONT_LEFT_DRIVE_MOTOR_CAN_ID);
		Robot.backLeftDriveMotor.set(Constants.FRONT_LEFT_DRIVE_MOTOR_CAN_ID);
		Robot.centerRightDriveMotor.set(Constants.FRONT_RIGHT_DRIVE_MOTOR_CAN_ID);
		Robot.backRightDriveMotor.set(Constants.FRONT_RIGHT_DRIVE_MOTOR_CAN_ID);
	}

	public static void tankDrive(double driveSpeedLeft, double driveSpeedRight) {
		Robot.frontLeftDriveMotor.set(driveSpeedLeft);
		Robot.frontRightDriveMotor.set(driveSpeedRight);
	}

	public static void inverseDrive(double driveSpeedLeft, double driveSpeedRight) {
		tankDrive(-driveSpeedRight, -driveSpeedLeft);
	}

	public static void stopDrive() {
		tankDrive(0, 0);
	}

	public static void toggleBrakeMode(boolean brakeStatus) {
		Robot.frontLeftDriveMotor.enableBrakeMode(brakeStatus);
		Robot.frontRightDriveMotor.enableBrakeMode(brakeStatus);
		Robot.backLeftDriveMotor.enableBrakeMode(brakeStatus);
		Robot.backRightDriveMotor.enableBrakeMode(brakeStatus);
	}

	public static void checkInverseToggle() {
		inverseDriveButtonPressed = Robot.leftJoystick.getRawButton(Constants.INVERSE_DRIVE_TOGGLE_BUTTON);
		if (inverseDriveButtonPressed && !inverseDriveButtonLastPressed) {
			inverseDriveMode = !inverseDriveMode;
		}
		inverseDriveButtonLastPressed = inverseDriveButtonPressed;
	}

	public static void checkBrakeToggle() {
		brakeModeButtonPressed = Robot.rightJoystick.getRawButton(Constants.BRAKE_MODE_TOGGLE_BUTTON);
		if (brakeModeButtonPressed && !brakeModeButtonLastPressed) {
			brakeMode = !brakeMode;
		}
		brakeModeButtonLastPressed = brakeModeButtonPressed;

		Drive.toggleBrakeMode(brakeMode);
	}

	public static void run() {
		leftDriveSpeed = Robot.leftJoystick.getY();
		rightDriveSpeed = Robot.rightJoystick.getY();

		checkInverseToggle();
		checkBrakeToggle();

		if (Robot.leftJoystick.getRawButton(Constants.STOP_DRIVE_BUTTON)) {
			stopDrive();
		} else {
			if (Robot.leftJoystick.getRawButton(Constants.SQUARED_DRIVE_BUTTON)) {
				// Taking absolute value of one preserves the positive or
				// negative result (normally squaring makes it positive)
				leftDriveSpeed = Math.abs(leftDriveSpeed) * leftDriveSpeed;
				rightDriveSpeed = Math.abs(rightDriveSpeed) * rightDriveSpeed;
			}

			if (inverseDriveMode) {
				inverseDrive(leftDriveSpeed, rightDriveSpeed);
			} else {
				tankDrive(leftDriveSpeed, rightDriveSpeed);
			}
		}
	}

	public static void updateSmartDashboard() {
		SmartDashboard.putNumber("Left Drive Speed", leftDriveSpeed);
		SmartDashboard.putNumber("Right Drive Speed", rightDriveSpeed);
		SmartDashboard.putNumber("Left Encoder A", Robot.leftDriveEncoderA.get());
		SmartDashboard.putNumber("Right Encoder A", Robot.rightDriveEncoderA.get());
		SmartDashboard.putNumber("Left Encoder B", Robot.leftDriveEncoderB.get());
		SmartDashboard.putNumber("Right Encoder B", Robot.rightDriveEncoderB.get());
	}
	
	public static void updateLeftModel(double powerToLeft, double loopTime, double frontEncoderDistance, double backEncoderDistance) {
		double[] newState = new double[3];
		newState[0] = leftModel[0] + loopTime * leftModel[1] + Math.pow(loopTime, 2) * leftModel[2] / 2;
		newState[1] = leftModel[1] + loopTime * leftModel[2];
		newState[2] = powerToLeft;

		double r_k = ((frontEncoderDistance + backEncoderDistance) / 2) - newState[0];
		newState[0] = newState[0] + Constants.SYSTEM_MODEL_CONSTANTS[0] * r_k;
		newState[1] = newState[1] + (Constants.SYSTEM_MODEL_CONSTANTS[1] / loopTime) * r_k;
		
		leftModel = newState;
	}
	
	public static void updateRightModel(double powerToRight, double loopTime, double frontEncoderDistance, double backEncoderDistance) {
		double[] newState = new double[3];
		newState[0] = rightModel[0] + loopTime * rightModel[1] + Math.pow(loopTime, 2) * rightModel[2] / 2;
		newState[1] = rightModel[1] + loopTime * rightModel[2];
		newState[2] = powerToRight;

		double r_k = ((frontEncoderDistance + backEncoderDistance) / 2) - newState[0];
		newState[0] = newState[0] + Constants.SYSTEM_MODEL_CONSTANTS[0] * r_k;
		newState[1] = newState[1] + (Constants.SYSTEM_MODEL_CONSTANTS[1] / loopTime) * r_k;
		
		rightModel = newState;
	}
	
	public static void updateSystemModel(double gyroAngle, double robotWidth, double loopTime) { //TODO: put robotWidth into Constants
		double[] newState = new double[4];
		newState[0] = systemModel[0] + loopTime * systemModel[2] * Math.sin(systemModel[3]);
		newState[1] = systemModel[1] + loopTime * systemModel[2] * Math.cos(systemModel[3]);
		newState[2] = (leftModel[1] + rightModel[1]) / 2;
		newState[3] = (1 - Constants.SYSTEM_MODEL_CONSTANTS[2]) * (Math.PI * robotWidth / 4) * (leftModel[0] - rightModel[0])
				+ Constants.SYSTEM_MODEL_CONSTANTS[2] * gyroAngle;
		
		systemModel = newState;
	}
}
