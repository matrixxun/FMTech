package com.google.android.gms.auth;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.List;

public class RecoveryReadResponse
  implements SafeParcelable
{
  public static final RecoveryReadResponseCreator CREATOR = new RecoveryReadResponseCreator();
  public String mAction;
  public String mAllowedOptions;
  public List<Country> mCountryList;
  public String mError;
  public String mPhoneCountryCode;
  public String mPhoneNumber;
  public String mSecondaryEmail;
  final int mVersionCode;
  
  public RecoveryReadResponse()
  {
    this.mVersionCode = 1;
  }
  
  RecoveryReadResponse(int paramInt, String paramString1, String paramString2, String paramString3, List<Country> paramList, String paramString4, String paramString5, String paramString6)
  {
    this.mVersionCode = paramInt;
    this.mSecondaryEmail = paramString1;
    this.mPhoneNumber = paramString2;
    this.mPhoneCountryCode = paramString3;
    this.mCountryList = paramList;
    this.mError = paramString4;
    this.mAction = paramString5;
    this.mAllowedOptions = paramString6;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    RecoveryReadResponseCreator.zza$d57022f(this, paramParcel);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.auth.RecoveryReadResponse
 * JD-Core Version:    0.7.0.1
 */