package com.google.android.gms.auth;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class Country
  implements SafeParcelable
{
  public static final CountryCreator CREATOR = new CountryCreator();
  public String mCode;
  public String mName;
  final int mVersionCode;
  
  public Country()
  {
    this.mVersionCode = 1;
  }
  
  Country(int paramInt, String paramString1, String paramString2)
  {
    this.mVersionCode = paramInt;
    this.mName = paramString1;
    this.mCode = paramString2;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    CountryCreator.zza$53a79cc9(this, paramParcel);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.auth.Country
 * JD-Core Version:    0.7.0.1
 */