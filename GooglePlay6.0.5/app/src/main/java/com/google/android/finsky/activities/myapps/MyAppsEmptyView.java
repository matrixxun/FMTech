package com.google.android.finsky.activities.myapps;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ScrollView;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.layout.AccountSelectorView;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.Toc.CorpusMetadata;

public class MyAppsEmptyView
  extends ScrollView
{
  AccountSelectorView mAccountNameView;
  View mAppBrowsing;
  TextView mDescriptionView;
  View mGamesBrowsing;
  
  public MyAppsEmptyView(Context paramContext)
  {
    super(paramContext);
  }
  
  public MyAppsEmptyView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mAccountNameView = ((AccountSelectorView)findViewById(2131755219));
    this.mDescriptionView = ((TextView)findViewById(2131755731));
    this.mAppBrowsing = findViewById(2131755732);
    this.mGamesBrowsing = findViewById(2131755733);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.myapps.MyAppsEmptyView
 * JD-Core Version:    0.7.0.1
 */