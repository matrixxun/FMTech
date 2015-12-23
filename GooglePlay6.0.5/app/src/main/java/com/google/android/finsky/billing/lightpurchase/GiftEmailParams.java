package com.google.android.finsky.billing.lightpurchase;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;

public class GiftEmailParams
  implements Parcelable
{
  public static final Parcelable.Creator<GiftEmailParams> CREATOR = new Parcelable.Creator() {};
  public final String giftMessage;
  public final String recipientEmailAddress;
  public final String senderName;
  
  private GiftEmailParams(Parcel paramParcel)
  {
    this.senderName = paramParcel.readString();
    this.recipientEmailAddress = paramParcel.readString();
    this.giftMessage = paramParcel.readString();
  }
  
  private GiftEmailParams(Builder paramBuilder)
  {
    this.senderName = paramBuilder.mSenderName;
    if (TextUtils.isEmpty(this.senderName)) {
      throw new IllegalArgumentException("senderName cannot be empty");
    }
    this.recipientEmailAddress = paramBuilder.mRecipientEmailAddress;
    if (TextUtils.isEmpty(this.recipientEmailAddress)) {
      throw new IllegalArgumentException("recipientEmailAddress cannot be empty");
    }
    this.giftMessage = paramBuilder.mGiftMessage;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(this.senderName);
    paramParcel.writeString(this.recipientEmailAddress);
    paramParcel.writeString(this.giftMessage);
  }
  
  public static final class Builder
  {
    public String mGiftMessage;
    public String mRecipientEmailAddress;
    public String mSenderName;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.lightpurchase.GiftEmailParams
 * JD-Core Version:    0.7.0.1
 */