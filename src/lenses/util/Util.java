package lenses.util;

import processing.core.PApplet;

public class Util
{

	public static boolean floatsWithin(float a, float b, float within)
	{
		return PApplet.abs(PApplet.abs(a)-PApplet.abs(b))<=within;
	}
}


