package com.google.android.finsky.layout;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.android.finsky.utils.UiUtils;

public class SongIndex
  extends FrameLayout
{
  private ProgressBar mProgressIndicator;
  private TextView mSongIndexText;
  private int mState = 0;
  private ImageView mStatusIndicator;
  
  public SongIndex(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  private void updateUiVisibility(int paramInt1, int paramInt2)
  {
    switch (paramInt1)
    {
    default: 
    case 0: 
      do
      {
        return;
      } while (this.mSongIndexText == null);
      this.mSongIndexText.setVisibility(paramInt2);
      return;
    case 1: 
      this.mProgressIndicator.setVisibility(paramInt2);
      return;
    }
    this.mStatusIndicator.setVisibility(paramInt2);
  }
  
  public int getBaseline()
  {
    if (this.mSongIndexText != null) {
      this.mSongIndexText.getBaseline();
    }
    return super.getBaseline();
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mSongIndexText = ((TextView)findViewById(2131755703));
    this.mStatusIndicator = ((ImageView)findViewById(2131755570));
    this.mProgressIndicator = ((ProgressBar)findViewById(2131755571));
  }
  
  public void setState(int paramInt)
  {
    updateUiVisibility(this.mState, 8);
    updateUiVisibility(paramInt, 0);
    ImageView localImageView1 = this.mStatusIndicator;
    int i;
    ImageView localImageView2;
    Resources localResources;
    int j;
    switch (paramInt)
    {
    case 3: 
    default: 
      i = 2130837828;
      localImageView1.setImageResource(i);
      localImageView2 = this.mStatusIndicator;
      localResources = getResources();
      switch (paramInt)
      {
      case 3: 
      default: 
        j = 2131362029;
      }
      break;
    }
    for (;;)
    {
      localImageView2.setContentDescription(localResources.getString(j));
      Context localContext = getContext();
      if ((this.mState != 4) && (paramInt == 4) && (UiUtils.isAccessibilityEnabled(localContext))) {
        UiUtils.sendAccessibilityEventWithText(localContext, localContext.getString(2131362027), this.mStatusIndicator, true);
      }
      this.mState = paramInt;
      return;
      i = 2130837827;
      break;
      i = 2130837833;
      break;
      i = 2130837599;
      break;
      j = 2131362031;
      continue;
      j = 2131362027;
      continue;
      j = 2131362030;
    }
  }
  
  public void setTrackNumber(int paramInt)
  {
    this.mSongIndexText.setText(String.valueOf(paramInt));
    TextView localTextView = this.mSongIndexText;
    Resources localResources = getResources();
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Integer.valueOf(paramInt);
    localTextView.setContentDescription(localResources.getString(2131362028, arrayOfObject));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.SongIndex
 * JD-Core Version:    0.7.0.1
 */