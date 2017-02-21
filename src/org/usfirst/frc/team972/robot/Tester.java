package org.usfirst.frc.team972.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.*;

public class Tester {

	static CANTalon one = new CANTalon(1);
	static CANTalon two = new CANTalon(2);
	static CANTalon three = new CANTalon(3);
	static CANTalon four = new CANTalon(4);
	static CANTalon five = new CANTalon(5);
	static CANTalon six = new CANTalon(6);
	static CANTalon seven = new CANTalon(7);
	static CANTalon eight = new CANTalon(8);
	static CANTalon nine = new CANTalon(9);
	static CANTalon ten = new CANTalon(10);
	static CANTalon eleven = new CANTalon(11);
	static CANTalon twelve = new CANTalon(12);
	static CANTalon thirteen = new CANTalon(13);
	static CANTalon fourteen = new CANTalon(14);
	static CANTalon fifteen = new CANTalon(15);
	static CANTalon sixteen = new CANTalon(16);

	static Joystick leftJoystick = new Joystick(0);
	static Joystick rightJoystick = new Joystick(1);
	static Joystick operatorJoystick = new Joystick(2);

	/**
	 * Manages motor testing.
	 * 
	 * @see runMotor()
	 */
	public static void run() {
		runMotor(one, operatorJoystick, 1);
		runMotor(two, operatorJoystick, 2);
		runMotor(three, operatorJoystick, 3);
		runMotor(four, operatorJoystick, 4);
		runMotor(five, leftJoystick, 1);
		runMotor(six, leftJoystick, 2);
		runMotor(seven, leftJoystick, 3);
		runMotor(eight, leftJoystick, 4);
		runMotor(nine, leftJoystick, 5);
		runMotor(ten, leftJoystick, 6);
		runMotor(eleven, rightJoystick, 1);
		runMotor(twelve, rightJoystick, 2);
		runMotor(thirteen, rightJoystick, 3);
		runMotor(fourteen, rightJoystick, 4);
		runMotor(fifteen, rightJoystick, 5);
		runMotor(sixteen, rightJoystick, 6);
	}

	/**
	 * Runs a given motor depending on joystick input.
	 * 
	 * @param motor
	 *            motor to test
	 * @param joy
	 *            joystick to check
	 * @param button
	 *            button on joystick to check
	 */
	public static void runMotor(CANTalon motor, Joystick joy, int button) {
		double speed = 0;
		if (joy.getRawButton(button)) {
			speed = joy.getY();
			motor.set(speed);
			System.out.println("Motor #" + motor.getDeviceID() + " Test Speed" + speed);
			System.out.println("Motor Current Draw: " + motor.getOutputCurrent());
		} else {
			motor.set(0);
		}
		SmartDashboard.putNumber("Motor #" + motor.getDeviceID() + " Test Speed", speed);
	}
}
