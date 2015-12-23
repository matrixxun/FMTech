package com.google.android.play.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import com.google.android.play.R.color;
import com.google.android.play.R.drawable;

public final class PlayCorpusUtils
{
  private static boolean sIsEnterpriseTheme = false;
  
  public static int getPlayActionButtonBackgroundDrawable$1a54e363(int paramInt)
  {
    switch (paramInt)
    {
    case 5: 
    case 7: 
    case 8: 
    case 9: 
    default: 
      if (sIsEnterpriseTheme) {
        return R.drawable.play_action_button_apps_ent_base;
      }
      break;
    case 3: 
      if (sIsEnterpriseTheme) {
        return R.drawable.play_action_button_apps_ent_base;
      }
      return R.drawable.play_action_button_apps;
    case 1: 
      return R.drawable.play_action_button_books;
    case 6: 
      return R.drawable.play_action_button_newsstand;
    case 4: 
      return R.drawable.play_action_button_movies;
    case 2: 
      return R.drawable.play_action_button_music;
    case 10: 
      return R.drawable.play_action_button_commerce;
    }
    return R.drawable.play_action_button_multi;
  }
  
  public static int getPlayActionButtonBackgroundSecondaryDrawable$1a54e363(int paramInt)
  {
    switch (paramInt)
    {
    case 5: 
    default: 
      if (sIsEnterpriseTheme) {
        return R.drawable.play_action_button_apps_ent_secondary;
      }
      break;
    case 3: 
      if (sIsEnterpriseTheme) {
        return R.drawable.play_action_button_apps_ent_secondary;
      }
      return R.drawable.play_action_button_apps_secondary;
    case 1: 
      return R.drawable.play_action_button_books_secondary;
    case 6: 
      return R.drawable.play_action_button_newsstand_secondary;
    case 4: 
      return R.drawable.play_action_button_movies_secondary;
    case 2: 
      return R.drawable.play_action_button_music_secondary;
    }
    return R.drawable.play_action_button_multi_secondary;
  }
  
  public static ColorStateList getPrimaryTextColor(Context paramContext, int paramInt)
  {
    int i;
    switch (paramInt)
    {
    case 5: 
    case 7: 
    case 8: 
    case 9: 
    default: 
      if (sIsEnterpriseTheme) {
        i = R.color.play_apps_ent_primary_text;
      }
      break;
    }
    for (;;)
    {
      return paramContext.getResources().getColorStateList(i);
      if (sIsEnterpriseTheme) {}
      for (i = R.color.play_apps_ent_primary_text;; i = R.color.play_apps_primary_text) {
        break;
      }
      i = R.color.play_books_primary_text;
      continue;
      i = R.color.play_newsstand_primary_text;
      continue;
      i = R.color.play_movies_primary_text;
      continue;
      i = R.color.play_music_primary_text;
      continue;
      i = R.color.play_commerce_primary_text;
      continue;
      i = R.color.play_multi_primary_text;
    }
  }
  
  public static int getRecentsColor(Context paramContext, int paramInt)
  {
    int i;
    switch (paramInt)
    {
    case 5: 
    default: 
      if (sIsEnterpriseTheme) {
        i = R.color.play_apps_ent_recents;
      }
      break;
    }
    for (;;)
    {
      return paramContext.getResources().getColor(i);
      if (sIsEnterpriseTheme) {}
      for (i = R.color.play_apps_ent_recents;; i = R.color.play_apps_recents) {
        break;
      }
      i = R.color.play_books_recents;
      continue;
      i = R.color.play_newsstand_recents;
      continue;
      i = R.color.play_movies_recents;
      continue;
      i = R.color.play_music_recents;
      continue;
      i = R.color.play_multi_recents;
    }
  }
  
  public static void setIsEnterpriseTheme(boolean paramBoolean)
  {
    sIsEnterpriseTheme = paramBoolean;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.utils.PlayCorpusUtils
 * JD-Core Version:    0.7.0.1
 */