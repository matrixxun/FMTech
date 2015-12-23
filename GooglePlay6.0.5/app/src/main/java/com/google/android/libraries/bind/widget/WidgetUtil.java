package com.google.android.libraries.bind.widget;

import android.graphics.Rect;
import android.view.View;

public final class WidgetUtil
{
  private static int[] tempLocation = new int[2];
  private static Rect tempRect = new Rect();
  private static Rect tempRect2 = new Rect();
  
  public static boolean isVisibleOnScreen(View paramView)
  {
    return paramView.getGlobalVisibleRect(tempRect);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.libraries.bind.widget.WidgetUtil
 * JD-Core Version:    0.7.0.1
 */