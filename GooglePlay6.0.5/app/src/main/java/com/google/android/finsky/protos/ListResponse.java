package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class ListResponse
  extends MessageNano
{
  public Bucket[] bucket = Bucket.emptyArray();
  public DocV2[] doc = DocV2.emptyArray();
  
  public ListResponse()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.bucket != null) && (this.bucket.length > 0)) {
      for (int k = 0; k < this.bucket.length; k++)
      {
        Bucket localBucket = this.bucket[k];
        if (localBucket != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(1, localBucket);
        }
      }
    }
    if ((this.doc != null) && (this.doc.length > 0)) {
      for (int j = 0; j < this.doc.length; j++)
      {
        DocV2 localDocV2 = this.doc[j];
        if (localDocV2 != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(2, localDocV2);
        }
      }
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.bucket != null) && (this.bucket.length > 0)) {
      for (int j = 0; j < this.bucket.length; j++)
      {
        Bucket localBucket = this.bucket[j];
        if (localBucket != null) {
          paramCodedOutputByteBufferNano.writeMessage(1, localBucket);
        }
      }
    }
    if ((this.doc != null) && (this.doc.length > 0)) {
      for (int i = 0; i < this.doc.length; i++)
      {
        DocV2 localDocV2 = this.doc[i];
        if (localDocV2 != null) {
          paramCodedOutputByteBufferNano.writeMessage(2, localDocV2);
        }
      }
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.ListResponse
 * JD-Core Version:    0.7.0.1
 */