package com.google.android.gms.measurement.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class EventParcel
  implements SafeParcelable
{
  public static final zzj CREATOR = new zzj();
  public final String name;
  public final int versionCode;
  public final String zzbmA;
  public final long zzbmB;
  public final EventParams zzbmz;
  
  EventParcel(int paramInt, String paramString1, EventParams paramEventParams, String paramString2, long paramLong)
  {
    this.versionCode = paramInt;
    this.name = paramString1;
    this.zzbmz = paramEventParams;
    this.zzbmA = paramString2;
    this.zzbmB = paramLong;
  }
  
  public EventParcel(String paramString1, EventParams paramEventParams, String paramString2, long paramLong)
  {
    this.versionCode = 1;
    this.name = paramString1;
    this.zzbmz = paramEventParams;
    this.zzbmA = paramString2;
    this.zzbmB = paramLong;
  }
  
  public final int describeContents()
  {
    return 0;
  }
  
  public final String toString()
  {
    return "origin=" + this.zzbmA + ",name=" + this.name + ",params=" + this.zzbmz;
  }
  
  public final void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzj.zza(this, paramParcel, paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.measurement.internal.EventParcel
 * JD-Core Version:    0.7.0.1
 */