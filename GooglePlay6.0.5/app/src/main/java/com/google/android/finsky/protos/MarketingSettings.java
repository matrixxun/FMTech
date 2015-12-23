package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class MarketingSettings
  extends MessageNano
{
  public boolean hasMarketingEmailsOptedIn = false;
  public boolean marketingEmailsOptedIn = false;
  
  public MarketingSettings()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasMarketingEmailsOptedIn) || (this.marketingEmailsOptedIn)) {
      i += 1 + CodedOutputByteBufferNano.computeTagSize(1);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasMarketingEmailsOptedIn) || (this.marketingEmailsOptedIn)) {
      paramCodedOutputByteBufferNano.writeBool(1, this.marketingEmailsOptedIn);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.MarketingSettings
 * JD-Core Version:    0.7.0.1
 */