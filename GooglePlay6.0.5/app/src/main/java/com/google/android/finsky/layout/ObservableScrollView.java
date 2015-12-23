package com.google.android.finsky.layout;

import android.content.Context;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.widget.ScrollView;
import java.util.List;

public class ObservableScrollView
  extends ScrollView
{
  private static final boolean HAS_IN_LAYOUT_METHOD;
  private boolean mIsInLayout;
  private List<Object> mOnScrollListeners;
  
  static
  {
    if (Build.VERSION.SDK_INT >= 18) {}
    for (boolean bool = true;; bool = false)
    {
      HAS_IN_LAYOUT_METHOD = bool;
      return;
    }
  }
  
  public ObservableScrollView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public ObservableScrollView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mIsInLayout = true;
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    this.mIsInLayout = false;
  }
  
  protected void onScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onScrollChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    if (this.mOnScrollListeners != null) {
      for (int i = -1 + this.mOnScrollListeners.size(); i >= 0; i--) {
        this.mOnScrollListeners.get(i);
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.ObservableScrollView
 * JD-Core Version:    0.7.0.1
 */