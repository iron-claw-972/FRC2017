package org.usfirst.frc.team972.robot;

public class Task {
	public int type = 0;
	public double distance = 0;
	public double time = 0;
	public double angle = 0;
	
	public Task(int _type, double distance2, double duration, double _angle) {
		type = _type;
		distance = distance2;
		time = duration;
		angle = _angle;
	}
}
