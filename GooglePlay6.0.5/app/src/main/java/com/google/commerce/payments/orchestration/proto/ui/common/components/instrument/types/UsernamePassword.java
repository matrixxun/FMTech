package com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.types;

import com.google.commerce.payments.orchestration.proto.ui.common.components.legal.LegalMessageOuterClass.LegalMessage;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.UiFieldOuterClass.UiField;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.UiFieldOuterClass.UiFieldValue;
import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;
import java.util.Arrays;

public abstract interface UsernamePassword
{
  public static final class UsernamePasswordForm
    extends MessageNano
  {
    public byte[] credentialsEncryptionKey = WireFormatNano.EMPTY_BYTES;
    public int encryptionType = 0;
    public String id = "";
    public LegalMessageOuterClass.LegalMessage legalMessage = null;
    public String loginHelpHtml = "";
    public UiFieldOuterClass.UiField passwordField = null;
    public String title = "";
    public UiFieldOuterClass.UiField usernameField = null;
    public String vendorSpecificSalt = "";
    
    public UsernamePasswordForm()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (!this.id.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.id);
      }
      if (this.encryptionType != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(2, this.encryptionType);
      }
      if (!Arrays.equals(this.credentialsEncryptionKey, WireFormatNano.EMPTY_BYTES)) {
        i += CodedOutputByteBufferNano.computeBytesSize(4, this.credentialsEncryptionKey);
      }
      if (this.usernameField != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(5, this.usernameField);
      }
      if (this.passwordField != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(6, this.passwordField);
      }
      if (!this.loginHelpHtml.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(7, this.loginHelpHtml);
      }
      if (this.legalMessage != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(8, this.legalMessage);
      }
      if (!this.title.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(9, this.title);
      }
      if (!this.vendorSpecificSalt.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(10, this.vendorSpecificSalt);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (!this.id.equals("")) {
        paramCodedOutputByteBufferNano.writeString(1, this.id);
      }
      if (this.encryptionType != 0) {
        paramCodedOutputByteBufferNano.writeInt32(2, this.encryptionType);
      }
      if (!Arrays.equals(this.credentialsEncryptionKey, WireFormatNano.EMPTY_BYTES)) {
        paramCodedOutputByteBufferNano.writeBytes(4, this.credentialsEncryptionKey);
      }
      if (this.usernameField != null) {
        paramCodedOutputByteBufferNano.writeMessage(5, this.usernameField);
      }
      if (this.passwordField != null) {
        paramCodedOutputByteBufferNano.writeMessage(6, this.passwordField);
      }
      if (!this.loginHelpHtml.equals("")) {
        paramCodedOutputByteBufferNano.writeString(7, this.loginHelpHtml);
      }
      if (this.legalMessage != null) {
        paramCodedOutputByteBufferNano.writeMessage(8, this.legalMessage);
      }
      if (!this.title.equals("")) {
        paramCodedOutputByteBufferNano.writeString(9, this.title);
      }
      if (!this.vendorSpecificSalt.equals("")) {
        paramCodedOutputByteBufferNano.writeString(10, this.vendorSpecificSalt);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class UsernamePasswordFormValue
    extends MessageNano
  {
    public int encryptionType = 0;
    public String hashedDeviceId = "";
    public String legalDocData = "";
    public UiFieldOuterClass.UiFieldValue password = null;
    public UiFieldOuterClass.UiFieldValue username = null;
    
    public UsernamePasswordFormValue()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.username != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.username);
      }
      if (this.password != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.password);
      }
      if (this.encryptionType != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(3, this.encryptionType);
      }
      if (!this.legalDocData.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(4, this.legalDocData);
      }
      if (!this.hashedDeviceId.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(5, this.hashedDeviceId);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.username != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.username);
      }
      if (this.password != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.password);
      }
      if (this.encryptionType != 0) {
        paramCodedOutputByteBufferNano.writeInt32(3, this.encryptionType);
      }
      if (!this.legalDocData.equals("")) {
        paramCodedOutputByteBufferNano.writeString(4, this.legalDocData);
      }
      if (!this.hashedDeviceId.equals("")) {
        paramCodedOutputByteBufferNano.writeString(5, this.hashedDeviceId);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.types.UsernamePassword
 * JD-Core Version:    0.7.0.1
 */