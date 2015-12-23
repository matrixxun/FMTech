package com.google.android.play.search;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import com.google.android.play.R.drawable;
import com.google.android.play.R.string;

public class PlaySearchActionButton
  extends ImageView
  implements PlaySearchListener
{
  private final boolean mCanPerformVoiceSearch;
  private Drawable mClearDrawable;
  private PlaySearchController mController;
  private int mCurrentMode = 1;
  private Drawable mMicDrawable;
  
  public PlaySearchActionButton(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlaySearchActionButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public PlaySearchActionButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    this.mClearDrawable = paramContext.getResources().getDrawable(R.drawable.play_ic_clear);
    this.mMicDrawable = paramContext.getResources().getDrawable(R.drawable.ic_mic_dark);
    this.mCanPerformVoiceSearch = PlaySearchVoiceController.canPerformVoiceSearch(paramContext);
    setMode(2);
  }
  
  private void handleChange()
  {
    if (this.mController == null) {
      return;
    }
    String str = this.mController.mCurrentQuery;
    if ((this.mCanPerformVoiceSearch) && ((this.mController.isInSteadyStateMode()) || (TextUtils.isEmpty(str))))
    {
      setMode(2);
      return;
    }
    setMode(1);
  }
  
  private void setMode(int paramInt)
  {
    if (this.mCurrentMode == paramInt) {}
    for (;;)
    {
      return;
      this.mCurrentMode = paramInt;
      Drawable localDrawable;
      int i;
      if (paramInt == 1)
      {
        localDrawable = this.mClearDrawable;
        i = R.string.play_accessibility_search_plate_clear;
      }
      while (localDrawable != null)
      {
        setImageDrawable(localDrawable);
        setContentDescription(getContext().getResources().getString(i));
        setVisibility(0);
        return;
        i = 0;
        localDrawable = null;
        if (paramInt == 2)
        {
          localDrawable = this.mMicDrawable;
          i = R.string.play_accessibility_search_plate_voice_search_button;
        }
      }
    }
  }
  
  public void onFinishInflate()
  {
    super.onFinishInflate();
    setOnClickListener(new View.OnClickListener()
    {
      public final void onClick(View paramAnonymousView)
      {
        if (PlaySearchActionButton.this.mController == null) {}
        do
        {
          return;
          if (PlaySearchActionButton.this.mCurrentMode == 1)
          {
            PlaySearchActionButton.this.mController.setQueryInternal("", true);
            return;
          }
        } while (PlaySearchActionButton.this.mCurrentMode != 2);
        PlaySearchActionButton.this.mController.setMode(4);
      }
    });
  }
  
  public final void onModeChanged(int paramInt)
  {
    handleChange();
  }
  
  public final void onQueryChanged(String paramString, boolean paramBoolean)
  {
    handleChange();
  }
  
  public final void onSearch(String paramString) {}
  
  public final void onSuggestionClicked(PlaySearchSuggestionModel paramPlaySearchSuggestionModel) {}
  
  public void setPlaySearchController(PlaySearchController paramPlaySearchController)
  {
    if (this.mController != null) {
      this.mController.removePlaySearchListener(this);
    }
    this.mController = paramPlaySearchController;
    this.mController.addPlaySearchListener(this);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.search.PlaySearchActionButton
 * JD-Core Version:    0.7.0.1
 */