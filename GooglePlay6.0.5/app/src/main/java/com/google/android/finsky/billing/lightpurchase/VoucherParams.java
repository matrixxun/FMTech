package com.google.android.finsky.billing.lightpurchase;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class VoucherParams
  implements Parcelable
{
  public static final Parcelable.Creator<VoucherParams> CREATOR = new Parcelable.Creator() {};
  public final boolean autoApply;
  public final boolean hasVouchers;
  public final String selectedVoucherId;
  
  private VoucherParams(Parcel paramParcel)
  {
    this.selectedVoucherId = paramParcel.readString();
    int j;
    if (paramParcel.readByte() == i)
    {
      j = i;
      this.autoApply = j;
      if (paramParcel.readByte() != i) {
        break label48;
      }
    }
    for (;;)
    {
      this.hasVouchers = i;
      return;
      j = 0;
      break;
      label48:
      i = 0;
    }
  }
  
  public VoucherParams(String paramString, boolean paramBoolean1, boolean paramBoolean2)
  {
    this.selectedVoucherId = paramString;
    this.autoApply = paramBoolean1;
    this.hasVouchers = paramBoolean2;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    int i = 1;
    paramParcel.writeString(this.selectedVoucherId);
    int j;
    if (this.autoApply)
    {
      j = i;
      paramParcel.writeByte((byte)j);
      if (!this.hasVouchers) {
        break label47;
      }
    }
    for (;;)
    {
      paramParcel.writeByte((byte)i);
      return;
      j = 0;
      break;
      label47:
      i = 0;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.lightpurchase.VoucherParams
 * JD-Core Version:    0.7.0.1
 */