package com.google.android.finsky.layout.actionbar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager.TaskDescription;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.PaintDrawable;
import android.os.Build.VERSION;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.MainActivity;
import com.google.android.finsky.activities.TextSectionTranslatable;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.appstate.AppStates;
import com.google.android.finsky.appstate.AppStates.AppState;
import com.google.android.finsky.appstate.GmsCoreHelper;
import com.google.android.finsky.appstate.PackageStateRepository.PackageState;
import com.google.android.finsky.config.PreferenceFile.PrefixSharedPreference;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.layout.play.FinskyDrawerLayout;
import com.google.android.finsky.layout.play.GenericUiElementNode;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.navigationmanager.NavigationState;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.Toc.CorpusMetadata;
import com.google.android.finsky.protos.Toc.TocResponse;
import com.google.android.finsky.receivers.Installer;
import com.google.android.finsky.utils.AutoUpdateUtils;
import com.google.android.finsky.utils.CorpusResourceUtils;
import com.google.android.finsky.utils.DotNotificationUtils;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.finsky.utils.Utils;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.image.BitmapLoader.BitmapContainer;
import com.google.android.play.image.BitmapLoader.BitmapLoadedHandler;
import com.google.android.play.search.PlaySearch;
import com.google.android.play.search.PlaySearchNavigationButton;
import com.google.android.play.search.PlaySearchPlate;
import com.google.android.play.search.PlaySearchToolbar;
import com.google.android.play.utils.PlayCorpusUtils;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public final class ActionBarHelper
{
  public static final boolean IS_SEARCH_ALWAYS_VISIBLE;
  private static Map<Integer, SoftReference<Drawable>> sBackgroundCache;
  private static boolean sSawFirstBackstackChange;
  public ActionBar mActionBar;
  public ActionBarController mActionBarController;
  private Stack<ActionBarState> mActionBarStateStack;
  private Activity mActivity;
  public MenuItem mAutoUpdateItem;
  public int mCurrentBackendId;
  private Drawable mCurrentBackgroundDrawable;
  private CharSequence mCurrentTitle;
  private String mDefaultSearchQuery = "";
  public MenuItem mEnvironmentItem;
  public TextSectionTranslatable mExpandedModeTranslatable;
  private boolean mIgnoreActionBarBackground;
  public boolean mIsMenuConfigured;
  public PlayStoreUiElementNode mMainMenuUiElementNode;
  public NavigationManager mNavigationManager;
  private int mPrevRecentsBackendForColor = -1;
  private CharSequence mPrevRecentsTitle;
  private SoftReference<Bitmap> mRecentsIcon;
  public MenuItem mSearchItem;
  public SearchView mSearchView;
  public FinskySearchToolbar mToolbar;
  public MenuItem mTranslateItem;
  private final Drawable mTransparentBackgroundDrawable;
  
  static
  {
    if (Build.VERSION.SDK_INT < 11) {}
    for (boolean bool = true;; bool = false)
    {
      IS_SEARCH_ALWAYS_VISIBLE = bool;
      sBackgroundCache = new HashMap();
      sSawFirstBackstackChange = false;
      return;
    }
  }
  
  public ActionBarHelper(NavigationManager paramNavigationManager, ActionBarActivity paramActionBarActivity)
  {
    this(paramNavigationManager, null, paramActionBarActivity);
  }
  
  public ActionBarHelper(NavigationManager paramNavigationManager, ActionBarController paramActionBarController, ActionBarActivity paramActionBarActivity)
  {
    this.mActionBar = paramActionBarActivity.getDelegate().getSupportActionBar();
    this.mToolbar = ((FinskySearchToolbar)paramActionBarActivity.findViewById(2131755196));
    this.mActivity = paramActionBarActivity;
    this.mNavigationManager = paramNavigationManager;
    this.mActionBarController = paramActionBarController;
    this.mActionBarStateStack = new Stack();
    this.mActionBarStateStack.push(new ActionBarState(0, null));
    this.mCurrentBackendId = 0;
    if (this.mToolbar != null)
    {
      this.mToolbar.setCurrentBackendId(this.mCurrentBackendId);
      this.mToolbar.setNavigationManager(this.mNavigationManager);
      this.mToolbar.setActionBarController(this.mActionBarController);
      this.mToolbar.setVisibility(4);
    }
    if (this.mToolbar != null) {
      if ((!DotNotificationUtils.shouldShowAccountCompletionDotNotification()) || (((Integer)FinskyPreferences.accountCompletionMainMenuDotTapCount.get(FinskyApp.get().getCurrentAccountName()).get()).intValue() != 0)) {
        break label306;
      }
    }
    label306:
    for (int i = 1;; i = 0)
    {
      if (i != 0)
      {
        View localView = this.mToolbar.getSearchView().findViewById(2131755724);
        if (localView != null) {
          localView.setVisibility(0);
        }
        this.mMainMenuUiElementNode = new GenericUiElementNode(5301, null, null, new GenericUiElementNode(5300, null, null, this.mNavigationManager.getActivePage()));
        FinskyApp.get().getEventLogger().logPathImpression$7d139cbf(299, this.mMainMenuUiElementNode);
      }
      if (this.mActionBar != null) {
        this.mActionBar.setBackgroundDrawable(getBackgroundColorDrawable(CorpusResourceUtils.getPrimaryColor(this.mActivity, 0)));
      }
      this.mTransparentBackgroundDrawable = new ColorDrawable(0);
      paramNavigationManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener()
      {
        public final void onBackStackChanged()
        {
          if (ActionBarHelper.sSawFirstBackstackChange) {
            ActionBarHelper.this.clearSearch();
          }
          for (;;)
          {
            ActionBarHelper.this.syncState();
            return;
            ActionBarHelper.access$002$138603();
          }
        }
      });
      return;
    }
  }
  
  private static Drawable getBackgroundColorDrawable(int paramInt)
  {
    SoftReference localSoftReference = (SoftReference)sBackgroundCache.get(Integer.valueOf(paramInt));
    if ((localSoftReference == null) || (localSoftReference.get() == null))
    {
      localSoftReference = new SoftReference(new PaintDrawable(paramInt));
      sBackgroundCache.put(Integer.valueOf(paramInt), localSoftReference);
    }
    return (Drawable)localSoftReference.get();
  }
  
  private void setTitle(CharSequence paramCharSequence)
  {
    if (this.mActionBar != null)
    {
      this.mCurrentTitle = paramCharSequence;
      this.mActionBar.setTitle(this.mCurrentTitle);
    }
  }
  
  @SuppressLint({"NewApi"})
  public final void clearSearch()
  {
    if (this.mSearchView == null) {}
    do
    {
      do
      {
        return;
      } while (this.mSearchView == null);
      this.mSearchView.setQuery("", false);
      this.mSearchView.setIconified(true);
    } while (!MenuItemCompat.isActionViewExpanded(this.mSearchItem));
    MenuItemCompat.collapseActionView(this.mSearchItem);
  }
  
  public final void enterActionBarTransientOpacityMode(int paramInt, CharSequence paramCharSequence)
  {
    if (isInMode(Integer.valueOf(paramInt))) {
      return;
    }
    this.mActionBarStateStack.push(new ActionBarState(paramInt, paramCharSequence));
  }
  
  public final void exitCurrentActionBarMode()
  {
    this.mActionBarStateStack.pop();
  }
  
  public final boolean isInMode(Integer paramInteger)
  {
    return ((ActionBarState)this.mActionBarStateStack.peek()).mode == paramInteger.intValue();
  }
  
  public final void overrideSearchBoxWidth(int paramInt)
  {
    if (this.mToolbar != null) {
      this.mToolbar.setSearchBoxFixedWidth(paramInt);
    }
  }
  
  public final void removeModeFromStack(Integer paramInteger)
  {
    int i = this.mActionBarStateStack.size();
    for (int j = 0;; j++) {
      if (j < i)
      {
        if (((ActionBarState)this.mActionBarStateStack.get(j)).mode == paramInteger.intValue()) {
          this.mActionBarStateStack.remove(j);
        }
      }
      else {
        return;
      }
    }
  }
  
  public final void setDefaultSearchQuery(String paramString)
  {
    this.mDefaultSearchQuery = paramString;
    if (this.mToolbar != null) {
      this.mToolbar.setQuery(this.mDefaultSearchQuery);
    }
  }
  
  public final void syncActions()
  {
    boolean bool1 = isInMode(Integer.valueOf(2));
    boolean bool2;
    if (this.mActionBar != null)
    {
      ActionBar localActionBar = this.mActionBar;
      if (!this.mNavigationManager.isBackStackEmpty())
      {
        bool2 = true;
        localActionBar.setDisplayHomeAsUpEnabled(bool2);
      }
    }
    else
    {
      if (this.mIsMenuConfigured) {
        break label56;
      }
    }
    label56:
    do
    {
      return;
      bool2 = false;
      break;
      if (bool1) {
        break label95;
      }
      syncDetailsPageMenuItem();
    } while ((this.mSearchItem == null) || (IS_SEARCH_ALWAYS_VISIBLE));
    this.mSearchItem.setVisible(this.mNavigationManager.canSearch());
    return;
    label95:
    if (!IS_SEARCH_ALWAYS_VISIBLE) {
      this.mSearchItem.setVisible(false);
    }
    this.mAutoUpdateItem.setVisible(false);
    this.mEnvironmentItem.setVisible(false);
    if (this.mExpandedModeTranslatable != null)
    {
      this.mTranslateItem.setVisible(this.mExpandedModeTranslatable.hasTranslation());
      MenuItem localMenuItem = this.mTranslateItem;
      if (this.mExpandedModeTranslatable.isShowingTranslation()) {}
      for (int i = 2131362664;; i = 2131362803)
      {
        localMenuItem.setTitle(i);
        return;
      }
    }
    this.mTranslateItem.setVisible(false);
  }
  
  public final void syncDetailsPageMenuItem()
  {
    if (this.mTranslateItem != null) {
      this.mTranslateItem.setVisible(false);
    }
    boolean bool1;
    Document localDocument;
    String str;
    Libraries localLibraries;
    AppStates localAppStates;
    Installer localInstaller;
    int i;
    if (this.mNavigationManager.getCurrentPageType() == 5)
    {
      bool1 = true;
      localDocument = this.mNavigationManager.getCurrentDocument();
      if (this.mAutoUpdateItem != null) {
        if ((localDocument != null) && (localDocument.mDocument.backendId == 3))
        {
          str = localDocument.mDocument.docid;
          localLibraries = FinskyApp.get().mLibraries;
          localAppStates = FinskyApp.get().mAppStates;
          localInstaller = FinskyApp.get().mInstaller;
          if (str != null) {
            break label124;
          }
          i = 0;
        }
      }
    }
    label226:
    for (;;)
    {
      if (i != 0) {
        break label254;
      }
      this.mAutoUpdateItem.setVisible(false);
      return;
      bool1 = false;
      break;
      label124:
      if (GmsCoreHelper.isGmsCore(str))
      {
        i = 0;
      }
      else if (localLibraries.getAppOwners(str).isEmpty())
      {
        i = 0;
      }
      else
      {
        AppStates.AppState localAppState = localAppStates.getApp(str);
        if (localAppState == null)
        {
          i = 0;
        }
        else
        {
          if (localAppState.packageManagerState != null) {}
          for (int j = 1;; j = 0)
          {
            int k = localInstaller.getState(str);
            if ((j != 0) || (Utils.isDownloadingOrInstalling(k))) {
              break label226;
            }
            i = 0;
            break;
          }
          if ((j != 0) && (localAppState.packageManagerState.isDisabled)) {
            i = 0;
          } else {
            i = 1;
          }
        }
      }
    }
    label254:
    boolean bool2 = AutoUpdateUtils.isAutoUpdateEnabled(localDocument.mDocument.docid);
    this.mAutoUpdateItem.setTitle(2131361838);
    this.mAutoUpdateItem.setCheckable(true);
    this.mAutoUpdateItem.setChecked(bool2);
    this.mAutoUpdateItem.setVisible(bool1);
  }
  
  public final void syncState()
  {
    CharSequence localCharSequence1 = ((ActionBarState)this.mActionBarStateStack.peek()).title;
    label138:
    MainActivity localMainActivity;
    int k;
    NavigationManager localNavigationManager;
    boolean bool;
    if (!TextUtils.isEmpty(localCharSequence1))
    {
      setTitle(localCharSequence1);
      if (this.mEnvironmentItem != null)
      {
        DfeToc localDfeToc2 = FinskyApp.get().mToc;
        if ((localDfeToc2 == null) || (!localDfeToc2.mToc.hasIconOverrideUrl)) {
          break label655;
        }
        BitmapLoader.BitmapContainer localBitmapContainer = FinskyApp.get().mBitmapLoader.get(localDfeToc2.mToc.iconOverrideUrl, 0, 0, new BitmapLoader.BitmapLoadedHandler()
        {
          public final void onResponse(BitmapLoader.BitmapContainer paramAnonymousBitmapContainer)
          {
            BitmapDrawable localBitmapDrawable = new BitmapDrawable(ActionBarHelper.this.mActivity.getResources(), paramAnonymousBitmapContainer.mBitmap);
            ActionBarHelper.this.mEnvironmentItem.setIcon(localBitmapDrawable);
            ActionBarHelper.this.mEnvironmentItem.setVisible(true);
          }
        });
        if (localBitmapContainer.mBitmap != null)
        {
          BitmapDrawable localBitmapDrawable = new BitmapDrawable(this.mActivity.getResources(), localBitmapContainer.mBitmap);
          this.mEnvironmentItem.setIcon(localBitmapDrawable);
          this.mEnvironmentItem.setVisible(true);
        }
      }
      if (Build.VERSION.SDK_INT >= 21)
      {
        if (!(this.mActivity instanceof MainActivity)) {
          break label688;
        }
        localMainActivity = (MainActivity)this.mActivity;
        k = CorpusResourceUtils.getPrimaryColor(this.mActivity, this.mCurrentBackendId);
        localNavigationManager = this.mNavigationManager;
        if (!localNavigationManager.mBackStack.isEmpty()) {
          break label669;
        }
        bool = false;
        label198:
        if (!bool) {
          break label759;
        }
      }
    }
    label221:
    label743:
    label752:
    label759:
    for (int m = ColorUtils.setAlphaComponent(k, 0);; m = k)
    {
      localMainActivity.mDrawerLayout.setStatusBarBackgroundColor(m);
      CharSequence localCharSequence2;
      if (Build.VERSION.SDK_INT >= 21)
      {
        localCharSequence2 = this.mCurrentTitle;
        if (TextUtils.isEmpty(localCharSequence2)) {
          localCharSequence2 = this.mActivity.getTitle();
        }
        if (!TextUtils.isEmpty(localCharSequence2)) {
          break label752;
        }
      }
      for (Object localObject = this.mActivity.getResources().getString(2131362289);; localObject = localCharSequence2)
      {
        int i;
        int j;
        Drawable localDrawable1;
        ActionBar localActionBar;
        if (this.mPrevRecentsBackendForColor == this.mCurrentBackendId)
        {
          i = 1;
          if ((this.mPrevRecentsTitle == null) || (!this.mPrevRecentsTitle.equals(localObject))) {
            break label718;
          }
          j = 1;
          if ((i == 0) || (j == 0))
          {
            if ((this.mRecentsIcon == null) || (this.mRecentsIcon.get() == null)) {
              this.mRecentsIcon = new SoftReference(BitmapFactory.decodeResource(this.mActivity.getResources(), 2130903045));
            }
            ActivityManager.TaskDescription localTaskDescription = new ActivityManager.TaskDescription(((CharSequence)localObject).toString(), (Bitmap)this.mRecentsIcon.get(), PlayCorpusUtils.getRecentsColor(this.mActivity, this.mCurrentBackendId));
            this.mActivity.setTaskDescription(localTaskDescription);
            this.mPrevRecentsBackendForColor = this.mCurrentBackendId;
            this.mPrevRecentsTitle = ((CharSequence)localObject);
          }
          if (!isInMode(Integer.valueOf(2))) {
            break label724;
          }
          Activity localActivity = this.mActivity;
          SoftReference localSoftReference = (SoftReference)sBackgroundCache.get(Integer.valueOf(2130837574));
          if ((localSoftReference == null) || (localSoftReference.get() == null))
          {
            localSoftReference = new SoftReference(new LayerDrawable(new Drawable[] { ContextCompat.getDrawable(localActivity, 2130837574) })
            {
              public final boolean getPadding(Rect paramAnonymousRect)
              {
                paramAnonymousRect.top = 0;
                paramAnonymousRect.bottom = 0;
                paramAnonymousRect.left = 0;
                paramAnonymousRect.right = 0;
                return false;
              }
            });
            sBackgroundCache.put(Integer.valueOf(2130837574), localSoftReference);
          }
          localDrawable1 = (Drawable)localSoftReference.get();
          this.mCurrentBackgroundDrawable = localDrawable1;
          if (this.mActionBar != null)
          {
            localActionBar = this.mActionBar;
            if (!this.mIgnoreActionBarBackground) {
              break label743;
            }
          }
        }
        for (Drawable localDrawable2 = this.mTransparentBackgroundDrawable;; localDrawable2 = this.mCurrentBackgroundDrawable)
        {
          localActionBar.setBackgroundDrawable(localDrawable2);
          syncActions();
          return;
          setTitle(this.mActivity.getString(2131362289));
          DfeToc localDfeToc1 = FinskyApp.get().mToc;
          if (this.mCurrentBackendId == 9)
          {
            setTitle(this.mActivity.getString(2131362762));
            break;
          }
          if ((localDfeToc1 == null) || (this.mCurrentBackendId == 0)) {
            break;
          }
          Toc.CorpusMetadata localCorpusMetadata = localDfeToc1.getCorpus(this.mCurrentBackendId);
          if (localCorpusMetadata == null) {
            break;
          }
          setTitle(localCorpusMetadata.name);
          break;
          this.mEnvironmentItem.setVisible(false);
          break label138;
          bool = ((NavigationState)localNavigationManager.mBackStack.peek()).isStatusBarOverlayEnabled;
          break label198;
          this.mActivity.getWindow().setStatusBarColor(CorpusResourceUtils.getStatusBarColor(this.mActivity, this.mCurrentBackendId));
          break label221;
          i = 0;
          break label289;
          j = 0;
          break label311;
          localDrawable1 = getBackgroundColorDrawable(CorpusResourceUtils.getPrimaryColor(this.mActivity, this.mCurrentBackendId));
          break label529;
        }
      }
    }
  }
  
  public final void updateActionBarMode(boolean paramBoolean, int paramInt)
  {
    if (this.mToolbar != null)
    {
      this.mToolbar.setMode(paramBoolean, paramInt);
      if (this.mToolbar.getVisibility() != 0) {
        this.mToolbar.setVisibility(0);
      }
    }
  }
  
  public final void updateCurrentBackendId(int paramInt, boolean paramBoolean)
  {
    this.mCurrentBackendId = paramInt;
    this.mIgnoreActionBarBackground = paramBoolean;
    if (this.mToolbar != null) {
      this.mToolbar.setCurrentBackendId(paramInt);
    }
    syncState();
  }
  
  public final void updateDefaultTitle(String paramString)
  {
    ((ActionBarState)this.mActionBarStateStack.get(0)).title = paramString;
    syncState();
  }
  
  public final void updateNavDawerState(boolean paramBoolean)
  {
    if (this.mToolbar != null)
    {
      PlaySearchNavigationButton localPlaySearchNavigationButton = this.mToolbar.mSearchView.mSearchPlate.mNavButton;
      if (localPlaySearchNavigationButton.mCurrentMode == 0) {
        localPlaySearchNavigationButton.updateContentDescription(localPlaySearchNavigationButton.mCurrentMode, paramBoolean);
      }
    }
  }
  
  private static final class ActionBarState
  {
    public int mode;
    public CharSequence title;
    
    public ActionBarState(int paramInt, CharSequence paramCharSequence)
    {
      this.mode = paramInt;
      this.title = paramCharSequence;
    }
    
    public final String toString()
    {
      return "[type: " + this.mode + ", title: " + this.title + "]";
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.actionbar.ActionBarHelper
 * JD-Core Version:    0.7.0.1
 */