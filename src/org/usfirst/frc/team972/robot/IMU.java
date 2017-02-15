package org.usfirst.frc.team972.robot;

import com.analog.adis16448.frc.ADIS16448_IMU;

public class IMU {
	private static ADIS16448_IMU imu = new ADIS16448_IMU();

	private static double calibrationAngle = 0.0;

	/**
	 * IMU initiation sequence. Calibrates the IMU.
	 */
	public static void init() {
		imu.calibrate();
		recalibrate(0.0);
	}

	/**
	 * Recalibrates the gyro with a given current angle.
	 * 
	 * @param curr_angle	current angle
	 */
	public static void recalibrate(double curr_angle) {
		calibrationAngle = curr_angle - (imu.getAngleZ() / 4.0);
	}

	/**
	 * Get current angle.
	 * 
	 * @return current angle from IMU
	 */
	public static double getAngle() {
		return ((imu.getAngleZ() / 4.0) + calibrationAngle);
	}
}