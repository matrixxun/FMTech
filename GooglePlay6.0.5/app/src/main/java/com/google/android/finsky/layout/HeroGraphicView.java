package com.google.android.finsky.layout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Matrix.ScaleToFit;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewPropertyAnimator;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import com.android.vending.R.styleable;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.DetailsSummaryViewBinder;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.billing.lightpurchase.AgeVerificationActivity;
import com.google.android.finsky.layout.actionbar.FinskySearchToolbar;
import com.google.android.finsky.layout.play.FinskyHeaderListLayout;
import com.google.android.finsky.layout.play.PlayCardClusterMetadata;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.protos.Common.Image.Dimension;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.EditorialSeriesContainer;
import com.google.android.finsky.utils.ClientMutationCache;
import com.google.android.finsky.utils.CorpusResourceUtils;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.finsky.utils.image.ThumbnailUtils;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.image.FifeImageView;
import com.google.android.play.image.FifeImageView.OnLoadedListener;
import com.google.android.play.layout.ForegroundRelativeLayout;
import java.util.List;

public class HeroGraphicView
  extends ForegroundRelativeLayout
  implements DetailsSectionStack.NoBottomSeparator, DetailsSectionStack.NoTopSeparator, FifeImageView.OnLoadedListener
{
  protected View mBottomOverlayView;
  private int mBottomOverlayViewBottom;
  private int mCorpusFillMode;
  private View mCorpusFillView;
  protected float mDefaultAspectRatio;
  private boolean mForceUseDefaultRatio;
  public int mFullScreenModePeekAmount;
  public FifeImageView mHeroImageView;
  private boolean mIsFrozenInCorpusFill;
  private final boolean mIsFullScreenMode;
  private boolean mIsImageLoaded;
  public boolean mIsInBackgroundLayer;
  private int mLeadingSpacerVerticalSpan;
  private boolean mNeedsToShowPlayIconAfterUnfreezing;
  private ImageView mPlayImageView;
  private boolean mShouldTopAlignImage;
  private TextView mTitle;
  private View mTopOverlayView;
  
  public HeroGraphicView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public HeroGraphicView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.HeroGraphicView);
    this.mIsFullScreenMode = localTypedArray.getBoolean(0, false);
    this.mForceUseDefaultRatio = localTypedArray.getBoolean(1, false);
    this.mShouldTopAlignImage = this.mIsFullScreenMode;
    localTypedArray.recycle();
    this.mCorpusFillMode = 0;
  }
  
  private int getDefaultFillColor(int paramInt)
  {
    return CorpusResourceUtils.getPrimaryColor(getContext(), paramInt);
  }
  
  private static float getDetailsHeroAspectRatio(Document paramDocument, boolean paramBoolean)
  {
    int i;
    if ((paramBoolean) && (paramDocument.mDocument.docType == 2))
    {
      i = 1;
      if (i == 0) {
        break label33;
      }
    }
    label33:
    for (int j = 3;; j = paramDocument.mDocument.docType)
    {
      return getHeroAspectRatio(j);
      i = 0;
      break;
    }
  }
  
  public static Common.Image getDetailsHeroGraphic(Document paramDocument, boolean paramBoolean)
  {
    int i = paramDocument.mDocument.docType;
    int j;
    if ((paramBoolean) && (i == 2))
    {
      j = 1;
      if (j == 0) {
        break label41;
      }
    }
    label41:
    for (Document localDocument = paramDocument.getCreatorDoc();; localDocument = paramDocument)
    {
      if (localDocument != null) {
        break label47;
      }
      return null;
      j = 0;
      break;
    }
    label47:
    if ((paramBoolean) && ((i == 24) || (i == 25))) {}
    for (int k = 1; k != 0; k = 0) {
      return getHeroGraphic(paramDocument, new int[] { 4 });
    }
    return getHeroGraphic(localDocument);
  }
  
  public static int getDetailsHeroSpacerHeight(Context paramContext, Document paramDocument, boolean paramBoolean)
  {
    Resources localResources = paramContext.getResources();
    if ((paramBoolean) && (getDetailsHeroGraphic(paramDocument, paramBoolean) == null)) {}
    for (int i = 1; shouldShowDetailsHeroGraphic(paramDocument, paramBoolean); i = 0)
    {
      int j = getSpacerHeight(paramContext, localResources.getDisplayMetrics().widthPixels, true, getDetailsHeroAspectRatio(paramDocument, paramBoolean));
      if (i != 0) {
        j /= 2;
      }
      if ((!paramBoolean) && (DetailsSummaryViewBinder.supportsThumbnailPeekingOnNonWideLayout(paramDocument.mDocument.docType))) {
        j -= localResources.getDimensionPixelSize(2131493525);
      }
      if (InsetsFrameLayout.SUPPORTS_IMMERSIVE_MODE) {
        j -= UiUtils.getStatusBarHeight(paramContext);
      }
      return j;
    }
    return FinskyHeaderListLayout.getMinimumHeaderHeight$3047fd86(paramContext, 2);
  }
  
  public static float getHeroAspectRatio(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return PlayCardClusterMetadata.getAspectRatio(paramInt);
    case 6: 
    case 7: 
    case 8: 
    case 18: 
    case 19: 
    case 20: 
    case 28: 
    case 30: 
    case 34: 
      return 0.5625F;
    case 1: 
    case 16: 
    case 17: 
    case 44: 
      return 0.4882813F;
    }
    return 0.5F;
  }
  
  public static Common.Image getHeroGraphic(Document paramDocument)
  {
    switch (paramDocument.mDocument.docType)
    {
    default: 
      return getHeroGraphic(paramDocument, new int[] { 4, 0 });
    case 1: 
      return getHeroGraphic(paramDocument, new int[] { 2, 0 });
    case 2: 
      return getHeroGraphic(paramDocument, new int[] { 4, 0 });
    case 5: 
      return null;
    case 44: 
      return getHeroGraphic(paramDocument, new int[] { 14, 0 });
    case 6: 
      return getHeroGraphic(paramDocument, new int[] { 0, 13 });
    case 18: 
    case 19: 
    case 20: 
      return getHeroGraphic(paramDocument, new int[] { 2, 4 });
    case 16: 
    case 17: 
      return getHeroGraphic(paramDocument, new int[] { 14 });
    case 3: 
      return getHeroGraphic(paramDocument, new int[] { 4, 0 });
    case 30: 
      return getHeroGraphic(paramDocument, new int[] { 14 });
    case 8: 
      return getHeroGraphic(paramDocument, new int[] { 14 });
    case 7: 
    case 12: 
      return getHeroGraphic(paramDocument, new int[] { 1 });
    }
    return getHeroGraphic(paramDocument, new int[] { 14 });
  }
  
  private static Common.Image getHeroGraphic(Document paramDocument, int... paramVarArgs)
  {
    if (paramDocument == null) {}
    for (;;)
    {
      return null;
      int i = paramVarArgs.length;
      for (int j = 0; j < i; j++)
      {
        List localList = paramDocument.getImages(paramVarArgs[j]);
        if ((localList != null) && (!localList.isEmpty())) {
          return (Common.Image)localList.get(0);
        }
      }
    }
  }
  
  public static int getSpacerHeight(Context paramContext, int paramInt, boolean paramBoolean, float paramFloat)
  {
    if (!paramBoolean) {
      return 3 * FinskySearchToolbar.getToolbarHeight(paramContext);
    }
    Resources localResources = paramContext.getResources();
    DisplayMetrics localDisplayMetrics = localResources.getDisplayMetrics();
    int i = localDisplayMetrics.widthPixels;
    int j = localDisplayMetrics.heightPixels;
    if ((paramFloat > 0.0F) && (j > i)) {
      return (int)(paramFloat * paramInt);
    }
    return Math.min(localResources.getDimensionPixelSize(2131493118), j / 2);
  }
  
  public static boolean shouldPreferVideosAppForPlayback(Document paramDocument)
  {
    return paramDocument.mDocument.docType == 6;
  }
  
  public static boolean shouldShowDetailsHeroGraphic(Document paramDocument, boolean paramBoolean)
  {
    if (paramBoolean) {}
    do
    {
      return true;
      switch (paramDocument.mDocument.docType)
      {
      }
    } while (getDetailsHeroGraphic(paramDocument, paramBoolean) != null);
    return false;
    return false;
  }
  
  private static boolean shouldShowPlayIcon(Document paramDocument)
  {
    if (paramDocument.getAvailabilityRestriction() == 12) {}
    List localList;
    do
    {
      return false;
      localList = paramDocument.getImages(3);
    } while ((localList == null) || (localList.isEmpty()));
    return true;
  }
  
  public final void bindBlankBackend$2549578(int paramInt)
  {
    setCorpusForFill(paramInt);
    setCorpusFillMode(2);
    this.mDefaultAspectRatio = 0.5625F;
    setVisibility(0);
  }
  
  public final void bindDetailsEditorial(Document paramDocument, BitmapLoader paramBitmapLoader, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    EditorialSeriesContainer localEditorialSeriesContainer = paramDocument.getEditorialSeriesContainer();
    setFillColor(UiUtils.getFillColor(localEditorialSeriesContainer, getDefaultFillColor(paramDocument.mDocument.backendId)));
    bindHero$2f5f9d70(getHeroGraphic(paramDocument), paramDocument, paramBitmapLoader, false, paramPlayStoreUiElementNode);
    TextView localTextView = this.mTitle;
    if (TextUtils.isEmpty(localEditorialSeriesContainer.episodeTitle)) {}
    for (String str = localEditorialSeriesContainer.seriesTitle;; str = localEditorialSeriesContainer.episodeTitle)
    {
      localTextView.setText(str);
      this.mTitle.setVisibility(0);
      this.mBottomOverlayView.setVisibility(0);
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = paramDocument.mDocument.title;
      setContentDescription(getContext().getString(2131361990, arrayOfObject));
      this.mShouldTopAlignImage = true;
      this.mFullScreenModePeekAmount = (2 * FinskySearchToolbar.getToolbarHeight(getContext()));
      return;
    }
  }
  
  public final void bindEditorialVideoFooter(BitmapLoader paramBitmapLoader, List<Common.Image> paramList)
  {
    this.mIsInBackgroundLayer = false;
    this.mDefaultAspectRatio = 0.5625F;
    Common.Image localImage = ThumbnailUtils.getBestImage(paramList, 0, getResources().getDimensionPixelSize(2131493118));
    this.mHeroImageView.setImage(localImage.imageUrl, localImage.supportsFifeUrlOptions, paramBitmapLoader);
    setBackgroundResource(0);
  }
  
  public final void bindFeaturedCard(Document paramDocument, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    this.mIsInBackgroundLayer = false;
    this.mDefaultAspectRatio = getHeroAspectRatio(paramDocument.mDocument.docType);
    if (shouldShowPlayIcon(paramDocument)) {
      showPlayIcon(((Common.Image)paramDocument.getImages(3).get(0)).imageUrl, paramDocument.mDocument.title, false, paramDocument.mDocument.mature, paramDocument.mDocument.backendId, paramPlayStoreUiElementNode);
    }
    Common.Image localImage = getHeroGraphic(paramDocument);
    this.mHeroImageView.setVisibility(0);
    this.mHeroImageView.setImage(localImage.imageUrl, localImage.supportsFifeUrlOptions, FinskyApp.get().mBitmapLoader);
    this.mForceUseDefaultRatio = true;
  }
  
  public final void bindGeneric$3dce7526(Document paramDocument)
  {
    BitmapLoader localBitmapLoader = FinskyApp.get().mBitmapLoader;
    List localList = paramDocument.getImages(14);
    if ((localList == null) || (localList.isEmpty()))
    {
      this.mHeroImageView.setVisibility(8);
      return;
    }
    this.mHeroImageView.setVisibility(0);
    this.mHeroImageView.setImage(((Common.Image)localList.get(0)).imageUrl, ((Common.Image)localList.get(0)).supportsFifeUrlOptions, localBitmapLoader);
    this.mDefaultAspectRatio = 0.0F;
  }
  
  public final void bindHero$2f5f9d70(Common.Image paramImage, Document paramDocument, BitmapLoader paramBitmapLoader, boolean paramBoolean, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    this.mIsInBackgroundLayer = true;
    setCorpusForFill(paramDocument.mDocument.backendId);
    if (paramImage == null)
    {
      setCorpusFillMode(2);
      return;
    }
    setCorpusFillMode(1);
    this.mHeroImageView.setOnLoadedListener(this);
    this.mHeroImageView.setToFadeInAfterLoad(false);
    this.mHeroImageView.setImage(paramImage.imageUrl, paramImage.supportsFifeUrlOptions, paramBitmapLoader);
    if (shouldShowPlayIcon(paramDocument)) {
      showPlayIcon(((Common.Image)paramDocument.getImages(3).get(0)).imageUrl, paramDocument.mDocument.title, paramBoolean, paramDocument.mDocument.mature, paramDocument.mDocument.backendId, paramPlayStoreUiElementNode);
    }
    configureDetailsAspectRatio(paramImage, paramDocument);
    setVisibility(0);
  }
  
  public final void bindVideoThumbnail(BitmapLoader paramBitmapLoader, Document paramDocument)
  {
    this.mIsInBackgroundLayer = false;
    this.mShouldTopAlignImage = true;
    this.mDefaultAspectRatio = 0.5625F;
    Common.Image localImage = getHeroGraphic(paramDocument, new int[] { 13 });
    if (localImage == null)
    {
      setVisibility(8);
      return;
    }
    this.mHeroImageView.setImage(localImage.imageUrl, localImage.supportsFifeUrlOptions, paramBitmapLoader);
    setBackgroundResource(0);
  }
  
  public final void configureDetailsAspectRatio(Common.Image paramImage, Document paramDocument)
  {
    if (!this.mIsFullScreenMode)
    {
      this.mDefaultAspectRatio = getHeroAspectRatio(paramDocument.mDocument.docType);
      return;
    }
    if ((paramImage.dimension != null) && (paramImage.dimension.hasWidth) && (paramImage.dimension.hasHeight))
    {
      this.mDefaultAspectRatio = (paramImage.dimension.height / paramImage.dimension.width);
      return;
    }
    this.mDefaultAspectRatio = getDetailsHeroAspectRatio(paramDocument, this.mIsFullScreenMode);
  }
  
  @TargetApi(21)
  public final void freezeCorpusFill$1349ef()
  {
    boolean bool = true;
    this.mIsFrozenInCorpusFill = bool;
    if (this.mPlayImageView.getVisibility() == 0) {}
    for (;;)
    {
      this.mNeedsToShowPlayIconAfterUnfreezing = bool;
      this.mCorpusFillView.setAlpha(0.0F);
      this.mCorpusFillView.animate().alpha(1.0F).setDuration(400L).start();
      this.mHeroImageView.setVisibility(4);
      this.mPlayImageView.setVisibility(4);
      return;
      bool = false;
    }
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mHeroImageView = ((FifeImageView)findViewById(2131755593));
    this.mPlayImageView = ((ImageView)findViewById(2131755596));
    this.mCorpusFillView = findViewById(2131755592);
    this.mTopOverlayView = findViewById(2131755594);
    this.mBottomOverlayView = findViewById(2131755595);
    this.mTitle = ((TextView)findViewById(2131755597));
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = getWidth();
    int j = getHeight();
    int k = getPaddingRight();
    int m = this.mHeroImageView.getMeasuredWidth();
    int n = (i - m - k) / 2;
    this.mHeroImageView.layout(n, 0, n + m, this.mHeroImageView.getMeasuredHeight());
    int i5;
    if (this.mPlayImageView.getVisibility() != 8)
    {
      int i2 = this.mPlayImageView.getMeasuredWidth();
      int i3 = this.mPlayImageView.getMeasuredHeight();
      int i4 = (i - i2 - k) / 2;
      if ((this.mLeadingSpacerVerticalSpan == 0) || (this.mForceUseDefaultRatio))
      {
        i5 = j;
        int i6 = (i5 - i3) / 2;
        this.mPlayImageView.layout(i4, i6, i4 + i2, i6 + i3);
      }
    }
    else
    {
      if ((this.mCorpusFillView != null) && (this.mCorpusFillView.getVisibility() != 8)) {
        this.mCorpusFillView.layout(0, 0, this.mCorpusFillView.getMeasuredWidth(), this.mCorpusFillView.getMeasuredHeight());
      }
      if (this.mTopOverlayView.getVisibility() != 8) {
        this.mTopOverlayView.layout(0, 0, i, this.mTopOverlayView.getMeasuredHeight());
      }
      if (this.mBottomOverlayView.getVisibility() != 8) {
        this.mBottomOverlayView.layout(0, this.mBottomOverlayViewBottom - this.mBottomOverlayView.getMeasuredHeight(), i, this.mBottomOverlayViewBottom);
      }
      if (this.mTitle.getVisibility() != 8) {
        if (this.mLeadingSpacerVerticalSpan != 0) {
          break label316;
        }
      }
    }
    label316:
    for (int i1 = j;; i1 = this.mLeadingSpacerVerticalSpan)
    {
      this.mTitle.layout(0, i1 - this.mTitle.getMeasuredHeight(), i, i1);
      return;
      i5 = this.mLeadingSpacerVerticalSpan;
      break;
    }
  }
  
  public final void onLoaded(FifeImageView paramFifeImageView, Bitmap paramBitmap)
  {
    this.mIsImageLoaded = true;
    if (!this.mIsFrozenInCorpusFill) {
      this.mHeroImageView.setVisibility(0);
    }
    if ((this.mShouldTopAlignImage) && (paramBitmap != null))
    {
      int i = paramBitmap.getWidth();
      int j = paramBitmap.getHeight();
      int k = this.mHeroImageView.getWidth();
      float f = j / i;
      Matrix localMatrix = new Matrix();
      RectF localRectF = new RectF(0.0F, 0.0F, i, j);
      int m = (int)(f * k);
      localMatrix.setRectToRect(localRectF, new RectF(0.0F, 0.0F, k, m), Matrix.ScaleToFit.FILL);
      this.mHeroImageView.setScaleType(ImageView.ScaleType.MATRIX);
      this.mHeroImageView.setImageMatrix(localMatrix);
    }
  }
  
  public final void onLoadedAndFadedIn(FifeImageView paramFifeImageView)
  {
    if (!this.mIsFrozenInCorpusFill)
    {
      setCorpusFillMode(0);
      this.mHeroImageView.setVisibility(0);
      if (this.mNeedsToShowPlayIconAfterUnfreezing) {
        this.mPlayImageView.setVisibility(0);
      }
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.mPlayImageView.measure(0, 0);
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    Context localContext1 = getContext();
    boolean bool1;
    int i6;
    label75:
    Context localContext2;
    float f;
    int n;
    label135:
    label189:
    int i3;
    if (this.mHeroImageView.getVisibility() != 8)
    {
      bool1 = true;
      this.mLeadingSpacerVerticalSpan = getSpacerHeight(localContext1, i, bool1, this.mDefaultAspectRatio);
      if (this.mIsFullScreenMode)
      {
        if (this.mCorpusFillMode != 2) {
          break label305;
        }
        i6 = 1;
        if ((i6 != 0) && (this.mFullScreenModePeekAmount == 0)) {
          this.mLeadingSpacerVerticalSpan /= 2;
        }
      }
      localContext2 = getContext();
      boolean bool2 = this.mIsFullScreenMode;
      boolean bool3 = this.mIsInBackgroundLayer;
      f = this.mDefaultAspectRatio;
      if ((!bool2) || (!bool3)) {
        break label311;
      }
      n = j;
      if (this.mTitle.getVisibility() != 8) {
        this.mTitle.measure(View.MeasureSpec.makeMeasureSpec(i, 1073741824), 0);
      }
      if (this.mHeroImageView.getVisibility() != 8) {
        break label390;
      }
      this.mBottomOverlayView.measure(paramInt1, View.MeasureSpec.makeMeasureSpec(0, 1073741824));
      if ((this.mCorpusFillView != null) && (this.mCorpusFillView.getVisibility() != 8))
      {
        if (this.mCorpusFillMode != 2) {
          break label571;
        }
        if (!this.mIsFullScreenMode) {
          break label562;
        }
        i3 = this.mLeadingSpacerVerticalSpan + 2 * FinskySearchToolbar.getToolbarHeight(getContext());
      }
    }
    for (;;)
    {
      this.mCorpusFillView.measure(paramInt1, View.MeasureSpec.makeMeasureSpec(i3, 1073741824));
      if (this.mTopOverlayView.getVisibility() != 8) {
        this.mTopOverlayView.measure(paramInt1, View.MeasureSpec.makeMeasureSpec(this.mTopOverlayView.getLayoutParams().height, 1073741824));
      }
      setMeasuredDimension(i, n);
      return;
      bool1 = false;
      break;
      label305:
      i6 = 0;
      break label75;
      label311:
      Resources localResources = localContext2.getResources();
      DisplayMetrics localDisplayMetrics = localResources.getDisplayMetrics();
      int k = localDisplayMetrics.widthPixels;
      int m = localDisplayMetrics.heightPixels;
      if ((f > 0.0F) && ((this.mForceUseDefaultRatio) || (m > k)))
      {
        n = (int)(f * i);
        break label135;
      }
      n = Math.min(localResources.getDimensionPixelSize(2131493118), m / 2);
      break label135;
      label390:
      int i1;
      label415:
      int i2;
      Drawable localDrawable;
      if ((this.mIsFullScreenMode) && (this.mFullScreenModePeekAmount > 0))
      {
        i1 = this.mLeadingSpacerVerticalSpan + this.mFullScreenModePeekAmount;
        this.mHeroImageView.measure(paramInt1, View.MeasureSpec.makeMeasureSpec(i1, 1073741824));
        if (this.mBottomOverlayView.getVisibility() == 8) {
          break label189;
        }
        boolean bool4 = this.mIsImageLoaded;
        i2 = 0;
        if (bool4)
        {
          localDrawable = this.mHeroImageView.getDrawable();
          if (localDrawable != null) {
            break label533;
          }
        }
      }
      label533:
      int i4;
      for (this.mBottomOverlayViewBottom = i1;; this.mBottomOverlayViewBottom = Math.min(i * localDrawable.getIntrinsicHeight() / i4, i1))
      {
        int i5 = Math.min(i1 / 2, this.mLeadingSpacerVerticalSpan - this.mTitle.getMeasuredHeight());
        i2 = this.mBottomOverlayViewBottom - i5;
        this.mBottomOverlayView.measure(paramInt1, View.MeasureSpec.makeMeasureSpec(i2, 1073741824));
        break;
        i1 = n;
        break label415;
        i4 = localDrawable.getIntrinsicWidth();
      }
      label562:
      i3 = this.mLeadingSpacerVerticalSpan;
      continue;
      label571:
      if ((this.mIsFullScreenMode) && (this.mDefaultAspectRatio > 0.0F)) {
        i3 = Math.min((int)(i * this.mDefaultAspectRatio), this.mHeroImageView.getMeasuredHeight());
      } else {
        i3 = n;
      }
    }
  }
  
  public void setCorpusFillMode(int paramInt)
  {
    int i = 8;
    if (this.mCorpusFillView == null) {}
    while ((this.mIsFrozenInCorpusFill) || (this.mCorpusFillMode == paramInt)) {
      return;
    }
    this.mCorpusFillMode = paramInt;
    FifeImageView localFifeImageView = this.mHeroImageView;
    int j;
    View localView;
    if (this.mCorpusFillMode == 2)
    {
      j = i;
      localFifeImageView.setVisibility(j);
      localView = this.mCorpusFillView;
      if (this.mCorpusFillMode != 0) {
        break label79;
      }
    }
    for (;;)
    {
      localView.setVisibility(i);
      return;
      j = 0;
      break;
      label79:
      i = 0;
    }
  }
  
  public void setCorpusForFill(int paramInt)
  {
    setFillColor(getDefaultFillColor(paramInt));
  }
  
  public void setFillColor(int paramInt)
  {
    if (this.mCorpusFillView != null) {
      this.mCorpusFillView.setBackgroundColor(paramInt);
    }
  }
  
  public final void showPlayIcon(final String paramString1, String paramString2, final boolean paramBoolean1, final boolean paramBoolean2, final int paramInt, final PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    this.mPlayImageView.setVisibility(0);
    final String str = FinskyApp.get().getCurrentAccountName();
    boolean bool = UiUtils.isAccessibilityEnabled(getContext());
    Object localObject;
    if (bool)
    {
      localObject = this.mPlayImageView;
      ((View)localObject).setOnClickListener(new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          FinskyApp.get().getEventLogger().logClickEvent(120, null, paramPlayStoreUiElementNode);
          if ((paramBoolean2) && (FinskyApp.get().getClientMutationCache(str).isAgeVerificationRequired())) {}
          for (Intent localIntent = AgeVerificationActivity.createIntent(str, paramInt, null);; localIntent = HeroGraphicView.access$000(HeroGraphicView.this, paramString1, paramBoolean1))
          {
            HeroGraphicView.this.getContext().startActivity(localIntent);
            return;
          }
        }
      });
      if ((!TextUtils.isEmpty(paramString2)) && (bool)) {
        this.mPlayImageView.setContentDescription(getContext().getString(2131361990, new Object[] { paramString2 }));
      }
      if (!bool) {
        break label138;
      }
      ViewCompat.setImportantForAccessibility(this, 2);
      setFocusable(false);
    }
    for (;;)
    {
      if (this.mIsFrozenInCorpusFill)
      {
        this.mNeedsToShowPlayIconAfterUnfreezing = true;
        this.mPlayImageView.setVisibility(4);
      }
      return;
      localObject = this;
      break;
      label138:
      setFocusable(true);
    }
  }
  
  @TargetApi(21)
  public final void unfreezeCorpusFill$1349ef()
  {
    this.mIsFrozenInCorpusFill = false;
    if (this.mIsImageLoaded)
    {
      this.mHeroImageView.setVisibility(0);
      this.mHeroImageView.setAlpha(0.0F);
      this.mHeroImageView.animate().alpha(1.0F).setDuration(400L).setListener(new AnimatorListenerAdapter()
      {
        public final void onAnimationEnd(Animator paramAnonymousAnimator)
        {
          HeroGraphicView.this.setCorpusFillMode(0);
        }
      }).start();
      if (this.mNeedsToShowPlayIconAfterUnfreezing)
      {
        this.mPlayImageView.setVisibility(0);
        this.mPlayImageView.setScaleX(0.0F);
        this.mPlayImageView.setScaleY(0.0F);
        this.mPlayImageView.animate().scaleY(1.0F).setDuration(400L).start();
        this.mPlayImageView.animate().scaleX(1.0F).setDuration(400L).start();
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.HeroGraphicView
 * JD-Core Version:    0.7.0.1
 */