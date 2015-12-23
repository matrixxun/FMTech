package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class VideoRentalTerm
  extends MessageNano
{
  private static volatile VideoRentalTerm[] _emptyArray;
  public boolean hasOfferAbbreviation = false;
  public boolean hasOfferType = false;
  public boolean hasRentalHeader = false;
  public String offerAbbreviation = "";
  public int offerType = 1;
  public String rentalHeader = "";
  public Term[] term = Term.emptyArray();
  
  public VideoRentalTerm()
  {
    this.cachedSize = -1;
  }
  
  public static VideoRentalTerm[] emptyArray()
  {
    if (_emptyArray == null) {}
    synchronized (InternalNano.LAZY_INIT_LOCK)
    {
      if (_emptyArray == null) {
        _emptyArray = new VideoRentalTerm[0];
      }
      return _emptyArray;
    }
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.offerType != 1) || (this.hasOfferType)) {
      i += CodedOutputByteBufferNano.computeInt32Size(1, this.offerType);
    }
    if ((this.hasOfferAbbreviation) || (!this.offerAbbreviation.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.offerAbbreviation);
    }
    if ((this.hasRentalHeader) || (!this.rentalHeader.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(3, this.rentalHeader);
    }
    if ((this.term != null) && (this.term.length > 0)) {
      for (int j = 0; j < this.term.length; j++)
      {
        Term localTerm = this.term[j];
        if (localTerm != null) {
          i += CodedOutputByteBufferNano.computeGroupSize(4, localTerm);
        }
      }
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.offerType != 1) || (this.hasOfferType)) {
      paramCodedOutputByteBufferNano.writeInt32(1, this.offerType);
    }
    if ((this.hasOfferAbbreviation) || (!this.offerAbbreviation.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.offerAbbreviation);
    }
    if ((this.hasRentalHeader) || (!this.rentalHeader.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(3, this.rentalHeader);
    }
    if ((this.term != null) && (this.term.length > 0)) {
      for (int i = 0; i < this.term.length; i++)
      {
        Term localTerm = this.term[i];
        if (localTerm != null) {
          paramCodedOutputByteBufferNano.writeGroup(4, localTerm);
        }
      }
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
  
  public static final class Term
    extends MessageNano
  {
    private static volatile Term[] _emptyArray;
    public String body = "";
    public boolean hasBody = false;
    public boolean hasHeader = false;
    public String header = "";
    
    public Term()
    {
      this.cachedSize = -1;
    }
    
    public static Term[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new Term[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasHeader) || (!this.header.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(5, this.header);
      }
      if ((this.hasBody) || (!this.body.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(6, this.body);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasHeader) || (!this.header.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(5, this.header);
      }
      if ((this.hasBody) || (!this.body.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(6, this.body);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.VideoRentalTerm
 * JD-Core Version:    0.7.0.1
 */