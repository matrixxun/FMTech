package com.google.android.play.search;

import android.animation.Animator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.ActionProvider;
import android.support.v4.view.MenuItemCompat.OnActionExpandListener;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.Toolbar.LayoutParams;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import com.android.vending.R;
import com.google.android.play.R.dimen;
import com.google.android.play.R.layout;
import com.google.android.play.image.BitmapLoader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class PlaySearchToolbar
  extends Toolbar
  implements MenuItemCompat.OnActionExpandListener
{
  private PlaySearchToolbarActionListener mActionListener;
  private ActionProvider mActionProvider;
  private PlaySearch mActionView;
  private Drawable mBackground;
  public int mExpandedMenuItemId;
  private boolean mIsInSearchBoxOnlyMode;
  public View mLegacySearchActionView;
  private PlaySearchListener mListener;
  private int mOriginalPaddingBottom;
  private int mOriginalPaddingEnd;
  private int mOriginalPaddingStart;
  private int mOriginalPaddingTop;
  private final Map<View, Integer> mPreviousVisibility = new HashMap();
  private int mSearchBoxTopMargin;
  public MenuItem mSearchItem;
  public PlaySearch mSearchView;
  private Toolbar.LayoutParams mSearchViewLayoutParams;
  
  public PlaySearchToolbar(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    Resources localResources = getContext().getResources();
    this.mSearchBoxTopMargin = (localResources.getDimensionPixelSize(R.dimen.play_search_toolbar_padding_top) + localResources.getDimensionPixelSize(R.dimen.play_card_default_inset));
  }
  
  public static int getToolbarHeight(Context paramContext)
  {
    return paramContext.getResources().getDimensionPixelSize(R.dimen.play_search_toolbar_height);
  }
  
  private void updateHeightAndPadding()
  {
    updateHeightAndPadding(isSearchBoxPresent());
  }
  
  private void updateHeightAndPadding(boolean paramBoolean)
  {
    ViewGroup.LayoutParams localLayoutParams = getLayoutParams();
    int i;
    if (localLayoutParams != null)
    {
      if (!paramBoolean) {
        break label128;
      }
      i = -2;
      if (!paramBoolean) {
        break label139;
      }
    }
    label128:
    label139:
    for (int j = 0;; j = getToolbarHeight(getContext()))
    {
      if ((localLayoutParams.height != i) || (ViewCompat.getMinimumHeight(this) != j))
      {
        localLayoutParams.height = i;
        setMinimumHeight(j);
      }
      if (!paramBoolean) {
        break label151;
      }
      int k = ViewCompat.getPaddingStart(this);
      int m = ViewCompat.getPaddingEnd(this);
      if (k != 0) {
        this.mOriginalPaddingStart = k;
      }
      if (m != 0) {
        this.mOriginalPaddingEnd = m;
      }
      if (getPaddingTop() != 0) {
        this.mOriginalPaddingTop = getPaddingTop();
      }
      if (getPaddingBottom() != 0) {
        this.mOriginalPaddingBottom = getPaddingBottom();
      }
      setPadding(0, 0, 0, 0);
      return;
      i = getToolbarHeight(getContext());
      break;
    }
    label151:
    ViewCompat.setPaddingRelative(this, this.mOriginalPaddingStart, this.mOriginalPaddingTop, this.mOriginalPaddingEnd, this.mOriginalPaddingBottom);
  }
  
  public final void configure(Configurator paramConfigurator)
  {
    int i = paramConfigurator.getSearchBoxHorizontalMargin();
    this.mSearchView = ((PlaySearch)LayoutInflater.from(getContext()).inflate(paramConfigurator.getPlaySearchLayoutId(), this, false));
    this.mSearchViewLayoutParams = new Toolbar.LayoutParams((byte)0);
    this.mSearchView.setSearchBoxMargins(i, this.mSearchBoxTopMargin, i, 0, true);
    this.mSearchView.configure(paramConfigurator.getBitmapLoader());
    this.mSearchView.setSteadyStateMode(1);
    this.mSearchView.mController.switchToSteadyStateMode();
    this.mSearchView.setListener(new PlaySearchListener()
    {
      public final void onModeChanged(int paramAnonymousInt)
      {
        if (PlaySearchToolbar.this.mListener != null) {
          PlaySearchToolbar.this.mListener.onModeChanged(paramAnonymousInt);
        }
      }
      
      public final void onQueryChanged(String paramAnonymousString, boolean paramAnonymousBoolean)
      {
        if (PlaySearchToolbar.this.mActionView != null) {
          PlaySearchToolbar.this.mActionView.setQuery(paramAnonymousString);
        }
        if (PlaySearchToolbar.this.mListener != null) {
          PlaySearchToolbar.this.mListener.onQueryChanged(paramAnonymousString, paramAnonymousBoolean);
        }
      }
      
      public final void onSearch(String paramAnonymousString)
      {
        if (PlaySearchToolbar.this.mListener != null) {
          PlaySearchToolbar.this.mListener.onSearch(paramAnonymousString);
        }
      }
      
      public final void onSuggestionClicked(PlaySearchSuggestionModel paramAnonymousPlaySearchSuggestionModel)
      {
        if (PlaySearchToolbar.this.mListener != null) {
          PlaySearchToolbar.this.mListener.onSuggestionClicked(paramAnonymousPlaySearchSuggestionModel);
        }
      }
    });
    this.mBackground = getBackground();
    this.mActionView = paramConfigurator.createActionSearchView(this);
    this.mActionView.configure(paramConfigurator.getBitmapLoader());
    this.mActionView.setSteadyStateMode(2);
    this.mActionView.mController.switchToSteadyStateMode();
    this.mActionView.setListener(new PlaySearchListener()
    {
      public final void onModeChanged(int paramAnonymousInt)
      {
        PlaySearch localPlaySearch;
        Runnable local1;
        PlaySearch.3 local3;
        View localView;
        if ((paramAnonymousInt == 1) || (paramAnonymousInt == 2))
        {
          PlaySearchToolbar.this.onExitSearchMode();
          localPlaySearch = PlaySearchToolbar.this.mActionView;
          local1 = new Runnable()
          {
            public final void run()
            {
              PlaySearchToolbar.this.collapseActionView();
              PlaySearchToolbar.this.updateHeightAndPadding();
            }
          };
          if (!PlaySearch.CAN_USE_RIPPLE_ANIMATION) {
            break label175;
          }
          local3 = new PlaySearch.3(localPlaySearch, local1);
          localView = (View)localPlaySearch.getParent();
          if ((localView != null) && (localPlaySearch.mSearchContainer != null) && (localPlaySearch.mSearchContainer.isAttachedToWindow())) {
            break label106;
          }
        }
        for (;;)
        {
          if (PlaySearchToolbar.this.mListener != null) {
            PlaySearchToolbar.this.mListener.onModeChanged(paramAnonymousInt);
          }
          return;
          label106:
          Point localPoint = localPlaySearch.getRevealCenter();
          Animator localAnimator = ViewAnimationUtils.createCircularReveal(localPlaySearch.mSearchContainer, localPoint.x, localPoint.y, localView.getWidth(), 0.0F);
          localAnimator.setDuration(300L);
          localAnimator.addListener(new PlaySearch.5(localPlaySearch));
          localAnimator.addListener(local3);
          localAnimator.start();
          continue;
          label175:
          localPlaySearch.setVisibility(4);
          local1.run();
        }
      }
      
      public final void onQueryChanged(String paramAnonymousString, boolean paramAnonymousBoolean)
      {
        if (PlaySearchToolbar.this.mSearchView != null) {
          PlaySearchToolbar.this.mSearchView.setQuery(paramAnonymousString);
        }
        if (PlaySearchToolbar.this.mListener != null) {
          PlaySearchToolbar.this.mListener.onQueryChanged(paramAnonymousString, paramAnonymousBoolean);
        }
      }
      
      public final void onSearch(String paramAnonymousString)
      {
        if (PlaySearchToolbar.this.shouldKeepSearchActive()) {
          PlaySearchToolbar.this.setMode(true, 2);
        }
        for (;;)
        {
          if (PlaySearchToolbar.this.mListener != null) {
            PlaySearchToolbar.this.mListener.onSearch(paramAnonymousString);
          }
          return;
          PlaySearchToolbar.this.mActionView.mController.switchToSteadyStateMode();
        }
      }
      
      public final void onSuggestionClicked(PlaySearchSuggestionModel paramAnonymousPlaySearchSuggestionModel)
      {
        if (PlaySearchToolbar.this.shouldKeepSearchActive()) {
          PlaySearchToolbar.this.setMode(true, 2);
        }
        for (;;)
        {
          PlaySearchToolbar.this.mActionView.mController.switchToSteadyStateMode();
          if (PlaySearchToolbar.this.mListener != null) {
            PlaySearchToolbar.this.mListener.onSuggestionClicked(paramAnonymousPlaySearchSuggestionModel);
          }
          return;
          PlaySearchToolbar.this.mActionView.mController.switchToSteadyStateMode();
        }
      }
    });
  }
  
  public ActionProvider getActionProvider()
  {
    if (this.mActionProvider == null) {
      this.mActionProvider = new ActionProvider(getContext())
      {
        public final View onCreateActionView()
        {
          return PlaySearchToolbar.this.mActionView;
        }
      };
    }
    return this.mActionProvider;
  }
  
  public PlaySearch getActionView()
  {
    return this.mActionView;
  }
  
  public PlaySearch getSearchView()
  {
    return this.mSearchView;
  }
  
  public final boolean isSearchBoxPresent()
  {
    return (this.mIsInSearchBoxOnlyMode) || (hasExpandedActionView());
  }
  
  public void onEnterSearchMode()
  {
    if (this.mActionListener != null) {
      this.mActionListener.onEnterSearchMode();
    }
  }
  
  public void onExitSearchMode()
  {
    if (this.mActionListener != null) {
      this.mActionListener.onExitSearchMode();
    }
  }
  
  public void onMeasure(int paramInt1, int paramInt2)
  {
    if (isSearchBoxPresent())
    {
      if (hasExpandedActionView())
      {
        Toolbar.LayoutParams localLayoutParams = (Toolbar.LayoutParams)this.mActionView.getLayoutParams();
        if (localLayoutParams.width != -1)
        {
          localLayoutParams.width = -1;
          this.mActionView.setLayoutParams(localLayoutParams);
        }
      }
      if (hasExpandedActionView()) {}
      for (PlaySearch localPlaySearch2 = this.mActionView;; localPlaySearch2 = this.mSearchView) {
        for (int j = 0; j < getChildCount(); j++)
        {
          View localView = getChildAt(j);
          if ((localView != null) && (localView != localPlaySearch2) && (localView.getVisibility() != 8))
          {
            this.mPreviousVisibility.put(localView, Integer.valueOf(localView.getVisibility()));
            localView.setVisibility(8);
          }
        }
      }
    }
    if (this.mPreviousVisibility.size() != 0)
    {
      Iterator localIterator = this.mPreviousVisibility.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        if (localEntry.getKey() != null) {
          ((View)localEntry.getKey()).setVisibility(((Integer)localEntry.getValue()).intValue());
        }
      }
      this.mPreviousVisibility.clear();
    }
    PlaySearch localPlaySearch1 = this.mSearchView;
    boolean bool = this.mIsInSearchBoxOnlyMode;
    int i = 0;
    if (bool) {}
    for (;;)
    {
      localPlaySearch1.setVisibility(i);
      super.onMeasure(paramInt1, paramInt2);
      return;
      i = 8;
    }
  }
  
  public final boolean onMenuItemActionCollapse(MenuItem paramMenuItem)
  {
    this.mExpandedMenuItemId = -1;
    return true;
  }
  
  public final boolean onMenuItemActionExpand(MenuItem paramMenuItem)
  {
    if (Build.VERSION.SDK_INT >= 21)
    {
      View localView = findViewById(paramMenuItem.getItemId());
      if (localView != null)
      {
        Rect localRect = new Rect();
        localView.getGlobalVisibleRect(localRect);
        this.mActionView.setRevealCenter(new Point(localRect.centerX(), localRect.centerY()));
      }
    }
    this.mExpandedMenuItemId = paramMenuItem.getItemId();
    updateHeightAndPadding(true);
    onEnterSearchMode();
    return true;
  }
  
  protected void onRestoreInstanceState(Parcelable paramParcelable)
  {
    if ((paramParcelable instanceof Bundle))
    {
      Bundle localBundle = (Bundle)paramParcelable;
      this.mExpandedMenuItemId = localBundle.getInt("play_search_toolbar.expanded_menu_item_id");
      this.mSearchView.onRestoreInstanceState(localBundle.getParcelable("play_search_toolbar.search_view_state"));
      super.onRestoreInstanceState(localBundle.getParcelable("play_search_toolbar.parent_instance_state"));
      return;
    }
    super.onRestoreInstanceState(paramParcelable);
  }
  
  protected Parcelable onSaveInstanceState()
  {
    Bundle localBundle = new Bundle();
    localBundle.putParcelable("play_search_toolbar.parent_instance_state", super.onSaveInstanceState());
    localBundle.putInt("play_search_toolbar.expanded_menu_item_id", this.mExpandedMenuItemId);
    localBundle.putParcelable("play_search_toolbar.search_view_state", this.mSearchView.onSaveInstanceState());
    return localBundle;
  }
  
  public void setBackgroundDrawable(Drawable paramDrawable)
  {
    this.mBackground = paramDrawable;
    if (this.mIsInSearchBoxOnlyMode) {
      paramDrawable = null;
    }
    super.setBackgroundDrawable(paramDrawable);
  }
  
  public void setIdleModeDrawerIconState(int paramInt)
  {
    this.mSearchView.setIdleModeDrawerIconState(paramInt);
  }
  
  public final void setMode(boolean paramBoolean, int paramInt)
  {
    if ((paramBoolean == this.mIsInSearchBoxOnlyMode) && (paramInt == this.mSearchView.getSteadyStateMode())) {
      return;
    }
    if (paramBoolean)
    {
      if (hasExpandedActionView()) {
        collapseActionView();
      }
      this.mSearchView.setSteadyStateMode(paramInt);
      this.mSearchView.mController.switchToSteadyStateMode();
      if (this.mSearchView.getParent() == null) {
        addView(this.mSearchView, this.mSearchViewLayoutParams);
      }
      if (this.mIsInSearchBoxOnlyMode != paramBoolean)
      {
        this.mIsInSearchBoxOnlyMode = paramBoolean;
        if (!this.mIsInSearchBoxOnlyMode) {
          break label139;
        }
      }
    }
    label139:
    for (Drawable localDrawable = null;; localDrawable = this.mBackground)
    {
      super.setBackgroundDrawable(localDrawable);
      updateHeightAndPadding();
      return;
      if (this.mSearchView.getParent() != this) {
        break;
      }
      this.mSearchView.mController.switchToSteadyStateMode();
      removeView(this.mSearchView);
      break;
    }
  }
  
  public void setNavigationOnClickListener(final View.OnClickListener paramOnClickListener)
  {
    super.setNavigationOnClickListener(paramOnClickListener);
    this.mSearchView.setOnNavButtonClickListener(new View.OnClickListener()
    {
      public final void onClick(View paramAnonymousView)
      {
        if ((PlaySearchToolbar.this.mSearchView.mController.isInSteadyStateMode()) && (paramOnClickListener != null)) {
          paramOnClickListener.onClick(paramAnonymousView);
        }
      }
    });
  }
  
  public void setPlaySearchListener(PlaySearchListener paramPlaySearchListener)
  {
    this.mListener = paramPlaySearchListener;
  }
  
  public void setPlaySearchToolbarActionListener(PlaySearchToolbarActionListener paramPlaySearchToolbarActionListener)
  {
    this.mActionListener = paramPlaySearchToolbarActionListener;
  }
  
  public void setQuery(String paramString)
  {
    this.mSearchView.setQuery(paramString);
  }
  
  public void setSuggestions(List<? extends PlaySearchSuggestionModel> paramList)
  {
    if (hasExpandedActionView()) {}
    for (PlaySearch localPlaySearch = this.mActionView;; localPlaySearch = this.mSearchView)
    {
      localPlaySearch.setSuggestions(paramList);
      return;
    }
  }
  
  public boolean shouldKeepSearchActive()
  {
    return false;
  }
  
  public static abstract class Configurator
  {
    public final Context mContext;
    
    public Configurator(Context paramContext)
    {
      this.mContext = paramContext;
    }
    
    public PlaySearch createActionSearchView(ViewGroup paramViewGroup)
    {
      return (PlaySearch)LayoutInflater.from(this.mContext).inflate(R.layout.play_search, paramViewGroup, false);
    }
    
    public abstract BitmapLoader getBitmapLoader();
    
    public int getPlaySearchLayoutId()
    {
      return R.layout.play_search;
    }
    
    public int getSearchBoxHorizontalMargin()
    {
      return this.mContext.getResources().getDimensionPixelSize(R.dimen.play_collection_edge_padding_minus_card_half_spacing);
    }
  }
  
  public static abstract interface PlaySearchToolbarActionListener
  {
    public abstract void onEnterSearchMode();
    
    public abstract void onExitSearchMode();
  }
}



/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar

 * Qualified Name:     com.google.android.play.search.PlaySearchToolbar

 * JD-Core Version:    0.7.0.1

 */