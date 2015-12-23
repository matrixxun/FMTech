package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;

public final class BillingAddressSpec
  extends MessageNano
{
  public int billingAddressType = 1;
  public boolean hasBillingAddressType = false;
  public int[] requiredField = WireFormatNano.EMPTY_INT_ARRAY;
  
  public BillingAddressSpec()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.billingAddressType != 1) || (this.hasBillingAddressType)) {
      i += CodedOutputByteBufferNano.computeInt32Size(1, this.billingAddressType);
    }
    if ((this.requiredField != null) && (this.requiredField.length > 0))
    {
      int j = 0;
      for (int k = 0; k < this.requiredField.length; k++) {
        j += CodedOutputByteBufferNano.computeInt32SizeNoTag(this.requiredField[k]);
      }
      i = i + j + 1 * this.requiredField.length;
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.billingAddressType != 1) || (this.hasBillingAddressType)) {
      paramCodedOutputByteBufferNano.writeInt32(1, this.billingAddressType);
    }
    if ((this.requiredField != null) && (this.requiredField.length > 0)) {
      for (int i = 0; i < this.requiredField.length; i++) {
        paramCodedOutputByteBufferNano.writeInt32(2, this.requiredField[i]);
      }
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.BillingAddressSpec
 * JD-Core Version:    0.7.0.1
 */