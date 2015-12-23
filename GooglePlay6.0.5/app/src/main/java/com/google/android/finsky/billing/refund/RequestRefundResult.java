package com.google.android.finsky.billing.refund;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class RequestRefundResult
  implements Parcelable
{
  public static final Parcelable.Creator<RequestRefundResult> CREATOR = new Parcelable.Creator() {};
  private int mRefundResultId;
  
  public RequestRefundResult(Parcel paramParcel)
  {
    this.mRefundResultId = paramParcel.readInt();
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeInt(this.mRefundResultId);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.refund.RequestRefundResult
 * JD-Core Version:    0.7.0.1
 */