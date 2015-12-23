package com.google.android.gms.people.model;

import com.google.android.gms.common.data.AbstractDataBuffer;
import com.google.android.gms.common.data.DataHolder;

public abstract class DataBufferWithSyncInfo<T>
  extends AbstractDataBuffer<T>
{
  protected DataBufferWithSyncInfo(DataHolder paramDataHolder)
  {
    super(paramDataHolder);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.people.model.DataBufferWithSyncInfo
 * JD-Core Version:    0.7.0.1
 */