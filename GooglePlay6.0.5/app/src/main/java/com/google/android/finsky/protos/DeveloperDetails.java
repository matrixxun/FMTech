package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class DeveloperDetails
  extends MessageNano
{
  public boolean hasWebsiteUrl = false;
  public String websiteUrl = "";
  
  public DeveloperDetails()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasWebsiteUrl) || (!this.websiteUrl.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(1, this.websiteUrl);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasWebsiteUrl) || (!this.websiteUrl.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(1, this.websiteUrl);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.DeveloperDetails
 * JD-Core Version:    0.7.0.1
 */