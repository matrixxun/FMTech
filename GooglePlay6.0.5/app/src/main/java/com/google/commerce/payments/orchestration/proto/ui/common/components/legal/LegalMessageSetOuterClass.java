package com.google.commerce.payments.orchestration.proto.ui.common.components.legal;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public abstract interface LegalMessageSetOuterClass
{
  public static final class LegalMessageByCountry
    extends MessageNano
  {
    private static volatile LegalMessageByCountry[] _emptyArray;
    public String country = "";
    public LegalMessageOuterClass.LegalMessage message = null;
    
    public LegalMessageByCountry()
    {
      this.cachedSize = -1;
    }
    
    public static LegalMessageByCountry[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new LegalMessageByCountry[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (!this.country.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.country);
      }
      if (this.message != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.message);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (!this.country.equals("")) {
        paramCodedOutputByteBufferNano.writeString(1, this.country);
      }
      if (this.message != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.message);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class LegalMessageSet
    extends MessageNano
  {
    public LegalMessageOuterClass.LegalMessage defaultMessage = null;
    public LegalMessageSetOuterClass.LegalMessageByCountry[] messageByCountry = LegalMessageSetOuterClass.LegalMessageByCountry.emptyArray();
    
    public LegalMessageSet()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.defaultMessage != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.defaultMessage);
      }
      if ((this.messageByCountry != null) && (this.messageByCountry.length > 0)) {
        for (int j = 0; j < this.messageByCountry.length; j++)
        {
          LegalMessageSetOuterClass.LegalMessageByCountry localLegalMessageByCountry = this.messageByCountry[j];
          if (localLegalMessageByCountry != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(2, localLegalMessageByCountry);
          }
        }
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.defaultMessage != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.defaultMessage);
      }
      if ((this.messageByCountry != null) && (this.messageByCountry.length > 0)) {
        for (int i = 0; i < this.messageByCountry.length; i++)
        {
          LegalMessageSetOuterClass.LegalMessageByCountry localLegalMessageByCountry = this.messageByCountry[i];
          if (localLegalMessageByCountry != null) {
            paramCodedOutputByteBufferNano.writeMessage(2, localLegalMessageByCountry);
          }
        }
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.commerce.payments.orchestration.proto.ui.common.components.legal.LegalMessageSetOuterClass
 * JD-Core Version:    0.7.0.1
 */