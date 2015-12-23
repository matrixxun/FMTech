package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class UserSettingDirtyData
  extends MessageNano
{
  private static volatile UserSettingDirtyData[] _emptyArray;
  public UserSettingsConsistencyTokens consistencyTokens = null;
  public boolean hasType = false;
  public int type = 0;
  
  public UserSettingDirtyData()
  {
    this.cachedSize = -1;
  }
  
  public static UserSettingDirtyData[] emptyArray()
  {
    if (_emptyArray == null) {}
    synchronized (InternalNano.LAZY_INIT_LOCK)
    {
      if (_emptyArray == null) {
        _emptyArray = new UserSettingDirtyData[0];
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
    if (this.consistencyTokens != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(2, this.consistencyTokens);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.type != 0) || (this.hasType)) {
      paramCodedOutputByteBufferNano.writeInt32(1, this.type);
    }
    if (this.consistencyTokens != null) {
      paramCodedOutputByteBufferNano.writeMessage(2, this.consistencyTokens);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.UserSettingDirtyData
 * JD-Core Version:    0.7.0.1
 */