package com.google.android.libraries.bind.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.google.android.libraries.bind.data.Bound;
import com.google.android.libraries.bind.data.BoundHelper;
import com.google.android.libraries.bind.data.Data;

public class BoundFrameLayout
  extends FrameLayout
  implements Bound
{
  private final BoundHelper boundHelper = new BoundHelper(paramContext, paramAttributeSet, this);
  
  public BoundFrameLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public BoundFrameLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public BoundFrameLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public final void updateBoundData(Data paramData)
  {
    this.boundHelper.updateBoundData(paramData);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.libraries.bind.widget.BoundFrameLayout
 * JD-Core Version:    0.7.0.1
 */