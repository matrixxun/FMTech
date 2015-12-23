package com.google.android.finsky.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.google.android.finsky.layout.play.Identifiable;

public class IdentifiableFrameLayout
  extends FrameLayout
  implements Identifiable
{
  private String mIdentifier;
  
  public IdentifiableFrameLayout(Context paramContext)
  {
    super(paramContext);
  }
  
  public IdentifiableFrameLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public String getIdentifier()
  {
    return this.mIdentifier;
  }
  
  public void setIdentifier(String paramString)
  {
    this.mIdentifier = paramString;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.IdentifiableFrameLayout
 * JD-Core Version:    0.7.0.1
 */