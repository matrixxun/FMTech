package com.google.android.wallet.common.analytics.util;

import android.os.Handler;
import com.google.android.gsf.GservicesValue;
import com.google.android.wallet.analytics.UiNode;
import com.google.android.wallet.config.G;

public final class PageImpressionTracker
{
  public boolean mImpressionForPageTracked;
  Handler mImpressionHandler = new Handler();
  
  public PageImpressionTracker(boolean paramBoolean)
  {
    this.mImpressionForPageTracked = paramBoolean;
  }
  
  public final void trackImpressionIfNecessary(final UiNode paramUiNode)
  {
    if (!this.mImpressionForPageTracked)
    {
      this.mImpressionHandler.removeCallbacksAndMessages(null);
      this.mImpressionHandler.postDelayed(new Runnable()
      {
        public final void run()
        {
          if (!PageImpressionTracker.this.mImpressionForPageTracked)
          {
            PageImpressionTracker.this.mImpressionForPageTracked = true;
            AnalyticsUtil.createAndSendImpressionEvent(paramUiNode, -1);
          }
        }
      }, ((Integer)G.pageImpressionDelayBeforeTrackingMs.get()).intValue());
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.common.analytics.util.PageImpressionTracker
 * JD-Core Version:    0.7.0.1
 */