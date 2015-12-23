package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class Bucket
  extends MessageNano
{
  private static volatile Bucket[] _emptyArray;
  public String analyticsCookie = "";
  public DocV1[] document = DocV1.emptyArray();
  public long estimatedResults = 0L;
  public String fullContentsListUrl = "";
  public String fullContentsUrl = "";
  public boolean hasAnalyticsCookie = false;
  public boolean hasEstimatedResults = false;
  public boolean hasFullContentsListUrl = false;
  public boolean hasFullContentsUrl = false;
  public boolean hasIconUrl = false;
  public boolean hasMultiCorpus = false;
  public boolean hasNextPageUrl = false;
  public boolean hasOrdered = false;
  public boolean hasRelevance = false;
  public boolean hasTitle = false;
  public String iconUrl = "";
  public boolean multiCorpus = false;
  public String nextPageUrl = "";
  public boolean ordered = false;
  public double relevance = 0.0D;
  public String title = "";
  
  public Bucket()
  {
    this.cachedSize = -1;
  }
  
  public static Bucket[] emptyArray()
  {
    if (_emptyArray == null) {}
    synchronized (InternalNano.LAZY_INIT_LOCK)
    {
      if (_emptyArray == null) {
        _emptyArray = new Bucket[0];
      }
      return _emptyArray;
    }
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.document != null) && (this.document.length > 0)) {
      for (int j = 0; j < this.document.length; j++)
      {
        DocV1 localDocV1 = this.document[j];
        if (localDocV1 != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(1, localDocV1);
        }
      }
    }
    if ((this.hasMultiCorpus) || (this.multiCorpus)) {
      i += 1 + CodedOutputByteBufferNano.computeTagSize(2);
    }
    if ((this.hasTitle) || (!this.title.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(3, this.title);
    }
    if ((this.hasIconUrl) || (!this.iconUrl.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(4, this.iconUrl);
    }
    if ((this.hasFullContentsUrl) || (!this.fullContentsUrl.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(5, this.fullContentsUrl);
    }
    if ((this.hasRelevance) || (Double.doubleToLongBits(this.relevance) != Double.doubleToLongBits(0.0D))) {
      i += 8 + CodedOutputByteBufferNano.computeTagSize(6);
    }
    if ((this.hasEstimatedResults) || (this.estimatedResults != 0L)) {
      i += CodedOutputByteBufferNano.computeInt64Size(7, this.estimatedResults);
    }
    if ((this.hasAnalyticsCookie) || (!this.analyticsCookie.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(8, this.analyticsCookie);
    }
    if ((this.hasFullContentsListUrl) || (!this.fullContentsListUrl.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(9, this.fullContentsListUrl);
    }
    if ((this.hasNextPageUrl) || (!this.nextPageUrl.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(10, this.nextPageUrl);
    }
    if ((this.hasOrdered) || (this.ordered)) {
      i += 1 + CodedOutputByteBufferNano.computeTagSize(11);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.document != null) && (this.document.length > 0)) {
      for (int i = 0; i < this.document.length; i++)
      {
        DocV1 localDocV1 = this.document[i];
        if (localDocV1 != null) {
          paramCodedOutputByteBufferNano.writeMessage(1, localDocV1);
        }
      }
    }
    if ((this.hasMultiCorpus) || (this.multiCorpus)) {
      paramCodedOutputByteBufferNano.writeBool(2, this.multiCorpus);
    }
    if ((this.hasTitle) || (!this.title.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(3, this.title);
    }
    if ((this.hasIconUrl) || (!this.iconUrl.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(4, this.iconUrl);
    }
    if ((this.hasFullContentsUrl) || (!this.fullContentsUrl.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(5, this.fullContentsUrl);
    }
    if ((this.hasRelevance) || (Double.doubleToLongBits(this.relevance) != Double.doubleToLongBits(0.0D))) {
      paramCodedOutputByteBufferNano.writeDouble(6, this.relevance);
    }
    if ((this.hasEstimatedResults) || (this.estimatedResults != 0L)) {
      paramCodedOutputByteBufferNano.writeInt64(7, this.estimatedResults);
    }
    if ((this.hasAnalyticsCookie) || (!this.analyticsCookie.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(8, this.analyticsCookie);
    }
    if ((this.hasFullContentsListUrl) || (!this.fullContentsListUrl.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(9, this.fullContentsListUrl);
    }
    if ((this.hasNextPageUrl) || (!this.nextPageUrl.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(10, this.nextPageUrl);
    }
    if ((this.hasOrdered) || (this.ordered)) {
      paramCodedOutputByteBufferNano.writeBool(11, this.ordered);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.Bucket
 * JD-Core Version:    0.7.0.1
 */