package org.usfirst.frc.team972.robot;

import edu.wpi.first.wpilibj.*;
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
			Constants.LEFT_DRIVE_ENCODER_FRONT_PORT_B);
	static Encoder rightDriveEncoderFront = new Encoder(Constants.RIGHT_DRIVE_ENCODER_FRONT_PORT_A,
			Constants.RIGHT_DRIVE_ENCODER_FRONT_PORT_B);
	static Encoder leftDriveEncoderBack = new Encoder(Constants.LEFT_DRIVE_ENCODER_BACK_PORT_A,
			Constants.LEFT_DRIVE_ENCODER_BACK_PORT_B);
	static Encoder rightDriveEncoderBack = new Encoder(Constants.RIGHT_DRIVE_ENCODER_BACK_PORT_A,
			Constants.RIGHT_DRIVE_ENCODER_BACK_PORT_B);

	static Compressor compressor = new Compressor(Constants.COMPRESSOR_PCM_PORT);
	static DoubleSolenoid gearPegPiston = new DoubleSolenoid(Constants.GEAR_PEG_PISTON_FORWARD_PCM_PORT,
			Constants.GEAR_PEG_PISTON_REVERSE_PCM_PORT);
	static DoubleSolenoid gearPusherPiston = new DoubleSolenoid(Constants.GEAR_PUSHER_PISTON_FORWARD_PCM_PORT,
			Constants.GEAR_PUSHER_PISTON_REVERSE_PCM_PORT);

	// TODO: Drive isn't going to use PID
	static PIDController leftAzimuthPID = new PIDController(0, 0, 0, leftAzimuthMotor, leftAzimuthMotor);
	static PIDController rightAzimuthPID = new PIDController(0, 0, 0, rightAzimuthMotor, rightAzimuthMotor);
	static PIDController leftShooterPID = new PIDController(0, 0, 0, leftShooterMotorA, leftShooterMotorA);
	static PIDController rightShooterPID = new PIDController(0, 0, 0, rightShooterMotorA, rightShooterMotorA);

	public void robotInit() {
		Autonomous.createChooser();
		Autonomous.updateSmartDashboard();
		Teleop.updateSmartDashboard();
		
		init();
	}

	/*
	 * The four primary functions below (autonomousInit(), autonomousPeriodic(),
	 * teleopInit(), and teleopPeriodic()) should not be altered. Code should be
	 * altered in its respective class (Autonomous or Teleop). This change will
	 * help shorten the Robot class and make it easier to navigate.
	 */

	public void autonomousInit() {
		Autonomous.init(this);
	}

	public void autonomousPeriodic() {
		Autonomous.periodic(this);
	}

	public void teleopInit() {
		Teleop.init(this);
	}

	public void teleopPeriodic() {
		Teleop.periodic(this);
	}

	public void disabledInit() {

	}
	
	public void testPeriodic() {
		Tester.run();
	}
  
	public void init() {
		Drive.init();
		Winch.init();
		Shooter.init();
		ShooterAlignment.init();
		Intake.init();
		GearMechanism.init();
		Time.init();
	}
}
