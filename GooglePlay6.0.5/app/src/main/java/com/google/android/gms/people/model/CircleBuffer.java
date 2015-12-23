package com.google.android.gms.people.model;

import com.google.android.gms.common.data.DataHolder;

public final class CircleBuffer
  extends DataBufferWithSyncInfo<Circle>
{
  public CircleBuffer(DataHolder paramDataHolder)
  {
    super(paramDataHolder);
  }
  
  public final String toString()
  {
    return "Circles:size=" + getCount();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.people.model.CircleBuffer
 * JD-Core Version:    0.7.0.1
 */