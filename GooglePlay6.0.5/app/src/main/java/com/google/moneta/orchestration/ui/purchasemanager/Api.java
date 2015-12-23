package com.google.moneta.orchestration.ui.purchasemanager;

import com.google.commerce.payments.orchestration.proto.ui.common.RequestContextOuterClass.RequestContext;
import com.google.commerce.payments.orchestration.proto.ui.common.ResponseContextOuterClass.ResponseContext;
import com.google.commerce.payments.orchestration.proto.ui.common.SecureDataHeaderOuterClass.SecureDataHeader;
import com.google.commerce.payments.orchestration.proto.ui.common.UiErrorOuterClass.UiError;
import com.google.commerce.payments.orchestration.proto.ui.common.components.ButtonContainerOuterClass.Button;
import com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.InstrumentFormOuterClass.InstrumentForm;
import com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.InstrumentFormOuterClass.InstrumentFormValue;
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
    public String checkoutOrderId = "";
    public ResponseContextOuterClass.ResponseContext context = null;
    public UiErrorOuterClass.UiError error = null;
    public int flowInstruction = 0;
    public Api.Page initialPage = null;
    public SecureDataHeaderOuterClass.SecureDataHeader secureHeader = null;
    
    public InitializeResponse()
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
      if (this.secureHeader != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(3, this.secureHeader);
      }
      if (this.flowInstruction != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(4, this.flowInstruction);
      }
      if (this.initialPage != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(5, this.initialPage);
      }
      if (!this.checkoutOrderId.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(6, this.checkoutOrderId);
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
      if (this.secureHeader != null) {
        paramCodedOutputByteBufferNano.writeMessage(3, this.secureHeader);
      }
      if (this.flowInstruction != 0) {
        paramCodedOutputByteBufferNano.writeInt32(4, this.flowInstruction);
      }
      if (this.initialPage != null) {
        paramCodedOutputByteBufferNano.writeMessage(5, this.initialPage);
      }
      if (!this.checkoutOrderId.equals("")) {
        paramCodedOutputByteBufferNano.writeString(6, this.checkoutOrderId);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class Page
    extends MessageNano
  {
    public boolean autoSubmit = false;
    public DependencyGraphOuterClass.DependencyGraph dependencyGraph = null;
    public InstrumentFormOuterClass.InstrumentForm form = null;
    public String progressDialogText = "";
    public String progressDialogTitle = "";
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
      if (!this.title.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.title);
      }
      if (this.titleImage != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.titleImage);
      }
      if (this.topInfoMessage != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(3, this.topInfoMessage);
      }
      if (this.dependencyGraph != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(4, this.dependencyGraph);
      }
      if (this.submitButton != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(5, this.submitButton);
      }
      if (this.autoSubmit) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(6);
      }
      if (!this.progressDialogText.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(7, this.progressDialogText);
      }
      if (!this.progressDialogTitle.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(8, this.progressDialogTitle);
      }
      if (this.form != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(9, this.form);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (!this.title.equals("")) {
        paramCodedOutputByteBufferNano.writeString(1, this.title);
      }
      if (this.titleImage != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.titleImage);
      }
      if (this.topInfoMessage != null) {
        paramCodedOutputByteBufferNano.writeMessage(3, this.topInfoMessage);
      }
      if (this.dependencyGraph != null) {
        paramCodedOutputByteBufferNano.writeMessage(4, this.dependencyGraph);
      }
      if (this.submitButton != null) {
        paramCodedOutputByteBufferNano.writeMessage(5, this.submitButton);
      }
      if (this.autoSubmit) {
        paramCodedOutputByteBufferNano.writeBool(6, this.autoSubmit);
      }
      if (!this.progressDialogText.equals("")) {
        paramCodedOutputByteBufferNano.writeString(7, this.progressDialogText);
      }
      if (!this.progressDialogTitle.equals("")) {
        paramCodedOutputByteBufferNano.writeString(8, this.progressDialogTitle);
      }
      if (this.form != null) {
        paramCodedOutputByteBufferNano.writeMessage(9, this.form);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class PageValue
    extends MessageNano
  {
    public byte[] dependencyGraphRequestToken = WireFormatNano.EMPTY_BYTES;
    public InstrumentFormOuterClass.InstrumentFormValue formValue = null;
    
    public PageValue()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.formValue != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.formValue);
      }
      if (!Arrays.equals(this.dependencyGraphRequestToken, WireFormatNano.EMPTY_BYTES)) {
        i += CodedOutputByteBufferNano.computeBytesSize(2, this.dependencyGraphRequestToken);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.formValue != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.formValue);
      }
      if (!Arrays.equals(this.dependencyGraphRequestToken, WireFormatNano.EMPTY_BYTES)) {
        paramCodedOutputByteBufferNano.writeBytes(2, this.dependencyGraphRequestToken);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class PurchaseManagerParameters
    extends MessageNano
  {
    public String checkoutOrderId = "";
    
    public PurchaseManagerParameters()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (!this.checkoutOrderId.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.checkoutOrderId);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (!this.checkoutOrderId.equals("")) {
        paramCodedOutputByteBufferNano.writeString(1, this.checkoutOrderId);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class SubmitRequest
    extends MessageNano
  {
    public RequestContextOuterClass.RequestContext context = null;
    public Api.PageValue pageValue = null;
    
    public SubmitRequest()
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
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class SubmitResponse
    extends MessageNano
  {
    public String checkoutOrderId = "";
    public ResponseContextOuterClass.ResponseContext context = null;
    public UiErrorOuterClass.UiError error = null;
    public int flowInstruction = 0;
    public Api.Page nextPage = null;
    public SecureDataHeaderOuterClass.SecureDataHeader secureHeader = null;
    
    public SubmitResponse()
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
      if (this.secureHeader != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(3, this.secureHeader);
      }
      if (this.flowInstruction != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(4, this.flowInstruction);
      }
      if (this.nextPage != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(5, this.nextPage);
      }
      if (!this.checkoutOrderId.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(6, this.checkoutOrderId);
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
      if (this.secureHeader != null) {
        paramCodedOutputByteBufferNano.writeMessage(3, this.secureHeader);
      }
      if (this.flowInstruction != 0) {
        paramCodedOutputByteBufferNano.writeInt32(4, this.flowInstruction);
      }
      if (this.nextPage != null) {
        paramCodedOutputByteBufferNano.writeMessage(5, this.nextPage);
      }
      if (!this.checkoutOrderId.equals("")) {
        paramCodedOutputByteBufferNano.writeString(6, this.checkoutOrderId);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.moneta.orchestration.ui.purchasemanager.Api
 * JD-Core Version:    0.7.0.1
 */