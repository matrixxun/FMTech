package com.google.commerce.payments.orchestration.proto.common;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public abstract interface DateOuterClass
{
  public static final class Date
    extends MessageNano
  {
    public int day = 0;
    public int month = 0;
    public int year = 0;
    
    public Date()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.year != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.year);
      }
      if (this.month != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(2, this.month);
      }
      if (this.day != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(3, this.day);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.year != 0) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.year);
      }
      if (this.month != 0) {
        paramCodedOutputByteBufferNano.writeInt32(2, this.month);
      }
      if (this.day != 0) {
        paramCodedOutputByteBufferNano.writeInt32(3, this.day);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.commerce.payments.orchestration.proto.common.DateOuterClass
 * JD-Core Version:    0.7.0.1
 */