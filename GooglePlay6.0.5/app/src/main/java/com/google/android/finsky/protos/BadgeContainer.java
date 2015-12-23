package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class BadgeContainer
  extends MessageNano
{
  private static volatile BadgeContainer[] _emptyArray;
  public Badge[] badge = Badge.emptyArray();
  public boolean hasTitle = false;
  public Common.Image[] image = Common.Image.emptyArray();
  public String title = "";
  
  public BadgeContainer()
  {
    this.cachedSize = -1;
  }
  
  public static BadgeContainer[] emptyArray()
  {
    if (_emptyArray == null) {}
    synchronized (InternalNano.LAZY_INIT_LOCK)
    {
      if (_emptyArray == null) {
        _emptyArray = new BadgeContainer[0];
      }
      return _emptyArray;
    }
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasTitle) || (!this.title.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(1, this.title);
    }
    if ((this.image != null) && (this.image.length > 0)) {
      for (int k = 0; k < this.image.length; k++)
      {
        Common.Image localImage = this.image[k];
        if (localImage != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(2, localImage);
        }
      }
    }
    if ((this.badge != null) && (this.badge.length > 0)) {
      for (int j = 0; j < this.badge.length; j++)
      {
        Badge localBadge = this.badge[j];
        if (localBadge != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(3, localBadge);
        }
      }
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasTitle) || (!this.title.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(1, this.title);
    }
    if ((this.image != null) && (this.image.length > 0)) {
      for (int j = 0; j < this.image.length; j++)
      {
        Common.Image localImage = this.image[j];
        if (localImage != null) {
          paramCodedOutputByteBufferNano.writeMessage(2, localImage);
        }
      }
    }
    if ((this.badge != null) && (this.badge.length > 0)) {
      for (int i = 0; i < this.badge.length; i++)
      {
        Badge localBadge = this.badge[i];
        if (localBadge != null) {
          paramCodedOutputByteBufferNano.writeMessage(3, localBadge);
        }
      }
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.BadgeContainer
 * JD-Core Version:    0.7.0.1
 */