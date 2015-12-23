package com.google.android.finsky.utils.hats;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Build.VERSION;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.finsky.activities.SurveyActivity;
import com.google.android.finsky.layout.ButtonBar.ClickListener;
import com.google.android.finsky.protos.Survey;

public final class HappinessSurveyControllerV2
{
  public final ViewGroup mContainerView;
  public final Activity mParentActivity;
  public Survey mSurvey;
  
  public HappinessSurveyControllerV2(Activity paramActivity, ViewGroup paramViewGroup)
  {
    this.mParentActivity = paramActivity;
    this.mContainerView = paramViewGroup;
  }
  
  public final void hideSurveyPrompt()
  {
    if (this.mContainerView == null) {}
    final View localView;
    do
    {
      return;
      localView = this.mContainerView.findViewById(2131756157);
    } while ((localView == null) || (localView.getVisibility() == 8));
    if (Build.VERSION.SDK_INT >= 11)
    {
      float[] arrayOfFloat = new float[1];
      arrayOfFloat[0] = localView.getHeight();
      ObjectAnimator localObjectAnimator = ObjectAnimator.ofFloat(localView, "translationY", arrayOfFloat);
      localObjectAnimator.addListener(new AnimatorListenerAdapter()
      {
        public final void onAnimationCancel(Animator paramAnonymousAnimator)
        {
          localView.setVisibility(8);
        }
        
        public final void onAnimationEnd(Animator paramAnonymousAnimator)
        {
          localView.setVisibility(8);
        }
      });
      localObjectAnimator.start();
      return;
    }
    localView.setVisibility(8);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.hats.HappinessSurveyControllerV2
 * JD-Core Version:    0.7.0.1
 */