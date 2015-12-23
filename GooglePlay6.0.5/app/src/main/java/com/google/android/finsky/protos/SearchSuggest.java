package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;
import java.util.Arrays;

public abstract interface SearchSuggest
{
  public static final class NavSuggestion
    extends MessageNano
  {
    public String description = "";
    public String docId = "";
    public boolean hasDescription = false;
    public boolean hasDocId = false;
    public boolean hasImageBlob = false;
    public Common.Image image = null;
    public byte[] imageBlob = WireFormatNano.EMPTY_BYTES;
    
    public NavSuggestion()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasDocId) || (!this.docId.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.docId);
      }
      if ((this.hasImageBlob) || (!Arrays.equals(this.imageBlob, WireFormatNano.EMPTY_BYTES))) {
        i += CodedOutputByteBufferNano.computeBytesSize(2, this.imageBlob);
      }
      if (this.image != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(3, this.image);
      }
      if ((this.hasDescription) || (!this.description.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(4, this.description);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasDocId) || (!this.docId.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.docId);
      }
      if ((this.hasImageBlob) || (!Arrays.equals(this.imageBlob, WireFormatNano.EMPTY_BYTES))) {
        paramCodedOutputByteBufferNano.writeBytes(2, this.imageBlob);
      }
      if (this.image != null) {
        paramCodedOutputByteBufferNano.writeMessage(3, this.image);
      }
      if ((this.hasDescription) || (!this.description.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(4, this.description);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class SearchSuggestResponse
    extends MessageNano
  {
    public boolean hasServerLogsCookie = false;
    public byte[] serverLogsCookie = WireFormatNano.EMPTY_BYTES;
    public SearchSuggest.Suggestion[] suggestion = SearchSuggest.Suggestion.emptyArray();
    
    public SearchSuggestResponse()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.suggestion != null) && (this.suggestion.length > 0)) {
        for (int j = 0; j < this.suggestion.length; j++)
        {
          SearchSuggest.Suggestion localSuggestion = this.suggestion[j];
          if (localSuggestion != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(1, localSuggestion);
          }
        }
      }
      if ((this.hasServerLogsCookie) || (!Arrays.equals(this.serverLogsCookie, WireFormatNano.EMPTY_BYTES))) {
        i += CodedOutputByteBufferNano.computeBytesSize(2, this.serverLogsCookie);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.suggestion != null) && (this.suggestion.length > 0)) {
        for (int i = 0; i < this.suggestion.length; i++)
        {
          SearchSuggest.Suggestion localSuggestion = this.suggestion[i];
          if (localSuggestion != null) {
            paramCodedOutputByteBufferNano.writeMessage(1, localSuggestion);
          }
        }
      }
      if ((this.hasServerLogsCookie) || (!Arrays.equals(this.serverLogsCookie, WireFormatNano.EMPTY_BYTES))) {
        paramCodedOutputByteBufferNano.writeBytes(2, this.serverLogsCookie);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class Suggestion
    extends MessageNano
  {
    private static volatile Suggestion[] _emptyArray;
    public String displayText = "";
    public DocV2 document = null;
    public boolean hasDisplayText = false;
    public boolean hasServerLogsCookie = false;
    public boolean hasSuggestedQuery = false;
    public boolean hasType = false;
    public Common.Image image = null;
    public Link link = null;
    public SearchSuggest.NavSuggestion navSuggestion = null;
    public byte[] serverLogsCookie = WireFormatNano.EMPTY_BYTES;
    public String suggestedQuery = "";
    public int type = 1;
    
    public Suggestion()
    {
      this.cachedSize = -1;
    }
    
    public static Suggestion[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new Suggestion[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.type != 1) || (this.hasType)) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.type);
      }
      if ((this.hasSuggestedQuery) || (!this.suggestedQuery.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.suggestedQuery);
      }
      if (this.navSuggestion != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(3, this.navSuggestion);
      }
      if ((this.hasServerLogsCookie) || (!Arrays.equals(this.serverLogsCookie, WireFormatNano.EMPTY_BYTES))) {
        i += CodedOutputByteBufferNano.computeBytesSize(4, this.serverLogsCookie);
      }
      if (this.image != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(5, this.image);
      }
      if ((this.hasDisplayText) || (!this.displayText.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(6, this.displayText);
      }
      if (this.link != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(7, this.link);
      }
      if (this.document != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(8, this.document);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.type != 1) || (this.hasType)) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.type);
      }
      if ((this.hasSuggestedQuery) || (!this.suggestedQuery.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.suggestedQuery);
      }
      if (this.navSuggestion != null) {
        paramCodedOutputByteBufferNano.writeMessage(3, this.navSuggestion);
      }
      if ((this.hasServerLogsCookie) || (!Arrays.equals(this.serverLogsCookie, WireFormatNano.EMPTY_BYTES))) {
        paramCodedOutputByteBufferNano.writeBytes(4, this.serverLogsCookie);
      }
      if (this.image != null) {
        paramCodedOutputByteBufferNano.writeMessage(5, this.image);
      }
      if ((this.hasDisplayText) || (!this.displayText.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(6, this.displayText);
      }
      if (this.link != null) {
        paramCodedOutputByteBufferNano.writeMessage(7, this.link);
      }
      if (this.document != null) {
        paramCodedOutputByteBufferNano.writeMessage(8, this.document);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.SearchSuggest
 * JD-Core Version:    0.7.0.1
 */