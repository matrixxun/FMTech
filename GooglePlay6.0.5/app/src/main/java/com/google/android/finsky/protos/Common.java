package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;

public abstract interface Common
{
  public static final class Attribution
    extends MessageNano
  {
    public boolean hasLicenseTitle = false;
    public boolean hasLicenseUrl = false;
    public boolean hasSourceTitle = false;
    public boolean hasSourceUrl = false;
    public String licenseTitle = "";
    public String licenseUrl = "";
    public String sourceTitle = "";
    public String sourceUrl = "";
    
    public Attribution()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasSourceTitle) || (!this.sourceTitle.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.sourceTitle);
      }
      if ((this.hasSourceUrl) || (!this.sourceUrl.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.sourceUrl);
      }
      if ((this.hasLicenseTitle) || (!this.licenseTitle.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.licenseTitle);
      }
      if ((this.hasLicenseUrl) || (!this.licenseUrl.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(4, this.licenseUrl);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasSourceTitle) || (!this.sourceTitle.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.sourceTitle);
      }
      if ((this.hasSourceUrl) || (!this.sourceUrl.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.sourceUrl);
      }
      if ((this.hasLicenseTitle) || (!this.licenseTitle.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.licenseTitle);
      }
      if ((this.hasLicenseUrl) || (!this.licenseUrl.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(4, this.licenseUrl);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class Docid
    extends MessageNano
  {
    private static volatile Docid[] _emptyArray;
    public int backend = 0;
    public String backendDocid = "";
    public boolean hasBackend = false;
    public boolean hasBackendDocid = false;
    public boolean hasType = false;
    public int type = 1;
    
    public Docid()
    {
      this.cachedSize = -1;
    }
    
    public static Docid[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new Docid[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasBackendDocid) || (!this.backendDocid.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.backendDocid);
      }
      if ((this.type != 1) || (this.hasType)) {
        i += CodedOutputByteBufferNano.computeInt32Size(2, this.type);
      }
      if ((this.backend != 0) || (this.hasBackend)) {
        i += CodedOutputByteBufferNano.computeInt32Size(3, this.backend);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasBackendDocid) || (!this.backendDocid.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.backendDocid);
      }
      if ((this.type != 1) || (this.hasType)) {
        paramCodedOutputByteBufferNano.writeInt32(2, this.type);
      }
      if ((this.backend != 0) || (this.hasBackend)) {
        paramCodedOutputByteBufferNano.writeInt32(3, this.backend);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class FamilyShareability
    extends MessageNano
  {
    public boolean hasState = false;
    public int state = 0;
    
    public FamilyShareability()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.state != 0) || (this.hasState)) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.state);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.state != 0) || (this.hasState)) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.state);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class GroupLicenseKey
    extends MessageNano
  {
    public long dasherCustomerId = 0L;
    public Common.Docid docid = null;
    public boolean hasDasherCustomerId = false;
    public boolean hasLicensedOfferType = false;
    public boolean hasRentalPeriodDays = false;
    public boolean hasType = false;
    public int licensedOfferType = 1;
    public int rentalPeriodDays = 0;
    public int type = 0;
    
    public GroupLicenseKey()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasDasherCustomerId) || (this.dasherCustomerId != 0L)) {
        i += 8 + CodedOutputByteBufferNano.computeTagSize(1);
      }
      if (this.docid != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.docid);
      }
      if ((this.licensedOfferType != 1) || (this.hasLicensedOfferType)) {
        i += CodedOutputByteBufferNano.computeInt32Size(3, this.licensedOfferType);
      }
      if ((this.type != 0) || (this.hasType)) {
        i += CodedOutputByteBufferNano.computeInt32Size(4, this.type);
      }
      if ((this.hasRentalPeriodDays) || (this.rentalPeriodDays != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(5, this.rentalPeriodDays);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasDasherCustomerId) || (this.dasherCustomerId != 0L)) {
        paramCodedOutputByteBufferNano.writeFixed64(1, this.dasherCustomerId);
      }
      if (this.docid != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.docid);
      }
      if ((this.licensedOfferType != 1) || (this.hasLicensedOfferType)) {
        paramCodedOutputByteBufferNano.writeInt32(3, this.licensedOfferType);
      }
      if ((this.type != 0) || (this.hasType)) {
        paramCodedOutputByteBufferNano.writeInt32(4, this.type);
      }
      if ((this.hasRentalPeriodDays) || (this.rentalPeriodDays != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(5, this.rentalPeriodDays);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class Image
    extends MessageNano
  {
    private static volatile Image[] _emptyArray;
    public String altTextLocalized = "";
    public Common.Attribution attribution = null;
    public boolean autogen = false;
    public String backgroundColorRgb = "";
    public Citation citation = null;
    public int deviceClass = 0;
    public Dimension dimension = null;
    public int durationSeconds = 0;
    public String fillColorRgb = "";
    public boolean hasAltTextLocalized = false;
    public boolean hasAutogen = false;
    public boolean hasBackgroundColorRgb = false;
    public boolean hasDeviceClass = false;
    public boolean hasDurationSeconds = false;
    public boolean hasFillColorRgb = false;
    public boolean hasImageType = false;
    public boolean hasImageUrl = false;
    public boolean hasPositionInSequence = false;
    public boolean hasSecureUrl = false;
    public boolean hasSupportsFifeUrlOptions = false;
    public int imageType = 0;
    public String imageUrl = "";
    public Common.ImagePalette palette = null;
    public int positionInSequence = 0;
    public String secureUrl = "";
    public boolean supportsFifeUrlOptions = false;
    
    public Image()
    {
      this.cachedSize = -1;
    }
    
    public static Image[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new Image[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.imageType != 0) || (this.hasImageType)) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.imageType);
      }
      if (this.dimension != null) {
        i += CodedOutputByteBufferNano.computeGroupSize(2, this.dimension);
      }
      if ((this.hasImageUrl) || (!this.imageUrl.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(5, this.imageUrl);
      }
      if ((this.hasAltTextLocalized) || (!this.altTextLocalized.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(6, this.altTextLocalized);
      }
      if ((this.hasSecureUrl) || (!this.secureUrl.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(7, this.secureUrl);
      }
      if ((this.hasPositionInSequence) || (this.positionInSequence != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(8, this.positionInSequence);
      }
      if ((this.hasSupportsFifeUrlOptions) || (this.supportsFifeUrlOptions)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(9);
      }
      if (this.citation != null) {
        i += CodedOutputByteBufferNano.computeGroupSize(10, this.citation);
      }
      if ((this.hasDurationSeconds) || (this.durationSeconds != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(14, this.durationSeconds);
      }
      if ((this.hasFillColorRgb) || (!this.fillColorRgb.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(15, this.fillColorRgb);
      }
      if ((this.hasAutogen) || (this.autogen)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(16);
      }
      if (this.attribution != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(17, this.attribution);
      }
      if ((this.hasBackgroundColorRgb) || (!this.backgroundColorRgb.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(19, this.backgroundColorRgb);
      }
      if (this.palette != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(20, this.palette);
      }
      if ((this.deviceClass != 0) || (this.hasDeviceClass)) {
        i += CodedOutputByteBufferNano.computeInt32Size(21, this.deviceClass);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.imageType != 0) || (this.hasImageType)) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.imageType);
      }
      if (this.dimension != null) {
        paramCodedOutputByteBufferNano.writeGroup(2, this.dimension);
      }
      if ((this.hasImageUrl) || (!this.imageUrl.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(5, this.imageUrl);
      }
      if ((this.hasAltTextLocalized) || (!this.altTextLocalized.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(6, this.altTextLocalized);
      }
      if ((this.hasSecureUrl) || (!this.secureUrl.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(7, this.secureUrl);
      }
      if ((this.hasPositionInSequence) || (this.positionInSequence != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(8, this.positionInSequence);
      }
      if ((this.hasSupportsFifeUrlOptions) || (this.supportsFifeUrlOptions)) {
        paramCodedOutputByteBufferNano.writeBool(9, this.supportsFifeUrlOptions);
      }
      if (this.citation != null) {
        paramCodedOutputByteBufferNano.writeGroup(10, this.citation);
      }
      if ((this.hasDurationSeconds) || (this.durationSeconds != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(14, this.durationSeconds);
      }
      if ((this.hasFillColorRgb) || (!this.fillColorRgb.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(15, this.fillColorRgb);
      }
      if ((this.hasAutogen) || (this.autogen)) {
        paramCodedOutputByteBufferNano.writeBool(16, this.autogen);
      }
      if (this.attribution != null) {
        paramCodedOutputByteBufferNano.writeMessage(17, this.attribution);
      }
      if ((this.hasBackgroundColorRgb) || (!this.backgroundColorRgb.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(19, this.backgroundColorRgb);
      }
      if (this.palette != null) {
        paramCodedOutputByteBufferNano.writeMessage(20, this.palette);
      }
      if ((this.deviceClass != 0) || (this.hasDeviceClass)) {
        paramCodedOutputByteBufferNano.writeInt32(21, this.deviceClass);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
    
    public static final class Citation
      extends MessageNano
    {
      public boolean hasTitleLocalized = false;
      public boolean hasUrl = false;
      public String titleLocalized = "";
      public String url = "";
      
      public Citation()
      {
        this.cachedSize = -1;
      }
      
      protected final int computeSerializedSize()
      {
        int i = super.computeSerializedSize();
        if ((this.hasTitleLocalized) || (!this.titleLocalized.equals(""))) {
          i += CodedOutputByteBufferNano.computeStringSize(11, this.titleLocalized);
        }
        if ((this.hasUrl) || (!this.url.equals(""))) {
          i += CodedOutputByteBufferNano.computeStringSize(12, this.url);
        }
        return i;
      }
      
      public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
        throws IOException
      {
        if ((this.hasTitleLocalized) || (!this.titleLocalized.equals(""))) {
          paramCodedOutputByteBufferNano.writeString(11, this.titleLocalized);
        }
        if ((this.hasUrl) || (!this.url.equals(""))) {
          paramCodedOutputByteBufferNano.writeString(12, this.url);
        }
        super.writeTo(paramCodedOutputByteBufferNano);
      }
    }
    
    public static final class Dimension
      extends MessageNano
    {
      public int aspectRatio = 0;
      public boolean hasAspectRatio = false;
      public boolean hasHeight = false;
      public boolean hasWidth = false;
      public int height = 0;
      public int width = 0;
      
      public Dimension()
      {
        this.cachedSize = -1;
      }
      
      protected final int computeSerializedSize()
      {
        int i = super.computeSerializedSize();
        if ((this.hasWidth) || (this.width != 0)) {
          i += CodedOutputByteBufferNano.computeInt32Size(3, this.width);
        }
        if ((this.hasHeight) || (this.height != 0)) {
          i += CodedOutputByteBufferNano.computeInt32Size(4, this.height);
        }
        if ((this.aspectRatio != 0) || (this.hasAspectRatio)) {
          i += CodedOutputByteBufferNano.computeInt32Size(18, this.aspectRatio);
        }
        return i;
      }
      
      public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
        throws IOException
      {
        if ((this.hasWidth) || (this.width != 0)) {
          paramCodedOutputByteBufferNano.writeInt32(3, this.width);
        }
        if ((this.hasHeight) || (this.height != 0)) {
          paramCodedOutputByteBufferNano.writeInt32(4, this.height);
        }
        if ((this.aspectRatio != 0) || (this.hasAspectRatio)) {
          paramCodedOutputByteBufferNano.writeInt32(18, this.aspectRatio);
        }
        super.writeTo(paramCodedOutputByteBufferNano);
      }
    }
  }
  
  public static final class ImagePalette
    extends MessageNano
  {
    public String darkMutedRgb = "";
    public String darkVibrantRgb = "";
    public boolean hasDarkMutedRgb = false;
    public boolean hasDarkVibrantRgb = false;
    public boolean hasLightMutedRgb = false;
    public boolean hasLightVibrantRgb = false;
    public boolean hasMutedRgb = false;
    public boolean hasVibrantRgb = false;
    public String lightMutedRgb = "";
    public String lightVibrantRgb = "";
    public String mutedRgb = "";
    public String vibrantRgb = "";
    
    public ImagePalette()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasLightVibrantRgb) || (!this.lightVibrantRgb.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.lightVibrantRgb);
      }
      if ((this.hasVibrantRgb) || (!this.vibrantRgb.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.vibrantRgb);
      }
      if ((this.hasDarkVibrantRgb) || (!this.darkVibrantRgb.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.darkVibrantRgb);
      }
      if ((this.hasLightMutedRgb) || (!this.lightMutedRgb.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(4, this.lightMutedRgb);
      }
      if ((this.hasMutedRgb) || (!this.mutedRgb.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(5, this.mutedRgb);
      }
      if ((this.hasDarkMutedRgb) || (!this.darkMutedRgb.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(6, this.darkMutedRgb);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasLightVibrantRgb) || (!this.lightVibrantRgb.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.lightVibrantRgb);
      }
      if ((this.hasVibrantRgb) || (!this.vibrantRgb.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.vibrantRgb);
      }
      if ((this.hasDarkVibrantRgb) || (!this.darkVibrantRgb.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.darkVibrantRgb);
      }
      if ((this.hasLightMutedRgb) || (!this.lightMutedRgb.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(4, this.lightMutedRgb);
      }
      if ((this.hasMutedRgb) || (!this.mutedRgb.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(5, this.mutedRgb);
      }
      if ((this.hasDarkMutedRgb) || (!this.darkMutedRgb.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(6, this.darkMutedRgb);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class Install
    extends MessageNano
  {
    private static volatile Install[] _emptyArray;
    public long androidId = 0L;
    public boolean bundled = false;
    public boolean hasAndroidId = false;
    public boolean hasBundled = false;
    public boolean hasLastUpdateTimestampMillis = false;
    public boolean hasPending = false;
    public boolean hasVersion = false;
    public long lastUpdateTimestampMillis = 0L;
    public boolean pending = false;
    public int version = 0;
    
    public Install()
    {
      this.cachedSize = -1;
    }
    
    public static Install[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new Install[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasAndroidId) || (this.androidId != 0L)) {
        i += 8 + CodedOutputByteBufferNano.computeTagSize(1);
      }
      if ((this.hasVersion) || (this.version != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(2, this.version);
      }
      if ((this.hasBundled) || (this.bundled)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(3);
      }
      if ((this.hasPending) || (this.pending)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(4);
      }
      if ((this.hasLastUpdateTimestampMillis) || (this.lastUpdateTimestampMillis != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(5, this.lastUpdateTimestampMillis);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasAndroidId) || (this.androidId != 0L)) {
        paramCodedOutputByteBufferNano.writeFixed64(1, this.androidId);
      }
      if ((this.hasVersion) || (this.version != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(2, this.version);
      }
      if ((this.hasBundled) || (this.bundled)) {
        paramCodedOutputByteBufferNano.writeBool(3, this.bundled);
      }
      if ((this.hasPending) || (this.pending)) {
        paramCodedOutputByteBufferNano.writeBool(4, this.pending);
      }
      if ((this.hasLastUpdateTimestampMillis) || (this.lastUpdateTimestampMillis != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(5, this.lastUpdateTimestampMillis);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class LicenseTerms
    extends MessageNano
  {
    public Common.GroupLicenseKey groupLicenseKey = null;
    
    public LicenseTerms()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.groupLicenseKey != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.groupLicenseKey);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.groupLicenseKey != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.groupLicenseKey);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class LicensedDocumentInfo
    extends MessageNano
  {
    public long assignedByGaiaId = 0L;
    public String dEPRECATEDAssignmentId = "";
    public long[] gaiaGroupId = WireFormatNano.EMPTY_LONG_ARRAY;
    public String groupLicenseCheckoutOrderId = "";
    public Common.GroupLicenseKey groupLicenseKey = null;
    public boolean hasAssignedByGaiaId = false;
    public boolean hasDEPRECATEDAssignmentId = false;
    public boolean hasGroupLicenseCheckoutOrderId = false;
    
    public LicensedDocumentInfo()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.gaiaGroupId != null) && (this.gaiaGroupId.length > 0)) {
        i = i + 8 * this.gaiaGroupId.length + 1 * this.gaiaGroupId.length;
      }
      if ((this.hasGroupLicenseCheckoutOrderId) || (!this.groupLicenseCheckoutOrderId.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.groupLicenseCheckoutOrderId);
      }
      if (this.groupLicenseKey != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(3, this.groupLicenseKey);
      }
      if ((this.hasAssignedByGaiaId) || (this.assignedByGaiaId != 0L)) {
        i += 8 + CodedOutputByteBufferNano.computeTagSize(4);
      }
      if ((this.hasDEPRECATEDAssignmentId) || (!this.dEPRECATEDAssignmentId.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(5, this.dEPRECATEDAssignmentId);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.gaiaGroupId != null) && (this.gaiaGroupId.length > 0)) {
        for (int i = 0; i < this.gaiaGroupId.length; i++) {
          paramCodedOutputByteBufferNano.writeFixed64(1, this.gaiaGroupId[i]);
        }
      }
      if ((this.hasGroupLicenseCheckoutOrderId) || (!this.groupLicenseCheckoutOrderId.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.groupLicenseCheckoutOrderId);
      }
      if (this.groupLicenseKey != null) {
        paramCodedOutputByteBufferNano.writeMessage(3, this.groupLicenseKey);
      }
      if ((this.hasAssignedByGaiaId) || (this.assignedByGaiaId != 0L)) {
        paramCodedOutputByteBufferNano.writeFixed64(4, this.assignedByGaiaId);
      }
      if ((this.hasDEPRECATEDAssignmentId) || (!this.dEPRECATEDAssignmentId.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(5, this.dEPRECATEDAssignmentId);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class MonthAndDay
    extends MessageNano
  {
    public int day = 0;
    public boolean hasDay = false;
    public boolean hasMonth = false;
    public int month = 0;
    
    public MonthAndDay()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasMonth) || (this.month != 0)) {
        i += CodedOutputByteBufferNano.computeUInt32Size(1, this.month);
      }
      if ((this.hasDay) || (this.day != 0)) {
        i += CodedOutputByteBufferNano.computeUInt32Size(2, this.day);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasMonth) || (this.month != 0)) {
        paramCodedOutputByteBufferNano.writeUInt32(1, this.month);
      }
      if ((this.hasDay) || (this.day != 0)) {
        paramCodedOutputByteBufferNano.writeUInt32(2, this.day);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class Offer
    extends MessageNano
  {
    private static volatile Offer[] _emptyArray;
    public String buyButtonLabel = "";
    public boolean checkoutFlowRequired = false;
    public Offer[] convertedPrice = emptyArray();
    public String currencyCode = "";
    public String formattedAmount = "";
    public String formattedDescription = "";
    public String formattedFullAmount = "";
    public String formattedName = "";
    public long fullPriceMicros = 0L;
    public boolean hasBuyButtonLabel = false;
    public boolean hasCheckoutFlowRequired = false;
    public boolean hasCurrencyCode = false;
    public boolean hasFormattedAmount = false;
    public boolean hasFormattedDescription = false;
    public boolean hasFormattedFullAmount = false;
    public boolean hasFormattedName = false;
    public boolean hasFullPriceMicros = false;
    public boolean hasInstantPurchaseEnabled = false;
    public boolean hasLicensedOfferType = false;
    public boolean hasMicros = false;
    public boolean hasOfferId = false;
    public boolean hasOfferType = false;
    public boolean hasOnSaleDate = false;
    public boolean hasOnSaleDateDisplayTimeZoneOffsetMsec = false;
    public boolean hasPreorder = false;
    public boolean hasPreorderFulfillmentDisplayDate = false;
    public boolean hasRepeatLastPayment = false;
    public boolean hasTemporarilyFree = false;
    public boolean instantPurchaseEnabled = false;
    public Common.LicenseTerms licenseTerms = null;
    public int licensedOfferType = 1;
    public long micros = 0L;
    public String offerId = "";
    public Common.OfferPayment[] offerPayment = Common.OfferPayment.emptyArray();
    public int offerType = 1;
    public long onSaleDate = 0L;
    public int onSaleDateDisplayTimeZoneOffsetMsec = 0;
    public boolean preorder = false;
    public long preorderFulfillmentDisplayDate = 0L;
    public String[] promotionLabel = WireFormatNano.EMPTY_STRING_ARRAY;
    public Common.RentalTerms rentalTerms = null;
    public boolean repeatLastPayment = false;
    public Common.SubscriptionContentTerms subscriptionContentTerms = null;
    public Common.SubscriptionTerms subscriptionTerms = null;
    public boolean temporarilyFree = false;
    public Common.VoucherOfferTerms voucherTerms = null;
    
    public Offer()
    {
      this.cachedSize = -1;
    }
    
    public static Offer[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new Offer[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasMicros) || (this.micros != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(1, this.micros);
      }
      if ((this.hasCurrencyCode) || (!this.currencyCode.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.currencyCode);
      }
      if ((this.hasFormattedAmount) || (!this.formattedAmount.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.formattedAmount);
      }
      if ((this.convertedPrice != null) && (this.convertedPrice.length > 0)) {
        for (int i1 = 0; i1 < this.convertedPrice.length; i1++)
        {
          Offer localOffer = this.convertedPrice[i1];
          if (localOffer != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(4, localOffer);
          }
        }
      }
      if ((this.hasCheckoutFlowRequired) || (this.checkoutFlowRequired)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(5);
      }
      if ((this.hasFullPriceMicros) || (this.fullPriceMicros != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(6, this.fullPriceMicros);
      }
      if ((this.hasFormattedFullAmount) || (!this.formattedFullAmount.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(7, this.formattedFullAmount);
      }
      if ((this.offerType != 1) || (this.hasOfferType)) {
        i += CodedOutputByteBufferNano.computeInt32Size(8, this.offerType);
      }
      if (this.rentalTerms != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(9, this.rentalTerms);
      }
      if ((this.hasOnSaleDate) || (this.onSaleDate != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(10, this.onSaleDate);
      }
      if ((this.promotionLabel != null) && (this.promotionLabel.length > 0))
      {
        int k = 0;
        int m = 0;
        for (int n = 0; n < this.promotionLabel.length; n++)
        {
          String str = this.promotionLabel[n];
          if (str != null)
          {
            k++;
            m += CodedOutputByteBufferNano.computeStringSizeNoTag(str);
          }
        }
        i = i + m + k * 1;
      }
      if (this.subscriptionTerms != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(12, this.subscriptionTerms);
      }
      if ((this.hasFormattedName) || (!this.formattedName.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(13, this.formattedName);
      }
      if ((this.hasFormattedDescription) || (!this.formattedDescription.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(14, this.formattedDescription);
      }
      if ((this.hasPreorder) || (this.preorder)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(15);
      }
      if ((this.hasOnSaleDateDisplayTimeZoneOffsetMsec) || (this.onSaleDateDisplayTimeZoneOffsetMsec != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(16, this.onSaleDateDisplayTimeZoneOffsetMsec);
      }
      if ((this.licensedOfferType != 1) || (this.hasLicensedOfferType)) {
        i += CodedOutputByteBufferNano.computeInt32Size(17, this.licensedOfferType);
      }
      if (this.subscriptionContentTerms != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(18, this.subscriptionContentTerms);
      }
      if ((this.hasOfferId) || (!this.offerId.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(19, this.offerId);
      }
      if ((this.hasPreorderFulfillmentDisplayDate) || (this.preorderFulfillmentDisplayDate != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(20, this.preorderFulfillmentDisplayDate);
      }
      if (this.licenseTerms != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(21, this.licenseTerms);
      }
      if ((this.hasTemporarilyFree) || (this.temporarilyFree)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(22);
      }
      if (this.voucherTerms != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(23, this.voucherTerms);
      }
      if ((this.offerPayment != null) && (this.offerPayment.length > 0)) {
        for (int j = 0; j < this.offerPayment.length; j++)
        {
          Common.OfferPayment localOfferPayment = this.offerPayment[j];
          if (localOfferPayment != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(24, localOfferPayment);
          }
        }
      }
      if ((this.hasRepeatLastPayment) || (this.repeatLastPayment)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(25);
      }
      if ((this.hasBuyButtonLabel) || (!this.buyButtonLabel.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(26, this.buyButtonLabel);
      }
      if ((this.hasInstantPurchaseEnabled) || (this.instantPurchaseEnabled)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(27);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasMicros) || (this.micros != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(1, this.micros);
      }
      if ((this.hasCurrencyCode) || (!this.currencyCode.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.currencyCode);
      }
      if ((this.hasFormattedAmount) || (!this.formattedAmount.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.formattedAmount);
      }
      if ((this.convertedPrice != null) && (this.convertedPrice.length > 0)) {
        for (int k = 0; k < this.convertedPrice.length; k++)
        {
          Offer localOffer = this.convertedPrice[k];
          if (localOffer != null) {
            paramCodedOutputByteBufferNano.writeMessage(4, localOffer);
          }
        }
      }
      if ((this.hasCheckoutFlowRequired) || (this.checkoutFlowRequired)) {
        paramCodedOutputByteBufferNano.writeBool(5, this.checkoutFlowRequired);
      }
      if ((this.hasFullPriceMicros) || (this.fullPriceMicros != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(6, this.fullPriceMicros);
      }
      if ((this.hasFormattedFullAmount) || (!this.formattedFullAmount.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(7, this.formattedFullAmount);
      }
      if ((this.offerType != 1) || (this.hasOfferType)) {
        paramCodedOutputByteBufferNano.writeInt32(8, this.offerType);
      }
      if (this.rentalTerms != null) {
        paramCodedOutputByteBufferNano.writeMessage(9, this.rentalTerms);
      }
      if ((this.hasOnSaleDate) || (this.onSaleDate != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(10, this.onSaleDate);
      }
      if ((this.promotionLabel != null) && (this.promotionLabel.length > 0)) {
        for (int j = 0; j < this.promotionLabel.length; j++)
        {
          String str = this.promotionLabel[j];
          if (str != null) {
            paramCodedOutputByteBufferNano.writeString(11, str);
          }
        }
      }
      if (this.subscriptionTerms != null) {
        paramCodedOutputByteBufferNano.writeMessage(12, this.subscriptionTerms);
      }
      if ((this.hasFormattedName) || (!this.formattedName.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(13, this.formattedName);
      }
      if ((this.hasFormattedDescription) || (!this.formattedDescription.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(14, this.formattedDescription);
      }
      if ((this.hasPreorder) || (this.preorder)) {
        paramCodedOutputByteBufferNano.writeBool(15, this.preorder);
      }
      if ((this.hasOnSaleDateDisplayTimeZoneOffsetMsec) || (this.onSaleDateDisplayTimeZoneOffsetMsec != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(16, this.onSaleDateDisplayTimeZoneOffsetMsec);
      }
      if ((this.licensedOfferType != 1) || (this.hasLicensedOfferType)) {
        paramCodedOutputByteBufferNano.writeInt32(17, this.licensedOfferType);
      }
      if (this.subscriptionContentTerms != null) {
        paramCodedOutputByteBufferNano.writeMessage(18, this.subscriptionContentTerms);
      }
      if ((this.hasOfferId) || (!this.offerId.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(19, this.offerId);
      }
      if ((this.hasPreorderFulfillmentDisplayDate) || (this.preorderFulfillmentDisplayDate != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(20, this.preorderFulfillmentDisplayDate);
      }
      if (this.licenseTerms != null) {
        paramCodedOutputByteBufferNano.writeMessage(21, this.licenseTerms);
      }
      if ((this.hasTemporarilyFree) || (this.temporarilyFree)) {
        paramCodedOutputByteBufferNano.writeBool(22, this.temporarilyFree);
      }
      if (this.voucherTerms != null) {
        paramCodedOutputByteBufferNano.writeMessage(23, this.voucherTerms);
      }
      if ((this.offerPayment != null) && (this.offerPayment.length > 0)) {
        for (int i = 0; i < this.offerPayment.length; i++)
        {
          Common.OfferPayment localOfferPayment = this.offerPayment[i];
          if (localOfferPayment != null) {
            paramCodedOutputByteBufferNano.writeMessage(24, localOfferPayment);
          }
        }
      }
      if ((this.hasRepeatLastPayment) || (this.repeatLastPayment)) {
        paramCodedOutputByteBufferNano.writeBool(25, this.repeatLastPayment);
      }
      if ((this.hasBuyButtonLabel) || (!this.buyButtonLabel.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(26, this.buyButtonLabel);
      }
      if ((this.hasInstantPurchaseEnabled) || (this.instantPurchaseEnabled)) {
        paramCodedOutputByteBufferNano.writeBool(27, this.instantPurchaseEnabled);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class OfferPayment
    extends MessageNano
  {
    private static volatile OfferPayment[] _emptyArray;
    public String currencyCode = "";
    public boolean hasCurrencyCode = false;
    public boolean hasMicros = false;
    public long micros = 0L;
    public Common.OfferPaymentOverride[] offerPaymentOverride = Common.OfferPaymentOverride.emptyArray();
    public Common.OfferPaymentPeriod offerPaymentPeriod = null;
    
    public OfferPayment()
    {
      this.cachedSize = -1;
    }
    
    public static OfferPayment[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new OfferPayment[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasMicros) || (this.micros != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(1, this.micros);
      }
      if ((this.hasCurrencyCode) || (!this.currencyCode.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.currencyCode);
      }
      if (this.offerPaymentPeriod != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(3, this.offerPaymentPeriod);
      }
      if ((this.offerPaymentOverride != null) && (this.offerPaymentOverride.length > 0)) {
        for (int j = 0; j < this.offerPaymentOverride.length; j++)
        {
          Common.OfferPaymentOverride localOfferPaymentOverride = this.offerPaymentOverride[j];
          if (localOfferPaymentOverride != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(4, localOfferPaymentOverride);
          }
        }
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasMicros) || (this.micros != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(1, this.micros);
      }
      if ((this.hasCurrencyCode) || (!this.currencyCode.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.currencyCode);
      }
      if (this.offerPaymentPeriod != null) {
        paramCodedOutputByteBufferNano.writeMessage(3, this.offerPaymentPeriod);
      }
      if ((this.offerPaymentOverride != null) && (this.offerPaymentOverride.length > 0)) {
        for (int i = 0; i < this.offerPaymentOverride.length; i++)
        {
          Common.OfferPaymentOverride localOfferPaymentOverride = this.offerPaymentOverride[i];
          if (localOfferPaymentOverride != null) {
            paramCodedOutputByteBufferNano.writeMessage(4, localOfferPaymentOverride);
          }
        }
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class OfferPaymentOverride
    extends MessageNano
  {
    private static volatile OfferPaymentOverride[] _emptyArray;
    public Common.MonthAndDay end = null;
    public boolean hasMicros = false;
    public long micros = 0L;
    public Common.MonthAndDay start = null;
    
    public OfferPaymentOverride()
    {
      this.cachedSize = -1;
    }
    
    public static OfferPaymentOverride[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new OfferPaymentOverride[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasMicros) || (this.micros != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(1, this.micros);
      }
      if (this.start != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.start);
      }
      if (this.end != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(3, this.end);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasMicros) || (this.micros != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(1, this.micros);
      }
      if (this.start != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.start);
      }
      if (this.end != null) {
        paramCodedOutputByteBufferNano.writeMessage(3, this.end);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class OfferPaymentPeriod
    extends MessageNano
  {
    public Common.TimePeriod duration = null;
    public Common.MonthAndDay end = null;
    public Common.MonthAndDay start = null;
    
    public OfferPaymentPeriod()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.duration != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.duration);
      }
      if (this.start != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.start);
      }
      if (this.end != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(3, this.end);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.duration != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.duration);
      }
      if (this.start != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.start);
      }
      if (this.end != null) {
        paramCodedOutputByteBufferNano.writeMessage(3, this.end);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class RedemptionRecordKey
    extends MessageNano
  {
    public long campaignId = 0L;
    public long codeGroupId = 0L;
    public boolean hasCampaignId = false;
    public boolean hasCodeGroupId = false;
    public boolean hasPublisherId = false;
    public boolean hasRecordId = false;
    public boolean hasType = false;
    public long publisherId = 0L;
    public long recordId = 0L;
    public int type = 1;
    
    public RedemptionRecordKey()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasPublisherId) || (this.publisherId != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(1, this.publisherId);
      }
      if ((this.hasCampaignId) || (this.campaignId != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(2, this.campaignId);
      }
      if ((this.hasCodeGroupId) || (this.codeGroupId != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(3, this.codeGroupId);
      }
      if ((this.hasRecordId) || (this.recordId != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(4, this.recordId);
      }
      if ((this.type != 1) || (this.hasType)) {
        i += CodedOutputByteBufferNano.computeInt32Size(5, this.type);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasPublisherId) || (this.publisherId != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(1, this.publisherId);
      }
      if ((this.hasCampaignId) || (this.campaignId != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(2, this.campaignId);
      }
      if ((this.hasCodeGroupId) || (this.codeGroupId != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(3, this.codeGroupId);
      }
      if ((this.hasRecordId) || (this.recordId != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(4, this.recordId);
      }
      if ((this.type != 1) || (this.hasType)) {
        paramCodedOutputByteBufferNano.writeInt32(5, this.type);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class RentalTerms
    extends MessageNano
  {
    public Common.TimePeriod activatePeriod = null;
    public int dEPRECATEDActivatePeriodSeconds = 0;
    public int dEPRECATEDGrantPeriodSeconds = 0;
    public long grantEndTimeSeconds = 0L;
    public Common.TimePeriod grantPeriod = null;
    public boolean hasDEPRECATEDActivatePeriodSeconds = false;
    public boolean hasDEPRECATEDGrantPeriodSeconds = false;
    public boolean hasGrantEndTimeSeconds = false;
    
    public RentalTerms()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasDEPRECATEDGrantPeriodSeconds) || (this.dEPRECATEDGrantPeriodSeconds != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.dEPRECATEDGrantPeriodSeconds);
      }
      if ((this.hasDEPRECATEDActivatePeriodSeconds) || (this.dEPRECATEDActivatePeriodSeconds != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(2, this.dEPRECATEDActivatePeriodSeconds);
      }
      if (this.grantPeriod != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(3, this.grantPeriod);
      }
      if (this.activatePeriod != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(4, this.activatePeriod);
      }
      if ((this.hasGrantEndTimeSeconds) || (this.grantEndTimeSeconds != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(5, this.grantEndTimeSeconds);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasDEPRECATEDGrantPeriodSeconds) || (this.dEPRECATEDGrantPeriodSeconds != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.dEPRECATEDGrantPeriodSeconds);
      }
      if ((this.hasDEPRECATEDActivatePeriodSeconds) || (this.dEPRECATEDActivatePeriodSeconds != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(2, this.dEPRECATEDActivatePeriodSeconds);
      }
      if (this.grantPeriod != null) {
        paramCodedOutputByteBufferNano.writeMessage(3, this.grantPeriod);
      }
      if (this.activatePeriod != null) {
        paramCodedOutputByteBufferNano.writeMessage(4, this.activatePeriod);
      }
      if ((this.hasGrantEndTimeSeconds) || (this.grantEndTimeSeconds != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(5, this.grantEndTimeSeconds);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class SeasonalSubscriptionInfo
    extends MessageNano
  {
    public boolean hasMidSeasonPricingStrategy = false;
    public boolean hasProrated = false;
    public int midSeasonPricingStrategy = 0;
    public Common.MonthAndDay periodEnd = null;
    public Common.MonthAndDay periodStart = null;
    public boolean prorated = false;
    
    public SeasonalSubscriptionInfo()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.periodStart != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.periodStart);
      }
      if (this.periodEnd != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.periodEnd);
      }
      if ((this.midSeasonPricingStrategy != 0) || (this.hasMidSeasonPricingStrategy)) {
        i += CodedOutputByteBufferNano.computeInt32Size(3, this.midSeasonPricingStrategy);
      }
      if ((this.hasProrated) || (this.prorated)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(4);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.periodStart != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.periodStart);
      }
      if (this.periodEnd != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.periodEnd);
      }
      if ((this.midSeasonPricingStrategy != 0) || (this.hasMidSeasonPricingStrategy)) {
        paramCodedOutputByteBufferNano.writeInt32(3, this.midSeasonPricingStrategy);
      }
      if ((this.hasProrated) || (this.prorated)) {
        paramCodedOutputByteBufferNano.writeBool(4, this.prorated);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class SignedData
    extends MessageNano
  {
    public boolean hasSignature = false;
    public boolean hasSignedData = false;
    public String signature = "";
    public String signedData = "";
    
    public SignedData()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasSignedData) || (!this.signedData.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.signedData);
      }
      if ((this.hasSignature) || (!this.signature.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.signature);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasSignedData) || (!this.signedData.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.signedData);
      }
      if ((this.hasSignature) || (!this.signature.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.signature);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class SubscriptionContentTerms
    extends MessageNano
  {
    public Common.Docid requiredSubscription = null;
    
    public SubscriptionContentTerms()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.requiredSubscription != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.requiredSubscription);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.requiredSubscription != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.requiredSubscription);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class SubscriptionTerms
    extends MessageNano
  {
    public String formattedPriceWithRecurrencePeriod = "";
    public Common.TimePeriod gracePeriod = null;
    public boolean hasFormattedPriceWithRecurrencePeriod = false;
    public boolean hasInitialValidUntilTimestampMsec = false;
    public boolean hasNextPaymentCurrencyCode = false;
    public boolean hasNextPaymentPriceMicros = false;
    public boolean hasResignup = false;
    public long initialValidUntilTimestampMsec = 0L;
    public String nextPaymentCurrencyCode = "";
    public long nextPaymentPriceMicros = 0L;
    public Common.TimePeriod recurringPeriod = null;
    public Common.Docid[] replaceDocid = Common.Docid.emptyArray();
    public boolean resignup = false;
    public Common.SeasonalSubscriptionInfo seasonalSubscriptionInfo = null;
    public Common.TimePeriod trialPeriod = null;
    
    public SubscriptionTerms()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.recurringPeriod != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.recurringPeriod);
      }
      if (this.trialPeriod != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.trialPeriod);
      }
      if ((this.hasFormattedPriceWithRecurrencePeriod) || (!this.formattedPriceWithRecurrencePeriod.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.formattedPriceWithRecurrencePeriod);
      }
      if (this.seasonalSubscriptionInfo != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(4, this.seasonalSubscriptionInfo);
      }
      if ((this.replaceDocid != null) && (this.replaceDocid.length > 0)) {
        for (int j = 0; j < this.replaceDocid.length; j++)
        {
          Common.Docid localDocid = this.replaceDocid[j];
          if (localDocid != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(5, localDocid);
          }
        }
      }
      if (this.gracePeriod != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(6, this.gracePeriod);
      }
      if ((this.hasResignup) || (this.resignup)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(7);
      }
      if ((this.hasInitialValidUntilTimestampMsec) || (this.initialValidUntilTimestampMsec != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(8, this.initialValidUntilTimestampMsec);
      }
      if ((this.hasNextPaymentCurrencyCode) || (!this.nextPaymentCurrencyCode.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(9, this.nextPaymentCurrencyCode);
      }
      if ((this.hasNextPaymentPriceMicros) || (this.nextPaymentPriceMicros != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(10, this.nextPaymentPriceMicros);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.recurringPeriod != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.recurringPeriod);
      }
      if (this.trialPeriod != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.trialPeriod);
      }
      if ((this.hasFormattedPriceWithRecurrencePeriod) || (!this.formattedPriceWithRecurrencePeriod.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.formattedPriceWithRecurrencePeriod);
      }
      if (this.seasonalSubscriptionInfo != null) {
        paramCodedOutputByteBufferNano.writeMessage(4, this.seasonalSubscriptionInfo);
      }
      if ((this.replaceDocid != null) && (this.replaceDocid.length > 0)) {
        for (int i = 0; i < this.replaceDocid.length; i++)
        {
          Common.Docid localDocid = this.replaceDocid[i];
          if (localDocid != null) {
            paramCodedOutputByteBufferNano.writeMessage(5, localDocid);
          }
        }
      }
      if (this.gracePeriod != null) {
        paramCodedOutputByteBufferNano.writeMessage(6, this.gracePeriod);
      }
      if ((this.hasResignup) || (this.resignup)) {
        paramCodedOutputByteBufferNano.writeBool(7, this.resignup);
      }
      if ((this.hasInitialValidUntilTimestampMsec) || (this.initialValidUntilTimestampMsec != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(8, this.initialValidUntilTimestampMsec);
      }
      if ((this.hasNextPaymentCurrencyCode) || (!this.nextPaymentCurrencyCode.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(9, this.nextPaymentCurrencyCode);
      }
      if ((this.hasNextPaymentPriceMicros) || (this.nextPaymentPriceMicros != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(10, this.nextPaymentPriceMicros);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class TimePeriod
    extends MessageNano
  {
    public int count = 0;
    public boolean hasCount = false;
    public boolean hasUnit = false;
    public int unit = 0;
    
    public TimePeriod()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.unit != 0) || (this.hasUnit)) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.unit);
      }
      if ((this.hasCount) || (this.count != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(2, this.count);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.unit != 0) || (this.hasUnit)) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.unit);
      }
      if ((this.hasCount) || (this.count != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(2, this.count);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class VoucherId
    extends MessageNano
  {
    public Common.RedemptionRecordKey key = null;
    public Common.Docid voucherDocid = null;
    
    public VoucherId()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.voucherDocid != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.voucherDocid);
      }
      if (this.key != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.key);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.voucherDocid != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.voucherDocid);
      }
      if (this.key != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.key);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class VoucherOfferTerms
    extends MessageNano
  {
    public boolean hasVoucherFormattedAmount = false;
    public boolean hasVoucherPriceMicros = false;
    public Common.Docid[] voucherDocid = Common.Docid.emptyArray();
    public String voucherFormattedAmount = "";
    public long voucherPriceMicros = 0L;
    
    public VoucherOfferTerms()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.voucherDocid != null) && (this.voucherDocid.length > 0)) {
        for (int j = 0; j < this.voucherDocid.length; j++)
        {
          Common.Docid localDocid = this.voucherDocid[j];
          if (localDocid != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(1, localDocid);
          }
        }
      }
      if ((this.hasVoucherPriceMicros) || (this.voucherPriceMicros != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(2, this.voucherPriceMicros);
      }
      if ((this.hasVoucherFormattedAmount) || (!this.voucherFormattedAmount.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.voucherFormattedAmount);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.voucherDocid != null) && (this.voucherDocid.length > 0)) {
        for (int i = 0; i < this.voucherDocid.length; i++)
        {
          Common.Docid localDocid = this.voucherDocid[i];
          if (localDocid != null) {
            paramCodedOutputByteBufferNano.writeMessage(1, localDocid);
          }
        }
      }
      if ((this.hasVoucherPriceMicros) || (this.voucherPriceMicros != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(2, this.voucherPriceMicros);
      }
      if ((this.hasVoucherFormattedAmount) || (!this.voucherFormattedAmount.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.voucherFormattedAmount);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.Common
 * JD-Core Version:    0.7.0.1
 */