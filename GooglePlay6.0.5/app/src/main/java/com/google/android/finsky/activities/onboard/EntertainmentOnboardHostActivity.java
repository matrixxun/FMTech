package com.google.android.finsky.activities.onboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class EntertainmentOnboardHostActivity
  extends FragmentActivity
{
  private static final String ANIMATED_FRAGMENT_TAG = AnimatedEntertainmentOnboardFragment.class.getSimpleName();
  
  public static Intent createIntent(Context paramContext)
  {
    return new Intent(paramContext, EntertainmentOnboardHostActivity.class);
  }
  
  public void onBackPressed()
  {
    super.onBackPressed();
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (getSupportFragmentManager().findFragmentByTag(ANIMATED_FRAGMENT_TAG) == null)
    {
      FragmentTransaction localFragmentTransaction = getSupportFragmentManager().beginTransaction();
      localFragmentTransaction.replace(16908290, new AnimatedEntertainmentOnboardFragment(), ANIMATED_FRAGMENT_TAG);
      localFragmentTransaction.commit();
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.onboard.EntertainmentOnboardHostActivity
 * JD-Core Version:    0.7.0.1
 */