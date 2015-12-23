package com.google.android.gms.wearable;

import android.os.Parcelable;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.data.DataBuffer;

public abstract interface LargeAssetApi
{
  public static abstract interface QueueEntryBuffer
    extends Result, DataBuffer
  {}
  
  public static abstract interface QueueState
    extends Parcelable
  {}
  
  public static abstract interface QueueStateChange
    extends Releasable
  {}
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.LargeAssetApi
 * JD-Core Version:    0.7.0.1
 */