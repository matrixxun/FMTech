package com.caverock.androidsvg;

public final class PreserveAspectRatio
{
  public static final PreserveAspectRatio BOTTOM = new PreserveAspectRatio(Alignment.XMidYMax, Scale.Meet);
  public static final PreserveAspectRatio END;
  public static final PreserveAspectRatio FULLSCREEN = new PreserveAspectRatio(Alignment.XMidYMid, Scale.Slice);
  public static final PreserveAspectRatio FULLSCREEN_START = new PreserveAspectRatio(Alignment.XMinYMin, Scale.Slice);
  public static final PreserveAspectRatio LETTERBOX;
  public static final PreserveAspectRatio START;
  public static final PreserveAspectRatio STRETCH;
  public static final PreserveAspectRatio TOP;
  public static final PreserveAspectRatio UNSCALED = new PreserveAspectRatio(null, null);
  Alignment alignment;
  Scale scale;
  
  static
  {
    STRETCH = new PreserveAspectRatio(Alignment.None, null);
    LETTERBOX = new PreserveAspectRatio(Alignment.XMidYMid, Scale.Meet);
    START = new PreserveAspectRatio(Alignment.XMinYMin, Scale.Meet);
    END = new PreserveAspectRatio(Alignment.XMaxYMax, Scale.Meet);
    TOP = new PreserveAspectRatio(Alignment.XMidYMin, Scale.Meet);
  }
  
  public PreserveAspectRatio(Alignment paramAlignment, Scale paramScale)
  {
    this.alignment = paramAlignment;
    this.scale = paramScale;
  }
  
  public final boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    PreserveAspectRatio localPreserveAspectRatio;
    do
    {
      return true;
      if (paramObject == null) {
        return false;
      }
      if (getClass() != paramObject.getClass()) {
        return false;
      }
      localPreserveAspectRatio = (PreserveAspectRatio)paramObject;
      if (this.alignment != localPreserveAspectRatio.alignment) {
        return false;
      }
    } while (this.scale == localPreserveAspectRatio.scale);
    return false;
  }
  
  public static enum Alignment
  {
    static
    {
      XMidYMin = new Alignment("XMidYMin", 2);
      XMaxYMin = new Alignment("XMaxYMin", 3);
      XMinYMid = new Alignment("XMinYMid", 4);
      XMidYMid = new Alignment("XMidYMid", 5);
      XMaxYMid = new Alignment("XMaxYMid", 6);
      XMinYMax = new Alignment("XMinYMax", 7);
      XMidYMax = new Alignment("XMidYMax", 8);
      XMaxYMax = new Alignment("XMaxYMax", 9);
      Alignment[] arrayOfAlignment = new Alignment[10];
      arrayOfAlignment[0] = None;
      arrayOfAlignment[1] = XMinYMin;
      arrayOfAlignment[2] = XMidYMin;
      arrayOfAlignment[3] = XMaxYMin;
      arrayOfAlignment[4] = XMinYMid;
      arrayOfAlignment[5] = XMidYMid;
      arrayOfAlignment[6] = XMaxYMid;
      arrayOfAlignment[7] = XMinYMax;
      arrayOfAlignment[8] = XMidYMax;
      arrayOfAlignment[9] = XMaxYMax;
      $VALUES = arrayOfAlignment;
    }
    
    private Alignment() {}
  }
  
  public static enum Scale
  {
    static
    {
      Scale[] arrayOfScale = new Scale[2];
      arrayOfScale[0] = Meet;
      arrayOfScale[1] = Slice;
      $VALUES = arrayOfScale;
    }
    
    private Scale() {}
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.caverock.androidsvg.PreserveAspectRatio
 * JD-Core Version:    0.7.0.1
 */