package org.usfirst.frc.team972.robot;

public class LED {
	static int leftR = 0, leftG = 0, leftB = 0;
	static int rightR = 0, rightG = 0, rightB = 0;
	
	public static void setLeftColor(int r, int g, int b) {
		leftR = r;
		leftG = g;
		rightB = b;
	}
	
	public static void setRightColor(int r, int g, int b) {
		rightR = r;
		rightG = g;
		rightB = b;
	}
	
	// Sets the LED colors on the robot (call this periodically)
	public static void update() {
		
	}
}
