package com.google.android.finsky.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.transition.PathMotion;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import com.google.android.play.image.BitmapTransformation;
import com.google.android.play.image.FifeImageView;
import java.util.Map;

@TargetApi(21)
public final class CircleTransition
  extends Transition
{
  private static final String[] TRANSITION_PROPERTIES = { "circleTransition:bounds", "circleTransition:position" };
  public int mCircleSizeCap = -1;
  private final int mColor;
  
  public CircleTransition(int paramInt)
  {
    this.mColor = paramInt;
  }
  
  private static View addViewToOverlay(ViewGroup paramViewGroup, int paramInt1, int paramInt2, Drawable paramDrawable)
  {
    NoOverlapView localNoOverlapView = new NoOverlapView(paramViewGroup.getContext());
    localNoOverlapView.setBackground(paramDrawable);
    localNoOverlapView.measure(View.MeasureSpec.makeMeasureSpec(paramInt1, 1073741824), View.MeasureSpec.makeMeasureSpec(paramInt2, 1073741824));
    localNoOverlapView.layout(0, 0, paramInt1, paramInt2);
    paramViewGroup.getOverlay().add(localNoOverlapView);
    return localNoOverlapView;
  }
  
  private static float calculateMaxRadius(View paramView)
  {
    return 0.5F * (float)Math.sqrt(paramView.getWidth() * paramView.getWidth() + paramView.getHeight() * paramView.getHeight());
  }
  
  private static int calculateMinRadius(View paramView)
  {
    return Math.min(paramView.getWidth() / 2, paramView.getHeight() / 2);
  }
  
  private static void captureValues(TransitionValues paramTransitionValues)
  {
    View localView = paramTransitionValues.view;
    paramTransitionValues.values.put("circleTransition:bounds", new Rect(localView.getLeft(), localView.getTop(), localView.getRight(), localView.getBottom()));
    int[] arrayOfInt = new int[2];
    paramTransitionValues.view.getLocationInWindow(arrayOfInt);
    paramTransitionValues.values.put("circleTransition:position", arrayOfInt);
    if ((localView instanceof FifeImageView))
    {
      Map localMap = paramTransitionValues.values;
      BitmapTransformation localBitmapTransformation = ((FifeImageView)localView).mBitmapTransformation;
      boolean bool = false;
      if (localBitmapTransformation != null) {
        bool = true;
      }
      localMap.put("circleTransition:isAvatar", Boolean.valueOf(bool));
      return;
    }
    paramTransitionValues.values.put("circleTransition:isAvatar", Boolean.valueOf(false));
  }
  
  private static Animator createCircularReveal(View paramView, float paramFloat1, float paramFloat2)
  {
    return new NoPauseAnimator(ViewAnimationUtils.createCircularReveal(paramView, paramView.getWidth() / 2, paramView.getHeight() / 2, paramFloat1, paramFloat2));
  }
  
  public final void captureEndValues(TransitionValues paramTransitionValues)
  {
    View localView = paramTransitionValues.view;
    if ((localView.getWidth() <= 0) || (localView.getHeight() <= 0)) {
      return;
    }
    captureValues(paramTransitionValues);
  }
  
  public final void captureStartValues(TransitionValues paramTransitionValues)
  {
    View localView = paramTransitionValues.view;
    if ((localView.getWidth() <= 0) || (localView.getHeight() <= 0)) {
      return;
    }
    captureValues(paramTransitionValues);
    Bitmap localBitmap = Bitmap.createBitmap(localView.getWidth(), localView.getHeight(), Bitmap.Config.ARGB_8888);
    localView.draw(new Canvas(localBitmap));
    paramTransitionValues.values.put("circleTransition:image", localBitmap);
  }
  
  public final Animator createAnimator(final ViewGroup paramViewGroup, TransitionValues paramTransitionValues1, TransitionValues paramTransitionValues2)
  {
    if ((paramTransitionValues1 == null) || (paramTransitionValues2 == null)) {
      return null;
    }
    boolean bool1 = ((Boolean)paramTransitionValues1.values.get("circleTransition:isAvatar")).booleanValue();
    boolean bool2 = ((Boolean)paramTransitionValues2.values.get("circleTransition:isAvatar")).booleanValue();
    Rect localRect1 = (Rect)paramTransitionValues1.values.get("circleTransition:bounds");
    Rect localRect2 = (Rect)paramTransitionValues2.values.get("circleTransition:bounds");
    if ((localRect1 == null) || (localRect2 == null) || (localRect1.equals(localRect2))) {
      return null;
    }
    Bitmap localBitmap = (Bitmap)paramTransitionValues1.values.get("circleTransition:image");
    BitmapDrawable localBitmapDrawable = new BitmapDrawable(localBitmap);
    final View localView1 = addViewToOverlay(paramViewGroup, localBitmap.getWidth(), localBitmap.getHeight(), localBitmapDrawable);
    ColorDrawable localColorDrawable = new ColorDrawable(this.mColor);
    OvalShape localOvalShape = new OvalShape();
    Object localObject1 = new ShapeDrawable(localOvalShape);
    ((ShapeDrawable)localObject1).getPaint().setColor(this.mColor);
    int i = localBitmap.getWidth();
    int j = localBitmap.getHeight();
    Object localObject2;
    final View localView2;
    int[] arrayOfInt1;
    final View localView3;
    int n;
    final int i1;
    label350:
    final View localView4;
    Animator localAnimator1;
    Animator localAnimator2;
    ObjectAnimator localObjectAnimator1;
    AnimatorSet localAnimatorSet1;
    int[] arrayOfInt3;
    ObjectAnimator localObjectAnimator2;
    ObjectAnimator localObjectAnimator3;
    int i2;
    int i3;
    if (bool1)
    {
      localObject2 = localObject1;
      localView2 = addViewToOverlay(paramViewGroup, i, j, (Drawable)localObject2);
      arrayOfInt1 = new int[2];
      paramViewGroup.getLocationInWindow(arrayOfInt1);
      int[] arrayOfInt2 = (int[])paramTransitionValues1.values.get("circleTransition:position");
      int k = arrayOfInt2[0] - arrayOfInt1[0];
      int m = arrayOfInt2[1] - arrayOfInt1[1];
      localView1.setTranslationX(k);
      localView1.setTranslationY(m);
      localView2.setTranslationX(k);
      localView2.setTranslationY(m);
      localView3 = paramTransitionValues2.view;
      float f1 = calculateMaxRadius(localView2);
      n = Math.min(calculateMinRadius(localView2), calculateMinRadius(localView3));
      if (this.mCircleSizeCap > 0) {
        break label935;
      }
      i1 = n;
      ShapeDrawable localShapeDrawable = new ShapeDrawable(new OvalShape());
      localShapeDrawable.getPaint().setColor(this.mColor);
      localView4 = addViewToOverlay(paramViewGroup, i1 * 2, i1 * 2, localShapeDrawable);
      float f2 = arrayOfInt2[0] - arrayOfInt1[0] + (localView1.getWidth() - localView2.getWidth()) / 2;
      float f3 = arrayOfInt2[1] - arrayOfInt1[1] + (localView1.getHeight() - localView2.getHeight()) / 2;
      localView2.setAlpha(0.0F);
      localView3.setAlpha(0.0F);
      localAnimator1 = createCircularReveal(localView2, f1, i1);
      localAnimator2 = createCircularReveal(localView1, f1, i1);
      localObjectAnimator1 = ObjectAnimator.ofFloat(localView2, View.ALPHA, new float[] { 0.0F, 1.0F });
      localAnimatorSet1 = new AnimatorSet();
      arrayOfInt3 = (int[])paramTransitionValues2.values.get("circleTransition:position");
      float f4 = arrayOfInt3[0] - arrayOfInt1[0] + (localView3.getWidth() - localView2.getWidth()) / 2;
      float f5 = arrayOfInt3[1] - arrayOfInt1[1] + (localView3.getHeight() - localView2.getHeight()) / 2;
      Path localPath = getPathMotion().getPath(f2, f3, f4, f5);
      localView4.setVisibility(4);
      localObjectAnimator2 = ObjectAnimator.ofFloat(localView2, View.TRANSLATION_X, View.TRANSLATION_Y, localPath);
      localObjectAnimator3 = ObjectAnimator.ofFloat(localView1, View.TRANSLATION_X, View.TRANSLATION_Y, localPath);
      i2 = localView3.getWidth();
      i3 = localView3.getHeight();
      if (!bool2) {
        break label951;
      }
    }
    for (;;)
    {
      final View localView5 = addViewToOverlay(paramViewGroup, i2, i3, (Drawable)localObject1);
      localView5.setVisibility(4);
      float f6 = arrayOfInt3[0] - arrayOfInt1[0];
      float f7 = arrayOfInt3[1] - arrayOfInt1[1];
      localView5.setTranslationX(f6);
      localView5.setTranslationY(f7);
      float f8 = calculateMaxRadius(localView3);
      localAnimatorSet1.playTogether(new Animator[] { localAnimator1, localAnimator2, localObjectAnimator1, localObjectAnimator2, localObjectAnimator3 });
      localAnimator1.addListener(new AnimatorListenerAdapter()
      {
        public final void onAnimationEnd(Animator paramAnonymousAnimator)
        {
          localView2.setVisibility(4);
          localView1.setVisibility(4);
          localView4.setVisibility(0);
          int i = (int)(localView1.getX() + localView1.getWidth() / 2);
          int j = (int)(localView1.getY() + localView1.getHeight() / 2);
          localView4.setTranslationX(i - i1);
          localView4.setTranslationY(j - i1);
        }
      });
      ObjectAnimator localObjectAnimator4 = ObjectAnimator.ofFloat(localView5, View.ALPHA, new float[] { 1.0F, 0.0F });
      Animator localAnimator3 = createCircularReveal(localView3, i1, f8);
      Animator localAnimator4 = createCircularReveal(localView5, i1, f8);
      localAnimator4.addListener(new AnimatorListenerAdapter()
      {
        public final void onAnimationEnd(Animator paramAnonymousAnimator)
        {
          ViewGroupOverlay localViewGroupOverlay = paramViewGroup.getOverlay();
          localViewGroupOverlay.remove(localView1);
          localViewGroupOverlay.remove(localView4);
          localViewGroupOverlay.remove(localView2);
          localViewGroupOverlay.remove(localView5);
        }
        
        public final void onAnimationStart(Animator paramAnonymousAnimator)
        {
          localView4.setVisibility(4);
          localView3.setAlpha(1.0F);
          localView5.setVisibility(0);
        }
      });
      AnimatorSet localAnimatorSet2 = new AnimatorSet();
      localAnimatorSet2.playTogether(new Animator[] { localObjectAnimator4, localAnimator3, localAnimator4 });
      AnimatorSet localAnimatorSet3 = new AnimatorSet();
      localAnimatorSet3.playSequentially(new Animator[] { localAnimatorSet1, localAnimatorSet2 });
      localAnimatorSet3.addListener(new AnimatorListenerAdapter()
      {
        public final void onAnimationCancel(Animator paramAnonymousAnimator)
        {
          ViewGroupOverlay localViewGroupOverlay = paramViewGroup.getOverlay();
          localViewGroupOverlay.remove(localView1);
          localViewGroupOverlay.remove(localView4);
          localViewGroupOverlay.remove(localView2);
          localViewGroupOverlay.remove(localView5);
        }
      });
      return localAnimatorSet3;
      localObject2 = localColorDrawable;
      break;
      label935:
      i1 = Math.min(n, this.mCircleSizeCap / 2);
      break label350;
      label951:
      localObject1 = localColorDrawable;
    }
  }
  
  public final String[] getTransitionProperties()
  {
    return TRANSITION_PROPERTIES;
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
 * Qualified Name:     com.google.android.finsky.transition.CircleTransition
 * JD-Core Version:    0.7.0.1
 */