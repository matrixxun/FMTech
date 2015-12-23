package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class VideoSnippet
  extends MessageNano
{
  private static volatile VideoSnippet[] _emptyArray;
  public String description = "";
  public boolean hasDescription = false;
  public boolean hasTitle = false;
  public Common.Image[] image = Common.Image.emptyArray();
  public String title = "";
  
  public VideoSnippet()
  {
    this.cachedSize = -1;
  }
  
  public static VideoSnippet[] emptyArray()
  {
    if (_emptyArray == null) {}
    synchronized (InternalNano.LAZY_INIT_LOCK)
    {
      if (_emptyArray == null) {
        _emptyArray = new VideoSnippet[0];
      }
      return _emptyArray;
    }
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.image != null) && (this.image.length > 0)) {
      for (int j = 0; j < this.image.length; j++)
      {
        Common.Image localImage = this.image[j];
        if (localImage != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(1, localImage);
        }
      }
    }
    if ((this.hasTitle) || (!this.title.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.title);
    }
    if ((this.hasDescription) || (!this.description.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(3, this.description);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.image != null) && (this.image.length > 0)) {
      for (int i = 0; i < this.image.length; i++)
      {
        Common.Image localImage = this.image[i];
        if (localImage != null) {
          paramCodedOutputByteBufferNano.writeMessage(1, localImage);
        }
      }
    }
    if ((this.hasTitle) || (!this.title.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.title);
    }
    if ((this.hasDescription) || (!this.description.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(3, this.description);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.VideoSnippet
 * JD-Core Version:    0.7.0.1
 */