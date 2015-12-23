package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public abstract interface Log
{
  public static final class ClickLogEvent
    extends MessageNano
  {
    private static volatile ClickLogEvent[] _emptyArray;
    public long eventTime = 0L;
    public boolean hasEventTime = false;
    public boolean hasListId = false;
    public boolean hasReferrerListId = false;
    public boolean hasReferrerUrl = false;
    public boolean hasUrl = false;
    public String listId = "";
    public String referrerListId = "";
    public String referrerUrl = "";
    public String url = "";
    
    public ClickLogEvent()
    {
      this.cachedSize = -1;
    }
    
    public static ClickLogEvent[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new ClickLogEvent[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasEventTime) || (this.eventTime != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(1, this.eventTime);
      }
      if ((this.hasUrl) || (!this.url.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.url);
      }
      if ((this.hasListId) || (!this.listId.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.listId);
      }
      if ((this.hasReferrerUrl) || (!this.referrerUrl.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(4, this.referrerUrl);
      }
      if ((this.hasReferrerListId) || (!this.referrerListId.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(5, this.referrerListId);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasEventTime) || (this.eventTime != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(1, this.eventTime);
      }
      if ((this.hasUrl) || (!this.url.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.url);
      }
      if ((this.hasListId) || (!this.listId.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.listId);
      }
      if ((this.hasReferrerUrl) || (!this.referrerUrl.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(4, this.referrerUrl);
      }
      if ((this.hasReferrerListId) || (!this.referrerListId.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(5, this.referrerListId);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class LogRequest
    extends MessageNano
  {
    public Log.ClickLogEvent[] clickEvent = Log.ClickLogEvent.emptyArray();
    
    public LogRequest()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.clickEvent != null) && (this.clickEvent.length > 0)) {
        for (int j = 0; j < this.clickEvent.length; j++)
        {
          Log.ClickLogEvent localClickLogEvent = this.clickEvent[j];
          if (localClickLogEvent != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(1, localClickLogEvent);
          }
        }
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.clickEvent != null) && (this.clickEvent.length > 0)) {
        for (int i = 0; i < this.clickEvent.length; i++)
        {
          Log.ClickLogEvent localClickLogEvent = this.clickEvent[i];
          if (localClickLogEvent != null) {
            paramCodedOutputByteBufferNano.writeMessage(1, localClickLogEvent);
          }
        }
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class LogResponse
    extends MessageNano
  {
    public LogResponse()
    {
      this.cachedSize = -1;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.Log
 * JD-Core Version:    0.7.0.1
 */