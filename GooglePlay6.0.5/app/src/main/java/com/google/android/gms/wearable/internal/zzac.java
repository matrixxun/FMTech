package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.zzc;
import com.google.android.gms.wearable.DataItemAsset;

public final class zzac
  extends zzc
  implements DataItemAsset
{
  public zzac(DataHolder paramDataHolder, int paramInt)
  {
    super(paramDataHolder, paramInt);
  }
  
  public final String getDataItemKey()
  {
    return getString("asset_key");
  }
  
  public final String getId()
  {
    return getString("asset_id");
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.zzac
 * JD-Core Version:    0.7.0.1
 */