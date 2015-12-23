package com.google.android.finsky.layout;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.google.android.finsky.layout.play.SingleLineContainer;
import com.google.android.finsky.protos.Review;
import com.google.android.finsky.utils.BadgeUtils;
import com.google.android.finsky.utils.DateUtils;
import com.google.android.finsky.utils.IntentUtils;
import com.google.android.play.layout.StarRatingBar;

public class ReviewItemHeaderLayout
  extends SingleLineContainer
{
  private TextView mDate;
  private TextView mEdited;
  private StarRatingBar mRating;
  private TextView mSource;
  
  public ReviewItemHeaderLayout(Context paramContext)
  {
    super(paramContext);
  }
  
  public ReviewItemHeaderLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mRating = ((StarRatingBar)findViewById(2131755861));
    this.mSource = ((TextView)findViewById(2131756055));
    this.mDate = ((TextView)findViewById(2131755865));
    this.mEdited = ((TextView)findViewById(2131756056));
  }
  
  public void setReviewInfo(Review paramReview)
  {
    String str1 = paramReview.source;
    final String str2 = paramReview.url;
    boolean bool;
    if (!TextUtils.isEmpty(str1))
    {
      this.mSource.setText(str1.toUpperCase());
      this.mSource.setVisibility(0);
      if (TextUtils.isEmpty(str2))
      {
        this.mSource.setOnClickListener(null);
        if (!paramReview.hasStarRating) {
          break label217;
        }
        this.mRating.setVisibility(0);
        this.mRating.setRating(paramReview.starRating);
        StarRatingBar localStarRatingBar = this.mRating;
        if (BadgeUtils.shouldHideEmptyStarsInReviews()) {
          break label211;
        }
        bool = true;
        label93:
        localStarRatingBar.setShowEmptyStars(bool);
        label100:
        if (!paramReview.hasTimestampMsec) {
          break label229;
        }
        this.mDate.setText(DateUtils.formatShortDisplayDate(paramReview.timestampMsec));
        this.mDate.setVisibility(0);
      }
    }
    for (;;)
    {
      this.mEdited.setVisibility(8);
      if ((paramReview.hasReplyText) && (paramReview.hasReplyTimestampMsec) && (paramReview.hasTimestampMsec) && (paramReview.timestampMsec > paramReview.replyTimestampMsec)) {
        this.mEdited.setVisibility(0);
      }
      return;
      this.mSource.setOnClickListener(new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          paramAnonymousView.getContext().startActivity(IntentUtils.createViewIntentForUrl(Uri.parse(str2)));
        }
      });
      break;
      this.mSource.setVisibility(8);
      break;
      label211:
      bool = false;
      break label93;
      label217:
      this.mRating.setVisibility(8);
      break label100;
      label229:
      this.mDate.setVisibility(8);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.ReviewItemHeaderLayout
 * JD-Core Version:    0.7.0.1
 */