package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class AddCreditCardPromoOffer
  extends MessageNano
{
  public String descriptionHtml = "";
  public boolean hasDescriptionHtml = false;
  public boolean hasHeaderText = false;
  public boolean hasIntroductoryTextHtml = false;
  public boolean hasNoActionDescription = false;
  public boolean hasOfferTitle = false;
  public boolean hasTermsAndConditionsHtml = false;
  public String headerText = "";
  public Common.Image image = null;
  public String introductoryTextHtml = "";
  public String noActionDescription = "";
  public String offerTitle = "";
  public String termsAndConditionsHtml = "";
  
  public AddCreditCardPromoOffer()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasHeaderText) || (!this.headerText.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(1, this.headerText);
    }
    if ((this.hasDescriptionHtml) || (!this.descriptionHtml.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.descriptionHtml);
    }
    if (this.image != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(3, this.image);
    }
    if ((this.hasIntroductoryTextHtml) || (!this.introductoryTextHtml.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(4, this.introductoryTextHtml);
    }
    if ((this.hasOfferTitle) || (!this.offerTitle.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(5, this.offerTitle);
    }
    if ((this.hasNoActionDescription) || (!this.noActionDescription.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(6, this.noActionDescription);
    }
    if ((this.hasTermsAndConditionsHtml) || (!this.termsAndConditionsHtml.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(7, this.termsAndConditionsHtml);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasHeaderText) || (!this.headerText.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(1, this.headerText);
    }
    if ((this.hasDescriptionHtml) || (!this.descriptionHtml.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.descriptionHtml);
    }
    if (this.image != null) {
      paramCodedOutputByteBufferNano.writeMessage(3, this.image);
    }
    if ((this.hasIntroductoryTextHtml) || (!this.introductoryTextHtml.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(4, this.introductoryTextHtml);
    }
    if ((this.hasOfferTitle) || (!this.offerTitle.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(5, this.offerTitle);
    }
    if ((this.hasNoActionDescription) || (!this.noActionDescription.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(6, this.noActionDescription);
    }
    if ((this.hasTermsAndConditionsHtml) || (!this.termsAndConditionsHtml.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(7, this.termsAndConditionsHtml);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.AddCreditCardPromoOffer
 * JD-Core Version:    0.7.0.1
 */