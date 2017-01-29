package org.usfirst.frc.team9972.robot;

public class Constants {
	/*
	 * All constants should be placed in this class.
	 * Constants should be named in all caps with underscores.
	 * All constants should be public, static, and final.
	 * 
	 * Example:
	 * public static final int FRONT_LEFT_DRIVE_MOTOR_CAN_ID = 1;
	 */
	
	// Left Joystick
	public static final int INVERSE_DRIVE_TOGGLE_BUTTON = 1;
	public static final int STOP_DRIVE_BUTTON = 4;
	
	// Right Joystick
	public static final int SQUARED_DRIVE_BUTTON = 4;
	public static final int BRAKE_MODE_TOGGLE_BUTTON = 0;
	
	// Operator Joystick
	public static final int WINCH_MOTOR_BUTTON = 1;
	
	// Motor IDs
	public static final int FRONT_LEFT_DRIVE_MOTOR_CAN_ID_PORT = 1;
	public static final int FRONT_RIGHT_DRIVE_MOTOR_CAN_ID_PORT = 2;
	public static final int BACK_LEFT_DRIVE_MOTOR_CAN_ID_PORT = 3;
	public static final int BACK_RIGHT_DRIVE_MOTOR_CAN_ID_PORT = 4;
	public static final int WINCH_MOTOR_CAN_ID_PORT = 0;
	public static final int LEFT_FLYWHEEL_MOTOR_CAN_ID_PORT = 0;
	public static final int RIGHT_FLYWHEEL_MOTOR_CAN_ID_PORT = 0;	
	public static final int INTAKE_MOTOR_CAN_ID_PORT = 0;	
	public static final int LEFT_LOADER_MOTOR_CAN_ID_PORT = 0;
	public static final int RIGHT_LOADER_MOTOR_CAN_ID_PORT = 0;
	
	// PWM IDs
	public static final int LEFT_HOOD_SERVO_PWM_PORT = 0;
	public static final int RIGHT_HOOD_SERVO_PWM_PORT = 0;
	public static final int LEFT_SWIVEL_SERVO_PWM_PORT = 0;
	public static final int RIGHT_SWIVEL_SERVO_PWM_PORT = 0;
	
	// Joystick IDs
	public static final int LEFT_JOYSTICK_INPUT_USB_PORT = 0;
	public static final int RIGHT_JOYSTICK_INPUT_USB_PORT = 1;
	public static final int OPERATOR_JOYSTICK_INPUT_USB_PORT = 2;
	
	// DIO Ports
	public static final int LEFT_DRIVE_ENCODER_PORT_A = 0;
	public static final int LEFT_DRIVE_ENCODER_PORT_B = 1;
	public static final int RIGHT_DRIVE_ENCODER_PORT_A = 2;
	public static final int RIGHT_DRIVE_ENCODER_PORT_B = 3;
	
	// Motor Speeds
	public static final double WINCH_MOTOR_SPEED = 0.7;
}
