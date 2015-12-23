package com.google.android.finsky.detailspage;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView.RecycledViewPool;
import android.util.AttributeSet;
import android.view.View.OnClickListener;
import com.google.android.finsky.layout.ClusterContentBinder;
import com.google.android.finsky.layout.ClusterContentConfiguratorRepository;
import com.google.android.finsky.layout.play.PlayCardClusterViewV2;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.utils.UiUtils;

public class CardClusterModuleLayoutV2
  extends PlayCardClusterViewV2
  implements DetailsClusterDecoration.FlatFillSection, ModuleDividerItemDecoration.NoBottomDivider, ModuleDividerItemDecoration.NoTopDivider, ModuleMarginItemDecoration.EdgeToEdge, ModuleMarginItemDecoration.MarginOffset
{
  private final int mMarginOffset;
  
  public CardClusterModuleLayoutV2(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public CardClusterModuleLayoutV2(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    Resources localResources = paramContext.getResources();
    if (localResources.getBoolean(2131427334))
    {
      this.mMarginOffset = (-localResources.getDimensionPixelSize(2131493385));
      return;
    }
    this.mMarginOffset = 0;
  }
  
  public final void bind(int paramInt1, String paramString1, String paramString2, View.OnClickListener paramOnClickListener, int paramInt2, ClusterContentBinder paramClusterContentBinder, ClusterContentConfiguratorRepository paramClusterContentConfiguratorRepository, RecyclerView.RecycledViewPool paramRecycledViewPool, Bundle paramBundle, PlayStoreUiElementNode paramPlayStoreUiElementNode, byte[] paramArrayOfByte)
  {
    Resources localResources = getResources();
    int i = Math.max(localResources.getDimensionPixelSize(2131492956), ModuleMarginItemDecoration.getDefaultSideMargin(localResources, localResources.getBoolean(2131427334)));
    setCardContentHorizontalPadding(i);
    showHeader(paramInt1, paramString1, null, paramString2, paramOnClickListener, i);
    createContent(paramClusterContentBinder, paramClusterContentConfiguratorRepository.getClusterContentConfigurator(paramInt2), UiUtils.getFeaturedGridColumnCount(localResources, 1.0D), paramRecycledViewPool, paramBundle, paramPlayStoreUiElementNode, paramArrayOfByte);
  }
  
  public int getMarginOffset()
  {
    return this.mMarginOffset;
  }
  
  public int getMaxItemsPerPage()
  {
    return UiUtils.getDetailsCardColumnCount(getResources());
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.CardClusterModuleLayoutV2
 * JD-Core Version:    0.7.0.1
 */