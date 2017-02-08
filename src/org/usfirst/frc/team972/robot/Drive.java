package org.usfirst.frc.team972.robot;

import com.ctre.CANTalon.*;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive {

	static double leftDriveSpeed = 0;
	static double rightDriveSpeed = 0;
	
	static double prev_v_error = 0.0;
	static double prev_theta_error = 0.0;

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
		leftDriveSpeed = driveSpeedLeft;
		rightDriveSpeed = driveSpeedRight;
		Robot.frontLeftDriveMotor.set(leftDriveSpeed);
		Robot.frontRightDriveMotor.set(rightDriveSpeed);
	}

	public static void inverseDrive(double driveSpeedLeft, double driveSpeedRight) {
		leftDriveSpeed = -driveSpeedLeft;
		rightDriveSpeed = -driveSpeedRight;
		tankDrive(leftDriveSpeed, rightDriveSpeed);
	}

	public static void stopDrive() {
		leftDriveSpeed = 0;
		rightDriveSpeed = 0;
		tankDrive(leftDriveSpeed, rightDriveSpeed);
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
		double leftDriveInput = Robot.leftJoystick.getY();
		double rightDriveInput = Robot.rightJoystick.getY();

		checkInverseToggle();
		checkBrakeToggle();

		if (Robot.leftJoystick.getRawButton(Constants.STOP_DRIVE_BUTTON)) {
			stopDrive();
		} else {
			if (Robot.leftJoystick.getRawButton(Constants.SQUARED_DRIVE_BUTTON)) { //TODO Squared drive is the worst
				// Taking absolute value of one preserves the positive or negative result (normally squaring makes it positive)
				leftDriveInput = Math.abs(leftDriveInput) * leftDriveInput;
				rightDriveInput = Math.abs(rightDriveInput) * rightDriveInput;
			}

			if (inverseDriveMode) {
				inverseDrive(leftDriveInput, rightDriveInput);
			} else {
				tankDrive(leftDriveInput, rightDriveInput);
			}
		}
	}
	
	public static boolean autoDrive(double x_desired, double y_desired, double theta_desired, double dT) {
		double curr_x = MotionProfiling.getX();
		double curr_y = MotionProfiling.getY();
		double curr_v = MotionProfiling.getV();
		double curr_theta = MotionProfiling.getTheta();
		
		double v_error = 0.0;
		double theta_error = 0.0;
		
		boolean done = false;
		
		setBrakeMode(true);
		
		//distance from robot to desired point using Pythagorean theorem
		double distance = Math.pow((Math.pow((x_desired - curr_x), 2) + Math.pow((y_desired - curr_y), 2)), 0.5);
		if (distance > 0.02) {
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
			theta_error = trajectory_angle - curr_theta;
			if (theta_error > 90.0) {
				isReverseDrive = true;
				theta_error = theta_error - 180;
			} else if (theta_error < -90.0) {
				isReverseDrive = true;
				theta_error = 180 + theta_error;
			}
		
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
			
			double dVdT = 0.0;
			if (prev_v_error != 0.0) {
				dVdT = (v_error - prev_v_error) / dT;
			}
			double dThetadT = 0.0;
			if (prev_theta_error != 0.0) {
				dThetadT = (theta_error - prev_theta_error) / dT;
			}
			
			double power_for_velocity = Constants.AUTON_DRIVE_RATIO * ((Constants.AUTON_DRIVE_VP * v_error) - (Constants.AUTON_DRIVE_VD * dVdT));
			double power_for_turning = (1 - Constants.AUTON_DRIVE_RATIO) * (Constants.AUTON_DRIVE_AP * Math.abs(theta_error) * theta_error) - 
					(Constants.AUTON_DRIVE_AD * dThetadT);
		
			double leftDriveInput = power_for_velocity + power_for_turning;
			double rightDriveInput = power_for_velocity - power_for_turning;
		
			if (isReverseDrive) {
				inverseDrive(leftDriveInput, rightDriveInput);
			} else {
				tankDrive(leftDriveInput, rightDriveInput);
			}
		} else {
			theta_error = theta_desired - curr_theta;
			double dThetadT = 0.0;
			if (prev_theta_error != 0.0) {
				dThetadT = (theta_error - prev_theta_error) / dT;
			}
			double turn_power = (Constants.AUTON_DRIVE_AP * theta_error) - (Constants.AUTON_DRIVE_AD * dThetadT);
			
			double leftDriveInput = turn_power;
			double rightDriveInput = turn_power;
			
			tankDrive(leftDriveInput, rightDriveInput);
			
			if (theta_error < 10) {
				done = true;
			}
		}
		
		prev_v_error = v_error;
		prev_theta_error = theta_error;
		
		if (done) {
			stopDrive();
		}
		
		return done;
	}
	
	public static void updateModel(double dT) {
		MotionProfiling.update(dT, IMU.getAngle(), Robot.leftDriveEncoderFront.get(), Robot.leftDriveEncoderBack.get(), Robot.rightDriveEncoderFront.get(), 
				Robot.rightDriveEncoderBack.get(), getAccel("left"), getAccel("right"));
	}

	public static void updateSmartDashboard() {
		SmartDashboard.putNumber("Left Drive Speed", leftDriveSpeed);
		SmartDashboard.putNumber("Right Drive Speed", rightDriveSpeed);
		SmartDashboard.putNumber("Left Encoder Front", Robot.leftDriveEncoderFront.get());
		SmartDashboard.putNumber("Right Encoder Front", Robot.rightDriveEncoderFront.get());
		SmartDashboard.putNumber("Left Encoder Back", Robot.leftDriveEncoderBack.get());
		SmartDashboard.putNumber("Right Encoder Back", Robot.rightDriveEncoderBack.get());
	}
	
	public static double getAccel(String robotSide) { //TODO: make sure this is actually the right equation
		if (robotSide == "left") {
			return 0.4448 * ((Robot.frontLeftDriveMotor.getOutputCurrent() + Robot.backLeftDriveMotor.getOutputCurrent()) / 2) * (1 - 0.0356 * LeftModel.v_k) / Constants.ROBOT_MASS;
		} else if (robotSide == "right") {
			return 0.4448 * ((Robot.frontRightDriveMotor.getOutputCurrent() + Robot.backRightDriveMotor.getOutputCurrent()) / 2) * (1 - 0.0356 * RightModel.v_k) / Constants.ROBOT_MASS;
		} else {
			return -9001.0;
		}
	}
}
