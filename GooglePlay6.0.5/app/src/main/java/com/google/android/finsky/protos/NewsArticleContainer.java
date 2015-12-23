package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class NewsArticleContainer
  extends MessageNano
{
  public CallToAction action = null;
  public boolean hasNewsstandArticleId = false;
  public boolean hasPublishDateString = false;
  public boolean hasPublisher = false;
  public boolean hasSnippet = false;
  public boolean hasTitle = false;
  public String newsstandArticleId = "";
  public String publishDateString = "";
  public String publisher = "";
  public String snippet = "";
  public String title = "";
  
  public NewsArticleContainer()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasTitle) || (!this.title.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(1, this.title);
    }
    if ((this.hasSnippet) || (!this.snippet.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.snippet);
    }
    if (this.action != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(3, this.action);
    }
    if ((this.hasPublisher) || (!this.publisher.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(4, this.publisher);
    }
    if ((this.hasPublishDateString) || (!this.publishDateString.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(5, this.publishDateString);
    }
    if ((this.hasNewsstandArticleId) || (!this.newsstandArticleId.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(6, this.newsstandArticleId);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasTitle) || (!this.title.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(1, this.title);
    }
    if ((this.hasSnippet) || (!this.snippet.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.snippet);
    }
    if (this.action != null) {
      paramCodedOutputByteBufferNano.writeMessage(3, this.action);
    }
    if ((this.hasPublisher) || (!this.publisher.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(4, this.publisher);
    }
    if ((this.hasPublishDateString) || (!this.publishDateString.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(5, this.publishDateString);
    }
    if ((this.hasNewsstandArticleId) || (!this.newsstandArticleId.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(6, this.newsstandArticleId);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.NewsArticleContainer
 * JD-Core Version:    0.7.0.1
 */