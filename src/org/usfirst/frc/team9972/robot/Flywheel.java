package org.usfirst.frc.team9972.robot;

import edu.wpi.first.wpilibj.*;

public class Flywheel {
//	double kP = 0, kI = 0, kD = 0;
//	double dP = 0.001, dI = 0.001, dD = 0.001;
//	boolean lastP = false, lastI = false, lastD = false;
//	
//	long started = -1;
//	boolean runningPID = false;
//	int counter = 0;
//	double runningDeviationSum = 0;
//	
//	public void robotInit() {
//		pidController.setAbsoluteTolerance(5);
//		encoder.setPIDSourceType(PIDSourceType.kRate);
//	}
//	
//	public void teleopInit() {
//		pidController.setSetpoint(setpoint);
//		encoder.reset();
//	}
//	
//	public void teleopPeriodic() {
//		updatePID();
//		
//		if (opJoy.getRawButton(1) && started == -1) {
//			started = System.currentTimeMillis();
//			runningPID = true;
//			counter = 0;
//		} else if (opJoy.getRawButton(2)) {
//			pidController.disable(); 
//			runningPID = false;
//			started= -1;
//			motor.set(opJoy.getY());
//		} else if (!runningPID) {
//			motor.set(0);
//		}
//		
//		if (started != -1 && System.currentTimeMillis() - started > 30000) {
//			started = -1;
//			runningPID = false;
//			pidController.disable();
//			motor.set(0);
//			
//			runningDeviationSum = Math.sqrt(runningDeviationSum/counter);
//			SmartDashboard.putNumber("Standard Deviation", runningDeviationSum);
//		} 
//		
//		if (runningPID) {
//			pidController.enable();
//			
//			runningDeviationSum += Math.pow((encoder.pidGet()-pidController.getSetpoint()), 2);
//			counter++;
//		}
//		
//		SmartDashboard.putNumber("Current Rate", encoder.getRate());
//		SmartDashboard.putNumber("Current Value", encoder.pidGet());
//		SmartDashboard.putNumber("Setpoint", pidController.getSetpoint());
//		SmartDashboard.putNumber("Error", pidController.getError());
//		SmartDashboard.putBoolean("On Target", pidController.onTarget());
//		SmartDashboard.putBoolean("Running PID", runningPID);
//	}
//	
//	public void disabledPeriodic() {
//		pidController.disable();
//		motor.set(0);
//	}
//	
//	public void updatePID() {
//		getPIDFromJoystick();
//		
//		SmartDashboard.putNumber("P", kP);
//		SmartDashboard.putNumber("I", kI);
//		SmartDashboard.putNumber("D", kD);
//		pidController.setPID(kP, kI, kD);
//	}
//	
//	public void getPIDFromJoystick() {
//		int p = leftJoy.getPOV();
//		int i = rightJoy.getPOV();
//		int d = opJoy.getPOV();
//		
//		if(p == 0 && !lastP) {
//			kP += dP;
//		} else if(p == 180 && !lastP) {
//			kP -= dP;
//		}
//		
//		if(i == 0 && !lastI) {
//			kI += dI;
//		} else if(i == 180 && !lastI) {
//			kI -= dI;
//		}
//		
//		if(d == 0 && !lastD) {
//			kD += dD;
//		} else if(d == 180 && !lastD) {
//			kD -= dD;
//		}
//		
//		lastP = p == 0 || p == 180;
//		lastI = i == 0 || i == 180;
//		lastD = d == 0 || d == 180;
//		
//		if(kP < 0) {
//			kP = 0;
//		}
//		
//		if(kI < 0) {
//			kI = 0;
//		}
//		
//		if(kD < 0) {
//			kD = 0;
//		}
//	}
}
