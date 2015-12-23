package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class VideoAnnotations
  extends MessageNano
{
  public String alsoAvailableInListUrl = "";
  public boolean bundle = false;
  public String bundleContentListUrl = "";
  public Common.Docid[] bundleDocid = Common.Docid.emptyArray();
  public String extrasContentListUrl = "";
  public boolean hasAlsoAvailableInListUrl = false;
  public boolean hasBundle = false;
  public boolean hasBundleContentListUrl = false;
  public boolean hasExtrasContentListUrl = false;
  
  public VideoAnnotations()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasBundle) || (this.bundle)) {
      i += 1 + CodedOutputByteBufferNano.computeTagSize(1);
    }
    if ((this.hasBundleContentListUrl) || (!this.bundleContentListUrl.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.bundleContentListUrl);
    }
    if ((this.hasExtrasContentListUrl) || (!this.extrasContentListUrl.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(3, this.extrasContentListUrl);
    }
    if ((this.hasAlsoAvailableInListUrl) || (!this.alsoAvailableInListUrl.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(4, this.alsoAvailableInListUrl);
    }
    if ((this.bundleDocid != null) && (this.bundleDocid.length > 0)) {
      for (int j = 0; j < this.bundleDocid.length; j++)
      {
        Common.Docid localDocid = this.bundleDocid[j];
        if (localDocid != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(5, localDocid);
        }
      }
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasBundle) || (this.bundle)) {
      paramCodedOutputByteBufferNano.writeBool(1, this.bundle);
    }
    if ((this.hasBundleContentListUrl) || (!this.bundleContentListUrl.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.bundleContentListUrl);
    }
    if ((this.hasExtrasContentListUrl) || (!this.extrasContentListUrl.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(3, this.extrasContentListUrl);
    }
    if ((this.hasAlsoAvailableInListUrl) || (!this.alsoAvailableInListUrl.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(4, this.alsoAvailableInListUrl);
    }
    if ((this.bundleDocid != null) && (this.bundleDocid.length > 0)) {
      for (int i = 0; i < this.bundleDocid.length; i++)
      {
        Common.Docid localDocid = this.bundleDocid[i];
        if (localDocid != null) {
          paramCodedOutputByteBufferNano.writeMessage(5, localDocid);
        }
      }
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.VideoAnnotations
 * JD-Core Version:    0.7.0.1
 */