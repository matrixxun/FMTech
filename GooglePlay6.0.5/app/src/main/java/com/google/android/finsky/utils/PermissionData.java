package com.google.android.finsky.utils;

public final class PermissionData
{
  public final boolean mForcePermissionPrompt;
  public final int mOtherBucketIndex;
  public final PermissionBucket[] mPermissionsBuckets;
  
  public PermissionData(PermissionBucket[] paramArrayOfPermissionBucket, int paramInt, boolean paramBoolean)
  {
    this.mPermissionsBuckets = paramArrayOfPermissionBucket;
    this.mOtherBucketIndex = paramInt;
    this.mForcePermissionPrompt = paramBoolean;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.PermissionData
 * JD-Core Version:    0.7.0.1
 */