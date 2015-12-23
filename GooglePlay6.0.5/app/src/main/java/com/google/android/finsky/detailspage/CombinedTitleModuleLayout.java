package com.google.android.finsky.detailspage;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import com.google.android.finsky.layout.DetailsPartialFadeSection;
import com.google.android.finsky.layout.DiscoveryBar;
import java.util.List;

public class CombinedTitleModuleLayout
  extends LinearLayout
  implements ModuleDividerItemDecoration.NoBottomDivider, ModuleDividerItemDecoration.NoTopDivider, DetailsPartialFadeSection
{
  private DiscoveryBar mDiscoveryBar;
  private WarningMessageModuleLayout mWarningMessageModuleLayout;
  
  public CombinedTitleModuleLayout(Context paramContext)
  {
    super(paramContext);
  }
  
  public CombinedTitleModuleLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public final void addParticipatingChildViewIds(List<Integer> paramList)
  {
    paramList.add(Integer.valueOf(2131755332));
  }
  
  public final void addParticipatingChildViews(List<View> paramList)
  {
    paramList.add(findViewById(2131755332));
  }
  
  public DiscoveryBar getDiscoveryBarModuleLayout()
  {
    return this.mDiscoveryBar;
  }
  
  public View getTitleModuleLayout()
  {
    return this;
  }
  
  public WarningMessageModuleLayout getWarningMessageModuleLayout()
  {
    return this.mWarningMessageModuleLayout;
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mDiscoveryBar = ((DiscoveryBar)findViewById(2131755446));
    this.mWarningMessageModuleLayout = ((WarningMessageModuleLayout)findViewById(2131756227));
    this.mDiscoveryBar.setBackgroundDrawable(null);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.CombinedTitleModuleLayout
 * JD-Core Version:    0.7.0.1
 */