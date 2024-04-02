package lenses.objects;

import java.util.ArrayList;
import java.util.List;

import lenses.util.Angle;
import lenses.util.Color;
import processing.core.PVector;

public class LightSource implements HasHandle
{

	private PVector position;
	private Handle handle;
	private List<LightBeam> beams;

	public LightSource(PVector pos, int beams, Angle spread)
	{
		position = pos;
		float ang =0; // we specifically want integer division here...

		this.beams = new ArrayList<LightBeam>();

		for(int i= 0; i<beams; i++)
		{
			Color c = getColor();
			this.beams.add(new LightBeam(position, Angle.fromDegrees(ang),new Color(c.red(),c.green(),c.blue(),100)));
			ang += spread.getDegrees()/beams;

		}

		handle= new Handle(this);
		nudgeAngle(Angle.fromDegrees(-spread.getDegrees()/2.0f));
	}

	public Color getColor()
	{
		return new Color(200, 50, 50);
	}

	public PVector getHandlePos() { return position;} // returns the difference between the object's position and the handle's position. 


	public void updatePosition(PVector newHandlePos)
	{
		position = newHandlePos;
		for(LightBeam beam: beams)
		{
			beam.setStart(position);
		}
	}


	public void nudgeAngle(Angle ang)
	{
		for(LightBeam beam: beams)
		{
			beam.setAngle(beam.getAngle().add(ang));
		}
	}


	public Handle getHandle()
	{
		return handle;
	}


	public void update(List<HasHandle> objects)
	{
		for(LightBeam ray : beams)
		{
			ray.update( objects);
		}
	}

	public void draw()
	{
		handle.update();
		for(LightBeam ray : beams)
		{
			ray.draw();
		}

	}
}