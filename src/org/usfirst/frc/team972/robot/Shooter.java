package org.usfirst.frc.team972.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.*;
import com.ctre.CANTalon.TalonControlMode;

public class Shooter {

	static double shooter_kP = 4.0E-7, shooter_kI = 4.5E-6, shooter_kD = 1.482E-4; // 3.38
	static double shooter_dP = 0.000000001, shooter_dI = 0.0000001, shooter_dD = 0.0000001;
	static boolean shooter_lastP = false, shooter_lastI = false, shooter_lastD = false, shooter_lastSetpoint = false;
	static int shooter_setpoint = 19250; // 18750 seems better-ish, 65000 max
	static int shooter_dSetpoint = 250;
	static double manualSpeed = 0;

	static int alignment_kP = 0, alignment_kI = 0, alignment_kD = 0; // kP 0.01 too violent, 0.004
																		// gud
	static int alignment_dP = 10, alignment_dI = 1, alignment_dD = 1;
	static boolean alignment_lastP = false, alignment_lastI = false, alignment_lastD = false;
	static boolean alignment_pidRunning = false;
	// pidRunning = if you are running PID
	static boolean alignment_runPID = false;
	// runPID = if you are trying to run PID (from joystick inputs)

	/**
	 * Shooter initiation sequence.
	 */
	public static void init() {
		if (Constants.USE_LEFT_SHOOTER) {
			Robot.leftShooterMotorA.enableBrakeMode(true);
			Robot.leftShooterMotorB.enableBrakeMode(true);

			Robot.leftShooterMotorA.changeControlMode(TalonControlMode.PercentVbus);
			Robot.leftShooterMotorB.changeControlMode(TalonControlMode.Follower);
			Robot.leftShooterMotorB.set(Robot.leftShooterMotorA.getDeviceID());

			Robot.leftShooterMotorA.setPIDSourceType(PIDSourceType.kRate);
			Robot.leftShooterMotorA.setPosition(0);

			Robot.leftShooterPID.setSetpoint(shooter_setpoint);

			Robot.leftAzimuthMotor.enableBrakeMode(true);
			Robot.leftAzimuthMotor.setPosition(0);
		}

		if (Constants.USE_RIGHT_SHOOTER) {
			Robot.rightShooterMotorA.enableBrakeMode(true);
			Robot.rightShooterMotorB.enableBrakeMode(true);

			Robot.rightShooterMotorA.changeControlMode(TalonControlMode.PercentVbus);
			Robot.rightShooterMotorB.changeControlMode(TalonControlMode.Follower);
			Robot.rightShooterMotorB.set(Robot.rightShooterMotorA.getDeviceID());

			Robot.rightShooterMotorA.setPIDSourceType(PIDSourceType.kRate);
			Robot.rightShooterMotorA.setPosition(0);

			Robot.rightShooterPID.setSetpoint(shooter_setpoint);

			Robot.rightAzimuthMotor.enableBrakeMode(true);
			Robot.rightAzimuthMotor.setPosition(0);
		}

		// shooter_kP = Constants.FLYWHEEL_P;
		// shooter_kI = Constants.FLYWHEEL_I;
		// shooter_kD = Constants.FLYWHEEL_D;

		// alignment_kP = Constants.AZIMUTH_P;
		// alignment_kI = Constants.AZIMUTH_I;
		// alignment_kD = Constants.AZIMUTH_D;

		SmartDashboard.putNumber("Left Shooter Target Time", 0);
	}

	/**
	 * Manages the shooter flywheels.
	 * 
	 * @see runShooter()
	 */
	public static void shoot() {
		if (Constants.CHANGE_FLYWHEEL_PID_WITH_JOYSTICKS) {
			updateShooterPIDValues();
		}
		
		if (Constants.USE_LEFT_SHOOTER) {
			runShooter(Robot.leftShooterMotorA, Robot.leftShooterMotorB);
		}
		if (Constants.USE_RIGHT_SHOOTER) {
			runShooter(Robot.rightShooterMotorA, Robot.rightShooterMotorB);
		}

		updateSmartDashboard();
	}

