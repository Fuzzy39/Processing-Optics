package lenses.objects;


import lenses.Main;
import lenses.util.Color;
import processing.core.PVector;

public class Handle
{
	private HasHandle parent;
	static final int size = 25;
	private boolean dragged;
	private boolean locked;

	public Handle(HasHandle parent, boolean locked)
	{
		this.parent = parent;
		this.locked = locked;
	}

	public Handle(HasHandle parent)
	{
		this(parent, false);
	}

	private boolean handleHover()
	{
		return new PVector(Main.app.mouseX, Main.app.mouseY).dist(parent.getHandlePos())<=size;
	}

	public boolean setDragged(boolean mouseDown)
	{
		if(locked) return false;
		dragged = mouseDown && handleHover();
		return dragged;
	}

	public void update()
	{

		if(locked) return;
		if(dragged)
		{

			int border = 5;
			int mx = Main.app.mouseX;
			if(mx<border) mx = border;
			if(mx>Main.app.width-border) mx = Main.app.width-border;
			int my = Main.app.mouseY;
			if(my<border) my = border;
			if(my>Main.app.height-border) my=Main.app.height-border;

			parent.updatePosition(new PVector(mx, my));
		}
	}

	public void draw()
	{
		if(locked) return;
		

		// handle
		Main.app.strokeWeight(size);
		Color c = parent.getColor();
		if(handleHover())
		{
			Main.app.stroke(c.red(),c.green(),c.blue(),128);
		}
		else
		{
			Main.app.noStroke();
		}

		Main.app.fill(c.value);
		PVector pos = parent.getHandlePos();
		Main.app.circle(pos.x,pos.y,size);
	}


}