package com.google.commerce.payments.orchestration.proto.ui.common.generic;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public abstract interface ImageWithCaptionOuterClass
{
  public static final class ImageWithCaption
    extends MessageNano
  {
    private static volatile ImageWithCaption[] _emptyArray;
    public String altText = "";
    public int heightPixels = 0;
    public String imageUri = "";
    public int widthPixels = 0;
    
    public ImageWithCaption()
    {
      this.cachedSize = -1;
    }
    
    public static ImageWithCaption[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new ImageWithCaption[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (!this.imageUri.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.imageUri);
      }
      if (!this.altText.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.altText);
      }
      if (this.widthPixels != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(4, this.widthPixels);
      }
      if (this.heightPixels != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(5, this.heightPixels);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (!this.imageUri.equals("")) {
        paramCodedOutputByteBufferNano.writeString(1, this.imageUri);
      }
      if (!this.altText.equals("")) {
        paramCodedOutputByteBufferNano.writeString(3, this.altText);
      }
      if (this.widthPixels != 0) {
        paramCodedOutputByteBufferNano.writeInt32(4, this.widthPixels);
      }
      if (this.heightPixels != 0) {
        paramCodedOutputByteBufferNano.writeInt32(5, this.heightPixels);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.commerce.payments.orchestration.proto.ui.common.generic.ImageWithCaptionOuterClass
 * JD-Core Version:    0.7.0.1
 */