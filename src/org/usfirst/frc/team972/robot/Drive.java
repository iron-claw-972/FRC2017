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
		Robot.frontLeftDriveMotor.setInverted(true);
		Robot.frontRightDriveMotor.setInverted(false);
		Robot.backLeftDriveMotor.setInverted(true);
		Robot.backRightDriveMotor.setInverted(false);

		// TODO: Robot starts on COAST for some reason.
		Robot.frontLeftDriveMotor.enableBrakeMode(true);
		Robot.frontRightDriveMotor.enableBrakeMode(true);
		Robot.backLeftDriveMotor.enableBrakeMode(true);
		Robot.backRightDriveMotor.enableBrakeMode(true);

		Robot.backLeftDriveMotor.changeControlMode(TalonControlMode.Follower);
		Robot.backRightDriveMotor.changeControlMode(TalonControlMode.Follower);

		Robot.backLeftDriveMotor.set(Constants.FRONT_LEFT_DRIVE_MOTOR_CAN_ID);
		Robot.backRightDriveMotor.set(Constants.FRONT_RIGHT_DRIVE_MOTOR_CAN_ID);
		
		stopDrive();
	}

	/**
	 * Drives robot using standard tank drive.
	 * 
	 * @param leftDriveSpeed
	 *            speed of left wheels
	 * @param rightDriveSpeed
	 *            speed of right wheels
	 */
	public static void tankDrive(double leftSpeed, double rightSpeed) {
		leftDriveSpeed = leftSpeed;
		rightDriveSpeed = rightSpeed;
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
	public static void inverseDrive(double leftSpeed, double rightSpeed) {
		leftDriveSpeed = -leftSpeed;
		rightDriveSpeed = -rightSpeed;
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
		double curr_v = Math.pow((Math.pow(MotionProfiling.getV_X(), 2) + Math.pow(MotionProfiling.getV_Y(), 2)), 0.5);
		double curr_theta = MotionProfiling.getTheta();
		
		double v_error = 0.0;
		double theta_error = 0.0;
		
		boolean done = false;
		
		setBrakeMode(false); //testing out coast?
		
		// distance from robot to desired point using Pythagorean theorem
		double distance = Math.pow((Math.pow((x_desired - curr_x), 2) + Math.pow((y_desired - curr_y), 2)), 0.5);
		if (distance > 0.12) { //maybe more
			//get trajectory angle from -pi to pi but with 0 on the x axis
			double trajectory_angle = 0.0;
			if (x_desired >= curr_x && y_desired >= curr_y) {
				 trajectory_angle = Math.abs(Math.atan((y_desired - curr_y) / (x_desired - curr_x)));
			} else if (x_desired <= curr_x && y_desired >= curr_y) {
				 trajectory_angle = Math.PI - Math.abs(Math.atan((y_desired - curr_y) / (x_desired - curr_x)));
			} else if (x_desired >= curr_x && y_desired <= curr_y) {
				trajectory_angle = - Math.abs(Math.atan((y_desired - curr_y) / (x_desired - curr_x)));
			} else if (x_desired <= curr_x && y_desired <= curr_y) {
				trajectory_angle = - Math.PI + Math.abs(Math.atan((y_desired - curr_y) / (x_desired - curr_x)));
			}
			
			//convert angle to degrees in the -180 to 180 scheme used everywhere
			if (trajectory_angle > Math.PI / 2) {
				trajectory_angle = (180 * trajectory_angle / Math.PI) - 90;
			} else if (trajectory_angle <  - Math.PI / 2) {
				trajectory_angle = - 270 - (180 * trajectory_angle / Math.PI);
			} else {
				trajectory_angle = 90 - (180 * trajectory_angle / Math.PI);
			}
			SmartDashboard.putNumber("traj_angle", trajectory_angle);
			SmartDashboard.putNumber("dist", distance);
			
			theta_error = - trajectory_angle + curr_theta;
			if (trajectory_angle > 90 && curr_theta < -90) {
				theta_error = theta_error + 360; 
			} else if (trajectory_angle < -90 && curr_theta > 90) {
				theta_error = theta_error - 360;
			}
			
			boolean isReverseDrive = false;
			if (theta_error > 90.0) {
				isReverseDrive = true;
				theta_error = 180 - theta_error;
			} else if (theta_error < -90.0) {
				isReverseDrive = true;
				theta_error = -theta_error - 180;
			}
			
			v_error = Constants.ROBOT_MAX_VELOCITY - curr_v;
			if (distance < Constants.AUTON_STOPPING_DISTANCE_2) {
				v_error = ((Constants.AUTON_STOPPING_DISTANCE_2 - distance) *
						((Constants.ROBOT_MAX_VELOCITY * Constants.AUTON_VELOCITY_STOPPING_PROPORTION) / Constants.AUTON_STOPPING_DISTANCE_2)) - curr_v;
			} else if (distance < Constants.AUTON_STOPPING_DISTANCE_1) {
				v_error = ((Constants.AUTON_STOPPING_DISTANCE_1 - distance) * 
						(((1 - Constants.AUTON_VELOCITY_STOPPING_PROPORTION) * Constants.ROBOT_MAX_VELOCITY) / 
								(Constants.AUTON_STOPPING_DISTANCE_1 - Constants.AUTON_STOPPING_DISTANCE_2))) - curr_v;
			}
			
			double dVdT = 0.0;
			if (prev_v_error != 0.0) {
				dVdT = (v_error - prev_v_error) / dT;
			}
			double dThetadT = 0.0;
			if (prev_theta_error != 0.0) {
				dThetadT = (theta_error - prev_theta_error) / dT;
			}
			
			double power_for_velocity = (Constants.AUTON_DRIVE_RATIO - Constants.AUTON_DRIVE_F) * ((Constants.AUTON_DRIVE_VP * v_error) - (Constants.AUTON_DRIVE_VD * dVdT));
			double power_for_turning = (1 - Constants.AUTON_DRIVE_RATIO) * ((Constants.AUTON_DRIVE_AP * theta_error) - 
					(Constants.AUTON_DRIVE_AD * dThetadT));
			
			double leftDriveInput = power_for_velocity + power_for_turning + Constants.AUTON_DRIVE_F;
			double rightDriveInput = power_for_velocity - power_for_turning + Constants.AUTON_DRIVE_F;
			/*if (Math.abs(theta_error) > 40) {
				leftDriveInput = 0.75 * power_for_velocity + power_for_turning + Constants.AUTON_DRIVE_F;
				rightDriveInput = 0.75 * power_for_velocity - power_for_turning + Constants.AUTON_DRIVE_F;
			}*/
		
			if (isReverseDrive) {
				inverseDrive(leftDriveInput, rightDriveInput);
			} else {
				tankDrive(leftDriveInput, rightDriveInput);
			}
		} else {
			if (curr_v > 0.15) {
				tankDrive(curr_v / 15, curr_v / 15);
			} else {
				theta_error = - theta_desired + curr_theta;
				if (theta_desired > 90 && curr_theta < -90) {
					theta_error = theta_error + 360; 
				} else if (theta_desired < -90 && curr_theta > 90) {
					theta_error = theta_error - 360;
				}
				double dThetadT = 0.0;
				if (prev_theta_error != 0.0) {
						dThetadT = ( - theta_error + prev_theta_error) / dT;
				}
				double turn_power = (Constants.AUTON_DRIVE_TURNP * theta_error) - (Constants.AUTON_DRIVE_TURND * dThetadT);
				
				double leftDriveInput = turn_power;
				double rightDriveInput = - turn_power;
				if (turn_power > 0) {
					leftDriveInput = leftDriveInput + Constants.AUTON_DRIVE_TURNF;
					rightDriveInput = rightDriveInput - Constants.AUTON_DRIVE_TURNF;
				} else {
					leftDriveInput = leftDriveInput - Constants.AUTON_DRIVE_TURNF;
					rightDriveInput = rightDriveInput + Constants.AUTON_DRIVE_TURNF;
				}
				
				if (Math.abs(theta_error) < 7 && Math.abs(dThetadT) < 2) {
					done = true;
					prev_v_error = 0.0;
					prev_theta_error = 0.0;
				} else {			
					tankDrive(leftDriveInput, rightDriveInput);
				}
			}
		}
		
		SmartDashboard.putNumber("V Error", v_error);
		SmartDashboard.putNumber("Theta Error", theta_error);
		
		prev_v_error = v_error;
		prev_theta_error = theta_error;
		
		if (done) {
			stopDrive();
		}
		
		return done;
	}
	/*
	public static void boilerAutonAlign(boolean isBlueAlliance, double dT) {
		double curr_x = MotionProfiling.getX();
		double curr_y = MotionProfiling.getY();
		if (isBlueAlliance) {
			double abs_angle = Math.atan(curr_y / curr_x);
			
		}
	}
	*/
	public static void gearAutonAlign(double dT) {
		Vision.startGearVision();
		boolean visionData = Vision.newData();
		boolean done = false;
		double gear_x;
		double gear_y;
		double gear_theta;
		if (visionData) {
			double distance = Vision.getDistance();
			double angle = Vision.getAngle();
			double data_time = Vision.getTime();
			double[] framePosition = Logger.readLog("Motion_Profiling_Data", data_time);
			if (framePosition.length == 3) {
				gear_x = Math.sin(angle) * distance + Math.sin(framePosition[2]) * (Constants.ROBOT_LENGTH / 2) + framePosition[0];
				gear_y = Math.cos(angle) * distance + Math.cos(framePosition[2]) * (Constants.ROBOT_LENGTH / 2) + framePosition[1];
				gear_theta = framePosition[2] + angle;
				if (framePosition[2] > 90 && angle > 90) {
					gear_theta = gear_theta - 360; 
				} else if (framePosition[2] < -90 && angle < -90) {
					gear_theta = gear_theta + 360;
				}
			} else {
				Logger.logError("Failed to determine the position of the robot from the logs.");
			}
			done = Drive.autonDrive(gear_x, gear_y - (Constants.ROBOT_LENGTH / 2), gear_theta, dT);
		}
	}
	
	public static void updateModel(double dT) {
		MotionProfiling.update(dT, IMU.getAngle(), IMU.getAccelX(), IMU.getAccelY(), Robot.leftDriveEncoderFront.get(), Robot.leftDriveEncoderBack.get(), Robot.rightDriveEncoderFront.get(), 
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
		SmartDashboard.putNumber("Left Encoder Front", Robot.leftDriveEncoderFront.get() * Constants.ROBOT_DRIVE_WHEEL_CIRCUMFERENCE / Constants.ENCODER_CLICKS_PER_ROTATION);
		SmartDashboard.putNumber("Right Encoder Front", Robot.rightDriveEncoderFront.get() * Constants.ROBOT_DRIVE_WHEEL_CIRCUMFERENCE / Constants.ENCODER_CLICKS_PER_ROTATION);
		SmartDashboard.putNumber("Left Encoder Back", Robot.leftDriveEncoderBack.get() * Constants.ROBOT_DRIVE_WHEEL_CIRCUMFERENCE / Constants.ENCODER_CLICKS_PER_ROTATION);
		SmartDashboard.putNumber("Right Encoder Back", Robot.rightDriveEncoderBack.get() * Constants.ROBOT_DRIVE_WHEEL_CIRCUMFERENCE / Constants.ENCODER_CLICKS_PER_ROTATION);
	}
}
