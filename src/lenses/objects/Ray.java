package lenses.objects;

import java.util.List;

import lenses.Main;
import lenses.util.*;
import processing.core.PVector;

//represents a single lonely ray of light travelling along a straight path.
public class Ray
{

	private PVector start;
	private PVector end;

	private Angle angle;  // angle clockwise from the right.
	private boolean drawEnds = true;
	private Color Color;


	// update must be called before ray can be drawn.
	public Ray(PVector start, Angle angle, Color Color)
	{
		this.start = start;
		this.angle = angle;
		this.Color = Color;
	}  


	// mind-numbing getters and setters
	public PVector getStart() { return start; }
	public void setStart(PVector start) { this.start = start;} 

	public Angle getAngle()
	{
		return angle; 
	}

	public void setAngle(Angle ang)
	{
		angle = ang;
	}


	public PVector getEnd(){ return end; }





	public HitInfo update(List<HasHandle> objects)
	{
		
		HitInfo toReturn = null;
		for(HasHandle handle : objects)
		{
			if(!(handle instanceof Shape)) continue;
			Shape s = (Shape)handle;
			HitInfo info = s.determineHit(this);
			
			if(toReturn == null)
			{
				toReturn = info;
				continue;
			}
			
			if(info == null) continue;
			if(start.dist(info.hit) <=start.dist(toReturn.hit))
			{
				toReturn = info;
			}	
		}
		
		if(toReturn == null)
		{
			throw new NullPointerException("Ray at "+start+", angle "+angle.getDegrees()+" degrees intersected no object.");
		}
		
		end = toReturn.hit;
		return toReturn;

	}



	public void draw()
	{

		Main.app.stroke(Color.value);
		Main.app.strokeWeight(3);
		Main.app.line(start.x, start.y, end.x, end.y);

		Main.app.noStroke();
		Main.app.fill(Color.value);
		Main.app.circle(start.x,start.y, 15);
		Main.app.circle(end.x, end.y, 15);

	}

}