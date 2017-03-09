package org.usfirst.frc.team972.robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

public class DriveLoopRedundant {
	double PID_DIVISION_FACTOR = 100000;
	
	public static void drive(CANTalon can, boolean inDrive, double value) {
		if(inDrive) {
			can.set(value);
			can.setPID((double)120000/PID_DIVISION_FACTOR, (double)350/(PID_DIVISION_FACTOR), (double)10000/PID_DIVISION_FACTOR);
		} else {
			can.changeControlMode(TalonControlMode.Voltage);
			can.enableBrakeMode(false);
			can.set(0);
		}
	}
}
