package com.google.android.finsky.download.inlineappinstaller.steps;

import android.content.res.Resources;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.billing.lightpurchase.multistep.StepFragment;
import com.google.android.finsky.download.inlineappinstaller.InlineConsumptionAppInstallerFragment;
import com.google.android.finsky.download.inlineappinstaller.InlineConsumptionAppInstallerSidecar;
import com.google.android.finsky.navigationmanager.ConsumptionUtils;
import com.google.android.finsky.protos.Acquisition.SuccessInfo;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.ParcelableProto;
import com.google.android.finsky.utils.UiUtils;

public final class InlineConsumptionAppInstallerShouldInstallStep
  extends StepFragment<InlineConsumptionAppInstallerFragment>
{
  private View mMainView;
  private DocV2 mMediaDoc;
  private final PlayStore.PlayStoreUiElement mUiElement = FinskyEventLog.obtainPlayStoreUiElement(5101);
  
  public final String getContinueButtonLabel(Resources paramResources)
  {
    return paramResources.getString(2131362224);
  }
  
  public final PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mUiElement;
  }
  
  public final void onContinueButtonClicked()
  {
    ((InlineConsumptionAppInstallerFragment)this.mParentFragment).logClick(5112, this);
    InlineConsumptionAppInstallerFragment localInlineConsumptionAppInstallerFragment = (InlineConsumptionAppInstallerFragment)this.mParentFragment;
    Document localDocument = localInlineConsumptionAppInstallerFragment.mSidecar.mAppDoc;
    if ((Build.VERSION.SDK_INT > 22) && (localDocument.getTargetSdk() > 22))
    {
      localInlineConsumptionAppInstallerFragment.mSidecar.switchToDownloadStep();
      return;
    }
    localInlineConsumptionAppInstallerFragment.mSidecar.setState(6, 0);
  }
  
  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    Bundle localBundle = this.mArguments;
    this.mMediaDoc = ((DocV2)ParcelableProto.getProtoFromBundle(localBundle, "mediaDoc"));
    Acquisition.SuccessInfo localSuccessInfo = (Acquisition.SuccessInfo)ParcelableProto.getProtoFromBundle(localBundle, "installStep");
    this.mMainView = paramLayoutInflater.inflate(2130968798, paramViewGroup, false);
    TextView localTextView1 = (TextView)this.mMainView.findViewById(2131755348);
    TextView localTextView2 = (TextView)this.mMainView.findViewById(2131755618);
    int i = this.mMediaDoc.backendId;
    int j;
    switch (i)
    {
    default: 
      j = ConsumptionUtils.getConsumptionAppRequiredString(i);
      if (localSuccessInfo != null)
      {
        localTextView1.setText(Html.fromHtml(localSuccessInfo.messageHtml));
        localTextView2.setText(Html.fromHtml(getString(j)));
      }
      break;
    }
    for (TextView localTextView3 = localTextView2;; localTextView3 = localTextView1)
    {
      localTextView3.setOnClickListener(new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          InlineConsumptionAppInstallerFragment localInlineConsumptionAppInstallerFragment = (InlineConsumptionAppInstallerFragment)InlineConsumptionAppInstallerShouldInstallStep.access$000(InlineConsumptionAppInstallerShouldInstallStep.this);
          localInlineConsumptionAppInstallerFragment.mShouldGoToAppDetailsUponExit = true;
          localInlineConsumptionAppInstallerFragment.finish(false);
        }
      });
      return this.mMainView;
      j = 2131361907;
      break;
      j = 2131362369;
      break;
      localTextView1.setText(Html.fromHtml(getString(j)));
      localTextView2.setVisibility(8);
    }
  }
  
  public final void onResume()
  {
    super.onResume();
    UiUtils.sendAccessibilityEventWithText(getContext(), getString(ConsumptionUtils.getConsumptionAppRequiredString(this.mMediaDoc.backendId)), this.mMainView);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.download.inlineappinstaller.steps.InlineConsumptionAppInstallerShouldInstallStep
 * JD-Core Version:    0.7.0.1
 */