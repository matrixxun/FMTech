package com.google.android.vending.verifier.api;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.config.G;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.play.utils.config.GservicesValue;
import com.google.android.vending.verifier.PackageVerificationLoggingService;
import com.google.android.vending.verifier.protos.CsdClient.ClientDownloadRequest;
import com.google.android.vending.verifier.protos.CsdClient.ClientDownloadRequest.ApkInfo;
import com.google.android.vending.verifier.protos.CsdClient.ClientDownloadRequest.CertificateChain;
import com.google.android.vending.verifier.protos.CsdClient.ClientDownloadRequest.CertificateChain.Element;
import com.google.android.vending.verifier.protos.CsdClient.ClientDownloadRequest.Digests;
import com.google.android.vending.verifier.protos.CsdClient.ClientDownloadRequest.FileInfo;
import com.google.android.vending.verifier.protos.CsdClient.ClientDownloadRequest.Resource;
import com.google.android.vending.verifier.protos.CsdClient.ClientDownloadRequest.SignatureInfo;
import com.google.android.vending.verifier.protos.CsdClient.ClientDownloadStatsRequest;
import com.google.android.vending.verifier.protos.CsdClient.VerifyAppsReport;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;

public final class PackageVerificationApi
{
  public static CsdClient.ClientDownloadRequest buildRequestForApp(byte[] paramArrayOfByte, long paramLong, String paramString1, Integer paramInteger1, byte[][] paramArrayOfByte1, byte[][] paramArrayOfByte2, FileInfo[] paramArrayOfFileInfo, Uri paramUri1, Uri paramUri2, InetAddress paramInetAddress1, InetAddress paramInetAddress2, String[] paramArrayOfString1, byte[][] paramArrayOfByte3, String[] paramArrayOfString2, byte[][] paramArrayOfByte4, String paramString2, Long paramLong1, String paramString3, Integer paramInteger2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, boolean paramBoolean6, boolean paramBoolean7)
  {
    CsdClient.ClientDownloadRequest localClientDownloadRequest = new CsdClient.ClientDownloadRequest();
    if ((paramString1 != null) || (paramInteger1 != null))
    {
      CsdClient.ClientDownloadRequest.ApkInfo localApkInfo = new CsdClient.ClientDownloadRequest.ApkInfo();
      if (paramString1 != null)
      {
        localApkInfo.packageName = paramString1;
        localApkInfo.hasPackageName = true;
      }
      if (paramInteger1 != null)
      {
        localApkInfo.versionCode = paramInteger1.intValue();
        localApkInfo.hasVersionCode = true;
      }
      if (paramArrayOfFileInfo != null)
      {
        localApkInfo.files = new CsdClient.ClientDownloadRequest.FileInfo[paramArrayOfFileInfo.length];
        for (int j = 0; j < paramArrayOfFileInfo.length; j++)
        {
          CsdClient.ClientDownloadRequest.FileInfo[] arrayOfFileInfo = localApkInfo.files;
          FileInfo localFileInfo = paramArrayOfFileInfo[j];
          CsdClient.ClientDownloadRequest.FileInfo localFileInfo1 = new CsdClient.ClientDownloadRequest.FileInfo();
          localFileInfo1.name = localFileInfo.name;
          localFileInfo1.hasName = true;
          localFileInfo1.digests = buildSha256Digest(localFileInfo.digest);
          localFileInfo1.verificationErrors = localFileInfo.verificationErrors;
          localFileInfo1.hasVerificationErrors = true;
          arrayOfFileInfo[j] = localFileInfo1;
        }
      }
      if (paramBoolean1)
      {
        localApkInfo.installedByPlay = true;
        localApkInfo.hasInstalledByPlay = true;
      }
      if (paramBoolean2)
      {
        localApkInfo.forwardLocked = true;
        localApkInfo.hasForwardLocked = true;
      }
      if (paramBoolean3)
      {
        localApkInfo.inStoppedState = true;
        localApkInfo.hasInStoppedState = true;
      }
      if (paramBoolean4)
      {
        localApkInfo.systemApplication = true;
        localApkInfo.hasSystemApplication = true;
      }
      if (paramBoolean5)
      {
        localApkInfo.updatedSystemApplication = true;
        localApkInfo.hasUpdatedSystemApplication = true;
      }
      if (paramBoolean6)
      {
        localApkInfo.debuggable = true;
        localApkInfo.hasDebuggable = true;
      }
      if (paramBoolean7)
      {
        localApkInfo.dontWarnAgain = true;
        localApkInfo.hasDontWarnAgain = true;
      }
      localClientDownloadRequest.apkInfo = localApkInfo;
    }
    if ((paramArrayOfByte1 != null) && (paramArrayOfByte1.length > 0))
    {
      localClientDownloadRequest.signature = createSignatureInfo(paramArrayOfByte1);
      if (paramUri1 == null) {
        break label719;
      }
      localClientDownloadRequest.url = paramUri1.toString();
      localClientDownloadRequest.hasUrl = true;
      if (paramUri2 == null) {
        break label707;
      }
      localClientDownloadRequest.resources = new CsdClient.ClientDownloadRequest.Resource[2];
      label354:
      CsdClient.ClientDownloadRequest.Resource localResource1 = createResource(paramUri1, paramInetAddress1, paramUri2, 0);
      localClientDownloadRequest.resources[0] = localResource1;
      if (paramUri2 != null)
      {
        CsdClient.ClientDownloadRequest.Resource localResource2 = createResource(paramUri2, paramInetAddress2, null, 2);
        localClientDownloadRequest.resources[1] = localResource2;
      }
    }
    for (;;)
    {
      if (paramArrayOfString1 != null) {
        localClientDownloadRequest.originatingPackages = paramArrayOfString1;
      }
      if ((paramArrayOfByte3 != null) && (paramArrayOfByte3.length > 0)) {
        localClientDownloadRequest.originatingSignature = createSignatureInfo(paramArrayOfByte3);
      }
      if (paramArrayOfString2 != null) {
        localClientDownloadRequest.installerPackages = paramArrayOfString2;
      }
      if ((paramArrayOfByte4 != null) && (paramArrayOfByte4.length > 0)) {
        localClientDownloadRequest.installerSignature = createSignatureInfo(paramArrayOfByte4);
      }
      localClientDownloadRequest.length = paramLong;
      localClientDownloadRequest.hasLength = true;
      localClientDownloadRequest.digests = buildSha256Digest(paramArrayOfByte);
      localClientDownloadRequest.downloadType = 2;
      localClientDownloadRequest.hasDownloadType = true;
      if (paramString2 != null)
      {
        localClientDownloadRequest.locale = paramString2;
        localClientDownloadRequest.hasLocale = true;
      }
      if (paramLong1 != null)
      {
        localClientDownloadRequest.androidId = paramLong1.longValue();
        localClientDownloadRequest.hasAndroidId = true;
      }
      if (!TextUtils.isEmpty(paramString3))
      {
        localClientDownloadRequest.safetyNetId = paramString3;
        localClientDownloadRequest.hasSafetyNetId = true;
      }
      if (paramInteger2 != null) {}
      try
      {
        localClientDownloadRequest.requestId = Integer.toHexString(paramInteger2.intValue()).getBytes("UTF-8");
        localClientDownloadRequest.hasRequestId = true;
        return localClientDownloadRequest;
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException)
      {
        CsdClient.ClientDownloadRequest.SignatureInfo localSignatureInfo;
        int i;
        throw new RuntimeException(localUnsupportedEncodingException);
      }
      if ((paramArrayOfByte2 == null) || (paramArrayOfByte2.length <= 0)) {
        break;
      }
      localSignatureInfo = new CsdClient.ClientDownloadRequest.SignatureInfo();
      localSignatureInfo.certificateChain = new CsdClient.ClientDownloadRequest.CertificateChain[paramArrayOfByte2.length];
      for (i = 0; i < paramArrayOfByte2.length; i++)
      {
        CsdClient.ClientDownloadRequest.CertificateChain localCertificateChain = new CsdClient.ClientDownloadRequest.CertificateChain();
        CsdClient.ClientDownloadRequest.CertificateChain.Element localElement = new CsdClient.ClientDownloadRequest.CertificateChain.Element();
        localElement.fingerprint = paramArrayOfByte2[i];
        localElement.hasFingerprint = true;
        localCertificateChain.element = new CsdClient.ClientDownloadRequest.CertificateChain.Element[] { localElement };
        localSignatureInfo.certificateChain[i] = localCertificateChain;
      }
      localClientDownloadRequest.signature = localSignatureInfo;
      break;
      label707:
      localClientDownloadRequest.resources = new CsdClient.ClientDownloadRequest.Resource[1];
      break label354;
      label719:
      localClientDownloadRequest.url = "";
      localClientDownloadRequest.hasUrl = true;
    }
  }
  
