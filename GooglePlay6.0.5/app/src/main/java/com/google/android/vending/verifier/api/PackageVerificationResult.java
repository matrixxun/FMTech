package com.google.android.vending.verifier.api;

import android.net.Uri;
import com.google.android.vending.verifier.protos.CsdClient.ClientDownloadResponse;
import com.google.android.vending.verifier.protos.CsdClient.ClientDownloadResponse.MoreInfo;

public final class PackageVerificationResult
{
  public final int alternateLayoutVersion;
  public final String description;
  public final Uri moreInfoUri;
  public final byte[] token;
  public final boolean uploadApk;
  public final int verdict;
  
  private PackageVerificationResult(int paramInt1, String paramString, Uri paramUri, byte[] paramArrayOfByte, boolean paramBoolean, int paramInt2)
  {
    this.verdict = paramInt1;
    this.description = paramString;
    this.moreInfoUri = paramUri;
    this.token = paramArrayOfByte;
    this.uploadApk = paramBoolean;
    this.alternateLayoutVersion = paramInt2;
  }
  
  public static PackageVerificationResult fromResponse(CsdClient.ClientDownloadResponse paramClientDownloadResponse)
  {
    CsdClient.ClientDownloadResponse.MoreInfo localMoreInfo = paramClientDownloadResponse.moreInfo;
    String str1 = null;
    Uri localUri = null;
    int i = 0;
    if (localMoreInfo != null)
    {
      str1 = localMoreInfo.description;
      i = localMoreInfo.alternateLayoutVersion;
      String str2 = localMoreInfo.url;
      localUri = null;
      if (str2 != null) {
        localUri = Uri.parse(localMoreInfo.url);
      }
    }
    boolean bool = paramClientDownloadResponse.uploadApk;
    return new PackageVerificationResult(paramClientDownloadResponse.verdict, str1, localUri, paramClientDownloadResponse.token, bool, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.vending.verifier.api.PackageVerificationResult
 * JD-Core Version:    0.7.0.1
 */