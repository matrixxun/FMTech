package com.google.android.gms.ads;

import android.content.Context;
import android.os.RemoteException;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.ads.internal.client.zza;
import com.google.android.gms.ads.internal.client.zzz;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.purchase.InAppPurchaseListener;
import com.google.android.gms.internal.zzgi;

class BaseAdView
  extends ViewGroup
{
  private final zzz zzoP;
  
  public AdListener getAdListener()
  {
    return this.zzoP.zztT;
  }
  
  public AdSize getAdSize()
  {
    return this.zzoP.getAdSize();
  }
  
  public String getAdUnitId()
  {
    return this.zzoP.zzqm;
  }
  
  public InAppPurchaseListener getInAppPurchaseListener()
  {
    return this.zzoP.zzve;
  }
  
  public String getMediationAdapterClassName()
  {
    return this.zzoP.getMediationAdapterClassName();
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
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
  
  protected void onMeasure(int paramInt1, int paramInt2)
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
  
  public void setAdListener(AdListener paramAdListener)
  {
    this.zzoP.setAdListener(paramAdListener);
    if ((paramAdListener != null) && ((paramAdListener instanceof zza))) {
      this.zzoP.zza((zza)paramAdListener);
    }
    while (paramAdListener != null) {
      return;
    }
    this.zzoP.zza(null);
  }
  
  public void setAdSize(AdSize paramAdSize)
  {
    this.zzoP.setAdSizes(new AdSize[] { paramAdSize });
  }
  
  public void setAdUnitId(String paramString)
  {
    this.zzoP.setAdUnitId(paramString);
  }
  
  public void setInAppPurchaseListener(InAppPurchaseListener paramInAppPurchaseListener)
  {
    zzz localzzz = this.zzoP;
    if (localzzz.zzvf != null) {
      throw new IllegalStateException("Play store purchase parameter has already been set.");
    }
    try
    {
      localzzz.zzve = paramInAppPurchaseListener;
      if ((localzzz.zzvb != null) && (paramInAppPurchaseListener != null)) {
        new zzgi(paramInAppPurchaseListener);
      }
      return;
    }
    catch (RemoteException localRemoteException)
    {
      zzb.w("Failed to set the InAppPurchaseListener.", localRemoteException);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.ads.BaseAdView
 * JD-Core Version:    0.7.0.1
 */