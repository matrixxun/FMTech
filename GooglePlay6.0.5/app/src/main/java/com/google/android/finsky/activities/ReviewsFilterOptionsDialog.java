package com.google.android.finsky.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import com.google.android.wallet.ui.common.AlertDialogBuilderCompat;

public final class ReviewsFilterOptionsDialog
  extends DialogFragment
{
  private final boolean[] mChecked = new boolean[2];
  
  public static ReviewsFilterOptionsDialog newInstance(boolean paramBoolean1, boolean paramBoolean2)
  {
    ReviewsFilterOptionsDialog localReviewsFilterOptionsDialog = new ReviewsFilterOptionsDialog();
    Bundle localBundle = new Bundle();
    localBundle.putBoolean("filterByVersion", paramBoolean1);
    localBundle.putBoolean("filterByDevice", paramBoolean2);
    localReviewsFilterOptionsDialog.setArguments(localBundle);
    return localReviewsFilterOptionsDialog;
  }
  
  public final Dialog onCreateDialog(Bundle paramBundle)
  {
    FragmentActivity localFragmentActivity = getActivity();
    Bundle localBundle = this.mArguments;
    AlertDialogBuilderCompat localAlertDialogBuilderCompat = new AlertDialogBuilderCompat(localFragmentActivity);
    localAlertDialogBuilderCompat.setTitle(2131362703);
    String[] arrayOfString = new String[this.mChecked.length];
    arrayOfString[0] = localFragmentActivity.getString(2131362702);
    arrayOfString[1] = localFragmentActivity.getString(2131362701);
    this.mChecked[0] = localBundle.getBoolean("filterByVersion");
    this.mChecked[1] = localBundle.getBoolean("filterByDevice");
    boolean[] arrayOfBoolean = this.mChecked;
    DialogInterface.OnMultiChoiceClickListener local1 = new DialogInterface.OnMultiChoiceClickListener()
    {
      public final void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt, boolean paramAnonymousBoolean)
      {
        ReviewsFilterOptionsDialog.this.mChecked[paramAnonymousInt] = paramAnonymousBoolean;
      }
    };
    if (localAlertDialogBuilderCompat.mPlatformBuilder != null) {
      localAlertDialogBuilderCompat.mPlatformBuilder.setMultiChoiceItems(arrayOfString, arrayOfBoolean, local1);
    }
    for (;;)
    {
      localAlertDialogBuilderCompat.setPositiveButton(17039370, new DialogInterface.OnClickListener()
      {
        public final void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          ReviewsFilterOptionsDialog.Listener localListener = ReviewsFilterOptionsDialog.access$100(ReviewsFilterOptionsDialog.this);
          if (localListener != null) {
            localListener.onReviewFilterChanged(ReviewsFilterOptionsDialog.this.mChecked[0], ReviewsFilterOptionsDialog.this.mChecked[1]);
          }
          ReviewsFilterOptionsDialog.this.dismissInternal(false);
        }
      });
      return localAlertDialogBuilderCompat.create();
      android.support.v7.app.AlertDialog.Builder localBuilder = localAlertDialogBuilderCompat.mSupportBuilder;
      localBuilder.P.mItems = arrayOfString;
      localBuilder.P.mOnCheckboxClickListener = local1;
      localBuilder.P.mCheckedItems = arrayOfBoolean;
      localBuilder.P.mIsMultiChoice = true;
    }
  }
  
  public static abstract interface Listener
  {
    public abstract void onReviewFilterChanged(boolean paramBoolean1, boolean paramBoolean2);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.ReviewsFilterOptionsDialog
 * JD-Core Version:    0.7.0.1
 */