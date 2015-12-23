package com.google.android.finsky.detailspage;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.finsky.layout.DetailsSectionStack.NoBottomSeparator;
import com.google.android.finsky.layout.DetailsSectionStack.NoTopSeparator;

public class WarningMessageModuleLayout
  extends LinearLayout
  implements ModuleDividerItemDecoration.NoBottomDivider, ModuleDividerItemDecoration.NoTopDivider, DetailsSectionStack.NoBottomSeparator, DetailsSectionStack.NoTopSeparator
{
  boolean mBinded;
  ImageView mWarningMessageIcon;
  TextView mWarningMessageText;
  
  public WarningMessageModuleLayout(Context paramContext)
  {
    super(paramContext);
  }
  
  public WarningMessageModuleLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mWarningMessageText = ((TextView)findViewById(2131756229));
    this.mWarningMessageIcon = ((ImageView)findViewById(2131756228));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.WarningMessageModuleLayout
 * JD-Core Version:    0.7.0.1
 */