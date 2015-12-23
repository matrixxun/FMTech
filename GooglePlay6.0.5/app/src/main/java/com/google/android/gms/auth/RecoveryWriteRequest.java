package com.google.android.gms.auth;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class RecoveryWriteRequest
  implements SafeParcelable
{
  public static final RecoveryWriteRequestCreator CREATOR = new RecoveryWriteRequestCreator();
  public String mAccount;
  public boolean mIsBroadUse;
  public String mPhoneCountryCode;
  public String mPhoneNumber;
  public String mSecondaryEmail;
  final int mVersionCode;
  
  public RecoveryWriteRequest()
  {
    this.mVersionCode = 1;
  }
  
  RecoveryWriteRequest(int paramInt, String paramString1, String paramString2, String paramString3, String paramString4, boolean paramBoolean)
  {
    this.mVersionCode = paramInt;
    this.mAccount = paramString1;
    this.mSecondaryEmail = paramString2;
    this.mPhoneNumber = paramString3;
    this.mPhoneCountryCode = paramString4;
    this.mIsBroadUse = paramBoolean;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    RecoveryWriteRequestCreator.zza$4479f008(this, paramParcel);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.auth.RecoveryWriteRequest
 * JD-Core Version:    0.7.0.1
 */