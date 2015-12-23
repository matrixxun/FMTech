package com.android.ex.photo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.Menu;
import android.view.MenuItem;

public class PhotoViewActivity
  extends ActionBarActivity
  implements PhotoViewController.ActivityInterface
{
  private ActionBarWrapper mActionBar;
  private PhotoViewController mController;
  
  public PhotoViewController createController()
  {
    return new PhotoViewController(this);
  }
  
  public ActionBarInterface getActionBarInterface()
  {
    if (this.mActionBar == null) {
      this.mActionBar = new ActionBarWrapper(getDelegate().getSupportActionBar());
    }
    return this.mActionBar;
  }
  
  public final Context getContext()
  {
    return this;
  }
  
  public final PhotoViewController getController()
  {
    return this.mController;
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
  }
  
  public void onBackPressed()
  {
    if (!this.mController.onBackPressed()) {
      super.onBackPressed();
    }
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mController = createController();
    this.mController.onCreate(paramBundle);
  }
  
  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    return true;
  }
  
  protected void onDestroy()
  {
    this.mController.mIsDestroyedCompat = true;
    super.onDestroy();
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    PhotoViewController localPhotoViewController = this.mController;
    switch (paramMenuItem.getItemId())
    {
    }
    for (int i = 0;; i = 1)
    {
      boolean bool1;
      if (i == 0)
      {
        boolean bool2 = super.onOptionsItemSelected(paramMenuItem);
        bool1 = false;
        if (!bool2) {}
      }
      else
      {
        bool1 = true;
      }
      return bool1;
      localPhotoViewController.mActivity.finish();
    }
  }
  
  protected void onPause()
  {
    this.mController.onPause();
    super.onPause();
  }
  
  public boolean onPrepareOptionsMenu(Menu paramMenu)
  {
    return true;
  }
  
  protected void onResume()
  {
    super.onResume();
    this.mController.onResume();
  }
  
  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    this.mController.onSaveInstanceState(paramBundle);
  }
  
  protected void onStart()
  {
    super.onStart();
  }
  
  protected void onStop()
  {
    super.onStop();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.ex.photo.PhotoViewActivity
 * JD-Core Version:    0.7.0.1
 */