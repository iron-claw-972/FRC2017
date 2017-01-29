package org.usfirst.frc.team972.robot;

public class Constants {
	/*
	 * All constants should be placed in this class. Constants should be named
	 * in all caps with underscores. All constants should be public, static, and
	 * final.
	 * 
	 * Example: public static final int FRONT_LEFT_DRIVE_MOTOR_CAN_ID = 1;
	 */

	// Left Joystick
	public static final int INVERSE_DRIVE_TOGGLE_BUTTON = 1;
	public static final int STOP_DRIVE_BUTTON = 4;

	// Right Joystick
	public static final int SQUARED_DRIVE_BUTTON = 4;
	public static final int BRAKE_MODE_TOGGLE_BUTTON = 0;

	// Operator Joystick
	public static final int WINCH_MOTOR_TOGGLE_BUTTON = 1;
	public static final int INTAKE_MOTOR_TOGGLE_BUTTON = 2;

	// Motor IDs
	public static final int FRONT_LEFT_DRIVE_MOTOR_CAN_ID = 1;
	public static final int FRONT_RIGHT_DRIVE_MOTOR_CAN_ID = 2;
	public static final int CENTER_LEFT_DRIVE_MOTOR_CAN_ID = 3;
	public static final int CENTER_RIGHT_DRIVE_MOTOR_CAN_ID = 4;
	public static final int BACK_LEFT_DRIVE_MOTOR_CAN_ID = 5;
	public static final int BACK_RIGHT_DRIVE_MOTOR_CAN_ID = 6;
	public static final int WINCH_MOTOR_CAN_ID = 7;
	public static final int LEFT_SHOOTER_MOTOR_A_CAN_ID = 8;
	public static final int LEFT_SHOOTER_MOTOR_B_CAN_ID = 9;
	public static final int RIGHT_SHOOTER_MOTOR_A_CAN_ID = 10;
	public static final int RIGHT_SHOOTER_MOTOR_B_CAN_ID = 11;
	public static final int LEFT_AZIMUTH_MOTOR_CAN_ID = 12;
	public static final int RIGHT_AZIMUTH_MOTOR_CAN_ID = 13;
	public static final int INTAKE_MOTOR_CAN_ID = 14;
	public static final int LEFT_LOADER_MOTOR_CAN_ID = 15;
	public static final int RIGHT_LOADER_MOTOR_CAN_ID = 16;

	// PWM IDs
	public static final int LEFT_HOOD_LINEAR_ACTUATOR_PWM_PORT = 0;
	public static final int RIGHT_HOOD_LINEAR_ACTUATOR_PWM_PORT = 1;

	// USB Ports
	public static final int LEFT_JOYSTICK_INPUT_USB_PORT = 0;
	public static final int RIGHT_JOYSTICK_INPUT_USB_PORT = 1;
	public static final int OPERATOR_JOYSTICK_INPUT_USB_PORT = 2;

	// DIO Ports
	public static final int LEFT_DRIVE_ENCODER_A_PORT_A = 0;
	public static final int LEFT_DRIVE_ENCODER_A_PORT_B = 1;
	public static final int LEFT_DRIVE_ENCODER_B_PORT_A = 2;
	public static final int LEFT_DRIVE_ENCODER_B_PORT_B = 3;
	public static final int RIGHT_DRIVE_ENCODER_A_PORT_A = 4;
	public static final int RIGHT_DRIVE_ENCODER_A_PORT_B = 5;
	public static final int RIGHT_DRIVE_ENCODER_B_PORT_A = 6;
	public static final int RIGHT_DRIVE_ENCODER_B_PORT_B = 7;

	// PCM Ports
	public static final int COMPRESSOR_PCM_PORT = 0; // TODO: Change to the port of the PCM
	public static final int GEAR_PEG_PISTON_FORWARD_PCM_PORT = 1;
	public static final int GEAR_PEG_PISTON_REVERSE_PCM_PORT = 2;
	public static final int GEAR_PUSHER_PISTON_FORWARD_PCM_PORT = 3;
	public static final int GEAR_PUSHER_PISTON_REVERSE_PCM_PORT = 4;
	
	// PID Constants
	public static final int LEFT_SHOOTER_FLYWHEEL_TOLERANCE = 5;
	public static final int RIGHT_SHOOTER_FLYWHEEL_TOLERANCE = 5;
	public static final int LEFT_SHOOTER_FLYWHEEL_SETPOINT = 500;
	public static final int RIGHT_SHOOTER_FLYWHEEL_SETPOINT = 500;

	// Constants
	public static final double WINCH_MOTOR_SPEED = 0.7;
	public static final double INTAKE_MOTOR_SPEED = 0.5;

}
