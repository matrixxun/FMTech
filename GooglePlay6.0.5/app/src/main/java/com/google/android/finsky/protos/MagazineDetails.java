package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class MagazineDetails
  extends MessageNano
{
  public String deliveryFrequencyDescription = "";
  public String deviceAvailabilityDescriptionHtml = "";
  public boolean hasDeliveryFrequencyDescription = false;
  public boolean hasDeviceAvailabilityDescriptionHtml = false;
  public boolean hasParentDetailsUrl = false;
  public boolean hasPsvDescription = false;
  public String parentDetailsUrl = "";
  public String psvDescription = "";
  
  public MagazineDetails()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasParentDetailsUrl) || (!this.parentDetailsUrl.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(1, this.parentDetailsUrl);
    }
    if ((this.hasDeviceAvailabilityDescriptionHtml) || (!this.deviceAvailabilityDescriptionHtml.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.deviceAvailabilityDescriptionHtml);
    }
    if ((this.hasPsvDescription) || (!this.psvDescription.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(3, this.psvDescription);
    }
    if ((this.hasDeliveryFrequencyDescription) || (!this.deliveryFrequencyDescription.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(4, this.deliveryFrequencyDescription);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasParentDetailsUrl) || (!this.parentDetailsUrl.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(1, this.parentDetailsUrl);
    }
    if ((this.hasDeviceAvailabilityDescriptionHtml) || (!this.deviceAvailabilityDescriptionHtml.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.deviceAvailabilityDescriptionHtml);
    }
    if ((this.hasPsvDescription) || (!this.psvDescription.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(3, this.psvDescription);
    }
    if ((this.hasDeliveryFrequencyDescription) || (!this.deliveryFrequencyDescription.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(4, this.deliveryFrequencyDescription);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.MagazineDetails
 * JD-Core Version:    0.7.0.1
 */