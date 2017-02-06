package org.usfirst.frc.team972.robot;

import com.analog.adis16448.frc.ADIS16448_IMU;

public class IMU {
	ADIS16448_IMU imu = new ADIS16448_IMU();
	
	private double calibAngle = 0.0;
	
	public void init() {
		imu.calibrate();
		calibAngle = 0.0;
	}
	
	public void recalibrate(double curr_angle) {
		calibAngle = curr_angle + (imu.getAngleZ() / 4);
	}
	
	public double getAngle() {
		return (-(imu.getAngleZ() / 4) - calibAngle);
	}
}