package org.usfirst.frc.team972.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MotionProfiling {

	/**
	 * Updates robot model.
	 *
	 * @param dT						Loop time (change in time) in seconds
	 * @param gyro						Gyro angle in degrees
	 * @param frontLeftEncoderValue		Front left encoder distance
	 * @param backLeftEncoderValue		Back left encoder distance
	 * @param frontRightEncoderValue	Front right encoder distance
	 * @param backRightEncoderValue		Back right encoder distance
	 * @param LeftAccel					Left side acceleration (m/s^2)
	 * @param rightAccel				Right side acceleration (m/s^2)
	 */
	public static void update(double dT, double gyro, double frontLeftEncoderValue, double backLeftEncoderValue,
			double frontRightEncoderValue, double backRightEncoderValue, double leftAccel, double rightAccel) {
		// @formatter:off
		LeftModel.update(leftAccel, dT, frontLeftEncoderValue * Constants.ROBOT_DRIVE_WHEEL_CIRCUMFERENCE / Constants.ENCODER_CLICKS_PER_ROTATION, 
				backLeftEncoderValue * Constants.ROBOT_DRIVE_WHEEL_CIRCUMFERENCE / Constants.ENCODER_CLICKS_PER_ROTATION);
		RightModel.update(rightAccel, dT, frontRightEncoderValue * Constants.ROBOT_DRIVE_WHEEL_CIRCUMFERENCE / Constants.ENCODER_CLICKS_PER_ROTATION,
				backRightEncoderValue * Constants.ROBOT_DRIVE_WHEEL_CIRCUMFERENCE / Constants.ENCODER_CLICKS_PER_ROTATION);
		SystemModel.update(gyro, dT);
		// @formatter:on
	}

	/**
	 * Initialize at start of match
	 * 
	 * @param x_init	Initial x position in meters
	 * @param y_init	Initial y position in meters
	 */
	public static void init(double x_init, double y_init) {
		IMU.init();
		LeftModel.setState(0.0, 0.0, 0.0);
		RightModel.setState(0.0, 0.0, 0.0);
		SystemModel.setState(x_init, y_init, 0.0, 0.0);
	}

	/**
	 * Resets the robot position when at a fixed location (i.e. the gear lift)
	 * 
	 * @param x_pos	New x position in meters
	 * @param y_pos	New y position in meters
	 * @param theta	New angle in degrees
	 */
	public static void reset(double x_pos, double y_pos, double theta) {
		double distance = 0.5 * ((Math.PI * Constants.ROBOT_WIDTH) / 360) * theta;
		// this makes sure that our system model doesn't mess up if theta isn't zero
		
		double average_encoder_value = 0.0;
		if (!LeftModel.useBackEncoder) {
			if (!RightModel.useBackEncoder) {
				average_encoder_value = (Robot.leftDriveEncoderFront.get() + Robot.rightDriveEncoderFront.get()) / 2;
			} else if (!RightModel.useFrontEncoder) {
				average_encoder_value = (Robot.leftDriveEncoderFront.get() + Robot.rightDriveEncoderBack.get()) / 2;
			} else {
				average_encoder_value = (Robot.leftDriveEncoderFront.get() + Robot.rightDriveEncoderFront.get() + Robot.rightDriveEncoderBack.get()) / 3;
			}
		} else if (!LeftModel.useFrontEncoder) {
			if (!RightModel.useBackEncoder) {
				average_encoder_value = (Robot.leftDriveEncoderBack.get() + Robot.rightDriveEncoderFront.get()) / 2;
			} else if (!RightModel.useFrontEncoder) {
				average_encoder_value = (Robot.leftDriveEncoderBack.get() + Robot.rightDriveEncoderBack.get()) / 2;
			} else {
				average_encoder_value = (Robot.leftDriveEncoderBack.get() + Robot.rightDriveEncoderFront.get() + Robot.rightDriveEncoderBack.get()) / 3;
			}
		} else if (!RightModel.useBackEncoder) {
			average_encoder_value = (Robot.leftDriveEncoderFront.get() + Robot.rightDriveEncoderFront.get() + Robot.leftDriveEncoderBack.get()) / 3;
		} else if (!RightModel.useFrontEncoder) {
			average_encoder_value = (Robot.leftDriveEncoderFront.get() + Robot.rightDriveEncoderBack.get() + Robot.leftDriveEncoderBack.get()) / 3;
		} else {
			average_encoder_value = (Robot.leftDriveEncoderFront.get() + Robot.rightDriveEncoderBack.get() + Robot.leftDriveEncoderBack.get() + Robot.rightDriveEncoderFront.get()) / 4;
		}

		LeftModel.setState(average_encoder_value + distance, 0.0, 0.0);
		RightModel.setState(average_encoder_value - distance, 0.0, 0.0);
		SystemModel.setState(x_pos, y_pos, 0.0, theta);
	}

	public static void updateSmartDashboard() {
		SmartDashboard.putNumber("X Position", getX());
		SmartDashboard.putNumber("Y Position", getY());
		SmartDashboard.putNumber("Angle", getTheta());
		SmartDashboard.putNumber("Left X", LeftModel.x_k);
		SmartDashboard.putNumber("Right X", RightModel.x_k);
		SmartDashboard.putNumber("Gyro", IMU.getAngle());
		SmartDashboard.putNumber("PHI", Constants.PHI);
		SmartDashboard.putNumber("ALPHA", Constants.ALPHA);
		SmartDashboard.putNumber("BETA", Constants.BETA);
	}

	public static double getX() {
		return SystemModel.x_k;
	}

	public static double getY() {
		return SystemModel.y_k;
	}

	public static double getV() {
		return SystemModel.v_k;
	}

	public static double getTheta() {
		return SystemModel.theta_k;
	}
}