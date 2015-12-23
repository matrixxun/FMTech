package com.google.android.finsky.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.android.finsky.billing.BillingFlowContext;
import com.google.android.finsky.billing.BillingFlowListener;

public abstract class ChallengeActivity
  extends ActionBarActivity
  implements BillingFlowContext, BillingFlowListener
{
  public final void hideFragment$4b1b4969(Fragment paramFragment)
  {
    FragmentTransaction localFragmentTransaction = getSupportFragmentManager().beginTransaction();
    localFragmentTransaction.remove(paramFragment);
    localFragmentTransaction.commit();
  }
  
  public final void hideProgress() {}
  
  public final void onError$5b46052e(String paramString)
  {
    if (paramString != null) {
      Toast.makeText(this, paramString, 1).show();
    }
    setResult(0);
    finish();
  }
  
  public final void onFinished$713ba888(boolean paramBoolean, Bundle paramBundle)
  {
    if (paramBoolean)
    {
      setResult(0);
      finish();
      return;
    }
    Intent localIntent = new Intent();
    localIntent.putExtra("challenge_response", paramBundle);
    setResult(-1, localIntent);
    finish();
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    return super.onOptionsItemSelected(paramMenuItem);
  }
  
  public final void persistFragment(Bundle paramBundle, String paramString, Fragment paramFragment)
  {
    getSupportFragmentManager().putFragment(paramBundle, paramString, paramFragment);
  }
  
  public final Fragment restoreFragment(Bundle paramBundle, String paramString)
  {
    return getSupportFragmentManager().getFragment(paramBundle, paramString);
  }
  
  public final void showDialogFragment(DialogFragment paramDialogFragment, String paramString)
  {
    FragmentTransaction localFragmentTransaction = getSupportFragmentManager().beginTransaction();
    Fragment localFragment = getSupportFragmentManager().findFragmentByTag(paramString);
    if (localFragment != null) {
      localFragmentTransaction.remove(localFragment);
    }
    localFragmentTransaction.addToBackStack(null);
    paramDialogFragment.show(getSupportFragmentManager(), paramString);
  }
  
  public final void showFragment$41b27b4d(Fragment paramFragment, String paramString)
  {
    FragmentTransaction localFragmentTransaction = getSupportFragmentManager().beginTransaction();
    localFragmentTransaction.add(2131755234, paramFragment);
    localFragmentTransaction.commitAllowingStateLoss();
  }
  
  public final void showProgress(int paramInt) {}
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.ChallengeActivity
 * JD-Core Version:    0.7.0.1
 */