package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class MySubscriptionDetails
  extends MessageNano
{
  public boolean cancelSubscription = false;
  public String formattedPrice = "";
  public boolean hasCancelSubscription = false;
  public boolean hasFormattedPrice = false;
  public boolean hasInTrialPeriod = false;
  public boolean hasPriceBylineHtml = false;
  public boolean hasSubscriptionStatusHtml = false;
  public boolean hasTitle = false;
  public boolean hasTitleBylineHtml = false;
  public boolean inTrialPeriod = false;
  public Link paymentDeclinedLearnMoreLink = null;
  public String priceBylineHtml = "";
  public String subscriptionStatusHtml = "";
  public String title = "";
  public String titleBylineHtml = "";
  public Common.Image titleBylineIcon = null;
  
  public MySubscriptionDetails()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasSubscriptionStatusHtml) || (!this.subscriptionStatusHtml.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(1, this.subscriptionStatusHtml);
    }
    if ((this.hasTitle) || (!this.title.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.title);
    }
    if ((this.hasTitleBylineHtml) || (!this.titleBylineHtml.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(3, this.titleBylineHtml);
    }
    if ((this.hasFormattedPrice) || (!this.formattedPrice.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(4, this.formattedPrice);
    }
    if ((this.hasPriceBylineHtml) || (!this.priceBylineHtml.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(5, this.priceBylineHtml);
    }
    if ((this.hasCancelSubscription) || (this.cancelSubscription)) {
      i += 1 + CodedOutputByteBufferNano.computeTagSize(6);
    }
    if (this.paymentDeclinedLearnMoreLink != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(7, this.paymentDeclinedLearnMoreLink);
    }
    if ((this.hasInTrialPeriod) || (this.inTrialPeriod)) {
      i += 1 + CodedOutputByteBufferNano.computeTagSize(8);
    }
    if (this.titleBylineIcon != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(9, this.titleBylineIcon);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasSubscriptionStatusHtml) || (!this.subscriptionStatusHtml.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(1, this.subscriptionStatusHtml);
    }
    if ((this.hasTitle) || (!this.title.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.title);
    }
    if ((this.hasTitleBylineHtml) || (!this.titleBylineHtml.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(3, this.titleBylineHtml);
    }
    if ((this.hasFormattedPrice) || (!this.formattedPrice.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(4, this.formattedPrice);
    }
    if ((this.hasPriceBylineHtml) || (!this.priceBylineHtml.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(5, this.priceBylineHtml);
    }
    if ((this.hasCancelSubscription) || (this.cancelSubscription)) {
      paramCodedOutputByteBufferNano.writeBool(6, this.cancelSubscription);
    }
    if (this.paymentDeclinedLearnMoreLink != null) {
      paramCodedOutputByteBufferNano.writeMessage(7, this.paymentDeclinedLearnMoreLink);
    }
    if ((this.hasInTrialPeriod) || (this.inTrialPeriod)) {
      paramCodedOutputByteBufferNano.writeBool(8, this.inTrialPeriod);
    }
    if (this.titleBylineIcon != null) {
      paramCodedOutputByteBufferNano.writeMessage(9, this.titleBylineIcon);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.MySubscriptionDetails
 * JD-Core Version:    0.7.0.1
 */