package com.google.android.play.drawer;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.play.R.color;
import com.google.android.play.R.drawable;
import com.google.android.play.R.id;
import com.google.android.play.R.string;

class PlayDrawerMiniProfileInfoView
  extends RelativeLayout
{
  private boolean mAccountListEnabled;
  private boolean mAccountListExpanded;
  TextView mDisplayName;
  private ImageView mToggleAccountListButton;
  
  public PlayDrawerMiniProfileInfoView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayDrawerMiniProfileInfoView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  private void bindAccountToggler()
  {
    Resources localResources = getResources();
    if (this.mAccountListExpanded)
    {
      this.mToggleAccountListButton.setVisibility(0);
      this.mToggleAccountListButton.setImageResource(R.drawable.ic_up_white_16);
      setContentDescription(localResources.getString(R.string.play_drawer_content_description_hide_account_list_button));
      return;
    }
    if (this.mAccountListEnabled)
    {
      this.mToggleAccountListButton.setVisibility(0);
      this.mToggleAccountListButton.setImageResource(R.drawable.ic_down_white_16);
      setContentDescription(localResources.getString(R.string.play_drawer_content_description_show_account_list_button));
      return;
    }
    this.mToggleAccountListButton.setVisibility(8);
    setContentDescription(null);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mDisplayName = ((TextView)findViewById(R.id.mini_display_name));
    this.mToggleAccountListButton = ((ImageView)findViewById(R.id.mini_toggle_account_list_button));
  }
  
  public final void setAccountListEnabled(boolean paramBoolean)
  {
    if (this.mAccountListEnabled != paramBoolean)
    {
      this.mAccountListEnabled = paramBoolean;
      bindAccountToggler();
      if (!paramBoolean) {
        setAccountListExpanded(false);
      }
    }
  }
  
  public final void setAccountListExpanded(boolean paramBoolean)
  {
    if (this.mAccountListExpanded != paramBoolean)
    {
      this.mAccountListExpanded = paramBoolean;
      bindAccountToggler();
    }
  }
  
  public final void setAccountTogglerListener(View.OnClickListener paramOnClickListener)
  {
    int i = ViewCompat.getPaddingStart(this);
    int j = ViewCompat.getPaddingEnd(this);
    int k = getPaddingTop();
    int m = getPaddingBottom();
    if (paramOnClickListener == null)
    {
      setBackgroundResource(R.color.play_apps_primary);
      ViewCompat.setPaddingRelative(this, i, k, j, m);
      setOnClickListener(paramOnClickListener);
      if (paramOnClickListener == null) {
        break label72;
      }
    }
    label72:
    for (boolean bool = true;; bool = false)
    {
      setClickable(bool);
      return;
      setBackgroundResource(R.drawable.drawer_mini_profile_background);
      break;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.drawer.PlayDrawerMiniProfileInfoView
 * JD-Core Version:    0.7.0.1
 */