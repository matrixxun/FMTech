package android.support.v7.app;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.app.TaskStackBuilder.SupportParentable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

public class AppCompatActivity
  extends FragmentActivity
  implements TaskStackBuilder.SupportParentable, ActionBarDrawerToggle.DelegateProvider, AppCompatCallback
{
  private AppCompatDelegate mDelegate;
  
  private boolean onSupportNavigateUp()
  {
    Intent localIntent1 = NavUtils.getParentActivityIntent(this);
    TaskStackBuilder localTaskStackBuilder;
    Intent localIntent2;
    if (localIntent1 != null) {
      if (NavUtils.shouldUpRecreateTask(this, localIntent1))
      {
        localTaskStackBuilder = TaskStackBuilder.create(this);
        boolean bool = this instanceof TaskStackBuilder.SupportParentable;
        localIntent2 = null;
        if (bool) {
          localIntent2 = ((TaskStackBuilder.SupportParentable)this).getSupportParentActivityIntent();
        }
        if (localIntent2 != null) {
          break label130;
        }
      }
    }
    label130:
    for (Intent localIntent3 = NavUtils.getParentActivityIntent(this);; localIntent3 = localIntent2)
    {
      if (localIntent3 != null)
      {
        ComponentName localComponentName = localIntent3.getComponent();
        if (localComponentName == null) {
          localComponentName = localIntent3.resolveActivity(localTaskStackBuilder.mSourceContext.getPackageManager());
        }
        localTaskStackBuilder.addParentStack(localComponentName);
        localTaskStackBuilder.addNextIntent(localIntent3);
      }
      localTaskStackBuilder.startActivities();
      for (;;)
      {
        try
        {
          ActivityCompat.finishAffinity(this);
          return true;
        }
        catch (IllegalStateException localIllegalStateException)
        {
          finish();
          continue;
        }
        NavUtils.navigateUpTo(this, localIntent1);
      }
      return false;
    }
  }
  
  public void addContentView(View paramView, ViewGroup.LayoutParams paramLayoutParams)
  {
    getDelegate().addContentView(paramView, paramLayoutParams);
  }
  
  public final AppCompatDelegate getDelegate()
  {
    if (this.mDelegate == null) {
      this.mDelegate = AppCompatDelegate.create(this, this);
    }
    return this.mDelegate;
  }
  
  public final ActionBarDrawerToggle.Delegate getDrawerToggleDelegate()
  {
    return getDelegate().getDrawerToggleDelegate();
  }
  
  public MenuInflater getMenuInflater()
  {
    return getDelegate().getMenuInflater();
  }
  
  public final Intent getSupportParentActivityIntent()
  {
    return NavUtils.getParentActivityIntent(this);
  }
  
  public void invalidateOptionsMenu()
  {
    getDelegate().invalidateOptionsMenu();
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
    getDelegate().onConfigurationChanged(paramConfiguration);
  }
  
  public void onContentChanged() {}
  
  public void onCreate(Bundle paramBundle)
  {
    getDelegate().installViewFactory();
    getDelegate().onCreate$79e5e33f();
    super.onCreate(paramBundle);
  }
  
  public void onDestroy()
  {
    super.onDestroy();
    getDelegate().onDestroy();
  }
  
  public final boolean onMenuItemSelected(int paramInt, MenuItem paramMenuItem)
  {
    if (super.onMenuItemSelected(paramInt, paramMenuItem)) {
      return true;
    }
    ActionBar localActionBar = getDelegate().getSupportActionBar();
    if ((paramMenuItem.getItemId() == 16908332) && (localActionBar != null) && ((0x4 & localActionBar.getDisplayOptions()) != 0)) {
      return onSupportNavigateUp();
    }
    return false;
  }
  
  public boolean onMenuOpened(int paramInt, Menu paramMenu)
  {
    return super.onMenuOpened(paramInt, paramMenu);
  }
  
  public void onPanelClosed(int paramInt, Menu paramMenu)
  {
    super.onPanelClosed(paramInt, paramMenu);
  }
  
  public void onPostCreate(Bundle paramBundle)
  {
    super.onPostCreate(paramBundle);
    getDelegate().onPostCreate$79e5e33f();
  }
  
  protected void onPostResume()
  {
    super.onPostResume();
    getDelegate().onPostResume();
  }
  
  public void onStop()
  {
    super.onStop();
    getDelegate().onStop();
  }
  
  protected void onTitleChanged(CharSequence paramCharSequence, int paramInt)
  {
    super.onTitleChanged(paramCharSequence, paramInt);
    getDelegate().setTitle(paramCharSequence);
  }
  
  public void setContentView(int paramInt)
  {
    getDelegate().setContentView(paramInt);
  }
  
  public void setContentView(View paramView)
  {
    getDelegate().setContentView(paramView);
  }
  
  public void setContentView(View paramView, ViewGroup.LayoutParams paramLayoutParams)
  {
    getDelegate().setContentView(paramView, paramLayoutParams);
  }
  
  public final void setSupportActionBar(Toolbar paramToolbar)
  {
    getDelegate().setSupportActionBar(paramToolbar);
  }
  
  public final void supportInvalidateOptionsMenu()
  {
    getDelegate().invalidateOptionsMenu();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.app.AppCompatActivity
 * JD-Core Version:    0.7.0.1
 */