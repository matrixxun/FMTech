package com.google.location.country;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;

public abstract interface Postaladdress
{
  public static final class PostalAddress
    extends MessageNano
  {
    public String[] addressLine = WireFormatNano.EMPTY_STRING_ARRAY;
    public String administrativeAreaName = "";
    public String countryName = "";
    public String countryNameCode = "";
    public String dependentLocalityName = "";
    public String dependentThoroughfareName = "";
    public String emailAddress = "";
    public String firmName = "";
    public String languageCode = "";
    public String localityName = "";
    public String postBoxNumber = "";
    public String postalCodeNumber = "";
    public String postalCodeNumberExtension = "";
    public String premiseName = "";
    public String recipientName = "";
    public String sortingCode = "";
    public String subAdministrativeAreaName = "";
    public String subPremiseName = "";
    public String thoroughfareName = "";
    public String thoroughfareNumber = "";
    
    public PostalAddress()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (!this.countryNameCode.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.countryNameCode);
      }
      if (!this.countryName.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.countryName);
      }
      if (!this.administrativeAreaName.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.administrativeAreaName);
      }
      if (!this.subAdministrativeAreaName.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(4, this.subAdministrativeAreaName);
      }
      if (!this.localityName.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(5, this.localityName);
      }
      if (!this.thoroughfareName.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(6, this.thoroughfareName);
      }
      if (!this.thoroughfareNumber.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(11, this.thoroughfareNumber);
      }
      if (!this.postalCodeNumber.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(12, this.postalCodeNumber);
      }
      if (!this.postalCodeNumberExtension.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(13, this.postalCodeNumberExtension);
      }
      if ((this.addressLine != null) && (this.addressLine.length > 0))
      {
        int j = 0;
        int k = 0;
        for (int m = 0; m < this.addressLine.length; m++)
        {
          String str = this.addressLine[m];
          if (str != null)
          {
            j++;
            k += CodedOutputByteBufferNano.computeStringSizeNoTag(str);
          }
        }
        i = i + k + j * 1;
      }
      if (!this.premiseName.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(15, this.premiseName);
      }
      if (!this.subPremiseName.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(16, this.subPremiseName);
      }
      if (!this.dependentLocalityName.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(17, this.dependentLocalityName);
      }
      if (!this.dependentThoroughfareName.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(21, this.dependentThoroughfareName);
      }
      if (!this.languageCode.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(26, this.languageCode);
      }
      if (!this.firmName.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(27, this.firmName);
      }
      if (!this.recipientName.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(28, this.recipientName);
      }
      if (!this.sortingCode.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(29, this.sortingCode);
      }
      if (!this.postBoxNumber.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(30, this.postBoxNumber);
      }
      if (!this.emailAddress.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(1150, this.emailAddress);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (!this.countryNameCode.equals("")) {
        paramCodedOutputByteBufferNano.writeString(1, this.countryNameCode);
      }
      if (!this.countryName.equals("")) {
        paramCodedOutputByteBufferNano.writeString(2, this.countryName);
      }
      if (!this.administrativeAreaName.equals("")) {
        paramCodedOutputByteBufferNano.writeString(3, this.administrativeAreaName);
      }
      if (!this.subAdministrativeAreaName.equals("")) {
        paramCodedOutputByteBufferNano.writeString(4, this.subAdministrativeAreaName);
      }
      if (!this.localityName.equals("")) {
        paramCodedOutputByteBufferNano.writeString(5, this.localityName);
      }
      if (!this.thoroughfareName.equals("")) {
        paramCodedOutputByteBufferNano.writeString(6, this.thoroughfareName);
      }
      if (!this.thoroughfareNumber.equals("")) {
        paramCodedOutputByteBufferNano.writeString(11, this.thoroughfareNumber);
      }
      if (!this.postalCodeNumber.equals("")) {
        paramCodedOutputByteBufferNano.writeString(12, this.postalCodeNumber);
      }
      if (!this.postalCodeNumberExtension.equals("")) {
        paramCodedOutputByteBufferNano.writeString(13, this.postalCodeNumberExtension);
      }
      if ((this.addressLine != null) && (this.addressLine.length > 0)) {
        for (int i = 0; i < this.addressLine.length; i++)
        {
          String str = this.addressLine[i];
          if (str != null) {
            paramCodedOutputByteBufferNano.writeString(14, str);
          }
        }
      }
      if (!this.premiseName.equals("")) {
        paramCodedOutputByteBufferNano.writeString(15, this.premiseName);
      }
      if (!this.subPremiseName.equals("")) {
        paramCodedOutputByteBufferNano.writeString(16, this.subPremiseName);
      }
      if (!this.dependentLocalityName.equals("")) {
        paramCodedOutputByteBufferNano.writeString(17, this.dependentLocalityName);
      }
      if (!this.dependentThoroughfareName.equals("")) {
        paramCodedOutputByteBufferNano.writeString(21, this.dependentThoroughfareName);
      }
      if (!this.languageCode.equals("")) {
        paramCodedOutputByteBufferNano.writeString(26, this.languageCode);
      }
      if (!this.firmName.equals("")) {
        paramCodedOutputByteBufferNano.writeString(27, this.firmName);
      }
      if (!this.recipientName.equals("")) {
        paramCodedOutputByteBufferNano.writeString(28, this.recipientName);
      }
      if (!this.sortingCode.equals("")) {
        paramCodedOutputByteBufferNano.writeString(29, this.sortingCode);
      }
      if (!this.postBoxNumber.equals("")) {
        paramCodedOutputByteBufferNano.writeString(30, this.postBoxNumber);
      }
      if (!this.emailAddress.equals("")) {
        paramCodedOutputByteBufferNano.writeString(1150, this.emailAddress);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.location.country.Postaladdress
 * JD-Core Version:    0.7.0.1
 */