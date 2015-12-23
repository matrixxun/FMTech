package android.support.v4.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewPropertyAnimator;

final class ViewPropertyAnimatorCompatICS
{
  public static void setListener(final View paramView, ViewPropertyAnimatorListener paramViewPropertyAnimatorListener)
  {
    paramView.animate().setListener(new AnimatorListenerAdapter()
    {
      public final void onAnimationCancel(Animator paramAnonymousAnimator)
      {
        this.val$listener.onAnimationCancel(paramView);
      }
      
      public final void onAnimationEnd(Animator paramAnonymousAnimator)
      {
        this.val$listener.onAnimationEnd(paramView);
      }
      
      public final void onAnimationStart(Animator paramAnonymousAnimator)
      {
        this.val$listener.onAnimationStart(paramView);
      }
    });
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v4.view.ViewPropertyAnimatorCompatICS
 * JD-Core Version:    0.7.0.1
 */