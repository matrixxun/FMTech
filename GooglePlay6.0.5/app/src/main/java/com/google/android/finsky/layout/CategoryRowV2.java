package com.google.android.finsky.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.finsky.adapters.CardLookDecoration.CardSection;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.play.image.FifeImageView;

public class CategoryRowV2
  extends LinearLayout
  implements CardLookDecoration.CardSection, PlayStoreUiElementNode
{
  public FifeImageView mCategoryIcon;
  public TextView mCategoryTitle;
  public PlayStoreUiElementNode mParentNode;
  public PlayStore.PlayStoreUiElement mUiElementProto = FinskyEventLog.obtainPlayStoreUiElement(100);
  
  public CategoryRowV2(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public CategoryRowV2(Context paramContext, AttributeSet paramAttributeSet)
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
    this.mCategoryTitle = ((TextView)findViewById(2131755327));
    this.mCategoryIcon = ((FifeImageView)findViewById(2131755326));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.CategoryRowV2
 * JD-Core Version:    0.7.0.1
 */