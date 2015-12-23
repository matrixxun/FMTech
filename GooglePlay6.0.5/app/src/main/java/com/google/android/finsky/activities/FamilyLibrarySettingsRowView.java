package com.google.android.finsky.activities;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.play.layout.ForegroundRelativeLayout;

public class FamilyLibrarySettingsRowView
  extends ForegroundRelativeLayout
  implements View.OnClickListener
{
  CheckBox mCheckBox;
  ImageView mImage;
  TextView mName;
  
  public FamilyLibrarySettingsRowView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public FamilyLibrarySettingsRowView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public void onClick(View paramView)
  {
    CheckBox localCheckBox = this.mCheckBox;
    if (!this.mCheckBox.isChecked()) {}
    for (boolean bool = true;; bool = false)
    {
      localCheckBox.setChecked(bool);
      return;
    }
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mName = ((TextView)findViewById(2131755355));
    this.mImage = ((ImageView)findViewById(2131755354));
    this.mCheckBox = ((CheckBox)findViewById(2131755356));
    setOnClickListener(this);
    this.mCheckBox.setSaveEnabled(false);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.FamilyLibrarySettingsRowView
 * JD-Core Version:    0.7.0.1
 */