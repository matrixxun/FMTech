package android.support.v7.internal.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.support.v7.appcompat.R.attr;
import android.support.v7.appcompat.R.id;
import android.support.v7.appcompat.R.layout;
import android.support.v7.appcompat.R.styleable;
import android.support.v7.internal.view.menu.MenuBuilder;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.ActionMenuPresenter;
import android.support.v7.widget.ActionMenuView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.accessibility.AccessibilityEvent;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ActionBarContextView
  extends AbsActionBarView
{
  public View mClose;
  private int mCloseItemLayout;
  private View mCustomView;
  private CharSequence mSubtitle;
  private int mSubtitleStyleRes;
  private TextView mSubtitleView;
  private CharSequence mTitle;
  private LinearLayout mTitleLayout;
  public boolean mTitleOptional;
  private int mTitleStyleRes;
  private TextView mTitleView;
  
  public ActionBarContextView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public ActionBarContextView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.actionModeStyle);
  }
  
  public ActionBarContextView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    TintTypedArray localTintTypedArray = TintTypedArray.obtainStyledAttributes$1a6c1917(paramContext, paramAttributeSet, R.styleable.ActionMode, paramInt);
    setBackgroundDrawable(localTintTypedArray.getDrawable(R.styleable.ActionMode_background));
    this.mTitleStyleRes = localTintTypedArray.getResourceId(R.styleable.ActionMode_titleTextStyle, 0);
    this.mSubtitleStyleRes = localTintTypedArray.getResourceId(R.styleable.ActionMode_subtitleTextStyle, 0);
    this.mContentHeight = localTintTypedArray.getLayoutDimension(R.styleable.ActionMode_height, 0);
    this.mCloseItemLayout = localTintTypedArray.getResourceId(R.styleable.ActionMode_closeItemLayout, R.layout.abc_action_mode_close_item_material);
    localTintTypedArray.mWrapped.recycle();
  }
  
  private void initTitle()
  {
    int i = 8;
    if (this.mTitleLayout == null)
    {
      LayoutInflater.from(getContext()).inflate(R.layout.abc_action_bar_title_item, this);
      this.mTitleLayout = ((LinearLayout)getChildAt(-1 + getChildCount()));
      this.mTitleView = ((TextView)this.mTitleLayout.findViewById(R.id.action_bar_title));
      this.mSubtitleView = ((TextView)this.mTitleLayout.findViewById(R.id.action_bar_subtitle));
      if (this.mTitleStyleRes != 0) {
        this.mTitleView.setTextAppearance(getContext(), this.mTitleStyleRes);
      }
      if (this.mSubtitleStyleRes != 0) {
        this.mSubtitleView.setTextAppearance(getContext(), this.mSubtitleStyleRes);
      }
    }
    this.mTitleView.setText(this.mTitle);
    this.mSubtitleView.setText(this.mSubtitle);
    int j;
    int k;
    label166:
    TextView localTextView;
    if (!TextUtils.isEmpty(this.mTitle))
    {
      j = 1;
      if (TextUtils.isEmpty(this.mSubtitle)) {
        break label232;
      }
      k = 1;
      localTextView = this.mSubtitleView;
      if (k == 0) {
        break label237;
      }
    }
    label232:
    label237:
    for (int m = 0;; m = i)
    {
      localTextView.setVisibility(m);
      LinearLayout localLinearLayout = this.mTitleLayout;
      if ((j != 0) || (k != 0)) {
        i = 0;
      }
      localLinearLayout.setVisibility(i);
      if (this.mTitleLayout.getParent() == null) {
        addView(this.mTitleLayout);
      }
      return;
      j = 0;
      break;
      k = 0;
      break label166;
    }
  }
  
  protected ViewGroup.LayoutParams generateDefaultLayoutParams()
  {
    return new ViewGroup.MarginLayoutParams(-1, -2);
  }
  
  public ViewGroup.LayoutParams generateLayoutParams(AttributeSet paramAttributeSet)
  {
    return new ViewGroup.MarginLayoutParams(getContext(), paramAttributeSet);
  }
  
  public CharSequence getSubtitle()
  {
    return this.mSubtitle;
  }
  
  public CharSequence getTitle()
  {
    return this.mTitle;
  }
  
  public final void initForMode(final ActionMode paramActionMode)
  {
    if (this.mClose == null)
    {
      this.mClose = LayoutInflater.from(getContext()).inflate(this.mCloseItemLayout, this, false);
      addView(this.mClose);
    }
    for (;;)
    {
      this.mClose.findViewById(R.id.action_mode_close_button).setOnClickListener(new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          paramActionMode.finish();
        }
      });
      MenuBuilder localMenuBuilder = (MenuBuilder)paramActionMode.getMenu();
      if (this.mActionMenuPresenter != null) {
        this.mActionMenuPresenter.dismissPopupMenus();
      }
      this.mActionMenuPresenter = new ActionMenuPresenter(getContext());
      this.mActionMenuPresenter.setReserveOverflow$1385ff();
      ViewGroup.LayoutParams localLayoutParams = new ViewGroup.LayoutParams(-2, -1);
      localMenuBuilder.addMenuPresenter(this.mActionMenuPresenter, this.mPopupContext);
      this.mMenuView = ((ActionMenuView)this.mActionMenuPresenter.getMenuView(this));
      this.mMenuView.setBackgroundDrawable(null);
      addView(this.mMenuView, localLayoutParams);
      return;
      if (this.mClose.getParent() == null) {
        addView(this.mClose);
      }
    }
  }
  
  public final void killMode()
  {
    removeAllViews();
    this.mCustomView = null;
    this.mMenuView = null;
  }
  
  public void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    if (this.mActionMenuPresenter != null)
    {
      this.mActionMenuPresenter.hideOverflowMenu();
      this.mActionMenuPresenter.hideSubMenus();
    }
  }
  
  public void onInitializeAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent)
  {
    if (Build.VERSION.SDK_INT >= 14)
    {
      if (paramAccessibilityEvent.getEventType() == 32)
      {
        paramAccessibilityEvent.setSource(this);
        paramAccessibilityEvent.setClassName(getClass().getName());
        paramAccessibilityEvent.setPackageName(getContext().getPackageName());
        paramAccessibilityEvent.setContentDescription(this.mTitle);
      }
    }
    else {
      return;
    }
    super.onInitializeAccessibilityEvent(paramAccessibilityEvent);
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    boolean bool1 = ViewUtils.isLayoutRtl(this);
    int i;
    int j;
    int k;
    ViewGroup.MarginLayoutParams localMarginLayoutParams;
    int n;
    label87:
    int i1;
    label99:
    int m;
    label217:
    ActionMenuView localActionMenuView;
    if (bool1)
    {
      i = paramInt3 - paramInt1 - getPaddingRight();
      j = getPaddingTop();
      k = paramInt4 - paramInt2 - getPaddingTop() - getPaddingBottom();
      if ((this.mClose != null) && (this.mClose.getVisibility() != 8))
      {
        localMarginLayoutParams = (ViewGroup.MarginLayoutParams)this.mClose.getLayoutParams();
        if (!bool1) {
          break label262;
        }
        n = localMarginLayoutParams.rightMargin;
        if (!bool1) {
          break label272;
        }
        i1 = localMarginLayoutParams.leftMargin;
        int i2 = next(i, n, bool1);
        i = next(i2 + positionChild(this.mClose, i2, j, k, bool1), i1, bool1);
      }
      if ((this.mTitleLayout != null) && (this.mCustomView == null) && (this.mTitleLayout.getVisibility() != 8)) {
        i += positionChild(this.mTitleLayout, i, j, k, bool1);
      }
      if (this.mCustomView != null) {
        positionChild(this.mCustomView, i, j, k, bool1);
      }
      if (!bool1) {
        break label282;
      }
      m = getPaddingLeft();
      if (this.mMenuView != null)
      {
        localActionMenuView = this.mMenuView;
        if (bool1) {
          break label296;
        }
      }
    }
    label262:
    label272:
    label282:
    label296:
    for (boolean bool2 = true;; bool2 = false)
    {
      positionChild(localActionMenuView, m, j, k, bool2);
      return;
      i = getPaddingLeft();
      break;
      n = localMarginLayoutParams.leftMargin;
      break label87;
      i1 = localMarginLayoutParams.rightMargin;
      break label99;
      m = paramInt3 - paramInt1 - getPaddingRight();
      break label217;
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    if (View.MeasureSpec.getMode(paramInt1) != 1073741824) {
      throw new IllegalStateException(getClass().getSimpleName() + " can only be used with android:layout_width=\"match_parent\" (or fill_parent)");
    }
    if (View.MeasureSpec.getMode(paramInt2) == 0) {
      throw new IllegalStateException(getClass().getSimpleName() + " can only be used with android:layout_height=\"wrap_content\"");
    }
    int i = View.MeasureSpec.getSize(paramInt1);
    int j;
    int k;
    int m;
    int n;
    int i1;
    int i12;
    label281:
    int i13;
    label307:
    label314:
    ViewGroup.LayoutParams localLayoutParams;
    int i6;
    label345:
    int i7;
    label365:
    int i8;
    if (this.mContentHeight > 0)
    {
      j = this.mContentHeight;
      k = getPaddingTop() + getPaddingBottom();
      m = i - getPaddingLeft() - getPaddingRight();
      n = j - k;
      i1 = View.MeasureSpec.makeMeasureSpec(n, -2147483648);
      if (this.mClose != null)
      {
        int i14 = measureChildView$1bb94239(this.mClose, m, i1);
        ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams)this.mClose.getLayoutParams();
        m = i14 - (localMarginLayoutParams.leftMargin + localMarginLayoutParams.rightMargin);
      }
      if ((this.mMenuView != null) && (this.mMenuView.getParent() == this)) {
        m = measureChildView$1bb94239(this.mMenuView, m, i1);
      }
      if ((this.mTitleLayout != null) && (this.mCustomView == null))
      {
        if (!this.mTitleOptional) {
          break label500;
        }
        int i10 = View.MeasureSpec.makeMeasureSpec(0, 0);
        this.mTitleLayout.measure(i10, i1);
        int i11 = this.mTitleLayout.getMeasuredWidth();
        if (i11 > m) {
          break label487;
        }
        i12 = 1;
        if (i12 != 0) {
          m -= i11;
        }
        LinearLayout localLinearLayout = this.mTitleLayout;
        if (i12 == 0) {
          break label493;
        }
        i13 = 0;
        localLinearLayout.setVisibility(i13);
      }
      if (this.mCustomView != null)
      {
        localLayoutParams = this.mCustomView.getLayoutParams();
        if (localLayoutParams.width == -2) {
          break label516;
        }
        i6 = 1073741824;
        if (localLayoutParams.width < 0) {
          break label524;
        }
        i7 = Math.min(localLayoutParams.width, m);
        if (localLayoutParams.height == -2) {
          break label531;
        }
        i8 = 1073741824;
        label380:
        if (localLayoutParams.height < 0) {
          break label539;
        }
      }
    }
    int i2;
    label516:
    label524:
    label531:
    label539:
    for (int i9 = Math.min(localLayoutParams.height, n);; i9 = n)
    {
      this.mCustomView.measure(View.MeasureSpec.makeMeasureSpec(i7, i6), View.MeasureSpec.makeMeasureSpec(i9, i8));
      if (this.mContentHeight > 0) {
        break label554;
      }
      i2 = 0;
      int i3 = getChildCount();
      for (int i4 = 0; i4 < i3; i4++)
      {
        int i5 = k + getChildAt(i4).getMeasuredHeight();
        if (i5 > i2) {
          i2 = i5;
        }
      }
      j = View.MeasureSpec.getSize(paramInt2);
      break;
      label487:
      i12 = 0;
      break label281;
      label493:
      i13 = 8;
      break label307;
      label500:
      m = measureChildView$1bb94239(this.mTitleLayout, m, i1);
      break label314;
      i6 = -2147483648;
      break label345;
      i7 = m;
      break label365;
      i8 = -2147483648;
      break label380;
    }
    setMeasuredDimension(i, i2);
    return;
    label554:
    setMeasuredDimension(i, j);
  }
  
  public void setContentHeight(int paramInt)
  {
    this.mContentHeight = paramInt;
  }
  
  public void setCustomView(View paramView)
  {
    if (this.mCustomView != null) {
      removeView(this.mCustomView);
    }
    this.mCustomView = paramView;
    if ((paramView != null) && (this.mTitleLayout != null))
    {
      removeView(this.mTitleLayout);
      this.mTitleLayout = null;
    }
    if (paramView != null) {
      addView(paramView);
    }
    requestLayout();
  }
  
  public void setSubtitle(CharSequence paramCharSequence)
  {
    this.mSubtitle = paramCharSequence;
    initTitle();
  }
  
  public void setTitle(CharSequence paramCharSequence)
  {
    this.mTitle = paramCharSequence;
    initTitle();
  }
  
  public void setTitleOptional(boolean paramBoolean)
  {
    if (paramBoolean != this.mTitleOptional) {
      requestLayout();
    }
    this.mTitleOptional = paramBoolean;
  }
  
  public boolean shouldDelayChildPressedState()
  {
    return false;
  }
  
  public final boolean showOverflowMenu()
  {
    if (this.mActionMenuPresenter != null) {
      return this.mActionMenuPresenter.showOverflowMenu();
    }
    return false;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.internal.widget.ActionBarContextView
 * JD-Core Version:    0.7.0.1
 */