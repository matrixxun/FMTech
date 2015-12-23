package com.google.android.libraries.bind.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.google.android.libraries.bind.R.layout;

public class LoadingView
  extends FrameLayout
{
  public LoadingView(Context paramContext)
  {
    this(paramContext, null, 0);
  }
  
  public LoadingView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public LoadingView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    inflate(paramContext, R.layout.bind__loading, this);
    setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.libraries.bind.widget.LoadingView
 * JD-Core Version:    0.7.0.1
 */