	public static void stopShooter() {
		if (Constants.USE_LEFT_SHOOTER) {
			Robot.leftShooterMotorA.changeControlMode(TalonControlMode.PercentVbus);
			Robot.leftShooterMotorA.set(0);

			Robot.leftShooterMotorB.changeControlMode(TalonControlMode.PercentVbus);
			Robot.leftShooterMotorB.set(0);
		}

		if (Constants.USE_RIGHT_SHOOTER) {
			Robot.rightShooterMotorA.changeControlMode(TalonControlMode.PercentVbus);
			Robot.rightShooterMotorA.set(0);

			Robot.rightShooterMotorB.changeControlMode(TalonControlMode.PercentVbus);
			Robot.rightShooterMotorB.set(0);
		}
	}

	public static void runShooter(CANTalon masterMotor, CANTalon slaveMotor) {
		manualSpeed = (-Robot.leftJoystick.getThrottle() + 1)/2;

		if (Robot.leftJoystick.getRawButton(1)) {
			Robot.leftShooterPID.setSetpoint(shooter_setpoint);
			masterMotor.changeControlMode(TalonControlMode.PercentVbus);
			slaveMotor.changeControlMode(TalonControlMode.Follower);
			slaveMotor.set(masterMotor.getDeviceID());
			SmartDashboard.putNumber("I Accum", masterMotor.GetIaccum());

			Robot.leftShooterPID.enable();
		} else {
//			masterMotor.ClearIaccum();
			Robot.leftShooterPID.reset();
//			masterMotor.clearIAccum();
			if (Robot.leftJoystick.getRawButton(3)) {
				masterMotor.changeControlMode(TalonControlMode.PercentVbus);
				slaveMotor.changeControlMode(TalonControlMode.Follower);
				slaveMotor.set(masterMotor.getDeviceID());
				masterMotor.set(manualSpeed);
			} else {
//				pidController.disable();
				//System.out.println("STOP EVERYTHING");
				masterMotor.changeControlMode(TalonControlMode.PercentVbus);
				masterMotor.set(0);
				slaveMotor.changeControlMode(TalonControlMode.PercentVbus);
				slaveMotor.set(0);
			}
		}
	}

	/**
	 * Changes kP, kI, and kD values using joystick POV values.
	 */
	public static void updateShooterPIDValues() {
		boolean pUp = Robot.leftJoystick.getRawButton(7);
		boolean pDown = Robot.leftJoystick.getRawButton(8);
		boolean iUp = Robot.leftJoystick.getRawButton(9);
		boolean iDown = Robot.leftJoystick.getRawButton(10);
		boolean dUp = Robot.leftJoystick.getRawButton(11);
		boolean shooter_dDown = Robot.leftJoystick.getRawButton(12);
		boolean setUp = Robot.leftJoystick.getRawButton(5);
		boolean setDown = Robot.leftJoystick.getRawButton(3);
		
		
		if (pUp && !shooter_lastP) {
			shooter_kP += shooter_dP;
		} else if (pDown && !shooter_lastP) {
			shooter_kP -= shooter_dP;
		}

		if (iUp && !shooter_lastI) {
			shooter_kI += shooter_dI;
		} else if (iDown && !shooter_lastI) {
			shooter_kI -= shooter_dI;
		}

		if (dUp && !shooter_lastD) {
			shooter_kD += shooter_dD;
		} else if (shooter_dDown && !shooter_lastD) {
			shooter_kD -= shooter_dD;
		}

		if (setUp && !shooter_lastSetpoint) {
			shooter_setpoint += shooter_dSetpoint;
		} else if (setDown && !shooter_lastSetpoint) {
			shooter_setpoint -= shooter_dSetpoint;
		}
		
		if (shooter_kP < 0) {
			shooter_kP = 0;
		}

		if (shooter_kI < 0) {
			shooter_kI = 0;
		}

		if (shooter_kD < 0) {
			shooter_kD = 0;
		}

		Robot.leftShooterPID.setPID(shooter_kP, shooter_kI, shooter_kD);

		shooter_lastP = pUp || pDown;
		shooter_lastI = iUp || iDown;
		shooter_lastD = dUp || shooter_dDown;
		shooter_lastSetpoint = setUp || setDown;
		
	}

