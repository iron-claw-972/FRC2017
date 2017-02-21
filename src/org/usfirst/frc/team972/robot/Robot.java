package org.usfirst.frc.team972.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.text.*;
import java.util.*;
import com.ctre.*;

public class Robot extends IterativeRobot {

	static CANTalon frontLeftDriveMotor = new CANTalon(Constants.FRONT_LEFT_DRIVE_MOTOR_CAN_ID);
	static CANTalon frontRightDriveMotor = new CANTalon(Constants.FRONT_RIGHT_DRIVE_MOTOR_CAN_ID);
	static CANTalon backLeftDriveMotor = new CANTalon(Constants.BACK_LEFT_DRIVE_MOTOR_CAN_ID);
	static CANTalon backRightDriveMotor = new CANTalon(Constants.BACK_RIGHT_DRIVE_MOTOR_CAN_ID);
	static CANTalon winchMotor = new CANTalon(Constants.WINCH_MOTOR_CAN_ID);
	static CANTalon leftShooterMotorA = new CANTalon(Constants.LEFT_SHOOTER_MOTOR_A_CAN_ID);
	static CANTalon leftShooterMotorB = new CANTalon(Constants.LEFT_SHOOTER_MOTOR_B_CAN_ID);
	static CANTalon rightShooterMotorA = new CANTalon(Constants.RIGHT_SHOOTER_MOTOR_A_CAN_ID);
	static CANTalon rightShooterMotorB = new CANTalon(Constants.RIGHT_SHOOTER_MOTOR_B_CAN_ID);
	static CANTalon leftAzimuthMotor = new CANTalon(Constants.LEFT_AZIMUTH_MOTOR_CAN_ID);
	static CANTalon rightAzimuthMotor = new CANTalon(Constants.RIGHT_AZIMUTH_MOTOR_CAN_ID);
	static CANTalon intakeMotor = new CANTalon(Constants.INTAKE_MOTOR_CAN_ID);
	static CANTalon leftLoaderMotor = new CANTalon(Constants.LEFT_LOADER_MOTOR_CAN_ID);
	static CANTalon rightLoaderMotor = new CANTalon(Constants.RIGHT_LOADER_MOTOR_CAN_ID);

	static Servo leftHoodLinearActuator = new Servo(Constants.LEFT_HOOD_LINEAR_ACTUATOR_PWM_PORT);
	static Servo rightHoodLinearActuator = new Servo(Constants.RIGHT_HOOD_LINEAR_ACTUATOR_PWM_PORT);

	static Joystick leftJoystick = new Joystick(Constants.LEFT_JOYSTICK_INPUT_USB_PORT);
	static Joystick rightJoystick = new Joystick(Constants.RIGHT_JOYSTICK_INPUT_USB_PORT);
	static Joystick operatorJoystick = new Joystick(Constants.OPERATOR_JOYSTICK_INPUT_USB_PORT);

	static Encoder leftDriveEncoderFront = new Encoder(Constants.LEFT_DRIVE_ENCODER_FRONT_PORT_A,
			Constants.LEFT_DRIVE_ENCODER_FRONT_PORT_B, true, Encoder.EncodingType.k2X);
	static Encoder rightDriveEncoderFront = new Encoder(Constants.RIGHT_DRIVE_ENCODER_FRONT_PORT_A,
			Constants.RIGHT_DRIVE_ENCODER_FRONT_PORT_B, false, Encoder.EncodingType.k2X);
	static Encoder leftDriveEncoderBack = new Encoder(Constants.LEFT_DRIVE_ENCODER_BACK_PORT_A,
			Constants.LEFT_DRIVE_ENCODER_BACK_PORT_B, true, Encoder.EncodingType.k2X);
	static Encoder rightDriveEncoderBack = new Encoder(Constants.RIGHT_DRIVE_ENCODER_BACK_PORT_A,
			Constants.RIGHT_DRIVE_ENCODER_BACK_PORT_B, false, Encoder.EncodingType.k2X);
	
	static PowerDistributionPanel pdp = new PowerDistributionPanel(Constants.PDP_CAN_ID);

//	static Compressor compressor = new Compressor(Constants.COMPRESSOR_PCM_PORT);
//	static DoubleSolenoid gearPegPiston = new DoubleSolenoid(Constants.GEAR_PEG_PISTON_FORWARD_PCM_PORT,
//			Constants.GEAR_PEG_PISTON_REVERSE_PCM_PORT);
//	static DoubleSolenoid gearPusherPiston = new DoubleSolenoid(Constants.GEAR_PUSHER_PISTON_FORWARD_PCM_PORT,
//			Constants.GEAR_PUSHER_PISTON_REVERSE_PCM_PORT);
//	static DoubleSolenoid loaderDoorPiston = new DoubleSolenoid(Constants.LOADER_DOOR_PISTON_FORWARD_PCM_PORT,
//			Constants.LOADER_DOOR_PISTON_REVERSE_PCM_PORT);
//	static DoubleSolenoid fieldHopperPiston = new DoubleSolenoid(Constants.FIELD_HOPPER_PISTON_FOWARD_PCM_PORT,
//			Constants.FIELD_HOPPER_PISTON_REVERSE_PCM_PORT);
//
	/**
	 * Robot-wide initialization code. This method is for default Robot-wide initialization and will
	 * be called when the robot is first powered on. It will be called exactly one time.
	 * <p>
	 * Warning: the Driver Station "Robot Code" light and FMS "Robot Ready" indicators will be off
	 * until robotInit() exits. Code in robotInit() that waits for enable will cause the robot to
	 * never indicate that the code is ready, causing the robot to be bypassed in a match.
	 * 
	 * @see init()
	 */
	public void robotInit() {
		IMU.init();
		SendableChooser autoChooser = new SendableChooser();
		autoChooser.addDefault("1", AutonomousRoutine.DO_NOTHING);
		autoChooser.addObject("2", AutonomousRoutine.CROSS_BASELINE);
		SmartDashboard.putData("Autonomous Mode Chooser", autoChooser);
		Autonomous.createChooser();
		Autonomous.updateSmartDashboard();
		Teleop.updateSmartDashboard();
		CameraStreaming.init();
    
		(new Thread(new Jetson())).start(); //start networking to Jetson

		init();
	}

