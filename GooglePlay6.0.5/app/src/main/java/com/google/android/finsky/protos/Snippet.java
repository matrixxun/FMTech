package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class Snippet
  extends MessageNano
{
  public boolean hasSnippetHtml = false;
  public String snippetHtml = "";
  
  public Snippet()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasSnippetHtml) || (!this.snippetHtml.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(1, this.snippetHtml);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasSnippetHtml) || (!this.snippetHtml.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(1, this.snippetHtml);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.Snippet
 * JD-Core Version:    0.7.0.1
 */