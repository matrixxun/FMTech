package com.google.android.finsky.billing.lightpurchase;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.protos.Common.Docid;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.Purchase.InAppPurchaseInfo;
import com.google.android.finsky.utils.ParcelableProto;

public class PurchaseParams
  implements Parcelable
{
  public static final Parcelable.Creator<PurchaseParams> CREATOR = new Parcelable.Creator() {};
  public final String appContinueUrl;
  public final Boolean appEverExternallyHosted;
  public final int appInstallLocation;
  public final String appTitle;
  public final int appVersionCode;
  public final String callingPackage;
  public final Common.Docid docid;
  public final String docidStr;
  public final Document document;
  public final Purchase.InAppPurchaseInfo inAppPurchaseInfo;
  public final int indirectProvisioningType;
  public final String offerId;
  public final int offerType;
  public final String voucherId;
  
  private PurchaseParams(Parcel paramParcel)
  {
    this.docid = ((Common.Docid)ParcelableProto.getProtoFromParcel(paramParcel));
    this.docidStr = paramParcel.readString();
    this.document = ((Document)paramParcel.readParcelable(Document.class.getClassLoader()));
    this.offerType = paramParcel.readInt();
    this.offerId = paramParcel.readString();
    this.appVersionCode = paramParcel.readInt();
    this.appTitle = paramParcel.readString();
    this.appContinueUrl = paramParcel.readString();
    Boolean localBoolean;
    if (paramParcel.readByte() != 0) {
      if (paramParcel.readByte() != 0) {
        localBoolean = Boolean.TRUE;
      }
    }
    for (this.appEverExternallyHosted = localBoolean;; this.appEverExternallyHosted = null)
    {
      this.appInstallLocation = paramParcel.readInt();
      this.voucherId = paramParcel.readString();
      this.inAppPurchaseInfo = ((Purchase.InAppPurchaseInfo)ParcelableProto.getProtoFromParcel(paramParcel));
      this.callingPackage = paramParcel.readString();
      this.indirectProvisioningType = paramParcel.readInt();
      return;
      localBoolean = Boolean.FALSE;
      break;
    }
  }
  
  private PurchaseParams(Builder paramBuilder)
  {
    this.docid = paramBuilder.docid;
    if (this.docid == null) {
      throw new IllegalArgumentException("docid cannot be null");
    }
    this.docidStr = paramBuilder.docidStr;
    if (this.docidStr == null) {
      throw new IllegalArgumentException("docidStr cannot be null");
    }
    this.document = paramBuilder.document;
    this.offerType = paramBuilder.offerType;
    this.offerId = paramBuilder.offerId;
    this.appVersionCode = paramBuilder.appVersionCode;
    this.appTitle = paramBuilder.appTitle;
    this.appContinueUrl = paramBuilder.appContinueUrl;
    this.appEverExternallyHosted = paramBuilder.appEverExternallyHosted;
    this.appInstallLocation = paramBuilder.appInstallLocation;
    this.voucherId = paramBuilder.voucherId;
    this.inAppPurchaseInfo = paramBuilder.inAppPurchaseInfo;
    this.callingPackage = paramBuilder.callingPackage;
    this.indirectProvisioningType = paramBuilder.indirectProvisioningType;
  }
  
  public static Builder builder()
  {
    return new Builder();
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    int i = 1;
    paramParcel.writeParcelable(ParcelableProto.forProto(this.docid), paramInt);
    paramParcel.writeString(this.docidStr);
    paramParcel.writeParcelable(this.document, paramInt);
    paramParcel.writeInt(this.offerType);
    paramParcel.writeString(this.offerId);
    paramParcel.writeInt(this.appVersionCode);
    paramParcel.writeString(this.appTitle);
    paramParcel.writeString(this.appContinueUrl);
    int j;
    if (this.appEverExternallyHosted == null)
    {
      j = 0;
      paramParcel.writeByte((byte)j);
      if (this.appEverExternallyHosted != null) {
        if (!this.appEverExternallyHosted.booleanValue()) {
          break label162;
        }
      }
    }
    for (;;)
    {
      paramParcel.writeByte((byte)i);
      paramParcel.writeInt(this.appInstallLocation);
      paramParcel.writeString(this.voucherId);
      paramParcel.writeParcelable(ParcelableProto.forProto(this.inAppPurchaseInfo), paramInt);
      paramParcel.writeString(this.callingPackage);
      paramParcel.writeInt(this.indirectProvisioningType);
      return;
      j = i;
      break;
      label162:
      i = 0;
    }
  }
  
  public static final class Builder
  {
    String appContinueUrl;
    Boolean appEverExternallyHosted;
    int appInstallLocation = 0;
    String appTitle;
    int appVersionCode;
    String callingPackage;
    public Common.Docid docid;
    public String docidStr;
    Document document;
    public Purchase.InAppPurchaseInfo inAppPurchaseInfo;
    int indirectProvisioningType;
    String offerId;
    public int offerType;
    public String voucherId;
    
    public final PurchaseParams build()
    {
      return new PurchaseParams(this, (byte)0);
    }
    
    public final Builder setAppData(int paramInt, String paramString1, String paramString2)
    {
      this.appVersionCode = paramInt;
      this.appTitle = paramString1;
      this.appContinueUrl = paramString2;
      return this;
    }
    
    public final Builder setDocument(Document paramDocument)
    {
      this.docid = paramDocument.getFullDocid();
      this.docidStr = paramDocument.mDocument.docid;
      this.document = paramDocument;
      return this;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.lightpurchase.PurchaseParams
 * JD-Core Version:    0.7.0.1
 */