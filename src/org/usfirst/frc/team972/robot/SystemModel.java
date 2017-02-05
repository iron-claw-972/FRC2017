package org.usfirst.frc.team972.robot;

public class SystemModel {

	//x is m, y is m, v is m/s, theta is degrees
	static double x_k, y_k, v_k, theta_k; // at t=k
	
	// The following variables are private to make it less confusing to use
	private static double x_k1, y_k1, v_k1, theta_k1; // at t=k+1

	private static double PHI = Constants.PHI;

	/**
	 * Updates system model.
	 *
	 * @param gyro	Gyro angle
	 * @param dT	Loop time (change in time) in seconds
	 */
	public static void update(double gyro, double dT) {
		x_k1 = x_k + (dT * v_k * Math.sin(theta_k));
		y_k1 = y_k + (dT * v_k * Math.cos(theta_k));
		v_k1 = (LeftModel.v_k + RightModel.v_k) / 2;
		
		double angle_from_encoders = ((360 / (Math.PI * Constants.ROBOT_WIDTH)) * (LeftModel.x_k - RightModel.x_k)) % 360.0;
		if (angle_from_encoders > 180) {
			angle_from_encoders = -360 + angle_from_encoders;
		} else if (angle_from_encoders < -180) {
			angle_from_encoders = 360 + angle_from_encoders;
		}
		double angle_from_gyro = gyro % 360.0;
		if (angle_from_gyro > 180) {
			angle_from_gyro = -360 + angle_from_gyro;
		} else if (angle_from_gyro < -180) {
			angle_from_gyro = 360 + angle_from_gyro;
		}
		if (angle_from_encoders < -90 && angle_from_gyro > 90) {
			theta_k1 = ((1 - Constants.PHI) * (360 + angle_from_encoders)) + (Constants.PHI * angle_from_gyro);
		} else if (angle_from_encoders > 90 && angle_from_gyro < -90) {
			theta_k1 = ((1 - Constants.PHI) * (-360 + angle_from_encoders)) + (Constants.PHI * angle_from_gyro);
		} else {
			theta_k1 = ((1 - Constants.PHI) * angle_from_encoders) + (Constants.PHI * angle_from_gyro);
		}
		if (theta_k1 > 180) {
			theta_k1 = -360 + theta_k1;
		} else if (theta_k1 < -180) {
			theta_k1 = 360 + theta_k1;
		}
		
		x_k = x_k1;
		y_k = y_k1;
		v_k = v_k1;
		theta_k = theta_k1;
	}
	
	/**
	 * Set to a new state
	 * 
	 * @param x_state		New x position in meters
	 * @param y_state		New y position in meters
	 * @param v_state		New velocity in m/s
	 * @param theta_state	New angle in degrees
	 */
	public static void setState(double x_state, double y_state, double v_state, double theta_state) {
		x_k = x_state;
		y_k = y_state;
		v_k = v_state;
		theta_k = theta_state;
	}
}
