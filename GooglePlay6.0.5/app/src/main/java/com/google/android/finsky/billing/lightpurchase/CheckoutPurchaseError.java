package com.google.android.finsky.billing.lightpurchase;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.finsky.protos.Purchase.PreparePurchaseResponse;
import com.google.android.finsky.protos.Purchase.RecoverableErrorPrompt;
import com.google.android.finsky.utils.ParcelableProto;

public class CheckoutPurchaseError
  implements Parcelable
{
  public static final Parcelable.Creator<CheckoutPurchaseError> CREATOR = new Parcelable.Creator() {};
  public final String continueButtonLabel;
  public final String errorMessageHtml;
  public final String errorTitle;
  public final int permissionError;
  public final Purchase.PreparePurchaseResponse preparePurchaseResponse;
  public final String purchaseContextToken;
  
  public CheckoutPurchaseError()
  {
    this(null);
  }
  
  public CheckoutPurchaseError(int paramInt, Purchase.RecoverableErrorPrompt paramRecoverableErrorPrompt, String paramString, Purchase.PreparePurchaseResponse paramPreparePurchaseResponse)
  {
    this(paramInt, paramRecoverableErrorPrompt.title, paramRecoverableErrorPrompt.errorMessageHtml, paramRecoverableErrorPrompt.buttonLabel, paramString, paramPreparePurchaseResponse);
  }
  
  public CheckoutPurchaseError(int paramInt, String paramString)
  {
    this(paramInt, null, paramString, null, null, null);
  }
  
  public CheckoutPurchaseError(int paramInt, String paramString1, String paramString2, String paramString3, String paramString4, Purchase.PreparePurchaseResponse paramPreparePurchaseResponse)
  {
    this.permissionError = paramInt;
    this.errorTitle = paramString1;
    this.errorMessageHtml = paramString2;
    this.continueButtonLabel = paramString3;
    this.purchaseContextToken = paramString4;
    this.preparePurchaseResponse = paramPreparePurchaseResponse;
  }
  
  public CheckoutPurchaseError(String paramString)
  {
    this(0, paramString);
  }
  
  public CheckoutPurchaseError(String paramString1, String paramString2)
  {
    this(0, paramString1, paramString2, null, null, null);
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeInt(this.permissionError);
    paramParcel.writeString(this.errorTitle);
    paramParcel.writeString(this.errorMessageHtml);
    paramParcel.writeString(this.continueButtonLabel);
    paramParcel.writeString(this.purchaseContextToken);
    paramParcel.writeParcelable(ParcelableProto.forProto(this.preparePurchaseResponse), paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.lightpurchase.CheckoutPurchaseError
 * JD-Core Version:    0.7.0.1
 */