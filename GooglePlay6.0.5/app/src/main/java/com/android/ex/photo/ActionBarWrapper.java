package com.android.ex.photo;

import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.OnMenuVisibilityListener;

public final class ActionBarWrapper
  implements ActionBarInterface
{
  private final ActionBar mActionBar;
  
  public ActionBarWrapper(ActionBar paramActionBar)
  {
    this.mActionBar = paramActionBar;
  }
  
  public final void addOnMenuVisibilityListener(ActionBarInterface.OnMenuVisibilityListener paramOnMenuVisibilityListener)
  {
    this.mActionBar.addOnMenuVisibilityListener(new MenuVisiblityListenerWrapper(paramOnMenuVisibilityListener));
  }
  
  public final void hide()
  {
    this.mActionBar.hide();
  }
  
  public final void setDisplayHomeAsUpEnabled$1385ff()
  {
    this.mActionBar.setDisplayHomeAsUpEnabled(true);
  }
  
  public final void setDisplayOptionsShowTitle()
  {
    this.mActionBar.setDisplayOptions(8, 8);
  }
  
  public final void setLogo(Drawable paramDrawable)
  {
    this.mActionBar.setLogo(paramDrawable);
  }
  
  public final void setSubtitle(CharSequence paramCharSequence)
  {
    this.mActionBar.setSubtitle(paramCharSequence);
  }
  
  public final void setTitle(CharSequence paramCharSequence)
  {
    this.mActionBar.setTitle(paramCharSequence);
  }
  
  public final void show()
  {
    this.mActionBar.show();
  }
  
  private final class MenuVisiblityListenerWrapper
    implements ActionBar.OnMenuVisibilityListener
  {
    private final ActionBarInterface.OnMenuVisibilityListener mWrapped;
    
    public MenuVisiblityListenerWrapper(ActionBarInterface.OnMenuVisibilityListener paramOnMenuVisibilityListener)
    {
      this.mWrapped = paramOnMenuVisibilityListener;
    }
    
    public final void onMenuVisibilityChanged(boolean paramBoolean)
    {
      this.mWrapped.onMenuVisibilityChanged(paramBoolean);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.ex.photo.ActionBarWrapper
 * JD-Core Version:    0.7.0.1
 */