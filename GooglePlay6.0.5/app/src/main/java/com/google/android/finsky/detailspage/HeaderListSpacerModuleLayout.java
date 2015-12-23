package com.google.android.finsky.detailspage;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.google.android.finsky.layout.play.FinskyHeaderListLayout.StreamSpacer;

public class HeaderListSpacerModuleLayout
  extends FrameLayout
  implements ModuleDividerItemDecoration.NoBottomDivider, ModuleDividerItemDecoration.NoTopDivider, FinskyHeaderListLayout.StreamSpacer
{
  public HeaderListSpacerModuleLayout(Context paramContext)
  {
    super(paramContext);
  }
  
  public HeaderListSpacerModuleLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.HeaderListSpacerModuleLayout
 * JD-Core Version:    0.7.0.1
 */