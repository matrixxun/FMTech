package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class TopChartsContainer
  extends MessageNano
{
  public CallToAction action = null;
  public boolean hasTopChartDescription = false;
  public String topChartDescription = "";
  
  public TopChartsContainer()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasTopChartDescription) || (!this.topChartDescription.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(1, this.topChartDescription);
    }
    if (this.action != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(2, this.action);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasTopChartDescription) || (!this.topChartDescription.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(1, this.topChartDescription);
    }
    if (this.action != null) {
      paramCodedOutputByteBufferNano.writeMessage(2, this.action);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.TopChartsContainer
 * JD-Core Version:    0.7.0.1
 */