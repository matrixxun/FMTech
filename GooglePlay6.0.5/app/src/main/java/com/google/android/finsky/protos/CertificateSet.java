package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;

public final class CertificateSet
  extends MessageNano
{
  private static volatile CertificateSet[] _emptyArray;
  public String[] certificateHash = WireFormatNano.EMPTY_STRING_ARRAY;
  
  public CertificateSet()
  {
    this.cachedSize = -1;
  }
  
  public static CertificateSet[] emptyArray()
  {
    if (_emptyArray == null) {}
    synchronized (InternalNano.LAZY_INIT_LOCK)
    {
      if (_emptyArray == null) {
        _emptyArray = new CertificateSet[0];
      }
      return _emptyArray;
    }
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.certificateHash != null) && (this.certificateHash.length > 0))
    {
      int j = 0;
      int k = 0;
      for (int m = 0; m < this.certificateHash.length; m++)
      {
        String str = this.certificateHash[m];
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
    if ((this.certificateHash != null) && (this.certificateHash.length > 0)) {
      for (int i = 0; i < this.certificateHash.length; i++)
      {
        String str = this.certificateHash[i];
        if (str != null) {
          paramCodedOutputByteBufferNano.writeString(1, str);
        }
      }
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.CertificateSet
 * JD-Core Version:    0.7.0.1
 */