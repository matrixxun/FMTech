package android.support.v4.widget;

import android.widget.EdgeEffect;

final class EdgeEffectCompatIcs
{
  public static boolean onPull(Object paramObject, float paramFloat)
  {
    ((EdgeEffect)paramObject).onPull(paramFloat);
    return true;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v4.widget.EdgeEffectCompatIcs
 * JD-Core Version:    0.7.0.1
 */