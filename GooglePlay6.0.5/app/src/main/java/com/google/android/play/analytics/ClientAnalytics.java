package com.google.android.play.analytics;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;
import java.util.Arrays;

public abstract interface ClientAnalytics
{
  public static final class ActiveExperiments
    extends MessageNano
  {
    public String[] clientAlteringExperiment = WireFormatNano.EMPTY_STRING_ARRAY;
    public int[] gwsExperiment = WireFormatNano.EMPTY_INT_ARRAY;
    public String[] otherExperiment = WireFormatNano.EMPTY_STRING_ARRAY;
    public long[] playExperiment = WireFormatNano.EMPTY_LONG_ARRAY;
    public long[] unsupportedPlayExperiment = WireFormatNano.EMPTY_LONG_ARRAY;
    
    public ActiveExperiments()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.clientAlteringExperiment != null) && (this.clientAlteringExperiment.length > 0))
      {
        int i6 = 0;
        int i7 = 0;
        for (int i8 = 0; i8 < this.clientAlteringExperiment.length; i8++)
        {
          String str2 = this.clientAlteringExperiment[i8];
          if (str2 != null)
          {
            i6++;
            i7 += CodedOutputByteBufferNano.computeStringSizeNoTag(str2);
          }
        }
        i = i + i7 + i6 * 1;
      }
      if ((this.otherExperiment != null) && (this.otherExperiment.length > 0))
      {
        int i3 = 0;
        int i4 = 0;
        for (int i5 = 0; i5 < this.otherExperiment.length; i5++)
        {
          String str1 = this.otherExperiment[i5];
          if (str1 != null)
          {
            i3++;
            i4 += CodedOutputByteBufferNano.computeStringSizeNoTag(str1);
          }
        }
        i = i + i4 + i3 * 1;
      }
      if ((this.gwsExperiment != null) && (this.gwsExperiment.length > 0))
      {
        int i1 = 0;
        for (int i2 = 0; i2 < this.gwsExperiment.length; i2++) {
          i1 += CodedOutputByteBufferNano.computeInt32SizeNoTag(this.gwsExperiment[i2]);
        }
        i = i + i1 + 1 * this.gwsExperiment.length;
      }
      if ((this.playExperiment != null) && (this.playExperiment.length > 0))
      {
        int m = 0;
        for (int n = 0; n < this.playExperiment.length; n++) {
          m += CodedOutputByteBufferNano.computeRawVarint64Size(this.playExperiment[n]);
        }
        i = i + m + 1 * this.playExperiment.length;
      }
      if ((this.unsupportedPlayExperiment != null) && (this.unsupportedPlayExperiment.length > 0))
      {
        int j = 0;
        for (int k = 0; k < this.unsupportedPlayExperiment.length; k++) {
          j += CodedOutputByteBufferNano.computeRawVarint64Size(this.unsupportedPlayExperiment[k]);
        }
        i = i + j + 1 * this.unsupportedPlayExperiment.length;
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.clientAlteringExperiment != null) && (this.clientAlteringExperiment.length > 0)) {
        for (int n = 0; n < this.clientAlteringExperiment.length; n++)
        {
          String str2 = this.clientAlteringExperiment[n];
          if (str2 != null) {
            paramCodedOutputByteBufferNano.writeString(1, str2);
          }
        }
      }
      if ((this.otherExperiment != null) && (this.otherExperiment.length > 0)) {
        for (int m = 0; m < this.otherExperiment.length; m++)
        {
          String str1 = this.otherExperiment[m];
          if (str1 != null) {
            paramCodedOutputByteBufferNano.writeString(2, str1);
          }
        }
      }
      if ((this.gwsExperiment != null) && (this.gwsExperiment.length > 0)) {
        for (int k = 0; k < this.gwsExperiment.length; k++) {
          paramCodedOutputByteBufferNano.writeInt32(3, this.gwsExperiment[k]);
        }
      }
      if ((this.playExperiment != null) && (this.playExperiment.length > 0)) {
        for (int j = 0; j < this.playExperiment.length; j++) {
          paramCodedOutputByteBufferNano.writeInt64(4, this.playExperiment[j]);
        }
      }
      if ((this.unsupportedPlayExperiment != null) && (this.unsupportedPlayExperiment.length > 0)) {
        for (int i = 0; i < this.unsupportedPlayExperiment.length; i++) {
          paramCodedOutputByteBufferNano.writeInt64(5, this.unsupportedPlayExperiment[i]);
        }
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class AndroidClientInfo
    extends MessageNano
  {
    public long androidId = 0L;
    public String applicationBuild = "";
    public String board = "";
    public String brand = "";
    public String country = "";
    public String device = "";
    public long deviceId = 0L;
    public String fingerprint = "";
    public int gmsCoreVersionCode = 0;
    public String hardware = "";
    public boolean hasAndroidId = false;
    public boolean hasApplicationBuild = false;
    public boolean hasBoard = false;
    public boolean hasBrand = false;
    public boolean hasCountry = false;
    public boolean hasDevice = false;
    public boolean hasDeviceId = false;
    public boolean hasFingerprint = false;
    public boolean hasGmsCoreVersionCode = false;
    public boolean hasHardware = false;
    public boolean hasIsSidewinderDevice = false;
    public boolean hasLocale = false;
    public boolean hasLoggingId = false;
    public boolean hasManufacturer = false;
    public boolean hasMccMnc = false;
    public boolean hasModel = false;
    public boolean hasOsBuild = false;
    public boolean hasProduct = false;
    public boolean hasRadioVersion = false;
    public boolean hasSdkVersion = false;
    public boolean isSidewinderDevice = false;
    public String locale = "";
    public String loggingId = "";
    public String manufacturer = "";
    public String mccMnc = "";
    public String model = "";
    public String osBuild = "";
    public String product = "";
    public String radioVersion = "";
    public int sdkVersion = 0;
    
    public AndroidClientInfo()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasAndroidId) || (this.androidId != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(1, this.androidId);
      }
      if ((this.hasLoggingId) || (!this.loggingId.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.loggingId);
      }
      if ((this.hasSdkVersion) || (this.sdkVersion != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(3, this.sdkVersion);
      }
      if ((this.hasModel) || (!this.model.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(4, this.model);
      }
      if ((this.hasProduct) || (!this.product.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(5, this.product);
      }
      if ((this.hasOsBuild) || (!this.osBuild.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(6, this.osBuild);
      }
      if ((this.hasApplicationBuild) || (!this.applicationBuild.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(7, this.applicationBuild);
      }
      if ((this.hasHardware) || (!this.hardware.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(8, this.hardware);
      }
      if ((this.hasDevice) || (!this.device.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(9, this.device);
      }
      if ((this.hasMccMnc) || (!this.mccMnc.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(10, this.mccMnc);
      }
      if ((this.hasLocale) || (!this.locale.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(11, this.locale);
      }
      if ((this.hasCountry) || (!this.country.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(12, this.country);
      }
      if ((this.hasManufacturer) || (!this.manufacturer.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(13, this.manufacturer);
      }
      if ((this.hasBrand) || (!this.brand.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(14, this.brand);
      }
      if ((this.hasBoard) || (!this.board.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(15, this.board);
      }
      if ((this.hasRadioVersion) || (!this.radioVersion.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(16, this.radioVersion);
      }
      if ((this.hasFingerprint) || (!this.fingerprint.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(17, this.fingerprint);
      }
      if ((this.hasDeviceId) || (this.deviceId != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(18, this.deviceId);
      }
      if ((this.hasGmsCoreVersionCode) || (this.gmsCoreVersionCode != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(19, this.gmsCoreVersionCode);
      }
      if ((this.hasIsSidewinderDevice) || (this.isSidewinderDevice)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(20);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasAndroidId) || (this.androidId != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(1, this.androidId);
      }
      if ((this.hasLoggingId) || (!this.loggingId.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.loggingId);
      }
      if ((this.hasSdkVersion) || (this.sdkVersion != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(3, this.sdkVersion);
      }
      if ((this.hasModel) || (!this.model.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(4, this.model);
      }
      if ((this.hasProduct) || (!this.product.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(5, this.product);
      }
      if ((this.hasOsBuild) || (!this.osBuild.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(6, this.osBuild);
      }
      if ((this.hasApplicationBuild) || (!this.applicationBuild.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(7, this.applicationBuild);
      }
      if ((this.hasHardware) || (!this.hardware.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(8, this.hardware);
      }
      if ((this.hasDevice) || (!this.device.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(9, this.device);
      }
      if ((this.hasMccMnc) || (!this.mccMnc.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(10, this.mccMnc);
      }
      if ((this.hasLocale) || (!this.locale.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(11, this.locale);
      }
      if ((this.hasCountry) || (!this.country.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(12, this.country);
      }
      if ((this.hasManufacturer) || (!this.manufacturer.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(13, this.manufacturer);
      }
      if ((this.hasBrand) || (!this.brand.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(14, this.brand);
      }
      if ((this.hasBoard) || (!this.board.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(15, this.board);
      }
      if ((this.hasRadioVersion) || (!this.radioVersion.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(16, this.radioVersion);
      }
      if ((this.hasFingerprint) || (!this.fingerprint.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(17, this.fingerprint);
      }
      if ((this.hasDeviceId) || (this.deviceId != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(18, this.deviceId);
      }
      if ((this.hasGmsCoreVersionCode) || (this.gmsCoreVersionCode != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(19, this.gmsCoreVersionCode);
      }
      if ((this.hasIsSidewinderDevice) || (this.isSidewinderDevice)) {
        paramCodedOutputByteBufferNano.writeBool(20, this.isSidewinderDevice);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class AppUsage1pLogEvent
    extends MessageNano
  {
    public String androidPackageName = "";
    public int appType = 0;
    public boolean hasAndroidPackageName = false;
    public boolean hasAppType = false;
    public boolean hasVersion = false;
    public String version = "";
    
    public AppUsage1pLogEvent()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.appType != 0) || (this.hasAppType)) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.appType);
      }
      if ((this.hasAndroidPackageName) || (!this.androidPackageName.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.androidPackageName);
      }
      if ((this.hasVersion) || (!this.version.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.version);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.appType != 0) || (this.hasAppType)) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.appType);
      }
      if ((this.hasAndroidPackageName) || (!this.androidPackageName.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.androidPackageName);
      }
      if ((this.hasVersion) || (!this.version.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.version);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class BrowserInfo
    extends MessageNano
  {
    public String browser = "";
    public String browserVersion = "";
    public String flashVersion = "";
    public boolean hasBrowser = false;
    public boolean hasBrowserVersion = false;
    public boolean hasFlashVersion = false;
    public boolean hasLocale = false;
    public String locale = "";
    
    public BrowserInfo()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasLocale) || (!this.locale.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.locale);
      }
      if ((this.hasBrowser) || (!this.browser.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.browser);
      }
      if ((this.hasBrowserVersion) || (!this.browserVersion.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.browserVersion);
      }
      if ((this.hasFlashVersion) || (!this.flashVersion.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(4, this.flashVersion);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasLocale) || (!this.locale.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.locale);
      }
      if ((this.hasBrowser) || (!this.browser.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.browser);
      }
      if ((this.hasBrowserVersion) || (!this.browserVersion.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.browserVersion);
      }
      if ((this.hasFlashVersion) || (!this.flashVersion.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(4, this.flashVersion);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class ClientInfo
    extends MessageNano
  {
    public ClientAnalytics.AndroidClientInfo androidClientInfo = null;
    public ClientAnalytics.BrowserInfo browserInfo = null;
    public int clientType = 0;
    public ClientAnalytics.DesktopClientInfo desktopClientInfo = null;
    public boolean hasClientType = false;
    public boolean hasRemoteHost = false;
    public boolean hasRemoteHost6 = false;
    public ClientAnalytics.IosClientInfo iosClientInfo = null;
    public ClientAnalytics.PancettaClientInfo pancettaClientInfo = null;
    public ClientAnalytics.PlayCeClientInfo playCeClientInfo = null;
    public String remoteHost = "";
    public String remoteHost6 = "";
    public ClientAnalytics.VrClientInfo vrClientInfo = null;
    
    public ClientInfo()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.clientType != 0) || (this.hasClientType)) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.clientType);
      }
      if (this.androidClientInfo != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.androidClientInfo);
      }
      if (this.desktopClientInfo != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(3, this.desktopClientInfo);
      }
      if (this.iosClientInfo != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(4, this.iosClientInfo);
      }
      if (this.playCeClientInfo != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(5, this.playCeClientInfo);
      }
      if ((this.hasRemoteHost) || (!this.remoteHost.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(6, this.remoteHost);
      }
      if ((this.hasRemoteHost6) || (!this.remoteHost6.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(7, this.remoteHost6);
      }
      if (this.vrClientInfo != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(8, this.vrClientInfo);
      }
      if (this.browserInfo != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(9, this.browserInfo);
      }
      if (this.pancettaClientInfo != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(10, this.pancettaClientInfo);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.clientType != 0) || (this.hasClientType)) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.clientType);
      }
      if (this.androidClientInfo != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.androidClientInfo);
      }
      if (this.desktopClientInfo != null) {
        paramCodedOutputByteBufferNano.writeMessage(3, this.desktopClientInfo);
      }
      if (this.iosClientInfo != null) {
        paramCodedOutputByteBufferNano.writeMessage(4, this.iosClientInfo);
      }
      if (this.playCeClientInfo != null) {
        paramCodedOutputByteBufferNano.writeMessage(5, this.playCeClientInfo);
      }
      if ((this.hasRemoteHost) || (!this.remoteHost.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(6, this.remoteHost);
      }
      if ((this.hasRemoteHost6) || (!this.remoteHost6.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(7, this.remoteHost6);
      }
      if (this.vrClientInfo != null) {
        paramCodedOutputByteBufferNano.writeMessage(8, this.vrClientInfo);
      }
      if (this.browserInfo != null) {
        paramCodedOutputByteBufferNano.writeMessage(9, this.browserInfo);
      }
      if (this.pancettaClientInfo != null) {
        paramCodedOutputByteBufferNano.writeMessage(10, this.pancettaClientInfo);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class DesktopClientInfo
    extends MessageNano
  {
    public String applicationBuild = "";
    public String clientId = "";
    public String country = "";
    public boolean hasApplicationBuild = false;
    public boolean hasClientId = false;
    public boolean hasCountry = false;
    public boolean hasLoggingId = false;
    public boolean hasOs = false;
    public boolean hasOsFullVersion = false;
    public boolean hasOsMajorVersion = false;
    public String loggingId = "";
    public String os = "";
    public String osFullVersion = "";
    public String osMajorVersion = "";
    
    public DesktopClientInfo()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasClientId) || (!this.clientId.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.clientId);
      }
      if ((this.hasLoggingId) || (!this.loggingId.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.loggingId);
      }
      if ((this.hasOs) || (!this.os.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.os);
      }
      if ((this.hasOsMajorVersion) || (!this.osMajorVersion.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(4, this.osMajorVersion);
      }
      if ((this.hasOsFullVersion) || (!this.osFullVersion.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(5, this.osFullVersion);
      }
      if ((this.hasApplicationBuild) || (!this.applicationBuild.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(6, this.applicationBuild);
      }
      if ((this.hasCountry) || (!this.country.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(7, this.country);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasClientId) || (!this.clientId.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.clientId);
      }
      if ((this.hasLoggingId) || (!this.loggingId.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.loggingId);
      }
      if ((this.hasOs) || (!this.os.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.os);
      }
      if ((this.hasOsMajorVersion) || (!this.osMajorVersion.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(4, this.osMajorVersion);
      }
      if ((this.hasOsFullVersion) || (!this.osFullVersion.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(5, this.osFullVersion);
      }
      if ((this.hasApplicationBuild) || (!this.applicationBuild.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(6, this.applicationBuild);
      }
      if ((this.hasCountry) || (!this.country.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(7, this.country);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class DeviceStatus
    extends MessageNano
  {
    public int autoTimeStatus = 0;
    public boolean hasAutoTimeStatus = false;
    public boolean hasIsCharging = false;
    public boolean hasIsGooglerDevice = false;
    public boolean hasIsUnmetered = false;
    public boolean isCharging = false;
    public boolean isGooglerDevice = false;
    public boolean isUnmetered = false;
    
    public DeviceStatus()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasIsUnmetered) || (this.isUnmetered)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(1);
      }
      if ((this.hasIsCharging) || (this.isCharging)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(2);
      }
      if ((this.autoTimeStatus != 0) || (this.hasAutoTimeStatus)) {
        i += CodedOutputByteBufferNano.computeInt32Size(3, this.autoTimeStatus);
      }
      if ((this.hasIsGooglerDevice) || (this.isGooglerDevice)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(4);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasIsUnmetered) || (this.isUnmetered)) {
        paramCodedOutputByteBufferNano.writeBool(1, this.isUnmetered);
      }
      if ((this.hasIsCharging) || (this.isCharging)) {
        paramCodedOutputByteBufferNano.writeBool(2, this.isCharging);
      }
      if ((this.autoTimeStatus != 0) || (this.hasAutoTimeStatus)) {
        paramCodedOutputByteBufferNano.writeInt32(3, this.autoTimeStatus);
      }
      if ((this.hasIsGooglerDevice) || (this.isGooglerDevice)) {
        paramCodedOutputByteBufferNano.writeBool(4, this.isGooglerDevice);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class ExperimentIdList
    extends MessageNano
  {
    public String[] id = WireFormatNano.EMPTY_STRING_ARRAY;
    
    public ExperimentIdList()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.id != null) && (this.id.length > 0))
      {
        int j = 0;
        int k = 0;
        for (int m = 0; m < this.id.length; m++)
        {
          String str = this.id[m];
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
      if ((this.id != null) && (this.id.length > 0)) {
        for (int i = 0; i < this.id.length; i++)
        {
          String str = this.id[i];
          if (str != null) {
            paramCodedOutputByteBufferNano.writeString(1, str);
          }
        }
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class ExperimentIds
    extends MessageNano
  {
    public byte[] clearBlob = WireFormatNano.EMPTY_BYTES;
    public byte[][] encryptedBlob = WireFormatNano.EMPTY_BYTES_ARRAY;
    public boolean hasClearBlob = false;
    public boolean hasUsersMatch = false;
    public boolean usersMatch = false;
    
    public ExperimentIds()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasClearBlob) || (!Arrays.equals(this.clearBlob, WireFormatNano.EMPTY_BYTES))) {
        i += CodedOutputByteBufferNano.computeBytesSize(1, this.clearBlob);
      }
      if ((this.encryptedBlob != null) && (this.encryptedBlob.length > 0))
      {
        int j = 0;
        int k = 0;
        for (int m = 0; m < this.encryptedBlob.length; m++)
        {
          byte[] arrayOfByte = this.encryptedBlob[m];
          if (arrayOfByte != null)
          {
            j++;
            k += CodedOutputByteBufferNano.computeBytesSizeNoTag(arrayOfByte);
          }
        }
        i = i + k + j * 1;
      }
      if ((this.hasUsersMatch) || (this.usersMatch)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(3);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasClearBlob) || (!Arrays.equals(this.clearBlob, WireFormatNano.EMPTY_BYTES))) {
        paramCodedOutputByteBufferNano.writeBytes(1, this.clearBlob);
      }
      if ((this.encryptedBlob != null) && (this.encryptedBlob.length > 0)) {
        for (int i = 0; i < this.encryptedBlob.length; i++)
        {
          byte[] arrayOfByte = this.encryptedBlob[i];
          if (arrayOfByte != null) {
            paramCodedOutputByteBufferNano.writeBytes(2, arrayOfByte);
          }
        }
      }
      if ((this.hasUsersMatch) || (this.usersMatch)) {
        paramCodedOutputByteBufferNano.writeBool(3, this.usersMatch);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class ExternalTimestamp
    extends MessageNano
  {
    public boolean hasSource = false;
    public boolean hasTimeMs = false;
    public boolean hasUptimeMs = false;
    public String source = "";
    public long timeMs = 0L;
    public long uptimeMs = 0L;
    
    public ExternalTimestamp()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasTimeMs) || (this.timeMs != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(1, this.timeMs);
      }
      if ((this.hasUptimeMs) || (this.uptimeMs != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(2, this.uptimeMs);
      }
      if ((this.hasSource) || (!this.source.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.source);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasTimeMs) || (this.timeMs != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(1, this.timeMs);
      }
      if ((this.hasUptimeMs) || (this.uptimeMs != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(2, this.uptimeMs);
      }
      if ((this.hasSource) || (!this.source.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.source);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class IosClientInfo
    extends MessageNano
  {
    public String applicationBuild = "";
    public String clientId = "";
    public String country = "";
    public boolean hasApplicationBuild = false;
    public boolean hasClientId = false;
    public boolean hasCountry = false;
    public boolean hasLoggingId = false;
    public boolean hasModel = false;
    public boolean hasOsFullVersion = false;
    public boolean hasOsMajorVersion = false;
    public String loggingId = "";
    public String model = "";
    public String osFullVersion = "";
    public String osMajorVersion = "";
    
    public IosClientInfo()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasClientId) || (!this.clientId.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.clientId);
      }
      if ((this.hasLoggingId) || (!this.loggingId.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.loggingId);
      }
      if ((this.hasOsMajorVersion) || (!this.osMajorVersion.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.osMajorVersion);
      }
      if ((this.hasOsFullVersion) || (!this.osFullVersion.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(4, this.osFullVersion);
      }
      if ((this.hasApplicationBuild) || (!this.applicationBuild.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(5, this.applicationBuild);
      }
      if ((this.hasCountry) || (!this.country.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(6, this.country);
      }
      if ((this.hasModel) || (!this.model.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(7, this.model);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasClientId) || (!this.clientId.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.clientId);
      }
      if ((this.hasLoggingId) || (!this.loggingId.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.loggingId);
      }
      if ((this.hasOsMajorVersion) || (!this.osMajorVersion.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.osMajorVersion);
      }
      if ((this.hasOsFullVersion) || (!this.osFullVersion.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(4, this.osFullVersion);
      }
      if ((this.hasApplicationBuild) || (!this.applicationBuild.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(5, this.applicationBuild);
      }
      if ((this.hasCountry) || (!this.country.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(6, this.country);
      }
      if ((this.hasModel) || (!this.model.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(7, this.model);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class LogEvent
    extends MessageNano
  {
    private static volatile LogEvent[] _emptyArray;
    public ClientAnalytics.AppUsage1pLogEvent appUsage1P;
    public long bootCount;
    public byte[] clientVe;
    public int eventCode;
    public int eventFlowId;
    public long eventTimeMs;
    public long eventUptimeMs;
    public ClientAnalytics.ActiveExperiments exp;
    public ClientAnalytics.ExperimentIds experimentIds;
    public boolean hasBootCount;
    public boolean hasClientVe;
    public boolean hasEventCode;
    public boolean hasEventFlowId;
    public boolean hasEventTimeMs;
    public boolean hasEventUptimeMs;
    public boolean hasInternalEvent;
    public boolean hasIsUserInitiated;
    public boolean hasSequencePosition;
    public boolean hasSourceExtension;
    public boolean hasSourceExtensionJs;
    public boolean hasSourceExtensionJson;
    public boolean hasStore;
    public boolean hasTag;
    public boolean hasTestId;
    public boolean hasTimezoneOffsetSeconds;
    public int internalEvent;
    public boolean isUserInitiated;
    public long sequencePosition;
    public byte[] sourceExtension;
    public String sourceExtensionJs;
    public String sourceExtensionJson;
    public byte[] store;
    public String tag;
    public int[] testCode;
    public String testId;
    public long timezoneOffsetSeconds;
    public ClientAnalytics.LogEventKeyValues[] value;
    
    public LogEvent()
    {
      clear();
    }
    
    public static LogEvent[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new LogEvent[0];
        }
        return _emptyArray;
      }
    }
    
    public final LogEvent clear()
    {
      this.eventTimeMs = 0L;
      this.hasEventTimeMs = false;
      this.eventUptimeMs = 0L;
      this.hasEventUptimeMs = false;
      this.sequencePosition = 0L;
      this.hasSequencePosition = false;
      this.tag = "";
      this.hasTag = false;
      this.eventCode = 0;
      this.hasEventCode = false;
      this.eventFlowId = 0;
      this.hasEventFlowId = false;
      this.isUserInitiated = false;
      this.hasIsUserInitiated = false;
      this.value = ClientAnalytics.LogEventKeyValues.emptyArray();
      this.store = WireFormatNano.EMPTY_BYTES;
      this.hasStore = false;
      this.appUsage1P = null;
      this.sourceExtension = WireFormatNano.EMPTY_BYTES;
      this.hasSourceExtension = false;
      this.sourceExtensionJs = "";
      this.hasSourceExtensionJs = false;
      this.sourceExtensionJson = "";
      this.hasSourceExtensionJson = false;
      this.exp = null;
      this.testId = "";
      this.hasTestId = false;
      this.timezoneOffsetSeconds = 0L;
      this.hasTimezoneOffsetSeconds = false;
      this.experimentIds = null;
      this.clientVe = WireFormatNano.EMPTY_BYTES;
      this.hasClientVe = false;
      this.internalEvent = 0;
      this.hasInternalEvent = false;
      this.testCode = WireFormatNano.EMPTY_INT_ARRAY;
      this.bootCount = 0L;
      this.hasBootCount = false;
      this.cachedSize = -1;
      return this;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasEventTimeMs) || (this.eventTimeMs != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(1, this.eventTimeMs);
      }
      if ((this.hasTag) || (!this.tag.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.tag);
      }
      if ((this.value != null) && (this.value.length > 0)) {
        for (int m = 0; m < this.value.length; m++)
        {
          ClientAnalytics.LogEventKeyValues localLogEventKeyValues = this.value[m];
          if (localLogEventKeyValues != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(3, localLogEventKeyValues);
          }
        }
      }
      if ((this.hasStore) || (!Arrays.equals(this.store, WireFormatNano.EMPTY_BYTES))) {
        i += CodedOutputByteBufferNano.computeBytesSize(4, this.store);
      }
      if ((this.hasSourceExtension) || (!Arrays.equals(this.sourceExtension, WireFormatNano.EMPTY_BYTES))) {
        i += CodedOutputByteBufferNano.computeBytesSize(6, this.sourceExtension);
      }
      if (this.exp != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(7, this.exp);
      }
      if ((this.hasSourceExtensionJs) || (!this.sourceExtensionJs.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(8, this.sourceExtensionJs);
      }
      if (this.appUsage1P != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(9, this.appUsage1P);
      }
      if ((this.hasIsUserInitiated) || (this.isUserInitiated)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(10);
      }
      if ((this.hasEventCode) || (this.eventCode != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(11, this.eventCode);
      }
      if ((this.hasEventFlowId) || (this.eventFlowId != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(12, this.eventFlowId);
      }
      if ((this.hasSourceExtensionJson) || (!this.sourceExtensionJson.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(13, this.sourceExtensionJson);
      }
      if ((this.hasTestId) || (!this.testId.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(14, this.testId);
      }
      if ((this.hasTimezoneOffsetSeconds) || (this.timezoneOffsetSeconds != 0L))
      {
        long l = this.timezoneOffsetSeconds;
        i += CodedOutputByteBufferNano.computeTagSize(15) + CodedOutputByteBufferNano.computeRawVarint64Size(CodedOutputByteBufferNano.encodeZigZag64(l));
      }
      if (this.experimentIds != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(16, this.experimentIds);
      }
      if ((this.hasEventUptimeMs) || (this.eventUptimeMs != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(17, this.eventUptimeMs);
      }
      if ((this.hasClientVe) || (!Arrays.equals(this.clientVe, WireFormatNano.EMPTY_BYTES))) {
        i += CodedOutputByteBufferNano.computeBytesSize(18, this.clientVe);
      }
      if ((this.internalEvent != 0) || (this.hasInternalEvent)) {
        i += CodedOutputByteBufferNano.computeInt32Size(19, this.internalEvent);
      }
      if ((this.testCode != null) && (this.testCode.length > 0))
      {
        int j = 0;
        for (int k = 0; k < this.testCode.length; k++) {
          j += CodedOutputByteBufferNano.computeInt32SizeNoTag(this.testCode[k]);
        }
        i = i + j + 2 * this.testCode.length;
      }
      if ((this.hasSequencePosition) || (this.sequencePosition != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(21, this.sequencePosition);
      }
      if ((this.hasBootCount) || (this.bootCount != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(22, this.bootCount);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasEventTimeMs) || (this.eventTimeMs != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(1, this.eventTimeMs);
      }
      if ((this.hasTag) || (!this.tag.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.tag);
      }
      if ((this.value != null) && (this.value.length > 0)) {
        for (int j = 0; j < this.value.length; j++)
        {
          ClientAnalytics.LogEventKeyValues localLogEventKeyValues = this.value[j];
          if (localLogEventKeyValues != null) {
            paramCodedOutputByteBufferNano.writeMessage(3, localLogEventKeyValues);
          }
        }
      }
      if ((this.hasStore) || (!Arrays.equals(this.store, WireFormatNano.EMPTY_BYTES))) {
        paramCodedOutputByteBufferNano.writeBytes(4, this.store);
      }
      if ((this.hasSourceExtension) || (!Arrays.equals(this.sourceExtension, WireFormatNano.EMPTY_BYTES))) {
        paramCodedOutputByteBufferNano.writeBytes(6, this.sourceExtension);
      }
      if (this.exp != null) {
        paramCodedOutputByteBufferNano.writeMessage(7, this.exp);
      }
      if ((this.hasSourceExtensionJs) || (!this.sourceExtensionJs.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(8, this.sourceExtensionJs);
      }
      if (this.appUsage1P != null) {
        paramCodedOutputByteBufferNano.writeMessage(9, this.appUsage1P);
      }
      if ((this.hasIsUserInitiated) || (this.isUserInitiated)) {
        paramCodedOutputByteBufferNano.writeBool(10, this.isUserInitiated);
      }
      if ((this.hasEventCode) || (this.eventCode != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(11, this.eventCode);
      }
      if ((this.hasEventFlowId) || (this.eventFlowId != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(12, this.eventFlowId);
      }
      if ((this.hasSourceExtensionJson) || (!this.sourceExtensionJson.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(13, this.sourceExtensionJson);
      }
      if ((this.hasTestId) || (!this.testId.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(14, this.testId);
      }
      if ((this.hasTimezoneOffsetSeconds) || (this.timezoneOffsetSeconds != 0L))
      {
        long l = this.timezoneOffsetSeconds;
        paramCodedOutputByteBufferNano.writeTag(15, 0);
        paramCodedOutputByteBufferNano.writeRawVarint64(CodedOutputByteBufferNano.encodeZigZag64(l));
      }
      if (this.experimentIds != null) {
        paramCodedOutputByteBufferNano.writeMessage(16, this.experimentIds);
      }
      if ((this.hasEventUptimeMs) || (this.eventUptimeMs != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(17, this.eventUptimeMs);
      }
      if ((this.hasClientVe) || (!Arrays.equals(this.clientVe, WireFormatNano.EMPTY_BYTES))) {
        paramCodedOutputByteBufferNano.writeBytes(18, this.clientVe);
      }
      if ((this.internalEvent != 0) || (this.hasInternalEvent)) {
        paramCodedOutputByteBufferNano.writeInt32(19, this.internalEvent);
      }
      if ((this.testCode != null) && (this.testCode.length > 0)) {
        for (int i = 0; i < this.testCode.length; i++) {
          paramCodedOutputByteBufferNano.writeInt32(20, this.testCode[i]);
        }
      }
      if ((this.hasSequencePosition) || (this.sequencePosition != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(21, this.sequencePosition);
      }
      if ((this.hasBootCount) || (this.bootCount != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(22, this.bootCount);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class LogEventKeyValues
    extends MessageNano
  {
    private static volatile LogEventKeyValues[] _emptyArray;
    public boolean hasKey;
    public boolean hasValue;
    public String key;
    public String value;
    
    public LogEventKeyValues()
    {
      clear();
    }
    
    public static LogEventKeyValues[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new LogEventKeyValues[0];
        }
        return _emptyArray;
      }
    }
    
    public final LogEventKeyValues clear()
    {
      this.key = "";
      this.hasKey = false;
      this.value = "";
      this.hasValue = false;
      this.cachedSize = -1;
      return this;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasKey) || (!this.key.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.key);
      }
      if ((this.hasValue) || (!this.value.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.value);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasKey) || (!this.key.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.key);
      }
      if ((this.hasValue) || (!this.value.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.value);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class LogRequest
    extends MessageNano
  {
    public ClientAnalytics.ClientInfo clientInfo = null;
    public ClientAnalytics.DeviceStatus deviceStatus = null;
    public ClientAnalytics.ExternalTimestamp externalTimestamp = null;
    public boolean hasLogSource = false;
    public boolean hasLogSourceName = false;
    public boolean hasQosTier = false;
    public boolean hasRequestTimeMs = false;
    public boolean hasRequestUptimeMs = false;
    public boolean hasScheduler = false;
    public boolean hasZwiebackCookie = false;
    public ClientAnalytics.LogEvent[] logEvent = ClientAnalytics.LogEvent.emptyArray();
    public int logSource = -1;
    public String logSourceName = "";
    public int qosTier = 0;
    public long requestTimeMs = 0L;
    public long requestUptimeMs = 0L;
    public int scheduler = 0;
    public byte[][] serializedLogEvents = WireFormatNano.EMPTY_BYTES_ARRAY;
    public String zwiebackCookie = "";
    
    public LogRequest()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.clientInfo != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.clientInfo);
      }
      if ((this.logSource != -1) || (this.hasLogSource)) {
        i += CodedOutputByteBufferNano.computeInt32Size(2, this.logSource);
      }
      if ((this.logEvent != null) && (this.logEvent.length > 0)) {
        for (int n = 0; n < this.logEvent.length; n++)
        {
          ClientAnalytics.LogEvent localLogEvent = this.logEvent[n];
          if (localLogEvent != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(3, localLogEvent);
          }
        }
      }
      if ((this.hasRequestTimeMs) || (this.requestTimeMs != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(4, this.requestTimeMs);
      }
      if ((this.serializedLogEvents != null) && (this.serializedLogEvents.length > 0))
      {
        int j = 0;
        int k = 0;
        for (int m = 0; m < this.serializedLogEvents.length; m++)
        {
          byte[] arrayOfByte = this.serializedLogEvents[m];
          if (arrayOfByte != null)
          {
            j++;
            k += CodedOutputByteBufferNano.computeBytesSizeNoTag(arrayOfByte);
          }
        }
        i = i + k + j * 1;
      }
      if ((this.hasLogSourceName) || (!this.logSourceName.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(6, this.logSourceName);
      }
      if ((this.hasZwiebackCookie) || (!this.zwiebackCookie.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(7, this.zwiebackCookie);
      }
      if ((this.hasRequestUptimeMs) || (this.requestUptimeMs != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(8, this.requestUptimeMs);
      }
      if ((this.qosTier != 0) || (this.hasQosTier)) {
        i += CodedOutputByteBufferNano.computeInt32Size(9, this.qosTier);
      }
      if ((this.scheduler != 0) || (this.hasScheduler)) {
        i += CodedOutputByteBufferNano.computeInt32Size(10, this.scheduler);
      }
      if (this.deviceStatus != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(11, this.deviceStatus);
      }
      if (this.externalTimestamp != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(12, this.externalTimestamp);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.clientInfo != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.clientInfo);
      }
      if ((this.logSource != -1) || (this.hasLogSource)) {
        paramCodedOutputByteBufferNano.writeInt32(2, this.logSource);
      }
      if ((this.logEvent != null) && (this.logEvent.length > 0)) {
        for (int j = 0; j < this.logEvent.length; j++)
        {
          ClientAnalytics.LogEvent localLogEvent = this.logEvent[j];
          if (localLogEvent != null) {
            paramCodedOutputByteBufferNano.writeMessage(3, localLogEvent);
          }
        }
      }
      if ((this.hasRequestTimeMs) || (this.requestTimeMs != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(4, this.requestTimeMs);
      }
      if ((this.serializedLogEvents != null) && (this.serializedLogEvents.length > 0)) {
        for (int i = 0; i < this.serializedLogEvents.length; i++)
        {
          byte[] arrayOfByte = this.serializedLogEvents[i];
          if (arrayOfByte != null) {
            paramCodedOutputByteBufferNano.writeBytes(5, arrayOfByte);
          }
        }
      }
      if ((this.hasLogSourceName) || (!this.logSourceName.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(6, this.logSourceName);
      }
      if ((this.hasZwiebackCookie) || (!this.zwiebackCookie.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(7, this.zwiebackCookie);
      }
      if ((this.hasRequestUptimeMs) || (this.requestUptimeMs != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(8, this.requestUptimeMs);
      }
      if ((this.qosTier != 0) || (this.hasQosTier)) {
        paramCodedOutputByteBufferNano.writeInt32(9, this.qosTier);
      }
      if ((this.scheduler != 0) || (this.hasScheduler)) {
        paramCodedOutputByteBufferNano.writeInt32(10, this.scheduler);
      }
      if (this.deviceStatus != null) {
        paramCodedOutputByteBufferNano.writeMessage(11, this.deviceStatus);
      }
      if (this.externalTimestamp != null) {
        paramCodedOutputByteBufferNano.writeMessage(12, this.externalTimestamp);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class LogResponse
    extends MessageNano
  {
    public ClientAnalytics.ExperimentIdList experiments = null;
    public boolean hasNextRequestWaitMillis = false;
    public long nextRequestWaitMillis = -1L;
    public ClientAnalytics.QosTiersOverride qosTier = null;
    
    public LogResponse()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasNextRequestWaitMillis) || (this.nextRequestWaitMillis != -1L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(1, this.nextRequestWaitMillis);
      }
      if (this.experiments != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.experiments);
      }
      if (this.qosTier != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(3, this.qosTier);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasNextRequestWaitMillis) || (this.nextRequestWaitMillis != -1L)) {
        paramCodedOutputByteBufferNano.writeInt64(1, this.nextRequestWaitMillis);
      }
      if (this.experiments != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.experiments);
      }
      if (this.qosTier != null) {
        paramCodedOutputByteBufferNano.writeMessage(3, this.qosTier);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class PancettaClientInfo
    extends MessageNano
  {
    public String appId = "";
    public String appVersion = "";
    public String deviceId = "";
    public boolean hasAppId = false;
    public boolean hasAppVersion = false;
    public boolean hasDeviceId = false;
    public boolean hasMccMnc = false;
    public boolean hasOs = false;
    public String mccMnc = "";
    public int os = 0;
    
    public PancettaClientInfo()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasDeviceId) || (!this.deviceId.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.deviceId);
      }
      if ((this.os != 0) || (this.hasOs)) {
        i += CodedOutputByteBufferNano.computeInt32Size(2, this.os);
      }
      if ((this.hasAppId) || (!this.appId.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.appId);
      }
      if ((this.hasAppVersion) || (!this.appVersion.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(4, this.appVersion);
      }
      if ((this.hasMccMnc) || (!this.mccMnc.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(5, this.mccMnc);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasDeviceId) || (!this.deviceId.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.deviceId);
      }
      if ((this.os != 0) || (this.hasOs)) {
        paramCodedOutputByteBufferNano.writeInt32(2, this.os);
      }
      if ((this.hasAppId) || (!this.appId.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.appId);
      }
      if ((this.hasAppVersion) || (!this.appVersion.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(4, this.appVersion);
      }
      if ((this.hasMccMnc) || (!this.mccMnc.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(5, this.mccMnc);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class PlayCeClientInfo
    extends MessageNano
  {
    public String applicationBuild = "";
    public String clientId = "";
    public String country = "";
    public boolean hasApplicationBuild = false;
    public boolean hasClientId = false;
    public boolean hasCountry = false;
    public boolean hasLoggingId = false;
    public boolean hasMake = false;
    public boolean hasModel = false;
    public boolean hasPlatformVersion = false;
    public String loggingId = "";
    public String make = "";
    public String model = "";
    public String platformVersion = "";
    
    public PlayCeClientInfo()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasClientId) || (!this.clientId.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.clientId);
      }
      if ((this.hasMake) || (!this.make.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.make);
      }
      if ((this.hasModel) || (!this.model.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(4, this.model);
      }
      if ((this.hasApplicationBuild) || (!this.applicationBuild.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(5, this.applicationBuild);
      }
      if ((this.hasPlatformVersion) || (!this.platformVersion.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(6, this.platformVersion);
      }
      if ((this.hasLoggingId) || (!this.loggingId.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(7, this.loggingId);
      }
      if ((this.hasCountry) || (!this.country.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(8, this.country);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasClientId) || (!this.clientId.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.clientId);
      }
      if ((this.hasMake) || (!this.make.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.make);
      }
      if ((this.hasModel) || (!this.model.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(4, this.model);
      }
      if ((this.hasApplicationBuild) || (!this.applicationBuild.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(5, this.applicationBuild);
      }
      if ((this.hasPlatformVersion) || (!this.platformVersion.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(6, this.platformVersion);
      }
      if ((this.hasLoggingId) || (!this.loggingId.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(7, this.loggingId);
      }
      if ((this.hasCountry) || (!this.country.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(8, this.country);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class QosTierConfiguration
    extends MessageNano
  {
    private static volatile QosTierConfiguration[] _emptyArray;
    public boolean hasLogSourceName = false;
    public boolean hasQosTier = false;
    public String logSourceName = "";
    public int qosTier = 0;
    
    public QosTierConfiguration()
    {
      this.cachedSize = -1;
    }
    
    public static QosTierConfiguration[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new QosTierConfiguration[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasLogSourceName) || (!this.logSourceName.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.logSourceName);
      }
      if ((this.qosTier != 0) || (this.hasQosTier)) {
        i += CodedOutputByteBufferNano.computeInt32Size(2, this.qosTier);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasLogSourceName) || (!this.logSourceName.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.logSourceName);
      }
      if ((this.qosTier != 0) || (this.hasQosTier)) {
        paramCodedOutputByteBufferNano.writeInt32(2, this.qosTier);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class QosTiersOverride
    extends MessageNano
  {
    public boolean hasQosTierFingerprint = false;
    public ClientAnalytics.QosTierConfiguration[] qosTierConfiguration = ClientAnalytics.QosTierConfiguration.emptyArray();
    public long qosTierFingerprint = 0L;
    
    public QosTiersOverride()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.qosTierConfiguration != null) && (this.qosTierConfiguration.length > 0)) {
        for (int j = 0; j < this.qosTierConfiguration.length; j++)
        {
          ClientAnalytics.QosTierConfiguration localQosTierConfiguration = this.qosTierConfiguration[j];
          if (localQosTierConfiguration != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(1, localQosTierConfiguration);
          }
        }
      }
      if ((this.hasQosTierFingerprint) || (this.qosTierFingerprint != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(2, this.qosTierFingerprint);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.qosTierConfiguration != null) && (this.qosTierConfiguration.length > 0)) {
        for (int i = 0; i < this.qosTierConfiguration.length; i++)
        {
          ClientAnalytics.QosTierConfiguration localQosTierConfiguration = this.qosTierConfiguration[i];
          if (localQosTierConfiguration != null) {
            paramCodedOutputByteBufferNano.writeMessage(1, localQosTierConfiguration);
          }
        }
      }
      if ((this.hasQosTierFingerprint) || (this.qosTierFingerprint != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(2, this.qosTierFingerprint);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class VrClientInfo
    extends MessageNano
  {
    public String country = "";
    public String fingerprint = "";
    public String gvrVersion = "";
    public boolean hasCountry = false;
    public boolean hasFingerprint = false;
    public boolean hasGvrVersion = false;
    public boolean hasLanguage = false;
    public boolean hasManufacturer = false;
    public boolean hasModel = false;
    public boolean hasSdkVersion = false;
    public boolean hasUnitySdkVersion = false;
    public boolean hasUnityVersion = false;
    public boolean hasVrClientType = false;
    public String language = "";
    public String manufacturer = "";
    public String model = "";
    public String sdkVersion = "";
    public String unitySdkVersion = "";
    public String unityVersion = "";
    public int vrClientType = 0;
    
    public VrClientInfo()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.vrClientType != 0) || (this.hasVrClientType)) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.vrClientType);
      }
      if ((this.hasSdkVersion) || (!this.sdkVersion.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.sdkVersion);
      }
      if ((this.hasFingerprint) || (!this.fingerprint.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.fingerprint);
      }
      if ((this.hasGvrVersion) || (!this.gvrVersion.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(4, this.gvrVersion);
      }
      if ((this.hasManufacturer) || (!this.manufacturer.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(5, this.manufacturer);
      }
      if ((this.hasModel) || (!this.model.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(6, this.model);
      }
      if ((this.hasLanguage) || (!this.language.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(7, this.language);
      }
      if ((this.hasCountry) || (!this.country.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(8, this.country);
      }
      if ((this.hasUnityVersion) || (!this.unityVersion.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(9, this.unityVersion);
      }
      if ((this.hasUnitySdkVersion) || (!this.unitySdkVersion.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(10, this.unitySdkVersion);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.vrClientType != 0) || (this.hasVrClientType)) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.vrClientType);
      }
      if ((this.hasSdkVersion) || (!this.sdkVersion.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.sdkVersion);
      }
      if ((this.hasFingerprint) || (!this.fingerprint.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.fingerprint);
      }
      if ((this.hasGvrVersion) || (!this.gvrVersion.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(4, this.gvrVersion);
      }
      if ((this.hasManufacturer) || (!this.manufacturer.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(5, this.manufacturer);
      }
      if ((this.hasModel) || (!this.model.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(6, this.model);
      }
      if ((this.hasLanguage) || (!this.language.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(7, this.language);
      }
      if ((this.hasCountry) || (!this.country.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(8, this.country);
      }
      if ((this.hasUnityVersion) || (!this.unityVersion.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(9, this.unityVersion);
      }
      if ((this.hasUnitySdkVersion) || (!this.unitySdkVersion.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(10, this.unitySdkVersion);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.analytics.ClientAnalytics
 * JD-Core Version:    0.7.0.1
 */