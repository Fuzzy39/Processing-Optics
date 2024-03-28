package lenses;

import processing.core.PApplet;

public class Applet extends PApplet
{
	public void settings()
	{
		size(1600,900, P2D);
		//pixelDensity(displayDensity());
		Main.app = this;
	}
	
	public void setup() 
	{
		Main.getInstance().setup();
		
	}
	
	public void draw()
	{
		Main.getInstance().draw();
	}
	
	public void mousePressed()
	{
		Main.getInstance().mousePressed();
	}
	
	public void mouseReleased()
	{
		Main.getInstance().mouseReleased();
	}
}
