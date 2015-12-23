package com.google.android.finsky.layout;

import android.widget.BaseAdapter;

public abstract class PermissionAdapter
  extends BaseAdapter
{
  public abstract boolean isAppInstalled();
  
  public abstract boolean showTheNoPermissionMessage();
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.PermissionAdapter
 * JD-Core Version:    0.7.0.1
 */