package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public abstract interface GroupLicense
{
  public static final class GroupLicenseInfo
    extends MessageNano
  {
    public long gaiaGroupId = 0L;
    public Common.GroupLicenseKey groupLicenseKey = null;
    public boolean hasGaiaGroupId = false;
    public boolean hasLicensedOfferType = false;
    public int licensedOfferType = 1;
    
    public GroupLicenseInfo()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.licensedOfferType != 1) || (this.hasLicensedOfferType)) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.licensedOfferType);
      }
      if ((this.hasGaiaGroupId) || (this.gaiaGroupId != 0L)) {
        i += 8 + CodedOutputByteBufferNano.computeTagSize(2);
      }
      if (this.groupLicenseKey != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(3, this.groupLicenseKey);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.licensedOfferType != 1) || (this.hasLicensedOfferType)) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.licensedOfferType);
      }
      if ((this.hasGaiaGroupId) || (this.gaiaGroupId != 0L)) {
        paramCodedOutputByteBufferNano.writeFixed64(2, this.gaiaGroupId);
      }
      if (this.groupLicenseKey != null) {
        paramCodedOutputByteBufferNano.writeMessage(3, this.groupLicenseKey);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.GroupLicense
 * JD-Core Version:    0.7.0.1
 */