package com.google.android.finsky.layout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.finsky.activities.SimpleAlertDialog.ConfigurableView;

public class PlayGamesInstallView
  extends LinearLayout
  implements SimpleAlertDialog.ConfigurableView
{
  public PlayGamesInstallView(Context paramContext)
  {
    super(paramContext);
  }
  
  public PlayGamesInstallView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  @SuppressLint({"NewApi"})
  public PlayGamesInstallView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public final void configureView(Bundle paramBundle)
  {
    ((TextView)findViewById(2131755904)).setText(Html.fromHtml(getContext().getString(2131362571)));
  }
  
  public Bundle getResult()
  {
    return null;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.PlayGamesInstallView
 * JD-Core Version:    0.7.0.1
 */