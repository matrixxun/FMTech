package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public abstract interface Gift
{
  public static final class GiftDeliveryInfo
    extends MessageNano
  {
    public String giftMessage = "";
    public boolean hasGiftMessage = false;
    public boolean hasRecipientEmailAddress = false;
    public boolean hasSenderName = false;
    public String recipientEmailAddress = "";
    public String senderName = "";
    
    public GiftDeliveryInfo()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasSenderName) || (!this.senderName.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.senderName);
      }
      if ((this.hasRecipientEmailAddress) || (!this.recipientEmailAddress.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.recipientEmailAddress);
      }
      if ((this.hasGiftMessage) || (!this.giftMessage.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.giftMessage);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasSenderName) || (!this.senderName.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.senderName);
      }
      if ((this.hasRecipientEmailAddress) || (!this.recipientEmailAddress.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.recipientEmailAddress);
      }
      if ((this.hasGiftMessage) || (!this.giftMessage.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.giftMessage);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.Gift
 * JD-Core Version:    0.7.0.1
 */