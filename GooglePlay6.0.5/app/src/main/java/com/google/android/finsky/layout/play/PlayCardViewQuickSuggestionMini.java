package com.google.android.finsky.layout.play;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import com.google.android.finsky.utils.PlayAnimationUtils;
import com.google.android.play.layout.PlayCardThumbnail;
import com.google.android.play.layout.PlayCardViewMini;
import com.google.android.play.utils.PlayUtils;

public class PlayCardViewQuickSuggestionMini
  extends PlayCardViewMini
  implements Animation.AnimationListener, PlayCardRateAndSuggestClusterViewContent.PendingStateHandler
{
  private final int mCardInset;
  private boolean mIsInPendingState = true;
  private View mPendingOverlay;
  
  public PlayCardViewQuickSuggestionMini(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayCardViewQuickSuggestionMini(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mCardInset = paramContext.getResources().getDimensionPixelSize(2131493385);
  }
  
  public final void enterPendingStateIfNecessary(boolean paramBoolean)
  {
    this.mLoadingIndicator.setVisibility(8);
    if (this.mIsInPendingState) {
      return;
    }
    this.mIsInPendingState = true;
    if (paramBoolean)
    {
      Animation localAnimation = PlayAnimationUtils.getFadeInAnimation(getContext(), 300L, this);
      this.mPendingOverlay.setVisibility(0);
      this.mPendingOverlay.startAnimation(localAnimation);
      return;
    }
    this.mPendingOverlay.setVisibility(0);
  }
  
  public final void exitPendingStateIfNecessary(boolean paramBoolean)
  {
    if (!this.mIsInPendingState) {
      return;
    }
    this.mIsInPendingState = false;
    if (paramBoolean)
    {
      Animation localAnimation = PlayAnimationUtils.getFadeOutAnimation(getContext(), 300L, this);
      this.mPendingOverlay.startAnimation(localAnimation);
      return;
    }
    this.mPendingOverlay.setVisibility(8);
  }
  
  public void onAnimationEnd(Animation paramAnimation)
  {
    View localView = this.mPendingOverlay;
    if (this.mIsInPendingState) {}
    for (int i = 0;; i = 8)
    {
      localView.setVisibility(i);
      return;
    }
  }
  
  public void onAnimationRepeat(Animation paramAnimation) {}
  
  public void onAnimationStart(Animation paramAnimation) {}
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    this.mPendingOverlay.setVisibility(0);
    this.mIsInPendingState = true;
  }
  
  protected void onDetachedFromWindow()
  {
    this.mPendingOverlay.setVisibility(8);
    this.mIsInPendingState = false;
    super.onDetachedFromWindow();
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mPendingOverlay = findViewById(2131755877);
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    if (this.mPendingOverlay.getVisibility() == 8) {
      return;
    }
    if (ViewCompat.getLayoutDirection(this) == 0) {}
    for (boolean bool = true;; bool = false)
    {
      int i = getWidth();
      int j = this.mPendingOverlay.getMeasuredWidth();
      int k = PlayUtils.getViewLeftFromParentStart(i, j, bool, this.mCardInset);
      int m = this.mThumbnail.getTop();
      this.mPendingOverlay.layout(k, m, k + j, m + this.mPendingOverlay.getMeasuredHeight());
      return;
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    if (this.mPendingOverlay.getVisibility() == 8) {
      return;
    }
    int i = getMeasuredWidth() - 2 * this.mCardInset;
    int j = getMeasuredHeight() - 2 * this.mCardInset;
    this.mPendingOverlay.measure(View.MeasureSpec.makeMeasureSpec(i, 1073741824), View.MeasureSpec.makeMeasureSpec(j, 1073741824));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayCardViewQuickSuggestionMini
 * JD-Core Version:    0.7.0.1
 */