package lenses.objects;

import java.util.Arrays;
import java.util.List;



import lenses.Main;
import lenses.util.Color;
import lenses.util.HitInfo;
import lenses.util.Line;
import lenses.util.ShapeType;
import processing.core.PApplet;
import processing.core.PVector;

public class Shape implements HasHandle
{

	// handle size

	public ShapeType type=ShapeType.opaque;
	private PVector pos;
	private PVector size;
	private List<PVector> points;
	private Handle handle;

	public Shape(boolean locked, ShapeType t, PVector... points)
	{
		type=t;
		init(locked, points);
	}

	public Shape(ShapeType t, PVector... points)
	{
		this(false, t, points);
	}

	protected void init(boolean locked, PVector... points)
	{
		if (points.length==0)
		{
			throw new IllegalArgumentException();
		}

		this.points = Arrays.asList(points);
		float minX = points[0].x;
		float maxX = points[0].x;
		float minY = points[0].y;
		float maxY = points[0].y;

		for (PVector point : points)
		{
			if (point.x<minX)
			{
				minX = point.x;
			}

			if (point.x>maxX)
			{
				maxX = point.x;
			}

			if (point.y<minY)
			{
				minY = point.y;
			}

			if (point.y>maxY)
			{
				maxY = point.y;
			}
		}

		size = new PVector(maxX-minX, maxY-minY);
		pos = new PVector((float)(size.x/2.0+minX), (float)(size.y/2.0+minY));
		handle= new Handle(this, locked);
	}

	public Color getColor() {
		switch(type)
		{
		case opaque:
			return new Color(60, 0, 110);
		case glass:
			return new Color(120, 0, 220);
		case mirror:
			return new Color(100, 100, 230);
		default:
			return new Color(0,0,0);
		}
	}

	public PVector getPos() {
		return pos;
	}

	public PVector getHandlePos() {
		return getPos();
	}

	public Handle getHandle() {
		return handle;
	}


	public boolean containsPoint(PVector point)
	{
		// whether a point is in a polygon can be determined by making an arbitrary ray and counting
		// the locations in which it intersects the polygon.
		// if this number is even, then the point is not in the polygon.
		Line test = new Line(point, 0);
		
		int hits = 0;
		
		for (int i = 0; i<points.size(); i++)
		{
			// get two points that make up a line.
			PVector p1 = points.get(i);
			PVector p2 = getP2(i);

			Line toHit = new Line(p1, p2);
			PVector potentialHit = toHit.intersect(test);
			if(potentialHit.x>=point.x && isHit(potentialHit, p1, p2))
			{
				hits++;
			}
		}

		return hits%2 == 1;
		//isHit(hit.hit, p1, p2)
	}
	
	private PVector getP2(int i)
	{
		if (i+1 == points.size())
		{
			return points.get(0);
		} 
		
		return points.get(i+1);
		
		
	}

	public HitInfo determineHit(Ray ray)
	{
		HitInfo hit = null;

		for (int i = 0; i<points.size(); i++)
		{
			// get two points that make up a line.
			PVector p1 = points.get(i);
			PVector p2 = getP2(i);
			
			hit = getHit(ray, hit, p1, p2);
		}

		return hit;
	}


	private HitInfo getHit(Ray ray, HitInfo prev, PVector p1, PVector p2)
	{

		// calculate hit.

		// sort for greater X.
		Line segment = new Line(p1, p2);

		// handle a quite literal edge case.
		if(segment.containsPoint(ray.getStart()))
		{
			return prev;
		}

		PVector hitP = segment.intersect(ray);
		HitInfo hit = new HitInfo(hitP, segment, this);

		// is this hit even valid?
		if (!isHit(hit.hit, p1, p2))
		{
			return prev;
		}


		// this hit is valid. should it replace the previous hit?
		if (prev == null)
		{
			return hit;
		}

		if (ray.getStart().dist(prev.hit)< ray.getStart().dist(hit.hit))
		{
			return prev;
		}

		return hit;
	}

	private boolean isHit(PVector hit, PVector p1, PVector p2)
	{
		if(hit==null) return false;
		PVector lessX = p1.x>p2.x ? p2 : p1;
		PVector moreX = lessX==p1? p2: p1;
		if (hit.x>moreX.x || hit.x<lessX.x) {
			return false;
		}

		if (p1.y != p2.y)
		{
			PVector lessY = p1.y>p2.y ? p2 : p1;
			PVector moreY = lessY==p1? p2: p1;
			if (hit.y>moreY.y || hit.y<lessY.y) {
				return false;
			}
		}
		return true;
	}



	public void updatePosition(PVector pos)
	{

		PVector delta = PVector.sub(pos, this.pos);
		for (PVector point : points)
		{
			point.add(delta);
		}

		this.pos=pos;
	}




	public void draw()
	{


		handle.update();
		// we create a shape and mould it to our desires. glorious.
		Main.app.noFill();
		Main.app.stroke(getColor().value);
		Main.app.strokeWeight(5);
		Main.app.beginShape();
		for (PVector i : points)
		{
			Main.app.vertex(i.x, i.y);
		}
		Main.app.endShape(PApplet.CLOSE);
	}
}