package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class CheckPromoOfferResponse
  extends MessageNano
{
  public AvailablePromoOffer[] availableOffer = AvailablePromoOffer.emptyArray();
  public int availablePromoOfferStatus = 0;
  public boolean hasAvailablePromoOfferStatus = false;
  public RedeemedPromoOffer redeemedOffer = null;
  
  public CheckPromoOfferResponse()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.availableOffer != null) && (this.availableOffer.length > 0)) {
      for (int j = 0; j < this.availableOffer.length; j++)
      {
        AvailablePromoOffer localAvailablePromoOffer = this.availableOffer[j];
        if (localAvailablePromoOffer != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(1, localAvailablePromoOffer);
        }
      }
    }
    if (this.redeemedOffer != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(2, this.redeemedOffer);
    }
    if ((this.availablePromoOfferStatus != 0) || (this.hasAvailablePromoOfferStatus)) {
      i += CodedOutputByteBufferNano.computeInt32Size(4, this.availablePromoOfferStatus);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.availableOffer != null) && (this.availableOffer.length > 0)) {
      for (int i = 0; i < this.availableOffer.length; i++)
      {
        AvailablePromoOffer localAvailablePromoOffer = this.availableOffer[i];
        if (localAvailablePromoOffer != null) {
          paramCodedOutputByteBufferNano.writeMessage(1, localAvailablePromoOffer);
        }
      }
    }
    if (this.redeemedOffer != null) {
      paramCodedOutputByteBufferNano.writeMessage(2, this.redeemedOffer);
    }
    if ((this.availablePromoOfferStatus != 0) || (this.hasAvailablePromoOfferStatus)) {
      paramCodedOutputByteBufferNano.writeInt32(4, this.availablePromoOfferStatus);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.CheckPromoOfferResponse
 * JD-Core Version:    0.7.0.1
 */