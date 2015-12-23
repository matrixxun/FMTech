package com.google.android.finsky.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class WebViewDialog
  extends AppCompatActivity
{
  public static Intent getIntent$2b01ea99(Context paramContext, String paramString)
  {
    Intent localIntent = new Intent(paramContext, WebViewDialog.class);
    localIntent.putExtra("android.intent.extra.TITLE", 2131362462);
    localIntent.putExtra("android.intent.extra.STREAM", paramString);
    return localIntent;
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setTitle(getIntent().getIntExtra("android.intent.extra.TITLE", 2131361848));
    setContentView(2130968604);
    ((WebView)findViewById(2131755214)).loadUrl(getIntent().getStringExtra("android.intent.extra.STREAM"));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.WebViewDialog
 * JD-Core Version:    0.7.0.1
 */