package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;

public final class InstrumentSetupInfo
  extends MessageNano
{
  private static volatile InstrumentSetupInfo[] _emptyArray;
  public ChallengeProto.AddressChallenge addressChallenge = null;
  public Money balance = null;
  public String[] footerHtml = WireFormatNano.EMPTY_STRING_ARRAY;
  public boolean hasInstrumentFamily = false;
  public boolean hasSupported = false;
  public int instrumentFamily = 0;
  public boolean supported = false;
  
  public InstrumentSetupInfo()
  {
    this.cachedSize = -1;
  }
  
  public static InstrumentSetupInfo[] emptyArray()
  {
    if (_emptyArray == null) {}
    synchronized (InternalNano.LAZY_INIT_LOCK)
    {
      if (_emptyArray == null) {
        _emptyArray = new InstrumentSetupInfo[0];
      }
      return _emptyArray;
    }
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.instrumentFamily != 0) || (this.hasInstrumentFamily)) {
      i += CodedOutputByteBufferNano.computeInt32Size(1, this.instrumentFamily);
    }
    if ((this.hasSupported) || (this.supported)) {
      i += 1 + CodedOutputByteBufferNano.computeTagSize(2);
    }
    if (this.addressChallenge != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(3, this.addressChallenge);
    }
    if (this.balance != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(4, this.balance);
    }
    if ((this.footerHtml != null) && (this.footerHtml.length > 0))
    {
      int j = 0;
      int k = 0;
      for (int m = 0; m < this.footerHtml.length; m++)
      {
        String str = this.footerHtml[m];
        if (str != null)
        {
          j++;
          k += CodedOutputByteBufferNano.computeStringSizeNoTag(str);
        }
      }
      i = i + k + j * 1;
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.instrumentFamily != 0) || (this.hasInstrumentFamily)) {
      paramCodedOutputByteBufferNano.writeInt32(1, this.instrumentFamily);
    }
    if ((this.hasSupported) || (this.supported)) {
      paramCodedOutputByteBufferNano.writeBool(2, this.supported);
    }
    if (this.addressChallenge != null) {
      paramCodedOutputByteBufferNano.writeMessage(3, this.addressChallenge);
    }
    if (this.balance != null) {
      paramCodedOutputByteBufferNano.writeMessage(4, this.balance);
    }
    if ((this.footerHtml != null) && (this.footerHtml.length > 0)) {
      for (int i = 0; i < this.footerHtml.length; i++)
      {
        String str = this.footerHtml[i];
        if (str != null) {
          paramCodedOutputByteBufferNano.writeString(5, str);
        }
      }
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.InstrumentSetupInfo
 * JD-Core Version:    0.7.0.1
 */