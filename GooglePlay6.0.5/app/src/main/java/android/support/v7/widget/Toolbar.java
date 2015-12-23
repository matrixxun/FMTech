package android.support.v7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MarginLayoutParamsCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar.LayoutParams;
import android.support.v7.appcompat.R.attr;
import android.support.v7.appcompat.R.styleable;
import android.support.v7.internal.view.SupportMenuInflater;
import android.support.v7.internal.view.menu.MenuBuilder;
import android.support.v7.internal.view.menu.MenuBuilder.Callback;
import android.support.v7.internal.view.menu.MenuItemImpl;
import android.support.v7.internal.view.menu.MenuPresenter;
import android.support.v7.internal.view.menu.MenuPresenter.Callback;
import android.support.v7.internal.view.menu.SubMenuBuilder;
import android.support.v7.internal.widget.DecorToolbar;
import android.support.v7.internal.widget.RtlSpacingHelper;
import android.support.v7.internal.widget.TintManager;
import android.support.v7.internal.widget.TintTypedArray;
import android.support.v7.internal.widget.ToolbarWidgetWrapper;
import android.support.v7.internal.widget.ViewUtils;
import android.support.v7.view.CollapsibleActionView;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class Toolbar
  extends ViewGroup
{
  public MenuPresenter.Callback mActionMenuPresenterCallback;
  private int mButtonGravity;
  private ImageButton mCollapseButtonView;
  private CharSequence mCollapseDescription;
  private Drawable mCollapseIcon;
  private boolean mCollapsible;
  public final RtlSpacingHelper mContentInsets = new RtlSpacingHelper();
  private boolean mEatingHover;
  private boolean mEatingTouch;
  View mExpandedActionView;
  public ExpandedActionViewMenuPresenter mExpandedMenuPresenter;
  private int mGravity = 8388627;
  final ArrayList<View> mHiddenViews = new ArrayList();
  private ImageView mLogoView;
  private int mMaxButtonHeight;
  public MenuBuilder.Callback mMenuBuilderCallback;
  public ActionMenuView mMenuView;
  private final ActionMenuView.OnMenuItemClickListener mMenuViewItemClickListener;
  private ImageButton mNavButtonView;
  private OnMenuItemClickListener mOnMenuItemClickListener;
  public ActionMenuPresenter mOuterActionMenuPresenter;
  public Context mPopupContext;
  public int mPopupTheme;
  private final Runnable mShowOverflowMenuRunnable;
  private CharSequence mSubtitleText;
  public int mSubtitleTextAppearance;
  private int mSubtitleTextColor;
  public TextView mSubtitleTextView;
  private final int[] mTempMargins = new int[2];
  private final ArrayList<View> mTempViews = new ArrayList();
  private final TintManager mTintManager;
  private int mTitleMarginBottom;
  private int mTitleMarginEnd;
  private int mTitleMarginStart;
  private int mTitleMarginTop;
  private CharSequence mTitleText;
  public int mTitleTextAppearance;
  private int mTitleTextColor;
  public TextView mTitleTextView;
  private ToolbarWidgetWrapper mWrapper;
  
  public Toolbar(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public Toolbar(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.toolbarStyle);
  }
  
  public Toolbar(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    ActionMenuView.OnMenuItemClickListener local1 = new ActionMenuView.OnMenuItemClickListener()
    {
      public final boolean onMenuItemClick(MenuItem paramAnonymousMenuItem)
      {
        if (Toolbar.this.mOnMenuItemClickListener != null) {
          return Toolbar.this.mOnMenuItemClickListener.onMenuItemClick(paramAnonymousMenuItem);
        }
        return false;
      }
    };
    this.mMenuViewItemClickListener = local1;
    Runnable local2 = new Runnable()
    {
      public final void run()
      {
        Toolbar.this.showOverflowMenu();
      }
    };
    this.mShowOverflowMenuRunnable = local2;
    TintTypedArray localTintTypedArray = TintTypedArray.obtainStyledAttributes$1a6c1917(getContext(), paramAttributeSet, R.styleable.Toolbar, paramInt);
    this.mTitleTextAppearance = localTintTypedArray.getResourceId(R.styleable.Toolbar_titleTextAppearance, 0);
    this.mSubtitleTextAppearance = localTintTypedArray.getResourceId(R.styleable.Toolbar_subtitleTextAppearance, 0);
    int i = R.styleable.Toolbar_android_gravity;
    int j = this.mGravity;
    this.mGravity = localTintTypedArray.mWrapped.getInteger(i, j);
    this.mButtonGravity = 48;
    int k = localTintTypedArray.getDimensionPixelOffset(R.styleable.Toolbar_titleMargins, 0);
    this.mTitleMarginBottom = k;
    this.mTitleMarginTop = k;
    this.mTitleMarginEnd = k;
    this.mTitleMarginStart = k;
    int m = localTintTypedArray.getDimensionPixelOffset(R.styleable.Toolbar_titleMarginStart, -1);
    if (m >= 0) {
      this.mTitleMarginStart = m;
    }
    int n = localTintTypedArray.getDimensionPixelOffset(R.styleable.Toolbar_titleMarginEnd, -1);
    if (n >= 0) {
      this.mTitleMarginEnd = n;
    }
    int i1 = localTintTypedArray.getDimensionPixelOffset(R.styleable.Toolbar_titleMarginTop, -1);
    if (i1 >= 0) {
      this.mTitleMarginTop = i1;
    }
    int i2 = localTintTypedArray.getDimensionPixelOffset(R.styleable.Toolbar_titleMarginBottom, -1);
    if (i2 >= 0) {
      this.mTitleMarginBottom = i2;
    }
    this.mMaxButtonHeight = localTintTypedArray.getDimensionPixelSize(R.styleable.Toolbar_maxButtonHeight, -1);
    int i3 = localTintTypedArray.getDimensionPixelOffset(R.styleable.Toolbar_contentInsetStart, -2147483648);
    int i4 = localTintTypedArray.getDimensionPixelOffset(R.styleable.Toolbar_contentInsetEnd, -2147483648);
    int i5 = localTintTypedArray.getDimensionPixelSize(R.styleable.Toolbar_contentInsetLeft, 0);
    int i6 = localTintTypedArray.getDimensionPixelSize(R.styleable.Toolbar_contentInsetRight, 0);
    RtlSpacingHelper localRtlSpacingHelper = this.mContentInsets;
    localRtlSpacingHelper.mIsRelative = false;
    if (i5 != -2147483648)
    {
      localRtlSpacingHelper.mExplicitLeft = i5;
      localRtlSpacingHelper.mLeft = i5;
    }
    if (i6 != -2147483648)
    {
      localRtlSpacingHelper.mExplicitRight = i6;
      localRtlSpacingHelper.mRight = i6;
    }
    if ((i3 != -2147483648) || (i4 != -2147483648)) {
      this.mContentInsets.setRelative(i3, i4);
    }
    this.mCollapseIcon = localTintTypedArray.getDrawable(R.styleable.Toolbar_collapseIcon);
    this.mCollapseDescription = localTintTypedArray.getText(R.styleable.Toolbar_collapseContentDescription);
    CharSequence localCharSequence1 = localTintTypedArray.getText(R.styleable.Toolbar_title);
    if (!TextUtils.isEmpty(localCharSequence1)) {
      setTitle(localCharSequence1);
    }
    CharSequence localCharSequence2 = localTintTypedArray.getText(R.styleable.Toolbar_subtitle);
    if (!TextUtils.isEmpty(localCharSequence2)) {
      setSubtitle(localCharSequence2);
    }
    this.mPopupContext = getContext();
    setPopupTheme(localTintTypedArray.getResourceId(R.styleable.Toolbar_popupTheme, 0));
    Drawable localDrawable1 = localTintTypedArray.getDrawable(R.styleable.Toolbar_navigationIcon);
    if (localDrawable1 != null) {
      setNavigationIcon(localDrawable1);
    }
    CharSequence localCharSequence3 = localTintTypedArray.getText(R.styleable.Toolbar_navigationContentDescription);
    if (!TextUtils.isEmpty(localCharSequence3)) {
      setNavigationContentDescription(localCharSequence3);
    }
    Drawable localDrawable2 = localTintTypedArray.getDrawable(R.styleable.Toolbar_logo);
    if (localDrawable2 != null) {
      setLogo(localDrawable2);
    }
    CharSequence localCharSequence4 = localTintTypedArray.getText(R.styleable.Toolbar_logoDescription);
    if (!TextUtils.isEmpty(localCharSequence4)) {
      setLogoDescription(localCharSequence4);
    }
    if (localTintTypedArray.hasValue(R.styleable.Toolbar_titleTextColor)) {
      setTitleTextColor(localTintTypedArray.getColor$255f288(R.styleable.Toolbar_titleTextColor));
    }
    if (localTintTypedArray.hasValue(R.styleable.Toolbar_subtitleTextColor)) {
      setSubtitleTextColor(localTintTypedArray.getColor$255f288(R.styleable.Toolbar_subtitleTextColor));
    }
    localTintTypedArray.mWrapped.recycle();
    this.mTintManager = localTintTypedArray.getTintManager();
  }
  
  private void addCustomViewsWithGravity(List<View> paramList, int paramInt)
  {
    int i = 1;
    if (ViewCompat.getLayoutDirection(this) == i) {}
    int j;
    int k;
    for (;;)
    {
      j = getChildCount();
      k = GravityCompat.getAbsoluteGravity(paramInt, ViewCompat.getLayoutDirection(this));
      paramList.clear();
      if (i == 0) {
        break;
      }
      for (int n = j - 1; n >= 0; n--)
      {
        View localView2 = getChildAt(n);
        LayoutParams localLayoutParams2 = (LayoutParams)localView2.getLayoutParams();
        if ((localLayoutParams2.mViewType == 0) && (shouldLayout(localView2)) && (getChildHorizontalGravity(localLayoutParams2.gravity) == k)) {
          paramList.add(localView2);
        }
      }
      i = 0;
    }
    for (int m = 0; m < j; m++)
    {
      View localView1 = getChildAt(m);
      LayoutParams localLayoutParams1 = (LayoutParams)localView1.getLayoutParams();
      if ((localLayoutParams1.mViewType == 0) && (shouldLayout(localView1)) && (getChildHorizontalGravity(localLayoutParams1.gravity) == k)) {
        paramList.add(localView1);
      }
    }
  }
  
  private void addSystemView(View paramView, boolean paramBoolean)
  {
    ViewGroup.LayoutParams localLayoutParams = paramView.getLayoutParams();
    LayoutParams localLayoutParams1;
    if (localLayoutParams == null) {
      localLayoutParams1 = new LayoutParams();
    }
    for (;;)
    {
      localLayoutParams1.mViewType = 1;
      if ((!paramBoolean) || (this.mExpandedActionView == null)) {
        break;
      }
      paramView.setLayoutParams(localLayoutParams1);
      this.mHiddenViews.add(paramView);
      return;
      if (!checkLayoutParams(localLayoutParams)) {
        localLayoutParams1 = generateLayoutParams(localLayoutParams);
      } else {
        localLayoutParams1 = (LayoutParams)localLayoutParams;
      }
    }
    addView(paramView, localLayoutParams1);
  }
  
  private void ensureLogoView()
  {
    if (this.mLogoView == null) {
      this.mLogoView = new ImageView(getContext());
    }
  }
  
  private void ensureMenu()
  {
    ensureMenuView();
    if (this.mMenuView.mMenu == null)
    {
      MenuBuilder localMenuBuilder = (MenuBuilder)this.mMenuView.getMenu();
      if (this.mExpandedMenuPresenter == null) {
        this.mExpandedMenuPresenter = new ExpandedActionViewMenuPresenter((byte)0);
      }
      this.mMenuView.setExpandedActionViewsExclusive(true);
      localMenuBuilder.addMenuPresenter(this.mExpandedMenuPresenter, this.mPopupContext);
    }
  }
  
  private void ensureNavButtonView()
  {
    if (this.mNavButtonView == null)
    {
      this.mNavButtonView = new ImageButton(getContext(), null, R.attr.toolbarNavigationButtonStyle);
      LayoutParams localLayoutParams = new LayoutParams();
      localLayoutParams.gravity = (0x800003 | 0x70 & this.mButtonGravity);
      this.mNavButtonView.setLayoutParams(localLayoutParams);
    }
  }
  
  protected static LayoutParams generateDefaultLayoutParams()
  {
    return new LayoutParams();
  }
  
  private static LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    if ((paramLayoutParams instanceof LayoutParams)) {
      return new LayoutParams((LayoutParams)paramLayoutParams);
    }
    if ((paramLayoutParams instanceof ActionBar.LayoutParams)) {
      return new LayoutParams((ActionBar.LayoutParams)paramLayoutParams);
    }
    if ((paramLayoutParams instanceof ViewGroup.MarginLayoutParams)) {
      return new LayoutParams((ViewGroup.MarginLayoutParams)paramLayoutParams);
    }
    return new LayoutParams(paramLayoutParams);
  }
  
  private int getChildHorizontalGravity(int paramInt)
  {
    int i = ViewCompat.getLayoutDirection(this);
    int j = 0x7 & GravityCompat.getAbsoluteGravity(paramInt, i);
    switch (j)
    {
    case 2: 
    case 4: 
    default: 
      if (i == 1) {
        j = 5;
      }
      break;
    case 1: 
    case 3: 
    case 5: 
      return j;
    }
    return 3;
  }
  
  private int getChildTop(View paramView, int paramInt)
  {
    LayoutParams localLayoutParams = (LayoutParams)paramView.getLayoutParams();
    int i = paramView.getMeasuredHeight();
    int j;
    int m;
    int n;
    int i1;
    int i2;
    if (paramInt > 0)
    {
      j = (i - paramInt) / 2;
      int k = 0x70 & localLayoutParams.gravity;
      switch (k)
      {
      default: 
        k = 0x70 & this.mGravity;
      }
      switch (k)
      {
      default: 
        m = getPaddingTop();
        n = getPaddingBottom();
        i1 = getHeight();
        i2 = (i1 - m - n - i) / 2;
        if (i2 < localLayoutParams.topMargin) {
          i2 = localLayoutParams.topMargin;
        }
        break;
      }
    }
    for (;;)
    {
      return m + i2;
      j = 0;
      break;
      return getPaddingTop() - j;
      return getHeight() - getPaddingBottom() - i - localLayoutParams.bottomMargin - j;
      int i3 = i1 - n - i - i2 - m;
      if (i3 < localLayoutParams.bottomMargin) {
        i2 = Math.max(0, i2 - (localLayoutParams.bottomMargin - i3));
      }
    }
  }
  
  private static int getHorizontalMargins(View paramView)
  {
    ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams)paramView.getLayoutParams();
    return MarginLayoutParamsCompat.getMarginStart(localMarginLayoutParams) + MarginLayoutParamsCompat.getMarginEnd(localMarginLayoutParams);
  }
  
  private MenuInflater getMenuInflater()
  {
    return new SupportMenuInflater(getContext());
  }
  
  private static int getVerticalMargins(View paramView)
  {
    ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams)paramView.getLayoutParams();
    return localMarginLayoutParams.topMargin + localMarginLayoutParams.bottomMargin;
  }
  
  private boolean isChildOrHidden(View paramView)
  {
    return (paramView.getParent() == this) || (this.mHiddenViews.contains(paramView));
  }
  
  private int layoutChildLeft(View paramView, int paramInt1, int[] paramArrayOfInt, int paramInt2)
  {
    LayoutParams localLayoutParams = (LayoutParams)paramView.getLayoutParams();
    int i = localLayoutParams.leftMargin - paramArrayOfInt[0];
    int j = paramInt1 + Math.max(0, i);
    paramArrayOfInt[0] = Math.max(0, -i);
    int k = getChildTop(paramView, paramInt2);
    int m = paramView.getMeasuredWidth();
    paramView.layout(j, k, j + m, k + paramView.getMeasuredHeight());
    return j + (m + localLayoutParams.rightMargin);
  }
  
  private int layoutChildRight(View paramView, int paramInt1, int[] paramArrayOfInt, int paramInt2)
  {
    LayoutParams localLayoutParams = (LayoutParams)paramView.getLayoutParams();
    int i = localLayoutParams.rightMargin - paramArrayOfInt[1];
    int j = paramInt1 - Math.max(0, i);
    paramArrayOfInt[1] = Math.max(0, -i);
    int k = getChildTop(paramView, paramInt2);
    int m = paramView.getMeasuredWidth();
    paramView.layout(j - m, k, j, k + paramView.getMeasuredHeight());
    return j - (m + localLayoutParams.leftMargin);
  }
  
  private int measureChildCollapseMargins(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfInt)
  {
    ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams)paramView.getLayoutParams();
    int i = localMarginLayoutParams.leftMargin - paramArrayOfInt[0];
    int j = localMarginLayoutParams.rightMargin - paramArrayOfInt[1];
    int k = Math.max(0, i) + Math.max(0, j);
    paramArrayOfInt[0] = Math.max(0, -i);
    paramArrayOfInt[1] = Math.max(0, -j);
    paramView.measure(getChildMeasureSpec(paramInt1, paramInt2 + (k + (getPaddingLeft() + getPaddingRight())), localMarginLayoutParams.width), getChildMeasureSpec(paramInt3, paramInt4 + (getPaddingTop() + getPaddingBottom() + localMarginLayoutParams.topMargin + localMarginLayoutParams.bottomMargin), localMarginLayoutParams.height));
    return k + paramView.getMeasuredWidth();
  }
  
  private void measureChildConstrained$12802926(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams)paramView.getLayoutParams();
    int i = getChildMeasureSpec(paramInt1, paramInt2 + (getPaddingLeft() + getPaddingRight() + localMarginLayoutParams.leftMargin + localMarginLayoutParams.rightMargin), localMarginLayoutParams.width);
    int j = getChildMeasureSpec(paramInt3, 0 + (getPaddingTop() + getPaddingBottom() + localMarginLayoutParams.topMargin + localMarginLayoutParams.bottomMargin), localMarginLayoutParams.height);
    int k = View.MeasureSpec.getMode(j);
    if ((k != 1073741824) && (paramInt4 >= 0))
    {
      if (k != 0) {
        paramInt4 = Math.min(View.MeasureSpec.getSize(j), paramInt4);
      }
      j = View.MeasureSpec.makeMeasureSpec(paramInt4, 1073741824);
    }
    paramView.measure(i, j);
  }
  
  private boolean shouldLayout(View paramView)
  {
    return (paramView != null) && (paramView.getParent() == this) && (paramView.getVisibility() != 8);
  }
  
  protected boolean checkLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    return (super.checkLayoutParams(paramLayoutParams)) && ((paramLayoutParams instanceof LayoutParams));
  }
  
  public final void collapseActionView()
  {
    if (this.mExpandedMenuPresenter == null) {}
    for (MenuItemImpl localMenuItemImpl = null;; localMenuItemImpl = this.mExpandedMenuPresenter.mCurrentExpandedItem)
    {
      if (localMenuItemImpl != null) {
        localMenuItemImpl.collapseActionView();
      }
      return;
    }
  }
  
  public final void ensureMenuView()
  {
    if (this.mMenuView == null)
    {
      this.mMenuView = new ActionMenuView(getContext());
      this.mMenuView.setPopupTheme(this.mPopupTheme);
      this.mMenuView.setOnMenuItemClickListener(this.mMenuViewItemClickListener);
      ActionMenuView localActionMenuView = this.mMenuView;
      MenuPresenter.Callback localCallback = this.mActionMenuPresenterCallback;
      MenuBuilder.Callback localCallback1 = this.mMenuBuilderCallback;
      localActionMenuView.mActionMenuPresenterCallback = localCallback;
      localActionMenuView.mMenuBuilderCallback = localCallback1;
      LayoutParams localLayoutParams = new LayoutParams();
      localLayoutParams.gravity = (0x800005 | 0x70 & this.mButtonGravity);
      this.mMenuView.setLayoutParams(localLayoutParams);
      addSystemView(this.mMenuView, false);
    }
  }
  
  public int getContentInsetEnd()
  {
    RtlSpacingHelper localRtlSpacingHelper = this.mContentInsets;
    if (localRtlSpacingHelper.mIsRtl) {
      return localRtlSpacingHelper.mLeft;
    }
    return localRtlSpacingHelper.mRight;
  }
  
  public int getContentInsetLeft()
  {
    return this.mContentInsets.mLeft;
  }
  
  public int getContentInsetRight()
  {
    return this.mContentInsets.mRight;
  }
  
  public int getContentInsetStart()
  {
    RtlSpacingHelper localRtlSpacingHelper = this.mContentInsets;
    if (localRtlSpacingHelper.mIsRtl) {
      return localRtlSpacingHelper.mRight;
    }
    return localRtlSpacingHelper.mLeft;
  }
  
  public Drawable getLogo()
  {
    if (this.mLogoView != null) {
      return this.mLogoView.getDrawable();
    }
    return null;
  }
  
  public CharSequence getLogoDescription()
  {
    if (this.mLogoView != null) {
      return this.mLogoView.getContentDescription();
    }
    return null;
  }
  
  public Menu getMenu()
  {
    ensureMenu();
    return this.mMenuView.getMenu();
  }
  
  public CharSequence getNavigationContentDescription()
  {
    if (this.mNavButtonView != null) {
      return this.mNavButtonView.getContentDescription();
    }
    return null;
  }
  
  public Drawable getNavigationIcon()
  {
    if (this.mNavButtonView != null) {
      return this.mNavButtonView.getDrawable();
    }
    return null;
  }
  
  public Drawable getOverflowIcon()
  {
    ensureMenu();
    return this.mMenuView.getOverflowIcon();
  }
  
  public int getPopupTheme()
  {
    return this.mPopupTheme;
  }
  
  public CharSequence getSubtitle()
  {
    return this.mSubtitleText;
  }
  
  public CharSequence getTitle()
  {
    return this.mTitleText;
  }
  
  public DecorToolbar getWrapper()
  {
    if (this.mWrapper == null) {
      this.mWrapper = new ToolbarWidgetWrapper(this, true);
    }
    return this.mWrapper;
  }
  
  public final boolean hasExpandedActionView()
  {
    return (this.mExpandedMenuPresenter != null) && (this.mExpandedMenuPresenter.mCurrentExpandedItem != null);
  }
  
  public final boolean isOverflowMenuShowing()
  {
    if (this.mMenuView != null)
    {
      ActionMenuView localActionMenuView = this.mMenuView;
      if ((localActionMenuView.mPresenter != null) && (localActionMenuView.mPresenter.isOverflowMenuShowing())) {}
      for (int i = 1; i != 0; i = 0) {
        return true;
      }
    }
    return false;
  }
  
  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    removeCallbacks(this.mShowOverflowMenuRunnable);
  }
  
  public boolean onHoverEvent(MotionEvent paramMotionEvent)
  {
    int i = MotionEventCompat.getActionMasked(paramMotionEvent);
    if (i == 9) {
      this.mEatingHover = false;
    }
    if (!this.mEatingHover)
    {
      boolean bool = super.onHoverEvent(paramMotionEvent);
      if ((i == 9) && (!bool)) {
        this.mEatingHover = true;
      }
    }
    if ((i == 10) || (i == 3)) {
      this.mEatingHover = false;
    }
    return true;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i;
    int j;
    int k;
    int m;
    int n;
    int i1;
    int i2;
    int i3;
    int i4;
    int[] arrayOfInt;
    int i5;
    label112:
    int i7;
    label144:
    label176:
    int i9;
    label280:
    label312:
    boolean bool1;
    boolean bool2;
    int i10;
    TextView localTextView1;
    label437:
    TextView localTextView2;
    label448:
    LayoutParams localLayoutParams1;
    LayoutParams localLayoutParams2;
    int i11;
    label501:
    int i52;
    label582:
    int i12;
    if (ViewCompat.getLayoutDirection(this) == 1)
    {
      i = 1;
      j = getWidth();
      k = getHeight();
      m = getPaddingLeft();
      n = getPaddingRight();
      i1 = getPaddingTop();
      i2 = getPaddingBottom();
      i3 = m;
      i4 = j - n;
      arrayOfInt = this.mTempMargins;
      arrayOfInt[1] = 0;
      arrayOfInt[0] = 0;
      i5 = ViewCompat.getMinimumHeight(this);
      if (shouldLayout(this.mNavButtonView))
      {
        if (i == 0) {
          break label878;
        }
        i4 = layoutChildRight(this.mNavButtonView, i4, arrayOfInt, i5);
      }
      if (shouldLayout(this.mCollapseButtonView))
      {
        if (i == 0) {
          break label897;
        }
        i4 = layoutChildRight(this.mCollapseButtonView, i4, arrayOfInt, i5);
      }
      if (shouldLayout(this.mMenuView))
      {
        if (i == 0) {
          break label916;
        }
        i3 = layoutChildLeft(this.mMenuView, i3, arrayOfInt, i5);
      }
      arrayOfInt[0] = Math.max(0, getContentInsetLeft() - i3);
      arrayOfInt[1] = Math.max(0, getContentInsetRight() - (j - n - i4));
      int i6 = getContentInsetLeft();
      i7 = Math.max(i3, i6);
      int i8 = j - n - getContentInsetRight();
      i9 = Math.min(i4, i8);
      if (shouldLayout(this.mExpandedActionView))
      {
        if (i == 0) {
          break label935;
        }
        i9 = layoutChildRight(this.mExpandedActionView, i9, arrayOfInt, i5);
      }
      if (shouldLayout(this.mLogoView))
      {
        if (i == 0) {
          break label954;
        }
        i9 = layoutChildRight(this.mLogoView, i9, arrayOfInt, i5);
      }
      bool1 = shouldLayout(this.mTitleTextView);
      bool2 = shouldLayout(this.mSubtitleTextView);
      i10 = 0;
      if (bool1)
      {
        LayoutParams localLayoutParams9 = (LayoutParams)this.mTitleTextView.getLayoutParams();
        i10 = 0 + (localLayoutParams9.topMargin + this.mTitleTextView.getMeasuredHeight() + localLayoutParams9.bottomMargin);
      }
      if (bool2)
      {
        LayoutParams localLayoutParams8 = (LayoutParams)this.mSubtitleTextView.getLayoutParams();
        i10 += localLayoutParams8.topMargin + this.mSubtitleTextView.getMeasuredHeight() + localLayoutParams8.bottomMargin;
      }
      if ((bool1) || (bool2))
      {
        if (!bool1) {
          break label973;
        }
        localTextView1 = this.mTitleTextView;
        if (!bool2) {
          break label982;
        }
        localTextView2 = this.mSubtitleTextView;
        localLayoutParams1 = (LayoutParams)localTextView1.getLayoutParams();
        localLayoutParams2 = (LayoutParams)localTextView2.getLayoutParams();
        if (((!bool1) || (this.mTitleTextView.getMeasuredWidth() <= 0)) && ((!bool2) || (this.mSubtitleTextView.getMeasuredWidth() <= 0))) {
          break label991;
        }
        i11 = 1;
        switch (0x70 & this.mGravity)
        {
        default: 
          i52 = (k - i1 - i2 - i10) / 2;
          int i53 = localLayoutParams1.topMargin + this.mTitleMarginTop;
          if (i52 < i53)
          {
            i52 = localLayoutParams1.topMargin + this.mTitleMarginTop;
            i12 = i1 + i52;
            label589:
            if (i == 0) {
              break label1103;
            }
            if (i11 == 0) {
              break label1097;
            }
          }
          break;
        }
      }
    }
    label897:
    label916:
    label935:
    label954:
    label1097:
    for (int i43 = this.mTitleMarginStart;; i43 = 0)
    {
      int i44 = i43 - arrayOfInt[1];
      i9 -= Math.max(0, i44);
      arrayOfInt[1] = Math.max(0, -i44);
      int i45 = i9;
      int i46 = i9;
      if (bool1)
      {
        LayoutParams localLayoutParams7 = (LayoutParams)this.mTitleTextView.getLayoutParams();
        int i50 = i45 - this.mTitleTextView.getMeasuredWidth();
        int i51 = i12 + this.mTitleTextView.getMeasuredHeight();
        this.mTitleTextView.layout(i50, i12, i45, i51);
        i45 = i50 - this.mTitleMarginEnd;
        i12 = i51 + localLayoutParams7.bottomMargin;
      }
      LayoutParams localLayoutParams6;
      if (bool2)
      {
        localLayoutParams6 = (LayoutParams)this.mSubtitleTextView.getLayoutParams();
        int i47 = i12 + localLayoutParams6.topMargin;
        int i48 = i46 - this.mSubtitleTextView.getMeasuredWidth();
        int i49 = i47 + this.mSubtitleTextView.getMeasuredHeight();
        this.mSubtitleTextView.layout(i48, i47, i46, i49);
        i46 -= this.mTitleMarginEnd;
      }
      if (i11 != 0) {
        i9 = Math.min(i45, i46);
      }
      addCustomViewsWithGravity(this.mTempViews, 3);
      int i17 = this.mTempViews.size();
      for (int i18 = 0; i18 < i17; i18++) {
        i7 = layoutChildLeft((View)this.mTempViews.get(i18), i7, arrayOfInt, i5);
      }
      i = 0;
      break;
      label878:
      i3 = layoutChildLeft(this.mNavButtonView, m, arrayOfInt, i5);
      break label112;
      i3 = layoutChildLeft(this.mCollapseButtonView, i3, arrayOfInt, i5);
      break label144;
      i4 = layoutChildRight(this.mMenuView, i4, arrayOfInt, i5);
      break label176;
      i7 = layoutChildLeft(this.mExpandedActionView, i7, arrayOfInt, i5);
      break label280;
      i7 = layoutChildLeft(this.mLogoView, i7, arrayOfInt, i5);
      break label312;
      label973:
      localTextView1 = this.mSubtitleTextView;
      break label437;
      label982:
      localTextView2 = this.mTitleTextView;
      break label448;
      label991:
      i11 = 0;
      break label501;
      i12 = getPaddingTop() + localLayoutParams1.topMargin + this.mTitleMarginTop;
      break label589;
      int i54 = k - i2 - i10 - i52 - i1;
      if (i54 >= localLayoutParams1.bottomMargin + this.mTitleMarginBottom) {
        break label582;
      }
      i52 = Math.max(0, i52 - (localLayoutParams2.bottomMargin + this.mTitleMarginBottom - i54));
      break label582;
      i12 = k - i2 - localLayoutParams2.bottomMargin - this.mTitleMarginBottom - i10;
      break label589;
    }
    label1103:
    if (i11 != 0) {}
    for (int i13 = this.mTitleMarginStart;; i13 = 0)
    {
      int i14 = i13 - arrayOfInt[0];
      i7 += Math.max(0, i14);
      arrayOfInt[0] = Math.max(0, -i14);
      int i15 = i7;
      int i16 = i7;
      if (bool1)
      {
        LayoutParams localLayoutParams5 = (LayoutParams)this.mTitleTextView.getLayoutParams();
        int i41 = i15 + this.mTitleTextView.getMeasuredWidth();
        int i42 = i12 + this.mTitleTextView.getMeasuredHeight();
        this.mTitleTextView.layout(i15, i12, i41, i42);
        i15 = i41 + this.mTitleMarginEnd;
        i12 = i42 + localLayoutParams5.bottomMargin;
      }
      LayoutParams localLayoutParams4;
      if (bool2)
      {
        localLayoutParams4 = (LayoutParams)this.mSubtitleTextView.getLayoutParams();
        int i38 = i12 + localLayoutParams4.topMargin;
        int i39 = i16 + this.mSubtitleTextView.getMeasuredWidth();
        int i40 = i38 + this.mSubtitleTextView.getMeasuredHeight();
        this.mSubtitleTextView.layout(i16, i38, i39, i40);
        i16 = i39 + this.mTitleMarginEnd;
      }
      if (i11 == 0) {
        break;
      }
      i7 = Math.max(i15, i16);
      break;
    }
    addCustomViewsWithGravity(this.mTempViews, 5);
    int i19 = this.mTempViews.size();
    for (int i20 = 0; i20 < i19; i20++) {
      i9 = layoutChildRight((View)this.mTempViews.get(i20), i9, arrayOfInt, i5);
    }
    addCustomViewsWithGravity(this.mTempViews, 1);
    ArrayList localArrayList = this.mTempViews;
    int i21 = arrayOfInt[0];
    int i22 = arrayOfInt[1];
    int i23 = 0;
    int i24 = localArrayList.size();
    int i25 = i22;
    int i26 = i21;
    int i27 = 0;
    while (i27 < i24)
    {
      View localView = (View)localArrayList.get(i27);
      LayoutParams localLayoutParams3 = (LayoutParams)localView.getLayoutParams();
      int i32 = localLayoutParams3.leftMargin - i26;
      int i33 = localLayoutParams3.rightMargin - i25;
      int i34 = Math.max(0, i32);
      int i35 = Math.max(0, i33);
      int i36 = Math.max(0, -i32);
      int i37 = Math.max(0, -i33);
      i23 += i35 + (i34 + localView.getMeasuredWidth());
      i27++;
      i26 = i36;
      i25 = i37;
    }
    int i28 = m + (j - m - n) / 2 - i23 / 2;
    int i29 = i28 + i23;
    if (i28 < i7) {
      i28 = i7;
    }
    for (;;)
    {
      int i30 = this.mTempViews.size();
      for (int i31 = 0; i31 < i30; i31++) {
        i28 = layoutChildLeft((View)this.mTempViews.get(i31), i28, arrayOfInt, i5);
      }
      if (i29 > i9) {
        i28 -= i29 - i9;
      }
    }
    this.mTempViews.clear();
  }
  
  public void onMeasure(int paramInt1, int paramInt2)
  {
    int[] arrayOfInt = this.mTempMargins;
    int j;
    int i;
    if (ViewUtils.isLayoutRtl(this))
    {
      j = 1;
      i = 0;
    }
    int k;
    int m;
    int i5;
    for (;;)
    {
      boolean bool1 = shouldLayout(this.mNavButtonView);
      k = 0;
      m = 0;
      int n = 0;
      if (bool1)
      {
        measureChildConstrained$12802926(this.mNavButtonView, paramInt1, 0, paramInt2, this.mMaxButtonHeight);
        n = this.mNavButtonView.getMeasuredWidth() + getHorizontalMargins(this.mNavButtonView);
        m = Math.max(0, this.mNavButtonView.getMeasuredHeight() + getVerticalMargins(this.mNavButtonView));
        k = ViewUtils.combineMeasuredStates(0, ViewCompat.getMeasuredState(this.mNavButtonView));
      }
      if (shouldLayout(this.mCollapseButtonView))
      {
        measureChildConstrained$12802926(this.mCollapseButtonView, paramInt1, 0, paramInt2, this.mMaxButtonHeight);
        n = this.mCollapseButtonView.getMeasuredWidth() + getHorizontalMargins(this.mCollapseButtonView);
        int i32 = this.mCollapseButtonView.getMeasuredHeight() + getVerticalMargins(this.mCollapseButtonView);
        m = Math.max(m, i32);
        int i33 = ViewCompat.getMeasuredState(this.mCollapseButtonView);
        k = ViewUtils.combineMeasuredStates(k, i33);
      }
      int i1 = getContentInsetStart();
      int i2 = 0 + Math.max(i1, n);
      arrayOfInt[j] = Math.max(0, i1 - n);
      boolean bool2 = shouldLayout(this.mMenuView);
      int i3 = 0;
      if (bool2)
      {
        measureChildConstrained$12802926(this.mMenuView, paramInt1, i2, paramInt2, this.mMaxButtonHeight);
        i3 = this.mMenuView.getMeasuredWidth() + getHorizontalMargins(this.mMenuView);
        int i30 = this.mMenuView.getMeasuredHeight() + getVerticalMargins(this.mMenuView);
        m = Math.max(m, i30);
        int i31 = ViewCompat.getMeasuredState(this.mMenuView);
        k = ViewUtils.combineMeasuredStates(k, i31);
      }
      int i4 = getContentInsetEnd();
      i5 = i2 + Math.max(i4, i3);
      arrayOfInt[i] = Math.max(0, i4 - i3);
      if (shouldLayout(this.mExpandedActionView))
      {
        i5 += measureChildCollapseMargins(this.mExpandedActionView, paramInt1, i5, paramInt2, 0, arrayOfInt);
        int i28 = this.mExpandedActionView.getMeasuredHeight() + getVerticalMargins(this.mExpandedActionView);
        m = Math.max(m, i28);
        int i29 = ViewCompat.getMeasuredState(this.mExpandedActionView);
        k = ViewUtils.combineMeasuredStates(k, i29);
      }
      if (shouldLayout(this.mLogoView))
      {
        i5 += measureChildCollapseMargins(this.mLogoView, paramInt1, i5, paramInt2, 0, arrayOfInt);
        int i26 = this.mLogoView.getMeasuredHeight() + getVerticalMargins(this.mLogoView);
        m = Math.max(m, i26);
        int i27 = ViewCompat.getMeasuredState(this.mLogoView);
        k = ViewUtils.combineMeasuredStates(k, i27);
      }
      int i6 = getChildCount();
      for (int i7 = 0; i7 < i6; i7++)
      {
        View localView2 = getChildAt(i7);
        if ((((LayoutParams)localView2.getLayoutParams()).mViewType == 0) && (shouldLayout(localView2)))
        {
          i5 += measureChildCollapseMargins(localView2, paramInt1, i5, paramInt2, 0, arrayOfInt);
          int i24 = localView2.getMeasuredHeight() + getVerticalMargins(localView2);
          m = Math.max(m, i24);
          int i25 = ViewCompat.getMeasuredState(localView2);
          k = ViewUtils.combineMeasuredStates(k, i25);
        }
      }
      i = 1;
      j = 0;
    }
    int i8 = this.mTitleMarginTop + this.mTitleMarginBottom;
    int i9 = this.mTitleMarginStart + this.mTitleMarginEnd;
    boolean bool3 = shouldLayout(this.mTitleTextView);
    int i10 = 0;
    int i11 = 0;
    if (bool3)
    {
      measureChildCollapseMargins(this.mTitleTextView, paramInt1, i5 + i9, paramInt2, i8, arrayOfInt);
      i11 = this.mTitleTextView.getMeasuredWidth() + getHorizontalMargins(this.mTitleTextView);
      i10 = this.mTitleTextView.getMeasuredHeight() + getVerticalMargins(this.mTitleTextView);
      int i23 = ViewCompat.getMeasuredState(this.mTitleTextView);
      k = ViewUtils.combineMeasuredStates(k, i23);
    }
    if (shouldLayout(this.mSubtitleTextView))
    {
      int i21 = measureChildCollapseMargins(this.mSubtitleTextView, paramInt1, i5 + i9, paramInt2, i10 + i8, arrayOfInt);
      i11 = Math.max(i11, i21);
      i10 += this.mSubtitleTextView.getMeasuredHeight() + getVerticalMargins(this.mSubtitleTextView);
      int i22 = ViewCompat.getMeasuredState(this.mSubtitleTextView);
      k = ViewUtils.combineMeasuredStates(k, i22);
    }
    int i12 = i5 + i11;
    int i13 = Math.max(m, i10);
    int i14 = i12 + (getPaddingLeft() + getPaddingRight());
    int i15 = i13 + (getPaddingTop() + getPaddingBottom());
    int i16 = ViewCompat.resolveSizeAndState(Math.max(i14, getSuggestedMinimumWidth()), paramInt1, 0xFF000000 & k);
    int i17 = ViewCompat.resolveSizeAndState(Math.max(i15, getSuggestedMinimumHeight()), paramInt2, k << 16);
    int i20;
    if (!this.mCollapsible) {
      i20 = 0;
    }
    for (;;)
    {
      if (i20 != 0) {
        i17 = 0;
      }
      setMeasuredDimension(i16, i17);
      return;
      int i18 = getChildCount();
      for (int i19 = 0;; i19++)
      {
        if (i19 >= i18) {
          break label984;
        }
        View localView1 = getChildAt(i19);
        if ((shouldLayout(localView1)) && (localView1.getMeasuredWidth() > 0) && (localView1.getMeasuredHeight() > 0))
        {
          i20 = 0;
          break;
        }
      }
      label984:
      i20 = 1;
    }
  }
  
  public void onRestoreInstanceState(Parcelable paramParcelable)
  {
    SavedState localSavedState = (SavedState)paramParcelable;
    super.onRestoreInstanceState(localSavedState.getSuperState());
    if (this.mMenuView != null) {}
    for (MenuBuilder localMenuBuilder = this.mMenuView.mMenu;; localMenuBuilder = null)
    {
      if ((localSavedState.expandedMenuItemId != 0) && (this.mExpandedMenuPresenter != null) && (localMenuBuilder != null))
      {
        MenuItem localMenuItem = localMenuBuilder.findItem(localSavedState.expandedMenuItemId);
        if (localMenuItem != null) {
          MenuItemCompat.expandActionView(localMenuItem);
        }
      }
      if (localSavedState.isOverflowOpen)
      {
        removeCallbacks(this.mShowOverflowMenuRunnable);
        post(this.mShowOverflowMenuRunnable);
      }
      return;
    }
  }
  
  public void onRtlPropertiesChanged(int paramInt)
  {
    int i = 1;
    if (Build.VERSION.SDK_INT >= 17) {
      super.onRtlPropertiesChanged(paramInt);
    }
    RtlSpacingHelper localRtlSpacingHelper = this.mContentInsets;
    int m;
    if (paramInt == i) {
      if (i != localRtlSpacingHelper.mIsRtl)
      {
        localRtlSpacingHelper.mIsRtl = i;
        if (!localRtlSpacingHelper.mIsRelative) {
          break label176;
        }
        if (i == 0) {
          break label115;
        }
        if (localRtlSpacingHelper.mEnd == -2147483648) {
          break label97;
        }
        m = localRtlSpacingHelper.mEnd;
        label64:
        localRtlSpacingHelper.mLeft = m;
        if (localRtlSpacingHelper.mStart == -2147483648) {
          break label106;
        }
      }
    }
    label97:
    label106:
    for (int n = localRtlSpacingHelper.mStart;; n = localRtlSpacingHelper.mExplicitRight)
    {
      localRtlSpacingHelper.mRight = n;
      return;
      i = 0;
      break;
      m = localRtlSpacingHelper.mExplicitLeft;
      break label64;
    }
    label115:
    int j;
    if (localRtlSpacingHelper.mStart != -2147483648)
    {
      j = localRtlSpacingHelper.mStart;
      localRtlSpacingHelper.mLeft = j;
      if (localRtlSpacingHelper.mEnd == -2147483648) {
        break label167;
      }
    }
    label167:
    for (int k = localRtlSpacingHelper.mEnd;; k = localRtlSpacingHelper.mExplicitRight)
    {
      localRtlSpacingHelper.mRight = k;
      return;
      j = localRtlSpacingHelper.mExplicitLeft;
      break;
    }
    label176:
    localRtlSpacingHelper.mLeft = localRtlSpacingHelper.mExplicitLeft;
    localRtlSpacingHelper.mRight = localRtlSpacingHelper.mExplicitRight;
  }
  
  public Parcelable onSaveInstanceState()
  {
    SavedState localSavedState = new SavedState(super.onSaveInstanceState());
    if ((this.mExpandedMenuPresenter != null) && (this.mExpandedMenuPresenter.mCurrentExpandedItem != null)) {
      localSavedState.expandedMenuItemId = this.mExpandedMenuPresenter.mCurrentExpandedItem.getItemId();
    }
    localSavedState.isOverflowOpen = isOverflowMenuShowing();
    return localSavedState;
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    int i = MotionEventCompat.getActionMasked(paramMotionEvent);
    if (i == 0) {
      this.mEatingTouch = false;
    }
    if (!this.mEatingTouch)
    {
      boolean bool = super.onTouchEvent(paramMotionEvent);
      if ((i == 0) && (!bool)) {
        this.mEatingTouch = true;
      }
    }
    if ((i == 1) || (i == 3)) {
      this.mEatingTouch = false;
    }
    return true;
  }
  
  public void setCollapsible(boolean paramBoolean)
  {
    this.mCollapsible = paramBoolean;
    requestLayout();
  }
  
  public void setLogo(int paramInt)
  {
    setLogo(this.mTintManager.getDrawable(paramInt, false));
  }
  
  public void setLogo(Drawable paramDrawable)
  {
    if (paramDrawable != null)
    {
      ensureLogoView();
      if (!isChildOrHidden(this.mLogoView)) {
        addSystemView(this.mLogoView, true);
      }
    }
    for (;;)
    {
      if (this.mLogoView != null) {
        this.mLogoView.setImageDrawable(paramDrawable);
      }
      return;
      if ((this.mLogoView != null) && (isChildOrHidden(this.mLogoView)))
      {
        removeView(this.mLogoView);
        this.mHiddenViews.remove(this.mLogoView);
      }
    }
  }
  
  public void setLogoDescription(int paramInt)
  {
    setLogoDescription(getContext().getText(paramInt));
  }
  
  public void setLogoDescription(CharSequence paramCharSequence)
  {
    if (!TextUtils.isEmpty(paramCharSequence)) {
      ensureLogoView();
    }
    if (this.mLogoView != null) {
      this.mLogoView.setContentDescription(paramCharSequence);
    }
  }
  
  public void setNavigationContentDescription(int paramInt)
  {
    if (paramInt != 0) {}
    for (CharSequence localCharSequence = getContext().getText(paramInt);; localCharSequence = null)
    {
      setNavigationContentDescription(localCharSequence);
      return;
    }
  }
  
  public void setNavigationContentDescription(CharSequence paramCharSequence)
  {
    if (!TextUtils.isEmpty(paramCharSequence)) {
      ensureNavButtonView();
    }
    if (this.mNavButtonView != null) {
      this.mNavButtonView.setContentDescription(paramCharSequence);
    }
  }
  
  public void setNavigationIcon(int paramInt)
  {
    setNavigationIcon(this.mTintManager.getDrawable(paramInt, false));
  }
  
  public void setNavigationIcon(Drawable paramDrawable)
  {
    if (paramDrawable != null)
    {
      ensureNavButtonView();
      if (!isChildOrHidden(this.mNavButtonView)) {
        addSystemView(this.mNavButtonView, true);
      }
    }
    for (;;)
    {
      if (this.mNavButtonView != null) {
        this.mNavButtonView.setImageDrawable(paramDrawable);
      }
      return;
      if ((this.mNavButtonView != null) && (isChildOrHidden(this.mNavButtonView)))
      {
        removeView(this.mNavButtonView);
        this.mHiddenViews.remove(this.mNavButtonView);
      }
    }
  }
  
  public void setNavigationOnClickListener(View.OnClickListener paramOnClickListener)
  {
    ensureNavButtonView();
    this.mNavButtonView.setOnClickListener(paramOnClickListener);
  }
  
  public void setOnMenuItemClickListener(OnMenuItemClickListener paramOnMenuItemClickListener)
  {
    this.mOnMenuItemClickListener = paramOnMenuItemClickListener;
  }
  
  public void setOverflowIcon(Drawable paramDrawable)
  {
    ensureMenu();
    this.mMenuView.setOverflowIcon(paramDrawable);
  }
  
  public void setPopupTheme(int paramInt)
  {
    if (this.mPopupTheme != paramInt)
    {
      this.mPopupTheme = paramInt;
      if (paramInt == 0) {
        this.mPopupContext = getContext();
      }
    }
    else
    {
      return;
    }
    this.mPopupContext = new ContextThemeWrapper(getContext(), paramInt);
  }
  
  public void setSubtitle(int paramInt)
  {
    setSubtitle(getContext().getText(paramInt));
  }
  
  public void setSubtitle(CharSequence paramCharSequence)
  {
    if (!TextUtils.isEmpty(paramCharSequence))
    {
      if (this.mSubtitleTextView == null)
      {
        Context localContext = getContext();
        this.mSubtitleTextView = new TextView(localContext);
        this.mSubtitleTextView.setSingleLine();
        this.mSubtitleTextView.setEllipsize(TextUtils.TruncateAt.END);
        if (this.mSubtitleTextAppearance != 0) {
          this.mSubtitleTextView.setTextAppearance(localContext, this.mSubtitleTextAppearance);
        }
        if (this.mSubtitleTextColor != 0) {
          this.mSubtitleTextView.setTextColor(this.mSubtitleTextColor);
        }
      }
      if (!isChildOrHidden(this.mSubtitleTextView)) {
        addSystemView(this.mSubtitleTextView, true);
      }
    }
    for (;;)
    {
      if (this.mSubtitleTextView != null) {
        this.mSubtitleTextView.setText(paramCharSequence);
      }
      this.mSubtitleText = paramCharSequence;
      return;
      if ((this.mSubtitleTextView != null) && (isChildOrHidden(this.mSubtitleTextView)))
      {
        removeView(this.mSubtitleTextView);
        this.mHiddenViews.remove(this.mSubtitleTextView);
      }
    }
  }
  
  public void setSubtitleTextColor(int paramInt)
  {
    this.mSubtitleTextColor = paramInt;
    if (this.mSubtitleTextView != null) {
      this.mSubtitleTextView.setTextColor(paramInt);
    }
  }
  
  public void setTitle(int paramInt)
  {
    setTitle(getContext().getText(paramInt));
  }
  
  public void setTitle(CharSequence paramCharSequence)
  {
    if (!TextUtils.isEmpty(paramCharSequence))
    {
      if (this.mTitleTextView == null)
      {
        Context localContext = getContext();
        this.mTitleTextView = new TextView(localContext);
        this.mTitleTextView.setSingleLine();
        this.mTitleTextView.setEllipsize(TextUtils.TruncateAt.END);
        if (this.mTitleTextAppearance != 0) {
          this.mTitleTextView.setTextAppearance(localContext, this.mTitleTextAppearance);
        }
        if (this.mTitleTextColor != 0) {
          this.mTitleTextView.setTextColor(this.mTitleTextColor);
        }
      }
      if (!isChildOrHidden(this.mTitleTextView)) {
        addSystemView(this.mTitleTextView, true);
      }
    }
    for (;;)
    {
      if (this.mTitleTextView != null) {
        this.mTitleTextView.setText(paramCharSequence);
      }
      this.mTitleText = paramCharSequence;
      return;
      if ((this.mTitleTextView != null) && (isChildOrHidden(this.mTitleTextView)))
      {
        removeView(this.mTitleTextView);
        this.mHiddenViews.remove(this.mTitleTextView);
      }
    }
  }
  
  public void setTitleTextColor(int paramInt)
  {
    this.mTitleTextColor = paramInt;
    if (this.mTitleTextView != null) {
      this.mTitleTextView.setTextColor(paramInt);
    }
  }
  
  public final boolean showOverflowMenu()
  {
    if (this.mMenuView != null)
    {
      ActionMenuView localActionMenuView = this.mMenuView;
      if ((localActionMenuView.mPresenter != null) && (localActionMenuView.mPresenter.showOverflowMenu())) {}
      for (int i = 1; i != 0; i = 0) {
        return true;
      }
    }
    return false;
  }
  
  private final class ExpandedActionViewMenuPresenter
    implements MenuPresenter
  {
    MenuItemImpl mCurrentExpandedItem;
    MenuBuilder mMenu;
    
    private ExpandedActionViewMenuPresenter() {}
    
    public final boolean collapseItemActionView$29f2911(MenuItemImpl paramMenuItemImpl)
    {
      if ((Toolbar.this.mExpandedActionView instanceof CollapsibleActionView)) {
        ((CollapsibleActionView)Toolbar.this.mExpandedActionView).onActionViewCollapsed();
      }
      Toolbar.this.removeView(Toolbar.this.mExpandedActionView);
      Toolbar.this.removeView(Toolbar.this.mCollapseButtonView);
      Toolbar.this.mExpandedActionView = null;
      Toolbar localToolbar = Toolbar.this;
      for (int i = -1 + localToolbar.mHiddenViews.size(); i >= 0; i--) {
        localToolbar.addView((View)localToolbar.mHiddenViews.get(i));
      }
      localToolbar.mHiddenViews.clear();
      this.mCurrentExpandedItem = null;
      Toolbar.this.requestLayout();
      paramMenuItemImpl.setActionViewExpanded(false);
      return true;
    }
    
    public final boolean expandItemActionView$29f2911(MenuItemImpl paramMenuItemImpl)
    {
      Toolbar.access$200(Toolbar.this);
      if (Toolbar.this.mCollapseButtonView.getParent() != Toolbar.this) {
        Toolbar.this.addView(Toolbar.this.mCollapseButtonView);
      }
      Toolbar.this.mExpandedActionView = paramMenuItemImpl.getActionView();
      this.mCurrentExpandedItem = paramMenuItemImpl;
      if (Toolbar.this.mExpandedActionView.getParent() != Toolbar.this)
      {
        Toolbar.LayoutParams localLayoutParams = Toolbar.generateDefaultLayoutParams();
        localLayoutParams.gravity = (0x800003 | 0x70 & Toolbar.this.mButtonGravity);
        localLayoutParams.mViewType = 2;
        Toolbar.this.mExpandedActionView.setLayoutParams(localLayoutParams);
        Toolbar.this.addView(Toolbar.this.mExpandedActionView);
      }
      Toolbar localToolbar = Toolbar.this;
      for (int i = -1 + localToolbar.getChildCount(); i >= 0; i--)
      {
        View localView = localToolbar.getChildAt(i);
        if ((((Toolbar.LayoutParams)localView.getLayoutParams()).mViewType != 2) && (localView != localToolbar.mMenuView))
        {
          localToolbar.removeViewAt(i);
          localToolbar.mHiddenViews.add(localView);
        }
      }
      Toolbar.this.requestLayout();
      paramMenuItemImpl.setActionViewExpanded(true);
      if ((Toolbar.this.mExpandedActionView instanceof CollapsibleActionView)) {
        ((CollapsibleActionView)Toolbar.this.mExpandedActionView).onActionViewExpanded();
      }
      return true;
    }
    
    public final boolean flagActionItems()
    {
      return false;
    }
    
    public final void initForMenu(Context paramContext, MenuBuilder paramMenuBuilder)
    {
      if ((this.mMenu != null) && (this.mCurrentExpandedItem != null)) {
        this.mMenu.collapseItemActionView(this.mCurrentExpandedItem);
      }
      this.mMenu = paramMenuBuilder;
    }
    
    public final void onCloseMenu(MenuBuilder paramMenuBuilder, boolean paramBoolean) {}
    
    public final boolean onSubMenuSelected(SubMenuBuilder paramSubMenuBuilder)
    {
      return false;
    }
    
    public final void updateMenuView(boolean paramBoolean)
    {
      int i;
      int j;
      if (this.mCurrentExpandedItem != null)
      {
        MenuBuilder localMenuBuilder = this.mMenu;
        i = 0;
        if (localMenuBuilder != null) {
          j = this.mMenu.size();
        }
      }
      for (int k = 0;; k++)
      {
        i = 0;
        if (k < j)
        {
          if (this.mMenu.getItem(k) == this.mCurrentExpandedItem) {
            i = 1;
          }
        }
        else
        {
          if (i == 0) {
            collapseItemActionView$29f2911(this.mCurrentExpandedItem);
          }
          return;
        }
      }
    }
  }
  
  public static final class LayoutParams
    extends ActionBar.LayoutParams
  {
    int mViewType = 0;
    
    public LayoutParams()
    {
      super(-2);
      this.gravity = 8388627;
    }
    
    public LayoutParams(byte paramByte)
    {
      super(-2);
      this.gravity = 17;
    }
    
    public LayoutParams(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
    }
    
    public LayoutParams(ActionBar.LayoutParams paramLayoutParams)
    {
      super();
    }
    
    public LayoutParams(LayoutParams paramLayoutParams)
    {
      super();
      this.mViewType = paramLayoutParams.mViewType;
    }
    
    public LayoutParams(ViewGroup.LayoutParams paramLayoutParams)
    {
      super();
    }
    
    public LayoutParams(ViewGroup.MarginLayoutParams paramMarginLayoutParams)
    {
      super();
      this.leftMargin = paramMarginLayoutParams.leftMargin;
      this.topMargin = paramMarginLayoutParams.topMargin;
      this.rightMargin = paramMarginLayoutParams.rightMargin;
      this.bottomMargin = paramMarginLayoutParams.bottomMargin;
    }
  }
  
  public static abstract interface OnMenuItemClickListener
  {
    public abstract boolean onMenuItemClick(MenuItem paramMenuItem);
  }
  
  public static class SavedState
    extends View.BaseSavedState
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator() {};
    int expandedMenuItemId;
    boolean isOverflowOpen;
    
    public SavedState(Parcel paramParcel)
    {
      super();
      this.expandedMenuItemId = paramParcel.readInt();
      if (paramParcel.readInt() != 0) {}
      for (boolean bool = true;; bool = false)
      {
        this.isOverflowOpen = bool;
        return;
      }
    }
    
    public SavedState(Parcelable paramParcelable)
    {
      super();
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      paramParcel.writeInt(this.expandedMenuItemId);
      if (this.isOverflowOpen) {}
      for (int i = 1;; i = 0)
      {
        paramParcel.writeInt(i);
        return;
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.widget.Toolbar
 * JD-Core Version:    0.7.0.1
 */