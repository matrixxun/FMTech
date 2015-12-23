package android.support.v7.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.CollectionItemInfoCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import java.util.Arrays;

public final class GridLayoutManager
  extends LinearLayoutManager
{
  static final int MAIN_DIR_SPEC = View.MeasureSpec.makeMeasureSpec(0, 0);
  int[] mCachedBorders;
  final Rect mDecorInsets = new Rect();
  boolean mPendingSpanCountChange = false;
  final SparseIntArray mPreLayoutSpanIndexCache = new SparseIntArray();
  final SparseIntArray mPreLayoutSpanSizeCache = new SparseIntArray();
  View[] mSet;
  int mSpanCount = -1;
  public SpanSizeLookup mSpanSizeLookup = new DefaultSpanSizeLookup();
  
  public GridLayoutManager(Context paramContext)
  {
    if (2 != this.mSpanCount)
    {
      this.mPendingSpanCountChange = true;
      this.mSpanCount = 2;
      this.mSpanSizeLookup.mSpanIndexCache.clear();
    }
  }
  
  private void assignSpans$1d107c69(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, int paramInt, boolean paramBoolean)
  {
    int i;
    int j;
    int k;
    int n;
    int m;
    label40:
    int i1;
    label44:
    LayoutParams localLayoutParams;
    if (paramBoolean)
    {
      i = 0;
      j = paramInt;
      k = 1;
      if ((this.mOrientation != 1) || (!isLayoutRTL())) {
        break label155;
      }
      n = -1 + this.mSpanCount;
      m = -1;
      i1 = i;
      if (i1 == j) {
        return;
      }
      View localView = this.mSet[i1];
      localLayoutParams = (LayoutParams)localView.getLayoutParams();
      LayoutParams.access$102(localLayoutParams, getSpanSize(paramRecycler, paramState, getPosition(localView)));
      if ((m != -1) || (localLayoutParams.mSpanSize <= 1)) {
        break label164;
      }
      LayoutParams.access$002(localLayoutParams, n - (-1 + localLayoutParams.mSpanSize));
    }
    for (;;)
    {
      n += m * localLayoutParams.mSpanSize;
      i1 += k;
      break label44;
      i = paramInt - 1;
      j = -1;
      k = -1;
      break;
      label155:
      m = 1;
      n = 0;
      break label40;
      label164:
      LayoutParams.access$002(localLayoutParams, n);
    }
  }
  
  private static int getMainDirSpec(int paramInt)
  {
    if (paramInt < 0) {
      return MAIN_DIR_SPEC;
    }
    return View.MeasureSpec.makeMeasureSpec(paramInt, 1073741824);
  }
  
  private int getSpanGroupIndex(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, int paramInt)
  {
    if (!paramState.mInPreLayout) {
      return this.mSpanSizeLookup.getSpanGroupIndex(paramInt, this.mSpanCount);
    }
    int i = paramRecycler.convertPreLayoutPositionToPostLayout(paramInt);
    if (i == -1)
    {
      Log.w("GridLayoutManager", "Cannot find span size for pre layout position. " + paramInt);
      return 0;
    }
    return this.mSpanSizeLookup.getSpanGroupIndex(i, this.mSpanCount);
  }
  
  private int getSpanIndex(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, int paramInt)
  {
    int i;
    if (!paramState.mInPreLayout) {
      i = this.mSpanSizeLookup.getCachedSpanIndex(paramInt, this.mSpanCount);
    }
    do
    {
      return i;
      i = this.mPreLayoutSpanIndexCache.get(paramInt, -1);
    } while (i != -1);
    int j = paramRecycler.convertPreLayoutPositionToPostLayout(paramInt);
    if (j == -1)
    {
      Log.w("GridLayoutManager", "Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:" + paramInt);
      return 0;
    }
    return this.mSpanSizeLookup.getCachedSpanIndex(j, this.mSpanCount);
  }
  
  private int getSpanSize(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, int paramInt)
  {
    int i;
    if (!paramState.mInPreLayout) {
      i = this.mSpanSizeLookup.getSpanSize(paramInt);
    }
    do
    {
      return i;
      i = this.mPreLayoutSpanSizeCache.get(paramInt, -1);
    } while (i != -1);
    int j = paramRecycler.convertPreLayoutPositionToPostLayout(paramInt);
    if (j == -1)
    {
      Log.w("GridLayoutManager", "Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:" + paramInt);
      return 1;
    }
    return this.mSpanSizeLookup.getSpanSize(j);
  }
  
  private void measureChildWithDecorationsAndMargin(View paramView, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    Rect localRect = this.mDecorInsets;
    if (this.mRecyclerView == null) {
      localRect.set(0, 0, 0, 0);
    }
    for (;;)
    {
      RecyclerView.LayoutParams localLayoutParams = (RecyclerView.LayoutParams)paramView.getLayoutParams();
      if ((paramBoolean) || (this.mOrientation == 1)) {
        paramInt1 = updateSpecWithExtra(paramInt1, localLayoutParams.leftMargin + this.mDecorInsets.left, localLayoutParams.rightMargin + this.mDecorInsets.right);
      }
      if ((paramBoolean) || (this.mOrientation == 0)) {
        paramInt2 = updateSpecWithExtra(paramInt2, localLayoutParams.topMargin + this.mDecorInsets.top, localLayoutParams.bottomMargin + this.mDecorInsets.bottom);
      }
      paramView.measure(paramInt1, paramInt2);
      return;
      localRect.set(this.mRecyclerView.getItemDecorInsetsForChild(paramView));
    }
  }
  
  private static int updateSpecWithExtra(int paramInt1, int paramInt2, int paramInt3)
  {
    if ((paramInt2 == 0) && (paramInt3 == 0)) {}
    int i;
    do
    {
      return paramInt1;
      i = View.MeasureSpec.getMode(paramInt1);
    } while ((i != -2147483648) && (i != 1073741824));
    return View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(paramInt1) - paramInt2 - paramInt3, i);
  }
  
  public final boolean checkLayoutParams(RecyclerView.LayoutParams paramLayoutParams)
  {
    return paramLayoutParams instanceof LayoutParams;
  }
  
  final View findReferenceChild(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, int paramInt1, int paramInt2, int paramInt3)
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
        break label167;
      }
      localObject3 = getChildAt(m);
      int n = getPosition((View)localObject3);
      if ((n >= 0) && (n < paramInt3) && (getSpanIndex(paramRecycler, paramState, n) == 0))
      {
        if (!((RecyclerView.LayoutParams)((View)localObject3).getLayoutParams()).mViewHolder.isRemoved()) {
          break label127;
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
      label127:
      if ((this.mOrientationHelper.getDecoratedStart((View)localObject3) < j) && (this.mOrientationHelper.getDecoratedEnd((View)localObject3) >= i)) {
        break label176;
      }
      if (localObject2 == null) {
        localObject2 = localObject3;
      }
    }
    label167:
    if (localObject2 != null)
    {
      localObject3 = localObject2;
      label176:
      return localObject3;
    }
    return localObject1;
  }
  
  public final RecyclerView.LayoutParams generateDefaultLayoutParams()
  {
    return new LayoutParams();
  }
  
  public final RecyclerView.LayoutParams generateLayoutParams(Context paramContext, AttributeSet paramAttributeSet)
  {
    return new LayoutParams(paramContext, paramAttributeSet);
  }
  
  public final RecyclerView.LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    if ((paramLayoutParams instanceof ViewGroup.MarginLayoutParams)) {
      return new LayoutParams((ViewGroup.MarginLayoutParams)paramLayoutParams);
    }
    return new LayoutParams(paramLayoutParams);
  }
  
  public final int getColumnCountForAccessibility(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    if (this.mOrientation == 1) {
      return this.mSpanCount;
    }
    if (paramState.getItemCount() <= 0) {
      return 0;
    }
    return getSpanGroupIndex(paramRecycler, paramState, -1 + paramState.getItemCount());
  }
  
  public final int getRowCountForAccessibility(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    if (this.mOrientation == 0) {
      return this.mSpanCount;
    }
    if (paramState.getItemCount() <= 0) {
      return 0;
    }
    return getSpanGroupIndex(paramRecycler, paramState, -1 + paramState.getItemCount());
  }
  
  final void layoutChunk(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, LinearLayoutManager.LayoutState paramLayoutState, LinearLayoutManager.LayoutChunkResult paramLayoutChunkResult)
  {
    boolean bool;
    int i;
    int j;
    if (paramLayoutState.mItemDirection == 1)
    {
      bool = true;
      i = this.mSpanCount;
      j = 0;
      if (!bool) {
        i = getSpanIndex(paramRecycler, paramState, paramLayoutState.mCurrentPosition) + getSpanSize(paramRecycler, paramState, paramLayoutState.mCurrentPosition);
      }
    }
    for (;;)
    {
      if ((j >= this.mSpanCount) || (!paramLayoutState.hasMore(paramState)) || (i <= 0)) {
        break label196;
      }
      int i10 = paramLayoutState.mCurrentPosition;
      int i11 = getSpanSize(paramRecycler, paramState, i10);
      if (i11 > this.mSpanCount)
      {
        throw new IllegalArgumentException("Item at position " + i10 + " requires " + i11 + " spans but GridLayoutManager has only " + this.mSpanCount + " spans.");
        bool = false;
        break;
      }
      i -= i11;
      if (i < 0) {
        break label196;
      }
      View localView4 = paramLayoutState.next(paramRecycler);
      if (localView4 == null) {
        break label196;
      }
      this.mSet[j] = localView4;
      j++;
    }
    label196:
    if (j == 0)
    {
      paramLayoutChunkResult.mFinished = true;
      return;
    }
    int k = 0;
    assignSpans$1d107c69(paramRecycler, paramState, j, bool);
    int m = 0;
    if (m < j)
    {
      View localView3 = this.mSet[m];
      label260:
      LayoutParams localLayoutParams3;
      int i8;
      if (paramLayoutState.mScrapList == null) {
        if (bool)
        {
          super.addViewInt(localView3, -1, false);
          localLayoutParams3 = (LayoutParams)localView3.getLayoutParams();
          i8 = View.MeasureSpec.makeMeasureSpec(this.mCachedBorders[(localLayoutParams3.mSpanIndex + localLayoutParams3.mSpanSize)] - this.mCachedBorders[localLayoutParams3.mSpanIndex], 1073741824);
          if (this.mOrientation != 1) {
            break label395;
          }
          measureChildWithDecorationsAndMargin(localView3, i8, getMainDirSpec(localLayoutParams3.height), false);
        }
      }
      for (;;)
      {
        int i9 = this.mOrientationHelper.getDecoratedMeasurement(localView3);
        if (i9 > k) {
          k = i9;
        }
        m++;
        break;
        super.addViewInt(localView3, 0, false);
        break label260;
        if (bool)
        {
          super.addViewInt(localView3, -1, true);
          break label260;
        }
        super.addViewInt(localView3, 0, true);
        break label260;
        label395:
        measureChildWithDecorationsAndMargin(localView3, getMainDirSpec(localLayoutParams3.width), i8, false);
      }
    }
    int n = getMainDirSpec(k);
    int i1 = 0;
    if (i1 < j)
    {
      View localView2 = this.mSet[i1];
      int i7;
      if (this.mOrientationHelper.getDecoratedMeasurement(localView2) != k)
      {
        LayoutParams localLayoutParams2 = (LayoutParams)localView2.getLayoutParams();
        i7 = View.MeasureSpec.makeMeasureSpec(this.mCachedBorders[(localLayoutParams2.mSpanIndex + localLayoutParams2.mSpanSize)] - this.mCachedBorders[localLayoutParams2.mSpanIndex], 1073741824);
        if (this.mOrientation != 1) {
          break label524;
        }
        measureChildWithDecorationsAndMargin(localView2, i7, n, true);
      }
      for (;;)
      {
        i1++;
        break;
        label524:
        measureChildWithDecorationsAndMargin(localView2, n, i7, true);
      }
    }
    paramLayoutChunkResult.mConsumed = k;
    int i2 = 0;
    int i3 = 0;
    int i4;
    int i5;
    int i6;
    label583:
    View localView1;
    LayoutParams localLayoutParams1;
    if (this.mOrientation == 1) {
      if (paramLayoutState.mLayoutDirection == -1)
      {
        i4 = paramLayoutState.mOffset;
        i5 = i4 - k;
        i6 = 0;
        if (i6 >= j) {
          break label843;
        }
        localView1 = this.mSet[i6];
        localLayoutParams1 = (LayoutParams)localView1.getLayoutParams();
        if (this.mOrientation != 1) {
          break label809;
        }
        i2 = getPaddingLeft() + this.mCachedBorders[localLayoutParams1.mSpanIndex];
        i3 = i2 + this.mOrientationHelper.getDecoratedMeasurementInOther(localView1);
      }
    }
    for (;;)
    {
      layoutDecorated(localView1, i2 + localLayoutParams1.leftMargin, i5 + localLayoutParams1.topMargin, i3 - localLayoutParams1.rightMargin, i4 - localLayoutParams1.bottomMargin);
      if ((localLayoutParams1.mViewHolder.isRemoved()) || (localLayoutParams1.mViewHolder.isUpdated())) {
        paramLayoutChunkResult.mIgnoreConsumed = true;
      }
      paramLayoutChunkResult.mFocusable |= localView1.isFocusable();
      i6++;
      break label583;
      i5 = paramLayoutState.mOffset;
      i4 = i5 + k;
      i2 = 0;
      i3 = 0;
      break;
      if (paramLayoutState.mLayoutDirection == -1)
      {
        i3 = paramLayoutState.mOffset;
        i2 = i3 - k;
        i4 = 0;
        i5 = 0;
        break;
      }
      i2 = paramLayoutState.mOffset;
      i3 = i2 + k;
      i4 = 0;
      i5 = 0;
      break;
      label809:
      i5 = getPaddingTop() + this.mCachedBorders[localLayoutParams1.mSpanIndex];
      i4 = i5 + this.mOrientationHelper.getDecoratedMeasurementInOther(localView1);
    }
    label843:
    Arrays.fill(this.mSet, null);
  }
  
  final void onAnchorReady(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, LinearLayoutManager.AnchorInfo paramAnchorInfo)
  {
    int i = 0;
    super.onAnchorReady(paramRecycler, paramState, paramAnchorInfo);
    int j;
    int k;
    int n;
    int i1;
    label115:
    int i3;
    int i5;
    if (this.mOrientation == 1)
    {
      j = getWidth() - getPaddingRight() - getPaddingLeft();
      if ((this.mCachedBorders == null) || (this.mCachedBorders.length != 1 + this.mSpanCount) || (this.mCachedBorders[(-1 + this.mCachedBorders.length)] != j)) {
        this.mCachedBorders = new int[1 + this.mSpanCount];
      }
      this.mCachedBorders[0] = 0;
      k = j / this.mSpanCount;
      int m = j % this.mSpanCount;
      n = 1;
      i1 = 0;
      if (n > this.mSpanCount) {
        break label208;
      }
      i3 = i1 + m;
      if ((i3 <= 0) || (this.mSpanCount - i3 >= m)) {
        break label302;
      }
      i5 = k + 1;
      i1 = i3 - this.mSpanCount;
    }
    for (int i4 = i5;; i4 = k)
    {
      i += i4;
      this.mCachedBorders[n] = i;
      n++;
      break label115;
      j = getHeight() - getPaddingBottom() - getPaddingTop();
      break;
      label208:
      if ((paramState.getItemCount() > 0) && (!paramState.mInPreLayout)) {
        for (int i2 = getSpanIndex(paramRecycler, paramState, paramAnchorInfo.mPosition); (i2 > 0) && (paramAnchorInfo.mPosition > 0); i2 = getSpanIndex(paramRecycler, paramState, paramAnchorInfo.mPosition)) {
          paramAnchorInfo.mPosition = (-1 + paramAnchorInfo.mPosition);
        }
      }
      if ((this.mSet == null) || (this.mSet.length != this.mSpanCount)) {
        this.mSet = new View[this.mSpanCount];
      }
      return;
      label302:
      i1 = i3;
    }
  }
  
  public final void onInitializeAccessibilityNodeInfoForItem(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, View paramView, AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat)
  {
    ViewGroup.LayoutParams localLayoutParams = paramView.getLayoutParams();
    if (!(localLayoutParams instanceof LayoutParams))
    {
      super.onInitializeAccessibilityNodeInfoForItem(paramView, paramAccessibilityNodeInfoCompat);
      return;
    }
    LayoutParams localLayoutParams1 = (LayoutParams)localLayoutParams;
    int i = getSpanGroupIndex(paramRecycler, paramState, localLayoutParams1.mViewHolder.getLayoutPosition());
    if (this.mOrientation == 0)
    {
      int i2 = localLayoutParams1.mSpanIndex;
      int i3 = localLayoutParams1.mSpanSize;
      int i4 = this.mSpanCount;
      boolean bool2 = false;
      if (i4 > 1)
      {
        int i5 = localLayoutParams1.mSpanSize;
        int i6 = this.mSpanCount;
        bool2 = false;
        if (i5 == i6) {
          bool2 = true;
        }
      }
      paramAccessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain$430787b1(i2, i3, i, 1, bool2));
      return;
    }
    int j = localLayoutParams1.mSpanIndex;
    int k = localLayoutParams1.mSpanSize;
    int m = this.mSpanCount;
    boolean bool1 = false;
    if (m > 1)
    {
      int n = localLayoutParams1.mSpanSize;
      int i1 = this.mSpanCount;
      bool1 = false;
      if (n == i1) {
        bool1 = true;
      }
    }
    paramAccessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain$430787b1(i, 1, j, k, bool1));
  }
  
  public final void onItemsAdded$5927c743()
  {
    this.mSpanSizeLookup.mSpanIndexCache.clear();
  }
  
  public final void onItemsChanged$57043c5d()
  {
    this.mSpanSizeLookup.mSpanIndexCache.clear();
  }
  
  public final void onItemsMoved$342e6be0()
  {
    this.mSpanSizeLookup.mSpanIndexCache.clear();
  }
  
  public final void onItemsRemoved$5927c743()
  {
    this.mSpanSizeLookup.mSpanIndexCache.clear();
  }
  
  public final void onItemsUpdated$783f8c5f$5927c743()
  {
    this.mSpanSizeLookup.mSpanIndexCache.clear();
  }
  
  public final void onLayoutChildren(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    if (paramState.mInPreLayout)
    {
      int i = getChildCount();
      for (int j = 0; j < i; j++)
      {
        LayoutParams localLayoutParams = (LayoutParams)getChildAt(j).getLayoutParams();
        int k = localLayoutParams.mViewHolder.getLayoutPosition();
        this.mPreLayoutSpanSizeCache.put(k, localLayoutParams.mSpanSize);
        this.mPreLayoutSpanIndexCache.put(k, localLayoutParams.mSpanIndex);
      }
    }
    super.onLayoutChildren(paramRecycler, paramState);
    this.mPreLayoutSpanSizeCache.clear();
    this.mPreLayoutSpanIndexCache.clear();
    if (!paramState.mInPreLayout) {
      this.mPendingSpanCountChange = false;
    }
  }
  
  public final boolean supportsPredictiveItemAnimations()
  {
    return (this.mPendingSavedState == null) && (!this.mPendingSpanCountChange);
  }
  
  public static final class DefaultSpanSizeLookup
    extends GridLayoutManager.SpanSizeLookup
  {
    public final int getSpanIndex(int paramInt1, int paramInt2)
    {
      return paramInt1 % paramInt2;
    }
    
    public final int getSpanSize(int paramInt)
    {
      return 1;
    }
  }
  
  public static final class LayoutParams
    extends RecyclerView.LayoutParams
  {
    int mSpanIndex = -1;
    int mSpanSize = 0;
    
    public LayoutParams()
    {
      super(-2);
    }
    
    public LayoutParams(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
    }
    
    public LayoutParams(ViewGroup.LayoutParams paramLayoutParams)
    {
      super();
    }
    
    public LayoutParams(ViewGroup.MarginLayoutParams paramMarginLayoutParams)
    {
      super();
    }
  }
  
  public static abstract class SpanSizeLookup
  {
    private boolean mCacheSpanIndices = false;
    final SparseIntArray mSpanIndexCache = new SparseIntArray();
    
    final int getCachedSpanIndex(int paramInt1, int paramInt2)
    {
      int i;
      if (!this.mCacheSpanIndices) {
        i = getSpanIndex(paramInt1, paramInt2);
      }
      do
      {
        return i;
        i = this.mSpanIndexCache.get(paramInt1, -1);
      } while (i != -1);
      int j = getSpanIndex(paramInt1, paramInt2);
      this.mSpanIndexCache.put(paramInt1, j);
      return j;
    }
    
    public final int getSpanGroupIndex(int paramInt1, int paramInt2)
    {
      int i = 0;
      int j = 0;
      int k = getSpanSize(paramInt1);
      int m = 0;
      if (m < paramInt1)
      {
        int n = getSpanSize(m);
        i += n;
        if (i == paramInt2)
        {
          i = 0;
          j++;
        }
        for (;;)
        {
          m++;
          break;
          if (i > paramInt2)
          {
            i = n;
            j++;
          }
        }
      }
      if (i + k > paramInt2) {
        j++;
      }
      return j;
    }
    
    public int getSpanIndex(int paramInt1, int paramInt2)
    {
      int i = getSpanSize(paramInt1);
      int j;
      if (i == paramInt2) {
        j = 0;
      }
      label188:
      label224:
      do
      {
        return j;
        boolean bool = this.mCacheSpanIndices;
        j = 0;
        int k = 0;
        int i5;
        if (bool)
        {
          int i1 = this.mSpanIndexCache.size();
          j = 0;
          k = 0;
          if (i1 > 0)
          {
            int i2 = -1 + this.mSpanIndexCache.size();
            int i3 = 0;
            while (i3 <= i2)
            {
              int i6 = i3 + i2 >>> 1;
              if (this.mSpanIndexCache.keyAt(i6) < paramInt1) {
                i3 = i6 + 1;
              } else {
                i2 = i6 - 1;
              }
            }
            int i4 = i3 - 1;
            if ((i4 < 0) || (i4 >= this.mSpanIndexCache.size())) {
              break label224;
            }
            i5 = this.mSpanIndexCache.keyAt(i4);
            j = 0;
            k = 0;
            if (i5 >= 0)
            {
              j = this.mSpanIndexCache.get(i5) + getSpanSize(i5);
              k = i5 + 1;
            }
          }
        }
        int m = k;
        if (m < paramInt1)
        {
          int n = getSpanSize(m);
          j += n;
          if (j == paramInt2) {
            j = 0;
          }
          for (;;)
          {
            m++;
            break label188;
            i5 = -1;
            break;
            if (j > paramInt2) {
              j = n;
            }
          }
        }
      } while (j + i <= paramInt2);
      return 0;
    }
    
    public abstract int getSpanSize(int paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.widget.GridLayoutManager
 * JD-Core Version:    0.7.0.1
 */