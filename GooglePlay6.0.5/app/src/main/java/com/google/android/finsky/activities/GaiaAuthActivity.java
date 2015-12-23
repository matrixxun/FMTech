package com.google.android.finsky.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import com.google.android.finsky.auth.AuthState;
import com.google.android.finsky.fragments.GaiaAuthFragment;
import com.google.android.finsky.fragments.GaiaAuthFragment.Listener;

public class GaiaAuthActivity
  extends AppCompatActivity
  implements GaiaAuthFragment.Listener
{
  private GaiaAuthFragment mGaiaAuthFragment;
  
  public static Intent getIntent(Context paramContext, String paramString, boolean paramBoolean, AuthState paramAuthState, Bundle paramBundle)
  {
    paramAuthState.mUseFingerprintAuth = false;
    Intent localIntent = new Intent(paramContext, GaiaAuthActivity.class);
    localIntent.putExtra("GaiaAuthActivity_authState", paramAuthState);
    localIntent.putExtra("GaiaAuthActivity_accountName", paramString);
    localIntent.putExtra("GaiaAuthActivity_showWarning", paramBoolean);
    localIntent.putExtra("GaiaAuthActivity_extraParams", paramBundle);
    return localIntent;
  }
  
  public final void onCancel()
  {
    setResult(0);
    finish();
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130968777);
    AuthState localAuthState = (AuthState)getIntent().getParcelableExtra("GaiaAuthActivity_authState");
    if (paramBundle != null)
    {
      this.mGaiaAuthFragment = ((GaiaAuthFragment)getSupportFragmentManager().getFragment(paramBundle, "GaiaAuthActivity_GaiaAuthFragment"));
      this.mGaiaAuthFragment.mListener = this;
      return;
    }
    Intent localIntent = getIntent();
    this.mGaiaAuthFragment = GaiaAuthFragment.newInstance(localIntent.getStringExtra("GaiaAuthActivity_accountName"), localIntent.getBooleanExtra("GaiaAuthActivity_showWarning", false), localAuthState);
    this.mGaiaAuthFragment.mListener = this;
    FragmentTransaction localFragmentTransaction = getSupportFragmentManager().beginTransaction();
    localFragmentTransaction.add(2131755234, this.mGaiaAuthFragment);
    localFragmentTransaction.commit();
  }
  
  protected void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    getSupportFragmentManager().putFragment(paramBundle, "GaiaAuthActivity_GaiaAuthFragment", this.mGaiaAuthFragment);
  }
  
  public final void onSuccess$255f295()
  {
    Intent localIntent = new Intent();
    localIntent.putExtra("GaiaAuthActivity_extraParams", getIntent().getBundleExtra("GaiaAuthActivity_extraParams"));
    setResult(-1, localIntent);
    finish();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.GaiaAuthActivity
 * JD-Core Version:    0.7.0.1
 */