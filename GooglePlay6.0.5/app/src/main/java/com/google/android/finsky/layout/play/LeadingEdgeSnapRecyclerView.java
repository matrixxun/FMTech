package com.google.android.finsky.layout.play;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.RecyclerView.SmoothScroller;
import android.support.v7.widget.RecyclerView.SmoothScroller.Action;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.DecelerateInterpolator;
import com.google.android.finsky.adapters.Recyclable;

public class LeadingEdgeSnapRecyclerView
  extends PlayRecyclerView
  implements Recyclable
{
  boolean mIgnoreNextTouchSequence;
  protected boolean mIsViewRecycled = false;
  private int mLeadingGapForSnapping;
  private final int mMinVelocity = ViewConfiguration.get(getContext()).getScaledMinimumFlingVelocity();
  private int mNearestChild;
  private int mNearestOffset;
  private boolean mNearestOffsetIsForEndAlignment;
  private final RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener()
  {
    public final void onScrollStateChanged(RecyclerView paramAnonymousRecyclerView, int paramAnonymousInt)
    {
      if (!LeadingEdgeSnapRecyclerView.this.supportsSnapping()) {}
      while (LeadingEdgeSnapRecyclerView.this.mIsViewRecycled) {
        return;
      }
      if (paramAnonymousInt != 2) {
        LeadingEdgeSnapRecyclerView.access$000$7a24dc0e(LeadingEdgeSnapRecyclerView.this);
      }
      if (paramAnonymousInt == 0) {}
      for (boolean bool = true;; bool = false)
      {
        if ((bool) && (!LeadingEdgeSnapRecyclerView.this.mWasIdle)) {
          LeadingEdgeSnapRecyclerView.this.smoothScrollBy(LeadingEdgeSnapRecyclerView.this.mNearestOffset, 0);
        }
        LeadingEdgeSnapRecyclerView.access$102(LeadingEdgeSnapRecyclerView.this, bool);
        if (bool) {
          LeadingEdgeSnapRecyclerView.this.onScrollEnded();
        }
        super.onScrollStateChanged(paramAnonymousRecyclerView, paramAnonymousInt);
        return;
      }
    }
  };
  private int mSwipeDistanceX;
  private int mSwipeStartX;
  private boolean mWasIdle = false;
  
  public LeadingEdgeSnapRecyclerView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public LeadingEdgeSnapRecyclerView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    addOnScrollListener(this.mOnScrollListener);
  }
  
  private static int getChildWidth(View paramView)
  {
    RecyclerView.LayoutParams localLayoutParams = (RecyclerView.LayoutParams)paramView.getLayoutParams();
    return paramView.getWidth() + localLayoutParams.leftMargin + localLayoutParams.rightMargin;
  }
  
  private int getFullyVisibleChildrenAfterPadding(int paramInt)
  {
    return Math.max(1, (getWidth() - getLeadingGapForSnapping()) / paramInt);
  }
  
  private void recomputeChildNearestToTheSnapOffset(boolean paramBoolean)
  {
    if (ViewCompat.getLayoutDirection(this) == 0) {}
    int j;
    int k;
    int i5;
    int i6;
    for (int i = 1;; i = 0)
    {
      j = getWidth();
      k = getChildCount();
      m = this.mLeadingGapForSnapping;
      this.mNearestChild = -1;
      this.mNearestOffset = 2147483647;
      this.mNearestOffsetIsForEndAlignment = false;
      if (!paramBoolean) {
        break label168;
      }
      int i2 = getAdapter().getItemCount();
      int i3 = ((LinearLayoutManager)getLayoutManager()).findLastVisibleItemPosition() - (i2 - 1 - getTrailingSpacerCount());
      if (i3 <= 0) {
        break label168;
      }
      int i4 = getChildWidth(getChildAt(-1 + (k - getTrailingSpacerCount())));
      i5 = i4 * getFullyVisibleChildrenAfterPadding(i4);
      i6 = 0;
      for (int i7 = 0; i7 < i3; i7++) {
        i6 += getChildWidth(getChildAt(k - 1 - i7));
      }
    }
    int m = j - i6 - i5;
    this.mNearestOffsetIsForEndAlignment = true;
    label168:
    int n = 0;
    if (n < k)
    {
      View localView = getChildAt(n);
      if (localView.getWidth() != 0) {
        if (i == 0) {
          break label241;
        }
      }
      label241:
      for (int i1 = localView.getLeft() - m;; i1 = m + localView.getRight() - j)
      {
        if (Math.abs(i1) < Math.abs(this.mNearestOffset))
        {
          this.mNearestOffset = i1;
          this.mNearestChild = n;
        }
        n++;
        break;
      }
    }
  }
  
  private void trackSwipes(MotionEvent paramMotionEvent)
  {
    switch (paramMotionEvent.getAction())
    {
    default: 
      return;
    case 0: 
      this.mSwipeStartX = ((int)paramMotionEvent.getX());
      return;
    }
    this.mSwipeDistanceX = Math.abs((int)paramMotionEvent.getX() - this.mSwipeStartX);
  }
  
  public final void bindView()
  {
    this.mIsViewRecycled = false;
  }
  
  public final boolean fling(int paramInt1, int paramInt2)
  {
    int i = 0;
    if ((!supportsSnapping()) || (Math.abs(paramInt1) < this.mMinVelocity) || (getChildCount() <= 0)) {
      return super.fling(paramInt1, paramInt2);
    }
    int j;
    int k;
    if (ViewCompat.getLayoutDirection(this) == 0)
    {
      j = 1;
      k = paramInt1 * j;
      if (k <= 0) {
        break label103;
      }
    }
    final int i11;
    label103:
    for (boolean bool1 = true;; bool1 = false)
    {
      recomputeChildNearestToTheSnapOffset(bool1);
      if (this.mNearestChild != -1) {
        break label109;
      }
      i11 = 0;
      ((LinearLayoutManager)getLayoutManager()).startSmoothScroll(new RecyclerView.SmoothScroller()
      {
        protected final void onSeekTargetStep$64702b56(int paramAnonymousInt1, int paramAnonymousInt2, RecyclerView.SmoothScroller.Action paramAnonymousAction)
        {
          if (paramAnonymousAction.mDx == 0)
          {
            int i = i11;
            paramAnonymousAction.changed = true;
            paramAnonymousAction.mDx = i;
            int j = 125 + (int)(275.0D * Math.sqrt(Math.abs(i11) / LeadingEdgeSnapRecyclerView.this.getWidth()));
            paramAnonymousAction.changed = true;
            paramAnonymousAction.mDuration = j;
            DecelerateInterpolator localDecelerateInterpolator = new DecelerateInterpolator(2.0F);
            paramAnonymousAction.changed = true;
            paramAnonymousAction.mInterpolator = localDecelerateInterpolator;
          }
        }
        
        protected final void onStop() {}
        
        protected final void onTargetFound$68abd3fe(View paramAnonymousView, RecyclerView.SmoothScroller.Action paramAnonymousAction) {}
      });
      return true;
      j = -1;
      break;
    }
    label109:
    int m = j * this.mNearestOffset;
    int n;
    label126:
    int i1;
    int i2;
    int i3;
    int i4;
    label162:
    int i6;
    if (k < 0)
    {
      n = -1;
      i1 = getWidth();
      i2 = getChildWidth(getChildAt(this.mNearestChild));
      i3 = getFullyVisibleChildrenAfterPadding(i2);
      if (i3 <= 1) {
        break label270;
      }
      i4 = 2;
      int i5 = i4 * i1 - this.mSwipeDistanceX;
      i6 = (n * Math.min(n * (k / 5), i5) - m) / i2;
      if ((i6 != 0) || (n * m >= 0)) {
        break label363;
      }
    }
    for (;;)
    {
      RecyclerView.LayoutManager localLayoutManager = getLayoutManager();
      int i7 = n + RecyclerView.LayoutManager.getPosition(getChildAt(this.mNearestChild));
      if (i7 <= 0) {
        n += 1 - i7;
      }
      for (;;)
      {
        i11 = j * (i + (m + n * i2));
        break;
        n = 1;
        break label126;
        label270:
        i4 = 1;
        break label162;
        int i8 = getTrailingSpacerCount();
        i = 0;
        if (i8 < 2)
        {
          int i9 = localLayoutManager.getItemCount() - getTrailingSpacerCount() - i3;
          i = 0;
          if (i7 > i9)
          {
            n -= i7 - i9;
            boolean bool2 = this.mNearestOffsetIsForEndAlignment;
            i = 0;
            if (!bool2)
            {
              int i10 = i1 - getLeadingGapForSnapping() - i3 * i2;
              i = getLeadingGapForSnapping() - i10;
            }
          }
        }
      }
      label363:
      n = i6;
    }
  }
  
  public int getLeadingGapForSnapping()
  {
    return this.mLeadingGapForSnapping;
  }
  
  protected int getTrailingSpacerCount()
  {
    return 0;
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    if (this.mIgnoreNextTouchSequence)
    {
      if ((paramMotionEvent.getAction() == 3) || (paramMotionEvent.getAction() == 1)) {
        this.mIgnoreNextTouchSequence = false;
      }
      return false;
    }
    trackSwipes(paramMotionEvent);
    return super.onInterceptTouchEvent(paramMotionEvent);
  }
  
  protected void onLeadingGapForSnappingChanged() {}
  
  public void onRecycle()
  {
    this.mIsViewRecycled = true;
  }
  
  protected void onScrollEnded() {}
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    trackSwipes(paramMotionEvent);
    return super.onTouchEvent(paramMotionEvent);
  }
  
  public void setLayoutManager(RecyclerView.LayoutManager paramLayoutManager)
  {
    if (!(paramLayoutManager instanceof LinearLayoutManager)) {
      throw new IllegalArgumentException("Only LinearLayoutManager is supported");
    }
    if (((LinearLayoutManager)paramLayoutManager).mOrientation != 0) {
      throw new IllegalArgumentException("Only horizontal LinearLayoutManager is supported");
    }
    super.setLayoutManager(paramLayoutManager);
  }
  
  public void setLeadingGapForSnapping(int paramInt)
  {
    if (this.mLeadingGapForSnapping != paramInt)
    {
      this.mLeadingGapForSnapping = paramInt;
      onLeadingGapForSnappingChanged();
    }
  }
  
  protected boolean supportsSnapping()
  {
    return true;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.LeadingEdgeSnapRecyclerView
 * JD-Core Version:    0.7.0.1
 */