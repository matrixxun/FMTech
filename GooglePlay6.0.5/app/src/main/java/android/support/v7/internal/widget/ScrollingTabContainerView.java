package android.support.v7.internal.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.appcompat.R.attr;
import android.support.v7.appcompat.R.dimen;
import android.support.v7.appcompat.R.styleable;
import android.support.v7.internal.view.ActionBarPolicy;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutCompat.LayoutParams;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public final class ScrollingTabContainerView
  extends HorizontalScrollView
  implements AdapterView.OnItemSelectedListener
{
  private static final Interpolator sAlphaInterpolator = new DecelerateInterpolator();
  private boolean mAllowCollapse;
  private int mContentHeight;
  int mMaxTabWidth;
  private int mSelectedTabIndex;
  int mStackedTabMaxWidth;
  private LinearLayoutCompat mTabLayout;
  Runnable mTabSelector;
  private Spinner mTabSpinner;
  
  private boolean isCollapsed()
  {
    return (this.mTabSpinner != null) && (this.mTabSpinner.getParent() == this);
  }
  
  private boolean performExpand()
  {
    if (!isCollapsed()) {
      return false;
    }
    removeView(this.mTabSpinner);
    addView(this.mTabLayout, new ViewGroup.LayoutParams(-2, -1));
    setTabSelected(this.mTabSpinner.getSelectedItemPosition());
    return false;
  }
  
  public final void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    if (this.mTabSelector != null) {
      post(this.mTabSelector);
    }
  }
  
  protected final void onConfigurationChanged(Configuration paramConfiguration)
  {
    if (Build.VERSION.SDK_INT >= 8) {
      super.onConfigurationChanged(paramConfiguration);
    }
    ActionBarPolicy localActionBarPolicy = ActionBarPolicy.get(getContext());
    TypedArray localTypedArray = localActionBarPolicy.mContext.obtainStyledAttributes(null, R.styleable.ActionBar, R.attr.actionBarStyle, 0);
    int i = localTypedArray.getLayoutDimension(R.styleable.ActionBar_height, 0);
    Resources localResources = localActionBarPolicy.mContext.getResources();
    if (!localActionBarPolicy.hasEmbeddedTabs()) {
      i = Math.min(i, localResources.getDimensionPixelSize(R.dimen.abc_action_bar_stacked_max_height));
    }
    localTypedArray.recycle();
    setContentHeight(i);
    this.mStackedTabMaxWidth = localActionBarPolicy.mContext.getResources().getDimensionPixelSize(R.dimen.abc_action_bar_stacked_tab_max_width);
  }
  
  public final void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    if (this.mTabSelector != null) {
      removeCallbacks(this.mTabSelector);
    }
  }
  
  public final void onItemSelected(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong) {}
  
  public final void onMeasure(int paramInt1, int paramInt2)
  {
    int i = 1;
    int j = View.MeasureSpec.getMode(paramInt1);
    boolean bool;
    label72:
    label87:
    int m;
    if (j == 1073741824)
    {
      bool = i;
      setFillViewport(bool);
      int k = this.mTabLayout.getChildCount();
      if ((k <= i) || ((j != 1073741824) && (j != -2147483648))) {
        break label339;
      }
      if (k <= 2) {
        break label326;
      }
      this.mMaxTabWidth = ((int)(0.4F * View.MeasureSpec.getSize(paramInt1)));
      this.mMaxTabWidth = Math.min(this.mMaxTabWidth, this.mStackedTabMaxWidth);
      m = View.MeasureSpec.makeMeasureSpec(this.mContentHeight, 1073741824);
      if ((bool) || (!this.mAllowCollapse)) {
        break label347;
      }
      label110:
      if (i == 0) {
        break label360;
      }
      this.mTabLayout.measure(0, m);
      if (this.mTabLayout.getMeasuredWidth() <= View.MeasureSpec.getSize(paramInt1)) {
        break label352;
      }
      if (!isCollapsed())
      {
        if (this.mTabSpinner == null)
        {
          AppCompatSpinner localAppCompatSpinner = new AppCompatSpinner(getContext(), null, R.attr.actionDropDownStyle);
          localAppCompatSpinner.setLayoutParams(new LinearLayoutCompat.LayoutParams(-2, -1));
          localAppCompatSpinner.setOnItemSelectedListener(this);
          this.mTabSpinner = localAppCompatSpinner;
        }
        removeView(this.mTabLayout);
        addView(this.mTabSpinner, new ViewGroup.LayoutParams(-2, -1));
        if (this.mTabSpinner.getAdapter() == null) {
          this.mTabSpinner.setAdapter(new TabAdapter((byte)0));
        }
        if (this.mTabSelector != null)
        {
          removeCallbacks(this.mTabSelector);
          this.mTabSelector = null;
        }
        this.mTabSpinner.setSelection(this.mSelectedTabIndex);
      }
    }
    for (;;)
    {
      int n = getMeasuredWidth();
      super.onMeasure(paramInt1, m);
      int i1 = getMeasuredWidth();
      if ((bool) && (n != i1)) {
        setTabSelected(this.mSelectedTabIndex);
      }
      return;
      bool = false;
      break;
      label326:
      this.mMaxTabWidth = (View.MeasureSpec.getSize(paramInt1) / 2);
      break label72;
      label339:
      this.mMaxTabWidth = -1;
      break label87;
      label347:
      i = 0;
      break label110;
      label352:
      performExpand();
      continue;
      label360:
      performExpand();
    }
  }
  
  public final void onNothingSelected(AdapterView<?> paramAdapterView) {}
  
  public final void setAllowCollapse(boolean paramBoolean)
  {
    this.mAllowCollapse = paramBoolean;
  }
  
  public final void setContentHeight(int paramInt)
  {
    this.mContentHeight = paramInt;
    requestLayout();
  }
  
  public final void setTabSelected(int paramInt)
  {
    this.mSelectedTabIndex = paramInt;
    int i = this.mTabLayout.getChildCount();
    int j = 0;
    if (j < i)
    {
      View localView1 = this.mTabLayout.getChildAt(j);
      if (j == paramInt) {}
      for (boolean bool = true;; bool = false)
      {
        localView1.setSelected(bool);
        if (bool)
        {
          final View localView2 = this.mTabLayout.getChildAt(paramInt);
          if (this.mTabSelector != null) {
            removeCallbacks(this.mTabSelector);
          }
          this.mTabSelector = new Runnable()
          {
            public final void run()
            {
              int i = localView2.getLeft() - (ScrollingTabContainerView.this.getWidth() - localView2.getWidth()) / 2;
              ScrollingTabContainerView.this.smoothScrollTo(i, 0);
              ScrollingTabContainerView.this.mTabSelector = null;
            }
          };
          post(this.mTabSelector);
        }
        j++;
        break;
      }
    }
    if ((this.mTabSpinner != null) && (paramInt >= 0)) {
      this.mTabSpinner.setSelection(paramInt);
    }
  }
  
  private final class TabAdapter
    extends BaseAdapter
  {
    private TabAdapter() {}
    
    public final int getCount()
    {
      return ScrollingTabContainerView.this.mTabLayout.getChildCount();
    }
    
    public final Object getItem(int paramInt)
    {
      return ((ScrollingTabContainerView.TabView)ScrollingTabContainerView.this.mTabLayout.getChildAt(paramInt)).mTab;
    }
    
    public final long getItemId(int paramInt)
    {
      return paramInt;
    }
    
    public final View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      if (paramView == null) {
        return ScrollingTabContainerView.access$300$c6ad75e(ScrollingTabContainerView.this, (ActionBar.Tab)getItem(paramInt));
      }
      ScrollingTabContainerView.TabView localTabView = (ScrollingTabContainerView.TabView)paramView;
      localTabView.mTab = ((ActionBar.Tab)getItem(paramInt));
      localTabView.update();
      return paramView;
    }
  }
  
  private final class TabView
    extends LinearLayoutCompat
    implements View.OnLongClickListener
  {
    private final int[] BG_ATTRS = { 16842964 };
    private View mCustomView;
    private ImageView mIconView;
    ActionBar.Tab mTab;
    private TextView mTextView;
    
    public TabView(Context paramContext, ActionBar.Tab paramTab, boolean paramBoolean)
    {
      super(null, R.attr.actionBarTabStyle);
      this.mTab = paramTab;
      TintTypedArray localTintTypedArray = TintTypedArray.obtainStyledAttributes$1a6c1917(paramContext, null, this.BG_ATTRS, R.attr.actionBarTabStyle);
      if (localTintTypedArray.hasValue(0)) {
        setBackgroundDrawable(localTintTypedArray.getDrawable(0));
      }
      localTintTypedArray.mWrapped.recycle();
      setGravity(8388627);
      update();
    }
    
    public final void onInitializeAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent)
    {
      super.onInitializeAccessibilityEvent(paramAccessibilityEvent);
      paramAccessibilityEvent.setClassName(ActionBar.Tab.class.getName());
    }
    
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo paramAccessibilityNodeInfo)
    {
      super.onInitializeAccessibilityNodeInfo(paramAccessibilityNodeInfo);
      if (Build.VERSION.SDK_INT >= 14) {
        paramAccessibilityNodeInfo.setClassName(ActionBar.Tab.class.getName());
      }
    }
    
    public final boolean onLongClick(View paramView)
    {
      int[] arrayOfInt = new int[2];
      getLocationOnScreen(arrayOfInt);
      Context localContext = getContext();
      int i = getWidth();
      int j = getHeight();
      int k = localContext.getResources().getDisplayMetrics().widthPixels;
      Toast localToast = Toast.makeText(localContext, this.mTab.getContentDescription(), 0);
      localToast.setGravity(49, arrayOfInt[0] + i / 2 - k / 2, j);
      localToast.show();
      return true;
    }
    
    public final void onMeasure(int paramInt1, int paramInt2)
    {
      super.onMeasure(paramInt1, paramInt2);
      if ((ScrollingTabContainerView.this.mMaxTabWidth > 0) && (getMeasuredWidth() > ScrollingTabContainerView.this.mMaxTabWidth)) {
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(ScrollingTabContainerView.this.mMaxTabWidth, 1073741824), paramInt2);
      }
    }
    
    public final void setSelected(boolean paramBoolean)
    {
      if (isSelected() != paramBoolean) {}
      for (int i = 1;; i = 0)
      {
        super.setSelected(paramBoolean);
        if ((i != 0) && (paramBoolean)) {
          sendAccessibilityEvent(4);
        }
        return;
      }
    }
    
    public final void update()
    {
      ActionBar.Tab localTab = this.mTab;
      View localView = localTab.getCustomView();
      if (localView != null)
      {
        ViewParent localViewParent = localView.getParent();
        if (localViewParent != this)
        {
          if (localViewParent != null) {
            ((ViewGroup)localViewParent).removeView(localView);
          }
          addView(localView);
        }
        this.mCustomView = localView;
        if (this.mTextView != null) {
          this.mTextView.setVisibility(8);
        }
        if (this.mIconView != null)
        {
          this.mIconView.setVisibility(8);
          this.mIconView.setImageDrawable(null);
        }
        return;
      }
      if (this.mCustomView != null)
      {
        removeView(this.mCustomView);
        this.mCustomView = null;
      }
      Drawable localDrawable = localTab.getIcon();
      CharSequence localCharSequence = localTab.getText();
      int i;
      if (localDrawable != null)
      {
        if (this.mIconView == null)
        {
          ImageView localImageView = new ImageView(getContext());
          LinearLayoutCompat.LayoutParams localLayoutParams2 = new LinearLayoutCompat.LayoutParams(-2, -2);
          localLayoutParams2.gravity = 16;
          localImageView.setLayoutParams(localLayoutParams2);
          addView(localImageView, 0);
          this.mIconView = localImageView;
        }
        this.mIconView.setImageDrawable(localDrawable);
        this.mIconView.setVisibility(0);
        if (TextUtils.isEmpty(localCharSequence)) {
          break label372;
        }
        i = 1;
        label213:
        if (i == 0) {
          break label378;
        }
        if (this.mTextView == null)
        {
          AppCompatTextView localAppCompatTextView = new AppCompatTextView(getContext(), null, R.attr.actionBarTabTextStyle);
          localAppCompatTextView.setEllipsize(TextUtils.TruncateAt.END);
          LinearLayoutCompat.LayoutParams localLayoutParams1 = new LinearLayoutCompat.LayoutParams(-2, -2);
          localLayoutParams1.gravity = 16;
          localAppCompatTextView.setLayoutParams(localLayoutParams1);
          addView(localAppCompatTextView);
          this.mTextView = localAppCompatTextView;
        }
        this.mTextView.setText(localCharSequence);
        this.mTextView.setVisibility(0);
      }
      for (;;)
      {
        if (this.mIconView != null) {
          this.mIconView.setContentDescription(localTab.getContentDescription());
        }
        if ((i != 0) || (TextUtils.isEmpty(localTab.getContentDescription()))) {
          break label405;
        }
        setOnLongClickListener(this);
        return;
        if (this.mIconView == null) {
          break;
        }
        this.mIconView.setVisibility(8);
        this.mIconView.setImageDrawable(null);
        break;
        label372:
        i = 0;
        break label213;
        label378:
        if (this.mTextView != null)
        {
          this.mTextView.setVisibility(8);
          this.mTextView.setText(null);
        }
      }
      label405:
      setOnLongClickListener(null);
      setLongClickable(false);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.internal.widget.ScrollingTabContainerView
 * JD-Core Version:    0.7.0.1
 */