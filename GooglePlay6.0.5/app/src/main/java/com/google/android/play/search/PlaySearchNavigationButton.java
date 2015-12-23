package com.google.android.play.search;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import com.google.android.play.R.color;
import com.google.android.play.R.string;
import com.google.android.play.drawer.PlayDrawerArrowDrawable;

public class PlaySearchNavigationButton
  extends ImageView
  implements PlaySearchListener
{
  private PlayDrawerArrowDrawable mArrowOrBurgerDrawable;
  private PlaySearchController mController;
  public int mCurrentMode;
  private int mIdleModeDrawerIconState = 0;
  
  public PlaySearchNavigationButton(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlaySearchNavigationButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public PlaySearchNavigationButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    this.mArrowOrBurgerDrawable = new PlayDrawerArrowDrawable(paramContext);
    this.mArrowOrBurgerDrawable.setColorFilter(new PorterDuffColorFilter(paramContext.getResources().getColor(R.color.play_search_plate_navigation_button_color), PorterDuff.Mode.SRC_IN));
  }
  
  private void setMode(int paramInt)
  {
    if (this.mCurrentMode == paramInt)
    {
      if (this.mIdleModeDrawerIconState == 1) {
        updateContentDescription(paramInt, false);
      }
      return;
    }
    this.mArrowOrBurgerDrawable.setState(paramInt, 2);
    updateContentDescription(paramInt, false);
    this.mCurrentMode = paramInt;
  }
  
  public void onFinishInflate()
  {
    super.onFinishInflate();
    setImageDrawable(this.mArrowOrBurgerDrawable);
    this.mArrowOrBurgerDrawable.setState(0, 0);
    setOnClickListener(new View.OnClickListener()
    {
      public final void onClick(View paramAnonymousView)
      {
        if (PlaySearchNavigationButton.this.mController != null)
        {
          PlaySearchController localPlaySearchController = PlaySearchNavigationButton.this.mController;
          if (localPlaySearchController.mNavigationClickListener != null) {
            localPlaySearchController.mNavigationClickListener.onClick(paramAnonymousView);
          }
          if (PlaySearchNavigationButton.this.mCurrentMode == 1) {
            PlaySearchNavigationButton.this.mController.switchToSteadyStateMode();
          }
        }
      }
    });
    setMode(0);
  }
  
  public final void onModeChanged(int paramInt)
  {
    if (paramInt == 1) {
      setMode(this.mIdleModeDrawerIconState);
    }
    while ((paramInt != 3) && (paramInt != 2)) {
      return;
    }
    setMode(1);
  }
  
  public final void onQueryChanged(String paramString, boolean paramBoolean) {}
  
  public final void onSearch(String paramString) {}
  
  public final void onSuggestionClicked(PlaySearchSuggestionModel paramPlaySearchSuggestionModel) {}
  
  public void setIdleModeDrawerIconState(int paramInt)
  {
    this.mIdleModeDrawerIconState = paramInt;
    setMode(paramInt);
  }
  
  public void setPlaySearchController(PlaySearchController paramPlaySearchController)
  {
    if (this.mController != null) {
      this.mController.removePlaySearchListener(this);
    }
    this.mController = paramPlaySearchController;
    this.mController.addPlaySearchListener(this);
  }
  
  public final void updateContentDescription(int paramInt, boolean paramBoolean)
  {
    int i;
    if (paramInt == 0) {
      if (paramBoolean) {
        i = R.string.play_drawer_close;
      }
    }
    for (;;)
    {
      setContentDescription(getContext().getText(i));
      return;
      i = R.string.play_drawer_open;
      continue;
      if ((this.mController != null) && (this.mController.mCurrentSearchMode == 1)) {
        i = R.string.play_accessibility_search_plate_navigate_up_button;
      } else {
        i = R.string.play_accessibility_search_plate_back_button;
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.search.PlaySearchNavigationButton
 * JD-Core Version:    0.7.0.1
 */