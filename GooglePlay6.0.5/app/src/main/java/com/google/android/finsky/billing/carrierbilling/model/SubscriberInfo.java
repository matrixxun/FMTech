package com.google.android.finsky.billing.carrierbilling.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.Base64;
import com.google.android.finsky.utils.Objects;
import com.google.android.finsky.utils.Utils;
import java.util.Arrays;

public class SubscriberInfo
  implements Parcelable
{
  public static final Parcelable.Creator<SubscriberInfo> CREATOR = new Parcelable.Creator() {};
  public final String mAddress1;
  public final String mAddress2;
  public final String mCity;
  public final String mCountry;
  public final String mIdentifier;
  public final String mName;
  public final String mPostalCode;
  public final String mState;
  
  private SubscriberInfo(Parcel paramParcel)
  {
    this.mName = paramParcel.readString();
    this.mIdentifier = paramParcel.readString();
    this.mAddress1 = paramParcel.readString();
    this.mAddress2 = paramParcel.readString();
    this.mCity = paramParcel.readString();
    this.mState = paramParcel.readString();
    this.mPostalCode = paramParcel.readString();
    this.mCountry = paramParcel.readString();
  }
  
  private SubscriberInfo(Builder paramBuilder)
  {
    this.mName = paramBuilder.name;
    this.mIdentifier = paramBuilder.identifier;
    this.mAddress1 = paramBuilder.address1;
    this.mAddress2 = paramBuilder.address2;
    this.mCity = paramBuilder.city;
    this.mState = paramBuilder.state;
    this.mPostalCode = paramBuilder.postalCode;
    this.mCountry = paramBuilder.country;
  }
  
  private static String switchChars(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder(paramString.length());
    char[] arrayOfChar = paramString.toCharArray();
    int i = arrayOfChar.length;
    int j = 0;
    if (j < i)
    {
      char c = arrayOfChar[j];
      if (((c >= 'a') && (c <= 'm')) || ((c >= 'A') && (c <= 'M'))) {
        c = (char)(c + '\r');
      }
      for (;;)
      {
        localStringBuilder.append(c);
        j++;
        break;
        if (((c >= 'n') && (c <= 'z')) || ((c >= 'N') && (c <= 'Z'))) {
          c = (char)(c - '\r');
        } else if ((c >= '0') && (c <= '4')) {
          c = (char)(c + '\005');
        } else if ((c >= '5') && (c <= '9')) {
          c = (char)(c - '\005');
        }
      }
    }
    return localStringBuilder.toString();
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    SubscriberInfo localSubscriberInfo;
    do
    {
      return true;
      if (!(paramObject instanceof SubscriberInfo)) {
        return false;
      }
      localSubscriberInfo = (SubscriberInfo)paramObject;
    } while ((Objects.equal(this.mName, localSubscriberInfo.mName)) && (Objects.equal(this.mIdentifier, localSubscriberInfo.mIdentifier)) && (Objects.equal(this.mAddress1, localSubscriberInfo.mAddress1)) && (Objects.equal(this.mAddress2, localSubscriberInfo.mAddress2)) && (Objects.equal(this.mCity, localSubscriberInfo.mCity)) && (Objects.equal(this.mState, localSubscriberInfo.mState)) && (Objects.equal(this.mPostalCode, localSubscriberInfo.mPostalCode)) && (Objects.equal(this.mCountry, localSubscriberInfo.mCountry)));
    return false;
  }
  
  public int hashCode()
  {
    Object[] arrayOfObject = new Object[8];
    arrayOfObject[0] = this.mName;
    arrayOfObject[1] = this.mIdentifier;
    arrayOfObject[2] = this.mAddress1;
    arrayOfObject[3] = this.mAddress2;
    arrayOfObject[4] = this.mCity;
    arrayOfObject[5] = this.mState;
    arrayOfObject[6] = this.mPostalCode;
    arrayOfObject[7] = this.mCountry;
    return Arrays.hashCode(arrayOfObject);
  }
  
  public String toString()
  {
    String str1;
    String str2;
    label20:
    String str3;
    label30:
    String str4;
    label41:
    String str5;
    label52:
    String str6;
    label63:
    String str7;
    if (this.mName == null)
    {
      str1 = "";
      if (this.mIdentifier != null) {
        break label196;
      }
      str2 = "";
      if (this.mAddress1 != null) {
        break label207;
      }
      str3 = "";
      if (this.mAddress2 != null) {
        break label218;
      }
      str4 = "";
      if (this.mCity != null) {
        break label230;
      }
      str5 = "";
      if (this.mState != null) {
        break label242;
      }
      str6 = "";
      if (this.mPostalCode != null) {
        break label254;
      }
      str7 = "";
      label74:
      if (this.mCountry != null) {
        break label266;
      }
    }
    label266:
    for (String str8 = "";; str8 = Utils.urlEncode(this.mCountry))
    {
      return switchChars(new String(Base64.encode((str1 + "," + str2 + "," + str3 + "," + str4 + "," + str5 + "," + str6 + "," + str7 + "," + str8).getBytes(), 0)));
      str1 = Utils.urlEncode(this.mName);
      break;
      label196:
      str2 = Utils.urlEncode(this.mIdentifier);
      break label20;
      label207:
      str3 = Utils.urlEncode(this.mAddress1);
      break label30;
      label218:
      str4 = Utils.urlEncode(this.mAddress2);
      break label41;
      label230:
      str5 = Utils.urlEncode(this.mCity);
      break label52;
      label242:
      str6 = Utils.urlEncode(this.mState);
      break label63;
      label254:
      str7 = Utils.urlEncode(this.mPostalCode);
      break label74;
    }
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(this.mName);
    paramParcel.writeString(this.mIdentifier);
    paramParcel.writeString(this.mAddress1);
    paramParcel.writeString(this.mAddress2);
    paramParcel.writeString(this.mCity);
    paramParcel.writeString(this.mState);
    paramParcel.writeString(this.mPostalCode);
    paramParcel.writeString(this.mCountry);
  }
  
  public static final class Builder
  {
    public String address1;
    public String address2;
    public String city;
    public String country;
    public String identifier;
    public String name;
    public String postalCode;
    public String state;
    
    public final SubscriberInfo build()
    {
      return new SubscriberInfo(this, (byte)0);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.carrierbilling.model.SubscriberInfo
 * JD-Core Version:    0.7.0.1
 */