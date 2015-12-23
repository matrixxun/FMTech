package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class VoucherInfo
  extends MessageNano
{
  private static volatile VoucherInfo[] _emptyArray;
  public DocV2 doc = null;
  public Common.Offer[] offer = Common.Offer.emptyArray();
  
  public VoucherInfo()
  {
    this.cachedSize = -1;
  }
  
  public static VoucherInfo[] emptyArray()
  {
    if (_emptyArray == null) {}
    synchronized (InternalNano.LAZY_INIT_LOCK)
    {
      if (_emptyArray == null) {
        _emptyArray = new VoucherInfo[0];
      }
      return _emptyArray;
    }
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if (this.doc != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(1, this.doc);
    }
    if ((this.offer != null) && (this.offer.length > 0)) {
      for (int j = 0; j < this.offer.length; j++)
      {
        Common.Offer localOffer = this.offer[j];
        if (localOffer != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(2, localOffer);
        }
      }
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if (this.doc != null) {
      paramCodedOutputByteBufferNano.writeMessage(1, this.doc);
    }
    if ((this.offer != null) && (this.offer.length > 0)) {
      for (int i = 0; i < this.offer.length; i++)
      {
        Common.Offer localOffer = this.offer[i];
        if (localOffer != null) {
          paramCodedOutputByteBufferNano.writeMessage(2, localOffer);
        }
      }
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.VoucherInfo
 * JD-Core Version:    0.7.0.1
 */