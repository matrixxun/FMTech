package com.google.android.gms.people.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;
import com.google.android.gms.common.internal.zzw.zza;

public class ParcelableLoadImageOptions
  implements SafeParcelable
{
  public static final zzk CREATOR = new zzk();
  final boolean mUseLargePictureForCp2Images;
  final int mVersionCode;
  final int zzbsM;
  final int zzbsN;
  
  ParcelableLoadImageOptions(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
  {
    this.mVersionCode = paramInt1;
    this.zzbsM = paramInt2;
    this.zzbsN = paramInt3;
    this.mUseLargePictureForCp2Images = paramBoolean;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public String toString()
  {
    return zzw.zzB(this).zzh("imageSize", Integer.valueOf(this.zzbsM)).zzh("avatarOptions", Integer.valueOf(this.zzbsN)).zzh("useLargePictureForCp2Images", Boolean.valueOf(this.mUseLargePictureForCp2Images)).toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzk.zza$277d357a(this, paramParcel);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.people.internal.ParcelableLoadImageOptions
 * JD-Core Version:    0.7.0.1
 */