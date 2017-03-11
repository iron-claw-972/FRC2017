package org.usfirst.frc.team972.robot;

public class Constants {
	/*
	 * All constants should be placed in this class. Constants should be named in all caps with underscores. All constants should be public, static, and final.
	 * 
	 * Example: public static final int FRONT_LEFT_DRIVE_MOTOR_CAN_ID = 1;
	 */

	// Left Joystick
	public static final int JOYSTICK_INVERSE_DRIVE_TOGGLE_BUTTON = 1;
	public static final int JOYSTICK_STOP_DRIVE_BUTTON = 4;


	// Right Joystick
	public static final int JOYSTICK_SQUARED_DRIVE_BUTTON = 4;
	public static final int JOYSTICK_BRAKE_MODE_TOGGLE_BUTTON = 1;
	
	// Gamepad Joystick
	public static final int GAMEPAD_INVERSE_DRIVE_TOGGLE_BUTTON = 5; // Left Top Button Thing
	public static final int GAMEPAD_STOP_DRIVE_BUTTON = 6; // Right Top Button Thing
	public static final int GAMEPAD_SQUARED_DRIVE_BUTTON = 2; // B Button
	public static final int GAMEPAD_BRAKE_MODE_TOGGLE_BUTTON = 1; // A Button
	public static final int GAMEPAD_GEAR_ALIGN_BUTTON = 3;

	// Operator Joystick
	public static final int WINCH_MOTOR_TOGGLE_BUTTON = 11;
	public static final int INTAKE_MOTOR_TOGGLE_BUTTON = 12;
	public static final int SHOOTER_FLYWHEEL_MOTOR_BUTTON = 1;
	public static final int SHOOTER_FLYWHEEL_MANUAL_OVERRIDE_BUTTON = 7;
	public static final int SHOOTER_AZIMUTH_MOTOR_BUTTON = 2;
	public static final int SHOOTER_AZIMUTH_MANUAL_OVERRIDE_BUTTON = 8;
	public static final int SHOOTER_HOOD_MANUAL_OVERRIDE_UP_BUTTON = 3;
	public static final int SHOOTER_HOOD_MANUAL_OVERRIDE_DOWN_BUTTON = 3;
	public static final int HOPPER_CAM_BUTTON = 5;


	public static final int PDP_CAN_ID = 20;
	 
	// Motor IDs
	public static final int FRONT_LEFT_DRIVE_MOTOR_CAN_ID = 1;
	public static final int FRONT_RIGHT_DRIVE_MOTOR_CAN_ID = 3;
	public static final int BACK_LEFT_DRIVE_MOTOR_CAN_ID = 2;
	public static final int BACK_RIGHT_DRIVE_MOTOR_CAN_ID = 4;
	public static final int WINCH_MOTOR_CAN_ID = 5;
	public static final int LEFT_SHOOTER_MOTOR_A_CAN_ID = 6; 
	public static final int LEFT_SHOOTER_MOTOR_B_CAN_ID = 7;
	public static final int RIGHT_SHOOTER_MOTOR_A_CAN_ID = 8; // NOT USING
	public static final int RIGHT_SHOOTER_MOTOR_B_CAN_ID = 9; // NOT USING
	public static final int LEFT_AZIMUTH_MOTOR_CAN_ID = 10; // was 15
	public static final int RIGHT_AZIMUTH_MOTOR_CAN_ID = 11; // NOT USING
	public static final int INTAKE_MOTOR_CAN_ID = 12;
	public static final int LEFT_LOADER_MOTOR_CAN_ID = 13;
	public static final int RIGHT_LOADER_MOTOR_CAN_ID = 14;

	// PWM IDs
	public static final int LEFT_HOOD_LINEAR_ACTUATOR_PWM_PORT = 0;
	public static final int RIGHT_HOOD_LINEAR_ACTUATOR_PWM_PORT = 1;

	// USB Ports
	public static final int LEFT_JOYSTICK_INPUT_USB_PORT = 1;
	public static final int RIGHT_JOYSTICK_INPUT_USB_PORT = 2;
	public static final int OPERATOR_JOYSTICK_INPUT_USB_PORT = 0;
	public static final int GAMEPAD_JOYSTICK_INPUT_USB_PORT = 1;

