package com.google.android.finsky.download.inlineappinstaller;

import android.accounts.Account;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.DfeUtils;
import com.google.android.finsky.api.model.DfeDetails;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.api.model.OnDataChangedListener;
import com.google.android.finsky.fragments.SidecarFragment;
import com.google.android.finsky.installer.InstallerListener;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.receivers.Installer;
import com.google.android.finsky.utils.IntentUtils;
import com.google.android.finsky.utils.LibraryUtils;
import com.google.android.finsky.utils.ParcelableProto;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class InlineConsumptionAppInstallerSidecar
  extends SidecarFragment
  implements Response.ErrorListener, OnDataChangedListener, InstallerListener
{
  private String mAccount;
  public Document mAppDoc;
  private DfeDetails mDfeDetails;
  String mErrorMsg;
  public final List<InstallerStepListener> mInstallStepListeners = new ArrayList();
  public int mMostRecentInstallEvent = -1;
  
  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Bundle localBundle = this.mArguments;
    this.mAccount = localBundle.getString("authAccount");
    this.mDfeDetails = new DfeDetails(FinskyApp.get().getDfeApi(this.mAccount), DfeUtils.createDetailsUrlFromId(IntentUtils.getPackageName(((DocV2)ParcelableProto.getProtoFromBundle(localBundle, "InlineConsumptionAppInstallerSidecar.mediaDoc")).backendId)));
    this.mDfeDetails.addDataChangedListener(this);
    this.mDfeDetails.addErrorListener(this);
  }
  
  public final void onDataChanged()
  {
    this.mAppDoc = this.mDfeDetails.getDocument();
    if (this.mAppDoc == null)
    {
      switchToErrorState(getString(2131362185));
      return;
    }
    setState(5, 0);
  }
  
  public final void onErrorResponse(VolleyError paramVolleyError)
  {
    switchToErrorState(getString(2131362185));
  }
  
  public final void onInstallPackageEvent(String paramString, int paramInt1, int paramInt2)
  {
    if ((paramString == null) || (!paramString.equals(this.mAppDoc.mDocument.backendDocid))) {}
    for (;;)
    {
      return;
      this.mMostRecentInstallEvent = paramInt1;
      Iterator localIterator = this.mInstallStepListeners.iterator();
      while (localIterator.hasNext()) {
        ((InstallerStepListener)localIterator.next()).onInstallPackageEvent(paramInt1);
      }
    }
  }
  
  public final void onStart()
  {
    super.onStart();
    this.mDfeDetails.addDataChangedListener(this);
    this.mDfeDetails.addErrorListener(this);
  }
  
  public final void onStop()
  {
    this.mDfeDetails.removeDataChangedListener(this);
    this.mDfeDetails.removeErrorListener(this);
    super.onStop();
  }
  
  public final void switchToDownloadStep()
  {
    Account localAccount = LibraryUtils.getOwnerWithCurrentAccount(this.mAppDoc, FinskyApp.get().mLibraries, new Account(this.mAccount, "com.google"));
    Installer localInstaller = FinskyApp.get().mInstaller;
    localInstaller.requestInstall(this.mAppDoc.mDocument.backendDocid, this.mAppDoc.getVersionCode(), localAccount.name, this.mAppDoc.mDocument.title, false, "content_dependency", 1, localInstaller.extractInstallLocation(this.mAppDoc));
    localInstaller.addListener(this);
    setState(7, 0);
  }
  
  public final void switchToErrorState(String paramString)
  {
    this.mErrorMsg = paramString;
    setState(3, 0);
  }
  
  public static abstract interface InstallerStepListener
  {
    public abstract void onInstallPackageEvent(int paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.download.inlineappinstaller.InlineConsumptionAppInstallerSidecar
 * JD-Core Version:    0.7.0.1
 */