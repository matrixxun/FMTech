package com.google.android.finsky.layout.play;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MarginLayoutParamsCompat;
import android.support.v4.view.ViewCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.CorpusResourceUtils;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.PlayAnimationUtils;
import com.google.android.finsky.utils.PlayAnimationUtils.AnimationListenerAdapter;
import com.google.android.finsky.utils.RateReviewHelper;
import com.google.android.finsky.utils.RateReviewHelper.RateReviewListener;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.play.layout.PlayCardLabelView;
import com.google.android.play.layout.PlayCardSnippet;
import com.google.android.play.layout.PlayCardThumbnail;
import com.google.android.play.layout.PlayCardViewBase;
import com.google.android.play.layout.PlayTextView;
import com.google.android.play.utils.PlayUtils;

public class PlayCardViewRate
  extends PlayCardViewBase
  implements PlayRatingBar.OnRatingChangeListener
{
  private PlayCardViewRateOverlay mContentOverlay;
  private PlayRatingBar mRateBar;
  private View mRateBarSeparator;
  private RateListener mRateListener;
  private TextView mSkip;
  private SkipListener mSkipListener;
  private int mState = 0;
  
  public PlayCardViewRate(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayCardViewRate(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  private void setRating(float paramFloat, boolean paramBoolean)
  {
    int i = Math.round(paramFloat);
    if (i <= 0) {
      return;
    }
    if (this.mRateListener != null) {
      this.mRateListener.onRate$2563266(paramBoolean);
    }
    this.mRateBar.setRating(i);
    if (i > 0)
    {
      Context localContext = getContext();
      Resources localResources = getResources();
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(i);
      UiUtils.sendAccessibilityEventWithText(localContext, localResources.getQuantityString(2131296257, i, arrayOfObject), this.mRateBar);
    }
    if (this.mState == 0)
    {
      setState(1);
      Animation localAnimation = PlayAnimationUtils.getFadeInAnimation(getContext(), 100L, null);
      this.mContentOverlay.setVisibility(0);
      Document localDocument = (Document)getData();
      PlayCardViewRateOverlay localPlayCardViewRateOverlay = this.mContentOverlay;
      int j = localDocument.mDocument.backendId;
      localPlayCardViewRateOverlay.mRatingStarsText = Integer.toString(i);
      localPlayCardViewRateOverlay.mRatingLabelText = localPlayCardViewRateOverlay.getResources().getQuantityString(2131296278, i);
      int k = CorpusResourceUtils.getPrimaryColor(localPlayCardViewRateOverlay.getContext(), j);
      localPlayCardViewRateOverlay.mRatingStarsPaint.setColor(k);
      localPlayCardViewRateOverlay.mRatingLabelPaint.setColor(k);
      localPlayCardViewRateOverlay.mRatingStarsPaint.getTextBounds(localPlayCardViewRateOverlay.mRatingStarsText, 0, localPlayCardViewRateOverlay.mRatingStarsText.length(), localPlayCardViewRateOverlay.mRatingStarsRect);
      localPlayCardViewRateOverlay.mRatingLabelPaint.getTextBounds(localPlayCardViewRateOverlay.mRatingLabelText, 0, localPlayCardViewRateOverlay.mRatingLabelText.length(), localPlayCardViewRateOverlay.mRatingLabelRect);
      this.mContentOverlay.startAnimation(localAnimation);
    }
    invalidate();
  }
  
  public final void bindLoading()
  {
    super.bindLoading();
    if (this.mRateBarSeparator != null) {
      this.mRateBarSeparator.setVisibility(8);
    }
    if (this.mRateBar != null) {
      this.mRateBar.setVisibility(8);
    }
  }
  
  public final void clearRating()
  {
    if (this.mRateListener != null) {
      this.mRateListener.onRateCleared();
    }
    this.mRateBar.setRating(0);
    if (this.mState == 1)
    {
      Animation localAnimation = PlayAnimationUtils.getFadeOutAnimation(getContext(), 250L, new PlayAnimationUtils.AnimationListenerAdapter()
      {
        public final void onAnimationEnd(Animation paramAnonymousAnimation)
        {
          PlayCardViewRate.this.setState(0);
        }
      });
      this.mContentOverlay.startAnimation(localAnimation);
    }
    invalidate();
  }
  
  public int getCardType()
  {
    return 13;
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mRateBarSeparator = findViewById(2131755878);
    this.mRateBar = ((PlayRatingBar)findViewById(2131755879));
    this.mContentOverlay = ((PlayCardViewRateOverlay)findViewById(2131755881));
    this.mSkip = ((TextView)findViewById(2131755880));
    this.mSkip.setText(this.mSkip.getText().toString().toUpperCase());
    this.mSkip.setOnClickListener(new View.OnClickListener()
    {
      public final void onClick(View paramAnonymousView)
      {
        if (PlayCardViewRate.this.mSkipListener != null) {
          PlayCardViewRate.this.mSkipListener.onSkip();
        }
      }
    });
    if (!DISABLE_NESTED_FOCUS_TRAVERSAL)
    {
      setNextFocusDownId(2131756006);
      this.mRateBar.findViewById(2131756006).setNextFocusUpId(2131755454);
    }
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (ViewCompat.getLayoutDirection(this) == 0) {}
    for (boolean bool = true;; bool = false)
    {
      int i = ViewCompat.getPaddingStart(this);
      int j = ViewCompat.getPaddingEnd(this);
      int k = getPaddingTop();
      int m = getPaddingBottom();
      int n = getWidth();
      int i1 = getHeight();
      ViewGroup.MarginLayoutParams localMarginLayoutParams1 = (ViewGroup.MarginLayoutParams)this.mThumbnail.getLayoutParams();
      ViewGroup.MarginLayoutParams localMarginLayoutParams2 = (ViewGroup.MarginLayoutParams)this.mTitle.getLayoutParams();
      ViewGroup.MarginLayoutParams localMarginLayoutParams3 = (ViewGroup.MarginLayoutParams)this.mOverflow.getLayoutParams();
      ViewGroup.MarginLayoutParams localMarginLayoutParams4 = (ViewGroup.MarginLayoutParams)this.mLabel.getLayoutParams();
      ViewGroup.MarginLayoutParams localMarginLayoutParams5 = (ViewGroup.MarginLayoutParams)this.mSnippet1.getLayoutParams();
      if (this.mRateBar.getVisibility() != 8)
      {
        ViewGroup.MarginLayoutParams localMarginLayoutParams6 = (ViewGroup.MarginLayoutParams)this.mRateBarSeparator.getLayoutParams();
        ViewGroup.MarginLayoutParams localMarginLayoutParams7 = (ViewGroup.MarginLayoutParams)this.mRateBar.getLayoutParams();
        ViewGroup.MarginLayoutParams localMarginLayoutParams8 = (ViewGroup.MarginLayoutParams)this.mSkip.getLayoutParams();
        int i31 = this.mSkip.getMeasuredWidth();
        int i32 = MarginLayoutParamsCompat.getMarginEnd(localMarginLayoutParams8);
        int i33 = this.mRateBar.getMeasuredWidth();
        int i34 = this.mRateBar.getMeasuredHeight();
        int i35 = PlayUtils.getViewLeftFromParentStart(n, i33, bool, i + (n - i - j - i33 - i31 - MarginLayoutParamsCompat.getMarginStart(localMarginLayoutParams8) - i32) / 2);
        int i36 = i1 - m - localMarginLayoutParams7.bottomMargin - i34;
        this.mRateBar.layout(i35, i36, i35 + i33, i36 + i34);
        int i37 = this.mRateBarSeparator.getMeasuredWidth();
        int i38 = this.mRateBarSeparator.getMeasuredHeight();
        int i39 = PlayUtils.getViewLeftFromParentEnd(n, i37, bool, j + MarginLayoutParamsCompat.getMarginEnd(localMarginLayoutParams6));
        int i40 = i36 - localMarginLayoutParams7.topMargin - localMarginLayoutParams6.bottomMargin - i38;
        this.mRateBarSeparator.layout(i39, i40, i39 + i37, i40 + this.mRateBarSeparator.getMeasuredHeight());
        int i41 = this.mSkip.getMeasuredHeight();
        int i42 = i36 + (i34 - i41) / 2;
        int i43 = PlayUtils.getViewLeftFromParentEnd(n, i31, bool, j + i32);
        this.mSkip.layout(i43, i42, i43 + i31, i42 + i41);
      }
      int i2 = this.mThumbnail.getMeasuredHeight();
      int i3 = this.mThumbnail.getMeasuredWidth();
      int i4 = i + MarginLayoutParamsCompat.getMarginStart(localMarginLayoutParams1);
      int i5 = PlayUtils.getViewLeftFromParentStart(n, i3, bool, i4);
      this.mThumbnail.layout(i5, k + localMarginLayoutParams1.topMargin, i5 + i3, i2 + (k + localMarginLayoutParams1.topMargin));
      int i6 = i4 + i3;
      int i7 = this.mTitle.getMeasuredWidth();
      int i8 = k + localMarginLayoutParams2.topMargin;
      int i9 = i6 + MarginLayoutParamsCompat.getMarginStart(localMarginLayoutParams2);
      int i10 = PlayUtils.getViewLeftFromParentStart(n, i7, bool, i9);
      int i11 = this.mTitle.getMeasuredHeight();
      this.mTitle.layout(i10, i8, i10 + i7, i8 + i11);
      int i12 = this.mOverflow.getMeasuredWidth();
      int i13 = i8 + localMarginLayoutParams3.topMargin;
      int i14 = PlayUtils.getViewLeftFromParentEnd(n, i12, bool, j + MarginLayoutParamsCompat.getMarginEnd(localMarginLayoutParams3));
      this.mOverflow.layout(i14, i13, i14 + i12, i13 + this.mOverflow.getMeasuredHeight());
      int i15 = this.mLabel.getMeasuredWidth();
      int i16 = this.mLabel.getMeasuredHeight();
      int i17 = this.mTitle.getBottom() + localMarginLayoutParams4.topMargin;
      int i18 = PlayUtils.getViewLeftFromParentEnd(n, i15, bool, j + MarginLayoutParamsCompat.getMarginEnd(localMarginLayoutParams4));
      this.mLabel.layout(i18, i17, i18 + i15, i17 + i16);
      int i19 = i17 + this.mLabel.getBaseline() - this.mSubtitle.getBaseline();
      int i20 = this.mSubtitle.getMeasuredWidth();
      int i21 = PlayUtils.getViewLeftFromParentStart(n, i20, bool, i9);
      this.mSubtitle.layout(i21, i19, i21 + i20, i19 + this.mSubtitle.getMeasuredHeight());
      if (this.mSnippet1.getVisibility() != 8)
      {
        int i28 = this.mSnippet1.getMeasuredWidth();
        int i29 = this.mThumbnail.getBottom() - localMarginLayoutParams5.bottomMargin;
        int i30 = PlayUtils.getViewLeftFromParentStart(n, i28, bool, i10 + MarginLayoutParamsCompat.getMarginStart(localMarginLayoutParams5));
        this.mSnippet1.layout(i30, i29 - this.mSnippet1.getMeasuredHeight(), i30 + i28, i29);
      }
      if (this.mContentOverlay.getVisibility() != 8)
      {
        int i26 = this.mContentOverlay.getMeasuredWidth();
        int i27 = PlayUtils.getViewLeftFromParentStart(n, i26, bool, i);
        this.mContentOverlay.layout(i27, k, i27 + i26, k + this.mContentOverlay.getMeasuredHeight());
      }
      int i22 = this.mLoadingIndicator.getMeasuredWidth();
      int i23 = this.mLoadingIndicator.getMeasuredHeight();
      int i24 = i + (n - i - j - i22) / 2;
      int i25 = k + (i1 - k - m - i23) / 2;
      this.mLoadingIndicator.layout(i24, i25, i24 + this.mLoadingIndicator.getMeasuredWidth(), i25 + this.mLoadingIndicator.getMeasuredHeight());
      return;
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i;
    int j;
    int k;
    int i3;
    ViewGroup.MarginLayoutParams localMarginLayoutParams2;
    ViewGroup.MarginLayoutParams localMarginLayoutParams3;
    ViewGroup.MarginLayoutParams localMarginLayoutParams4;
    ViewGroup.MarginLayoutParams localMarginLayoutParams5;
    ViewGroup.MarginLayoutParams localMarginLayoutParams7;
    int i6;
    int i8;
    if ((this.mData != null) && (((Document)this.mData).mDocument.backendId == 3))
    {
      i = 1;
      j = View.MeasureSpec.getSize(paramInt1);
      k = View.MeasureSpec.getSize(paramInt2);
      int m = getPaddingLeft();
      int n = getPaddingRight();
      int i1 = getPaddingTop();
      int i2 = getPaddingBottom();
      i3 = j - m - n;
      ViewGroup.MarginLayoutParams localMarginLayoutParams1 = (ViewGroup.MarginLayoutParams)this.mThumbnail.getLayoutParams();
      localMarginLayoutParams2 = (ViewGroup.MarginLayoutParams)this.mTitle.getLayoutParams();
      localMarginLayoutParams3 = (ViewGroup.MarginLayoutParams)this.mSubtitle.getLayoutParams();
      localMarginLayoutParams4 = (ViewGroup.MarginLayoutParams)this.mLabel.getLayoutParams();
      localMarginLayoutParams5 = (ViewGroup.MarginLayoutParams)this.mSnippet1.getLayoutParams();
      ViewGroup.MarginLayoutParams localMarginLayoutParams6 = (ViewGroup.MarginLayoutParams)this.mRateBar.getLayoutParams();
      localMarginLayoutParams7 = (ViewGroup.MarginLayoutParams)this.mRateBarSeparator.getLayoutParams();
      int i4 = this.mRateBar.getVisibility();
      int i5 = 0;
      if (i4 != 8)
      {
        ViewGroup.MarginLayoutParams localMarginLayoutParams8 = (ViewGroup.MarginLayoutParams)this.mSkip.getLayoutParams();
        this.mSkip.measure(0, 0);
        int i15 = this.mSkip.getMeasuredWidth() + localMarginLayoutParams8.leftMargin + localMarginLayoutParams8.rightMargin;
        this.mRateBar.measure(View.MeasureSpec.makeMeasureSpec(i3 - i15, 1073741824), 0);
        i5 = localMarginLayoutParams6.topMargin + this.mRateBar.getMeasuredHeight() + localMarginLayoutParams6.bottomMargin + localMarginLayoutParams7.bottomMargin;
        if (i != 0) {
          i5 += localMarginLayoutParams7.height + localMarginLayoutParams7.topMargin;
        }
      }
      i6 = k - i1 - i2 - i5;
      int i7 = (int)Math.min(i6 / this.mThumbnailAspectRatio, 2 * (j - m - n) / 3);
      this.mThumbnail.measure(View.MeasureSpec.makeMeasureSpec(i7, 1073741824), View.MeasureSpec.makeMeasureSpec(i6, 1073741824));
      i8 = i3 - i7 - localMarginLayoutParams1.rightMargin;
      if (this.mRateBar.getVisibility() != 8) {
        if (i == 0) {
          break label688;
        }
      }
    }
    label688:
    for (int i14 = i3 - localMarginLayoutParams7.leftMargin - localMarginLayoutParams7.rightMargin;; i14 = i8 - localMarginLayoutParams7.rightMargin - localMarginLayoutParams2.leftMargin)
    {
      this.mRateBarSeparator.measure(View.MeasureSpec.makeMeasureSpec(i14, 1073741824), View.MeasureSpec.makeMeasureSpec(localMarginLayoutParams7.height, 1073741824));
      this.mLabel.measure(View.MeasureSpec.makeMeasureSpec(i3, -2147483648), 0);
      int i9 = this.mLabel.getMeasuredWidth() + localMarginLayoutParams4.leftMargin + localMarginLayoutParams4.rightMargin;
      this.mOverflow.measure(0, 0);
      int i10 = i8 - localMarginLayoutParams2.leftMargin - localMarginLayoutParams2.rightMargin;
      this.mTitle.measure(View.MeasureSpec.makeMeasureSpec(i10, 1073741824), 0);
      int i11 = i8 - localMarginLayoutParams3.leftMargin - localMarginLayoutParams3.rightMargin - i9;
      this.mSubtitle.measure(0, 0);
      if (this.mSubtitle.getMeasuredWidth() > i11) {
        this.mSubtitle.measure(View.MeasureSpec.makeMeasureSpec(i11, 1073741824), 0);
      }
      if (this.mSnippet1.getVisibility() != 8)
      {
        int i13 = i8 - localMarginLayoutParams5.leftMargin - localMarginLayoutParams5.rightMargin;
        this.mSnippet1.measure(View.MeasureSpec.makeMeasureSpec(i13, 1073741824), 1073741824);
      }
      if (this.mContentOverlay.getVisibility() != 8)
      {
        int i12 = i6;
        if ((i != 0) && (this.mRateBar != null)) {
          i12 += this.mRateBarSeparator.getMeasuredHeight() + localMarginLayoutParams7.topMargin;
        }
        this.mContentOverlay.measure(View.MeasureSpec.makeMeasureSpec(i3, 1073741824), View.MeasureSpec.makeMeasureSpec(i12, 1073741824));
      }
      this.mLoadingIndicator.measure(0, 0);
      setMeasuredDimension(j, k);
      return;
      i = 0;
      break;
    }
  }
  
  public final void onRatingChanged(PlayRatingBar paramPlayRatingBar, int paramInt)
  {
    setRating(paramInt, false);
    PlayStoreUiElementNode localPlayStoreUiElementNode = (PlayStoreUiElementNode)getLoggingData();
    FinskyApp.get().getEventLogger().logClickEvent(1208, null, localPlayStoreUiElementNode);
    if (!(paramPlayRatingBar.getContext() instanceof FragmentActivity))
    {
      FinskyLog.wtf("View context is not a fragment activity in Rate Card", new Object[0]);
      return;
    }
    FragmentActivity localFragmentActivity = (FragmentActivity)paramPlayRatingBar.getContext();
    Document localDocument = (Document)getData();
    RateReviewHelper.rateDocument$359c2010(FinskyApp.get().getCurrentAccountName(), localDocument.mDocument.docid, localDocument.mDocument.detailsUrl, Math.round(paramInt), localFragmentActivity, new RateReviewHelper.RateReviewListener()
    {
      public final void onRateReviewCommitted$6ef37c42(int paramAnonymousInt)
      {
        PlayCardViewRate.access$100$1ec146f8(PlayCardViewRate.this, paramAnonymousInt);
      }
      
      public final void onRateReviewFailed()
      {
        PlayCardViewRate.this.clearRating();
      }
    });
  }
  
  public final void setData(Object paramObject, int paramInt)
  {
    super.setData(paramObject, paramInt);
    Document localDocument = (Document)paramObject;
    this.mRateBar.setVerticalPadding(2131493493);
    this.mRateBar.configure(0, this.mBackendId, this);
    String str = CorpusResourceUtils.getRateString(getResources(), localDocument.mDocument.docType);
    this.mRateBar.setContentDescription(str);
  }
  
  public void setRateListener(RateListener paramRateListener)
  {
    this.mRateListener = paramRateListener;
  }
  
  public void setSkipListener(SkipListener paramSkipListener)
  {
    this.mSkipListener = paramSkipListener;
  }
  
  public void setState(int paramInt)
  {
    int i = 1;
    if (this.mState == paramInt) {
      return;
    }
    this.mState = paramInt;
    PlayCardViewRateOverlay localPlayCardViewRateOverlay = this.mContentOverlay;
    int k;
    label55:
    TextView localTextView;
    if (this.mState == i)
    {
      k = 0;
      localPlayCardViewRateOverlay.setVisibility(k);
      PlayRatingBar localPlayRatingBar = this.mRateBar;
      if (this.mState == i) {
        break label90;
      }
      int m = i;
      localPlayRatingBar.setEnabled(m);
      localTextView = this.mSkip;
      if (this.mState == i) {
        break label96;
      }
    }
    for (;;)
    {
      localTextView.setEnabled(i);
      return;
      k = 8;
      break;
      label90:
      int n = 0;
      break label55;
      label96:
      int j = 0;
    }
  }
  
  public void setVisibility(int paramInt)
  {
    if ((this.mState == 2) && (paramInt == 0)) {
      return;
    }
    super.setVisibility(paramInt);
  }
  
  public static abstract interface RateListener
  {
    public abstract void onRate$2563266(boolean paramBoolean);
    
    public abstract void onRateCleared();
  }
  
  public static abstract interface SkipListener
  {
    public abstract void onSkip();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayCardViewRate
 * JD-Core Version:    0.7.0.1
 */