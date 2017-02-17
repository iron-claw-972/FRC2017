package org.usfirst.frc.team972.robot;

import com.analog.adis16448.frc.ADIS16448_IMU;

public class IMU {
	private static ADIS16448_IMU imu = new ADIS16448_IMU();

	private static double calibrationAngle = 0.0;
	private static double calibrationAccelX = 0.0;
	private static double calibrationAccelY = 0.0;

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
		calibrationAccelX = - (imu.getAccelX() * 9.8); //TODO: check if this is correct sign and direction
		calibrationAccelY = - (imu.getAccelY() * 9.8); //TODO: check if this is correct sign and direction
	}

	/**
	 * Get current angle.
	 * 
	 * @return current angle from IMU
	 */
	public static double getAngle() {
		return ((imu.getAngleZ() / 4.0) + calibrationAngle);
	}
	
	public static double getAccelX() {
		return ((imu.getAccelX() * 9.8) + calibrationAccelX);
	}
	
	public static double getAccelY() {
		return ((imu.getAccelY() * 9.8) + calibrationAccelY);
	}
}