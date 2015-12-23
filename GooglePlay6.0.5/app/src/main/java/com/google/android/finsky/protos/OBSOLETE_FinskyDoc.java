package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class OBSOLETE_FinskyDoc
  extends MessageNano
{
  private static volatile OBSOLETE_FinskyDoc[] _emptyArray;
  public Rating.AggregateRating aggregateRating = null;
  public FilterRules.Availability availability = null;
  public OBSOLETE_FinskyDoc[] child = emptyArray();
  public Common.Docid docid = null;
  public Common.Docid fetchDocid = null;
  public boolean hasTitle = false;
  public boolean hasUrl = false;
  public Common.Image[] image = Common.Image.emptyArray();
  public Common.Offer[] offer = Common.Offer.emptyArray();
  public Common.Offer priceDeprecated = null;
  public Common.Docid sampleDocid = null;
  public String title = "";
  public OBSOLETE_FinskyTranslatedText[] translatedSnippet = OBSOLETE_FinskyTranslatedText.emptyArray();
  public String url = "";
  
  public OBSOLETE_FinskyDoc()
  {
    this.cachedSize = -1;
  }
  
  private static OBSOLETE_FinskyDoc[] emptyArray()
  {
    if (_emptyArray == null) {}
    synchronized (InternalNano.LAZY_INIT_LOCK)
    {
      if (_emptyArray == null) {
        _emptyArray = new OBSOLETE_FinskyDoc[0];
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
    if (this.fetchDocid != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(2, this.fetchDocid);
    }
    if (this.sampleDocid != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(3, this.sampleDocid);
    }
    if ((this.hasTitle) || (!this.title.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(4, this.title);
    }
    if ((this.hasUrl) || (!this.url.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(5, this.url);
    }
    if (this.priceDeprecated != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(7, this.priceDeprecated);
    }
    if (this.availability != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(9, this.availability);
    }
    if ((this.image != null) && (this.image.length > 0)) {
      for (int n = 0; n < this.image.length; n++)
      {
        Common.Image localImage = this.image[n];
        if (localImage != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(10, localImage);
        }
      }
    }
    if ((this.child != null) && (this.child.length > 0)) {
      for (int m = 0; m < this.child.length; m++)
      {
        OBSOLETE_FinskyDoc localOBSOLETE_FinskyDoc = this.child[m];
        if (localOBSOLETE_FinskyDoc != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(11, localOBSOLETE_FinskyDoc);
        }
      }
    }
    if (this.aggregateRating != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(13, this.aggregateRating);
    }
    if ((this.offer != null) && (this.offer.length > 0)) {
      for (int k = 0; k < this.offer.length; k++)
      {
        Common.Offer localOffer = this.offer[k];
        if (localOffer != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(14, localOffer);
        }
      }
    }
    if ((this.translatedSnippet != null) && (this.translatedSnippet.length > 0)) {
      for (int j = 0; j < this.translatedSnippet.length; j++)
      {
        OBSOLETE_FinskyTranslatedText localOBSOLETE_FinskyTranslatedText = this.translatedSnippet[j];
        if (localOBSOLETE_FinskyTranslatedText != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(15, localOBSOLETE_FinskyTranslatedText);
        }
      }
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if (this.docid != null) {
      paramCodedOutputByteBufferNano.writeMessage(1, this.docid);
    }
    if (this.fetchDocid != null) {
      paramCodedOutputByteBufferNano.writeMessage(2, this.fetchDocid);
    }
    if (this.sampleDocid != null) {
      paramCodedOutputByteBufferNano.writeMessage(3, this.sampleDocid);
    }
    if ((this.hasTitle) || (!this.title.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(4, this.title);
    }
    if ((this.hasUrl) || (!this.url.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(5, this.url);
    }
    if (this.priceDeprecated != null) {
      paramCodedOutputByteBufferNano.writeMessage(7, this.priceDeprecated);
    }
    if (this.availability != null) {
      paramCodedOutputByteBufferNano.writeMessage(9, this.availability);
    }
    if ((this.image != null) && (this.image.length > 0)) {
      for (int m = 0; m < this.image.length; m++)
      {
        Common.Image localImage = this.image[m];
        if (localImage != null) {
          paramCodedOutputByteBufferNano.writeMessage(10, localImage);
        }
      }
    }
    if ((this.child != null) && (this.child.length > 0)) {
      for (int k = 0; k < this.child.length; k++)
      {
        OBSOLETE_FinskyDoc localOBSOLETE_FinskyDoc = this.child[k];
        if (localOBSOLETE_FinskyDoc != null) {
          paramCodedOutputByteBufferNano.writeMessage(11, localOBSOLETE_FinskyDoc);
        }
      }
    }
    if (this.aggregateRating != null) {
      paramCodedOutputByteBufferNano.writeMessage(13, this.aggregateRating);
    }
    if ((this.offer != null) && (this.offer.length > 0)) {
      for (int j = 0; j < this.offer.length; j++)
      {
        Common.Offer localOffer = this.offer[j];
        if (localOffer != null) {
          paramCodedOutputByteBufferNano.writeMessage(14, localOffer);
        }
      }
    }
    if ((this.translatedSnippet != null) && (this.translatedSnippet.length > 0)) {
      for (int i = 0; i < this.translatedSnippet.length; i++)
      {
        OBSOLETE_FinskyTranslatedText localOBSOLETE_FinskyTranslatedText = this.translatedSnippet[i];
        if (localOBSOLETE_FinskyTranslatedText != null) {
          paramCodedOutputByteBufferNano.writeMessage(15, localOBSOLETE_FinskyTranslatedText);
        }
      }
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.OBSOLETE_FinskyDoc
 * JD-Core Version:    0.7.0.1
 */