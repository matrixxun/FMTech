package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class EncryptionParams
  extends MessageNano
{
  public String encryptionKey = "";
  public boolean hasEncryptionKey = false;
  public boolean hasHmacKey = false;
  public boolean hasVersion = false;
  public String hmacKey = "";
  public int version = 1;
  
  public EncryptionParams()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasVersion) || (this.version != 1)) {
      i += CodedOutputByteBufferNano.computeInt32Size(1, this.version);
    }
    if ((this.hasEncryptionKey) || (!this.encryptionKey.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.encryptionKey);
    }
    if ((this.hasHmacKey) || (!this.hmacKey.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(3, this.hmacKey);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasVersion) || (this.version != 1)) {
      paramCodedOutputByteBufferNano.writeInt32(1, this.version);
    }
    if ((this.hasEncryptionKey) || (!this.encryptionKey.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.encryptionKey);
    }
    if ((this.hasHmacKey) || (!this.hmacKey.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(3, this.hmacKey);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.EncryptionParams
 * JD-Core Version:    0.7.0.1
 */