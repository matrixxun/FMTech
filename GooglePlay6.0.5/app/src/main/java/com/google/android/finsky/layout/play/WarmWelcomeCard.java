package com.google.android.finsky.layout.play;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.android.vending.R.styleable;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.adapters.Recyclable;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.layout.IdentifiableFrameLayout;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.utils.CorpusResourceUtils;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.image.FifeImageView;

public class WarmWelcomeCard
  extends IdentifiableFrameLayout
  implements Recyclable, PlayStoreUiElementNode
{
  private TextView mBody;
  private WarmWelcomeCardButton mButtonPrimary;
  private WarmWelcomeCardButton mButtonSecondary;
  private View mButtonSeparator;
  private final int mDefaultTitleTopPadding;
  private FifeImageView mGraphic;
  private View mGraphicBox;
  private PlayStoreUiElementNode mParentNode;
  private final boolean mSupportsDynamicTitleTopPadding;
  private TextView mTitle;
  private PlayStore.PlayStoreUiElement mUiElementProto;
  
  public WarmWelcomeCard(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public WarmWelcomeCard(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.WarmWelcomeCard);
    this.mSupportsDynamicTitleTopPadding = localTypedArray.getBoolean(0, true);
    localTypedArray.recycle();
    this.mDefaultTitleTopPadding = paramContext.getResources().getDimensionPixelSize(2131493555);
  }
  
  private void syncButtonAlignment()
  {
    if (this.mButtonSecondary.getVisibility() == 8)
    {
      this.mButtonPrimary.setGravity(8388627);
      return;
    }
    this.mButtonPrimary.setGravity(17);
    this.mButtonSecondary.setGravity(17);
  }
  
  public final void childImpression(PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    throw new IllegalStateException("unwanted children");
  }
  
  public final void configureActionButton(String paramString, Common.Image paramImage, View.OnClickListener paramOnClickListener, boolean paramBoolean)
  {
    WarmWelcomeCardButton localWarmWelcomeCardButton;
    if (paramBoolean)
    {
      localWarmWelcomeCardButton = this.mButtonPrimary;
      BitmapLoader localBitmapLoader = FinskyApp.get().mBitmapLoader;
      localWarmWelcomeCardButton.mText.setText(paramString);
      if (paramImage == null) {
        break label101;
      }
      localWarmWelcomeCardButton.mIcon.setImage(paramImage.imageUrl, paramImage.supportsFifeUrlOptions, localBitmapLoader);
      localWarmWelcomeCardButton.mIcon.setVisibility(0);
    }
    for (;;)
    {
      localWarmWelcomeCardButton.setContentDescription(paramString);
      localWarmWelcomeCardButton.setOnClickListener(paramOnClickListener);
      syncButtonAlignment();
      return;
      localWarmWelcomeCardButton = this.mButtonSecondary;
      this.mButtonSeparator.setVisibility(0);
      this.mButtonSecondary.setVisibility(0);
      break;
      label101:
      localWarmWelcomeCardButton.mIcon.setVisibility(8);
    }
  }
  
  public final void configureContent(CharSequence paramCharSequence1, CharSequence paramCharSequence2, Common.Image paramImage, int paramInt, PlayStoreUiElementNode paramPlayStoreUiElementNode, byte[] paramArrayOfByte)
  {
    int i = 1;
    this.mTitle.setText(paramCharSequence1);
    this.mBody.setText(paramCharSequence2);
    int j;
    label89:
    label105:
    int k;
    if ((paramInt != 0) && (paramInt != 9))
    {
      j = i;
      if (paramImage == null) {
        break label197;
      }
      this.mGraphicBox.setVisibility(0);
      this.mGraphic.setImage(paramImage.imageUrl, paramImage.supportsFifeUrlOptions, FinskyApp.get().mBitmapLoader);
      if (j == 0) {
        break label186;
      }
      this.mGraphicBox.setBackgroundColor(CorpusResourceUtils.getPrimaryColor(getContext(), paramInt));
      if ((paramImage == null) || (j != 0) || (!this.mSupportsDynamicTitleTopPadding)) {
        break label209;
      }
      k = 0;
      if (i == 0) {
        break label215;
      }
    }
    for (;;)
    {
      ViewCompat.setPaddingRelative(this.mTitle, ViewCompat.getPaddingStart(this.mTitle), k, ViewCompat.getPaddingEnd(this.mTitle), this.mTitle.getPaddingBottom());
      this.mUiElementProto = FinskyEventLog.obtainPlayStoreUiElement(516);
      FinskyEventLog.setServerLogCookie(this.mUiElementProto, paramArrayOfByte);
      this.mParentNode = paramPlayStoreUiElementNode;
      getParentNode().childImpression(this);
      return;
      j = 0;
      break;
      label186:
      this.mGraphicBox.setBackgroundDrawable(null);
      break label89;
      label197:
      this.mGraphicBox.setVisibility(8);
      break label89;
      label209:
      i = 0;
      break label105;
      label215:
      k = this.mDefaultTitleTopPadding;
    }
  }
  
  public PlayStoreUiElementNode getParentNode()
  {
    return this.mParentNode;
  }
  
  public PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mUiElementProto;
  }
  
  public final void hideSecondaryAction()
  {
    this.mButtonSeparator.setVisibility(8);
    this.mButtonSecondary.setVisibility(8);
    syncButtonAlignment();
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mTitle = ((TextView)findViewById(2131756219));
    this.mBody = ((TextView)findViewById(2131756220));
    this.mGraphicBox = findViewById(2131756216);
    this.mGraphic = ((FifeImageView)this.mGraphicBox.findViewById(2131756217));
    this.mButtonPrimary = ((WarmWelcomeCardButton)findViewById(2131756213));
    this.mButtonSecondary = ((WarmWelcomeCardButton)findViewById(2131756215));
    this.mButtonSeparator = findViewById(2131756214);
  }
  
  public final void onRecycle()
  {
    this.mGraphic.clearImage();
    this.mButtonPrimary.onRecycle();
    this.mButtonSecondary.onRecycle();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.WarmWelcomeCard
 * JD-Core Version:    0.7.0.1
 */