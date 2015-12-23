package com.google.android.finsky.layout;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.vending.R.styleable;
import com.google.android.finsky.utils.BadgeUtils;
import com.google.android.play.layout.StarRatingBar;
import java.text.NumberFormat;

public class HistogramView
  extends RelativeLayout
{
  private TextView mAverageValue;
  private final int mHalfSeparatorThickness;
  private HistogramTable mHistogramTable;
  private NumberFormat mIntegerFormatter = NumberFormat.getIntegerInstance();
  private final boolean mNeedsHeightCorrection;
  private StarRatingBar mRatingBar;
  private TextView mRatingCount;
  private final int mSeparatorInset;
  private final Paint mSeparatorPaint;
  private final boolean mShowsBottomSeparator;
  private LinearLayout mSummaryBox;
  
  public HistogramView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public HistogramView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.HistogramView);
    this.mNeedsHeightCorrection = localTypedArray.getBoolean(0, false);
    this.mShowsBottomSeparator = localTypedArray.getBoolean(1, false);
    localTypedArray.recycle();
    setWillNotDraw(false);
    Resources localResources = paramContext.getResources();
    int i = localResources.getDimensionPixelSize(2131493415);
    this.mHalfSeparatorThickness = ((-1 + (i + 2)) / 2);
    this.mSeparatorPaint = new Paint();
    this.mSeparatorPaint.setColor(localResources.getColor(2131689680));
    this.mSeparatorPaint.setStrokeWidth(i);
    this.mSeparatorInset = localResources.getDimensionPixelSize(2131492993);
  }
  
  public final void bind(long paramLong, float paramFloat, int[] paramArrayOfInt)
  {
    boolean bool = true;
    setVisibility(0);
    Resources localResources = getResources();
    this.mRatingCount.setText(this.mIntegerFormatter.format(paramLong));
    TextView localTextView1 = this.mRatingCount;
    int i = (int)paramLong;
    Object[] arrayOfObject1 = new Object[bool];
    arrayOfObject1[0] = Long.valueOf(paramLong);
    localTextView1.setContentDescription(localResources.getQuantityString(2131296258, i, arrayOfObject1));
    String str = BadgeUtils.formatRating(paramFloat);
    this.mAverageValue.setText(str);
    TextView localTextView2 = this.mAverageValue;
    Object[] arrayOfObject2 = new Object[bool];
    arrayOfObject2[0] = str;
    localTextView2.setContentDescription(localResources.getString(2131362009, arrayOfObject2));
    this.mRatingBar.setRating(paramFloat);
    StarRatingBar localStarRatingBar = this.mRatingBar;
    if (!BadgeUtils.shouldHideEmptyStarsInReviews()) {}
    for (;;)
    {
      localStarRatingBar.setShowEmptyStars(bool);
      this.mHistogramTable.setHistogram(paramArrayOfInt);
      return;
      bool = false;
    }
  }
  
  public View getSummaryBox()
  {
    return this.mSummaryBox;
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    if (this.mShowsBottomSeparator)
    {
      int i = getHeight() - this.mHalfSeparatorThickness;
      paramCanvas.drawLine(this.mSeparatorInset, i, getWidth() - this.mSeparatorInset, i, this.mSeparatorPaint);
    }
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mSummaryBox = ((LinearLayout)findViewById(2131756009));
    this.mHistogramTable = ((HistogramTable)findViewById(2131756070));
    this.mAverageValue = ((TextView)findViewById(2131755440));
    this.mRatingBar = ((StarRatingBar)this.mSummaryBox.findViewById(2131756010));
    this.mRatingCount = ((TextView)this.mSummaryBox.findViewById(2131756011));
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    int i;
    int j;
    int k;
    if (this.mNeedsHeightCorrection)
    {
      i = View.MeasureSpec.getSize(paramInt1);
      j = this.mAverageValue.getMeasuredHeight() + this.mSummaryBox.getMeasuredHeight();
      k = this.mHistogramTable.getMeasuredHeight();
      if (j <= k) {
        break label89;
      }
    }
    label89:
    for (int m = j + (this.mHistogramTable.getMeasuredHeight() - this.mHistogramTable.getBaseline());; m = k + (this.mRatingCount.getMeasuredHeight() - this.mRatingCount.getBaseline()))
    {
      setMeasuredDimension(i, m + getPaddingBottom() + getPaddingTop());
      return;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.HistogramView
 * JD-Core Version:    0.7.0.1
 */