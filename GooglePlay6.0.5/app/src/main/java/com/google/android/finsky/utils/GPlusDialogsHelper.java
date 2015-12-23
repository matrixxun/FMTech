package com.google.android.finsky.utils;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.SimpleAlertDialog;
import com.google.android.finsky.activities.SimpleAlertDialog.Builder;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.config.PreferenceFile.PrefixSharedPreference;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.protos.Toc.TocResponse;

public final class GPlusDialogsHelper
{
  public static boolean hasUserAcceptedGPlusReviews()
  {
    return ((Boolean)FinskyPreferences.acceptedPlusReviews.get(FinskyApp.get().getCurrentAccountName()).get()).booleanValue();
  }
  
  public static void showGPlusSignUpDialog(FragmentManager paramFragmentManager, Context paramContext)
  {
    if (!FinskyApp.get().mToc.mToc.gplusSignupEnabled)
    {
      Toast.makeText(paramContext, paramContext.getResources().getText(2131362195), 0).show();
      return;
    }
    GPlusSignUpDialog.newInstance().show(paramFragmentManager, null);
  }
  
  public static final class GPlusSignUpAndPublicReviewsDialog
    extends SimpleAlertDialog
  {
    public static GPlusSignUpAndPublicReviewsDialog newInstance()
    {
      GPlusSignUpAndPublicReviewsDialog localGPlusSignUpAndPublicReviewsDialog = new GPlusSignUpAndPublicReviewsDialog();
      SimpleAlertDialog.Builder localBuilder = new SimpleAlertDialog.Builder();
      localBuilder.setMessageId(2131362197).setPositiveId(2131362283).setNegativeId(2131361915);
      localBuilder.configureDialog(localGPlusSignUpAndPublicReviewsDialog);
      return localGPlusSignUpAndPublicReviewsDialog;
    }
    
    protected final void onPositiveClick()
    {
      super.onPositiveClick();
      GPlusUtils.launchGPlusSignUp(getActivity());
      FinskyPreferences.acceptedPlusReviews.get(FinskyApp.get().getCurrentAccountName()).put(Boolean.valueOf(true));
    }
  }
  
  public static final class GPlusSignUpDialog
    extends SimpleAlertDialog
  {
    public static GPlusSignUpDialog newInstance()
    {
      GPlusSignUpDialog localGPlusSignUpDialog = new GPlusSignUpDialog();
      SimpleAlertDialog.Builder localBuilder = new SimpleAlertDialog.Builder();
      localBuilder.setMessageId(2131362199).setPositiveId(2131362283).setNegativeId(2131361915);
      localBuilder.configureDialog(localGPlusSignUpDialog);
      return localGPlusSignUpDialog;
    }
    
    protected final void onPositiveClick()
    {
      super.onPositiveClick();
      GPlusUtils.launchGPlusSignUp(getActivity());
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.GPlusDialogsHelper
 * JD-Core Version:    0.7.0.1
 */