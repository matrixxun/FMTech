package com.google.android.play.headerlist;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.database.DataSetObserver;
import android.os.Build.VERSION;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import com.google.android.libraries.bind.bidi.BidiPagingHelper;
import com.google.android.play.R.color;
import com.google.android.play.R.id;
import com.google.android.play.R.layout;
import java.lang.ref.WeakReference;

public class PlayHeaderListTabStrip
  extends FrameLayout
{
  private boolean mAnimateContainerPadding;
  private boolean mAnimateOnTabClick;
  private boolean mEnablePagerScrollSync = true;
  private ViewPager.OnPageChangeListener mExternalPageChangeListener;
  private int mLastScrollto;
  private int mMaxTabWidthPx;
  private PlayHeaderListLayout.OnTabSelectedListener mOnTabSelectedListener;
  private final PageListener mPageListener = new PageListener((byte)0);
  private ViewPager mPager;
  private HorizontalScrollView mScrollView;
  private float mSmoothScrollThresholdPx = 5.0F * getResources().getDisplayMetrics().density;
  int mTabBackgroundResId;
  PlayHeaderListTabContainer mTabContainer;
  boolean mUseHighContrast;
  private WeakReference<PagerAdapter> mWatchingAdapter;
  
  public PlayHeaderListTabStrip(Context paramContext)
  {
    this(paramContext, null, 0);
  }
  
  public PlayHeaderListTabStrip(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public PlayHeaderListTabStrip(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  private int getSelectedTabPosition()
  {
    if (this.mPager == null) {}
    View localView;
    do
    {
      return 0;
      int i = this.mPager.getCurrentItem();
      localView = this.mTabContainer.getChildAt(i);
    } while (localView == null);
    return localView.getLeft() - this.mScrollView.getScrollX();
  }
  
  private void scrollToVisualPosition(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    if (this.mScrollView == null) {}
    int j;
    do
    {
      View localView;
      do
      {
        int i;
        do
        {
          return;
          i = this.mTabContainer.getChildCount();
        } while ((i == 0) || (paramInt1 < 0) || (paramInt1 >= i));
        localView = this.mTabContainer.getChildAt(paramInt1);
      } while ((localView == null) || (localView.getMeasuredWidth() == 0));
      j = paramInt2 + localView.getLeft() - getWidth() / 2 + localView.getWidth() / 2;
    } while (j == this.mLastScrollto);
    int k;
    if (Math.abs(j - this.mScrollView.getScrollX()) > this.mSmoothScrollThresholdPx)
    {
      k = 1;
      if ((k == 0) || (!paramBoolean)) {
        break label145;
      }
      this.mScrollView.smoothScrollTo(j, 0);
    }
    for (;;)
    {
      this.mLastScrollto = j;
      return;
      k = 0;
      break;
      label145:
      this.mScrollView.smoothScrollBy(0, 0);
      this.mScrollView.smoothScrollBy(0, 0);
      this.mScrollView.scrollTo(j, 0);
    }
  }
  
  private void updateTabs()
  {
    this.mTabContainer.removeAllViews();
    if (this.mPager == null) {}
    for (PagerAdapter localPagerAdapter = null; localPagerAdapter == null; localPagerAdapter = this.mPager.getAdapter()) {
      return;
    }
    LayoutInflater localLayoutInflater = LayoutInflater.from(getContext());
    int i = localPagerAdapter.getCount();
    for (int j = 0; j < i; j++)
    {
      int k = BidiPagingHelper.getLogicalPosition(localPagerAdapter, j);
      View localView = makeTabView(localLayoutInflater, this.mTabContainer, k);
      TextView localTextView = (TextView)localView;
      localTextView.setText(localPagerAdapter.getPageTitle(k));
      localTextView.setOnClickListener(new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          if (PlayHeaderListTabStrip.this.mOnTabSelectedListener != null) {
            PlayHeaderListTabStrip.this.mOnTabSelectedListener.onBeforeTabSelected(this.val$pagerPosition);
          }
          PlayHeaderListTabStrip.access$502(PlayHeaderListTabStrip.this, PlayHeaderListTabStrip.this.mAnimateOnTabClick);
          PlayHeaderListTabStrip.this.updateSelectedTab(this.val$pagerPosition, true);
          PlayHeaderListTabStrip.this.mPager.setCurrentItem(this.val$pagerPosition);
          if (PlayHeaderListTabStrip.this.mOnTabSelectedListener != null) {
            PlayHeaderListTabStrip.this.mOnTabSelectedListener.onTabSelected(this.val$pagerPosition);
          }
        }
      });
      this.mTabContainer.addView(localView);
    }
    this.mTabContainer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
    {
      public final void onGlobalLayout()
      {
        int i = PlayHeaderListTabStrip.this.mPager.getCurrentItem();
        PlayHeaderListTabStrip.access$200$77a5b19b(PlayHeaderListTabStrip.this, i, 0);
        PlayHeaderListTabStrip.this.mTabContainer.getViewTreeObserver().removeGlobalOnLayoutListener(this);
      }
    });
    updateSelectedTab$1385ff();
    this.mTabContainer.mPaddingValidForTabs = false;
  }
  
  void applyTabContrastMode(TextView paramTextView, boolean paramBoolean)
  {
    if (paramBoolean)
    {
      paramTextView.setTextColor(-1);
      return;
    }
    paramTextView.setTextColor(getResources().getColorStateList(R.color.play_header_list_tab_text_color));
  }
  
  public String getCurrentTabTitle()
  {
    if ((this.mPager == null) || (this.mPager.getAdapter() == null)) {
      return null;
    }
    return (String)this.mPager.getAdapter().getPageTitle(this.mPager.getCurrentItem());
  }
  
  protected int getMaxTabWidth()
  {
    if (this.mMaxTabWidthPx == 0)
    {
      DisplayMetrics localDisplayMetrics = new DisplayMetrics();
      ((Activity)getContext()).getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
      this.mMaxTabWidthPx = localDisplayMetrics.widthPixels;
    }
    return this.mMaxTabWidthPx;
  }
  
  void getSubViewReferences()
  {
    this.mScrollView = ((HorizontalScrollView)findViewById(R.id.play_header_list_tab_scroll));
    this.mTabContainer = ((PlayHeaderListTabContainer)findViewById(R.id.play_header_list_tab_container));
  }
  
  @TargetApi(14)
  public View makeTabView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, int paramInt)
  {
    TextView localTextView = (TextView)paramLayoutInflater.inflate(R.layout.play_header_list_tab_text, paramViewGroup, false);
    localTextView.setMaxWidth(getMaxTabWidth());
    applyTabContrastMode(localTextView, this.mUseHighContrast);
    localTextView.setBackgroundResource(this.mTabBackgroundResId);
    return localTextView;
  }
  
  public final void notifyPagerAdapterChanged()
  {
    Object localObject;
    if (this.mPager == null)
    {
      localObject = null;
      if (this.mWatchingAdapter == null) {
        break label84;
      }
    }
    label84:
    for (PagerAdapter localPagerAdapter = (PagerAdapter)this.mWatchingAdapter.get();; localPagerAdapter = null)
    {
      if (localPagerAdapter != null)
      {
        localPagerAdapter.unregisterDataSetObserver(this.mPageListener);
        this.mWatchingAdapter = null;
      }
      if (localObject != null)
      {
        ((PagerAdapter)localObject).registerDataSetObserver(this.mPageListener);
        this.mWatchingAdapter = new WeakReference(localObject);
      }
      updateTabs();
      return;
      localObject = this.mPager.getAdapter();
      break;
    }
  }
  
  public void onFinishInflate()
  {
    getSubViewReferences();
    this.mTabContainer.setSelectedIndicatorColor(getResources().getColor(R.color.play_header_list_tab_underline_color));
  }
  
  @TargetApi(12)
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (this.mAnimateContainerPadding) {}
    for (int i = getSelectedTabPosition();; i = 0)
    {
      super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
      if (this.mScrollView.getScrollX() != this.mLastScrollto) {
        this.mScrollView.scrollTo(this.mLastScrollto, 0);
      }
      if (this.mAnimateContainerPadding)
      {
        updateSelectedTab$1385ff();
        if (Build.VERSION.SDK_INT >= 12)
        {
          int j = getSelectedTabPosition();
          if (j != i)
          {
            int k = j - i;
            this.mTabContainer.setTranslationX(-k);
            this.mTabContainer.animate().translationX(0.0F).setDuration(200L);
          }
        }
      }
      return;
    }
  }
  
  public void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    if (i > 0) {
      this.mTabContainer.setStripWidth(i);
    }
    super.onMeasure(paramInt1, paramInt2);
  }
  
  public void setAnimateOnTabClick(boolean paramBoolean)
  {
    this.mAnimateOnTabClick = paramBoolean;
  }
  
  void setExternalOnPageChangeListener(ViewPager.OnPageChangeListener paramOnPageChangeListener)
  {
    this.mExternalPageChangeListener = paramOnPageChangeListener;
  }
  
  void setOnTabSelectedListener(PlayHeaderListLayout.OnTabSelectedListener paramOnTabSelectedListener)
  {
    this.mOnTabSelectedListener = paramOnTabSelectedListener;
  }
  
  public void setSelectedUnderlineThickness(int paramInt)
  {
    this.mTabContainer.setSelectedUnderlineThickness(paramInt);
  }
  
  void setTabsBackgroundResource(int paramInt)
  {
    if (this.mTabBackgroundResId != paramInt)
    {
      this.mTabBackgroundResId = paramInt;
      int i = this.mTabContainer.getChildCount();
      for (int j = 0; j < i; j++) {
        this.mTabContainer.getChildAt(j).setBackgroundResource(paramInt);
      }
    }
  }
  
  final void setUseFloatingTabPadding(boolean paramBoolean1, boolean paramBoolean2)
  {
    this.mTabContainer.setUseFloatingTabPadding(paramBoolean1);
    this.mAnimateContainerPadding = paramBoolean2;
    updateSelectedTab$1385ff();
  }
  
  public void setViewPager(ViewPager paramViewPager)
  {
    if (this.mPager != null) {
      this.mPager.setOnPageChangeListener(null);
    }
    this.mPager = paramViewPager;
    if (this.mPager != null) {
      this.mPager.setOnPageChangeListener(this.mPageListener);
    }
    notifyPagerAdapterChanged();
  }
  
  protected final void updateSelectedTab(int paramInt, boolean paramBoolean)
  {
    scrollToVisualPosition(paramInt, 0, paramBoolean);
    int i = 0;
    if (i < this.mTabContainer.getChildCount())
    {
      View localView = this.mTabContainer.getChildAt(i);
      if (i == paramInt) {}
      for (boolean bool = true;; bool = false)
      {
        localView.setSelected(bool);
        i++;
        break;
      }
    }
  }
  
  protected final void updateSelectedTab$1385ff()
  {
    if (this.mPager == null) {
      return;
    }
    updateSelectedTab(this.mPager.getCurrentItem(), false);
  }
  
  private final class PageListener
    extends DataSetObserver
    implements ViewPager.OnPageChangeListener
  {
    private int mScrollState;
    
    private PageListener() {}
    
    public final void onChanged()
    {
      PlayHeaderListTabStrip.this.updateTabs();
    }
    
    public final void onPageScrollStateChanged(int paramInt)
    {
      this.mScrollState = paramInt;
      if (PlayHeaderListTabStrip.this.mExternalPageChangeListener != null) {
        PlayHeaderListTabStrip.this.mExternalPageChangeListener.onPageScrollStateChanged(paramInt);
      }
      if (this.mScrollState == 0) {
        PlayHeaderListTabStrip.access$502(PlayHeaderListTabStrip.this, true);
      }
    }
    
    public final void onPageScrolled(int paramInt1, float paramFloat, int paramInt2)
    {
      if (PlayHeaderListTabStrip.this.mExternalPageChangeListener != null) {
        PlayHeaderListTabStrip.this.mExternalPageChangeListener.onPageScrolled(paramInt1, paramFloat, paramInt2);
      }
      if (!PlayHeaderListTabStrip.this.mEnablePagerScrollSync) {}
      int i;
      do
      {
        return;
        i = PlayHeaderListTabStrip.this.mTabContainer.getChildCount();
      } while ((i == 0) || (paramInt1 < 0) || (paramInt1 >= i));
      PlayHeaderListTabContainer localPlayHeaderListTabContainer = PlayHeaderListTabStrip.this.mTabContainer;
      localPlayHeaderListTabContainer.mIndexForSelection = paramInt1;
      localPlayHeaderListTabContainer.mSelectionOffset = paramFloat;
      localPlayHeaderListTabContainer.invalidate();
      View localView1 = PlayHeaderListTabStrip.this.mTabContainer.getChildAt(paramInt1);
      int j;
      View localView2;
      if (localView1 == null)
      {
        j = 0;
        int k = paramInt1 + 1;
        localView2 = PlayHeaderListTabStrip.this.mTabContainer.getChildAt(k);
        if (localView2 != null) {
          break label172;
        }
      }
      label172:
      for (int m = 0;; m = localView2.getWidth())
      {
        int n = (int)(0.5F * (paramFloat * (j + m)));
        PlayHeaderListTabStrip.access$200$77a5b19b(PlayHeaderListTabStrip.this, paramInt1, n);
        return;
        j = localView1.getWidth();
        break;
      }
    }
    
    public final void onPageSelected(int paramInt)
    {
      if (PlayHeaderListTabStrip.this.mExternalPageChangeListener != null) {
        PlayHeaderListTabStrip.this.mExternalPageChangeListener.onPageSelected(paramInt);
      }
      PlayHeaderListTabContainer localPlayHeaderListTabContainer = PlayHeaderListTabStrip.this.mTabContainer;
      localPlayHeaderListTabContainer.mIndexForSelection = paramInt;
      localPlayHeaderListTabContainer.mSelectionOffset = 0.0F;
      localPlayHeaderListTabContainer.invalidate();
      PlayHeaderListTabStrip.this.updateSelectedTab$1385ff();
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.headerlist.PlayHeaderListTabStrip
 * JD-Core Version:    0.7.0.1
 */