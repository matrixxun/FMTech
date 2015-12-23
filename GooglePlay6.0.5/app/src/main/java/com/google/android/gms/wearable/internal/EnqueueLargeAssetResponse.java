package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzx;

public final class EnqueueLargeAssetResponse
  implements SafeParcelable
{
  public static final Parcelable.Creator<EnqueueLargeAssetResponse> CREATOR = new zzah();
  final int mVersionCode;
  public final int statusCode;
  public final LargeAssetQueueEntryParcelable zzcgi;
  
  EnqueueLargeAssetResponse(int paramInt1, int paramInt2, LargeAssetQueueEntryParcelable paramLargeAssetQueueEntryParcelable)
  {
    this.mVersionCode = paramInt1;
    this.statusCode = paramInt2;
    if (paramInt2 == 0)
    {
      if (paramLargeAssetQueueEntryParcelable != null) {}
      for (;;)
      {
        zzx.zzb(bool1, "Expecting non-null queueEntry");
        this.zzcgi = paramLargeAssetQueueEntryParcelable;
        return;
        bool1 = false;
      }
    }
    if (paramLargeAssetQueueEntryParcelable == null) {}
    for (boolean bool2 = bool1;; bool2 = false)
    {
      Object[] arrayOfObject = new Object[bool1];
      arrayOfObject[0] = paramLargeAssetQueueEntryParcelable;
      zzx.zzb(bool2, "Expecting null queueEntry: %s", arrayOfObject);
      break;
    }
  }
  
  public final int describeContents()
  {
    return 0;
  }
  
  public final void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzah.zza(this, paramParcel, paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.EnqueueLargeAssetResponse
 * JD-Core Version:    0.7.0.1
 */