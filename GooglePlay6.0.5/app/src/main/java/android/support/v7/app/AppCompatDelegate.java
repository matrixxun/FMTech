package android.support.v7.app;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build.VERSION;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;

public abstract class AppCompatDelegate
{
  public static AppCompatDelegate create(Activity paramActivity, AppCompatCallback paramAppCompatCallback)
  {
    return create(paramActivity, paramActivity.getWindow(), paramAppCompatCallback);
  }
  
  static AppCompatDelegate create(Context paramContext, Window paramWindow, AppCompatCallback paramAppCompatCallback)
  {
    int i = Build.VERSION.SDK_INT;
    if (i >= 23) {
      return new AppCompatDelegateImplV23(paramContext, paramWindow, paramAppCompatCallback);
    }
    if (i >= 14) {
      return new AppCompatDelegateImplV14(paramContext, paramWindow, paramAppCompatCallback);
    }
    if (i >= 11) {
      return new AppCompatDelegateImplV11(paramContext, paramWindow, paramAppCompatCallback);
    }
    return new AppCompatDelegateImplV7(paramContext, paramWindow, paramAppCompatCallback);
  }
  
  public abstract void addContentView(View paramView, ViewGroup.LayoutParams paramLayoutParams);
  
  public abstract ActionBarDrawerToggle.Delegate getDrawerToggleDelegate();
  
  public abstract MenuInflater getMenuInflater();
  
  public abstract ActionBar getSupportActionBar();
  
  public abstract void installViewFactory();
  
  public abstract void invalidateOptionsMenu();
  
  public abstract void onConfigurationChanged(Configuration paramConfiguration);
  
  public abstract void onCreate$79e5e33f();
  
  public abstract void onDestroy();
  
  public abstract void onPostCreate$79e5e33f();
  
  public abstract void onPostResume();
  
  public abstract void onStop();
  
  public abstract boolean requestWindowFeature(int paramInt);
  
  public abstract void setContentView(int paramInt);
  
  public abstract void setContentView(View paramView);
  
  public abstract void setContentView(View paramView, ViewGroup.LayoutParams paramLayoutParams);
  
  public abstract void setSupportActionBar(Toolbar paramToolbar);
  
  public abstract void setTitle(CharSequence paramCharSequence);
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.app.AppCompatDelegate
 * JD-Core Version:    0.7.0.1
 */