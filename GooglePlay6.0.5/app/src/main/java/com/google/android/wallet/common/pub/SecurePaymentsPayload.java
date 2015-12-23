package com.google.android.wallet.common.pub;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class SecurePaymentsPayload
  implements Parcelable
{
  public static final Parcelable.Creator<SecurePaymentsPayload> CREATOR = new Parcelable.Creator() {};
  public final byte[] opaqueToken;
  public final SecurePaymentsData[] secureData;
  
  public SecurePaymentsPayload(byte[] paramArrayOfByte, SecurePaymentsData[] paramArrayOfSecurePaymentsData)
  {
    if (paramArrayOfByte == null) {
      throw new IllegalArgumentException("opaqueToken must not be null");
    }
    this.opaqueToken = paramArrayOfByte;
    this.secureData = paramArrayOfSecurePaymentsData;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeByteArray(this.opaqueToken);
    int i = this.secureData.length;
    paramParcel.writeInt(i);
    for (int j = 0; j < i; j++)
    {
      paramParcel.writeInt(this.secureData[j].key);
      paramParcel.writeString(this.secureData[j].value);
    }
  }
  
  public static final class SecurePaymentsData
  {
    public final int key;
    public final String value;
    
    public SecurePaymentsData(int paramInt, String paramString)
    {
      if (paramInt <= 0) {
        throw new IllegalArgumentException("SecurePaymentsData.key must be > 0");
      }
      if (paramString == null) {
        throw new IllegalArgumentException("SecurePaymentsData.value must not be null");
      }
      this.key = paramInt;
      this.value = paramString;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.common.pub.SecurePaymentsPayload
 * JD-Core Version:    0.7.0.1
 */