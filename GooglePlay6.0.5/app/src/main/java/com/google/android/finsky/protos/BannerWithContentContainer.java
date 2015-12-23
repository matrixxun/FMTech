package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class BannerWithContentContainer
  extends MessageNano
{
  public DocV2[] content = DocV2.emptyArray();
  
  public BannerWithContentContainer()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.content != null) && (this.content.length > 0)) {
      for (int j = 0; j < this.content.length; j++)
      {
        DocV2 localDocV2 = this.content[j];
        if (localDocV2 != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(1, localDocV2);
        }
      }
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.content != null) && (this.content.length > 0)) {
      for (int i = 0; i < this.content.length; i++)
      {
        DocV2 localDocV2 = this.content[i];
        if (localDocV2 != null) {
          paramCodedOutputByteBufferNano.writeMessage(1, localDocV2);
        }
      }
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.BannerWithContentContainer
 * JD-Core Version:    0.7.0.1
 */