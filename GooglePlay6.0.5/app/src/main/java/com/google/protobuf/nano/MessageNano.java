package com.google.protobuf.nano;

import java.io.IOException;
import java.nio.ByteBuffer;

public abstract class MessageNano
{
  public volatile int cachedSize = -1;
  
  public static <T extends MessageNano> T mergeFrom$1ec43da(T paramT, byte[] paramArrayOfByte, int paramInt)
    throws InvalidProtocolBufferNanoException
  {
    try
    {
      CodedInputByteBufferNano localCodedInputByteBufferNano = CodedInputByteBufferNano.newInstance(paramArrayOfByte, 0, paramInt);
      paramT.mergeFrom(localCodedInputByteBufferNano);
      localCodedInputByteBufferNano.checkLastTagWas(0);
      return paramT;
    }
    catch (InvalidProtocolBufferNanoException localInvalidProtocolBufferNanoException)
    {
      throw localInvalidProtocolBufferNanoException;
    }
    catch (IOException localIOException)
    {
      throw new RuntimeException("Reading from a byte array threw an IOException (should never happen).");
    }
  }
  
  public static final byte[] toByteArray(MessageNano paramMessageNano)
  {
    byte[] arrayOfByte = new byte[paramMessageNano.getSerializedSize()];
    int i = arrayOfByte.length;
    try
    {
      CodedOutputByteBufferNano localCodedOutputByteBufferNano = new CodedOutputByteBufferNano(arrayOfByte, 0, i);
      paramMessageNano.writeTo(localCodedOutputByteBufferNano);
      if (localCodedOutputByteBufferNano.buffer.remaining() != 0) {
        throw new IllegalStateException("Did not write as much data as expected.");
      }
    }
    catch (IOException localIOException)
    {
      throw new RuntimeException("Serializing to a byte array threw an IOException (should never happen).", localIOException);
    }
    return arrayOfByte;
  }
  
  public int computeSerializedSize()
  {
    return 0;
  }
  
  public final int getCachedSize()
  {
    if (this.cachedSize < 0) {
      getSerializedSize();
    }
    return this.cachedSize;
  }
  
  public final int getSerializedSize()
  {
    int i = computeSerializedSize();
    this.cachedSize = i;
    return i;
  }
  
  public abstract MessageNano mergeFrom(CodedInputByteBufferNano paramCodedInputByteBufferNano)
    throws IOException;
  
  public String toString()
  {
    return MessageNanoPrinter.print(this);
  }
  
  public void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {}
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.protobuf.nano.MessageNano
 * JD-Core Version:    0.7.0.1
 */