package com.google.android.gms.ads.internal.client;

import android.content.Context;
import android.os.RemoteException;
import android.view.ViewGroup;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.Correlator;
import com.google.android.gms.ads.doubleclick.AppEventListener;
import com.google.android.gms.ads.doubleclick.OnCustomRenderedAdLoadedListener;
import com.google.android.gms.ads.purchase.InAppPurchaseListener;
import com.google.android.gms.ads.purchase.PlayStorePurchaseListener;
import com.google.android.gms.internal.zzhb;

@zzhb
public final class zzz
{
  public boolean zzpY;
  public String zzqm;
  private zza zztS;
  public AdListener zztT;
  public AppEventListener zzuG;
  public AdSize[] zzuH;
  public zzs zzvb;
  private ViewGroup zzvd;
  public InAppPurchaseListener zzve;
  public PlayStorePurchaseListener zzvf;
  public OnCustomRenderedAdLoadedListener zzvg;
  public Correlator zzvh;
  private boolean zzvi;
  
  public final AdSize getAdSize()
  {
    try
    {
      if (this.zzvb != null)
      {
        AdSizeParcel localAdSizeParcel = this.zzvb.zzbb();
        if (localAdSizeParcel != null)
        {
          AdSize localAdSize = new AdSize(localAdSizeParcel.width, localAdSizeParcel.height, localAdSizeParcel.zzuA);
          return localAdSize;
        }
      }
    }
    catch (RemoteException localRemoteException)
    {
      com.google.android.gms.ads.internal.util.client.zzb.w("Failed to get the current AdSize.", localRemoteException);
      if (this.zzuH != null) {
        return this.zzuH[0];
      }
    }
    return null;
  }
  
  public final String getMediationAdapterClassName()
  {
    try
    {
      if (this.zzvb != null)
      {
        String str = this.zzvb.getMediationAdapterClassName();
        return str;
      }
    }
    catch (RemoteException localRemoteException)
    {
      com.google.android.gms.ads.internal.util.client.zzb.w("Failed to get the mediation adapter class name.", localRemoteException);
    }
    return null;
  }
  
  public final void setAdListener(AdListener paramAdListener)
  {
    try
    {
      this.zztT = paramAdListener;
      if ((this.zzvb != null) && (paramAdListener != null)) {
        new zzc(paramAdListener);
      }
      return;
    }
    catch (RemoteException localRemoteException)
    {
      com.google.android.gms.ads.internal.util.client.zzb.w("Failed to set the AdListener.", localRemoteException);
    }
  }
  
  public final void setAdSizes(AdSize... paramVarArgs)
  {
    if (this.zzuH != null) {
      throw new IllegalStateException("The ad size can only be set once on AdView.");
    }
    zza(paramVarArgs);
  }
  
  public final void setAdUnitId(String paramString)
  {
    if (this.zzqm != null) {
      throw new IllegalStateException("The ad unit ID can only be set once on AdView.");
    }
    this.zzqm = paramString;
  }
  
  public final void zza(zza paramzza)
  {
    try
    {
      this.zztS = paramzza;
      if ((this.zzvb != null) && (paramzza != null)) {
        new zzb(paramzza);
      }
      return;
    }
    catch (RemoteException localRemoteException)
    {
      com.google.android.gms.ads.internal.util.client.zzb.w("Failed to set the AdClickListener.", localRemoteException);
    }
  }
  
  public final void zza(AdSize... paramVarArgs)
  {
    this.zzuH = paramVarArgs;
    try
    {
      if (this.zzvb != null)
      {
        Context localContext = this.zzvd.getContext();
        AdSize[] arrayOfAdSize = this.zzuH;
        boolean bool = this.zzvi;
        new AdSizeParcel(localContext, arrayOfAdSize).zzuF = bool;
      }
      this.zzvd.requestLayout();
      return;
    }
    catch (RemoteException localRemoteException)
    {
      for (;;)
      {
        com.google.android.gms.ads.internal.util.client.zzb.w("Failed to set the ad size.", localRemoteException);
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.ads.internal.client.zzz
 * JD-Core Version:    0.7.0.1
 */