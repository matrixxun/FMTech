package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.InvalidProtocolBufferNanoException;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;

public abstract interface ContentFilters
{
  public static final class ContentFilterSettingsResponse
    extends MessageNano
  {
    public ContentFilters.FilterRange[] filterRange = ContentFilters.FilterRange.emptyArray();
    public boolean hasInfoText = false;
    public boolean hasInfoTitle = false;
    public boolean hasTutorialText = false;
    public String infoText = "";
    public String infoTitle = "";
    public Common.Image tutorialImageFife = null;
    public String tutorialText = "";
    
    public ContentFilterSettingsResponse()
    {
      this.cachedSize = -1;
    }
    
    public static ContentFilterSettingsResponse parseFrom(byte[] paramArrayOfByte)
      throws InvalidProtocolBufferNanoException
    {
      return (ContentFilterSettingsResponse)MessageNano.mergeFrom$1ec43da(new ContentFilterSettingsResponse(), paramArrayOfByte, paramArrayOfByte.length);
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.filterRange != null) && (this.filterRange.length > 0)) {
        for (int j = 0; j < this.filterRange.length; j++)
        {
          ContentFilters.FilterRange localFilterRange = this.filterRange[j];
          if (localFilterRange != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(1, localFilterRange);
          }
        }
      }
      if ((this.hasTutorialText) || (!this.tutorialText.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.tutorialText);
      }
      if (this.tutorialImageFife != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(3, this.tutorialImageFife);
      }
      if ((this.hasInfoTitle) || (!this.infoTitle.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(4, this.infoTitle);
      }
      if ((this.hasInfoText) || (!this.infoText.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(5, this.infoText);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.filterRange != null) && (this.filterRange.length > 0)) {
        for (int i = 0; i < this.filterRange.length; i++)
        {
          ContentFilters.FilterRange localFilterRange = this.filterRange[i];
          if (localFilterRange != null) {
            paramCodedOutputByteBufferNano.writeMessage(1, localFilterRange);
          }
        }
      }
      if ((this.hasTutorialText) || (!this.tutorialText.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.tutorialText);
      }
      if (this.tutorialImageFife != null) {
        paramCodedOutputByteBufferNano.writeMessage(3, this.tutorialImageFife);
      }
      if ((this.hasInfoTitle) || (!this.infoTitle.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(4, this.infoTitle);
      }
      if ((this.hasInfoText) || (!this.infoText.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(5, this.infoText);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class FilterChoice
    extends MessageNano
  {
    private static volatile FilterChoice[] _emptyArray;
    public String dfeHeaderValue = "";
    public boolean hasDfeHeaderValue = false;
    public boolean hasLabel = false;
    public boolean hasLabelSummary = false;
    public boolean hasLevel = false;
    public boolean hasSelected = false;
    public Common.Image imageFife = null;
    public String label = "";
    public String labelSummary = "";
    public int level = 0;
    public boolean selected = false;
    
    public FilterChoice()
    {
      this.cachedSize = -1;
    }
    
    public static FilterChoice[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new FilterChoice[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasLevel) || (this.level != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.level);
      }
      if (this.imageFife != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.imageFife);
      }
      if ((this.hasLabel) || (!this.label.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.label);
      }
      if ((this.hasDfeHeaderValue) || (!this.dfeHeaderValue.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(4, this.dfeHeaderValue);
      }
      if ((this.hasSelected) || (this.selected)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(5);
      }
      if ((this.hasLabelSummary) || (!this.labelSummary.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(6, this.labelSummary);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasLevel) || (this.level != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.level);
      }
      if (this.imageFife != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.imageFife);
      }
      if ((this.hasLabel) || (!this.label.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.label);
      }
      if ((this.hasDfeHeaderValue) || (!this.dfeHeaderValue.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(4, this.dfeHeaderValue);
      }
      if ((this.hasSelected) || (this.selected)) {
        paramCodedOutputByteBufferNano.writeBool(5, this.selected);
      }
      if ((this.hasLabelSummary) || (!this.labelSummary.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(6, this.labelSummary);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class FilterRange
    extends MessageNano
  {
    private static volatile FilterRange[] _emptyArray;
    public String appPackageName = "";
    public int authorityId = 0;
    public String confirmationDialogContent = "";
    public String confirmationDialogTitle = "";
    public int[] documentType = WireFormatNano.EMPTY_INT_ARRAY;
    public ContentFilters.FilterChoice[] filterChoice = ContentFilters.FilterChoice.emptyArray();
    public boolean hasAppPackageName = false;
    public boolean hasAuthorityId = false;
    public boolean hasConfirmationDialogContent = false;
    public boolean hasConfirmationDialogTitle = false;
    public boolean hasLabel = false;
    public boolean hasMinVersionCode = false;
    public boolean hasRepresentChoiceAsToggle = false;
    public boolean hasSelectionDialogLabel = false;
    public Common.Image iconFife = null;
    public String label = "";
    public int minVersionCode = 0;
    public boolean representChoiceAsToggle = false;
    public String selectionDialogLabel = "";
    
    public FilterRange()
    {
      this.cachedSize = -1;
    }
    
    public static FilterRange[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new FilterRange[0];
        }
        return _emptyArray;
      }
    }
    
    public static FilterRange parseFrom(byte[] paramArrayOfByte)
      throws InvalidProtocolBufferNanoException
    {
      return (FilterRange)MessageNano.mergeFrom$1ec43da(new FilterRange(), paramArrayOfByte, paramArrayOfByte.length);
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.documentType != null) && (this.documentType.length > 0))
      {
        int k = 0;
        for (int m = 0; m < this.documentType.length; m++) {
          k += CodedOutputByteBufferNano.computeInt32SizeNoTag(this.documentType[m]);
        }
        i = i + k + 1 * this.documentType.length;
      }
      if ((this.hasAuthorityId) || (this.authorityId != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(2, this.authorityId);
      }
      if ((this.filterChoice != null) && (this.filterChoice.length > 0)) {
        for (int j = 0; j < this.filterChoice.length; j++)
        {
          ContentFilters.FilterChoice localFilterChoice = this.filterChoice[j];
          if (localFilterChoice != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(3, localFilterChoice);
          }
        }
      }
      if ((this.hasLabel) || (!this.label.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(4, this.label);
      }
      if (this.iconFife != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(5, this.iconFife);
      }
      if ((this.hasSelectionDialogLabel) || (!this.selectionDialogLabel.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(6, this.selectionDialogLabel);
      }
      if ((this.hasConfirmationDialogTitle) || (!this.confirmationDialogTitle.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(7, this.confirmationDialogTitle);
      }
      if ((this.hasConfirmationDialogContent) || (!this.confirmationDialogContent.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(8, this.confirmationDialogContent);
      }
      if ((this.hasRepresentChoiceAsToggle) || (this.representChoiceAsToggle)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(9);
      }
      if ((this.hasAppPackageName) || (!this.appPackageName.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(10, this.appPackageName);
      }
      if ((this.hasMinVersionCode) || (this.minVersionCode != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(11, this.minVersionCode);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.documentType != null) && (this.documentType.length > 0)) {
        for (int j = 0; j < this.documentType.length; j++) {
          paramCodedOutputByteBufferNano.writeInt32(1, this.documentType[j]);
        }
      }
      if ((this.hasAuthorityId) || (this.authorityId != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(2, this.authorityId);
      }
      if ((this.filterChoice != null) && (this.filterChoice.length > 0)) {
        for (int i = 0; i < this.filterChoice.length; i++)
        {
          ContentFilters.FilterChoice localFilterChoice = this.filterChoice[i];
          if (localFilterChoice != null) {
            paramCodedOutputByteBufferNano.writeMessage(3, localFilterChoice);
          }
        }
      }
      if ((this.hasLabel) || (!this.label.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(4, this.label);
      }
      if (this.iconFife != null) {
        paramCodedOutputByteBufferNano.writeMessage(5, this.iconFife);
      }
      if ((this.hasSelectionDialogLabel) || (!this.selectionDialogLabel.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(6, this.selectionDialogLabel);
      }
      if ((this.hasConfirmationDialogTitle) || (!this.confirmationDialogTitle.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(7, this.confirmationDialogTitle);
      }
      if ((this.hasConfirmationDialogContent) || (!this.confirmationDialogContent.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(8, this.confirmationDialogContent);
      }
      if ((this.hasRepresentChoiceAsToggle) || (this.representChoiceAsToggle)) {
        paramCodedOutputByteBufferNano.writeBool(9, this.representChoiceAsToggle);
      }
      if ((this.hasAppPackageName) || (!this.appPackageName.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(10, this.appPackageName);
      }
      if ((this.hasMinVersionCode) || (this.minVersionCode != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(11, this.minVersionCode);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.ContentFilters
 * JD-Core Version:    0.7.0.1
 */