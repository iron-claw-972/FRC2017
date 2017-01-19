package org.usfirst.frc.team972.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.*; 

public class Autonomous {
	
	static SendableChooser autoChooser = new SendableChooser();
	
	public static void createChooser() {
		autoChooser.addDefault("Do Nothing", new Integer(Constants.DO_NOTHING_AUTONOMOUS_MODE));
		autoChooser.addObject("Straight Driving", new Integer (Constants.DRIVE_STRAIGHT_AUTONOMOUS_MODE));
		autoChooser.addObject("Shoot High Goal", new Integer (Constants.SHOOT_HIGH_AUTONOMOUS_MODE));
		autoChooser.addObject("Place Gears", new Integer (Constants.PLACE_GEAR_AUTONOMOUS_MODE));
		autoChooser.addObject("Shoot Low Goal", new Integer (Constants.LOW_GOAL_AUTONOMOUS_MODE));
		SmartDashboard.putData("Autonomous Mode Chooser", autoChooser);
	}
	
	public static void init(Robot r) {
		
	}
	
	public static void periodic(Robot r) {
		
	}
}
