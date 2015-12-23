package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class EarlyDocumentInfo
  extends MessageNano
{
  private static volatile EarlyDocumentInfo[] _emptyArray;
  public boolean background = false;
  public boolean critical = false;
  public Common.Docid docid = null;
  public boolean hasBackground = false;
  public boolean hasCritical = false;
  public boolean hasTitle = false;
  public boolean hasVersionCode = false;
  public String title = "";
  public int versionCode = 0;
  
  public EarlyDocumentInfo()
  {
    this.cachedSize = -1;
  }
  
  public static EarlyDocumentInfo[] emptyArray()
  {
    if (_emptyArray == null) {}
    synchronized (InternalNano.LAZY_INIT_LOCK)
    {
      if (_emptyArray == null) {
        _emptyArray = new EarlyDocumentInfo[0];
      }
      return _emptyArray;
    }
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if (this.docid != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(1, this.docid);
    }
    if ((this.hasTitle) || (!this.title.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.title);
    }
    if ((this.hasVersionCode) || (this.versionCode != 0)) {
      i += CodedOutputByteBufferNano.computeInt32Size(3, this.versionCode);
    }
    if ((this.hasBackground) || (this.background)) {
      i += 1 + CodedOutputByteBufferNano.computeTagSize(4);
    }
    if ((this.hasCritical) || (this.critical)) {
      i += 1 + CodedOutputByteBufferNano.computeTagSize(5);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if (this.docid != null) {
      paramCodedOutputByteBufferNano.writeMessage(1, this.docid);
    }
    if ((this.hasTitle) || (!this.title.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.title);
    }
    if ((this.hasVersionCode) || (this.versionCode != 0)) {
      paramCodedOutputByteBufferNano.writeInt32(3, this.versionCode);
    }
    if ((this.hasBackground) || (this.background)) {
      paramCodedOutputByteBufferNano.writeBool(4, this.background);
    }
    if ((this.hasCritical) || (this.critical)) {
      paramCodedOutputByteBufferNano.writeBool(5, this.critical);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.EarlyDocumentInfo
 * JD-Core Version:    0.7.0.1
 */