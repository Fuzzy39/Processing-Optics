package lenses.util;



import lenses.objects.Ray;
import processing.core.PVector;

public class Line
{

	private float A; // x coef.
	private float B; // y coef.
	private float C; // Ax+By+C = 0
	// 

	public Line(PVector p1, PVector p2)
	{
		float run = p1.x-p2.x;
		float rise = p1.y-p2.y;
		A= -rise;
		B= run;
		C= p1.y*run - p1.x*rise;
	}
	
	
	public Line(PVector point, Angle ang)
	{
		float run = (float) Math.cos(ang.getRadians());
		float rise = (float) Math.sin(ang.getRadians());
	
		A = -rise;
		B = run;	
		C = run*point.y - rise*point.x;
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
	
	public PVector PointFromPoint(PVector point, float distance)
	{
		if(!containsPoint(point))
		{
			PVector inter = new Line(point, getAngle().add(Angle.fromDegrees(90))).intersect(this);
			throw new IllegalArgumentException("Point not on line. Dist: "+inter.dist(point));
		}
		
		float x;
		float y;
		
		if(B == 0)
		{
			x = -C/A;
			float g = distance*distance - point.x*point.x - (2*point.x*C)/A;
			g+= ((-1*C*C)/(A*A))+2*point.y*point.y;
			float squart = (float)Math.sqrt(g);
			squart *= distance>=0? 1: -1;
			y = point.y*point.y + squart;
		}
		else
		{
			float A2 = (1 + ((A*A)/(B*B)));
			float B2 = ((2*C*A)/(B*B))+(2*point.y*A/B)-2*point.x;
			float q = point.x*point.x+point.y*point.y -distance*distance;
			float C2 = q + (C*C)/(B*B) + (2*point.y*C)/B;
			float squart = (float)Math.sqrt(B2*B2 - (4*A2*C2));
			squart *= distance>=0? 1: -1;
			x = (-1*B2 + squart)/(2*A2);
			y = (-1*C - A*x)/B;
			
		}
		
		// this is very wrong
		return new PVector(x,y);
		
	}
	
	public Angle getAngle()
	{
		float rads = (float)Math.atan(-(A/B));
		if(!Float.isFinite(rads))
		{
			// Line is vertical. angle closest to zero for this is 90
			return Angle.fromDegrees(90);
		}
		if(rads > Math.PI)
		{
			rads-=Math.PI;
		}
		return Angle.fromRadians(rads);
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