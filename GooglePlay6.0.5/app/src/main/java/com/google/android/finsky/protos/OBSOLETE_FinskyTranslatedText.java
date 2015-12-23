package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class OBSOLETE_FinskyTranslatedText
  extends MessageNano
{
  private static volatile OBSOLETE_FinskyTranslatedText[] _emptyArray;
  public boolean hasSourceLocale = false;
  public boolean hasTargetLocale = false;
  public boolean hasText = false;
  public String sourceLocale = "";
  public String targetLocale = "";
  public String text = "";
  
  public OBSOLETE_FinskyTranslatedText()
  {
    this.cachedSize = -1;
  }
  
  public static OBSOLETE_FinskyTranslatedText[] emptyArray()
  {
    if (_emptyArray == null) {}
    synchronized (InternalNano.LAZY_INIT_LOCK)
    {
      if (_emptyArray == null) {
        _emptyArray = new OBSOLETE_FinskyTranslatedText[0];
      }
      return _emptyArray;
    }
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasText) || (!this.text.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(1, this.text);
    }
    if ((this.hasSourceLocale) || (!this.sourceLocale.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.sourceLocale);
    }
    if ((this.hasTargetLocale) || (!this.targetLocale.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(3, this.targetLocale);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasText) || (!this.text.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(1, this.text);
    }
    if ((this.hasSourceLocale) || (!this.sourceLocale.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.sourceLocale);
    }
    if ((this.hasTargetLocale) || (!this.targetLocale.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(3, this.targetLocale);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.OBSOLETE_FinskyTranslatedText
 * JD-Core Version:    0.7.0.1
 */