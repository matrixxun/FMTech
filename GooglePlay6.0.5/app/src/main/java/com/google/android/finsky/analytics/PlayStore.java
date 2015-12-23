package com.google.android.finsky.analytics;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;
import java.util.Arrays;

public abstract interface PlayStore
{
  public static final class AppData
    extends MessageNano
  {
    public boolean downloadExternal = false;
    public boolean downloadGzip = false;
    public boolean downloadPatch = false;
    public long downloadedBytes = 0L;
    public boolean forWear = false;
    public boolean hasDownloadExternal = false;
    public boolean hasDownloadGzip = false;
    public boolean hasDownloadPatch = false;
    public boolean hasDownloadedBytes = false;
    public boolean hasForWear = false;
    public boolean hasOldVersion = false;
    public boolean hasPatchFormat = false;
    public boolean hasSplitId = false;
    public boolean hasSystemApp = false;
    public boolean hasVersion = false;
    public int oldVersion = 0;
    public int patchFormat = 0;
    public String splitId = "";
    public boolean systemApp = false;
    public int version = 0;
    
    public AppData()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasVersion) || (this.version != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.version);
      }
      if ((this.hasOldVersion) || (this.oldVersion != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(2, this.oldVersion);
      }
      if ((this.hasSystemApp) || (this.systemApp)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(3);
      }
      if ((this.hasDownloadExternal) || (this.downloadExternal)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(4);
      }
      if ((this.hasDownloadGzip) || (this.downloadGzip)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(5);
      }
      if ((this.hasDownloadPatch) || (this.downloadPatch)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(6);
      }
      if ((this.hasSplitId) || (!this.splitId.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(7, this.splitId);
      }
      if ((this.hasForWear) || (this.forWear)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(8);
      }
      if ((this.hasDownloadedBytes) || (this.downloadedBytes != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(9, this.downloadedBytes);
      }
      if ((this.hasPatchFormat) || (this.patchFormat != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(10, this.patchFormat);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasVersion) || (this.version != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.version);
      }
      if ((this.hasOldVersion) || (this.oldVersion != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(2, this.oldVersion);
      }
      if ((this.hasSystemApp) || (this.systemApp)) {
        paramCodedOutputByteBufferNano.writeBool(3, this.systemApp);
      }
      if ((this.hasDownloadExternal) || (this.downloadExternal)) {
        paramCodedOutputByteBufferNano.writeBool(4, this.downloadExternal);
      }
      if ((this.hasDownloadGzip) || (this.downloadGzip)) {
        paramCodedOutputByteBufferNano.writeBool(5, this.downloadGzip);
      }
      if ((this.hasDownloadPatch) || (this.downloadPatch)) {
        paramCodedOutputByteBufferNano.writeBool(6, this.downloadPatch);
      }
      if ((this.hasSplitId) || (!this.splitId.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(7, this.splitId);
      }
      if ((this.hasForWear) || (this.forWear)) {
        paramCodedOutputByteBufferNano.writeBool(8, this.forWear);
      }
      if ((this.hasDownloadedBytes) || (this.downloadedBytes != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(9, this.downloadedBytes);
      }
      if ((this.hasPatchFormat) || (this.patchFormat != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(10, this.patchFormat);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class AuthContext
    extends MessageNano
  {
    public int delegationReason = 0;
    public boolean hasDelegationReason = false;
    public boolean hasMode = false;
    public int mode = 0;
    
    public AuthContext()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.mode != 0) || (this.hasMode)) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.mode);
      }
      if ((this.delegationReason != 0) || (this.hasDelegationReason)) {
        i += CodedOutputByteBufferNano.computeInt32Size(2, this.delegationReason);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.mode != 0) || (this.hasMode)) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.mode);
      }
      if ((this.delegationReason != 0) || (this.hasDelegationReason)) {
        paramCodedOutputByteBufferNano.writeInt32(2, this.delegationReason);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class AutoUpdateData
    extends MessageNano
  {
    public boolean autoUpdateSuccess = false;
    public boolean hasAutoUpdateSuccess = false;
    public boolean hasNumFailedAttempts = false;
    public boolean hasNumPackagesDeferred = false;
    public boolean hasNumPackagesInstalled = false;
    public boolean hasRecheckState = false;
    public boolean hasRescheduled = false;
    public boolean hasSkippedDueToDisabledByUser = false;
    public boolean hasSkippedDueToForeground = false;
    public boolean hasSkippedDueToGlobalDisabled = false;
    public boolean hasSkippedDueToLargeDownload = false;
    public boolean hasSkippedDueToNewPermission = false;
    public boolean hasSkippedDueToPower = false;
    public boolean hasSkippedDueToProjection = false;
    public boolean hasSkippedDueToWifi = false;
    public boolean hasTimeSinceFirstFailMs = false;
    public boolean hasWifiCheckIntervalMs = false;
    public int numFailedAttempts = 0;
    public int numPackagesDeferred = 0;
    public int numPackagesInstalled = 0;
    public int recheckState = 0;
    public boolean rescheduled = false;
    public boolean skippedDueToDisabledByUser = false;
    public boolean skippedDueToForeground = false;
    public boolean skippedDueToGlobalDisabled = false;
    public boolean skippedDueToLargeDownload = false;
    public boolean skippedDueToNewPermission = false;
    public boolean skippedDueToPower = false;
    public boolean skippedDueToProjection = false;
    public boolean skippedDueToWifi = false;
    public long timeSinceFirstFailMs = 0L;
    public long wifiCheckIntervalMs = 0L;
    
    public AutoUpdateData()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasAutoUpdateSuccess) || (this.autoUpdateSuccess)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(1);
      }
      if ((this.hasNumFailedAttempts) || (this.numFailedAttempts != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(2, this.numFailedAttempts);
      }
      if ((this.hasTimeSinceFirstFailMs) || (this.timeSinceFirstFailMs != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(3, this.timeSinceFirstFailMs);
      }
      if ((this.hasWifiCheckIntervalMs) || (this.wifiCheckIntervalMs != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(4, this.wifiCheckIntervalMs);
      }
      if ((this.hasSkippedDueToProjection) || (this.skippedDueToProjection)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(5);
      }
      if ((this.hasSkippedDueToPower) || (this.skippedDueToPower)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(6);
      }
      if ((this.hasSkippedDueToWifi) || (this.skippedDueToWifi)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(7);
      }
      if ((this.hasRecheckState) || (this.recheckState != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(8, this.recheckState);
      }
      if ((this.hasSkippedDueToNewPermission) || (this.skippedDueToNewPermission)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(9);
      }
      if ((this.hasSkippedDueToLargeDownload) || (this.skippedDueToLargeDownload)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(10);
      }
      if ((this.hasSkippedDueToDisabledByUser) || (this.skippedDueToDisabledByUser)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(11);
      }
      if ((this.hasSkippedDueToGlobalDisabled) || (this.skippedDueToGlobalDisabled)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(12);
      }
      if ((this.hasNumPackagesInstalled) || (this.numPackagesInstalled != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(13, this.numPackagesInstalled);
      }
      if ((this.hasNumPackagesDeferred) || (this.numPackagesDeferred != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(14, this.numPackagesDeferred);
      }
      if ((this.hasSkippedDueToForeground) || (this.skippedDueToForeground)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(15);
      }
      if ((this.hasRescheduled) || (this.rescheduled)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(16);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasAutoUpdateSuccess) || (this.autoUpdateSuccess)) {
        paramCodedOutputByteBufferNano.writeBool(1, this.autoUpdateSuccess);
      }
      if ((this.hasNumFailedAttempts) || (this.numFailedAttempts != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(2, this.numFailedAttempts);
      }
      if ((this.hasTimeSinceFirstFailMs) || (this.timeSinceFirstFailMs != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(3, this.timeSinceFirstFailMs);
      }
      if ((this.hasWifiCheckIntervalMs) || (this.wifiCheckIntervalMs != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(4, this.wifiCheckIntervalMs);
      }
      if ((this.hasSkippedDueToProjection) || (this.skippedDueToProjection)) {
        paramCodedOutputByteBufferNano.writeBool(5, this.skippedDueToProjection);
      }
      if ((this.hasSkippedDueToPower) || (this.skippedDueToPower)) {
        paramCodedOutputByteBufferNano.writeBool(6, this.skippedDueToPower);
      }
      if ((this.hasSkippedDueToWifi) || (this.skippedDueToWifi)) {
        paramCodedOutputByteBufferNano.writeBool(7, this.skippedDueToWifi);
      }
      if ((this.hasRecheckState) || (this.recheckState != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(8, this.recheckState);
      }
      if ((this.hasSkippedDueToNewPermission) || (this.skippedDueToNewPermission)) {
        paramCodedOutputByteBufferNano.writeBool(9, this.skippedDueToNewPermission);
      }
      if ((this.hasSkippedDueToLargeDownload) || (this.skippedDueToLargeDownload)) {
        paramCodedOutputByteBufferNano.writeBool(10, this.skippedDueToLargeDownload);
      }
      if ((this.hasSkippedDueToDisabledByUser) || (this.skippedDueToDisabledByUser)) {
        paramCodedOutputByteBufferNano.writeBool(11, this.skippedDueToDisabledByUser);
      }
      if ((this.hasSkippedDueToGlobalDisabled) || (this.skippedDueToGlobalDisabled)) {
        paramCodedOutputByteBufferNano.writeBool(12, this.skippedDueToGlobalDisabled);
      }
      if ((this.hasNumPackagesInstalled) || (this.numPackagesInstalled != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(13, this.numPackagesInstalled);
      }
      if ((this.hasNumPackagesDeferred) || (this.numPackagesDeferred != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(14, this.numPackagesDeferred);
      }
      if ((this.hasSkippedDueToForeground) || (this.skippedDueToForeground)) {
        paramCodedOutputByteBufferNano.writeBool(15, this.skippedDueToForeground);
      }
      if ((this.hasRescheduled) || (this.rescheduled)) {
        paramCodedOutputByteBufferNano.writeBool(16, this.rescheduled);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class BillingProfileData
    extends MessageNano
  {
    public int flow = 0;
    public boolean hasFlow = false;
    
    public BillingProfileData()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasFlow) || (this.flow != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.flow);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasFlow) || (this.flow != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.flow);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class CheckIabPromoData
    extends MessageNano
  {
    public int actualResult = 0;
    public String callingPackage = "";
    public boolean hasActualResult = false;
    public boolean hasCallingPackage = false;
    public boolean hasReportedResult = false;
    public int reportedResult = 0;
    
    public CheckIabPromoData()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.reportedResult != 0) || (this.hasReportedResult)) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.reportedResult);
      }
      if ((this.actualResult != 0) || (this.hasActualResult)) {
        i += CodedOutputByteBufferNano.computeInt32Size(2, this.actualResult);
      }
      if ((this.hasCallingPackage) || (!this.callingPackage.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.callingPackage);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.reportedResult != 0) || (this.hasReportedResult)) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.reportedResult);
      }
      if ((this.actualResult != 0) || (this.hasActualResult)) {
        paramCodedOutputByteBufferNano.writeInt32(2, this.actualResult);
      }
      if ((this.hasCallingPackage) || (!this.callingPackage.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.callingPackage);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class CreditCardEntryAction
    extends MessageNano
  {
    public int cameraInputPreference = 0;
    public int expDateEntryType = 0;
    public boolean expDateOcrEnabled = false;
    public boolean expDateRecognizedByNfc = false;
    public boolean expDateRecognizedByOcr = false;
    public boolean expDateValidationErrorOccurred = false;
    public boolean hasCameraInputPreference = false;
    public boolean hasExpDateEntryType = false;
    public boolean hasExpDateOcrEnabled = false;
    public boolean hasExpDateRecognizedByNfc = false;
    public boolean hasExpDateRecognizedByOcr = false;
    public boolean hasExpDateValidationErrorOccurred = false;
    public boolean hasNameEntryType = false;
    public boolean hasNameOcrEnabled = false;
    public boolean hasNameRecognizedByNfc = false;
    public boolean hasNameRecognizedByOcr = false;
    public boolean hasNameValidationErrorOccurred = false;
    public boolean hasNfcAdapterEnabled = false;
    public boolean hasNfcElapsedTimeMillis = false;
    public boolean hasNfcErrorReason = false;
    public boolean hasNfcExitReason = false;
    public boolean hasNfcFeatureEnabled = false;
    public boolean hasNfcInputPreference = false;
    public boolean hasNumNfcAttempts = false;
    public boolean hasNumOcrAttempts = false;
    public boolean hasOcrExitReason = false;
    public boolean hasPanEntryType = false;
    public boolean hasPanOcrEnabled = false;
    public boolean hasPanRecognizedByNfc = false;
    public boolean hasPanRecognizedByOcr = false;
    public boolean hasPanValidationErrorOccurred = false;
    public int nameEntryType = 0;
    public boolean nameOcrEnabled = false;
    public boolean nameRecognizedByNfc = false;
    public boolean nameRecognizedByOcr = false;
    public boolean nameValidationErrorOccurred = false;
    public boolean nfcAdapterEnabled = false;
    public long nfcElapsedTimeMillis = 0L;
    public int nfcErrorReason = 0;
    public int nfcExitReason = 0;
    public boolean nfcFeatureEnabled = false;
    public int nfcInputPreference = 0;
    public int numNfcAttempts = 0;
    public int numOcrAttempts = 0;
    public int ocrExitReason = 0;
    public int panEntryType = 0;
    public boolean panOcrEnabled = false;
    public boolean panRecognizedByNfc = false;
    public boolean panRecognizedByOcr = false;
    public boolean panValidationErrorOccurred = false;
    
    public CreditCardEntryAction()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasPanOcrEnabled) || (this.panOcrEnabled)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(1);
      }
      if ((this.panEntryType != 0) || (this.hasPanEntryType)) {
        i += CodedOutputByteBufferNano.computeInt32Size(2, this.panEntryType);
      }
      if ((this.hasPanRecognizedByOcr) || (this.panRecognizedByOcr)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(3);
      }
      if ((this.hasPanValidationErrorOccurred) || (this.panValidationErrorOccurred)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(4);
      }
      if ((this.hasExpDateOcrEnabled) || (this.expDateOcrEnabled)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(5);
      }
      if ((this.expDateEntryType != 0) || (this.hasExpDateEntryType)) {
        i += CodedOutputByteBufferNano.computeInt32Size(6, this.expDateEntryType);
      }
      if ((this.hasExpDateRecognizedByOcr) || (this.expDateRecognizedByOcr)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(7);
      }
      if ((this.hasExpDateValidationErrorOccurred) || (this.expDateValidationErrorOccurred)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(8);
      }
      if ((this.hasNumOcrAttempts) || (this.numOcrAttempts != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(9, this.numOcrAttempts);
      }
      if ((this.ocrExitReason != 0) || (this.hasOcrExitReason)) {
        i += CodedOutputByteBufferNano.computeInt32Size(10, this.ocrExitReason);
      }
      if ((this.hasPanRecognizedByNfc) || (this.panRecognizedByNfc)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(11);
      }
      if ((this.hasExpDateRecognizedByNfc) || (this.expDateRecognizedByNfc)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(12);
      }
      if ((this.hasNameOcrEnabled) || (this.nameOcrEnabled)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(13);
      }
      if ((this.nameEntryType != 0) || (this.hasNameEntryType)) {
        i += CodedOutputByteBufferNano.computeInt32Size(14, this.nameEntryType);
      }
      if ((this.hasNameRecognizedByOcr) || (this.nameRecognizedByOcr)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(15);
      }
      if ((this.hasNameValidationErrorOccurred) || (this.nameValidationErrorOccurred)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(16);
      }
      if ((this.hasNameRecognizedByNfc) || (this.nameRecognizedByNfc)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(17);
      }
      if ((this.hasNfcFeatureEnabled) || (this.nfcFeatureEnabled)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(18);
      }
      if ((this.hasNumNfcAttempts) || (this.numNfcAttempts != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(19, this.numNfcAttempts);
      }
      if ((this.nfcExitReason != 0) || (this.hasNfcExitReason)) {
        i += CodedOutputByteBufferNano.computeInt32Size(20, this.nfcExitReason);
      }
      if ((this.nfcErrorReason != 0) || (this.hasNfcErrorReason)) {
        i += CodedOutputByteBufferNano.computeInt32Size(21, this.nfcErrorReason);
      }
      if ((this.hasNfcElapsedTimeMillis) || (this.nfcElapsedTimeMillis != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(22, this.nfcElapsedTimeMillis);
      }
      if ((this.hasNfcAdapterEnabled) || (this.nfcAdapterEnabled)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(23);
      }
      if ((this.hasCameraInputPreference) || (this.cameraInputPreference != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(24, this.cameraInputPreference);
      }
      if ((this.hasNfcInputPreference) || (this.nfcInputPreference != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(25, this.nfcInputPreference);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasPanOcrEnabled) || (this.panOcrEnabled)) {
        paramCodedOutputByteBufferNano.writeBool(1, this.panOcrEnabled);
      }
      if ((this.panEntryType != 0) || (this.hasPanEntryType)) {
        paramCodedOutputByteBufferNano.writeInt32(2, this.panEntryType);
      }
      if ((this.hasPanRecognizedByOcr) || (this.panRecognizedByOcr)) {
        paramCodedOutputByteBufferNano.writeBool(3, this.panRecognizedByOcr);
      }
      if ((this.hasPanValidationErrorOccurred) || (this.panValidationErrorOccurred)) {
        paramCodedOutputByteBufferNano.writeBool(4, this.panValidationErrorOccurred);
      }
      if ((this.hasExpDateOcrEnabled) || (this.expDateOcrEnabled)) {
        paramCodedOutputByteBufferNano.writeBool(5, this.expDateOcrEnabled);
      }
      if ((this.expDateEntryType != 0) || (this.hasExpDateEntryType)) {
        paramCodedOutputByteBufferNano.writeInt32(6, this.expDateEntryType);
      }
      if ((this.hasExpDateRecognizedByOcr) || (this.expDateRecognizedByOcr)) {
        paramCodedOutputByteBufferNano.writeBool(7, this.expDateRecognizedByOcr);
      }
      if ((this.hasExpDateValidationErrorOccurred) || (this.expDateValidationErrorOccurred)) {
        paramCodedOutputByteBufferNano.writeBool(8, this.expDateValidationErrorOccurred);
      }
      if ((this.hasNumOcrAttempts) || (this.numOcrAttempts != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(9, this.numOcrAttempts);
      }
      if ((this.ocrExitReason != 0) || (this.hasOcrExitReason)) {
        paramCodedOutputByteBufferNano.writeInt32(10, this.ocrExitReason);
      }
      if ((this.hasPanRecognizedByNfc) || (this.panRecognizedByNfc)) {
        paramCodedOutputByteBufferNano.writeBool(11, this.panRecognizedByNfc);
      }
      if ((this.hasExpDateRecognizedByNfc) || (this.expDateRecognizedByNfc)) {
        paramCodedOutputByteBufferNano.writeBool(12, this.expDateRecognizedByNfc);
      }
      if ((this.hasNameOcrEnabled) || (this.nameOcrEnabled)) {
        paramCodedOutputByteBufferNano.writeBool(13, this.nameOcrEnabled);
      }
      if ((this.nameEntryType != 0) || (this.hasNameEntryType)) {
        paramCodedOutputByteBufferNano.writeInt32(14, this.nameEntryType);
      }
      if ((this.hasNameRecognizedByOcr) || (this.nameRecognizedByOcr)) {
        paramCodedOutputByteBufferNano.writeBool(15, this.nameRecognizedByOcr);
      }
      if ((this.hasNameValidationErrorOccurred) || (this.nameValidationErrorOccurred)) {
        paramCodedOutputByteBufferNano.writeBool(16, this.nameValidationErrorOccurred);
      }
      if ((this.hasNameRecognizedByNfc) || (this.nameRecognizedByNfc)) {
        paramCodedOutputByteBufferNano.writeBool(17, this.nameRecognizedByNfc);
      }
      if ((this.hasNfcFeatureEnabled) || (this.nfcFeatureEnabled)) {
        paramCodedOutputByteBufferNano.writeBool(18, this.nfcFeatureEnabled);
      }
      if ((this.hasNumNfcAttempts) || (this.numNfcAttempts != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(19, this.numNfcAttempts);
      }
      if ((this.nfcExitReason != 0) || (this.hasNfcExitReason)) {
        paramCodedOutputByteBufferNano.writeInt32(20, this.nfcExitReason);
      }
      if ((this.nfcErrorReason != 0) || (this.hasNfcErrorReason)) {
        paramCodedOutputByteBufferNano.writeInt32(21, this.nfcErrorReason);
      }
      if ((this.hasNfcElapsedTimeMillis) || (this.nfcElapsedTimeMillis != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(22, this.nfcElapsedTimeMillis);
      }
      if ((this.hasNfcAdapterEnabled) || (this.nfcAdapterEnabled)) {
        paramCodedOutputByteBufferNano.writeBool(23, this.nfcAdapterEnabled);
      }
      if ((this.hasCameraInputPreference) || (this.cameraInputPreference != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(24, this.cameraInputPreference);
      }
      if ((this.hasNfcInputPreference) || (this.nfcInputPreference != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(25, this.nfcInputPreference);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class DeviceFeature
    extends MessageNano
  {
    public DeviceFeatureInfo[] deviceFeatureInfo = DeviceFeatureInfo.emptyArray();
    
    public DeviceFeature()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.deviceFeatureInfo != null) && (this.deviceFeatureInfo.length > 0)) {
        for (int j = 0; j < this.deviceFeatureInfo.length; j++)
        {
          DeviceFeatureInfo localDeviceFeatureInfo = this.deviceFeatureInfo[j];
          if (localDeviceFeatureInfo != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(1, localDeviceFeatureInfo);
          }
        }
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.deviceFeatureInfo != null) && (this.deviceFeatureInfo.length > 0)) {
        for (int i = 0; i < this.deviceFeatureInfo.length; i++)
        {
          DeviceFeatureInfo localDeviceFeatureInfo = this.deviceFeatureInfo[i];
          if (localDeviceFeatureInfo != null) {
            paramCodedOutputByteBufferNano.writeMessage(1, localDeviceFeatureInfo);
          }
        }
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
    
    public static final class DeviceFeatureInfo
      extends MessageNano
    {
      private static volatile DeviceFeatureInfo[] _emptyArray;
      public String featureName = "";
      public boolean hasFeatureName = false;
      public boolean hasLastConnectionTimeMs = false;
      public long lastConnectionTimeMs = 0L;
      
      public DeviceFeatureInfo()
      {
        this.cachedSize = -1;
      }
      
      public static DeviceFeatureInfo[] emptyArray()
      {
        if (_emptyArray == null) {}
        synchronized (InternalNano.LAZY_INIT_LOCK)
        {
          if (_emptyArray == null) {
            _emptyArray = new DeviceFeatureInfo[0];
          }
          return _emptyArray;
        }
      }
      
      protected final int computeSerializedSize()
      {
        int i = super.computeSerializedSize();
        if ((this.hasFeatureName) || (!this.featureName.equals(""))) {
          i += CodedOutputByteBufferNano.computeStringSize(1, this.featureName);
        }
        if ((this.hasLastConnectionTimeMs) || (this.lastConnectionTimeMs != 0L)) {
          i += CodedOutputByteBufferNano.computeInt64Size(2, this.lastConnectionTimeMs);
        }
        return i;
      }
      
      public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
        throws IOException
      {
        if ((this.hasFeatureName) || (!this.featureName.equals(""))) {
          paramCodedOutputByteBufferNano.writeString(1, this.featureName);
        }
        if ((this.hasLastConnectionTimeMs) || (this.lastConnectionTimeMs != 0L)) {
          paramCodedOutputByteBufferNano.writeInt64(2, this.lastConnectionTimeMs);
        }
        super.writeTo(paramCodedOutputByteBufferNano);
      }
    }
  }
  
  public static final class NlpRepairStatus
    extends MessageNano
  {
    public int enabled = 0;
    public int flags = 0;
    public boolean foundTestKeys = false;
    public boolean hasEnabled = false;
    public boolean hasFlags = false;
    public boolean hasFoundTestKeys = false;
    public boolean hasHoldoffAfterInstall = false;
    public boolean hasHoldoffUntilBoot = false;
    public boolean hasHoldoffUntilWipe = false;
    public boolean hasRepairStatus = false;
    public boolean hasSignatureHash = false;
    public boolean hasVersionCode = false;
    public boolean holdoffAfterInstall = false;
    public boolean holdoffUntilBoot = false;
    public boolean holdoffUntilWipe = false;
    public int repairStatus = 0;
    public String signatureHash = "";
    public int versionCode = 0;
    
    public NlpRepairStatus()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.repairStatus != 0) || (this.hasRepairStatus)) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.repairStatus);
      }
      if ((this.hasFlags) || (this.flags != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(2, this.flags);
      }
      if ((this.hasVersionCode) || (this.versionCode != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(3, this.versionCode);
      }
      if ((this.hasSignatureHash) || (!this.signatureHash.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(4, this.signatureHash);
      }
      if ((this.hasFoundTestKeys) || (this.foundTestKeys)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(5);
      }
      if ((this.hasHoldoffUntilBoot) || (this.holdoffUntilBoot)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(6);
      }
      if ((this.hasHoldoffUntilWipe) || (this.holdoffUntilWipe)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(7);
      }
      if ((this.hasHoldoffAfterInstall) || (this.holdoffAfterInstall)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(8);
      }
      if ((this.hasEnabled) || (this.enabled != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(9, this.enabled);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.repairStatus != 0) || (this.hasRepairStatus)) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.repairStatus);
      }
      if ((this.hasFlags) || (this.flags != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(2, this.flags);
      }
      if ((this.hasVersionCode) || (this.versionCode != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(3, this.versionCode);
      }
      if ((this.hasSignatureHash) || (!this.signatureHash.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(4, this.signatureHash);
      }
      if ((this.hasFoundTestKeys) || (this.foundTestKeys)) {
        paramCodedOutputByteBufferNano.writeBool(5, this.foundTestKeys);
      }
      if ((this.hasHoldoffUntilBoot) || (this.holdoffUntilBoot)) {
        paramCodedOutputByteBufferNano.writeBool(6, this.holdoffUntilBoot);
      }
      if ((this.hasHoldoffUntilWipe) || (this.holdoffUntilWipe)) {
        paramCodedOutputByteBufferNano.writeBool(7, this.holdoffUntilWipe);
      }
      if ((this.hasHoldoffAfterInstall) || (this.holdoffAfterInstall)) {
        paramCodedOutputByteBufferNano.writeBool(8, this.holdoffAfterInstall);
      }
      if ((this.hasEnabled) || (this.enabled != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(9, this.enabled);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class PlayStoreBackgroundActionEvent
    extends MessageNano
  {
    public PlayStore.AppData appData;
    public int attempts;
    public PlayStore.AuthContext authContext;
    public PlayStore.AutoUpdateData autoUpdateData;
    public PlayStore.BillingProfileData billingProfileData;
    public String callingPackage;
    public PlayStore.CheckIabPromoData checkIabPromoData;
    public long clientLatencyMs;
    public PlayStore.CreditCardEntryAction creditCardEntryAction;
    public PlayStore.DeviceFeature deviceFeature;
    public String document;
    public int errorCode;
    public String exceptionType;
    public String externalReferrer;
    public int fromSetting;
    public boolean hasAttempts;
    public boolean hasCallingPackage;
    public boolean hasClientLatencyMs;
    public boolean hasDocument;
    public boolean hasErrorCode;
    public boolean hasExceptionType;
    public boolean hasExternalReferrer;
    public boolean hasFromSetting;
    public boolean hasHost;
    public boolean hasLastUrl;
    public boolean hasOfferCheckoutFlowRequired;
    public boolean hasOfferType;
    public boolean hasOperationSuccess;
    public boolean hasReason;
    public boolean hasServerLatencyMs;
    public boolean hasServerLogsCookie;
    public boolean hasToSetting;
    public boolean hasType;
    public String host;
    public String lastUrl;
    public PlayStore.NlpRepairStatus nlpRepairStatus;
    public boolean offerCheckoutFlowRequired;
    public int offerType;
    public boolean operationSuccess;
    public PlayStore.PurchaseData purchaseData;
    public String reason;
    public PlayStore.ReviewData reviewData;
    public PlayStore.PlayStoreRpcReport rpcReport;
    public PlayStore.SearchSuggestionReport searchSuggestionReport;
    public long serverLatencyMs;
    public byte[] serverLogsCookie;
    public PlayStore.PlayStoreSessionData sessionInfo;
    public int toSetting;
    public int type;
    public PlayStore.WearInfo wearInfo;
    public PlayStore.WebViewPageLoadEvent webViewPageLoadEvent;
    public PlayStore.WidgetEventData widgetEventData;
    
    public PlayStoreBackgroundActionEvent()
    {
      clear();
    }
    
    public final PlayStoreBackgroundActionEvent clear()
    {
      this.type = 0;
      this.hasType = false;
      this.document = "";
      this.hasDocument = false;
      this.reason = "";
      this.hasReason = false;
      this.errorCode = 0;
      this.hasErrorCode = false;
      this.exceptionType = "";
      this.hasExceptionType = false;
      this.serverLogsCookie = WireFormatNano.EMPTY_BYTES;
      this.hasServerLogsCookie = false;
      this.offerType = 0;
      this.hasOfferType = false;
      this.fromSetting = 0;
      this.hasFromSetting = false;
      this.toSetting = 0;
      this.hasToSetting = false;
      this.sessionInfo = null;
      this.appData = null;
      this.serverLatencyMs = 0L;
      this.hasServerLatencyMs = false;
      this.clientLatencyMs = 0L;
      this.hasClientLatencyMs = false;
      this.nlpRepairStatus = null;
      this.operationSuccess = false;
      this.hasOperationSuccess = false;
      this.host = "";
      this.hasHost = false;
      this.widgetEventData = null;
      this.autoUpdateData = null;
      this.attempts = 0;
      this.hasAttempts = false;
      this.offerCheckoutFlowRequired = false;
      this.hasOfferCheckoutFlowRequired = false;
      this.searchSuggestionReport = null;
      this.callingPackage = "";
      this.hasCallingPackage = false;
      this.reviewData = null;
      this.lastUrl = "";
      this.hasLastUrl = false;
      this.authContext = null;
      this.deviceFeature = null;
      this.rpcReport = null;
      this.creditCardEntryAction = null;
      this.externalReferrer = "";
      this.hasExternalReferrer = false;
      this.webViewPageLoadEvent = null;
      this.billingProfileData = null;
      this.checkIabPromoData = null;
      this.purchaseData = null;
      this.wearInfo = null;
      this.cachedSize = -1;
      return this;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.type != 0) || (this.hasType)) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.type);
      }
      if ((this.hasDocument) || (!this.document.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.document);
      }
      if ((this.hasReason) || (!this.reason.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.reason);
      }
      if ((this.hasErrorCode) || (this.errorCode != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(4, this.errorCode);
      }
      if ((this.hasExceptionType) || (!this.exceptionType.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(5, this.exceptionType);
      }
      if ((this.hasServerLogsCookie) || (!Arrays.equals(this.serverLogsCookie, WireFormatNano.EMPTY_BYTES))) {
        i += CodedOutputByteBufferNano.computeBytesSize(6, this.serverLogsCookie);
      }
      if ((this.hasOfferType) || (this.offerType != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(7, this.offerType);
      }
      if ((this.hasFromSetting) || (this.fromSetting != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(8, this.fromSetting);
      }
      if ((this.hasToSetting) || (this.toSetting != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(9, this.toSetting);
      }
      if (this.sessionInfo != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(10, this.sessionInfo);
      }
      if (this.appData != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(11, this.appData);
      }
      if ((this.hasServerLatencyMs) || (this.serverLatencyMs != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(12, this.serverLatencyMs);
      }
      if ((this.hasClientLatencyMs) || (this.clientLatencyMs != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(13, this.clientLatencyMs);
      }
      if (this.nlpRepairStatus != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(14, this.nlpRepairStatus);
      }
      if ((this.hasOperationSuccess) || (this.operationSuccess)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(15);
      }
      if ((this.hasHost) || (!this.host.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(16, this.host);
      }
      if (this.widgetEventData != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(17, this.widgetEventData);
      }
      if (this.autoUpdateData != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(18, this.autoUpdateData);
      }
      if ((this.hasAttempts) || (this.attempts != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(19, this.attempts);
      }
      if ((this.hasOfferCheckoutFlowRequired) || (this.offerCheckoutFlowRequired)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(20);
      }
      if (this.searchSuggestionReport != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(21, this.searchSuggestionReport);
      }
      if ((this.hasCallingPackage) || (!this.callingPackage.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(23, this.callingPackage);
      }
      if (this.reviewData != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(24, this.reviewData);
      }
      if ((this.hasLastUrl) || (!this.lastUrl.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(25, this.lastUrl);
      }
      if (this.authContext != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(26, this.authContext);
      }
      if (this.deviceFeature != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(27, this.deviceFeature);
      }
      if (this.rpcReport != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(28, this.rpcReport);
      }
      if (this.creditCardEntryAction != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(29, this.creditCardEntryAction);
      }
      if ((this.hasExternalReferrer) || (!this.externalReferrer.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(30, this.externalReferrer);
      }
      if (this.webViewPageLoadEvent != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(31, this.webViewPageLoadEvent);
      }
      if (this.billingProfileData != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(32, this.billingProfileData);
      }
      if (this.checkIabPromoData != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(33, this.checkIabPromoData);
      }
      if (this.purchaseData != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(34, this.purchaseData);
      }
      if (this.wearInfo != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(35, this.wearInfo);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.type != 0) || (this.hasType)) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.type);
      }
      if ((this.hasDocument) || (!this.document.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.document);
      }
      if ((this.hasReason) || (!this.reason.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.reason);
      }
      if ((this.hasErrorCode) || (this.errorCode != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(4, this.errorCode);
      }
      if ((this.hasExceptionType) || (!this.exceptionType.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(5, this.exceptionType);
      }
      if ((this.hasServerLogsCookie) || (!Arrays.equals(this.serverLogsCookie, WireFormatNano.EMPTY_BYTES))) {
        paramCodedOutputByteBufferNano.writeBytes(6, this.serverLogsCookie);
      }
      if ((this.hasOfferType) || (this.offerType != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(7, this.offerType);
      }
      if ((this.hasFromSetting) || (this.fromSetting != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(8, this.fromSetting);
      }
      if ((this.hasToSetting) || (this.toSetting != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(9, this.toSetting);
      }
      if (this.sessionInfo != null) {
        paramCodedOutputByteBufferNano.writeMessage(10, this.sessionInfo);
      }
      if (this.appData != null) {
        paramCodedOutputByteBufferNano.writeMessage(11, this.appData);
      }
      if ((this.hasServerLatencyMs) || (this.serverLatencyMs != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(12, this.serverLatencyMs);
      }
      if ((this.hasClientLatencyMs) || (this.clientLatencyMs != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(13, this.clientLatencyMs);
      }
      if (this.nlpRepairStatus != null) {
        paramCodedOutputByteBufferNano.writeMessage(14, this.nlpRepairStatus);
      }
      if ((this.hasOperationSuccess) || (this.operationSuccess)) {
        paramCodedOutputByteBufferNano.writeBool(15, this.operationSuccess);
      }
      if ((this.hasHost) || (!this.host.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(16, this.host);
      }
      if (this.widgetEventData != null) {
        paramCodedOutputByteBufferNano.writeMessage(17, this.widgetEventData);
      }
      if (this.autoUpdateData != null) {
        paramCodedOutputByteBufferNano.writeMessage(18, this.autoUpdateData);
      }
      if ((this.hasAttempts) || (this.attempts != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(19, this.attempts);
      }
      if ((this.hasOfferCheckoutFlowRequired) || (this.offerCheckoutFlowRequired)) {
        paramCodedOutputByteBufferNano.writeBool(20, this.offerCheckoutFlowRequired);
      }
      if (this.searchSuggestionReport != null) {
        paramCodedOutputByteBufferNano.writeMessage(21, this.searchSuggestionReport);
      }
      if ((this.hasCallingPackage) || (!this.callingPackage.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(23, this.callingPackage);
      }
      if (this.reviewData != null) {
        paramCodedOutputByteBufferNano.writeMessage(24, this.reviewData);
      }
      if ((this.hasLastUrl) || (!this.lastUrl.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(25, this.lastUrl);
      }
      if (this.authContext != null) {
        paramCodedOutputByteBufferNano.writeMessage(26, this.authContext);
      }
      if (this.deviceFeature != null) {
        paramCodedOutputByteBufferNano.writeMessage(27, this.deviceFeature);
      }
      if (this.rpcReport != null) {
        paramCodedOutputByteBufferNano.writeMessage(28, this.rpcReport);
      }
      if (this.creditCardEntryAction != null) {
        paramCodedOutputByteBufferNano.writeMessage(29, this.creditCardEntryAction);
      }
      if ((this.hasExternalReferrer) || (!this.externalReferrer.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(30, this.externalReferrer);
      }
      if (this.webViewPageLoadEvent != null) {
        paramCodedOutputByteBufferNano.writeMessage(31, this.webViewPageLoadEvent);
      }
      if (this.billingProfileData != null) {
        paramCodedOutputByteBufferNano.writeMessage(32, this.billingProfileData);
      }
      if (this.checkIabPromoData != null) {
        paramCodedOutputByteBufferNano.writeMessage(33, this.checkIabPromoData);
      }
      if (this.purchaseData != null) {
        paramCodedOutputByteBufferNano.writeMessage(34, this.purchaseData);
      }
      if (this.wearInfo != null) {
        paramCodedOutputByteBufferNano.writeMessage(35, this.wearInfo);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class PlayStoreClickEvent
    extends MessageNano
  {
    public PlayStore.PlayStoreUiElement[] elementPath;
    
    public PlayStoreClickEvent()
    {
      clear();
    }
    
    public final PlayStoreClickEvent clear()
    {
      this.elementPath = PlayStore.PlayStoreUiElement.emptyArray();
      this.cachedSize = -1;
      return this;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.elementPath != null) && (this.elementPath.length > 0)) {
        for (int j = 0; j < this.elementPath.length; j++)
        {
          PlayStore.PlayStoreUiElement localPlayStoreUiElement = this.elementPath[j];
          if (localPlayStoreUiElement != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(1, localPlayStoreUiElement);
          }
        }
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.elementPath != null) && (this.elementPath.length > 0)) {
        for (int i = 0; i < this.elementPath.length; i++)
        {
          PlayStore.PlayStoreUiElement localPlayStoreUiElement = this.elementPath[i];
          if (localPlayStoreUiElement != null) {
            paramCodedOutputByteBufferNano.writeMessage(1, localPlayStoreUiElement);
          }
        }
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class PlayStoreDeepLinkEvent
    extends MessageNano
  {
    public boolean canResolve = false;
    public String externalReferrer = "";
    public String externalUrl = "";
    public boolean hasCanResolve = false;
    public boolean hasExternalReferrer = false;
    public boolean hasExternalUrl = false;
    public boolean hasMinVersion = false;
    public boolean hasNewEnough = false;
    public boolean hasPackageName = false;
    public boolean hasReferrerPackage = false;
    public boolean hasResolvedType = false;
    public boolean hasServerLogsCookie = false;
    public int minVersion = 0;
    public boolean newEnough = false;
    public String packageName = "";
    public String referrerPackage = "";
    public int resolvedType = 0;
    public byte[] serverLogsCookie = WireFormatNano.EMPTY_BYTES;
    
    public PlayStoreDeepLinkEvent()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasExternalUrl) || (!this.externalUrl.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.externalUrl);
      }
      if ((this.resolvedType != 0) || (this.hasResolvedType)) {
        i += CodedOutputByteBufferNano.computeInt32Size(2, this.resolvedType);
      }
      if ((this.hasPackageName) || (!this.packageName.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.packageName);
      }
      if ((this.hasMinVersion) || (this.minVersion != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(4, this.minVersion);
      }
      if ((this.hasNewEnough) || (this.newEnough)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(5);
      }
      if ((this.hasCanResolve) || (this.canResolve)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(6);
      }
      if ((this.hasServerLogsCookie) || (!Arrays.equals(this.serverLogsCookie, WireFormatNano.EMPTY_BYTES))) {
        i += CodedOutputByteBufferNano.computeBytesSize(7, this.serverLogsCookie);
      }
      if ((this.hasExternalReferrer) || (!this.externalReferrer.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(8, this.externalReferrer);
      }
      if ((this.hasReferrerPackage) || (!this.referrerPackage.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(9, this.referrerPackage);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasExternalUrl) || (!this.externalUrl.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.externalUrl);
      }
      if ((this.resolvedType != 0) || (this.hasResolvedType)) {
        paramCodedOutputByteBufferNano.writeInt32(2, this.resolvedType);
      }
      if ((this.hasPackageName) || (!this.packageName.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.packageName);
      }
      if ((this.hasMinVersion) || (this.minVersion != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(4, this.minVersion);
      }
      if ((this.hasNewEnough) || (this.newEnough)) {
        paramCodedOutputByteBufferNano.writeBool(5, this.newEnough);
      }
      if ((this.hasCanResolve) || (this.canResolve)) {
        paramCodedOutputByteBufferNano.writeBool(6, this.canResolve);
      }
      if ((this.hasServerLogsCookie) || (!Arrays.equals(this.serverLogsCookie, WireFormatNano.EMPTY_BYTES))) {
        paramCodedOutputByteBufferNano.writeBytes(7, this.serverLogsCookie);
      }
      if ((this.hasExternalReferrer) || (!this.externalReferrer.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(8, this.externalReferrer);
      }
      if ((this.hasReferrerPackage) || (!this.referrerPackage.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(9, this.referrerPackage);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class PlayStoreImpressionEvent
    extends MessageNano
  {
    public boolean hasId;
    public long id;
    public PlayStore.PlayStoreUiElement[] referrerPath;
    public PlayStore.PlayStoreUiElement tree;
    
    public PlayStoreImpressionEvent()
    {
      clear();
    }
    
    public final PlayStoreImpressionEvent clear()
    {
      this.tree = null;
      this.referrerPath = PlayStore.PlayStoreUiElement.emptyArray();
      this.id = 0L;
      this.hasId = false;
      this.cachedSize = -1;
      return this;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.tree != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.tree);
      }
      if ((this.referrerPath != null) && (this.referrerPath.length > 0)) {
        for (int j = 0; j < this.referrerPath.length; j++)
        {
          PlayStore.PlayStoreUiElement localPlayStoreUiElement = this.referrerPath[j];
          if (localPlayStoreUiElement != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(2, localPlayStoreUiElement);
          }
        }
      }
      if ((this.hasId) || (this.id != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(3, this.id);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.tree != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.tree);
      }
      if ((this.referrerPath != null) && (this.referrerPath.length > 0)) {
        for (int i = 0; i < this.referrerPath.length; i++)
        {
          PlayStore.PlayStoreUiElement localPlayStoreUiElement = this.referrerPath[i];
          if (localPlayStoreUiElement != null) {
            paramCodedOutputByteBufferNano.writeMessage(2, localPlayStoreUiElement);
          }
        }
      }
      if ((this.hasId) || (this.id != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(3, this.id);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class PlayStoreLogEvent
    extends MessageNano
  {
    public PlayStore.PlayStoreBackgroundActionEvent backgroundAction;
    public PlayStore.PlayStoreClickEvent click;
    public PlayStore.PlayStoreDeepLinkEvent deepLink;
    public PlayStore.PlayStoreImpressionEvent impression;
    public PlayStore.PlayStoreSearchEvent search;
    public PlayStore.PlayStoreSurveyEvent survey;
    
    public PlayStoreLogEvent()
    {
      clear();
    }
    
    public final PlayStoreLogEvent clear()
    {
      this.impression = null;
      this.click = null;
      this.backgroundAction = null;
      this.search = null;
      this.deepLink = null;
      this.survey = null;
      this.cachedSize = -1;
      return this;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.impression != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.impression);
      }
      if (this.click != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(3, this.click);
      }
      if (this.backgroundAction != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(4, this.backgroundAction);
      }
      if (this.search != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(5, this.search);
      }
      if (this.deepLink != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(6, this.deepLink);
      }
      if (this.survey != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(7, this.survey);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.impression != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.impression);
      }
      if (this.click != null) {
        paramCodedOutputByteBufferNano.writeMessage(3, this.click);
      }
      if (this.backgroundAction != null) {
        paramCodedOutputByteBufferNano.writeMessage(4, this.backgroundAction);
      }
      if (this.search != null) {
        paramCodedOutputByteBufferNano.writeMessage(5, this.search);
      }
      if (this.deepLink != null) {
        paramCodedOutputByteBufferNano.writeMessage(6, this.deepLink);
      }
      if (this.survey != null) {
        paramCodedOutputByteBufferNano.writeMessage(7, this.survey);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class PlayStoreRpcReport
    extends MessageNano
  {
    public float backoffMultiplier = 0.0F;
    public boolean cacheHit = false;
    public long clientLatencyMs = 0L;
    public int endConnectionType = 0;
    public boolean hasBackoffMultiplier = false;
    public boolean hasCacheHit = false;
    public boolean hasClientLatencyMs = false;
    public boolean hasEndConnectionType = false;
    public boolean hasNumAttempts = false;
    public boolean hasResponseBodySizeBytes = false;
    public boolean hasServerLatencyMs = false;
    public boolean hasStartConnectionType = false;
    public boolean hasTimeoutMs = false;
    public boolean hasUrl = false;
    public boolean hasVolleyErrorType = false;
    public boolean hasWasSuccessful = false;
    public int numAttempts = 0;
    public int responseBodySizeBytes = 0;
    public long serverLatencyMs = 0L;
    public int startConnectionType = 0;
    public int timeoutMs = 0;
    public String url = "";
    public int volleyErrorType = 0;
    public boolean wasSuccessful = false;
    
    public PlayStoreRpcReport()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasUrl) || (!this.url.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.url);
      }
      if ((this.hasClientLatencyMs) || (this.clientLatencyMs != 0L)) {
        i += CodedOutputByteBufferNano.computeUInt64Size(2, this.clientLatencyMs);
      }
      if ((this.hasServerLatencyMs) || (this.serverLatencyMs != 0L)) {
        i += CodedOutputByteBufferNano.computeUInt64Size(3, this.serverLatencyMs);
      }
      if ((this.hasNumAttempts) || (this.numAttempts != 0)) {
        i += CodedOutputByteBufferNano.computeUInt32Size(4, this.numAttempts);
      }
      if ((this.hasTimeoutMs) || (this.timeoutMs != 0)) {
        i += CodedOutputByteBufferNano.computeUInt32Size(5, this.timeoutMs);
      }
      if ((this.hasBackoffMultiplier) || (Float.floatToIntBits(this.backoffMultiplier) != Float.floatToIntBits(0.0F))) {
        i += 4 + CodedOutputByteBufferNano.computeTagSize(6);
      }
      if ((this.hasWasSuccessful) || (this.wasSuccessful)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(7);
      }
      if ((this.startConnectionType != 0) || (this.hasStartConnectionType)) {
        i += CodedOutputByteBufferNano.computeInt32Size(8, this.startConnectionType);
      }
      if ((this.endConnectionType != 0) || (this.hasEndConnectionType)) {
        i += CodedOutputByteBufferNano.computeInt32Size(9, this.endConnectionType);
      }
      if ((this.hasResponseBodySizeBytes) || (this.responseBodySizeBytes != 0)) {
        i += CodedOutputByteBufferNano.computeUInt32Size(10, this.responseBodySizeBytes);
      }
      if ((this.volleyErrorType != 0) || (this.hasVolleyErrorType)) {
        i += CodedOutputByteBufferNano.computeInt32Size(11, this.volleyErrorType);
      }
      if ((this.hasCacheHit) || (this.cacheHit)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(12);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasUrl) || (!this.url.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.url);
      }
      if ((this.hasClientLatencyMs) || (this.clientLatencyMs != 0L)) {
        paramCodedOutputByteBufferNano.writeUInt64(2, this.clientLatencyMs);
      }
      if ((this.hasServerLatencyMs) || (this.serverLatencyMs != 0L)) {
        paramCodedOutputByteBufferNano.writeUInt64(3, this.serverLatencyMs);
      }
      if ((this.hasNumAttempts) || (this.numAttempts != 0)) {
        paramCodedOutputByteBufferNano.writeUInt32(4, this.numAttempts);
      }
      if ((this.hasTimeoutMs) || (this.timeoutMs != 0)) {
        paramCodedOutputByteBufferNano.writeUInt32(5, this.timeoutMs);
      }
      if ((this.hasBackoffMultiplier) || (Float.floatToIntBits(this.backoffMultiplier) != Float.floatToIntBits(0.0F))) {
        paramCodedOutputByteBufferNano.writeFloat(6, this.backoffMultiplier);
      }
      if ((this.hasWasSuccessful) || (this.wasSuccessful)) {
        paramCodedOutputByteBufferNano.writeBool(7, this.wasSuccessful);
      }
      if ((this.startConnectionType != 0) || (this.hasStartConnectionType)) {
        paramCodedOutputByteBufferNano.writeInt32(8, this.startConnectionType);
      }
      if ((this.endConnectionType != 0) || (this.hasEndConnectionType)) {
        paramCodedOutputByteBufferNano.writeInt32(9, this.endConnectionType);
      }
      if ((this.hasResponseBodySizeBytes) || (this.responseBodySizeBytes != 0)) {
        paramCodedOutputByteBufferNano.writeUInt32(10, this.responseBodySizeBytes);
      }
      if ((this.volleyErrorType != 0) || (this.hasVolleyErrorType)) {
        paramCodedOutputByteBufferNano.writeInt32(11, this.volleyErrorType);
      }
      if ((this.hasCacheHit) || (this.cacheHit)) {
        paramCodedOutputByteBufferNano.writeBool(12, this.cacheHit);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class PlayStoreSearchEvent
    extends MessageNano
  {
    public boolean hasQuery;
    public boolean hasQueryUrl;
    public boolean hasReferrerUrl;
    public String query;
    public String queryUrl;
    public String referrerUrl;
    
    public PlayStoreSearchEvent()
    {
      clear();
    }
    
    public final PlayStoreSearchEvent clear()
    {
      this.query = "";
      this.hasQuery = false;
      this.queryUrl = "";
      this.hasQueryUrl = false;
      this.referrerUrl = "";
      this.hasReferrerUrl = false;
      this.cachedSize = -1;
      return this;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasQuery) || (!this.query.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.query);
      }
      if ((this.hasQueryUrl) || (!this.queryUrl.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.queryUrl);
      }
      if ((this.hasReferrerUrl) || (!this.referrerUrl.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.referrerUrl);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasQuery) || (!this.query.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.query);
      }
      if ((this.hasQueryUrl) || (!this.queryUrl.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.queryUrl);
      }
      if ((this.hasReferrerUrl) || (!this.referrerUrl.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.referrerUrl);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class PlayStoreSessionData
    extends MessageNano
  {
    public boolean allowUnknownSources = false;
    public int autoUpdateCleanupDialogNumTimesShown = 0;
    public int contentFilterLevel = 0;
    public int downloadDataDirSizeMb = 0;
    public boolean gaiaPasswordAuthOptedOut = false;
    public boolean globalAutoUpdateEnabled = false;
    public boolean globalAutoUpdateOverWifiOnly = false;
    public boolean hasAllowUnknownSources = false;
    public boolean hasAutoUpdateCleanupDialogNumTimesShown = false;
    public boolean hasContentFilterLevel = false;
    public boolean hasDownloadDataDirSizeMb = false;
    public boolean hasGaiaPasswordAuthOptedOut = false;
    public boolean hasGlobalAutoUpdateEnabled = false;
    public boolean hasGlobalAutoUpdateOverWifiOnly = false;
    public boolean hasNetworkSubType = false;
    public boolean hasNetworkType = false;
    public boolean hasNumAccountsOnDevice = false;
    public boolean hasNumAutoUpdatingInstalledApps = false;
    public boolean hasNumInstalledApps = false;
    public boolean hasNumInstalledAppsNotAutoUpdating = false;
    public boolean hasPurchaseAuthType = false;
    public boolean hasRecommendedMaxDownloadOverMobileBytes = false;
    public int networkSubType = 0;
    public int networkType = 0;
    public int numAccountsOnDevice = 0;
    public int numAutoUpdatingInstalledApps = 0;
    public int numInstalledApps = 0;
    public int numInstalledAppsNotAutoUpdating = 0;
    public PlayStore.PromptForFopData promptForFopData = null;
    public int purchaseAuthType = 0;
    public long recommendedMaxDownloadOverMobileBytes = 0L;
    
    public PlayStoreSessionData()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasGlobalAutoUpdateEnabled) || (this.globalAutoUpdateEnabled)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(1);
      }
      if ((this.hasGlobalAutoUpdateOverWifiOnly) || (this.globalAutoUpdateOverWifiOnly)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(2);
      }
      if ((this.hasAutoUpdateCleanupDialogNumTimesShown) || (this.autoUpdateCleanupDialogNumTimesShown != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(3, this.autoUpdateCleanupDialogNumTimesShown);
      }
      if ((this.hasNetworkType) || (this.networkType != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(4, this.networkType);
      }
      if ((this.hasNetworkSubType) || (this.networkSubType != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(5, this.networkSubType);
      }
      if ((this.hasNumAccountsOnDevice) || (this.numAccountsOnDevice != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(6, this.numAccountsOnDevice);
      }
      if ((this.hasNumInstalledApps) || (this.numInstalledApps != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(7, this.numInstalledApps);
      }
      if ((this.hasNumAutoUpdatingInstalledApps) || (this.numAutoUpdatingInstalledApps != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(8, this.numAutoUpdatingInstalledApps);
      }
      if ((this.hasNumInstalledAppsNotAutoUpdating) || (this.numInstalledAppsNotAutoUpdating != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(9, this.numInstalledAppsNotAutoUpdating);
      }
      if ((this.hasGaiaPasswordAuthOptedOut) || (this.gaiaPasswordAuthOptedOut)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(10);
      }
      if ((this.hasContentFilterLevel) || (this.contentFilterLevel != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(11, this.contentFilterLevel);
      }
      if ((this.hasAllowUnknownSources) || (this.allowUnknownSources)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(12);
      }
      if (this.promptForFopData != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(13, this.promptForFopData);
      }
      if ((this.hasPurchaseAuthType) || (this.purchaseAuthType != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(14, this.purchaseAuthType);
      }
      if ((this.hasDownloadDataDirSizeMb) || (this.downloadDataDirSizeMb != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(15, this.downloadDataDirSizeMb);
      }
      if ((this.hasRecommendedMaxDownloadOverMobileBytes) || (this.recommendedMaxDownloadOverMobileBytes != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(16, this.recommendedMaxDownloadOverMobileBytes);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasGlobalAutoUpdateEnabled) || (this.globalAutoUpdateEnabled)) {
        paramCodedOutputByteBufferNano.writeBool(1, this.globalAutoUpdateEnabled);
      }
      if ((this.hasGlobalAutoUpdateOverWifiOnly) || (this.globalAutoUpdateOverWifiOnly)) {
        paramCodedOutputByteBufferNano.writeBool(2, this.globalAutoUpdateOverWifiOnly);
      }
      if ((this.hasAutoUpdateCleanupDialogNumTimesShown) || (this.autoUpdateCleanupDialogNumTimesShown != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(3, this.autoUpdateCleanupDialogNumTimesShown);
      }
      if ((this.hasNetworkType) || (this.networkType != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(4, this.networkType);
      }
      if ((this.hasNetworkSubType) || (this.networkSubType != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(5, this.networkSubType);
      }
      if ((this.hasNumAccountsOnDevice) || (this.numAccountsOnDevice != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(6, this.numAccountsOnDevice);
      }
      if ((this.hasNumInstalledApps) || (this.numInstalledApps != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(7, this.numInstalledApps);
      }
      if ((this.hasNumAutoUpdatingInstalledApps) || (this.numAutoUpdatingInstalledApps != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(8, this.numAutoUpdatingInstalledApps);
      }
      if ((this.hasNumInstalledAppsNotAutoUpdating) || (this.numInstalledAppsNotAutoUpdating != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(9, this.numInstalledAppsNotAutoUpdating);
      }
      if ((this.hasGaiaPasswordAuthOptedOut) || (this.gaiaPasswordAuthOptedOut)) {
        paramCodedOutputByteBufferNano.writeBool(10, this.gaiaPasswordAuthOptedOut);
      }
      if ((this.hasContentFilterLevel) || (this.contentFilterLevel != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(11, this.contentFilterLevel);
      }
      if ((this.hasAllowUnknownSources) || (this.allowUnknownSources)) {
        paramCodedOutputByteBufferNano.writeBool(12, this.allowUnknownSources);
      }
      if (this.promptForFopData != null) {
        paramCodedOutputByteBufferNano.writeMessage(13, this.promptForFopData);
      }
      if ((this.hasPurchaseAuthType) || (this.purchaseAuthType != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(14, this.purchaseAuthType);
      }
      if ((this.hasDownloadDataDirSizeMb) || (this.downloadDataDirSizeMb != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(15, this.downloadDataDirSizeMb);
      }
      if ((this.hasRecommendedMaxDownloadOverMobileBytes) || (this.recommendedMaxDownloadOverMobileBytes != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(16, this.recommendedMaxDownloadOverMobileBytes);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class PlayStoreSurveyEvent
    extends MessageNano
  {
    public int answerId = 0;
    public boolean hasAnswerId = false;
    public boolean hasSurveyContext = false;
    public boolean hasSurveyId = false;
    public boolean hasType = false;
    public int surveyContext = 0;
    public long surveyId = 0L;
    public int type = 0;
    
    public PlayStoreSurveyEvent()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.type != 0) || (this.hasType)) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.type);
      }
      if ((this.hasSurveyId) || (this.surveyId != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(2, this.surveyId);
      }
      if ((this.hasAnswerId) || (this.answerId != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(3, this.answerId);
      }
      if ((this.hasSurveyContext) || (this.surveyContext != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(4, this.surveyContext);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.type != 0) || (this.hasType)) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.type);
      }
      if ((this.hasSurveyId) || (this.surveyId != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(2, this.surveyId);
      }
      if ((this.hasAnswerId) || (this.answerId != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(3, this.answerId);
      }
      if ((this.hasSurveyContext) || (this.surveyContext != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(4, this.surveyContext);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class PlayStoreUiElement
    extends MessageNano
  {
    private static volatile PlayStoreUiElement[] _emptyArray;
    public PlayStoreUiElement[] child;
    public PlayStore.PlayStoreUiElementInfo clientLogsCookie;
    public boolean hasServerLogsCookie;
    public boolean hasType;
    public byte[] serverLogsCookie;
    public int type;
    
    public PlayStoreUiElement()
    {
      clear();
    }
    
    public static PlayStoreUiElement[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new PlayStoreUiElement[0];
        }
        return _emptyArray;
      }
    }
    
    public final PlayStoreUiElement clear()
    {
      this.type = 0;
      this.hasType = false;
      this.serverLogsCookie = WireFormatNano.EMPTY_BYTES;
      this.hasServerLogsCookie = false;
      this.clientLogsCookie = null;
      this.child = emptyArray();
      this.cachedSize = -1;
      return this;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.type != 0) || (this.hasType)) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.type);
      }
      if ((this.hasServerLogsCookie) || (!Arrays.equals(this.serverLogsCookie, WireFormatNano.EMPTY_BYTES))) {
        i += CodedOutputByteBufferNano.computeBytesSize(2, this.serverLogsCookie);
      }
      if (this.clientLogsCookie != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(3, this.clientLogsCookie);
      }
      if ((this.child != null) && (this.child.length > 0)) {
        for (int j = 0; j < this.child.length; j++)
        {
          PlayStoreUiElement localPlayStoreUiElement = this.child[j];
          if (localPlayStoreUiElement != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(4, localPlayStoreUiElement);
          }
        }
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.type != 0) || (this.hasType)) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.type);
      }
      if ((this.hasServerLogsCookie) || (!Arrays.equals(this.serverLogsCookie, WireFormatNano.EMPTY_BYTES))) {
        paramCodedOutputByteBufferNano.writeBytes(2, this.serverLogsCookie);
      }
      if (this.clientLogsCookie != null) {
        paramCodedOutputByteBufferNano.writeMessage(3, this.clientLogsCookie);
      }
      if ((this.child != null) && (this.child.length > 0)) {
        for (int i = 0; i < this.child.length; i++)
        {
          PlayStoreUiElement localPlayStoreUiElement = this.child[i];
          if (localPlayStoreUiElement != null) {
            paramCodedOutputByteBufferNano.writeMessage(4, localPlayStoreUiElement);
          }
        }
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class PlayStoreUiElementInfo
    extends MessageNano
  {
    public PlayStore.AuthContext authContext = null;
    public String document = "";
    public boolean hasDocument = false;
    public boolean hasHost = false;
    public boolean hasIsImeAction = false;
    public boolean hasOfferType = false;
    public boolean hasSerialDocid = false;
    public String host = "";
    public InstrumentInfo instrumentInfo = null;
    public boolean isImeAction = false;
    public int offerType = 0;
    public String serialDocid = "";
    
    public PlayStoreUiElementInfo()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.instrumentInfo != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.instrumentInfo);
      }
      if ((this.hasSerialDocid) || (!this.serialDocid.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.serialDocid);
      }
      if ((this.hasHost) || (!this.host.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.host);
      }
      if ((this.hasDocument) || (!this.document.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(4, this.document);
      }
      if ((this.hasOfferType) || (this.offerType != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(5, this.offerType);
      }
      if ((this.hasIsImeAction) || (this.isImeAction)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(6);
      }
      if (this.authContext != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(7, this.authContext);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.instrumentInfo != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.instrumentInfo);
      }
      if ((this.hasSerialDocid) || (!this.serialDocid.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.serialDocid);
      }
      if ((this.hasHost) || (!this.host.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.host);
      }
      if ((this.hasDocument) || (!this.document.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(4, this.document);
      }
      if ((this.hasOfferType) || (this.offerType != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(5, this.offerType);
      }
      if ((this.hasIsImeAction) || (this.isImeAction)) {
        paramCodedOutputByteBufferNano.writeBool(6, this.isImeAction);
      }
      if (this.authContext != null) {
        paramCodedOutputByteBufferNano.writeMessage(7, this.authContext);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
    
    public static final class InstrumentInfo
      extends MessageNano
    {
      public boolean hasInstrumentFamily = false;
      public boolean hasIsDefault = false;
      public int instrumentFamily = 0;
      public boolean isDefault = false;
      
      public InstrumentInfo()
      {
        this.cachedSize = -1;
      }
      
      protected final int computeSerializedSize()
      {
        int i = super.computeSerializedSize();
        if ((this.hasInstrumentFamily) || (this.instrumentFamily != 0)) {
          i += CodedOutputByteBufferNano.computeInt32Size(1, this.instrumentFamily);
        }
        if ((this.hasIsDefault) || (this.isDefault)) {
          i += 1 + CodedOutputByteBufferNano.computeTagSize(2);
        }
        return i;
      }
      
      public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
        throws IOException
      {
        if ((this.hasInstrumentFamily) || (this.instrumentFamily != 0)) {
          paramCodedOutputByteBufferNano.writeInt32(1, this.instrumentFamily);
        }
        if ((this.hasIsDefault) || (this.isDefault)) {
          paramCodedOutputByteBufferNano.writeBool(2, this.isDefault);
        }
        super.writeTo(paramCodedOutputByteBufferNano);
      }
    }
  }
  
  public static final class PromptForFopData
    extends MessageNano
  {
    public boolean fopAdded = false;
    public boolean hasFop = false;
    public boolean hasFopAdded = false;
    public boolean hasHasFop = false;
    public boolean hasNumDialogShown = false;
    public boolean hasNumFopSelectorShown = false;
    public boolean hasNumSnooze = false;
    public int numDialogShown = 0;
    public int numFopSelectorShown = 0;
    public int numSnooze = 0;
    
    public PromptForFopData()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasHasFop) || (this.hasFop)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(1);
      }
      if ((this.hasFopAdded) || (this.fopAdded)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(2);
      }
      if ((this.hasNumDialogShown) || (this.numDialogShown != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(3, this.numDialogShown);
      }
      if ((this.hasNumFopSelectorShown) || (this.numFopSelectorShown != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(4, this.numFopSelectorShown);
      }
      if ((this.hasNumSnooze) || (this.numSnooze != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(5, this.numSnooze);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasHasFop) || (this.hasFop)) {
        paramCodedOutputByteBufferNano.writeBool(1, this.hasFop);
      }
      if ((this.hasFopAdded) || (this.fopAdded)) {
        paramCodedOutputByteBufferNano.writeBool(2, this.fopAdded);
      }
      if ((this.hasNumDialogShown) || (this.numDialogShown != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(3, this.numDialogShown);
      }
      if ((this.hasNumFopSelectorShown) || (this.numFopSelectorShown != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(4, this.numFopSelectorShown);
      }
      if ((this.hasNumSnooze) || (this.numSnooze != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(5, this.numSnooze);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class PurchaseData
    extends MessageNano
  {
    public int experienceType = 0;
    public boolean hasExperienceType = false;
    public boolean hasSplitTenderApplied = false;
    public boolean hasTimeSinceDocumentAddedToCacheMs = false;
    public int[] instantPurchaseClientDisabledReasons = WireFormatNano.EMPTY_INT_ARRAY;
    public boolean splitTenderApplied = false;
    public long timeSinceDocumentAddedToCacheMs = 0L;
    
    public PurchaseData()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasTimeSinceDocumentAddedToCacheMs) || (this.timeSinceDocumentAddedToCacheMs != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(1, this.timeSinceDocumentAddedToCacheMs);
      }
      if ((this.experienceType != 0) || (this.hasExperienceType)) {
        i += CodedOutputByteBufferNano.computeInt32Size(2, this.experienceType);
      }
      if ((this.instantPurchaseClientDisabledReasons != null) && (this.instantPurchaseClientDisabledReasons.length > 0))
      {
        int j = 0;
        for (int k = 0; k < this.instantPurchaseClientDisabledReasons.length; k++) {
          j += CodedOutputByteBufferNano.computeInt32SizeNoTag(this.instantPurchaseClientDisabledReasons[k]);
        }
        i = i + j + 1 * this.instantPurchaseClientDisabledReasons.length;
      }
      if ((this.hasSplitTenderApplied) || (this.splitTenderApplied)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(4);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasTimeSinceDocumentAddedToCacheMs) || (this.timeSinceDocumentAddedToCacheMs != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(1, this.timeSinceDocumentAddedToCacheMs);
      }
      if ((this.experienceType != 0) || (this.hasExperienceType)) {
        paramCodedOutputByteBufferNano.writeInt32(2, this.experienceType);
      }
      if ((this.instantPurchaseClientDisabledReasons != null) && (this.instantPurchaseClientDisabledReasons.length > 0)) {
        for (int i = 0; i < this.instantPurchaseClientDisabledReasons.length; i++) {
          paramCodedOutputByteBufferNano.writeInt32(3, this.instantPurchaseClientDisabledReasons[i]);
        }
      }
      if ((this.hasSplitTenderApplied) || (this.splitTenderApplied)) {
        paramCodedOutputByteBufferNano.writeBool(4, this.splitTenderApplied);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class ReviewData
    extends MessageNano
  {
    public boolean containsText = false;
    public boolean hasContainsText = false;
    public boolean hasRating = false;
    public boolean hasReviewSource = false;
    public boolean hasTextLength = false;
    public int rating = 0;
    public int reviewSource = 0;
    public int textLength = 0;
    
    public ReviewData()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.reviewSource != 0) || (this.hasReviewSource)) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.reviewSource);
      }
      if ((this.hasRating) || (this.rating != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(2, this.rating);
      }
      if ((this.hasContainsText) || (this.containsText)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(3);
      }
      if ((this.hasTextLength) || (this.textLength != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(4, this.textLength);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.reviewSource != 0) || (this.hasReviewSource)) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.reviewSource);
      }
      if ((this.hasRating) || (this.rating != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(2, this.rating);
      }
      if ((this.hasContainsText) || (this.containsText)) {
        paramCodedOutputByteBufferNano.writeBool(3, this.containsText);
      }
      if ((this.hasTextLength) || (this.textLength != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(4, this.textLength);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class SearchSuggestionReport
    extends MessageNano
  {
    public long clientLatencyMs;
    public boolean hasClientLatencyMs;
    public boolean hasQuery;
    public boolean hasResponseServerLogsCookie;
    public boolean hasResultCount;
    public boolean hasSource;
    public boolean hasSuggestedQuery;
    public boolean hasSuggestionServerLogsCookie;
    public String query;
    public byte[] responseServerLogsCookie;
    public int resultCount;
    public int source;
    public String suggestedQuery;
    public byte[] suggestionServerLogsCookie;
    
    public SearchSuggestionReport()
    {
      clear();
    }
    
    public final SearchSuggestionReport clear()
    {
      this.query = "";
      this.hasQuery = false;
      this.suggestedQuery = "";
      this.hasSuggestedQuery = false;
      this.clientLatencyMs = 0L;
      this.hasClientLatencyMs = false;
      this.source = 0;
      this.hasSource = false;
      this.resultCount = 0;
      this.hasResultCount = false;
      this.responseServerLogsCookie = WireFormatNano.EMPTY_BYTES;
      this.hasResponseServerLogsCookie = false;
      this.suggestionServerLogsCookie = WireFormatNano.EMPTY_BYTES;
      this.hasSuggestionServerLogsCookie = false;
      this.cachedSize = -1;
      return this;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasQuery) || (!this.query.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.query);
      }
      if ((this.hasSuggestedQuery) || (!this.suggestedQuery.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.suggestedQuery);
      }
      if ((this.hasClientLatencyMs) || (this.clientLatencyMs != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(3, this.clientLatencyMs);
      }
      if ((this.source != 0) || (this.hasSource)) {
        i += CodedOutputByteBufferNano.computeInt32Size(4, this.source);
      }
      if ((this.hasResultCount) || (this.resultCount != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(5, this.resultCount);
      }
      if ((this.hasResponseServerLogsCookie) || (!Arrays.equals(this.responseServerLogsCookie, WireFormatNano.EMPTY_BYTES))) {
        i += CodedOutputByteBufferNano.computeBytesSize(6, this.responseServerLogsCookie);
      }
      if ((this.hasSuggestionServerLogsCookie) || (!Arrays.equals(this.suggestionServerLogsCookie, WireFormatNano.EMPTY_BYTES))) {
        i += CodedOutputByteBufferNano.computeBytesSize(7, this.suggestionServerLogsCookie);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasQuery) || (!this.query.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.query);
      }
      if ((this.hasSuggestedQuery) || (!this.suggestedQuery.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.suggestedQuery);
      }
      if ((this.hasClientLatencyMs) || (this.clientLatencyMs != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(3, this.clientLatencyMs);
      }
      if ((this.source != 0) || (this.hasSource)) {
        paramCodedOutputByteBufferNano.writeInt32(4, this.source);
      }
      if ((this.hasResultCount) || (this.resultCount != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(5, this.resultCount);
      }
      if ((this.hasResponseServerLogsCookie) || (!Arrays.equals(this.responseServerLogsCookie, WireFormatNano.EMPTY_BYTES))) {
        paramCodedOutputByteBufferNano.writeBytes(6, this.responseServerLogsCookie);
      }
      if ((this.hasSuggestionServerLogsCookie) || (!Arrays.equals(this.suggestionServerLogsCookie, WireFormatNano.EMPTY_BYTES))) {
        paramCodedOutputByteBufferNano.writeBytes(7, this.suggestionServerLogsCookie);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class WearInfo
    extends MessageNano
  {
    public String androidId = "";
    public boolean hasAndroidId = false;
    
    public WearInfo()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasAndroidId) || (!this.androidId.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.androidId);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasAndroidId) || (!this.androidId.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.androidId);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class WebViewPageLoadEvent
    extends MessageNano
  {
    public long endTimestampMs = 0L;
    public int errorCode = 0;
    public String errorDescription = "";
    public boolean hasEndTimestampMs = false;
    public boolean hasErrorCode = false;
    public boolean hasErrorDescription = false;
    public boolean hasOrientation = false;
    public boolean hasScreenHeightPx = false;
    public boolean hasScreenWidthPx = false;
    public boolean hasScreenXDpi = false;
    public boolean hasScreenYDpi = false;
    public boolean hasStartTimestampMs = false;
    public boolean hasUrl = false;
    public int orientation = 0;
    public int screenHeightPx = 0;
    public int screenWidthPx = 0;
    public float screenXDpi = 0.0F;
    public float screenYDpi = 0.0F;
    public long startTimestampMs = 0L;
    public String url = "";
    
    public WebViewPageLoadEvent()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasUrl) || (!this.url.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.url);
      }
      if ((this.hasStartTimestampMs) || (this.startTimestampMs != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(3, this.startTimestampMs);
      }
      if ((this.hasEndTimestampMs) || (this.endTimestampMs != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(4, this.endTimestampMs);
      }
      if ((this.hasErrorCode) || (this.errorCode != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(5, this.errorCode);
      }
      if ((this.hasErrorDescription) || (!this.errorDescription.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(6, this.errorDescription);
      }
      if ((this.hasOrientation) || (this.orientation != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(7, this.orientation);
      }
      if ((this.hasScreenWidthPx) || (this.screenWidthPx != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(8, this.screenWidthPx);
      }
      if ((this.hasScreenHeightPx) || (this.screenHeightPx != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(9, this.screenHeightPx);
      }
      if ((this.hasScreenXDpi) || (Float.floatToIntBits(this.screenXDpi) != Float.floatToIntBits(0.0F))) {
        i += 4 + CodedOutputByteBufferNano.computeTagSize(10);
      }
      if ((this.hasScreenYDpi) || (Float.floatToIntBits(this.screenYDpi) != Float.floatToIntBits(0.0F))) {
        i += 4 + CodedOutputByteBufferNano.computeTagSize(11);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasUrl) || (!this.url.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.url);
      }
      if ((this.hasStartTimestampMs) || (this.startTimestampMs != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(3, this.startTimestampMs);
      }
      if ((this.hasEndTimestampMs) || (this.endTimestampMs != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(4, this.endTimestampMs);
      }
      if ((this.hasErrorCode) || (this.errorCode != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(5, this.errorCode);
      }
      if ((this.hasErrorDescription) || (!this.errorDescription.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(6, this.errorDescription);
      }
      if ((this.hasOrientation) || (this.orientation != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(7, this.orientation);
      }
      if ((this.hasScreenWidthPx) || (this.screenWidthPx != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(8, this.screenWidthPx);
      }
      if ((this.hasScreenHeightPx) || (this.screenHeightPx != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(9, this.screenHeightPx);
      }
      if ((this.hasScreenXDpi) || (Float.floatToIntBits(this.screenXDpi) != Float.floatToIntBits(0.0F))) {
        paramCodedOutputByteBufferNano.writeFloat(10, this.screenXDpi);
      }
      if ((this.hasScreenYDpi) || (Float.floatToIntBits(this.screenYDpi) != Float.floatToIntBits(0.0F))) {
        paramCodedOutputByteBufferNano.writeFloat(11, this.screenYDpi);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class WidgetEventData
    extends MessageNano
  {
    public int backendId = 0;
    public int classId = 0;
    public boolean hasBackendId = false;
    public boolean hasClassId = false;
    public boolean hasIntentActionId = false;
    public boolean hasMaxHeight = false;
    public boolean hasMaxWidth = false;
    public boolean hasMinHeight = false;
    public boolean hasMinWidth = false;
    public boolean hasNumWidgets = false;
    public int intentActionId = 0;
    public int maxHeight = 0;
    public int maxWidth = 0;
    public int minHeight = 0;
    public int minWidth = 0;
    public int numWidgets = 0;
    
    public WidgetEventData()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.classId != 0) || (this.hasClassId)) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.classId);
      }
      if ((this.intentActionId != 0) || (this.hasIntentActionId)) {
        i += CodedOutputByteBufferNano.computeInt32Size(2, this.intentActionId);
      }
      if ((this.hasNumWidgets) || (this.numWidgets != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(3, this.numWidgets);
      }
      if ((this.hasBackendId) || (this.backendId != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(4, this.backendId);
      }
      if ((this.hasMinWidth) || (this.minWidth != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(5, this.minWidth);
      }
      if ((this.hasMinHeight) || (this.minHeight != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(6, this.minHeight);
      }
      if ((this.hasMaxWidth) || (this.maxWidth != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(7, this.maxWidth);
      }
      if ((this.hasMaxHeight) || (this.maxHeight != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(8, this.maxHeight);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.classId != 0) || (this.hasClassId)) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.classId);
      }
      if ((this.intentActionId != 0) || (this.hasIntentActionId)) {
        paramCodedOutputByteBufferNano.writeInt32(2, this.intentActionId);
      }
      if ((this.hasNumWidgets) || (this.numWidgets != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(3, this.numWidgets);
      }
      if ((this.hasBackendId) || (this.backendId != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(4, this.backendId);
      }
      if ((this.hasMinWidth) || (this.minWidth != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(5, this.minWidth);
      }
      if ((this.hasMinHeight) || (this.minHeight != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(6, this.minHeight);
      }
      if ((this.hasMaxWidth) || (this.maxWidth != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(7, this.maxWidth);
      }
      if ((this.hasMaxHeight) || (this.maxHeight != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(8, this.maxHeight);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.analytics.PlayStore
 * JD-Core Version:    0.7.0.1
 */