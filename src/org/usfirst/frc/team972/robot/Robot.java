package org.usfirst.frc.team972.robot;

import edu.wpi.first.wpilibj.*;

public class Robot extends IterativeRobot {
	public void robotInit() {
		
	}
	
	/*
	 * The four primary functions below (autonomousInit(), autonomousPeriodic(),
	 * teleopInit(), and teleopPeriodic()) should not be altered. Code should be
	 * altered in its respective class (Auto or Teleop).
	 * This change will help shorten the Robot class and make it easier to navigate.
	 */
	
	public void autonomousInit() {
		Auto.init(this);
	}
	
	public void autonomousPeriodic() {
		Auto.periodic(this);
	}
	
	public void teleopInit() {
		Teleop.init(this);
	}
	
	public void teleopPeriodic() {
		Teleop.periodic(this);
	}
}