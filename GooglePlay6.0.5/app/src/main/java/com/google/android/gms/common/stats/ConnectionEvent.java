package com.google.android.gms.common.stats;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class ConnectionEvent
  extends zzf
  implements SafeParcelable
{
  public static final Parcelable.Creator<ConnectionEvent> CREATOR = new zza();
  final int mVersionCode;
  final long zzavM;
  int zzavN;
  final String zzavO;
  final String zzavP;
  final String zzavQ;
  final String zzavR;
  final String zzavS;
  final String zzavT;
  final long zzavU;
  final long zzavV;
  private long zzavW;
  
  ConnectionEvent(int paramInt1, long paramLong1, int paramInt2, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, long paramLong2, long paramLong3)
  {
    this.mVersionCode = paramInt1;
    this.zzavM = paramLong1;
    this.zzavN = paramInt2;
    this.zzavO = paramString1;
    this.zzavP = paramString2;
    this.zzavQ = paramString3;
    this.zzavR = paramString4;
    this.zzavW = -1L;
    this.zzavS = paramString5;
    this.zzavT = paramString6;
    this.zzavU = paramLong2;
    this.zzavV = paramLong3;
  }
  
  public ConnectionEvent(long paramLong1, int paramInt, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, long paramLong2, long paramLong3)
  {
    this(1, paramLong1, paramInt, paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, paramLong2, paramLong3);
  }
  
  public final int describeContents()
  {
    return 0;
  }
  
  public final long getDurationMillis()
  {
    return this.zzavW;
  }
  
  public final int getEventType()
  {
    return this.zzavN;
  }
  
  public final long getTimeMillis()
  {
    return this.zzavM;
  }
  
  public final void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zza.zza$151b04f0(this, paramParcel);
  }
  
  public final String zzre()
  {
    StringBuilder localStringBuilder = new StringBuilder("\t").append(this.zzavO).append("/").append(this.zzavP).append("\t").append(this.zzavQ).append("/").append(this.zzavR).append("\t");
    if (this.zzavS == null) {}
    for (String str = "";; str = this.zzavS) {
      return str + "\t" + this.zzavV;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.stats.ConnectionEvent
 * JD-Core Version:    0.7.0.1
 */