package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;

public abstract interface Toc
{
  public static final class BillingConfig
    extends MessageNano
  {
    public Toc.CarrierBillingConfig carrierBillingConfig = null;
    public boolean hasMaxIabApiVersion = false;
    public int maxIabApiVersion = 0;
    
    public BillingConfig()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.carrierBillingConfig != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.carrierBillingConfig);
      }
      if ((this.hasMaxIabApiVersion) || (this.maxIabApiVersion != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(2, this.maxIabApiVersion);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.carrierBillingConfig != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.carrierBillingConfig);
      }
      if ((this.hasMaxIabApiVersion) || (this.maxIabApiVersion != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(2, this.maxIabApiVersion);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class CarrierBillingConfig
    extends MessageNano
  {
    public int apiVersion = 0;
    public String credentialsUrl = "";
    public boolean hasApiVersion = false;
    public boolean hasCredentialsUrl = false;
    public boolean hasId = false;
    public boolean hasName = false;
    public boolean hasPerTransactionCredentialsRequired = false;
    public boolean hasProvisioningUrl = false;
    public boolean hasSendSubscriberIdWithCarrierBillingRequests = false;
    public boolean hasTosRequired = false;
    public String id = "";
    public String name = "";
    public boolean perTransactionCredentialsRequired = false;
    public String provisioningUrl = "";
    public boolean sendSubscriberIdWithCarrierBillingRequests = false;
    public boolean tosRequired = false;
    
    public CarrierBillingConfig()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasId) || (!this.id.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.id);
      }
      if ((this.hasName) || (!this.name.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.name);
      }
      if ((this.hasApiVersion) || (this.apiVersion != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(3, this.apiVersion);
      }
      if ((this.hasProvisioningUrl) || (!this.provisioningUrl.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(4, this.provisioningUrl);
      }
      if ((this.hasCredentialsUrl) || (!this.credentialsUrl.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(5, this.credentialsUrl);
      }
      if ((this.hasTosRequired) || (this.tosRequired)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(6);
      }
      if ((this.hasPerTransactionCredentialsRequired) || (this.perTransactionCredentialsRequired)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(7);
      }
      if ((this.hasSendSubscriberIdWithCarrierBillingRequests) || (this.sendSubscriberIdWithCarrierBillingRequests)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(8);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasId) || (!this.id.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.id);
      }
      if ((this.hasName) || (!this.name.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.name);
      }
      if ((this.hasApiVersion) || (this.apiVersion != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(3, this.apiVersion);
      }
      if ((this.hasProvisioningUrl) || (!this.provisioningUrl.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(4, this.provisioningUrl);
      }
      if ((this.hasCredentialsUrl) || (!this.credentialsUrl.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(5, this.credentialsUrl);
      }
      if ((this.hasTosRequired) || (this.tosRequired)) {
        paramCodedOutputByteBufferNano.writeBool(6, this.tosRequired);
      }
      if ((this.hasPerTransactionCredentialsRequired) || (this.perTransactionCredentialsRequired)) {
        paramCodedOutputByteBufferNano.writeBool(7, this.perTransactionCredentialsRequired);
      }
      if ((this.hasSendSubscriberIdWithCarrierBillingRequests) || (this.sendSubscriberIdWithCarrierBillingRequests)) {
        paramCodedOutputByteBufferNano.writeBool(8, this.sendSubscriberIdWithCarrierBillingRequests);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class CorpusMetadata
    extends MessageNano
  {
    private static volatile CorpusMetadata[] _emptyArray;
    public int backend = 0;
    public boolean hasBackend = false;
    public boolean hasLandingUrl = false;
    public boolean hasLibraryName = false;
    public boolean hasName = false;
    public boolean hasRecsWidgetUrl = false;
    public boolean hasShopName = false;
    public String landingUrl = "";
    public String libraryName = "";
    public String name = "";
    public String recsWidgetUrl = "";
    public String shopName = "";
    
    public CorpusMetadata()
    {
      this.cachedSize = -1;
    }
    
    public static CorpusMetadata[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new CorpusMetadata[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.backend != 0) || (this.hasBackend)) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.backend);
      }
      if ((this.hasName) || (!this.name.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.name);
      }
      if ((this.hasLandingUrl) || (!this.landingUrl.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.landingUrl);
      }
      if ((this.hasLibraryName) || (!this.libraryName.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(4, this.libraryName);
      }
      if ((this.hasRecsWidgetUrl) || (!this.recsWidgetUrl.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(6, this.recsWidgetUrl);
      }
      if ((this.hasShopName) || (!this.shopName.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(7, this.shopName);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.backend != 0) || (this.hasBackend)) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.backend);
      }
      if ((this.hasName) || (!this.name.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.name);
      }
      if ((this.hasLandingUrl) || (!this.landingUrl.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.landingUrl);
      }
      if ((this.hasLibraryName) || (!this.libraryName.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(4, this.libraryName);
      }
      if ((this.hasRecsWidgetUrl) || (!this.recsWidgetUrl.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(6, this.recsWidgetUrl);
      }
      if ((this.hasShopName) || (!this.shopName.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(7, this.shopName);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class Experiments
    extends MessageNano
  {
    public String[] experimentId = WireFormatNano.EMPTY_STRING_ARRAY;
    
    public Experiments()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.experimentId != null) && (this.experimentId.length > 0))
      {
        int j = 0;
        int k = 0;
        for (int m = 0; m < this.experimentId.length; m++)
        {
          String str = this.experimentId[m];
          if (str != null)
          {
            j++;
            k += CodedOutputByteBufferNano.computeStringSizeNoTag(str);
          }
        }
        i = i + k + j * 1;
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.experimentId != null) && (this.experimentId.length > 0)) {
        for (int i = 0; i < this.experimentId.length; i++)
        {
          String str = this.experimentId[i];
          if (str != null) {
            paramCodedOutputByteBufferNano.writeString(1, str);
          }
        }
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class SelfUpdateConfig
    extends MessageNano
  {
    public boolean hasLatestClientVersionCode = false;
    public int latestClientVersionCode = 0;
    
    public SelfUpdateConfig()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasLatestClientVersionCode) || (this.latestClientVersionCode != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.latestClientVersionCode);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasLatestClientVersionCode) || (this.latestClientVersionCode != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.latestClientVersionCode);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class TocResponse
    extends MessageNano
  {
    public boolean ageVerificationRequired = false;
    public Toc.BillingConfig billingConfig = null;
    public String cookie = "";
    public Toc.CorpusMetadata[] corpus = Toc.CorpusMetadata.emptyArray();
    public String entertainmentHomeUrl = "";
    public Toc.Experiments experiments = null;
    public boolean gplusSignupEnabled = false;
    public boolean hasAgeVerificationRequired = false;
    public boolean hasCookie = false;
    public boolean hasEntertainmentHomeUrl = false;
    public boolean hasGplusSignupEnabled = false;
    public boolean hasHelpUrl = false;
    public boolean hasHomeUrl = false;
    public boolean hasIconOverrideUrl = false;
    public boolean hasRecsWidgetUrl = false;
    public boolean hasRedeemEnabled = false;
    public boolean hasRequiresUploadDeviceConfig = false;
    public boolean hasSocialHomeUrl = false;
    public boolean hasThemeId = false;
    public boolean hasTosCheckboxTextMarketingEmails = false;
    public boolean hasTosContent = false;
    public boolean hasTosToken = false;
    public boolean hasTosVersionDeprecated = false;
    public String helpUrl = "";
    public String homeUrl = "";
    public String iconOverrideUrl = "";
    public String recsWidgetUrl = "";
    public boolean redeemEnabled = false;
    public boolean requiresUploadDeviceConfig = false;
    public Toc.SelfUpdateConfig selfUpdateConfig = null;
    public String socialHomeUrl = "";
    public int themeId = 0;
    public String tosCheckboxTextMarketingEmails = "";
    public String tosContent = "";
    public String tosToken = "";
    public int tosVersionDeprecated = 0;
    public OBSOLETEUserSettings userSettings = null;
    
    public TocResponse()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.corpus != null) && (this.corpus.length > 0)) {
        for (int j = 0; j < this.corpus.length; j++)
        {
          Toc.CorpusMetadata localCorpusMetadata = this.corpus[j];
          if (localCorpusMetadata != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(1, localCorpusMetadata);
          }
        }
      }
      if ((this.hasTosVersionDeprecated) || (this.tosVersionDeprecated != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(2, this.tosVersionDeprecated);
      }
      if ((this.hasTosContent) || (!this.tosContent.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.tosContent);
      }
      if ((this.hasHomeUrl) || (!this.homeUrl.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(4, this.homeUrl);
      }
      if (this.experiments != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(5, this.experiments);
      }
      if ((this.hasTosCheckboxTextMarketingEmails) || (!this.tosCheckboxTextMarketingEmails.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(6, this.tosCheckboxTextMarketingEmails);
      }
      if ((this.hasTosToken) || (!this.tosToken.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(7, this.tosToken);
      }
      if (this.userSettings != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(8, this.userSettings);
      }
      if ((this.hasIconOverrideUrl) || (!this.iconOverrideUrl.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(9, this.iconOverrideUrl);
      }
      if (this.selfUpdateConfig != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(10, this.selfUpdateConfig);
      }
      if ((this.hasRequiresUploadDeviceConfig) || (this.requiresUploadDeviceConfig)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(11);
      }
      if (this.billingConfig != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(12, this.billingConfig);
      }
      if ((this.hasRecsWidgetUrl) || (!this.recsWidgetUrl.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(13, this.recsWidgetUrl);
      }
      if ((this.hasSocialHomeUrl) || (!this.socialHomeUrl.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(15, this.socialHomeUrl);
      }
      if ((this.hasAgeVerificationRequired) || (this.ageVerificationRequired)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(16);
      }
      if ((this.hasGplusSignupEnabled) || (this.gplusSignupEnabled)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(17);
      }
      if ((this.hasRedeemEnabled) || (this.redeemEnabled)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(18);
      }
      if ((this.hasHelpUrl) || (!this.helpUrl.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(19, this.helpUrl);
      }
      if ((this.themeId != 0) || (this.hasThemeId)) {
        i += CodedOutputByteBufferNano.computeInt32Size(20, this.themeId);
      }
      if ((this.hasEntertainmentHomeUrl) || (!this.entertainmentHomeUrl.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(21, this.entertainmentHomeUrl);
      }
      if ((this.hasCookie) || (!this.cookie.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(22, this.cookie);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.corpus != null) && (this.corpus.length > 0)) {
        for (int i = 0; i < this.corpus.length; i++)
        {
          Toc.CorpusMetadata localCorpusMetadata = this.corpus[i];
          if (localCorpusMetadata != null) {
            paramCodedOutputByteBufferNano.writeMessage(1, localCorpusMetadata);
          }
        }
      }
      if ((this.hasTosVersionDeprecated) || (this.tosVersionDeprecated != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(2, this.tosVersionDeprecated);
      }
      if ((this.hasTosContent) || (!this.tosContent.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.tosContent);
      }
      if ((this.hasHomeUrl) || (!this.homeUrl.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(4, this.homeUrl);
      }
      if (this.experiments != null) {
        paramCodedOutputByteBufferNano.writeMessage(5, this.experiments);
      }
      if ((this.hasTosCheckboxTextMarketingEmails) || (!this.tosCheckboxTextMarketingEmails.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(6, this.tosCheckboxTextMarketingEmails);
      }
      if ((this.hasTosToken) || (!this.tosToken.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(7, this.tosToken);
      }
      if (this.userSettings != null) {
        paramCodedOutputByteBufferNano.writeMessage(8, this.userSettings);
      }
      if ((this.hasIconOverrideUrl) || (!this.iconOverrideUrl.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(9, this.iconOverrideUrl);
      }
      if (this.selfUpdateConfig != null) {
        paramCodedOutputByteBufferNano.writeMessage(10, this.selfUpdateConfig);
      }
      if ((this.hasRequiresUploadDeviceConfig) || (this.requiresUploadDeviceConfig)) {
        paramCodedOutputByteBufferNano.writeBool(11, this.requiresUploadDeviceConfig);
      }
      if (this.billingConfig != null) {
        paramCodedOutputByteBufferNano.writeMessage(12, this.billingConfig);
      }
      if ((this.hasRecsWidgetUrl) || (!this.recsWidgetUrl.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(13, this.recsWidgetUrl);
      }
      if ((this.hasSocialHomeUrl) || (!this.socialHomeUrl.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(15, this.socialHomeUrl);
      }
      if ((this.hasAgeVerificationRequired) || (this.ageVerificationRequired)) {
        paramCodedOutputByteBufferNano.writeBool(16, this.ageVerificationRequired);
      }
      if ((this.hasGplusSignupEnabled) || (this.gplusSignupEnabled)) {
        paramCodedOutputByteBufferNano.writeBool(17, this.gplusSignupEnabled);
      }
      if ((this.hasRedeemEnabled) || (this.redeemEnabled)) {
        paramCodedOutputByteBufferNano.writeBool(18, this.redeemEnabled);
      }
      if ((this.hasHelpUrl) || (!this.helpUrl.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(19, this.helpUrl);
      }
      if ((this.themeId != 0) || (this.hasThemeId)) {
        paramCodedOutputByteBufferNano.writeInt32(20, this.themeId);
      }
      if ((this.hasEntertainmentHomeUrl) || (!this.entertainmentHomeUrl.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(21, this.entertainmentHomeUrl);
      }
      if ((this.hasCookie) || (!this.cookie.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(22, this.cookie);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.Toc
 * JD-Core Version:    0.7.0.1
 */