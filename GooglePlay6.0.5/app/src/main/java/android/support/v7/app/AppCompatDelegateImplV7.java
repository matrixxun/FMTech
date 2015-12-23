package android.support.v7.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.media.AudioManager;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v4.view.WindowInsetsCompat;
import android.support.v7.appcompat.R.attr;
import android.support.v7.appcompat.R.id;
import android.support.v7.appcompat.R.layout;
import android.support.v7.appcompat.R.style;
import android.support.v7.appcompat.R.styleable;
import android.support.v7.internal.app.AppCompatViewInflater;
import android.support.v7.internal.app.ToolbarActionBar;
import android.support.v7.internal.app.WindowDecorActionBar;
import android.support.v7.internal.view.ContextThemeWrapper;
import android.support.v7.internal.view.menu.ListMenuPresenter;
import android.support.v7.internal.view.menu.MenuBuilder;
import android.support.v7.internal.view.menu.MenuBuilder.Callback;
import android.support.v7.internal.view.menu.MenuPresenter.Callback;
import android.support.v7.internal.view.menu.MenuView;
import android.support.v7.internal.widget.ActionBarContextView;
import android.support.v7.internal.widget.ContentFrameLayout;
import android.support.v7.internal.widget.ContentFrameLayout.OnAttachListener;
import android.support.v7.internal.widget.DecorContentParent;
import android.support.v7.internal.widget.FitWindowsViewGroup;
import android.support.v7.internal.widget.FitWindowsViewGroup.OnFitSystemWindowsListener;
import android.support.v7.internal.widget.TintManager;
import android.support.v7.internal.widget.ViewUtils;
import android.support.v7.view.ActionMode;
import android.support.v7.view.ActionMode.Callback;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatMultiAutoCompleteTextView;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AndroidRuntimeException;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.view.Window;
import android.view.Window.Callback;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.TextView;

