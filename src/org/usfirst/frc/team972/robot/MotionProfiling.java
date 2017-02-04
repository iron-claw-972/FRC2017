package org.usfirst.frc.team972.robot;

public class MotionProfiling {
	
	/**
	 * Updates robot model.
	 *
	 * @param dT						Loop time (change in time) in seconds
	 * @param gyro						Gyro angle in degrees
	 * @param frontLeftEncoderDistance	Front left encoder distance
	 * @param backLeftEncoderDistance	Back left encoder distance
	 * @param frontRightEncoderDistance	Front right encoder distance
	 * @param backRightEncoderDistance	Back right encoder distance
	 * @param powerToLeft				Power to left motors
	 * @param powerToRight				Power to right motors
	 */
	public static void update(double dT, double gyro, double frontLeftEncoderDistance, double backLeftEncoderDistance,
			double frontRightEncoderDistance, double backRightEncoderDistance, double powerToLeft, double powerToRight) { //TODO fix encoder click distance to meters
		LeftModel.update(powerToLeft, dT, frontLeftEncoderDistance, backLeftEncoderDistance); //TODO solve power to motors
		RightModel.update(powerToRight, dT, frontRightEncoderDistance, backRightEncoderDistance);
		SystemModel.update(gyro, dT);
	}
	
	/**
	 * Initialize at start of match
	 * 
	 * @param x_init	Initial x position in meters
	 * @param y_init	Initial y position in meters
	 */
	public static void init(double x_init, double y_init) {
		LeftModel.setState(0.0, 0.0, 0.0);
		RightModel.setState(0.0, 0.0, 0.0);
		SystemModel.setState(x_init, y_init, 0.0, 0.0);
	}
	
	/**
	 * Resets the robot position when at a fixed location (i.e. the gear lift)
	 * 
	 * @param x_pos		New x position in meters
	 * @param y_pos		New y position in meters
	 * @param theta		New angle in degrees
	 */
	public static void reset(double x_pos, double y_pos, double theta) {
		double distance = 0.5 * ((Math.PI * Constants.ROBOT_WIDTH) / 360) * theta; //this makes sure that our system model doesn't mess up if theta isn't zero
		LeftModel.setState(distance, 0.0, 0.0);
		RightModel.setState(-distance, 0.0, 0.0);
		SystemModel.setState(x_pos, y_pos, 0.0, theta);
	}
}