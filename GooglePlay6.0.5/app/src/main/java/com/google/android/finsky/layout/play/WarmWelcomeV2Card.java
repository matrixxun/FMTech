package com.google.android.finsky.layout.play;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import com.google.android.finsky.adapters.Recyclable;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.layout.IdentifiableFrameLayout;
import com.google.android.play.image.FifeImageView;
import com.google.android.play.layout.CardLinearLayout;

public class WarmWelcomeV2Card
  extends IdentifiableFrameLayout
  implements Recyclable, PlayStoreUiElementNode
{
  public TextView mBody;
  public View mButtonPanel;
  public CardLinearLayout mCardLayout;
  public FifeImageView mGraphic;
  public View mGraphicBox;
  public PlayStoreUiElementNode mParentNode;
  public TextView mPrimaryButton;
  public TextView mSecondaryButton;
  public TextView mTitle;
  public PlayStore.PlayStoreUiElement mUiElementProto;
  
  public WarmWelcomeV2Card(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public WarmWelcomeV2Card(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public final void childImpression(PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    throw new IllegalStateException("unwanted children");
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
    this.mTitle = ((TextView)findViewById(2131756221));
    this.mBody = ((TextView)findViewById(2131756222));
    this.mCardLayout = ((CardLinearLayout)findViewById(2131756224));
    this.mGraphic = ((FifeImageView)findViewById(2131756226));
    this.mGraphicBox = findViewById(2131756225);
    this.mPrimaryButton = ((TextView)findViewById(2131756213));
    this.mSecondaryButton = ((TextView)findViewById(2131756215));
    this.mButtonPanel = findViewById(2131756223);
  }
  
  public final void onRecycle()
  {
    this.mGraphic.clearImage();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.WarmWelcomeV2Card
 * JD-Core Version:    0.7.0.1
 */