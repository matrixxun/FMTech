package com.google.android.gms.wearable.internal;

import android.content.IntentFilter;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class AddListenerRequest
  implements SafeParcelable
{
  public static final Parcelable.Creator<AddListenerRequest> CREATOR = new zzc();
  final int mVersionCode;
  public final zzba zzceU;
  public final IntentFilter[] zzceV;
  public final String zzceW;
  public final String zzceX;
  
  AddListenerRequest(int paramInt, IBinder paramIBinder, IntentFilter[] paramArrayOfIntentFilter, String paramString1, String paramString2)
  {
    this.mVersionCode = paramInt;
    if (paramIBinder != null) {}
    for (this.zzceU = zzba.zza.zzib(paramIBinder);; this.zzceU = null)
    {
      this.zzceV = paramArrayOfIntentFilter;
      this.zzceW = paramString1;
      this.zzceX = paramString2;
      return;
    }
  }
  
  public AddListenerRequest(zzcf paramzzcf)
  {
    this.mVersionCode = 1;
    this.zzceU = paramzzcf;
    this.zzceV = paramzzcf.zzchi;
    this.zzceW = paramzzcf.zzchR;
    this.zzceX = null;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzc.zza(this, paramParcel, paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.AddListenerRequest
 * JD-Core Version:    0.7.0.1
 */