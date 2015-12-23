package com.google.android.finsky.billing.giftcard;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.finsky.protos.Link;
import com.google.android.finsky.utils.ParcelableProto;

public class RedeemCodeResult
  implements Parcelable
{
  public static final Parcelable.Creator<RedeemCodeResult> CREATOR = new Parcelable.Creator() {};
  public final Bundle mExtraPurchaseData;
  public final boolean mIsInstallAppDeferred;
  public final Link mLink;
  public final String mStoredValueInstrumentId;
  private final String mVoucherId;
  
  public RedeemCodeResult(Parcel paramParcel)
  {
    this.mStoredValueInstrumentId = paramParcel.readString();
    if (paramParcel.readByte() == i) {}
    for (;;)
    {
      this.mIsInstallAppDeferred = i;
      this.mExtraPurchaseData = paramParcel.readBundle();
      this.mLink = ((Link)ParcelableProto.getProtoFromParcel(paramParcel));
      this.mVoucherId = paramParcel.readString();
      return;
      i = 0;
    }
  }
  
  public RedeemCodeResult(String paramString1, boolean paramBoolean, Bundle paramBundle, Link paramLink, String paramString2)
  {
    this.mStoredValueInstrumentId = paramString1;
    this.mIsInstallAppDeferred = paramBoolean;
    this.mExtraPurchaseData = paramBundle;
    this.mLink = paramLink;
    this.mVoucherId = paramString2;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public final String getVoucherId()
  {
    if (this.mIsInstallAppDeferred) {
      return this.mVoucherId;
    }
    return null;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(this.mStoredValueInstrumentId);
    if (this.mIsInstallAppDeferred) {}
    for (int i = 1;; i = 0)
    {
      paramParcel.writeByte((byte)i);
      paramParcel.writeBundle(this.mExtraPurchaseData);
      paramParcel.writeParcelable(ParcelableProto.forProto(this.mLink), 0);
      paramParcel.writeString(this.mVoucherId);
      return;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.giftcard.RedeemCodeResult
 * JD-Core Version:    0.7.0.1
 */