class AppCompatDelegateImplV7
  extends AppCompatDelegateImplBase
  implements LayoutInflaterFactory, MenuBuilder.Callback
{
  private ActionMenuPresenterCallback mActionMenuPresenterCallback;
  ActionMode mActionMode;
  PopupWindow mActionModePopup;
  ActionBarContextView mActionModeView;
  private AppCompatViewInflater mAppCompatViewInflater;
  private boolean mClosingActionMenu;
  DecorContentParent mDecorContentParent;
  private boolean mEnableDefaultActionBarUp;
  ViewPropertyAnimatorCompat mFadeAnim = null;
  private boolean mFeatureIndeterminateProgress;
  private boolean mFeatureProgress;
  int mInvalidatePanelMenuFeatures;
  boolean mInvalidatePanelMenuPosted;
  private final Runnable mInvalidatePanelMenuRunnable = new Runnable()
  {
    public final void run()
    {
      if ((0x1 & AppCompatDelegateImplV7.this.mInvalidatePanelMenuFeatures) != 0) {
        AppCompatDelegateImplV7.access$100(AppCompatDelegateImplV7.this, 0);
      }
      if ((0x1000 & AppCompatDelegateImplV7.this.mInvalidatePanelMenuFeatures) != 0) {
        AppCompatDelegateImplV7.access$100(AppCompatDelegateImplV7.this, 108);
      }
      AppCompatDelegateImplV7.this.mInvalidatePanelMenuPosted = false;
      AppCompatDelegateImplV7.this.mInvalidatePanelMenuFeatures = 0;
    }
  };
  private PanelMenuPresenterCallback mPanelMenuPresenterCallback;
  private PanelFeatureState[] mPanels;
  private PanelFeatureState mPreparedPanel;
  Runnable mShowActionModePopup;
  private View mStatusGuard;
  ViewGroup mSubDecor;
  private boolean mSubDecorInstalled;
  private Rect mTempRect1;
  private Rect mTempRect2;
  private TextView mTitleView;
  ViewGroup mWindowDecor;
  
  AppCompatDelegateImplV7(Context paramContext, Window paramWindow, AppCompatCallback paramAppCompatCallback)
  {
    super(paramContext, paramWindow, paramAppCompatCallback);
  }
  
  private void ensureSubDecor()
  {
    TypedArray localTypedArray1;
    LayoutInflater localLayoutInflater;
    Object localObject1;
    if (!this.mSubDecorInstalled)
    {
      localTypedArray1 = this.mContext.obtainStyledAttributes(R.styleable.Theme);
      if (!localTypedArray1.hasValue(R.styleable.Theme_windowActionBar))
      {
        localTypedArray1.recycle();
        throw new IllegalStateException("You need to use a Theme.AppCompat theme (or descendant) with this activity.");
      }
      if (localTypedArray1.getBoolean(R.styleable.Theme_windowNoTitle, false))
      {
        requestWindowFeature(1);
        if (localTypedArray1.getBoolean(R.styleable.Theme_windowActionBarOverlay, false)) {
          requestWindowFeature(109);
        }
        if (localTypedArray1.getBoolean(R.styleable.Theme_windowActionModeOverlay, false)) {
          requestWindowFeature(10);
        }
        this.mIsFloating = localTypedArray1.getBoolean(R.styleable.Theme_android_windowIsFloating, false);
        localTypedArray1.recycle();
        localLayoutInflater = LayoutInflater.from(this.mContext);
        if (this.mWindowNoTitle) {
          break label447;
        }
        if (!this.mIsFloating) {
          break label272;
        }
        ViewGroup localViewGroup4 = (ViewGroup)localLayoutInflater.inflate(R.layout.abc_dialog_title_material, null);
        this.mOverlayActionBar = false;
        this.mHasActionBar = false;
        localObject1 = localViewGroup4;
      }
    }
    for (;;)
    {
      if (localObject1 == null)
      {
        throw new IllegalArgumentException("AppCompat does not support the current theme features: { windowActionBar: " + this.mHasActionBar + ", windowActionBarOverlay: " + this.mOverlayActionBar + ", android:windowIsFloating: " + this.mIsFloating + ", windowActionModeOverlay: " + this.mOverlayActionMode + ", windowNoTitle: " + this.mWindowNoTitle + " }");
        if (!localTypedArray1.getBoolean(R.styleable.Theme_windowActionBar, false)) {
          break;
        }
        requestWindowFeature(108);
        break;
        label272:
        if (!this.mHasActionBar) {
          break label996;
        }
        TypedValue localTypedValue = new TypedValue();
        this.mContext.getTheme().resolveAttribute(R.attr.actionBarTheme, localTypedValue, true);
        if (localTypedValue.resourceId != 0) {}
        for (Object localObject2 = new ContextThemeWrapper(this.mContext, localTypedValue.resourceId);; localObject2 = this.mContext)
        {
          ViewGroup localViewGroup3 = (ViewGroup)LayoutInflater.from((Context)localObject2).inflate(R.layout.abc_screen_toolbar, null);
          this.mDecorContentParent = ((DecorContentParent)localViewGroup3.findViewById(R.id.decor_content_parent));
          this.mDecorContentParent.setWindowCallback(this.mWindow.getCallback());
          if (this.mOverlayActionBar) {
            this.mDecorContentParent.initFeature(109);
          }
          if (this.mFeatureProgress) {
            this.mDecorContentParent.initFeature(2);
          }
          if (this.mFeatureIndeterminateProgress) {
            this.mDecorContentParent.initFeature(5);
          }
          localObject1 = localViewGroup3;
          break;
        }
        label447:
        if (this.mOverlayActionMode) {}
        for (ViewGroup localViewGroup1 = (ViewGroup)localLayoutInflater.inflate(R.layout.abc_screen_simple_overlay_action_mode, null);; localViewGroup1 = (ViewGroup)localLayoutInflater.inflate(R.layout.abc_screen_simple, null))
        {
          if (Build.VERSION.SDK_INT < 21) {
            break label511;
          }
          ViewCompat.setOnApplyWindowInsetsListener(localViewGroup1, new OnApplyWindowInsetsListener()
          {
            public final WindowInsetsCompat onApplyWindowInsets(View paramAnonymousView, WindowInsetsCompat paramAnonymousWindowInsetsCompat)
            {
              int i = paramAnonymousWindowInsetsCompat.getSystemWindowInsetTop();
              int j = AppCompatDelegateImplV7.access$300(AppCompatDelegateImplV7.this, i);
              if (i != j) {
                paramAnonymousWindowInsetsCompat = paramAnonymousWindowInsetsCompat.replaceSystemWindowInsets(paramAnonymousWindowInsetsCompat.getSystemWindowInsetLeft(), j, paramAnonymousWindowInsetsCompat.getSystemWindowInsetRight(), paramAnonymousWindowInsetsCompat.getSystemWindowInsetBottom());
              }
              return ViewCompat.onApplyWindowInsets(paramAnonymousView, paramAnonymousWindowInsetsCompat);
            }
          });
          localObject1 = localViewGroup1;
          break;
        }
        label511:
        ((FitWindowsViewGroup)localViewGroup1).setOnFitSystemWindowsListener(new FitWindowsViewGroup.OnFitSystemWindowsListener()
        {
          public final void onFitSystemWindows(Rect paramAnonymousRect)
          {
            paramAnonymousRect.top = AppCompatDelegateImplV7.access$300(AppCompatDelegateImplV7.this, paramAnonymousRect.top);
          }
        });
        localObject1 = localViewGroup1;
        continue;
      }
      if (this.mDecorContentParent == null) {
        this.mTitleView = ((TextView)((ViewGroup)localObject1).findViewById(R.id.title));
      }
      ViewUtils.makeOptionalFitsSystemWindows((View)localObject1);
      ViewGroup localViewGroup2 = (ViewGroup)this.mWindow.findViewById(16908290);
      ContentFrameLayout localContentFrameLayout1 = (ContentFrameLayout)((ViewGroup)localObject1).findViewById(R.id.action_bar_activity_content);
      while (localViewGroup2.getChildCount() > 0)
      {
        View localView = localViewGroup2.getChildAt(0);
        localViewGroup2.removeViewAt(0);
        localContentFrameLayout1.addView(localView);
      }
      this.mWindow.setContentView((View)localObject1);
      localViewGroup2.setId(-1);
      localContentFrameLayout1.setId(16908290);
      if ((localViewGroup2 instanceof FrameLayout)) {
        ((FrameLayout)localViewGroup2).setForeground(null);
      }
      localContentFrameLayout1.setAttachListener(new ContentFrameLayout.OnAttachListener()
      {
        public final void onDetachedFromWindow()
        {
          AppCompatDelegateImplV7 localAppCompatDelegateImplV7 = AppCompatDelegateImplV7.this;
          if (localAppCompatDelegateImplV7.mDecorContentParent != null) {
            localAppCompatDelegateImplV7.mDecorContentParent.dismissPopups();
          }
          if (localAppCompatDelegateImplV7.mActionModePopup != null)
          {
            localAppCompatDelegateImplV7.mWindowDecor.removeCallbacks(localAppCompatDelegateImplV7.mShowActionModePopup);
            if (localAppCompatDelegateImplV7.mActionModePopup.isShowing()) {
              localAppCompatDelegateImplV7.mActionModePopup.dismiss();
            }
            localAppCompatDelegateImplV7.mActionModePopup = null;
          }
          localAppCompatDelegateImplV7.endOnGoingFadeAnimation();
          AppCompatDelegateImplV7.PanelFeatureState localPanelFeatureState = localAppCompatDelegateImplV7.getPanelState$5103c037(0);
          if ((localPanelFeatureState != null) && (localPanelFeatureState.menu != null)) {
            localPanelFeatureState.menu.close();
          }
        }
      });
      this.mSubDecor = ((ViewGroup)localObject1);
      if ((this.mOriginalWindowCallback instanceof Activity)) {}
      for (CharSequence localCharSequence = ((Activity)this.mOriginalWindowCallback).getTitle();; localCharSequence = this.mTitle)
      {
        if (!TextUtils.isEmpty(localCharSequence)) {
          onTitleChanged(localCharSequence);
        }
        ContentFrameLayout localContentFrameLayout2 = (ContentFrameLayout)this.mSubDecor.findViewById(16908290);
        int i = this.mWindowDecor.getPaddingLeft();
        int j = this.mWindowDecor.getPaddingTop();
        int k = this.mWindowDecor.getPaddingRight();
        int m = this.mWindowDecor.getPaddingBottom();
        localContentFrameLayout2.mDecorPadding.set(i, j, k, m);
        if (ViewCompat.isLaidOut(localContentFrameLayout2)) {
          localContentFrameLayout2.requestLayout();
        }
        TypedArray localTypedArray2 = this.mContext.obtainStyledAttributes(R.styleable.Theme);
        localTypedArray2.getValue(R.styleable.Theme_windowMinWidthMajor, localContentFrameLayout2.getMinWidthMajor());
        localTypedArray2.getValue(R.styleable.Theme_windowMinWidthMinor, localContentFrameLayout2.getMinWidthMinor());
        if (localTypedArray2.hasValue(R.styleable.Theme_windowFixedWidthMajor)) {
          localTypedArray2.getValue(R.styleable.Theme_windowFixedWidthMajor, localContentFrameLayout2.getFixedWidthMajor());
        }
        if (localTypedArray2.hasValue(R.styleable.Theme_windowFixedWidthMinor)) {
          localTypedArray2.getValue(R.styleable.Theme_windowFixedWidthMinor, localContentFrameLayout2.getFixedWidthMinor());
        }
        if (localTypedArray2.hasValue(R.styleable.Theme_windowFixedHeightMajor)) {
          localTypedArray2.getValue(R.styleable.Theme_windowFixedHeightMajor, localContentFrameLayout2.getFixedHeightMajor());
        }
        if (localTypedArray2.hasValue(R.styleable.Theme_windowFixedHeightMinor)) {
          localTypedArray2.getValue(R.styleable.Theme_windowFixedHeightMinor, localContentFrameLayout2.getFixedHeightMinor());
        }
        localTypedArray2.recycle();
        localContentFrameLayout2.requestLayout();
        this.mSubDecorInstalled = true;
        PanelFeatureState localPanelFeatureState = getPanelState$5103c037(0);
        if ((!this.mIsDestroyed) && ((localPanelFeatureState == null) || (localPanelFeatureState.menu == null))) {
          invalidatePanelMenu(108);
        }
        return;
      }
      label996:
      localObject1 = null;
    }
  }
  
  private void invalidatePanelMenu(int paramInt)
  {
    this.mInvalidatePanelMenuFeatures |= 1 << paramInt;
    if ((!this.mInvalidatePanelMenuPosted) && (this.mWindowDecor != null))
    {
      ViewCompat.postOnAnimation(this.mWindowDecor, this.mInvalidatePanelMenuRunnable);
      this.mInvalidatePanelMenuPosted = true;
    }
  }
  
  private void openPanel(PanelFeatureState paramPanelFeatureState, KeyEvent paramKeyEvent)
  {
    if ((paramPanelFeatureState.isOpen) || (this.mIsDestroyed)) {}
    label111:
    label117:
    label121:
    label123:
    WindowManager localWindowManager;
    int i;
    Resources.Theme localTheme;
    label274:
    do
    {
      do
      {
        for (;;)
        {
          return;
          int n;
          if (paramPanelFeatureState.featureId == 0)
          {
            Context localContext2 = this.mContext;
            if ((0xF & localContext2.getResources().getConfiguration().screenLayout) != 4) {
              break label111;
            }
            n = 1;
            if (localContext2.getApplicationInfo().targetSdkVersion < 11) {
              break label117;
            }
          }
          for (int i1 = 1;; i1 = 0)
          {
            if ((n != 0) && (i1 != 0)) {
              break label121;
            }
            Window.Callback localCallback = this.mWindow.getCallback();
            if ((localCallback == null) || (localCallback.onMenuOpened(paramPanelFeatureState.featureId, paramPanelFeatureState.menu))) {
              break label123;
            }
            closePanel(paramPanelFeatureState, true);
            return;
            n = 0;
            break;
          }
        }
        localWindowManager = (WindowManager)this.mContext.getSystemService("window");
      } while ((localWindowManager == null) || (!preparePanel(paramPanelFeatureState, paramKeyEvent)));
      i = -2;
      if ((paramPanelFeatureState.decorView != null) && (!paramPanelFeatureState.refreshDecorView)) {
        break label793;
      }
      if (paramPanelFeatureState.decorView != null) {
        break label607;
      }
      Context localContext1 = getActionBarThemedContext();
      TypedValue localTypedValue = new TypedValue();
      localTheme = localContext1.getResources().newTheme();
      localTheme.setTo(localContext1.getTheme());
      localTheme.resolveAttribute(R.attr.actionBarPopupTheme, localTypedValue, true);
      if (localTypedValue.resourceId != 0) {
        localTheme.applyStyle(localTypedValue.resourceId, true);
      }
      localTheme.resolveAttribute(R.attr.panelMenuListTheme, localTypedValue, true);
      if (localTypedValue.resourceId == 0) {
        break;
      }
      localTheme.applyStyle(localTypedValue.resourceId, true);
      ContextThemeWrapper localContextThemeWrapper = new ContextThemeWrapper(localContext1, 0);
      localContextThemeWrapper.getTheme().setTo(localTheme);
      paramPanelFeatureState.listPresenterContext = localContextThemeWrapper;
      TypedArray localTypedArray = localContextThemeWrapper.obtainStyledAttributes(R.styleable.Theme);
      paramPanelFeatureState.background = localTypedArray.getResourceId(R.styleable.Theme_panelBackground, 0);
      paramPanelFeatureState.windowAnimations = localTypedArray.getResourceId(R.styleable.Theme_android_windowAnimationStyle, 0);
      localTypedArray.recycle();
      paramPanelFeatureState.decorView = new ListMenuDecorView(paramPanelFeatureState.listPresenterContext);
      paramPanelFeatureState.gravity = 81;
    } while (paramPanelFeatureState.decorView == null);
    label372:
    int j;
    label390:
    int k;
    if (paramPanelFeatureState.createdPanelView != null)
    {
      paramPanelFeatureState.shownPanelView = paramPanelFeatureState.createdPanelView;
      j = 1;
      if (j == 0) {
        break label764;
      }
      if (paramPanelFeatureState.shownPanelView == null) {
        break label787;
      }
      if (paramPanelFeatureState.createdPanelView == null) {
        break label766;
      }
      k = 1;
      label412:
      if (k == 0) {
        break label791;
      }
      ViewGroup.LayoutParams localLayoutParams1 = paramPanelFeatureState.shownPanelView.getLayoutParams();
      if (localLayoutParams1 == null) {
        localLayoutParams1 = new ViewGroup.LayoutParams(-2, -2);
      }
      int m = paramPanelFeatureState.background;
      paramPanelFeatureState.decorView.setBackgroundResource(m);
      ViewParent localViewParent = paramPanelFeatureState.shownPanelView.getParent();
      if ((localViewParent != null) && ((localViewParent instanceof ViewGroup))) {
        ((ViewGroup)localViewParent).removeView(paramPanelFeatureState.shownPanelView);
      }
      paramPanelFeatureState.decorView.addView(paramPanelFeatureState.shownPanelView, localLayoutParams1);
      if (!paramPanelFeatureState.shownPanelView.hasFocus()) {
        paramPanelFeatureState.shownPanelView.requestFocus();
      }
    }
    for (;;)
    {
      paramPanelFeatureState.isHandled = false;
      WindowManager.LayoutParams localLayoutParams = new WindowManager.LayoutParams(i, -2, paramPanelFeatureState.x, paramPanelFeatureState.y, 1002, 8519680, -3);
      localLayoutParams.gravity = paramPanelFeatureState.gravity;
      localLayoutParams.windowAnimations = paramPanelFeatureState.windowAnimations;
      localWindowManager.addView(paramPanelFeatureState.decorView, localLayoutParams);
      paramPanelFeatureState.isOpen = true;
      return;
      localTheme.applyStyle(R.style.Theme_AppCompat_CompactMenu, true);
      break label274;
      label607:
      if ((!paramPanelFeatureState.refreshDecorView) || (paramPanelFeatureState.decorView.getChildCount() <= 0)) {
        break label372;
      }
      paramPanelFeatureState.decorView.removeAllViews();
      break label372;
      if (paramPanelFeatureState.menu != null)
      {
        if (this.mPanelMenuPresenterCallback == null) {
          this.mPanelMenuPresenterCallback = new PanelMenuPresenterCallback((byte)0);
        }
        PanelMenuPresenterCallback localPanelMenuPresenterCallback = this.mPanelMenuPresenterCallback;
        if (paramPanelFeatureState.menu == null) {}
        for (MenuView localMenuView = null;; localMenuView = paramPanelFeatureState.listMenuPresenter.getMenuView(paramPanelFeatureState.decorView))
        {
          paramPanelFeatureState.shownPanelView = ((View)localMenuView);
          if (paramPanelFeatureState.shownPanelView == null) {
            break label760;
          }
          j = 1;
          break;
          if (paramPanelFeatureState.listMenuPresenter == null)
          {
            paramPanelFeatureState.listMenuPresenter = new ListMenuPresenter(paramPanelFeatureState.listPresenterContext, R.layout.abc_list_menu_item_layout);
            paramPanelFeatureState.listMenuPresenter.mCallback = localPanelMenuPresenterCallback;
            paramPanelFeatureState.menu.addMenuPresenter(paramPanelFeatureState.listMenuPresenter);
          }
        }
      }
      label760:
      j = 0;
      break label390;
      label764:
      break;
      label766:
      if (paramPanelFeatureState.listMenuPresenter.getAdapter().getCount() > 0)
      {
        k = 1;
        break label412;
      }
      label787:
      k = 0;
      break label412;
      label791:
      break;
      label793:
      if (paramPanelFeatureState.createdPanelView != null)
      {
        ViewGroup.LayoutParams localLayoutParams2 = paramPanelFeatureState.createdPanelView.getLayoutParams();
        if ((localLayoutParams2 != null) && (localLayoutParams2.width == -1)) {
          i = -1;
        }
      }
    }
  }
  
  private boolean performPanelShortcut$1f243752(PanelFeatureState paramPanelFeatureState, int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramKeyEvent.isSystem()) {}
    while (((!paramPanelFeatureState.isPrepared) && (!preparePanel(paramPanelFeatureState, paramKeyEvent))) || (paramPanelFeatureState.menu == null)) {
      return false;
    }
    return paramPanelFeatureState.menu.performShortcut(paramInt, paramKeyEvent, 1);
  }
  
  private boolean preparePanel(PanelFeatureState paramPanelFeatureState, KeyEvent paramKeyEvent)
  {
    if (this.mIsDestroyed) {
      return false;
    }
    if (paramPanelFeatureState.isPrepared) {
      return true;
    }
    if ((this.mPreparedPanel != null) && (this.mPreparedPanel != paramPanelFeatureState)) {
      closePanel(this.mPreparedPanel, false);
    }
    Window.Callback localCallback = this.mWindow.getCallback();
    if (localCallback != null) {
      paramPanelFeatureState.createdPanelView = localCallback.onCreatePanelView(paramPanelFeatureState.featureId);
    }
    int i;
    label87:
    Context localContext;
    TypedValue localTypedValue;
    Resources.Theme localTheme1;
    Resources.Theme localTheme2;
    label256:
    Object localObject;
    if ((paramPanelFeatureState.featureId == 0) || (paramPanelFeatureState.featureId == 108))
    {
      i = 1;
      if ((i != 0) && (this.mDecorContentParent != null)) {
        this.mDecorContentParent.setMenuPrepared();
      }
      if ((paramPanelFeatureState.createdPanelView != null) || ((i != 0) && ((this.mActionBar instanceof ToolbarActionBar)))) {
        break label625;
      }
      if ((paramPanelFeatureState.menu != null) && (!paramPanelFeatureState.refreshMenuContent)) {
        break label493;
      }
      if (paramPanelFeatureState.menu == null)
      {
        localContext = this.mContext;
        if (((paramPanelFeatureState.featureId != 0) && (paramPanelFeatureState.featureId != 108)) || (this.mDecorContentParent == null)) {
          break label654;
        }
        localTypedValue = new TypedValue();
        localTheme1 = localContext.getTheme();
        localTheme1.resolveAttribute(R.attr.actionBarTheme, localTypedValue, true);
        if (localTypedValue.resourceId == 0) {
          break label470;
        }
        localTheme2 = localContext.getResources().newTheme();
        localTheme2.setTo(localTheme1);
        localTheme2.applyStyle(localTypedValue.resourceId, true);
        localTheme2.resolveAttribute(R.attr.actionBarWidgetTheme, localTypedValue, true);
        if (localTypedValue.resourceId != 0)
        {
          if (localTheme2 == null)
          {
            localTheme2 = localContext.getResources().newTheme();
            localTheme2.setTo(localTheme1);
          }
          localTheme2.applyStyle(localTypedValue.resourceId, true);
        }
        Resources.Theme localTheme3 = localTheme2;
        if (localTheme3 == null) {
          break label654;
        }
        localObject = new ContextThemeWrapper(localContext, 0);
        ((Context)localObject).getTheme().setTo(localTheme3);
      }
    }
    for (;;)
    {
      MenuBuilder localMenuBuilder = new MenuBuilder((Context)localObject);
      localMenuBuilder.setCallback(this);
      paramPanelFeatureState.setMenu(localMenuBuilder);
      if (paramPanelFeatureState.menu == null) {
        break;
      }
      if ((i != 0) && (this.mDecorContentParent != null))
      {
        if (this.mActionMenuPresenterCallback == null) {
          this.mActionMenuPresenterCallback = new ActionMenuPresenterCallback((byte)0);
        }
        this.mDecorContentParent.setMenu(paramPanelFeatureState.menu, this.mActionMenuPresenterCallback);
      }
      paramPanelFeatureState.menu.stopDispatchingItemsChanged();
      if (!localCallback.onCreatePanelMenu(paramPanelFeatureState.featureId, paramPanelFeatureState.menu))
      {
        paramPanelFeatureState.setMenu(null);
        if ((i == 0) || (this.mDecorContentParent == null)) {
          break;
        }
        this.mDecorContentParent.setMenu(null, this.mActionMenuPresenterCallback);
        return false;
        i = 0;
        break label87;
        label470:
        localTheme1.resolveAttribute(R.attr.actionBarWidgetTheme, localTypedValue, true);
        localTheme2 = null;
        break label256;
      }
      paramPanelFeatureState.refreshMenuContent = false;
      label493:
      paramPanelFeatureState.menu.stopDispatchingItemsChanged();
      if (paramPanelFeatureState.frozenActionViewState != null)
      {
        paramPanelFeatureState.menu.restoreActionViewStates(paramPanelFeatureState.frozenActionViewState);
        paramPanelFeatureState.frozenActionViewState = null;
      }
      if (!localCallback.onPreparePanel(0, paramPanelFeatureState.createdPanelView, paramPanelFeatureState.menu))
      {
        if ((i != 0) && (this.mDecorContentParent != null)) {
          this.mDecorContentParent.setMenu(null, this.mActionMenuPresenterCallback);
        }
        paramPanelFeatureState.menu.startDispatchingItemsChanged();
        return false;
      }
      int j;
      if (paramKeyEvent != null)
      {
        j = paramKeyEvent.getDeviceId();
        if (KeyCharacterMap.load(j).getKeyboardType() == 1) {
          break label648;
        }
      }
      label648:
      for (boolean bool = true;; bool = false)
      {
        paramPanelFeatureState.qwertyMode = bool;
        paramPanelFeatureState.menu.setQwertyMode(paramPanelFeatureState.qwertyMode);
        paramPanelFeatureState.menu.startDispatchingItemsChanged();
        label625:
        paramPanelFeatureState.isPrepared = true;
        paramPanelFeatureState.isHandled = false;
        this.mPreparedPanel = paramPanelFeatureState;
        return true;
        j = -1;
        break;
      }
      label654:
      localObject = localContext;
    }
  }
  
  private void throwFeatureRequestIfSubDecorInstalled()
  {
    if (this.mSubDecorInstalled) {
      throw new AndroidRuntimeException("Window feature must be requested before adding content");
    }
  }
  
  public final void addContentView(View paramView, ViewGroup.LayoutParams paramLayoutParams)
  {
    ensureSubDecor();
    ((ViewGroup)this.mSubDecor.findViewById(16908290)).addView(paramView, paramLayoutParams);
    this.mOriginalWindowCallback.onContentChanged();
  }
  
  View callActivityOnCreateView$1fef4371(String paramString, Context paramContext, AttributeSet paramAttributeSet)
  {
    if ((this.mOriginalWindowCallback instanceof LayoutInflater.Factory))
    {
      View localView = ((LayoutInflater.Factory)this.mOriginalWindowCallback).onCreateView(paramString, paramContext, paramAttributeSet);
      if (localView != null) {
        return localView;
      }
    }
    return null;
  }
  
  final void callOnPanelClosed(int paramInt, PanelFeatureState paramPanelFeatureState, Menu paramMenu)
  {
    if (paramMenu == null)
    {
      if ((paramPanelFeatureState == null) && (paramInt >= 0) && (paramInt < this.mPanels.length)) {
        paramPanelFeatureState = this.mPanels[paramInt];
      }
      if (paramPanelFeatureState != null) {
        paramMenu = paramPanelFeatureState.menu;
      }
    }
    if ((paramPanelFeatureState != null) && (!paramPanelFeatureState.isOpen)) {}
    while (this.mIsDestroyed) {
      return;
    }
    this.mOriginalWindowCallback.onPanelClosed(paramInt, paramMenu);
  }
  
  final void checkCloseActionMenu(MenuBuilder paramMenuBuilder)
  {
    if (this.mClosingActionMenu) {
      return;
    }
    this.mClosingActionMenu = true;
    this.mDecorContentParent.dismissPopups();
    Window.Callback localCallback = this.mWindow.getCallback();
    if ((localCallback != null) && (!this.mIsDestroyed)) {
      localCallback.onPanelClosed(108, paramMenuBuilder);
    }
    this.mClosingActionMenu = false;
  }
  
  final void closePanel(PanelFeatureState paramPanelFeatureState, boolean paramBoolean)
  {
    if ((paramBoolean) && (paramPanelFeatureState.featureId == 0) && (this.mDecorContentParent != null) && (this.mDecorContentParent.isOverflowMenuShowing())) {
      checkCloseActionMenu(paramPanelFeatureState.menu);
    }
    do
    {
      return;
      WindowManager localWindowManager = (WindowManager)this.mContext.getSystemService("window");
      if ((localWindowManager != null) && (paramPanelFeatureState.isOpen) && (paramPanelFeatureState.decorView != null))
      {
        localWindowManager.removeView(paramPanelFeatureState.decorView);
        if (paramBoolean) {
          callOnPanelClosed(paramPanelFeatureState.featureId, paramPanelFeatureState, null);
        }
      }
      paramPanelFeatureState.isPrepared = false;
      paramPanelFeatureState.isHandled = false;
      paramPanelFeatureState.isOpen = false;
      paramPanelFeatureState.shownPanelView = null;
      paramPanelFeatureState.refreshDecorView = true;
    } while (this.mPreparedPanel != paramPanelFeatureState);
    this.mPreparedPanel = null;
  }
  
  final boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    if ((paramKeyEvent.getKeyCode() == 82) && (this.mOriginalWindowCallback.dispatchKeyEvent(paramKeyEvent))) {
      return true;
    }
    int i = paramKeyEvent.getKeyCode();
    int j;
    if (paramKeyEvent.getAction() == 0) {
      j = 1;
    }
    while (j != 0) {
      switch (i)
      {
      default: 
        if (Build.VERSION.SDK_INT < 11) {
          onKeyShortcut(i, paramKeyEvent);
        }
        return false;
        j = 0;
        break;
      case 82: 
        if (paramKeyEvent.getRepeatCount() == 0)
        {
          PanelFeatureState localPanelFeatureState3 = getPanelState$5103c037(0);
          if (!localPanelFeatureState3.isOpen) {
            preparePanel(localPanelFeatureState3, paramKeyEvent);
          }
        }
        return true;
      }
    }
    PanelFeatureState localPanelFeatureState2;
    boolean bool1;
    switch (i)
    {
    default: 
      return false;
    case 82: 
      if (this.mActionMode == null)
      {
        localPanelFeatureState2 = getPanelState$5103c037(0);
        if ((this.mDecorContentParent == null) || (!this.mDecorContentParent.canShowOverflowMenu()) || (ViewConfigurationCompat.hasPermanentMenuKey(ViewConfiguration.get(this.mContext)))) {
          break label275;
        }
        if (this.mDecorContentParent.isOverflowMenuShowing()) {
          break label261;
        }
        if ((this.mIsDestroyed) || (!preparePanel(localPanelFeatureState2, paramKeyEvent))) {
          break;
        }
        bool1 = this.mDecorContentParent.showOverflowMenu();
      }
      for (;;)
      {
        label228:
        if (bool1)
        {
          AudioManager localAudioManager = (AudioManager)this.mContext.getSystemService("audio");
          if (localAudioManager == null) {
            break label357;
          }
          localAudioManager.playSoundEffect(0);
        }
        label259:
        return true;
        label261:
        bool1 = this.mDecorContentParent.hideOverflowMenu();
        continue;
        label275:
        if ((!localPanelFeatureState2.isOpen) && (!localPanelFeatureState2.isHandled)) {
          break;
        }
        bool1 = localPanelFeatureState2.isOpen;
        closePanel(localPanelFeatureState2, true);
      }
      if (localPanelFeatureState2.isPrepared)
      {
        if (!localPanelFeatureState2.refreshMenuContent) {
          break label460;
        }
        localPanelFeatureState2.isPrepared = false;
      }
      break;
    }
    label460:
    for (boolean bool2 = preparePanel(localPanelFeatureState2, paramKeyEvent);; bool2 = true)
    {
      if (bool2)
      {
        openPanel(localPanelFeatureState2, paramKeyEvent);
        bool1 = true;
        break label228;
        label357:
        Log.w("AppCompatDelegate", "Couldn't get audio manager");
        break label259;
        PanelFeatureState localPanelFeatureState1 = getPanelState$5103c037(0);
        if ((localPanelFeatureState1 != null) && (localPanelFeatureState1.isOpen))
        {
          closePanel(localPanelFeatureState1, true);
          return true;
        }
        int k;
        if (this.mActionMode != null)
        {
          this.mActionMode.finish();
          k = 1;
        }
        while (k != 0)
        {
          return true;
          ActionBar localActionBar = getSupportActionBar();
          if ((localActionBar != null) && (localActionBar.collapseActionView())) {
            k = 1;
          } else {
            k = 0;
          }
        }
        break;
      }
      bool1 = false;
      break label228;
    }
  }
  
  final void endOnGoingFadeAnimation()
  {
    if (this.mFadeAnim != null) {
      this.mFadeAnim.cancel();
    }
  }
  
  final PanelFeatureState findMenuPanel(Menu paramMenu)
  {
    PanelFeatureState[] arrayOfPanelFeatureState = this.mPanels;
    int i;
    if (arrayOfPanelFeatureState != null) {
      i = arrayOfPanelFeatureState.length;
    }
    for (int j = 0;; j++)
    {
      if (j >= i) {
        break label55;
      }
      PanelFeatureState localPanelFeatureState = arrayOfPanelFeatureState[j];
      if ((localPanelFeatureState != null) && (localPanelFeatureState.menu == paramMenu))
      {
        return localPanelFeatureState;
        i = 0;
        break;
      }
    }
    label55:
    return null;
  }
  
  final PanelFeatureState getPanelState$5103c037(int paramInt)
  {
    Object localObject = this.mPanels;
    if ((localObject == null) || (localObject.length <= paramInt))
    {
      PanelFeatureState[] arrayOfPanelFeatureState = new PanelFeatureState[paramInt + 1];
      if (localObject != null) {
        System.arraycopy(localObject, 0, arrayOfPanelFeatureState, 0, localObject.length);
      }
      localObject = arrayOfPanelFeatureState;
      this.mPanels = arrayOfPanelFeatureState;
    }
    PanelFeatureState localPanelFeatureState = localObject[paramInt];
    if (localPanelFeatureState == null)
    {
      localPanelFeatureState = new PanelFeatureState(paramInt);
      localObject[paramInt] = localPanelFeatureState;
    }
    return localPanelFeatureState;
  }
  
  public final void initWindowDecorActionBar()
  {
    ensureSubDecor();
    if ((!this.mHasActionBar) || (this.mActionBar != null)) {}
    for (;;)
    {
      return;
      if ((this.mOriginalWindowCallback instanceof Activity)) {
        this.mActionBar = new WindowDecorActionBar((Activity)this.mOriginalWindowCallback, this.mOverlayActionBar);
      }
      while (this.mActionBar != null)
      {
        this.mActionBar.setDefaultDisplayHomeAsUpEnabled(this.mEnableDefaultActionBarUp);
        return;
        if ((this.mOriginalWindowCallback instanceof Dialog)) {
          this.mActionBar = new WindowDecorActionBar((Dialog)this.mOriginalWindowCallback);
        }
      }
    }
  }
  
  public final void installViewFactory()
  {
    LayoutInflater localLayoutInflater = LayoutInflater.from(this.mContext);
    if (localLayoutInflater.getFactory() == null)
    {
      LayoutInflaterCompat.setFactory(localLayoutInflater, this);
      return;
    }
    Log.i("AppCompatDelegate", "The Activity's LayoutInflater already has a Factory installed so we can not install AppCompat's");
  }
  
  public final void invalidateOptionsMenu()
  {
    ActionBar localActionBar = getSupportActionBar();
    if ((localActionBar != null) && (localActionBar.invalidateOptionsMenu())) {
      return;
    }
    invalidatePanelMenu(0);
  }
  
  public final void onConfigurationChanged(Configuration paramConfiguration)
  {
    if ((this.mHasActionBar) && (this.mSubDecorInstalled))
    {
      ActionBar localActionBar = getSupportActionBar();
      if (localActionBar != null) {
        localActionBar.onConfigurationChanged(paramConfiguration);
      }
    }
  }
  
  public final void onCreate$79e5e33f()
  {
    this.mWindowDecor = ((ViewGroup)this.mWindow.getDecorView());
    ActionBar localActionBar;
    if (((this.mOriginalWindowCallback instanceof Activity)) && (NavUtils.getParentActivityName((Activity)this.mOriginalWindowCallback) != null))
    {
      localActionBar = this.mActionBar;
      if (localActionBar == null) {
        this.mEnableDefaultActionBarUp = true;
      }
    }
    else
    {
      return;
    }
    localActionBar.setDefaultDisplayHomeAsUpEnabled(true);
  }
  
  public final View onCreateView(View paramView, String paramString, Context paramContext, AttributeSet paramAttributeSet)
  {
    Object localObject = callActivityOnCreateView$1fef4371(paramString, paramContext, paramAttributeSet);
    if (localObject != null) {
      label15:
      return localObject;
    }
    if (Build.VERSION.SDK_INT < 21) {}
    ViewParent localViewParent1;
    int k;
    int i;
    label81:
    Context localContext1;
    label102:
    Context localContext2;
    label232:
    int j;
    for (boolean bool = true;; bool = false)
    {
      if (this.mAppCompatViewInflater == null) {
        this.mAppCompatViewInflater = new AppCompatViewInflater();
      }
      if ((!bool) || (!this.mSubDecorInstalled)) {
        break label406;
      }
      localViewParent1 = (ViewParent)paramView;
      if (localViewParent1 != null) {
        break label841;
      }
      k = 0;
      if (k == 0) {
        break label406;
      }
      i = 1;
      AppCompatViewInflater localAppCompatViewInflater = this.mAppCompatViewInflater;
      if ((i == 0) || (paramView == null)) {
        break label835;
      }
      localContext1 = paramView.getContext();
      localContext2 = AppCompatViewInflater.themifyContext(localContext1, paramAttributeSet, bool, true);
      switch (paramString.hashCode())
      {
      default: 
        j = -1;
        label235:
        localObject = null;
        switch (j)
        {
        default: 
          label308:
          if ((localObject == null) && (paramContext != localContext2)) {
            localObject = localAppCompatViewInflater.createViewFromTag(localContext2, paramString, paramAttributeSet);
          }
          if (localObject == null) {
            break label15;
          }
          AppCompatViewInflater.checkOnClickListener((View)localObject, paramAttributeSet);
          return localObject;
        }
        break;
      }
    }
    label352:
    for (ViewParent localViewParent2 = localViewParent2.getParent();; localViewParent2 = localViewParent1)
    {
      if (localViewParent2 == null)
      {
        k = 1;
        break;
      }
      if ((localViewParent2 != this.mWindowDecor) && ((localViewParent2 instanceof View)) && (!ViewCompat.isAttachedToWindow((View)localViewParent2))) {
        break label352;
      }
      k = 0;
      break;
      i = 0;
      break label81;
      if (!paramString.equals("TextView")) {
        break label232;
      }
      j = 0;
      break label235;
      if (!paramString.equals("ImageView")) {
        break label232;
      }
      j = 1;
      break label235;
      if (!paramString.equals("Button")) {
        break label232;
      }
      j = 2;
      break label235;
      if (!paramString.equals("EditText")) {
        break label232;
      }
      j = 3;
      break label235;
      if (!paramString.equals("Spinner")) {
        break label232;
      }
      j = 4;
      break label235;
      if (!paramString.equals("ImageButton")) {
        break label232;
      }
      j = 5;
      break label235;
      if (!paramString.equals("CheckBox")) {
        break label232;
      }
      j = 6;
      break label235;
      if (!paramString.equals("RadioButton")) {
        break label232;
      }
      j = 7;
      break label235;
      if (!paramString.equals("CheckedTextView")) {
        break label232;
      }
      j = 8;
      break label235;
      if (!paramString.equals("AutoCompleteTextView")) {
        break label232;
      }
      j = 9;
      break label235;
      if (!paramString.equals("MultiAutoCompleteTextView")) {
        break label232;
      }
      j = 10;
      break label235;
      if (!paramString.equals("RatingBar")) {
        break label232;
      }
      j = 11;
      break label235;
      if (!paramString.equals("SeekBar")) {
        break label232;
      }
      j = 12;
      break label235;
      localObject = new AppCompatTextView(localContext2, paramAttributeSet);
      break label308;
      localObject = new AppCompatImageView(localContext2, paramAttributeSet);
      break label308;
      localObject = new AppCompatButton(localContext2, paramAttributeSet);
      break label308;
      localObject = new AppCompatEditText(localContext2, paramAttributeSet);
      break label308;
      localObject = new AppCompatSpinner(localContext2, paramAttributeSet);
      break label308;
      localObject = new AppCompatImageButton(localContext2, paramAttributeSet);
      break label308;
      localObject = new AppCompatCheckBox(localContext2, paramAttributeSet);
      break label308;
      localObject = new AppCompatRadioButton(localContext2, paramAttributeSet);
      break label308;
      localObject = new AppCompatCheckedTextView(localContext2, paramAttributeSet);
      break label308;
      localObject = new AppCompatAutoCompleteTextView(localContext2, paramAttributeSet);
      break label308;
      localObject = new AppCompatMultiAutoCompleteTextView(localContext2, paramAttributeSet);
      break label308;
      localObject = new AppCompatRatingBar(localContext2, paramAttributeSet);
      break label308;
      localObject = new AppCompatSeekBar(localContext2, paramAttributeSet);
      break label308;
      localContext1 = paramContext;
      break label102;
    }
  }
  
  final boolean onKeyShortcut(int paramInt, KeyEvent paramKeyEvent)
  {
    ActionBar localActionBar = getSupportActionBar();
    if ((localActionBar != null) && (localActionBar.onKeyShortcut(paramInt, paramKeyEvent))) {}
    boolean bool;
    do
    {
      do
      {
        return true;
        if ((this.mPreparedPanel == null) || (!performPanelShortcut$1f243752(this.mPreparedPanel, paramKeyEvent.getKeyCode(), paramKeyEvent))) {
          break;
        }
      } while (this.mPreparedPanel == null);
      this.mPreparedPanel.isHandled = true;
      return true;
      if (this.mPreparedPanel != null) {
        break;
      }
      PanelFeatureState localPanelFeatureState = getPanelState$5103c037(0);
      preparePanel(localPanelFeatureState, paramKeyEvent);
      bool = performPanelShortcut$1f243752(localPanelFeatureState, paramKeyEvent.getKeyCode(), paramKeyEvent);
      localPanelFeatureState.isPrepared = false;
    } while (bool);
    return false;
  }
  
  public final boolean onMenuItemSelected(MenuBuilder paramMenuBuilder, MenuItem paramMenuItem)
  {
    Window.Callback localCallback = this.mWindow.getCallback();
    if ((localCallback != null) && (!this.mIsDestroyed))
    {
      PanelFeatureState localPanelFeatureState = findMenuPanel(paramMenuBuilder.getRootMenu());
      if (localPanelFeatureState != null) {
        return localCallback.onMenuItemSelected(localPanelFeatureState.featureId, paramMenuItem);
      }
    }
    return false;
  }
  
  public final void onMenuModeChange(MenuBuilder paramMenuBuilder)
  {
    if ((this.mDecorContentParent != null) && (this.mDecorContentParent.canShowOverflowMenu()) && ((!ViewConfigurationCompat.hasPermanentMenuKey(ViewConfiguration.get(this.mContext))) || (this.mDecorContentParent.isOverflowMenuShowPending())))
    {
      Window.Callback localCallback = this.mWindow.getCallback();
      if (!this.mDecorContentParent.isOverflowMenuShowing()) {
        if ((localCallback != null) && (!this.mIsDestroyed))
        {
          if ((this.mInvalidatePanelMenuPosted) && ((0x1 & this.mInvalidatePanelMenuFeatures) != 0))
          {
            this.mWindowDecor.removeCallbacks(this.mInvalidatePanelMenuRunnable);
            this.mInvalidatePanelMenuRunnable.run();
          }
          PanelFeatureState localPanelFeatureState2 = getPanelState$5103c037(0);
          if ((localPanelFeatureState2.menu != null) && (!localPanelFeatureState2.refreshMenuContent) && (localCallback.onPreparePanel(0, localPanelFeatureState2.createdPanelView, localPanelFeatureState2.menu)))
          {
            localCallback.onMenuOpened(108, localPanelFeatureState2.menu);
            this.mDecorContentParent.showOverflowMenu();
          }
        }
      }
      do
      {
        return;
        this.mDecorContentParent.hideOverflowMenu();
      } while (this.mIsDestroyed);
      localCallback.onPanelClosed(108, getPanelState$5103c037(0).menu);
      return;
    }
    PanelFeatureState localPanelFeatureState1 = getPanelState$5103c037(0);
    localPanelFeatureState1.refreshDecorView = true;
    closePanel(localPanelFeatureState1, false);
    openPanel(localPanelFeatureState1, null);
  }
  
  final boolean onMenuOpened$2fef5512(int paramInt)
  {
    if (paramInt == 108)
    {
      ActionBar localActionBar = getSupportActionBar();
      if (localActionBar != null) {
        localActionBar.dispatchMenuVisibilityChanged(true);
      }
      return true;
    }
    return false;
  }
  
  final void onPanelClosed$2fef5516(int paramInt)
  {
    if (paramInt == 108)
    {
      ActionBar localActionBar = getSupportActionBar();
      if (localActionBar != null) {
        localActionBar.dispatchMenuVisibilityChanged(false);
      }
    }
    PanelFeatureState localPanelFeatureState;
    do
    {
      do
      {
        return;
      } while (paramInt != 0);
      localPanelFeatureState = getPanelState$5103c037(paramInt);
    } while (!localPanelFeatureState.isOpen);
    closePanel(localPanelFeatureState, false);
  }
  
  public final void onPostCreate$79e5e33f()
  {
    ensureSubDecor();
  }
  
  public final void onPostResume()
  {
    ActionBar localActionBar = getSupportActionBar();
    if (localActionBar != null) {
      localActionBar.setShowHideAnimationEnabled(true);
    }
  }
  
  public final void onStop()
  {
    ActionBar localActionBar = getSupportActionBar();
    if (localActionBar != null) {
      localActionBar.setShowHideAnimationEnabled(false);
    }
  }
  
  final void onTitleChanged(CharSequence paramCharSequence)
  {
    if (this.mDecorContentParent != null) {
      this.mDecorContentParent.setWindowTitle(paramCharSequence);
    }
    do
    {
      return;
      if (this.mActionBar != null)
      {
        this.mActionBar.setWindowTitle(paramCharSequence);
        return;
      }
    } while (this.mTitleView == null);
    this.mTitleView.setText(paramCharSequence);
  }
  
  public final boolean requestWindowFeature(int paramInt)
  {
    if (paramInt == 8)
    {
      Log.i("AppCompatDelegate", "You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR id when requesting this feature.");
      paramInt = 108;
    }
    while ((this.mWindowNoTitle) && (paramInt == 108))
    {
      return false;
      if (paramInt == 9)
      {
        Log.i("AppCompatDelegate", "You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR_OVERLAY id when requesting this feature.");
        paramInt = 109;
      }
    }
    if ((this.mHasActionBar) && (paramInt == 1)) {
      this.mHasActionBar = false;
    }
    switch (paramInt)
    {
    default: 
      return this.mWindow.requestFeature(paramInt);
    case 108: 
      throwFeatureRequestIfSubDecorInstalled();
      this.mHasActionBar = true;
      return true;
    case 109: 
      throwFeatureRequestIfSubDecorInstalled();
      this.mOverlayActionBar = true;
      return true;
    case 10: 
      throwFeatureRequestIfSubDecorInstalled();
      this.mOverlayActionMode = true;
      return true;
    case 2: 
      throwFeatureRequestIfSubDecorInstalled();
      this.mFeatureProgress = true;
      return true;
    case 5: 
      throwFeatureRequestIfSubDecorInstalled();
      this.mFeatureIndeterminateProgress = true;
      return true;
    }
    throwFeatureRequestIfSubDecorInstalled();
    this.mWindowNoTitle = true;
    return true;
  }
  
  public final void setContentView(int paramInt)
  {
    ensureSubDecor();
    ViewGroup localViewGroup = (ViewGroup)this.mSubDecor.findViewById(16908290);
    localViewGroup.removeAllViews();
    LayoutInflater.from(this.mContext).inflate(paramInt, localViewGroup);
    this.mOriginalWindowCallback.onContentChanged();
  }
  
  public final void setContentView(View paramView)
  {
    ensureSubDecor();
    ViewGroup localViewGroup = (ViewGroup)this.mSubDecor.findViewById(16908290);
    localViewGroup.removeAllViews();
    localViewGroup.addView(paramView);
    this.mOriginalWindowCallback.onContentChanged();
  }
  
  public final void setContentView(View paramView, ViewGroup.LayoutParams paramLayoutParams)
  {
    ensureSubDecor();
    ViewGroup localViewGroup = (ViewGroup)this.mSubDecor.findViewById(16908290);
    localViewGroup.removeAllViews();
    localViewGroup.addView(paramView, paramLayoutParams);
    this.mOriginalWindowCallback.onContentChanged();
  }
  
  public final void setSupportActionBar(Toolbar paramToolbar)
  {
    if (!(this.mOriginalWindowCallback instanceof Activity)) {
      return;
    }
    if ((getSupportActionBar() instanceof WindowDecorActionBar)) {
      throw new IllegalStateException("This Activity already has an action bar supplied by the window decor. Do not request Window.FEATURE_SUPPORT_ACTION_BAR and set windowActionBar to false in your theme to use a Toolbar instead.");
    }
    this.mMenuInflater = null;
    ToolbarActionBar localToolbarActionBar = new ToolbarActionBar(paramToolbar, ((Activity)this.mContext).getTitle(), this.mAppCompatWindowCallback);
    this.mActionBar = localToolbarActionBar;
    this.mWindow.setCallback(localToolbarActionBar.mWindowCallback);
    localToolbarActionBar.invalidateOptionsMenu();
  }
  
  private final class ActionMenuPresenterCallback
    implements MenuPresenter.Callback
  {
    private ActionMenuPresenterCallback() {}
    
    public final void onCloseMenu(MenuBuilder paramMenuBuilder, boolean paramBoolean)
    {
      AppCompatDelegateImplV7.this.checkCloseActionMenu(paramMenuBuilder);
    }
    
    public final boolean onOpenSubMenu(MenuBuilder paramMenuBuilder)
    {
      Window.Callback localCallback = AppCompatDelegateImplV7.this.mWindow.getCallback();
      if (localCallback != null) {
        localCallback.onMenuOpened(108, paramMenuBuilder);
      }
      return true;
    }
  }
  
  final class ActionModeCallbackWrapperV7
    implements ActionMode.Callback
  {
    private ActionMode.Callback mWrapped;
    
    public ActionModeCallbackWrapperV7(ActionMode.Callback paramCallback)
    {
      this.mWrapped = paramCallback;
    }
    
    public final boolean onActionItemClicked(ActionMode paramActionMode, MenuItem paramMenuItem)
    {
      return this.mWrapped.onActionItemClicked(paramActionMode, paramMenuItem);
    }
    
    public final boolean onCreateActionMode(ActionMode paramActionMode, Menu paramMenu)
    {
      return this.mWrapped.onCreateActionMode(paramActionMode, paramMenu);
    }
    
    public final void onDestroyActionMode(ActionMode paramActionMode)
    {
      this.mWrapped.onDestroyActionMode(paramActionMode);
      if (AppCompatDelegateImplV7.this.mActionModePopup != null) {
        AppCompatDelegateImplV7.this.mWindow.getDecorView().removeCallbacks(AppCompatDelegateImplV7.this.mShowActionModePopup);
      }
      if (AppCompatDelegateImplV7.this.mActionModeView != null)
      {
        AppCompatDelegateImplV7.this.endOnGoingFadeAnimation();
        AppCompatDelegateImplV7.this.mFadeAnim = ViewCompat.animate(AppCompatDelegateImplV7.this.mActionModeView).alpha(0.0F);
        AppCompatDelegateImplV7.this.mFadeAnim.setListener(new ViewPropertyAnimatorListenerAdapter()
        {
          public final void onAnimationEnd(View paramAnonymousView)
          {
            AppCompatDelegateImplV7.this.mActionModeView.setVisibility(8);
            if (AppCompatDelegateImplV7.this.mActionModePopup != null) {
              AppCompatDelegateImplV7.this.mActionModePopup.dismiss();
            }
            for (;;)
            {
              AppCompatDelegateImplV7.this.mActionModeView.removeAllViews();
              AppCompatDelegateImplV7.this.mFadeAnim.setListener(null);
              AppCompatDelegateImplV7.this.mFadeAnim = null;
              return;
              if ((AppCompatDelegateImplV7.this.mActionModeView.getParent() instanceof View)) {
                ViewCompat.requestApplyInsets((View)AppCompatDelegateImplV7.this.mActionModeView.getParent());
              }
            }
          }
        });
      }
      AppCompatDelegateImplV7.this.mActionMode = null;
    }
    
    public final boolean onPrepareActionMode(ActionMode paramActionMode, Menu paramMenu)
    {
      return this.mWrapped.onPrepareActionMode(paramActionMode, paramMenu);
    }
  }
  
  private final class ListMenuDecorView
    extends ContentFrameLayout
  {
    public ListMenuDecorView(Context paramContext)
    {
      super();
    }
    
    public final boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
    {
      return (AppCompatDelegateImplV7.this.dispatchKeyEvent(paramKeyEvent)) || (super.dispatchKeyEvent(paramKeyEvent));
    }
    
    public final boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
    {
      if (paramMotionEvent.getAction() == 0)
      {
        int i = (int)paramMotionEvent.getX();
        int j = (int)paramMotionEvent.getY();
        if ((i < -5) || (j < -5) || (i > 5 + getWidth()) || (j > 5 + getHeight())) {}
        for (int k = 1; k != 0; k = 0)
        {
          AppCompatDelegateImplV7 localAppCompatDelegateImplV7 = AppCompatDelegateImplV7.this;
          localAppCompatDelegateImplV7.closePanel(localAppCompatDelegateImplV7.getPanelState$5103c037(0), true);
          return true;
        }
      }
      return super.onInterceptTouchEvent(paramMotionEvent);
    }
    
    public final void setBackgroundResource(int paramInt)
    {
      setBackgroundDrawable(TintManager.getDrawable(getContext(), paramInt));
    }
  }
  
  private static final class PanelFeatureState
  {
    int background;
    View createdPanelView;
    ViewGroup decorView;
    int featureId;
    Bundle frozenActionViewState;
    int gravity;
    boolean isHandled;
    boolean isOpen;
    boolean isPrepared;
    ListMenuPresenter listMenuPresenter;
    Context listPresenterContext;
    MenuBuilder menu;
    public boolean qwertyMode;
    boolean refreshDecorView;
    boolean refreshMenuContent;
    View shownPanelView;
    int windowAnimations;
    int x;
    int y;
    
    PanelFeatureState(int paramInt)
    {
      this.featureId = paramInt;
      this.refreshDecorView = false;
    }
    
    final void setMenu(MenuBuilder paramMenuBuilder)
    {
      if (paramMenuBuilder == this.menu) {}
      do
      {
        return;
        if (this.menu != null) {
          this.menu.removeMenuPresenter(this.listMenuPresenter);
        }
        this.menu = paramMenuBuilder;
      } while ((paramMenuBuilder == null) || (this.listMenuPresenter == null));
      paramMenuBuilder.addMenuPresenter(this.listMenuPresenter);
    }
  }
  
  private final class PanelMenuPresenterCallback
    implements MenuPresenter.Callback
  {
    private PanelMenuPresenterCallback() {}
    
    public final void onCloseMenu(MenuBuilder paramMenuBuilder, boolean paramBoolean)
    {
      MenuBuilder localMenuBuilder = paramMenuBuilder.getRootMenu();
      if (localMenuBuilder != paramMenuBuilder) {}
      AppCompatDelegateImplV7.PanelFeatureState localPanelFeatureState;
      for (int i = 1;; i = 0)
      {
        AppCompatDelegateImplV7 localAppCompatDelegateImplV7 = AppCompatDelegateImplV7.this;
        if (i != 0) {
          paramMenuBuilder = localMenuBuilder;
        }
        localPanelFeatureState = localAppCompatDelegateImplV7.findMenuPanel(paramMenuBuilder);
        if (localPanelFeatureState != null)
        {
          if (i == 0) {
            break;
          }
          AppCompatDelegateImplV7.this.callOnPanelClosed(localPanelFeatureState.featureId, localPanelFeatureState, localMenuBuilder);
          AppCompatDelegateImplV7.this.closePanel(localPanelFeatureState, true);
        }
        return;
      }
      AppCompatDelegateImplV7.this.closePanel(localPanelFeatureState, paramBoolean);
    }
    
    public final boolean onOpenSubMenu(MenuBuilder paramMenuBuilder)
    {
      if ((paramMenuBuilder == null) && (AppCompatDelegateImplV7.this.mHasActionBar))
      {
        Window.Callback localCallback = AppCompatDelegateImplV7.this.mWindow.getCallback();
        if ((localCallback != null) && (!AppCompatDelegateImplV7.this.mIsDestroyed)) {
          localCallback.onMenuOpened(108, paramMenuBuilder);
        }
      }
      return true;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.app.AppCompatDelegateImplV7
 * JD-Core Version:    0.7.0.1
 */