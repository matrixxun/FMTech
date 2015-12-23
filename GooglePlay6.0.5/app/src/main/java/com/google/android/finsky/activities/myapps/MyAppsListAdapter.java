package com.google.android.finsky.activities.myapps;

import android.widget.Adapter;
import com.google.android.finsky.api.model.Document;

public abstract interface MyAppsListAdapter
  extends Adapter
{
  public abstract Document getDocument(int paramInt);
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.myapps.MyAppsListAdapter
 * JD-Core Version:    0.7.0.1
 */