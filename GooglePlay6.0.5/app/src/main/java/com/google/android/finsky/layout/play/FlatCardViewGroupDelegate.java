package com.google.android.finsky.layout.play;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import com.android.vending.R.styleable;
import com.google.android.play.cardview.CardViewGroupDelegate;

final class FlatCardViewGroupDelegate
  implements CardViewGroupDelegate
{
  static final FlatCardViewGroupDelegate INSTANCE = new FlatCardViewGroupDelegate();
  
  public final void initialize(View paramView, Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.PlayCardViewGroup, paramInt, 0);
    float f1 = localTypedArray.getDimension(1, 0.0F);
    int i = localTypedArray.getDimensionPixelSize(3, 0);
    Resources localResources = paramContext.getResources();
    int j = Math.round(localResources.getDisplayMetrics().density);
    float f2 = f1 + j;
    int k = i - j;
    GradientDrawable localGradientDrawable = new GradientDrawable();
    localGradientDrawable.setShape(0);
    localGradientDrawable.setCornerRadius(f2);
    localGradientDrawable.setStroke(j, localResources.getColor(2131689545));
    localGradientDrawable.setColor(0);
    paramView.setBackgroundDrawable(new InsetDrawable(localGradientDrawable, k)
    {
      public final boolean getPadding(Rect paramAnonymousRect)
      {
        paramAnonymousRect.setEmpty();
        return false;
      }
    });
    if (Build.VERSION.SDK_INT >= 21) {
      paramView.setClipToOutline(localTypedArray.getBoolean(4, true));
    }
    localTypedArray.recycle();
  }
  
  public final void setBackgroundColor(View paramView, int paramInt) {}
  
  public final void setBackgroundResource(View paramView, int paramInt) {}
  
  public final void setCardElevation(View paramView, float paramFloat) {}
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.FlatCardViewGroupDelegate
 * JD-Core Version:    0.7.0.1
 */