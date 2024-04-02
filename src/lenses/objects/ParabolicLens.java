package lenses.objects;

import lenses.util.ShapeType;
import processing.core.PVector;

public class ParabolicLens extends Shape
{

	// goal for now is just a parabola
	public ParabolicLens(PVector pos, int tall, int wideLeft, int wideRight, int wideExtra, int points)
	{
		this(ShapeType.glass, pos, tall, wideLeft, wideRight, wideExtra, points);
	}
	
	public ParabolicLens(ShapeType t, PVector pos, int tall, int wideLeft, int wideRight, int wideExtra, int points)
	{
		super(t, pos);
		// basically the goal is to create a list of points.
		PVector center = new PVector(pos.x, pos.y);
		center.x+=wideLeft;
		center.y+=tall/2.0;

		PVector[] left = parabola(center, tall, wideLeft, points);
		center.x+=wideExtra;
		PVector[] right = parabola(center, tall, -wideRight, points);

		int minus = wideExtra==0?2:0;
		PVector[] pointArr = new PVector[(left.length+right.length)-minus];
		int j;
		for(j = 0; j<left.length; j++)
		{
			pointArr[j]=left[j];
		}

		int mod = minus==2?1:0;
		for(int i= right.length-1-mod; i>=0+mod; i--)
		{
			//println(j);
			pointArr[j]=right[i];
			j++;
		}
		init(false, pointArr);
	}


	private PVector[] parabola(PVector center, int tall, int wide, int points)
	{
		PVector[] pointArr = new PVector[points];
		for(int i = 0; i<points; i++)
		{
			pointArr[i] = new PVector();

			pointArr[i].y = (float)(((tall+0.0)/(points-1))*i+center.y-(tall/2.0));
			float thingy = (float)Math.pow(pointArr[i].y-center.y, 2);

			pointArr[i].x = (float)(thingy*((4*wide)/(tall*tall*1.0))+center.x-wide);


		}
		return pointArr;
	}
}