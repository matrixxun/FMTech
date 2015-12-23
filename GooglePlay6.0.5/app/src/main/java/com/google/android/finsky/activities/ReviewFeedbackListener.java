package com.google.android.finsky.activities;

public abstract interface ReviewFeedbackListener
{
  public abstract void onReviewFeedback(String paramString1, String paramString2, ReviewFeedbackRating paramReviewFeedbackRating);
  
  public static enum ReviewFeedbackRating
  {
    int mDisplayStringId;
    int mIndex;
    public int mRpcId;
    
    static
    {
      HELPFUL = new ReviewFeedbackRating("HELPFUL", 1, 1, 0, 2131362681);
      NOT_HELPFUL = new ReviewFeedbackRating("NOT_HELPFUL", 2, 2, 1, 2131362684);
      ReviewFeedbackRating[] arrayOfReviewFeedbackRating = new ReviewFeedbackRating[3];
      arrayOfReviewFeedbackRating[0] = SPAM;
      arrayOfReviewFeedbackRating[1] = HELPFUL;
      arrayOfReviewFeedbackRating[2] = NOT_HELPFUL;
      $VALUES = arrayOfReviewFeedbackRating;
    }
    
    private ReviewFeedbackRating(int paramInt1, int paramInt2, int paramInt3)
    {
      this.mRpcId = paramInt1;
      this.mIndex = paramInt2;
      this.mDisplayStringId = paramInt3;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.ReviewFeedbackListener
 * JD-Core Version:    0.7.0.1
 */