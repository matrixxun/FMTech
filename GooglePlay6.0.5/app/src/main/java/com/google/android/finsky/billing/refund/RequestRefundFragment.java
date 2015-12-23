package com.google.android.finsky.billing.refund;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.billing.lightpurchase.multistep.MultiStepFragment;
import com.google.android.finsky.billing.refund.steps.RefundConfirmRequestStep;
import com.google.android.finsky.billing.refund.steps.RefundFinishedStep;
import com.google.android.finsky.billing.refund.steps.SurveyNeededStep;
import com.google.android.finsky.fragments.SidecarFragment;
import com.google.android.finsky.fragments.SidecarFragment.Listener;
import com.google.android.finsky.utils.ErrorStrings;
import com.google.android.finsky.utils.FinskyLog;

public final class RequestRefundFragment
  extends MultiStepFragment
  implements SidecarFragment.Listener
{
  private String mAccountName;
  private Document mDocument;
  private int mLastStateInstance;
  RequestRefundResult mRequestRefundResult;
  public RequestRefundSidecar mSidecar;
  
  public static RequestRefundFragment newInstance(String paramString, Document paramDocument)
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("authAccount", paramString);
    localBundle.putParcelable("RequestRefundFragment.document", paramDocument);
    RequestRefundFragment localRequestRefundFragment = new RequestRefundFragment();
    localRequestRefundFragment.setArguments(localBundle);
    return localRequestRefundFragment;
  }
  
  public final void finish()
  {
    if ((getActivity() instanceof Listener))
    {
      ((Listener)getActivity()).onFinished();
      return;
    }
    FinskyLog.wtf("No listener.", new Object[0]);
  }
  
  protected final int getBackendId()
  {
    return 0;
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Bundle localBundle = this.mArguments;
    this.mAccountName = localBundle.getString("authAccount");
    this.mDocument = ((Document)localBundle.getParcelable("RequestRefundFragment.document"));
    if (paramBundle != null)
    {
      this.mLastStateInstance = paramBundle.getInt("RequestRefundFragment.lastStateInstance");
      this.mRequestRefundResult = ((RequestRefundResult)paramBundle.getParcelable("RequestRefundFragment.requestRefundResult"));
    }
  }
  
  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130969068, paramViewGroup, false);
  }
  
  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putInt("RequestRefundFragment.lastStateInstance", this.mLastStateInstance);
    paramBundle.putParcelable("RequestRefundFragment.requestRefundResult", this.mRequestRefundResult);
  }
  
  public final void onStart()
  {
    super.onStart();
    this.mSidecar = ((RequestRefundSidecar)this.mFragmentManager.findFragmentByTag("RequestRefundFragment.sidecar"));
    if (this.mSidecar == null)
    {
      String str = this.mAccountName;
      Document localDocument = this.mDocument;
      Bundle localBundle = new Bundle();
      localBundle.putString("RequestRefundSidecar.accountName", str);
      localBundle.putParcelable("RequestRefundSidecar.document", localDocument);
      RequestRefundSidecar localRequestRefundSidecar = new RequestRefundSidecar();
      localRequestRefundSidecar.setArguments(localBundle);
      this.mSidecar = localRequestRefundSidecar;
      this.mFragmentManager.beginTransaction().add(this.mSidecar, "RequestRefundFragment.sidecar").commit();
    }
    this.mSidecar.setListener(this);
  }
  
  public final void onStateChange(SidecarFragment paramSidecarFragment)
  {
    if (paramSidecarFragment != this.mSidecar)
    {
      FinskyLog.wtf("Received state change for unknown fragment: %s", new Object[] { paramSidecarFragment });
      return;
    }
    if (this.mSidecar.mStateInstance <= this.mLastStateInstance)
    {
      Object[] arrayOfObject3 = new Object[1];
      arrayOfObject3[0] = Integer.valueOf(this.mLastStateInstance);
      FinskyLog.d("Already received state instance %d, ignore.", arrayOfObject3);
      return;
    }
    if (FinskyLog.DEBUG)
    {
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = Integer.valueOf(this.mSidecar.mState);
      FinskyLog.d("State changed: %d", arrayOfObject2);
    }
    this.mLastStateInstance = this.mSidecar.mStateInstance;
    switch (this.mSidecar.mState)
    {
    case 2: 
    default: 
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = Integer.valueOf(this.mSidecar.mState);
      FinskyLog.wtf("Unknown sidecar state: %d", arrayOfObject1);
      return;
    case 0: 
      this.mSidecar.setState(4, 0);
      return;
    case 1: 
      showLoading();
      return;
    case 4: 
      String str3 = this.mSidecar.getDialogMessage();
      String str4 = this.mAccountName;
      Bundle localBundle2 = new Bundle();
      localBundle2.putString("RefundConfirmRequestStep.userMessage", str3);
      localBundle2.putString("RefundConfirmRequestStep.accountName", str4);
      RefundConfirmRequestStep localRefundConfirmRequestStep = new RefundConfirmRequestStep();
      localRefundConfirmRequestStep.setArguments(localBundle2);
      showStep(localRefundConfirmRequestStep);
      return;
    case 5: 
    case 6: 
      String str2 = this.mSidecar.getDialogMessage();
      Bundle localBundle1 = new Bundle();
      localBundle1.putString("SurveyNeededStep.userMessage", str2);
      SurveyNeededStep localSurveyNeededStep = new SurveyNeededStep();
      localSurveyNeededStep.setArguments(localBundle1);
      showStep(localSurveyNeededStep);
      return;
    case 7: 
      showStep(RefundFinishedStep.newInstance(this.mSidecar.getDialogMessage()));
      return;
    }
    if ((this.mSidecar.mSubstate == 1) && (this.mSidecar.mVolleyError != null)) {}
    for (String str1 = ErrorStrings.get(getActivity(), this.mSidecar.mVolleyError);; str1 = this.mSidecar.getDialogMessage())
    {
      showStep(RefundFinishedStep.newInstance(str1));
      return;
    }
  }
  
  public final void onStop()
  {
    this.mSidecar.setListener(null);
    super.onStop();
  }
  
  public static abstract interface Listener
  {
    public abstract void onFinished();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.refund.RequestRefundFragment
 * JD-Core Version:    0.7.0.1
 */