package lenses.util;
import processing.core.PConstants;

public class Angle
{
	private float rad;

	private Angle() {}

	public float getRadians()
	{
		return rad;
	}

	public float getDegrees()
	{
		return (rad*180)/(float)Math.PI;
	}

	public void setRadians(float radians)
	{
		rad = radians%(2.0f*(float)Math.PI);
		if(rad<0)
		{
			rad =2.0f*((float)Math.PI)+rad;
		}
	}

	public void setDegrees(float degrees)
	{
		setRadians((degrees*(float)Math.PI)/180);

	}

	public Angle add(Angle other)
	{
		return Angle.fromRadians(rad+other.rad);
	}


	public Angle sub(Angle other)
	{
		return Angle.fromRadians(rad-other.rad);
	}

	public static Angle fromDegrees(float degrees)
	{
		Angle ang = new Angle();
		ang.setDegrees(degrees);
		return ang;
	}

	public static Angle fromRadians(float radians)
	{
		Angle ang = new Angle();
		ang.setRadians(radians);
		return ang;
	}
}
