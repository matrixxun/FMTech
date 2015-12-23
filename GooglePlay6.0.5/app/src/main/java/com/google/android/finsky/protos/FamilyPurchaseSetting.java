package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class FamilyPurchaseSetting
  extends MessageNano
{
  public String consistencyToken = "";
  public String docid = "";
  public boolean hasConsistencyToken = false;
  public boolean hasDocid = false;
  public boolean hasMode = false;
  public boolean hasSelectedPurchaseOptionId = false;
  public int mode = 0;
  public int selectedPurchaseOptionId = 0;
  
  public FamilyPurchaseSetting()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasDocid) || (!this.docid.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(1, this.docid);
    }
    if ((this.mode != 0) || (this.hasMode)) {
      i += CodedOutputByteBufferNano.computeInt32Size(2, this.mode);
    }
    if ((this.hasConsistencyToken) || (!this.consistencyToken.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(3, this.consistencyToken);
    }
    if ((this.hasSelectedPurchaseOptionId) || (this.selectedPurchaseOptionId != 0)) {
      i += CodedOutputByteBufferNano.computeInt32Size(4, this.selectedPurchaseOptionId);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasDocid) || (!this.docid.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(1, this.docid);
    }
    if ((this.mode != 0) || (this.hasMode)) {
      paramCodedOutputByteBufferNano.writeInt32(2, this.mode);
    }
    if ((this.hasConsistencyToken) || (!this.consistencyToken.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(3, this.consistencyToken);
    }
    if ((this.hasSelectedPurchaseOptionId) || (this.selectedPurchaseOptionId != 0)) {
      paramCodedOutputByteBufferNano.writeInt32(4, this.selectedPurchaseOptionId);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.FamilyPurchaseSetting
 * JD-Core Version:    0.7.0.1
 */