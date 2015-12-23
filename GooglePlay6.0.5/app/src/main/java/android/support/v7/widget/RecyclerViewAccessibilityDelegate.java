package android.support.v7.widget;

import android.os.Bundle;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.CollectionInfoCompat;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;

public final class RecyclerViewAccessibilityDelegate
  extends AccessibilityDelegateCompat
{
  final AccessibilityDelegateCompat mItemDelegate = new AccessibilityDelegateCompat()
  {
    public final void onInitializeAccessibilityNodeInfo(View paramAnonymousView, AccessibilityNodeInfoCompat paramAnonymousAccessibilityNodeInfoCompat)
    {
      super.onInitializeAccessibilityNodeInfo(paramAnonymousView, paramAnonymousAccessibilityNodeInfoCompat);
      if ((!RecyclerViewAccessibilityDelegate.this.shouldIgnore()) && (RecyclerViewAccessibilityDelegate.this.mRecyclerView.getLayoutManager() != null)) {
        RecyclerViewAccessibilityDelegate.this.mRecyclerView.getLayoutManager().onInitializeAccessibilityNodeInfoForItem(paramAnonymousView, paramAnonymousAccessibilityNodeInfoCompat);
      }
    }
    
    public final boolean performAccessibilityAction(View paramAnonymousView, int paramAnonymousInt, Bundle paramAnonymousBundle)
    {
      boolean bool2;
      if (super.performAccessibilityAction(paramAnonymousView, paramAnonymousInt, paramAnonymousBundle)) {
        bool2 = true;
      }
      RecyclerView.LayoutManager localLayoutManager1;
      do
      {
        boolean bool1;
        do
        {
          return bool2;
          bool1 = RecyclerViewAccessibilityDelegate.this.shouldIgnore();
          bool2 = false;
        } while (bool1);
        localLayoutManager1 = RecyclerViewAccessibilityDelegate.this.mRecyclerView.getLayoutManager();
        bool2 = false;
      } while (localLayoutManager1 == null);
      RecyclerView.LayoutManager localLayoutManager2 = RecyclerViewAccessibilityDelegate.this.mRecyclerView.getLayoutManager();
      return false;
    }
  };
  final RecyclerView mRecyclerView;
  
  public RecyclerViewAccessibilityDelegate(RecyclerView paramRecyclerView)
  {
    this.mRecyclerView = paramRecyclerView;
  }
  
  public final void onInitializeAccessibilityEvent(View paramView, AccessibilityEvent paramAccessibilityEvent)
  {
    super.onInitializeAccessibilityEvent(paramView, paramAccessibilityEvent);
    paramAccessibilityEvent.setClassName(RecyclerView.class.getName());
    if (((paramView instanceof RecyclerView)) && (!shouldIgnore()))
    {
      RecyclerView localRecyclerView = (RecyclerView)paramView;
      if (localRecyclerView.getLayoutManager() != null) {
        localRecyclerView.getLayoutManager().onInitializeAccessibilityEvent(paramAccessibilityEvent);
      }
    }
  }
  
  public final void onInitializeAccessibilityNodeInfo(View paramView, AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat)
  {
    super.onInitializeAccessibilityNodeInfo(paramView, paramAccessibilityNodeInfoCompat);
    paramAccessibilityNodeInfoCompat.setClassName(RecyclerView.class.getName());
    if ((!shouldIgnore()) && (this.mRecyclerView.getLayoutManager() != null))
    {
      RecyclerView.LayoutManager localLayoutManager = this.mRecyclerView.getLayoutManager();
      RecyclerView.Recycler localRecycler = localLayoutManager.mRecyclerView.mRecycler;
      RecyclerView.State localState = localLayoutManager.mRecyclerView.mState;
      if ((ViewCompat.canScrollVertically(localLayoutManager.mRecyclerView, -1)) || (ViewCompat.canScrollHorizontally(localLayoutManager.mRecyclerView, -1)))
      {
        paramAccessibilityNodeInfoCompat.addAction(8192);
        paramAccessibilityNodeInfoCompat.setScrollable(true);
      }
      if ((ViewCompat.canScrollVertically(localLayoutManager.mRecyclerView, 1)) || (ViewCompat.canScrollHorizontally(localLayoutManager.mRecyclerView, 1)))
      {
        paramAccessibilityNodeInfoCompat.addAction(4096);
        paramAccessibilityNodeInfoCompat.setScrollable(true);
      }
      int i = localLayoutManager.getRowCountForAccessibility(localRecycler, localState);
      int j = localLayoutManager.getColumnCountForAccessibility(localRecycler, localState);
      AccessibilityNodeInfoCompat.CollectionInfoCompat localCollectionInfoCompat = new AccessibilityNodeInfoCompat.CollectionInfoCompat(AccessibilityNodeInfoCompat.access$000().obtainCollectionInfo(i, j, false, 0));
      AccessibilityNodeInfoCompat.IMPL.setCollectionInfo(paramAccessibilityNodeInfoCompat.mInfo, ((AccessibilityNodeInfoCompat.CollectionInfoCompat)localCollectionInfoCompat).mInfo);
    }
  }
  
  public final boolean performAccessibilityAction(View paramView, int paramInt, Bundle paramBundle)
  {
    boolean bool2;
    if (super.performAccessibilityAction(paramView, paramInt, paramBundle)) {
      bool2 = true;
    }
    RecyclerView.LayoutManager localLayoutManager2;
    int k;
    int j;
    do
    {
      RecyclerView localRecyclerView;
      do
      {
        RecyclerView.LayoutManager localLayoutManager1;
        do
        {
          boolean bool1;
          do
          {
            return bool2;
            bool1 = shouldIgnore();
            bool2 = false;
          } while (bool1);
          localLayoutManager1 = this.mRecyclerView.getLayoutManager();
          bool2 = false;
        } while (localLayoutManager1 == null);
        localLayoutManager2 = this.mRecyclerView.getLayoutManager();
        localRecyclerView = localLayoutManager2.mRecyclerView;
        bool2 = false;
      } while (localRecyclerView == null);
      switch (paramInt)
      {
      default: 
        k = 0;
        j = 0;
        if (j != 0) {
          break label135;
        }
        bool2 = false;
      }
    } while (k == 0);
    label135:
    localLayoutManager2.mRecyclerView.scrollBy(k, j);
    return true;
    if (ViewCompat.canScrollVertically(localLayoutManager2.mRecyclerView, -1)) {}
    for (int i = -(localLayoutManager2.getHeight() - localLayoutManager2.getPaddingTop() - localLayoutManager2.getPaddingBottom());; i = 0)
    {
      if (ViewCompat.canScrollHorizontally(localLayoutManager2.mRecyclerView, -1))
      {
        int n = -(localLayoutManager2.getWidth() - localLayoutManager2.getPaddingLeft() - localLayoutManager2.getPaddingRight());
        j = i;
        k = n;
        break;
        if (!ViewCompat.canScrollVertically(localLayoutManager2.mRecyclerView, 1)) {
          break label307;
        }
      }
      label307:
      for (i = localLayoutManager2.getHeight() - localLayoutManager2.getPaddingTop() - localLayoutManager2.getPaddingBottom();; i = 0)
      {
        if (ViewCompat.canScrollHorizontally(localLayoutManager2.mRecyclerView, 1))
        {
          int m = localLayoutManager2.getWidth() - localLayoutManager2.getPaddingLeft() - localLayoutManager2.getPaddingRight();
          j = i;
          k = m;
          break;
        }
        j = i;
        k = 0;
        break;
      }
    }
  }
  
  final boolean shouldIgnore()
  {
    RecyclerView localRecyclerView = this.mRecyclerView;
    return (!localRecyclerView.mFirstLayoutComplete) || (localRecyclerView.mDataSetHasChangedAfterLayout) || (localRecyclerView.mAdapterHelper.hasPendingUpdates());
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.widget.RecyclerViewAccessibilityDelegate
 * JD-Core Version:    0.7.0.1
 */