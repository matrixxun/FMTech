package com.google.android.gms.auth;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class RecoveryWriteResponse
  implements SafeParcelable
{
  public static final RecoveryWriteResponseCreator CREATOR = new RecoveryWriteResponseCreator();
  public String mErrorCode;
  final int mVersionCode;
  
  public RecoveryWriteResponse()
  {
    this.mVersionCode = 1;
  }
  
  RecoveryWriteResponse(int paramInt, String paramString)
  {
    this.mVersionCode = paramInt;
    this.mErrorCode = paramString;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    RecoveryWriteResponseCreator.zza$361acac(this, paramParcel);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.auth.RecoveryWriteResponse
 * JD-Core Version:    0.7.0.1
 */