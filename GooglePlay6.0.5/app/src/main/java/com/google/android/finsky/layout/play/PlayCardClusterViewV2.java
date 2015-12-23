package com.google.android.finsky.layout.play;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView.RecycledViewPool;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.MarginLayoutParams;
import com.google.android.finsky.api.model.OnDataChangedListener;
import com.google.android.finsky.layout.ClusterContentBinder;
import com.google.android.finsky.layout.ClusterContentConfigurator;
import com.google.android.finsky.utils.PlayCardImageTypeSequence;

public class PlayCardClusterViewV2
  extends PlayClusterView
  implements OnDataChangedListener, HorizontalScrollerContainer, PlayCardImageTypeSequence
{
  protected PlayClusterViewContentV2 mContent;
  
  public PlayCardClusterViewV2(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayCardClusterViewV2(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public final void createContent(ClusterContentBinder paramClusterContentBinder, ClusterContentConfigurator paramClusterContentConfigurator, int paramInt, RecyclerView.RecycledViewPool paramRecycledViewPool, Bundle paramBundle, PlayStoreUiElementNode paramPlayStoreUiElementNode, byte[] paramArrayOfByte)
  {
    configureLogging(paramArrayOfByte, paramPlayStoreUiElementNode);
    this.mContent.createContent(paramClusterContentBinder, paramClusterContentConfigurator, paramInt, false, paramRecycledViewPool, paramBundle, getImageTypePreference());
  }
  
  public String getContentId()
  {
    return this.mContent.getChildContentSourceId();
  }
  
  public int[] getImageTypePreference()
  {
    return PlayCardImageTypeSequence.CORE_IMAGE_TYPES;
  }
  
  public final boolean isPointInHorizontalScroller(float paramFloat1, float paramFloat2)
  {
    return (paramFloat1 >= this.mContent.getLeft()) && (paramFloat1 < this.mContent.getRight()) && (paramFloat2 >= this.mContent.getTop()) && (paramFloat2 < this.mContent.getBottom());
  }
  
  public final void onDataChanged()
  {
    this.mContent.onDataChanged();
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mContent = ((PlayClusterViewContentV2)findViewById(2131755307));
  }
  
  public final void onIgnoreNextTouchSequence()
  {
    this.mContent.mIgnoreNextTouchSequence = true;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = getWidth();
    int j = getPaddingTop();
    if ((this.mHeader != null) && (this.mHeader.getVisibility() != 8))
    {
      this.mHeader.layout(0, j, i, j + this.mHeader.getMeasuredHeight());
      j += this.mHeader.getMeasuredHeight();
    }
    int k = j + ((ViewGroup.MarginLayoutParams)this.mContent.getLayoutParams()).topMargin;
    this.mContent.layout(0, k, i, k + this.mContent.getMeasuredHeight());
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    PlayCardClusterViewHeader localPlayCardClusterViewHeader = this.mHeader;
    int i = 0;
    if (localPlayCardClusterViewHeader != null)
    {
      int k = this.mHeader.getVisibility();
      i = 0;
      if (k != 8)
      {
        this.mHeader.measure(paramInt1, 0);
        i = this.mHeader.getMeasuredHeight();
      }
    }
    ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams)this.mContent.getLayoutParams();
    if (localMarginLayoutParams.height != -1) {
      this.mContent.measure(paramInt1, 0);
    }
    if (View.MeasureSpec.getMode(paramInt2) == 1073741824) {}
    for (int j = View.MeasureSpec.getSize(paramInt2);; j = i + getPaddingTop() + getPaddingBottom() + localMarginLayoutParams.topMargin + this.mContent.getMeasuredHeight() + localMarginLayoutParams.bottomMargin)
    {
      setMeasuredDimension(View.MeasureSpec.getSize(paramInt1), j);
      if (localMarginLayoutParams.height == -1) {
        this.mContent.measure(paramInt1, View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824));
      }
      return;
    }
  }
  
  public void onRecycle()
  {
    super.onRecycle();
    this.mContent.onRecycle();
  }
  
  public final void onSaveInstanceState(Bundle paramBundle)
  {
    this.mContent.onSaveInstanceState(paramBundle);
  }
  
  public final boolean setCardContentHorizontalPadding(int paramInt)
  {
    return this.mContent.setContentHorizontalPadding(paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayCardClusterViewV2
 * JD-Core Version:    0.7.0.1
 */