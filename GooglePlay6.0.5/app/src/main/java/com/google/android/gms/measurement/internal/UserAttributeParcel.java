package com.google.android.gms.measurement.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzx;

public class UserAttributeParcel
  implements SafeParcelable
{
  public static final zzad CREATOR = new zzad();
  public final String name;
  public final String stringValue;
  public final int versionCode;
  public final String zzbmA;
  public final long zzboS;
  public final Long zzboT;
  public final Float zzboU;
  
  UserAttributeParcel(int paramInt, String paramString1, long paramLong, Long paramLong1, Float paramFloat, String paramString2, String paramString3)
  {
    this.versionCode = paramInt;
    this.name = paramString1;
    this.zzboS = paramLong;
    this.zzboT = paramLong1;
    this.zzboU = paramFloat;
    this.stringValue = paramString2;
    this.zzbmA = paramString3;
  }
  
  UserAttributeParcel(String paramString1, long paramLong, Object paramObject, String paramString2)
  {
    zzx.zzcG(paramString1);
    this.versionCode = 1;
    this.name = paramString1;
    this.zzboS = paramLong;
    this.zzbmA = paramString2;
    if (paramObject == null)
    {
      this.zzboT = null;
      this.zzboU = null;
      this.stringValue = null;
      return;
    }
    if ((paramObject instanceof Long))
    {
      this.zzboT = ((Long)paramObject);
      this.zzboU = null;
      this.stringValue = null;
      return;
    }
    if ((paramObject instanceof Float))
    {
      this.zzboT = null;
      this.zzboU = ((Float)paramObject);
      this.stringValue = null;
      return;
    }
    if ((paramObject instanceof String))
    {
      this.zzboT = null;
      this.zzboU = null;
      this.stringValue = ((String)paramObject);
      return;
    }
    throw new IllegalArgumentException("User attribute given of un-supported type");
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public final Object getValue()
  {
    if (this.zzboT != null) {
      return this.zzboT;
    }
    if (this.zzboU != null) {
      return this.zzboU;
    }
    if (this.stringValue != null) {
      return this.stringValue;
    }
    return null;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzad.zza$2732f0dd(this, paramParcel);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.measurement.internal.UserAttributeParcel
 * JD-Core Version:    0.7.0.1
 */