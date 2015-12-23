package com.google.android.play.search;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.google.android.play.R.id;

public class PlaySearchPlate
  extends FrameLayout
{
  private PlaySearchActionButton mActionButton;
  public PlaySearchNavigationButton mNavButton;
  PlaySearchPlateTextContainer mSearchPlateTextContainer;
  
  public PlaySearchPlate(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlaySearchPlate(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public PlaySearchPlate(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public int getFocusViewId()
  {
    return R.id.search_box_text_input;
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mNavButton = ((PlaySearchNavigationButton)findViewById(R.id.navigation_button));
    this.mSearchPlateTextContainer = ((PlaySearchPlateTextContainer)findViewById(R.id.text_container));
    this.mActionButton = ((PlaySearchActionButton)findViewById(R.id.action_button));
  }
  
  public void setHint(CharSequence paramCharSequence)
  {
    this.mSearchPlateTextContainer.setHint(paramCharSequence);
  }
  
  public void setIdleModeDrawerIconState(int paramInt)
  {
    this.mNavButton.setIdleModeDrawerIconState(paramInt);
  }
  
  public void setPlaySearchController(PlaySearchController paramPlaySearchController)
  {
    this.mNavButton.setPlaySearchController(paramPlaySearchController);
    this.mSearchPlateTextContainer.setPlaySearchController(paramPlaySearchController);
    this.mActionButton.setPlaySearchController(paramPlaySearchController);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.search.PlaySearchPlate
 * JD-Core Version:    0.7.0.1
 */