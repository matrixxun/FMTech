package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class Address
  extends MessageNano
{
  public String addressLine1 = "";
  public String addressLine2 = "";
  public String city = "";
  public String dependentLocality = "";
  public boolean deprecatedIsReduced = false;
  public String email = "";
  public String firstName = "";
  public boolean hasAddressLine1 = false;
  public boolean hasAddressLine2 = false;
  public boolean hasCity = false;
  public boolean hasDependentLocality = false;
  public boolean hasDeprecatedIsReduced = false;
  public boolean hasEmail = false;
  public boolean hasFirstName = false;
  public boolean hasLanguageCode = false;
  public boolean hasLastName = false;
  public boolean hasName = false;
  public boolean hasPhoneNumber = false;
  public boolean hasPostalCode = false;
  public boolean hasPostalCountry = false;
  public boolean hasSortingCode = false;
  public boolean hasState = false;
  public String languageCode = "";
  public String lastName = "";
  public String name = "";
  public String phoneNumber = "";
  public String postalCode = "";
  public String postalCountry = "";
  public String sortingCode = "";
  public String state = "";
  
  public Address()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasName) || (!this.name.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(1, this.name);
    }
    if ((this.hasAddressLine1) || (!this.addressLine1.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.addressLine1);
    }
    if ((this.hasAddressLine2) || (!this.addressLine2.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(3, this.addressLine2);
    }
    if ((this.hasCity) || (!this.city.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(4, this.city);
    }
    if ((this.hasState) || (!this.state.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(5, this.state);
    }
    if ((this.hasPostalCode) || (!this.postalCode.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(6, this.postalCode);
    }
    if ((this.hasPostalCountry) || (!this.postalCountry.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(7, this.postalCountry);
    }
    if ((this.hasDependentLocality) || (!this.dependentLocality.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(8, this.dependentLocality);
    }
    if ((this.hasSortingCode) || (!this.sortingCode.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(9, this.sortingCode);
    }
    if ((this.hasLanguageCode) || (!this.languageCode.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(10, this.languageCode);
    }
    if ((this.hasPhoneNumber) || (!this.phoneNumber.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(11, this.phoneNumber);
    }
    if ((this.hasDeprecatedIsReduced) || (this.deprecatedIsReduced)) {
      i += 1 + CodedOutputByteBufferNano.computeTagSize(12);
    }
    if ((this.hasFirstName) || (!this.firstName.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(13, this.firstName);
    }
    if ((this.hasLastName) || (!this.lastName.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(14, this.lastName);
    }
    if ((this.hasEmail) || (!this.email.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(15, this.email);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasName) || (!this.name.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(1, this.name);
    }
    if ((this.hasAddressLine1) || (!this.addressLine1.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.addressLine1);
    }
    if ((this.hasAddressLine2) || (!this.addressLine2.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(3, this.addressLine2);
    }
    if ((this.hasCity) || (!this.city.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(4, this.city);
    }
    if ((this.hasState) || (!this.state.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(5, this.state);
    }
    if ((this.hasPostalCode) || (!this.postalCode.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(6, this.postalCode);
    }
    if ((this.hasPostalCountry) || (!this.postalCountry.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(7, this.postalCountry);
    }
    if ((this.hasDependentLocality) || (!this.dependentLocality.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(8, this.dependentLocality);
    }
    if ((this.hasSortingCode) || (!this.sortingCode.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(9, this.sortingCode);
    }
    if ((this.hasLanguageCode) || (!this.languageCode.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(10, this.languageCode);
    }
    if ((this.hasPhoneNumber) || (!this.phoneNumber.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(11, this.phoneNumber);
    }
    if ((this.hasDeprecatedIsReduced) || (this.deprecatedIsReduced)) {
      paramCodedOutputByteBufferNano.writeBool(12, this.deprecatedIsReduced);
    }
    if ((this.hasFirstName) || (!this.firstName.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(13, this.firstName);
    }
    if ((this.hasLastName) || (!this.lastName.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(14, this.lastName);
    }
    if ((this.hasEmail) || (!this.email.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(15, this.email);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.Address
 * JD-Core Version:    0.7.0.1
 */