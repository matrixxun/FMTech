package com.google.android.finsky.layout.play;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.MainActivity;
import com.google.android.finsky.adapters.Recyclable;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.layout.HeroGraphicView;
import com.google.android.finsky.layout.IdentifiableFrameLayout;
import com.google.android.finsky.layout.InsetsFrameLayout;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.gms.smart_profile.SmartProfile.IntentBuilder;
import com.google.android.play.image.FifeImageView;

public class PeopleDetailsProfileInfoView
  extends IdentifiableFrameLayout
  implements View.OnClickListener, Recyclable, FinskyHeaderListLayout.StreamSpacer
{
  private final int mAvatarOverlap;
  private View mButtonContainer;
  private final int mCardInset;
  public PlayCirclesButton mCirclesButton;
  private int mCoverHeight;
  public TextView mDisplayName;
  private final int mExtraBottomMargin;
  public PlayStoreUiElementNode mParentNode;
  public Document mPlusDoc;
  public FifeImageView mProfileAvatarImage;
  private View mProfileBlock;
  public ProfileButton mViewProfileButton;
  
  public PeopleDetailsProfileInfoView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PeopleDetailsProfileInfoView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    Resources localResources = paramContext.getResources();
    this.mAvatarOverlap = localResources.getDimensionPixelSize(2131493453);
    this.mExtraBottomMargin = (2 * localResources.getDimensionPixelSize(2131493382));
    this.mCardInset = localResources.getDimensionPixelSize(2131493068);
  }
  
  public void onClick(View paramView)
  {
    if (!(paramView.getContext() instanceof MainActivity)) {}
    MainActivity localMainActivity;
    do
    {
      return;
      localMainActivity = (MainActivity)paramView.getContext();
    } while ((paramView != this.mViewProfileButton) || (this.mPlusDoc == null));
    FinskyApp.get().getEventLogger().logClickEvent(281, null, this.mParentNode);
    String str1 = this.mPlusDoc.mDocument.backendDocid;
    SmartProfile.IntentBuilder localIntentBuilder = new SmartProfile.IntentBuilder();
    String str2 = "g:" + str1;
    localIntentBuilder.mIntent.putExtra("com.google.android.gms.people.smart_profile.QUALIFIED_ID", str2);
    String str3 = FinskyApp.get().getCurrentAccountName();
    localIntentBuilder.mIntent.putExtra("com.google.android.gms.people.smart_profile.VIEWER_ACCOUNT_NAME", str3);
    localIntentBuilder.mIntent.putExtra("com.google.android.gms.people.smart_profile.APPLICATION_ID", 121);
    localMainActivity.startActivityForResult(localIntentBuilder.mIntent, 0);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mProfileAvatarImage = ((FifeImageView)findViewById(2131755382));
    this.mProfileBlock = findViewById(2131755816);
    this.mDisplayName = ((TextView)this.mProfileBlock.findViewById(2131755817));
    this.mButtonContainer = this.mProfileBlock.findViewById(2131755406);
    this.mCirclesButton = ((PlayCirclesButton)this.mButtonContainer.findViewById(2131755818));
    this.mViewProfileButton = ((ProfileButton)this.mButtonContainer.findViewById(2131755819));
    if (Build.VERSION.SDK_INT >= 21) {
      this.mProfileAvatarImage.setZ(1.0F + this.mProfileBlock.getZ());
    }
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = getWidth();
    int j = this.mProfileAvatarImage.getMeasuredHeight();
    int k = this.mProfileAvatarImage.getMeasuredWidth();
    int m = this.mCoverHeight - j + this.mAvatarOverlap;
    int n = (i - k) / 2;
    this.mProfileAvatarImage.layout(n, m, n + k, m + j);
    int i1 = this.mCoverHeight - this.mCardInset;
    ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams)this.mProfileBlock.getLayoutParams();
    this.mProfileBlock.layout(localMarginLayoutParams.leftMargin - this.mCardInset, i1, localMarginLayoutParams.leftMargin + this.mProfileBlock.getMeasuredWidth(), i1 + this.mProfileBlock.getMeasuredHeight());
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    this.mCoverHeight = HeroGraphicView.getSpacerHeight(getContext(), i, true, 0.5625F);
    if (InsetsFrameLayout.SUPPORTS_IMMERSIVE_MODE) {
      this.mCoverHeight -= UiUtils.getStatusBarHeight(getContext());
    }
    ViewGroup.LayoutParams localLayoutParams = this.mProfileAvatarImage.getLayoutParams();
    this.mProfileAvatarImage.measure(View.MeasureSpec.makeMeasureSpec(localLayoutParams.width, 1073741824), View.MeasureSpec.makeMeasureSpec(localLayoutParams.height, 1073741824));
    ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams)this.mProfileBlock.getLayoutParams();
    int j = i - (localMarginLayoutParams.leftMargin + localMarginLayoutParams.rightMargin - 2 * this.mCardInset);
    this.mProfileBlock.measure(View.MeasureSpec.makeMeasureSpec(j, 1073741824), 0);
    setMeasuredDimension(i, this.mCoverHeight + this.mProfileBlock.getMeasuredHeight() + this.mExtraBottomMargin);
  }
  
  public final void onRecycle()
  {
    this.mProfileAvatarImage.clearImage();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PeopleDetailsProfileInfoView
 * JD-Core Version:    0.7.0.1
 */