package com.google.android.play.onboard;

import android.content.Context;
import android.util.AttributeSet;
import com.google.android.libraries.bind.data.Data;
import com.google.android.libraries.bind.widget.BindingFrameLayout;
import com.google.android.play.R.id;

public class OnboardTutorialPage
  extends BindingFrameLayout
  implements OnboardPage
{
  public static final int DK_BACKGROUND_COLOR = R.id.play_onboard__OnboardTutorialPage_backgroundColor;
  public static final int DK_BODY_TEXT = R.id.play_onboard__OnboardTutorialPage_bodyText;
  public static final int DK_ICON_DRAWABLE_ID = R.id.play_onboard__OnboardTutorialPage_iconDrawableId;
  public static final int DK_TITLE_TEXT = R.id.play_onboard__OnboardTutorialPage_titleText;
  protected OnboardHostControl mHostControl;
  
  public OnboardTutorialPage(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public OnboardTutorialPage(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public OnboardTutorialPage(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public OnboardPageInfo getPageInfo()
  {
    Data localData = getData();
    if (localData == null) {}
    for (Object localObject = null;; localObject = localData.get(OnboardPage.DK_PAGE_INFO)) {
      return (OnboardPageInfo)localObject;
    }
  }
  
  public void setOnboardHostControl(OnboardHostControl paramOnboardHostControl)
  {
    this.mHostControl = paramOnboardHostControl;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.onboard.OnboardTutorialPage
 * JD-Core Version:    0.7.0.1
 */