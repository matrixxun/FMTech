package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;

public final class AppDetails
  extends MessageNano
{
  public String[] appCategory = WireFormatNano.EMPTY_STRING_ARRAY;
  public String appType = "";
  public String[] autoAcquireFreeAppIfHigherVersionAvailableTag = WireFormatNano.EMPTY_STRING_ARRAY;
  public String[] certificateHash = WireFormatNano.EMPTY_STRING_ARRAY;
  public CertificateSet[] certificateSet = CertificateSet.emptyArray();
  public int contentRating = 0;
  public boolean declaresIab = false;
  public String developerEmail = "";
  public String developerName = "";
  public String developerWebsite = "";
  public boolean everExternallyHosted = false;
  public boolean externallyHosted = false;
  public FileMetadata[] file = FileMetadata.emptyArray();
  public boolean gamepadRequired = false;
  public boolean hasAppType = false;
  public boolean hasContentRating = false;
  public boolean hasDeclaresIab = false;
  public boolean hasDeveloperEmail = false;
  public boolean hasDeveloperName = false;
  public boolean hasDeveloperWebsite = false;
  public boolean hasEverExternallyHosted = false;
  public boolean hasExternallyHosted = false;
  public boolean hasGamepadRequired = false;
  public boolean hasInstallLocation = false;
  public boolean hasInstallNotes = false;
  public boolean hasInstallationSize = false;
  public boolean hasMajorVersionNumber = false;
  public boolean hasNumDownloads = false;
  public boolean hasPackageName = false;
  public boolean hasPreregistrationPromoCode = false;
  public boolean hasRecentChangesHtml = false;
  public boolean hasTargetSdkVersion = false;
  public boolean hasTitle = false;
  public boolean hasUploadDate = false;
  public boolean hasVariesByAccount = false;
  public boolean hasVersionCode = false;
  public boolean hasVersionString = false;
  public int installLocation = 0;
  public String installNotes = "";
  public long installationSize = 0L;
  public int majorVersionNumber = 0;
  public String numDownloads = "";
  public String packageName = "";
  public String[] permission = WireFormatNano.EMPTY_STRING_ARRAY;
  public String preregistrationPromoCode = "";
  public String recentChangesHtml = "";
  public String[] splitId = WireFormatNano.EMPTY_STRING_ARRAY;
  public int targetSdkVersion = 0;
  public String title = "";
  public String uploadDate = "";
  public boolean variesByAccount = true;
  public int versionCode = 0;
  public String versionString = "";
  
  public AppDetails()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasDeveloperName) || (!this.developerName.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(1, this.developerName);
    }
    if ((this.hasMajorVersionNumber) || (this.majorVersionNumber != 0)) {
      i += CodedOutputByteBufferNano.computeInt32Size(2, this.majorVersionNumber);
    }
    if ((this.hasVersionCode) || (this.versionCode != 0)) {
      i += CodedOutputByteBufferNano.computeInt32Size(3, this.versionCode);
    }
    if ((this.hasVersionString) || (!this.versionString.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(4, this.versionString);
    }
    if ((this.hasTitle) || (!this.title.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(5, this.title);
    }
    if ((this.appCategory != null) && (this.appCategory.length > 0))
    {
      int i11 = 0;
      int i12 = 0;
      for (int i13 = 0; i13 < this.appCategory.length; i13++)
      {
        String str5 = this.appCategory[i13];
        if (str5 != null)
        {
          i11++;
          i12 += CodedOutputByteBufferNano.computeStringSizeNoTag(str5);
        }
      }
      i = i + i12 + i11 * 1;
    }
    if ((this.hasContentRating) || (this.contentRating != 0)) {
      i += CodedOutputByteBufferNano.computeInt32Size(8, this.contentRating);
    }
    if ((this.hasInstallationSize) || (this.installationSize != 0L)) {
      i += CodedOutputByteBufferNano.computeInt64Size(9, this.installationSize);
    }
    if ((this.permission != null) && (this.permission.length > 0))
    {
      int i8 = 0;
      int i9 = 0;
      for (int i10 = 0; i10 < this.permission.length; i10++)
      {
        String str4 = this.permission[i10];
        if (str4 != null)
        {
          i8++;
          i9 += CodedOutputByteBufferNano.computeStringSizeNoTag(str4);
        }
      }
      i = i + i9 + i8 * 1;
    }
    if ((this.hasDeveloperEmail) || (!this.developerEmail.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(11, this.developerEmail);
    }
    if ((this.hasDeveloperWebsite) || (!this.developerWebsite.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(12, this.developerWebsite);
    }
    if ((this.hasNumDownloads) || (!this.numDownloads.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(13, this.numDownloads);
    }
    if ((this.hasPackageName) || (!this.packageName.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(14, this.packageName);
    }
    if ((this.hasRecentChangesHtml) || (!this.recentChangesHtml.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(15, this.recentChangesHtml);
    }
    if ((this.hasUploadDate) || (!this.uploadDate.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(16, this.uploadDate);
    }
    if ((this.file != null) && (this.file.length > 0)) {
      for (int i7 = 0; i7 < this.file.length; i7++)
      {
        FileMetadata localFileMetadata = this.file[i7];
        if (localFileMetadata != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(17, localFileMetadata);
        }
      }
    }
    if ((this.hasAppType) || (!this.appType.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(18, this.appType);
    }
    if ((this.certificateHash != null) && (this.certificateHash.length > 0))
    {
      int i4 = 0;
      int i5 = 0;
      for (int i6 = 0; i6 < this.certificateHash.length; i6++)
      {
        String str3 = this.certificateHash[i6];
        if (str3 != null)
        {
          i4++;
          i5 += CodedOutputByteBufferNano.computeStringSizeNoTag(str3);
        }
      }
      i = i + i5 + i4 * 2;
    }
    if ((this.hasVariesByAccount) || (this.variesByAccount != true)) {
      i += 1 + CodedOutputByteBufferNano.computeTagSize(21);
    }
    if ((this.certificateSet != null) && (this.certificateSet.length > 0)) {
      for (int i3 = 0; i3 < this.certificateSet.length; i3++)
      {
        CertificateSet localCertificateSet = this.certificateSet[i3];
        if (localCertificateSet != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(22, localCertificateSet);
        }
      }
    }
    if ((this.autoAcquireFreeAppIfHigherVersionAvailableTag != null) && (this.autoAcquireFreeAppIfHigherVersionAvailableTag.length > 0))
    {
      int n = 0;
      int i1 = 0;
      for (int i2 = 0; i2 < this.autoAcquireFreeAppIfHigherVersionAvailableTag.length; i2++)
      {
        String str2 = this.autoAcquireFreeAppIfHigherVersionAvailableTag[i2];
        if (str2 != null)
        {
          n++;
          i1 += CodedOutputByteBufferNano.computeStringSizeNoTag(str2);
        }
      }
      i = i + i1 + n * 2;
    }
    if ((this.hasDeclaresIab) || (this.declaresIab)) {
      i += 1 + CodedOutputByteBufferNano.computeTagSize(24);
    }
    if ((this.splitId != null) && (this.splitId.length > 0))
    {
      int j = 0;
      int k = 0;
      for (int m = 0; m < this.splitId.length; m++)
      {
        String str1 = this.splitId[m];
        if (str1 != null)
        {
          j++;
          k += CodedOutputByteBufferNano.computeStringSizeNoTag(str1);
        }
      }
      i = i + k + j * 2;
    }
    if ((this.hasGamepadRequired) || (this.gamepadRequired)) {
      i += 1 + CodedOutputByteBufferNano.computeTagSize(26);
    }
    if ((this.hasExternallyHosted) || (this.externallyHosted)) {
      i += 1 + CodedOutputByteBufferNano.computeTagSize(27);
    }
    if ((this.hasEverExternallyHosted) || (this.everExternallyHosted)) {
      i += 1 + CodedOutputByteBufferNano.computeTagSize(28);
    }
    if ((this.hasInstallNotes) || (!this.installNotes.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(30, this.installNotes);
    }
    if ((this.installLocation != 0) || (this.hasInstallLocation)) {
      i += CodedOutputByteBufferNano.computeInt32Size(31, this.installLocation);
    }
    if ((this.hasTargetSdkVersion) || (this.targetSdkVersion != 0)) {
      i += CodedOutputByteBufferNano.computeInt32Size(32, this.targetSdkVersion);
    }
    if ((this.hasPreregistrationPromoCode) || (!this.preregistrationPromoCode.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(33, this.preregistrationPromoCode);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasDeveloperName) || (!this.developerName.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(1, this.developerName);
    }
    if ((this.hasMajorVersionNumber) || (this.majorVersionNumber != 0)) {
      paramCodedOutputByteBufferNano.writeInt32(2, this.majorVersionNumber);
    }
    if ((this.hasVersionCode) || (this.versionCode != 0)) {
      paramCodedOutputByteBufferNano.writeInt32(3, this.versionCode);
    }
    if ((this.hasVersionString) || (!this.versionString.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(4, this.versionString);
    }
    if ((this.hasTitle) || (!this.title.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(5, this.title);
    }
    if ((this.appCategory != null) && (this.appCategory.length > 0)) {
      for (int i2 = 0; i2 < this.appCategory.length; i2++)
      {
        String str5 = this.appCategory[i2];
        if (str5 != null) {
          paramCodedOutputByteBufferNano.writeString(7, str5);
        }
      }
    }
    if ((this.hasContentRating) || (this.contentRating != 0)) {
      paramCodedOutputByteBufferNano.writeInt32(8, this.contentRating);
    }
    if ((this.hasInstallationSize) || (this.installationSize != 0L)) {
      paramCodedOutputByteBufferNano.writeInt64(9, this.installationSize);
    }
    if ((this.permission != null) && (this.permission.length > 0)) {
      for (int i1 = 0; i1 < this.permission.length; i1++)
      {
        String str4 = this.permission[i1];
        if (str4 != null) {
          paramCodedOutputByteBufferNano.writeString(10, str4);
        }
      }
    }
    if ((this.hasDeveloperEmail) || (!this.developerEmail.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(11, this.developerEmail);
    }
    if ((this.hasDeveloperWebsite) || (!this.developerWebsite.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(12, this.developerWebsite);
    }
    if ((this.hasNumDownloads) || (!this.numDownloads.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(13, this.numDownloads);
    }
    if ((this.hasPackageName) || (!this.packageName.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(14, this.packageName);
    }
    if ((this.hasRecentChangesHtml) || (!this.recentChangesHtml.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(15, this.recentChangesHtml);
    }
    if ((this.hasUploadDate) || (!this.uploadDate.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(16, this.uploadDate);
    }
    if ((this.file != null) && (this.file.length > 0)) {
      for (int n = 0; n < this.file.length; n++)
      {
        FileMetadata localFileMetadata = this.file[n];
        if (localFileMetadata != null) {
          paramCodedOutputByteBufferNano.writeMessage(17, localFileMetadata);
        }
      }
    }
    if ((this.hasAppType) || (!this.appType.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(18, this.appType);
    }
    if ((this.certificateHash != null) && (this.certificateHash.length > 0)) {
      for (int m = 0; m < this.certificateHash.length; m++)
      {
        String str3 = this.certificateHash[m];
        if (str3 != null) {
          paramCodedOutputByteBufferNano.writeString(19, str3);
        }
      }
    }
    if ((this.hasVariesByAccount) || (this.variesByAccount != true)) {
      paramCodedOutputByteBufferNano.writeBool(21, this.variesByAccount);
    }
    if ((this.certificateSet != null) && (this.certificateSet.length > 0)) {
      for (int k = 0; k < this.certificateSet.length; k++)
      {
        CertificateSet localCertificateSet = this.certificateSet[k];
        if (localCertificateSet != null) {
          paramCodedOutputByteBufferNano.writeMessage(22, localCertificateSet);
        }
      }
    }
    if ((this.autoAcquireFreeAppIfHigherVersionAvailableTag != null) && (this.autoAcquireFreeAppIfHigherVersionAvailableTag.length > 0)) {
      for (int j = 0; j < this.autoAcquireFreeAppIfHigherVersionAvailableTag.length; j++)
      {
        String str2 = this.autoAcquireFreeAppIfHigherVersionAvailableTag[j];
        if (str2 != null) {
          paramCodedOutputByteBufferNano.writeString(23, str2);
        }
      }
    }
    if ((this.hasDeclaresIab) || (this.declaresIab)) {
      paramCodedOutputByteBufferNano.writeBool(24, this.declaresIab);
    }
    if ((this.splitId != null) && (this.splitId.length > 0)) {
      for (int i = 0; i < this.splitId.length; i++)
      {
        String str1 = this.splitId[i];
        if (str1 != null) {
          paramCodedOutputByteBufferNano.writeString(25, str1);
        }
      }
    }
    if ((this.hasGamepadRequired) || (this.gamepadRequired)) {
      paramCodedOutputByteBufferNano.writeBool(26, this.gamepadRequired);
    }
    if ((this.hasExternallyHosted) || (this.externallyHosted)) {
      paramCodedOutputByteBufferNano.writeBool(27, this.externallyHosted);
    }
    if ((this.hasEverExternallyHosted) || (this.everExternallyHosted)) {
      paramCodedOutputByteBufferNano.writeBool(28, this.everExternallyHosted);
    }
    if ((this.hasInstallNotes) || (!this.installNotes.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(30, this.installNotes);
    }
    if ((this.installLocation != 0) || (this.hasInstallLocation)) {
      paramCodedOutputByteBufferNano.writeInt32(31, this.installLocation);
    }
    if ((this.hasTargetSdkVersion) || (this.targetSdkVersion != 0)) {
      paramCodedOutputByteBufferNano.writeInt32(32, this.targetSdkVersion);
    }
    if ((this.hasPreregistrationPromoCode) || (!this.preregistrationPromoCode.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(33, this.preregistrationPromoCode);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.AppDetails
 * JD-Core Version:    0.7.0.1
 */