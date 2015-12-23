package com.google.android.finsky.autoupdate;

import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.appstate.PackageStateRepository.PackageState;
import com.google.android.finsky.protos.AppDetails;
import java.util.Comparator;

public final class AutoUpdateEntry
{
  public static final Comparator<AutoUpdateEntry> INSTALL_ORDER = new Comparator() {};
  public static int REASONS_USER_APPROVAL_REQUIRED = 7;
  public final Document mDocument;
  public int mHoldOffReason;
  public int mHoldOffTrigger;
  public int mInstallOptions;
  public int mInstallOrder;
  public int mInstallPriority;
  public String mInstallReason;
  public int mLoggingOptions;
  public final PackageStateRepository.PackageState mPackageState;
  public int mPendingUpdateNotificationOptions;
  
  public AutoUpdateEntry(Document paramDocument, PackageStateRepository.PackageState paramPackageState)
  {
    this.mDocument = paramDocument;
    this.mPackageState = paramPackageState;
  }
  
  public final String toString()
  {
    AppDetails localAppDetails = this.mDocument.getAppDetails();
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = localAppDetails.packageName;
    arrayOfObject[1] = Integer.valueOf(localAppDetails.versionCode);
    return String.format("%s v:%d", arrayOfObject);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.autoupdate.AutoUpdateEntry
 * JD-Core Version:    0.7.0.1
 */