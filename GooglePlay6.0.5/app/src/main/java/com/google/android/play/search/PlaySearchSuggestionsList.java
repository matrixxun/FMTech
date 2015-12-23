package com.google.android.play.search;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.AdapterDataObserver;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.Transformation;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import com.google.android.play.R.dimen;
import com.google.android.play.R.id;
import com.google.android.play.image.BitmapLoader;
import java.util.Collections;
import java.util.List;

public class PlaySearchSuggestionsList
  extends LinearLayout
  implements PlaySearchListener
{
  private PlaySearchSuggestionAdapter mAdapter;
  private RecyclerView.AdapterDataObserver mAdapterDataObserver;
  private PlaySearchController mController;
  private final float mDensity;
  private int mMaxUsableScreenHeight;
  private final int mOneSuggestionHeight;
  RecyclerView mRecyclerView;
  private Animation mRecyclerViewAnimation;
  private int mScreenHeight;
  private final int mSuggestionsListBottomMargin;
  
  public PlaySearchSuggestionsList(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlaySearchSuggestionsList(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    Resources localResources = paramContext.getResources();
    this.mOneSuggestionHeight = localResources.getDimensionPixelSize(R.dimen.play_search_one_suggestion_height);
    this.mSuggestionsListBottomMargin = localResources.getDimensionPixelOffset(R.dimen.play_search_suggestions_list_bottom_margin);
    this.mDensity = getResources().getDisplayMetrics().density;
  }
  
  private void animateView(final boolean paramBoolean)
  {
    if (this.mRecyclerViewAnimation != null) {
      this.mRecyclerViewAnimation.cancel();
    }
    if (paramBoolean)
    {
      setVisibility(0);
      this.mRecyclerView.setVisibility(0);
      if (this.mMaxUsableScreenHeight != 0) {}
    }
    final int i;
    int j;
    do
    {
      return;
      i = this.mRecyclerView.getHeight();
      j = 0;
      if (paramBoolean) {
        j = Math.min(this.mMaxUsableScreenHeight, this.mAdapter.getItemCount() * this.mOneSuggestionHeight);
      }
    } while (i == j);
    final int k = j - i;
    Animation local4 = new Animation()
    {
      protected final void applyTransformation(float paramAnonymousFloat, Transformation paramAnonymousTransformation)
      {
        PlaySearchSuggestionsList.this.mRecyclerView.getLayoutParams().height = (i + (int)(paramAnonymousFloat * k));
        PlaySearchSuggestionsList.this.mRecyclerView.requestLayout();
      }
      
      public final boolean willChangeBounds()
      {
        return true;
      }
    };
    local4.setAnimationListener(new Animation.AnimationListener()
    {
      public final void onAnimationEnd(Animation paramAnonymousAnimation)
      {
        if (!paramBoolean)
        {
          PlaySearchSuggestionsList.this.setVisibility(8);
          PlaySearchSuggestionsList.this.mRecyclerView.setVisibility(8);
        }
        PlaySearchSuggestionsList.access$202$2ea0f9f1(PlaySearchSuggestionsList.this);
      }
      
      public final void onAnimationRepeat(Animation paramAnonymousAnimation) {}
      
      public final void onAnimationStart(Animation paramAnonymousAnimation) {}
    });
    local4.setDuration(Math.max(50, Math.min(200, (int)(k / this.mDensity))));
    this.mRecyclerViewAnimation = local4;
    this.mRecyclerView.startAnimation(local4);
  }
  
  private void clearAdapterData()
  {
    this.mAdapter.updateData(Collections.emptyList());
  }
  
  private void updateVisibility()
  {
    if (this.mController == null) {
      return;
    }
    int i = this.mController.mCurrentSearchMode;
    if ((this.mAdapter.getItemCount() <= 0) || (i == 1) || (i == 2))
    {
      animateView(false);
      return;
    }
    animateView(true);
  }
  
  public int getFocusViewId()
  {
    return this.mRecyclerView.getId();
  }
  
  public void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    if (this.mRecyclerViewAnimation != null)
    {
      this.mRecyclerViewAnimation.cancel();
      this.mRecyclerView.getLayoutParams().height = 0;
      this.mRecyclerView.setVisibility(8);
      setVisibility(8);
    }
  }
  
  public void onFinishInflate()
  {
    super.onFinishInflate();
    this.mRecyclerView = ((RecyclerView)findViewById(R.id.suggestion_list_recycler_view));
    this.mRecyclerView.setLayoutManager(new LinearLayoutManager()
    {
      public final View onInterceptFocusSearch(View paramAnonymousView, int paramAnonymousInt)
      {
        if ((paramAnonymousInt == 130) && (getPosition(paramAnonymousView) >= -1 + getItemCount())) {
          return paramAnonymousView;
        }
        return super.onInterceptFocusSearch(paramAnonymousView, paramAnonymousInt);
      }
    });
    this.mAdapter = new PlaySearchSuggestionAdapter();
    this.mRecyclerView.setAdapter(this.mAdapter);
    this.mAdapterDataObserver = new RecyclerView.AdapterDataObserver()
    {
      public final void onChanged()
      {
        PlaySearchSuggestionsList.this.updateVisibility();
      }
    };
    this.mAdapter.registerAdapterDataObserver(this.mAdapterDataObserver);
    final InputMethodManager localInputMethodManager = (InputMethodManager)getContext().getSystemService("input_method");
    this.mRecyclerView.setOnTouchListener(new View.OnTouchListener()
    {
      public final boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
      {
        localInputMethodManager.hideSoftInputFromWindow(paramAnonymousView.getWindowToken(), 0);
        return false;
      }
    });
    this.mScreenHeight = getContext().getResources().getDisplayMetrics().heightPixels;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    if (this.mMaxUsableScreenHeight == 0)
    {
      Rect localRect = new Rect();
      getGlobalVisibleRect(localRect);
      if (localRect.top > 0) {
        this.mMaxUsableScreenHeight = (this.mScreenHeight - localRect.top - this.mRecyclerView.getTop() + this.mSuggestionsListBottomMargin);
      }
      updateVisibility();
    }
  }
  
  public final void onModeChanged(int paramInt)
  {
    if (paramInt == 1)
    {
      clearAdapterData();
      return;
    }
    updateVisibility();
  }
  
  public final void onQueryChanged(String paramString, boolean paramBoolean) {}
  
  public final void onSearch(String paramString)
  {
    clearAdapterData();
  }
  
  public final void onSuggestionClicked(PlaySearchSuggestionModel paramPlaySearchSuggestionModel)
  {
    clearAdapterData();
  }
  
  public void setBitmapLoader(BitmapLoader paramBitmapLoader)
  {
    this.mAdapter.mBitmapLoader = paramBitmapLoader;
  }
  
  public void setPlaySearchController(PlaySearchController paramPlaySearchController)
  {
    if (this.mController != null) {
      this.mController.removePlaySearchListener(this);
    }
    this.mController = paramPlaySearchController;
    this.mController.addPlaySearchListener(this);
    this.mAdapter.mController = paramPlaySearchController;
  }
  
  public void setSuggestions(List<? extends PlaySearchSuggestionModel> paramList)
  {
    this.mAdapter.updateData(paramList);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.search.PlaySearchSuggestionsList
 * JD-Core Version:    0.7.0.1
 */