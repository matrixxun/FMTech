package com.google.android.finsky.layout.play;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.play.cardview.CardBubbleViewGroupDelegate;
import com.google.android.play.cardview.CardBubbleViewGroupDelegates;
import com.google.android.play.layout.PlayCardViewBase;
import com.google.android.play.layout.PlayTextView;
import com.google.android.play.layout.StarRatingBar;
import com.google.android.play.layout.WithBubblePadding;

public class PlayCardFriendReviewHeaderView
  extends PlayCardViewBase
  implements WithBubblePadding
{
  private boolean mExpanded;
  private final View.OnClickListener mExpansionListener = new View.OnClickListener()
  {
    public final void onClick(View paramAnonymousView)
    {
      if (PlayCardFriendReviewHeaderView.this.mExpanded)
      {
        PlayCardFriendReviewHeaderView.access$100(PlayCardFriendReviewHeaderView.this);
        return;
      }
      PlayCardFriendReviewHeaderView.access$200(PlayCardFriendReviewHeaderView.this);
    }
  };
  private String mFullReviewText;
  private boolean mIsWideLayout;
  private String mLessReviewText;
  private final ViewTreeObserver.OnPreDrawListener mOnPreDrawListener = new ViewTreeObserver.OnPreDrawListener()
  {
    public final boolean onPreDraw()
    {
      if (PlayCardFriendReviewHeaderView.this.mReview.getLineCount() > 3)
      {
        PlayCardFriendReviewHeaderView.access$100(PlayCardFriendReviewHeaderView.this);
        PlayCardFriendReviewHeaderView.this.mReadFullReview.setOnClickListener(PlayCardFriendReviewHeaderView.this.mExpansionListener);
        PlayCardFriendReviewHeaderView.this.mReview.setOnClickListener(PlayCardFriendReviewHeaderView.this.mExpansionListener);
        PlayCardFriendReviewHeaderView.this.mReview.setBackgroundResource(2130837960);
      }
      for (;;)
      {
        PlayCardFriendReviewHeaderView.this.getViewTreeObserver().removeOnPreDrawListener(this);
        return true;
        ViewCompat.setPaddingRelative(PlayCardFriendReviewHeaderView.this.mReview, ViewCompat.getPaddingStart(PlayCardFriendReviewHeaderView.this.mReview), PlayCardFriendReviewHeaderView.this.mReview.getPaddingTop(), ViewCompat.getPaddingEnd(PlayCardFriendReviewHeaderView.this.mReview), PlayCardFriendReviewHeaderView.this.mShortReviewBottomPadding);
        PlayCardFriendReviewHeaderView.this.mReadFullReview.setVisibility(8);
        PlayCardFriendReviewHeaderView.this.mReview.setBackgroundResource(0);
        PlayCardFriendReviewHeaderView.this.mReview.setOnClickListener(null);
        PlayCardFriendReviewHeaderView.this.mReview.setClickable(false);
      }
    }
  };
  private int mOriginalPaddingBottom;
  private int mOriginalPaddingLeft;
  private int mOriginalPaddingRight;
  private int mOriginalPaddingTop;
  private TextView mReadFullReview;
  PlayTextView mReview;
  private int mShortReviewBottomPadding;
  private StarRatingBar mStarRating;
  private int mWideLayoutBubbleSize;
  private int mWideLayoutWidth;
  
  public PlayCardFriendReviewHeaderView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayCardFriendReviewHeaderView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    Resources localResources = paramContext.getResources();
    this.mShortReviewBottomPadding = localResources.getDimensionPixelSize(2131493305);
    this.mFullReviewText = localResources.getString(2131362558).toUpperCase();
    this.mLessReviewText = localResources.getString(2131362559).toUpperCase();
    this.mIsWideLayout = localResources.getBoolean(2131427335);
    if (this.mIsWideLayout)
    {
      this.mWideLayoutWidth = (2 * (UiUtils.getGridColumnContentWidth(localResources) / UiUtils.getFeaturedGridColumnCount(localResources, 1.0D)));
      this.mWideLayoutBubbleSize = localResources.getDimensionPixelSize(2131493014);
    }
  }
  
  public int getCardType()
  {
    return 21;
  }
  
  public CardBubbleViewGroupDelegate getCardViewGroupDelegate()
  {
    return CardBubbleViewGroupDelegates.IMPL;
  }
  
  public final void getOriginalPadding(int[] paramArrayOfInt)
  {
    paramArrayOfInt[0] = this.mOriginalPaddingLeft;
    paramArrayOfInt[1] = this.mOriginalPaddingTop;
    paramArrayOfInt[2] = this.mOriginalPaddingRight;
    paramArrayOfInt[3] = this.mOriginalPaddingBottom;
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mReview = ((PlayTextView)findViewById(2131755862));
    this.mReadFullReview = ((TextView)findViewById(2131755863));
    this.mStarRating = ((StarRatingBar)findViewById(2131755861));
    getViewTreeObserver().addOnPreDrawListener(this.mOnPreDrawListener);
    getCardViewGroupDelegate().setBubbleGravity(this, 80);
    if (this.mIsWideLayout)
    {
      RelativeLayout.LayoutParams localLayoutParams = (RelativeLayout.LayoutParams)getLayoutParams();
      localLayoutParams.width = (this.mWideLayoutWidth + this.mWideLayoutBubbleSize);
      setLayoutParams(localLayoutParams);
    }
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    if (this.mIsWideLayout) {
      this.mStarRating.layout(this.mStarRating.getLeft() + this.mWideLayoutBubbleSize / 2, this.mStarRating.getTop(), this.mStarRating.getRight() + this.mWideLayoutBubbleSize / 2, this.mStarRating.getBottom());
    }
  }
  
  public final void onSaveOriginalPadding(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mOriginalPaddingLeft = paramInt1;
    this.mOriginalPaddingTop = paramInt2;
    this.mOriginalPaddingRight = paramInt3;
    this.mOriginalPaddingBottom = paramInt4;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayCardFriendReviewHeaderView
 * JD-Core Version:    0.7.0.1
 */