	// DIO Ports
	public static final int LEFT_DRIVE_ENCODER_FRONT_PORT_A = 0;
	public static final int LEFT_DRIVE_ENCODER_FRONT_PORT_B = 1;
	public static final int LEFT_DRIVE_ENCODER_BACK_PORT_A = 4;
	public static final int LEFT_DRIVE_ENCODER_BACK_PORT_B = 5;
	public static final int RIGHT_DRIVE_ENCODER_FRONT_PORT_A = 2;
	public static final int RIGHT_DRIVE_ENCODER_FRONT_PORT_B = 3;
	public static final int RIGHT_DRIVE_ENCODER_BACK_PORT_A = 6;
	public static final int RIGHT_DRIVE_ENCODER_BACK_PORT_B = 7;

	// PCM Ports
	// TODO: Change to the port of the PCM
	public static final int COMPRESSOR_PCM_PORT = 0;
	public static final int GEAR_PEG_PISTON_FORWARD_PCM_PORT = 1;
	public static final int GEAR_PEG_PISTON_REVERSE_PCM_PORT = 2;
	public static final int GEAR_PUSHER_PISTON_FORWARD_PCM_PORT = 3;
	public static final int GEAR_PUSHER_PISTON_REVERSE_PCM_PORT = 4;
	public static final int LOADER_DOOR_PISTON_FORWARD_PCM_PORT = 5;
	public static final int LOADER_DOOR_PISTON_REVERSE_PCM_PORT = 6;
	public static final int FIELD_HOPPER_PISTON_FOWARD_PCM_PORT = 7;
	public static final int FIELD_HOPPER_PISTON_REVERSE_PCM_PORT = 8;

	// Robot Information
	public static final double ROBOT_WIDTH = 0.751; // in meters
	public static final double ROBOT_LENGTH = 0.737; // in meters
	public static final double LENGTH_GEAR_PEG = 0.203; // in meters distance from the plane which includes the gear vision tape 
	public static final double ROBOT_MAX_VELOCITY = 3.5; // m/s
	public static final double ROBOT_DRIVE_WHEEL_CIRCUMFERENCE = 0.320; // meters (diameter 0.101 meters)
	public static final double ENCODER_CLICKS_PER_ROTATION = 2048;
	public static final double ROBOT_MASS = 45; // TODO: calculate (in kg) (current weight is not the full weight) (estimated that the robot is 135lbs = 61.235 with batteries and bumpers)

	// System Model
	public static final double ALPHA = 1.0; //TODO I think these are good but needs more testing
	public static final double BETA = 0.6;
	public static final double PHI = 0.98;
	public static final double SYSVEL = 0.6;
	public static final double SYSACC = 0.75;

	// Auton Drive
	public static final double AUTON_DRIVE_RATIO = 0.7; // TODO: determine (needs to be between 0 and 1)
	public static final double AUTON_STOPPING_DISTANCE_1 = 1.5; // distance in meters to start stopping (sharp deceleration)
	public static final double AUTON_STOPPING_DISTANCE_2 = 0.5; // distance in meters to finish stopping (lower deceleration) (less than distance 1)
	public static final double AUTON_VELOCITY_STOPPING_PROPORTION = 0.35; // proportion of max velocity city that should be reached after first stopping
	public static final double AUTON_DRIVE_VP = 0.08; // proportion of velocity error //TODO determine all these
	public static final double AUTON_DRIVE_VD = 0.016; // proportion of acceleration (should be small)
	public static final double AUTON_DRIVE_AP = 0.01; // proportion of angle error (during motion)
	public static final double AUTON_DRIVE_AD = 0.0005; // proportion of angle change (during motion) (should be small)
	public static final double AUTON_DRIVE_F = 0.13; //feed forward
	public static final double AUTON_DRIVE_TURNP = 0.005; // proportion of angle error (during motion)
	public static final double AUTON_DRIVE_TURND = 0.0012; // proportion of angle change (during motion) (should be small)
	public static final double AUTON_DRIVE_TURNF = 0.05; //feed forward

