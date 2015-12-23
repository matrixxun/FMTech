package com.google.android.gms.ads.internal.client;

import android.content.Context;
import android.content.res.Resources;
import android.os.Parcel;
import android.util.DisplayMetrics;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.internal.util.client.zza;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.zzhb;

@zzhb
public class AdSizeParcel
  implements SafeParcelable
{
  public static final zzi CREATOR = new zzi();
  public final int height;
  public final int heightPixels;
  public final int versionCode;
  public final int width;
  public final int widthPixels;
  public final String zzuA;
  public final boolean zzuB;
  public final AdSizeParcel[] zzuC;
  public final boolean zzuD;
  public final boolean zzuE;
  public boolean zzuF;
  
  public AdSizeParcel()
  {
    this(5, "interstitial_mb", 0, 0, true, 0, 0, null, false, false, false);
  }
  
  AdSizeParcel(int paramInt1, String paramString, int paramInt2, int paramInt3, boolean paramBoolean1, int paramInt4, int paramInt5, AdSizeParcel[] paramArrayOfAdSizeParcel, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
  {
    this.versionCode = paramInt1;
    this.zzuA = paramString;
    this.height = paramInt2;
    this.heightPixels = paramInt3;
    this.zzuB = paramBoolean1;
    this.width = paramInt4;
    this.widthPixels = paramInt5;
    this.zzuC = paramArrayOfAdSizeParcel;
    this.zzuD = paramBoolean2;
    this.zzuE = paramBoolean3;
    this.zzuF = paramBoolean4;
  }
  
  public AdSizeParcel(Context paramContext, AdSize paramAdSize)
  {
    this(paramContext, new AdSize[] { paramAdSize });
  }
  
  public AdSizeParcel(Context paramContext, AdSize[] paramArrayOfAdSize)
  {
    AdSize localAdSize = paramArrayOfAdSize[0];
    this.versionCode = 5;
    this.zzuB = false;
    boolean bool;
    label72:
    int i;
    label83:
    int j;
    label95:
    DisplayMetrics localDisplayMetrics;
    label153:
    int m;
    label193:
    int n;
    if ((localAdSize.zzoM == -3) && (localAdSize.zzoN == -4))
    {
      bool = true;
      this.zzuE = bool;
      if (!this.zzuE) {
        break label320;
      }
      this.width = AdSize.BANNER.zzoM;
      this.height = AdSize.BANNER.zzoN;
      if (this.width != -1) {
        break label339;
      }
      i = 1;
      if (this.height != -2) {
        break label345;
      }
      j = 1;
      localDisplayMetrics = paramContext.getResources().getDisplayMetrics();
      if (i == 0) {
        break label363;
      }
      zzl.zzcX();
      if (!zza.zzV(paramContext)) {
        break label351;
      }
      zzl.zzcX();
      if (!zza.zzW(paramContext)) {
        break label351;
      }
      int i3 = localDisplayMetrics.widthPixels;
      zzl.zzcX();
      this.widthPixels = (i3 - zza.zzX(paramContext));
      double d = this.widthPixels / localDisplayMetrics.density;
      int i2 = (int)d;
      if (d - (int)d >= 0.01D) {
        i2++;
      }
      m = i2;
      if (j == 0) {
        break label393;
      }
      n = zzc(localDisplayMetrics);
      label205:
      zzl.zzcX();
      this.heightPixels = zza.zza(localDisplayMetrics, n);
      if ((i == 0) && (j == 0)) {
        break label402;
      }
      this.zzuA = (m + "x" + n + "_as");
    }
    for (;;)
    {
      if (paramArrayOfAdSize.length <= 1) {
        break label429;
      }
      this.zzuC = new AdSizeParcel[paramArrayOfAdSize.length];
      for (int i1 = 0; i1 < paramArrayOfAdSize.length; i1++) {
        this.zzuC[i1] = new AdSizeParcel(paramContext, paramArrayOfAdSize[i1]);
      }
      bool = false;
      break;
      label320:
      this.width = localAdSize.zzoM;
      this.height = localAdSize.zzoN;
      break label72;
      label339:
      i = 0;
      break label83;
      label345:
      j = 0;
      break label95;
      label351:
      this.widthPixels = localDisplayMetrics.widthPixels;
      break label153;
      label363:
      int k = this.width;
      zzl.zzcX();
      this.widthPixels = zza.zza(localDisplayMetrics, this.width);
      m = k;
      break label193;
      label393:
      n = this.height;
      break label205;
      label402:
      if (this.zzuE) {
        this.zzuA = "320x50_mb";
      } else {
        this.zzuA = localAdSize.toString();
      }
    }
    label429:
    this.zzuC = null;
    this.zzuD = false;
    this.zzuF = false;
  }
  
  public static int zza(DisplayMetrics paramDisplayMetrics)
  {
    return paramDisplayMetrics.widthPixels;
  }
  
  public static int zzb(DisplayMetrics paramDisplayMetrics)
  {
    return (int)(zzc(paramDisplayMetrics) * paramDisplayMetrics.density);
  }
  
  private static int zzc(DisplayMetrics paramDisplayMetrics)
  {
    int i = (int)(paramDisplayMetrics.heightPixels / paramDisplayMetrics.density);
    if (i <= 400) {
      return 32;
    }
    if (i <= 720) {
      return 50;
    }
    return 90;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzi.zza(this, paramParcel, paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.ads.internal.client.AdSizeParcel
 * JD-Core Version:    0.7.0.1
 */