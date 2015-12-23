package com.google.android.finsky.layout.play;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.TextView;
import com.google.android.finsky.adapters.Recyclable;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.layout.FadingEdgeImageView;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.play.image.FifeImageView;
import com.google.android.play.image.FifeImageView.OnLoadedListener;
import com.google.android.play.layout.ForegroundRelativeLayout;

public class PlayMerchBannerView
  extends ForegroundRelativeLayout
  implements Recyclable, Identifiable, PlayStoreUiElementNode, FifeImageView.OnLoadedListener
{
  public int mColumnCount;
  private final boolean mCompactHeight;
  public int mContentHorizontalPadding;
  public final int mFallbackMerchColor;
  private String mIdentifier;
  public int mMerchColor;
  public FadingEdgeImageView mMerchImage;
  private int mMinTextTrailingSpace;
  public PlayStoreUiElementNode mParentNode;
  public TextView mSubtitle;
  public TextView mTitle;
  public PlayStore.PlayStoreUiElement mUiElementProto;
  
  public PlayMerchBannerView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayMerchBannerView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    Resources localResources = paramContext.getResources();
    this.mColumnCount = -1;
    this.mCompactHeight = localResources.getBoolean(2131427342);
    this.mFallbackMerchColor = localResources.getColor(2131689644);
  }
  
  private int getMerchImageOffset(int paramInt)
  {
    if (this.mColumnCount > 2) {
      return 0;
    }
    return (int)(2.0F * (1.777778F * paramInt) / 10.0F);
  }
  
  private void measureTexts(int paramInt1, int paramInt2)
  {
    int i = getMerchImageOffset(paramInt2);
    if (this.mColumnCount <= 4) {}
    int j;
    int k;
    for (float f = 0.85F;; f = 1.0F)
    {
      j = paramInt1 - (-i + (int)(f * (1.777778F * paramInt2))) - this.mContentHorizontalPadding;
      this.mTitle.measure(View.MeasureSpec.makeMeasureSpec(j, 1073741824), 0);
      this.mSubtitle.measure(View.MeasureSpec.makeMeasureSpec(j, 1073741824), 0);
      Layout localLayout1 = this.mTitle.getLayout();
      k = 0;
      if (localLayout1 == null) {
        break;
      }
      for (int n = 0; n < localLayout1.getLineCount(); n++) {
        k = Math.max(k, (int)localLayout1.getLineWidth(n));
      }
    }
    Layout localLayout2 = this.mSubtitle.getLayout();
    if (localLayout2 != null) {
      for (int m = 0; m < localLayout2.getLineCount(); m++) {
        k = Math.max(k, (int)localLayout2.getLineWidth(m));
      }
    }
    if (k == 0) {
      k = j;
    }
    this.mMinTextTrailingSpace = (j - k);
  }
  
  public final void childImpression(PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    throw new IllegalStateException("unwanted children");
  }
  
  public final void configureImageFadingEdge()
  {
    this.mMerchImage.configureFadingEdges(false, true, this.mMerchImage.getMeasuredWidth() / 4, this.mMerchColor);
  }
  
  public int getFallbackMerchTextColor()
  {
    if (UiUtils.isColorBright(this.mMerchColor)) {}
    for (int i = 2131689599;; i = 2131689600) {
      return getResources().getColor(i);
    }
  }
  
  public String getIdentifier()
  {
    return this.mIdentifier;
  }
  
  public PlayStoreUiElementNode getParentNode()
  {
    return this.mParentNode;
  }
  
  public PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mUiElementProto;
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mMerchImage = ((FadingEdgeImageView)findViewById(2131755866));
    this.mTitle = ((TextView)findViewById(2131755932));
    this.mSubtitle = ((TextView)findViewById(2131755933));
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = getWidth();
    int j = getHeight();
    int k = getPaddingTop();
    int m = getPaddingBottom();
    int n = this.mMerchImage.getMeasuredWidth();
    if (n > 0)
    {
      int i6 = getMerchImageOffset(j);
      int i7 = n + -i6;
      this.mMerchImage.layout(-i6, k, i7, k + this.mMerchImage.getMeasuredHeight());
    }
    for (;;)
    {
      int i1 = this.mTitle.getMeasuredHeight();
      int i2 = this.mSubtitle.getMeasuredHeight();
      int i3 = k + (j - i1 - i2 - k - m) / 2;
      int i4 = i - this.mContentHorizontalPadding - this.mTitle.getMeasuredWidth() + this.mMinTextTrailingSpace / 2;
      this.mTitle.layout(i4, i3, i4 + this.mTitle.getMeasuredWidth(), i3 + i1);
      int i5 = i3 + i1;
      this.mSubtitle.layout(i4, i5, i4 + this.mSubtitle.getMeasuredWidth(), i5 + i2);
      return;
      this.mMerchImage.layout(0, k, n, k + this.mMerchImage.getMeasuredHeight());
    }
  }
  
  public final void onLoaded(FifeImageView paramFifeImageView, Bitmap paramBitmap)
  {
    configureImageFadingEdge();
  }
  
  public final void onLoadedAndFadedIn(FifeImageView paramFifeImageView) {}
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    if (this.mColumnCount <= 0)
    {
      setMeasuredDimension(i, 0);
      return;
    }
    int j = i / this.mColumnCount;
    int k = j;
    if (this.mCompactHeight)
    {
      int n = j * 2 / 3;
      measureTexts(i, n);
      if (this.mTitle.getMeasuredHeight() + this.mSubtitle.getMeasuredHeight() <= n)
      {
        k = n;
        int m = (int)(1.777778F * k);
        this.mMerchImage.measure(View.MeasureSpec.makeMeasureSpec(m, 1073741824), View.MeasureSpec.makeMeasureSpec(k, 1073741824));
        if (!this.mMerchImage.isLoaded()) {
          break label149;
        }
        configureImageFadingEdge();
      }
    }
    for (;;)
    {
      setMeasuredDimension(i, k + getPaddingTop() + getPaddingBottom());
      return;
      measureTexts(i, j);
      break;
      label149:
      this.mMerchImage.clearFadingEdges();
    }
  }
  
  public final void onRecycle()
  {
    this.mMerchImage.clearImage();
  }
  
  public void setIdentifier(String paramString)
  {
    this.mIdentifier = paramString;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayMerchBannerView
 * JD-Core Version:    0.7.0.1
 */