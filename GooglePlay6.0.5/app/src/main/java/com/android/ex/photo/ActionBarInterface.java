package com.android.ex.photo;

import android.graphics.drawable.Drawable;

public abstract interface ActionBarInterface
{
  public abstract void addOnMenuVisibilityListener(OnMenuVisibilityListener paramOnMenuVisibilityListener);
  
  public abstract void hide();
  
  public abstract void setDisplayHomeAsUpEnabled$1385ff();
  
  public abstract void setDisplayOptionsShowTitle();
  
  public abstract void setLogo(Drawable paramDrawable);
  
  public abstract void setSubtitle(CharSequence paramCharSequence);
  
  public abstract void setTitle(CharSequence paramCharSequence);
  
  public abstract void show();
  
  public static abstract interface OnMenuVisibilityListener
  {
    public abstract void onMenuVisibilityChanged(boolean paramBoolean);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.ex.photo.ActionBarInterface
 * JD-Core Version:    0.7.0.1
 */