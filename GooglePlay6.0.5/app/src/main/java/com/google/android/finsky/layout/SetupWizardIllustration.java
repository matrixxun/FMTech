package com.google.android.finsky.layout;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Matrix.ScaleToFit;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.android.vending.R.styleable;
import com.google.android.play.image.FifeImageView;
import com.google.android.play.image.FifeImageView.OnLoadedListener;

public class SetupWizardIllustration
  extends LinearLayout
{
  private final float mAspectRatio;
  private final float mBaselineGridSize;
  public final boolean mCollapsable;
  public FifeImageView mImageView;
  private final int mStatusBarOffset;
  private TextView mTitleView;
  private final boolean mUseTabletGraphic;
  
  public SetupWizardIllustration(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public SetupWizardIllustration(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.SetupWizardIllustration, 0, 0);
    this.mCollapsable = localTypedArray.getBoolean(0, false);
    this.mAspectRatio = localTypedArray.getFloat(1, 0.0F);
    localTypedArray.recycle();
    this.mBaselineGridSize = getResources().getDimensionPixelSize(2131493507);
    this.mStatusBarOffset = getResources().getDimensionPixelOffset(2131493518);
    this.mUseTabletGraphic = getResources().getBoolean(2131427344);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mImageView = ((FifeImageView)findViewById(2131756118));
    this.mTitleView = ((TextView)findViewById(2131755173));
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i;
    LinearLayout.LayoutParams localLayoutParams;
    int m;
    if ((this.mImageView != null) && (this.mImageView.getVisibility() != 8))
    {
      i = 1;
      if (this.mTitleView != null)
      {
        localLayoutParams = (LinearLayout.LayoutParams)this.mTitleView.getLayoutParams();
        m = 0;
        if (i == 0) {
          break label119;
        }
      }
    }
    for (;;)
    {
      localLayoutParams.topMargin = m;
      if ((i != 0) && (this.mAspectRatio != 0.0F))
      {
        int j = (int)(View.MeasureSpec.getSize(paramInt1) / this.mAspectRatio);
        int k = (int)(j - j % this.mBaselineGridSize);
        this.mImageView.getLayoutParams().height = k;
      }
      super.onMeasure(paramInt1, paramInt2);
      return;
      i = 0;
      break;
      label119:
      m = this.mStatusBarOffset;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.SetupWizardIllustration
 * JD-Core Version:    0.7.0.1
 */