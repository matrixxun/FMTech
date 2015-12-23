package com.google.android.finsky.detailspage;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.google.android.finsky.layout.AccessibleLinearLayout;

public class ArtistRadioModuleLayout
  extends AccessibleLinearLayout
  implements View.OnClickListener, ModuleDividerItemDecoration.NoBottomDivider, ModuleDividerItemDecoration.NoTopDivider
{
  private TextView mArtistRadioButtonText;
  ArtistRadioClickListener mArtistRadioClickListener;
  
  public ArtistRadioModuleLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public ArtistRadioModuleLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public void onClick(View paramView)
  {
    if (this.mArtistRadioClickListener == null) {
      return;
    }
    this.mArtistRadioClickListener.onArtistRadioClick$3c7ec8c3();
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    setOnClickListener(this);
    this.mArtistRadioButtonText = ((TextView)findViewById(2131755261));
    this.mArtistRadioButtonText.setText(getContext().getString(2131361860).toUpperCase());
  }
  
  public static abstract interface ArtistRadioClickListener
  {
    public abstract void onArtistRadioClick$3c7ec8c3();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.ArtistRadioModuleLayout
 * JD-Core Version:    0.7.0.1
 */