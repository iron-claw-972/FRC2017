package org.usfirst.frc.team972.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.*;
import com.ctre.CANTalon.TalonControlMode;

public class Shooter {

	static double leftShooterSpeed = 0;
	static double rightShooterSpeed = 0;
	
	public static void init() {
		Robot.leftShooterMotorA.enableBrakeMode(true);
		Robot.leftShooterMotorB.enableBrakeMode(true);
		Robot.rightShooterMotorA.enableBrakeMode(true);
		Robot.rightShooterMotorB.enableBrakeMode(true);

		Robot.leftShooterMotorB.changeControlMode(TalonControlMode.Follower);
		Robot.rightShooterMotorB.changeControlMode(TalonControlMode.Follower);

		Robot.leftShooterMotorB.set(Constants.LEFT_SHOOTER_MOTOR_A_CAN_ID);
		Robot.rightShooterMotorB.set(Constants.RIGHT_SHOOTER_MOTOR_A_CAN_ID);
	}
	
	public static void updateSmartDashboard() {
		SmartDashboard.putNumber("Left Shooter Speed", leftShooterSpeed);
		SmartDashboard.putNumber("Right Shooter Speed", rightShooterSpeed);
	}
}
