package com.google.android.gms.internal;

import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.util.SparseArray;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzx;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;

public final class zzns
  extends Fragment
  implements DialogInterface.OnCancelListener
{
  private static final GoogleApiAvailability zzaqC = ;
  public boolean mStarted;
  public boolean zzaqD;
  private int zzaqE = -1;
  private ConnectionResult zzaqF;
  private final Handler zzaqG = new Handler(Looper.getMainLooper());
  private zznh zzaqH;
  public final SparseArray<zza> zzaqI = new SparseArray();
  
  private void zza(int paramInt, ConnectionResult paramConnectionResult)
  {
    Log.w("GmsSupportLifecycleFrag", "Unresolved error while connecting client. Stopping auto-manage.");
    zza localzza1 = (zza)this.zzaqI.get(paramInt);
    if (localzza1 != null)
    {
      zza localzza2 = (zza)this.zzaqI.get(paramInt);
      this.zzaqI.remove(paramInt);
      if (localzza2 != null)
      {
        localzza2.zzaqK.unregisterConnectionFailedListener(localzza2);
        localzza2.zzaqK.disconnect();
      }
      GoogleApiClient.OnConnectionFailedListener localOnConnectionFailedListener = localzza1.zzaqL;
      if (localOnConnectionFailedListener != null) {
        localOnConnectionFailedListener.onConnectionFailed(paramConnectionResult);
      }
    }
    zzps();
  }
  
  public static zzns zzb(FragmentActivity paramFragmentActivity)
  {
    zzx.zzcx("Must be called from main thread of process");
    FragmentManager localFragmentManager = paramFragmentActivity.getSupportFragmentManager();
    try
    {
      zzns localzzns = (zzns)localFragmentManager.findFragmentByTag("GmsSupportLifecycleFrag");
      if ((localzzns == null) || (localzzns.mRemoving)) {
        localzzns = null;
      }
      return localzzns;
    }
    catch (ClassCastException localClassCastException)
    {
      throw new IllegalStateException("Fragment with tag GmsSupportLifecycleFrag is not a SupportLifecycleFragment", localClassCastException);
    }
  }
  
  public static zzns zzc(FragmentActivity paramFragmentActivity)
  {
    zzns localzzns = zzb(paramFragmentActivity);
    FragmentManager localFragmentManager = paramFragmentActivity.getSupportFragmentManager();
    if (localzzns == null)
    {
      localzzns = new zzns();
      localFragmentManager.beginTransaction().add(localzzns, "GmsSupportLifecycleFrag").commitAllowingStateLoss();
      localFragmentManager.executePendingTransactions();
    }
    return localzzns;
  }
  
  private void zzps()
  {
    this.zzaqD = false;
    this.zzaqE = -1;
    this.zzaqF = null;
    if (this.zzaqH != null)
    {
      this.zzaqH.unregister();
      this.zzaqH = null;
    }
    for (int i = 0; i < this.zzaqI.size(); i++) {
      ((zza)this.zzaqI.valueAt(i)).zzaqK.connect();
    }
  }
  
  public final void dump(String paramString, FileDescriptor paramFileDescriptor, PrintWriter paramPrintWriter, String[] paramArrayOfString)
  {
    super.dump(paramString, paramFileDescriptor, paramPrintWriter, paramArrayOfString);
    for (int i = 0; i < this.zzaqI.size(); i++)
    {
      zza localzza = (zza)this.zzaqI.valueAt(i);
      paramPrintWriter.append(paramString).append("GoogleApiClient #").print(localzza.zzaqJ);
      paramPrintWriter.println(":");
      localzza.zzaqK.dump(paramString + "  ", paramFileDescriptor, paramPrintWriter, paramArrayOfString);
    }
  }
  
  public final void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    int i = 1;
    switch (paramInt1)
    {
    }
    for (;;)
    {
      i = 0;
      do
      {
        if (i == 0) {
          break label80;
        }
        zzps();
        return;
        if (GoogleApiAvailability.isGooglePlayServicesAvailable(getActivity()) != 0) {
          break;
        }
      } while ((goto 31) || (paramInt2 == -1));
      if (paramInt2 == 0) {
        this.zzaqF = new ConnectionResult(13, null);
      }
    }
    label80:
    zza(this.zzaqE, this.zzaqF);
  }
  
  public final void onCancel(DialogInterface paramDialogInterface)
  {
    zza(this.zzaqE, new ConnectionResult(13, null));
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (paramBundle != null)
    {
      this.zzaqD = paramBundle.getBoolean("resolving_error", false);
      this.zzaqE = paramBundle.getInt("failed_client_id", -1);
      if (this.zzaqE >= 0) {
        this.zzaqF = new ConnectionResult(paramBundle.getInt("failed_status"), (PendingIntent)paramBundle.getParcelable("failed_resolution"));
      }
    }
  }
  
  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putBoolean("resolving_error", this.zzaqD);
    if (this.zzaqE >= 0)
    {
      paramBundle.putInt("failed_client_id", this.zzaqE);
      paramBundle.putInt("failed_status", this.zzaqF.zzakr);
      paramBundle.putParcelable("failed_resolution", this.zzaqF.mPendingIntent);
    }
  }
  
  public final void onStart()
  {
    super.onStart();
    this.mStarted = true;
    if (!this.zzaqD) {
      for (int i = 0; i < this.zzaqI.size(); i++) {
        ((zza)this.zzaqI.valueAt(i)).zzaqK.connect();
      }
    }
  }
  
  public final void onStop()
  {
    super.onStop();
    this.mStarted = false;
    for (int i = 0; i < this.zzaqI.size(); i++) {
      ((zza)this.zzaqI.valueAt(i)).zzaqK.disconnect();
    }
  }
  
  private final class zza
    implements GoogleApiClient.OnConnectionFailedListener
  {
    public final int zzaqJ;
    public final GoogleApiClient zzaqK;
    public final GoogleApiClient.OnConnectionFailedListener zzaqL;
    
    public zza(int paramInt, GoogleApiClient paramGoogleApiClient, GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener)
    {
      this.zzaqJ = paramInt;
      this.zzaqK = paramGoogleApiClient;
      this.zzaqL = paramOnConnectionFailedListener;
      paramGoogleApiClient.registerConnectionFailedListener(this);
    }
    
    public final void onConnectionFailed(ConnectionResult paramConnectionResult)
    {
      zzns.zzd(zzns.this).post(new zzns.zzb(zzns.this, this.zzaqJ, paramConnectionResult));
    }
  }
  
  private final class zzb
    implements Runnable
  {
    private final int zzaqN;
    private final ConnectionResult zzaqO;
    
    public zzb(int paramInt, ConnectionResult paramConnectionResult)
    {
      this.zzaqN = paramInt;
      this.zzaqO = paramConnectionResult;
    }
    
    public final void run()
    {
      if ((!zzns.zza(zzns.this)) || (zzns.zzb(zzns.this))) {}
      for (;;)
      {
        return;
        zzns.zza$7b9ee69f(zzns.this);
        zzns.zza(zzns.this, this.zzaqN);
        zzns.zza(zzns.this, this.zzaqO);
        if (this.zzaqO.hasResolution()) {
          try
          {
            int i = 1 + (1 + zzns.this.getActivity().getSupportFragmentManager().getFragments().indexOf(zzns.this) << 16);
            ConnectionResult localConnectionResult = this.zzaqO;
            FragmentActivity localFragmentActivity = zzns.this.getActivity();
            if (localConnectionResult.hasResolution())
            {
              localFragmentActivity.startIntentSenderForResult(localConnectionResult.mPendingIntent.getIntentSender(), i, null, 0, 0, 0);
              return;
            }
          }
          catch (IntentSender.SendIntentException localSendIntentException)
          {
            zzns.zzc(zzns.this);
            return;
          }
        }
      }
      zzns.zzpt();
      if (GoogleApiAvailability.isUserResolvableError(this.zzaqO.zzakr))
      {
        GooglePlayServicesUtil.showErrorDialogFragment(this.zzaqO.zzakr, zzns.this.getActivity(), zzns.this, 2, zzns.this);
        return;
      }
      if (this.zzaqO.zzakr == 18)
      {
        zzns.zzpt();
        final Dialog localDialog = GoogleApiAvailability.zza(zzns.this.getActivity(), zzns.this);
        zzns.zza(zzns.this, zznh.zza(zzns.this.getActivity().getApplicationContext(), new zznh()
        {
          protected final void zzpl()
          {
            zzns.zzc(zzns.this);
            localDialog.dismiss();
          }
        }));
        return;
      }
      zzns.zza(zzns.this, this.zzaqN, this.zzaqO);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzns
 * JD-Core Version:    0.7.0.1
 */