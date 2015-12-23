package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;

public final class VideoCredit
  extends MessageNano
{
  private static volatile VideoCredit[] _emptyArray;
  public String credit = "";
  public int creditType = 0;
  public boolean hasCredit = false;
  public boolean hasCreditType = false;
  public String[] name = WireFormatNano.EMPTY_STRING_ARRAY;
  
  public VideoCredit()
  {
    this.cachedSize = -1;
  }
  
  public static VideoCredit[] emptyArray()
  {
    if (_emptyArray == null) {}
    synchronized (InternalNano.LAZY_INIT_LOCK)
    {
      if (_emptyArray == null) {
        _emptyArray = new VideoCredit[0];
      }
      return _emptyArray;
    }
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.creditType != 0) || (this.hasCreditType)) {
      i += CodedOutputByteBufferNano.computeInt32Size(1, this.creditType);
    }
    if ((this.hasCredit) || (!this.credit.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.credit);
    }
    if ((this.name != null) && (this.name.length > 0))
    {
      int j = 0;
      int k = 0;
      for (int m = 0; m < this.name.length; m++)
      {
        String str = this.name[m];
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
    if ((this.creditType != 0) || (this.hasCreditType)) {
      paramCodedOutputByteBufferNano.writeInt32(1, this.creditType);
    }
    if ((this.hasCredit) || (!this.credit.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.credit);
    }
    if ((this.name != null) && (this.name.length > 0)) {
      for (int i = 0; i < this.name.length; i++)
      {
        String str = this.name[i];
        if (str != null) {
          paramCodedOutputByteBufferNano.writeString(3, str);
        }
      }
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.VideoCredit
 * JD-Core Version:    0.7.0.1
 */