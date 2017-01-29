package org.usfirst.frc.team972.robot;

import edu.wpi.first.wpilibj.*;
import com.ctre.*;

public class Robot extends IterativeRobot {

	static CANTalon frontLeftDriveMotor = new CANTalon(Constants.FRONT_LEFT_DRIVE_MOTOR_CAN_ID);
	static CANTalon frontRightDriveMotor = new CANTalon(Constants.FRONT_RIGHT_DRIVE_MOTOR_CAN_ID);
	static CANTalon centerLeftDriveMotor = new CANTalon(Constants.CENTER_LEFT_DRIVE_MOTOR_CAN_ID);
	static CANTalon centerRightDriveMotor = new CANTalon(Constants.CENTER_RIGHT_DRIVE_MOTOR_CAN_ID);
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

	static Encoder leftDriveEncoderA = new Encoder(Constants.LEFT_DRIVE_ENCODER_A_PORT_A,
			Constants.LEFT_DRIVE_ENCODER_A_PORT_B);
	static Encoder rightDriveEncoderA = new Encoder(Constants.RIGHT_DRIVE_ENCODER_A_PORT_A,
			Constants.RIGHT_DRIVE_ENCODER_A_PORT_B);
	static Encoder leftDriveEncoderB = new Encoder(Constants.LEFT_DRIVE_ENCODER_B_PORT_A,
			Constants.LEFT_DRIVE_ENCODER_B_PORT_B);
	static Encoder rightDriveEncoderB = new Encoder(Constants.RIGHT_DRIVE_ENCODER_B_PORT_A,
			Constants.RIGHT_DRIVE_ENCODER_B_PORT_B);

	// TODO: Deal with the PIDSource for Drive
	static PIDController leftDrivePID = new PIDController(0, 0, 0, leftDriveEncoderA, frontLeftDriveMotor);
	static PIDController rightDrivePID = new PIDController(0, 0, 0, rightDriveEncoderA, frontRightDriveMotor);
	static PIDController leftAzimuthPID = new PIDController(0, 0, 0, leftAzimuthMotor, leftAzimuthMotor);
	static PIDController rightAzimuthPID = new PIDController(0, 0, 0, rightAzimuthMotor, rightAzimuthMotor);
	static PIDController leftShooterPID = new PIDController(0, 0, 0, leftShooterMotorA, leftShooterMotorA);
	static PIDController rightShooterPID = new PIDController(0, 0, 0, rightShooterMotorA, rightShooterMotorA);

	public void robotInit() {
		Autonomous.createChooser();
		updateSmartDashboard();

		Drive.init();
		Winch.init();
		Shooter.init();
		ShooterAlignment.init();
		Intake.init();
	}

	/*
	 * The four primary functions below (autonomousInit(), autonomousPeriodic(),
	 * teleopInit(), and teleopPeriodic()) should not be altered. Code should be
	 * altered in its respective class (Autonomous or Teleop). This change will
	 * help shorten the Robot class and make it easier to navigate.
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

	public void disabledPeriodic() {

	}

	public static void updateSmartDashboard() {
		Autonomous.updateSmartDashboard();
		Teleop.updateSmartDashboard();
	}

}
