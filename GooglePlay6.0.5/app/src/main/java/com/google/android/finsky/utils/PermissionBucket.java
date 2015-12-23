package com.google.android.finsky.utils;

import java.util.ArrayList;
import java.util.List;

public final class PermissionBucket
{
  public final int mBucketDescription;
  public final int mBucketIcon;
  public final int mBucketId;
  public final int mBucketTitle;
  public final List<String> mExistingPermissionDescriptions = new ArrayList();
  public final List<String> mNewPermissionDescriptions = new ArrayList();
  
  public PermissionBucket(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mBucketId = paramInt1;
    this.mBucketIcon = paramInt2;
    this.mBucketTitle = paramInt3;
    this.mBucketDescription = paramInt4;
  }
  
  public final boolean hasExistingPermissions()
  {
    return this.mExistingPermissionDescriptions.size() > 0;
  }
  
  public final boolean hasNewPermissions()
  {
    return this.mNewPermissionDescriptions.size() > 0;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.PermissionBucket
 * JD-Core Version:    0.7.0.1
 */