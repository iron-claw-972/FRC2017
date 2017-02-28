package org.usfirst.frc.team972.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.*;

public class ETalon extends CANTalon {

	public static final int MAX_INT = (int) (Math.pow(2, 15)) + 1;

	ETalon(int deviceNumber) {
		super(deviceNumber);
	}

	public double getSpeed() {
		double reportedSpeed = super.getSpeed();
		if (reportedSpeed < 0) {
			return (2 * MAX_INT) + reportedSpeed;
		} else {
			return super.getSpeed();
		}
	}

	public double pidGet() {
		switch (super.getPIDSourceType()) {
			case kDisplacement:
				double position = super.getPosition();
//				System.out.println("Position " + position);
				return position;
			case kRate:
				double speed = getSpeed();
//				System.out.println("Speed " + speed);
				return speed;
			default:
				System.out.println("ERROR!!! PID GET DEFAULTED!!!");
		}
		return 0.0;
	}
	
	public void pidWrite(double output) {
//		System.out.println("PID OUTPUT: " + output);
		SmartDashboard.putNumber("PID Output", output);
		super.pidWrite(output);
	}
 
	public void set(double arg0) {
		super.set(arg0);
		if(arg0 < 0) {
			super.set(0);
			System.out.println("LESS THAN 0!!!");
			System.out.println("LESS THAN 0!!!");
			System.out.println("LESS THAN 0!!!");
			System.out.println("LESS THAN 0!!!");
			System.out.println("LESS THAN 0!!!");
			System.out.println("LESS THAN 0!!!");
			System.out.println("LESS THAN 0!!!");
			System.out.println("LESS THAN 0!!!");
		}
		switch (super.getControlMode()) {
			case PercentVbus:
//				System.out.println(
//						"Setting " + super.getDeviceID() + " to " + arg0 + " on " + getControlMode().toString());
				break;
			default:
				break;
		}
	}
}
