package com.google.android.finsky.billing.lightpurchase.billingprofile.instruments;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.billing.lightpurchase.AppCompatDelegateActivity;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.receivers.FlushLogsReceiver;
import com.google.android.finsky.utils.FinskyLog;

public abstract class LoggingFragmentActivity
  extends AppCompatDelegateActivity
  implements PlayStoreUiElementNode
{
  public String mAccountName;
  public FinskyEventLog mEventLog;
  private PlayStore.PlayStoreUiElement mUiElement = FinskyEventLog.obtainPlayStoreUiElement(getPlayStoreUiElementType());
  
  public static void addAccountNameExtra(Intent paramIntent, String paramString)
  {
    paramIntent.putExtra("authAccount", paramString);
  }
  
  public final void childImpression(PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    throw new UnsupportedOperationException("Unwanted children.");
  }
  
  public PlayStoreUiElementNode getParentNode()
  {
    return null;
  }
  
  public PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mUiElement;
  }
  
  public abstract int getPlayStoreUiElementType();
  
  public void onBackPressed()
  {
    this.mEventLog.logClickEvent(600, null, this);
    super.onBackPressed();
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mAccountName = getIntent().getStringExtra("authAccount");
    if (this.mAccountName == null) {
      FinskyLog.wtf("authAccount argument not set.", new Object[0]);
    }
    this.mEventLog = FinskyApp.get().getEventLogger(this.mAccountName);
    if (paramBundle == null) {
      this.mEventLog.logPathImpression(0L, this);
    }
  }
  
  public void onDestroy()
  {
    if ((isFinishing()) && (this.mEventLog != null)) {
      this.mEventLog.logPathImpression$7d139cbf(603, this);
    }
    super.onDestroy();
  }
  
  protected void onPause()
  {
    super.onPause();
    FlushLogsReceiver.scheduleLogsFlushOnAppExit();
  }
  
  public void onResume()
  {
    super.onResume();
    FlushLogsReceiver.cancelLogsFlush();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.lightpurchase.billingprofile.instruments.LoggingFragmentActivity
 * JD-Core Version:    0.7.0.1
 */