package com.google.android.finsky.layout.play;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import com.android.vending.R.styleable;
import com.caverock.androidsvg.SVG;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.fragments.PageFragmentHost;
import com.google.android.finsky.model.CirclesModel;
import com.google.android.finsky.model.CirclesModel.CirclesModelListener;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.ClientMutationCache;
import com.google.android.finsky.utils.GPlusUtils;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.people.data.AudienceMember;
import java.util.List;

public class PlayCirclesButton
  extends ProfileButton
  implements View.OnClickListener, CirclesModel.CirclesModelListener
{
  private CirclesModel mCircleModel;
  private final boolean mIsFlat;
  private PlayStoreUiElementNode mParentNode;
  
  public PlayCirclesButton(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayCirclesButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.PlayCirclesButton);
    this.mIsFlat = localTypedArray.getBoolean(0, false);
    localTypedArray.recycle();
  }
  
  private void configure(List<AudienceMember> paramList)
  {
    int i = 2131230724;
    int j = 2130837597;
    int k;
    Resources localResources;
    String str1;
    label57:
    int n;
    label66:
    Drawable localDrawable;
    if ((paramList != null) && (!paramList.isEmpty()))
    {
      k = 1;
      localResources = getResources();
      str1 = GPlusUtils.getCirclesString(paramList, localResources);
      if (!UiUtils.isSvgExperimentEnabled()) {
        break label175;
      }
      if (!this.mIsFlat) {
        break label130;
      }
      j = 2130837960;
      if (k == 0) {
        break label117;
      }
      if (k == 0) {
        break label123;
      }
      n = 2131689584;
      if (i <= 0) {
        break label169;
      }
      localDrawable = SVG.getDrawableFromResource(getResources(), i, n);
      label82:
      this.mIcon = localDrawable;
      super.configure(str1, j);
      if (k == 0) {
        break label242;
      }
    }
    label117:
    label123:
    Object[] arrayOfObject;
    for (String str2 = str1;; str2 = localResources.getString(2131361969, arrayOfObject))
    {
      setContentDescription(str2);
      return;
      k = 0;
      break;
      i = 2131230723;
      break label57;
      n = 2131689624;
      break label66;
      label130:
      if (k != 0)
      {
        label135:
        if (k == 0) {
          break label158;
        }
        label140:
        if (k == 0) {
          break label163;
        }
      }
      label158:
      label163:
      for (n = 2131689753;; n = 0)
      {
        break;
        j = 2130837596;
        break label135;
        i = 0;
        break label140;
      }
      label169:
      localDrawable = null;
      break label82;
      label175:
      if (this.mIsFlat)
      {
        j = 2130837960;
        if (k != 0) {}
        for (m = 2130837695;; m = 2130837678)
        {
          configure(str1, m, j);
          break;
        }
      }
      if (k != 0) {
        label218:
        if (k == 0) {
          break label236;
        }
      }
      label236:
      for (int m = 2130837697;; m = 0)
      {
        break;
        j = 2130837596;
        break label218;
      }
      label242:
      arrayOfObject = new Object[1];
      arrayOfObject[0] = this.mCircleModel.mTargetPersonDoc.mDocument.title;
    }
  }
  
  public final void bind(Document paramDocument, String paramString, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    this.mParentNode = paramPlayStoreUiElementNode;
    if (this.mCircleModel == null)
    {
      this.mCircleModel = FinskyApp.get().getClientMutationCache(paramString).getCachedCirclesModel(paramDocument, paramString);
      this.mCircleModel.mCirclesModelListener = this;
      Context localContext = FinskyApp.get().getApplicationContext();
      GoogleApiClient localGoogleApiClient = ((PageFragmentHost)getContext()).getPeopleClient();
      this.mCircleModel.loadCircles(localContext, localGoogleApiClient);
      setOnClickListener(this);
    }
    configure(this.mCircleModel.mCircles);
  }
  
  public final void onCirclesUpdate(List<AudienceMember> paramList)
  {
    configure(paramList);
  }
  
  public void onClick(View paramView)
  {
    if ((paramView == this) && ((paramView.getContext() instanceof FragmentActivity)))
    {
      FinskyApp.get().getEventLogger().logClickEvent(280, null, this.mParentNode);
      this.mCircleModel.launchCirclePicker((FragmentActivity)paramView.getContext());
    }
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
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayCirclesButton
 * JD-Core Version:    0.7.0.1
 */