package com.google.commerce.payments.orchestration.proto.ui.instrumentmanager;

import com.google.commerce.payments.orchestration.proto.ui.common.FormFieldReferenceOuterClass.FormFieldReference;
import com.google.commerce.payments.orchestration.proto.ui.common.RequestContextOuterClass.RequestContext;
import com.google.commerce.payments.orchestration.proto.ui.common.ResponseContextOuterClass.ResponseContext;
import com.google.commerce.payments.orchestration.proto.ui.common.SecureDataHeaderOuterClass.SecureDataHeader;
import com.google.commerce.payments.orchestration.proto.ui.common.UiErrorOuterClass.UiError;
import com.google.commerce.payments.orchestration.proto.ui.common.components.ButtonContainerOuterClass.Button;
import com.google.commerce.payments.orchestration.proto.ui.common.components.customer.CustomerFormOuterClass.CustomerForm;
import com.google.commerce.payments.orchestration.proto.ui.common.components.customer.CustomerFormOuterClass.CustomerFormValue;
import com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.InstrumentFormOuterClass.InstrumentForm;
import com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.InstrumentFormOuterClass.InstrumentFormValue;
import com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.types.CreditCard.CreditCardUpdateForm;
import com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.types.CreditCard.CreditCardUpdateFormValue;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.DependencyGraphOuterClass.DependencyGraph;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.ImageWithCaptionOuterClass.ImageWithCaption;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.InfoMessageOuterClass.InfoMessage;
import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;
import java.util.Arrays;

