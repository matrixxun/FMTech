package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;
import java.util.Arrays;

public abstract interface VendingProtos
{
  public static final class AckNotificationsRequestProto
    extends MessageNano
  {
    public String[] nackNotificationId = WireFormatNano.EMPTY_STRING_ARRAY;
    public String[] notificationId = WireFormatNano.EMPTY_STRING_ARRAY;
    public VendingProtos.SignatureHashProto signatureHash = null;
    
    public AckNotificationsRequestProto()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.notificationId != null) && (this.notificationId.length > 0))
      {
        int n = 0;
        int i1 = 0;
        for (int i2 = 0; i2 < this.notificationId.length; i2++)
        {
          String str2 = this.notificationId[i2];
          if (str2 != null)
          {
            n++;
            i1 += CodedOutputByteBufferNano.computeStringSizeNoTag(str2);
          }
        }
        i = i + i1 + n * 1;
      }
      if (this.signatureHash != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.signatureHash);
      }
      if ((this.nackNotificationId != null) && (this.nackNotificationId.length > 0))
      {
        int j = 0;
        int k = 0;
        for (int m = 0; m < this.nackNotificationId.length; m++)
        {
          String str1 = this.nackNotificationId[m];
          if (str1 != null)
          {
            j++;
            k += CodedOutputByteBufferNano.computeStringSizeNoTag(str1);
          }
        }
        i = i + k + j * 1;
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.notificationId != null) && (this.notificationId.length > 0)) {
        for (int j = 0; j < this.notificationId.length; j++)
        {
          String str2 = this.notificationId[j];
          if (str2 != null) {
            paramCodedOutputByteBufferNano.writeString(1, str2);
          }
        }
      }
      if (this.signatureHash != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.signatureHash);
      }
      if ((this.nackNotificationId != null) && (this.nackNotificationId.length > 0)) {
        for (int i = 0; i < this.nackNotificationId.length; i++)
        {
          String str1 = this.nackNotificationId[i];
          if (str1 != null) {
            paramCodedOutputByteBufferNano.writeString(3, str1);
          }
        }
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class AckNotificationsResponseProto
    extends MessageNano
  {
    public AckNotificationsResponseProto()
    {
      this.cachedSize = -1;
    }
  }
  
  public static final class AppDataProto
    extends MessageNano
  {
    private static volatile AppDataProto[] _emptyArray;
    public boolean hasKey = false;
    public boolean hasValue = false;
    public String key = "";
    public String value = "";
    
    public AppDataProto()
    {
      this.cachedSize = -1;
    }
    
    public static AppDataProto[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new AppDataProto[0];
        }
        return _emptyArray;
      }
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
  
  public static final class BillingEventRequestProto
    extends MessageNano
  {
    public String billingParametersId = "";
    public String clientMessage = "";
    public int eventType = 0;
    public boolean hasBillingParametersId = false;
    public boolean hasClientMessage = false;
    public boolean hasEventType = false;
    public boolean hasResultSuccess = false;
    public boolean resultSuccess = false;
    
    public BillingEventRequestProto()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.eventType != 0) || (this.hasEventType)) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.eventType);
      }
      if ((this.hasBillingParametersId) || (!this.billingParametersId.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.billingParametersId);
      }
      if ((this.hasResultSuccess) || (this.resultSuccess)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(3);
      }
      if ((this.hasClientMessage) || (!this.clientMessage.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(4, this.clientMessage);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.eventType != 0) || (this.hasEventType)) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.eventType);
      }
      if ((this.hasBillingParametersId) || (!this.billingParametersId.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.billingParametersId);
      }
      if ((this.hasResultSuccess) || (this.resultSuccess)) {
        paramCodedOutputByteBufferNano.writeBool(3, this.resultSuccess);
      }
      if ((this.hasClientMessage) || (!this.clientMessage.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(4, this.clientMessage);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class BillingEventResponseProto
    extends MessageNano
  {
    public BillingEventResponseProto()
    {
      this.cachedSize = -1;
    }
  }
  
  public static final class CheckForNotificationsRequestProto
    extends MessageNano
  {
    public long alarmDuration = 0L;
    public boolean hasAlarmDuration = false;
    
    public CheckForNotificationsRequestProto()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasAlarmDuration) || (this.alarmDuration != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(1, this.alarmDuration);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasAlarmDuration) || (this.alarmDuration != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(1, this.alarmDuration);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class CheckLicenseRequestProto
    extends MessageNano
  {
    public boolean hasNonce = false;
    public boolean hasPackageName = false;
    public boolean hasVersionCode = false;
    public long nonce = 0L;
    public String packageName = "";
    public int versionCode = 0;
    
    public CheckLicenseRequestProto()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasPackageName) || (!this.packageName.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.packageName);
      }
      if ((this.hasVersionCode) || (this.versionCode != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(2, this.versionCode);
      }
      if ((this.hasNonce) || (this.nonce != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(3, this.nonce);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasPackageName) || (!this.packageName.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.packageName);
      }
      if ((this.hasVersionCode) || (this.versionCode != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(2, this.versionCode);
      }
      if ((this.hasNonce) || (this.nonce != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(3, this.nonce);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class CheckLicenseResponseProto
    extends MessageNano
  {
    public boolean hasResponseCode = false;
    public boolean hasSignature = false;
    public boolean hasSignedData = false;
    public int responseCode = 0;
    public String signature = "";
    public String signedData = "";
    
    public CheckLicenseResponseProto()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasResponseCode) || (this.responseCode != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.responseCode);
      }
      if ((this.hasSignedData) || (!this.signedData.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.signedData);
      }
      if ((this.hasSignature) || (!this.signature.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.signature);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasResponseCode) || (this.responseCode != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.responseCode);
      }
      if ((this.hasSignedData) || (!this.signedData.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.signedData);
      }
      if ((this.hasSignature) || (!this.signature.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.signature);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class ContentSyncRequestProto
    extends MessageNano
  {
    public AssetInstallState[] assetInstallState = AssetInstallState.emptyArray();
    public boolean hasIncremental = false;
    public boolean hasSideloadedAppCount = false;
    public boolean incremental = false;
    public int sideloadedAppCount = 0;
    public SystemApp[] systemApp = SystemApp.emptyArray();
    
    public ContentSyncRequestProto()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasIncremental) || (this.incremental)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(1);
      }
      if ((this.assetInstallState != null) && (this.assetInstallState.length > 0)) {
        for (int k = 0; k < this.assetInstallState.length; k++)
        {
          AssetInstallState localAssetInstallState = this.assetInstallState[k];
          if (localAssetInstallState != null) {
            i += CodedOutputByteBufferNano.computeGroupSize(2, localAssetInstallState);
          }
        }
      }
      if ((this.systemApp != null) && (this.systemApp.length > 0)) {
        for (int j = 0; j < this.systemApp.length; j++)
        {
          SystemApp localSystemApp = this.systemApp[j];
          if (localSystemApp != null) {
            i += CodedOutputByteBufferNano.computeGroupSize(10, localSystemApp);
          }
        }
      }
      if ((this.hasSideloadedAppCount) || (this.sideloadedAppCount != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(14, this.sideloadedAppCount);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasIncremental) || (this.incremental)) {
        paramCodedOutputByteBufferNano.writeBool(1, this.incremental);
      }
      if ((this.assetInstallState != null) && (this.assetInstallState.length > 0)) {
        for (int j = 0; j < this.assetInstallState.length; j++)
        {
          AssetInstallState localAssetInstallState = this.assetInstallState[j];
          if (localAssetInstallState != null) {
            paramCodedOutputByteBufferNano.writeGroup(2, localAssetInstallState);
          }
        }
      }
      if ((this.systemApp != null) && (this.systemApp.length > 0)) {
        for (int i = 0; i < this.systemApp.length; i++)
        {
          SystemApp localSystemApp = this.systemApp[i];
          if (localSystemApp != null) {
            paramCodedOutputByteBufferNano.writeGroup(10, localSystemApp);
          }
        }
      }
      if ((this.hasSideloadedAppCount) || (this.sideloadedAppCount != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(14, this.sideloadedAppCount);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
    
    public static final class AssetInstallState
      extends MessageNano
    {
      private static volatile AssetInstallState[] _emptyArray;
      public String assetId = "";
      public String assetReferrer = "";
      public int assetState = 1;
      public boolean hasAssetId = false;
      public boolean hasAssetReferrer = false;
      public boolean hasAssetState = false;
      public boolean hasInstallTime = false;
      public boolean hasPackageName = false;
      public boolean hasUninstallTime = false;
      public boolean hasVersionCode = false;
      public long installTime = 0L;
      public String packageName = "";
      public long uninstallTime = 0L;
      public int versionCode = 0;
      
      public AssetInstallState()
      {
        this.cachedSize = -1;
      }
      
      public static AssetInstallState[] emptyArray()
      {
        if (_emptyArray == null) {}
        synchronized (InternalNano.LAZY_INIT_LOCK)
        {
          if (_emptyArray == null) {
            _emptyArray = new AssetInstallState[0];
          }
          return _emptyArray;
        }
      }
      
      protected final int computeSerializedSize()
      {
        int i = super.computeSerializedSize();
        if ((this.hasAssetId) || (!this.assetId.equals(""))) {
          i += CodedOutputByteBufferNano.computeStringSize(3, this.assetId);
        }
        if ((this.assetState != 1) || (this.hasAssetState)) {
          i += CodedOutputByteBufferNano.computeInt32Size(4, this.assetState);
        }
        if ((this.hasInstallTime) || (this.installTime != 0L)) {
          i += CodedOutputByteBufferNano.computeInt64Size(5, this.installTime);
        }
        if ((this.hasUninstallTime) || (this.uninstallTime != 0L)) {
          i += CodedOutputByteBufferNano.computeInt64Size(6, this.uninstallTime);
        }
        if ((this.hasPackageName) || (!this.packageName.equals(""))) {
          i += CodedOutputByteBufferNano.computeStringSize(7, this.packageName);
        }
        if ((this.hasVersionCode) || (this.versionCode != 0)) {
          i += CodedOutputByteBufferNano.computeInt32Size(8, this.versionCode);
        }
        if ((this.hasAssetReferrer) || (!this.assetReferrer.equals(""))) {
          i += CodedOutputByteBufferNano.computeStringSize(9, this.assetReferrer);
        }
        return i;
      }
      
      public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
        throws IOException
      {
        if ((this.hasAssetId) || (!this.assetId.equals(""))) {
          paramCodedOutputByteBufferNano.writeString(3, this.assetId);
        }
        if ((this.assetState != 1) || (this.hasAssetState)) {
          paramCodedOutputByteBufferNano.writeInt32(4, this.assetState);
        }
        if ((this.hasInstallTime) || (this.installTime != 0L)) {
          paramCodedOutputByteBufferNano.writeInt64(5, this.installTime);
        }
        if ((this.hasUninstallTime) || (this.uninstallTime != 0L)) {
          paramCodedOutputByteBufferNano.writeInt64(6, this.uninstallTime);
        }
        if ((this.hasPackageName) || (!this.packageName.equals(""))) {
          paramCodedOutputByteBufferNano.writeString(7, this.packageName);
        }
        if ((this.hasVersionCode) || (this.versionCode != 0)) {
          paramCodedOutputByteBufferNano.writeInt32(8, this.versionCode);
        }
        if ((this.hasAssetReferrer) || (!this.assetReferrer.equals(""))) {
          paramCodedOutputByteBufferNano.writeString(9, this.assetReferrer);
        }
        super.writeTo(paramCodedOutputByteBufferNano);
      }
    }
    
    public static final class SystemApp
      extends MessageNano
    {
      private static volatile SystemApp[] _emptyArray;
      public String[] certificateHash = WireFormatNano.EMPTY_STRING_ARRAY;
      public boolean hasPackageName = false;
      public boolean hasVersionCode = false;
      public String packageName = "";
      public int versionCode = 0;
      
      public SystemApp()
      {
        this.cachedSize = -1;
      }
      
      public static SystemApp[] emptyArray()
      {
        if (_emptyArray == null) {}
        synchronized (InternalNano.LAZY_INIT_LOCK)
        {
          if (_emptyArray == null) {
            _emptyArray = new SystemApp[0];
          }
          return _emptyArray;
        }
      }
      
      protected final int computeSerializedSize()
      {
        int i = super.computeSerializedSize();
        if ((this.hasPackageName) || (!this.packageName.equals(""))) {
          i += CodedOutputByteBufferNano.computeStringSize(11, this.packageName);
        }
        if ((this.hasVersionCode) || (this.versionCode != 0)) {
          i += CodedOutputByteBufferNano.computeInt32Size(12, this.versionCode);
        }
        if ((this.certificateHash != null) && (this.certificateHash.length > 0))
        {
          int j = 0;
          int k = 0;
          for (int m = 0; m < this.certificateHash.length; m++)
          {
            String str = this.certificateHash[m];
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
        if ((this.hasPackageName) || (!this.packageName.equals(""))) {
          paramCodedOutputByteBufferNano.writeString(11, this.packageName);
        }
        if ((this.hasVersionCode) || (this.versionCode != 0)) {
          paramCodedOutputByteBufferNano.writeInt32(12, this.versionCode);
        }
        if ((this.certificateHash != null) && (this.certificateHash.length > 0)) {
          for (int i = 0; i < this.certificateHash.length; i++)
          {
            String str = this.certificateHash[i];
            if (str != null) {
              paramCodedOutputByteBufferNano.writeString(13, str);
            }
          }
        }
        super.writeTo(paramCodedOutputByteBufferNano);
      }
    }
  }
  
  public static final class ContentSyncResponseProto
    extends MessageNano
  {
    public boolean hasNumUpdatesAvailable = false;
    public int numUpdatesAvailable = 0;
    
    public ContentSyncResponseProto()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasNumUpdatesAvailable) || (this.numUpdatesAvailable != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.numUpdatesAvailable);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasNumUpdatesAvailable) || (this.numUpdatesAvailable != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.numUpdatesAvailable);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class DataMessageProto
    extends MessageNano
  {
    private static volatile DataMessageProto[] _emptyArray;
    public VendingProtos.AppDataProto[] appData = VendingProtos.AppDataProto.emptyArray();
    public String category = "";
    public boolean hasCategory = false;
    
    public DataMessageProto()
    {
      this.cachedSize = -1;
    }
    
    public static DataMessageProto[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new DataMessageProto[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasCategory) || (!this.category.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.category);
      }
      if ((this.appData != null) && (this.appData.length > 0)) {
        for (int j = 0; j < this.appData.length; j++)
        {
          VendingProtos.AppDataProto localAppDataProto = this.appData[j];
          if (localAppDataProto != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(3, localAppDataProto);
          }
        }
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasCategory) || (!this.category.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.category);
      }
      if ((this.appData != null) && (this.appData.length > 0)) {
        for (int i = 0; i < this.appData.length; i++)
        {
          VendingProtos.AppDataProto localAppDataProto = this.appData[i];
          if (localAppDataProto != null) {
            paramCodedOutputByteBufferNano.writeMessage(3, localAppDataProto);
          }
        }
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class FileMetadataProto
    extends MessageNano
  {
    private static volatile FileMetadataProto[] _emptyArray;
    public String downloadUrl = "";
    public int fileType = 0;
    public boolean hasDownloadUrl = false;
    public boolean hasFileType = false;
    public boolean hasSize = false;
    public boolean hasVersionCode = false;
    public long size = 0L;
    public int versionCode = 0;
    
    public FileMetadataProto()
    {
      this.cachedSize = -1;
    }
    
    public static FileMetadataProto[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new FileMetadataProto[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.fileType != 0) || (this.hasFileType)) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.fileType);
      }
      if ((this.hasVersionCode) || (this.versionCode != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(2, this.versionCode);
      }
      if ((this.hasSize) || (this.size != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(3, this.size);
      }
      if ((this.hasDownloadUrl) || (!this.downloadUrl.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(4, this.downloadUrl);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.fileType != 0) || (this.hasFileType)) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.fileType);
      }
      if ((this.hasVersionCode) || (this.versionCode != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(2, this.versionCode);
      }
      if ((this.hasSize) || (this.size != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(3, this.size);
      }
      if ((this.hasDownloadUrl) || (!this.downloadUrl.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(4, this.downloadUrl);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class GetAssetResponseProto
    extends MessageNano
  {
    private static volatile GetAssetResponseProto[] _emptyArray;
    public VendingProtos.FileMetadataProto[] additionalFile = VendingProtos.FileMetadataProto.emptyArray();
    public InstallAsset installAsset = null;
    
    public GetAssetResponseProto()
    {
      this.cachedSize = -1;
    }
    
    public static GetAssetResponseProto[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new GetAssetResponseProto[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.installAsset != null) {
        i += CodedOutputByteBufferNano.computeGroupSize(1, this.installAsset);
      }
      if ((this.additionalFile != null) && (this.additionalFile.length > 0)) {
        for (int j = 0; j < this.additionalFile.length; j++)
        {
          VendingProtos.FileMetadataProto localFileMetadataProto = this.additionalFile[j];
          if (localFileMetadataProto != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(15, localFileMetadataProto);
          }
        }
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.installAsset != null) {
        paramCodedOutputByteBufferNano.writeGroup(1, this.installAsset);
      }
      if ((this.additionalFile != null) && (this.additionalFile.length > 0)) {
        for (int i = 0; i < this.additionalFile.length; i++)
        {
          VendingProtos.FileMetadataProto localFileMetadataProto = this.additionalFile[i];
          if (localFileMetadataProto != null) {
            paramCodedOutputByteBufferNano.writeMessage(15, localFileMetadataProto);
          }
        }
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
    
    public static final class InstallAsset
      extends MessageNano
    {
      public String assetId = "";
      public String assetName = "";
      public String assetPackage = "";
      public String assetSignature = "";
      public long assetSize = 0L;
      public String assetType = "";
      public String blobUrl = "";
      public String downloadAuthCookieName = "";
      public String downloadAuthCookieValue = "";
      public boolean forwardLocked = false;
      public boolean hasAssetId = false;
      public boolean hasAssetName = false;
      public boolean hasAssetPackage = false;
      public boolean hasAssetSignature = false;
      public boolean hasAssetSize = false;
      public boolean hasAssetType = false;
      public boolean hasBlobUrl = false;
      public boolean hasDownloadAuthCookieName = false;
      public boolean hasDownloadAuthCookieValue = false;
      public boolean hasForwardLocked = false;
      public boolean hasPostInstallRefundWindowMillis = false;
      public boolean hasRefundTimeoutMillis = false;
      public boolean hasSecured = false;
      public boolean hasVersionCode = false;
      public long postInstallRefundWindowMillis = 0L;
      public long refundTimeoutMillis = 0L;
      public boolean secured = false;
      public int versionCode = 0;
      
      public InstallAsset()
      {
        this.cachedSize = -1;
      }
      
      protected final int computeSerializedSize()
      {
        int i = super.computeSerializedSize();
        if ((this.hasAssetId) || (!this.assetId.equals(""))) {
          i += CodedOutputByteBufferNano.computeStringSize(2, this.assetId);
        }
        if ((this.hasAssetName) || (!this.assetName.equals(""))) {
          i += CodedOutputByteBufferNano.computeStringSize(3, this.assetName);
        }
        if ((this.hasAssetType) || (!this.assetType.equals(""))) {
          i += CodedOutputByteBufferNano.computeStringSize(4, this.assetType);
        }
        if ((this.hasAssetPackage) || (!this.assetPackage.equals(""))) {
          i += CodedOutputByteBufferNano.computeStringSize(5, this.assetPackage);
        }
        if ((this.hasBlobUrl) || (!this.blobUrl.equals(""))) {
          i += CodedOutputByteBufferNano.computeStringSize(6, this.blobUrl);
        }
        if ((this.hasAssetSignature) || (!this.assetSignature.equals(""))) {
          i += CodedOutputByteBufferNano.computeStringSize(7, this.assetSignature);
        }
        if ((this.hasAssetSize) || (this.assetSize != 0L)) {
          i += CodedOutputByteBufferNano.computeInt64Size(8, this.assetSize);
        }
        if ((this.hasRefundTimeoutMillis) || (this.refundTimeoutMillis != 0L)) {
          i += CodedOutputByteBufferNano.computeInt64Size(9, this.refundTimeoutMillis);
        }
        if ((this.hasForwardLocked) || (this.forwardLocked)) {
          i += 1 + CodedOutputByteBufferNano.computeTagSize(10);
        }
        if ((this.hasSecured) || (this.secured)) {
          i += 1 + CodedOutputByteBufferNano.computeTagSize(11);
        }
        if ((this.hasVersionCode) || (this.versionCode != 0)) {
          i += CodedOutputByteBufferNano.computeInt32Size(12, this.versionCode);
        }
        if ((this.hasDownloadAuthCookieName) || (!this.downloadAuthCookieName.equals(""))) {
          i += CodedOutputByteBufferNano.computeStringSize(13, this.downloadAuthCookieName);
        }
        if ((this.hasDownloadAuthCookieValue) || (!this.downloadAuthCookieValue.equals(""))) {
          i += CodedOutputByteBufferNano.computeStringSize(14, this.downloadAuthCookieValue);
        }
        if ((this.hasPostInstallRefundWindowMillis) || (this.postInstallRefundWindowMillis != 0L)) {
          i += CodedOutputByteBufferNano.computeInt64Size(16, this.postInstallRefundWindowMillis);
        }
        return i;
      }
      
      public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
        throws IOException
      {
        if ((this.hasAssetId) || (!this.assetId.equals(""))) {
          paramCodedOutputByteBufferNano.writeString(2, this.assetId);
        }
        if ((this.hasAssetName) || (!this.assetName.equals(""))) {
          paramCodedOutputByteBufferNano.writeString(3, this.assetName);
        }
        if ((this.hasAssetType) || (!this.assetType.equals(""))) {
          paramCodedOutputByteBufferNano.writeString(4, this.assetType);
        }
        if ((this.hasAssetPackage) || (!this.assetPackage.equals(""))) {
          paramCodedOutputByteBufferNano.writeString(5, this.assetPackage);
        }
        if ((this.hasBlobUrl) || (!this.blobUrl.equals(""))) {
          paramCodedOutputByteBufferNano.writeString(6, this.blobUrl);
        }
        if ((this.hasAssetSignature) || (!this.assetSignature.equals(""))) {
          paramCodedOutputByteBufferNano.writeString(7, this.assetSignature);
        }
        if ((this.hasAssetSize) || (this.assetSize != 0L)) {
          paramCodedOutputByteBufferNano.writeInt64(8, this.assetSize);
        }
        if ((this.hasRefundTimeoutMillis) || (this.refundTimeoutMillis != 0L)) {
          paramCodedOutputByteBufferNano.writeInt64(9, this.refundTimeoutMillis);
        }
        if ((this.hasForwardLocked) || (this.forwardLocked)) {
          paramCodedOutputByteBufferNano.writeBool(10, this.forwardLocked);
        }
        if ((this.hasSecured) || (this.secured)) {
          paramCodedOutputByteBufferNano.writeBool(11, this.secured);
        }
        if ((this.hasVersionCode) || (this.versionCode != 0)) {
          paramCodedOutputByteBufferNano.writeInt32(12, this.versionCode);
        }
        if ((this.hasDownloadAuthCookieName) || (!this.downloadAuthCookieName.equals(""))) {
          paramCodedOutputByteBufferNano.writeString(13, this.downloadAuthCookieName);
        }
        if ((this.hasDownloadAuthCookieValue) || (!this.downloadAuthCookieValue.equals(""))) {
          paramCodedOutputByteBufferNano.writeString(14, this.downloadAuthCookieValue);
        }
        if ((this.hasPostInstallRefundWindowMillis) || (this.postInstallRefundWindowMillis != 0L)) {
          paramCodedOutputByteBufferNano.writeInt64(16, this.postInstallRefundWindowMillis);
        }
        super.writeTo(paramCodedOutputByteBufferNano);
      }
    }
  }
  
  public static final class GetMarketMetadataResponseProto
    extends MessageNano
  {
    public boolean hasLatestClientVersionCode = false;
    public int latestClientVersionCode = 0;
    
    public GetMarketMetadataResponseProto()
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
  
  public static final class InAppPurchaseInformationRequestProto
    extends MessageNano
  {
    public int billingApiVersion = 0;
    public boolean hasBillingApiVersion = false;
    public boolean hasNonce = false;
    public boolean hasSignatureAlgorithm = false;
    public long nonce = 0L;
    public String[] notificationId = WireFormatNano.EMPTY_STRING_ARRAY;
    public String signatureAlgorithm = "";
    public VendingProtos.SignatureHashProto signatureHash = null;
    
    public InAppPurchaseInformationRequestProto()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.signatureHash != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.signatureHash);
      }
      if ((this.hasNonce) || (this.nonce != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(2, this.nonce);
      }
      if ((this.notificationId != null) && (this.notificationId.length > 0))
      {
        int j = 0;
        int k = 0;
        for (int m = 0; m < this.notificationId.length; m++)
        {
          String str = this.notificationId[m];
          if (str != null)
          {
            j++;
            k += CodedOutputByteBufferNano.computeStringSizeNoTag(str);
          }
        }
        i = i + k + j * 1;
      }
      if ((this.hasSignatureAlgorithm) || (!this.signatureAlgorithm.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(4, this.signatureAlgorithm);
      }
      if ((this.hasBillingApiVersion) || (this.billingApiVersion != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(5, this.billingApiVersion);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.signatureHash != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.signatureHash);
      }
      if ((this.hasNonce) || (this.nonce != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(2, this.nonce);
      }
      if ((this.notificationId != null) && (this.notificationId.length > 0)) {
        for (int i = 0; i < this.notificationId.length; i++)
        {
          String str = this.notificationId[i];
          if (str != null) {
            paramCodedOutputByteBufferNano.writeString(3, str);
          }
        }
      }
      if ((this.hasSignatureAlgorithm) || (!this.signatureAlgorithm.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(4, this.signatureAlgorithm);
      }
      if ((this.hasBillingApiVersion) || (this.billingApiVersion != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(5, this.billingApiVersion);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class InAppPurchaseInformationResponseProto
    extends MessageNano
  {
    public VendingProtos.PurchaseResultProto purchaseResult = null;
    public VendingProtos.SignedDataProto signedResponse = null;
    public VendingProtos.StatusBarNotificationProto[] statusBarNotification = VendingProtos.StatusBarNotificationProto.emptyArray();
    
    public InAppPurchaseInformationResponseProto()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.signedResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.signedResponse);
      }
      if ((this.statusBarNotification != null) && (this.statusBarNotification.length > 0)) {
        for (int j = 0; j < this.statusBarNotification.length; j++)
        {
          VendingProtos.StatusBarNotificationProto localStatusBarNotificationProto = this.statusBarNotification[j];
          if (localStatusBarNotificationProto != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(2, localStatusBarNotificationProto);
          }
        }
      }
      if (this.purchaseResult != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(3, this.purchaseResult);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.signedResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.signedResponse);
      }
      if ((this.statusBarNotification != null) && (this.statusBarNotification.length > 0)) {
        for (int i = 0; i < this.statusBarNotification.length; i++)
        {
          VendingProtos.StatusBarNotificationProto localStatusBarNotificationProto = this.statusBarNotification[i];
          if (localStatusBarNotificationProto != null) {
            paramCodedOutputByteBufferNano.writeMessage(2, localStatusBarNotificationProto);
          }
        }
      }
      if (this.purchaseResult != null) {
        paramCodedOutputByteBufferNano.writeMessage(3, this.purchaseResult);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class InAppRestoreTransactionsRequestProto
    extends MessageNano
  {
    public int billingApiVersion = 0;
    public boolean hasBillingApiVersion = false;
    public boolean hasNonce = false;
    public boolean hasSignatureAlgorithm = false;
    public long nonce = 0L;
    public String signatureAlgorithm = "";
    public VendingProtos.SignatureHashProto signatureHash = null;
    
    public InAppRestoreTransactionsRequestProto()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.signatureHash != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.signatureHash);
      }
      if ((this.hasNonce) || (this.nonce != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(2, this.nonce);
      }
      if ((this.hasSignatureAlgorithm) || (!this.signatureAlgorithm.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.signatureAlgorithm);
      }
      if ((this.hasBillingApiVersion) || (this.billingApiVersion != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(4, this.billingApiVersion);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.signatureHash != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.signatureHash);
      }
      if ((this.hasNonce) || (this.nonce != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(2, this.nonce);
      }
      if ((this.hasSignatureAlgorithm) || (!this.signatureAlgorithm.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.signatureAlgorithm);
      }
      if ((this.hasBillingApiVersion) || (this.billingApiVersion != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(4, this.billingApiVersion);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class InAppRestoreTransactionsResponseProto
    extends MessageNano
  {
    public VendingProtos.PurchaseResultProto purchaseResult = null;
    public VendingProtos.SignedDataProto signedResponse = null;
    
    public InAppRestoreTransactionsResponseProto()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.signedResponse != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.signedResponse);
      }
      if (this.purchaseResult != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.purchaseResult);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.signedResponse != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.signedResponse);
      }
      if (this.purchaseResult != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.purchaseResult);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class ModifyCommentRequestProto
    extends MessageNano
  {
    public String assetId = "";
    public String flagMessage = "";
    public int flagType = 1;
    public boolean hasAssetId = false;
    public boolean hasFlagMessage = false;
    public boolean hasFlagType = false;
    
    public ModifyCommentRequestProto()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasAssetId) || (!this.assetId.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.assetId);
      }
      if ((this.flagType != 1) || (this.hasFlagType)) {
        i += CodedOutputByteBufferNano.computeInt32Size(5, this.flagType);
      }
      if ((this.hasFlagMessage) || (!this.flagMessage.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(6, this.flagMessage);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasAssetId) || (!this.assetId.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.assetId);
      }
      if ((this.flagType != 1) || (this.hasFlagType)) {
        paramCodedOutputByteBufferNano.writeInt32(5, this.flagType);
      }
      if ((this.hasFlagMessage) || (!this.flagMessage.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(6, this.flagMessage);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class ModifyCommentResponseProto
    extends MessageNano
  {
    public ModifyCommentResponseProto()
    {
      this.cachedSize = -1;
    }
  }
  
  public static final class PendingNotificationsProto
    extends MessageNano
  {
    public Notification[] finskyNotification = Notification.emptyArray();
    public boolean hasNextCheckMillis = false;
    public long nextCheckMillis = 0L;
    public VendingProtos.DataMessageProto[] notification = VendingProtos.DataMessageProto.emptyArray();
    
    public PendingNotificationsProto()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.notification != null) && (this.notification.length > 0)) {
        for (int k = 0; k < this.notification.length; k++)
        {
          VendingProtos.DataMessageProto localDataMessageProto = this.notification[k];
          if (localDataMessageProto != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(1, localDataMessageProto);
          }
        }
      }
      if ((this.hasNextCheckMillis) || (this.nextCheckMillis != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(2, this.nextCheckMillis);
      }
      if ((this.finskyNotification != null) && (this.finskyNotification.length > 0)) {
        for (int j = 0; j < this.finskyNotification.length; j++)
        {
          Notification localNotification = this.finskyNotification[j];
          if (localNotification != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(3, localNotification);
          }
        }
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.notification != null) && (this.notification.length > 0)) {
        for (int j = 0; j < this.notification.length; j++)
        {
          VendingProtos.DataMessageProto localDataMessageProto = this.notification[j];
          if (localDataMessageProto != null) {
            paramCodedOutputByteBufferNano.writeMessage(1, localDataMessageProto);
          }
        }
      }
      if ((this.hasNextCheckMillis) || (this.nextCheckMillis != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(2, this.nextCheckMillis);
      }
      if ((this.finskyNotification != null) && (this.finskyNotification.length > 0)) {
        for (int i = 0; i < this.finskyNotification.length; i++)
        {
          Notification localNotification = this.finskyNotification[i];
          if (localNotification != null) {
            paramCodedOutputByteBufferNano.writeMessage(3, localNotification);
          }
        }
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class PurchaseMetadataRequestProto
    extends MessageNano
  {
    public PurchaseMetadataRequestProto()
    {
      this.cachedSize = -1;
    }
  }
  
  public static final class PurchaseMetadataResponseProto
    extends MessageNano
  {
    public Countries countries = null;
    
    public PurchaseMetadataResponseProto()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.countries != null) {
        i += CodedOutputByteBufferNano.computeGroupSize(1, this.countries);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.countries != null) {
        paramCodedOutputByteBufferNano.writeGroup(1, this.countries);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
    
    public static final class Countries
      extends MessageNano
    {
      public Country[] country = Country.emptyArray();
      
      public Countries()
      {
        this.cachedSize = -1;
      }
      
      protected final int computeSerializedSize()
      {
        int i = super.computeSerializedSize();
        if ((this.country != null) && (this.country.length > 0)) {
          for (int j = 0; j < this.country.length; j++)
          {
            Country localCountry = this.country[j];
            if (localCountry != null) {
              i += CodedOutputByteBufferNano.computeGroupSize(2, localCountry);
            }
          }
        }
        return i;
      }
      
      public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
        throws IOException
      {
        if ((this.country != null) && (this.country.length > 0)) {
          for (int i = 0; i < this.country.length; i++)
          {
            Country localCountry = this.country[i];
            if (localCountry != null) {
              paramCodedOutputByteBufferNano.writeGroup(2, localCountry);
            }
          }
        }
        super.writeTo(paramCodedOutputByteBufferNano);
      }
      
      public static final class Country
        extends MessageNano
      {
        private static volatile Country[] _emptyArray;
        public boolean allowsReducedBillingAddress = false;
        public String countryCode = "";
        public String countryName = "";
        public boolean hasAllowsReducedBillingAddress = false;
        public boolean hasCountryCode = false;
        public boolean hasCountryName = false;
        public InstrumentAddressSpec[] instrumentAddressSpec = InstrumentAddressSpec.emptyArray();
        
        public Country()
        {
          this.cachedSize = -1;
        }
        
        public static Country[] emptyArray()
        {
          if (_emptyArray == null) {}
          synchronized (InternalNano.LAZY_INIT_LOCK)
          {
            if (_emptyArray == null) {
              _emptyArray = new Country[0];
            }
            return _emptyArray;
          }
        }
        
        protected final int computeSerializedSize()
        {
          int i = super.computeSerializedSize();
          if ((this.hasCountryCode) || (!this.countryCode.equals(""))) {
            i += CodedOutputByteBufferNano.computeStringSize(3, this.countryCode);
          }
          if ((this.hasCountryName) || (!this.countryName.equals(""))) {
            i += CodedOutputByteBufferNano.computeStringSize(4, this.countryName);
          }
          if ((this.hasAllowsReducedBillingAddress) || (this.allowsReducedBillingAddress)) {
            i += 1 + CodedOutputByteBufferNano.computeTagSize(6);
          }
          if ((this.instrumentAddressSpec != null) && (this.instrumentAddressSpec.length > 0)) {
            for (int j = 0; j < this.instrumentAddressSpec.length; j++)
            {
              InstrumentAddressSpec localInstrumentAddressSpec = this.instrumentAddressSpec[j];
              if (localInstrumentAddressSpec != null) {
                i += CodedOutputByteBufferNano.computeGroupSize(7, localInstrumentAddressSpec);
              }
            }
          }
          return i;
        }
        
        public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
          throws IOException
        {
          if ((this.hasCountryCode) || (!this.countryCode.equals(""))) {
            paramCodedOutputByteBufferNano.writeString(3, this.countryCode);
          }
          if ((this.hasCountryName) || (!this.countryName.equals(""))) {
            paramCodedOutputByteBufferNano.writeString(4, this.countryName);
          }
          if ((this.hasAllowsReducedBillingAddress) || (this.allowsReducedBillingAddress)) {
            paramCodedOutputByteBufferNano.writeBool(6, this.allowsReducedBillingAddress);
          }
          if ((this.instrumentAddressSpec != null) && (this.instrumentAddressSpec.length > 0)) {
            for (int i = 0; i < this.instrumentAddressSpec.length; i++)
            {
              InstrumentAddressSpec localInstrumentAddressSpec = this.instrumentAddressSpec[i];
              if (localInstrumentAddressSpec != null) {
                paramCodedOutputByteBufferNano.writeGroup(7, localInstrumentAddressSpec);
              }
            }
          }
          super.writeTo(paramCodedOutputByteBufferNano);
        }
        
        public static final class InstrumentAddressSpec
          extends MessageNano
        {
          private static volatile InstrumentAddressSpec[] _emptyArray;
          public BillingAddressSpec billingAddressSpec = null;
          public boolean hasInstrumentFamily = false;
          public int instrumentFamily = 0;
          
          public InstrumentAddressSpec()
          {
            this.cachedSize = -1;
          }
          
          public static InstrumentAddressSpec[] emptyArray()
          {
            if (_emptyArray == null) {}
            synchronized (InternalNano.LAZY_INIT_LOCK)
            {
              if (_emptyArray == null) {
                _emptyArray = new InstrumentAddressSpec[0];
              }
              return _emptyArray;
            }
          }
          
          protected final int computeSerializedSize()
          {
            int i = super.computeSerializedSize();
            if ((this.instrumentFamily != 0) || (this.hasInstrumentFamily)) {
              i += CodedOutputByteBufferNano.computeInt32Size(8, this.instrumentFamily);
            }
            if (this.billingAddressSpec != null) {
              i += CodedOutputByteBufferNano.computeMessageSize(9, this.billingAddressSpec);
            }
            return i;
          }
          
          public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
            throws IOException
          {
            if ((this.instrumentFamily != 0) || (this.hasInstrumentFamily)) {
              paramCodedOutputByteBufferNano.writeInt32(8, this.instrumentFamily);
            }
            if (this.billingAddressSpec != null) {
              paramCodedOutputByteBufferNano.writeMessage(9, this.billingAddressSpec);
            }
            super.writeTo(paramCodedOutputByteBufferNano);
          }
        }
      }
    }
  }
  
  public static final class PurchaseResultProto
    extends MessageNano
  {
    public boolean hasResultCode = false;
    public boolean hasResultCodeMessage = false;
    public int resultCode = 0;
    public String resultCodeMessage = "";
    
    public PurchaseResultProto()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.resultCode != 0) || (this.hasResultCode)) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.resultCode);
      }
      if ((this.hasResultCodeMessage) || (!this.resultCodeMessage.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.resultCodeMessage);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.resultCode != 0) || (this.hasResultCode)) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.resultCode);
      }
      if ((this.hasResultCodeMessage) || (!this.resultCodeMessage.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.resultCodeMessage);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class RequestPropertiesProto
    extends MessageNano
  {
    public String aid = "";
    public String clientId = "";
    public boolean hasAid = false;
    public boolean hasClientId = false;
    public boolean hasLoggingId = false;
    public boolean hasOperatorName = false;
    public boolean hasOperatorNumericName = false;
    public boolean hasProductNameAndVersion = false;
    public boolean hasSimOperatorName = false;
    public boolean hasSimOperatorNumericName = false;
    public boolean hasSoftwareVersion = false;
    public boolean hasUserAuthToken = false;
    public boolean hasUserAuthTokenSecure = false;
    public boolean hasUserCountry = false;
    public boolean hasUserLanguage = false;
    public String loggingId = "";
    public String operatorName = "";
    public String operatorNumericName = "";
    public String productNameAndVersion = "";
    public String simOperatorName = "";
    public String simOperatorNumericName = "";
    public int softwareVersion = 0;
    public String userAuthToken = "";
    public boolean userAuthTokenSecure = false;
    public String userCountry = "";
    public String userLanguage = "";
    
    public RequestPropertiesProto()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasUserAuthToken) || (!this.userAuthToken.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.userAuthToken);
      }
      if ((this.hasUserAuthTokenSecure) || (this.userAuthTokenSecure)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(2);
      }
      if ((this.hasSoftwareVersion) || (this.softwareVersion != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(3, this.softwareVersion);
      }
      if ((this.hasAid) || (!this.aid.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(4, this.aid);
      }
      if ((this.hasProductNameAndVersion) || (!this.productNameAndVersion.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(5, this.productNameAndVersion);
      }
      if ((this.hasUserLanguage) || (!this.userLanguage.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(6, this.userLanguage);
      }
      if ((this.hasUserCountry) || (!this.userCountry.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(7, this.userCountry);
      }
      if ((this.hasOperatorName) || (!this.operatorName.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(8, this.operatorName);
      }
      if ((this.hasSimOperatorName) || (!this.simOperatorName.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(9, this.simOperatorName);
      }
      if ((this.hasOperatorNumericName) || (!this.operatorNumericName.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(10, this.operatorNumericName);
      }
      if ((this.hasSimOperatorNumericName) || (!this.simOperatorNumericName.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(11, this.simOperatorNumericName);
      }
      if ((this.hasClientId) || (!this.clientId.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(12, this.clientId);
      }
      if ((this.hasLoggingId) || (!this.loggingId.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(13, this.loggingId);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasUserAuthToken) || (!this.userAuthToken.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.userAuthToken);
      }
      if ((this.hasUserAuthTokenSecure) || (this.userAuthTokenSecure)) {
        paramCodedOutputByteBufferNano.writeBool(2, this.userAuthTokenSecure);
      }
      if ((this.hasSoftwareVersion) || (this.softwareVersion != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(3, this.softwareVersion);
      }
      if ((this.hasAid) || (!this.aid.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(4, this.aid);
      }
      if ((this.hasProductNameAndVersion) || (!this.productNameAndVersion.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(5, this.productNameAndVersion);
      }
      if ((this.hasUserLanguage) || (!this.userLanguage.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(6, this.userLanguage);
      }
      if ((this.hasUserCountry) || (!this.userCountry.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(7, this.userCountry);
      }
      if ((this.hasOperatorName) || (!this.operatorName.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(8, this.operatorName);
      }
      if ((this.hasSimOperatorName) || (!this.simOperatorName.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(9, this.simOperatorName);
      }
      if ((this.hasOperatorNumericName) || (!this.operatorNumericName.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(10, this.operatorNumericName);
      }
      if ((this.hasSimOperatorNumericName) || (!this.simOperatorNumericName.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(11, this.simOperatorNumericName);
      }
      if ((this.hasClientId) || (!this.clientId.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(12, this.clientId);
      }
      if ((this.hasLoggingId) || (!this.loggingId.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(13, this.loggingId);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class RequestProto
    extends MessageNano
  {
    public Request[] request = Request.emptyArray();
    public VendingProtos.RequestPropertiesProto requestProperties = null;
    
    public RequestProto()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.requestProperties != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.requestProperties);
      }
      if ((this.request != null) && (this.request.length > 0)) {
        for (int j = 0; j < this.request.length; j++)
        {
          Request localRequest = this.request[j];
          if (localRequest != null) {
            i += CodedOutputByteBufferNano.computeGroupSize(2, localRequest);
          }
        }
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.requestProperties != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.requestProperties);
      }
      if ((this.request != null) && (this.request.length > 0)) {
        for (int i = 0; i < this.request.length; i++)
        {
          Request localRequest = this.request[i];
          if (localRequest != null) {
            paramCodedOutputByteBufferNano.writeGroup(2, localRequest);
          }
        }
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
    
    public static final class Request
      extends MessageNano
    {
      private static volatile Request[] _emptyArray;
      public VendingProtos.AckNotificationsRequestProto ackNotificationsRequest = null;
      public VendingProtos.BillingEventRequestProto billingEventRequest = null;
      public VendingProtos.CheckForNotificationsRequestProto checkForNotificationsRequest = null;
      public VendingProtos.CheckLicenseRequestProto checkLicenseRequest = null;
      public VendingProtos.ContentSyncRequestProto contentSyncRequest = null;
      public VendingProtos.InAppPurchaseInformationRequestProto inAppPurchaseInformationRequest = null;
      public VendingProtos.InAppRestoreTransactionsRequestProto inAppRestoreTransactionsRequest = null;
      public VendingProtos.ModifyCommentRequestProto modifyCommentRequest = null;
      public VendingProtos.PurchaseMetadataRequestProto purchaseMetadataRequest = null;
      public VendingProtos.RestoreApplicationsRequestProto restoreApplicationsRequest = null;
      
      public Request()
      {
        this.cachedSize = -1;
      }
      
      public static Request[] emptyArray()
      {
        if (_emptyArray == null) {}
        synchronized (InternalNano.LAZY_INIT_LOCK)
        {
          if (_emptyArray == null) {
            _emptyArray = new Request[0];
          }
          return _emptyArray;
        }
      }
      
      protected final int computeSerializedSize()
      {
        int i = super.computeSerializedSize();
        if (this.modifyCommentRequest != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(6, this.modifyCommentRequest);
        }
        if (this.contentSyncRequest != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(9, this.contentSyncRequest);
        }
        if (this.purchaseMetadataRequest != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(13, this.purchaseMetadataRequest);
        }
        if (this.checkLicenseRequest != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(18, this.checkLicenseRequest);
        }
        if (this.restoreApplicationsRequest != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(24, this.restoreApplicationsRequest);
        }
        if (this.billingEventRequest != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(26, this.billingEventRequest);
        }
        if (this.inAppRestoreTransactionsRequest != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(31, this.inAppRestoreTransactionsRequest);
        }
        if (this.inAppPurchaseInformationRequest != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(32, this.inAppPurchaseInformationRequest);
        }
        if (this.checkForNotificationsRequest != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(33, this.checkForNotificationsRequest);
        }
        if (this.ackNotificationsRequest != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(34, this.ackNotificationsRequest);
        }
        return i;
      }
      
      public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
        throws IOException
      {
        if (this.modifyCommentRequest != null) {
          paramCodedOutputByteBufferNano.writeMessage(6, this.modifyCommentRequest);
        }
        if (this.contentSyncRequest != null) {
          paramCodedOutputByteBufferNano.writeMessage(9, this.contentSyncRequest);
        }
        if (this.purchaseMetadataRequest != null) {
          paramCodedOutputByteBufferNano.writeMessage(13, this.purchaseMetadataRequest);
        }
        if (this.checkLicenseRequest != null) {
          paramCodedOutputByteBufferNano.writeMessage(18, this.checkLicenseRequest);
        }
        if (this.restoreApplicationsRequest != null) {
          paramCodedOutputByteBufferNano.writeMessage(24, this.restoreApplicationsRequest);
        }
        if (this.billingEventRequest != null) {
          paramCodedOutputByteBufferNano.writeMessage(26, this.billingEventRequest);
        }
        if (this.inAppRestoreTransactionsRequest != null) {
          paramCodedOutputByteBufferNano.writeMessage(31, this.inAppRestoreTransactionsRequest);
        }
        if (this.inAppPurchaseInformationRequest != null) {
          paramCodedOutputByteBufferNano.writeMessage(32, this.inAppPurchaseInformationRequest);
        }
        if (this.checkForNotificationsRequest != null) {
          paramCodedOutputByteBufferNano.writeMessage(33, this.checkForNotificationsRequest);
        }
        if (this.ackNotificationsRequest != null) {
          paramCodedOutputByteBufferNano.writeMessage(34, this.ackNotificationsRequest);
        }
        super.writeTo(paramCodedOutputByteBufferNano);
      }
    }
  }
  
  public static final class ResponsePropertiesProto
    extends MessageNano
  {
    public boolean hasResult = false;
    public int result = 0;
    
    public ResponsePropertiesProto()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.result != 0) || (this.hasResult)) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.result);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.result != 0) || (this.hasResult)) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.result);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class ResponseProto
    extends MessageNano
  {
    public VendingProtos.PendingNotificationsProto pendingNotifications = null;
    public Response[] response = Response.emptyArray();
    
    public ResponseProto()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.response != null) && (this.response.length > 0)) {
        for (int j = 0; j < this.response.length; j++)
        {
          Response localResponse = this.response[j];
          if (localResponse != null) {
            i += CodedOutputByteBufferNano.computeGroupSize(1, localResponse);
          }
        }
      }
      if (this.pendingNotifications != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(38, this.pendingNotifications);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.response != null) && (this.response.length > 0)) {
        for (int i = 0; i < this.response.length; i++)
        {
          Response localResponse = this.response[i];
          if (localResponse != null) {
            paramCodedOutputByteBufferNano.writeGroup(1, localResponse);
          }
        }
      }
      if (this.pendingNotifications != null) {
        paramCodedOutputByteBufferNano.writeMessage(38, this.pendingNotifications);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
    
    public static final class Response
      extends MessageNano
    {
      private static volatile Response[] _emptyArray;
      public VendingProtos.AckNotificationsResponseProto ackNotificationsResponse = null;
      public VendingProtos.BillingEventResponseProto billingEventResponse = null;
      public VendingProtos.CheckLicenseResponseProto checkLicenseResponse = null;
      public VendingProtos.ContentSyncResponseProto contentSyncResponse = null;
      public VendingProtos.GetAssetResponseProto getAssetResponse = null;
      public VendingProtos.GetMarketMetadataResponseProto getMarketMetadataResponse = null;
      public VendingProtos.InAppPurchaseInformationResponseProto inAppPurchaseInformationResponse = null;
      public VendingProtos.InAppRestoreTransactionsResponseProto inAppRestoreTransactionsResponse = null;
      public VendingProtos.ModifyCommentResponseProto modifyCommentResponse = null;
      public VendingProtos.PurchaseMetadataResponseProto purchaseMetadataResponse = null;
      public VendingProtos.ResponsePropertiesProto responseProperties = null;
      public VendingProtos.RestoreApplicationsResponseProto restoreApplicationResponse = null;
      
      public Response()
      {
        this.cachedSize = -1;
      }
      
      public static Response[] emptyArray()
      {
        if (_emptyArray == null) {}
        synchronized (InternalNano.LAZY_INIT_LOCK)
        {
          if (_emptyArray == null) {
            _emptyArray = new Response[0];
          }
          return _emptyArray;
        }
      }
      
      protected final int computeSerializedSize()
      {
        int i = super.computeSerializedSize();
        if (this.responseProperties != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(2, this.responseProperties);
        }
        if (this.modifyCommentResponse != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(5, this.modifyCommentResponse);
        }
        if (this.contentSyncResponse != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(8, this.contentSyncResponse);
        }
        if (this.getAssetResponse != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(9, this.getAssetResponse);
        }
        if (this.purchaseMetadataResponse != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(12, this.purchaseMetadataResponse);
        }
        if (this.checkLicenseResponse != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(17, this.checkLicenseResponse);
        }
        if (this.getMarketMetadataResponse != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(18, this.getMarketMetadataResponse);
        }
        if (this.restoreApplicationResponse != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(23, this.restoreApplicationResponse);
        }
        if (this.billingEventResponse != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(25, this.billingEventResponse);
        }
        if (this.inAppRestoreTransactionsResponse != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(30, this.inAppRestoreTransactionsResponse);
        }
        if (this.inAppPurchaseInformationResponse != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(31, this.inAppPurchaseInformationResponse);
        }
        if (this.ackNotificationsResponse != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(33, this.ackNotificationsResponse);
        }
        return i;
      }
      
      public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
        throws IOException
      {
        if (this.responseProperties != null) {
          paramCodedOutputByteBufferNano.writeMessage(2, this.responseProperties);
        }
        if (this.modifyCommentResponse != null) {
          paramCodedOutputByteBufferNano.writeMessage(5, this.modifyCommentResponse);
        }
        if (this.contentSyncResponse != null) {
          paramCodedOutputByteBufferNano.writeMessage(8, this.contentSyncResponse);
        }
        if (this.getAssetResponse != null) {
          paramCodedOutputByteBufferNano.writeMessage(9, this.getAssetResponse);
        }
        if (this.purchaseMetadataResponse != null) {
          paramCodedOutputByteBufferNano.writeMessage(12, this.purchaseMetadataResponse);
        }
        if (this.checkLicenseResponse != null) {
          paramCodedOutputByteBufferNano.writeMessage(17, this.checkLicenseResponse);
        }
        if (this.getMarketMetadataResponse != null) {
          paramCodedOutputByteBufferNano.writeMessage(18, this.getMarketMetadataResponse);
        }
        if (this.restoreApplicationResponse != null) {
          paramCodedOutputByteBufferNano.writeMessage(23, this.restoreApplicationResponse);
        }
        if (this.billingEventResponse != null) {
          paramCodedOutputByteBufferNano.writeMessage(25, this.billingEventResponse);
        }
        if (this.inAppRestoreTransactionsResponse != null) {
          paramCodedOutputByteBufferNano.writeMessage(30, this.inAppRestoreTransactionsResponse);
        }
        if (this.inAppPurchaseInformationResponse != null) {
          paramCodedOutputByteBufferNano.writeMessage(31, this.inAppPurchaseInformationResponse);
        }
        if (this.ackNotificationsResponse != null) {
          paramCodedOutputByteBufferNano.writeMessage(33, this.ackNotificationsResponse);
        }
        super.writeTo(paramCodedOutputByteBufferNano);
      }
    }
  }
  
  public static final class RestoreApplicationsRequestProto
    extends MessageNano
  {
    public String backupAndroidId = "";
    public DeviceConfiguration.DeviceConfigurationProto deviceConfiguration = null;
    public boolean hasBackupAndroidId = false;
    public boolean hasTosVersion = false;
    public String tosVersion = "";
    
    public RestoreApplicationsRequestProto()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasBackupAndroidId) || (!this.backupAndroidId.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.backupAndroidId);
      }
      if ((this.hasTosVersion) || (!this.tosVersion.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.tosVersion);
      }
      if (this.deviceConfiguration != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(3, this.deviceConfiguration);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasBackupAndroidId) || (!this.backupAndroidId.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.backupAndroidId);
      }
      if ((this.hasTosVersion) || (!this.tosVersion.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.tosVersion);
      }
      if (this.deviceConfiguration != null) {
        paramCodedOutputByteBufferNano.writeMessage(3, this.deviceConfiguration);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class RestoreApplicationsResponseProto
    extends MessageNano
  {
    public VendingProtos.GetAssetResponseProto[] asset = VendingProtos.GetAssetResponseProto.emptyArray();
    
    public RestoreApplicationsResponseProto()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.asset != null) && (this.asset.length > 0)) {
        for (int j = 0; j < this.asset.length; j++)
        {
          VendingProtos.GetAssetResponseProto localGetAssetResponseProto = this.asset[j];
          if (localGetAssetResponseProto != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(1, localGetAssetResponseProto);
          }
        }
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.asset != null) && (this.asset.length > 0)) {
        for (int i = 0; i < this.asset.length; i++)
        {
          VendingProtos.GetAssetResponseProto localGetAssetResponseProto = this.asset[i];
          if (localGetAssetResponseProto != null) {
            paramCodedOutputByteBufferNano.writeMessage(1, localGetAssetResponseProto);
          }
        }
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class SignatureHashProto
    extends MessageNano
  {
    public boolean hasHash = false;
    public boolean hasPackageName = false;
    public boolean hasVersionCode = false;
    public byte[] hash = WireFormatNano.EMPTY_BYTES;
    public String packageName = "";
    public int versionCode = 0;
    
    public SignatureHashProto()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasPackageName) || (!this.packageName.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.packageName);
      }
      if ((this.hasVersionCode) || (this.versionCode != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(2, this.versionCode);
      }
      if ((this.hasHash) || (!Arrays.equals(this.hash, WireFormatNano.EMPTY_BYTES))) {
        i += CodedOutputByteBufferNano.computeBytesSize(3, this.hash);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasPackageName) || (!this.packageName.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.packageName);
      }
      if ((this.hasVersionCode) || (this.versionCode != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(2, this.versionCode);
      }
      if ((this.hasHash) || (!Arrays.equals(this.hash, WireFormatNano.EMPTY_BYTES))) {
        paramCodedOutputByteBufferNano.writeBytes(3, this.hash);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class SignedDataProto
    extends MessageNano
  {
    public boolean hasSignature = false;
    public boolean hasSignedData = false;
    public String signature = "";
    public String signedData = "";
    
    public SignedDataProto()
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
  
  public static final class StatusBarNotificationProto
    extends MessageNano
  {
    private static volatile StatusBarNotificationProto[] _emptyArray;
    public String contentText = "";
    public String contentTitle = "";
    public boolean hasContentText = false;
    public boolean hasContentTitle = false;
    public boolean hasSeverity = false;
    public boolean hasTickerText = false;
    public int severity = 0;
    public String tickerText = "";
    
    public StatusBarNotificationProto()
    {
      this.cachedSize = -1;
    }
    
    public static StatusBarNotificationProto[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new StatusBarNotificationProto[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasTickerText) || (!this.tickerText.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.tickerText);
      }
      if ((this.hasContentTitle) || (!this.contentTitle.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.contentTitle);
      }
      if ((this.hasContentText) || (!this.contentText.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.contentText);
      }
      if ((this.severity != 0) || (this.hasSeverity)) {
        i += CodedOutputByteBufferNano.computeInt32Size(4, this.severity);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasTickerText) || (!this.tickerText.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.tickerText);
      }
      if ((this.hasContentTitle) || (!this.contentTitle.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.contentTitle);
      }
      if ((this.hasContentText) || (!this.contentText.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.contentText);
      }
      if ((this.severity != 0) || (this.hasSeverity)) {
        paramCodedOutputByteBufferNano.writeInt32(4, this.severity);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.VendingProtos
 * JD-Core Version:    0.7.0.1
 */