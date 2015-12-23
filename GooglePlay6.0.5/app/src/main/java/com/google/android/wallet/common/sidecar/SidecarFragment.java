package com.google.android.wallet.common.sidecar;

import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.util.Log;

public class SidecarFragment
  extends Fragment
{
  private boolean mCreated;
  private Listener mListener;
  boolean mNotifyListenerOfStateChange = false;
  public int mState = -1;
  public int mSubstate = -1;
  
  private void notifyListener()
  {
    if (this.mListener != null)
    {
      this.mListener.onStateChange(this);
      this.mNotifyListenerOfStateChange = false;
    }
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setRetainInstance$1385ff();
    if (paramBundle == null) {
      setState(0, 0);
    }
    for (;;)
    {
      this.mCreated = true;
      return;
      restoreFromSavedInstanceState(paramBundle);
    }
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
    paramBundle.putBoolean("SidecarFragment.notifyListenerOfStateChange", this.mNotifyListenerOfStateChange);
  }
  
  public void restoreFromSavedInstanceState(Bundle paramBundle)
  {
    this.mState = paramBundle.getInt("SidecarFragment.state");
    this.mSubstate = paramBundle.getInt("SidecarFragment.substate");
    this.mNotifyListenerOfStateChange = paramBundle.getBoolean("SidecarFragment.notifyListenerOfStateChange");
    if (this.mState == 1)
    {
      Log.d("SidecarFragment", "Restoring after serialization in RUNNING, resetting to INIT.");
      setState(0, 0);
    }
    while (!this.mNotifyListenerOfStateChange) {
      return;
    }
    notifyListener();
  }
  
  public final void setListener(Listener paramListener)
  {
    this.mListener = paramListener;
    if ((this.mListener != null) && (this.mCreated) && (this.mNotifyListenerOfStateChange)) {
      notifyListener();
    }
  }
  
  public final void setState(int paramInt1, int paramInt2)
  {
    if ((paramInt1 < 0) || (paramInt2 < 0))
    {
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = Integer.valueOf(paramInt1);
      arrayOfObject[1] = Integer.valueOf(paramInt2);
      throw new IllegalArgumentException(String.format("State / substate should be >= 0, state=%d, substate=%d", arrayOfObject));
    }
    if (Looper.myLooper() != Looper.getMainLooper()) {
      throw new IllegalStateException("This method must be called from the UI thread.");
    }
    if ((paramInt1 != this.mState) || (paramInt2 != this.mSubstate))
    {
      this.mState = paramInt1;
      this.mSubstate = paramInt2;
      this.mNotifyListenerOfStateChange = true;
      notifyListener();
    }
  }
  
  public static abstract interface Listener
  {
    public abstract void onStateChange(SidecarFragment paramSidecarFragment);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.common.sidecar.SidecarFragment
 * JD-Core Version:    0.7.0.1
 */