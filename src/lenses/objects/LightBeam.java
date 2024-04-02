package lenses.objects;

import java.util.ArrayList;
import java.util.List;

import lenses.Main;
import lenses.util.Angle;
import lenses.util.Color;
import lenses.util.HitInfo;
import lenses.util.Line;
import lenses.util.ShapeType;
import processing.core.PVector;

/**
 * A LightBeam represents a series of rays passing through or reflecting off of materials.
 * @author jmcra
 *
 */
public class LightBeam 
{
	private final static int maximumRays = 10;
	private List<Ray> rays;
	
	private PVector start;
	private Angle angle;
	private Color color;
	
	public LightBeam(PVector start, Angle angle, Color color)
	{
		this.color = color;
		this.start = start;
		this.angle = angle;
		rays = new ArrayList<Ray>();
		
	}
	
	// mind-numbing getters and setters
	public PVector getStart() { return start; }
	public void setStart(PVector start) { this.start = start;} 

	public Angle getAngle(){ return angle; }

	public void setAngle(Angle ang){ angle = ang; }

	
	public void update(List<HasHandle> objects)
	{
		rays.clear();
		PVector pos = start;
		Angle angle = this.angle;
		for(int i = 0; i<maximumRays; i++)
		{
			Color c = new Color(color.red(), color.green(), color.blue(), (int)(color.alpha()*Math.pow(.65, i)));
			rays.add(new Ray(pos, angle, c));
			
			HitInfo info = rays.get(i).update(objects);
			if(info.shapeHit.type == ShapeType.opaque)
			{
				break;
			}
			pos=info.hit;
			float rr = getRefractionRatio(rays.get(i), info, objects);
			angle = calcNextAngle(rr, rays.get(i).getAngle(), info);
		}
	}
	
	
	private Angle calcNextAngle(float refractionRatio, Angle previous, HitInfo info)
	{
		if(info.shapeHit.type==ShapeType.opaque)
		{
			return null; // doesn't matter.
		}
		
		if(info.shapeHit.type==ShapeType.glass)
		{
			// ignoring refraction for the time being.
			if(refractionRatio == 1) return previous;
			
			double step1 = Math.cos(previous.sub(info.lineHit.getAngle()).getRadians());
			double step2 = Math.asin(step1*refractionRatio);
			double step3 = Math.PI*.5 + info.lineHit.getAngle().getRadians() - step2;
			return Angle.fromRadians((float)step3);
		}
		
		// mirror
	
		Angle surfaceAngle = info.lineHit.getAngle();
		//System.out.println(surfaceAngle);
		return surfaceAngle.mult(2).sub(previous);
		
	
	}
	
	
	private float getRefractionRatio(Ray previous, HitInfo info, List<HasHandle> objects)
	{
		if(info.shapeHit.type!=ShapeType.glass)
		{
			return 0;
		}
		
		Line ray = new Line(previous.getStart(), previous.getAngle());
		PVector A = ray.PointFromPoint(info.hit, .1f);
		PVector B = ray.PointFromPoint(info.hit, -.1f);
		
		// get the enter and exit points.
		PVector enterPoint = previous.getStart().dist(A)<previous.getStart().dist(B)? A:B;
		PVector exitPoint = enterPoint==A? B: A;
		
		float enterRefract = getRefractionIndex(enterPoint, objects);
		float exitRefract = getRefractionIndex(exitPoint, objects);
		
		return enterRefract/exitRefract;
	}
	
	
	private float getRefractionIndex(PVector point, List<HasHandle> objects)
	{
		for(HasHandle hh : objects)
		{
			if(!(hh instanceof Shape)) continue;
			Shape shape = (Shape)hh;
			
			if(!shape.containsPoint(point)) continue;
			if(shape.type == ShapeType.glass)
			{
				return 1.5f;
			}
			
		}
		
		return 1;
		
	}
	
	
	public void draw()
	{
		for(Ray ray : rays)
		{
			ray.draw();
		}
	}
}
