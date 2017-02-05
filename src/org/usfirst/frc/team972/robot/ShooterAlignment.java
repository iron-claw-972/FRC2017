package org.usfirst.frc.team972.robot;

import edu.wpi.first.wpilibj.*;

public class ShooterAlignment { //TODO put this in Shooter.java

	public static void init() {
		Robot.leftAzimuthMotor.enableBrakeMode(true);
		Robot.rightAzimuthMotor.enableBrakeMode(true);
		Robot.leftLoaderMotor.enableBrakeMode(true);
		Robot.rightLoaderMotor.enableBrakeMode(true);

		Robot.leftShooterPID.setAbsoluteTolerance(5);
		Robot.rightShooterPID.setAbsoluteTolerance(5);
		Robot.leftShooterMotorA.setPIDSourceType(PIDSourceType.kRate);
		Robot.rightShooterMotorA.setPIDSourceType(PIDSourceType.kRate);
		Robot.leftShooterMotorB.setPIDSourceType(PIDSourceType.kRate);
		Robot.rightShooterMotorB.setPIDSourceType(PIDSourceType.kRate);
	}
}
