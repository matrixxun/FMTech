package com.google.android.finsky.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.ContextThemeWrapper;
import android.widget.Button;
import com.google.android.finsky.protos.Review;
import com.google.android.wallet.ui.common.AlertDialogBuilderCompat;

public final class ReviewFeedbackDialog
  extends DialogFragment
{
  ReviewFeedbackListener.ReviewFeedbackRating mRating = null;
  
  private static ReviewFeedbackListener.ReviewFeedbackRating getRatingForIndex(int paramInt)
  {
    for (ReviewFeedbackListener.ReviewFeedbackRating localReviewFeedbackRating : ) {
      if (localReviewFeedbackRating.mIndex == paramInt) {
        return localReviewFeedbackRating;
      }
    }
    return null;
  }
  
  public static ReviewFeedbackDialog newInstance$47996a45(String paramString, Review paramReview)
  {
    ReviewFeedbackDialog localReviewFeedbackDialog = new ReviewFeedbackDialog();
    Bundle localBundle = new Bundle();
    localBundle.putString("doc_id", paramString);
    localBundle.putString("review_id", paramReview.commentId);
    localReviewFeedbackDialog.setArguments(localBundle);
    return localReviewFeedbackDialog;
  }
  
  private void syncOkButtonState()
  {
    if (this.mRating != null) {}
    for (boolean bool = true;; bool = false)
    {
      ((AlertDialog)this.mDialog).getButton$717182de().setEnabled(bool);
      return;
    }
  }
  
  public final Dialog onCreateDialog(Bundle paramBundle)
  {
    ContextThemeWrapper localContextThemeWrapper = new ContextThemeWrapper(getActivity(), 2131558816);
    Bundle localBundle = this.mArguments;
    final String str1 = localBundle.getString("review_id");
    final String str2 = localBundle.getString("doc_id");
    if (paramBundle != null) {}
    int i;
    CharSequence[] arrayOfCharSequence;
    for (;;)
    {
      i = paramBundle.getInt("previous_rating", -1);
      this.mRating = getRatingForIndex(i);
      arrayOfCharSequence = new CharSequence[ReviewFeedbackListener.ReviewFeedbackRating.values().length];
      for (ReviewFeedbackListener.ReviewFeedbackRating localReviewFeedbackRating : ReviewFeedbackListener.ReviewFeedbackRating.values()) {
        arrayOfCharSequence[localReviewFeedbackRating.mIndex] = localContextThemeWrapper.getString(localReviewFeedbackRating.mDisplayStringId);
      }
      paramBundle = localBundle;
    }
    AlertDialogBuilderCompat localAlertDialogBuilderCompat = new AlertDialogBuilderCompat(localContextThemeWrapper);
    localAlertDialogBuilderCompat.setTitle(2131362685);
    localAlertDialogBuilderCompat.setCancelable(true);
    localAlertDialogBuilderCompat.setSingleChoiceItems(arrayOfCharSequence, i, new DialogInterface.OnClickListener()
    {
      public final void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        ReviewFeedbackDialog.access$000(ReviewFeedbackDialog.this, paramAnonymousInt);
        ReviewFeedbackDialog.this.syncOkButtonState();
      }
    });
    localAlertDialogBuilderCompat.setPositiveButton(17039370, new DialogInterface.OnClickListener()
    {
      public final void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        ReviewFeedbackListener localReviewFeedbackListener = ReviewFeedbackDialog.access$200(ReviewFeedbackDialog.this);
        if ((localReviewFeedbackListener != null) && (ReviewFeedbackDialog.this.mRating != null) && (str2 != null) && (str1 != null)) {
          localReviewFeedbackListener.onReviewFeedback(str2, str1, ReviewFeedbackDialog.this.mRating);
        }
      }
    });
    localAlertDialogBuilderCompat.setNegativeButton(17039360, null);
    return localAlertDialogBuilderCompat.create();
  }
  
  public final void onSaveInstanceState(Bundle paramBundle)
  {
    if (this.mRating != null) {
      paramBundle.putInt("previous_rating", this.mRating.mIndex);
    }
  }
  
  public final void onStart()
  {
    super.onStart();
    syncOkButtonState();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.ReviewFeedbackDialog
 * JD-Core Version:    0.7.0.1
 */