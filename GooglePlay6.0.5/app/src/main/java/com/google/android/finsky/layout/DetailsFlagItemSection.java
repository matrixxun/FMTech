package com.google.android.finsky.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.FlagItemDialog;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.DocV2;

public class DetailsFlagItemSection
  extends AccessibleLinearLayout
  implements DetailsSectionStack.NoBottomSeparator
{
  public boolean mBinded;
  
  public DetailsFlagItemSection(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public DetailsFlagItemSection(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.DetailsFlagItemSection
 * JD-Core Version:    0.7.0.1
 */