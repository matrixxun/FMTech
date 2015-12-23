package android.support.v4.app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
import android.util.Log;

public final class NavUtils
{
  private static final NavUtilsImpl IMPL = new NavUtilsImplBase();
  
  static
  {
    if (Build.VERSION.SDK_INT >= 16)
    {
      IMPL = new NavUtilsImplJB();
      return;
    }
  }
  
  public static Intent getParentActivityIntent(Activity paramActivity)
  {
    return IMPL.getParentActivityIntent(paramActivity);
  }
  
  public static Intent getParentActivityIntent(Context paramContext, ComponentName paramComponentName)
    throws PackageManager.NameNotFoundException
  {
    String str = getParentActivityName(paramContext, paramComponentName);
    if (str == null) {
      return null;
    }
    ComponentName localComponentName = new ComponentName(paramComponentName.getPackageName(), str);
    if (getParentActivityName(paramContext, localComponentName) == null) {
      return IntentCompat.makeMainActivity(localComponentName);
    }
    return new Intent().setComponent(localComponentName);
  }
  
  public static String getParentActivityName(Activity paramActivity)
  {
    try
    {
      String str = getParentActivityName(paramActivity, paramActivity.getComponentName());
      return str;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      throw new IllegalArgumentException(localNameNotFoundException);
    }
  }
  
  public static String getParentActivityName(Context paramContext, ComponentName paramComponentName)
    throws PackageManager.NameNotFoundException
  {
    ActivityInfo localActivityInfo = paramContext.getPackageManager().getActivityInfo(paramComponentName, 128);
    return IMPL.getParentActivityName(paramContext, localActivityInfo);
  }
  
  public static void navigateUpTo(Activity paramActivity, Intent paramIntent)
  {
    IMPL.navigateUpTo(paramActivity, paramIntent);
  }
  
  public static boolean shouldUpRecreateTask(Activity paramActivity, Intent paramIntent)
  {
    return IMPL.shouldUpRecreateTask(paramActivity, paramIntent);
  }
  
  static abstract interface NavUtilsImpl
  {
    public abstract Intent getParentActivityIntent(Activity paramActivity);
    
    public abstract String getParentActivityName(Context paramContext, ActivityInfo paramActivityInfo);
    
    public abstract void navigateUpTo(Activity paramActivity, Intent paramIntent);
    
    public abstract boolean shouldUpRecreateTask(Activity paramActivity, Intent paramIntent);
  }
  
  static class NavUtilsImplBase
    implements NavUtils.NavUtilsImpl
  {
    public Intent getParentActivityIntent(Activity paramActivity)
    {
      String str = NavUtils.getParentActivityName(paramActivity);
      if (str == null) {
        return null;
      }
      ComponentName localComponentName = new ComponentName(paramActivity, str);
      try
      {
        if (NavUtils.getParentActivityName(paramActivity, localComponentName) == null) {
          return IntentCompat.makeMainActivity(localComponentName);
        }
        Intent localIntent = new Intent().setComponent(localComponentName);
        return localIntent;
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException)
      {
        Log.e("NavUtils", "getParentActivityIntent: bad parentActivityName '" + str + "' in manifest");
      }
      return null;
    }
    
    public String getParentActivityName(Context paramContext, ActivityInfo paramActivityInfo)
    {
      String str;
      if (paramActivityInfo.metaData == null) {
        str = null;
      }
      do
      {
        return str;
        str = paramActivityInfo.metaData.getString("android.support.PARENT_ACTIVITY");
        if (str == null) {
          return null;
        }
      } while (str.charAt(0) != '.');
      return paramContext.getPackageName() + str;
    }
    
    public void navigateUpTo(Activity paramActivity, Intent paramIntent)
    {
      paramIntent.addFlags(67108864);
      paramActivity.startActivity(paramIntent);
      paramActivity.finish();
    }
    
    public boolean shouldUpRecreateTask(Activity paramActivity, Intent paramIntent)
    {
      String str = paramActivity.getIntent().getAction();
      return (str != null) && (!str.equals("android.intent.action.MAIN"));
    }
  }
  
  static final class NavUtilsImplJB
    extends NavUtils.NavUtilsImplBase
  {
    public final Intent getParentActivityIntent(Activity paramActivity)
    {
      Intent localIntent = paramActivity.getParentActivityIntent();
      if (localIntent == null) {
        localIntent = super.getParentActivityIntent(paramActivity);
      }
      return localIntent;
    }
    
    public final String getParentActivityName(Context paramContext, ActivityInfo paramActivityInfo)
    {
      String str = paramActivityInfo.parentActivityName;
      if (str == null) {
        str = super.getParentActivityName(paramContext, paramActivityInfo);
      }
      return str;
    }
    
    public final void navigateUpTo(Activity paramActivity, Intent paramIntent)
    {
      paramActivity.navigateUpTo(paramIntent);
    }
    
    public final boolean shouldUpRecreateTask(Activity paramActivity, Intent paramIntent)
    {
      return paramActivity.shouldUpRecreateTask(paramIntent);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v4.app.NavUtils
 * JD-Core Version:    0.7.0.1
 */