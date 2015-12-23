package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public abstract interface Acquisition
{
  public static final class AutoDismissTemplate
    extends MessageNano
  {
    public int dismissMillisecond = 0;
    public boolean hasDismissMillisecond = false;
    public boolean hasMessageHtml = false;
    public String messageHtml = "";
    
    public AutoDismissTemplate()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasMessageHtml) || (!this.messageHtml.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.messageHtml);
      }
      if ((this.hasDismissMillisecond) || (this.dismissMillisecond != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(2, this.dismissMillisecond);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasMessageHtml) || (!this.messageHtml.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.messageHtml);
      }
      if ((this.hasDismissMillisecond) || (this.dismissMillisecond != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(2, this.dismissMillisecond);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class ComplexTemplate
    extends MessageNano
  {
    public String buttonLabel = "";
    public boolean hasButtonLabel = false;
    public boolean hasMessageHtml = false;
    public boolean hasShowCheckmark = false;
    public boolean hasTitle = false;
    public boolean hasTitleByline = false;
    public String messageHtml = "";
    public boolean showCheckmark = true;
    public Common.Image thumbnailImage = null;
    public String title = "";
    public String titleByline = "";
    
    public ComplexTemplate()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasTitle) || (!this.title.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.title);
      }
      if ((this.hasTitleByline) || (!this.titleByline.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.titleByline);
      }
      if ((this.hasMessageHtml) || (!this.messageHtml.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.messageHtml);
      }
      if ((this.hasShowCheckmark) || (this.showCheckmark != true)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(4);
      }
      if (this.thumbnailImage != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(5, this.thumbnailImage);
      }
      if ((this.hasButtonLabel) || (!this.buttonLabel.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(6, this.buttonLabel);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasTitle) || (!this.title.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.title);
      }
      if ((this.hasTitleByline) || (!this.titleByline.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.titleByline);
      }
      if ((this.hasMessageHtml) || (!this.messageHtml.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.messageHtml);
      }
      if ((this.hasShowCheckmark) || (this.showCheckmark != true)) {
        paramCodedOutputByteBufferNano.writeBool(4, this.showCheckmark);
      }
      if (this.thumbnailImage != null) {
        paramCodedOutputByteBufferNano.writeMessage(5, this.thumbnailImage);
      }
      if ((this.hasButtonLabel) || (!this.buttonLabel.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(6, this.buttonLabel);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class IconMessage
    extends MessageNano
  {
    public boolean hasMessageHtml = false;
    public Common.Image icon = null;
    public String messageHtml = "";
    
    public IconMessage()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.icon != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.icon);
      }
      if ((this.hasMessageHtml) || (!this.messageHtml.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.messageHtml);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.icon != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.icon);
      }
      if ((this.hasMessageHtml) || (!this.messageHtml.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.messageHtml);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class InstallAppAction
    extends MessageNano
  {
    public DocV2 doc = null;
    
    public InstallAppAction()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.doc != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.doc);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.doc != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.doc);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class OpenContainerDocumentAction
    extends MessageNano
  {
    public Link link = null;
    
    public OpenContainerDocumentAction()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.link != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.link);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.link != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.link);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class OpenDocAction
    extends MessageNano
  {
    public DocV2 doc = null;
    
    public OpenDocAction()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.doc != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.doc);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.doc != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.doc);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class OpenHomeAction
    extends MessageNano
  {
    public OpenHomeAction()
    {
      this.cachedSize = -1;
    }
  }
  
  public static final class PostAcquisitionPrompt
    extends MessageNano
  {
    public Acquisition.AutoDismissTemplate autoDismissTemplate = null;
    public Acquisition.ComplexTemplate complexTemplate = null;
    public Acquisition.PostSuccessAction postSuccessAction = null;
    public Acquisition.SetupWizardTemplate setupWizardTemplate = null;
    public Acquisition.SimpleMessageTemplate simpleMessageTemplate = null;
    public Acquisition.TitledTemplate titledTemplate = null;
    public Acquisition.TwoIconMessagesTemplate twoIconMessagesTemplate = null;
    
    public PostAcquisitionPrompt()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.postSuccessAction != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.postSuccessAction);
      }
      if (this.autoDismissTemplate != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.autoDismissTemplate);
      }
      if (this.simpleMessageTemplate != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(3, this.simpleMessageTemplate);
      }
      if (this.titledTemplate != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(4, this.titledTemplate);
      }
      if (this.complexTemplate != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(5, this.complexTemplate);
      }
      if (this.setupWizardTemplate != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(6, this.setupWizardTemplate);
      }
      if (this.twoIconMessagesTemplate != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(7, this.twoIconMessagesTemplate);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.postSuccessAction != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.postSuccessAction);
      }
      if (this.autoDismissTemplate != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.autoDismissTemplate);
      }
      if (this.simpleMessageTemplate != null) {
        paramCodedOutputByteBufferNano.writeMessage(3, this.simpleMessageTemplate);
      }
      if (this.titledTemplate != null) {
        paramCodedOutputByteBufferNano.writeMessage(4, this.titledTemplate);
      }
      if (this.complexTemplate != null) {
        paramCodedOutputByteBufferNano.writeMessage(5, this.complexTemplate);
      }
      if (this.setupWizardTemplate != null) {
        paramCodedOutputByteBufferNano.writeMessage(6, this.setupWizardTemplate);
      }
      if (this.twoIconMessagesTemplate != null) {
        paramCodedOutputByteBufferNano.writeMessage(7, this.twoIconMessagesTemplate);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class PostSuccessAction
    extends MessageNano
  {
    public Acquisition.InstallAppAction installApp = null;
    public Acquisition.OpenContainerDocumentAction openContainer = null;
    public Acquisition.OpenDocAction openDoc = null;
    public Acquisition.OpenHomeAction openHome = null;
    public Acquisition.PurchaseFlowAction purchaseFlow = null;
    
    public PostSuccessAction()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.openDoc != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.openDoc);
      }
      if (this.openHome != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.openHome);
      }
      if (this.installApp != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(3, this.installApp);
      }
      if (this.purchaseFlow != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(4, this.purchaseFlow);
      }
      if (this.openContainer != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(5, this.openContainer);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.openDoc != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.openDoc);
      }
      if (this.openHome != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.openHome);
      }
      if (this.installApp != null) {
        paramCodedOutputByteBufferNano.writeMessage(3, this.installApp);
      }
      if (this.purchaseFlow != null) {
        paramCodedOutputByteBufferNano.writeMessage(4, this.purchaseFlow);
      }
      if (this.openContainer != null) {
        paramCodedOutputByteBufferNano.writeMessage(5, this.openContainer);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class PurchaseFlowAction
    extends MessageNano
  {
    public boolean hasPurchaseOfferType = false;
    public DocV2 purchaseDoc = null;
    public int purchaseOfferType = 1;
    
    public PurchaseFlowAction()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.purchaseDoc != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.purchaseDoc);
      }
      if ((this.purchaseOfferType != 1) || (this.hasPurchaseOfferType)) {
        i += CodedOutputByteBufferNano.computeInt32Size(2, this.purchaseOfferType);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.purchaseDoc != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.purchaseDoc);
      }
      if ((this.purchaseOfferType != 1) || (this.hasPurchaseOfferType)) {
        paramCodedOutputByteBufferNano.writeInt32(2, this.purchaseOfferType);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class SetupWizardTemplate
    extends MessageNano
  {
    public String buttonLabel = "";
    public boolean hasButtonLabel = false;
    public boolean hasMessageHtml = false;
    public boolean hasTitle = false;
    public String messageHtml = "";
    public String title = "";
    
    public SetupWizardTemplate()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasTitle) || (!this.title.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.title);
      }
      if ((this.hasMessageHtml) || (!this.messageHtml.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.messageHtml);
      }
      if ((this.hasButtonLabel) || (!this.buttonLabel.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.buttonLabel);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasTitle) || (!this.title.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.title);
      }
      if ((this.hasMessageHtml) || (!this.messageHtml.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.messageHtml);
      }
      if ((this.hasButtonLabel) || (!this.buttonLabel.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.buttonLabel);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class SimpleMessageTemplate
    extends MessageNano
  {
    public String buttonLabel = "";
    public boolean hasButtonLabel = false;
    public boolean hasMessageHtml = false;
    public String messageHtml = "";
    
    public SimpleMessageTemplate()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasMessageHtml) || (!this.messageHtml.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.messageHtml);
      }
      if ((this.hasButtonLabel) || (!this.buttonLabel.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.buttonLabel);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasMessageHtml) || (!this.messageHtml.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.messageHtml);
      }
      if ((this.hasButtonLabel) || (!this.buttonLabel.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.buttonLabel);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class SuccessInfo
    extends MessageNano
  {
    public String buttonLabel = "";
    public boolean hasButtonLabel = false;
    public boolean hasMessageHtml = false;
    public boolean hasTitle = false;
    public boolean hasTitleBylineHtml = false;
    public LibraryUpdateProto.LibraryUpdate libraryUpdate = null;
    public String messageHtml = "";
    public Acquisition.PostSuccessAction postSuccessAction = null;
    public Common.Image thumbnailImage = null;
    public String title = "";
    public String titleBylineHtml = "";
    
    public SuccessInfo()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasMessageHtml) || (!this.messageHtml.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.messageHtml);
      }
      if ((this.hasButtonLabel) || (!this.buttonLabel.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.buttonLabel);
      }
      if (this.postSuccessAction != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(3, this.postSuccessAction);
      }
      if (this.libraryUpdate != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(4, this.libraryUpdate);
      }
      if ((this.hasTitle) || (!this.title.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(5, this.title);
      }
      if ((this.hasTitleBylineHtml) || (!this.titleBylineHtml.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(6, this.titleBylineHtml);
      }
      if (this.thumbnailImage != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(7, this.thumbnailImage);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasMessageHtml) || (!this.messageHtml.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.messageHtml);
      }
      if ((this.hasButtonLabel) || (!this.buttonLabel.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.buttonLabel);
      }
      if (this.postSuccessAction != null) {
        paramCodedOutputByteBufferNano.writeMessage(3, this.postSuccessAction);
      }
      if (this.libraryUpdate != null) {
        paramCodedOutputByteBufferNano.writeMessage(4, this.libraryUpdate);
      }
      if ((this.hasTitle) || (!this.title.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(5, this.title);
      }
      if ((this.hasTitleBylineHtml) || (!this.titleBylineHtml.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(6, this.titleBylineHtml);
      }
      if (this.thumbnailImage != null) {
        paramCodedOutputByteBufferNano.writeMessage(7, this.thumbnailImage);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class TitledTemplate
    extends MessageNano
  {
    public String buttonLabel = "";
    public boolean hasButtonLabel = false;
    public boolean hasMessageHtml = false;
    public boolean hasTitle = false;
    public String messageHtml = "";
    public String title = "";
    
    public TitledTemplate()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasTitle) || (!this.title.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.title);
      }
      if ((this.hasMessageHtml) || (!this.messageHtml.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.messageHtml);
      }
      if ((this.hasButtonLabel) || (!this.buttonLabel.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.buttonLabel);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasTitle) || (!this.title.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.title);
      }
      if ((this.hasMessageHtml) || (!this.messageHtml.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.messageHtml);
      }
      if ((this.hasButtonLabel) || (!this.buttonLabel.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.buttonLabel);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class TwoIconMessagesTemplate
    extends MessageNano
  {
    public String buttonLabel = "";
    public boolean hasButtonLabel = false;
    public boolean hasSecondaryButtonLabel = false;
    public Acquisition.IconMessage iconMessage1 = null;
    public Acquisition.IconMessage iconMessage2 = null;
    public String secondaryButtonLabel = "";
    public Acquisition.PostSuccessAction secondaryPostSuccessAction = null;
    
    public TwoIconMessagesTemplate()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.iconMessage1 != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.iconMessage1);
      }
      if (this.iconMessage2 != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.iconMessage2);
      }
      if ((this.hasButtonLabel) || (!this.buttonLabel.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.buttonLabel);
      }
      if ((this.hasSecondaryButtonLabel) || (!this.secondaryButtonLabel.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(4, this.secondaryButtonLabel);
      }
      if (this.secondaryPostSuccessAction != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(5, this.secondaryPostSuccessAction);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.iconMessage1 != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.iconMessage1);
      }
      if (this.iconMessage2 != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.iconMessage2);
      }
      if ((this.hasButtonLabel) || (!this.buttonLabel.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.buttonLabel);
      }
      if ((this.hasSecondaryButtonLabel) || (!this.secondaryButtonLabel.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(4, this.secondaryButtonLabel);
      }
      if (this.secondaryPostSuccessAction != null) {
        paramCodedOutputByteBufferNano.writeMessage(5, this.secondaryPostSuccessAction);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.Acquisition
 * JD-Core Version:    0.7.0.1
 */