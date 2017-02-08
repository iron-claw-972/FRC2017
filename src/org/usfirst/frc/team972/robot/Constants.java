package org.usfirst.frc.team972.robot;

public class Constants {
	/*
	 * All constants should be placed in this class. Constants should be named in all caps with underscores. All constants should be public, static, and final.
	 * 
	 * Example: public static final int FRONT_LEFT_DRIVE_MOTOR_CAN_ID = 1;
	 */

	// Left Joystick
	public static final int INVERSE_DRIVE_TOGGLE_BUTTON = 1;
	public static final int STOP_DRIVE_BUTTON = 4;

	// Right Joystick
	public static final int SQUARED_DRIVE_BUTTON = 4;
	public static final int BRAKE_MODE_TOGGLE_BUTTON = 1;

	// Operator Joystick
	public static final int WINCH_MOTOR_TOGGLE_BUTTON = 1;
	public static final int INTAKE_MOTOR_TOGGLE_BUTTON = 2;
	public static final int SHOOTER_FLYWHEEL_MOTOR_BUTTON = 3;

	// Motor IDs
	public static final int FRONT_LEFT_DRIVE_MOTOR_CAN_ID = 1;
	public static final int FRONT_RIGHT_DRIVE_MOTOR_CAN_ID = 2;
	public static final int BACK_LEFT_DRIVE_MOTOR_CAN_ID = 3;
	public static final int BACK_RIGHT_DRIVE_MOTOR_CAN_ID = 4;
	public static final int WINCH_MOTOR_CAN_ID = 5;
	public static final int LEFT_SHOOTER_MOTOR_A_CAN_ID = 6;
	public static final int LEFT_SHOOTER_MOTOR_B_CAN_ID = 7;
	public static final int RIGHT_SHOOTER_MOTOR_A_CAN_ID = 8;
	public static final int RIGHT_SHOOTER_MOTOR_B_CAN_ID = 9;
	public static final int LEFT_AZIMUTH_MOTOR_CAN_ID = 10;
	public static final int RIGHT_AZIMUTH_MOTOR_CAN_ID = 11;
	public static final int INTAKE_MOTOR_CAN_ID = 12;
	public static final int LEFT_LOADER_MOTOR_CAN_ID = 13;
	public static final int RIGHT_LOADER_MOTOR_CAN_ID = 14;

	// PWM IDs
	public static final int LEFT_HOOD_LINEAR_ACTUATOR_PWM_PORT = 0;
	public static final int RIGHT_HOOD_LINEAR_ACTUATOR_PWM_PORT = 1;

	// USB Ports
	public static final int LEFT_JOYSTICK_INPUT_USB_PORT = 0;
	public static final int RIGHT_JOYSTICK_INPUT_USB_PORT = 1;
	public static final int OPERATOR_JOYSTICK_INPUT_USB_PORT = 2;

	// DIO Ports
	public static final int LEFT_DRIVE_ENCODER_FRONT_PORT_A = 0;
	public static final int LEFT_DRIVE_ENCODER_FRONT_PORT_B = 1;
	public static final int LEFT_DRIVE_ENCODER_BACK_PORT_A = 2;
	public static final int LEFT_DRIVE_ENCODER_BACK_PORT_B = 3;
	public static final int RIGHT_DRIVE_ENCODER_FRONT_PORT_A = 4;
	public static final int RIGHT_DRIVE_ENCODER_FRONT_PORT_B = 5;
	public static final int RIGHT_DRIVE_ENCODER_BACK_PORT_A = 6;
	public static final int RIGHT_DRIVE_ENCODER_BACK_PORT_B = 7;

	// PCM Ports
	// TODO: Change to the port of the PCM
	public static final int COMPRESSOR_PCM_PORT = 0;
	public static final int GEAR_PEG_PISTON_FORWARD_PCM_PORT = 1;
	public static final int GEAR_PEG_PISTON_REVERSE_PCM_PORT = 2;
	public static final int GEAR_PUSHER_PISTON_FORWARD_PCM_PORT = 3;
	public static final int GEAR_PUSHER_PISTON_REVERSE_PCM_PORT = 4;

	// Robot Information
	public static final double ROBOT_WIDTH = 1; // TODO: determine all these
	public static final double ROBOT_LENGTH = 1;
	public static final double ROBOT_MAX_VELOCITY = 5; // m/s
	public static final double ROBOT_DRIVE_WHEEL_CIRCUMFERENCE = 0.317; // meters (diameter 0.101 meters)
	public static final double ENCODER_CLICKS_PER_ROTATION = 2048; // TODO: make sure this is right
	public static final double ROBOT_MASS = 1; // TODO: calculate (in kg)

	// System Model
	public static final double ALPHA = 0.5; // TODO: determine (all between 0 and 1)
	public static final double BETA = 0.5;
	public static final double PHI = 0.5;

	// Auton Drive
	public static final double AUTON_DRIVE_RATIO = 0.8;
	// TODO: determine (needs to be between 0 and 1)
	public static final double AUTON_STOPPING_DISTANCE_1 = 1.0;
	// distance in meters to start stopping (sharp deceleration)
	public static final double AUTON_STOPPING_DISTANCE_2 = 0.3;
	// distance in meters to finish stopping (lower deceleration) (less than distance 1)
	public static final double AUTON_VELOCITY_STOPPING_PROPORTION = 0.33;
	// proportion of max velocity city that should be reached after first stopping
	public static final double AUTON_DRIVE_VP = 0.0;
	// proportion of velocity error //TODO determine all these
	public static final double AUTON_DRIVE_VD = 0.0;
	// proportion of acceleration (should be small)
	public static final double AUTON_DRIVE_AP = 0.0;
	// proportion of angle error
	public static final double AUTON_DRIVE_AD = 0.0;
	// proportion of angle change (should be small)

	// Test Switches
	public static final boolean USE_LEFT_SHOOTER = true;
	public static final boolean USE_RIGHT_SHOOTER = false; // TODO: Revert

	// Constants
	public static final int DIVISION_FACTOR = 10000;
	public static final double WINCH_MOTOR_SPEED = 0.7;
	public static final double INTAKE_MOTOR_SPEED = 0.5;
	public static final double SHOOTER_FLYWHEEL_MOTOR_SPEED = 10000.0;
}
