package lenses;

import java.util.ArrayList;
import java.util.List;

import lenses.objects.HasHandle;
import lenses.objects.LightSource;
import lenses.objects.ParabolicLens;
import lenses.objects.Ray;
import lenses.objects.Shape;
import lenses.util.Angle;
import lenses.util.ShapeType;
import processing.core.PApplet;
import processing.core.PVector;

public class Main 
{

	public static PApplet app;
	private static Main main = null;
	
	List<HasHandle> objects;
	//Ray ray;

	/* Things to do:
    X Lightsources (Check)
    Beams (have a list of rays)
    Determine: Is a point in a shape?
    X HitInfo (technically)
    refraction
    X make rays travel in all directions.

	 */
	Ray ray;

	
	private Main() 
	{
	}
	
	public static void main(String[] args) 
	{
		PApplet.main("lenses.Applet");
	}
	
	public static Main getInstance()
	{
		if(main == null)
		{
			main = new Main();
		}
		return main;
	}
	


	public void setup() 
	{
		
		objects = new ArrayList<HasHandle>();

		// world box. do not remove.
		objects.add(new Shape(true,ShapeType.opaque, 
				new PVector(0,0), 
				new PVector(app.width, 0), 
				new PVector(app.width, app.height-1),
				new PVector(0, app.height-1)));
		//objects.add(new Shape(new PVector(200, 200), new PVector(200, 300), new PVector(300, 300), new PVector(300,200)));
		//objects.add(new Shape(
		//	new PVector(400, 400), new PVector(400, 540), new PVector(440, 600), new PVector(600, 600), new PVector(600,400)));
		
		objects.add( new Shape(ShapeType.mirror, 
				new PVector(200, 200), new PVector(200, 300), new PVector(300, 300), new PVector(300,200)));
		
		objects.add(new Shape(ShapeType.opaque, new PVector(800,500), new PVector(700, 600), new PVector(800, 700)));

		objects.add(new ParabolicLens(new PVector(800, 200), 300, 100, 100, 0, 25));
		objects.add(new ParabolicLens(new PVector(1200, 200), 300, -100, 0, 120, 25));
		objects.add(new LightSource(new PVector(100, 450), 50, Angle.fromDegrees(359.99f)));
		ray =null;
		//ray = new Ray(new PVector(100, 450), fromDegrees(193.56), color(220, 30, 30));
		// wrong at 90, -90.



	}


	
	public void draw()
	{
		

		//println("frame!!!!!!!!!!!!!!!!!!!!!!!!");
		app.background(230);

		for(HasHandle obj : objects)
		{
			if(obj instanceof LightSource)
			{
				LightSource ls = (LightSource)obj;
				ls.nudgeAngle(Angle.fromDegrees(.2f));
				ls.update(objects);

			}
			obj.draw();
		}

		for(HasHandle obj: objects)
		{
			obj.getHandle().draw();
		}

		if(ray!=null)
		{
			ray.update(objects);
			ray.draw();
		}


	}

	public void mousePressed()
	{
		for(HasHandle obj : objects)
		{

			if(obj.getHandle().setDragged(true))
			{
				break;
			}

		}
	}

	public void mouseReleased()
	{
		for(HasHandle obj : objects)
		{
			obj.getHandle().setDragged(false);
		}
	}


}
