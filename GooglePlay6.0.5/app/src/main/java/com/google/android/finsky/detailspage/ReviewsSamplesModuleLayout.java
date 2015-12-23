package com.google.android.finsky.detailspage;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.google.android.finsky.activities.ReviewFeedbackListener;
import com.google.android.finsky.activities.ReviewFeedbackListener.ReviewFeedbackRating;
import com.google.android.finsky.activities.ReviewsActivity;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.layout.ReviewItemLayout;
import com.google.android.finsky.layout.ReviewItemLayout.ReviewFeedbackActionListener;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.Review;
import java.util.List;

public class ReviewsSamplesModuleLayout
  extends BucketsLinearLayout<ReviewItem>
{
  private ReviewFeedbackListener mReviewFeedbackListener;
  private ReviewSamplesClickListener mReviewSamplesClickListener;
  private final boolean mUseReviewFeedbackV2 = ReviewsActivity.shouldUseReviewFeedbackV2();
  
  public ReviewsSamplesModuleLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public ReviewsSamplesModuleLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public final void bind(List<ReviewItem> paramList, final Document paramDocument, boolean paramBoolean, ReviewSamplesClickListener paramReviewSamplesClickListener, NavigationManager paramNavigationManager, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    super.bindRows(paramList, paramDocument, paramBoolean, paramNavigationManager, paramPlayStoreUiElementNode);
    this.mReviewSamplesClickListener = paramReviewSamplesClickListener;
    setOnClickListener(new View.OnClickListener()
    {
      public final void onClick(View paramAnonymousView)
      {
        ReviewsSamplesModuleLayout.this.mNavigationManager.goToAllReviews(paramDocument, paramDocument.mDocument.reviewsUrl, false);
      }
    });
  }
  
  protected int getBucketRowLayout()
  {
    return 2130969074;
  }
  
  protected String getFooterText()
  {
    return getContext().getString(2131361837).toUpperCase();
  }
  
  protected TextView getFooterView()
  {
    return (TextView)findViewById(2131756063);
  }
  
  protected final int getItemsPerRow(Resources paramResources)
  {
    return paramResources.getInteger(2131623948);
  }
  
  protected final int getMaxRows(Resources paramResources)
  {
    return paramResources.getInteger(2131623947);
  }
  
  protected String getSectionTitleText()
  {
    return getContext().getString(2131362704).toUpperCase();
  }
  
  protected TextView getSectionTitleView()
  {
    return (TextView)findViewById(2131756068);
  }
  
  public void setReviewFeedbackListener(ReviewFeedbackListener paramReviewFeedbackListener)
  {
    this.mReviewFeedbackListener = paramReviewFeedbackListener;
  }
  
  public static final class ReviewItem
  {
    boolean isHelpful;
    boolean isSpam;
    final Review review;
    
    public ReviewItem(Review paramReview, boolean paramBoolean1, boolean paramBoolean2)
    {
      this.review = paramReview;
      this.isHelpful = paramBoolean1;
      this.isSpam = paramBoolean2;
    }
  }
  
  public static abstract interface ReviewSamplesClickListener
  {
    public abstract void onReviewFeedbackClick(Review paramReview);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.ReviewsSamplesModuleLayout
 * JD-Core Version:    0.7.0.1
 */