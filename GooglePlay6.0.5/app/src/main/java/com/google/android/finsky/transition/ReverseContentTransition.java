package com.google.android.finsky.transition;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionValues;
import android.transition.Visibility;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import java.util.Map;

@TargetApi(19)
public final class ReverseContentTransition
  extends Visibility
{
  public final void captureStartValues(TransitionValues paramTransitionValues)
  {
    super.captureStartValues(paramTransitionValues);
    View localView1 = paramTransitionValues.view;
    View localView2 = localView1;
    ViewParent localViewParent = localView2.getParent();
    if ((localViewParent instanceof RecyclerView)) {}
    label160:
    for (RecyclerView localRecyclerView = (RecyclerView)localViewParent;; localRecyclerView = null)
    {
      if (localRecyclerView != null)
      {
        int i = localView1.getTop();
        int j = localView1.getBottom();
        int k = localRecyclerView.getChildCount();
        int m = j;
        int n = i;
        int i1 = 0;
        for (;;)
        {
          if (i1 < k)
          {
            View localView3 = localRecyclerView.getChildAt(i1);
            if (localView3.getVisibility() == 0)
            {
              n = Math.min(n, localView3.getTop());
              m = Math.max(m, localView3.getBottom());
            }
            i1++;
            continue;
            if (!(localViewParent instanceof View)) {
              break label160;
            }
            localView2 = (View)localViewParent;
            break;
          }
        }
        paramTransitionValues.values.put("translate:y", Integer.valueOf(m - n));
      }
      return;
    }
  }
  
  public final Animator onAppear(ViewGroup paramViewGroup, View paramView, TransitionValues paramTransitionValues1, TransitionValues paramTransitionValues2)
  {
    return null;
  }
  
  public final Animator onDisappear(ViewGroup paramViewGroup, View paramView, TransitionValues paramTransitionValues1, TransitionValues paramTransitionValues2)
  {
    if ((paramTransitionValues1 == null) || (!paramTransitionValues1.values.containsKey("translate:y"))) {
      return null;
    }
    int i = ((Integer)paramTransitionValues1.values.get("translate:y")).intValue();
    float[] arrayOfFloat = new float[2];
    arrayOfFloat[0] = 0.0F;
    arrayOfFloat[1] = i;
    ObjectAnimator localObjectAnimator1 = ObjectAnimator.ofFloat(paramView, "translationY", arrayOfFloat);
    ObjectAnimator localObjectAnimator2 = ObjectAnimator.ofFloat(paramView, "alpha", new float[] { 1.0F, 0.6F });
    AnimatorSet localAnimatorSet = new AnimatorSet();
    localAnimatorSet.playTogether(new Animator[] { localObjectAnimator1, localObjectAnimator2 });
    return new NoPauseAnimator(localAnimatorSet);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.transition.ReverseContentTransition
 * JD-Core Version:    0.7.0.1
 */