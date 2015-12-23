package com.google.android.gms.common.api;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;
import com.google.android.gms.common.internal.zzw.zza;
import java.util.Arrays;

public final class Status
  implements Result, SafeParcelable
{
  public static final Parcelable.Creator<Status> CREATOR = new zzb();
  public static final Status zzaoA;
  public static final Status zzaoB;
  public static final Status zzaoC;
  public static final Status zzaoD;
  public static final Status zzaoz = new Status(0);
  final PendingIntent mPendingIntent;
  final int mVersionCode;
  public final int zzakr;
  public final String zzanv;
  
  static
  {
    zzaoA = new Status(14);
    zzaoB = new Status(8);
    zzaoC = new Status(15);
    zzaoD = new Status(16);
  }
  
  public Status(int paramInt)
  {
    this(paramInt, null);
  }
  
  Status(int paramInt1, int paramInt2, String paramString, PendingIntent paramPendingIntent)
  {
    this.mVersionCode = paramInt1;
    this.zzakr = paramInt2;
    this.zzanv = paramString;
    this.mPendingIntent = paramPendingIntent;
  }
  
  public Status(int paramInt, String paramString)
  {
    this(1, paramInt, paramString, null);
  }
  
  public Status(int paramInt, String paramString, PendingIntent paramPendingIntent)
  {
    this(1, paramInt, paramString, paramPendingIntent);
  }
  
  public final int describeContents()
  {
    return 0;
  }
  
  public final boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof Status)) {}
    Status localStatus;
    do
    {
      return false;
      localStatus = (Status)paramObject;
    } while ((this.mVersionCode != localStatus.mVersionCode) || (this.zzakr != localStatus.zzakr) || (!zzw.equal(this.zzanv, localStatus.zzanv)) || (!zzw.equal(this.mPendingIntent, localStatus.mPendingIntent)));
    return true;
  }
  
  public final Status getStatus()
  {
    return this;
  }
  
  public final int hashCode()
  {
    Object[] arrayOfObject = new Object[4];
    arrayOfObject[0] = Integer.valueOf(this.mVersionCode);
    arrayOfObject[1] = Integer.valueOf(this.zzakr);
    arrayOfObject[2] = this.zzanv;
    arrayOfObject[3] = this.mPendingIntent;
    return Arrays.hashCode(arrayOfObject);
  }
  
  public final boolean isSuccess()
  {
    return this.zzakr <= 0;
  }
  
  public final String toString()
  {
    zzw.zza localzza = zzw.zzB(this);
    if (this.zzanv != null) {}
    for (String str = this.zzanv;; str = CommonStatusCodes.getStatusCodeString(this.zzakr)) {
      return localzza.zzh("statusCode", str).zzh("resolution", this.mPendingIntent).toString();
    }
  }
  
  public final void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzb.zza(this, paramParcel, paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.api.Status
 * JD-Core Version:    0.7.0.1
 */