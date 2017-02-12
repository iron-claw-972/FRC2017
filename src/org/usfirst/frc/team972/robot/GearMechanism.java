package org.usfirst.frc.team972.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class GearMechanism {
	
	/**
	 * Resets pistons to starting positions.
	 */
	public static void init() {
		Robot.gearPegPiston.set(true);
		Robot.gearPusherPiston.set(false);
	}
	
	/**
	 * Places gear on delivery peg.
	 */
	public static void placeGear() {
		Robot.gearPegPiston.set(false);
		Robot.gearPusherPiston.set(true);
	}
}
