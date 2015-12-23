package com.google.android.finsky.layout.play;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MarginLayoutParamsCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.fragments.PageFragmentHost;
import com.google.android.finsky.model.CirclesModel;
import com.google.android.finsky.model.CirclesModel.CirclesModelListener;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.ClientMutationCache;
import com.google.android.finsky.utils.GPlusUtils;
import com.google.android.finsky.utils.PlayCardUtils;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.people.data.AudienceMember;
import com.google.android.play.layout.PlayCardThumbnail;
import com.google.android.play.utils.PlayUtils;
import java.util.List;

public class PlayCardViewPerson
  extends PlayCardViewAvatar
  implements View.OnClickListener, CirclesModel.CirclesModelListener
{
  private CirclesModel mCircleModel;
  private PlayCirclesIcon mCirclesIcon;
  private TextView mCirclesStatus;
  private ImageView mCirclesStatusIcon;
  
  public PlayCardViewPerson(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayCardViewPerson(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  private void configure(List<AudienceMember> paramList)
  {
    int i;
    if ((paramList != null) && (!paramList.isEmpty()))
    {
      i = 1;
      if (i == 0) {
        break label100;
      }
      this.mCirclesStatus.setText(GPlusUtils.getCirclesString(paramList, getResources()));
      this.mCirclesStatusIcon.setVisibility(0);
    }
    PlayCirclesIcon localPlayCirclesIcon;
    String str;
    for (;;)
    {
      localPlayCirclesIcon = this.mCirclesIcon;
      str = this.mCircleModel.mTargetPersonDoc.mDocument.title;
      if (i == 0) {
        break label132;
      }
      localPlayCirclesIcon.setContentDescription(localPlayCirclesIcon.getResources().getString(2131361988, new Object[] { str }));
      localPlayCirclesIcon.setImageResource(2130837595);
      return;
      i = 0;
      break;
      label100:
      this.mCirclesStatus.setText(this.mCircleModel.mTargetPersonDoc.mDocument.subtitle);
      this.mCirclesStatusIcon.setVisibility(8);
    }
    label132:
    localPlayCirclesIcon.setContentDescription(localPlayCirclesIcon.getResources().getString(2131361969, new Object[] { str }));
    localPlayCirclesIcon.setImageResource(2130837594);
  }
  
  public int getCardType()
  {
    return 12;
  }
  
  public final void onCirclesUpdate(List<AudienceMember> paramList)
  {
    configure(paramList);
  }
  
  public void onClick(View paramView)
  {
    if (!(paramView.getContext() instanceof FragmentActivity)) {}
    while (paramView != this.mCirclesIcon) {
      return;
    }
    FinskyApp.get().getEventLogger().logClickEvent(280, null, PlayCardUtils.getCardParentNode(this));
    FragmentActivity localFragmentActivity = (FragmentActivity)paramView.getContext();
    this.mCircleModel.launchCirclePicker(localFragmentActivity);
  }
  
  public void onDetachedFromWindow()
  {
    if (this.mCircleModel != null)
    {
      this.mCircleModel.mCirclesModelListener = null;
      this.mCircleModel = null;
    }
    super.onDetachedFromWindow();
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mCirclesIcon = ((PlayCirclesIcon)findViewById(2131755818));
    this.mCirclesIcon.setOnClickListener(this);
    this.mCirclesStatus = ((TextView)findViewById(2131755872));
    this.mCirclesStatusIcon = ((ImageView)findViewById(2131755873));
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    boolean bool;
    int i5;
    label109:
    int i9;
    label152:
    int m;
    int n;
    if (ViewCompat.getLayoutDirection(this) == 0)
    {
      bool = true;
      int i = ViewCompat.getPaddingStart(this);
      int j = ViewCompat.getPaddingEnd(this);
      int k = getWidth();
      if (this.mCirclesStatus.getVisibility() != 8)
      {
        ViewGroup.MarginLayoutParams localMarginLayoutParams1 = (ViewGroup.MarginLayoutParams)this.mCirclesStatus.getLayoutParams();
        ViewGroup.MarginLayoutParams localMarginLayoutParams2 = (ViewGroup.MarginLayoutParams)this.mTitle.getLayoutParams();
        int i4 = this.mTitle.getBottom() + localMarginLayoutParams2.bottomMargin + localMarginLayoutParams1.topMargin;
        if (this.mCirclesStatusIcon.getVisibility() != 0) {
          break label402;
        }
        i5 = 1;
        int i6 = this.mCirclesStatus.getMeasuredWidth();
        int i7 = this.mCirclesStatusIcon.getMeasuredWidth();
        int i8 = i6 + MarginLayoutParamsCompat.getMarginStart(localMarginLayoutParams1) + MarginLayoutParamsCompat.getMarginEnd(localMarginLayoutParams1);
        if (i5 == 0) {
          break label408;
        }
        i9 = i7;
        int i10 = i8 + i9;
        int i11 = i + (k - i - j - i10) / 2;
        if (this.mCirclesStatusIcon.getVisibility() == 0)
        {
          int i13 = i4 + this.mCirclesStatus.getMeasuredHeight() / 2 - this.mCirclesStatusIcon.getMeasuredHeight() / 2;
          int i14 = PlayUtils.getViewLeftFromParentStart(k, i7, bool, i11);
          this.mCirclesStatusIcon.layout(i14, i13, i14 + i7, i13 + this.mCirclesStatusIcon.getMeasuredHeight());
          i11 += this.mCirclesStatusIcon.getMeasuredWidth();
        }
        int i12 = PlayUtils.getViewLeftFromParentStart(k, i6, bool, i11 + MarginLayoutParamsCompat.getMarginStart(localMarginLayoutParams1));
        this.mCirclesStatus.layout(i12, i4, i12 + i6, i4 + this.mCirclesStatus.getMeasuredHeight());
      }
      m = this.mThumbnail.getBottom() - this.mThumbnail.getPaddingBottom();
      n = this.mCirclesIcon.getMeasuredWidth();
      if (!bool) {
        break label414;
      }
    }
    label402:
    label408:
    label414:
    for (int i1 = this.mThumbnail.getRight();; i1 = this.mThumbnail.getLeft())
    {
      int i2 = ViewCompat.getPaddingEnd(this.mThumbnail);
      int i3 = PlayUtils.getViewLeftFromParentEnd(i1, n, bool, i2);
      this.mCirclesIcon.layout(i3, m - this.mCirclesIcon.getMeasuredHeight(), i3 + n, m);
      return;
      bool = false;
      break;
      i5 = 0;
      break label109;
      i9 = 0;
      break label152;
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = getPaddingLeft();
    int k = getPaddingRight();
    int m = i - j - k;
    ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams)this.mCirclesStatus.getLayoutParams();
    this.mCirclesStatusIcon.measure(0, 0);
    int n = m - localMarginLayoutParams.leftMargin - localMarginLayoutParams.rightMargin;
    if (this.mCirclesStatusIcon.getVisibility() == 0) {
      n -= this.mCirclesStatusIcon.getMeasuredWidth();
    }
    this.mCirclesStatus.measure(View.MeasureSpec.makeMeasureSpec(n, -2147483648), 0);
    this.mCirclesIcon.measure(0, 0);
    setMeasuredDimension(i, getMeasuredHeight() + localMarginLayoutParams.topMargin + this.mCirclesStatus.getMeasuredHeight() + localMarginLayoutParams.bottomMargin);
  }
  
  public final void setData(Object paramObject, int paramInt)
  {
    super.setData(paramObject, paramInt);
    Document localDocument = (Document)getData();
    if ((this.mCircleModel != null) && ((this.mCircleModel.mOwnerAccountName != FinskyApp.get().getCurrentAccountName()) || (this.mCircleModel.mTargetPersonDoc.mDocument.backendDocid != localDocument.mDocument.backendDocid)))
    {
      this.mCircleModel.mCirclesModelListener = null;
      this.mCircleModel = null;
    }
    if (this.mCircleModel == null)
    {
      String str = FinskyApp.get().getCurrentAccountName();
      this.mCircleModel = FinskyApp.get().getClientMutationCache(str).getCachedCirclesModel(localDocument, str);
      this.mCircleModel.mCirclesModelListener = this;
      Context localContext = FinskyApp.get().getApplicationContext();
      GoogleApiClient localGoogleApiClient = ((PageFragmentHost)getContext()).getPeopleClient();
      this.mCircleModel.loadCircles(localContext, localGoogleApiClient);
    }
    configure(this.mCircleModel.mCircles);
  }
  
  public final void showCirclesIcon(boolean paramBoolean)
  {
    PlayCirclesIcon localPlayCirclesIcon = this.mCirclesIcon;
    if (paramBoolean) {}
    for (int i = 0;; i = 8)
    {
      localPlayCirclesIcon.setVisibility(i);
      return;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayCardViewPerson
 * JD-Core Version:    0.7.0.1
 */