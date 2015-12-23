package android.support.v4.animation;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewPropertyAnimator;

final class HoneycombMr1AnimatorCompatProvider
  implements AnimatorProvider
{
  private TimeInterpolator mDefaultInterpolator;
  
  public final void clearInterpolator(View paramView)
  {
    if (this.mDefaultInterpolator == null) {
      this.mDefaultInterpolator = new ValueAnimator().getInterpolator();
    }
    paramView.animate().setInterpolator(this.mDefaultInterpolator);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v4.animation.HoneycombMr1AnimatorCompatProvider
 * JD-Core Version:    0.7.0.1
 */