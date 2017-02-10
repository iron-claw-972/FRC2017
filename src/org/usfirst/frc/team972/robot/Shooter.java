package org.usfirst.frc.team972.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.*;
import com.ctre.CANTalon.TalonControlMode;

public class Shooter {

	/**
	 * PID values.
	 */
	static int shooter_kP = 0, shooter_kI = 0, shooter_kD = 0;
	/**
	 * How much to increase/decrease PID values using joystick POV.
	 */
	static int shooter_dP = 100, shooter_dI = 10, shooter_dD = 10;
	/**
	 * If P/I/D was just changed (used in updatePIDValues())
	 */
	static boolean shooter_lastP = false, shooter_lastI = false, shooter_lastD = false;
	/**
	 * If you are currently running PID.
	 */
	static boolean shooter_pidRunning = false;
	/**
	 * If you are trying to run PID (as determined by joystick inputs)
	 */
	static boolean shooter_runPID = false;
	
	static int alignment_kP = 0, alignment_kI = 0, alignment_kD = 0;
	static int alignment_dP = 100, alignment_dI = 10, alignment_dD = 10;
	static boolean alignment_lastP = false, alignment_lastI = false, alignment_lastD = false;
	static boolean alignment_pidRunning = false;
	// pidRunning = if you are running PID
	static boolean alignment_runPID = false;
	// runPID = if you are trying to run PID (from joystick inputs)

	/**
	 * Shooter initiation sequence.
	 */
	public static void init() {
		Robot.leftShooterMotorA.enableBrakeMode(true);
		Robot.leftShooterMotorB.enableBrakeMode(true);
		Robot.rightShooterMotorA.enableBrakeMode(true);
		Robot.rightShooterMotorB.enableBrakeMode(true);

		// TODO: Uncomment this
		// Robot.leftShooterMotorB.changeControlMode(TalonControlMode.Follower);
		// Robot.rightShooterMotorB.changeControlMode(TalonControlMode.Follower);
		//
		// Robot.leftShooterMotorB.set(Constants.LEFT_SHOOTER_MOTOR_A_CAN_ID);
		// Robot.rightShooterMotorB.set(Constants.RIGHT_SHOOTER_MOTOR_A_CAN_ID);

		shooter_kP = Constants.FLYWHEEL_P;
		shooter_kI = Constants.FLYWHEEL_I;
		shooter_kD = Constants.FLYWHEEL_D;
		
		Robot.leftAzimuthMotor.enableBrakeMode(true);
		Robot.rightAzimuthMotor.enableBrakeMode(true);
		
		alignment_kP = Constants.AZIMUTH_P;
		alignment_kI = Constants.AZIMUTH_I;
		alignment_kD = Constants.AZIMUTH_D;
	}

	/**
	 * Manages the shooter flywheels.
	 * 
	 * @see runShooter()
	 */
	public static void shoot() {
		if (Constants.CHANGE_FLYWHEEL_PID_WITH_JOYSTICKS) {
			updatePIDValues();
		}

		shooter_runPID = Robot.operatorJoystick.getRawButton(Constants.SHOOTER_FLYWHEEL_MOTOR_BUTTON);

		if (Constants.USE_LEFT_SHOOTER) {
			runShooter(Robot.leftShooterMotorA, shooter_runPID);
		}
		if (Constants.USE_RIGHT_SHOOTER) {
			runShooter(Robot.rightShooterMotorA, shooter_runPID);
		}

		updateSmartDashboard();
	}

	/**
	 * Powers a single shooter.
	 * 
	 * @param motor
	 *            shooter motor controller
	 * @param runPID
	 *            run PID on motor
	 */
	public static void runShooter(CANTalon motor, boolean runPID) {
		motor.setP((double) shooter_kP / (double) Constants.PID_DIVISION_FACTOR);
		motor.setI((double) shooter_kI / (double) Constants.PID_DIVISION_FACTOR);
		motor.setD((double) shooter_kD / (double) Constants.PID_DIVISION_FACTOR);

		if (runPID) {
			motor.changeControlMode(TalonControlMode.Speed);
			motor.set(Constants.SHOOTER_FLYWHEEL_MOTOR_SPEED);
			shooter_pidRunning = true;
		} else {
			motor.changeControlMode(TalonControlMode.PercentVbus);
			motor.set(Robot.operatorJoystick.getY());
			motor.clearIAccum();
			shooter_pidRunning = false;
		}
	}

