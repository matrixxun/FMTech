package android.support.v7.internal.view;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.support.v7.appcompat.R.bool;

public final class ActionBarPolicy
{
  public Context mContext;
  
  private ActionBarPolicy(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  public static ActionBarPolicy get(Context paramContext)
  {
    return new ActionBarPolicy(paramContext);
  }
  
  public final boolean hasEmbeddedTabs()
  {
    if (this.mContext.getApplicationInfo().targetSdkVersion >= 16) {
      return this.mContext.getResources().getBoolean(R.bool.abc_action_bar_embed_tabs);
    }
    return this.mContext.getResources().getBoolean(R.bool.abc_action_bar_embed_tabs_pre_jb);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.internal.view.ActionBarPolicy
 * JD-Core Version:    0.7.0.1
 */