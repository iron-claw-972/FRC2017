package org.usfirst.frc.team9972.robot;

import edu.wpi.first.wpilibj.*;
import com.ctre.*;

public class Winch {
	
	public static void start(CANTalon motor) {
		motor.set(Constants.WINCH_MOTOR_SPEED);
	}
	
	public static void stop(CANTalon motor) {
		motor.set(0);
	}
}
