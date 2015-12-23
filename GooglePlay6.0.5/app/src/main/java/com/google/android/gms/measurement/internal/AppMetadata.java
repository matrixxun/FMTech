package com.google.android.gms.measurement.internal;

import android.os.Parcel;
import android.text.TextUtils;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzx;

public class AppMetadata
  implements SafeParcelable
{
  public static final zzb CREATOR = new zzb();
  public final String packageName;
  public final int versionCode;
  public final String zzbcL;
  public final String zzbmb;
  public final String zzbmc;
  public final long zzbmd;
  public final long zzbme;
  public final String zzbmf;
  public final boolean zzbmg;
  
  AppMetadata(int paramInt, String paramString1, String paramString2, String paramString3, String paramString4, long paramLong1, long paramLong2, String paramString5, boolean paramBoolean)
  {
    this.versionCode = paramInt;
    this.packageName = paramString1;
    this.zzbmb = paramString2;
    this.zzbcL = paramString3;
    this.zzbmc = paramString4;
    this.zzbmd = paramLong1;
    this.zzbme = paramLong2;
    this.zzbmf = paramString5;
    if (paramInt >= 3)
    {
      this.zzbmg = paramBoolean;
      return;
    }
    this.zzbmg = true;
  }
  
  AppMetadata(String paramString1, String paramString2, String paramString3, String paramString4, long paramLong1, long paramLong2, String paramString5, boolean paramBoolean)
  {
    zzx.zzcG(paramString1);
    this.versionCode = 3;
    this.packageName = paramString1;
    if (TextUtils.isEmpty(paramString2)) {
      paramString2 = null;
    }
    this.zzbmb = paramString2;
    this.zzbcL = paramString3;
    this.zzbmc = paramString4;
    this.zzbmd = paramLong1;
    this.zzbme = paramLong2;
    this.zzbmf = paramString5;
    this.zzbmg = paramBoolean;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzb.zza$4fcb2b27(this, paramParcel);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.measurement.internal.AppMetadata
 * JD-Core Version:    0.7.0.1
 */