package com.google.android.play.headerlist;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.play.R.dimen;

public class PlayHeaderListTabContainer
  extends LinearLayout
{
  private static int[] sNextLeftRight = new int[2];
  private static int[] sSelectedLeftRight = new int[2];
  int mIndexForSelection;
  boolean mPaddingValidForTabs;
  private final Paint mSelectedUnderlinePaint;
  private int mSelectedUnderlineThickness;
  float mSelectionOffset;
  private int mStripWidth;
  private boolean mUseFloatingTabPadding;
  
  public PlayHeaderListTabContainer(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayHeaderListTabContainer(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setWillNotDraw(false);
    this.mSelectedUnderlineThickness = paramContext.getResources().getDimensionPixelSize(R.dimen.play_header_list_tab_strip_selected_underline_height);
    this.mSelectedUnderlinePaint = new Paint();
  }
  
  private static void getUnderlineEdges(View paramView, int[] paramArrayOfInt)
  {
    if (!(paramView instanceof TextView))
    {
      paramArrayOfInt[0] = paramView.getLeft();
      paramArrayOfInt[1] = paramView.getRight();
      return;
    }
    TextView localTextView = (TextView)paramView;
    CharSequence localCharSequence = localTextView.getText();
    if ((localCharSequence == null) || (localCharSequence.length() == 0))
    {
      paramArrayOfInt[0] = ((localTextView.getLeft() + localTextView.getRight()) / 2);
      paramArrayOfInt[1] = paramArrayOfInt[0];
      return;
    }
    paramArrayOfInt[0] = localTextView.getLeft();
    paramArrayOfInt[1] = localTextView.getRight();
  }
  
  private void updatePadding()
  {
    int m;
    int n;
    if (this.mUseFloatingTabPadding)
    {
      m = getResources().getDimensionPixelSize(R.dimen.play_header_list_tab_floating_padding);
      n = m;
    }
    for (;;)
    {
      ViewCompat.setPaddingRelative(this, m, 0, n, 0);
      this.mPaddingValidForTabs = true;
      return;
      int i = this.mStripWidth / 2;
      View localView1 = getChildAt(0);
      int j = 0;
      if (localView1 != null)
      {
        localView1.measure(-2, -2);
        j = i - localView1.getMeasuredWidth() / 2;
      }
      View localView2 = getChildAt(-1 + getChildCount());
      int k = 0;
      if (localView2 != null)
      {
        localView2.measure(-2, -2);
        k = i - localView2.getMeasuredWidth() / 2;
      }
      if (ViewCompat.getLayoutDirection(this) == 0)
      {
        m = j;
        n = k;
      }
      else
      {
        m = k;
        n = j;
      }
    }
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    if ((getChildCount() > 0) && (this.mIndexForSelection < getChildCount()))
    {
      getUnderlineEdges(getChildAt(this.mIndexForSelection), sSelectedLeftRight);
      if ((this.mSelectionOffset > 0.0F) && (this.mIndexForSelection < -1 + getChildCount()))
      {
        getUnderlineEdges(getChildAt(1 + this.mIndexForSelection), sNextLeftRight);
        sSelectedLeftRight[0] = ((int)(this.mSelectionOffset * sNextLeftRight[0] + (1.0F - this.mSelectionOffset) * sSelectedLeftRight[0]));
        sSelectedLeftRight[1] = ((int)(this.mSelectionOffset * sNextLeftRight[1] + (1.0F - this.mSelectionOffset) * sSelectedLeftRight[1]));
      }
      int i = getHeight();
      paramCanvas.drawRect(sSelectedLeftRight[0], i - this.mSelectedUnderlineThickness, sSelectedLeftRight[1], i, this.mSelectedUnderlinePaint);
    }
  }
  
  public void setSelectedIndicatorColor(int paramInt)
  {
    this.mSelectedUnderlinePaint.setColor(paramInt);
    invalidate();
  }
  
  public void setSelectedUnderlineThickness(int paramInt)
  {
    this.mSelectedUnderlineThickness = paramInt;
    invalidate();
  }
  
  void setStripWidth(int paramInt)
  {
    if ((this.mStripWidth != paramInt) || (!this.mPaddingValidForTabs))
    {
      this.mStripWidth = paramInt;
      updatePadding();
    }
  }
  
  public void setUseFloatingTabPadding(boolean paramBoolean)
  {
    if (this.mUseFloatingTabPadding != paramBoolean)
    {
      this.mUseFloatingTabPadding = paramBoolean;
      updatePadding();
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.headerlist.PlayHeaderListTabContainer
 * JD-Core Version:    0.7.0.1
 */