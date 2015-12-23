package com.google.android.finsky.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Utils;

public class SidecarFragment
  extends Fragment
{
  private boolean mCreated;
  private Listener mListener;
  public int mState = 0;
  public int mStateInstance = 1;
  public int mSubstate;
  
  private void notifyListener()
  {
    if (this.mListener != null) {
      this.mListener.onStateChange(this);
    }
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setRetainInstance$1385ff();
    if (paramBundle != null) {
      restoreFromSavedInstanceState(paramBundle);
    }
    if (this.mListener != null) {
      notifyListener();
    }
    this.mCreated = true;
  }
  
  public final void onDestroy()
  {
    this.mCreated = false;
    super.onDestroy();
  }
  
  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putInt("SidecarFragment.state", this.mState);
    paramBundle.putInt("SidecarFragment.substate", this.mSubstate);
    paramBundle.putInt("SidecarFragment.stateInstance", this.mStateInstance);
  }
  
  public void restoreFromSavedInstanceState(Bundle paramBundle)
  {
    this.mState = paramBundle.getInt("SidecarFragment.state");
    this.mSubstate = paramBundle.getInt("SidecarFragment.substate");
    this.mStateInstance = paramBundle.getInt("SidecarFragment.stateInstance");
    if (this.mState == 1)
    {
      FinskyLog.d("Restoring after serialization in RUNNING, resetting to INIT.", new Object[0]);
      setState(0, 0);
    }
  }
  
  public final void setListener(Listener paramListener)
  {
    this.mListener = paramListener;
    if ((this.mListener != null) && (this.mCreated)) {
      notifyListener();
    }
  }
  
  public final void setState(int paramInt1, int paramInt2)
  {
    Utils.ensureOnMainThread();
    this.mState = paramInt1;
    this.mSubstate = paramInt2;
    this.mStateInstance = (1 + this.mStateInstance);
    notifyListener();
  }
  
  public static abstract interface Listener
  {
    public abstract void onStateChange(SidecarFragment paramSidecarFragment);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.fragments.SidecarFragment
 * JD-Core Version:    0.7.0.1
 */