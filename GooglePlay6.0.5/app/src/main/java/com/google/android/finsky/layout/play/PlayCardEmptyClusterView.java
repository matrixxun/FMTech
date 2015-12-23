package com.google.android.finsky.layout.play;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.EmptyContainer;
import com.google.android.finsky.protos.Template;
import com.google.android.finsky.utils.FastHtmlParser;
import com.google.android.play.image.FifeImageView;
import com.google.android.play.utils.DocV2Utils;

public class PlayCardEmptyClusterView
  extends PlayClusterView
{
  private FifeImageView mEmptyImage;
  private TextView mEmptyText;
  
  public PlayCardEmptyClusterView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayCardEmptyClusterView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public final void createContent(Document paramDocument, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    configureLogging(paramDocument.mDocument.serverLogsCookie, paramPlayStoreUiElementNode);
    String str;
    if (paramDocument.hasEmptyContainer())
    {
      str = paramDocument.getTemplate().emptyContainer.emptyMessage;
      Common.Image localImage = DocV2Utils.getFirstImageOfType(paramDocument.mDocument, 4);
      if (TextUtils.isEmpty(str)) {
        break label112;
      }
      this.mEmptyText.setText(FastHtmlParser.fromHtml(str));
      this.mEmptyText.setVisibility(0);
      label66:
      if (localImage == null) {
        break label124;
      }
      this.mEmptyImage.setImage(localImage.imageUrl, localImage.supportsFifeUrlOptions, FinskyApp.get().mBitmapLoader);
      this.mEmptyImage.setVisibility(0);
    }
    for (;;)
    {
      logEmptyClusterImpression();
      return;
      str = null;
      break;
      label112:
      this.mEmptyText.setVisibility(8);
      break label66;
      label124:
      this.mEmptyImage.setVisibility(8);
    }
  }
  
  protected int getPlayStoreUiElementType()
  {
    return 416;
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mEmptyImage = ((FifeImageView)findViewById(2131755859));
    this.mEmptyText = ((TextView)findViewById(2131755829));
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = getWidth();
    int j = getPaddingTop();
    if (this.mHeader.getVisibility() != 8)
    {
      this.mHeader.layout(0, j, i, j + this.mHeader.getMeasuredHeight());
      j += this.mHeader.getMeasuredHeight();
    }
    if (this.mEmptyImage.getVisibility() != 8)
    {
      ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams)this.mEmptyImage.getLayoutParams();
      int n = j + localMarginLayoutParams.topMargin;
      this.mEmptyImage.layout(0, n, i, n + this.mEmptyImage.getMeasuredHeight());
      j = n + (this.mEmptyImage.getMeasuredHeight() + localMarginLayoutParams.bottomMargin);
    }
    int k = this.mEmptyText.getMeasuredWidth();
    int m = (i - k) / 2;
    this.mEmptyText.layout(m, j, m + k, j + this.mEmptyText.getMeasuredHeight());
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = getPaddingTop() + getPaddingBottom();
    if (this.mHeader.getVisibility() != 8)
    {
      this.mHeader.measure(paramInt1, 0);
      j += this.mHeader.getMeasuredHeight();
    }
    if (this.mEmptyImage.getVisibility() != 8)
    {
      ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams)this.mEmptyImage.getLayoutParams();
      int k = View.MeasureSpec.makeMeasureSpec(localMarginLayoutParams.height, 1073741824);
      this.mEmptyImage.measure(paramInt1, k);
      j += localMarginLayoutParams.topMargin + this.mEmptyImage.getMeasuredHeight() + localMarginLayoutParams.bottomMargin;
    }
    this.mEmptyText.measure(View.MeasureSpec.makeMeasureSpec(i / 2, 1073741824), 0);
    setMeasuredDimension(i, j + this.mEmptyText.getMeasuredHeight());
  }
  
  public final void onRecycle()
  {
    super.onRecycle();
    this.mEmptyImage.clearImage();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayCardEmptyClusterView
 * JD-Core Version:    0.7.0.1
 */