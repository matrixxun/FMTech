package com.google.android.finsky.layout;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.protos.Family;
import com.google.android.finsky.protos.FamilyMember;
import com.google.android.finsky.utils.FamilyUtils;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.HelpFeedbackUtil;
import com.google.android.play.layout.CardLinearLayout;

public class MyAccountFamilyManagementCard
  extends CardLinearLayout
  implements PlayStoreUiElementNode
{
  public FamilyManagementCardCallback mCallback;
  public ViewGroup mCardContent;
  private FinskyEventLog mEventLog;
  public View mExtraLineSeparator;
  public int mFamilyButtonMode = 0;
  public View mLearnMoreView;
  public View mLoadingIndicator;
  public TextView mManageMyFamilyTextView;
  public PlayStoreUiElementNode mParentNode;
  public View mShareSettingsView;
  private final PlayStore.PlayStoreUiElement mUiElement = FinskyEventLog.obtainPlayStoreUiElement(2670);
  
  public MyAccountFamilyManagementCard(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public MyAccountFamilyManagementCard(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public final void childImpression(PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    FinskyEventLog.childImpression(this, paramPlayStoreUiElementNode);
  }
  
  public PlayStoreUiElementNode getParentNode()
  {
    return this.mParentNode;
  }
  
  public PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mUiElement;
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mEventLog = FinskyApp.get().getEventLogger();
    this.mManageMyFamilyTextView = ((TextView)findViewById(2131755715));
    this.mExtraLineSeparator = findViewById(2131755716);
    findViewById(2131755713).setOnClickListener(new View.OnClickListener()
    {
      public final void onClick(View paramAnonymousView)
      {
        switch (MyAccountFamilyManagementCard.this.mFamilyButtonMode)
        {
        default: 
          return;
        case 0: 
          MyAccountFamilyManagementCard.this.mEventLog.logClickEvent(2671, null, MyAccountFamilyManagementCard.this);
          MyAccountFamilyManagementCard.this.mCallback.launchFamilyCreation();
          return;
        case 1: 
          MyAccountFamilyManagementCard.this.mEventLog.logClickEvent(2672, null, MyAccountFamilyManagementCard.this);
          MyAccountFamilyManagementCard.this.mCallback.launchFamilyManagement();
          return;
        }
        MyAccountFamilyManagementCard.this.mEventLog.logClickEvent(2673, null, MyAccountFamilyManagementCard.this);
        MyAccountFamilyManagementCard.this.mCallback.launchFamilyManagement();
      }
    });
    this.mShareSettingsView = findViewById(2131755717);
    String str = FinskyApp.get().getCurrentAccountName();
    if (FinskyApp.get().getExperiments(str).isEnabled(12603252L)) {
      this.mShareSettingsView.setOnClickListener(new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          MyAccountFamilyManagementCard.this.mEventLog.logClickEvent(2674, null, MyAccountFamilyManagementCard.this);
          MyAccountFamilyManagementCard.this.mCallback.launchFamilyLibrarySettings();
        }
      });
    }
    for (;;)
    {
      ((TextView)findViewById(2131755720)).setText(getResources().getString(2131362290).toUpperCase(getResources().getConfiguration().locale));
      this.mLearnMoreView = findViewById(2131755719);
      this.mLoadingIndicator = findViewById(2131755289);
      this.mCardContent = ((ViewGroup)findViewById(2131755712));
      this.mLearnMoreView.setOnClickListener(new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          HelpFeedbackUtil.launchGoogleHelp((Activity)MyAccountFamilyManagementCard.this.getContext(), "familycreation_setup_android_ota");
        }
      });
      return;
      this.mExtraLineSeparator.setVisibility(8);
      this.mShareSettingsView.setVisibility(8);
    }
  }
  
  public final void updateViewWithUserInFamily(Family paramFamily)
  {
    FamilyMember localFamilyMember = FamilyUtils.findSelfInFamily(paramFamily);
    if (localFamilyMember == null)
    {
      FinskyLog.wtf("family management card: myself is missing.", new Object[0]);
      return;
    }
    if (localFamilyMember.role == 1)
    {
      this.mFamilyButtonMode = 1;
      this.mManageMyFamilyTextView.setText(2131362318);
      return;
    }
    this.mFamilyButtonMode = 2;
    this.mManageMyFamilyTextView.setText(2131363048);
  }
  
  public static abstract interface FamilyManagementCardCallback
  {
    public abstract boolean isLoading();
    
    public abstract void launchFamilyCreation();
    
    public abstract void launchFamilyLibrarySettings();
    
    public abstract void launchFamilyManagement();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.MyAccountFamilyManagementCard
 * JD-Core Version:    0.7.0.1
 */