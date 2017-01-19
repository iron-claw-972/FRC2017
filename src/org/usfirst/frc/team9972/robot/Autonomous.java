package org.usfirst.frc.team9972.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.*;

public class Autonomous {
	/*
	 * init() and periodic() are functions that will be called in Robot.java.
	 * All autonomous code should be placed in those two functions in order to
	 * make Robot.java easier to navigate (by shortening its length).
	 * 
	 * If you need to reference a non-static variable or function from Robot.java,
	 * such as isEnabled() or isAutonomous(), use the r Robot object.
	 * Example: if(r.isEnabled())
	 */
	
	static SendableChooser autoChooser = new SendableChooser();

	public static void createChooser() {
		autoChooser.addDefault("Do Nothing", AutonomousRoutine.DO_NOTHING);
		autoChooser.addObject("Cross Baseline", AutonomousRoutine.CROSS_BASELINE);
		SmartDashboard.putData("Autonomous Mode Chooser", autoChooser);
	}
	
	public static void init(Robot r) {
		updateSmartDashboardAutonomous();
	}
	
	public static void periodic(Robot r) {
		updateSmartDashboardAutonomous();
	}
	
	public static void updateSmartDashboardAutonomous() {
		SmartDashboard.putString("Autonomous Routine", autoChooser.getSelected().toString());
	}
}
