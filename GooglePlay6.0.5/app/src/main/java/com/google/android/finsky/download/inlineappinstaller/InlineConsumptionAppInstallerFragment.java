package com.google.android.finsky.download.inlineappinstaller;

import android.accounts.Account;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.billing.lightpurchase.multistep.MultiStepFragment;
import com.google.android.finsky.download.inlineappinstaller.steps.InlineConsumptionAppInstallerDownloadStep;
import com.google.android.finsky.download.inlineappinstaller.steps.InlineConsumptionAppInstallerErrorStep;
import com.google.android.finsky.download.inlineappinstaller.steps.InlineConsumptionAppInstallerPermissionsStep;
import com.google.android.finsky.download.inlineappinstaller.steps.InlineConsumptionAppInstallerReadyToReadStep;
import com.google.android.finsky.download.inlineappinstaller.steps.InlineConsumptionAppInstallerShouldInstallStep;
import com.google.android.finsky.fragments.SidecarFragment;
import com.google.android.finsky.fragments.SidecarFragment.Listener;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.protos.Acquisition.SuccessInfo;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.ParcelableProto;

public final class InlineConsumptionAppInstallerFragment
  extends MultiStepFragment
  implements SidecarFragment.Listener
{
  private int mHandledStateInstance = -1;
  public DocV2 mMediaDoc;
  private int mPreviousState;
  public boolean mShouldGoToAppDetailsUponExit;
  public InlineConsumptionAppInstallerSidecar mSidecar;
  private Acquisition.SuccessInfo mSuccessInfo;
  
  public static InlineConsumptionAppInstallerFragment newInstance(Account paramAccount, DocV2 paramDocV2, Acquisition.SuccessInfo paramSuccessInfo)
  {
    Bundle localBundle = new Bundle();
    localBundle.putParcelable("MultiStepFragment.account", paramAccount);
    localBundle.putParcelable("mediaDoc", ParcelableProto.forProto(paramDocV2));
    if (paramSuccessInfo != null) {
      localBundle.putParcelable("successInfo", ParcelableProto.forProto(paramSuccessInfo));
    }
    InlineConsumptionAppInstallerFragment localInlineConsumptionAppInstallerFragment = new InlineConsumptionAppInstallerFragment();
    localInlineConsumptionAppInstallerFragment.setArguments(localBundle);
    return localInlineConsumptionAppInstallerFragment;
  }
  
  public final void finish(boolean paramBoolean)
  {
    ((Listener)getActivity()).onFinished(paramBoolean);
  }
  
  protected final int getBackendId()
  {
    return this.mMediaDoc.backendId;
  }
  
  public final void onAttach(Context paramContext)
  {
    super.onAttach(paramContext);
    if (!(paramContext instanceof PlayStoreUiElementNode)) {
      throw new IllegalStateException("Activity must implement PlayStoreUiElementNode");
    }
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (paramBundle != null)
    {
      this.mSidecar = ((InlineConsumptionAppInstallerSidecar)this.mFragmentManager.findFragmentByTag("InlineConsumptionAppInstallerFragment.sidecar"));
      this.mHandledStateInstance = paramBundle.getInt("InlineConsumptionAppInstallerFragment.handledStateInstance");
      this.mPreviousState = paramBundle.getInt("InlineConsumptionAppInstallerFragment.previousState");
    }
    Bundle localBundle = this.mArguments;
    this.mMediaDoc = ((DocV2)ParcelableProto.getProtoFromBundle(localBundle, "mediaDoc"));
    this.mSuccessInfo = ((Acquisition.SuccessInfo)ParcelableProto.getProtoFromBundle(localBundle, "successInfo"));
  }
  
  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130968804, paramViewGroup, false);
  }
  
  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putInt("InlineConsumptionAppInstallerFragment.handledStateInstance", this.mHandledStateInstance);
    paramBundle.putInt("InlineConsumptionAppInstallerFragment.previousState", this.mPreviousState);
  }
  
  public final void onStart()
  {
    super.onStart();
    if (this.mSidecar == null)
    {
      String str = this.mAccount.name;
      DocV2 localDocV2 = this.mMediaDoc;
      Bundle localBundle = new Bundle();
      localBundle.putString("authAccount", str);
      localBundle.putParcelable("InlineConsumptionAppInstallerSidecar.mediaDoc", ParcelableProto.forProto(localDocV2));
      InlineConsumptionAppInstallerSidecar localInlineConsumptionAppInstallerSidecar = new InlineConsumptionAppInstallerSidecar();
      localInlineConsumptionAppInstallerSidecar.setArguments(localBundle);
      this.mSidecar = localInlineConsumptionAppInstallerSidecar;
      this.mFragmentManager.beginTransaction().add(this.mSidecar, "InlineConsumptionAppInstallerFragment.sidecar").commit();
    }
    this.mSidecar.setListener(this);
  }
  
  public final void onStateChange(SidecarFragment paramSidecarFragment)
  {
    int i = paramSidecarFragment.mStateInstance;
    if (FinskyLog.DEBUG)
    {
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = Integer.valueOf(paramSidecarFragment.mState);
      arrayOfObject2[1] = Integer.valueOf(i);
      FinskyLog.v("Received state change: state=%d, stateInstance=%d", arrayOfObject2);
    }
    if (i == this.mHandledStateInstance)
    {
      if (FinskyLog.DEBUG)
      {
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = Integer.valueOf(this.mHandledStateInstance);
        FinskyLog.d("Already handled state %d", arrayOfObject1);
      }
      return;
    }
    this.mHandledStateInstance = i;
    switch (this.mSidecar.mState)
    {
    case 1: 
    case 2: 
    case 3: 
    case 4: 
    default: 
      String str = this.mSidecar.mErrorMsg;
      Document localDocument3 = this.mSidecar.mAppDoc;
      InlineConsumptionAppInstallerErrorStep localInlineConsumptionAppInstallerErrorStep = new InlineConsumptionAppInstallerErrorStep();
      Bundle localBundle4 = new Bundle();
      localBundle4.putString("errorMsg", str);
      if (localDocument3 != null) {
        localBundle4.putParcelable("appDoc", localDocument3);
      }
      localInlineConsumptionAppInstallerErrorStep.setArguments(localBundle4);
      showStep(localInlineConsumptionAppInstallerErrorStep);
    }
    for (;;)
    {
      this.mPreviousState = paramSidecarFragment.mState;
      return;
      showLoading();
      continue;
      DocV2 localDocV2 = this.mMediaDoc;
      Acquisition.SuccessInfo localSuccessInfo = this.mSuccessInfo;
      InlineConsumptionAppInstallerShouldInstallStep localInlineConsumptionAppInstallerShouldInstallStep = new InlineConsumptionAppInstallerShouldInstallStep();
      Bundle localBundle3 = new Bundle();
      localBundle3.putParcelable("mediaDoc", ParcelableProto.forProto(localDocV2));
      if (localSuccessInfo != null) {
        localBundle3.putParcelable("installStep", ParcelableProto.forProto(localSuccessInfo));
      }
      localInlineConsumptionAppInstallerShouldInstallStep.setArguments(localBundle3);
      showStep(localInlineConsumptionAppInstallerShouldInstallStep);
      continue;
      Document localDocument2 = this.mSidecar.mAppDoc;
      InlineConsumptionAppInstallerPermissionsStep localInlineConsumptionAppInstallerPermissionsStep = new InlineConsumptionAppInstallerPermissionsStep();
      Bundle localBundle2 = new Bundle();
      localBundle2.putParcelable("appDoc", localDocument2);
      localInlineConsumptionAppInstallerPermissionsStep.setArguments(localBundle2);
      showStep(localInlineConsumptionAppInstallerPermissionsStep);
      continue;
      showStep(InlineConsumptionAppInstallerDownloadStep.newInstance(this.mSidecar.mAppDoc));
      continue;
      Document localDocument1 = this.mSidecar.mAppDoc;
      InlineConsumptionAppInstallerReadyToReadStep localInlineConsumptionAppInstallerReadyToReadStep = new InlineConsumptionAppInstallerReadyToReadStep();
      Bundle localBundle1 = new Bundle();
      localBundle1.putParcelable("InlineConsumptionAppInstallerReadyToReadStep.appDoc", localDocument1);
      localInlineConsumptionAppInstallerReadyToReadStep.setArguments(localBundle1);
      showStep(localInlineConsumptionAppInstallerReadyToReadStep);
    }
  }
  
  public final void onStop()
  {
    if (this.mSidecar != null) {
      this.mSidecar.setListener(null);
    }
    super.onStop();
  }
  
  public static abstract interface Listener
  {
    public abstract void onFinished(boolean paramBoolean);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.download.inlineappinstaller.InlineConsumptionAppInstallerFragment
 * JD-Core Version:    0.7.0.1
 */