  private static CsdClient.ClientDownloadRequest.Digests buildSha256Digest(byte[] paramArrayOfByte)
  {
    CsdClient.ClientDownloadRequest.Digests localDigests = new CsdClient.ClientDownloadRequest.Digests();
    localDigests.sha256 = paramArrayOfByte;
    localDigests.hasSha256 = true;
    return localDigests;
  }
  
  private static CsdClient.ClientDownloadRequest.Resource createResource(Uri paramUri1, InetAddress paramInetAddress, Uri paramUri2, int paramInt)
  {
    CsdClient.ClientDownloadRequest.Resource localResource = new CsdClient.ClientDownloadRequest.Resource();
    localResource.url = paramUri1.toString();
    localResource.hasUrl = true;
    localResource.type = paramInt;
    localResource.hasType = true;
    if (paramUri2 != null)
    {
      localResource.referrer = paramUri2.toString();
      localResource.hasReferrer = true;
    }
    if (paramInetAddress != null) {}
    try
    {
      localResource.remoteIp = paramInetAddress.getHostAddress().getBytes("UTF-8");
      localResource.hasRemoteIp = true;
      return localResource;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      throw new RuntimeException(localUnsupportedEncodingException);
    }
  }
  
  private static CsdClient.ClientDownloadRequest.SignatureInfo createSignatureInfo(byte[][] paramArrayOfByte)
  {
    CsdClient.ClientDownloadRequest.SignatureInfo localSignatureInfo = new CsdClient.ClientDownloadRequest.SignatureInfo();
    localSignatureInfo.certificateChain = new CsdClient.ClientDownloadRequest.CertificateChain[paramArrayOfByte.length];
    for (int i = 0; i < paramArrayOfByte.length; i++)
    {
      CsdClient.ClientDownloadRequest.CertificateChain localCertificateChain = new CsdClient.ClientDownloadRequest.CertificateChain();
      CsdClient.ClientDownloadRequest.CertificateChain.Element localElement = new CsdClient.ClientDownloadRequest.CertificateChain.Element();
      localElement.certificate = paramArrayOfByte[i];
      localElement.hasCertificate = true;
      localCertificateChain.element = new CsdClient.ClientDownloadRequest.CertificateChain.Element[] { localElement };
      localSignatureInfo.certificateChain[i] = localCertificateChain;
    }
    return localSignatureInfo;
  }
  
