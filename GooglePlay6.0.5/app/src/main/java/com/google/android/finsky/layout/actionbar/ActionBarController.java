package com.google.android.finsky.layout.actionbar;

import android.support.v7.widget.Toolbar;
import com.google.android.finsky.activities.TextSectionTranslatable;

public abstract interface ActionBarController
{
  public abstract void disableActionBarOverlay();
  
  public abstract void disableStatusBarOverlay();
  
  public abstract void enableActionBarOverlay();
  
  public abstract void enableStatusBarOverlay();
  
  public abstract void enterActionBarSearchMode();
  
  public abstract void enterActionBarSectionExpandedMode(CharSequence paramCharSequence, TextSectionTranslatable paramTextSectionTranslatable);
  
  public abstract void exitActionBarSearchMode();
  
  public abstract void exitActionBarSectionExpandedMode();
  
  public abstract Toolbar getToolbar();
  
  public abstract void setActionBarSearchModeListener(ActionBarSearchModeListener paramActionBarSearchModeListener);
  
  public abstract void setHomeAsUpIndicator(int paramInt);
  
  public static abstract interface ActionBarSearchModeListener
  {
    public abstract void onEnterActionBarSearchMode();
    
    public abstract void onExitActionBarSearchMode();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.actionbar.ActionBarController
 * JD-Core Version:    0.7.0.1
 */