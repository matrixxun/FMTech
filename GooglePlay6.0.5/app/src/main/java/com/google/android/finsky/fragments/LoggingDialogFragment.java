package com.google.android.finsky.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.utils.FinskyLog;

public abstract class LoggingDialogFragment
  extends DialogFragment
  implements PlayStoreUiElementNode
{
  protected FinskyEventLog mEventLog;
  private final PlayStore.PlayStoreUiElement mUiElement = FinskyEventLog.obtainPlayStoreUiElement(getPlayStoreUiElementType());
  
  public final void childImpression(PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    throw new UnsupportedOperationException("Unwanted children.");
  }
  
  public PlayStoreUiElementNode getParentNode()
  {
    return (PlayStoreUiElementNode)getActivity();
  }
  
  public PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mUiElement;
  }
  
  public abstract int getPlayStoreUiElementType();
  
  public final void logClickEvent(int paramInt)
  {
    this.mEventLog.logClickEvent(paramInt, null, this);
  }
  
  public final void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    if (!(paramActivity instanceof PlayStoreUiElementNode)) {
      throw new IllegalStateException("Parent activity must implement PlayStoreUiElementNode.");
    }
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    String str = this.mArguments.getString("authAccount");
    if (str == null) {
      FinskyLog.wtf("authAccount argument not set.", new Object[0]);
    }
    this.mEventLog = FinskyApp.get().getEventLogger(str);
    if (paramBundle == null) {
      this.mEventLog.logPathImpression(0L, this);
    }
  }
  
  public void onDismiss(DialogInterface paramDialogInterface)
  {
    if (this.mEventLog != null) {
      this.mEventLog.logPathImpression$7d139cbf(603, this);
    }
    super.onDismiss(paramDialogInterface);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.fragments.LoggingDialogFragment
 * JD-Core Version:    0.7.0.1
 */