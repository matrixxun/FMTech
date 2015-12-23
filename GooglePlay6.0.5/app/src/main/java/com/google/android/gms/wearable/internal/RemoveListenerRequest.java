package com.google.android.gms.wearable.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class RemoveListenerRequest
  implements SafeParcelable
{
  public static final Parcelable.Creator<RemoveListenerRequest> CREATOR = new zzbv();
  final int mVersionCode;
  public final zzba zzceU;
  
  RemoveListenerRequest(int paramInt, IBinder paramIBinder)
  {
    this.mVersionCode = paramInt;
    if (paramIBinder != null)
    {
      this.zzceU = zzba.zza.zzib(paramIBinder);
      return;
    }
    this.zzceU = null;
  }
  
  public RemoveListenerRequest(zzba paramzzba)
  {
    this.mVersionCode = 1;
    this.zzceU = paramzzba;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzbv.zza$1160eef7(this, paramParcel);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.RemoveListenerRequest
 * JD-Core Version:    0.7.0.1
 */