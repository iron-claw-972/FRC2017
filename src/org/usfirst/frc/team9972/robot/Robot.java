package org.usfirst.frc.team9972.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.AnalogInput;

public class Robot extends IterativeRobot {
	
	// Motor Objects
	static Victor frontLeftDriveMotor = new Victor(Constants.FRONT_LEFT_DRIVE_MOTOR_PWM_PORT);
	static Victor frontRightDriveMotor = new Victor(Constants.FRONT_RIGHT_DRIVE_MOTOR_PWM_PORT);
	static Victor backLeftDriveMotor = new Victor(Constants.BACK_LEFT_DRIVE_MOTOR_PWM_PORT);
	static Victor backRightDriveMotor = new Victor(Constants.BACK_RIGHT_DRIVE_MOTOR_PWM_PORT);
	

	static RobotDrive robotDrive = new RobotDrive(frontLeftDriveMotor, backLeftDriveMotor, frontRightDriveMotor, backRightDriveMotor);
	
	// Joysticks
	static Joystick leftJoystick = new Joystick(Constants.LEFT_JOYSTICK_INPUT_PORT);
	static Joystick rightJoystick = new Joystick(Constants.RIGHT_JOYSTICK_INPUT_PORT);
	static Joystick operatorJoystick = new Joystick(Constants.OPERATOR_JOYSTICK_INPUT_PORT);
	
	// Encoders
	static Encoder leftDriveEncoder = new Encoder(Constants.LEFT_DRIVE_ENCODER_PORT_A, Constants.LEFT_DRIVE_ENCODER_PORT_B);
	static Encoder rightDriveEncoder = new Encoder(Constants.RIGHT_DRIVE_ENCODER_PORT_A, Constants.RIGHT_DRIVE_ENCODER_PORT_B);
	
	//Analog Sensors
	static  AnalogInput pressureSensor = new AnalogInput(Constants.ANALOG_PRESSURE_SENSOR);
	
	public void robotInit() {
		Autonomous.createChooser();
		updateSmartDashboard();
	}
	
	/*
	 * The four primary functions below (autonomousInit(), autonomousPeriodic(),
	 * teleopInit(), and teleopPeriodic()) should not be altered. Code should be
	 * altered in its respective class (Autonomous or Teleop).
	 * This change will help shorten the Robot class and make it easier to navigate.
	 */
	
	public void autonomousInit() {
		Autonomous.init(this);
		updateSmartDashboard();
	}
	
	public void autonomousPeriodic() {
		Autonomous.periodic(this);
		updateSmartDashboard();
	}
	
	public void teleopInit() {
		Teleop.init(this);
		updateSmartDashboard();
	}
	
	public void teleopPeriodic() {
		Teleop.periodic(this);
		updateSmartDashboard();
	}
	
	public static void updateSmartDashboard() {
		Autonomous.updateSmartDashboardAutonomous();
		Teleop.updateSmartDashboardTeleop();
	}
	
}