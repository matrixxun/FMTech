package com.google.android.vending.verifier.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;
import java.util.Arrays;

public abstract interface CsdClient
{
  public static final class ClientDownloadRequest
    extends MessageNano
  {
    private static volatile ClientDownloadRequest[] _emptyArray;
    public long androidId = 0L;
    public ApkInfo apkInfo = null;
    public String[] clientAsn = WireFormatNano.EMPTY_STRING_ARRAY;
    public Digests digests = null;
    public int downloadType = 0;
    public String fileBasename = "";
    public boolean hasAndroidId = false;
    public boolean hasDownloadType = false;
    public boolean hasFileBasename = false;
    public boolean hasLength = false;
    public boolean hasLocale = false;
    public boolean hasRequestId = false;
    public boolean hasSafetyNetId = false;
    public boolean hasUrl = false;
    public boolean hasUserInitiated = false;
    public String[] installerPackages = WireFormatNano.EMPTY_STRING_ARRAY;
    public SignatureInfo installerSignature = null;
    public long length = 0L;
    public String locale = "";
    public String[] originatingPackages = WireFormatNano.EMPTY_STRING_ARRAY;
    public SignatureInfo originatingSignature = null;
    public byte[] requestId = WireFormatNano.EMPTY_BYTES;
    public Resource[] resources = Resource.emptyArray();
    public String safetyNetId = "";
    public SignatureInfo signature = null;
    public String url = "";
    public boolean userInitiated = false;
    
    public ClientDownloadRequest()
    {
      this.cachedSize = -1;
    }
    
    public static ClientDownloadRequest[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new ClientDownloadRequest[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasUrl) || (!this.url.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.url);
      }
      if (this.digests != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.digests);
      }
      if ((this.hasLength) || (this.length != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(3, this.length);
      }
      if ((this.resources != null) && (this.resources.length > 0)) {
        for (int i6 = 0; i6 < this.resources.length; i6++)
        {
          Resource localResource = this.resources[i6];
          if (localResource != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(4, localResource);
          }
        }
      }
      if (this.signature != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(5, this.signature);
      }
      if ((this.hasUserInitiated) || (this.userInitiated)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(6);
      }
      if ((this.clientAsn != null) && (this.clientAsn.length > 0))
      {
        int i3 = 0;
        int i4 = 0;
        for (int i5 = 0; i5 < this.clientAsn.length; i5++)
        {
          String str3 = this.clientAsn[i5];
          if (str3 != null)
          {
            i3++;
            i4 += CodedOutputByteBufferNano.computeStringSizeNoTag(str3);
          }
        }
        i = i + i4 + i3 * 1;
      }
      if ((this.hasFileBasename) || (!this.fileBasename.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(9, this.fileBasename);
      }
      if ((this.downloadType != 0) || (this.hasDownloadType)) {
        i += CodedOutputByteBufferNano.computeInt32Size(10, this.downloadType);
      }
      if ((this.hasLocale) || (!this.locale.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(11, this.locale);
      }
      if (this.apkInfo != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(12, this.apkInfo);
      }
      if ((this.hasAndroidId) || (this.androidId != 0L)) {
        i += 8 + CodedOutputByteBufferNano.computeTagSize(13);
      }
      if ((this.originatingPackages != null) && (this.originatingPackages.length > 0))
      {
        int n = 0;
        int i1 = 0;
        for (int i2 = 0; i2 < this.originatingPackages.length; i2++)
        {
          String str2 = this.originatingPackages[i2];
          if (str2 != null)
          {
            n++;
            i1 += CodedOutputByteBufferNano.computeStringSizeNoTag(str2);
          }
        }
        i = i + i1 + n * 1;
      }
      if ((this.hasRequestId) || (!Arrays.equals(this.requestId, WireFormatNano.EMPTY_BYTES))) {
        i += CodedOutputByteBufferNano.computeBytesSize(16, this.requestId);
      }
      if (this.originatingSignature != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(17, this.originatingSignature);
      }
      if ((this.installerPackages != null) && (this.installerPackages.length > 0))
      {
        int j = 0;
        int k = 0;
        for (int m = 0; m < this.installerPackages.length; m++)
        {
          String str1 = this.installerPackages[m];
          if (str1 != null)
          {
            j++;
            k += CodedOutputByteBufferNano.computeStringSizeNoTag(str1);
          }
        }
        i = i + k + j * 2;
      }
      if (this.installerSignature != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(21, this.installerSignature);
      }
      if ((this.hasSafetyNetId) || (!this.safetyNetId.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(23, this.safetyNetId);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasUrl) || (!this.url.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.url);
      }
      if (this.digests != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.digests);
      }
      if ((this.hasLength) || (this.length != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(3, this.length);
      }
      if ((this.resources != null) && (this.resources.length > 0)) {
        for (int m = 0; m < this.resources.length; m++)
        {
          Resource localResource = this.resources[m];
          if (localResource != null) {
            paramCodedOutputByteBufferNano.writeMessage(4, localResource);
          }
        }
      }
      if (this.signature != null) {
        paramCodedOutputByteBufferNano.writeMessage(5, this.signature);
      }
      if ((this.hasUserInitiated) || (this.userInitiated)) {
        paramCodedOutputByteBufferNano.writeBool(6, this.userInitiated);
      }
      if ((this.clientAsn != null) && (this.clientAsn.length > 0)) {
        for (int k = 0; k < this.clientAsn.length; k++)
        {
          String str3 = this.clientAsn[k];
          if (str3 != null) {
            paramCodedOutputByteBufferNano.writeString(8, str3);
          }
        }
      }
      if ((this.hasFileBasename) || (!this.fileBasename.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(9, this.fileBasename);
      }
      if ((this.downloadType != 0) || (this.hasDownloadType)) {
        paramCodedOutputByteBufferNano.writeInt32(10, this.downloadType);
      }
      if ((this.hasLocale) || (!this.locale.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(11, this.locale);
      }
      if (this.apkInfo != null) {
        paramCodedOutputByteBufferNano.writeMessage(12, this.apkInfo);
      }
      if ((this.hasAndroidId) || (this.androidId != 0L)) {
        paramCodedOutputByteBufferNano.writeFixed64(13, this.androidId);
      }
      if ((this.originatingPackages != null) && (this.originatingPackages.length > 0)) {
        for (int j = 0; j < this.originatingPackages.length; j++)
        {
          String str2 = this.originatingPackages[j];
          if (str2 != null) {
            paramCodedOutputByteBufferNano.writeString(15, str2);
          }
        }
      }
      if ((this.hasRequestId) || (!Arrays.equals(this.requestId, WireFormatNano.EMPTY_BYTES))) {
        paramCodedOutputByteBufferNano.writeBytes(16, this.requestId);
      }
      if (this.originatingSignature != null) {
        paramCodedOutputByteBufferNano.writeMessage(17, this.originatingSignature);
      }
      if ((this.installerPackages != null) && (this.installerPackages.length > 0)) {
        for (int i = 0; i < this.installerPackages.length; i++)
        {
          String str1 = this.installerPackages[i];
          if (str1 != null) {
            paramCodedOutputByteBufferNano.writeString(20, str1);
          }
        }
      }
      if (this.installerSignature != null) {
        paramCodedOutputByteBufferNano.writeMessage(21, this.installerSignature);
      }
      if ((this.hasSafetyNetId) || (!this.safetyNetId.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(23, this.safetyNetId);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
    
    public static final class ApkInfo
      extends MessageNano
    {
      public boolean debuggable = false;
      public boolean dontWarnAgain = false;
      public CsdClient.ClientDownloadRequest.FileInfo[] files = CsdClient.ClientDownloadRequest.FileInfo.emptyArray();
      public boolean forwardLocked = false;
      public boolean hasDebuggable = false;
      public boolean hasDontWarnAgain = false;
      public boolean hasForwardLocked = false;
      public boolean hasInStoppedState = false;
      public boolean hasInstalledByPlay = false;
      public boolean hasPackageName = false;
      public boolean hasSystemApplication = false;
      public boolean hasUpdatedSystemApplication = false;
      public boolean hasVersionCode = false;
      public boolean inStoppedState = false;
      public boolean installedByPlay = false;
      public String packageName = "";
      public boolean systemApplication = false;
      public boolean updatedSystemApplication = false;
      public int versionCode = 0;
      
      public ApkInfo()
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
        if ((this.files != null) && (this.files.length > 0)) {
          for (int j = 0; j < this.files.length; j++)
          {
            CsdClient.ClientDownloadRequest.FileInfo localFileInfo = this.files[j];
            if (localFileInfo != null) {
              i += CodedOutputByteBufferNano.computeMessageSize(3, localFileInfo);
            }
          }
        }
        if ((this.hasInstalledByPlay) || (this.installedByPlay)) {
          i += 1 + CodedOutputByteBufferNano.computeTagSize(4);
        }
        if ((this.hasForwardLocked) || (this.forwardLocked)) {
          i += 1 + CodedOutputByteBufferNano.computeTagSize(5);
        }
        if ((this.hasInStoppedState) || (this.inStoppedState)) {
          i += 1 + CodedOutputByteBufferNano.computeTagSize(6);
        }
        if ((this.hasDontWarnAgain) || (this.dontWarnAgain)) {
          i += 1 + CodedOutputByteBufferNano.computeTagSize(7);
        }
        if ((this.hasSystemApplication) || (this.systemApplication)) {
          i += 1 + CodedOutputByteBufferNano.computeTagSize(8);
        }
        if ((this.hasUpdatedSystemApplication) || (this.updatedSystemApplication)) {
          i += 1 + CodedOutputByteBufferNano.computeTagSize(9);
        }
        if ((this.hasDebuggable) || (this.debuggable)) {
          i += 1 + CodedOutputByteBufferNano.computeTagSize(10);
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
        if ((this.files != null) && (this.files.length > 0)) {
          for (int i = 0; i < this.files.length; i++)
          {
            CsdClient.ClientDownloadRequest.FileInfo localFileInfo = this.files[i];
            if (localFileInfo != null) {
              paramCodedOutputByteBufferNano.writeMessage(3, localFileInfo);
            }
          }
        }
        if ((this.hasInstalledByPlay) || (this.installedByPlay)) {
          paramCodedOutputByteBufferNano.writeBool(4, this.installedByPlay);
        }
        if ((this.hasForwardLocked) || (this.forwardLocked)) {
          paramCodedOutputByteBufferNano.writeBool(5, this.forwardLocked);
        }
        if ((this.hasInStoppedState) || (this.inStoppedState)) {
          paramCodedOutputByteBufferNano.writeBool(6, this.inStoppedState);
        }
        if ((this.hasDontWarnAgain) || (this.dontWarnAgain)) {
          paramCodedOutputByteBufferNano.writeBool(7, this.dontWarnAgain);
        }
        if ((this.hasSystemApplication) || (this.systemApplication)) {
          paramCodedOutputByteBufferNano.writeBool(8, this.systemApplication);
        }
        if ((this.hasUpdatedSystemApplication) || (this.updatedSystemApplication)) {
          paramCodedOutputByteBufferNano.writeBool(9, this.updatedSystemApplication);
        }
        if ((this.hasDebuggable) || (this.debuggable)) {
          paramCodedOutputByteBufferNano.writeBool(10, this.debuggable);
        }
        super.writeTo(paramCodedOutputByteBufferNano);
      }
    }
    
    public static final class CertificateChain
      extends MessageNano
    {
      private static volatile CertificateChain[] _emptyArray;
      public Element[] element = Element.emptyArray();
      
      public CertificateChain()
      {
        this.cachedSize = -1;
      }
      
      public static CertificateChain[] emptyArray()
      {
        if (_emptyArray == null) {}
        synchronized (InternalNano.LAZY_INIT_LOCK)
        {
          if (_emptyArray == null) {
            _emptyArray = new CertificateChain[0];
          }
          return _emptyArray;
        }
      }
      
      protected final int computeSerializedSize()
      {
        int i = super.computeSerializedSize();
        if ((this.element != null) && (this.element.length > 0)) {
          for (int j = 0; j < this.element.length; j++)
          {
            Element localElement = this.element[j];
            if (localElement != null) {
              i += CodedOutputByteBufferNano.computeMessageSize(1, localElement);
            }
          }
        }
        return i;
      }
      
      public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
        throws IOException
      {
        if ((this.element != null) && (this.element.length > 0)) {
          for (int i = 0; i < this.element.length; i++)
          {
            Element localElement = this.element[i];
            if (localElement != null) {
              paramCodedOutputByteBufferNano.writeMessage(1, localElement);
            }
          }
        }
        super.writeTo(paramCodedOutputByteBufferNano);
      }
      
      public static final class Element
        extends MessageNano
      {
        private static volatile Element[] _emptyArray;
        public byte[] certificate = WireFormatNano.EMPTY_BYTES;
        public long expiryTime = 0L;
        public byte[] fingerprint = WireFormatNano.EMPTY_BYTES;
        public boolean hasCertificate = false;
        public boolean hasExpiryTime = false;
        public boolean hasFingerprint = false;
        public boolean hasIssuer = false;
        public boolean hasParsedSuccessfully = false;
        public boolean hasStartTime = false;
        public boolean hasSubject = false;
        public byte[] issuer = WireFormatNano.EMPTY_BYTES;
        public boolean parsedSuccessfully = false;
        public long startTime = 0L;
        public byte[] subject = WireFormatNano.EMPTY_BYTES;
        
        public Element()
        {
          this.cachedSize = -1;
        }
        
        public static Element[] emptyArray()
        {
          if (_emptyArray == null) {}
          synchronized (InternalNano.LAZY_INIT_LOCK)
          {
            if (_emptyArray == null) {
              _emptyArray = new Element[0];
            }
            return _emptyArray;
          }
        }
        
        protected final int computeSerializedSize()
        {
          int i = super.computeSerializedSize();
          if ((this.hasCertificate) || (!Arrays.equals(this.certificate, WireFormatNano.EMPTY_BYTES))) {
            i += CodedOutputByteBufferNano.computeBytesSize(1, this.certificate);
          }
          if ((this.hasParsedSuccessfully) || (this.parsedSuccessfully)) {
            i += 1 + CodedOutputByteBufferNano.computeTagSize(2);
          }
          if ((this.hasSubject) || (!Arrays.equals(this.subject, WireFormatNano.EMPTY_BYTES))) {
            i += CodedOutputByteBufferNano.computeBytesSize(3, this.subject);
          }
          if ((this.hasIssuer) || (!Arrays.equals(this.issuer, WireFormatNano.EMPTY_BYTES))) {
            i += CodedOutputByteBufferNano.computeBytesSize(4, this.issuer);
          }
          if ((this.hasFingerprint) || (!Arrays.equals(this.fingerprint, WireFormatNano.EMPTY_BYTES))) {
            i += CodedOutputByteBufferNano.computeBytesSize(5, this.fingerprint);
          }
          if ((this.hasExpiryTime) || (this.expiryTime != 0L)) {
            i += CodedOutputByteBufferNano.computeInt64Size(6, this.expiryTime);
          }
          if ((this.hasStartTime) || (this.startTime != 0L)) {
            i += CodedOutputByteBufferNano.computeInt64Size(7, this.startTime);
          }
          return i;
        }
        
        public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
          throws IOException
        {
          if ((this.hasCertificate) || (!Arrays.equals(this.certificate, WireFormatNano.EMPTY_BYTES))) {
            paramCodedOutputByteBufferNano.writeBytes(1, this.certificate);
          }
          if ((this.hasParsedSuccessfully) || (this.parsedSuccessfully)) {
            paramCodedOutputByteBufferNano.writeBool(2, this.parsedSuccessfully);
          }
          if ((this.hasSubject) || (!Arrays.equals(this.subject, WireFormatNano.EMPTY_BYTES))) {
            paramCodedOutputByteBufferNano.writeBytes(3, this.subject);
          }
          if ((this.hasIssuer) || (!Arrays.equals(this.issuer, WireFormatNano.EMPTY_BYTES))) {
            paramCodedOutputByteBufferNano.writeBytes(4, this.issuer);
          }
          if ((this.hasFingerprint) || (!Arrays.equals(this.fingerprint, WireFormatNano.EMPTY_BYTES))) {
            paramCodedOutputByteBufferNano.writeBytes(5, this.fingerprint);
          }
          if ((this.hasExpiryTime) || (this.expiryTime != 0L)) {
            paramCodedOutputByteBufferNano.writeInt64(6, this.expiryTime);
          }
          if ((this.hasStartTime) || (this.startTime != 0L)) {
            paramCodedOutputByteBufferNano.writeInt64(7, this.startTime);
          }
          super.writeTo(paramCodedOutputByteBufferNano);
        }
      }
    }
    
    public static final class Digests
      extends MessageNano
    {
      public boolean hasMd5 = false;
      public boolean hasSha1 = false;
      public boolean hasSha256 = false;
      public byte[] md5 = WireFormatNano.EMPTY_BYTES;
      public byte[] sha1 = WireFormatNano.EMPTY_BYTES;
      public byte[] sha256 = WireFormatNano.EMPTY_BYTES;
      
      public Digests()
      {
        this.cachedSize = -1;
      }
      
      protected final int computeSerializedSize()
      {
        int i = super.computeSerializedSize();
        if ((this.hasSha256) || (!Arrays.equals(this.sha256, WireFormatNano.EMPTY_BYTES))) {
          i += CodedOutputByteBufferNano.computeBytesSize(1, this.sha256);
        }
        if ((this.hasSha1) || (!Arrays.equals(this.sha1, WireFormatNano.EMPTY_BYTES))) {
          i += CodedOutputByteBufferNano.computeBytesSize(2, this.sha1);
        }
        if ((this.hasMd5) || (!Arrays.equals(this.md5, WireFormatNano.EMPTY_BYTES))) {
          i += CodedOutputByteBufferNano.computeBytesSize(3, this.md5);
        }
        return i;
      }
      
      public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
        throws IOException
      {
        if ((this.hasSha256) || (!Arrays.equals(this.sha256, WireFormatNano.EMPTY_BYTES))) {
          paramCodedOutputByteBufferNano.writeBytes(1, this.sha256);
        }
        if ((this.hasSha1) || (!Arrays.equals(this.sha1, WireFormatNano.EMPTY_BYTES))) {
          paramCodedOutputByteBufferNano.writeBytes(2, this.sha1);
        }
        if ((this.hasMd5) || (!Arrays.equals(this.md5, WireFormatNano.EMPTY_BYTES))) {
          paramCodedOutputByteBufferNano.writeBytes(3, this.md5);
        }
        super.writeTo(paramCodedOutputByteBufferNano);
      }
    }
    
    public static final class FileInfo
      extends MessageNano
    {
      private static volatile FileInfo[] _emptyArray;
      public CsdClient.ClientDownloadRequest.Digests digests = null;
      public boolean hasName = false;
      public boolean hasVerificationErrors = false;
      public String name = "";
      public int verificationErrors = 0;
      
      public FileInfo()
      {
        this.cachedSize = -1;
      }
      
      public static FileInfo[] emptyArray()
      {
        if (_emptyArray == null) {}
        synchronized (InternalNano.LAZY_INIT_LOCK)
        {
          if (_emptyArray == null) {
            _emptyArray = new FileInfo[0];
          }
          return _emptyArray;
        }
      }
      
      protected final int computeSerializedSize()
      {
        int i = super.computeSerializedSize();
        if ((this.hasName) || (!this.name.equals(""))) {
          i += CodedOutputByteBufferNano.computeStringSize(1, this.name);
        }
        if (this.digests != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(2, this.digests);
        }
        if ((this.hasVerificationErrors) || (this.verificationErrors != 0)) {
          i += CodedOutputByteBufferNano.computeUInt32Size(3, this.verificationErrors);
        }
        return i;
      }
      
      public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
        throws IOException
      {
        if ((this.hasName) || (!this.name.equals(""))) {
          paramCodedOutputByteBufferNano.writeString(1, this.name);
        }
        if (this.digests != null) {
          paramCodedOutputByteBufferNano.writeMessage(2, this.digests);
        }
        if ((this.hasVerificationErrors) || (this.verificationErrors != 0)) {
          paramCodedOutputByteBufferNano.writeUInt32(3, this.verificationErrors);
        }
        super.writeTo(paramCodedOutputByteBufferNano);
      }
    }
    
    public static final class Resource
      extends MessageNano
    {
      private static volatile Resource[] _emptyArray;
      public boolean hasReferrer = false;
      public boolean hasRemoteIp = false;
      public boolean hasType = false;
      public boolean hasUrl = false;
      public String referrer = "";
      public byte[] remoteIp = WireFormatNano.EMPTY_BYTES;
      public int type = 0;
      public String url = "";
      
      public Resource()
      {
        this.cachedSize = -1;
      }
      
      public static Resource[] emptyArray()
      {
        if (_emptyArray == null) {}
        synchronized (InternalNano.LAZY_INIT_LOCK)
        {
          if (_emptyArray == null) {
            _emptyArray = new Resource[0];
          }
          return _emptyArray;
        }
      }
      
      protected final int computeSerializedSize()
      {
        int i = super.computeSerializedSize();
        if ((this.hasUrl) || (!this.url.equals(""))) {
          i += CodedOutputByteBufferNano.computeStringSize(1, this.url);
        }
        if ((this.type != 0) || (this.hasType)) {
          i += CodedOutputByteBufferNano.computeInt32Size(2, this.type);
        }
        if ((this.hasRemoteIp) || (!Arrays.equals(this.remoteIp, WireFormatNano.EMPTY_BYTES))) {
          i += CodedOutputByteBufferNano.computeBytesSize(3, this.remoteIp);
        }
        if ((this.hasReferrer) || (!this.referrer.equals(""))) {
          i += CodedOutputByteBufferNano.computeStringSize(4, this.referrer);
        }
        return i;
      }
      
      public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
        throws IOException
      {
        if ((this.hasUrl) || (!this.url.equals(""))) {
          paramCodedOutputByteBufferNano.writeString(1, this.url);
        }
        if ((this.type != 0) || (this.hasType)) {
          paramCodedOutputByteBufferNano.writeInt32(2, this.type);
        }
        if ((this.hasRemoteIp) || (!Arrays.equals(this.remoteIp, WireFormatNano.EMPTY_BYTES))) {
          paramCodedOutputByteBufferNano.writeBytes(3, this.remoteIp);
        }
        if ((this.hasReferrer) || (!this.referrer.equals(""))) {
          paramCodedOutputByteBufferNano.writeString(4, this.referrer);
        }
        super.writeTo(paramCodedOutputByteBufferNano);
      }
    }
    
    public static final class SignatureInfo
      extends MessageNano
    {
      public CsdClient.ClientDownloadRequest.CertificateChain[] certificateChain = CsdClient.ClientDownloadRequest.CertificateChain.emptyArray();
      public boolean hasTrusted = false;
      public boolean trusted = false;
      
      public SignatureInfo()
      {
        this.cachedSize = -1;
      }
      
      protected final int computeSerializedSize()
      {
        int i = super.computeSerializedSize();
        if ((this.certificateChain != null) && (this.certificateChain.length > 0)) {
          for (int j = 0; j < this.certificateChain.length; j++)
          {
            CsdClient.ClientDownloadRequest.CertificateChain localCertificateChain = this.certificateChain[j];
            if (localCertificateChain != null) {
              i += CodedOutputByteBufferNano.computeMessageSize(1, localCertificateChain);
            }
          }
        }
        if ((this.hasTrusted) || (this.trusted)) {
          i += 1 + CodedOutputByteBufferNano.computeTagSize(2);
        }
        return i;
      }
      
      public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
        throws IOException
      {
        if ((this.certificateChain != null) && (this.certificateChain.length > 0)) {
          for (int i = 0; i < this.certificateChain.length; i++)
          {
            CsdClient.ClientDownloadRequest.CertificateChain localCertificateChain = this.certificateChain[i];
            if (localCertificateChain != null) {
              paramCodedOutputByteBufferNano.writeMessage(1, localCertificateChain);
            }
          }
        }
        if ((this.hasTrusted) || (this.trusted)) {
          paramCodedOutputByteBufferNano.writeBool(2, this.trusted);
        }
        super.writeTo(paramCodedOutputByteBufferNano);
      }
    }
  }
  
  public static final class ClientDownloadResponse
    extends MessageNano
  {
    private static volatile ClientDownloadResponse[] _emptyArray;
    public boolean hasRequestId = false;
    public boolean hasToken = false;
    public boolean hasUploadApk = false;
    public boolean hasVerdict = false;
    public MoreInfo moreInfo = null;
    public byte[] requestId = WireFormatNano.EMPTY_BYTES;
    public byte[] token = WireFormatNano.EMPTY_BYTES;
    public boolean uploadApk = false;
    public int verdict = 0;
    
    public ClientDownloadResponse()
    {
      this.cachedSize = -1;
    }
    
    public static ClientDownloadResponse[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new ClientDownloadResponse[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.verdict != 0) || (this.hasVerdict)) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.verdict);
      }
      if (this.moreInfo != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.moreInfo);
      }
      if ((this.hasToken) || (!Arrays.equals(this.token, WireFormatNano.EMPTY_BYTES))) {
        i += CodedOutputByteBufferNano.computeBytesSize(3, this.token);
      }
      if ((this.hasRequestId) || (!Arrays.equals(this.requestId, WireFormatNano.EMPTY_BYTES))) {
        i += CodedOutputByteBufferNano.computeBytesSize(4, this.requestId);
      }
      if ((this.hasUploadApk) || (this.uploadApk)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(5);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.verdict != 0) || (this.hasVerdict)) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.verdict);
      }
      if (this.moreInfo != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.moreInfo);
      }
      if ((this.hasToken) || (!Arrays.equals(this.token, WireFormatNano.EMPTY_BYTES))) {
        paramCodedOutputByteBufferNano.writeBytes(3, this.token);
      }
      if ((this.hasRequestId) || (!Arrays.equals(this.requestId, WireFormatNano.EMPTY_BYTES))) {
        paramCodedOutputByteBufferNano.writeBytes(4, this.requestId);
      }
      if ((this.hasUploadApk) || (this.uploadApk)) {
        paramCodedOutputByteBufferNano.writeBool(5, this.uploadApk);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
    
    public static final class MoreInfo
      extends MessageNano
    {
      public int alternateLayoutVersion = 0;
      public String description = "";
      public boolean hasAlternateLayoutVersion = false;
      public boolean hasDescription = false;
      public boolean hasUrl = false;
      public String url = "";
      
      public MoreInfo()
      {
        this.cachedSize = -1;
      }
      
      protected final int computeSerializedSize()
      {
        int i = super.computeSerializedSize();
        if ((this.hasDescription) || (!this.description.equals(""))) {
          i += CodedOutputByteBufferNano.computeStringSize(1, this.description);
        }
        if ((this.hasUrl) || (!this.url.equals(""))) {
          i += CodedOutputByteBufferNano.computeStringSize(2, this.url);
        }
        if ((this.hasAlternateLayoutVersion) || (this.alternateLayoutVersion != 0)) {
          i += CodedOutputByteBufferNano.computeInt32Size(4, this.alternateLayoutVersion);
        }
        return i;
      }
      
      public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
        throws IOException
      {
        if ((this.hasDescription) || (!this.description.equals(""))) {
          paramCodedOutputByteBufferNano.writeString(1, this.description);
        }
        if ((this.hasUrl) || (!this.url.equals(""))) {
          paramCodedOutputByteBufferNano.writeString(2, this.url);
        }
        if ((this.hasAlternateLayoutVersion) || (this.alternateLayoutVersion != 0)) {
          paramCodedOutputByteBufferNano.writeInt32(4, this.alternateLayoutVersion);
        }
        super.writeTo(paramCodedOutputByteBufferNano);
      }
    }
  }
  
