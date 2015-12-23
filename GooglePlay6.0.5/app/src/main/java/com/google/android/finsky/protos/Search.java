package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;
import java.util.Arrays;

public abstract interface Search
{
  public static final class RelatedSearch
    extends MessageNano
  {
    private static volatile RelatedSearch[] _emptyArray;
    public int backendId = 0;
    public boolean current = false;
    public int docType = 1;
    public boolean hasBackendId = false;
    public boolean hasCurrent = false;
    public boolean hasDocType = false;
    public boolean hasHeader = false;
    public boolean hasSearchUrl = false;
    public String header = "";
    public String searchUrl = "";
    
    public RelatedSearch()
    {
      this.cachedSize = -1;
    }
    
    public static RelatedSearch[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new RelatedSearch[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasSearchUrl) || (!this.searchUrl.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.searchUrl);
      }
      if ((this.hasHeader) || (!this.header.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.header);
      }
      if ((this.backendId != 0) || (this.hasBackendId)) {
        i += CodedOutputByteBufferNano.computeInt32Size(3, this.backendId);
      }
      if ((this.docType != 1) || (this.hasDocType)) {
        i += CodedOutputByteBufferNano.computeInt32Size(4, this.docType);
      }
      if ((this.hasCurrent) || (this.current)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(5);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasSearchUrl) || (!this.searchUrl.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.searchUrl);
      }
      if ((this.hasHeader) || (!this.header.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.header);
      }
      if ((this.backendId != 0) || (this.hasBackendId)) {
        paramCodedOutputByteBufferNano.writeInt32(3, this.backendId);
      }
      if ((this.docType != 1) || (this.hasDocType)) {
        paramCodedOutputByteBufferNano.writeInt32(4, this.docType);
      }
      if ((this.hasCurrent) || (this.current)) {
        paramCodedOutputByteBufferNano.writeBool(5, this.current);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class SearchResponse
    extends MessageNano
  {
    public boolean aggregateQuery = false;
    public Bucket[] bucket = Bucket.emptyArray();
    public boolean containsSnow = false;
    public DocV2[] doc = DocV2.emptyArray();
    public boolean fullPageReplaced = false;
    public boolean hasAggregateQuery = false;
    public boolean hasContainsSnow = false;
    public boolean hasFullPageReplaced = false;
    public boolean hasOriginalQuery = false;
    public boolean hasServerLogsCookie = false;
    public boolean hasSuggestedQuery = false;
    public String originalQuery = "";
    public Search.RelatedSearch[] relatedSearch = Search.RelatedSearch.emptyArray();
    public byte[] serverLogsCookie = WireFormatNano.EMPTY_BYTES;
    public String suggestedQuery = "";
    
    public SearchResponse()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasOriginalQuery) || (!this.originalQuery.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.originalQuery);
      }
      if ((this.hasSuggestedQuery) || (!this.suggestedQuery.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.suggestedQuery);
      }
      if ((this.hasAggregateQuery) || (this.aggregateQuery)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(3);
      }
      if ((this.bucket != null) && (this.bucket.length > 0)) {
        for (int m = 0; m < this.bucket.length; m++)
        {
          Bucket localBucket = this.bucket[m];
          if (localBucket != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(4, localBucket);
          }
        }
      }
      if ((this.doc != null) && (this.doc.length > 0)) {
        for (int k = 0; k < this.doc.length; k++)
        {
          DocV2 localDocV2 = this.doc[k];
          if (localDocV2 != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(5, localDocV2);
          }
        }
      }
      if ((this.relatedSearch != null) && (this.relatedSearch.length > 0)) {
        for (int j = 0; j < this.relatedSearch.length; j++)
        {
          Search.RelatedSearch localRelatedSearch = this.relatedSearch[j];
          if (localRelatedSearch != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(6, localRelatedSearch);
          }
        }
      }
      if ((this.hasServerLogsCookie) || (!Arrays.equals(this.serverLogsCookie, WireFormatNano.EMPTY_BYTES))) {
        i += CodedOutputByteBufferNano.computeBytesSize(7, this.serverLogsCookie);
      }
      if ((this.hasFullPageReplaced) || (this.fullPageReplaced)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(8);
      }
      if ((this.hasContainsSnow) || (this.containsSnow)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(9);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasOriginalQuery) || (!this.originalQuery.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.originalQuery);
      }
      if ((this.hasSuggestedQuery) || (!this.suggestedQuery.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.suggestedQuery);
      }
      if ((this.hasAggregateQuery) || (this.aggregateQuery)) {
        paramCodedOutputByteBufferNano.writeBool(3, this.aggregateQuery);
      }
      if ((this.bucket != null) && (this.bucket.length > 0)) {
        for (int k = 0; k < this.bucket.length; k++)
        {
          Bucket localBucket = this.bucket[k];
          if (localBucket != null) {
            paramCodedOutputByteBufferNano.writeMessage(4, localBucket);
          }
        }
      }
      if ((this.doc != null) && (this.doc.length > 0)) {
        for (int j = 0; j < this.doc.length; j++)
        {
          DocV2 localDocV2 = this.doc[j];
          if (localDocV2 != null) {
            paramCodedOutputByteBufferNano.writeMessage(5, localDocV2);
          }
        }
      }
      if ((this.relatedSearch != null) && (this.relatedSearch.length > 0)) {
        for (int i = 0; i < this.relatedSearch.length; i++)
        {
          Search.RelatedSearch localRelatedSearch = this.relatedSearch[i];
          if (localRelatedSearch != null) {
            paramCodedOutputByteBufferNano.writeMessage(6, localRelatedSearch);
          }
        }
      }
      if ((this.hasServerLogsCookie) || (!Arrays.equals(this.serverLogsCookie, WireFormatNano.EMPTY_BYTES))) {
        paramCodedOutputByteBufferNano.writeBytes(7, this.serverLogsCookie);
      }
      if ((this.hasFullPageReplaced) || (this.fullPageReplaced)) {
        paramCodedOutputByteBufferNano.writeBool(8, this.fullPageReplaced);
      }
      if ((this.hasContainsSnow) || (this.containsSnow)) {
        paramCodedOutputByteBufferNano.writeBool(9, this.containsSnow);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.Search
 * JD-Core Version:    0.7.0.1
 */