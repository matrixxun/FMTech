package com.google.android.vending.verifier;

public final class PackageVerificationData
{
  public final long mApkLength;
  public final long mCacheFingerprint;
  public byte[][] mCertFingerprints;
  public final boolean mForwardLocked;
  public boolean mInStoppedState;
  public boolean mInstalledByPlay;
  public final String mPackageName;
  public final byte[] mSha256Digest;
  public final boolean mSuppressUserWarning;
  public boolean mSystemApp;
  public boolean mUpdatedSystemApp;
  
  public PackageVerificationData(String paramString, long paramLong1, byte[] paramArrayOfByte, long paramLong2, boolean paramBoolean1, boolean paramBoolean2)
  {
    this.mPackageName = paramString;
    this.mCacheFingerprint = paramLong1;
    this.mSha256Digest = ((byte[])paramArrayOfByte.clone());
    this.mApkLength = paramLong2;
    this.mForwardLocked = paramBoolean1;
    this.mSuppressUserWarning = paramBoolean2;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.vending.verifier.PackageVerificationData
 * JD-Core Version:    0.7.0.1
 */