	/*
	 * The four primary functions below (autonomousInit(), autonomousPeriodic(), teleopInit(), and
	 * teleopPeriodic()) should not be altered. Code should be altered in its respective class
	 * (Autonomous or Teleop). This change will help shorten the Robot class and make it easier to
	 * navigate.
	 */

	/**
	 * Initialization code for autonomous mode. This method for initialization code will be called
	 * each time the robot enters autonomous mode.
	 * 
	 * @see Autonomous.init()
	 */
	public void autonomousInit() {
		Autonomous.init(this);
	}

	/**
	 * Periodic code for autonomous mode. This method will be called each time a new packet is
	 * received from the driver station and the robot is in autonomous mode.
	 * <p>
	 * Packets are received approximately every 20ms. Fixed loop timing is not guaranteed due to
	 * network timing variability and the function may not be called at all if the Driver Station is
	 * disconnected. For most use cases the variable timing will not be an issue.
	 * 
	 * @see Autonomous.periodic()
	 */
	public void autonomousPeriodic() {
		Autonomous.periodic(this);
	}

	/**
	 * Initialization code for teleop mode. This method for initialization code will be called each
	 * time the robot enters teleop mode.
	 * 
	 * @see Teleop.init()
	 */
	public void teleopInit() {
		Teleop.init(this);
	}

	/**
	 * Periodic code for teleop mode. This method will be called each time a new packet is received
	 * from the driver station and the robot is in teleop mode.
	 * <p>
	 * Packets are received approximately every 20ms. Fixed loop timing is not guaranteed due to
	 * network timing variability and the function may not be called at all if the Driver Station is
	 * disconnected. For most use cases the variable timing will not be an issue.
	 * 
	 * @see Teleop.teleopPeriodic()
	 */
	public void teleopPeriodic() {
		Teleop.periodic(this);
	}

	/**
	 * Periodic code for test mode. This method will be called each time a new packet is received
	 * from the driver station and the robot is in test mode.
	 * <p>
	 * Packets are received approximately every 20ms. Fixed loop timing is not guaranteed due to
	 * network timing variability and the function may not be called at all if the Driver Station is
	 * disconnected. For most use cases the variable timing will not be an issue.
	 * 
	 * @see Tester.run()
	 */
	public void testPeriodic() {
		Tester.run();
	}

	/**
	 * Gets current date and time with the format dd-MM-yy=HH-mm-ss.
	 * 
	 * @return current date and time as dd-MM-yy=HH-mm-ss
	 */
	public static String getTime() {
		return new SimpleDateFormat("MM-dd-yy_HH:mm:ss").format(Calendar.getInstance().getTime());
	}

	/**
	 * Initialization code. This method for initialization code will be called each time the robot
	 * enters autonomous or teleop mode.
	 */
	public static void init() {
		// Logger.init();
		Drive.init();
		// Winch.init();
		// Shooter.init();
		// Intake.init();
		// GearMechanism.init();
		Time.init();
		leftDriveEncoderFront.reset();
		leftDriveEncoderBack.reset();
		rightDriveEncoderFront.reset();
		rightDriveEncoderBack.reset();

		Robot.frontLeftDriveMotor.EnableCurrentLimit(false);
		Robot.frontRightDriveMotor.EnableCurrentLimit(false);
		Robot.backLeftDriveMotor.EnableCurrentLimit(false);
		Robot.backRightDriveMotor.EnableCurrentLimit(false);
		Robot.leftShooterMotorA.EnableCurrentLimit(false);
		Robot.leftShooterMotorB.EnableCurrentLimit(false);
		Robot.rightShooterMotorA.EnableCurrentLimit(false);
		Robot.rightShooterMotorB.EnableCurrentLimit(false);
		Robot.leftLoaderMotor.EnableCurrentLimit(false);
		Robot.rightLoaderMotor.EnableCurrentLimit(false);
		Robot.intakeMotor.EnableCurrentLimit(false);
		Robot.winchMotor.EnableCurrentLimit(false);
		Robot.leftAzimuthMotor.EnableCurrentLimit(false);
		Robot.rightAzimuthMotor.EnableCurrentLimit(false);
	}
}
