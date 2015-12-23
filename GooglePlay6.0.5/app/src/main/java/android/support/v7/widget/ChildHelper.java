package android.support.v7.widget;

import android.view.View;
import android.view.ViewGroup.LayoutParams;
import java.util.ArrayList;
import java.util.List;

public final class ChildHelper
{
  final Bucket mBucket;
  final Callback mCallback;
  final List<View> mHiddenViews;
  
  ChildHelper(Callback paramCallback)
  {
    this.mCallback = paramCallback;
    this.mBucket = new Bucket();
    this.mHiddenViews = new ArrayList();
  }
  
  final void addView(View paramView, int paramInt, boolean paramBoolean)
  {
    if (paramInt < 0) {}
    for (int i = this.mCallback.getChildCount();; i = getOffset(paramInt))
    {
      this.mBucket.insert(i, paramBoolean);
      if (paramBoolean) {
        hideViewInternal(paramView);
      }
      this.mCallback.addView(paramView, i);
      return;
    }
  }
  
  final void attachViewToParent(View paramView, int paramInt, ViewGroup.LayoutParams paramLayoutParams, boolean paramBoolean)
  {
    if (paramInt < 0) {}
    for (int i = this.mCallback.getChildCount();; i = getOffset(paramInt))
    {
      this.mBucket.insert(i, paramBoolean);
      if (paramBoolean) {
        hideViewInternal(paramView);
      }
      this.mCallback.attachViewToParent(paramView, i, paramLayoutParams);
      return;
    }
  }
  
  final void detachViewFromParent(int paramInt)
  {
    int i = getOffset(paramInt);
    this.mBucket.remove(i);
    this.mCallback.detachViewFromParent(i);
  }
  
  public final View getChildAt(int paramInt)
  {
    int i = getOffset(paramInt);
    return this.mCallback.getChildAt(i);
  }
  
  public final int getChildCount()
  {
    return this.mCallback.getChildCount() - this.mHiddenViews.size();
  }
  
  final int getOffset(int paramInt)
  {
    if (paramInt < 0)
    {
      j = -1;
      return j;
    }
    int i = this.mCallback.getChildCount();
    int j = paramInt;
    for (;;)
    {
      if (j >= i) {
        break label69;
      }
      int k = paramInt - (j - this.mBucket.countOnesBefore(j));
      if (k == 0)
      {
        while (this.mBucket.get(j)) {
          j++;
        }
        break;
      }
      j += k;
    }
    label69:
    return -1;
  }
  
  final View getUnfilteredChildAt(int paramInt)
  {
    return this.mCallback.getChildAt(paramInt);
  }
  
  final int getUnfilteredChildCount()
  {
    return this.mCallback.getChildCount();
  }
  
  final void hideViewInternal(View paramView)
  {
    this.mHiddenViews.add(paramView);
    this.mCallback.onEnteredHiddenState(paramView);
  }
  
  final int indexOfChild(View paramView)
  {
    int i = this.mCallback.indexOfChild(paramView);
    if (i == -1) {}
    while (this.mBucket.get(i)) {
      return -1;
    }
    return i - this.mBucket.countOnesBefore(i);
  }
  
  final boolean isHidden(View paramView)
  {
    return this.mHiddenViews.contains(paramView);
  }
  
  public final String toString()
  {
    return this.mBucket.toString() + ", hidden list:" + this.mHiddenViews.size();
  }
  
  final boolean unhideViewInternal(View paramView)
  {
    if (this.mHiddenViews.remove(paramView))
    {
      this.mCallback.onLeftHiddenState(paramView);
      return true;
    }
    return false;
  }
  
  static final class Bucket
  {
    long mData = 0L;
    Bucket next;
    
    private void ensureNext()
    {
      if (this.next == null) {
        this.next = new Bucket();
      }
    }
    
    final void clear(int paramInt)
    {
      while (paramInt >= 64)
      {
        if (this.next == null) {
          return;
        }
        this = this.next;
        paramInt -= 64;
      }
      this.mData &= (0xFFFFFFFF ^ 1L << paramInt);
    }
    
    final int countOnesBefore(int paramInt)
    {
      if (this.next == null)
      {
        if (paramInt >= 64) {
          return Long.bitCount(this.mData);
        }
        return Long.bitCount(this.mData & (1L << paramInt) - 1L);
      }
      if (paramInt < 64) {
        return Long.bitCount(this.mData & (1L << paramInt) - 1L);
      }
      return this.next.countOnesBefore(paramInt - 64) + Long.bitCount(this.mData);
    }
    
    final boolean get(int paramInt)
    {
      while (paramInt >= 64)
      {
        ensureNext();
        this = this.next;
        paramInt -= 64;
      }
      return (this.mData & 1L << paramInt) != 0L;
    }
    
    final void insert(int paramInt, boolean paramBoolean)
    {
      while (paramInt >= 64)
      {
        ensureNext();
        this = this.next;
        paramInt -= 64;
      }
      boolean bool;
      if ((0x0 & this.mData) != 0L)
      {
        bool = true;
        label36:
        long l = (1L << paramInt) - 1L;
        this.mData = (l & this.mData | (this.mData & (0xFFFFFFFF ^ l)) << 1);
        if (!paramBoolean) {
          break label109;
        }
        set(paramInt);
      }
      for (;;)
      {
        if ((!bool) && (this.next == null)) {
          return;
        }
        ensureNext();
        this = this.next;
        paramBoolean = bool;
        paramInt = 0;
        break;
        bool = false;
        break label36;
        label109:
        clear(paramInt);
      }
    }
    
    final boolean remove(int paramInt)
    {
      while (paramInt >= 64)
      {
        ensureNext();
        this = this.next;
        paramInt -= 64;
      }
      long l1 = 1L << paramInt;
      if ((l1 & this.mData) != 0L) {}
      for (boolean bool = true;; bool = false)
      {
        this.mData &= (0xFFFFFFFF ^ l1);
        long l2 = l1 - 1L;
        this.mData = (l2 & this.mData | Long.rotateRight(this.mData & (0xFFFFFFFF ^ l2), 1));
        if (this.next != null)
        {
          if (this.next.get(0)) {
            set(63);
          }
          this.next.remove(0);
        }
        return bool;
      }
    }
    
    final void set(int paramInt)
    {
      while (paramInt >= 64)
      {
        ensureNext();
        this = this.next;
        paramInt -= 64;
      }
      this.mData |= 1L << paramInt;
    }
    
    public final String toString()
    {
      if (this.next == null) {
        return Long.toBinaryString(this.mData);
      }
      return this.next.toString() + "xx" + Long.toBinaryString(this.mData);
    }
  }
  
  static abstract interface Callback
  {
    public abstract void addView(View paramView, int paramInt);
    
    public abstract void attachViewToParent(View paramView, int paramInt, ViewGroup.LayoutParams paramLayoutParams);
    
    public abstract void detachViewFromParent(int paramInt);
    
    public abstract View getChildAt(int paramInt);
    
    public abstract int getChildCount();
    
    public abstract RecyclerView.ViewHolder getChildViewHolder(View paramView);
    
    public abstract int indexOfChild(View paramView);
    
    public abstract void onEnteredHiddenState(View paramView);
    
    public abstract void onLeftHiddenState(View paramView);
    
    public abstract void removeAllViews();
    
    public abstract void removeViewAt(int paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.widget.ChildHelper
 * JD-Core Version:    0.7.0.1
 */