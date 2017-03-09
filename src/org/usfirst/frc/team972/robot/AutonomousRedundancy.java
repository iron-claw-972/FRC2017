package org.usfirst.frc.team972.robot;

import java.util.LinkedList;
import java.util.Queue;

import com.ctre.CANTalon.TalonControlMode;

public class AutonomousRedundancy {
	
	public static Queue<Task> queue = new LinkedList<Task>();
	public static boolean ready = true;
	
	public static boolean driveSimple(double distance, double duration) { //Allows the robot to drive foward x meters
		queue.add(new Task(0, distance, duration, 0));
		return false;
	}
	
	public static boolean driveSimpleAngle(double angle, double duration) { //Allows the robot to rotate to angle
		queue.add(new Task(1, 0, duration, angle));
		return false;
	}
	
	public static void driveLoop(){
		//Execute any driving tasks
		if (ready) {
			System.out.println(ready);
			if(queue.peek() != null){
				prepareMotors();
				AutoDriveTask.killTask();
				Task data = queue.poll();
				if(data.type == 0){
					AutoDriveTask.addTask(calculateMetersToClicks(data.distance), data.time, Robot.frontLeftDriveMotor);
					AutoDriveTask.addTask(calculateMetersToClicks(data.distance), data.time, Robot.frontRightDriveMotor);
				} else if(data.type == 1){
					//Angle = 0 to 180, 0 to -180
					if(data.angle > 0) { // To the right
						double r = calculateMetersToClicks(calculateAngleToCircum(data.angle));
						AutoDriveTask.addTask(r, data.time, Robot.frontLeftDriveMotor);
						AutoDriveTask.addTask(-r, data.time, Robot.frontRightDriveMotor);
					} else { // To the left
						double r = calculateMetersToClicks(calculateAngleToCircum(data.angle));
						AutoDriveTask.addTask(r, data.time, Robot.frontLeftDriveMotor);
						AutoDriveTask.addTask(-r, data.time, Robot.frontRightDriveMotor);
					}
				}
			}
			ready = false;
		}
		AutoDriveTask.runTask();
	}
	
	public static void prepareMotors() {
		Robot.frontLeftDriveMotor.setInverted(false);
		Robot.frontLeftDriveMotor.setPosition(0);
		Robot.frontLeftDriveMotor.changeControlMode(TalonControlMode.Position);
		Robot.frontLeftDriveMotor.set(0);
		
		Robot.frontRightDriveMotor.setInverted(false);
		Robot.frontRightDriveMotor.setPosition(0);
		Robot.frontRightDriveMotor.changeControlMode(TalonControlMode.Position);
		Robot.frontRightDriveMotor.set(0);
	}

	private static double calculateAngleToCircum(double angle) {
		return (angle/360) * (Math.PI*2) * Constants.ROBOT_WIDTH/2;		
	}
	private static double calculateMetersToClicks(double dist) {
		return dist * (Constants.ENCODER_CLICKS_PER_ROTATION/Constants.ROBOT_DRIVE_WHEEL_CIRCUMFERENCE);
	}
}