	// Test Switches
	public static final boolean CHANGE_FLYWHEEL_PID_WITH_JOYSTICKS = true;
	public static final boolean CHANGE_AZIMUTH_PID_WITH_JOYSTICKS = true;
	public static final boolean CHANGE_AZIMUTH_WITH_VISION = false; // TODO: change to true when vision is ready
	public static final boolean USE_LEFT_SHOOTER = true;
	public static final boolean USE_RIGHT_SHOOTER = false; // TODO: Revert

	// PID Values -- CURRENTLY NOT USED -- TODO
	public static final int FLYWHEEL_P = 600;
	public static final int FLYWHEEL_I = 150;
	public static final int FLYWHEEL_D = 5;
	public static final int AZIMUTH_P = 0;
	public static final int AZIMUTH_I = 0;
	public static final int AZIMUTH_D = 0;
	
	// Constants
	public static final String LOGGER_LOCATION = "home/admin/FRC2017Logs";
	public static final int PID_DIVISION_FACTOR = 10000;
	public static final int CURRENT_LIMIT = 200;
	public static final double WINCH_MOTOR_SPEED = 1.0;
	public static final double INTAKE_MOTOR_SPEED = 0.5;
	public static final double SHOOTER_FLYWHEEL_MOTOR_SPEED = 30000.0;
	public static final double SHOOTER_AZIMUTH_MOTOR_POSITION = 0.0;
	public static final double SHOOTER_HOOD_POSITION = 0.7;
	public static final double LOADER_MOTOR_SPEED = 0.5;
	public static final double SHOOTER_HOOD_SPEED = 0.2;
	
	// Do Nothing Auton defaults
	public static final double DO_NOTHING_AUTO_STARTX = 4.112; //maybe make this able to be edited by the driver? TODO check
	public static final double DO_NOTHING_AUTO_STARTY = 0.502;
	
	// Cross Baseline Auton defaults
	public static final double CROSS_BASELINE_AUTO_STARTX = 2.0; // TODO make Albert do math
	public static final double CROSS_BASELINE_AUTO_STARTY = 0.502;
	public static final double CROSS_BASELINE_AUTO_X = 2.0;
	public static final double CROSS_BASELINE_AUTO_Y = 3.0;
	public static final double CROSS_BASELINE_AUTO_THETA = 0.0;
	
	// Middle Gear Auton defaults
	public static final double MIDDLE_GEAR_AUTO_STARTX = 4.112;
	public static final double MIDDLE_GEAR_AUTO_STARTY = 0.502;
	public static final double MIDDLE_GEAR_AUTO_X = 4.112;
	public static final double MIDDLE_GEAR_AUTO_Y = 2.105;
	public static final double MIDDLE_GEAR_AUTO_THETA = 0.0;
	
	// Left Gear Auton defaults
	public static final double LEFT_GEAR_AUTO_STARTX = 1.489;
	public static final double LEFT_GEAR_AUTO_STARTY = 0.502;
	public static final double LEFT_GEAR_AUTO_X = 1.489;
	public static final double LEFT_GEAR_AUTO_Y = 1.774;
	public static final double LEFT_GEAR_AUTO_FINAL_X = 2.521;
	public static final double LEFT_GEAR_AUTO_FINAL_Y = 2.871;
	public static final double LEFT_GEAR_AUTO_THETA = 60.0;
	
	// Right Gear Auton defaults
	public static final double RIGHT_GEAR_AUTO_STARTX = 6.772;
	public static final double RIGHT_GEAR_AUTO_STARTY = 0.502;
	public static final double RIGHT_GEAR_AUTO_X = 6.772;
	public static final double RIGHT_GEAR_AUTO_Y = 1.774;
	public static final double RIGHT_GEAR_AUTO_FINAL_X = 5.702;
	public static final double RIGHT_GEAR_AUTO_FINAL_Y = 2.871;
	public static final double RIGHT_GEAR_AUTO_THETA = -60.0;
	
	public static final double IDEAL_SHOOTING_DISTANCE = 2.0; //TODO determine
}
