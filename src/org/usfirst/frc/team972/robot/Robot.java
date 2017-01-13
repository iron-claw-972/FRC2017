package org.usfirst.frc.team972.robot;

import edu.wpi.first.wpilibj.*;

public class Robot extends IterativeRobot {
	public void robotInit() {
		
	}
	
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