package org.usfirst.frc.team972.robot;

import edu.wpi.first.wpilibj.*;
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

	static Joystick leftJoy = new Joystick(0);
	static Joystick rightJoy = new Joystick(1);
	static Joystick operatorJoy = new Joystick(2);

	public static void run() {		if (leftJoy.getRawButton(1)) {
		double throttle = leftJoy.getThrottle();
		one.set(throttle);
		System.out.println("1 " + throttle);
	}
	
	if (leftJoy.getRawButton(2)) {
		double throttle = leftJoy.getThrottle();
		two.set(throttle);
		System.out.println("2 " + throttle);
	}

	if (leftJoy.getRawButton(3)) {
		double throttle = leftJoy.getThrottle();
		three.set(throttle);
		System.out.println("3 " + throttle);
	}

	if (leftJoy.getRawButton(4)) {
		double throttle = leftJoy.getThrottle();
		four.set(throttle);
		System.out.println("4 " + throttle);
	}

	if (leftJoy.getRawButton(5)) {
		double throttle = leftJoy.getThrottle();
		five.set(throttle);
		System.out.println("5 " + throttle);
	}

	if (leftJoy.getRawButton(6)) {
		double throttle = leftJoy.getThrottle();
		six.set(throttle);
		System.out.println("6 " + throttle);
	}

	if (rightJoy.getRawButton(1)) {
		double throttle = rightJoy.getThrottle();
		seven.set(throttle);
		System.out.println("7 " + throttle);
	}

	if (rightJoy.getRawButton(2)) {
		double throttle = rightJoy.getThrottle();
		eight.set(throttle);
		System.out.println("8 " + throttle);
	}

	if (rightJoy.getRawButton(3)) {
		double throttle = rightJoy.getThrottle();
		nine.set(throttle);
		System.out.println("9 " + throttle);
	}
	
	if (rightJoy.getRawButton(4)) {
		double throttle = rightJoy.getThrottle();
		ten.set(throttle);
		System.out.println("10 " + throttle);
	}

	if (rightJoy.getRawButton(5)) {
		double throttle = rightJoy.getThrottle();
		eleven.set(throttle);
		System.out.println("11 " + throttle);
	}

	if (rightJoy.getRawButton(6)) {
		double throttle = rightJoy.getThrottle();
		twelve.set(throttle);
		System.out.println("12 " + throttle);
	}

	if (operatorJoy.getRawButton(1)) {
		double throttle = operatorJoy.getThrottle();
		thirteen.set(throttle);
		System.out.println("13 " + throttle);
	}

	if (operatorJoy.getRawButton(2)) {
		double throttle = operatorJoy.getThrottle();
		fourteen.set(throttle);
		System.out.println("14 " + throttle);
	}

	if (operatorJoy.getRawButton(3)) {
		double throttle = operatorJoy.getThrottle();
		fifteen.set(throttle);
		System.out.println("15 " + throttle);
	}

	if (operatorJoy.getRawButton(4)) {
		double throttle = operatorJoy.getThrottle();
		sixteen.set(throttle);
		System.out.println("16 " + throttle);
	}
	}
}
