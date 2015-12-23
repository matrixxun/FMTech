package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class UserSettingsConsistencyTokens
  extends MessageNano
{
  public ConsistencyTokenInfo[] consistencyTokenInfo = ConsistencyTokenInfo.emptyArray();
  
  public UserSettingsConsistencyTokens()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.consistencyTokenInfo != null) && (this.consistencyTokenInfo.length > 0)) {
      for (int j = 0; j < this.consistencyTokenInfo.length; j++)
      {
        ConsistencyTokenInfo localConsistencyTokenInfo = this.consistencyTokenInfo[j];
        if (localConsistencyTokenInfo != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(1, localConsistencyTokenInfo);
        }
      }
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.consistencyTokenInfo != null) && (this.consistencyTokenInfo.length > 0)) {
      for (int i = 0; i < this.consistencyTokenInfo.length; i++)
      {
        ConsistencyTokenInfo localConsistencyTokenInfo = this.consistencyTokenInfo[i];
        if (localConsistencyTokenInfo != null) {
          paramCodedOutputByteBufferNano.writeMessage(1, localConsistencyTokenInfo);
        }
      }
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
  
  public static final class ConsistencyTokenInfo
    extends MessageNano
  {
    private static volatile ConsistencyTokenInfo[] _emptyArray;
    public String consistencyToken = "";
    public boolean hasConsistencyToken = false;
    public boolean hasRequestHeader = false;
    public String requestHeader = "";
    
    public ConsistencyTokenInfo()
    {
      this.cachedSize = -1;
    }
    
    public static ConsistencyTokenInfo[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new ConsistencyTokenInfo[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasRequestHeader) || (!this.requestHeader.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.requestHeader);
      }
      if ((this.hasConsistencyToken) || (!this.consistencyToken.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.consistencyToken);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasRequestHeader) || (!this.requestHeader.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.requestHeader);
      }
      if ((this.hasConsistencyToken) || (!this.consistencyToken.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.consistencyToken);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.UserSettingsConsistencyTokens
 * JD-Core Version:    0.7.0.1
 */