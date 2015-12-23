package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class GetFamilyPurchaseSettingResponse
  extends MessageNano
{
  public FamilyPurchaseSetting familyPurchaseSetting = null;
  public boolean hasLearnMoreText = false;
  public boolean hasPurchaseSettingSubtitle = false;
  public boolean hasPurchaseSettingTitle = false;
  public String learnMoreText = "";
  public FamilyPurchaseSettingOption[] purchaseOption = FamilyPurchaseSettingOption.emptyArray();
  public String purchaseSettingSubtitle = "";
  public String purchaseSettingTitle = "";
  
  public GetFamilyPurchaseSettingResponse()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if (this.familyPurchaseSetting != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(1, this.familyPurchaseSetting);
    }
    if ((this.purchaseOption != null) && (this.purchaseOption.length > 0)) {
      for (int j = 0; j < this.purchaseOption.length; j++)
      {
        FamilyPurchaseSettingOption localFamilyPurchaseSettingOption = this.purchaseOption[j];
        if (localFamilyPurchaseSettingOption != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(2, localFamilyPurchaseSettingOption);
        }
      }
    }
    if ((this.hasPurchaseSettingTitle) || (!this.purchaseSettingTitle.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(3, this.purchaseSettingTitle);
    }
    if ((this.hasPurchaseSettingSubtitle) || (!this.purchaseSettingSubtitle.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(4, this.purchaseSettingSubtitle);
    }
    if ((this.hasLearnMoreText) || (!this.learnMoreText.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(5, this.learnMoreText);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if (this.familyPurchaseSetting != null) {
      paramCodedOutputByteBufferNano.writeMessage(1, this.familyPurchaseSetting);
    }
    if ((this.purchaseOption != null) && (this.purchaseOption.length > 0)) {
      for (int i = 0; i < this.purchaseOption.length; i++)
      {
        FamilyPurchaseSettingOption localFamilyPurchaseSettingOption = this.purchaseOption[i];
        if (localFamilyPurchaseSettingOption != null) {
          paramCodedOutputByteBufferNano.writeMessage(2, localFamilyPurchaseSettingOption);
        }
      }
    }
    if ((this.hasPurchaseSettingTitle) || (!this.purchaseSettingTitle.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(3, this.purchaseSettingTitle);
    }
    if ((this.hasPurchaseSettingSubtitle) || (!this.purchaseSettingSubtitle.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(4, this.purchaseSettingSubtitle);
    }
    if ((this.hasLearnMoreText) || (!this.learnMoreText.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(5, this.learnMoreText);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.GetFamilyPurchaseSettingResponse
 * JD-Core Version:    0.7.0.1
 */