package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class PlayDailyRelatedLink
  extends MessageNano
{
  private static volatile PlayDailyRelatedLink[] _emptyArray;
  public String anchorText = "";
  public boolean hasAnchorText = false;
  public boolean hasUrl = false;
  public String url = "";
  
  public PlayDailyRelatedLink()
  {
    this.cachedSize = -1;
  }
  
  public static PlayDailyRelatedLink[] emptyArray()
  {
    if (_emptyArray == null) {}
    synchronized (InternalNano.LAZY_INIT_LOCK)
    {
      if (_emptyArray == null) {
        _emptyArray = new PlayDailyRelatedLink[0];
      }
      return _emptyArray;
    }
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasAnchorText) || (!this.anchorText.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(1, this.anchorText);
    }
    if ((this.hasUrl) || (!this.url.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.url);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasAnchorText) || (!this.anchorText.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(1, this.anchorText);
    }
    if ((this.hasUrl) || (!this.url.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.url);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.PlayDailyRelatedLink
 * JD-Core Version:    0.7.0.1
 */