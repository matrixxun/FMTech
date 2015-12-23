package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class DisabledInfo
  extends MessageNano
{
  private static volatile DisabledInfo[] _emptyArray;
  public String disabledMessageHtml = "";
  public int disabledReason = 1;
  public String errorMessage = "";
  public boolean hasDisabledMessageHtml = false;
  public boolean hasDisabledReason = false;
  public boolean hasErrorMessage = false;
  
  public DisabledInfo()
  {
    this.cachedSize = -1;
  }
  
  public static DisabledInfo[] emptyArray()
  {
    if (_emptyArray == null) {}
    synchronized (InternalNano.LAZY_INIT_LOCK)
    {
      if (_emptyArray == null) {
        _emptyArray = new DisabledInfo[0];
      }
      return _emptyArray;
    }
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.disabledReason != 1) || (this.hasDisabledReason)) {
      i += CodedOutputByteBufferNano.computeInt32Size(1, this.disabledReason);
    }
    if ((this.hasDisabledMessageHtml) || (!this.disabledMessageHtml.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.disabledMessageHtml);
    }
    if ((this.hasErrorMessage) || (!this.errorMessage.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(3, this.errorMessage);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.disabledReason != 1) || (this.hasDisabledReason)) {
      paramCodedOutputByteBufferNano.writeInt32(1, this.disabledReason);
    }
    if ((this.hasDisabledMessageHtml) || (!this.disabledMessageHtml.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.disabledMessageHtml);
    }
    if ((this.hasErrorMessage) || (!this.errorMessage.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(3, this.errorMessage);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.DisabledInfo
 * JD-Core Version:    0.7.0.1
 */