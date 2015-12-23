package com.google.android.gms.people.identity.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class AccountToken
  implements SafeParcelable
{
  public static final zza CREATOR = new zza();
  final int mVersionCode;
  final String zzVh;
  final String zzbsk;
  
  AccountToken(int paramInt, String paramString1, String paramString2)
  {
    this.mVersionCode = paramInt;
    this.zzVh = paramString1;
    this.zzbsk = paramString2;
  }
  
  public final int describeContents()
  {
    return 0;
  }
  
  public final void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zza.zza$6247cd09(this, paramParcel);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.people.identity.internal.AccountToken
 * JD-Core Version:    0.7.0.1
 */