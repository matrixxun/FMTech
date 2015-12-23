package com.google.android.gms.car;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zza
  implements Parcelable.Creator<CarAudioConfiguration>
{
  static void zza$56713635(CarAudioConfiguration paramCarAudioConfiguration, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramCarAudioConfiguration.sampleRate);
    zzb.zzc(paramParcel, 1000, paramCarAudioConfiguration.mVersionCode);
    zzb.zzc(paramParcel, 2, paramCarAudioConfiguration.channelConfig);
    zzb.zzc(paramParcel, 3, paramCarAudioConfiguration.audioFormat);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.zza
 * JD-Core Version:    0.7.0.1
 */