package com.google.android.gms.common;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;
import com.google.android.gms.common.internal.zzw.zza;
import java.util.Arrays;

public final class ConnectionResult
  implements SafeParcelable
{
  public static final Parcelable.Creator<ConnectionResult> CREATOR = new zzb();
  public static final ConnectionResult zzanu = new ConnectionResult();
  public final PendingIntent mPendingIntent;
  final int mVersionCode;
  public final int zzakr;
  final String zzanv;
  
  private ConnectionResult()
  {
    this(0, null, (byte)0);
  }
  
  ConnectionResult(int paramInt1, int paramInt2, PendingIntent paramPendingIntent, String paramString)
  {
    this.mVersionCode = paramInt1;
    this.zzakr = paramInt2;
    this.mPendingIntent = paramPendingIntent;
    this.zzanv = paramString;
  }
  
  public ConnectionResult(int paramInt, PendingIntent paramPendingIntent)
  {
    this(paramInt, paramPendingIntent, (byte)0);
  }
  
  private ConnectionResult(int paramInt, PendingIntent paramPendingIntent, byte paramByte)
  {
    this(1, paramInt, paramPendingIntent, null);
  }
  
  public final int describeContents()
  {
    return 0;
  }
  
  public final boolean equals(Object paramObject)
  {
    if (paramObject == this) {}
    ConnectionResult localConnectionResult;
    do
    {
      return true;
      if (!(paramObject instanceof ConnectionResult)) {
        return false;
      }
      localConnectionResult = (ConnectionResult)paramObject;
    } while ((this.zzakr == localConnectionResult.zzakr) && (zzw.equal(this.mPendingIntent, localConnectionResult.mPendingIntent)) && (zzw.equal(this.zzanv, localConnectionResult.zzanv)));
    return false;
  }
  
  public final boolean hasResolution()
  {
    return (this.zzakr != 0) && (this.mPendingIntent != null);
  }
  
  public final int hashCode()
  {
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = Integer.valueOf(this.zzakr);
    arrayOfObject[1] = this.mPendingIntent;
    arrayOfObject[2] = this.zzanv;
    return Arrays.hashCode(arrayOfObject);
  }
  
  public final boolean isSuccess()
  {
    return this.zzakr == 0;
  }
  
  public final String toString()
  {
    zzw.zza localzza = zzw.zzB(this);
    int i = this.zzakr;
    String str;
    switch (i)
    {
    case 12: 
    default: 
      str = "UNKNOWN_ERROR_CODE(" + i + ")";
    }
    for (;;)
    {
      return localzza.zzh("statusCode", str).zzh("resolution", this.mPendingIntent).zzh("message", this.zzanv).toString();
      str = "SUCCESS";
      continue;
      str = "SERVICE_MISSING";
      continue;
      str = "SERVICE_VERSION_UPDATE_REQUIRED";
      continue;
      str = "SERVICE_DISABLED";
      continue;
      str = "SIGN_IN_REQUIRED";
      continue;
      str = "INVALID_ACCOUNT";
      continue;
      str = "RESOLUTION_REQUIRED";
      continue;
      str = "NETWORK_ERROR";
      continue;
      str = "INTERNAL_ERROR";
      continue;
      str = "SERVICE_INVALID";
      continue;
      str = "DEVELOPER_ERROR";
      continue;
      str = "LICENSE_CHECK_FAILED";
      continue;
      str = "CANCELED";
      continue;
      str = "TIMEOUT";
      continue;
      str = "INTERRUPTED";
      continue;
      str = "API_UNAVAILABLE";
      continue;
      str = "SIGN_IN_FAILED";
      continue;
      str = "SERVICE_UPDATING";
      continue;
      str = "SERVICE_MISSING_PERMISSION";
    }
  }
  
  public final void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzb.zza(this, paramParcel, paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.ConnectionResult
 * JD-Core Version:    0.7.0.1
 */