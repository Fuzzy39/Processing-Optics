package lenses.objects;

import lenses.util.Color;
import processing.core.PVector;

public interface HasHandle
{
  public Color getColor();   
  public PVector getHandlePos(); // returns the difference between the object's position and the handle's position. 
  public void updatePosition(PVector newHandlePos); 
  
  public Handle getHandle();
  
  public void draw();
  
}