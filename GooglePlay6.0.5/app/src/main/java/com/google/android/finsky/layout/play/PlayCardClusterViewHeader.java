package com.google.android.finsky.layout.play;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v4.view.MarginLayoutParamsCompat;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import com.android.vending.R.styleable;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.utils.CorpusResourceUtils;
import com.google.android.finsky.utils.FastHtmlParser;
import com.google.android.play.cardview.CardViewGroupDelegate;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.layout.ForegroundRelativeLayout;
import com.google.android.play.utils.PlayUtils;

public class PlayCardClusterViewHeader
  extends ForegroundRelativeLayout
  implements Identifiable
{
  private int mCurrBackendId = -1;
  private String mCurrMoreText;
  private final int mHeaderIconBackgroundSize;
  private final int mHeaderIconSize;
  public CircularBackgroundView mHeaderImage;
  private final int mHeaderXPadding;
  private String mIdentifier;
  private final boolean mIsH20Enabled;
  private final int mMinHeight;
  public CardTextView mMoreView;
  public View mTitleGroup;
  public TextView mTitleMain;
  private TextView mTitleSecondary;
  public final int mTopPadding;
  
  public PlayCardClusterViewHeader(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayCardClusterViewHeader(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    Resources localResources = paramContext.getResources();
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.PlayCardClusterViewHeader);
    this.mMinHeight = localTypedArray.getDimensionPixelSize(0, 0);
    this.mHeaderXPadding = localTypedArray.getDimensionPixelSize(1, 0);
    this.mHeaderIconSize = localResources.getDimensionPixelSize(2131493396);
    this.mHeaderIconBackgroundSize = localResources.getDimensionPixelSize(2131493077);
    this.mTopPadding = localResources.getDimensionPixelSize(2131493401);
    localTypedArray.recycle();
    ViewCompat.setPaddingRelative(this, this.mHeaderXPadding, 0, this.mHeaderXPadding, 0);
    this.mIsH20Enabled = FinskyApp.get().getExperiments().isH20StoreEnabled();
  }
  
  public String getIdentifier()
  {
    return this.mIdentifier;
  }
  
  public void onFinishInflate()
  {
    super.onFinishInflate();
    this.mHeaderImage = ((CircularBackgroundView)findViewById(2131755381));
    this.mTitleGroup = findViewById(2131755383);
    this.mTitleMain = ((TextView)this.mTitleGroup.findViewById(2131755384));
    this.mTitleSecondary = ((TextView)this.mTitleGroup.findViewById(2131755385));
    this.mMoreView = ((CardTextView)findViewById(2131755386));
    if (this.mIsH20Enabled)
    {
      Resources localResources = getResources();
      CardViewGroupDelegate localCardViewGroupDelegate = this.mMoreView.getCardViewGroupDelegate();
      localCardViewGroupDelegate.setBackgroundColor(this.mMoreView, localResources.getColor(2131689681));
      localCardViewGroupDelegate.setCardElevation(this.mMoreView, 0.0F);
      this.mMoreView.setTextSize(0, localResources.getDimension(2131493380));
      int i = localResources.getDimensionPixelSize(2131493400);
      ViewCompat.setPaddingRelative(this.mMoreView, i, this.mMoreView.getPaddingTop(), i, this.mMoreView.getPaddingBottom());
    }
  }
  
  public void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (ViewCompat.getLayoutDirection(this) == 0) {}
    for (boolean bool = true;; bool = false)
    {
      int i = getWidth();
      int j = getHeight();
      int k = getPaddingTop();
      int m = ViewCompat.getPaddingStart(this);
      if (this.mHeaderImage.getVisibility() != 8)
      {
        int i6 = this.mHeaderImage.getMeasuredWidth();
        int i7 = this.mHeaderImage.getMeasuredHeight();
        int i8 = k + (j - i7 - k) / 2;
        int i9 = PlayUtils.getViewLeftFromParentStart(i, i6, bool, m);
        this.mHeaderImage.layout(i9, i8, i9 + i6, i8 + i7);
        m += i6 + MarginLayoutParamsCompat.getMarginEnd((ViewGroup.MarginLayoutParams)this.mHeaderImage.getLayoutParams());
      }
      int n = this.mTitleGroup.getMeasuredWidth();
      int i1 = PlayUtils.getViewLeftFromParentStart(i, n, bool, m);
      this.mTitleGroup.layout(i1, k, i1 + n, j);
      if (this.mMoreView.getVisibility() != 8)
      {
        int i2 = this.mMoreView.getMeasuredWidth();
        int i3 = this.mMoreView.getMeasuredHeight();
        int i4 = k + (j - i3 - k) / 2;
        int i5 = PlayUtils.getViewLeftFromParentEnd(i, i2, bool, ViewCompat.getPaddingEnd(this));
        this.mMoreView.layout(i5, i4, i5 + i2, i4 + i3);
      }
      return;
    }
  }
  
  public void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = i - getPaddingLeft() - getPaddingRight();
    int k = this.mHeaderImage.getVisibility();
    int m = 0;
    if (k != 8)
    {
      ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams)this.mHeaderImage.getLayoutParams();
      this.mHeaderImage.measure(View.MeasureSpec.makeMeasureSpec(localMarginLayoutParams.width, 1073741824), View.MeasureSpec.makeMeasureSpec(localMarginLayoutParams.height, 1073741824));
      m = this.mHeaderImage.getMeasuredHeight();
      j -= this.mHeaderImage.getMeasuredWidth() + localMarginLayoutParams.rightMargin;
    }
    if (this.mMoreView.getVisibility() != 8)
    {
      this.mMoreView.measure(0, 0);
      m = Math.max(m, this.mMoreView.getMeasuredHeight());
      j -= this.mMoreView.getMeasuredWidth();
    }
    this.mTitleGroup.measure(View.MeasureSpec.makeMeasureSpec(j, 1073741824), 0);
    setMeasuredDimension(i, Math.max(Math.max(m, this.mTitleGroup.getMeasuredHeight()), this.mMinHeight) + getPaddingTop());
  }
  
  public final void replaceTitles(CharSequence paramCharSequence1, CharSequence paramCharSequence2)
  {
    int i = 8;
    this.mTitleMain.setText(paramCharSequence1);
    TextView localTextView1 = this.mTitleMain;
    int j;
    TextView localTextView2;
    if (TextUtils.isEmpty(paramCharSequence1))
    {
      j = i;
      localTextView1.setVisibility(j);
      this.mTitleSecondary.setText(paramCharSequence2);
      localTextView2 = this.mTitleSecondary;
      if (!TextUtils.isEmpty(paramCharSequence2)) {
        break label68;
      }
    }
    for (;;)
    {
      localTextView2.setVisibility(i);
      return;
      j = 0;
      break;
      label68:
      i = 0;
    }
  }
  
  public final void setContent(int paramInt, String paramString1, String paramString2, String paramString3, View.OnClickListener paramOnClickListener)
  {
    setContent(paramInt, paramString1, paramString2, paramString3, paramOnClickListener, null, null, 0, null, null);
  }
  
  public final void setContent(int paramInt, String paramString1, String paramString2, String paramString3, View.OnClickListener paramOnClickListener, Common.Image paramImage, BitmapLoader paramBitmapLoader)
  {
    if ((paramImage != null) && (paramBitmapLoader != null))
    {
      setContent(paramInt, paramString1, paramString2, paramString3, paramOnClickListener, paramBitmapLoader, paramImage, 0, null, Integer.valueOf(CorpusResourceUtils.getPrimaryColor(getContext(), paramInt)));
      return;
    }
    setContent(paramInt, paramString1, paramString2, paramString3, paramOnClickListener, null, null, 0, null, null);
  }
  
  public final void setContent(int paramInt1, String paramString1, String paramString2, String paramString3, View.OnClickListener paramOnClickListener, BitmapLoader paramBitmapLoader, Common.Image paramImage, int paramInt2, Integer paramInteger1, Integer paramInteger2)
  {
    getContext();
    if (paramImage != null)
    {
      this.mHeaderImage.setImage(paramImage.imageUrl, paramImage.supportsFifeUrlOptions, paramBitmapLoader);
      this.mHeaderImage.setVisibility(0);
      if ((this.mHeaderImage.getVisibility() != 0) || (paramInteger2 == null)) {
        break label185;
      }
      this.mHeaderImage.setBackgroundPaintColor(paramInteger2.intValue());
      ViewGroup.LayoutParams localLayoutParams2 = this.mHeaderImage.getLayoutParams();
      localLayoutParams2.height = this.mHeaderIconBackgroundSize;
      localLayoutParams2.width = this.mHeaderIconBackgroundSize;
      if (paramInt2 > 0) {
        this.mHeaderImage.setScaleType(ImageView.ScaleType.CENTER);
      }
      label106:
      this.mTitleMain.setText(paramString1);
      if (!TextUtils.isEmpty(paramString2)) {
        break label225;
      }
      this.mTitleSecondary.setVisibility(8);
    }
    for (;;)
    {
      if (!TextUtils.isEmpty(paramString3)) {
        break label247;
      }
      this.mMoreView.setVisibility(8);
      return;
      if (paramInt2 > 0)
      {
        this.mHeaderImage.setImageResource(paramInt2);
        this.mHeaderImage.setVisibility(0);
        break;
      }
      this.mHeaderImage.setVisibility(8);
      break;
      label185:
      this.mHeaderImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
      ViewGroup.LayoutParams localLayoutParams1 = this.mHeaderImage.getLayoutParams();
      localLayoutParams1.height = this.mHeaderIconSize;
      localLayoutParams1.width = this.mHeaderIconSize;
      break label106;
      label225:
      this.mTitleSecondary.setText(FastHtmlParser.fromHtml(paramString2));
      this.mTitleSecondary.setVisibility(0);
    }
    label247:
    this.mMoreView.setVisibility(0);
    if ((this.mCurrMoreText == null) || (paramString3.compareToIgnoreCase(this.mCurrMoreText) != 0))
    {
      this.mMoreView.setText(paramString3.toUpperCase());
      this.mCurrMoreText = paramString3;
    }
    if (this.mCurrBackendId != paramInt1)
    {
      Resources localResources = getResources();
      if (this.mIsH20Enabled)
      {
        this.mMoreView.setTextColor(localResources.getColor(CorpusResourceUtils.getSecondaryColorResId(paramInt1)));
        this.mCurrBackendId = paramInt1;
      }
    }
    else
    {
      setOnClickListener(paramOnClickListener);
      if (paramOnClickListener == null) {
        break label389;
      }
    }
    label389:
    for (boolean bool = true;; bool = false)
    {
      setClickable(bool);
      return;
      int i = CorpusResourceUtils.getSecondaryColorResId(paramInt1);
      this.mMoreView.getCardViewGroupDelegate().setBackgroundColor(this.mMoreView, getResources().getColor(i));
      break;
    }
  }
  
  public void setExtraHorizontalPadding(int paramInt)
  {
    ViewCompat.setPaddingRelative(this, paramInt + this.mHeaderXPadding, 0, paramInt + this.mHeaderXPadding, 0);
  }
  
  public void setIdentifier(String paramString)
  {
    this.mIdentifier = paramString;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayCardClusterViewHeader
 * JD-Core Version:    0.7.0.1
 */