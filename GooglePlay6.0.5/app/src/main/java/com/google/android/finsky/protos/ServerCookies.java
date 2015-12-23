package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class ServerCookies
  extends MessageNano
{
  public ServerCookie[] serverCookie = ServerCookie.emptyArray();
  
  public ServerCookies()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.serverCookie != null) && (this.serverCookie.length > 0)) {
      for (int j = 0; j < this.serverCookie.length; j++)
      {
        ServerCookie localServerCookie = this.serverCookie[j];
        if (localServerCookie != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(1, localServerCookie);
        }
      }
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.serverCookie != null) && (this.serverCookie.length > 0)) {
      for (int i = 0; i < this.serverCookie.length; i++)
      {
        ServerCookie localServerCookie = this.serverCookie[i];
        if (localServerCookie != null) {
          paramCodedOutputByteBufferNano.writeMessage(1, localServerCookie);
        }
      }
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.ServerCookies
 * JD-Core Version:    0.7.0.1
 */