  public static final class ClientDownloadStatsRequest
    extends MessageNano
  {
    public boolean dontWarnAgain = false;
    public boolean hasDontWarnAgain = false;
    public boolean hasPressedBackButton = false;
    public boolean hasPressedUninstallAction = false;
    public boolean hasToken = false;
    public boolean hasUserDecision = false;
    public boolean pressedBackButton = false;
    public boolean pressedUninstallAction = false;
    public byte[] token = WireFormatNano.EMPTY_BYTES;
    public int userDecision = 0;
    
    public ClientDownloadStatsRequest()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.userDecision != 0) || (this.hasUserDecision)) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.userDecision);
      }
      if ((this.hasToken) || (!Arrays.equals(this.token, WireFormatNano.EMPTY_BYTES))) {
        i += CodedOutputByteBufferNano.computeBytesSize(2, this.token);
      }
      if ((this.hasDontWarnAgain) || (this.dontWarnAgain)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(3);
      }
      if ((this.hasPressedBackButton) || (this.pressedBackButton)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(4);
      }
      if ((this.hasPressedUninstallAction) || (this.pressedUninstallAction)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(5);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.userDecision != 0) || (this.hasUserDecision)) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.userDecision);
      }
      if ((this.hasToken) || (!Arrays.equals(this.token, WireFormatNano.EMPTY_BYTES))) {
        paramCodedOutputByteBufferNano.writeBytes(2, this.token);
      }
      if ((this.hasDontWarnAgain) || (this.dontWarnAgain)) {
        paramCodedOutputByteBufferNano.writeBool(3, this.dontWarnAgain);
      }
      if ((this.hasPressedBackButton) || (this.pressedBackButton)) {
        paramCodedOutputByteBufferNano.writeBool(4, this.pressedBackButton);
      }
      if ((this.hasPressedUninstallAction) || (this.pressedUninstallAction)) {
        paramCodedOutputByteBufferNano.writeBool(5, this.pressedUninstallAction);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class ClientMultiDownloadRequest
    extends MessageNano
  {
    public boolean hasMillisecondsSinceLastAutoscanRun = false;
    public boolean hasMinimumMillisecondsBetweenAutoscanRuns = false;
    public boolean hasUserConsented = false;
    public long millisecondsSinceLastAutoscanRun = 0L;
    public long minimumMillisecondsBetweenAutoscanRuns = 0L;
    public CsdClient.ClientDownloadRequest[] requests = CsdClient.ClientDownloadRequest.emptyArray();
    public boolean userConsented = false;
    
    public ClientMultiDownloadRequest()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.requests != null) && (this.requests.length > 0)) {
        for (int j = 0; j < this.requests.length; j++)
        {
          CsdClient.ClientDownloadRequest localClientDownloadRequest = this.requests[j];
          if (localClientDownloadRequest != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(1, localClientDownloadRequest);
          }
        }
      }
      if ((this.hasUserConsented) || (this.userConsented)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(2);
      }
      if ((this.hasMillisecondsSinceLastAutoscanRun) || (this.millisecondsSinceLastAutoscanRun != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(3, this.millisecondsSinceLastAutoscanRun);
      }
      if ((this.hasMinimumMillisecondsBetweenAutoscanRuns) || (this.minimumMillisecondsBetweenAutoscanRuns != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(4, this.minimumMillisecondsBetweenAutoscanRuns);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.requests != null) && (this.requests.length > 0)) {
        for (int i = 0; i < this.requests.length; i++)
        {
          CsdClient.ClientDownloadRequest localClientDownloadRequest = this.requests[i];
          if (localClientDownloadRequest != null) {
            paramCodedOutputByteBufferNano.writeMessage(1, localClientDownloadRequest);
          }
        }
      }
      if ((this.hasUserConsented) || (this.userConsented)) {
        paramCodedOutputByteBufferNano.writeBool(2, this.userConsented);
      }
      if ((this.hasMillisecondsSinceLastAutoscanRun) || (this.millisecondsSinceLastAutoscanRun != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(3, this.millisecondsSinceLastAutoscanRun);
      }
      if ((this.hasMinimumMillisecondsBetweenAutoscanRuns) || (this.minimumMillisecondsBetweenAutoscanRuns != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(4, this.minimumMillisecondsBetweenAutoscanRuns);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class ClientMultiDownloadResponse
    extends MessageNano
  {
    public CsdClient.ClientDownloadResponse[] responses = CsdClient.ClientDownloadResponse.emptyArray();
    
    public ClientMultiDownloadResponse()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.responses != null) && (this.responses.length > 0)) {
        for (int j = 0; j < this.responses.length; j++)
        {
          CsdClient.ClientDownloadResponse localClientDownloadResponse = this.responses[j];
          if (localClientDownloadResponse != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(1, localClientDownloadResponse);
          }
        }
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.responses != null) && (this.responses.length > 0)) {
        for (int i = 0; i < this.responses.length; i++)
        {
          CsdClient.ClientDownloadResponse localClientDownloadResponse = this.responses[i];
          if (localClientDownloadResponse != null) {
            paramCodedOutputByteBufferNano.writeMessage(1, localClientDownloadResponse);
          }
        }
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class VerifyAppsReport
    extends MessageNano
  {
    public CsdClient.ClientDownloadStatsRequest userDecisionReport = null;
    
    public VerifyAppsReport()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.userDecisionReport != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.userDecisionReport);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.userDecisionReport != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.userDecisionReport);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.vending.verifier.protos.CsdClient
 * JD-Core Version:    0.7.0.1
 */