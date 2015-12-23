package com.google.android.libraries.happiness;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;

public final class HatsSurveyDialog
  extends DialogFragment
{
  private int mLayout;
  Runnable mOnCancelAction;
  private int mResourceId;
  private boolean mSurveyRendered;
  WebView mWebView;
  private ViewGroup mWebViewContainer;
  
  final void bindWebview()
  {
    if ((this.mWebView != null) && (this.mWebViewContainer != null))
    {
      ViewGroup localViewGroup = (ViewGroup)this.mWebView.getParent();
      if (localViewGroup != null) {
        localViewGroup.removeView(this.mWebView);
      }
      this.mWebViewContainer.addView(this.mWebView, 0, new ViewGroup.LayoutParams(-1, -1));
      this.mSurveyRendered = true;
    }
  }
  
  public final void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    if ((this.mSurveyRendered) && (this.mWebView == null))
    {
      getActivity().setRequestedOrientation(-1);
      dismissInternal(false);
    }
  }
  
  public final void onCancel(DialogInterface paramDialogInterface)
  {
    if (this.mOnCancelAction != null) {
      this.mOnCancelAction.run();
    }
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mLayout = 2130969130;
    this.mResourceId = 2131756156;
    if (paramBundle != null) {
      this.mSurveyRendered = paramBundle.getBoolean("showSurveyPrompt.HATS_SURVEY_RENDERED");
    }
  }
  
  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    setCancelable(true);
    View localView = paramLayoutInflater.inflate(this.mLayout, paramViewGroup);
    this.mWebViewContainer = ((ViewGroup)localView.findViewById(this.mResourceId));
    bindWebview();
    return localView;
  }
  
  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putBoolean("showSurveyPrompt.HATS_SURVEY_RENDERED", this.mSurveyRendered);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.libraries.happiness.HatsSurveyDialog
 * JD-Core Version:    0.7.0.1
 */