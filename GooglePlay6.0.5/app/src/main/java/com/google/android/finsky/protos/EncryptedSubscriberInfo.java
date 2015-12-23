package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class EncryptedSubscriberInfo
  extends MessageNano
{
  public int carrierKeyVersion = 0;
  public String data = "";
  public String encryptedKey = "";
  public int googleKeyVersion = 0;
  public boolean hasCarrierKeyVersion = false;
  public boolean hasData = false;
  public boolean hasEncryptedKey = false;
  public boolean hasGoogleKeyVersion = false;
  public boolean hasInitVector = false;
  public boolean hasSignature = false;
  public String initVector = "";
  public String signature = "";
  
  public EncryptedSubscriberInfo()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasData) || (!this.data.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(1, this.data);
    }
    if ((this.hasEncryptedKey) || (!this.encryptedKey.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.encryptedKey);
    }
    if ((this.hasSignature) || (!this.signature.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(3, this.signature);
    }
    if ((this.hasInitVector) || (!this.initVector.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(4, this.initVector);
    }
    if ((this.hasGoogleKeyVersion) || (this.googleKeyVersion != 0)) {
      i += CodedOutputByteBufferNano.computeInt32Size(5, this.googleKeyVersion);
    }
    if ((this.hasCarrierKeyVersion) || (this.carrierKeyVersion != 0)) {
      i += CodedOutputByteBufferNano.computeInt32Size(6, this.carrierKeyVersion);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasData) || (!this.data.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(1, this.data);
    }
    if ((this.hasEncryptedKey) || (!this.encryptedKey.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.encryptedKey);
    }
    if ((this.hasSignature) || (!this.signature.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(3, this.signature);
    }
    if ((this.hasInitVector) || (!this.initVector.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(4, this.initVector);
    }
    if ((this.hasGoogleKeyVersion) || (this.googleKeyVersion != 0)) {
      paramCodedOutputByteBufferNano.writeInt32(5, this.googleKeyVersion);
    }
    if ((this.hasCarrierKeyVersion) || (this.carrierKeyVersion != 0)) {
      paramCodedOutputByteBufferNano.writeInt32(6, this.carrierKeyVersion);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.EncryptedSubscriberInfo
 * JD-Core Version:    0.7.0.1
 */