package com.google.android.gms.wearable;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.zze;

public final class DataEventBuffer
  extends zze<DataEvent>
  implements Result
{
  private final Status zzUc;
  
  public DataEventBuffer(DataHolder paramDataHolder)
  {
    super(paramDataHolder);
    this.zzUc = new Status(paramDataHolder.zzakr);
  }
  
  public final Status getStatus()
  {
    return this.zzUc;
  }
  
  protected final String zzpK()
  {
    return "path";
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.DataEventBuffer
 * JD-Core Version:    0.7.0.1
 */