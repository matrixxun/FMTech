package com.google.android.finsky.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.google.android.finsky.layout.play.Identifiable;

public class IdentifiableLinearLayout
  extends LinearLayout
  implements Identifiable
{
  private String mIdentifier;
  
  public IdentifiableLinearLayout(Context paramContext)
  {
    super(paramContext);
  }
  
  public IdentifiableLinearLayout(Context paramContext, AttributeSet paramAttributeSet)
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
 * Qualified Name:     com.google.android.finsky.layout.IdentifiableLinearLayout
 * JD-Core Version:    0.7.0.1
 */