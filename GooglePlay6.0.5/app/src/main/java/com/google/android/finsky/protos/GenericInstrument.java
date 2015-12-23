package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class GenericInstrument
  extends MessageNano
{
  public CreateInstrument.DeviceCreateInstrumentFlowHandle createInstrumentFlowHandle = null;
  public CreateInstrument.CreateInstrumentFlowOption[] createInstrumentFlowOption = CreateInstrument.CreateInstrumentFlowOption.emptyArray();
  public CreateInstrument.DeviceCreateInstrumentMetadata createInstrumentMetadata = null;
  public Common.Image iconImage = null;
  
  public GenericInstrument()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.createInstrumentFlowOption != null) && (this.createInstrumentFlowOption.length > 0)) {
      for (int j = 0; j < this.createInstrumentFlowOption.length; j++)
      {
        CreateInstrument.CreateInstrumentFlowOption localCreateInstrumentFlowOption = this.createInstrumentFlowOption[j];
        if (localCreateInstrumentFlowOption != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(2, localCreateInstrumentFlowOption);
        }
      }
    }
    if (this.createInstrumentFlowHandle != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(3, this.createInstrumentFlowHandle);
    }
    if (this.createInstrumentMetadata != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(4, this.createInstrumentMetadata);
    }
    if (this.iconImage != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(5, this.iconImage);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.createInstrumentFlowOption != null) && (this.createInstrumentFlowOption.length > 0)) {
      for (int i = 0; i < this.createInstrumentFlowOption.length; i++)
      {
        CreateInstrument.CreateInstrumentFlowOption localCreateInstrumentFlowOption = this.createInstrumentFlowOption[i];
        if (localCreateInstrumentFlowOption != null) {
          paramCodedOutputByteBufferNano.writeMessage(2, localCreateInstrumentFlowOption);
        }
      }
    }
    if (this.createInstrumentFlowHandle != null) {
      paramCodedOutputByteBufferNano.writeMessage(3, this.createInstrumentFlowHandle);
    }
    if (this.createInstrumentMetadata != null) {
      paramCodedOutputByteBufferNano.writeMessage(4, this.createInstrumentMetadata);
    }
    if (this.iconImage != null) {
      paramCodedOutputByteBufferNano.writeMessage(5, this.iconImage);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.GenericInstrument
 * JD-Core Version:    0.7.0.1
 */