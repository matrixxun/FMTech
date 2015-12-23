package com.google.android.wallet.instrumentmanager.ui.common;

import android.content.Context;
import android.util.AttributeSet;
import com.google.android.wallet.analytics.AnalyticsClickListener;
import com.google.android.wallet.analytics.UiNode;
import com.google.android.wallet.common.analytics.util.AnalyticsUtil;
import com.google.android.wallet.ui.common.InfoMessageTextView;

public class ImInfoMessageTextView
  extends InfoMessageTextView
  implements AnalyticsClickListener
{
  public ImInfoMessageTextView(Context paramContext)
  {
    super(paramContext, null);
    setAnalyticsClickListener(this);
  }
  
  public ImInfoMessageTextView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setAnalyticsClickListener(this);
  }
  
  public ImInfoMessageTextView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    setAnalyticsClickListener(this);
  }
  
  public final void onAnalyticsClickEvent(UiNode paramUiNode, int paramInt)
  {
    AnalyticsUtil.createAndSendClickEvent(paramUiNode, -1, paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.instrumentmanager.ui.common.ImInfoMessageTextView
 * JD-Core Version:    0.7.0.1
 */