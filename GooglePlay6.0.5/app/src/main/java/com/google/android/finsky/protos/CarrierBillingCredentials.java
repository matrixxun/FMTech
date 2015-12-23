package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class CarrierBillingCredentials
  extends MessageNano
{
  public long expiration = 0L;
  public boolean hasExpiration = false;
  public boolean hasValue = false;
  public String value = "";
  
  public CarrierBillingCredentials()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasValue) || (!this.value.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(1, this.value);
    }
    if ((this.hasExpiration) || (this.expiration != 0L)) {
      i += CodedOutputByteBufferNano.computeInt64Size(2, this.expiration);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasValue) || (!this.value.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(1, this.value);
    }
    if ((this.hasExpiration) || (this.expiration != 0L)) {
      paramCodedOutputByteBufferNano.writeInt64(2, this.expiration);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.CarrierBillingCredentials
 * JD-Core Version:    0.7.0.1
 */