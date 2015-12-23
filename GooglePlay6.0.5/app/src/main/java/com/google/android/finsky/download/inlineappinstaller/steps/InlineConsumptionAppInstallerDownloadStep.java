package com.google.android.finsky.download.inlineappinstaller.steps;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.adapters.DownloadProgressHelper;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.billing.lightpurchase.multistep.StepFragment;
import com.google.android.finsky.download.inlineappinstaller.InlineConsumptionAppInstallerFragment;
import com.google.android.finsky.download.inlineappinstaller.InlineConsumptionAppInstallerSidecar;
import com.google.android.finsky.download.inlineappinstaller.InlineConsumptionAppInstallerSidecar.InstallerStepListener;
import com.google.android.finsky.fragments.SidecarFragment;
import com.google.android.finsky.navigationmanager.ConsumptionUtils;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.receivers.Installer;
import com.google.android.finsky.receivers.Installer.InstallerProgressReport;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.UiUtils;
import java.util.List;

public final class InlineConsumptionAppInstallerDownloadStep
  extends StepFragment<InlineConsumptionAppInstallerFragment>
  implements View.OnClickListener, InlineConsumptionAppInstallerSidecar.InstallerStepListener
{
  private Document mAppDoc;
  private ImageView mCancelDownload;
  private TextView mDownloadPercentageView;
  private View mMainView;
  private ProgressBar mProgress;
  private TextView mStatusView;
  private final PlayStore.PlayStoreUiElement mUiElement = FinskyEventLog.obtainPlayStoreUiElement(5101);
  
  private String getBackendDocId()
  {
    return this.mAppDoc.mDocument.backendDocid;
  }
  
  public static InlineConsumptionAppInstallerDownloadStep newInstance(Document paramDocument)
  {
    InlineConsumptionAppInstallerDownloadStep localInlineConsumptionAppInstallerDownloadStep = new InlineConsumptionAppInstallerDownloadStep();
    Bundle localBundle = new Bundle();
    localBundle.putParcelable("appDoc", paramDocument);
    localInlineConsumptionAppInstallerDownloadStep.setArguments(localBundle);
    return localInlineConsumptionAppInstallerDownloadStep;
  }
  
  public final boolean allowButtonBar()
  {
    return false;
  }
  
  public final String getContinueButtonLabel(Resources paramResources)
  {
    return null;
  }
  
  public final PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mUiElement;
  }
  
  public final void onClick(View paramView)
  {
    if (paramView == this.mCancelDownload)
    {
      String str = this.mAppDoc.mDocument.backendDocid;
      if (str != null) {
        FinskyApp.get().mInstaller.cancel(str);
      }
      ((InlineConsumptionAppInstallerFragment)this.mParentFragment).logClick(5106, this);
      ((InlineConsumptionAppInstallerFragment)this.mParentFragment).finish(false);
    }
  }
  
  public final void onContinueButtonClicked()
  {
    FinskyLog.wtf("onContinueButtonClicked clicked", new Object[0]);
  }
  
  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    this.mAppDoc = ((Document)this.mArguments.getParcelable("appDoc"));
    this.mMainView = paramLayoutInflater.inflate(2130968794, paramViewGroup, false);
    TextView localTextView = (TextView)this.mMainView.findViewById(2131755173);
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = this.mAppDoc.mDocument.title;
    localTextView.setText(getString(2131362251, arrayOfObject));
    this.mProgress = ((ProgressBar)this.mMainView.findViewById(2131755612));
    this.mCancelDownload = ((ImageView)this.mMainView.findViewById(2131755387));
    this.mCancelDownload.setOnClickListener(this);
    this.mDownloadPercentageView = ((TextView)this.mMainView.findViewById(2131755388));
    this.mStatusView = ((TextView)this.mMainView.findViewById(2131755611));
    ConsumptionUtils.maybeBindAppIcon(this.mAppDoc, this.mMainView);
    InlineConsumptionAppInstallerFragment localInlineConsumptionAppInstallerFragment = (InlineConsumptionAppInstallerFragment)this.mParentFragment;
    int i = localInlineConsumptionAppInstallerFragment.mSidecar.mMostRecentInstallEvent;
    int j;
    if (i == -1)
    {
      j = FinskyApp.get().mInstaller.getState(this.mAppDoc.mDocument.backendDocid);
      if (j != 2) {
        break label250;
      }
      i = 1;
    }
    label309:
    for (;;)
    {
      if (i != -1) {
        onInstallPackageEvent(i);
      }
      localInlineConsumptionAppInstallerFragment.mSidecar.mInstallStepListeners.add(this);
      return this.mMainView;
      label250:
      if (j == 3)
      {
        i = 4;
      }
      else
      {
        if (!ConsumptionUtils.isConsumptionAppNeeded(localInlineConsumptionAppInstallerFragment.getContext(), new Document(localInlineConsumptionAppInstallerFragment.mMediaDoc), localInlineConsumptionAppInstallerFragment.mAccount)) {}
        for (int k = 1;; k = 0)
        {
          if (k == 0) {
            break label309;
          }
          i = 6;
          break;
        }
      }
    }
  }
  
  public final void onDestroyView()
  {
    ((InlineConsumptionAppInstallerFragment)this.mParentFragment).mSidecar.mInstallStepListeners.remove(this);
    super.onDestroyView();
  }
  
  public final void onInstallPackageEvent(int paramInt)
  {
    int i = 0;
    int j;
    switch (paramInt)
    {
    default: 
      j = 2131362243;
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = getBackendDocId();
      arrayOfObject2[1] = Integer.valueOf(paramInt);
      FinskyLog.wtf("Install package event %s: unexpected %d", arrayOfObject2);
    }
    for (;;)
    {
      if (i != 0)
      {
        this.mProgress.setVisibility(4);
        InlineConsumptionAppInstallerFragment localInlineConsumptionAppInstallerFragment = (InlineConsumptionAppInstallerFragment)this.mParentFragment;
        String str = getString(j);
        localInlineConsumptionAppInstallerFragment.mSidecar.switchToErrorState(str);
      }
      return;
      Installer.InstallerProgressReport localInstallerProgressReport = FinskyApp.get().mInstaller.getProgress(getBackendDocId());
      DownloadProgressHelper.configureDownloadProgressUi(getContext(), localInstallerProgressReport, this.mStatusView, this.mDownloadPercentageView, this.mProgress);
      ImageView localImageView = this.mCancelDownload;
      if (paramInt == 1) {}
      for (int k = 0;; k = 8)
      {
        localImageView.setVisibility(k);
        j = 0;
        i = 0;
        break;
      }
      j = 2131361922;
      this.mStatusView.setText(2131361922);
      i = 0;
      continue;
      if (paramInt == 5) {}
      for (j = 2131362243;; j = 2131362131)
      {
        this.mStatusView.setText(j);
        i = 1;
        break;
      }
      InlineConsumptionAppInstallerSidecar localInlineConsumptionAppInstallerSidecar = ((InlineConsumptionAppInstallerFragment)this.mParentFragment).mSidecar;
      if (localInlineConsumptionAppInstallerSidecar.mState != 7)
      {
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = Integer.valueOf(localInlineConsumptionAppInstallerSidecar.mState);
        FinskyLog.wtf("switchToDownloadStep() called in state %d", arrayOfObject1);
      }
      localInlineConsumptionAppInstallerSidecar.setState(8, 0);
      j = 0;
      i = 0;
    }
  }
  
  public final void onResume()
  {
    super.onResume();
    UiUtils.sendAccessibilityEventWithText(getContext(), getString(2131362251), this.mMainView);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.download.inlineappinstaller.steps.InlineConsumptionAppInstallerDownloadStep
 * JD-Core Version:    0.7.0.1
 */