package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class PurchaseDetails
  extends MessageNano
{
  public boolean hasLegalDocumentBrokerId = false;
  public boolean hasPurchaseAuthenticationRequired = false;
  public boolean hasShowAgeConfirmationPrompt = false;
  public long legalDocumentBrokerId = 0L;
  public boolean purchaseAuthenticationRequired = false;
  public boolean showAgeConfirmationPrompt = false;
  
  public PurchaseDetails()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasLegalDocumentBrokerId) || (this.legalDocumentBrokerId != 0L)) {
      i += CodedOutputByteBufferNano.computeInt64Size(1, this.legalDocumentBrokerId);
    }
    if ((this.hasShowAgeConfirmationPrompt) || (this.showAgeConfirmationPrompt)) {
      i += 1 + CodedOutputByteBufferNano.computeTagSize(33);
    }
    if ((this.hasPurchaseAuthenticationRequired) || (this.purchaseAuthenticationRequired)) {
      i += 1 + CodedOutputByteBufferNano.computeTagSize(34);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasLegalDocumentBrokerId) || (this.legalDocumentBrokerId != 0L)) {
      paramCodedOutputByteBufferNano.writeInt64(1, this.legalDocumentBrokerId);
    }
    if ((this.hasShowAgeConfirmationPrompt) || (this.showAgeConfirmationPrompt)) {
      paramCodedOutputByteBufferNano.writeBool(33, this.showAgeConfirmationPrompt);
    }
    if ((this.hasPurchaseAuthenticationRequired) || (this.purchaseAuthenticationRequired)) {
      paramCodedOutputByteBufferNano.writeBool(34, this.purchaseAuthenticationRequired);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.PurchaseDetails
 * JD-Core Version:    0.7.0.1
 */