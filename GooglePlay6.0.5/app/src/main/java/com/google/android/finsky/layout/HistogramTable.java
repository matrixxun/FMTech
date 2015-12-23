package com.google.android.finsky.layout;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import com.android.vending.R.styleable;
import com.google.android.finsky.utils.Lists;
import java.text.NumberFormat;
import java.util.List;

public class HistogramTable
  extends TableLayout
{
  private final int mBarHeight;
  private final int mBarMargin;
  private final List<HistogramBar> mBars = Lists.newArrayList(5);
  private final boolean mLabelsOn;
  private final int mMaxBarWidth;
  
  public HistogramTable(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public HistogramTable(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.HistogramTable);
    this.mLabelsOn = localTypedArray.getBoolean(0, false);
    this.mBarHeight = localTypedArray.getDimensionPixelSize(1, 0);
    this.mMaxBarWidth = localTypedArray.getDimensionPixelSize(2, -1);
    this.mBarMargin = localTypedArray.getDimensionPixelSize(3, 0);
    localTypedArray.recycle();
  }
  
  public int getBaseline()
  {
    int i = getMeasuredHeight();
    int j = getChildCount();
    if (j == 0) {
      return i;
    }
    View localView = getChildAt(j - 1);
    return i - localView.getMeasuredHeight() + localView.getBaseline();
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    if ((this.mLabelsOn) && (!this.mBars.isEmpty()))
    {
      int i = 0;
      for (int j = 0; j < getChildCount(); j++)
      {
        View localView1 = getChildAt(j);
        if ((localView1 instanceof TableRow))
        {
          TableRow localTableRow = (TableRow)localView1;
          int m = 0;
          for (int n = 0; n < localTableRow.getChildCount(); n++)
          {
            View localView2 = localTableRow.getChildAt(n);
            TableRow.LayoutParams localLayoutParams = (TableRow.LayoutParams)localView2.getLayoutParams();
            m += localLayoutParams.leftMargin + localView2.getMeasuredWidth() + localLayoutParams.rightMargin;
          }
          if (m - localTableRow.getMeasuredWidth() > i) {
            i = m - localTableRow.getMeasuredWidth();
          }
        }
      }
      if (i > 0)
      {
        for (int k = 0; k < this.mBars.size(); k++) {
          ((HistogramBar)this.mBars.get(k)).setMaxBarWidth(this.mMaxBarWidth - i);
        }
        this.mBars.clear();
      }
    }
  }
  
  public void setHistogram(int[] paramArrayOfInt)
  {
    removeAllViews();
    this.mBars.clear();
    double d = 0.0D;
    for (int i = 0; i < 5; i++) {
      if (paramArrayOfInt[i] > d) {
        d = paramArrayOfInt[i];
      }
    }
    LayoutInflater localLayoutInflater = LayoutInflater.from(getContext());
    NumberFormat localNumberFormat = NumberFormat.getIntegerInstance();
    Resources localResources = getResources();
    int j = 0;
    if (j < 5)
    {
      TableRow localTableRow = (TableRow)localLayoutInflater.inflate(2130968788, this, false);
      StarLabel localStarLabel = (StarLabel)localTableRow.findViewById(2131755599);
      TextView localTextView = (TextView)localTableRow.findViewById(2131755601);
      localStarLabel.setMaxStars(5);
      localStarLabel.setStarHeight(this.mBarHeight);
      label165:
      HistogramBar localHistogramBar;
      TableLayout.LayoutParams localLayoutParams;
      int k;
      if (this.mLabelsOn)
      {
        localStarLabel.setNumStars(5 - j);
        localTextView.setText(localNumberFormat.format(paramArrayOfInt[j]));
        localTableRow.setBaselineAlignedChildIndex(2);
        localHistogramBar = (HistogramBar)localTableRow.findViewById(2131755600);
        localHistogramBar.setMaxBarWidth(this.mMaxBarWidth);
        localHistogramBar.setBarHeight(this.mBarHeight);
        localHistogramBar.setWidthPercentage(paramArrayOfInt[j] / d);
        localLayoutParams = new TableLayout.LayoutParams(-2, -2);
        if (j != 0) {
          localLayoutParams.setMargins(0, this.mBarMargin, 0, 0);
        }
        switch (j)
        {
        default: 
          k = 2131689698;
        }
      }
      for (;;)
      {
        localHistogramBar.setColor(k);
        this.mBars.add(localHistogramBar);
        int m = paramArrayOfInt[j];
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = Integer.valueOf(paramArrayOfInt[j]);
        arrayOfObject[1] = Integer.valueOf(5 - j);
        localTableRow.setContentDescription(localResources.getQuantityString(2131296259, m, arrayOfObject));
        addView(localTableRow, localLayoutParams);
        j++;
        break;
        localStarLabel.setVisibility(8);
        localTextView.setVisibility(8);
        localTableRow.setBaselineAlignedChildIndex(1);
        break label165;
        k = 2131689702;
        continue;
        k = 2131689701;
        continue;
        k = 2131689700;
        continue;
        k = 2131689699;
        continue;
        k = 2131689698;
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.HistogramTable
 * JD-Core Version:    0.7.0.1
 */