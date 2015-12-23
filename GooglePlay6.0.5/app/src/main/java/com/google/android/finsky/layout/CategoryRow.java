package com.google.android.finsky.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.play.layout.ForegroundRelativeLayout;

public class CategoryRow
  extends ForegroundRelativeLayout
  implements PlayStoreUiElementNode
{
  private ImageView mCategoryIcon;
  public TextView mCategoryTitle;
  public PlayStoreUiElementNode mParentNode;
  public PlayStore.PlayStoreUiElement mUiElementProto = FinskyEventLog.obtainPlayStoreUiElement(100);
  
  public CategoryRow(Context paramContext)
  {
    super(paramContext);
  }
  
  public CategoryRow(Context paramContext, AttributeSet paramAttributeSet)
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
    this.mCategoryIcon = ((ImageView)findViewById(2131755326));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.CategoryRow
 * JD-Core Version:    0.7.0.1
 */