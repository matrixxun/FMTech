package com.google.android.finsky.layout.play;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View.MeasureSpec;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.ClientMutationCache;
import com.google.android.finsky.utils.PlayAnimationUtils;
import com.google.android.finsky.utils.PlayAnimationUtils.AnimationListenerAdapter;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.finsky.utils.UiUtils.ClusterFadeOutListener;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.layout.PlayCardViewBase;

public class PlayCardRateClusterView
  extends PlayCardClusterView
  implements PlayCardDismissListener
{
  private UiUtils.ClusterFadeOutListener mClusterFadeOutListener;
  private TextView mEmptyDone;
  private boolean mRejectTouchEvents;
  
  public PlayCardRateClusterView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayCardRateClusterView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  private void fadeOutCard(final PlayCardViewRate paramPlayCardViewRate)
  {
    paramPlayCardViewRate.startAnimation(PlayAnimationUtils.getFadeOutAnimation(getContext(), 150L, new PlayAnimationUtils.AnimationListenerAdapter()
    {
      public final void onAnimationEnd(Animation paramAnonymousAnimation)
      {
        paramPlayCardViewRate.setVisibility(4);
        paramPlayCardViewRate.setState(2);
        PlayCardRateClusterView.access$200(PlayCardRateClusterView.this, paramPlayCardViewRate);
      }
    }));
  }
  
  private void transitionToEmptyState(boolean paramBoolean)
  {
    if (!paramBoolean)
    {
      this.mContent.setVisibility(4);
      this.mEmptyDone.setVisibility(0);
      return;
    }
    Animation localAnimation1 = PlayAnimationUtils.getFadeOutAnimation(getContext(), 250L, new PlayAnimationUtils.AnimationListenerAdapter()
    {
      public final void onAnimationEnd(Animation paramAnonymousAnimation)
      {
        PlayCardRateClusterView.this.mContent.setVisibility(8);
        if (PlayListView.ENABLE_ANIMATION) {
          UiUtils.fadeOutCluster(PlayCardRateClusterView.this, PlayCardRateClusterView.this.mClusterFadeOutListener, 2500L);
        }
      }
    });
    this.mContent.startAnimation(localAnimation1);
    Animation localAnimation2 = PlayAnimationUtils.getFadeInAnimation(getContext(), 250L, null);
    this.mEmptyDone.setVisibility(0);
    UiUtils.sendAccessibilityEventWithText(getContext(), this.mEmptyDone.getText(), this.mEmptyDone, true);
    this.mEmptyDone.startAnimation(localAnimation2);
  }
  
  public final void createContent(PlayCardClusterMetadata paramPlayCardClusterMetadata, ClientMutationCache paramClientMutationCache, DfeApi paramDfeApi, NavigationManager paramNavigationManager, BitmapLoader paramBitmapLoader, PlayCardDismissListener paramPlayCardDismissListener, PlayCardHeap paramPlayCardHeap, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    super.createContent(paramPlayCardClusterMetadata, paramClientMutationCache, paramDfeApi, paramNavigationManager, paramBitmapLoader, this, paramPlayCardHeap, paramPlayStoreUiElementNode);
    int i = getCardChildCount();
    for (int j = 0; j < i; j++)
    {
      final PlayCardViewRate localPlayCardViewRate = (PlayCardViewRate)getCardChildAt(j);
      localPlayCardViewRate.setRateListener(new PlayCardViewRate.RateListener()
      {
        public final void onRate$2563266(boolean paramAnonymousBoolean)
        {
          PlayCardRateClusterView.access$002(PlayCardRateClusterView.this, true);
          if (paramAnonymousBoolean) {
            PlayCardRateClusterView.this.fadeOutCard(localPlayCardViewRate);
          }
        }
        
        public final void onRateCleared()
        {
          PlayCardRateClusterView.access$002(PlayCardRateClusterView.this, false);
        }
      });
      localPlayCardViewRate.setState(0);
    }
    if (!((PlayCardRateClusterViewContent)this.mContent).hasItemsToRate()) {
      transitionToEmptyState(false);
    }
    this.mRejectTouchEvents = false;
  }
  
  protected int getPlayStoreUiElementType()
  {
    return 412;
  }
  
  public final void onDismissDocument(Document paramDocument, PlayCardViewBase paramPlayCardViewBase)
  {
    this.mClientMutationCache.dismissRecommendation(paramDocument.mDocument.docid);
    fadeOutCard((PlayCardViewRate)paramPlayCardViewBase);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mEmptyDone = ((TextView)findViewById(2131755883));
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    return (this.mRejectTouchEvents) || (super.onInterceptTouchEvent(paramMotionEvent));
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = getWidth();
    int j = getPaddingTop();
    if ((this.mHeader != null) && (this.mHeader.getVisibility() != 8))
    {
      this.mHeader.layout(0, j, i, j + this.mHeader.getMeasuredHeight());
      j += this.mHeader.getMeasuredHeight();
    }
    int k = this.mContent.getMeasuredHeight();
    this.mContent.layout(0, j, i, j + k);
    int m = j + (k - this.mEmptyDone.getMeasuredHeight()) / 2;
    this.mEmptyDone.layout(0, m, i, m + this.mEmptyDone.getMeasuredHeight());
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = getPaddingTop() + getPaddingBottom();
    if ((this.mHeader != null) && (this.mHeader.getVisibility() != 8))
    {
      this.mHeader.measure(paramInt1, 0);
      j += this.mHeader.getMeasuredHeight();
    }
    this.mContent.measure(paramInt1, 0);
    int k = j + this.mContent.getMeasuredHeight();
    this.mEmptyDone.measure(View.MeasureSpec.makeMeasureSpec(this.mContent.getMeasuredWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(this.mContent.getMeasuredHeight(), -2147483648));
    setMeasuredDimension(i, k);
  }
  
  public void setClusterFadeOutListener(UiUtils.ClusterFadeOutListener paramClusterFadeOutListener)
  {
    this.mClusterFadeOutListener = paramClusterFadeOutListener;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayCardRateClusterView
 * JD-Core Version:    0.7.0.1
 */