package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class ReasonPlusOne
  extends MessageNano
{
  public boolean hasLocalizedDescriptionHtml = false;
  public String localizedDescriptionHtml = "";
  public OBSOLETE_PlusProfile[] oBSOLETEPlusProfile = OBSOLETE_PlusProfile.emptyArray();
  public DocV2[] person = DocV2.emptyArray();
  
  public ReasonPlusOne()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasLocalizedDescriptionHtml) || (!this.localizedDescriptionHtml.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(1, this.localizedDescriptionHtml);
    }
    if ((this.oBSOLETEPlusProfile != null) && (this.oBSOLETEPlusProfile.length > 0)) {
      for (int k = 0; k < this.oBSOLETEPlusProfile.length; k++)
      {
        OBSOLETE_PlusProfile localOBSOLETE_PlusProfile = this.oBSOLETEPlusProfile[k];
        if (localOBSOLETE_PlusProfile != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(2, localOBSOLETE_PlusProfile);
        }
      }
    }
    if ((this.person != null) && (this.person.length > 0)) {
      for (int j = 0; j < this.person.length; j++)
      {
        DocV2 localDocV2 = this.person[j];
        if (localDocV2 != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(3, localDocV2);
        }
      }
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasLocalizedDescriptionHtml) || (!this.localizedDescriptionHtml.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(1, this.localizedDescriptionHtml);
    }
    if ((this.oBSOLETEPlusProfile != null) && (this.oBSOLETEPlusProfile.length > 0)) {
      for (int j = 0; j < this.oBSOLETEPlusProfile.length; j++)
      {
        OBSOLETE_PlusProfile localOBSOLETE_PlusProfile = this.oBSOLETEPlusProfile[j];
        if (localOBSOLETE_PlusProfile != null) {
          paramCodedOutputByteBufferNano.writeMessage(2, localOBSOLETE_PlusProfile);
        }
      }
    }
    if ((this.person != null) && (this.person.length > 0)) {
      for (int i = 0; i < this.person.length; i++)
      {
        DocV2 localDocV2 = this.person[i];
        if (localDocV2 != null) {
          paramCodedOutputByteBufferNano.writeMessage(3, localDocV2);
        }
      }
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.ReasonPlusOne
 * JD-Core Version:    0.7.0.1
 */