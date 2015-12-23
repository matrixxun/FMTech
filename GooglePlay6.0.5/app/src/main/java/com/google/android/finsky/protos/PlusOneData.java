package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class PlusOneData
  extends MessageNano
{
  public DocV2[] circlePerson = DocV2.emptyArray();
  public long circlesTotal = 0L;
  public boolean hasCirclesTotal = false;
  public boolean hasSetByUser = false;
  public boolean hasTotal = false;
  public OBSOLETE_PlusProfile[] oBSOLETECirclesProfiles = OBSOLETE_PlusProfile.emptyArray();
  public boolean setByUser = false;
  public long total = 0L;
  
  public PlusOneData()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasSetByUser) || (this.setByUser)) {
      i += 1 + CodedOutputByteBufferNano.computeTagSize(1);
    }
    if ((this.hasTotal) || (this.total != 0L)) {
      i += CodedOutputByteBufferNano.computeInt64Size(2, this.total);
    }
    if ((this.hasCirclesTotal) || (this.circlesTotal != 0L)) {
      i += CodedOutputByteBufferNano.computeInt64Size(3, this.circlesTotal);
    }
    if ((this.oBSOLETECirclesProfiles != null) && (this.oBSOLETECirclesProfiles.length > 0)) {
      for (int k = 0; k < this.oBSOLETECirclesProfiles.length; k++)
      {
        OBSOLETE_PlusProfile localOBSOLETE_PlusProfile = this.oBSOLETECirclesProfiles[k];
        if (localOBSOLETE_PlusProfile != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(4, localOBSOLETE_PlusProfile);
        }
      }
    }
    if ((this.circlePerson != null) && (this.circlePerson.length > 0)) {
      for (int j = 0; j < this.circlePerson.length; j++)
      {
        DocV2 localDocV2 = this.circlePerson[j];
        if (localDocV2 != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(5, localDocV2);
        }
      }
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasSetByUser) || (this.setByUser)) {
      paramCodedOutputByteBufferNano.writeBool(1, this.setByUser);
    }
    if ((this.hasTotal) || (this.total != 0L)) {
      paramCodedOutputByteBufferNano.writeInt64(2, this.total);
    }
    if ((this.hasCirclesTotal) || (this.circlesTotal != 0L)) {
      paramCodedOutputByteBufferNano.writeInt64(3, this.circlesTotal);
    }
    if ((this.oBSOLETECirclesProfiles != null) && (this.oBSOLETECirclesProfiles.length > 0)) {
      for (int j = 0; j < this.oBSOLETECirclesProfiles.length; j++)
      {
        OBSOLETE_PlusProfile localOBSOLETE_PlusProfile = this.oBSOLETECirclesProfiles[j];
        if (localOBSOLETE_PlusProfile != null) {
          paramCodedOutputByteBufferNano.writeMessage(4, localOBSOLETE_PlusProfile);
        }
      }
    }
    if ((this.circlePerson != null) && (this.circlePerson.length > 0)) {
      for (int i = 0; i < this.circlePerson.length; i++)
      {
        DocV2 localDocV2 = this.circlePerson[i];
        if (localDocV2 != null) {
          paramCodedOutputByteBufferNano.writeMessage(5, localDocV2);
        }
      }
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.PlusOneData
 * JD-Core Version:    0.7.0.1
 */