	public static void align() {
		if (Constants.CHANGE_AZIMUTH_PID_WITH_JOYSTICKS) {
			getAlignmentPIDFromJoystick();
		}
		if (Constants.CHANGE_AZIMUTH_WITH_VISION) {
			// TODO: Add values d and thetaC from vision
			getAlignmentFromVision(0, 0);
		}

		alignment_runPID = Robot.operatorJoystick.getRawButton(Constants.SHOOTER_AZIMUTH_MOTOR_BUTTON);

		// TODO: Add Azimuth calculations
		// TODO: Add Hood calculations
		if (Constants.USE_LEFT_SHOOTER) {
			moveAzimuth(Robot.leftAzimuthMotor, Constants.SHOOTER_AZIMUTH_MOTOR_POSITION, alignment_runPID);
			// moveHood(Robot.leftHoodLinearActuator, Constants.SHOOTER_HOOD_POSITION);
		}

		/*
		 * if (Constants.USE_RIGHT_SHOOTER) { moveAzimuth(Robot.rightAzimuthMotor,
		 * Constants.SHOOTER_AZIMUTH_MOTOR_POSITION, alignment_runPID);
		 * moveHood(Robot.rightHoodLinearActuator, Constants.SHOOTER_HOOD_POSITION); }
		 */
		updateSmartDashboard();
	}

	public static void stopAlign() {
		Robot.leftAzimuthMotor.changeControlMode(TalonControlMode.PercentVbus);
		Robot.leftAzimuthMotor.set(0);

		Robot.rightAzimuthMotor.changeControlMode(TalonControlMode.PercentVbus);
		Robot.rightAzimuthMotor.set(0);
	}

