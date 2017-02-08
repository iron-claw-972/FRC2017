package org.usfirst.frc.team972.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.*;
import com.ctre.CANTalon.TalonControlMode;

public class Shooter {

	static int kP = 0, kI = 0, kD = 0;
	static int dP = 100, dI = 10, dD = 10;
	static boolean lastP = false, lastI = false, lastD = false;
	static boolean pidRunning = false;
	// pidRunning = if you are running PID
	static boolean runPID = false;
	// runPID = if you are trying to run PID (from joystick inputs)

	public static void init() {
		Robot.leftShooterMotorA.enableBrakeMode(true);
		Robot.leftShooterMotorB.enableBrakeMode(true);
		Robot.rightShooterMotorA.enableBrakeMode(true);
		Robot.rightShooterMotorB.enableBrakeMode(true);

		Robot.leftShooterMotorB.changeControlMode(TalonControlMode.Follower);
		Robot.rightShooterMotorB.changeControlMode(TalonControlMode.Follower);

		Robot.leftShooterMotorB.set(Constants.LEFT_SHOOTER_MOTOR_A_CAN_ID);
		Robot.rightShooterMotorB.set(Constants.RIGHT_SHOOTER_MOTOR_A_CAN_ID);
	}

	public static void run() {
		getPIDFromJoystick();

		runPID = Robot.operatorJoystick.getRawButton(Constants.SHOOTER_FLYWHEEL_MOTOR_BUTTON);

		if (Constants.USE_LEFT_SHOOTER) {
			runShooter(Robot.leftShooterMotorA, runPID);
		}
		if (Constants.USE_RIGHT_SHOOTER) {
			runShooter(Robot.rightShooterMotorA, runPID);
		}

		printToDashboard();
	}

	public static void runShooter(CANTalon motor, boolean runPID) {
		motor.setP((double) kP / (double) Constants.PID_DIVISION_FACTOR);
		motor.setI((double) kI / (double) Constants.PID_DIVISION_FACTOR);
		motor.setD((double) kD / (double) Constants.PID_DIVISION_FACTOR);

		if (runPID) {
			motor.changeControlMode(TalonControlMode.Speed);
			motor.set(Constants.SHOOTER_FLYWHEEL_MOTOR_SPEED);
			pidRunning = true;
		} else {
			motor.changeControlMode(TalonControlMode.PercentVbus);
			motor.set(0);
			motor.clearIAccum();
			pidRunning = false;
		}
	}

	public static void printToDashboard() {
		SmartDashboard.putBoolean("pidRunning", pidRunning);
		SmartDashboard.putNumber("Target Speed", Constants.SHOOTER_FLYWHEEL_MOTOR_SPEED);
		// TODO: Use real target speed from CANTalon
		if (Constants.USE_LEFT_SHOOTER) {
			SmartDashboard.putNumber("Left Speed", Robot.leftShooterMotorA.getSpeed());
			SmartDashboard.putNumber("Left Closed Loop Error", Robot.leftShooterMotorA.getClosedLoopError());
			SmartDashboard.putNumber("Left P", Robot.leftShooterMotorA.getP());
			SmartDashboard.putNumber("Left I", Robot.leftShooterMotorA.getI());
			SmartDashboard.putNumber("Left D", Robot.leftShooterMotorA.getD());
			SmartDashboard.putNumber("Left I Accum", Robot.leftShooterMotorA.GetIaccum());
		}
		if (Constants.USE_RIGHT_SHOOTER) {
			SmartDashboard.putNumber("Right Speed", Robot.leftShooterMotorA.getSpeed());
			SmartDashboard.putNumber("Right Closed Loop Error", Robot.leftShooterMotorA.getClosedLoopError());
			SmartDashboard.putNumber("Right P", Robot.rightShooterMotorA.getP());
			SmartDashboard.putNumber("Right I", Robot.rightShooterMotorA.getI());
			SmartDashboard.putNumber("Right D", Robot.rightShooterMotorA.getD());
			SmartDashboard.putNumber("Right I Accum", Robot.rightShooterMotorA.GetIaccum());
		}
		SmartDashboard.putNumber("kP", kP);
		SmartDashboard.putNumber("kI", kI);
		SmartDashboard.putNumber("kD", kD);

	}

	public static void getPIDFromJoystick() {
		int leftJoystickPInput = Robot.leftJoystick.getPOV();
		int rightJoystickIInput = Robot.rightJoystick.getPOV();
		int operatorJoystickDInput = Robot.operatorJoystick.getPOV();

		if (leftJoystickPInput == 0 && !lastP) {
			kP += dP;
		} else if (leftJoystickPInput == 180 && !lastP) {
			kP -= dP;
		}

		if (rightJoystickIInput == 0 && !lastI) {
			kI += dI;
		} else if (rightJoystickIInput == 180 && !lastI) {
			kI -= dI;
		}

		if (operatorJoystickDInput == 0 && !lastD) {
			kD += dD;
		} else if (operatorJoystickDInput == 180 && !lastD) {
			kD -= dD;
		}

		lastP = leftJoystickPInput == 0 || leftJoystickPInput == 180;
		lastI = rightJoystickIInput == 0 || rightJoystickIInput == 180;
		lastD = operatorJoystickDInput == 0 || operatorJoystickDInput == 180;

		if (kP < 0) {
			kP = 0;
		}

		if (kI < 0) {
			kI = 0;
		}

		if (kD < 0) {
			kD = 0;
		}
	}
}
