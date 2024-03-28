package lenses.util;



import lenses.objects.Ray;
import processing.core.PVector;

public class Line
{

	private float A; // x coef.
	private float B; // y coef.
	private float C;
	// 

	public Line(PVector p1, PVector p2)
	{
		float run = p1.x-p2.x;
		float rise = p1.y-p2.y;
		A= -rise;
		B= run;
		C= p1.y*run - p1.x*rise;
	}

	public Line(PVector point, float slope)
	{
		A = -slope;
		B = 1;
		C = point.y - (slope*point.x);

	}

	public boolean containsPoint(PVector point)
	{
		return Util.floatsWithin(point.x*A+point.y*B, C, .01f);
	}

	public PVector intersect(Line other)
	{
		float x = ((C*other.B)-(B*other.C))/((A*other.B)-(other.A*B));
		float y = ((C*other.A)-(A*other.C))/((B*other.A)-(A*other.B));
		if(Float.isNaN(x) || Float.isInfinite(x) || Float.isNaN(y) || Float.isInfinite(y))
		{
			return null;
		}

		return new PVector(x,y);

	}

	// this is bad and stupid.
	public PVector intersect(Ray ray)
	{
		float rangle =ray.getAngle().getRadians();
		Line rayLine = new Line(ray.getStart(), (float)Math.tan(rangle));
		PVector toReturn = this.intersect(rayLine); // good




		if(toReturn == null) return null;



		if(rangle>Math.PI/2.0 && rangle <(3*Math.PI)/2.0)
		{
			// ray is facing backwards, our X must be lower than ray X
			if(toReturn.x>ray.getStart().x)
			{
				return null;
			}

			return toReturn;
		}

		if(toReturn.x>ray.getStart().x)
		{
			return toReturn;
		}

		return null;

	}


}