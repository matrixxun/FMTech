package com.google.android.finsky.appstate;

import java.util.Arrays;
import java.util.Collection;

public abstract interface PackageStateRepository
{
  public abstract boolean canLaunch(String paramString);
  
  public abstract PackageState get(String paramString);
  
  public abstract Collection<PackageState> getAllBlocking();
  
  public abstract int getVersionCode(String paramString);
  
  public abstract String getVersionName(String paramString);
  
  public abstract void invalidate(String paramString);
  
  public static final class PackageState
  {
    public final String[] certificateHashes;
    public final int installedVersion;
    public final boolean isActiveDeviceAdmin;
    public final boolean isDisabled;
    public final boolean isDisabledByUser;
    public final boolean isSystemApp;
    public final boolean isUpdatedSystemApp;
    public final String packageName;
    
    public PackageState(String paramString, String[] paramArrayOfString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, int paramInt, boolean paramBoolean5)
    {
      this.packageName = paramString;
      this.certificateHashes = paramArrayOfString;
      this.isSystemApp = paramBoolean1;
      this.isUpdatedSystemApp = paramBoolean2;
      this.isDisabled = paramBoolean3;
      this.isDisabledByUser = paramBoolean4;
      this.installedVersion = paramInt;
      this.isActiveDeviceAdmin = paramBoolean5;
    }
    
    public final boolean equals(Object paramObject)
    {
      if (this == paramObject) {}
      PackageState localPackageState;
      do
      {
        return true;
        if (!(paramObject instanceof PackageState)) {
          return false;
        }
        localPackageState = (PackageState)paramObject;
        if (this.installedVersion != localPackageState.installedVersion) {
          return false;
        }
        if (this.isActiveDeviceAdmin != localPackageState.isActiveDeviceAdmin) {
          return false;
        }
        if (this.isDisabled != localPackageState.isDisabled) {
          return false;
        }
        if (this.isDisabledByUser != localPackageState.isDisabledByUser) {
          return false;
        }
        if (this.isSystemApp != localPackageState.isSystemApp) {
          return false;
        }
        if (this.isUpdatedSystemApp != localPackageState.isUpdatedSystemApp) {
          return false;
        }
        if (!Arrays.equals(this.certificateHashes, localPackageState.certificateHashes)) {
          return false;
        }
        if (this.packageName == null) {
          break;
        }
      } while (this.packageName.equals(localPackageState.packageName));
      for (;;)
      {
        return false;
        if (localPackageState.packageName == null) {
          break;
        }
      }
    }
    
    public final int hashCode()
    {
      int i = 1;
      int j;
      int m;
      label38:
      int i1;
      label65:
      int i3;
      label85:
      int i5;
      label105:
      int i7;
      label125:
      int i8;
      if (this.packageName != null)
      {
        j = this.packageName.hashCode();
        int k = j * 31;
        if (this.certificateHashes == null) {
          break label152;
        }
        m = Arrays.hashCode(this.certificateHashes);
        int n = 31 * (31 * (k + m) + this.installedVersion);
        if (!this.isSystemApp) {
          break label158;
        }
        i1 = i;
        int i2 = 31 * (n + i1);
        if (!this.isUpdatedSystemApp) {
          break label164;
        }
        i3 = i;
        int i4 = 31 * (i2 + i3);
        if (!this.isDisabled) {
          break label170;
        }
        i5 = i;
        int i6 = 31 * (i4 + i5);
        if (!this.isDisabledByUser) {
          break label176;
        }
        i7 = i;
        i8 = 31 * (i6 + i7);
        if (!this.isActiveDeviceAdmin) {
          break label182;
        }
      }
      for (;;)
      {
        return i8 + i;
        j = 0;
        break;
        label152:
        m = 0;
        break label38;
        label158:
        i1 = 0;
        break label65;
        label164:
        i3 = 0;
        break label85;
        label170:
        i5 = 0;
        break label105;
        label176:
        i7 = 0;
        break label125;
        label182:
        i = 0;
      }
    }
    
    public final String toString()
    {
      Object[] arrayOfObject = new Object[4];
      arrayOfObject[0] = this.packageName;
      arrayOfObject[1] = Integer.valueOf(this.installedVersion);
      arrayOfObject[2] = Boolean.valueOf(this.isSystemApp);
      arrayOfObject[3] = Arrays.deepToString(this.certificateHashes);
      return String.format("(packageName=%s,installedVersion=%d,isSystemApp=%s,certificateHashes=%s)", arrayOfObject);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.appstate.PackageStateRepository
 * JD-Core Version:    0.7.0.1
 */