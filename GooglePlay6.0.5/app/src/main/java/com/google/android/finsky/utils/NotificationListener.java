package com.google.android.finsky.utils;

import com.google.android.finsky.activities.ErrorDialog.Builder;

public abstract interface NotificationListener
{
  public abstract boolean shouldShowAppNotification$14e1ec69(String paramString);
  
  public abstract boolean showAppAlert(String paramString, ErrorDialog.Builder paramBuilder);
  
  public abstract boolean showAppAlert(String paramString1, String paramString2, String paramString3, int paramInt);
  
  public abstract boolean showDocAlert(String paramString1, String paramString2, String paramString3, String paramString4);
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.NotificationListener
 * JD-Core Version:    0.7.0.1
 */