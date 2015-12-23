package com.google.android.finsky.detailspage;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.FrameLayout;
import com.google.android.finsky.layout.DetailsPartialFadeSection;
import com.google.android.play.image.FifeImageView;
import com.google.android.play.layout.PlayTextView;
import java.util.List;

public class AvatarTitleModuleLayout
  extends FrameLayout
  implements ModuleDividerItemDecoration.NoBottomDivider, ModuleDividerItemDecoration.NoTopDivider, DetailsPartialFadeSection
{
  FifeImageView mAvatar;
  boolean mAvatarHasTransitionName;
  private int mAvatarOverlap;
  PlayTextView mTitle;
  
  public AvatarTitleModuleLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public AvatarTitleModuleLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public final void addParticipatingChildViewIds(List<Integer> paramList)
  {
    paramList.add(Integer.valueOf(2131755262));
    if (!this.mAvatarHasTransitionName) {
      paramList.add(Integer.valueOf(2131755263));
    }
  }
  
  public final void addParticipatingChildViews(List<View> paramList)
  {
    paramList.add(this.mTitle);
    if (!this.mAvatarHasTransitionName) {
      paramList.add(this.mAvatar);
    }
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mAvatar = ((FifeImageView)findViewById(2131755263));
    this.mTitle = ((PlayTextView)findViewById(2131755262));
    Resources localResources = getResources();
    int i = localResources.getDimensionPixelSize(2131492936);
    this.mAvatarOverlap = localResources.getDimensionPixelSize(2131493453);
    ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams)getLayoutParams();
    localMarginLayoutParams.topMargin = (-i + this.mAvatarOverlap);
    setLayoutParams(localMarginLayoutParams);
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.mTitle.measure(paramInt1, 0);
    ViewGroup.LayoutParams localLayoutParams = this.mAvatar.getLayoutParams();
    this.mAvatar.measure(View.MeasureSpec.makeMeasureSpec(localLayoutParams.width, 1073741824), View.MeasureSpec.makeMeasureSpec(localLayoutParams.height, 1073741824));
    int i = this.mTitle.getMeasuredHeight() + this.mAvatar.getMeasuredHeight() - this.mAvatarOverlap;
    setMeasuredDimension(View.MeasureSpec.getSize(paramInt1), i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.AvatarTitleModuleLayout
 * JD-Core Version:    0.7.0.1
 */