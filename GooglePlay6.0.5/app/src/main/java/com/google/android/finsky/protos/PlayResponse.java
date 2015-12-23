package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InvalidProtocolBufferNanoException;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public abstract interface PlayResponse
{
  public static final class PlayPayload
    extends MessageNano
  {
    public AllKnownExperiments.AllKnownExperimentsResponse allKnownExperimentsResponse = null;
    public ExperimentsResponse experimentsResponse = null;
    public PlayPlusProfileResponse oBSOLETEPlusProfileResponse = null;
    public PlusProfileResponse plusProfileResponse = null;
    
    public PlayPayload()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.oBSOLETEPlusProfileResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.oBSOLETEPlusProfileResponse);
      }
      if (this.plusProfileResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.plusProfileResponse);
      }
      if (this.experimentsResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(3, this.experimentsResponse);
      }
      if (this.allKnownExperimentsResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(4, this.allKnownExperimentsResponse);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.oBSOLETEPlusProfileResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.oBSOLETEPlusProfileResponse);
      }
      if (this.plusProfileResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.plusProfileResponse);
      }
      if (this.experimentsResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(3, this.experimentsResponse);
      }
      if (this.allKnownExperimentsResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(4, this.allKnownExperimentsResponse);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class PlayResponseWrapper
    extends MessageNano
  {
    public ServerCommands commands = null;
    public PlayResponse.PlayPayload payload = null;
    public PreFetch[] preFetch = PreFetch.emptyArray();
    public ServerMetadata serverMetadata = null;
    
    public PlayResponseWrapper()
    {
      this.cachedSize = -1;
    }
    
    public static PlayResponseWrapper parseFrom(byte[] paramArrayOfByte)
      throws InvalidProtocolBufferNanoException
    {
      return (PlayResponseWrapper)MessageNano.mergeFrom$1ec43da(new PlayResponseWrapper(), paramArrayOfByte, paramArrayOfByte.length);
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.payload != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.payload);
      }
      if (this.commands != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.commands);
      }
      if ((this.preFetch != null) && (this.preFetch.length > 0)) {
        for (int j = 0; j < this.preFetch.length; j++)
        {
          PreFetch localPreFetch = this.preFetch[j];
          if (localPreFetch != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(3, localPreFetch);
          }
        }
      }
      if (this.serverMetadata != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(4, this.serverMetadata);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.payload != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.payload);
      }
      if (this.commands != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.commands);
      }
      if ((this.preFetch != null) && (this.preFetch.length > 0)) {
        for (int i = 0; i < this.preFetch.length; i++)
        {
          PreFetch localPreFetch = this.preFetch[i];
          if (localPreFetch != null) {
            paramCodedOutputByteBufferNano.writeMessage(3, localPreFetch);
          }
        }
      }
      if (this.serverMetadata != null) {
        paramCodedOutputByteBufferNano.writeMessage(4, this.serverMetadata);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.PlayResponse
 * JD-Core Version:    0.7.0.1
 */