  public static void reportUserDecision(Context paramContext, String paramString, int paramInt, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, byte[] paramArrayOfByte)
  {
    CsdClient.ClientDownloadStatsRequest localClientDownloadStatsRequest = new CsdClient.ClientDownloadStatsRequest();
    if (paramArrayOfByte != null)
    {
      localClientDownloadStatsRequest.token = paramArrayOfByte;
      localClientDownloadStatsRequest.hasToken = true;
    }
    localClientDownloadStatsRequest.userDecision = paramInt;
    localClientDownloadStatsRequest.hasUserDecision = true;
    if (paramBoolean1)
    {
      localClientDownloadStatsRequest.dontWarnAgain = true;
      localClientDownloadStatsRequest.hasDontWarnAgain = true;
    }
    if (paramBoolean2)
    {
      localClientDownloadStatsRequest.pressedBackButton = true;
      localClientDownloadStatsRequest.hasPressedBackButton = true;
    }
    if (paramBoolean3)
    {
      localClientDownloadStatsRequest.pressedUninstallAction = true;
      localClientDownloadStatsRequest.hasPressedUninstallAction = true;
    }
    if (((Boolean)G.verifyAppsLogToClearcut.get()).booleanValue())
    {
      CsdClient.VerifyAppsReport localVerifyAppsReport = new CsdClient.VerifyAppsReport();
      localVerifyAppsReport.userDecisionReport = localClientDownloadStatsRequest;
      PackageVerificationLoggingService.reportUserResponse(paramContext, localVerifyAppsReport);
    }
    if (((Boolean)G.verifyAppsLogUserDecisionToCsdServer.get()).booleanValue())
    {
      PackageVerificationStatsRequest localPackageVerificationStatsRequest = new PackageVerificationStatsRequest("https://safebrowsing.google.com/safebrowsing/clientreport/download-stat", new Response.ErrorListener()
      {
        public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
        {
          Object[] arrayOfObject = new Object[2];
          arrayOfObject[0] = this.val$packageName;
          arrayOfObject[1] = paramAnonymousVolleyError;
          FinskyLog.d("Verification feedback for package=%s: error response %s", arrayOfObject);
        }
      }, localClientDownloadStatsRequest);
      FinskyApp.get().mRequestQueue.add(localPackageVerificationStatsRequest);
    }
  }
  
  public static final class FileInfo
  {
    public final byte[] digest;
    public final String name;
    public final int verificationErrors;
    
    public FileInfo(String paramString, byte[] paramArrayOfByte, int paramInt)
    {
      this.name = paramString;
      this.digest = paramArrayOfByte;
      this.verificationErrors = paramInt;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.vending.verifier.api.PackageVerificationApi
 * JD-Core Version:    0.7.0.1
 */