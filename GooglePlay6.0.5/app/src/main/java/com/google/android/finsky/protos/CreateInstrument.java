package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;
import java.util.Arrays;

public abstract interface CreateInstrument
{
  public static final class AddressFormField
    extends MessageNano
  {
    public boolean hasType = false;
    public int type = 0;
    
    public AddressFormField()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.type != 0) || (this.hasType)) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.type);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.type != 0) || (this.hasType)) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.type);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class CreateInstrumentFlowHandle
    extends MessageNano
  {
    public int apiVersion = 0;
    public byte[] flowHandle = WireFormatNano.EMPTY_BYTES;
    public int flowType = 0;
    public boolean hasApiVersion = false;
    public boolean hasFlowHandle = false;
    public boolean hasFlowType = false;
    public boolean hasInstrumentType = false;
    public String instrumentType = "";
    
    public CreateInstrumentFlowHandle()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasInstrumentType) || (!this.instrumentType.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.instrumentType);
      }
      if ((this.hasFlowHandle) || (!Arrays.equals(this.flowHandle, WireFormatNano.EMPTY_BYTES))) {
        i += CodedOutputByteBufferNano.computeBytesSize(2, this.flowHandle);
      }
      if ((this.flowType != 0) || (this.hasFlowType)) {
        i += CodedOutputByteBufferNano.computeInt32Size(3, this.flowType);
      }
      if ((this.hasApiVersion) || (this.apiVersion != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(4, this.apiVersion);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasInstrumentType) || (!this.instrumentType.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.instrumentType);
      }
      if ((this.hasFlowHandle) || (!Arrays.equals(this.flowHandle, WireFormatNano.EMPTY_BYTES))) {
        paramCodedOutputByteBufferNano.writeBytes(2, this.flowHandle);
      }
      if ((this.flowType != 0) || (this.hasFlowType)) {
        paramCodedOutputByteBufferNano.writeInt32(3, this.flowType);
      }
      if ((this.hasApiVersion) || (this.apiVersion != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(4, this.apiVersion);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class CreateInstrumentFlowOption
    extends MessageNano
  {
    private static volatile CreateInstrumentFlowOption[] _emptyArray;
    public int apiMaxVersion = 0;
    public int apiMinVersion = 0;
    public boolean hasApiMaxVersion = false;
    public boolean hasApiMinVersion = false;
    public CreateInstrument.CreateInstrumentFlowHandle initialFlowHandle = null;
    
    public CreateInstrumentFlowOption()
    {
      this.cachedSize = -1;
    }
    
    public static CreateInstrumentFlowOption[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new CreateInstrumentFlowOption[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.initialFlowHandle != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.initialFlowHandle);
      }
      if ((this.hasApiMinVersion) || (this.apiMinVersion != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(2, this.apiMinVersion);
      }
      if ((this.hasApiMaxVersion) || (this.apiMaxVersion != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(3, this.apiMaxVersion);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.initialFlowHandle != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.initialFlowHandle);
      }
      if ((this.hasApiMinVersion) || (this.apiMinVersion != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(2, this.apiMinVersion);
      }
      if ((this.hasApiMaxVersion) || (this.apiMaxVersion != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(3, this.apiMaxVersion);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class DeviceCreateInstrumentFlowHandle
    extends MessageNano
  {
    public boolean hasToken = false;
    public byte[] token = WireFormatNano.EMPTY_BYTES;
    
    public DeviceCreateInstrumentFlowHandle()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasToken) || (!Arrays.equals(this.token, WireFormatNano.EMPTY_BYTES))) {
        i += CodedOutputByteBufferNano.computeBytesSize(1, this.token);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasToken) || (!Arrays.equals(this.token, WireFormatNano.EMPTY_BYTES))) {
        paramCodedOutputByteBufferNano.writeBytes(1, this.token);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class DeviceCreateInstrumentFlowState
    extends MessageNano
  {
    public CreateInstrument.DeviceCreateInstrumentFlowHandle handle = null;
    public CreateInstrument.ProfileForm profileForm = null;
    public CreateInstrument.UsernamePasswordForm usernamePasswordForm = null;
    
    public DeviceCreateInstrumentFlowState()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.handle != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.handle);
      }
      if (this.profileForm != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.profileForm);
      }
      if (this.usernamePasswordForm != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(3, this.usernamePasswordForm);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.handle != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.handle);
      }
      if (this.profileForm != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.profileForm);
      }
      if (this.usernamePasswordForm != null) {
        paramCodedOutputByteBufferNano.writeMessage(3, this.usernamePasswordForm);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class DeviceCreateInstrumentMetadata
    extends MessageNano
  {
    public int flowType = 0;
    public boolean hasFlowType = false;
    public boolean hasInstrumentType = false;
    public String instrumentType = "";
    
    public DeviceCreateInstrumentMetadata()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasInstrumentType) || (!this.instrumentType.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.instrumentType);
      }
      if ((this.flowType != 0) || (this.hasFlowType)) {
        i += CodedOutputByteBufferNano.computeInt32Size(2, this.flowType);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasInstrumentType) || (!this.instrumentType.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.instrumentType);
      }
      if ((this.flowType != 0) || (this.hasFlowType)) {
        paramCodedOutputByteBufferNano.writeInt32(2, this.flowType);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class FormInputRegexValidation
    extends MessageNano
  {
    public boolean hasRegex = false;
    public boolean hasRegexErrorMessage = false;
    public String regex = "";
    public String regexErrorMessage = "";
    
    public FormInputRegexValidation()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasRegexErrorMessage) || (!this.regexErrorMessage.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.regexErrorMessage);
      }
      if ((this.hasRegex) || (!this.regex.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.regex);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasRegexErrorMessage) || (!this.regexErrorMessage.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.regexErrorMessage);
      }
      if ((this.hasRegex) || (!this.regex.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.regex);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class ProfileForm
    extends MessageNano
  {
    public CreateInstrument.AddressFormField addressField = null;
    public CreateInstrument.TosFormField tosField = null;
    
    public ProfileForm()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.addressField != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.addressField);
      }
      if (this.tosField != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.tosField);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.addressField != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.addressField);
      }
      if (this.tosField != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.tosField);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class ResponseFormat
    extends MessageNano
  {
    public byte[] encryptionKey = WireFormatNano.EMPTY_BYTES;
    public boolean hasEncryptionKey = false;
    public boolean hasResponseFormatType = false;
    public boolean hasVendorSpecificSalt = false;
    public int responseFormatType = 0;
    public String vendorSpecificSalt = "";
    
    public ResponseFormat()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.responseFormatType != 0) || (this.hasResponseFormatType)) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.responseFormatType);
      }
      if ((this.hasEncryptionKey) || (!Arrays.equals(this.encryptionKey, WireFormatNano.EMPTY_BYTES))) {
        i += CodedOutputByteBufferNano.computeBytesSize(2, this.encryptionKey);
      }
      if ((this.hasVendorSpecificSalt) || (!this.vendorSpecificSalt.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.vendorSpecificSalt);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.responseFormatType != 0) || (this.hasResponseFormatType)) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.responseFormatType);
      }
      if ((this.hasEncryptionKey) || (!Arrays.equals(this.encryptionKey, WireFormatNano.EMPTY_BYTES))) {
        paramCodedOutputByteBufferNano.writeBytes(2, this.encryptionKey);
      }
      if ((this.hasVendorSpecificSalt) || (!this.vendorSpecificSalt.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.vendorSpecificSalt);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class TextFormField
    extends MessageNano
  {
    public int contentType = 0;
    public String defaultValue = "";
    public String errorMessage = "";
    public boolean hasContentType = false;
    public boolean hasDefaultValue = false;
    public boolean hasErrorMessage = false;
    public boolean hasLabel = false;
    public boolean hasUseBestGuess = false;
    public String label = "";
    public CreateInstrument.FormInputRegexValidation regexValidation = null;
    public CreateInstrument.ResponseFormat responseFormat = null;
    public boolean useBestGuess = false;
    
    public TextFormField()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasLabel) || (!this.label.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.label);
      }
      if ((this.contentType != 0) || (this.hasContentType)) {
        i += CodedOutputByteBufferNano.computeInt32Size(2, this.contentType);
      }
      if (this.responseFormat != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(3, this.responseFormat);
      }
      if (this.regexValidation != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(4, this.regexValidation);
      }
      if ((this.hasErrorMessage) || (!this.errorMessage.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(5, this.errorMessage);
      }
      if ((this.hasDefaultValue) || (!this.defaultValue.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(6, this.defaultValue);
      }
      if ((this.hasUseBestGuess) || (this.useBestGuess)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(7);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasLabel) || (!this.label.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.label);
      }
      if ((this.contentType != 0) || (this.hasContentType)) {
        paramCodedOutputByteBufferNano.writeInt32(2, this.contentType);
      }
      if (this.responseFormat != null) {
        paramCodedOutputByteBufferNano.writeMessage(3, this.responseFormat);
      }
      if (this.regexValidation != null) {
        paramCodedOutputByteBufferNano.writeMessage(4, this.regexValidation);
      }
      if ((this.hasErrorMessage) || (!this.errorMessage.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(5, this.errorMessage);
      }
      if ((this.hasDefaultValue) || (!this.defaultValue.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(6, this.defaultValue);
      }
      if ((this.hasUseBestGuess) || (this.useBestGuess)) {
        paramCodedOutputByteBufferNano.writeBool(7, this.useBestGuess);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class TosFormField
    extends MessageNano
  {
    public boolean hasTosHtml = false;
    public boolean hasTosHtmlShort = false;
    public boolean hasTosId = false;
    public boolean hasTosUrl = false;
    public String tosHtml = "";
    public String tosHtmlShort = "";
    public String tosId = "";
    public String tosUrl = "";
    
    public TosFormField()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasTosId) || (!this.tosId.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.tosId);
      }
      if ((this.hasTosHtml) || (!this.tosHtml.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.tosHtml);
      }
      if ((this.hasTosHtmlShort) || (!this.tosHtmlShort.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.tosHtmlShort);
      }
      if ((this.hasTosUrl) || (!this.tosUrl.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(4, this.tosUrl);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasTosId) || (!this.tosId.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.tosId);
      }
      if ((this.hasTosHtml) || (!this.tosHtml.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.tosHtml);
      }
      if ((this.hasTosHtmlShort) || (!this.tosHtmlShort.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.tosHtmlShort);
      }
      if ((this.hasTosUrl) || (!this.tosUrl.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(4, this.tosUrl);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class UsernamePasswordForm
    extends MessageNano
  {
    public String actionTitleText = "";
    public boolean hasActionTitleText = false;
    public boolean hasHelpUrl = false;
    public String helpUrl = "";
    public Common.Image logoImage = null;
    public CreateInstrument.TextFormField passwordField = null;
    public CreateInstrument.TosFormField tosField = null;
    public CreateInstrument.TextFormField usernameField = null;
    
    public UsernamePasswordForm()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.usernameField != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.usernameField);
      }
      if (this.passwordField != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.passwordField);
      }
      if (this.tosField != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(3, this.tosField);
      }
      if ((this.hasActionTitleText) || (!this.actionTitleText.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(4, this.actionTitleText);
      }
      if ((this.hasHelpUrl) || (!this.helpUrl.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(6, this.helpUrl);
      }
      if (this.logoImage != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(7, this.logoImage);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.usernameField != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.usernameField);
      }
      if (this.passwordField != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.passwordField);
      }
      if (this.tosField != null) {
        paramCodedOutputByteBufferNano.writeMessage(3, this.tosField);
      }
      if ((this.hasActionTitleText) || (!this.actionTitleText.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(4, this.actionTitleText);
      }
      if ((this.hasHelpUrl) || (!this.helpUrl.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(6, this.helpUrl);
      }
      if (this.logoImage != null) {
        paramCodedOutputByteBufferNano.writeMessage(7, this.logoImage);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.CreateInstrument
 * JD-Core Version:    0.7.0.1
 */