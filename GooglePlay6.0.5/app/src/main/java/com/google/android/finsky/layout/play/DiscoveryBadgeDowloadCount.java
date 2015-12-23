package com.google.android.finsky.layout.play;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.Details.DiscoveryBadge;
import com.google.android.play.image.BitmapLoader;

public class DiscoveryBadgeDowloadCount
  extends DiscoveryBadgeBase
{
  private TextView mDownloadCountNumber;
  private TextView mDownloadMagnitude;
  private Drawable mDownloadMagnitudeBackground;
  private RelativeLayout mDownloadMagnitudeContainer;
  private int mFocusedOutlineColor;
  private float mOutlineStrokeWidth;
  private Paint mPaint;
  private int mPressedFillColor;
  private int mPressedOutlineColor;
  private int mRingStrokeWidth;
  
  public DiscoveryBadgeDowloadCount(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public DiscoveryBadgeDowloadCount(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    Resources localResources = getResources();
    this.mBadgeRadius = (localResources.getDimensionPixelSize(2131493007) / 2);
    this.mDownloadMagnitudeBackground = DrawableCompat.wrap(ContextCompat.getDrawable(paramContext, 2130837664).mutate());
    this.mPaint = new Paint(1);
    this.mRingStrokeWidth = localResources.getDimensionPixelSize(2131493298);
    this.mOutlineStrokeWidth = (0.5F * localResources.getDimensionPixelSize(2131493377));
    this.mPressedFillColor = localResources.getColor(2131689475);
    this.mPressedOutlineColor = localResources.getColor(2131689476);
    this.mFocusedOutlineColor = localResources.getColor(2131689473);
    setWillNotDraw(false);
  }
  
  @SuppressLint({"NewApi"})
  public final void bind(Details.DiscoveryBadge paramDiscoveryBadge, BitmapLoader paramBitmapLoader, NavigationManager paramNavigationManager, Document paramDocument, DfeToc paramDfeToc, PackageManager paramPackageManager, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    super.bind(paramDiscoveryBadge, paramBitmapLoader, paramNavigationManager, paramDocument, paramDfeToc, paramPackageManager, paramPlayStoreUiElementNode);
    Resources localResources = getResources();
    if (paramDiscoveryBadge.downloadCount.length() < 4)
    {
      this.mDownloadCountNumber.setTextSize(0, localResources.getDimensionPixelSize(2131493296));
      this.mDownloadCountNumber.setText(paramDiscoveryBadge.downloadCount);
      this.mDownloadMagnitude.setText(paramDiscoveryBadge.downloadUnits);
      if (!TextUtils.isEmpty(paramDiscoveryBadge.downloadUnits)) {
        break label166;
      }
      this.mDownloadMagnitudeContainer.setVisibility(8);
      DrawableCompat.setTint(this.mDownloadMagnitudeBackground, this.mCurrentFillColor);
      if (Build.VERSION.SDK_INT < 16) {
        break label228;
      }
      this.mDownloadMagnitudeContainer.setBackground(this.mDownloadMagnitudeBackground);
    }
    for (;;)
    {
      this.mTitle.setText(paramDiscoveryBadge.title);
      this.mDownloadCountNumber.setContentDescription(null);
      this.mDownloadMagnitude.setContentDescription(null);
      return;
      this.mDownloadCountNumber.setTextSize(0, localResources.getDimensionPixelSize(2131493297));
      break;
      label166:
      this.mDownloadMagnitudeContainer.setVisibility(0);
      RelativeLayout.LayoutParams localLayoutParams = (RelativeLayout.LayoutParams)this.mDownloadMagnitudeContainer.getLayoutParams();
      if (paramDiscoveryBadge.downloadUnits.length() == 1) {
        localLayoutParams.addRule(7, 2131755417);
      }
      for (;;)
      {
        this.mDownloadMagnitudeContainer.setLayoutParams(localLayoutParams);
        break;
        localLayoutParams.addRule(14);
      }
      label228:
      this.mDownloadMagnitudeContainer.setBackgroundDrawable(this.mDownloadMagnitudeBackground);
    }
  }
  
  protected void dispatchDraw(Canvas paramCanvas)
  {
    super.dispatchDraw(paramCanvas);
    int i = getWidth() / 2;
    int j = this.mBadgeRadius;
    int k;
    if ((isPressed()) && ((isDuplicateParentStateEnabled()) || (isClickable())))
    {
      k = 1;
      if (k == 0) {
        break label140;
      }
      this.mPaint.setColor(this.mPressedFillColor);
      this.mPaint.setStyle(Paint.Style.FILL);
      paramCanvas.drawCircle(i, j, this.mBadgeRadius, this.mPaint);
      this.mPaint.setColor(this.mPressedOutlineColor);
      this.mPaint.setStyle(Paint.Style.STROKE);
      this.mPaint.setStrokeWidth(this.mOutlineStrokeWidth);
      paramCanvas.drawCircle(i, j, this.mBadgeRadius, this.mPaint);
    }
    label140:
    while (!isFocused())
    {
      return;
      k = 0;
      break;
    }
    this.mPaint.setColor(this.mFocusedOutlineColor);
    this.mPaint.setStyle(Paint.Style.STROKE);
    this.mPaint.setStrokeWidth(this.mOutlineStrokeWidth);
    paramCanvas.drawCircle(i, j, this.mBadgeRadius, this.mPaint);
  }
  
  protected void drawableStateChanged()
  {
    super.drawableStateChanged();
    invalidate();
  }
  
  protected int getPlayStoreUiElementType()
  {
    return 1805;
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    int i = getWidth() / 2;
    int j = this.mBadgeRadius;
    this.mPaint.setColor(this.mCurrentFillColor);
    this.mPaint.setStyle(Paint.Style.STROKE);
    this.mPaint.setStrokeWidth(this.mRingStrokeWidth);
    paramCanvas.drawCircle(i, j, this.mBadgeRadius - 2 * this.mRingStrokeWidth / 3, this.mPaint);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mDownloadCountNumber = ((TextView)findViewById(2131755435));
    this.mDownloadMagnitude = ((TextView)findViewById(2131755437));
    this.mDownloadMagnitudeContainer = ((RelativeLayout)findViewById(2131755436));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.DiscoveryBadgeDowloadCount
 * JD-Core Version:    0.7.0.1
 */