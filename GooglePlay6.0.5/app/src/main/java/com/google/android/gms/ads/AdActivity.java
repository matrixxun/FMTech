package com.google.android.gms.ads;

import android.app.Activity;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.internal.zzfu;
import com.google.android.gms.internal.zzfv;

public class AdActivity
  extends Activity
{
  private zzfv zzoG;
  
  private void zzaQ()
  {
    if (this.zzoG != null) {}
    try
    {
      this.zzoG.zzaQ();
      return;
    }
    catch (RemoteException localRemoteException)
    {
      zzb.w("Could not forward setContentViewSet to ad overlay:", localRemoteException);
    }
  }
  
  public void onBackPressed()
  {
    int i = 1;
    try
    {
      if (this.zzoG != null)
      {
        boolean bool = this.zzoG.zzfl();
        i = bool;
      }
    }
    catch (RemoteException localRemoteException)
    {
      for (;;)
      {
        zzb.w("Could not forward onBackPressed to ad overlay:", localRemoteException);
      }
    }
    if (i != 0) {
      super.onBackPressed();
    }
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.zzoG = zzfu.zzb(this);
    if (this.zzoG == null)
    {
      zzb.w("Could not create ad overlay.");
      finish();
      return;
    }
    try
    {
      this.zzoG.onCreate(paramBundle);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      zzb.w("Could not forward onCreate to ad overlay:", localRemoteException);
      finish();
    }
  }
  
  protected void onDestroy()
  {
    try
    {
      if (this.zzoG != null) {
        this.zzoG.onDestroy();
      }
      super.onDestroy();
      return;
    }
    catch (RemoteException localRemoteException)
    {
      for (;;)
      {
        zzb.w("Could not forward onDestroy to ad overlay:", localRemoteException);
      }
    }
  }
  
  protected void onPause()
  {
    try
    {
      if (this.zzoG != null) {
        this.zzoG.onPause();
      }
      super.onPause();
      return;
    }
    catch (RemoteException localRemoteException)
    {
      for (;;)
      {
        zzb.w("Could not forward onPause to ad overlay:", localRemoteException);
        finish();
      }
    }
  }
  
  protected void onRestart()
  {
    super.onRestart();
    try
    {
      if (this.zzoG != null) {
        this.zzoG.onRestart();
      }
      return;
    }
    catch (RemoteException localRemoteException)
    {
      zzb.w("Could not forward onRestart to ad overlay:", localRemoteException);
      finish();
    }
  }
  
  protected void onResume()
  {
    super.onResume();
    try
    {
      if (this.zzoG != null) {
        this.zzoG.onResume();
      }
      return;
    }
    catch (RemoteException localRemoteException)
    {
      zzb.w("Could not forward onResume to ad overlay:", localRemoteException);
      finish();
    }
  }
  
  protected void onSaveInstanceState(Bundle paramBundle)
  {
    try
    {
      if (this.zzoG != null) {
        this.zzoG.onSaveInstanceState(paramBundle);
      }
      super.onSaveInstanceState(paramBundle);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      for (;;)
      {
        zzb.w("Could not forward onSaveInstanceState to ad overlay:", localRemoteException);
        finish();
      }
    }
  }
  
  protected void onStart()
  {
    super.onStart();
    try
    {
      if (this.zzoG != null) {
        this.zzoG.onStart();
      }
      return;
    }
    catch (RemoteException localRemoteException)
    {
      zzb.w("Could not forward onStart to ad overlay:", localRemoteException);
      finish();
    }
  }
  
  protected void onStop()
  {
    try
    {
      if (this.zzoG != null) {
        this.zzoG.onStop();
      }
      super.onStop();
      return;
    }
    catch (RemoteException localRemoteException)
    {
      for (;;)
      {
        zzb.w("Could not forward onStop to ad overlay:", localRemoteException);
        finish();
      }
    }
  }
  
  public void setContentView(int paramInt)
  {
    super.setContentView(paramInt);
    zzaQ();
  }
  
  public void setContentView(View paramView)
  {
    super.setContentView(paramView);
    zzaQ();
  }
  
  public void setContentView(View paramView, ViewGroup.LayoutParams paramLayoutParams)
  {
    super.setContentView(paramView, paramLayoutParams);
    zzaQ();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.ads.AdActivity
 * JD-Core Version:    0.7.0.1
 */