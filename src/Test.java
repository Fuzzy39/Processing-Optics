import processing.core.PApplet;

public class Test extends PApplet {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main("Test");
	}
	

	public void settings()
	{
		size(1600,900);
	}
	
	public void setup()
	{
		
	}
	
	public void draw()
	{
		fill(200, 0, 100);
		circle(mouseX, mouseY,50);
	}


}
