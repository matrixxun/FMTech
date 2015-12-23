package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class AvailablePromoOffer
  extends MessageNano
{
  private static volatile AvailablePromoOffer[] _emptyArray;
  public AddCreditCardPromoOffer addCreditCardOffer = null;
  
  public AvailablePromoOffer()
  {
    this.cachedSize = -1;
  }
  
  public static AvailablePromoOffer[] emptyArray()
  {
    if (_emptyArray == null) {}
    synchronized (InternalNano.LAZY_INIT_LOCK)
    {
      if (_emptyArray == null) {
        _emptyArray = new AvailablePromoOffer[0];
      }
      return _emptyArray;
    }
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if (this.addCreditCardOffer != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(1, this.addCreditCardOffer);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if (this.addCreditCardOffer != null) {
      paramCodedOutputByteBufferNano.writeMessage(1, this.addCreditCardOffer);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.AvailablePromoOffer
 * JD-Core Version:    0.7.0.1
 */