package com.google.android.finsky.layout;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.play.utils.PlayTouchDelegate;

public class LightPurchaseCartLayout
  extends LinearLayout
{
  private View mHeader;
  private final Rect mHeaderArea = new Rect();
  private final int mHeaderTouchExtend = getResources().getDimensionPixelSize(2131493256);
  private final Rect mOldHeaderArea = new Rect();
  private final boolean mShouldUseTouchExtend = FinskyApp.get().getExperiments().isEnabled(12602632L);
  
  public LightPurchaseCartLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public LightPurchaseCartLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mHeader = findViewById(2131755487);
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    if (!this.mShouldUseTouchExtend) {}
    do
    {
      return;
      if ((this.mHeader == null) || (this.mHeader.getVisibility() == 8)) {
        break;
      }
      this.mHeader.getHitRect(this.mHeaderArea);
      this.mHeaderArea.inset(0, -this.mHeaderTouchExtend);
    } while (this.mHeaderArea.equals(this.mOldHeaderArea));
    setTouchDelegate(new PlayTouchDelegate(this.mHeaderArea, this.mHeader));
    this.mOldHeaderArea.set(this.mHeaderArea);
    return;
    this.mOldHeaderArea.setEmpty();
    setTouchDelegate(null);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.LightPurchaseCartLayout
 * JD-Core Version:    0.7.0.1
 */