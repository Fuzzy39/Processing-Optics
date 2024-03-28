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





	public void update(List<HasHandle> objects)
	{

		PVector potential = endNoHit();
		for(HasHandle handle : objects)
		{
			if(!(handle instanceof Shape)) continue;
			Shape s = (Shape)handle;
			HitInfo info = s.determineHit(this);

			PVector hit = info!=null?info.hit:null;

			if(potential==null || (hit!=null && start.dist(hit) <=start.dist(potential)))
			{
				potential = hit;
			}
		}

		end= potential;



	}



	// not needed, but may as well.
	private PVector endNoHit()
	{

		Line top = new Line(new PVector(0,0), new PVector(Main.app.width, 0));
		Line bottom = new Line(new PVector(0,Main.app.height), new PVector(Main.app.width, Main.app.height));

		Line left = new Line(new PVector(0,0), new PVector(0, Main.app.height));
		Line right = new Line(new PVector(Main.app.width,0), new PVector(Main.app.width, Main.app.height));
		Line[] lines = {top, bottom, left, right};


		PVector current = null;
		for(Line line : lines)
		{
			PVector next = line.intersect(this);
			if(current == null)
			{
				current = next;
				continue;
			}

			if(next==null)
			{
				continue;
			}

			if(start.dist(next)<start.dist(current))
			{
				current = next;
			}
		}


		return current;


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