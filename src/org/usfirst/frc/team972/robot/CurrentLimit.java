package org.usfirst.frc.team972.robot;

public class CurrentLimit {
	
	public void init() {
		
		Robot.frontLeftDriveMotor.setCurrentLimit(44);
		Robot.frontRightDriveMotor.setCurrentLimit(44);
		Robot.backLeftDriveMotor.setCurrentLimit(44);
		Robot.backRightDriveMotor.setCurrentLimit(44);
		Robot.leftShooterMotorA.setCurrentLimit(15);
		Robot.leftShooterMotorA.setCurrentLimit(15);
		Robot.rightShooterMotorA.setCurrentLimit(15);
		Robot.rightShooterMotorA.setCurrentLimit(15);
		Robot.leftAzimuthMotor.setCurrentLimit(15);
		Robot.rightAzimuthMotor.setCurrentLimit(15);
		Robot.intakeMotor.setCurrentLimit(21);
		Robot.leftLoaderMotor.setCurrentLimit(16);
		Robot.rightLoaderMotor.setCurrentLimit(16);
		
		Robot.frontLeftDriveMotor.EnableCurrentLimit(true);
		Robot.frontRightDriveMotor.EnableCurrentLimit(true);
		Robot.backLeftDriveMotor.EnableCurrentLimit(true);
		Robot.backRightDriveMotor.EnableCurrentLimit(true);
		Robot.leftShooterMotorA.EnableCurrentLimit(true);
		Robot.leftShooterMotorA.EnableCurrentLimit(true);
		Robot.rightShooterMotorA.EnableCurrentLimit(true);
		Robot.rightShooterMotorA.EnableCurrentLimit(true);
		Robot.leftAzimuthMotor.EnableCurrentLimit(true);
		Robot.rightAzimuthMotor.EnableCurrentLimit(true);
		Robot.intakeMotor.EnableCurrentLimit(true);
		Robot.leftLoaderMotor.EnableCurrentLimit(true);
		Robot.rightLoaderMotor.EnableCurrentLimit(true);
		
		
	}

}
