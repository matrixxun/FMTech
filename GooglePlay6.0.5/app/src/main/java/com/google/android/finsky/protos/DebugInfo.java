package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;

public final class DebugInfo
  extends MessageNano
{
  public String[] message = WireFormatNano.EMPTY_STRING_ARRAY;
  public Timing[] timing = Timing.emptyArray();
  
  public DebugInfo()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.message != null) && (this.message.length > 0))
    {
      int k = 0;
      int m = 0;
      for (int n = 0; n < this.message.length; n++)
      {
        String str = this.message[n];
        if (str != null)
        {
          k++;
          m += CodedOutputByteBufferNano.computeStringSizeNoTag(str);
        }
      }
      i = i + m + k * 1;
    }
    if ((this.timing != null) && (this.timing.length > 0)) {
      for (int j = 0; j < this.timing.length; j++)
      {
        Timing localTiming = this.timing[j];
        if (localTiming != null) {
          i += CodedOutputByteBufferNano.computeGroupSize(2, localTiming);
        }
      }
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.message != null) && (this.message.length > 0)) {
      for (int j = 0; j < this.message.length; j++)
      {
        String str = this.message[j];
        if (str != null) {
          paramCodedOutputByteBufferNano.writeString(1, str);
        }
      }
    }
    if ((this.timing != null) && (this.timing.length > 0)) {
      for (int i = 0; i < this.timing.length; i++)
      {
        Timing localTiming = this.timing[i];
        if (localTiming != null) {
          paramCodedOutputByteBufferNano.writeGroup(2, localTiming);
        }
      }
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
  
  public static final class Timing
    extends MessageNano
  {
    private static volatile Timing[] _emptyArray;
    public boolean hasName = false;
    public boolean hasTimeInMs = false;
    public String name = "";
    public double timeInMs = 0.0D;
    
    public Timing()
    {
      this.cachedSize = -1;
    }
    
    public static Timing[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new Timing[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasName) || (!this.name.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.name);
      }
      if ((this.hasTimeInMs) || (Double.doubleToLongBits(this.timeInMs) != Double.doubleToLongBits(0.0D))) {
        i += 8 + CodedOutputByteBufferNano.computeTagSize(4);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasName) || (!this.name.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.name);
      }
      if ((this.hasTimeInMs) || (Double.doubleToLongBits(this.timeInMs) != Double.doubleToLongBits(0.0D))) {
        paramCodedOutputByteBufferNano.writeDouble(4, this.timeInMs);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.DebugInfo
 * JD-Core Version:    0.7.0.1
 */