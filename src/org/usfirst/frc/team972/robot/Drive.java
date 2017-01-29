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
		if(inverseDriveButtonPressed && !inverseDriveButtonLastPressed) {
			inverseDriveMode = !inverseDriveMode;
		}
		inverseDriveButtonLastPressed = inverseDriveButtonPressed;
	}
	
	public static void checkBrakeToggle() {
		brakeModeButtonPressed = Robot.rightJoystick.getRawButton(Constants.BRAKE_MODE_TOGGLE_BUTTON);
		if(brakeModeButtonPressed && !brakeModeButtonLastPressed) {
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
				// Taking absolute value of one preserves the positive or negative result (normally squaring makes it positive)
				leftDriveSpeed = Math.abs(leftDriveSpeed) * leftDriveSpeed;
				rightDriveSpeed = Math.abs(rightDriveSpeed) * rightDriveSpeed;
			}
			
			if(inverseDriveMode) {
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
}
