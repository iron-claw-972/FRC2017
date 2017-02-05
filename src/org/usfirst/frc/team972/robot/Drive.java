package org.usfirst.frc.team972.robot;

import com.ctre.CANTalon.*;

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

	public static void setBrakeMode(boolean brakeStatus) {
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

		setBrakeMode(brakeMode);
	}

	public static void teleopDrive() {
		leftDriveSpeed = Robot.leftJoystick.getY();
		rightDriveSpeed = Robot.rightJoystick.getY();

		checkInverseToggle();
		checkBrakeToggle();

		if (Robot.leftJoystick.getRawButton(Constants.STOP_DRIVE_BUTTON)) {
			stopDrive();
		} else {
			if (Robot.leftJoystick.getRawButton(Constants.SQUARED_DRIVE_BUTTON)) { //TODO Squared drive is the worst
				// Taking absolute value of one preserves the positive or negative result (normally squaring makes it positive)
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
	
	public static void autoDrive(double x_desired, double y_desired, double theta_desired, double dT, double prev_v, double prev_theta) {
		double curr_x = MotionProfiling.getX();
		double curr_y = MotionProfiling.getY();
		double curr_v = MotionProfiling.getV();
		double curr_theta = MotionProfiling.getTheta();
		
		setBrakeMode(true);
		
		//distance from robot to desired point using Pythagorean theorem
		double distance = Math.pow((Math.pow((x_desired - curr_x), 2) + Math.pow((y_desired - curr_y), 2)), 0.5);
		
		//get desired angle of robot to get to in degrees using arctan from -180 to +180
		double trajectory_angle = Math.atan2((x_desired - curr_x), (y_desired - curr_y)) + (Math.PI / 2);
		if (trajectory_angle > Math.PI) {
			trajectory_angle = Math.toDegrees(trajectory_angle - (2 * Math.PI));
		} else if (trajectory_angle < -Math.PI) {
			trajectory_angle = Math.toDegrees((2 * Math.PI) + trajectory_angle);
		} else {
			trajectory_angle = Math.toDegrees(trajectory_angle);
		}
		
		//determine which direction the robot needs to turn
		boolean isReverseDrive = false;
		double theta_error = trajectory_angle - curr_theta;
		if (theta_error > 90.0) {
			isReverseDrive = true;
			theta_error = theta_error - 180;
		} else if (theta_error < -90.0) {
			isReverseDrive = true;
			theta_error = 180 + theta_error;
		}
		
		double v_error = 0.0;
		if (isReverseDrive) {
			v_error = -Constants.ROBOT_MAX_VELOCITY - curr_v;
			if (distance < Constants.AUTON_STOPPING_DISTANCE_2) {
				v_error = - ((Constants.AUTON_STOPPING_DISTANCE_2 - distance) *
						((Constants.ROBOT_MAX_VELOCITY * Constants.AUTON_VELOCITY_STOPPING_PROPORTION) / Constants.AUTON_STOPPING_DISTANCE_2)) - curr_v;
			} else if (distance < Constants.AUTON_STOPPING_DISTANCE_1) {
				v_error = - ((Constants.AUTON_STOPPING_DISTANCE_1 - distance) * 
						(((1 - Constants.AUTON_VELOCITY_STOPPING_PROPORTION) * Constants.ROBOT_MAX_VELOCITY) / 
								(Constants.AUTON_STOPPING_DISTANCE_1 - Constants.AUTON_STOPPING_DISTANCE_2))) - curr_v;
			}
		} else {
			v_error = Constants.ROBOT_MAX_VELOCITY - curr_v;
			if (distance < Constants.AUTON_STOPPING_DISTANCE_2) {
				v_error = ((Constants.AUTON_STOPPING_DISTANCE_2 - distance) *
						((Constants.ROBOT_MAX_VELOCITY * Constants.AUTON_VELOCITY_STOPPING_PROPORTION) / Constants.AUTON_STOPPING_DISTANCE_2)) - curr_v;
			} else if (distance < Constants.AUTON_STOPPING_DISTANCE_1) {
				v_error = ((Constants.AUTON_STOPPING_DISTANCE_1 - distance) * 
						(((1 - Constants.AUTON_VELOCITY_STOPPING_PROPORTION) * Constants.ROBOT_MAX_VELOCITY) / 
								(Constants.AUTON_STOPPING_DISTANCE_1 - Constants.AUTON_STOPPING_DISTANCE_2))) - curr_v;
			}
		}
		
		double dVdT = (curr_v - prev_v) / dT;
		double dThetadT = (curr_theta - prev_theta) / dT;
		
		double power_for_velocity = Constants.AUTON_DRIVE_RATIO * ((Constants.AUTON_DRIVE_VP * v_error) - (Constants.AUTON_DRIVE_VD * dVdT));
		double power_for_turning = (1 - Constants.AUTON_DRIVE_RATIO) * (Constants.AUTON_DRIVE_AP * Math.abs(theta_error) * theta_error) - (Constants.AUTON_DRIVE_AD * dThetadT);
		
		if (isReverseDrive) {
			inverseDrive(power_for_velocity + power_for_turning, power_for_velocity - power_for_turning);
		} else {
			tankDrive(power_for_velocity + power_for_turning, power_for_velocity - power_for_turning);
		}
	}
	
	public static void updateModel() { //TODO put values
		//MotionProfiling.update(dT, gyro, frontLeftEncoderDistance, backLeftEncoderDistance, frontRightEncoderDistance, backRightEncoderDistance, powerToLeft, powerToRight);
	}

	public static void updateSmartDashboard() {
		SmartDashboard.putNumber("Left Drive Speed", leftDriveSpeed);
		SmartDashboard.putNumber("Right Drive Speed", rightDriveSpeed);
		SmartDashboard.putNumber("Left Encoder A", Robot.leftDriveEncoderA.get()); //TODO change to Left Encoder Front, etc.
		SmartDashboard.putNumber("Right Encoder A", Robot.rightDriveEncoderA.get());
		SmartDashboard.putNumber("Left Encoder B", Robot.leftDriveEncoderB.get());
		SmartDashboard.putNumber("Right Encoder B", Robot.rightDriveEncoderB.get());
	}
}
