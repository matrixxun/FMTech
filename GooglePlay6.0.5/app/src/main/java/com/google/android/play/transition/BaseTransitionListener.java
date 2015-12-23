package com.google.android.play.transition;

import android.annotation.TargetApi;
import android.transition.Transition;
import android.transition.Transition.TransitionListener;

@TargetApi(21)
public class BaseTransitionListener
  implements Transition.TransitionListener
{
  public void onTransitionCancel(Transition paramTransition) {}
  
  public void onTransitionEnd(Transition paramTransition) {}
  
  public void onTransitionPause(Transition paramTransition) {}
  
  public void onTransitionResume(Transition paramTransition) {}
  
  public void onTransitionStart(Transition paramTransition) {}
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.transition.BaseTransitionListener
 * JD-Core Version:    0.7.0.1
 */