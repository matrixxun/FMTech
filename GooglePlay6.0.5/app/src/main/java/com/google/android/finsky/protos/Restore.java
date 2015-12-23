package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public abstract interface Restore
{
  public static final class BackupDeviceInfo
    extends MessageNano
  {
    private static volatile BackupDeviceInfo[] _emptyArray;
    public long androidId = 0L;
    public boolean hasAndroidId = false;
    public boolean hasLastCheckinTimeMs = false;
    public boolean hasName = false;
    public boolean hasNumDocuments = false;
    public boolean hasRestoreToken = false;
    public long lastCheckinTimeMs = 0L;
    public String name = "";
    public int numDocuments = 0;
    public String restoreToken = "";
    
    public BackupDeviceInfo()
    {
      this.cachedSize = -1;
    }
    
    public static BackupDeviceInfo[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new BackupDeviceInfo[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasAndroidId) || (this.androidId != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(1, this.androidId);
      }
      if ((this.hasName) || (!this.name.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.name);
      }
      if ((this.hasRestoreToken) || (!this.restoreToken.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.restoreToken);
      }
      if ((this.hasNumDocuments) || (this.numDocuments != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(4, this.numDocuments);
      }
      if ((this.hasLastCheckinTimeMs) || (this.lastCheckinTimeMs != 0L)) {
        i += CodedOutputByteBufferNano.computeUInt64Size(5, this.lastCheckinTimeMs);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasAndroidId) || (this.androidId != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(1, this.androidId);
      }
      if ((this.hasName) || (!this.name.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.name);
      }
      if ((this.hasRestoreToken) || (!this.restoreToken.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.restoreToken);
      }
      if ((this.hasNumDocuments) || (this.numDocuments != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(4, this.numDocuments);
      }
      if ((this.hasLastCheckinTimeMs) || (this.lastCheckinTimeMs != 0L)) {
        paramCodedOutputByteBufferNano.writeUInt64(5, this.lastCheckinTimeMs);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class BackupDocumentInfo
    extends MessageNano
  {
    private static volatile BackupDocumentInfo[] _emptyArray;
    public Common.Docid docid = null;
    public boolean hasInstallLocation = false;
    public boolean hasRestorePriority = false;
    public boolean hasSize = false;
    public boolean hasTitle = false;
    public boolean hasVersionCode = false;
    public int installLocation = 0;
    public int restorePriority = 0;
    public long size = 0L;
    public Common.Image thumbnailImage = null;
    public String title = "";
    public int versionCode = 0;
    
    public BackupDocumentInfo()
    {
      this.cachedSize = -1;
    }
    
    public static BackupDocumentInfo[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new BackupDocumentInfo[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.docid != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.docid);
      }
      if ((this.hasTitle) || (!this.title.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.title);
      }
      if ((this.hasVersionCode) || (this.versionCode != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(3, this.versionCode);
      }
      if (this.thumbnailImage != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(4, this.thumbnailImage);
      }
      if ((this.hasRestorePriority) || (this.restorePriority != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(5, this.restorePriority);
      }
      if ((this.installLocation != 0) || (this.hasInstallLocation)) {
        i += CodedOutputByteBufferNano.computeInt32Size(6, this.installLocation);
      }
      if ((this.hasSize) || (this.size != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(7, this.size);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.docid != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.docid);
      }
      if ((this.hasTitle) || (!this.title.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.title);
      }
      if ((this.hasVersionCode) || (this.versionCode != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(3, this.versionCode);
      }
      if (this.thumbnailImage != null) {
        paramCodedOutputByteBufferNano.writeMessage(4, this.thumbnailImage);
      }
      if ((this.hasRestorePriority) || (this.restorePriority != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(5, this.restorePriority);
      }
      if ((this.installLocation != 0) || (this.hasInstallLocation)) {
        paramCodedOutputByteBufferNano.writeInt32(6, this.installLocation);
      }
      if ((this.hasSize) || (this.size != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(7, this.size);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class GetBackupDeviceChoicesResponse
    extends MessageNano
  {
    public Restore.BackupDeviceInfo[] backupDeviceInfo = Restore.BackupDeviceInfo.emptyArray();
    
    public GetBackupDeviceChoicesResponse()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.backupDeviceInfo != null) && (this.backupDeviceInfo.length > 0)) {
        for (int j = 0; j < this.backupDeviceInfo.length; j++)
        {
          Restore.BackupDeviceInfo localBackupDeviceInfo = this.backupDeviceInfo[j];
          if (localBackupDeviceInfo != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(1, localBackupDeviceInfo);
          }
        }
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.backupDeviceInfo != null) && (this.backupDeviceInfo.length > 0)) {
        for (int i = 0; i < this.backupDeviceInfo.length; i++)
        {
          Restore.BackupDeviceInfo localBackupDeviceInfo = this.backupDeviceInfo[i];
          if (localBackupDeviceInfo != null) {
            paramCodedOutputByteBufferNano.writeMessage(1, localBackupDeviceInfo);
          }
        }
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class GetBackupDocumentChoicesResponse
    extends MessageNano
  {
    public Restore.BackupDocumentInfo[] backupDocumentInfo = Restore.BackupDocumentInfo.emptyArray();
    
    public GetBackupDocumentChoicesResponse()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.backupDocumentInfo != null) && (this.backupDocumentInfo.length > 0)) {
        for (int j = 0; j < this.backupDocumentInfo.length; j++)
        {
          Restore.BackupDocumentInfo localBackupDocumentInfo = this.backupDocumentInfo[j];
          if (localBackupDocumentInfo != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(1, localBackupDocumentInfo);
          }
        }
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.backupDocumentInfo != null) && (this.backupDocumentInfo.length > 0)) {
        for (int i = 0; i < this.backupDocumentInfo.length; i++)
        {
          Restore.BackupDocumentInfo localBackupDocumentInfo = this.backupDocumentInfo[i];
          if (localBackupDocumentInfo != null) {
            paramCodedOutputByteBufferNano.writeMessage(1, localBackupDocumentInfo);
          }
        }
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.Restore
 * JD-Core Version:    0.7.0.1
 */