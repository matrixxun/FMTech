package com.google.android.play.cardview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v4.view.GravityCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import com.google.android.play.R.color;
import com.google.android.play.R.styleable;
import com.google.android.play.layout.WithBubblePadding;
import com.google.android.play.layout.WithForegroundLayer;
import com.google.android.play.utils.PlayUtils;

public final class CardBubbleViewGroupDelegates
{
  public static final CardBubbleViewGroupDelegate IMPL = new CardBubbleViewGroupGingerbread((byte)0);
  
  static
  {
    if (Build.VERSION.SDK_INT >= 21)
    {
      IMPL = new CardBubbleViewGroupLollipop((byte)0);
      return;
    }
  }
  
  private static class CardBubbleViewGroupGingerbread
    implements CardBubbleViewGroupDelegate
  {
    private static void adjustViewPadding(int[] paramArrayOfInt, View paramView, int paramInt, float paramFloat)
    {
      int i = paramArrayOfInt[0];
      float f1;
      int j;
      float f2;
      label35:
      int m;
      float f3;
      label56:
      int i1;
      int i2;
      if (paramInt == 3)
      {
        f1 = paramFloat;
        j = i + (int)f1;
        int k = paramArrayOfInt[1];
        if (paramInt != 48) {
          break label97;
        }
        f2 = paramFloat;
        m = k + (int)f2;
        int n = paramArrayOfInt[2];
        if (paramInt != 5) {
          break label103;
        }
        f3 = paramFloat;
        i1 = n + (int)f3;
        i2 = paramArrayOfInt[3];
        if (paramInt != 80) {
          break label109;
        }
      }
      for (;;)
      {
        paramView.setPadding(j, m, i1, i2 + (int)paramFloat);
        return;
        f1 = 0.0F;
        break;
        label97:
        f2 = 0.0F;
        break label35;
        label103:
        f3 = 0.0F;
        break label56;
        label109:
        paramFloat = 0.0F;
      }
    }
    
    protected Drawable createForegroundDrawable(Resources paramResources, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, int paramInt1, int paramInt2, int paramInt3)
    {
      return new CardBubbleForegroundDrawable(paramResources, R.color.play_overlay_highlight_fill, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramInt1, paramInt2, paramInt3);
    }
    
    public final void initialize(View paramView, Context paramContext, AttributeSet paramAttributeSet, int paramInt)
    {
      TypedArray localTypedArray1 = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.PlayCardBubbleViewGroup, paramInt, 0);
      if (PlayUtils.useLtr(paramView.getContext())) {}
      for (int i = 0;; i = 1)
      {
        int j = GravityCompat.getAbsoluteGravity(localTypedArray1.getInt(R.styleable.PlayCardBubbleViewGroup_playCardBubbleGravity, 48), i);
        float f1 = localTypedArray1.getDimension(R.styleable.PlayCardBubbleViewGroup_playCardBubbleSize, 0.0F);
        int k = localTypedArray1.getDimensionPixelOffset(R.styleable.PlayCardBubbleViewGroup_playCardBubbleOffset, 0);
        int m = localTypedArray1.getInt(R.styleable.PlayCardBubbleViewGroup_playCardBubblePosition, 1);
        localTypedArray1.recycle();
        TypedArray localTypedArray2 = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.PlayCardViewGroup, paramInt, 0);
        Resources localResources = paramContext.getResources();
        float f2 = localTypedArray2.getDimension(R.styleable.PlayCardViewGroup_playCardCornerRadius, 0.0F);
        float f3 = localTypedArray2.getDimensionPixelSize(R.styleable.PlayCardViewGroup_playCardInset, 0);
        float f4 = localTypedArray2.getDimension(R.styleable.PlayCardViewGroup_playCardElevation, 0.0F);
        paramView.setBackgroundDrawable(new CardBubbleDrawableWithShadow(localResources, localTypedArray2.getColorStateList(R.styleable.PlayCardViewGroup_playCardBackgroundColor), f2, f3, f4, f1, j, k, m));
        if ((paramView instanceof WithForegroundLayer))
        {
          Drawable localDrawable = createForegroundDrawable(localResources, f2, f3, f4, f1, j, k, m);
          ((WithForegroundLayer)paramView).setForeground(localDrawable);
        }
        int[] arrayOfInt = new int[4];
        arrayOfInt[0] = paramView.getPaddingLeft();
        arrayOfInt[1] = paramView.getPaddingTop();
        arrayOfInt[2] = paramView.getPaddingRight();
        arrayOfInt[3] = paramView.getPaddingBottom();
        if ((paramView instanceof WithBubblePadding)) {
          ((WithBubblePadding)paramView).onSaveOriginalPadding(arrayOfInt[0], arrayOfInt[1], arrayOfInt[2], arrayOfInt[3]);
        }
        adjustViewPadding(arrayOfInt, paramView, j, f1);
        localTypedArray2.recycle();
        return;
      }
    }
    
    public final void setBackgroundColor(View paramView, int paramInt)
    {
      Drawable localDrawable = paramView.getBackground();
      if ((localDrawable instanceof CardViewBackgroundDrawable))
      {
        ((CardViewBackgroundDrawable)localDrawable).setBackgroundColor(paramInt);
        return;
      }
      Log.w("BubbleViewGroupDelegate", "Unable to set background color. CardView is not using a CardViewBackgroundDrawable");
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
          Log.w("BubbleViewGroupDelegate", "Unable to set background - ColorStateList not found.", localNotFoundException);
          return;
        }
      }
      Log.w("BubbleViewGroupDelegate", "Unable to set background. CardView is not using a CardViewBackgroundDrawable.");
    }
    
    public final void setBubbleGravity(View paramView, int paramInt)
    {
      if ((paramInt != 48) && (paramInt != 80) && (paramInt != 8388611) && (paramInt != 8388613)) {
        throw new IllegalArgumentException("Invalid gravity, must be TOP, BOTTOM, START or END.");
      }
      if (PlayUtils.useLtr(paramView.getContext())) {}
      int j;
      Drawable localDrawable;
      for (int i = 0;; i = 1)
      {
        j = GravityCompat.getAbsoluteGravity(paramInt, i);
        localDrawable = paramView.getBackground();
        if ((localDrawable instanceof CardBubbleDrawableWithShadow)) {
          break;
        }
        throw new IllegalArgumentException("Given view was not initialized by this CardBubbleViewGroupDelegate");
      }
      CardBubbleDrawableWithShadow localCardBubbleDrawableWithShadow = (CardBubbleDrawableWithShadow)localDrawable;
      localCardBubbleDrawableWithShadow.setBubbleGravity(j);
      int[] arrayOfInt = new int[4];
      if ((paramView instanceof WithBubblePadding)) {
        ((WithBubblePadding)paramView).getOriginalPadding(arrayOfInt);
      }
      adjustViewPadding(arrayOfInt, paramView, j, localCardBubbleDrawableWithShadow.getBubbleSize());
      setBubbleGravityOnForeground(paramView, j);
    }
    
    protected void setBubbleGravityOnForeground(View paramView, int paramInt)
    {
      Drawable localDrawable;
      if ((paramView instanceof WithForegroundLayer))
      {
        localDrawable = ((WithForegroundLayer)paramView).getForeground();
        if (!(localDrawable instanceof CardBubbleForegroundDrawable)) {
          throw new IllegalArgumentException("Given view was not initialized by this CardBubbleViewGroupDelegate");
        }
      }
      for (CardBubbleForegroundDrawable localCardBubbleForegroundDrawable = (CardBubbleForegroundDrawable)localDrawable;; localCardBubbleForegroundDrawable = null)
      {
        if (localCardBubbleForegroundDrawable != null) {
          localCardBubbleForegroundDrawable.setBubbleGravity(paramInt);
        }
        return;
      }
    }
    
    public final void setCardElevation(View paramView, float paramFloat)
    {
      Drawable localDrawable = paramView.getBackground();
      if ((localDrawable instanceof RoundRectDrawableWithShadow)) {
        ((RoundRectDrawableWithShadow)localDrawable).setShadowSize(paramFloat);
      }
    }
  }
  
  private static final class CardBubbleViewGroupLollipop
    extends CardBubbleViewGroupDelegates.CardBubbleViewGroupGingerbread
  {
    private CardBubbleViewGroupLollipop()
    {
      super();
    }
    
    protected final Drawable createForegroundDrawable(Resources paramResources, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, int paramInt1, int paramInt2, int paramInt3)
    {
      return new CardBubbleForegroundRippleDrawable(paramResources, new CardBubbleForegroundDrawable(paramResources, R.color.play_white, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramInt1, paramInt2, paramInt3));
    }
    
    protected final void setBubbleGravityOnForeground(View paramView, int paramInt)
    {
      Drawable localDrawable;
      if ((paramView instanceof WithForegroundLayer))
      {
        localDrawable = ((WithForegroundLayer)paramView).getForeground();
        if (!(localDrawable instanceof CardBubbleForegroundRippleDrawable)) {
          throw new IllegalArgumentException("Given view was not initialized by this CardBubbleViewGroupDelegate");
        }
      }
      for (CardBubbleForegroundRippleDrawable localCardBubbleForegroundRippleDrawable = (CardBubbleForegroundRippleDrawable)localDrawable;; localCardBubbleForegroundRippleDrawable = null)
      {
        if (localCardBubbleForegroundRippleDrawable != null)
        {
          localCardBubbleForegroundRippleDrawable.mForegroundDrawable.setBubbleGravity(paramInt);
          localCardBubbleForegroundRippleDrawable.refreshMask(localCardBubbleForegroundRippleDrawable.getBounds());
        }
        return;
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.cardview.CardBubbleViewGroupDelegates
 * JD-Core Version:    0.7.0.1
 */