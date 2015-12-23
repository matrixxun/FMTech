package com.google.android.finsky.layout.play;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.layout.DocImageView;
import com.google.android.finsky.layout.InsetsFrameLayout;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.play.cardview.CardViewGroupDelegate;
import com.google.android.play.cardview.CardViewGroupDelegates;
import com.google.android.play.image.FifeImageView;
import com.google.android.play.image.FifeImageView.OnLoadedListener;
import com.google.android.play.layout.ForegroundRelativeLayout;

public class PlayHighlightsBannerItemView
  extends ForegroundRelativeLayout
  implements PlayStoreUiElementNode, FifeImageView.OnLoadedListener
{
  public static final int[] HIGHLIGHTS_BANNER_IMAGE_TYPES = { 2 };
  public static StringBuilder sTransitionNameGenericBuilder;
  public View mBackground;
  private float mBreakPoint;
  public DocImageView mCoverImageView;
  public View mGradientOverlay;
  public FifeImageView.OnLoadedListener mOnLoadedListener;
  public PlayStoreUiElementNode mParentNode;
  private int mSolidHeight;
  public TextView mSubtitleView;
  public View mSwatchView;
  public TextView mTitleView;
  private int mTotalHeight;
  public PlayStore.PlayStoreUiElement mUiElementProto = FinskyEventLog.obtainPlayStoreUiElement(523);
  
  static
  {
    if (NavigationManager.areTransitionsEnabled())
    {
      StringBuilder localStringBuilder = new StringBuilder();
      sTransitionNameGenericBuilder = localStringBuilder;
      localStringBuilder.append("transition_generic_circle:");
    }
  }
  
  public PlayHighlightsBannerItemView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayHighlightsBannerItemView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public PlayHighlightsBannerItemView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    getCardViewGroupDelegate().initialize(this, paramContext, paramAttributeSet, paramInt);
  }
  
  public final void childImpression(PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    throw new IllegalStateException("unwanted children");
  }
  
  public CardViewGroupDelegate getCardViewGroupDelegate()
  {
    return CardViewGroupDelegates.IMPL;
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
    this.mTitleView = ((TextView)findViewById(2131755458));
    this.mSubtitleView = ((TextView)findViewById(2131755563));
    this.mCoverImageView = ((DocImageView)findViewById(2131755457));
    this.mSwatchView = findViewById(2131755929);
    this.mGradientOverlay = findViewById(2131755928);
    Resources localResources = getResources();
    if (InsetsFrameLayout.SUPPORTS_IMMERSIVE_MODE) {}
    for (int i = 0;; i = UiUtils.getStatusBarHeight(getContext()))
    {
      this.mSolidHeight = (localResources.getDimensionPixelSize(2131493027) - i);
      this.mTotalHeight = (localResources.getDimensionPixelSize(2131493026) - i);
      this.mBreakPoint = (this.mSolidHeight / this.mTotalHeight);
      this.mBackground = findViewById(2131755927);
      this.mCoverImageView.setPadding(0, this.mSolidHeight, 0, 0);
      return;
    }
  }
  
  public final void onLoaded(FifeImageView paramFifeImageView, Bitmap paramBitmap)
  {
    if (this.mSwatchView != null)
    {
      int i = Bitmap.createScaledBitmap(Bitmap.createBitmap(paramBitmap, 0, 0, paramBitmap.getWidth(), Math.min(20, paramBitmap.getHeight())), 1, 1, false).getPixel(0, 0);
      int j = Color.argb(0, Color.red(i), Color.green(i), Color.blue(i));
      float f = this.mTotalHeight;
      int[] arrayOfInt = { i, i, j };
      float[] arrayOfFloat = new float[3];
      arrayOfFloat[0] = 0.0F;
      arrayOfFloat[1] = this.mBreakPoint;
      arrayOfFloat[2] = 1.0F;
      LinearGradient localLinearGradient = new LinearGradient(0.0F, 0.0F, 0.0F, f, arrayOfInt, arrayOfFloat, Shader.TileMode.CLAMP);
      ShapeDrawable localShapeDrawable = new ShapeDrawable(new RectShape());
      localShapeDrawable.getPaint().setShader(localLinearGradient);
      UiUtils.setBackground(this.mSwatchView, localShapeDrawable);
      UiUtils.setBackground(this.mBackground, new ColorDrawable(i));
    }
    if (this.mOnLoadedListener != null) {
      this.mOnLoadedListener.onLoaded(paramFifeImageView, paramBitmap);
    }
  }
  
  public final void onLoadedAndFadedIn(FifeImageView paramFifeImageView)
  {
    if (this.mOnLoadedListener != null) {
      this.mOnLoadedListener.onLoadedAndFadedIn(paramFifeImageView);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayHighlightsBannerItemView
 * JD-Core Version:    0.7.0.1
 */