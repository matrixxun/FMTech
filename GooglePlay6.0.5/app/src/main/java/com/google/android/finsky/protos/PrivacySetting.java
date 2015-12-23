package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class PrivacySetting
  extends MessageNano
{
  private static volatile PrivacySetting[] _emptyArray;
  public int currentStatus = 0;
  public boolean enabledByDefault = false;
  public boolean hasCurrentStatus = false;
  public boolean hasEnabledByDefault = false;
  public boolean hasType = false;
  public int type = 0;
  
  public PrivacySetting()
  {
    this.cachedSize = -1;
  }
  
  public static PrivacySetting[] emptyArray()
  {
    if (_emptyArray == null) {}
    synchronized (InternalNano.LAZY_INIT_LOCK)
    {
      if (_emptyArray == null) {
        _emptyArray = new PrivacySetting[0];
      }
      return _emptyArray;
    }
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.type != 0) || (this.hasType)) {
      i += CodedOutputByteBufferNano.computeInt32Size(1, this.type);
    }
    if ((this.currentStatus != 0) || (this.hasCurrentStatus)) {
      i += CodedOutputByteBufferNano.computeInt32Size(2, this.currentStatus);
    }
    if ((this.hasEnabledByDefault) || (this.enabledByDefault)) {
      i += 1 + CodedOutputByteBufferNano.computeTagSize(3);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.type != 0) || (this.hasType)) {
      paramCodedOutputByteBufferNano.writeInt32(1, this.type);
    }
    if ((this.currentStatus != 0) || (this.hasCurrentStatus)) {
      paramCodedOutputByteBufferNano.writeInt32(2, this.currentStatus);
    }
    if ((this.hasEnabledByDefault) || (this.enabledByDefault)) {
      paramCodedOutputByteBufferNano.writeBool(3, this.enabledByDefault);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.PrivacySetting
 * JD-Core Version:    0.7.0.1
 */