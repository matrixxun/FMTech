package android.support.v7.internal.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat.ViewPropertyAnimatorCompatImpl;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v4.view.ViewPropertyAnimatorUpdateListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.OnMenuVisibilityListener;
import android.support.v7.appcompat.R.attr;
import android.support.v7.appcompat.R.id;
import android.support.v7.appcompat.R.styleable;
import android.support.v7.internal.view.ActionBarPolicy;
import android.support.v7.internal.view.SupportMenuInflater;
import android.support.v7.internal.view.ViewPropertyAnimatorCompatSet;
import android.support.v7.internal.view.menu.MenuBuilder;
import android.support.v7.internal.view.menu.MenuBuilder.Callback;
import android.support.v7.internal.widget.ActionBarContainer;
import android.support.v7.internal.widget.ActionBarContextView;
import android.support.v7.internal.widget.ActionBarOverlayLayout;
import android.support.v7.internal.widget.ActionBarOverlayLayout.ActionBarVisibilityCallback;
import android.support.v7.internal.widget.DecorToolbar;
import android.support.v7.internal.widget.ScrollingTabContainerView;
import android.support.v7.view.ActionMode;
import android.support.v7.view.ActionMode.Callback;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class WindowDecorActionBar
  extends ActionBar
  implements ActionBarOverlayLayout.ActionBarVisibilityCallback
{
  private static final boolean ALLOW_SHOW_HIDE_ANIMATIONS;
  private static final Interpolator sHideInterpolator;
  private static final Interpolator sShowInterpolator;
  ActionModeImpl mActionMode;
  private Activity mActivity;
  private ActionBarContainer mContainerView;
  private boolean mContentAnimations = true;
  private View mContentView;
  private Context mContext;
  private ActionBarContextView mContextView;
  private int mCurWindowVisibility = 0;
  private ViewPropertyAnimatorCompatSet mCurrentShowAnim;
  private DecorToolbar mDecorToolbar;
  ActionMode mDeferredDestroyActionMode;
  ActionMode.Callback mDeferredModeDestroyCallback;
  private Dialog mDialog;
  private boolean mDisplayHomeAsUpSet;
  private boolean mHasEmbeddedTabs;
  private boolean mHiddenByApp;
  private boolean mHiddenBySystem;
  final ViewPropertyAnimatorListener mHideListener = new ViewPropertyAnimatorListenerAdapter()
  {
    public final void onAnimationEnd(View paramAnonymousView)
    {
      if ((WindowDecorActionBar.this.mContentAnimations) && (WindowDecorActionBar.this.mContentView != null))
      {
        ViewCompat.setTranslationY(WindowDecorActionBar.this.mContentView, 0.0F);
        ViewCompat.setTranslationY(WindowDecorActionBar.this.mContainerView, 0.0F);
      }
      WindowDecorActionBar.this.mContainerView.setVisibility(8);
      WindowDecorActionBar.this.mContainerView.setTransitioning(false);
      WindowDecorActionBar.access$302$906f40c(WindowDecorActionBar.this);
      WindowDecorActionBar localWindowDecorActionBar = WindowDecorActionBar.this;
      if (localWindowDecorActionBar.mDeferredModeDestroyCallback != null)
      {
        localWindowDecorActionBar.mDeferredModeDestroyCallback.onDestroyActionMode(localWindowDecorActionBar.mDeferredDestroyActionMode);
        localWindowDecorActionBar.mDeferredDestroyActionMode = null;
        localWindowDecorActionBar.mDeferredModeDestroyCallback = null;
      }
      if (WindowDecorActionBar.this.mOverlayLayout != null) {
        ViewCompat.requestApplyInsets(WindowDecorActionBar.this.mOverlayLayout);
      }
    }
  };
  boolean mHideOnContentScroll;
  private boolean mLastMenuVisibility;
  private ArrayList<ActionBar.OnMenuVisibilityListener> mMenuVisibilityListeners = new ArrayList();
  private boolean mNowShowing = true;
  private ActionBarOverlayLayout mOverlayLayout;
  private int mSavedTabPosition = -1;
  private boolean mShowHideAnimationEnabled;
  final ViewPropertyAnimatorListener mShowListener = new ViewPropertyAnimatorListenerAdapter()
  {
    public final void onAnimationEnd(View paramAnonymousView)
    {
      WindowDecorActionBar.access$302$906f40c(WindowDecorActionBar.this);
      WindowDecorActionBar.this.mContainerView.requestLayout();
    }
  };
  private boolean mShowingForMode;
  private ScrollingTabContainerView mTabScrollView;
  private ArrayList<Object> mTabs = new ArrayList();
  private Context mThemedContext;
  final ViewPropertyAnimatorUpdateListener mUpdateListener = new ViewPropertyAnimatorUpdateListener()
  {
    public final void onAnimationUpdate$3c7ec8c3()
    {
      ((View)WindowDecorActionBar.this.mContainerView.getParent()).invalidate();
    }
  };
  
  static
  {
    boolean bool1 = true;
    boolean bool2;
    if (!WindowDecorActionBar.class.desiredAssertionStatus())
    {
      bool2 = bool1;
      $assertionsDisabled = bool2;
      sHideInterpolator = new AccelerateInterpolator();
      sShowInterpolator = new DecelerateInterpolator();
      if (Build.VERSION.SDK_INT < 14) {
        break label54;
      }
    }
    for (;;)
    {
      ALLOW_SHOW_HIDE_ANIMATIONS = bool1;
      return;
      bool2 = false;
      break;
      label54:
      bool1 = false;
    }
  }
  
  public WindowDecorActionBar(Activity paramActivity, boolean paramBoolean)
  {
    this.mActivity = paramActivity;
    View localView = paramActivity.getWindow().getDecorView();
    init(localView);
    if (!paramBoolean) {
      this.mContentView = localView.findViewById(16908290);
    }
  }
  
  public WindowDecorActionBar(Dialog paramDialog)
  {
    this.mDialog = paramDialog;
    init(paramDialog.getWindow().getDecorView());
  }
  
  private static boolean checkShowingFlags(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    if (paramBoolean3) {}
    while ((!paramBoolean1) && (!paramBoolean2)) {
      return true;
    }
    return false;
  }
  
  private void init(View paramView)
  {
    this.mOverlayLayout = ((ActionBarOverlayLayout)paramView.findViewById(R.id.decor_content_parent));
    if (this.mOverlayLayout != null) {
      this.mOverlayLayout.setActionBarVisibilityCallback(this);
    }
    View localView = paramView.findViewById(R.id.action_bar);
    if ((localView instanceof DecorToolbar)) {}
    for (DecorToolbar localDecorToolbar = (DecorToolbar)localView;; localDecorToolbar = ((Toolbar)localView).getWrapper())
    {
      this.mDecorToolbar = localDecorToolbar;
      this.mContextView = ((ActionBarContextView)paramView.findViewById(R.id.action_context_bar));
      this.mContainerView = ((ActionBarContainer)paramView.findViewById(R.id.action_bar_container));
      if ((this.mDecorToolbar != null) && (this.mContextView != null) && (this.mContainerView != null)) {
        break label201;
      }
      throw new IllegalStateException(getClass().getSimpleName() + " can only be used with a compatible window decor layout");
      if (!(localView instanceof Toolbar)) {
        break;
      }
    }
    if ("Can't make a decor toolbar out of " + localView != null) {}
    for (String str = localView.getClass().getSimpleName();; str = "null") {
      throw new IllegalStateException(str);
    }
    label201:
    this.mContext = this.mDecorToolbar.getContext();
    if ((0x4 & this.mDecorToolbar.getDisplayOptions()) != 0) {}
    TypedArray localTypedArray;
    for (int i = 1;; i = 0)
    {
      if (i != 0) {
        this.mDisplayHomeAsUpSet = true;
      }
      ActionBarPolicy localActionBarPolicy = ActionBarPolicy.get(this.mContext);
      setHasEmbeddedTabs(localActionBarPolicy.hasEmbeddedTabs());
      localTypedArray = this.mContext.obtainStyledAttributes(null, R.styleable.ActionBar, R.attr.actionBarStyle, 0);
      if (!localTypedArray.getBoolean(R.styleable.ActionBar_hideOnContentScroll, false)) {
        break label340;
      }
      if (this.mOverlayLayout.mOverlayMode) {
        break;
      }
      throw new IllegalStateException("Action bar must be in overlay mode (Window.FEATURE_OVERLAY_ACTION_BAR) to enable hide on content scroll");
    }
    this.mHideOnContentScroll = true;
    this.mOverlayLayout.setHideOnContentScrollEnabled(true);
    label340:
    int j = localTypedArray.getDimensionPixelSize(R.styleable.ActionBar_elevation, 0);
    if (j != 0)
    {
      float f = j;
      ViewCompat.setElevation(this.mContainerView, f);
    }
    localTypedArray.recycle();
  }
  
  private void setHasEmbeddedTabs(boolean paramBoolean)
  {
    boolean bool1 = true;
    this.mHasEmbeddedTabs = paramBoolean;
    boolean bool2;
    label50:
    label83:
    boolean bool3;
    label103:
    ActionBarOverlayLayout localActionBarOverlayLayout;
    if (!this.mHasEmbeddedTabs)
    {
      this.mDecorToolbar.setEmbeddedTabView(null);
      this.mContainerView.setTabContainer(this.mTabScrollView);
      if (this.mDecorToolbar.getNavigationMode() != 2) {
        break label160;
      }
      bool2 = bool1;
      if (this.mTabScrollView != null)
      {
        if (!bool2) {
          break label165;
        }
        this.mTabScrollView.setVisibility(0);
        if (this.mOverlayLayout != null) {
          ViewCompat.requestApplyInsets(this.mOverlayLayout);
        }
      }
      DecorToolbar localDecorToolbar = this.mDecorToolbar;
      if ((this.mHasEmbeddedTabs) || (!bool2)) {
        break label177;
      }
      bool3 = bool1;
      localDecorToolbar.setCollapsible(bool3);
      localActionBarOverlayLayout = this.mOverlayLayout;
      if ((this.mHasEmbeddedTabs) || (!bool2)) {
        break label183;
      }
    }
    for (;;)
    {
      localActionBarOverlayLayout.setHasNonEmbeddedTabs(bool1);
      return;
      this.mContainerView.setTabContainer(null);
      this.mDecorToolbar.setEmbeddedTabView(this.mTabScrollView);
      break;
      label160:
      bool2 = false;
      break label50;
      label165:
      this.mTabScrollView.setVisibility(8);
      break label83;
      label177:
      bool3 = false;
      break label103;
      label183:
      bool1 = false;
    }
  }
  
  private void updateVisibility(boolean paramBoolean)
  {
    if (checkShowingFlags(this.mHiddenByApp, this.mHiddenBySystem, this.mShowingForMode)) {
      if (!this.mNowShowing)
      {
        this.mNowShowing = true;
        if (this.mCurrentShowAnim != null) {
          this.mCurrentShowAnim.cancel();
        }
        this.mContainerView.setVisibility(0);
        if ((this.mCurWindowVisibility != 0) || (!ALLOW_SHOW_HIDE_ANIMATIONS) || ((!this.mShowHideAnimationEnabled) && (!paramBoolean))) {
          break label271;
        }
        ViewCompat.setTranslationY(this.mContainerView, 0.0F);
        f2 = -this.mContainerView.getHeight();
        if (paramBoolean)
        {
          arrayOfInt2 = new int[] { 0, 0 };
          this.mContainerView.getLocationInWindow(arrayOfInt2);
          f2 -= arrayOfInt2[1];
        }
        ViewCompat.setTranslationY(this.mContainerView, f2);
        localViewPropertyAnimatorCompatSet2 = new ViewPropertyAnimatorCompatSet();
        localViewPropertyAnimatorCompat2 = ViewCompat.animate(this.mContainerView).translationY(0.0F);
        localViewPropertyAnimatorCompat2.setUpdateListener(this.mUpdateListener);
        localViewPropertyAnimatorCompatSet2.play(localViewPropertyAnimatorCompat2);
        if ((this.mContentAnimations) && (this.mContentView != null))
        {
          ViewCompat.setTranslationY(this.mContentView, f2);
          localViewPropertyAnimatorCompatSet2.play(ViewCompat.animate(this.mContentView).translationY(0.0F));
        }
        localViewPropertyAnimatorCompatSet2.setInterpolator(sShowInterpolator);
        localViewPropertyAnimatorCompatSet2.setDuration$795ab51d();
        localViewPropertyAnimatorCompatSet2.setListener(this.mShowListener);
        this.mCurrentShowAnim = localViewPropertyAnimatorCompatSet2;
        localViewPropertyAnimatorCompatSet2.start();
        if (this.mOverlayLayout != null) {
          ViewCompat.requestApplyInsets(this.mOverlayLayout);
        }
      }
    }
    label271:
    while (!this.mNowShowing) {
      for (;;)
      {
        float f2;
        int[] arrayOfInt2;
        ViewPropertyAnimatorCompatSet localViewPropertyAnimatorCompatSet2;
        ViewPropertyAnimatorCompat localViewPropertyAnimatorCompat2;
        return;
        ViewCompat.setAlpha(this.mContainerView, 1.0F);
        ViewCompat.setTranslationY(this.mContainerView, 0.0F);
        if ((this.mContentAnimations) && (this.mContentView != null)) {
          ViewCompat.setTranslationY(this.mContentView, 0.0F);
        }
        this.mShowListener.onAnimationEnd(null);
      }
    }
    this.mNowShowing = false;
    if (this.mCurrentShowAnim != null) {
      this.mCurrentShowAnim.cancel();
    }
    if ((this.mCurWindowVisibility == 0) && (ALLOW_SHOW_HIDE_ANIMATIONS) && ((this.mShowHideAnimationEnabled) || (paramBoolean)))
    {
      ViewCompat.setAlpha(this.mContainerView, 1.0F);
      this.mContainerView.setTransitioning(true);
      ViewPropertyAnimatorCompatSet localViewPropertyAnimatorCompatSet1 = new ViewPropertyAnimatorCompatSet();
      float f1 = -this.mContainerView.getHeight();
      if (paramBoolean)
      {
        int[] arrayOfInt1 = { 0, 0 };
        this.mContainerView.getLocationInWindow(arrayOfInt1);
        f1 -= arrayOfInt1[1];
      }
      ViewPropertyAnimatorCompat localViewPropertyAnimatorCompat1 = ViewCompat.animate(this.mContainerView).translationY(f1);
      localViewPropertyAnimatorCompat1.setUpdateListener(this.mUpdateListener);
      localViewPropertyAnimatorCompatSet1.play(localViewPropertyAnimatorCompat1);
      if ((this.mContentAnimations) && (this.mContentView != null)) {
        localViewPropertyAnimatorCompatSet1.play(ViewCompat.animate(this.mContentView).translationY(f1));
      }
      localViewPropertyAnimatorCompatSet1.setInterpolator(sHideInterpolator);
      localViewPropertyAnimatorCompatSet1.setDuration$795ab51d();
      localViewPropertyAnimatorCompatSet1.setListener(this.mHideListener);
      this.mCurrentShowAnim = localViewPropertyAnimatorCompatSet1;
      localViewPropertyAnimatorCompatSet1.start();
      return;
    }
    this.mHideListener.onAnimationEnd(null);
  }
  
  public final void addOnMenuVisibilityListener(ActionBar.OnMenuVisibilityListener paramOnMenuVisibilityListener)
  {
    this.mMenuVisibilityListeners.add(paramOnMenuVisibilityListener);
  }
  
  public final void animateToMode(boolean paramBoolean)
  {
    ViewPropertyAnimatorCompat localViewPropertyAnimatorCompat2;
    ViewPropertyAnimatorCompat localViewPropertyAnimatorCompat1;
    label66:
    ViewPropertyAnimatorCompatSet localViewPropertyAnimatorCompatSet;
    View localView;
    if (paramBoolean)
    {
      if (!this.mShowingForMode)
      {
        this.mShowingForMode = true;
        if (this.mOverlayLayout != null) {
          this.mOverlayLayout.setShowingForActionMode(true);
        }
        updateVisibility(false);
      }
      if (!paramBoolean) {
        break label172;
      }
      localViewPropertyAnimatorCompat2 = this.mDecorToolbar.setupAnimatorToVisibility(4, 100L);
      localViewPropertyAnimatorCompat1 = this.mContextView.setupAnimatorToVisibility(0, 200L);
      localViewPropertyAnimatorCompatSet = new ViewPropertyAnimatorCompatSet();
      localViewPropertyAnimatorCompatSet.mAnimators.add(localViewPropertyAnimatorCompat2);
      localView = (View)localViewPropertyAnimatorCompat2.mView.get();
      if (localView == null) {
        break label202;
      }
    }
    label172:
    label202:
    for (long l = ViewPropertyAnimatorCompat.IMPL.getDuration$66604b42(localView);; l = 0L)
    {
      localViewPropertyAnimatorCompat1.setStartDelay(l);
      localViewPropertyAnimatorCompatSet.mAnimators.add(localViewPropertyAnimatorCompat1);
      localViewPropertyAnimatorCompatSet.start();
      return;
      if (!this.mShowingForMode) {
        break;
      }
      this.mShowingForMode = false;
      if (this.mOverlayLayout != null) {
        this.mOverlayLayout.setShowingForActionMode(false);
      }
      updateVisibility(false);
      break;
      localViewPropertyAnimatorCompat1 = this.mDecorToolbar.setupAnimatorToVisibility(0, 200L);
      localViewPropertyAnimatorCompat2 = this.mContextView.setupAnimatorToVisibility(8, 100L);
      break label66;
    }
  }
  
  public final boolean collapseActionView()
  {
    if ((this.mDecorToolbar != null) && (this.mDecorToolbar.hasExpandedActionView()))
    {
      this.mDecorToolbar.collapseActionView();
      return true;
    }
    return false;
  }
  
  public final void dispatchMenuVisibilityChanged(boolean paramBoolean)
  {
    if (paramBoolean == this.mLastMenuVisibility) {}
    for (;;)
    {
      return;
      this.mLastMenuVisibility = paramBoolean;
      int i = this.mMenuVisibilityListeners.size();
      for (int j = 0; j < i; j++) {
        ((ActionBar.OnMenuVisibilityListener)this.mMenuVisibilityListeners.get(j)).onMenuVisibilityChanged(paramBoolean);
      }
    }
  }
  
  public final void enableContentAnimations(boolean paramBoolean)
  {
    this.mContentAnimations = paramBoolean;
  }
  
  public final int getDisplayOptions()
  {
    return this.mDecorToolbar.getDisplayOptions();
  }
  
  public final int getHeight()
  {
    return this.mContainerView.getHeight();
  }
  
  public final Context getThemedContext()
  {
    int i;
    if (this.mThemedContext == null)
    {
      TypedValue localTypedValue = new TypedValue();
      this.mContext.getTheme().resolveAttribute(R.attr.actionBarWidgetTheme, localTypedValue, true);
      i = localTypedValue.resourceId;
      if (i == 0) {
        break label61;
      }
    }
    label61:
    for (this.mThemedContext = new ContextThemeWrapper(this.mContext, i);; this.mThemedContext = this.mContext) {
      return this.mThemedContext;
    }
  }
  
  public final void hide()
  {
    if (!this.mHiddenByApp)
    {
      this.mHiddenByApp = true;
      updateVisibility(false);
    }
  }
  
  public final void hideForSystem()
  {
    if (!this.mHiddenBySystem)
    {
      this.mHiddenBySystem = true;
      updateVisibility(true);
    }
  }
  
  public final void onConfigurationChanged(Configuration paramConfiguration)
  {
    setHasEmbeddedTabs(ActionBarPolicy.get(this.mContext).hasEmbeddedTabs());
  }
  
  public final void onContentScrollStarted()
  {
    if (this.mCurrentShowAnim != null)
    {
      this.mCurrentShowAnim.cancel();
      this.mCurrentShowAnim = null;
    }
  }
  
  public final void onWindowVisibilityChanged(int paramInt)
  {
    this.mCurWindowVisibility = paramInt;
  }
  
  public final void setBackgroundDrawable(Drawable paramDrawable)
  {
    this.mContainerView.setPrimaryBackground(paramDrawable);
  }
  
  public final void setDefaultDisplayHomeAsUpEnabled(boolean paramBoolean)
  {
    if (!this.mDisplayHomeAsUpSet) {
      setDisplayHomeAsUpEnabled(paramBoolean);
    }
  }
  
  public final void setDisplayHomeAsUpEnabled(boolean paramBoolean)
  {
    if (paramBoolean) {}
    for (int i = 4;; i = 0)
    {
      setDisplayOptions(i, 4);
      return;
    }
  }
  
  public final void setDisplayOptions(int paramInt1, int paramInt2)
  {
    int i = this.mDecorToolbar.getDisplayOptions();
    if ((paramInt2 & 0x4) != 0) {
      this.mDisplayHomeAsUpSet = true;
    }
    this.mDecorToolbar.setDisplayOptions(paramInt1 & paramInt2 | i & (paramInt2 ^ 0xFFFFFFFF));
  }
  
  public final void setHomeActionContentDescription(int paramInt)
  {
    this.mDecorToolbar.setNavigationContentDescription(paramInt);
  }
  
  public final void setHomeAsUpIndicator(int paramInt)
  {
    this.mDecorToolbar.setNavigationIcon(paramInt);
  }
  
  public final void setHomeAsUpIndicator(Drawable paramDrawable)
  {
    this.mDecorToolbar.setNavigationIcon(paramDrawable);
  }
  
  public final void setLogo(Drawable paramDrawable)
  {
    this.mDecorToolbar.setLogo(paramDrawable);
  }
  
  public final void setShowHideAnimationEnabled(boolean paramBoolean)
  {
    this.mShowHideAnimationEnabled = paramBoolean;
    if ((!paramBoolean) && (this.mCurrentShowAnim != null)) {
      this.mCurrentShowAnim.cancel();
    }
  }
  
  public final void setSubtitle(CharSequence paramCharSequence)
  {
    this.mDecorToolbar.setSubtitle(paramCharSequence);
  }
  
  public final void setTitle(CharSequence paramCharSequence)
  {
    this.mDecorToolbar.setTitle(paramCharSequence);
  }
  
  public final void setWindowTitle(CharSequence paramCharSequence)
  {
    this.mDecorToolbar.setWindowTitle(paramCharSequence);
  }
  
  public final void show()
  {
    if (this.mHiddenByApp)
    {
      this.mHiddenByApp = false;
      updateVisibility(false);
    }
  }
  
  public final void showForSystem()
  {
    if (this.mHiddenBySystem)
    {
      this.mHiddenBySystem = false;
      updateVisibility(true);
    }
  }
  
  public final ActionMode startActionMode(ActionMode.Callback paramCallback)
  {
    if (this.mActionMode != null) {
      this.mActionMode.finish();
    }
    this.mOverlayLayout.setHideOnContentScrollEnabled(false);
    this.mContextView.killMode();
    ActionModeImpl localActionModeImpl = new ActionModeImpl(this.mContextView.getContext(), paramCallback);
    if (localActionModeImpl.dispatchOnCreate())
    {
      localActionModeImpl.invalidate();
      this.mContextView.initForMode(localActionModeImpl);
      animateToMode(true);
      this.mContextView.sendAccessibilityEvent(32);
      this.mActionMode = localActionModeImpl;
      return localActionModeImpl;
    }
    return null;
  }
  
  public final class ActionModeImpl
    extends ActionMode
    implements MenuBuilder.Callback
  {
    private final Context mActionModeContext;
    private ActionMode.Callback mCallback;
    private WeakReference<View> mCustomView;
    private final MenuBuilder mMenu;
    
    public ActionModeImpl(Context paramContext, ActionMode.Callback paramCallback)
    {
      this.mActionModeContext = paramContext;
      this.mCallback = paramCallback;
      MenuBuilder localMenuBuilder = new MenuBuilder(paramContext);
      localMenuBuilder.mDefaultShowAsAction = 1;
      this.mMenu = localMenuBuilder;
      this.mMenu.setCallback(this);
    }
    
    public final boolean dispatchOnCreate()
    {
      this.mMenu.stopDispatchingItemsChanged();
      try
      {
        boolean bool = this.mCallback.onCreateActionMode(this, this.mMenu);
        return bool;
      }
      finally
      {
        this.mMenu.startDispatchingItemsChanged();
      }
    }
    
    public final void finish()
    {
      if (WindowDecorActionBar.this.mActionMode != this) {
        return;
      }
      if (!WindowDecorActionBar.access$700$49605cc3(WindowDecorActionBar.this.mHiddenByApp, WindowDecorActionBar.this.mHiddenBySystem))
      {
        WindowDecorActionBar.this.mDeferredDestroyActionMode = this;
        WindowDecorActionBar.this.mDeferredModeDestroyCallback = this.mCallback;
      }
      for (;;)
      {
        this.mCallback = null;
        WindowDecorActionBar.this.animateToMode(false);
        ActionBarContextView localActionBarContextView = WindowDecorActionBar.this.mContextView;
        if (localActionBarContextView.mClose == null) {
          localActionBarContextView.killMode();
        }
        WindowDecorActionBar.this.mDecorToolbar.getViewGroup().sendAccessibilityEvent(32);
        WindowDecorActionBar.this.mOverlayLayout.setHideOnContentScrollEnabled(WindowDecorActionBar.this.mHideOnContentScroll);
        WindowDecorActionBar.this.mActionMode = null;
        return;
        this.mCallback.onDestroyActionMode(this);
      }
    }
    
    public final View getCustomView()
    {
      if (this.mCustomView != null) {
        return (View)this.mCustomView.get();
      }
      return null;
    }
    
    public final Menu getMenu()
    {
      return this.mMenu;
    }
    
    public final MenuInflater getMenuInflater()
    {
      return new SupportMenuInflater(this.mActionModeContext);
    }
    
    public final CharSequence getSubtitle()
    {
      return WindowDecorActionBar.this.mContextView.getSubtitle();
    }
    
    public final CharSequence getTitle()
    {
      return WindowDecorActionBar.this.mContextView.getTitle();
    }
    
    public final void invalidate()
    {
      if (WindowDecorActionBar.this.mActionMode != this) {
        return;
      }
      this.mMenu.stopDispatchingItemsChanged();
      try
      {
        this.mCallback.onPrepareActionMode(this, this.mMenu);
        return;
      }
      finally
      {
        this.mMenu.startDispatchingItemsChanged();
      }
    }
    
    public final boolean isTitleOptional()
    {
      return WindowDecorActionBar.this.mContextView.mTitleOptional;
    }
    
    public final boolean onMenuItemSelected(MenuBuilder paramMenuBuilder, MenuItem paramMenuItem)
    {
      if (this.mCallback != null) {
        return this.mCallback.onActionItemClicked(this, paramMenuItem);
      }
      return false;
    }
    
    public final void onMenuModeChange(MenuBuilder paramMenuBuilder)
    {
      if (this.mCallback == null) {
        return;
      }
      invalidate();
      WindowDecorActionBar.this.mContextView.showOverflowMenu();
    }
    
    public final void setCustomView(View paramView)
    {
      WindowDecorActionBar.this.mContextView.setCustomView(paramView);
      this.mCustomView = new WeakReference(paramView);
    }
    
    public final void setSubtitle(int paramInt)
    {
      setSubtitle(WindowDecorActionBar.this.mContext.getResources().getString(paramInt));
    }
    
    public final void setSubtitle(CharSequence paramCharSequence)
    {
      WindowDecorActionBar.this.mContextView.setSubtitle(paramCharSequence);
    }
    
    public final void setTitle(int paramInt)
    {
      setTitle(WindowDecorActionBar.this.mContext.getResources().getString(paramInt));
    }
    
    public final void setTitle(CharSequence paramCharSequence)
    {
      WindowDecorActionBar.this.mContextView.setTitle(paramCharSequence);
    }
    
    public final void setTitleOptionalHint(boolean paramBoolean)
    {
      super.setTitleOptionalHint(paramBoolean);
      WindowDecorActionBar.this.mContextView.setTitleOptional(paramBoolean);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.internal.app.WindowDecorActionBar
 * JD-Core Version:    0.7.0.1
 */