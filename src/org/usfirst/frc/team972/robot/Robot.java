package org.usfirst.frc.team972.robot;

import edu.wpi.first.wpilibj.*;

public class Robot extends IterativeRobot {
	public void robotInit() {	
		Autonomous.createChooser();
	}

	/*
	 * The four primary functions below (autonomousInit(), autonomousPeriodic(),
	 * teleopInit(), and teleopPeriodic()) should not be altered. Code should be
	 * altered in its respective class (Autonomous or Teleop).
	 * This change will help shorten the Robot class and make it easier to navigate.
	 */
	
	public void autonomousInit() {
		Autonomous.init(this);
	}
	
	public void autonomousPeriodic() {
		Autonomous.periodic(this);
	}
	
	public void teleopInit() {
		Teleop.init(this);
	}
	
	public void teleopPeriodic() {
		Teleop.periodic(this);
	}
}