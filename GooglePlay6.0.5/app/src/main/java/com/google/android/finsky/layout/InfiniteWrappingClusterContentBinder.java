package com.google.android.finsky.layout;

import android.view.accessibility.AccessibilityEvent;

public abstract interface InfiniteWrappingClusterContentBinder
{
  public abstract int getDefaultFirstVisibleChildPosition();
  
  public abstract void onInitializeAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent, int paramInt1, int paramInt2);
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.InfiniteWrappingClusterContentBinder
 * JD-Core Version:    0.7.0.1
 */