package org.usfirst.frc.team972.robot;

import edu.wpi.first.wpilibj.*;

public class ShooterAlignment {
	
	public static void init() {
		Robot.leftAzimuthMotor.enableBrakeMode(true);
		Robot.rightAzimuthMotor.enableBrakeMode(true);
		Robot.leftLoaderMotor.enableBrakeMode(true);
		Robot.rightLoaderMotor.enableBrakeMode(true);
		
		Robot.leftFlywheelPID.setAbsoluteTolerance(5);
		Robot.rightFlywheelPID.setAbsoluteTolerance(5);
		Robot.leftFlywheelMotor.setPIDSourceType(PIDSourceType.kRate);
		Robot.rightFlywheelMotor.setPIDSourceType(PIDSourceType.kRate);
	}
}