	/**
	 * Updates SmartDashboard values for Shooter.
	 */
	public static void updateSmartDashboard() {
		// @formatter:off
		SmartDashboard.putBoolean("Flywheel pidRunning", shooter_pidRunning);
		SmartDashboard.putNumber("Flywheel Target Speed", Constants.SHOOTER_FLYWHEEL_MOTOR_SPEED);
		// TODO: Use real target speed from CANTalon
		if (Constants.USE_LEFT_SHOOTER) {
			SmartDashboard.putNumber("Flywheel Left Speed", Robot.leftShooterMotorA.getSpeed());
			SmartDashboard.putNumber("Flywheel Left Closed Loop Error", Robot.leftShooterMotorA.getClosedLoopError());
			SmartDashboard.putNumber("Flywheel Left P", Robot.leftShooterMotorA.getP());
			SmartDashboard.putNumber("Flywheel Left I", Robot.leftShooterMotorA.getI());
			SmartDashboard.putNumber("Flywheel Left D", Robot.leftShooterMotorA.getD());
			SmartDashboard.putNumber("Flywheel Left I Accum", Robot.leftShooterMotorA.GetIaccum());
		}
		if (Constants.USE_RIGHT_SHOOTER) {
			SmartDashboard.putNumber("Flywheel Right Speed", Robot.leftShooterMotorA.getSpeed());
			SmartDashboard.putNumber("Flywheel Right Closed Loop Error", Robot.leftShooterMotorA.getClosedLoopError());
			SmartDashboard.putNumber("Flywheel Right P", Robot.rightShooterMotorA.getP());
			SmartDashboard.putNumber("Flywheel Right I", Robot.rightShooterMotorA.getI());
			SmartDashboard.putNumber("Flywheel Right D", Robot.rightShooterMotorA.getD());
			SmartDashboard.putNumber("Flywheel Right I Accum", Robot.rightShooterMotorA.GetIaccum());
		}
		SmartDashboard.putNumber("Flywheel kP", shooter_kP);
		SmartDashboard.putNumber("Flywheel kI", shooter_kI);
		SmartDashboard.putNumber("Flywheel kD", shooter_kD);
		
		SmartDashboard.putBoolean("Azimuth pidRunning", alignment_pidRunning);
		SmartDashboard.putNumber("Azimuth Target Position", Constants.SHOOTER_AZIMUTH_MOTOR_POSITION);
		// TODO: Use real target position from CANTalon
		if (Constants.USE_LEFT_SHOOTER) {
			SmartDashboard.putNumber("Azimuth Left Position", Robot.leftAzimuthMotor.getPosition());
			SmartDashboard.putNumber("Azimuth Left Closed Loop Error", Robot.leftAzimuthMotor.getClosedLoopError());
			SmartDashboard.putNumber("Azimuth Left P", Robot.leftAzimuthMotor.getP());
			SmartDashboard.putNumber("Azimuth Left I", Robot.leftAzimuthMotor.getI());
			SmartDashboard.putNumber("Azimuth Left D", Robot.leftAzimuthMotor.getD());
			SmartDashboard.putNumber("Azimuth Left I Accum", Robot.leftAzimuthMotor.GetIaccum());
		}
		if (Constants.USE_RIGHT_SHOOTER) {
			SmartDashboard.putNumber("Azimuth Right Position", Robot.rightAzimuthMotor.getPosition());
			SmartDashboard.putNumber("Azimuth Right Closed Loop Error", Robot.rightAzimuthMotor.getClosedLoopError());
			SmartDashboard.putNumber("Azimuth Right P", Robot.rightAzimuthMotor.getP());
			SmartDashboard.putNumber("Azimuth Right I", Robot.rightAzimuthMotor.getI());
			SmartDashboard.putNumber("Azimuth Right D", Robot.rightAzimuthMotor.getD());
			SmartDashboard.putNumber("Azimuth Right I Accum", Robot.rightAzimuthMotor.GetIaccum());
		}
		SmartDashboard.putNumber("Azimuth kP", alignment_kP);
		SmartDashboard.putNumber("Azimuth kI", alignment_kI);
		SmartDashboard.putNumber("Azimuth kD", alignment_kD);
		// @formatter:on
	}

