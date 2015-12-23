package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;
import java.util.Arrays;

public final class PurchaseHistoryDetails
  extends MessageNano
{
  public byte[] clientRefundContext = WireFormatNano.EMPTY_BYTES;
  public boolean hasClientRefundContext = false;
  public boolean hasPurchaseDetailsHtml = false;
  public boolean hasPurchaseStatus = false;
  public boolean hasPurchaseTimestampMsec = false;
  public boolean hasTitleBylineHtml = false;
  public Common.Offer offer = null;
  public String purchaseDetailsHtml = "";
  public Common.Image purchaseDetailsImage = null;
  public String purchaseStatus = "";
  public long purchaseTimestampMsec = 0L;
  public String titleBylineHtml = "";
  
  public PurchaseHistoryDetails()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasPurchaseTimestampMsec) || (this.purchaseTimestampMsec != 0L)) {
      i += CodedOutputByteBufferNano.computeInt64Size(2, this.purchaseTimestampMsec);
    }
    if ((this.hasPurchaseDetailsHtml) || (!this.purchaseDetailsHtml.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(3, this.purchaseDetailsHtml);
    }
    if (this.offer != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(5, this.offer);
    }
    if ((this.hasPurchaseStatus) || (!this.purchaseStatus.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(6, this.purchaseStatus);
    }
    if ((this.hasTitleBylineHtml) || (!this.titleBylineHtml.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(7, this.titleBylineHtml);
    }
    if ((this.hasClientRefundContext) || (!Arrays.equals(this.clientRefundContext, WireFormatNano.EMPTY_BYTES))) {
      i += CodedOutputByteBufferNano.computeBytesSize(8, this.clientRefundContext);
    }
    if (this.purchaseDetailsImage != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(9, this.purchaseDetailsImage);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasPurchaseTimestampMsec) || (this.purchaseTimestampMsec != 0L)) {
      paramCodedOutputByteBufferNano.writeInt64(2, this.purchaseTimestampMsec);
    }
    if ((this.hasPurchaseDetailsHtml) || (!this.purchaseDetailsHtml.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(3, this.purchaseDetailsHtml);
    }
    if (this.offer != null) {
      paramCodedOutputByteBufferNano.writeMessage(5, this.offer);
    }
    if ((this.hasPurchaseStatus) || (!this.purchaseStatus.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(6, this.purchaseStatus);
    }
    if ((this.hasTitleBylineHtml) || (!this.titleBylineHtml.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(7, this.titleBylineHtml);
    }
    if ((this.hasClientRefundContext) || (!Arrays.equals(this.clientRefundContext, WireFormatNano.EMPTY_BYTES))) {
      paramCodedOutputByteBufferNano.writeBytes(8, this.clientRefundContext);
    }
    if (this.purchaseDetailsImage != null) {
      paramCodedOutputByteBufferNano.writeMessage(9, this.purchaseDetailsImage);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.PurchaseHistoryDetails
 * JD-Core Version:    0.7.0.1
 */