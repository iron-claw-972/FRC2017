package org.usfirst.frc.team972.robot;

import com.ctre.CANTalon.*;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive {

	static double leftDriveSpeed = 0;
	static double rightDriveSpeed = 0;

	static double prev_v_error = 0.0;
	static double prev_theta_error = 0.0;

	static boolean inverseDriveMode = false;
	static boolean inverseDriveButtonPressed = false;
	static boolean inverseDriveButtonLastPressed = false;

	static boolean brakeMode = false;
	static boolean brakeModeButtonPressed = false;
	static boolean brakeModeButtonLastPressed = false;

	/**
	 * Drive initiation sequence.
	 */
	public static void init() {
		Robot.frontLeftDriveMotor.setInverted(false);
		Robot.frontRightDriveMotor.setInverted(true);
		Robot.backLeftDriveMotor.setInverted(false);
		Robot.backRightDriveMotor.setInverted(true);

		// TODO: Robot starts on COAST for some reason.
		Robot.frontLeftDriveMotor.enableBrakeMode(true);
		Robot.frontRightDriveMotor.enableBrakeMode(true);
		Robot.backLeftDriveMotor.enableBrakeMode(true);
		Robot.backRightDriveMotor.enableBrakeMode(true);

		Robot.backLeftDriveMotor.changeControlMode(TalonControlMode.Follower);
		Robot.backRightDriveMotor.changeControlMode(TalonControlMode.Follower);

		Robot.backLeftDriveMotor.set(Constants.FRONT_LEFT_DRIVE_MOTOR_CAN_ID);
		Robot.backRightDriveMotor.set(Constants.FRONT_RIGHT_DRIVE_MOTOR_CAN_ID);
	}

	/**
	 * Drives robot using standard tank drive.
	 * 
	 * @param leftDriveSpeed
	 *            speed of left wheels
	 * @param rightDriveSpeed
	 *            speed of right wheels
	 */
	public static void tankDrive(double leftDriveSpeed, double rightDriveSpeed) {
		Drive.leftDriveSpeed = leftDriveSpeed;
		Drive.rightDriveSpeed = rightDriveSpeed;
		Robot.frontLeftDriveMotor.set(leftDriveSpeed);
		Robot.frontRightDriveMotor.set(rightDriveSpeed);
	}

	/**
	 * Drives robot using the original "back" as the new front.
	 * 
	 * @param leftDriveSpeed
	 *            speed of new left wheels
	 * @param rightDriveSpeed
	 *            speed of new right wheels
	 */
	public static void inverseDrive(double leftDriveSpeed, double rightDriveSpeed) {
		// Intentional, used for propery inverse drive
		Drive.leftDriveSpeed = -rightDriveSpeed;
		Drive.rightDriveSpeed = -leftDriveSpeed;
		tankDrive(leftDriveSpeed, rightDriveSpeed);
	}

	/**
	 * Stops the robot (no PID).
	 */
	public static void stopDrive() {
		leftDriveSpeed = 0;
		rightDriveSpeed = 0;
		tankDrive(leftDriveSpeed, rightDriveSpeed);
	}

	/**
	 * Switches between CANTalon brake and coast mode for all drive motors.
	 * 
	 * @param brakeStatus
	 *            should brake mode be enabled
	 */
	public static void setBrakeMode(boolean brakeStatus) {
		Robot.frontLeftDriveMotor.enableBrakeMode(brakeStatus);
		Robot.frontRightDriveMotor.enableBrakeMode(brakeStatus);
		Robot.backLeftDriveMotor.enableBrakeMode(brakeStatus);
		Robot.backRightDriveMotor.enableBrakeMode(brakeStatus);
	}

	/**
	 * Checks inverse drive mode toggle.
	 */
	public static void checkInverseToggle() {
		inverseDriveButtonPressed = Robot.leftJoystick.getRawButton(Constants.INVERSE_DRIVE_TOGGLE_BUTTON);
		if (inverseDriveButtonPressed && !inverseDriveButtonLastPressed) {
			inverseDriveMode = !inverseDriveMode;
		}
		inverseDriveButtonLastPressed = inverseDriveButtonPressed;
	}

	/**
	 * Checks brake vs coast drive mode toggle.
	 */
	public static void checkBrakeToggle() {
		brakeModeButtonPressed = Robot.rightJoystick.getRawButton(Constants.BRAKE_MODE_TOGGLE_BUTTON);
		if (brakeModeButtonPressed && !brakeModeButtonLastPressed) {
			brakeMode = !brakeMode;
		}
		brakeModeButtonLastPressed = brakeModeButtonPressed;

		setBrakeMode(brakeMode);
	}

	/**
	 * Drive control in teleop.
	 */
	public static void teleopDrive() {
		double leftDriveInput = Robot.leftJoystick.getY();
		double rightDriveInput = Robot.rightJoystick.getY();

		checkInverseToggle();
		checkBrakeToggle();

		if (Robot.leftJoystick.getRawButton(Constants.STOP_DRIVE_BUTTON)) {
			stopDrive();
		} else {
			if (Robot.leftJoystick.getRawButton(Constants.SQUARED_DRIVE_BUTTON)) {
				// Square inputs + keep +/- sign
				leftDriveInput = Math.abs(leftDriveInput) * leftDriveInput;
				rightDriveInput = Math.abs(rightDriveInput) * rightDriveInput;
			}

			if (inverseDriveMode) {
				inverseDrive(leftDriveInput, rightDriveInput);
			} else {
				tankDrive(leftDriveInput, rightDriveInput);
			}
		}
	}

	// @formatter:off
	public static boolean autonDrive(double x_desired, double y_desired, double theta_desired, double dT) {
		double curr_x = MotionProfiling.getX();
		double curr_y = MotionProfiling.getY();
		double curr_v = MotionProfiling.getV();
		double curr_theta = MotionProfiling.getTheta();
		
		double v_error = 0.0;
		double theta_error = 0.0;
		
		boolean done = false;
		
		setBrakeMode(true);
		
		// distance from robot to desired point using Pythagorean theorem
		double distance = Math.pow((Math.pow((x_desired - curr_x), 2) + Math.pow((y_desired - curr_y), 2)), 0.5);
		if (distance > 0.02) {
			// get desired angle of robot to get to in degrees using arctan from -180 to +180
			double trajectory_angle = Math.atan2((x_desired - curr_x), (y_desired - curr_y)) + (Math.PI / 2);
			if (trajectory_angle > Math.PI) {
				trajectory_angle = Math.toDegrees(trajectory_angle - (2 * Math.PI));
			} else if (trajectory_angle < -Math.PI) {
				trajectory_angle = Math.toDegrees((2 * Math.PI) + trajectory_angle);
			} else {
				trajectory_angle = Math.toDegrees(trajectory_angle);
			}
		
			// determine which direction the robot needs to turn
			boolean isReverseDrive = false;
			theta_error = trajectory_angle - curr_theta;
			if (theta_error > 90.0) {
				isReverseDrive = true;
				theta_error = theta_error - 180;
			} else if (theta_error < -90.0) {
				isReverseDrive = true;
				theta_error = 180 + theta_error;
			}
		
			if (isReverseDrive) {
				v_error = -Constants.ROBOT_MAX_VELOCITY - curr_v;
				if (distance < Constants.AUTON_STOPPING_DISTANCE_2) {
					v_error = - ((Constants.AUTON_STOPPING_DISTANCE_2 - distance) *
							((Constants.ROBOT_MAX_VELOCITY * Constants.AUTON_VELOCITY_STOPPING_PROPORTION) / Constants.AUTON_STOPPING_DISTANCE_2)) - curr_v;
				} else if (distance < Constants.AUTON_STOPPING_DISTANCE_1) {
					v_error = - ((Constants.AUTON_STOPPING_DISTANCE_1 - distance) * 
							(((1 - Constants.AUTON_VELOCITY_STOPPING_PROPORTION) * Constants.ROBOT_MAX_VELOCITY) / 
									(Constants.AUTON_STOPPING_DISTANCE_1 - Constants.AUTON_STOPPING_DISTANCE_2))) - curr_v;
				}
			} else {
				v_error = Constants.ROBOT_MAX_VELOCITY - curr_v;
				if (distance < Constants.AUTON_STOPPING_DISTANCE_2) {
					v_error = ((Constants.AUTON_STOPPING_DISTANCE_2 - distance) *
							((Constants.ROBOT_MAX_VELOCITY * Constants.AUTON_VELOCITY_STOPPING_PROPORTION) / Constants.AUTON_STOPPING_DISTANCE_2)) - curr_v;
				} else if (distance < Constants.AUTON_STOPPING_DISTANCE_1) {
					v_error = ((Constants.AUTON_STOPPING_DISTANCE_1 - distance) * 
							(((1 - Constants.AUTON_VELOCITY_STOPPING_PROPORTION) * Constants.ROBOT_MAX_VELOCITY) / 
									(Constants.AUTON_STOPPING_DISTANCE_1 - Constants.AUTON_STOPPING_DISTANCE_2))) - curr_v;
				}
			}
			
			double dVdT = 0.0;
			if (prev_v_error != 0.0) {
				dVdT = (v_error - prev_v_error) / dT;
			}
			double dThetadT = 0.0;
			if (prev_theta_error != 0.0) {
				dThetadT = (theta_error - prev_theta_error) / dT;
			}
			
			double power_for_velocity = Constants.AUTON_DRIVE_RATIO * ((Constants.AUTON_DRIVE_VP * v_error) - (Constants.AUTON_DRIVE_VD * dVdT));
			double power_for_turning = (1 - Constants.AUTON_DRIVE_RATIO) * (Constants.AUTON_DRIVE_AP * Math.abs(theta_error) * theta_error) - 
					(Constants.AUTON_DRIVE_AD * dThetadT);
		
			double leftDriveInput = power_for_velocity + power_for_turning;
			double rightDriveInput = power_for_velocity - power_for_turning;
		
			if (isReverseDrive) {
				inverseDrive(leftDriveInput, rightDriveInput);
			} else {
				tankDrive(leftDriveInput, rightDriveInput);
			}
		} else {
			theta_error = theta_desired - curr_theta;
			double dThetadT = 0.0;
			if (prev_theta_error != 0.0) {
				dThetadT = (theta_error - prev_theta_error) / dT;
			}
			double turn_power = (Constants.AUTON_DRIVE_TURNP * theta_error) - (Constants.AUTON_DRIVE_TURND * dThetadT);
			
			double leftDriveInput = turn_power;
			double rightDriveInput = turn_power;
			
			tankDrive(leftDriveInput, rightDriveInput);
			
			if (theta_error < 5) {
				done = true;
			}
		}
		
		prev_v_error = v_error;
		prev_theta_error = theta_error;
		
		if (done) {
			stopDrive();
		}
		
		return done;
	}
	
	public static void updateModel(double dT) {
		MotionProfiling.update(dT, IMU.getAngle(), Robot.leftDriveEncoderFront.get(), Robot.leftDriveEncoderBack.get(), Robot.rightDriveEncoderFront.get(), 
				Robot.rightDriveEncoderBack.get(), getAccel("left"), getAccel("right"));
	}

	/**
	 * Gets acceleration of a robot side.
	 * 
	 * @param robotSide
	 *            the side to get acceleration of ("left" or "right")
	 * @return acceleration of the side using system model
	 */
	public static double getAccel(String robotSide) {
		// TODO: make sure this is actually the right equation
		// TODO: magic numbers lel Andy
		if (robotSide == "left") {
			return 0.4448 * ((Robot.frontLeftDriveMotor.getOutputCurrent() + Robot.backLeftDriveMotor.getOutputCurrent()) / 2) * (1 - 0.0356 * LeftModel.v_k) / Constants.ROBOT_MASS;
		} else if (robotSide == "right") {
			return 0.4448 * ((Robot.frontRightDriveMotor.getOutputCurrent() + Robot.backRightDriveMotor.getOutputCurrent()) / 2) * (1 - 0.0356 * RightModel.v_k) / Constants.ROBOT_MASS;
		} else {
			return -9001.0;
		}
	}
	// @formatter:on

	/**
	 * Updates SmartDashboard values for Drive.
	 */
	public static void updateSmartDashboard() {
		SmartDashboard.putNumber("Left Drive Speed", leftDriveSpeed);
		SmartDashboard.putNumber("Right Drive Speed", rightDriveSpeed);
		SmartDashboard.putNumber("Left Encoder Front", Robot.leftDriveEncoderFront.get());
		SmartDashboard.putNumber("Right Encoder Front", Robot.rightDriveEncoderFront.get());
		SmartDashboard.putNumber("Left Encoder Back", Robot.leftDriveEncoderBack.get());
		SmartDashboard.putNumber("Right Encoder Back", Robot.rightDriveEncoderBack.get());
	}
}
