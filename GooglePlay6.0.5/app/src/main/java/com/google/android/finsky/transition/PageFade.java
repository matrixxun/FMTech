package com.google.android.finsky.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.transition.Fade;
import android.transition.TransitionValues;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import com.google.android.finsky.utils.CorpusResourceUtils;
import java.util.Map;

@TargetApi(21)
public final class PageFade
  extends Fade
{
  private static int TRANSLATION_Z_CONTROLS_CONTAINER = 10;
  private static int TRANSLATION_Z_EMULATED_STATUS_BAR = 20;
  private final int mBackend;
  private final int[] mLocationInWindow = new int[2];
  private final Rect mRect = new Rect();
  
  public PageFade(int paramInt)
  {
    this.mBackend = paramInt;
  }
  
  private void captureValues(TransitionValues paramTransitionValues)
  {
    View localView = paramTransitionValues.view;
    if (localView.getId() == 2131755913)
    {
      paramTransitionValues.values.put("pageFade:translationZ", Integer.valueOf(TRANSLATION_Z_CONTROLS_CONTAINER));
      int i;
      do
      {
        localView = (View)localView.getParent();
        if (localView == null) {
          break;
        }
        i = localView.getId();
        if (i == 2131755700) {
          break;
        }
      } while (i != 2131755234);
      localView.getLocationInWindow(this.mLocationInWindow);
      paramTransitionValues.values.put("pageFade:topInset", Integer.valueOf(this.mLocationInWindow[1]));
    }
  }
  
  public final void captureEndValues(TransitionValues paramTransitionValues)
  {
    super.captureEndValues(paramTransitionValues);
    View localView = paramTransitionValues.view;
    if ((localView.getWidth() <= 0) || (localView.getHeight() <= 0)) {
      return;
    }
    captureValues(paramTransitionValues);
  }
  
  public final void captureStartValues(TransitionValues paramTransitionValues)
  {
    View localView = paramTransitionValues.view;
    if (!localView.getGlobalVisibleRect(this.mRect)) {}
    do
    {
      return;
      super.captureStartValues(paramTransitionValues);
    } while ((localView.getWidth() <= 0) || (localView.getHeight() <= 0));
    captureValues(paramTransitionValues);
  }
  
  public final Animator onDisappear(final ViewGroup paramViewGroup, final View paramView, final TransitionValues paramTransitionValues1, TransitionValues paramTransitionValues2)
  {
    Animator localAnimator = super.onDisappear(paramViewGroup, paramView, paramTransitionValues1, paramTransitionValues2);
    if ((paramTransitionValues1 != null) && (paramTransitionValues1.values.containsKey("pageFade:translationZ")) && (paramTransitionValues1.values.containsKey("pageFade:topInset"))) {}
    for (int i = 1; i == 0; i = 0) {
      return localAnimator;
    }
    paramViewGroup.getLocationInWindow(this.mLocationInWindow);
    if (this.mLocationInWindow[1] > 0)
    {
      localAnimator.addListener(new AnimatorListenerAdapter()
      {
        public final void onAnimationStart(Animator paramAnonymousAnimator)
        {
          paramView.setTranslationZ(((Integer)paramTransitionValues1.values.get("pageFade:translationZ")).intValue());
        }
      });
      return localAnimator;
    }
    int j = paramView.getWidth();
    int k = -1 + ((Integer)paramTransitionValues1.values.get("pageFade:topInset")).intValue();
    ColorDrawable localColorDrawable = new ColorDrawable(CorpusResourceUtils.getStatusBarColor(paramView.getContext(), this.mBackend));
    final NoOverlapView localNoOverlapView = new NoOverlapView(paramViewGroup.getContext());
    localNoOverlapView.setBackground(localColorDrawable);
    localNoOverlapView.measure(View.MeasureSpec.makeMeasureSpec(j, 1073741824), View.MeasureSpec.makeMeasureSpec(k, 1073741824));
    localNoOverlapView.layout(0, 0, j, k);
    paramViewGroup.getOverlay().add(localNoOverlapView);
    localNoOverlapView.setTranslationZ(TRANSLATION_Z_EMULATED_STATUS_BAR);
    ObjectAnimator localObjectAnimator = ObjectAnimator.ofFloat(localNoOverlapView, View.ALPHA, new float[] { 1.0F, 0.0F });
    AnimatorSet localAnimatorSet = new AnimatorSet();
    localAnimatorSet.playTogether(new Animator[] { localObjectAnimator, localAnimator });
    localAnimatorSet.addListener(new AnimatorListenerAdapter()
    {
      public final void onAnimationCancel(Animator paramAnonymousAnimator)
      {
        paramViewGroup.getOverlay().remove(localNoOverlapView);
      }
      
      public final void onAnimationEnd(Animator paramAnonymousAnimator)
      {
        paramViewGroup.getOverlay().remove(localNoOverlapView);
      }
      
      public final void onAnimationStart(Animator paramAnonymousAnimator)
      {
        paramView.setTranslationZ(((Integer)paramTransitionValues1.values.get("pageFade:translationZ")).intValue());
      }
    });
    return localAnimatorSet;
  }
  
  private static final class NoOverlapView
    extends View
  {
    public NoOverlapView(Context paramContext)
    {
      super();
    }
    
    public final boolean hasOverlappingRendering()
    {
      return false;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.transition.PageFade
 * JD-Core Version:    0.7.0.1
 */