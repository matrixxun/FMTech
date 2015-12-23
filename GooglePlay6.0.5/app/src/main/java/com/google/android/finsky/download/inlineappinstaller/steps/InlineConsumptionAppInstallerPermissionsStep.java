package com.google.android.finsky.download.inlineappinstaller.steps;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.billing.lightpurchase.multistep.StepFragment;
import com.google.android.finsky.download.inlineappinstaller.InlineConsumptionAppInstallerFragment;
import com.google.android.finsky.download.inlineappinstaller.InlineConsumptionAppInstallerSidecar;
import com.google.android.finsky.layout.AppPermissionAdapter;
import com.google.android.finsky.layout.AppSecurityPermissions;
import com.google.android.finsky.navigationmanager.ConsumptionUtils;
import com.google.android.finsky.protos.AppDetails;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.PermissionsBucketer;
import com.google.android.finsky.utils.UiUtils;

public final class InlineConsumptionAppInstallerPermissionsStep
  extends StepFragment<InlineConsumptionAppInstallerFragment>
{
  private Document mAppDoc;
  private View mMainView;
  private AppSecurityPermissions mPermissionsView;
  private final PlayStore.PlayStoreUiElement mUiElement = FinskyEventLog.obtainPlayStoreUiElement(5111);
  
  public final String getContinueButtonLabel(Resources paramResources)
  {
    return paramResources.getString(2131361811);
  }
  
  public final PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mUiElement;
  }
  
  public final void onContinueButtonClicked()
  {
    ((InlineConsumptionAppInstallerFragment)this.mParentFragment).logClick(5112, this);
    ((InlineConsumptionAppInstallerFragment)this.mParentFragment).mSidecar.switchToDownloadStep();
  }
  
  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    this.mAppDoc = ((Document)this.mArguments.getParcelable("appDoc"));
    this.mMainView = paramLayoutInflater.inflate(2130968796, paramViewGroup, false);
    this.mPermissionsView = ((AppSecurityPermissions)this.mMainView.findViewById(2131755615));
    View localView = this.mMainView;
    String str = this.mAppDoc.mDocument.backendDocid;
    String[] arrayOfString = this.mAppDoc.getAppDetails().permission;
    ((TextView)localView.findViewById(2131755173)).setText(this.mAppDoc.mDocument.title);
    TextView localTextView = (TextView)localView.findViewById(2131755614);
    localTextView.setVisibility(0);
    ConsumptionUtils.maybeBindAppIcon(this.mAppDoc, localView);
    boolean bool = PermissionsBucketer.hasAcceptedBuckets(FinskyApp.get().mInstallerDataStore, str);
    AppPermissionAdapter localAppPermissionAdapter = new AppPermissionAdapter(getActivity(), str, arrayOfString, bool);
    if ((localAppPermissionAdapter.mIsAppInstalled) && (bool)) {}
    for (int i = 2131361840;; i = 2131362361)
    {
      localTextView.setText(i);
      this.mPermissionsView.bindInfo(localAppPermissionAdapter, this.mAppDoc.mDocument.title, paramBundle);
      this.mPermissionsView.requestFocus();
      return this.mMainView;
    }
  }
  
  public final void onResume()
  {
    super.onResume();
    UiUtils.sendAccessibilityEventWithText(getContext(), getString(2131362536), this.mMainView);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.download.inlineappinstaller.steps.InlineConsumptionAppInstallerPermissionsStep
 * JD-Core Version:    0.7.0.1
 */