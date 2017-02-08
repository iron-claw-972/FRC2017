package org.usfirst.frc.team972.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.*;
import com.ctre.CANTalon.TalonControlMode;

public class ShooterAlignment {

	static int kP = 0, kI = 0, kD = 0;
	static int dP = 100, dI = 10, dD = 10;
	static boolean lastP = false, lastI = false, lastD = false;
	static boolean pidRunning = false;
	// pidRunning = if you are running PID
	static boolean runPID = false;
	// runPID = if you are trying to run PID (from joystick inputs)

	public static void init() {
		Robot.leftAzimuthMotor.enableBrakeMode(true);
		Robot.rightAzimuthMotor.enableBrakeMode(true);
		
		kP = Constants.AZIMUTH_P;
		kI = Constants.AZIMUTH_I;
		kD = Constants.AZIMUTH_D;
	}

	public static void run() {
		if (Constants.CHANGE_AZIMUTH_PID_WITH_JOYSTICKS) {
			getPIDFromJoystick();
		}

		runPID = Robot.operatorJoystick.getRawButton(Constants.SHOOTER_AZIMUTH_MOTOR_BUTTON);

		// TODO: Add Azimuth calculations
		if (Constants.USE_LEFT_SHOOTER) {
			moveAzimuth(Robot.leftAzimuthMotor, Constants.SHOOTER_AZIMUTH_MOTOR_POSITION, runPID);
		}
		if (Constants.USE_RIGHT_SHOOTER) {
			moveAzimuth(Robot.rightAzimuthMotor, Constants.SHOOTER_AZIMUTH_MOTOR_POSITION, runPID);
		}

		printToDashboard();
	}

	public static void moveAzimuth(CANTalon motor, double position, boolean runPID) {
		motor.setP((double) kP / (double) Constants.PID_DIVISION_FACTOR);
		motor.setI((double) kI / (double) Constants.PID_DIVISION_FACTOR);
		motor.setD((double) kD / (double) Constants.PID_DIVISION_FACTOR);

		if (runPID) {
			motor.changeControlMode(TalonControlMode.Position);
			motor.set(position);
			pidRunning = true;
		} else {
			motor.changeControlMode(TalonControlMode.PercentVbus);
			motor.set(0);
			motor.clearIAccum();
			pidRunning = false;
		}
	}

	public static void printToDashboard() {
		SmartDashboard.putBoolean("Azimuth pidRunning", pidRunning);
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
		SmartDashboard.putNumber("Azimuth kP", kP);
		SmartDashboard.putNumber("Azimuth kI", kI);
		SmartDashboard.putNumber("Azimuth kD", kD);

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
