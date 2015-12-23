package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzx;
import java.util.Iterator;
import java.util.Set;

public class EventParams
  implements SafeParcelable, Iterable<String>
{
  public static final zzi CREATOR = new zzi();
  public final int versionCode;
  final Bundle zzbmw;
  
  EventParams(int paramInt, Bundle paramBundle)
  {
    this.versionCode = paramInt;
    this.zzbmw = paramBundle;
  }
  
  EventParams(Bundle paramBundle)
  {
    zzx.zzC(paramBundle);
    this.zzbmw = paramBundle;
    this.versionCode = 1;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public Iterator<String> iterator()
  {
    new Iterator()
    {
      Iterator<String> zzbmx = EventParams.zza(EventParams.this).keySet().iterator();
      
      public final boolean hasNext()
      {
        return this.zzbmx.hasNext();
      }
      
      public final void remove()
      {
        throw new UnsupportedOperationException("Remove not supported");
      }
    };
  }
  
  public String toString()
  {
    return this.zzbmw.toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzi.zza$789d8329(this, paramParcel);
  }
  
  public final Bundle zzCk()
  {
    return new Bundle(this.zzbmw);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.measurement.internal.EventParams
 * JD-Core Version:    0.7.0.1
 */