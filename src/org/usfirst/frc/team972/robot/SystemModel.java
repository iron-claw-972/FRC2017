package org.usfirst.frc.team972.robot;

public class SystemModel {

	static double x_k, y_k, v_k, theta_k; // at t=k
	
	// The following variables are private to make it less confusing to use
	private static double x_k1, y_k1, v_k1, theta_k1; // at t=k+1

	private static double PHI = Constants.PHI;

	/**
	 * Updates system model.
	 *
	 * @param theta Gyro angle
	 * @param dT	Loop time (change in time)
	 */
	public static void update(double theta, double dT) {
		x_k1 = x_k + (dT * v_k * Math.sin(theta_k));
		y_k1 = y_k + (dT * v_k * Math.cos(theta_k));
		v_k1 = (LeftModel.v_k + RightModel.v_k) / 2;
		theta_k1 = ((1 - PHI) * (Math.PI * Constants.ROBOT_WIDTH / 4) * (LeftModel.v_k + RightModel.v_k) )+ (PHI * theta);

		x_k = x_k1;
		y_k = y_k1;
		v_k = v_k1;
		theta_k = theta_k1;
	}
}
