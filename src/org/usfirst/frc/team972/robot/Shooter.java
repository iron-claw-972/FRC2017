package org.usfirst.frc.team972.robot;

import edu.wpi.first.wpilibj.*;
import com.ctre.*;

public class Shooter {
	
	public static void init() {
		Robot.leftFlywheelMotor.enableBrakeMode(true);
		Robot.rightFlywheelMotor.enableBrakeMode(true);
	}
}
