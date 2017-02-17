package org.usfirst.frc.team972.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SystemModel {

	//x is m, y is m, v is m/s, theta is degrees
	static double x_k = 0.0;
	static double y_k = 0.0;
	static double v_xk = 0.0;
	static double v_yk = 0.0;
	static double a_xk = 0.0;
	static double a_yk = 0.0;
	static double theta_k = 0.0; // at t=k
	
	// The following variables are private to make it less confusing to use
	private static double x_k1 = 0.0;
	private static double y_k1 = 0.0;
	static double v_xk1 = 0.0;
	static double v_yk1 = 0.0;
	static double a_xk1 = 0.0;
	static double a_yk1 = 0.0;
	private static double theta_k1 = 0.0; // at t=k+1

	/**
	 * Updates system model.
	 *
	 * @param gyro	Gyro angle
	 * @param dT	Loop time (change in time) in seconds
	 */
	public static void update(double gyro, double accel_x, double accel_y, double dT) {
		x_k1 = x_k + (v_xk * dT) + (a_xk * Math.pow(dT, 2) / 2);
		y_k1 = y_k + (v_yk * dT) + (a_yk * Math.pow(dT, 2) / 2);
		
		v_xk1 = v_xk + (a_xk * dT);
		v_yk1 = v_yk + (a_yk * dT);
		
		v_xk1 = (1 - Constants.SYSVEL) * Math.sin(theta_k * Math.PI / 180) * ((LeftModel.v_k + RightModel.v_k) / 2) + Constants.SYSVEL * v_xk1;
		v_yk1 = (1 - Constants.SYSVEL) * Math.cos(theta_k * Math.PI / 180) * ((LeftModel.v_k + RightModel.v_k) / 2) + Constants.SYSVEL * v_yk1;
		
		a_xk1 = (1 - Constants.SYSACC) * Math.sin(theta_k * Math.PI / 180) * ((LeftModel.a_k + RightModel.a_k) / 2) + Constants.SYSACC * a_xk1;
		a_xk1 = (1 - Constants.SYSACC) * Math.cos(theta_k * Math.PI / 180) * ((LeftModel.a_k + RightModel.a_k) / 2) + Constants.SYSACC * a_yk1;
		
		double angle_from_encoders = ((180 / (Math.PI * Constants.ROBOT_WIDTH)) * (LeftModel.x_k - RightModel.x_k)) % 360.0;
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
		v_xk = v_xk1;
		v_yk = v_yk1;
		a_xk = a_xk1;
		a_yk = a_yk1;
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