package android.support.v7.widget;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.support.v4.view.accessibility.AccessibilityRecordCompat;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import java.util.List;

public class LinearLayoutManager
  extends RecyclerView.LayoutManager
{
  final AnchorInfo mAnchorInfo = new AnchorInfo();
  private boolean mLastStackFromEnd;
  private LayoutState mLayoutState;
  public int mOrientation;
  OrientationHelper mOrientationHelper;
  SavedState mPendingSavedState = null;
  int mPendingScrollPosition = -1;
  int mPendingScrollPositionOffset = -2147483648;
  private boolean mRecycleChildrenOnDetach;
  private boolean mReverseLayout = false;
  boolean mShouldReverseLayout = false;
  private boolean mSmoothScrollbarEnabled = true;
  private boolean mStackFromEnd = false;
  
  public LinearLayoutManager()
  {
    this(1);
  }
  
  public LinearLayoutManager(int paramInt)
  {
    if ((paramInt != 0) && (paramInt != 1)) {
      throw new IllegalArgumentException("invalid orientation:" + paramInt);
    }
    assertNotInLayoutOrScroll(null);
    if (paramInt != this.mOrientation)
    {
      this.mOrientation = paramInt;
      this.mOrientationHelper = null;
      requestLayout();
    }
    assertNotInLayoutOrScroll(null);
    if (this.mReverseLayout)
    {
      this.mReverseLayout = false;
      requestLayout();
    }
  }
  
  private int computeScrollExtent(RecyclerView.State paramState)
  {
    boolean bool1 = true;
    if (getChildCount() == 0) {
      return 0;
    }
    ensureLayoutState();
    OrientationHelper localOrientationHelper = this.mOrientationHelper;
    boolean bool2;
    label30:
    View localView1;
    if (!this.mSmoothScrollbarEnabled)
    {
      bool2 = bool1;
      localView1 = findFirstVisibleChildClosestToStart$2930a1b7(bool2);
      if (this.mSmoothScrollbarEnabled) {
        break label110;
      }
    }
    View localView2;
    for (;;)
    {
      localView2 = findFirstVisibleChildClosestToEnd$2930a1b7(bool1);
      boolean bool3 = this.mSmoothScrollbarEnabled;
      if ((getChildCount() == 0) || (paramState.getItemCount() == 0) || (localView1 == null) || (localView2 == null)) {
        break;
      }
      if (bool3) {
        break label115;
      }
      return 1 + Math.abs(RecyclerView.LayoutManager.getPosition(localView1) - RecyclerView.LayoutManager.getPosition(localView2));
      bool2 = false;
      break label30;
      label110:
      bool1 = false;
    }
    label115:
    int i = localOrientationHelper.getDecoratedEnd(localView2) - localOrientationHelper.getDecoratedStart(localView1);
    return Math.min(localOrientationHelper.getTotalSpace(), i);
  }
  
  private int computeScrollOffset(RecyclerView.State paramState)
  {
    boolean bool1 = true;
    int i = getChildCount();
    int j = 0;
    if (i == 0) {}
    label258:
    for (;;)
    {
      return j;
      ensureLayoutState();
      OrientationHelper localOrientationHelper = this.mOrientationHelper;
      boolean bool2;
      View localView1;
      label52:
      View localView2;
      boolean bool3;
      int n;
      int i1;
      if (!this.mSmoothScrollbarEnabled)
      {
        bool2 = bool1;
        localView1 = findFirstVisibleChildClosestToStart$2930a1b7(bool2);
        if (this.mSmoothScrollbarEnabled) {
          break label244;
        }
        localView2 = findFirstVisibleChildClosestToEnd$2930a1b7(bool1);
        bool3 = this.mSmoothScrollbarEnabled;
        boolean bool4 = this.mShouldReverseLayout;
        int k = getChildCount();
        j = 0;
        if (k == 0) {
          continue;
        }
        int m = paramState.getItemCount();
        j = 0;
        if (m == 0) {
          continue;
        }
        j = 0;
        if (localView1 == null) {
          continue;
        }
        j = 0;
        if (localView2 == null) {
          continue;
        }
        n = Math.min(RecyclerView.LayoutManager.getPosition(localView1), RecyclerView.LayoutManager.getPosition(localView2));
        i1 = Math.max(RecyclerView.LayoutManager.getPosition(localView1), RecyclerView.LayoutManager.getPosition(localView2));
        if (!bool4) {
          break label249;
        }
      }
      label244:
      label249:
      for (j = Math.max(0, -1 + (paramState.getItemCount() - i1));; j = Math.max(0, n))
      {
        if (!bool3) {
          break label258;
        }
        int i2 = Math.abs(localOrientationHelper.getDecoratedEnd(localView2) - localOrientationHelper.getDecoratedStart(localView1));
        int i3 = 1 + Math.abs(RecyclerView.LayoutManager.getPosition(localView1) - RecyclerView.LayoutManager.getPosition(localView2));
        return Math.round(i2 / i3 * j + (localOrientationHelper.getStartAfterPadding() - localOrientationHelper.getDecoratedStart(localView1)));
        bool2 = false;
        break;
        bool1 = false;
        break label52;
      }
    }
  }
  
  private int computeScrollRange(RecyclerView.State paramState)
  {
    boolean bool1 = true;
    if (getChildCount() == 0) {
      return 0;
    }
    ensureLayoutState();
    OrientationHelper localOrientationHelper = this.mOrientationHelper;
    boolean bool2;
    label30:
    View localView1;
    if (!this.mSmoothScrollbarEnabled)
    {
      bool2 = bool1;
      localView1 = findFirstVisibleChildClosestToStart$2930a1b7(bool2);
      if (this.mSmoothScrollbarEnabled) {
        break label98;
      }
    }
    View localView2;
    for (;;)
    {
      localView2 = findFirstVisibleChildClosestToEnd$2930a1b7(bool1);
      boolean bool3 = this.mSmoothScrollbarEnabled;
      if ((getChildCount() == 0) || (paramState.getItemCount() == 0) || (localView1 == null) || (localView2 == null)) {
        break;
      }
      if (bool3) {
        break label103;
      }
      return paramState.getItemCount();
      bool2 = false;
      break label30;
      label98:
      bool1 = false;
    }
    label103:
    int i = localOrientationHelper.getDecoratedEnd(localView2) - localOrientationHelper.getDecoratedStart(localView1);
    int j = 1 + Math.abs(RecyclerView.LayoutManager.getPosition(localView1) - RecyclerView.LayoutManager.getPosition(localView2));
    return (int)(i / j * paramState.getItemCount());
  }
  
  private int fill(RecyclerView.Recycler paramRecycler, LayoutState paramLayoutState, RecyclerView.State paramState, boolean paramBoolean)
  {
    int i = paramLayoutState.mAvailable;
    if (paramLayoutState.mScrollingOffset != -2147483648)
    {
      if (paramLayoutState.mAvailable < 0) {
        paramLayoutState.mScrollingOffset += paramLayoutState.mAvailable;
      }
      recycleByLayoutState(paramRecycler, paramLayoutState);
    }
    int j = paramLayoutState.mAvailable + paramLayoutState.mExtra;
    LayoutChunkResult localLayoutChunkResult = new LayoutChunkResult();
    do
    {
      if ((j <= 0) || (!paramLayoutState.hasMore(paramState))) {
        break;
      }
      localLayoutChunkResult.mConsumed = 0;
      localLayoutChunkResult.mFinished = false;
      localLayoutChunkResult.mIgnoreConsumed = false;
      localLayoutChunkResult.mFocusable = false;
      layoutChunk(paramRecycler, paramState, paramLayoutState, localLayoutChunkResult);
      if (localLayoutChunkResult.mFinished) {
        break;
      }
      paramLayoutState.mOffset += localLayoutChunkResult.mConsumed * paramLayoutState.mLayoutDirection;
      if ((!localLayoutChunkResult.mIgnoreConsumed) || (this.mLayoutState.mScrapList != null) || (!paramState.mInPreLayout))
      {
        paramLayoutState.mAvailable -= localLayoutChunkResult.mConsumed;
        j -= localLayoutChunkResult.mConsumed;
      }
      if (paramLayoutState.mScrollingOffset != -2147483648)
      {
        paramLayoutState.mScrollingOffset += localLayoutChunkResult.mConsumed;
        if (paramLayoutState.mAvailable < 0) {
          paramLayoutState.mScrollingOffset += paramLayoutState.mAvailable;
        }
        recycleByLayoutState(paramRecycler, paramLayoutState);
      }
    } while ((!paramBoolean) || (!localLayoutChunkResult.mFocusable));
    return i - paramLayoutState.mAvailable;
  }
  
  private View findFirstReferenceChild(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    return findReferenceChild(paramRecycler, paramState, 0, getChildCount(), paramState.getItemCount());
  }
  
  private View findFirstVisibleChildClosestToEnd$2930a1b7(boolean paramBoolean)
  {
    if (this.mShouldReverseLayout) {
      return findOneVisibleChild(0, getChildCount(), paramBoolean, true);
    }
    return findOneVisibleChild(-1 + getChildCount(), -1, paramBoolean, true);
  }
  
  private View findFirstVisibleChildClosestToStart$2930a1b7(boolean paramBoolean)
  {
    if (this.mShouldReverseLayout) {
      return findOneVisibleChild(-1 + getChildCount(), -1, paramBoolean, true);
    }
    return findOneVisibleChild(0, getChildCount(), paramBoolean, true);
  }
  
  private View findLastReferenceChild(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    return findReferenceChild(paramRecycler, paramState, -1 + getChildCount(), -1, paramState.getItemCount());
  }
  
  private View findOneVisibleChild(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
  {
    ensureLayoutState();
    int i = this.mOrientationHelper.getStartAfterPadding();
    int j = this.mOrientationHelper.getEndAfterPadding();
    int k;
    Object localObject;
    int m;
    if (paramInt2 > paramInt1)
    {
      k = 1;
      localObject = null;
      m = paramInt1;
    }
    for (;;)
    {
      if (m == paramInt2) {
        break label132;
      }
      View localView = getChildAt(m);
      int n = this.mOrientationHelper.getDecoratedStart(localView);
      int i1 = this.mOrientationHelper.getDecoratedEnd(localView);
      if ((n < j) && (i1 > i))
      {
        if ((!paramBoolean1) || ((n >= i) && (i1 <= j)))
        {
          return localView;
          k = -1;
          break;
        }
        if (localObject == null) {
          localObject = localView;
        }
      }
      m += k;
    }
    label132:
    return localObject;
  }
  
  private View findReferenceChildClosestToEnd(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    if (this.mShouldReverseLayout) {
      return findFirstReferenceChild(paramRecycler, paramState);
    }
    return findLastReferenceChild(paramRecycler, paramState);
  }
  
  private View findReferenceChildClosestToStart(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    if (this.mShouldReverseLayout) {
      return findLastReferenceChild(paramRecycler, paramState);
    }
    return findFirstReferenceChild(paramRecycler, paramState);
  }
  
  private int fixLayoutEndGap(int paramInt, RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, boolean paramBoolean)
  {
    int i = this.mOrientationHelper.getEndAfterPadding() - paramInt;
    if (i > 0)
    {
      int j = -scrollBy(-i, paramRecycler, paramState);
      int k = paramInt + j;
      if (paramBoolean)
      {
        int m = this.mOrientationHelper.getEndAfterPadding() - k;
        if (m > 0)
        {
          this.mOrientationHelper.offsetChildren(m);
          j += m;
        }
      }
      return j;
    }
    return 0;
  }
  
  private int fixLayoutStartGap(int paramInt, RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, boolean paramBoolean)
  {
    int i = paramInt - this.mOrientationHelper.getStartAfterPadding();
    if (i > 0)
    {
      int j = -scrollBy(i, paramRecycler, paramState);
      int k = paramInt + j;
      if (paramBoolean)
      {
        int m = k - this.mOrientationHelper.getStartAfterPadding();
        if (m > 0)
        {
          this.mOrientationHelper.offsetChildren(-m);
          j -= m;
        }
      }
      return j;
    }
    return 0;
  }
  
  private View getChildClosestToEnd()
  {
    if (this.mShouldReverseLayout) {}
    for (int i = 0;; i = -1 + getChildCount()) {
      return getChildAt(i);
    }
  }
  
  private View getChildClosestToStart()
  {
    if (this.mShouldReverseLayout) {}
    for (int i = -1 + getChildCount();; i = 0) {
      return getChildAt(i);
    }
  }
  
  private int getExtraLayoutSpace(RecyclerView.State paramState)
  {
    if (paramState.mTargetPosition != -1) {}
    for (int i = 1;; i = 0)
    {
      int j = 0;
      if (i != 0) {
        j = this.mOrientationHelper.getTotalSpace();
      }
      return j;
    }
  }
  
  private void recycleByLayoutState(RecyclerView.Recycler paramRecycler, LayoutState paramLayoutState)
  {
    if (!paramLayoutState.mRecycle) {}
    for (;;)
    {
      return;
      if (paramLayoutState.mLayoutDirection == -1)
      {
        int n = paramLayoutState.mScrollingOffset;
        int i1 = getChildCount();
        if (n >= 0)
        {
          int i2 = this.mOrientationHelper.getEnd() - n;
          if (this.mShouldReverseLayout) {
            for (int i4 = 0; i4 < i1; i4++)
            {
              View localView4 = getChildAt(i4);
              if (this.mOrientationHelper.getDecoratedStart(localView4) < i2)
              {
                recycleChildren(paramRecycler, 0, i4);
                return;
              }
            }
          } else {
            for (int i3 = i1 - 1; i3 >= 0; i3--)
            {
              View localView3 = getChildAt(i3);
              if (this.mOrientationHelper.getDecoratedStart(localView3) < i2)
              {
                recycleChildren(paramRecycler, i1 - 1, i3);
                return;
              }
            }
          }
        }
      }
      else
      {
        int i = paramLayoutState.mScrollingOffset;
        if (i >= 0)
        {
          int j = getChildCount();
          if (this.mShouldReverseLayout) {
            for (int m = j - 1; m >= 0; m--)
            {
              View localView2 = getChildAt(m);
              if (this.mOrientationHelper.getDecoratedEnd(localView2) > i)
              {
                recycleChildren(paramRecycler, j - 1, m);
                return;
              }
            }
          } else {
            for (int k = 0; k < j; k++)
            {
              View localView1 = getChildAt(k);
              if (this.mOrientationHelper.getDecoratedEnd(localView1) > i)
              {
                recycleChildren(paramRecycler, 0, k);
                return;
              }
            }
          }
        }
      }
    }
  }
  
  private void recycleChildren(RecyclerView.Recycler paramRecycler, int paramInt1, int paramInt2)
  {
    if (paramInt1 == paramInt2) {}
    for (;;)
    {
      return;
      if (paramInt2 > paramInt1) {
        for (int j = paramInt2 - 1; j >= paramInt1; j--) {
          removeAndRecycleViewAt(j, paramRecycler);
        }
      } else {
        for (int i = paramInt1; i > paramInt2; i--) {
          removeAndRecycleViewAt(i, paramRecycler);
        }
      }
    }
  }
  
  private void resolveShouldLayoutReverse()
  {
    int i = 1;
    boolean bool;
    if ((this.mOrientation == i) || (!isLayoutRTL())) {
      bool = this.mReverseLayout;
    }
    for (;;)
    {
      this.mShouldReverseLayout = bool;
      return;
      if (this.mReverseLayout) {
        bool = false;
      }
    }
  }
  
  private int scrollBy(int paramInt, RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    if ((getChildCount() == 0) || (paramInt == 0)) {}
    int i;
    int j;
    int k;
    do
    {
      return 0;
      this.mLayoutState.mRecycle = true;
      ensureLayoutState();
      if (paramInt <= 0) {
        break;
      }
      i = 1;
      j = Math.abs(paramInt);
      updateLayoutState(i, j, true, paramState);
      k = this.mLayoutState.mScrollingOffset + fill(paramRecycler, this.mLayoutState, paramState, false);
    } while (k < 0);
    if (j > k) {}
    for (int m = i * k;; m = paramInt)
    {
      this.mOrientationHelper.offsetChildren(-m);
      this.mLayoutState.mLastScrollDelta = m;
      return m;
      i = -1;
      break;
    }
  }
  
  private void updateLayoutState(int paramInt1, int paramInt2, boolean paramBoolean, RecyclerView.State paramState)
  {
    int i = -1;
    int j = 1;
    this.mLayoutState.mExtra = getExtraLayoutSpace(paramState);
    this.mLayoutState.mLayoutDirection = paramInt1;
    int k;
    if (paramInt1 == j)
    {
      LayoutState localLayoutState4 = this.mLayoutState;
      localLayoutState4.mExtra += this.mOrientationHelper.getEndPadding();
      View localView2 = getChildClosestToEnd();
      LayoutState localLayoutState5 = this.mLayoutState;
      if (this.mShouldReverseLayout) {}
      for (;;)
      {
        localLayoutState5.mItemDirection = i;
        this.mLayoutState.mCurrentPosition = (getPosition(localView2) + this.mLayoutState.mItemDirection);
        this.mLayoutState.mOffset = this.mOrientationHelper.getDecoratedEnd(localView2);
        k = this.mOrientationHelper.getDecoratedEnd(localView2) - this.mOrientationHelper.getEndAfterPadding();
        this.mLayoutState.mAvailable = paramInt2;
        if (paramBoolean)
        {
          LayoutState localLayoutState3 = this.mLayoutState;
          localLayoutState3.mAvailable -= k;
        }
        this.mLayoutState.mScrollingOffset = k;
        return;
        i = j;
      }
    }
    View localView1 = getChildClosestToStart();
    LayoutState localLayoutState1 = this.mLayoutState;
    localLayoutState1.mExtra += this.mOrientationHelper.getStartAfterPadding();
    LayoutState localLayoutState2 = this.mLayoutState;
    if (this.mShouldReverseLayout) {}
    for (;;)
    {
      localLayoutState2.mItemDirection = j;
      this.mLayoutState.mCurrentPosition = (getPosition(localView1) + this.mLayoutState.mItemDirection);
      this.mLayoutState.mOffset = this.mOrientationHelper.getDecoratedStart(localView1);
      k = -this.mOrientationHelper.getDecoratedStart(localView1) + this.mOrientationHelper.getStartAfterPadding();
      break;
      j = i;
    }
  }
  
  private void updateLayoutStateToFillEnd(int paramInt1, int paramInt2)
  {
    this.mLayoutState.mAvailable = (this.mOrientationHelper.getEndAfterPadding() - paramInt2);
    LayoutState localLayoutState = this.mLayoutState;
    if (this.mShouldReverseLayout) {}
    for (int i = -1;; i = 1)
    {
      localLayoutState.mItemDirection = i;
      this.mLayoutState.mCurrentPosition = paramInt1;
      this.mLayoutState.mLayoutDirection = 1;
      this.mLayoutState.mOffset = paramInt2;
      this.mLayoutState.mScrollingOffset = -2147483648;
      return;
    }
  }
  
  private void updateLayoutStateToFillEnd(AnchorInfo paramAnchorInfo)
  {
    updateLayoutStateToFillEnd(paramAnchorInfo.mPosition, paramAnchorInfo.mCoordinate);
  }
  
  private void updateLayoutStateToFillStart(int paramInt1, int paramInt2)
  {
    this.mLayoutState.mAvailable = (paramInt2 - this.mOrientationHelper.getStartAfterPadding());
    this.mLayoutState.mCurrentPosition = paramInt1;
    LayoutState localLayoutState = this.mLayoutState;
    if (this.mShouldReverseLayout) {}
    for (int i = 1;; i = -1)
    {
      localLayoutState.mItemDirection = i;
      this.mLayoutState.mLayoutDirection = -1;
      this.mLayoutState.mOffset = paramInt2;
      this.mLayoutState.mScrollingOffset = -2147483648;
      return;
    }
  }
  
  private void updateLayoutStateToFillStart(AnchorInfo paramAnchorInfo)
  {
    updateLayoutStateToFillStart(paramAnchorInfo.mPosition, paramAnchorInfo.mCoordinate);
  }
  
  public final void assertNotInLayoutOrScroll(String paramString)
  {
    if (this.mPendingSavedState == null) {
      super.assertNotInLayoutOrScroll(paramString);
    }
  }
  
  public final boolean canScrollHorizontally()
  {
    return this.mOrientation == 0;
  }
  
  public final boolean canScrollVertically()
  {
    return this.mOrientation == 1;
  }
  
  public final int computeHorizontalScrollExtent(RecyclerView.State paramState)
  {
    return computeScrollExtent(paramState);
  }
  
  public final int computeHorizontalScrollOffset(RecyclerView.State paramState)
  {
    return computeScrollOffset(paramState);
  }
  
  public final int computeHorizontalScrollRange(RecyclerView.State paramState)
  {
    return computeScrollRange(paramState);
  }
  
  public final int computeVerticalScrollExtent(RecyclerView.State paramState)
  {
    return computeScrollExtent(paramState);
  }
  
  public final int computeVerticalScrollOffset(RecyclerView.State paramState)
  {
    return computeScrollOffset(paramState);
  }
  
  public final int computeVerticalScrollRange(RecyclerView.State paramState)
  {
    return computeScrollRange(paramState);
  }
  
  final void ensureLayoutState()
  {
    if (this.mLayoutState == null) {
      this.mLayoutState = new LayoutState();
    }
    if (this.mOrientationHelper == null) {
      switch (this.mOrientation)
      {
      default: 
        throw new IllegalArgumentException("invalid orientation");
      }
    }
    for (Object localObject = new OrientationHelper.1(this);; localObject = new OrientationHelper.2(this))
    {
      this.mOrientationHelper = ((OrientationHelper)localObject);
      return;
    }
  }
  
  public final int findFirstVisibleItemPosition()
  {
    View localView = findOneVisibleChild(0, getChildCount(), false, true);
    if (localView == null) {
      return -1;
    }
    return getPosition(localView);
  }
  
  public final int findLastVisibleItemPosition()
  {
    View localView = findOneVisibleChild(-1 + getChildCount(), -1, false, true);
    if (localView == null) {
      return -1;
    }
    return getPosition(localView);
  }
  
  View findReferenceChild(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, int paramInt1, int paramInt2, int paramInt3)
  {
    ensureLayoutState();
    Object localObject1 = null;
    Object localObject2 = null;
    int i = this.mOrientationHelper.getStartAfterPadding();
    int j = this.mOrientationHelper.getEndAfterPadding();
    int k;
    int m;
    label40:
    Object localObject3;
    if (paramInt2 > paramInt1)
    {
      k = 1;
      m = paramInt1;
      if (m == paramInt2) {
        break label156;
      }
      localObject3 = getChildAt(m);
      int n = getPosition((View)localObject3);
      if ((n >= 0) && (n < paramInt3))
      {
        if (!((RecyclerView.LayoutParams)((View)localObject3).getLayoutParams()).mViewHolder.isRemoved()) {
          break label116;
        }
        if (localObject1 == null) {
          localObject1 = localObject3;
        }
      }
    }
    for (;;)
    {
      m += k;
      break label40;
      k = -1;
      break;
      label116:
      if ((this.mOrientationHelper.getDecoratedStart((View)localObject3) < j) && (this.mOrientationHelper.getDecoratedEnd((View)localObject3) >= i)) {
        break label165;
      }
      if (localObject2 == null) {
        localObject2 = localObject3;
      }
    }
    label156:
    if (localObject2 != null)
    {
      localObject3 = localObject2;
      label165:
      return localObject3;
    }
    return localObject1;
  }
  
  public final View findViewByPosition(int paramInt)
  {
    int i = getChildCount();
    View localView;
    if (i == 0) {
      localView = null;
    }
    do
    {
      return localView;
      int j = paramInt - getPosition(getChildAt(0));
      if ((j < 0) || (j >= i)) {
        break;
      }
      localView = getChildAt(j);
    } while (getPosition(localView) == paramInt);
    return super.findViewByPosition(paramInt);
  }
  
  public RecyclerView.LayoutParams generateDefaultLayoutParams()
  {
    return new RecyclerView.LayoutParams(-2, -2);
  }
  
  protected final boolean isLayoutRTL()
  {
    return ViewCompat.getLayoutDirection(this.mRecyclerView) == 1;
  }
  
  void layoutChunk(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, LayoutState paramLayoutState, LayoutChunkResult paramLayoutChunkResult)
  {
    View localView = paramLayoutState.next(paramRecycler);
    if (localView == null)
    {
      paramLayoutChunkResult.mFinished = true;
      return;
    }
    RecyclerView.LayoutParams localLayoutParams1 = (RecyclerView.LayoutParams)localView.getLayoutParams();
    boolean bool4;
    label68:
    int i1;
    int n;
    label258:
    int m;
    int k;
    if (paramLayoutState.mScrapList == null)
    {
      boolean bool3 = this.mShouldReverseLayout;
      if (paramLayoutState.mLayoutDirection == -1)
      {
        bool4 = true;
        if (bool3 != bool4) {
          break label366;
        }
        super.addViewInt(localView, -1, false);
        RecyclerView.LayoutParams localLayoutParams2 = (RecyclerView.LayoutParams)localView.getLayoutParams();
        Rect localRect = this.mRecyclerView.getItemDecorInsetsForChild(localView);
        int i = 0 + (localRect.left + localRect.right);
        int j = 0 + (localRect.top + localRect.bottom);
        localView.measure(RecyclerView.LayoutManager.getChildMeasureSpec(getWidth(), i + (getPaddingLeft() + getPaddingRight() + localLayoutParams2.leftMargin + localLayoutParams2.rightMargin), localLayoutParams2.width, canScrollHorizontally()), RecyclerView.LayoutManager.getChildMeasureSpec(getHeight(), j + (getPaddingTop() + getPaddingBottom() + localLayoutParams2.topMargin + localLayoutParams2.bottomMargin), localLayoutParams2.height, canScrollVertically()));
        paramLayoutChunkResult.mConsumed = this.mOrientationHelper.getDecoratedMeasurement(localView);
        if (this.mOrientation != 1) {
          break label473;
        }
        if (!isLayoutRTL()) {
          break label429;
        }
        i1 = getWidth() - getPaddingRight();
        n = i1 - this.mOrientationHelper.getDecoratedMeasurementInOther(localView);
        if (paramLayoutState.mLayoutDirection != -1) {
          break label452;
        }
        m = paramLayoutState.mOffset;
        k = paramLayoutState.mOffset - paramLayoutChunkResult.mConsumed;
      }
    }
    for (;;)
    {
      layoutDecorated(localView, n + localLayoutParams1.leftMargin, k + localLayoutParams1.topMargin, i1 - localLayoutParams1.rightMargin, m - localLayoutParams1.bottomMargin);
      if ((localLayoutParams1.mViewHolder.isRemoved()) || (localLayoutParams1.mViewHolder.isUpdated())) {
        paramLayoutChunkResult.mIgnoreConsumed = true;
      }
      paramLayoutChunkResult.mFocusable = localView.isFocusable();
      return;
      bool4 = false;
      break;
      label366:
      super.addViewInt(localView, 0, false);
      break label68;
      boolean bool1 = this.mShouldReverseLayout;
      if (paramLayoutState.mLayoutDirection == -1) {}
      for (boolean bool2 = true;; bool2 = false)
      {
        if (bool1 != bool2) {
          break label418;
        }
        super.addViewInt(localView, -1, true);
        break;
      }
      label418:
      super.addViewInt(localView, 0, true);
      break label68;
      label429:
      n = getPaddingLeft();
      i1 = n + this.mOrientationHelper.getDecoratedMeasurementInOther(localView);
      break label258;
      label452:
      k = paramLayoutState.mOffset;
      m = paramLayoutState.mOffset + paramLayoutChunkResult.mConsumed;
      continue;
      label473:
      k = getPaddingTop();
      m = k + this.mOrientationHelper.getDecoratedMeasurementInOther(localView);
      if (paramLayoutState.mLayoutDirection == -1)
      {
        i1 = paramLayoutState.mOffset;
        n = paramLayoutState.mOffset - paramLayoutChunkResult.mConsumed;
      }
      else
      {
        n = paramLayoutState.mOffset;
        i1 = paramLayoutState.mOffset + paramLayoutChunkResult.mConsumed;
      }
    }
  }
  
  void onAnchorReady(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, AnchorInfo paramAnchorInfo) {}
  
  public final void onDetachedFromWindow(RecyclerView paramRecyclerView, RecyclerView.Recycler paramRecycler)
  {
    super.onDetachedFromWindow(paramRecyclerView, paramRecycler);
    if (this.mRecycleChildrenOnDetach)
    {
      removeAndRecycleAllViews(paramRecycler);
      paramRecycler.clear();
    }
  }
  
  public final View onFocusSearchFailed$1539f5dc(int paramInt, RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    resolveShouldLayoutReverse();
    View localView2;
    if (getChildCount() == 0) {
      localView2 = null;
    }
    for (;;)
    {
      return localView2;
      int i;
      switch (paramInt)
      {
      default: 
        i = -2147483648;
      }
      while (i == -2147483648)
      {
        return null;
        i = -1;
        continue;
        i = 1;
        continue;
        if (this.mOrientation == 1)
        {
          i = -1;
        }
        else
        {
          i = -2147483648;
          continue;
          if (this.mOrientation == 1)
          {
            i = 1;
          }
          else
          {
            i = -2147483648;
            continue;
            if (this.mOrientation == 0)
            {
              i = -1;
            }
            else
            {
              i = -2147483648;
              continue;
              if (this.mOrientation == 0) {
                i = 1;
              } else {
                i = -2147483648;
              }
            }
          }
        }
      }
      ensureLayoutState();
      if (i == -1) {}
      for (View localView1 = findReferenceChildClosestToStart(paramRecycler, paramState); localView1 == null; localView1 = findReferenceChildClosestToEnd(paramRecycler, paramState)) {
        return null;
      }
      ensureLayoutState();
      updateLayoutState(i, (int)(0.33F * this.mOrientationHelper.getTotalSpace()), false, paramState);
      this.mLayoutState.mScrollingOffset = -2147483648;
      this.mLayoutState.mRecycle = false;
      fill(paramRecycler, this.mLayoutState, paramState, true);
      if (i == -1) {}
      for (localView2 = getChildClosestToStart(); (localView2 == localView1) || (!localView2.isFocusable()); localView2 = getChildClosestToEnd()) {
        return null;
      }
    }
  }
  
  public final void onInitializeAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent)
  {
    super.onInitializeAccessibilityEvent(paramAccessibilityEvent);
    if (getChildCount() > 0)
    {
      AccessibilityRecordCompat localAccessibilityRecordCompat = AccessibilityEventCompat.asRecord(paramAccessibilityEvent);
      localAccessibilityRecordCompat.setFromIndex(findFirstVisibleItemPosition());
      localAccessibilityRecordCompat.setToIndex(findLastVisibleItemPosition());
    }
  }
  
  public void onLayoutChildren(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    if (((this.mPendingSavedState != null) || (this.mPendingScrollPosition != -1)) && (paramState.getItemCount() == 0))
    {
      removeAndRecycleAllViews(paramRecycler);
      return;
    }
    if ((this.mPendingSavedState != null) && (this.mPendingSavedState.hasValidAnchor())) {
      this.mPendingScrollPosition = this.mPendingSavedState.mAnchorPosition;
    }
    ensureLayoutState();
    this.mLayoutState.mRecycle = false;
    resolveShouldLayoutReverse();
    AnchorInfo localAnchorInfo1 = this.mAnchorInfo;
    localAnchorInfo1.mPosition = -1;
    localAnchorInfo1.mCoordinate = -2147483648;
    localAnchorInfo1.mLayoutFromEnd = false;
    this.mAnchorInfo.mLayoutFromEnd = (this.mShouldReverseLayout ^ this.mStackFromEnd);
    AnchorInfo localAnchorInfo2 = this.mAnchorInfo;
    int i;
    Object localObject;
    label155:
    int i32;
    label210:
    int i33;
    label240:
    int i28;
    label243:
    int i29;
    label268:
    int j;
    int m;
    int k;
    label299:
    int n;
    int i1;
    View localView2;
    int i27;
    label393:
    label405:
    int i2;
    label423:
    View localView1;
    if ((paramState.mInPreLayout) || (this.mPendingScrollPosition == -1))
    {
      i = 0;
      if (i == 0)
      {
        if (getChildCount() == 0) {
          break label1451;
        }
        if (this.mRecyclerView != null) {
          break label942;
        }
        localObject = null;
        if (localObject == null) {
          break label1293;
        }
        RecyclerView.LayoutParams localLayoutParams = (RecyclerView.LayoutParams)((View)localObject).getLayoutParams();
        if ((localLayoutParams.mViewHolder.isRemoved()) || (localLayoutParams.mViewHolder.getLayoutPosition() < 0) || (localLayoutParams.mViewHolder.getLayoutPosition() >= paramState.getItemCount())) {
          break label981;
        }
        i32 = 1;
        if (i32 == 0) {
          break label1293;
        }
        i33 = localAnchorInfo2.this$0.mOrientationHelper.getTotalSpaceChange();
        if (i33 < 0) {
          break label987;
        }
        localAnchorInfo2.assignFromView((View)localObject);
        i28 = 1;
        if (i28 == 0)
        {
          localAnchorInfo2.assignCoordinateFromPadding();
          if (!this.mStackFromEnd) {
            break label1457;
          }
          i29 = -1 + paramState.getItemCount();
          localAnchorInfo2.mPosition = i29;
        }
      }
      j = getExtraLayoutSpace(paramState);
      if (this.mLayoutState.mLastScrollDelta < 0) {
        break label1463;
      }
      m = j;
      k = 0;
      n = k + this.mOrientationHelper.getStartAfterPadding();
      i1 = m + this.mOrientationHelper.getEndPadding();
      if ((paramState.mInPreLayout) && (this.mPendingScrollPosition != -1) && (this.mPendingScrollPositionOffset != -2147483648))
      {
        localView2 = findViewByPosition(this.mPendingScrollPosition);
        if (localView2 != null)
        {
          if (!this.mShouldReverseLayout) {
            break label1473;
          }
          i27 = this.mOrientationHelper.getEndAfterPadding() - this.mOrientationHelper.getDecoratedEnd(localView2) - this.mPendingScrollPositionOffset;
          if (i27 <= 0) {
            break label1504;
          }
          n += i27;
        }
      }
      onAnchorReady(paramRecycler, paramState, this.mAnchorInfo);
      i2 = -1 + getChildCount();
      if (i2 < 0) {
        break label1529;
      }
      localView1 = getChildAt(i2);
      RecyclerView.ViewHolder localViewHolder2 = RecyclerView.getChildViewHolderInt(localView1);
      if (!localViewHolder2.shouldIgnore())
      {
        if ((!localViewHolder2.isInvalid()) || (localViewHolder2.isRemoved()) || (RecyclerView.access$2900(this.mRecyclerView).mHasStableIds)) {
          break label1514;
        }
        removeViewAt(i2);
        paramRecycler.recycleViewHolderInternal(localViewHolder2);
      }
    }
    for (;;)
    {
      i2--;
      break label423;
      if ((this.mPendingScrollPosition < 0) || (this.mPendingScrollPosition >= paramState.getItemCount()))
      {
        this.mPendingScrollPosition = -1;
        this.mPendingScrollPositionOffset = -2147483648;
        i = 0;
        break;
      }
      localAnchorInfo2.mPosition = this.mPendingScrollPosition;
      if ((this.mPendingSavedState != null) && (this.mPendingSavedState.hasValidAnchor()))
      {
        localAnchorInfo2.mLayoutFromEnd = this.mPendingSavedState.mAnchorLayoutFromEnd;
        if (localAnchorInfo2.mLayoutFromEnd) {}
        for (localAnchorInfo2.mCoordinate = (this.mOrientationHelper.getEndAfterPadding() - this.mPendingSavedState.mAnchorOffset);; localAnchorInfo2.mCoordinate = (this.mOrientationHelper.getStartAfterPadding() + this.mPendingSavedState.mAnchorOffset))
        {
          i = 1;
          break;
        }
      }
      View localView5;
      if (this.mPendingScrollPositionOffset == -2147483648)
      {
        localView5 = findViewByPosition(this.mPendingScrollPosition);
        if (localView5 != null) {
          if (this.mOrientationHelper.getDecoratedMeasurement(localView5) > this.mOrientationHelper.getTotalSpace()) {
            localAnchorInfo2.assignCoordinateFromPadding();
          }
        }
      }
      for (;;)
      {
        i = 1;
        break;
        if (this.mOrientationHelper.getDecoratedStart(localView5) - this.mOrientationHelper.getStartAfterPadding() < 0)
        {
          localAnchorInfo2.mCoordinate = this.mOrientationHelper.getStartAfterPadding();
          localAnchorInfo2.mLayoutFromEnd = false;
        }
        else if (this.mOrientationHelper.getEndAfterPadding() - this.mOrientationHelper.getDecoratedEnd(localView5) < 0)
        {
          localAnchorInfo2.mCoordinate = this.mOrientationHelper.getEndAfterPadding();
          localAnchorInfo2.mLayoutFromEnd = true;
        }
        else
        {
          if (localAnchorInfo2.mLayoutFromEnd) {}
          for (int i46 = this.mOrientationHelper.getDecoratedEnd(localView5) + this.mOrientationHelper.getTotalSpaceChange();; i46 = this.mOrientationHelper.getDecoratedStart(localView5))
          {
            localAnchorInfo2.mCoordinate = i46;
            i = 1;
            break;
          }
          int i45;
          if (getChildCount() > 0)
          {
            int i44 = getPosition(getChildAt(0));
            if (this.mPendingScrollPosition >= i44) {
              break label874;
            }
            i45 = 1;
            label847:
            if (i45 != this.mShouldReverseLayout) {
              break label880;
            }
          }
          label874:
          label880:
          for (boolean bool = true;; bool = false)
          {
            localAnchorInfo2.mLayoutFromEnd = bool;
            localAnchorInfo2.assignCoordinateFromPadding();
            break;
            i45 = 0;
            break label847;
          }
          localAnchorInfo2.mLayoutFromEnd = this.mShouldReverseLayout;
          if (this.mShouldReverseLayout) {
            localAnchorInfo2.mCoordinate = (this.mOrientationHelper.getEndAfterPadding() - this.mPendingScrollPositionOffset);
          } else {
            localAnchorInfo2.mCoordinate = (this.mOrientationHelper.getStartAfterPadding() + this.mPendingScrollPositionOffset);
          }
        }
      }
      label942:
      View localView3 = this.mRecyclerView.getFocusedChild();
      if ((localView3 == null) || (this.mChildHelper.isHidden(localView3)))
      {
        localObject = null;
        break label155;
      }
      localObject = localView3;
      break label155;
      label981:
      i32 = 0;
      break label210;
      label987:
      localAnchorInfo2.mPosition = getPosition((View)localObject);
      if (localAnchorInfo2.mLayoutFromEnd)
      {
        int i39 = localAnchorInfo2.this$0.mOrientationHelper.getEndAfterPadding() - i33 - localAnchorInfo2.this$0.mOrientationHelper.getDecoratedEnd((View)localObject);
        localAnchorInfo2.mCoordinate = (localAnchorInfo2.this$0.mOrientationHelper.getEndAfterPadding() - i39);
        if (i39 <= 0) {
          break label240;
        }
        int i40 = localAnchorInfo2.this$0.mOrientationHelper.getDecoratedMeasurement((View)localObject);
        int i41 = localAnchorInfo2.mCoordinate - i40;
        int i42 = localAnchorInfo2.this$0.mOrientationHelper.getStartAfterPadding();
        int i43 = i41 - (i42 + Math.min(localAnchorInfo2.this$0.mOrientationHelper.getDecoratedStart((View)localObject) - i42, 0));
        if (i43 >= 0) {
          break label240;
        }
        localAnchorInfo2.mCoordinate += Math.min(i39, -i43);
        break label240;
      }
      int i34 = localAnchorInfo2.this$0.mOrientationHelper.getDecoratedStart((View)localObject);
      int i35 = i34 - localAnchorInfo2.this$0.mOrientationHelper.getStartAfterPadding();
      localAnchorInfo2.mCoordinate = i34;
      if (i35 <= 0) {
        break label240;
      }
      int i36 = i34 + localAnchorInfo2.this$0.mOrientationHelper.getDecoratedMeasurement((View)localObject);
      int i37 = localAnchorInfo2.this$0.mOrientationHelper.getEndAfterPadding() - i33 - localAnchorInfo2.this$0.mOrientationHelper.getDecoratedEnd((View)localObject);
      int i38 = localAnchorInfo2.this$0.mOrientationHelper.getEndAfterPadding() - Math.min(0, i37) - i36;
      if (i38 >= 0) {
        break label240;
      }
      localAnchorInfo2.mCoordinate -= Math.min(i35, -i38);
      break label240;
      label1293:
      if (this.mLastStackFromEnd == this.mStackFromEnd)
      {
        View localView4;
        label1320:
        int i30;
        if (localAnchorInfo2.mLayoutFromEnd)
        {
          localView4 = findReferenceChildClosestToEnd(paramRecycler, paramState);
          if (localView4 == null) {
            break label1451;
          }
          localAnchorInfo2.assignFromView(localView4);
          if ((!paramState.mInPreLayout) && (supportsPredictiveItemAnimations()))
          {
            if ((this.mOrientationHelper.getDecoratedStart(localView4) < this.mOrientationHelper.getEndAfterPadding()) && (this.mOrientationHelper.getDecoratedEnd(localView4) >= this.mOrientationHelper.getStartAfterPadding())) {
              break label1433;
            }
            i30 = 1;
            label1387:
            if (i30 != 0) {
              if (!localAnchorInfo2.mLayoutFromEnd) {
                break label1439;
              }
            }
          }
        }
        label1433:
        label1439:
        for (int i31 = this.mOrientationHelper.getEndAfterPadding();; i31 = this.mOrientationHelper.getStartAfterPadding())
        {
          localAnchorInfo2.mCoordinate = i31;
          i28 = 1;
          break;
          localView4 = findReferenceChildClosestToStart(paramRecycler, paramState);
          break label1320;
          i30 = 0;
          break label1387;
        }
      }
      label1451:
      i28 = 0;
      break label243;
      label1457:
      i29 = 0;
      break label268;
      label1463:
      k = j;
      m = 0;
      break label299;
      label1473:
      int i26 = this.mOrientationHelper.getDecoratedStart(localView2) - this.mOrientationHelper.getStartAfterPadding();
      i27 = this.mPendingScrollPositionOffset - i26;
      break label393;
      label1504:
      i1 -= i27;
      break label405;
      label1514:
      detachViewAt(i2);
      paramRecycler.scrapView(localView1);
    }
    label1529:
    this.mLayoutState.mIsPreLayout = paramState.mInPreLayout;
    int i5;
    int i22;
    int i23;
    if (this.mAnchorInfo.mLayoutFromEnd)
    {
      updateLayoutStateToFillStart(this.mAnchorInfo);
      this.mLayoutState.mExtra = n;
      fill(paramRecycler, this.mLayoutState, paramState, false);
      i5 = this.mLayoutState.mOffset;
      int i24 = this.mLayoutState.mCurrentPosition;
      if (this.mLayoutState.mAvailable > 0) {
        i1 += this.mLayoutState.mAvailable;
      }
      updateLayoutStateToFillEnd(this.mAnchorInfo);
      this.mLayoutState.mExtra = i1;
      LayoutState localLayoutState2 = this.mLayoutState;
      localLayoutState2.mCurrentPosition += this.mLayoutState.mItemDirection;
      fill(paramRecycler, this.mLayoutState, paramState, false);
      i3 = this.mLayoutState.mOffset;
      if (this.mLayoutState.mAvailable > 0)
      {
        int i25 = this.mLayoutState.mAvailable;
        updateLayoutStateToFillStart(i24, i5);
        this.mLayoutState.mExtra = i25;
        fill(paramRecycler, this.mLayoutState, paramState, false);
        i5 = this.mLayoutState.mOffset;
      }
      if (getChildCount() > 0)
      {
        if (!(this.mShouldReverseLayout ^ this.mStackFromEnd)) {
          break label2074;
        }
        int i20 = fixLayoutEndGap(i3, paramRecycler, paramState, true);
        int i21 = i5 + i20;
        i22 = i3 + i20;
        i23 = fixLayoutStartGap(i21, paramRecycler, paramState, false);
        i5 = i21 + i23;
      }
    }
    label2074:
    int i18;
    int i19;
    for (int i3 = i22 + i23;; i3 = i18 + i19)
    {
      if ((paramState.mRunPredictiveAnimations) && (getChildCount() != 0) && (!paramState.mInPreLayout) && (supportsPredictiveItemAnimations())) {
        break label2127;
      }
      if (!paramState.mInPreLayout)
      {
        this.mPendingScrollPosition = -1;
        this.mPendingScrollPositionOffset = -2147483648;
        OrientationHelper localOrientationHelper = this.mOrientationHelper;
        localOrientationHelper.mLastTotalSpace = localOrientationHelper.getTotalSpace();
      }
      this.mLastStackFromEnd = this.mStackFromEnd;
      this.mPendingSavedState = null;
      return;
      updateLayoutStateToFillEnd(this.mAnchorInfo);
      this.mLayoutState.mExtra = i1;
      fill(paramRecycler, this.mLayoutState, paramState, false);
      i3 = this.mLayoutState.mOffset;
      int i4 = this.mLayoutState.mCurrentPosition;
      if (this.mLayoutState.mAvailable > 0) {
        n += this.mLayoutState.mAvailable;
      }
      updateLayoutStateToFillStart(this.mAnchorInfo);
      this.mLayoutState.mExtra = n;
      LayoutState localLayoutState1 = this.mLayoutState;
      localLayoutState1.mCurrentPosition += this.mLayoutState.mItemDirection;
      fill(paramRecycler, this.mLayoutState, paramState, false);
      i5 = this.mLayoutState.mOffset;
      if (this.mLayoutState.mAvailable <= 0) {
        break;
      }
      int i6 = this.mLayoutState.mAvailable;
      updateLayoutStateToFillEnd(i4, i3);
      this.mLayoutState.mExtra = i6;
      fill(paramRecycler, this.mLayoutState, paramState, false);
      i3 = this.mLayoutState.mOffset;
      break;
      int i16 = fixLayoutStartGap(i5, paramRecycler, paramState, true);
      int i17 = i5 + i16;
      i18 = i3 + i16;
      i19 = fixLayoutEndGap(i18, paramRecycler, paramState, false);
      i5 = i17 + i19;
    }
    label2127:
    int i7 = 0;
    int i8 = 0;
    List localList = paramRecycler.mUnmodifiableAttachedScrap;
    int i9 = localList.size();
    int i10 = getPosition(getChildAt(0));
    int i11 = 0;
    label2161:
    RecyclerView.ViewHolder localViewHolder1;
    int i14;
    label2203:
    int i15;
    label2215:
    int i13;
    int i12;
    if (i11 < i9)
    {
      localViewHolder1 = (RecyclerView.ViewHolder)localList.get(i11);
      if (localViewHolder1.isRemoved()) {
        break label2422;
      }
      if (localViewHolder1.getLayoutPosition() < i10)
      {
        i14 = 1;
        if (i14 == this.mShouldReverseLayout) {
          break label2262;
        }
        i15 = -1;
        if (i15 != -1) {
          break label2268;
        }
        i13 = i7 + this.mOrientationHelper.getDecoratedMeasurement(localViewHolder1.itemView);
        i12 = i8;
      }
    }
    for (;;)
    {
      i11++;
      i7 = i13;
      i8 = i12;
      break label2161;
      i14 = 0;
      break label2203;
      label2262:
      i15 = 1;
      break label2215;
      label2268:
      i12 = i8 + this.mOrientationHelper.getDecoratedMeasurement(localViewHolder1.itemView);
      i13 = i7;
      continue;
      this.mLayoutState.mScrapList = localList;
      if (i7 > 0)
      {
        updateLayoutStateToFillStart(getPosition(getChildClosestToStart()), i5);
        this.mLayoutState.mExtra = i7;
        this.mLayoutState.mAvailable = 0;
        this.mLayoutState.assignPositionFromScrapList(null);
        fill(paramRecycler, this.mLayoutState, paramState, false);
      }
      if (i8 > 0)
      {
        updateLayoutStateToFillEnd(getPosition(getChildClosestToEnd()), i3);
        this.mLayoutState.mExtra = i8;
        this.mLayoutState.mAvailable = 0;
        this.mLayoutState.assignPositionFromScrapList(null);
        fill(paramRecycler, this.mLayoutState, paramState, false);
      }
      this.mLayoutState.mScrapList = null;
      break;
      label2422:
      i12 = i8;
      i13 = i7;
    }
  }
  
  public final void onRestoreInstanceState(Parcelable paramParcelable)
  {
    if ((paramParcelable instanceof SavedState))
    {
      this.mPendingSavedState = ((SavedState)paramParcelable);
      requestLayout();
    }
  }
  
  public final Parcelable onSaveInstanceState()
  {
    if (this.mPendingSavedState != null) {
      return new SavedState(this.mPendingSavedState);
    }
    SavedState localSavedState = new SavedState();
    if (getChildCount() > 0)
    {
      ensureLayoutState();
      boolean bool = this.mLastStackFromEnd ^ this.mShouldReverseLayout;
      localSavedState.mAnchorLayoutFromEnd = bool;
      if (bool)
      {
        View localView2 = getChildClosestToEnd();
        localSavedState.mAnchorOffset = (this.mOrientationHelper.getEndAfterPadding() - this.mOrientationHelper.getDecoratedEnd(localView2));
        localSavedState.mAnchorPosition = getPosition(localView2);
        return localSavedState;
      }
      View localView1 = getChildClosestToStart();
      localSavedState.mAnchorPosition = getPosition(localView1);
      localSavedState.mAnchorOffset = (this.mOrientationHelper.getDecoratedStart(localView1) - this.mOrientationHelper.getStartAfterPadding());
      return localSavedState;
    }
    localSavedState.mAnchorPosition = -1;
    return localSavedState;
  }
  
  public final int scrollHorizontallyBy(int paramInt, RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    if (this.mOrientation == 1) {
      return 0;
    }
    return scrollBy(paramInt, paramRecycler, paramState);
  }
  
  public final void scrollToPosition(int paramInt)
  {
    this.mPendingScrollPosition = paramInt;
    this.mPendingScrollPositionOffset = -2147483648;
    if (this.mPendingSavedState != null) {
      this.mPendingSavedState.mAnchorPosition = -1;
    }
    requestLayout();
  }
  
  public final void scrollToPositionWithOffset(int paramInt1, int paramInt2)
  {
    this.mPendingScrollPosition = paramInt1;
    this.mPendingScrollPositionOffset = paramInt2;
    if (this.mPendingSavedState != null) {
      this.mPendingSavedState.mAnchorPosition = -1;
    }
    requestLayout();
  }
  
  public final int scrollVerticallyBy(int paramInt, RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    if (this.mOrientation == 0) {
      return 0;
    }
    return scrollBy(paramInt, paramRecycler, paramState);
  }
  
  public final void smoothScrollToPosition$7d69765f(RecyclerView paramRecyclerView, int paramInt)
  {
    LinearSmoothScroller local1 = new LinearSmoothScroller(paramRecyclerView.getContext())
    {
      public final PointF computeScrollVectorForPosition(int paramAnonymousInt)
      {
        int i = 1;
        LinearLayoutManager localLinearLayoutManager = LinearLayoutManager.this;
        if (localLinearLayoutManager.getChildCount() == 0) {
          return null;
        }
        int j = LinearLayoutManager.getPosition(localLinearLayoutManager.getChildAt(0));
        int k = 0;
        if (paramAnonymousInt < j) {
          k = i;
        }
        if (k != localLinearLayoutManager.mShouldReverseLayout) {
          i = -1;
        }
        if (localLinearLayoutManager.mOrientation == 0) {
          return new PointF(i, 0.0F);
        }
        return new PointF(0.0F, i);
      }
    };
    local1.mTargetPosition = 0;
    startSmoothScroll(local1);
  }
  
  public boolean supportsPredictiveItemAnimations()
  {
    return (this.mPendingSavedState == null) && (this.mLastStackFromEnd == this.mStackFromEnd);
  }
  
  final class AnchorInfo
  {
    int mCoordinate;
    boolean mLayoutFromEnd;
    int mPosition;
    
    AnchorInfo() {}
    
    final void assignCoordinateFromPadding()
    {
      if (this.mLayoutFromEnd) {}
      for (int i = LinearLayoutManager.this.mOrientationHelper.getEndAfterPadding();; i = LinearLayoutManager.this.mOrientationHelper.getStartAfterPadding())
      {
        this.mCoordinate = i;
        return;
      }
    }
    
    public final void assignFromView(View paramView)
    {
      if (this.mLayoutFromEnd) {}
      for (this.mCoordinate = (LinearLayoutManager.this.mOrientationHelper.getDecoratedEnd(paramView) + LinearLayoutManager.this.mOrientationHelper.getTotalSpaceChange());; this.mCoordinate = LinearLayoutManager.this.mOrientationHelper.getDecoratedStart(paramView))
      {
        this.mPosition = LinearLayoutManager.getPosition(paramView);
        return;
      }
    }
    
    public final String toString()
    {
      return "AnchorInfo{mPosition=" + this.mPosition + ", mCoordinate=" + this.mCoordinate + ", mLayoutFromEnd=" + this.mLayoutFromEnd + '}';
    }
  }
  
  protected static final class LayoutChunkResult
  {
    public int mConsumed;
    public boolean mFinished;
    public boolean mFocusable;
    public boolean mIgnoreConsumed;
  }
  
  static final class LayoutState
  {
    int mAvailable;
    int mCurrentPosition;
    int mExtra = 0;
    boolean mIsPreLayout = false;
    int mItemDirection;
    int mLastScrollDelta;
    int mLayoutDirection;
    int mOffset;
    boolean mRecycle = true;
    List<RecyclerView.ViewHolder> mScrapList = null;
    int mScrollingOffset;
    
    public final void assignPositionFromScrapList(View paramView)
    {
      int i = this.mScrapList.size();
      Object localObject1 = null;
      int j = 2147483647;
      int k = 0;
      View localView;
      int m;
      Object localObject3;
      for (;;)
      {
        if (k >= i) {
          break label169;
        }
        localView = ((RecyclerView.ViewHolder)this.mScrapList.get(k)).itemView;
        RecyclerView.LayoutParams localLayoutParams = (RecyclerView.LayoutParams)localView.getLayoutParams();
        if ((localView == paramView) || (localLayoutParams.mViewHolder.isRemoved())) {
          break label159;
        }
        m = (localLayoutParams.mViewHolder.getLayoutPosition() - this.mCurrentPosition) * this.mItemDirection;
        if ((m < 0) || (m >= j)) {
          break label159;
        }
        if (m == 0) {
          break;
        }
        localObject3 = localView;
        k++;
        localObject1 = localObject3;
        j = m;
      }
      label159:
      label169:
      for (Object localObject2 = localView;; localObject2 = localObject1)
      {
        if (localObject2 == null)
        {
          this.mCurrentPosition = -1;
          return;
        }
        this.mCurrentPosition = ((RecyclerView.LayoutParams)((View)localObject2).getLayoutParams()).mViewHolder.getLayoutPosition();
        return;
        m = j;
        localObject3 = localObject1;
        break;
      }
    }
    
    final boolean hasMore(RecyclerView.State paramState)
    {
      return (this.mCurrentPosition >= 0) && (this.mCurrentPosition < paramState.getItemCount());
    }
    
    final View next(RecyclerView.Recycler paramRecycler)
    {
      if (this.mScrapList != null)
      {
        int i = this.mScrapList.size();
        for (int j = 0; j < i; j++)
        {
          View localView2 = ((RecyclerView.ViewHolder)this.mScrapList.get(j)).itemView;
          RecyclerView.LayoutParams localLayoutParams = (RecyclerView.LayoutParams)localView2.getLayoutParams();
          if ((!localLayoutParams.mViewHolder.isRemoved()) && (this.mCurrentPosition == localLayoutParams.mViewHolder.getLayoutPosition()))
          {
            assignPositionFromScrapList(localView2);
            return localView2;
          }
        }
        return null;
      }
      View localView1 = paramRecycler.getViewForPosition$3a4f3d28(this.mCurrentPosition);
      this.mCurrentPosition += this.mItemDirection;
      return localView1;
    }
  }
  
  public static class SavedState
    implements Parcelable
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator() {};
    boolean mAnchorLayoutFromEnd;
    int mAnchorOffset;
    int mAnchorPosition;
    
    public SavedState() {}
    
    SavedState(Parcel paramParcel)
    {
      this.mAnchorPosition = paramParcel.readInt();
      this.mAnchorOffset = paramParcel.readInt();
      if (paramParcel.readInt() == i) {}
      for (;;)
      {
        this.mAnchorLayoutFromEnd = i;
        return;
        i = 0;
      }
    }
    
    public SavedState(SavedState paramSavedState)
    {
      this.mAnchorPosition = paramSavedState.mAnchorPosition;
      this.mAnchorOffset = paramSavedState.mAnchorOffset;
      this.mAnchorLayoutFromEnd = paramSavedState.mAnchorLayoutFromEnd;
    }
    
    public int describeContents()
    {
      return 0;
    }
    
    final boolean hasValidAnchor()
    {
      return this.mAnchorPosition >= 0;
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      paramParcel.writeInt(this.mAnchorPosition);
      paramParcel.writeInt(this.mAnchorOffset);
      if (this.mAnchorLayoutFromEnd) {}
      for (int i = 1;; i = 0)
      {
        paramParcel.writeInt(i);
        return;
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.widget.LinearLayoutManager
 * JD-Core Version:    0.7.0.1
 */