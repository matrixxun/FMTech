package com.google.android.gms.ads.doubleclick;

import android.content.Context;
import android.os.RemoteException;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.Correlator;
import com.google.android.gms.ads.internal.client.zzj;
import com.google.android.gms.ads.internal.client.zzz;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.internal.zzcn;

public final class PublisherAdView
  extends ViewGroup
{
  private final zzz zzoP;
  
  public final AdListener getAdListener()
  {
    return this.zzoP.zztT;
  }
  
  public final AdSize getAdSize()
  {
    return this.zzoP.getAdSize();
  }
  
  public final AdSize[] getAdSizes()
  {
    return this.zzoP.zzuH;
  }
  
  public final String getAdUnitId()
  {
    return this.zzoP.zzqm;
  }
  
  public final AppEventListener getAppEventListener()
  {
    return this.zzoP.zzuG;
  }
  
  public final String getMediationAdapterClassName()
  {
    return this.zzoP.getMediationAdapterClassName();
  }
  
  public final OnCustomRenderedAdLoadedListener getOnCustomRenderedAdLoadedListener()
  {
    return this.zzoP.zzvg;
  }
  
  protected final void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    View localView = getChildAt(0);
    if ((localView != null) && (localView.getVisibility() != 8))
    {
      int i = localView.getMeasuredWidth();
      int j = localView.getMeasuredHeight();
      int k = (paramInt3 - paramInt1 - i) / 2;
      int m = (paramInt4 - paramInt2 - j) / 2;
      localView.layout(k, m, i + k, j + m);
    }
  }
  
  protected final void onMeasure(int paramInt1, int paramInt2)
  {
    View localView = getChildAt(0);
    int j;
    int i;
    if ((localView != null) && (localView.getVisibility() != 8))
    {
      measureChild(localView, paramInt1, paramInt2);
      j = localView.getMeasuredWidth();
      i = localView.getMeasuredHeight();
    }
    for (;;)
    {
      int k = Math.max(j, getSuggestedMinimumWidth());
      int m = Math.max(i, getSuggestedMinimumHeight());
      setMeasuredDimension(View.resolveSize(k, paramInt1), View.resolveSize(m, paramInt2));
      return;
      AdSize localAdSize = getAdSize();
      if (localAdSize != null)
      {
        Context localContext = getContext();
        j = localAdSize.getWidthInPixels(localContext);
        i = localAdSize.getHeightInPixels(localContext);
      }
      else
      {
        i = 0;
        j = 0;
      }
    }
  }
  
  public final void setAdListener(AdListener paramAdListener)
  {
    this.zzoP.setAdListener(paramAdListener);
  }
  
  public final void setAdSizes(AdSize... paramVarArgs)
  {
    if ((paramVarArgs == null) || (paramVarArgs.length <= 0)) {
      throw new IllegalArgumentException("The supported ad sizes must contain at least one valid ad size.");
    }
    this.zzoP.zza(paramVarArgs);
  }
  
  public final void setAdUnitId(String paramString)
  {
    this.zzoP.setAdUnitId(paramString);
  }
  
  public final void setAppEventListener(AppEventListener paramAppEventListener)
  {
    zzz localzzz = this.zzoP;
    try
    {
      localzzz.zzuG = paramAppEventListener;
      if ((localzzz.zzvb != null) && (paramAppEventListener != null)) {
        new zzj(paramAppEventListener);
      }
      return;
    }
    catch (RemoteException localRemoteException)
    {
      zzb.w("Failed to set the AppEventListener.", localRemoteException);
    }
  }
  
  public final void setCorrelator(Correlator paramCorrelator)
  {
    this.zzoP.zzvh = paramCorrelator;
  }
  
  public final void setManualImpressionsEnabled(boolean paramBoolean)
  {
    this.zzoP.zzpY = paramBoolean;
  }
  
  public final void setOnCustomRenderedAdLoadedListener(OnCustomRenderedAdLoadedListener paramOnCustomRenderedAdLoadedListener)
  {
    zzz localzzz = this.zzoP;
    localzzz.zzvg = paramOnCustomRenderedAdLoadedListener;
    try
    {
      if ((localzzz.zzvb != null) && (paramOnCustomRenderedAdLoadedListener != null)) {
        new zzcn(paramOnCustomRenderedAdLoadedListener);
      }
      return;
    }
    catch (RemoteException localRemoteException)
    {
      zzb.w("Failed to set the onCustomRenderedAdLoadedListener.", localRemoteException);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.ads.doubleclick.PublisherAdView
 * JD-Core Version:    0.7.0.1
 */