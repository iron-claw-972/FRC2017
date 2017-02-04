package org.usfirst.frc.team972.robot;

public class RightModel {

	static double x_k, v_k, a_k; // at t=k
	
	// The following variables are private to make it less confusing to use
	private static double x_k1, v_k1, a_k1; // at t=k+1
	private static double r_k;
	
	private static double ALPHA = Constants.ALPHA;
	private static double BETA = Constants.BETA;
	
	/**
	 * Updates right model.
	 *
	 * @param powerToRight          Power to the right side
	 * @param dT			       Loop time (change in time)
	 * @param frontEncoderDistance Front right encoder distance
	 * @param backEncoderDistance  Front right encoder distance
	 */
	public static void update(double powerToRight, double dT, double frontEncoderDistance, double backEncoderDistance) {
		x_k1 = x_k + (dT * v_k) + ((Math.pow(dT, 2) * a_k) / 2);
		v_k1 = v_k + (dT * a_k);
		a_k1 = powerToRight;

		r_k = ((frontEncoderDistance + backEncoderDistance) / 2) - x_k1;
		x_k1 = x_k1 + (ALPHA * r_k);
		v_k1 = v_k1 + ((BETA / dT) * r_k);
		
		x_k = x_k1;
		v_k = v_k1;
		a_k = a_k1;
	}
}
