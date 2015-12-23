package com.google.android.gms.common.api;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzx;

public final class Scope
  implements SafeParcelable
{
  public static final Parcelable.Creator<Scope> CREATOR = new zza();
  final int mVersionCode;
  public final String zzaoy;
  
  Scope(int paramInt, String paramString)
  {
    zzx.zzi(paramString, "scopeUri must not be null or empty");
    this.mVersionCode = paramInt;
    this.zzaoy = paramString;
  }
  
  public Scope(String paramString)
  {
    this(1, paramString);
  }
  
  public final int describeContents()
  {
    return 0;
  }
  
  public final boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof Scope)) {
      return false;
    }
    return this.zzaoy.equals(((Scope)paramObject).zzaoy);
  }
  
  public final int hashCode()
  {
    return this.zzaoy.hashCode();
  }
  
  public final String toString()
  {
    return this.zzaoy;
  }
  
  public final void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zza.zza$514aa83(this, paramParcel);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.api.Scope
 * JD-Core Version:    0.7.0.1
 */