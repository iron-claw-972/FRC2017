package org.usfirst.frc.team9972.robot;

import edu.wpi.first.wpilibj.*;
import com.ctre.*;

public class Robot extends IterativeRobot {
	
	// Motor Objects
	static CANTalon frontLeftDriveMotor = new CANTalon(Constants.FRONT_LEFT_DRIVE_MOTOR_CAN_ID);
	static CANTalon frontRightDriveMotor = new CANTalon(Constants.FRONT_RIGHT_DRIVE_MOTOR_CAN_ID);
	static CANTalon backLeftDriveMotor = new CANTalon(Constants.BACK_LEFT_DRIVE_MOTOR_CAN_ID);
	static CANTalon backRightDriveMotor = new CANTalon(Constants.BACK_RIGHT_DRIVE_MOTOR_CAN_ID);
	static CANTalon winchMotor = new CANTalon(Constants.WINCH_MOTOR_CAN_ID);
	static CANTalon leftFlywheelMotor = new CANTalon(Constants.LEFT_FLYWHEEL_MOTOR_CAN_ID);
	static CANTalon rightFlywheelMotor = new CANTalon(Constants.RIGHT_FLYWHEEL_MOTOR_CAN_ID);
	static CANTalon leftAzimuthMotor = new CANTalon(Constants.LEFT_AZIMUTH_MOTOR_CAN_ID);
	static CANTalon rightAzimuthMotor = new CANTalon(Constants.RIGHT_AZIMUTH_MOTOR_CAN_ID);
	static CANTalon intakeMotor = new CANTalon(Constants.INTAKE_MOTOR_CAN_ID);
	static CANTalon leftLoaderMotor = new CANTalon(Constants.LEFT_LOADER_MOTOR_CAN_ID);
	static CANTalon rightLoaderMotor = new CANTalon(Constants.RIGHT_LOADER_MOTOR_CAN_ID);
	
	// PWM IDs
	static Servo leftHoodLinearActuator = new Servo(Constants.LEFT_HOOD_LINEAR_ACTUATOR_PWM_PORT);
	static Servo rightHoodLinearActuator = new Servo(Constants.RIGHT_HOOD_LINEAR_ACTUATOR_PWM_PORT);
	
	// Joysticks
	static Joystick leftJoystick = new Joystick(Constants.LEFT_JOYSTICK_INPUT_USB_PORT);
	static Joystick rightJoystick = new Joystick(Constants.RIGHT_JOYSTICK_INPUT_USB_PORT);
	static Joystick operatorJoystick = new Joystick(Constants.OPERATOR_JOYSTICK_INPUT_USB_PORT);
	
	// Encoders
	static Encoder leftDriveEncoder = new Encoder(Constants.LEFT_DRIVE_ENCODER_PORT_A, Constants.LEFT_DRIVE_ENCODER_PORT_B);
	static Encoder rightDriveEncoder = new Encoder(Constants.RIGHT_DRIVE_ENCODER_PORT_A, Constants.RIGHT_DRIVE_ENCODER_PORT_B);
	
	// PID Controllers
	static PIDController leftDrivePID = new PIDController(0,0,0,leftDriveEncoder,frontLeftDriveMotor);
	static PIDController rightDrivePID = new PIDController(0,0,0,rightDriveEncoder,frontRightDriveMotor);
	static PIDController leftAzimuthPID = new PIDController(0,0,0,leftAzimuthMotor,leftAzimuthMotor);
	static PIDController rightAzimuthPID = new PIDController(0,0,0,rightAzimuthMotor,rightAzimuthMotor);
	static PIDController leftFlywheelPID = new PIDController(0,0,0,leftFlywheelMotor,leftFlywheelMotor);
	static PIDController rightFlywheelPID = new PIDController(0,0,0,rightFlywheelMotor,rightFlywheelMotor);
	
	static RobotDrive robotDrive = new RobotDrive(frontLeftDriveMotor, backLeftDriveMotor, frontRightDriveMotor, backRightDriveMotor);

	
	public void robotInit() {
		Autonomous.createChooser();
		updateSmartDashboard();
		
		frontLeftDriveMotor.setInverted(true);
		frontRightDriveMotor.setInverted(true);
		backLeftDriveMotor.setInverted(true);
		backRightDriveMotor.setInverted(true);
		
		leftFlywheelPID.setAbsoluteTolerance(5);
		rightFlywheelPID.setAbsoluteTolerance(5);
		leftFlywheelMotor.setPIDSourceType(PIDSourceType.kRate);
		rightFlywheelMotor.setPIDSourceType(PIDSourceType.kRate);
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
