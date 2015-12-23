package com.google.android.finsky.layout.play;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import com.google.android.finsky.adapters.Recyclable;
import com.google.android.finsky.layout.HeroGraphicView;
import com.google.android.finsky.layout.IdentifiableFrameLayout;
import com.google.android.finsky.layout.InsetsFrameLayout;
import com.google.android.finsky.utils.UiUtils;

public class PlaySocialHeader
  extends IdentifiableFrameLayout
  implements Recyclable, FinskyHeaderListLayout.StreamSpacer
{
  public PlayAvatarPack mAvatarPack;
  
  public PlaySocialHeader(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlaySocialHeader(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mAvatarPack = ((PlayAvatarPack)findViewById(2131755846));
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = getWidth();
    int j = getHeight();
    this.mAvatarPack.layout(0, j - this.mAvatarPack.getMeasuredHeight(), i, j);
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = HeroGraphicView.getSpacerHeight(getContext(), i, true, 0.0F);
    this.mAvatarPack.measure(paramInt1, 0);
    int k = j + (int)(0.3F * this.mAvatarPack.getMeasuredHeight());
    if (InsetsFrameLayout.SUPPORTS_IMMERSIVE_MODE) {
      k -= UiUtils.getStatusBarHeight(getContext());
    }
    setMeasuredDimension(i, k);
  }
  
  public final void onRecycle()
  {
    this.mAvatarPack.onRecycle();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlaySocialHeader
 * JD-Core Version:    0.7.0.1
 */