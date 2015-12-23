package com.google.android.finsky.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.config.ContentLevel;
import com.google.android.finsky.config.G;
import com.google.android.finsky.layout.ButtonBar;
import com.google.android.finsky.layout.ButtonBar.ClickListener;
import com.google.android.finsky.layout.play.GenericUiElementNode;
import com.google.android.play.utils.config.GservicesValue;

@Deprecated
public class ContentFilterActivity
  extends AppCompatActivity
  implements ButtonBar.ClickListener
{
  private ButtonBar mButtonBar;
  private FinskyEventLog mEventLogger;
  private ContentLevel mLevel;
  private TextView mMoreInfoView;
  private GenericUiElementNode mNode = new GenericUiElementNode(315, null, null, null);
  private RadioGroup mRadioButtonsView;
  
  public static String getLabel(Context paramContext, int paramInt)
  {
    Resources localResources = paramContext.getResources();
    switch (paramInt)
    {
    default: 
      return null;
    case -1: 
      return localResources.getString(2131362052);
    case 0: 
      return localResources.getString(2131362048);
    case 1: 
      return localResources.getString(2131362050);
    case 2: 
      return localResources.getString(2131362051);
    case 3: 
      return localResources.getString(2131362049);
    }
    return localResources.getString(2131362054);
  }
  
  private void setupRadioButton(LayoutInflater paramLayoutInflater, ContentLevel paramContentLevel, int paramInt1, int paramInt2)
  {
    boolean bool = true;
    RadioButton localRadioButton = (RadioButton)paramLayoutInflater.inflate(2130969033, this.mRadioButtonsView, false);
    localRadioButton.setText(paramInt2);
    localRadioButton.setTag(paramContentLevel);
    localRadioButton.setFocusable(bool);
    if (this.mLevel == paramContentLevel) {}
    for (;;)
    {
      localRadioButton.setChecked(bool);
      localRadioButton.setId(paramInt1);
      this.mRadioButtonsView.addView(localRadioButton);
      return;
      bool = false;
    }
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130968666);
    if (paramBundle == null) {}
    for (this.mLevel = ContentLevel.importFromSettings(this);; this.mLevel = ContentLevel.convertFromLegacyValue(paramBundle.getInt("level")))
    {
      this.mRadioButtonsView = ((RadioGroup)findViewById(2131755339));
      this.mMoreInfoView = ((TextView)findViewById(2131755340));
      this.mButtonBar = ((ButtonBar)findViewById(2131755300));
      this.mRadioButtonsView.removeAllViews();
      LayoutInflater localLayoutInflater = LayoutInflater.from(this);
      setupRadioButton(localLayoutInflater, ContentLevel.EVERYONE, 2131755036, 2131362048);
      setupRadioButton(localLayoutInflater, ContentLevel.LOW_MATURITY, 2131755038, 2131362050);
      setupRadioButton(localLayoutInflater, ContentLevel.MEDIUM_MATURITY, 2131755039, 2131362051);
      setupRadioButton(localLayoutInflater, ContentLevel.HIGH_MATURITY, 2131755037, 2131362049);
      setupRadioButton(localLayoutInflater, ContentLevel.SHOW_ALL, 2131755040, 2131362054);
      this.mRadioButtonsView.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
      {
        public final void onCheckedChanged(RadioGroup paramAnonymousRadioGroup, int paramAnonymousInt)
        {
          ContentFilterActivity.access$002(ContentFilterActivity.this, (ContentLevel)paramAnonymousRadioGroup.findViewById(paramAnonymousInt).getTag());
        }
      });
      Object[] arrayOfObject = new Object[3];
      arrayOfObject[0] = getString(2131362223);
      arrayOfObject[1] = G.contentFilterInfoUrl.get();
      arrayOfObject[2] = getString(2131362327);
      String str = String.format("%s <a href='%s'>%s</a>", arrayOfObject);
      this.mMoreInfoView.setText(Html.fromHtml(str));
      this.mMoreInfoView.setMovementMethod(LinkMovementMethod.getInstance());
      this.mButtonBar.setPositiveButtonTitle(2131362053);
      this.mButtonBar.setNegativeButtonTitle(2131362047);
      this.mButtonBar.setClickListener(this);
      setResult(0);
      this.mEventLogger = FinskyApp.get().getEventLogger();
      if (paramBundle == null) {
        this.mEventLogger.logPathImpression(0L, this.mNode);
      }
      return;
    }
  }
  
  public final void onNegativeButtonClick()
  {
    this.mEventLogger.logClickEvent(255, null, this.mNode);
    finish();
  }
  
  public final void onPositiveButtonClick()
  {
    this.mEventLogger.logClickEvent(254, null, this.mNode);
    int i = ContentLevel.importFromSettings(this).mValue;
    int j = this.mLevel.mValue;
    if (i != j)
    {
      Intent localIntent = new Intent();
      localIntent.putExtra("ContentFilterActivity_selectedFilterLevel", j);
      setResult(-1, localIntent);
    }
    finish();
  }
  
  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putInt("level", this.mLevel.mValue);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.ContentFilterActivity
 * JD-Core Version:    0.7.0.1
 */