public abstract interface Api
{
  public static final class InitializeResponse
    extends MessageNano
  {
    public ResponseContextOuterClass.ResponseContext context = null;
    public UiErrorOuterClass.UiError error = null;
    public boolean flowComplete = false;
    public Api.Page initialPage = null;
    public SecureDataHeaderOuterClass.SecureDataHeader secureHeader = null;
    
    public InitializeResponse()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.initialPage != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(4, this.initialPage);
      }
      if (this.error != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(5, this.error);
      }
      if (this.context != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(6, this.context);
      }
      if (this.flowComplete) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(8);
      }
      if (this.secureHeader != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(9, this.secureHeader);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.initialPage != null) {
        paramCodedOutputByteBufferNano.writeMessage(4, this.initialPage);
      }
      if (this.error != null) {
        paramCodedOutputByteBufferNano.writeMessage(5, this.error);
      }
      if (this.context != null) {
        paramCodedOutputByteBufferNano.writeMessage(6, this.context);
      }
      if (this.flowComplete) {
        paramCodedOutputByteBufferNano.writeBool(8, this.flowComplete);
      }
      if (this.secureHeader != null) {
        paramCodedOutputByteBufferNano.writeMessage(9, this.secureHeader);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class InstrumentManagerParameters
    extends MessageNano
  {
    public int action = 0;
    public boolean allowCreditCardCameraInput = false;
    public String cdpBrokerId = "";
    public String country = "US";
    public String currencyCode = "USD";
    public boolean includeF1InstrumentId = false;
    public String instrumentId = "";
    public String languageCode = "";
    
    public InstrumentManagerParameters()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.action != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.action);
      }
      if (!this.cdpBrokerId.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.cdpBrokerId);
      }
      if (!this.currencyCode.equals("USD")) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.currencyCode);
      }
      if (!this.country.equals("US")) {
        i += CodedOutputByteBufferNano.computeStringSize(4, this.country);
      }
      if (!this.instrumentId.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(5, this.instrumentId);
      }
      if (!this.languageCode.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(6, this.languageCode);
      }
      if (this.allowCreditCardCameraInput) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(7);
      }
      if (this.includeF1InstrumentId) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(9);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.action != 0) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.action);
      }
      if (!this.cdpBrokerId.equals("")) {
        paramCodedOutputByteBufferNano.writeString(2, this.cdpBrokerId);
      }
      if (!this.currencyCode.equals("USD")) {
        paramCodedOutputByteBufferNano.writeString(3, this.currencyCode);
      }
      if (!this.country.equals("US")) {
        paramCodedOutputByteBufferNano.writeString(4, this.country);
      }
      if (!this.instrumentId.equals("")) {
        paramCodedOutputByteBufferNano.writeString(5, this.instrumentId);
      }
      if (!this.languageCode.equals("")) {
        paramCodedOutputByteBufferNano.writeString(6, this.languageCode);
      }
      if (this.allowCreditCardCameraInput) {
        paramCodedOutputByteBufferNano.writeBool(7, this.allowCreditCardCameraInput);
      }
      if (this.includeF1InstrumentId) {
        paramCodedOutputByteBufferNano.writeBool(9, this.includeF1InstrumentId);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class Page
    extends MessageNano
  {
    public boolean autoSubmit = false;
    public CreditCard.CreditCardUpdateForm creditCardUpdateForm = null;
    public CustomerFormOuterClass.CustomerForm customerForm = null;
    public DependencyGraphOuterClass.DependencyGraph dependencyGraph = null;
    public InstrumentFormOuterClass.InstrumentForm instrumentForm = null;
    public String progressDialogText = "";
    public String progressDialogTitle = "";
    public FormFieldReferenceOuterClass.FormFieldReference[] refreshTriggerField = FormFieldReferenceOuterClass.FormFieldReference.emptyArray();
    public ButtonContainerOuterClass.Button submitButton = null;
    public String title = "";
    public ImageWithCaptionOuterClass.ImageWithCaption titleImage = null;
    public InfoMessageOuterClass.InfoMessage topInfoMessage = null;
    
    public Page()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.customerForm != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.customerForm);
      }
      if (this.instrumentForm != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.instrumentForm);
      }
      if (this.creditCardUpdateForm != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(3, this.creditCardUpdateForm);
      }
      if (!this.title.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(4, this.title);
      }
      if ((this.refreshTriggerField != null) && (this.refreshTriggerField.length > 0)) {
        for (int j = 0; j < this.refreshTriggerField.length; j++)
        {
          FormFieldReferenceOuterClass.FormFieldReference localFormFieldReference = this.refreshTriggerField[j];
          if (localFormFieldReference != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(8, localFormFieldReference);
          }
        }
      }
      if (!this.progressDialogText.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(9, this.progressDialogText);
      }
      if (this.topInfoMessage != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(10, this.topInfoMessage);
      }
      if (this.titleImage != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(11, this.titleImage);
      }
      if (!this.progressDialogTitle.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(12, this.progressDialogTitle);
      }
      if (this.autoSubmit) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(13);
      }
      if (this.submitButton != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(14, this.submitButton);
      }
      if (this.dependencyGraph != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(15, this.dependencyGraph);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.customerForm != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.customerForm);
      }
      if (this.instrumentForm != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.instrumentForm);
      }
      if (this.creditCardUpdateForm != null) {
        paramCodedOutputByteBufferNano.writeMessage(3, this.creditCardUpdateForm);
      }
      if (!this.title.equals("")) {
        paramCodedOutputByteBufferNano.writeString(4, this.title);
      }
      if ((this.refreshTriggerField != null) && (this.refreshTriggerField.length > 0)) {
        for (int i = 0; i < this.refreshTriggerField.length; i++)
        {
          FormFieldReferenceOuterClass.FormFieldReference localFormFieldReference = this.refreshTriggerField[i];
          if (localFormFieldReference != null) {
            paramCodedOutputByteBufferNano.writeMessage(8, localFormFieldReference);
          }
        }
      }
      if (!this.progressDialogText.equals("")) {
        paramCodedOutputByteBufferNano.writeString(9, this.progressDialogText);
      }
      if (this.topInfoMessage != null) {
        paramCodedOutputByteBufferNano.writeMessage(10, this.topInfoMessage);
      }
      if (this.titleImage != null) {
        paramCodedOutputByteBufferNano.writeMessage(11, this.titleImage);
      }
      if (!this.progressDialogTitle.equals("")) {
        paramCodedOutputByteBufferNano.writeString(12, this.progressDialogTitle);
      }
      if (this.autoSubmit) {
        paramCodedOutputByteBufferNano.writeBool(13, this.autoSubmit);
      }
      if (this.submitButton != null) {
        paramCodedOutputByteBufferNano.writeMessage(14, this.submitButton);
      }
      if (this.dependencyGraph != null) {
        paramCodedOutputByteBufferNano.writeMessage(15, this.dependencyGraph);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class PageValue
    extends MessageNano
  {
    public CreditCard.CreditCardUpdateFormValue creditCardUpdateFormValue = null;
    public byte[] dependencyGraphRequestToken = WireFormatNano.EMPTY_BYTES;
    public CustomerFormOuterClass.CustomerFormValue newCustomer = null;
    public InstrumentFormOuterClass.InstrumentFormValue newInstrument = null;
    
    public PageValue()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.newCustomer != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.newCustomer);
      }
      if (this.newInstrument != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.newInstrument);
      }
      if (this.creditCardUpdateFormValue != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(3, this.creditCardUpdateFormValue);
      }
      if (!Arrays.equals(this.dependencyGraphRequestToken, WireFormatNano.EMPTY_BYTES)) {
        i += CodedOutputByteBufferNano.computeBytesSize(6, this.dependencyGraphRequestToken);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.newCustomer != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.newCustomer);
      }
      if (this.newInstrument != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.newInstrument);
      }
      if (this.creditCardUpdateFormValue != null) {
        paramCodedOutputByteBufferNano.writeMessage(3, this.creditCardUpdateFormValue);
      }
      if (!Arrays.equals(this.dependencyGraphRequestToken, WireFormatNano.EMPTY_BYTES)) {
        paramCodedOutputByteBufferNano.writeBytes(6, this.dependencyGraphRequestToken);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class RefreshPageRequest
    extends MessageNano
  {
    public RequestContextOuterClass.RequestContext context = null;
    public Api.PageValue pageValue = null;
    public FormFieldReferenceOuterClass.FormFieldReference refreshTriggerField = null;
    
    public RefreshPageRequest()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.context != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.context);
      }
      if (this.pageValue != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.pageValue);
      }
      if (this.refreshTriggerField != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(3, this.refreshTriggerField);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.context != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.context);
      }
      if (this.pageValue != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.pageValue);
      }
      if (this.refreshTriggerField != null) {
        paramCodedOutputByteBufferNano.writeMessage(3, this.refreshTriggerField);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class RefreshPageResponse
    extends MessageNano
  {
    public ResponseContextOuterClass.ResponseContext context = null;
    public UiErrorOuterClass.UiError error = null;
    public Api.Page nextPage = null;
    public SecureDataHeaderOuterClass.SecureDataHeader secureHeader = null;
    
    public RefreshPageResponse()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.error != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.error);
      }
      if (this.context != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.context);
      }
      if (this.nextPage != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(3, this.nextPage);
      }
      if (this.secureHeader != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(4, this.secureHeader);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.error != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.error);
      }
      if (this.context != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.context);
      }
      if (this.nextPage != null) {
        paramCodedOutputByteBufferNano.writeMessage(3, this.nextPage);
      }
      if (this.secureHeader != null) {
        paramCodedOutputByteBufferNano.writeMessage(4, this.secureHeader);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class SavePageRequest
    extends MessageNano
  {
    public RequestContextOuterClass.RequestContext context = null;
    public Api.PageValue pageValue = null;
    public Api.InstrumentManagerParameters parameters = null;
    
    public SavePageRequest()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.context != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.context);
      }
      if (this.parameters != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.parameters);
      }
      if (this.pageValue != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(4, this.pageValue);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.context != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.context);
      }
      if (this.parameters != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.parameters);
      }
      if (this.pageValue != null) {
        paramCodedOutputByteBufferNano.writeMessage(4, this.pageValue);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class SavePageResponse
    extends MessageNano
  {
    public ResponseContextOuterClass.ResponseContext context = null;
    public UiErrorOuterClass.UiError error = null;
    public long f1InstrumentId = 0L;
    public boolean flowComplete = false;
    public String instrumentId = "";
    public Api.Page nextPage = null;
    public SecureDataHeaderOuterClass.SecureDataHeader secureHeader = null;
    public boolean stayOnCurrentPage = false;
    
    public SavePageResponse()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (!this.instrumentId.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.instrumentId);
      }
      if (this.nextPage != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.nextPage);
      }
      if (this.error != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(3, this.error);
      }
      if (this.context != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(4, this.context);
      }
      if (this.flowComplete) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(5);
      }
      if (this.stayOnCurrentPage) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(6);
      }
      if (this.f1InstrumentId != 0L) {
        i += CodedOutputByteBufferNano.computeInt64Size(7, this.f1InstrumentId);
      }
      if (this.secureHeader != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(8, this.secureHeader);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (!this.instrumentId.equals("")) {
        paramCodedOutputByteBufferNano.writeString(1, this.instrumentId);
      }
      if (this.nextPage != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.nextPage);
      }
      if (this.error != null) {
        paramCodedOutputByteBufferNano.writeMessage(3, this.error);
      }
      if (this.context != null) {
        paramCodedOutputByteBufferNano.writeMessage(4, this.context);
      }
      if (this.flowComplete) {
        paramCodedOutputByteBufferNano.writeBool(5, this.flowComplete);
      }
      if (this.stayOnCurrentPage) {
        paramCodedOutputByteBufferNano.writeBool(6, this.stayOnCurrentPage);
      }
      if (this.f1InstrumentId != 0L) {
        paramCodedOutputByteBufferNano.writeInt64(7, this.f1InstrumentId);
      }
      if (this.secureHeader != null) {
        paramCodedOutputByteBufferNano.writeMessage(8, this.secureHeader);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.commerce.payments.orchestration.proto.ui.instrumentmanager.Api
 * JD-Core Version:    0.7.0.1
 */