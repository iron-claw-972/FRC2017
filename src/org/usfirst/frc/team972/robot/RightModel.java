package org.usfirst.frc.team972.robot;

public class RightModel {

	// x is m, v is m/s, a is m/s^2
	static double x_k = 0.0;
	static double v_k = 0.0;
	static double a_k = 0.0; // at t=k

	// The following variables are private to make it less confusing to use
	private static double x_k1 = 0.0;
	private static double v_k1 = 0.0;
	private static double a_k1 = 0.0; // at t=k+1
	private static double r_k = 0.0;

	static boolean useFrontEncoder = true;
	static boolean useBackEncoder = true;

	/**
	 * Updates right model.
	 *
	 * @param powerToRight          Power to the right motors
	 * @param dT                    Loop time (change in time) in seconds
	 * @param frontEncoderDistance  Front right encoder distance
	 * @param backEncoderDistance   Back right encoder distance
	 */
	public static void update(double powerToRight, double dT, double frontEncoderDistance, double backEncoderDistance) {
		x_k1 = x_k + (dT * v_k) + ((Math.pow(dT, 2) * a_k) / 2);
		v_k1 = v_k + (dT * a_k);
		a_k1 = powerToRight;

		if (useBackEncoder && useFrontEncoder) {
			if (Math.abs(x_k1 - frontEncoderDistance) / x_k1 < 0.02 && Math.abs(x_k1 - backEncoderDistance) / x_k1 > 0.10) {
				useBackEncoder = false;
				System.out.println("Not using back encoder");
			} else if (Math.abs(x_k1 - backEncoderDistance) / x_k1 < 0.02 && Math.abs(x_k1 - frontEncoderDistance) / x_k1 > 0.10) {
				useFrontEncoder = false;
				System.out.println("Not using front encoder");
			}
		}

		if (useBackEncoder && useFrontEncoder) {
			r_k = ((frontEncoderDistance + backEncoderDistance) / 2) - x_k1;
		} else if (useFrontEncoder && !useBackEncoder) {
			r_k = frontEncoderDistance - x_k1;
		} else if (useBackEncoder && !useFrontEncoder) {
			r_k = backEncoderDistance - x_k1;
		}

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
