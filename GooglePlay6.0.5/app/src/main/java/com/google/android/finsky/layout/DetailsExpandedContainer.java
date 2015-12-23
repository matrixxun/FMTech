package com.google.android.finsky.layout;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.text.method.ArrowKeyMovementMethod;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.TextSectionStateListener;
import com.google.android.finsky.activities.TextSectionTranslatable;
import com.google.android.finsky.detailspage.TextModule.DetailsExtraPrimary;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.navigationmanager.NavigationManager;

public class DetailsExpandedContainer
  extends LinearLayout
  implements TextSectionTranslatable
{
  public DetailsTextBlock mDetailsBodyForTranslation;
  public DetailsTextBlock mDetailsExpandedBody1;
  public DetailsTextBlock mDetailsExpandedBody2;
  public TextView mDetailsExpandedCallout;
  public ViewGroup mDetailsExpandedExtras;
  public boolean mIsShowingTranslation;
  public CharSequence mOriginalBody;
  public CharSequence mTranslatedBody;
  
  public DetailsExpandedContainer(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public DetailsExpandedContainer(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public final boolean hasTranslation()
  {
    return !TextUtils.isEmpty(this.mTranslatedBody);
  }
  
  public final boolean isShowingTranslation()
  {
    return this.mIsShowingTranslation;
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mDetailsExpandedCallout = ((TextView)findViewById(2131755581));
    this.mDetailsExpandedCallout.setMovementMethod(LinkMovementMethod.getInstance());
    this.mDetailsExpandedBody1 = ((DetailsTextBlock)findViewById(2131755582));
    this.mDetailsExpandedBody2 = ((DetailsTextBlock)findViewById(2131755583));
    this.mDetailsExpandedExtras = ((ViewGroup)findViewById(2131755584));
    if (Build.VERSION.SDK_INT >= 11)
    {
      this.mDetailsExpandedCallout.setTextIsSelectable(true);
      this.mDetailsExpandedCallout.setAutoLinkMask(15);
      this.mDetailsExpandedCallout.setMovementMethod(ArrowKeyMovementMethod.getInstance());
    }
    this.mDetailsExpandedBody1.setBodyTextIsSelectable(true);
    this.mDetailsExpandedBody2.setBodyTextIsSelectable(true);
  }
  
  protected void onRestoreInstanceState(Parcelable paramParcelable)
  {
    if ((paramParcelable instanceof Bundle))
    {
      Bundle localBundle = (Bundle)paramParcelable;
      this.mIsShowingTranslation = localBundle.getBoolean("expanded_container.translation_state");
      super.onRestoreInstanceState(localBundle.getParcelable("expanded_container.parent_instance_state"));
      return;
    }
    super.onRestoreInstanceState(paramParcelable);
  }
  
  protected Parcelable onSaveInstanceState()
  {
    Bundle localBundle = new Bundle();
    localBundle.putParcelable("expanded_container.parent_instance_state", super.onSaveInstanceState());
    localBundle.putBoolean("expanded_container.translation_state", this.mIsShowingTranslation);
    return localBundle;
  }
  
  public void setTopPaddingOnTopView(int paramInt)
  {
    int i;
    Object localObject;
    if (!TextUtils.isEmpty(this.mDetailsExpandedCallout.getText()))
    {
      i = 1;
      if (i == 0) {
        break label44;
      }
      localObject = this.mDetailsExpandedCallout;
    }
    for (;;)
    {
      ViewCompat.setPaddingRelative((View)localObject, ViewCompat.getPaddingStart((View)localObject), paramInt, ViewCompat.getPaddingEnd((View)localObject), 0);
      return;
      i = 0;
      break;
      label44:
      if (this.mDetailsExpandedBody1.hasBody()) {
        localObject = this.mDetailsExpandedBody1;
      } else {
        localObject = this.mDetailsExpandedBody2;
      }
    }
  }
  
  public final void toggleTranslation()
  {
    boolean bool;
    DetailsTextBlock localDetailsTextBlock;
    if (!this.mIsShowingTranslation)
    {
      bool = true;
      this.mIsShowingTranslation = bool;
      localDetailsTextBlock = this.mDetailsBodyForTranslation;
      if (!this.mIsShowingTranslation) {
        break label42;
      }
    }
    label42:
    for (CharSequence localCharSequence = this.mTranslatedBody;; localCharSequence = this.mOriginalBody)
    {
      localDetailsTextBlock.setBody(localCharSequence);
      return;
      bool = false;
      break;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.DetailsExpandedContainer
 * JD-Core Version:    0.7.0.1
 */