package com.google.android.gms.ads;

import android.content.Context;
import android.content.res.Resources;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.client.zzl;
import com.google.android.gms.ads.internal.util.client.zza;

public final class AdSize
{
  public static final AdSize BANNER = new AdSize(320, 50, "320x50_mb");
  public static final AdSize FLUID = new AdSize(-3, -4, "fluid");
  public static final AdSize FULL_BANNER = new AdSize(468, 60, "468x60_as");
  public static final AdSize LARGE_BANNER = new AdSize(320, 100, "320x100_as");
  public static final AdSize LEADERBOARD = new AdSize(728, 90, "728x90_as");
  public static final AdSize MEDIUM_RECTANGLE = new AdSize(300, 250, "300x250_as");
  public static final AdSize SMART_BANNER;
  public static final AdSize WIDE_SKYSCRAPER = new AdSize(160, 600, "160x600_as");
  public final int zzoM;
  public final int zzoN;
  private final String zzoO;
  
  static
  {
    SMART_BANNER = new AdSize(-1, -2, "smart_banner");
  }
  
  public AdSize(int paramInt1, int paramInt2) {}
  
  public AdSize(int paramInt1, int paramInt2, String paramString)
  {
    if ((paramInt1 < 0) && (paramInt1 != -1) && (paramInt1 != -3)) {
      throw new IllegalArgumentException("Invalid width for AdSize: " + paramInt1);
    }
    if ((paramInt2 < 0) && (paramInt2 != -2) && (paramInt2 != -4)) {
      throw new IllegalArgumentException("Invalid height for AdSize: " + paramInt2);
    }
    this.zzoM = paramInt1;
    this.zzoN = paramInt2;
    this.zzoO = paramString;
  }
  
  public final boolean equals(Object paramObject)
  {
    if (paramObject == this) {}
    AdSize localAdSize;
    do
    {
      return true;
      if (!(paramObject instanceof AdSize)) {
        return false;
      }
      localAdSize = (AdSize)paramObject;
    } while ((this.zzoM == localAdSize.zzoM) && (this.zzoN == localAdSize.zzoN) && (this.zzoO.equals(localAdSize.zzoO)));
    return false;
  }
  
  public final int getHeightInPixels(Context paramContext)
  {
    switch (this.zzoN)
    {
    default: 
      zzl.zzcX();
      return zza.zzb(paramContext, this.zzoN);
    case -2: 
      return AdSizeParcel.zzb(paramContext.getResources().getDisplayMetrics());
    }
    return -1;
  }
  
  public final int getWidthInPixels(Context paramContext)
  {
    switch (this.zzoM)
    {
    case -2: 
    default: 
      zzl.zzcX();
      return zza.zzb(paramContext, this.zzoM);
    case -1: 
      return AdSizeParcel.zza(paramContext.getResources().getDisplayMetrics());
    }
    return -1;
  }
  
  public final int hashCode()
  {
    return this.zzoO.hashCode();
  }
  
  public final String toString()
  {
    return this.zzoO;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.ads.AdSize
 * JD-Core Version:    0.7.0.1
 */