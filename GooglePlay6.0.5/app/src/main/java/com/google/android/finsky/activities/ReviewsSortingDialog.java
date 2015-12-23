package com.google.android.finsky.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import com.google.android.finsky.api.model.DfeReviews;
import com.google.android.finsky.utils.ReviewsSortingUtils;
import com.google.android.wallet.ui.common.AlertDialogBuilderCompat;

public final class ReviewsSortingDialog
  extends DialogFragment
{
  public static ReviewsSortingDialog newInstance(DfeReviews paramDfeReviews)
  {
    ReviewsSortingDialog localReviewsSortingDialog = new ReviewsSortingDialog();
    Bundle localBundle = new Bundle();
    localBundle.putInt("sorting_type", ReviewsSortingUtils.convertDataSortTypeToDisplayIndex(paramDfeReviews));
    localReviewsSortingDialog.setArguments(localBundle);
    return localReviewsSortingDialog;
  }
  
  public final Dialog onCreateDialog(Bundle paramBundle)
  {
    FragmentActivity localFragmentActivity = getActivity();
    Bundle localBundle = this.mArguments;
    AlertDialogBuilderCompat localAlertDialogBuilderCompat = new AlertDialogBuilderCompat(localFragmentActivity);
    localAlertDialogBuilderCompat.setTitle(2131362766);
    localAlertDialogBuilderCompat.setSingleChoiceItems(ReviewsSortingUtils.getAllDisplayStrings(localFragmentActivity), localBundle.getInt("sorting_type"), new DialogInterface.OnClickListener()
    {
      public final void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        ReviewsSortingDialog.this.dismissInternal(false);
        int i = ReviewsSortingUtils.convertDisplayIndexToDataSortType(paramAnonymousInt);
        ReviewsSortingDialog.access$000(ReviewsSortingDialog.this).onSortingChanged(i);
      }
    });
    return localAlertDialogBuilderCompat.create();
  }
  
  public static abstract interface Listener
  {
    public abstract void onSortingChanged(int paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.ReviewsSortingDialog
 * JD-Core Version:    0.7.0.1
 */