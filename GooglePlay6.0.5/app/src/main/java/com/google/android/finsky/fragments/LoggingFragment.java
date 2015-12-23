package com.google.android.finsky.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.utils.FinskyLog;

public abstract class LoggingFragment
  extends Fragment
  implements PlayStoreUiElementNode
{
  private FinskyEventLog mEventLog;
  private PlayStore.PlayStoreUiElement mUiElement;
  
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
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mUiElement = FinskyEventLog.obtainPlayStoreUiElement(getPlayStoreUiElementType());
    String str = this.mArguments.getString("authAccount");
    if (str == null) {
      FinskyLog.wtf("authAccount argument not set.", new Object[0]);
    }
    this.mEventLog = FinskyApp.get().getEventLogger(str);
    if (paramBundle == null) {
      this.mEventLog.logPathImpression(0L, this);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.fragments.LoggingFragment
 * JD-Core Version:    0.7.0.1
 */