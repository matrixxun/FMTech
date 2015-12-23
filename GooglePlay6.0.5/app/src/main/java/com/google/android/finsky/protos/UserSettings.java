package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class UserSettings
  extends MessageNano
{
  public Onboardings dismissedOnboardings = null;
  public FamilyInfo familyInfo = null;
  public MarketingSettings marketingSettings = null;
  public PlayAccountProto.PlayAccountGlobalPurchaseCache playAccountGlobalPurchaseCache = null;
  public PlayAccountProto.PlayAccountUserPurchaseCache playAccountUserPurchaseCache = null;
  public PrivacySettings privacySettings = null;
  
  public UserSettings()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if (this.marketingSettings != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(1, this.marketingSettings);
    }
    if (this.privacySettings != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(2, this.privacySettings);
    }
    if (this.familyInfo != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(3, this.familyInfo);
    }
    if (this.dismissedOnboardings != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(4, this.dismissedOnboardings);
    }
    if (this.playAccountUserPurchaseCache != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(5, this.playAccountUserPurchaseCache);
    }
    if (this.playAccountGlobalPurchaseCache != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(6, this.playAccountGlobalPurchaseCache);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if (this.marketingSettings != null) {
      paramCodedOutputByteBufferNano.writeMessage(1, this.marketingSettings);
    }
    if (this.privacySettings != null) {
      paramCodedOutputByteBufferNano.writeMessage(2, this.privacySettings);
    }
    if (this.familyInfo != null) {
      paramCodedOutputByteBufferNano.writeMessage(3, this.familyInfo);
    }
    if (this.dismissedOnboardings != null) {
      paramCodedOutputByteBufferNano.writeMessage(4, this.dismissedOnboardings);
    }
    if (this.playAccountUserPurchaseCache != null) {
      paramCodedOutputByteBufferNano.writeMessage(5, this.playAccountUserPurchaseCache);
    }
    if (this.playAccountGlobalPurchaseCache != null) {
      paramCodedOutputByteBufferNano.writeMessage(6, this.playAccountGlobalPurchaseCache);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.UserSettings
 * JD-Core Version:    0.7.0.1
 */