package com.google.commerce.payments.orchestration.proto.ui.common;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;

public abstract interface SecureDataHeaderOuterClass
{
  public static final class SecureDataHeader
    extends MessageNano
  {
    public String[] refreshActionHeader = WireFormatNano.EMPTY_STRING_ARRAY;
    public String[] submitActionHeader = WireFormatNano.EMPTY_STRING_ARRAY;
    
    public SecureDataHeader()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.submitActionHeader != null) && (this.submitActionHeader.length > 0))
      {
        int n = 0;
        int i1 = 0;
        for (int i2 = 0; i2 < this.submitActionHeader.length; i2++)
        {
          String str2 = this.submitActionHeader[i2];
          if (str2 != null)
          {
            n++;
            i1 += CodedOutputByteBufferNano.computeStringSizeNoTag(str2);
          }
        }
        i = i + i1 + n * 1;
      }
      if ((this.refreshActionHeader != null) && (this.refreshActionHeader.length > 0))
      {
        int j = 0;
        int k = 0;
        for (int m = 0; m < this.refreshActionHeader.length; m++)
        {
          String str1 = this.refreshActionHeader[m];
          if (str1 != null)
          {
            j++;
            k += CodedOutputByteBufferNano.computeStringSizeNoTag(str1);
          }
        }
        i = i + k + j * 1;
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.submitActionHeader != null) && (this.submitActionHeader.length > 0)) {
        for (int j = 0; j < this.submitActionHeader.length; j++)
        {
          String str2 = this.submitActionHeader[j];
          if (str2 != null) {
            paramCodedOutputByteBufferNano.writeString(1, str2);
          }
        }
      }
      if ((this.refreshActionHeader != null) && (this.refreshActionHeader.length > 0)) {
        for (int i = 0; i < this.refreshActionHeader.length; i++)
        {
          String str1 = this.refreshActionHeader[i];
          if (str1 != null) {
            paramCodedOutputByteBufferNano.writeString(2, str1);
          }
        }
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.commerce.payments.orchestration.proto.ui.common.SecureDataHeaderOuterClass
 * JD-Core Version:    0.7.0.1
 */