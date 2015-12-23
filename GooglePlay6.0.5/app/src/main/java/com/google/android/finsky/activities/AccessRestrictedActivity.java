package com.google.android.finsky.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class AccessRestrictedActivity
  extends FragmentActivity
  implements SimpleAlertDialog.Listener
{
  public static void showInvalidStoreTypeUI(Activity paramActivity)
  {
    startActivity(paramActivity, 2131362280);
  }
  
  public static void showLimitedPurchaseUI(Activity paramActivity)
  {
    startActivity(paramActivity, 2131362293);
  }
  
  public static void showLimitedUserUI(Activity paramActivity)
  {
    startActivity(paramActivity, 2131362294);
  }
  
  private static void startActivity(Activity paramActivity, int paramInt)
  {
    Intent localIntent = new Intent(paramActivity, AccessRestrictedActivity.class);
    localIntent.putExtra("AccessRestrictedActivity.messageId", paramInt);
    paramActivity.startActivity(localIntent);
  }
  
  public final void onNegativeClick(int paramInt, Bundle paramBundle)
  {
    finish();
  }
  
  public final void onPositiveClick(int paramInt, Bundle paramBundle)
  {
    finish();
  }
  
  protected void onResume()
  {
    super.onResume();
    int i = getIntent().getIntExtra("AccessRestrictedActivity.messageId", 2131362294);
    SimpleAlertDialog.Builder localBuilder = new SimpleAlertDialog.Builder();
    localBuilder.setMessageId(i).setPositiveId(2131362418).setCanceledOnTouchOutside(true).setEventLog(308, null, -1, -1, null);
    localBuilder.build().show(getSupportFragmentManager(), "access_restricted_dialog");
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.AccessRestrictedActivity
 * JD-Core Version:    0.7.0.1
 */