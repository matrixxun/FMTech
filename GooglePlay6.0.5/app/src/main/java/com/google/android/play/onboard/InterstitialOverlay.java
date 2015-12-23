package com.google.android.play.onboard;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.google.android.play.R.color;
import com.google.android.play.R.id;
import com.google.android.play.R.layout;
import com.google.android.play.R.string;
import com.google.android.play.utils.collections.Lists;
import com.google.android.play.widget.PulsatingDotDrawable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class InterstitialOverlay
  extends FrameLayout
{
  public static final int[] DEFAULT_ACCENT_COLORS_RES_IDS;
  private static final int LAYOUT = R.layout.play_onboard_interstitial_overlay;
  private int[] mColors;
  private List<PulsatingDotDrawable> mDots;
  private final Random mRandom = new Random();
  
  static
  {
    int[] arrayOfInt = new int[4];
    arrayOfInt[0] = R.color.play_onboard_accent_color_a;
    arrayOfInt[1] = R.color.play_onboard_accent_color_b;
    arrayOfInt[2] = R.color.play_onboard_accent_color_c;
    arrayOfInt[3] = R.color.play_onboard_accent_color_d;
    DEFAULT_ACCENT_COLORS_RES_IDS = arrayOfInt;
  }
  
  public InterstitialOverlay(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public InterstitialOverlay(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public InterstitialOverlay(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  private int getRandomColor()
  {
    return this.mColors[this.mRandom.nextInt(this.mColors.length)];
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    if (this.mDots == null)
    {
      OnboardUtils.Predicate local1 = new OnboardUtils.Predicate() {};
      ArrayList localArrayList1 = new ArrayList();
      ArrayList localArrayList2 = new ArrayList();
      localArrayList2.add(this);
      while (!localArrayList2.isEmpty())
      {
        View localView2 = (View)localArrayList2.remove(0);
        if (local1.apply(localView2)) {
          localArrayList1.add(localView2);
        }
        if ((localView2 instanceof ViewGroup))
        {
          ViewGroup localViewGroup = (ViewGroup)localView2;
          int j = localViewGroup.getChildCount();
          for (int k = 0; k < j; k++) {
            localArrayList2.add(localViewGroup.getChildAt(k));
          }
        }
      }
      if ((this.mColors == null) || (this.mColors.length == 0))
      {
        Context localContext = getContext();
        int[] arrayOfInt1 = DEFAULT_ACCENT_COLORS_RES_IDS;
        int[] arrayOfInt2 = new int[arrayOfInt1.length];
        Resources localResources = localContext.getResources();
        for (int i = 0; i < arrayOfInt1.length; i++) {
          arrayOfInt2[i] = localResources.getColor(arrayOfInt1[i]);
        }
        setColors(arrayOfInt2);
      }
      ArrayList localArrayList3 = Lists.newArrayList(localArrayList1.size());
      Iterator localIterator1 = localArrayList1.iterator();
      if (localIterator1.hasNext())
      {
        View localView1 = (View)localIterator1.next();
        PulsatingDotDrawable localPulsatingDotDrawable = new PulsatingDotDrawable(getRandomColor(), this.mRandom.nextInt(800));
        if (Build.VERSION.SDK_INT < 16) {
          localView1.setBackgroundDrawable(localPulsatingDotDrawable);
        }
        for (;;)
        {
          localArrayList3.add(localPulsatingDotDrawable);
          break;
          localView1.setBackground(localPulsatingDotDrawable);
        }
      }
      this.mDots = localArrayList3;
    }
    Iterator localIterator2 = this.mDots.iterator();
    while (localIterator2.hasNext()) {
      ((PulsatingDotDrawable)localIterator2.next()).start();
    }
  }
  
  protected void onDetachedFromWindow()
  {
    Iterator localIterator = this.mDots.iterator();
    while (localIterator.hasNext()) {
      ((PulsatingDotDrawable)localIterator.next()).stop();
    }
    super.onDetachedFromWindow();
  }
  
  public void setCaption(int paramInt)
  {
    ((TextView)findViewById(R.id.caption)).setText(paramInt);
  }
  
  public void setCaption(CharSequence paramCharSequence)
  {
    ((TextView)findViewById(R.id.caption)).setText(paramCharSequence);
  }
  
  protected void setColors(int[] paramArrayOfInt)
  {
    this.mColors = paramArrayOfInt;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.onboard.InterstitialOverlay
 * JD-Core Version:    0.7.0.1
 */