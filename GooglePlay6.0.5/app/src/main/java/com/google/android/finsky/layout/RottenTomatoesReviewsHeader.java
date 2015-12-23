package com.google.android.finsky.layout;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.CriticReviewsResponse;
import com.google.android.play.image.FifeImageView;

public class RottenTomatoesReviewsHeader
  extends RelativeLayout
{
  public RottenTomatoesMeter mFavorablePercentBar;
  public TextView mFavorablePercentValue;
  private final int mHalfSeparatorThickness;
  public TextView mReviewsCount;
  public FifeImageView mSentimentImage;
  private final int mSeparatorInset;
  private final Paint mSeparatorPaint;
  public TextView mSource;
  public TextView mTitle;
  
  public RottenTomatoesReviewsHeader(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public RottenTomatoesReviewsHeader(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    Resources localResources = paramContext.getResources();
    int i = localResources.getDimensionPixelSize(2131493415);
    this.mHalfSeparatorThickness = ((-1 + (i + 2)) / 2);
    this.mSeparatorPaint = new Paint();
    this.mSeparatorPaint.setColor(localResources.getColor(2131689680));
    this.mSeparatorPaint.setStrokeWidth(i);
    this.mSeparatorInset = localResources.getDimensionPixelSize(2131492993);
    setWillNotDraw(false);
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    int i = getHeight() - this.mHalfSeparatorThickness;
    paramCanvas.drawLine(this.mSeparatorInset, i, getWidth() - this.mSeparatorInset, i, this.mSeparatorPaint);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mTitle = ((TextView)findViewById(2131755173));
    this.mSentimentImage = ((FifeImageView)findViewById(2131756081));
    this.mFavorablePercentValue = ((TextView)findViewById(2131756083));
    this.mReviewsCount = ((TextView)findViewById(2131756085));
    this.mFavorablePercentBar = ((RottenTomatoesMeter)findViewById(2131756086));
    this.mSource = ((TextView)findViewById(2131756087));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.RottenTomatoesReviewsHeader
 * JD-Core Version:    0.7.0.1
 */