package com.google.android.play.search;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.view.CollapsibleActionView;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewAnimationUtils;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import com.google.android.play.R.id;
import com.google.android.play.image.BitmapLoader;
import java.util.List;

public class PlaySearch
  extends FrameLayout
  implements CollapsibleActionView, View.OnFocusChangeListener, PlaySearchListener
{
  static final boolean CAN_USE_RIPPLE_ANIMATION;
  public PlaySearchController mController;
  private PlaySearchListener mListener;
  private View mOverlay;
  private Point mRevealCenter;
  View mSearchContainer;
  public PlaySearchPlate mSearchPlate;
  private int mSearchPlateMarginBottom;
  private int mSearchPlateMarginLeft;
  private int mSearchPlateMarginRight;
  private int mSearchPlateMarginTop;
  private PlaySearchSuggestionsList mSuggestionsList;
  
  static
  {
    if (Build.VERSION.SDK_INT >= 21) {}
    for (boolean bool = true;; bool = false)
    {
      CAN_USE_RIPPLE_ANIMATION = bool;
      return;
    }
  }
  
  public PlaySearch(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlaySearch(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  private void updateOverlayVisibility(final boolean paramBoolean1, boolean paramBoolean2)
  {
    float f1 = 1.0F;
    if (this.mOverlay == null) {
      return;
    }
    label22:
    float f2;
    if (paramBoolean1)
    {
      this.mOverlay.setVisibility(0);
      if (!paramBoolean1) {
        break label107;
      }
      f2 = 0.0F;
      label29:
      if (!paramBoolean1) {
        break label113;
      }
    }
    for (;;)
    {
      AlphaAnimation localAlphaAnimation = new AlphaAnimation(f2, f1);
      localAlphaAnimation.setDuration(300L);
      localAlphaAnimation.setAnimationListener(new Animation.AnimationListener()
      {
        public final void onAnimationEnd(Animation paramAnonymousAnimation)
        {
          if (!paramBoolean1) {
            PlaySearch.this.mOverlay.setVisibility(8);
          }
        }
        
        public final void onAnimationRepeat(Animation paramAnonymousAnimation) {}
        
        public final void onAnimationStart(Animation paramAnonymousAnimation) {}
      });
      this.mOverlay.startAnimation(localAlphaAnimation);
      return;
      if (this.mOverlay.getVisibility() == 8) {
        break;
      }
      if ((paramBoolean1) || (paramBoolean2)) {
        break label22;
      }
      this.mOverlay.setVisibility(8);
      return;
      label107:
      f2 = f1;
      break label29;
      label113:
      f1 = 0.0F;
    }
  }
  
  public final void configure(BitmapLoader paramBitmapLoader)
  {
    this.mSuggestionsList.setBitmapLoader(paramBitmapLoader);
  }
  
  public int getMode()
  {
    return this.mController.mCurrentSearchMode;
  }
  
  Point getRevealCenter()
  {
    if (this.mRevealCenter != null)
    {
      Point localPoint1 = new Point(this.mRevealCenter);
      Rect localRect = new Rect();
      Point localPoint2 = new Point();
      getGlobalVisibleRect(localRect, localPoint2);
      localPoint1.offset(-localPoint2.x, -localPoint2.y);
      return localPoint1;
    }
    View localView = (View)getParent();
    return new Point(localView.getRight() - localView.getHeight() / 2, (localView.getTop() + localView.getBottom()) / 2);
  }
  
  public int getSearchPlateMarginBottom()
  {
    return this.mSearchPlateMarginBottom;
  }
  
  public int getSearchPlateMarginLeft()
  {
    return this.mSearchPlateMarginLeft;
  }
  
  public int getSearchPlateMarginRight()
  {
    return this.mSearchPlateMarginRight;
  }
  
  public int getSearchPlateMarginTop()
  {
    return this.mSearchPlateMarginTop;
  }
  
  public int getSteadyStateMode()
  {
    return this.mController.mSteadyStateMode;
  }
  
  public void onActionViewCollapsed()
  {
    this.mController.switchToSteadyStateMode();
  }
  
  public void onActionViewExpanded()
  {
    setVisibility(0);
    if (CAN_USE_RIPPLE_ANIMATION)
    {
      View localView = (View)getParent();
      if ((localView == null) || (this.mSearchContainer == null) || (!this.mSearchContainer.isAttachedToWindow())) {
        return;
      }
      Point localPoint = getRevealCenter();
      Animator localAnimator = ViewAnimationUtils.createCircularReveal(this.mSearchContainer, localPoint.x, localPoint.y, 0.0F, localView.getWidth());
      localAnimator.setDuration(300L);
      this.mController.switchToSteadyStateMode();
      localAnimator.addListener(new AnimatorListenerAdapter()
      {
        public final void onAnimationEnd(Animator paramAnonymousAnimator)
        {
          super.onAnimationEnd(paramAnonymousAnimator);
          PlaySearch.this.switchToMode(3);
        }
      });
      localAnimator.start();
      return;
    }
    switchToMode(3);
  }
  
  protected void onDetachedFromWindow()
  {
    updateOverlayVisibility(false, false);
    super.onDetachedFromWindow();
  }
  
  public void onFinishInflate()
  {
    super.onFinishInflate();
    this.mSearchPlate = ((PlaySearchPlate)findViewById(R.id.play_search_plate));
    this.mSuggestionsList = ((PlaySearchSuggestionsList)findViewById(R.id.play_search_suggestions_list));
    this.mOverlay = findViewById(R.id.play_search_overlay);
    this.mSearchContainer = findViewById(R.id.play_search_container);
    this.mOverlay.setOnClickListener(new View.OnClickListener()
    {
      public final void onClick(View paramAnonymousView)
      {
        PlaySearch.this.mController.switchToSteadyStateMode();
      }
    });
    this.mController = new PlaySearchController();
    this.mController.addPlaySearchListener(this);
    this.mSearchPlate.setPlaySearchController(this.mController);
    this.mSuggestionsList.setPlaySearchController(this.mController);
    PlaySearchPlate localPlaySearchPlate = this.mSearchPlate;
    int i = this.mSuggestionsList.getFocusViewId();
    PlaySearchPlateTextContainer localPlaySearchPlateTextContainer = localPlaySearchPlate.mSearchPlateTextContainer;
    localPlaySearchPlateTextContainer.mSearchBoxTextInput.setOnFocusChangeListener(this);
    localPlaySearchPlateTextContainer.mSearchBoxTextInput.setNextFocusDownId(i);
    PlaySearchSuggestionsList localPlaySearchSuggestionsList = this.mSuggestionsList;
    int j = this.mSearchPlate.getFocusViewId();
    localPlaySearchSuggestionsList.mRecyclerView.setOnFocusChangeListener(this);
    localPlaySearchSuggestionsList.mRecyclerView.setNextFocusUpId(j);
  }
  
  public void onFocusChange(View paramView, boolean paramBoolean)
  {
    if (!paramBoolean) {
      post(new Runnable()
      {
        public final void run()
        {
          if ((PlaySearch.this.mController != null) && (!PlaySearch.this.hasFocus())) {
            PlaySearch.this.mController.switchToSteadyStateMode();
          }
        }
      });
    }
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (this.mOverlay.getVisibility() != 8) {
      this.mOverlay.layout(0, 0, this.mOverlay.getMeasuredWidth(), this.mOverlay.getMeasuredHeight());
    }
    this.mSearchContainer.layout(this.mSearchPlateMarginLeft, this.mSearchPlateMarginTop, this.mSearchPlateMarginLeft + this.mSearchContainer.getMeasuredWidth(), this.mSearchPlateMarginTop + this.mSearchContainer.getMeasuredHeight());
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    int k = i - this.mSearchPlateMarginLeft - this.mSearchPlateMarginRight;
    this.mSearchContainer.measure(View.MeasureSpec.makeMeasureSpec(k, 1073741824), 0);
    int m = this.mOverlay.getVisibility();
    int n = 0;
    if (m == 8) {
      n = 1;
    }
    if (n == 0) {
      this.mOverlay.measure(View.MeasureSpec.makeMeasureSpec(i, 1073741824), View.MeasureSpec.makeMeasureSpec(j, 1073741824));
    }
    if (n != 0) {}
    for (int i1 = this.mSearchContainer.getMeasuredHeight() + this.mSearchPlateMarginTop + this.mSearchPlateMarginBottom;; i1 = j)
    {
      setMeasuredDimension(i, i1);
      return;
    }
  }
  
  public void onModeChanged(int paramInt)
  {
    if (this.mListener != null) {
      this.mListener.onModeChanged(paramInt);
    }
    if (paramInt == 3) {}
    for (boolean bool1 = true;; bool1 = false)
    {
      boolean bool2;
      if (!bool1)
      {
        bool2 = false;
        if (paramInt != 2) {}
      }
      else
      {
        bool2 = true;
      }
      updateOverlayVisibility(bool1, bool2);
      return;
    }
  }
  
  public void onQueryChanged(String paramString, boolean paramBoolean)
  {
    if (this.mListener != null) {
      this.mListener.onQueryChanged(paramString, paramBoolean);
    }
  }
  
  protected void onRestoreInstanceState(Parcelable paramParcelable)
  {
    if ((paramParcelable instanceof Bundle))
    {
      Bundle localBundle = (Bundle)paramParcelable;
      super.onRestoreInstanceState(localBundle.getParcelable("play_search.parent_instance_state"));
      int i = localBundle.getInt("play_search.mode_state", -1);
      if (i >= 0) {
        switchToMode(i);
      }
      String str = localBundle.getString("play_search.query_state");
      if (str != null) {
        setQuery(str);
      }
      return;
    }
    super.onRestoreInstanceState(paramParcelable);
  }
  
  protected Parcelable onSaveInstanceState()
  {
    Bundle localBundle = new Bundle();
    localBundle.putParcelable("play_search.parent_instance_state", super.onSaveInstanceState());
    if (this.mController != null)
    {
      localBundle.putInt("play_search.mode_state", getMode());
      localBundle.putString("play_search.query_state", this.mController.mCurrentQuery);
    }
    return localBundle;
  }
  
  public void onSearch(String paramString)
  {
    if (this.mListener != null) {
      this.mListener.onSearch(paramString);
    }
  }
  
  public void onSuggestionClicked(PlaySearchSuggestionModel paramPlaySearchSuggestionModel)
  {
    if (this.mListener != null) {
      this.mListener.onSuggestionClicked(paramPlaySearchSuggestionModel);
    }
  }
  
  public void setHint(CharSequence paramCharSequence)
  {
    this.mSearchPlate.setHint(paramCharSequence);
  }
  
  public void setIdleModeDrawerIconState(int paramInt)
  {
    this.mSearchPlate.setIdleModeDrawerIconState(paramInt);
  }
  
  public void setListener(PlaySearchListener paramPlaySearchListener)
  {
    this.mListener = paramPlaySearchListener;
  }
  
  public void setOnNavButtonClickListener(View.OnClickListener paramOnClickListener)
  {
    this.mController.mNavigationClickListener = paramOnClickListener;
  }
  
  public void setQuery(String paramString)
  {
    this.mController.setQueryInternal(paramString, true);
  }
  
  public void setRevealCenter(Point paramPoint)
  {
    this.mRevealCenter = new Point(paramPoint);
  }
  
  public final void setSearchBoxMargins(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean)
  {
    this.mSearchPlateMarginLeft = paramInt1;
    this.mSearchPlateMarginTop = paramInt2;
    this.mSearchPlateMarginRight = paramInt3;
    this.mSearchPlateMarginBottom = paramInt4;
    if (paramBoolean) {
      requestLayout();
    }
  }
  
  public void setSteadyStateMode(int paramInt)
  {
    if ((paramInt != 1) && (paramInt != 2)) {
      throw new UnsupportedOperationException("Unsupported search box steady state mode");
    }
    this.mController.mSteadyStateMode = paramInt;
  }
  
  public void setSuggestions(List<? extends PlaySearchSuggestionModel> paramList)
  {
    this.mSuggestionsList.setSuggestions(paramList);
  }
  
  public final void switchToMode(int paramInt)
  {
    this.mController.setMode(paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.search.PlaySearch
 * JD-Core Version:    0.7.0.1
 */