package com.google.android.finsky.layout.play;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.TimeInterpolator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat.ViewPropertyAnimatorCompatImpl;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.support.v4.view.accessibility.AccessibilityRecordCompat;
import android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.finsky.activities.HighlightsPagerAdapter.HighlightsPageListener;
import com.google.android.finsky.api.model.Document;
import com.google.android.play.widget.ScalingPageIndicator;
import com.google.android.play.widget.ScalingPageIndicator.2;
import com.google.android.play.widget.ScalingPageIndicator.3;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class PlayHighlightsOverlayView
  extends LinearLayout
  implements HighlightsPagerAdapter.HighlightsPageListener
{
  private static final boolean DO_NOT_ANIMATE_TITLE_CHANGE;
  private final TimeInterpolator mAlphaInterpolator = new FastOutSlowInInterpolator();
  public int mCurrentHighlightsLogicalSection;
  private int mCurrentPage = -1;
  private boolean mHasScrolled;
  private int mItemWidth;
  private int mLastOffset;
  private ScalingPageIndicator mPageIndicator;
  private int mTitlePosition;
  private PlayRecyclerView mTitleRecycler;
  private boolean mTransitioningAdapter;
  
  static
  {
    if (Build.VERSION.SDK_INT <= 15) {}
    for (boolean bool = true;; bool = false)
    {
      DO_NOT_ANIMATE_TITLE_CHANGE = bool;
      return;
    }
  }
  
  public PlayHighlightsOverlayView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayHighlightsOverlayView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public PlayHighlightsOverlayView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  private void scrollTitleToPosition(int paramInt)
  {
    TitlePagerAdapter localTitlePagerAdapter = (TitlePagerAdapter)this.mTitleRecycler.getAdapter();
    LinearLayoutManager localLinearLayoutManager;
    int i;
    if ((localTitlePagerAdapter != null) && (paramInt >= 0))
    {
      localLinearLayoutManager = (LinearLayoutManager)this.mTitleRecycler.getLayoutManager();
      this.mTitlePosition = paramInt;
      if (!this.mTransitioningAdapter)
      {
        i = this.mTitlePosition;
        if (i >= 0) {
          break label75;
        }
      }
    }
    for (;;)
    {
      localLinearLayoutManager.scrollToPositionWithOffset(i, this.mLastOffset);
      this.mPageIndicator.setSelectedPage(this.mCurrentPage);
      return;
      label75:
      i += localTitlePagerAdapter.mCenterPosition;
    }
  }
  
  public final void loadTitles(Document paramDocument)
  {
    if (paramDocument == null) {}
    for (Object localObject = null; (localObject == null) || (this.mTitleRecycler.getAdapter() == null) || (DO_NOT_ANIMATE_TITLE_CHANGE); localObject = new TitlePagerAdapter(getContext(), paramDocument, this.mItemWidth))
    {
      this.mTitleRecycler.setAdapter((RecyclerView.Adapter)localObject);
      scrollTitleToPosition(this.mTitlePosition);
      return;
    }
    this.mTransitioningAdapter = true;
    ViewPropertyAnimatorCompat localViewPropertyAnimatorCompat = ViewCompat.animate(this.mTitleRecycler).alpha(0.0F).setDuration(150L);
    Runnable local3 = new Runnable()
    {
      public final void run()
      {
        PlayHighlightsOverlayView.access$402$1912df39(PlayHighlightsOverlayView.this);
        PlayHighlightsOverlayView.access$502$1912df39(PlayHighlightsOverlayView.this);
        PlayRecyclerView localPlayRecyclerView = PlayHighlightsOverlayView.this.mTitleRecycler;
        PlayHighlightsOverlayView.TitlePagerAdapter localTitlePagerAdapter = this.val$adapter;
        localPlayRecyclerView.setLayoutFrozen(false);
        localPlayRecyclerView.setAdapterInternal(localTitlePagerAdapter, true, false);
        localPlayRecyclerView.setDataSetChangedAfterLayout();
        localPlayRecyclerView.requestLayout();
        PlayHighlightsOverlayView.this.scrollTitleToPosition(PlayHighlightsOverlayView.this.mTitlePosition);
        for (int i = 0; i < PlayHighlightsOverlayView.this.mTitleRecycler.getChildCount(); i++) {
          ViewCompat.setAlpha(PlayHighlightsOverlayView.this.mTitleRecycler.getChildAt(i), 1.0F);
        }
        ViewCompat.animate(PlayHighlightsOverlayView.this.mTitleRecycler).alpha(1.0F).setDuration(250L).start();
      }
    };
    View localView = (View)localViewPropertyAnimatorCompat.mView.get();
    if (localView != null) {
      ViewPropertyAnimatorCompat.IMPL.withEndAction(localViewPropertyAnimatorCompat, localView, local3);
    }
    localViewPropertyAnimatorCompat.start();
  }
  
  public final void onCurrentHighlightChanged(int paramInt)
  {
    if (paramInt != this.mCurrentPage)
    {
      this.mCurrentPage = paramInt;
      if ((paramInt >= 0) && (paramInt != this.mTitlePosition) && (!this.mHasScrolled)) {
        scrollTitleToPosition(paramInt);
      }
    }
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mTitleRecycler = ((PlayRecyclerView)findViewById(2131755930));
    this.mPageIndicator = ((ScalingPageIndicator)findViewById(2131755931));
    ViewCompat.setImportantForAccessibility(this.mPageIndicator, 2);
    getContext();
    LinearLayoutManager localLinearLayoutManager = new LinearLayoutManager(0);
    this.mTitleRecycler.setLayoutManager(localLinearLayoutManager);
    this.mTitleRecycler.setHasFixedSize(true);
    this.mTitleRecycler.setFocusable(false);
    ViewCompat.setAccessibilityDelegate(this.mTitleRecycler, new AccessibilityDelegateCompat()
    {
      public final void onInitializeAccessibilityEvent(View paramAnonymousView, AccessibilityEvent paramAnonymousAccessibilityEvent)
      {
        AccessibilityRecordCompat localAccessibilityRecordCompat = AccessibilityEventCompat.asRecord(paramAnonymousAccessibilityEvent);
        AccessibilityRecordCompat.IMPL.setContentDescription(localAccessibilityRecordCompat.mRecord, null);
      }
    });
    if (Build.VERSION.SDK_INT >= 14) {
      this.mTitleRecycler.addOnScrollListener(new RecyclerView.OnScrollListener()
      {
        @TargetApi(14)
        public final void onScrolled(RecyclerView paramAnonymousRecyclerView, int paramAnonymousInt1, int paramAnonymousInt2)
        {
          super.onScrolled(paramAnonymousRecyclerView, paramAnonymousInt1, paramAnonymousInt2);
          paramAnonymousRecyclerView.getLayoutManager();
          int i = PlayHighlightsOverlayView.this.mTitleRecycler.getWidth();
          int j = PlayHighlightsOverlayView.this.mTitleRecycler.getLeft() + i / 2;
          for (int k = 0; k < paramAnonymousRecyclerView.getChildCount(); k++)
          {
            View localView = paramAnonymousRecyclerView.getChildAt(k);
            float f = Math.abs(LinearLayoutManager.getDecoratedLeft(localView) + LinearLayoutManager.getDecoratedMeasuredWidth(localView) / 2 - j) / PlayHighlightsOverlayView.this.mItemWidth;
            localView.setAlpha(Math.max(0.0F, 1.0F - PlayHighlightsOverlayView.this.mAlphaInterpolator.getInterpolation(f)));
          }
        }
      });
    }
  }
  
  public final void onHighlightScrolled(int paramInt)
  {
    if (!this.mTransitioningAdapter)
    {
      this.mTitleRecycler.scrollBy(paramInt, 0);
      this.mHasScrolled = true;
    }
  }
  
  public final void onHighlightsContentLoaded(int paramInt1, int paramInt2)
  {
    ScalingPageIndicator localScalingPageIndicator;
    if ((this.mCurrentHighlightsLogicalSection == paramInt1) && (paramInt2 == this.mCurrentPage))
    {
      localScalingPageIndicator = this.mPageIndicator;
      if (Build.VERSION.SDK_INT < 14) {
        break label64;
      }
      if (localScalingPageIndicator.mWaitingAnimator != null)
      {
        localScalingPageIndicator.mWaitingAnimator.cancel();
        localScalingPageIndicator.mWaitingAnimator = null;
      }
    }
    for (;;)
    {
      localScalingPageIndicator.ensureCurrentPageSelected();
      label64:
      do
      {
        this.mPageIndicator.setSelectedPage(this.mCurrentPage);
        return;
      } while (localScalingPageIndicator.mWaitingAnimation == null);
      localScalingPageIndicator.mWaitingAnimation.cancel();
      localScalingPageIndicator.mWaitingAnimation = null;
    }
  }
  
  public final void onHighlightsContentLoading(int paramInt)
  {
    ScalingPageIndicator localScalingPageIndicator;
    if (this.mCurrentHighlightsLogicalSection == paramInt)
    {
      localScalingPageIndicator = this.mPageIndicator;
      if (Build.VERSION.SDK_INT < 14) {
        break label200;
      }
      if (localScalingPageIndicator.mWaitingAnimator == null)
      {
        localScalingPageIndicator.mDotWaitingAnimators = new ArrayList(2 * localScalingPageIndicator.getChildCount());
        for (j = 0; j < localScalingPageIndicator.getChildCount(); j++)
        {
          localView = localScalingPageIndicator.getChildAt(j);
          k = j * 100;
          localAnimator1 = localScalingPageIndicator.createScaleAnimator(localView, 0.6F, 1.0F, 200L);
          localAnimator1.setStartDelay(k);
          localAnimator2 = localScalingPageIndicator.createScaleAnimator(localView, 1.0F, 0.6F, 500L);
          localAnimator2.setStartDelay(k + 200);
          localScalingPageIndicator.mDotWaitingAnimators.add(localAnimator1);
          localScalingPageIndicator.mDotWaitingAnimators.add(localAnimator2);
        }
        localAnimatorSet = new AnimatorSet();
        localAnimatorSet.playTogether(localScalingPageIndicator.mDotWaitingAnimators);
        localAnimatorSet.addListener(new ScalingPageIndicator.2(localScalingPageIndicator));
        localScalingPageIndicator.mWaitingAnimator = localAnimatorSet;
        localScalingPageIndicator.mWaitingAnimationCurrentCycle = 0;
        localScalingPageIndicator.mWaitingAnimator.start();
      }
    }
    label200:
    while ((localScalingPageIndicator.mWaitingAnimation != null) && (!localScalingPageIndicator.mWaitingAnimation.hasEnded()))
    {
      int j;
      View localView;
      int k;
      Animator localAnimator1;
      Animator localAnimator2;
      AnimatorSet localAnimatorSet;
      return;
    }
    int i = localScalingPageIndicator.getChildCount();
    ScalingPageIndicator.3 local3 = new ScalingPageIndicator.3(localScalingPageIndicator, i);
    local3.setDuration(500L * i);
    local3.setRepeatCount(40);
    local3.start();
  }
  
  public final void onHighlightsLoaded(int paramInt1, Document paramDocument, int paramInt2)
  {
    if (this.mCurrentHighlightsLogicalSection == paramInt1)
    {
      onPagesConfigurationChanged(paramDocument.getChildCount());
      this.mTitlePosition = paramInt2;
      loadTitles(paramDocument);
    }
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    return true;
  }
  
  public final void onPagesConfigurationChanged(int paramInt)
  {
    this.mHasScrolled = false;
    this.mPageIndicator.setPageCount(paramInt);
    this.mTitlePosition = -1;
    this.mCurrentPage = -1;
  }
  
  public final void onScrolledToPosition(int paramInt1, int paramInt2)
  {
    this.mLastOffset = paramInt2;
    scrollTitleToPosition(paramInt1);
  }
  
  public void setCurrentPage(int paramInt)
  {
    onCurrentHighlightChanged(paramInt);
    if ((paramInt >= 0) && (paramInt != this.mCurrentPage)) {
      scrollTitleToPosition(paramInt);
    }
  }
  
  public void setHighlightWidth(int paramInt)
  {
    this.mItemWidth = paramInt;
  }
  
  private static final class HighlightsTitleViewHolder
    extends RecyclerView.ViewHolder
  {
    final TextView subtitle;
    final TextView title;
    
    public HighlightsTitleViewHolder(View paramView)
    {
      super();
      this.title = ((TextView)paramView.findViewById(2131755173));
      this.subtitle = ((TextView)paramView.findViewById(2131755291));
    }
  }
  
  private static final class TitlePagerAdapter
    extends RecyclerView.Adapter<PlayHighlightsOverlayView.HighlightsTitleViewHolder>
  {
    final int mCenterPosition;
    private final int mColWidth;
    private final Document mHighlightsDoc;
    private final LayoutInflater mInflater;
    private final int mRealCount;
    
    public TitlePagerAdapter(Context paramContext, Document paramDocument, int paramInt)
    {
      this.mHighlightsDoc = paramDocument;
      this.mRealCount = paramDocument.getChildCount();
      this.mInflater = LayoutInflater.from(paramContext);
      this.mColWidth = paramInt;
      if (this.mRealCount == 0) {}
      for (int i = 0;; i = 100000000 / this.mRealCount * this.mRealCount)
      {
        this.mCenterPosition = i;
        return;
      }
    }
    
    public final int getItemCount()
    {
      if (this.mRealCount == 0) {
        return 0;
      }
      return 200000000;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayHighlightsOverlayView
 * JD-Core Version:    0.7.0.1
 */