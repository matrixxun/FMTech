package com.google.android.gms.people.model;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;
import com.google.android.gms.common.internal.zzw.zza;
import com.google.android.gms.common.internal.zzx;

public final class AvatarReference
  implements SafeParcelable
{
  public static final ParcelableAvatarReference CREATOR = new ParcelableAvatarReference();
  final int mVersionCode;
  final String zzbwK;
  final int zzvA;
  
  AvatarReference(int paramInt1, int paramInt2, String paramString)
  {
    if (paramInt2 != 0) {}
    for (boolean bool = true;; bool = false)
    {
      zzx.zzaa(bool);
      this.mVersionCode = paramInt1;
      this.zzvA = paramInt2;
      this.zzbwK = paramString;
      return;
    }
  }
  
  public final int describeContents()
  {
    return 0;
  }
  
  public final String toString()
  {
    return zzw.zzB(this).zzh("source", Integer.valueOf(this.zzvA)).zzh("location", this.zzbwK).toString();
  }
  
  public final void writeToParcel(Parcel paramParcel, int paramInt)
  {
    ParcelableAvatarReference.zza$f2d72cc(this, paramParcel);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.people.model.AvatarReference
 * JD-Core Version:    0.7.0.1
 */