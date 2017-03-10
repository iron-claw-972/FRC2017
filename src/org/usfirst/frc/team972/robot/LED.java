package org.usfirst.frc.team972.robot;

public class LED {
	
	//object oriented for life
	//	static int rS = 0, gS = 0, bS = 0;
	int r = 0; int g = 0; int b = 0;
	PWM red;
	PWM green;
	PWM blue;
	
	public LED(int rPort, int gPort, int bPort) {//use available PWM ports
		red = new PWM(rPort);
		green = new PWM(gPort);
		blue = new PWM(bPort);
	}
	
	public void setColor(int r, int g, int b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}	
	
	public void setColor(String color) {	//annoying string parameter just in case

		if(color.equals("red")){
			r = 255;
			g = 0;
			b = 0;
		}
		
		if(color.equals("green")){
			r = 0;
			g = 255;
			b = 0;
		}
		
		if(color.equals("blue")){
			r = 0;
			g = 0;
			b = 255;
		}
		
		if(color.equals("white")){
			r = 255;
			g = 255;
			b = 255;
		}
		
		if(color.equals("purple")){
			r = 255;
			g = 0;
			b = 255;
		}
		
		if(color.equals("orange")){//needs testing
			r = 255;
			g = 60;
			b = 0;
		}
		
		if(color.equals("yellow")){
			r = 255;
			g = 255;
			b = 0;
		}
	}
	
	// Sets the LED colors on the robot (call this periodically)
	public void update() {
		red.setRaw(r);
		green.setRaw(g);
		blue.setRaw(b);
	}
	
	public void setBrightness(double factor){
		r = (int)(r*factor);
		g = (int)(g*factor);
		b = (int)(b*factor);
	}
	
	public void off(){
		r = 0;
		g = 0;
		b = 0;
		update();
	}
}
