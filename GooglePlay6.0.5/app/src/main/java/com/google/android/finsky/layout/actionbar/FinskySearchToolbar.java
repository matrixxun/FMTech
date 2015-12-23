package com.google.android.finsky.layout.actionbar;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.search.FinskySearch;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.search.PlaySearch;
import com.google.android.play.search.PlaySearchToolbar;
import com.google.android.play.search.PlaySearchToolbar.Configurator;

public class FinskySearchToolbar
  extends PlaySearchToolbar
{
  private ActionBarController mActionBarController;
  private int mSearchBoxFixedWidth;
  
  public FinskySearchToolbar(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public FinskySearchToolbar(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  protected final void onEnterSearchMode()
  {
    if (this.mActionBarController != null) {
      this.mActionBarController.enterActionBarSearchMode();
    }
  }
  
  protected final void onExitSearchMode()
  {
    if (this.mActionBarController != null) {
      this.mActionBarController.exitActionBarSearchMode();
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    if (this.mSearchBoxFixedWidth > 0)
    {
      int i = (View.MeasureSpec.getSize(paramInt1) - this.mSearchBoxFixedWidth) / 2;
      PlaySearch localPlaySearch = getSearchView();
      localPlaySearch.setSearchBoxMargins(i, localPlaySearch.getSearchPlateMarginTop(), i, localPlaySearch.getSearchPlateMarginBottom(), false);
    }
    super.onMeasure(paramInt1, paramInt2);
  }
  
  public void setActionBarController(ActionBarController paramActionBarController)
  {
    this.mActionBarController = paramActionBarController;
  }
  
  public void setCurrentBackendId(int paramInt)
  {
    ((FinskySearch)getSearchView()).setCurrentBackendId(paramInt);
    ((FinskySearch)getActionView()).setCurrentBackendId(paramInt);
    Resources localResources;
    String str;
    if (FinskyApp.get().getExperiments().isEnabled(12603098L))
    {
      localResources = getContext().getResources();
      switch (paramInt)
      {
      case 5: 
      case 8: 
      case 9: 
      case 10: 
      case 11: 
      case 12: 
      default: 
        str = localResources.getString(2131362575);
      }
    }
    for (;;)
    {
      getSearchView().setHint(str);
      getActionView().setHint(str);
      return;
      str = localResources.getString(2131362576);
      continue;
      str = localResources.getString(2131362577);
      continue;
      str = localResources.getString(2131362581);
      continue;
      str = localResources.getString(2131362579);
      continue;
      str = localResources.getString(2131362582);
      continue;
      str = localResources.getString(2131362580);
      continue;
      str = localResources.getString(2131362578);
    }
  }
  
  public void setNavigationManager(NavigationManager paramNavigationManager)
  {
    ((FinskySearch)getSearchView()).setNavigationManager(paramNavigationManager);
    ((FinskySearch)getActionView()).setNavigationManager(paramNavigationManager);
  }
  
  public void setSearchBoxFixedWidth(int paramInt)
  {
    this.mSearchBoxFixedWidth = paramInt;
    requestLayout();
  }
  
  protected final boolean shouldKeepSearchActive()
  {
    return true;
  }
  
  public static final class Configurator
    extends PlaySearchToolbar.Configurator
  {
    public Configurator(Context paramContext)
    {
      super();
    }
    
    public final PlaySearch createActionSearchView(ViewGroup paramViewGroup)
    {
      return (PlaySearch)LayoutInflater.from(this.mContext).inflate(2130968750, paramViewGroup, false);
    }
    
    public final BitmapLoader getBitmapLoader()
    {
      return FinskyApp.get().mBitmapLoader;
    }
    
    public final int getPlaySearchLayoutId()
    {
      return 2130968750;
    }
    
    public final int getSearchBoxHorizontalMargin()
    {
      return UiUtils.getGridHorizontalPadding(this.mContext.getResources());
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.actionbar.FinskySearchToolbar
 * JD-Core Version:    0.7.0.1
 */