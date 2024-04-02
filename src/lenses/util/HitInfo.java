package lenses.util;

import lenses.objects.Shape;
import processing.core.PVector;

public class HitInfo
{
  public PVector hit;
  public Line lineHit;
  public Shape shapeHit;
  
  public HitInfo(PVector hit, Line line, Shape shape)
  {
    this.hit = hit;
    lineHit = line;
    shapeHit = shape;
  }
}