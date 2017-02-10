package org.usfirst.frc.team972.robot;

public class RightModel {

	//x is m, v is m/s, a is m/s^2
	static double x_k, v_k, a_k; // at t=k
	
	// The following variables are private to make it less confusing to use
	private static double x_k1, v_k1, a_k1; // at t=k+1
	private static double r_k;
	
	/**
	 * Updates right model.
	 *
	 * @param powerToRight			Power to the right motors
	 * @param dT					Loop time (change in time) in seconds
	 * @param frontEncoderDistance	Front right encoder distance
	 * @param backEncoderDistance	Back right encoder distance
	 */
	public static void update(double powerToRight, double dT, double frontEncoderDistance, double backEncoderDistance) { //TODO convert encoder clicks to actual distance
		x_k1 = x_k + (dT * v_k) + ((Math.pow(dT, 2) * a_k) / 2);
		v_k1 = v_k + (dT * a_k);
		a_k1 = powerToRight;

		r_k = ((frontEncoderDistance + backEncoderDistance) / 2) - x_k1; //TODO make it remove an encoder if it is off from the other and system
		x_k1 = x_k1 + (Constants.ALPHA * r_k);
		v_k1 = v_k1 + ((Constants.BETA / dT) * r_k);
		
		x_k = x_k1;
		v_k = v_k1;
		a_k = a_k1;
	}
	
	/**
	 * Set to a new state
	 * 
	 * @param x_state	New distance traveled in meters
	 * @param v_state	New velocity in m/s
	 * @param a_state	New acceleration in m/s^2
	 */
	public static void setState(double x_state, double v_state, double a_state) {
		x_k = x_state;
		v_k = v_state;
		a_k = a_state;
	}
}
