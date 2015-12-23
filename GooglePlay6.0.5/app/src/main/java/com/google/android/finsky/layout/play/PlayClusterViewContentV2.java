package com.google.android.finsky.layout.play;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.AdapterDataObservable;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.RecycledViewPool;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.accessibility.AccessibilityEvent;
import com.android.vending.R.styleable;
import com.google.android.finsky.adapters.Recyclable;
import com.google.android.finsky.api.model.OnDataChangedListener;
import com.google.android.finsky.layout.ClusterContentBinder;
import com.google.android.finsky.layout.ClusterContentConfigurator;
import com.google.android.finsky.layout.InfiniteWrappingClusterContentBinder;
import com.google.android.play.image.BitmapLoader.BitmapContainer;
import com.google.android.play.image.BitmapLoader.BitmapLoadedHandler;
import com.google.android.play.utils.NetworkInfoUtil;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class PlayClusterViewContentV2
  extends LeadingEdgeSnapRecyclerView
  implements OnDataChangedListener, BitmapLoader.BitmapLoadedHandler
{
  private float mChildWidthMultiplier;
  private ClusterContentBinder mClusterContentBinder;
  private ClusterContentConfigurator mClusterContentConfigurator;
  protected int mContentHorizontalPadding;
  protected int mContentPaddingBottom;
  protected int mContentPaddingTop;
  private int mFixedChildWidth;
  private boolean mHasPeekingChild;
  private boolean mHasPreloadedCovers;
  private int[] mImageTypeSequence;
  private LayoutInflater mInflater;
  private final boolean mIsEligibleToPreloadCovers;
  protected int mItemsPerPage;
  private int mLastScrollToPosition;
  private List<BitmapLoader.BitmapContainer> mPreloadingContainerList;
  private Handler mPreloadingHandler;
  private Runnable mPreloadingRunnable;
  private float mPrimaryAspectRatio;
  private ScrollToPositionListener mScrollWithOffsetListener;
  private boolean mSupportsHorizontallyCenteredContent;
  private boolean mUseTrailingChildren;
  
  public PlayClusterViewContentV2(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayClusterViewContentV2(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mInflater = LayoutInflater.from(paramContext);
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.PlayClusterViewContent);
    this.mContentPaddingTop = localTypedArray.getDimensionPixelSize(0, 0);
    this.mContentPaddingBottom = localTypedArray.getDimensionPixelSize(1, 0);
    localTypedArray.recycle();
    setLayoutManager(new LinearLayoutManager(0));
    addItemDecoration(new ClusterRecyclerViewDecoration((byte)0));
    int i = NetworkInfoUtil.getNetworkType(paramContext);
    boolean bool;
    if ((i != 4) && (i != 3))
    {
      bool = false;
      if (i != 2) {}
    }
    else
    {
      bool = true;
    }
    this.mIsEligibleToPreloadCovers = bool;
    if (this.mIsEligibleToPreloadCovers) {
      this.mPreloadingHandler = new Handler(Looper.myLooper());
    }
  }
  
  private void cancelPreloading()
  {
    if (this.mPreloadingContainerList != null)
    {
      Iterator localIterator = this.mPreloadingContainerList.iterator();
      while (localIterator.hasNext()) {
        ((BitmapLoader.BitmapContainer)localIterator.next()).cancelRequest();
      }
      this.mPreloadingContainerList.clear();
    }
    if ((this.mPreloadingHandler != null) && (this.mPreloadingRunnable != null)) {
      this.mPreloadingHandler.removeCallbacks(this.mPreloadingRunnable);
    }
  }
  
  private int getFixedChildWidth(int paramInt1, int paramInt2)
  {
    int i;
    switch (this.mClusterContentConfigurator.getChildWidthPolicy())
    {
    default: 
      throw new UnsupportedOperationException("Can only be called for fixed policy");
    case 1: 
      i = this.mClusterContentConfigurator.getFixedChildWidth$255f288(paramInt2);
    case 2: 
      int k;
      int m;
      int n;
      int i1;
      do
      {
        return i;
        i = this.mClusterContentConfigurator.getFixedChildWidth$255f288(paramInt2);
        int j = paramInt1 - this.mContentHorizontalPadding;
        k = j / i;
        m = this.mClusterContentBinder.getAvailableChildCount();
        n = j - k * i;
        i1 = (int)(i * this.mClusterContentConfigurator.getChildPeekingAmount());
      } while ((n > i1) || (m == k));
      return i - (i1 - n) / k;
    }
    float f1 = this.mItemsPerPage;
    if (this.mUseTrailingChildren) {}
    for (float f2 = this.mClusterContentConfigurator.getChildPeekingAmount();; f2 = 0.0F)
    {
      float f3 = f1 + f2;
      return (int)((paramInt1 - 2 * this.mContentHorizontalPadding) / f3);
    }
  }
  
  private int getScrollPositionInternal()
  {
    int i = getScrolledToItemPosition();
    if (i >= 0) {
      return i + 1;
    }
    return -1;
  }
  
  private void preloadImages(boolean paramBoolean)
  {
    if ((this.mClusterContentBinder == null) || (this.mPreloadingHandler == null) || (getPreloadRadius() <= 0)) {
      return;
    }
    cancelPreloading();
    this.mPreloadingRunnable = new Runnable()
    {
      public final void run()
      {
        int i = 1;
        if (PlayClusterViewContentV2.this.mClusterContentBinder == null) {}
        int j;
        do
        {
          return;
          j = PlayClusterViewContentV2.this.getPreloadRadius();
        } while (j <= 0);
        if (PlayClusterViewContentV2.this.mPreloadingContainerList == null) {
          PlayClusterViewContentV2.access$302(PlayClusterViewContentV2.this, new ArrayList());
        }
        int k = PlayClusterViewContentV2.this.mClusterContentBinder.getChildCoverWidth(PlayClusterViewContentV2.this.getChildAt(i));
        int m = PlayClusterViewContentV2.this.mClusterContentBinder.getChildCoverHeight(PlayClusterViewContentV2.this.getChildAt(i));
        int n = PlayClusterViewContentV2.this.mItemsPerPage;
        if (PlayClusterViewContentV2.this.mHasPeekingChild) {}
        for (;;)
        {
          int i1 = n + i;
          int i2 = PlayClusterViewContentV2.this.mLastScrollToPosition - j * i1;
          int i3 = i1 + PlayClusterViewContentV2.this.mLastScrollToPosition + j * i1;
          int i4 = PlayClusterViewContentV2.this.mClusterContentBinder.getAvailableChildCount();
          for (int i5 = i2;; i5++)
          {
            if (i5 == i3) {
              break label254;
            }
            if (i5 > 0)
            {
              int i6 = i5 - 1;
              if (i6 >= i4) {
                break;
              }
              BitmapLoader.BitmapContainer localBitmapContainer = PlayClusterViewContentV2.this.mClusterContentBinder.preloadChildCoverImage(i6, k, m, PlayClusterViewContentV2.this, PlayClusterViewContentV2.this.mImageTypeSequence);
              if ((localBitmapContainer != null) && (localBitmapContainer.mBitmap == null)) {
                PlayClusterViewContentV2.this.mPreloadingContainerList.add(localBitmapContainer);
              }
            }
          }
          label254:
          break;
          i = 0;
        }
      }
    };
    if (paramBoolean)
    {
      this.mPreloadingHandler.postDelayed(this.mPreloadingRunnable, 500L);
      return;
    }
    this.mPreloadingRunnable.run();
  }
  
  public final void createContent(ClusterContentBinder paramClusterContentBinder, ClusterContentConfigurator paramClusterContentConfigurator, int paramInt, boolean paramBoolean, RecyclerView.RecycledViewPool paramRecycledViewPool, Bundle paramBundle, int[] paramArrayOfInt)
  {
    super.bindView();
    this.mClusterContentBinder = paramClusterContentBinder;
    this.mChildWidthMultiplier = paramClusterContentConfigurator.getChildWidthMultiplier();
    this.mItemsPerPage = Math.round(paramInt / this.mChildWidthMultiplier);
    this.mSupportsHorizontallyCenteredContent = paramBoolean;
    this.mImageTypeSequence = paramArrayOfInt;
    this.mClusterContentConfigurator = paramClusterContentConfigurator;
    boolean bool1;
    boolean bool2;
    label90:
    RecyclerView.Adapter localAdapter;
    if (this.mClusterContentBinder.getAvailableChildCount() > this.mItemsPerPage)
    {
      bool1 = true;
      this.mUseTrailingChildren = bool1;
      if (paramClusterContentConfigurator.getChildPeekingAmount() <= 0.0D) {
        break label205;
      }
      bool2 = true;
      this.mHasPeekingChild = bool2;
      this.mPrimaryAspectRatio = this.mClusterContentConfigurator.getPrimaryChildAspectRatio(this.mClusterContentBinder);
      localAdapter = getAdapter();
      if (localAdapter != null) {
        break label211;
      }
      setAdapter(new ClusterRecyclerViewAdapter());
      setRecycledViewPool(paramRecycledViewPool);
    }
    for (;;)
    {
      if ((this.mClusterContentBinder instanceof InfiniteWrappingClusterContentBinder)) {
        scrollToPosition(1 + ((InfiniteWrappingClusterContentBinder)this.mClusterContentBinder).getDefaultFirstVisibleChildPosition());
      }
      if (paramBundle != null)
      {
        this.mHasPreloadedCovers = true;
        scrollToPosition(paramBundle.getInt("PlayClusterViewContentV2.recyclerViewScrollPosition", -1));
      }
      preloadImages(true);
      return;
      bool1 = false;
      break;
      label205:
      bool2 = false;
      break label90;
      label211:
      localAdapter.mObservable.notifyChanged();
      onDataChanged();
      scrollToPosition(0);
      this.mHasPreloadedCovers = false;
    }
  }
  
  public String getChildContentSourceId()
  {
    return this.mClusterContentBinder.getChildContentSourceId();
  }
  
  public ClusterContentConfigurator getClusterContentConfigurator()
  {
    return this.mClusterContentConfigurator;
  }
  
  protected int getLeadingItemGap()
  {
    return 0;
  }
  
  final int getLeadingPixelGap(int paramInt1, int paramInt2)
  {
    if (this.mClusterContentConfigurator.getChildWidthPolicy() == 3) {
      return 0;
    }
    return getLeadingItemGap() * getFixedChildWidth(paramInt1, paramInt2);
  }
  
  protected int getPreloadRadius()
  {
    if ((getChildCount() <= 2) || (this.mChildWidthMultiplier == 0.0F)) {
      return -1;
    }
    return 1;
  }
  
  public int getScrolledToItemPosition()
  {
    int i = -1;
    LinearLayoutManager localLinearLayoutManager = (LinearLayoutManager)getLayoutManager();
    int j = localLinearLayoutManager.findFirstVisibleItemPosition();
    int k = 0;
    int m = -1;
    if (j != i)
    {
      n = localLinearLayoutManager.findLastVisibleItemPosition();
      i1 = j;
      if (i1 <= n) {
        if (i1 > 0)
        {
          localView = localLinearLayoutManager.findViewByPosition(i1);
          i2 = getLeadingGapForSnapping() + localView.getWidth() / 2;
          i3 = i2 - localView.getLeft();
          i4 = localView.getRight() - i2;
          if ((i3 >= 0) && (i4 > 0)) {
            i = i1 - 1;
          }
        }
      }
    }
    while (this.mLastScrollToPosition <= 0)
    {
      for (;;)
      {
        int n;
        int i1;
        View localView;
        int i2;
        int i3;
        int i4;
        return i;
        int i5 = Math.min(Math.abs(i3), Math.abs(i4));
        if ((m < 0) || (i5 < k))
        {
          m = i1;
          k = i5;
        }
        i1++;
      }
      if (m > 0) {
        return m - 1;
      }
    }
    return -1 + this.mLastScrollToPosition;
  }
  
  protected int getTrailingSpacerCount()
  {
    return -1 + ((ClusterRecyclerViewAdapter)getAdapter()).getSpacersAndLoadingIndicatorCount();
  }
  
  public final PlayHighlightsBannerItemView getViewForItemPosition(int paramInt)
  {
    return (PlayHighlightsBannerItemView)getLayoutManager().findViewByPosition(paramInt + 1);
  }
  
  public final void onDataChanged()
  {
    ClusterRecyclerViewAdapter localClusterRecyclerViewAdapter = (ClusterRecyclerViewAdapter)getAdapter();
    if (this.mClusterContentBinder.isMoreDataAvailable()) {
      ClusterRecyclerViewAdapter.access$700(localClusterRecyclerViewAdapter, 1);
    }
    for (;;)
    {
      preloadImages(false);
      return;
      ClusterRecyclerViewAdapter.access$700(localClusterRecyclerViewAdapter, 0);
    }
  }
  
  public void onInitializeAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent)
  {
    super.onInitializeAccessibilityEvent(paramAccessibilityEvent);
    if ((this.mClusterContentBinder instanceof InfiniteWrappingClusterContentBinder))
    {
      LinearLayoutManager localLinearLayoutManager = (LinearLayoutManager)getLayoutManager();
      ((InfiniteWrappingClusterContentBinder)this.mClusterContentBinder).onInitializeAccessibilityEvent(paramAccessibilityEvent, -1 + localLinearLayoutManager.findFirstVisibleItemPosition(), -1 + localLinearLayoutManager.findLastVisibleItemPosition());
    }
  }
  
  protected final void onLeadingGapForSnappingChanged()
  {
    super.onLeadingGapForSnappingChanged();
    scrollToPosition(getScrollPositionInternal());
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    if (this.mClusterContentConfigurator == null)
    {
      setMeasuredDimension(i, paramInt2);
      return;
    }
    int k;
    if (this.mClusterContentConfigurator.getChildWidthPolicy() != 3)
    {
      k = getFixedChildWidth(i, j);
      this.mFixedChildWidth = k;
      if (View.MeasureSpec.getMode(paramInt2) != 1073741824) {
        break label99;
      }
    }
    label99:
    for (int m = View.MeasureSpec.getSize(paramInt2);; m = this.mClusterContentConfigurator.getClusterHeight(this.mFixedChildWidth, this.mPrimaryAspectRatio))
    {
      setLeadingGapForSnapping(getLeadingPixelGap(i, m) + this.mContentHorizontalPadding);
      setMeasuredDimension(i, m);
      return;
      k = 0;
      break;
    }
  }
  
  public final void onRecycle()
  {
    super.onRecycle();
    this.mClusterContentBinder = null;
    cancelPreloading();
    RecyclerView.Adapter localAdapter = getAdapter();
    if ((localAdapter instanceof ClusterRecyclerViewAdapter))
    {
      ClusterRecyclerViewAdapter localClusterRecyclerViewAdapter = (ClusterRecyclerViewAdapter)localAdapter;
      RecyclerView.ViewHolder[] arrayOfViewHolder = (RecyclerView.ViewHolder[])localClusterRecyclerViewAdapter.mActiveViewHolders.toArray(new RecyclerView.ViewHolder[localClusterRecyclerViewAdapter.mActiveViewHolders.size()]);
      for (int i = 0; i < arrayOfViewHolder.length; i++) {
        localClusterRecyclerViewAdapter.onViewRecycled(arrayOfViewHolder[i]);
      }
    }
  }
  
  public final void onResponse(BitmapLoader.BitmapContainer paramBitmapContainer)
  {
    this.mPreloadingContainerList.remove(paramBitmapContainer);
  }
  
  public final void onSaveInstanceState(Bundle paramBundle)
  {
    paramBundle.putInt("PlayClusterViewContentV2.recyclerViewScrollPosition", getScrollPositionInternal());
  }
  
  protected final void onScrollEnded()
  {
    super.onScrollEnded();
    this.mLastScrollToPosition = getScrollPositionInternal();
    preloadImages(false);
  }
  
  public final void scrollToPosition(int paramInt)
  {
    if (paramInt < 0) {
      return;
    }
    this.mLastScrollToPosition = paramInt;
    LinearLayoutManager localLinearLayoutManager = (LinearLayoutManager)getLayoutManager();
    int i = getLeadingGapForSnapping();
    localLinearLayoutManager.scrollToPositionWithOffset(paramInt, i);
    if (this.mScrollWithOffsetListener != null)
    {
      int j = Math.max(paramInt - 1, 0);
      this.mScrollWithOffsetListener.onScrollToPositionWithOffset(j, i);
    }
    preloadImages(false);
  }
  
  public final boolean setContentHorizontalPadding(int paramInt)
  {
    if (this.mContentHorizontalPadding == paramInt) {
      return false;
    }
    this.mContentHorizontalPadding = paramInt;
    requestLayout();
    return true;
  }
  
  public void setOnScrollToPositionListener(ScrollToPositionListener paramScrollToPositionListener)
  {
    this.mScrollWithOffsetListener = paramScrollToPositionListener;
  }
  
  protected final boolean supportsSnapping()
  {
    return (this.mClusterContentConfigurator != null) && (this.mClusterContentConfigurator.getChildWidthPolicy() != 3);
  }
  
  private final class ClusterRecyclerViewAdapter
    extends RecyclerView.Adapter
  {
    HashSet<RecyclerView.ViewHolder> mActiveViewHolders;
    private int mFooterMode;
    
    public ClusterRecyclerViewAdapter()
    {
      if (PlayClusterViewContentV2.this.mClusterContentBinder.isMoreDataAvailable()) {}
      for (int i = 1;; i = 0)
      {
        this.mFooterMode = i;
        this.mActiveViewHolders = new HashSet();
        return;
      }
    }
    
    private int getSpacerExtraWidth(int paramInt)
    {
      if (!PlayClusterViewContentV2.this.mSupportsHorizontallyCenteredContent) {
        return 0;
      }
      return Math.max(0, (paramInt - 2 * PlayClusterViewContentV2.this.mContentHorizontalPadding - PlayClusterViewContentV2.this.mFixedChildWidth * (-2 + PlayClusterViewContentV2.this.getAdapter().getItemCount())) / 2);
    }
    
    private int getSpacersCount()
    {
      if (shouldUseTrailingSpacer()) {}
      for (int i = 1;; i = 0) {
        return i + 1;
      }
    }
    
    private boolean shouldUseTrailingSpacer()
    {
      return (PlayClusterViewContentV2.this.mContentHorizontalPadding > 0) || (PlayClusterViewContentV2.this.mSupportsHorizontallyCenteredContent);
    }
    
    public final int getItemCount()
    {
      return getSpacersAndLoadingIndicatorCount() + PlayClusterViewContentV2.this.mClusterContentBinder.getAvailableChildCount();
    }
    
    public final int getItemViewType(int paramInt)
    {
      int i = 1;
      if (paramInt <= 0) {
        i = 0;
      }
      int j;
      do
      {
        return i;
        j = getItemCount();
        if (this.mFooterMode != i) {
          break;
        }
        if (paramInt == j - 1) {
          return 2;
        }
      } while ((shouldUseTrailingSpacer()) && (paramInt == j - getSpacersCount()));
      while ((!shouldUseTrailingSpacer()) || (paramInt != j - 1)) {
        return PlayClusterViewContentV2.this.mClusterContentBinder.getChildLayoutId$134621();
      }
      return i;
    }
    
    final int getSpacersAndLoadingIndicatorCount()
    {
      int i = 1;
      int j = getSpacersCount();
      if (this.mFooterMode == i) {}
      for (;;)
      {
        return i + j;
        i = 0;
      }
    }
    
    public final void onBindViewHolder(RecyclerView.ViewHolder paramViewHolder, int paramInt)
    {
      this.mActiveViewHolders.add(paramViewHolder);
      int i = paramViewHolder.mItemViewType;
      int j = PlayClusterViewContentV2.this.getMeasuredWidth();
      int k = PlayClusterViewContentV2.this.getMeasuredHeight();
      ViewGroup.LayoutParams localLayoutParams = paramViewHolder.itemView.getLayoutParams();
      if (i == 0) {
        localLayoutParams.width = (PlayClusterViewContentV2.this.getLeadingPixelGap(j, k) + PlayClusterViewContentV2.this.mContentHorizontalPadding + getSpacerExtraWidth(j));
      }
      int m;
      do
      {
        do
        {
          return;
          if (i == 1)
          {
            localLayoutParams.width = (PlayClusterViewContentV2.this.mContentHorizontalPadding + getSpacerExtraWidth(j));
            return;
          }
        } while (i == 2);
        m = paramInt - 1;
        PlayClusterViewContentV2.this.mClusterContentBinder.bindChild(paramViewHolder.itemView, m);
        if (PlayClusterViewContentV2.this.mClusterContentConfigurator.getChildWidthPolicy() != 3) {
          localLayoutParams.width = PlayClusterViewContentV2.this.getFixedChildWidth(j, k);
        }
      } while (localLayoutParams.height == -1);
      localLayoutParams.height = PlayClusterViewContentV2.this.mClusterContentConfigurator.getChildHeight(PlayClusterViewContentV2.this.mClusterContentBinder.getChildCoverAspectRatio(m), PlayClusterViewContentV2.this.mPrimaryAspectRatio, localLayoutParams.width);
    }
    
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt)
    {
      if ((paramInt == 0) || (paramInt == 1))
      {
        View localView1 = new View(PlayClusterViewContentV2.this.getContext());
        localView1.setLayoutParams(new ViewGroup.LayoutParams(-2, -1));
        localView1.setTag("tagIsSpacer");
        PlayRecyclerView.ViewHolder localViewHolder = new PlayRecyclerView.ViewHolder(localView1);
        if (paramInt == 0) {
          localViewHolder.setIsRecyclable(false);
        }
        return localViewHolder;
      }
      if (paramInt == 2)
      {
        View localView2 = PlayClusterViewContentV2.this.mInflater.inflate(2130968663, paramViewGroup, false);
        localView2.setTag("tagIsSpacer");
        return new PlayRecyclerView.ViewHolder(localView2);
      }
      return new PlayRecyclerView.ViewHolder(PlayClusterViewContentV2.this.mInflater.inflate(paramInt, paramViewGroup, false));
    }
    
    public final boolean onFailedToRecycleView$cb3a904()
    {
      return true;
    }
    
    public final void onViewRecycled(RecyclerView.ViewHolder paramViewHolder)
    {
      this.mActiveViewHolders.remove(paramViewHolder);
      View localView = paramViewHolder.itemView;
      if ((localView instanceof Recyclable)) {
        ((Recyclable)localView).onRecycle();
      }
    }
  }
  
  private final class ClusterRecyclerViewDecoration
    extends RecyclerView.ItemDecoration
  {
    private ClusterRecyclerViewDecoration() {}
    
    public final void getItemOffsets$5c1923bd$3450f9fc(Rect paramRect, View paramView)
    {
      Object localObject = paramView.getTag();
      if ((localObject != null) && ("tagIsSpacer".equals(localObject))) {
        return;
      }
      float f = PlayClusterViewContentV2.this.mClusterContentBinder.getChildCoverAspectRatio(paramView);
      int i = PlayClusterViewContentV2.this.mClusterContentConfigurator.getChildVerticalOffset(f, PlayClusterViewContentV2.this.mPrimaryAspectRatio, PlayClusterViewContentV2.this.mFixedChildWidth);
      paramRect.set(0, i, 0, i);
    }
  }
  
  public static abstract interface ScrollToPositionListener
  {
    public abstract void onScrollToPositionWithOffset(int paramInt1, int paramInt2);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayClusterViewContentV2
 * JD-Core Version:    0.7.0.1
 */