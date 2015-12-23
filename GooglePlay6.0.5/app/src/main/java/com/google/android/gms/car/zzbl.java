package com.google.android.gms.car;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzbl
  implements Parcelable.Creator<RadioStationInfo.MetaData>
{
  static void zza(RadioStationInfo.MetaData paramMetaData, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramMetaData.audioChannels);
    zzb.zzc(paramParcel, 1000, paramMetaData.mVersionCode);
    zzb.zzc(paramParcel, 2, paramMetaData.signalQuality);
    zzb.zza$377a007(paramParcel, 3, paramMetaData.rdsData, paramInt);
    zzb.zza$377a007(paramParcel, 4, paramMetaData.hdData, paramInt);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.zzbl
 * JD-Core Version:    0.7.0.1
 */