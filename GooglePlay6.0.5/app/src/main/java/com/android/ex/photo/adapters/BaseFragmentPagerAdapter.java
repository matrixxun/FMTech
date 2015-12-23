package com.android.ex.photo.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.LruCache;
import android.support.v4.view.PagerAdapter;
import android.view.View;

public abstract class BaseFragmentPagerAdapter
  extends PagerAdapter
{
  FragmentTransaction mCurTransaction = null;
  private Fragment mCurrentPrimaryItem = null;
  private LruCache<String, Fragment> mFragmentCache = new FragmentCache();
  private final FragmentManager mFragmentManager;
  
  public BaseFragmentPagerAdapter(FragmentManager paramFragmentManager)
  {
    this.mFragmentManager = paramFragmentManager;
  }
  
  public void destroyItem(View paramView, int paramInt, Object paramObject)
  {
    if (this.mCurTransaction == null) {
      this.mCurTransaction = this.mFragmentManager.beginTransaction();
    }
    Fragment localFragment = (Fragment)paramObject;
    String str = localFragment.mTag;
    if (str == null) {
      str = makeFragmentName(paramView.getId(), paramInt);
    }
    this.mFragmentCache.put(str, localFragment);
    this.mCurTransaction.detach(localFragment);
  }
  
  public final void finishUpdate$3c7ec8c3()
  {
    if ((this.mCurTransaction != null) && (!this.mFragmentManager.isDestroyed()))
    {
      this.mCurTransaction.commitAllowingStateLoss();
      this.mCurTransaction = null;
      this.mFragmentManager.executePendingTransactions();
    }
  }
  
  public abstract Fragment getItem(int paramInt);
  
  public Object instantiateItem(View paramView, int paramInt)
  {
    if (this.mCurTransaction == null) {
      this.mCurTransaction = this.mFragmentManager.beginTransaction();
    }
    String str = makeFragmentName(paramView.getId(), paramInt);
    this.mFragmentCache.remove(str);
    Fragment localFragment = this.mFragmentManager.findFragmentByTag(str);
    if (localFragment != null) {
      this.mCurTransaction.attach(localFragment);
    }
    for (;;)
    {
      if (localFragment != this.mCurrentPrimaryItem) {
        localFragment.setMenuVisibility(false);
      }
      return localFragment;
      localFragment = getItem(paramInt);
      if (localFragment == null) {
        return null;
      }
      this.mCurTransaction.add(paramView.getId(), localFragment, makeFragmentName(paramView.getId(), paramInt));
    }
  }
  
  public final boolean isViewFromObject(View paramView, Object paramObject)
  {
    View localView = ((Fragment)paramObject).mView;
    for (Object localObject = paramView; (localObject instanceof View); localObject = ((View)localObject).getParent()) {
      if (localObject == localView) {
        return true;
      }
    }
    return false;
  }
  
  protected String makeFragmentName(int paramInt1, int paramInt2)
  {
    return "android:switcher:" + paramInt1 + ":" + paramInt2;
  }
  
  public final void setPrimaryItem$7e55ba3e(Object paramObject)
  {
    Fragment localFragment = (Fragment)paramObject;
    if (localFragment != this.mCurrentPrimaryItem)
    {
      if (this.mCurrentPrimaryItem != null) {
        this.mCurrentPrimaryItem.setMenuVisibility(false);
      }
      if (localFragment != null) {
        localFragment.setMenuVisibility(true);
      }
      this.mCurrentPrimaryItem = localFragment;
    }
  }
  
  private final class FragmentCache
    extends LruCache<String, Fragment>
  {
    public FragmentCache()
    {
      super();
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.ex.photo.adapters.BaseFragmentPagerAdapter
 * JD-Core Version:    0.7.0.1
 */