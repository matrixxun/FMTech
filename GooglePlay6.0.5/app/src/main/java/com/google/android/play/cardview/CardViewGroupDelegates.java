package com.google.android.play.cardview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import com.google.android.play.R.styleable;

public final class CardViewGroupDelegates
{
  public static final CardViewGroupDelegate IMPL;
  public static final CardViewGroupDelegate NO_CARD_BG_IMPL;
  
  static
  {
    if (Build.VERSION.SDK_INT >= 21) {}
    for (IMPL = new CardViewGroupL((byte)0);; IMPL = new CardViewGroupEclairMr1((byte)0))
    {
      NO_CARD_BG_IMPL = new CardViewGroupDelegate()
      {
        public final void initialize(View paramAnonymousView, Context paramAnonymousContext, AttributeSet paramAnonymousAttributeSet, int paramAnonymousInt) {}
        
        public final void setBackgroundColor(View paramAnonymousView, int paramAnonymousInt) {}
        
        public final void setBackgroundResource(View paramAnonymousView, int paramAnonymousInt) {}
        
        public final void setCardElevation(View paramAnonymousView, float paramAnonymousFloat) {}
      };
      return;
    }
  }
  
  @TargetApi(5)
  private static class CardViewGroupEclairMr1
    implements CardViewGroupDelegate
  {
    protected static float getElevation(TypedArray paramTypedArray)
    {
      return paramTypedArray.getDimension(R.styleable.PlayCardViewGroup_playCardElevation, 0.0F);
    }
    
    protected static int getInset(TypedArray paramTypedArray)
    {
      return paramTypedArray.getDimensionPixelSize(R.styleable.PlayCardViewGroup_playCardInset, 0);
    }
    
    protected static float getRadius(TypedArray paramTypedArray)
    {
      return paramTypedArray.getDimension(R.styleable.PlayCardViewGroup_playCardCornerRadius, 0.0F);
    }
    
    protected static TypedArray getStyledAttrs(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
    {
      return paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.PlayCardViewGroup, paramInt, 0);
    }
    
    public void initialize(View paramView, Context paramContext, AttributeSet paramAttributeSet, int paramInt)
    {
      TypedArray localTypedArray = getStyledAttrs(paramContext, paramAttributeSet, paramInt);
      paramView.setBackgroundDrawable(new RoundRectDrawableWithShadow(paramContext.getResources(), localTypedArray.getColorStateList(R.styleable.PlayCardViewGroup_playCardBackgroundColor), getRadius(localTypedArray), getElevation(localTypedArray), getInset(localTypedArray)));
      localTypedArray.recycle();
    }
    
    public final void setBackgroundColor(View paramView, int paramInt)
    {
      Drawable localDrawable = paramView.getBackground();
      if ((localDrawable instanceof CardViewBackgroundDrawable))
      {
        ((CardViewBackgroundDrawable)localDrawable).setBackgroundColor(paramInt);
        return;
      }
      Log.w("CardViewGroupDelegates", "Unable to set background color. CardView is not using a CardViewBackgroundDrawable");
    }
    
    public final void setBackgroundResource(View paramView, int paramInt)
    {
      if (paramInt == 0) {
        return;
      }
      Resources localResources = paramView.getResources();
      Drawable localDrawable = paramView.getBackground();
      if ((localDrawable instanceof CardViewBackgroundDrawable)) {
        try
        {
          ColorStateList localColorStateList = localResources.getColorStateList(paramInt);
          ((CardViewBackgroundDrawable)localDrawable).setBackgroundColorStateList(localColorStateList);
          return;
        }
        catch (Resources.NotFoundException localNotFoundException)
        {
          Log.w("CardViewGroupDelegates", "Unable to set background - ColorStateList not found.", localNotFoundException);
          return;
        }
      }
      Log.w("CardViewGroupDelegates", "Unable to set background. CardView is not using a CardViewBackgroundDrawable.");
    }
    
    public void setCardElevation(View paramView, float paramFloat)
    {
      Drawable localDrawable = paramView.getBackground();
      if ((localDrawable instanceof RoundRectDrawableWithShadow)) {
        ((RoundRectDrawableWithShadow)localDrawable).setShadowSize(paramFloat);
      }
    }
  }
  
  @TargetApi(21)
  private static final class CardViewGroupL
    extends CardViewGroupDelegates.CardViewGroupEclairMr1
  {
    private CardViewGroupL()
    {
      super();
    }
    
    public final void initialize(View paramView, Context paramContext, AttributeSet paramAttributeSet, int paramInt)
    {
      TypedArray localTypedArray = getStyledAttrs(paramContext, paramAttributeSet, paramInt);
      RoundRectDrawable localRoundRectDrawable = new RoundRectDrawable(localTypedArray.getColorStateList(R.styleable.PlayCardViewGroup_playCardBackgroundColor), getRadius(localTypedArray), getInset(localTypedArray));
      paramView.setClipToOutline(true);
      paramView.setElevation(getElevation(localTypedArray));
      paramView.setBackground(localRoundRectDrawable);
      paramView.setClipToOutline(localTypedArray.getBoolean(R.styleable.PlayCardViewGroup_playCardClipToOutline, true));
      localTypedArray.recycle();
    }
    
    public final void setCardElevation(View paramView, float paramFloat)
    {
      paramView.setElevation(paramFloat);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.cardview.CardViewGroupDelegates
 * JD-Core Version:    0.7.0.1
 */