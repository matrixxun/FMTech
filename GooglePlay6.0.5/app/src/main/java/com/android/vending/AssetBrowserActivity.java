package com.android.vending;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.finsky.activities.MainActivity;

public class AssetBrowserActivity
  extends Activity
{
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Intent localIntent = getIntent();
    localIntent.setClass(this, MainActivity.class);
    localIntent.setFlags(0xFFDFFFFF & localIntent.getFlags());
    startActivity(localIntent);
    finish();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.vending.AssetBrowserActivity
 * JD-Core Version:    0.7.0.1
 */