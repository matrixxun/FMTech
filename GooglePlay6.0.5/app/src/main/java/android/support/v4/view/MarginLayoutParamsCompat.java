package android.support.v4.view;

import android.os.Build.VERSION;
import android.view.ViewGroup.MarginLayoutParams;

public final class MarginLayoutParamsCompat
{
  static final MarginLayoutParamsCompatImpl IMPL = new MarginLayoutParamsCompatImplBase();
  
  static
  {
    if (Build.VERSION.SDK_INT >= 17)
    {
      IMPL = new MarginLayoutParamsCompatImplJbMr1();
      return;
    }
  }
  
  public static int getMarginEnd(ViewGroup.MarginLayoutParams paramMarginLayoutParams)
  {
    return IMPL.getMarginEnd(paramMarginLayoutParams);
  }
  
  public static int getMarginStart(ViewGroup.MarginLayoutParams paramMarginLayoutParams)
  {
    return IMPL.getMarginStart(paramMarginLayoutParams);
  }
  
  public static void setMarginEnd(ViewGroup.MarginLayoutParams paramMarginLayoutParams, int paramInt)
  {
    IMPL.setMarginEnd(paramMarginLayoutParams, paramInt);
  }
  
  public static void setMarginStart(ViewGroup.MarginLayoutParams paramMarginLayoutParams, int paramInt)
  {
    IMPL.setMarginStart(paramMarginLayoutParams, paramInt);
  }
  
  static abstract interface MarginLayoutParamsCompatImpl
  {
    public abstract int getMarginEnd(ViewGroup.MarginLayoutParams paramMarginLayoutParams);
    
    public abstract int getMarginStart(ViewGroup.MarginLayoutParams paramMarginLayoutParams);
    
    public abstract void setMarginEnd(ViewGroup.MarginLayoutParams paramMarginLayoutParams, int paramInt);
    
    public abstract void setMarginStart(ViewGroup.MarginLayoutParams paramMarginLayoutParams, int paramInt);
  }
  
  static final class MarginLayoutParamsCompatImplBase
    implements MarginLayoutParamsCompat.MarginLayoutParamsCompatImpl
  {
    public final int getMarginEnd(ViewGroup.MarginLayoutParams paramMarginLayoutParams)
    {
      return paramMarginLayoutParams.rightMargin;
    }
    
    public final int getMarginStart(ViewGroup.MarginLayoutParams paramMarginLayoutParams)
    {
      return paramMarginLayoutParams.leftMargin;
    }
    
    public final void setMarginEnd(ViewGroup.MarginLayoutParams paramMarginLayoutParams, int paramInt)
    {
      paramMarginLayoutParams.rightMargin = paramInt;
    }
    
    public final void setMarginStart(ViewGroup.MarginLayoutParams paramMarginLayoutParams, int paramInt)
    {
      paramMarginLayoutParams.leftMargin = paramInt;
    }
  }
  
  static final class MarginLayoutParamsCompatImplJbMr1
    implements MarginLayoutParamsCompat.MarginLayoutParamsCompatImpl
  {
    public final int getMarginEnd(ViewGroup.MarginLayoutParams paramMarginLayoutParams)
    {
      return paramMarginLayoutParams.getMarginEnd();
    }
    
    public final int getMarginStart(ViewGroup.MarginLayoutParams paramMarginLayoutParams)
    {
      return paramMarginLayoutParams.getMarginStart();
    }
    
    public final void setMarginEnd(ViewGroup.MarginLayoutParams paramMarginLayoutParams, int paramInt)
    {
      paramMarginLayoutParams.setMarginEnd(paramInt);
    }
    
    public final void setMarginStart(ViewGroup.MarginLayoutParams paramMarginLayoutParams, int paramInt)
    {
      paramMarginLayoutParams.setMarginStart(paramInt);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v4.view.MarginLayoutParamsCompat
 * JD-Core Version:    0.7.0.1
 */