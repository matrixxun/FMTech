package com.google.android.finsky.layout.play;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ListAdapter;
import android.widget.ListView;
import java.util.HashMap;
import java.util.Map;

public class PlayListView
  extends ListView
{
  public static final boolean ENABLE_ANIMATION;
  private static final boolean ENABLE_LAYER_CALLS;
  private Interpolator mAlphaInterpolator;
  private boolean mAnimateChanges = true;
  private DataSetObserver mAnimateObserver;
  private Map<Object, RectF> mCapturedPositions;
  private int[] mTempLocation;
  private RectF mTempRect;
  private Interpolator mTranslationInterpolator;
  
  static
  {
    boolean bool1 = true;
    boolean bool2;
    if (Build.VERSION.SDK_INT >= 14)
    {
      bool2 = bool1;
      ENABLE_ANIMATION = bool2;
      if (Build.VERSION.SDK_INT < 16) {
        break label34;
      }
    }
    for (;;)
    {
      ENABLE_LAYER_CALLS = bool1;
      return;
      bool2 = false;
      break;
      label34:
      bool1 = false;
    }
  }
  
  public PlayListView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayListView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    if (ENABLE_ANIMATION)
    {
      this.mAnimateObserver = new DataSetObserver()
      {
        public final void onChanged()
        {
          PlayListView.access$000(PlayListView.this);
        }
        
        public final void onInvalidated()
        {
          PlayListView.access$000(PlayListView.this);
        }
      };
      this.mTranslationInterpolator = new AccelerateDecelerateInterpolator();
      this.mAlphaInterpolator = new LinearInterpolator();
      this.mCapturedPositions = new HashMap();
      this.mTempLocation = new int[2];
      this.mTempRect = new RectF();
    }
  }
  
  private void disableClipChildren(ViewGroup paramViewGroup)
  {
    for (;;)
    {
      ViewGroup localViewGroup = paramViewGroup;
      paramViewGroup.setClipChildren(false);
      ViewParent localViewParent = localViewGroup.getParent();
      if ((!(localViewParent instanceof ViewGroup)) || (paramViewGroup == this)) {
        break;
      }
      paramViewGroup = (ViewGroup)localViewParent;
    }
  }
  
  @TargetApi(16)
  private void traverse(View paramView, boolean paramBoolean)
  {
    String str;
    if ((paramView instanceof Identifiable))
    {
      str = ((Identifiable)paramView).getIdentifier();
      if (paramBoolean)
      {
        paramView.getLocationInWindow(this.mTempLocation);
        this.mCapturedPositions.put(str, new RectF(this.mTempLocation[0], this.mTempLocation[1], this.mTempLocation[0] + paramView.getWidth(), this.mTempLocation[1] + paramView.getHeight()));
        paramView.animate().cancel();
        paramView.setAlpha(1.0F);
        paramView.setTranslationX(0.0F);
        paramView.setTranslationY(0.0F);
      }
    }
    for (;;)
    {
      return;
      RectF localRectF = (RectF)this.mCapturedPositions.get(str);
      if (localRectF != null)
      {
        paramView.getLocationInWindow(this.mTempLocation);
        this.mTempRect.set(this.mTempLocation[0], this.mTempLocation[1], this.mTempLocation[0] + paramView.getWidth(), this.mTempLocation[1] + paramView.getHeight());
        float f1 = localRectF.centerX() - this.mTempRect.centerX();
        float f2 = localRectF.centerY() - this.mTempRect.centerY();
        if ((Math.abs(f1) > 5.0F) || (Math.abs(f2) > 5.0F))
        {
          paramView.setTranslationX(f1);
          paramView.setTranslationY(f2);
          paramView.animate().translationX(0.0F).translationY(0.0F).setDuration(150L).setInterpolator(this.mTranslationInterpolator).setStartDelay(0L);
          ViewParent localViewParent = paramView.getParent();
          if ((localViewParent instanceof ViewGroup)) {
            disableClipChildren((ViewGroup)localViewParent);
          }
        }
      }
      else
      {
        paramView.setAlpha(0.0F);
        paramView.animate().alpha(1.0F).setStartDelay(150L).setInterpolator(this.mAlphaInterpolator).setDuration(150L);
        if (ENABLE_LAYER_CALLS)
        {
          paramView.animate().withLayer();
          return;
          if ((paramView instanceof ViewGroup))
          {
            ViewGroup localViewGroup = (ViewGroup)paramView;
            for (int i = 0; i < localViewGroup.getChildCount(); i++) {
              traverse(localViewGroup.getChildAt(i), paramBoolean);
            }
          }
        }
      }
    }
  }
  
  protected void dispatchDraw(Canvas paramCanvas)
  {
    super.dispatchDraw(paramCanvas);
    if ((ENABLE_ANIMATION) && (this.mAnimateChanges) && (!this.mCapturedPositions.isEmpty()))
    {
      traverse(this, false);
      this.mCapturedPositions.clear();
    }
  }
  
  public void setAdapter(ListAdapter paramListAdapter)
  {
    ListAdapter localListAdapter = getAdapter();
    if ((ENABLE_ANIMATION) && (this.mAnimateChanges) && (localListAdapter != null)) {
      localListAdapter.unregisterDataSetObserver(this.mAnimateObserver);
    }
    super.setAdapter(paramListAdapter);
    if ((ENABLE_ANIMATION) && (this.mAnimateChanges) && (paramListAdapter != null)) {
      paramListAdapter.registerDataSetObserver(this.mAnimateObserver);
    }
  }
  
  public void setAnimateChanges(boolean paramBoolean)
  {
    ListAdapter localListAdapter;
    if ((ENABLE_ANIMATION) && (this.mAnimateChanges != paramBoolean))
    {
      this.mAnimateChanges = paramBoolean;
      localListAdapter = getAdapter();
      if (localListAdapter != null)
      {
        if (!paramBoolean) {
          break label43;
        }
        localListAdapter.registerDataSetObserver(this.mAnimateObserver);
      }
    }
    return;
    label43:
    localListAdapter.unregisterDataSetObserver(this.mAnimateObserver);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayListView
 * JD-Core Version:    0.7.0.1
 */