package com.google.android.finsky.layout.play;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.widget.TextView;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.image.FifeImageView;
import com.google.android.play.utils.PlayCorpusUtils;
import java.util.List;

public class PlayCardClusterWithNoticeView
  extends PlayCardClusterView
  implements ViewTreeObserver.OnScrollChangedListener
{
  private final int[] locationOnScreen = new int[2];
  private final int mContentVerticalMargin;
  private View mNotice;
  private TextView mNoticeText;
  private PlayStoreUiElementNode mNoticeUiElementNode;
  private final int mNoticeWidthInPixels;
  private TextView mOkLabel;
  private OnNoticeShownListener mOnNoticeShownListener;
  private final Rect mScrollBounds;
  private TextView mSettingsLabel;
  
  public PlayCardClusterWithNoticeView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayCardClusterWithNoticeView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    Resources localResources = paramContext.getResources();
    this.mContentVerticalMargin = localResources.getDimensionPixelSize(2131493431);
    this.mNoticeWidthInPixels = (localResources.getInteger(2131623950) * (UiUtils.getGridColumnContentWidth(localResources) / UiUtils.getFeaturedGridColumnCount(localResources, 1.0D)));
    this.mScrollBounds = new Rect();
  }
  
  public final void configureExtraContent$786eba84(BitmapLoader paramBitmapLoader, Document paramDocument, boolean paramBoolean, int paramInt, String paramString, CharSequence paramCharSequence, View.OnClickListener paramOnClickListener1, View.OnClickListener paramOnClickListener2, View.OnClickListener paramOnClickListener3, OnNoticeShownListener paramOnNoticeShownListener)
  {
    PlayCardClusterWithNoticeViewContent localPlayCardClusterWithNoticeViewContent = (PlayCardClusterWithNoticeViewContent)this.mContent;
    ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams)localPlayCardClusterWithNoticeViewContent.getLayoutParams();
    localMarginLayoutParams.topMargin = this.mContentVerticalMargin;
    localMarginLayoutParams.bottomMargin = this.mContentVerticalMargin;
    Common.Image localImage = (Common.Image)paramDocument.getImages(14).get(0);
    localPlayCardClusterWithNoticeViewContent.mCoverImage.setImage(localImage.imageUrl, localImage.supportsFifeUrlOptions, paramBitmapLoader);
    localPlayCardClusterWithNoticeViewContent.mCoverImage.setOnClickListener(paramOnClickListener1);
    localPlayCardClusterWithNoticeViewContent.mCoverImage.setContentDescription(paramString);
    ViewCompat.setImportantForAccessibility(localPlayCardClusterWithNoticeViewContent.mCoverImage, 2);
    localPlayCardClusterWithNoticeViewContent.mTitle.setText(paramDocument.mDocument.title);
    localPlayCardClusterWithNoticeViewContent.mSubtitle.setText(paramDocument.mDocument.subtitle);
    if (paramString != null)
    {
      localPlayCardClusterWithNoticeViewContent.mActionButton.setVisibility(0);
      localPlayCardClusterWithNoticeViewContent.mActionButton.setText(paramString);
      localPlayCardClusterWithNoticeViewContent.mActionButton.setOnClickListener(paramOnClickListener1);
      logEmptyClusterImpression();
      if ((!paramBoolean) || (TextUtils.isEmpty(paramCharSequence))) {
        break label273;
      }
      this.mNotice.setVisibility(0);
      this.mNoticeText.setText(paramCharSequence);
      ColorStateList localColorStateList = PlayCorpusUtils.getPrimaryTextColor(getContext(), paramInt);
      this.mSettingsLabel.setTextColor(localColorStateList);
      this.mSettingsLabel.setOnClickListener(paramOnClickListener2);
      this.mOkLabel.setTextColor(localColorStateList);
      this.mOkLabel.setOnClickListener(paramOnClickListener3);
    }
    for (;;)
    {
      this.mOnNoticeShownListener = paramOnNoticeShownListener;
      this.mScrollBounds.setEmpty();
      return;
      localPlayCardClusterWithNoticeViewContent.mActionButton.setVisibility(8);
      break;
      label273:
      this.mNotice.setVisibility(8);
    }
  }
  
  protected final void configureLogging(byte[] paramArrayOfByte, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    super.configureLogging(paramArrayOfByte, paramPlayStoreUiElementNode);
    this.mNoticeUiElementNode = new GenericUiElementNode(2900, paramArrayOfByte, null, getParentOfChildren());
  }
  
  public PlayStoreUiElementNode getNoticeUiElementNode()
  {
    return this.mNoticeUiElementNode;
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    ViewGroup localViewGroup = (ViewGroup)getParent();
    if ((localViewGroup != null) && (this.mOnNoticeShownListener != null))
    {
      localViewGroup.getHitRect(this.mScrollBounds);
      localViewGroup.getViewTreeObserver().addOnScrollChangedListener(this);
    }
  }
  
  protected void onDetachedFromWindow()
  {
    ViewGroup localViewGroup = (ViewGroup)getParent();
    if (localViewGroup != null) {
      localViewGroup.getViewTreeObserver().removeOnScrollChangedListener(this);
    }
    super.onDetachedFromWindow();
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mNotice = findViewById(2131755855);
    this.mNoticeText = ((TextView)findViewById(2131755856));
    this.mSettingsLabel = ((TextView)findViewById(2131755857));
    this.mSettingsLabel.setText(getContext().getString(2131362722).toUpperCase());
    this.mOkLabel = ((TextView)findViewById(2131755858));
    this.mOkLabel.setText(getContext().getString(2131362418).toUpperCase());
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
    if (this.mNotice.getVisibility() == 0)
    {
      int m = k + this.mContent.getMeasuredHeight() + ((ViewGroup.MarginLayoutParams)this.mNotice.getLayoutParams()).topMargin;
      int n = (i - this.mNoticeWidthInPixels) / 2;
      this.mNotice.layout(n, m, n + this.mNotice.getMeasuredWidth(), m + this.mNotice.getMeasuredHeight());
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = getMeasuredHeight();
    if (this.mNotice.getVisibility() != 8)
    {
      this.mNotice.measure(View.MeasureSpec.makeMeasureSpec(this.mNoticeWidthInPixels, 1073741824), 0);
      ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams)this.mNotice.getLayoutParams();
      j += localMarginLayoutParams.topMargin + this.mNotice.getMeasuredHeight() + localMarginLayoutParams.bottomMargin;
    }
    setMeasuredDimension(i, j);
  }
  
  public void onScrollChanged()
  {
    if (this.mNotice.getVisibility() != 0) {}
    int i;
    int j;
    do
    {
      return;
      this.mNotice.getLocationOnScreen(this.locationOnScreen);
      i = this.locationOnScreen[0];
      j = this.locationOnScreen[1];
    } while (!this.mScrollBounds.contains(i, j, i + this.mNotice.getMeasuredWidth(), j + this.mNotice.getMeasuredHeight()));
    if (getParentOfChildren() != null) {
      getParentOfChildren().childImpression(getNoticeUiElementNode());
    }
    this.mOnNoticeShownListener.onNoticeShown();
  }
  
  public static abstract interface OnNoticeShownListener
  {
    public abstract void onNoticeShown();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayCardClusterWithNoticeView
 * JD-Core Version:    0.7.0.1
 */