	public static void moveAzimuth(CANTalon motor, double position, boolean runPID) {
		motor.setP((double) alignment_kP / (double) Constants.PID_DIVISION_FACTOR);
		motor.setI((double) alignment_kI / (double) Constants.PID_DIVISION_FACTOR);
		motor.setD((double) alignment_kD / (double) Constants.PID_DIVISION_FACTOR);
		// 0.004 for p and nothing else. It works well

		if (motor.getPosition() > 40000) {
			motor.setPosition(0);
		} else if (motor.getPosition() < -40000) {
			motor.setPosition(0);
		}
		/*
		 * untested - Error of -10000 encoder position found, this should allow the motor to only go
		 * one rotation so it can't accelerate too fast, effectively limiting the speed
		 */

		if (runPID) {
			motor.changeControlMode(TalonControlMode.Position);
			motor.set(position);
			alignment_pidRunning = true;
		} else if (Robot.operatorJoystick.getRawButton(1)) {
			motor.changeControlMode(TalonControlMode.PercentVbus);
			motor.set(Robot.operatorJoystick.getY());
			motor.clearIAccum();
			alignment_pidRunning = false;
		} else {
			motor.changeControlMode(TalonControlMode.PercentVbus);
			motor.set(0);
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

	public static void getAlignmentFromVision(double d, double theta) {
		double xL = -13.875; // Note: These values are from the CAD (x = distance between shooters,
								// y = distance between camera and shooters)
		double yL = -18.146;
		double xR = 13.875;
		double yR = -18.146;

		double xB = d * Math.sin(Math.toRadians(theta));
		double yB = d * Math.cos(Math.toRadians(theta));

		double leftShooterAngle = Math.atan((yB - yL) / (xB - xL));
		double rightShooterAngle = Math.atan((yB - yR) / (xB - xR));

		if (Robot.leftAzimuthMotor.getPosition() < leftShooterAngle) {
			moveAzimuth(Robot.leftAzimuthMotor, 0, true); // Note: find actual position
		}
		if (Robot.rightAzimuthMotor.getPosition() < rightShooterAngle) {
			moveAzimuth(Robot.rightAzimuthMotor, 0, true); // Note: find actual position
		}
	}

	public static double getAzimuthAngle(double theta_i) {

		theta_i = Math.toRadians(theta_i);

		double D = 4; // Distance between axles
		double L = 5; // Length of coupler bar
		double R_i = 0.5; // Input crank radius
		double R_o = 2.5; // Output crank radius

		System.out.println("D " + D);
		System.out.println("L " + L);
		System.out.println("R_i " + R_i);
		System.out.println("R_o " + R_o);

		double x_i = -D + (R_i * Math.cos(theta_i));
		double y_i = R_i * Math.sin(theta_i);

		System.out.println("x_i " + x_i);
		System.out.println("y_i " + y_i);

		double a = (Math.pow(x_i, 2) + Math.pow(y_i, 2) + Math.pow(R_o, 2) - Math.pow(L, 2)) / 2;

		System.out.println("a " + a);

		double A = Math.pow(x_i, 2) + Math.pow(y_i, 2);
		double B = -2 * a * x_i;
		double C = Math.pow(a, 2) - (Math.pow(R_o, 2) * Math.pow(y_i, 2));

		System.out.println("A " + A);
		System.out.println("B " + B);
		System.out.println("C " + C);

		double x_o_1, x_o_2;

		if (theta_i > Math.toRadians(0) && theta_i <= Math.toRadians(180)) {
			x_o_1 = (-B + Math.sqrt(Math.pow(B, 2) - (4 * A * C))) / (2 * A);
			x_o_2 = (-B - Math.sqrt(Math.pow(B, 2) - (4 * A * C))) / (2 * A);
		} else if (theta_i > Math.toRadians(180) && theta_i <= Math.toRadians(360)) {
			x_o_1 = (-B - Math.sqrt(Math.pow(B, 2) - (4 * A * C))) / (2 * A);
			x_o_2 = (-B + Math.sqrt(Math.pow(B, 2) - (4 * A * C))) / (2 * A);
		} else {
			System.out.println("ERROR!!!");
			x_o_1 = 0.0;
			x_o_2 = 0.0;
		}

		System.out.println("x_o_1 " + x_o_1);
		System.out.println("x_o_2 " + x_o_2);

		double y_o_1 = (a - (x_i * x_o_1)) / y_i;
		double y_o_2 = (a - (x_i * x_o_2)) / y_i;

		System.out.println("y_o_1 " + y_o_1);
		System.out.println("y_o_2 " + y_o_2);

		double theta_o_1 = Math.atan2(x_o_1, y_o_1);
		double theta_o_2 = Math.atan2(x_o_2, y_o_2);

		theta_o_1 = Math.toDegrees(theta_o_1);
		theta_o_2 = Math.toDegrees(theta_o_2);

		theta_o_1 = 90 - theta_o_1;
		theta_o_2 = 90 - theta_o_2;

		if (theta_o_2 > 90 && theta_o_2 < 270) {
			theta_o_2 = theta_o_2 - 360;
		}

		System.out.println("theta_o_1 " + theta_o_1);
		System.out.println("theta_o_2 " + theta_o_2);

		// TODO: Figure out which theta_o to use
		return theta_o_1;
	}

	/**
	 * Updates SmartDashboard values for Shooter.
	 */
	public static void updateSmartDashboard() {
		// @formatter:off
		
		// TODO: Use real target speed from CANTalon
		if (Constants.USE_LEFT_SHOOTER) {
			SmartDashboard.putNumber("kP", Robot.leftShooterPID.getP());
			SmartDashboard.putNumber("kI", Robot.leftShooterPID.getI());
			SmartDashboard.putNumber("kD", Robot.leftShooterPID.getD());
			SmartDashboard.putNumber("Setpoint", Robot.leftShooterPID.getSetpoint());
			SmartDashboard.putBoolean("PID Enabled", Robot.leftShooterPID.isEnabled());
			SmartDashboard.putNumber("Manual Speed", manualSpeed);
			SmartDashboard.putNumber("Error", Robot.leftShooterPID.getError());
			SmartDashboard.putNumber("Error", Robot.leftShooterPID.getError()); // 2nd for value, not graph

			SmartDashboard.putNumber("Voltage Master", Robot.leftShooterMotorA.getOutputVoltage());
			SmartDashboard.putNumber("Voltage Slave", Robot.leftShooterMotorB.getOutputVoltage());
			
			SmartDashboard.putNumber("Current Master", Robot.pdp.getCurrent(1));
			SmartDashboard.putNumber("Current Slave", Robot.pdp.getCurrent(15));
			
			SmartDashboard.putNumber("PID Get", Robot.leftShooterMotorA.pidGet());
			SmartDashboard.putNumber("Position", Robot.leftShooterMotorA.getPosition());
			SmartDashboard.putNumber("Speed", Robot.leftShooterMotorA.getSpeed());
			SmartDashboard.putNumber("Speed", Robot.leftShooterMotorA.getSpeed()); // 2nd for value, not graph
		}
//		if (Constants.USE_RIGHT_SHOOTER) {
//			SmartDashboard.putNumber("kP", Robot.leftShooterPID.getP());
//			SmartDashboard.putNumber("kI", Robot.leftShooterPID.getI());
//			SmartDashboard.putNumber("kD", Robot.leftShooterPID.getD());
//			SmartDashboard.putNumber("Setpoint", Robot.leftShooterPID.getSetpoint());
//			SmartDashboard.putBoolean("PID Enabled", Robot.leftShooterPID.isEnabled());
//			SmartDashboard.putNumber("Manual Speed", manualSpeed);
//			SmartDashboard.putNumber("Error", Robot.leftShooterPID.getError());
//			SmartDashboard.putNumber("Error", Robot.leftShooterPID.getError()); // 2nd for value, not graph
//
//			SmartDashboard.putNumber("Voltage Master", Robot.leftShooterMotorA.getOutputVoltage());
//			SmartDashboard.putNumber("Voltage Slave", Robot.leftShooterMotorB.getOutputVoltage());
//			
//			SmartDashboard.putNumber("Current Master", Robot.pdp.getCurrent(1));
//			SmartDashboard.putNumber("Current Slave", Robot.pdp.getCurrent(15));
//			
//			SmartDashboard.putNumber("PID Get", Robot.leftShooterMotorA.pidGet());
//			SmartDashboard.putNumber("Position", Robot.leftShooterMotorA.getPosition());
//			SmartDashboard.putNumber("Speed", Robot.leftShooterMotorA.getSpeed());
//			SmartDashboard.putNumber("Speed", Robot.leftShooterMotorA.getSpeed()); // 2nd for value, not graph
//		}
		
//		SmartDashboard.putBoolean("Azimuth pidRunning", alignment_pidRunning);
//		SmartDashboard.putNumber("Azimuth Target Position", Constants.SHOOTER_AZIMUTH_MOTOR_POSITION);
//		// TODO: Use real target position from CANTalon
//		
//		if (Constants.USE_LEFT_SHOOTER) {
//			SmartDashboard.putNumber("Azimuth Left Position", Robot.leftAzimuthMotor.getPosition());
//			SmartDashboard.putNumber("Azimuth Left Closed Loop Error", Robot.leftAzimuthMotor.getClosedLoopError());
//			SmartDashboard.putNumber("Azimuth Left P", Robot.leftAzimuthMotor.getP());
//			SmartDashboard.putNumber("Azimuth Left I", Robot.leftAzimuthMotor.getI());
//			SmartDashboard.putNumber("Azimuth Left D", Robot.leftAzimuthMotor.getD());
//			SmartDashboard.putNumber("Azimuth Left I Accum", Robot.leftAzimuthMotor.GetIaccum());
//		}
//		if (Constants.USE_RIGHT_SHOOTER) {
//			SmartDashboard.putNumber("Azimuth Right Position", Robot.rightAzimuthMotor.getPosition());
//			SmartDashboard.putNumber("Azimuth Right Closed Loop Error", Robot.rightAzimuthMotor.getClosedLoopError());
//			SmartDashboard.putNumber("Azimuth Right P", Robot.rightAzimuthMotor.getP());
//			SmartDashboard.putNumber("Azimuth Right I", Robot.rightAzimuthMotor.getI());
//			SmartDashboard.putNumber("Azimuth Right D", Robot.rightAzimuthMotor.getD());
//			SmartDashboard.putNumber("Azimuth Right I Accum", Robot.rightAzimuthMotor.GetIaccum());
//		}
//		
//		SmartDashboard.putNumber("Azimuth kP", alignment_kP);
//		SmartDashboard.putNumber("Azimuth kI", alignment_kI);
//		SmartDashboard.putNumber("Azimuth kD", alignment_kD);
//		// @formatter:on
	}
}
