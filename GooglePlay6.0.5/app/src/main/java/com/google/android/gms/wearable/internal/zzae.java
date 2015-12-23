package com.google.android.gms.wearable.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.wearable.DataItemAsset;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class zzae
  implements Parcelable.Creator<DataItemParcelable>
{
  static void zza(DataItemParcelable paramDataItemParcelable, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramDataItemParcelable.mVersionCode);
    zzb.zza$377a007(paramParcel, 2, paramDataItemParcelable.mUri, paramInt);
    Bundle localBundle = new Bundle();
    localBundle.setClassLoader(DataItemAssetParcelable.class.getClassLoader());
    Iterator localIterator = paramDataItemParcelable.zzcgg.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      localBundle.putParcelable((String)localEntry.getKey(), new DataItemAssetParcelable((DataItemAsset)localEntry.getValue()));
    }
    zzb.zza$f7bef55(paramParcel, 4, localBundle);
    zzb.zza$52910762(paramParcel, 5, paramDataItemParcelable.mData);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.zzae
 * JD-Core Version:    0.7.0.1
 */