package com.google.android.gms.people.model;

import com.google.android.gms.common.data.AbstractDataBuffer;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.people.internal.agg.PhoneEmailDecoder.EmailDecoder;
import com.google.android.gms.people.internal.agg.PhoneEmailDecoder.PhoneDecoder;
import com.google.android.gms.people.internal.zzq;

public final class PersonBuffer
  extends DataBufferWithSyncInfo<Person>
{
  private final PhoneEmailDecoder.PhoneDecoder zzbsX;
  private final PhoneEmailDecoder.EmailDecoder zzbsY;
  
  public PersonBuffer(DataHolder paramDataHolder, PhoneEmailDecoder.PhoneDecoder paramPhoneDecoder, PhoneEmailDecoder.EmailDecoder paramEmailDecoder)
  {
    super(paramDataHolder);
    this.zzbsX = paramPhoneDecoder;
    this.zzbsY = paramEmailDecoder;
  }
  
  public final Person get(int paramInt)
  {
    return new zzq(this.zzapd, paramInt, this.zzapd.zzarz, this.zzbsX, this.zzbsY);
  }
  
  public final String toString()
  {
    return "People:size=" + getCount();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.people.model.PersonBuffer
 * JD-Core Version:    0.7.0.1
 */