package com.google.android.finsky.billing.instrumentmanager;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.AccountHandler;
import com.google.android.finsky.billing.lightpurchase.multistep.MultiStepFragment;
import com.google.android.finsky.billing.lightpurchase.multistep.StepFragment;
import com.google.android.finsky.protos.SingleFopPaymentsIntegratorContext;
import com.google.android.finsky.utils.ParcelableProto;
import com.google.android.wallet.common.api.WalletRequestQueue;
import com.google.android.wallet.common.pub.BaseOrchestrationFragment.ResultListener;
import com.google.android.wallet.instrumentmanager.pub.InstrumentManagerFragment;

public abstract class InstrumentManagerStep<T extends MultiStepFragment>
  extends StepFragment<T>
  implements BaseOrchestrationFragment.ResultListener
{
  public static Bundle createArgs(String paramString, SingleFopPaymentsIntegratorContext paramSingleFopPaymentsIntegratorContext, int paramInt)
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("authAccount", paramString);
    localBundle.putParcelable("InstrumentManagerStep.tokens", ParcelableProto.forProto(paramSingleFopPaymentsIntegratorContext));
    localBundle.putInt("InstrumentManagerStep.theme_res_id", paramInt);
    return localBundle;
  }
  
  public static boolean isSuccess(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      throw new IllegalStateException("Unexpected InstrumentManager resultCode: " + paramInt);
    case 50: 
      return true;
    }
    return false;
  }
  
  public final boolean allowButtonBar()
  {
    return false;
  }
  
  public final String getContinueButtonLabel(Resources paramResources)
  {
    return null;
  }
  
  public final void onContinueButtonClicked() {}
  
  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130968801, paramViewGroup, false);
  }
  
  public final void onDestroy()
  {
    com.google.android.wallet.instrumentmanager.pub.analytics.InstrumentManagerAnalyticsEventDispatcher.sListener = null;
    super.onDestroy();
  }
  
  public final void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    Bundle localBundle = this.mArguments;
    String str = localBundle.getString("authAccount");
    com.google.android.wallet.instrumentmanager.pub.analytics.InstrumentManagerAnalyticsEventDispatcher.sListener = new InstrumentManagerLogger(this, FinskyApp.get().getEventLogger(str));
    WalletRequestQueue.setApiRequestQueue(FinskyApp.get().mRequestQueue);
    WalletRequestQueue.setImageRequestQueue(FinskyApp.get().mBitmapRequestQueue);
    if (getChildFragmentManager().findFragmentById(2131755620) == null)
    {
      SingleFopPaymentsIntegratorContext localSingleFopPaymentsIntegratorContext = (SingleFopPaymentsIntegratorContext)ParcelableProto.getProtoFromBundle(localBundle, "InstrumentManagerStep.tokens");
      InstrumentManagerFragment localInstrumentManagerFragment = InstrumentManagerFragment.newInstance(AccountHandler.findAccount(str, getActivity()), localSingleFopPaymentsIntegratorContext.commonToken, localSingleFopPaymentsIntegratorContext.instrumentToken, this.mArguments.getInt("InstrumentManagerStep.theme_res_id"), Bundle.EMPTY);
      getChildFragmentManager().beginTransaction().add(2131755620, localInstrumentManagerFragment).commit();
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.instrumentmanager.InstrumentManagerStep
 * JD-Core Version:    0.7.0.1
 */