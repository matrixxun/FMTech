package com.google.android.finsky.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowInsets;
import android.widget.FrameLayout;

public class InsetsFrameLayout
  extends FrameLayout
  implements InsetsAware
{
  public static boolean SUPPORTS_IMMERSIVE_MODE;
  
  static
  {
    if (Build.VERSION.SDK_INT >= 21) {}
    for (boolean bool = true;; bool = false)
    {
      SUPPORTS_IMMERSIVE_MODE = bool;
      return;
    }
  }
  
  public InsetsFrameLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public InsetsFrameLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public InsetsFrameLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  @TargetApi(21)
  public WindowInsets onApplyWindowInsets(WindowInsets paramWindowInsets)
  {
    int i = getChildCount();
    for (int j = 0;; j++)
    {
      int k = 0;
      if (j < i)
      {
        View localView3 = getChildAt(j);
        if (((localView3 instanceof InsetsAware)) && (((InsetsAware)localView3).shouldApplyWindowInsets())) {
          k = 1;
        }
      }
      else
      {
        if (k != 0) {
          break label124;
        }
        setPadding(paramWindowInsets.getSystemWindowInsetLeft(), paramWindowInsets.getSystemWindowInsetTop(), paramWindowInsets.getSystemWindowInsetRight(), paramWindowInsets.getSystemWindowInsetBottom());
        for (int i2 = 0; i2 < i; i2++)
        {
          View localView2 = getChildAt(i2);
          if (localView2.getId() == 2131755212) {
            localView2.setPadding(0, 0, 0, 0);
          }
        }
      }
    }
    return paramWindowInsets.consumeSystemWindowInsets();
    label124:
    setPadding(0, 0, 0, 0);
    int m = 0;
    if (m < i)
    {
      View localView1 = getChildAt(m);
      int n;
      if (localView1.getId() == 2131755212)
      {
        n = 1;
        label162:
        if ((!(localView1 instanceof InsetsAware)) || (!((InsetsAware)localView1).shouldApplyWindowInsets())) {
          break label215;
        }
      }
      label215:
      for (int i1 = 1;; i1 = 0)
      {
        if ((n != 0) || (i1 != 0)) {
          localView1.dispatchApplyWindowInsets(paramWindowInsets);
        }
        m++;
        break;
        n = 0;
        break label162;
      }
    }
    return paramWindowInsets.consumeSystemWindowInsets();
  }
  
  public final boolean shouldApplyWindowInsets()
  {
    int i = getChildCount();
    for (int j = 0; j < i; j++)
    {
      View localView = getChildAt(j);
      if (((localView instanceof InsetsAware)) && (((InsetsAware)localView).shouldApplyWindowInsets())) {
        return true;
      }
    }
    return false;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.InsetsFrameLayout
 * JD-Core Version:    0.7.0.1
 */