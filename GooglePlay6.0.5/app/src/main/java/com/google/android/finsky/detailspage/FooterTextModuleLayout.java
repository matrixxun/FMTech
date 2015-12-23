package com.google.android.finsky.detailspage;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class FooterTextModuleLayout
  extends TextView
  implements ModuleDividerItemDecoration.NoBottomDivider, ModuleDividerItemDecoration.NoTopDivider
{
  boolean mBinded;
  
  public FooterTextModuleLayout(Context paramContext)
  {
    super(paramContext, null);
  }
  
  public FooterTextModuleLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.FooterTextModuleLayout
 * JD-Core Version:    0.7.0.1
 */