package lenses.util;

import processing.core.PVector;

public class HitInfo
{
  public PVector hit;
  public Line lineHit;
  
  public HitInfo(PVector hit, Line line)
  {
    this.hit = hit;
    lineHit = line;
  }
}