	/**
	 * Changes kP, kI, and kD values using joystick POV values.
	 */
	public static void updateShooterPIDValues() {
		int leftJoystickPInput = Robot.leftJoystick.getPOV();
		int rightJoystickIInput = Robot.rightJoystick.getPOV();
		int operatorJoystickDInput = Robot.operatorJoystick.getPOV();

		if (leftJoystickPInput == 0 && !shooter_lastP) {
			shooter_kP += shooter_dP;
		} else if (leftJoystickPInput == 180 && !shooter_lastP) {
			shooter_kP -= shooter_dP;
		}

		if (rightJoystickIInput == 0 && !shooter_lastI) {
			shooter_kI += shooter_dI;
		} else if (rightJoystickIInput == 180 && !shooter_lastI) {
			shooter_kI -= shooter_dI;
		}

		if (operatorJoystickDInput == 0 && !shooter_lastD) {
			shooter_kD += shooter_dD;
		} else if (operatorJoystickDInput == 180 && !shooter_lastD) {
			shooter_kD -= shooter_dD;
		}

		shooter_lastP = leftJoystickPInput == 0 || leftJoystickPInput == 180;
		shooter_lastI = rightJoystickIInput == 0 || rightJoystickIInput == 180;
		shooter_lastD = operatorJoystickDInput == 0 || operatorJoystickDInput == 180;

		if (shooter_kP < 0) {
			shooter_kP = 0;
		}

		if (shooter_kI < 0) {
			shooter_kI = 0;
		}

		if (shooter_kD < 0) {
			shooter_kD = 0;
		}
	}

	public static void align() {
		if (Constants.CHANGE_AZIMUTH_PID_WITH_JOYSTICKS) {
			getPIDFromJoystick();
		}

		alignment_runPID = Robot.operatorJoystick.getRawButton(Constants.SHOOTER_AZIMUTH_MOTOR_BUTTON);

		// TODO: Add Azimuth calculations
		// TODO: Add Hood calculations
		if (Constants.USE_LEFT_SHOOTER) {
			moveAzimuth(Robot.leftAzimuthMotor, Constants.SHOOTER_AZIMUTH_MOTOR_POSITION, alignment_runPID);
			moveHood(Robot.leftHoodLinearActuator, Constants.SHOOTER_HOOD_POSITION);
		}
		if (Constants.USE_RIGHT_SHOOTER) {
			moveAzimuth(Robot.rightAzimuthMotor, Constants.SHOOTER_AZIMUTH_MOTOR_POSITION, alignment_runPID);
			moveHood(Robot.rightHoodLinearActuator, Constants.SHOOTER_HOOD_POSITION);
		}
		
		updateSmartDashboard();
	}

	public static void moveAzimuth(CANTalon motor, double position, boolean runPID) {
		motor.setP((double) alignment_kP / (double) Constants.PID_DIVISION_FACTOR);
		motor.setI((double) alignment_kI / (double) Constants.PID_DIVISION_FACTOR);
		motor.setD((double) alignment_kD / (double) Constants.PID_DIVISION_FACTOR);

		if (runPID) {
			motor.changeControlMode(TalonControlMode.Position);
			motor.set(position);
			alignment_pidRunning = true;
		} else {
			motor.changeControlMode(TalonControlMode.PercentVbus);
			motor.set(Robot.operatorJoystick.getY());
			motor.clearIAccum();
			alignment_pidRunning = false;
		}
	}
	
	public static void moveHood(Servo linearActuator, double position) {
		linearActuator.set(position);
	}

	public static void getAlignmentPIDFromJoystick() {
		int leftJoystickPInput = Robot.leftJoystick.getPOV();
		int rightJoystickIInput = Robot.rightJoystick.getPOV();
		int operatorJoystickDInput = Robot.operatorJoystick.getPOV();

		if (leftJoystickPInput == 0 && !alignment_lastP) {
			alignment_kP += alignment_dP;
		} else if (leftJoystickPInput == 180 && !alignment_lastP) {
			alignment_kP -= alignment_dP;
		}

		if (rightJoystickIInput == 0 && !alignment_lastI) {
			alignment_kI += alignment_dI;
		} else if (rightJoystickIInput == 180 && !alignment_lastI) {
			alignment_kI -= alignment_dI;
		}

		if (operatorJoystickDInput == 0 && !alignment_lastD) {
			alignment_kD += alignment_dD;
		} else if (operatorJoystickDInput == 180 && !alignment_lastD) {
			alignment_kD -= alignment_dD;
		}

		alignment_lastP = leftJoystickPInput == 0 || leftJoystickPInput == 180;
		alignment_lastI = rightJoystickIInput == 0 || rightJoystickIInput == 180;
		alignment_lastD = operatorJoystickDInput == 0 || operatorJoystickDInput == 180;

		if (alignment_kP < 0) {
			alignment_kP = 0;
		}

		if (alignment_kI < 0) {
			alignment_kI = 0;
		}

		if (alignment_kD < 0) {
			alignment_kD = 0;
		}
	}
}
