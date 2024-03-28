package lenses.util;

public class Color 
{
	public int value;
	
	public Color(int r, int g, int b, int alpha)
	{
		value = 0;
		value = (alpha &0xFF ) << 24 | (r&0xff)<<16 | (g&0xff)<<8 | b&0xff;
	}
	
	
	public Color(int r, int g, int b)
	{
		this(r,g,b,255);
	}
	

	public int alpha()
	{
		return value >> 24 & 0xFF;
	}
	
	public int red()
	{
		return value >> 16 & 0xFF;
	}
	

	public int green()
	{
		return value >> 8 & 0xFF;
	}
	

	public int blue()
	{
		return value & 0xFF;
	}
	
}
