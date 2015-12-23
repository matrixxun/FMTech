package com.google.android.finsky.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentHostCallback;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.fragments.PageFragment;
import com.google.android.finsky.fragments.PageFragmentHost;
import com.google.android.finsky.layout.LayoutSwitcher;
import com.google.android.finsky.layout.MyAccountCard;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.Family;
import com.google.android.finsky.protos.FamilyInfo;
import com.google.android.finsky.protos.FamilyMember;
import com.google.android.finsky.protos.FamilyPurchaseSetting;
import com.google.android.finsky.protos.FamilyPurchaseSettingOption;
import com.google.android.finsky.protos.FamilyPurchaseSettingWarning;
import com.google.android.finsky.protos.GetFamilyPurchaseSettingResponse;
import com.google.android.finsky.protos.SetFamilyPurchaseSettingResponse;
import com.google.android.finsky.utils.ErrorStrings;
import com.google.android.finsky.utils.FamilyUtils;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.HelpFeedbackUtil;
import com.google.android.finsky.utils.SignatureUtils;
import com.google.android.play.image.FifeImageView;
import com.google.android.play.utils.DocV2Utils;

public final class FamilyMemberSettingsFragment
  extends PageFragment
  implements View.OnClickListener, Response.ErrorListener, Response.Listener<GetFamilyPurchaseSettingResponse>
{
  private TextView mCardTitleTextView;
  private TextView mLearnMoreDescriptionTextView;
  private String mObfuscatedGaiaId;
  private RadioGroup mOptionsHolderRadioGroup;
  private ViewGroup mPurchaseSettingCard;
  private GetFamilyPurchaseSettingResponse mPurchaseSettingResponse;
  private Intent mRemoveMemberIntent;
  private FamilyMember mSelf;
  private boolean mShouldShowRemoveMenu;
  private boolean mShowingLeaveFamilyMenu;
  private FamilyMember mViewedMember;
  
  protected final int getLayoutRes()
  {
    return 2130968741;
  }
  
  public final PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return FinskyEventLog.obtainPlayStoreUiElement(5221);
  }
  
  public final void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    int i;
    if (SignatureUtils.getCallingFirstPartyPackage(getActivity()) == null)
    {
      FinskyLog.w("Calling from untrusted package", new Object[0]);
      i = 0;
    }
    while (i == 0)
    {
      this.mPageFragmentHost.goBack();
      return;
      Intent localIntent = getActivity().getIntent();
      this.mObfuscatedGaiaId = localIntent.getStringExtra("memberId");
      FamilyInfo localFamilyInfo = FamilyUtils.getCachedFamilyInfo(this.mDfeApi.getAccountName());
      if ((this.mObfuscatedGaiaId == null) || (localFamilyInfo == null))
      {
        FinskyLog.w("Invoking Family purchase settings without Gaia ID or family is not available", new Object[0]);
        i = 0;
      }
      else
      {
        Family localFamily = localFamilyInfo.family;
        String str = this.mObfuscatedGaiaId;
        int i1;
        label139:
        FamilyMember localFamilyMember;
        if ((localFamily != null) && (localFamily.member != null))
        {
          FamilyMember[] arrayOfFamilyMember = localFamily.member;
          int n = arrayOfFamilyMember.length;
          i1 = 0;
          if (i1 < n)
          {
            localFamilyMember = arrayOfFamilyMember[i1];
            if (!str.equals(localFamilyMember.personDocument.backendDocid)) {}
          }
        }
        for (;;)
        {
          this.mViewedMember = localFamilyMember;
          this.mSelf = FamilyUtils.findSelfInFamily(localFamilyInfo.family);
          if ((this.mViewedMember != null) && (this.mSelf != null)) {
            break label227;
          }
          FinskyLog.w("Cannot find either viewed member or self in family", new Object[0]);
          i = 0;
          break;
          i1++;
          break label139;
          localFamilyMember = null;
        }
        label227:
        boolean bool1;
        label252:
        int m;
        if ((this.mSelf.role != 1) && (this.mSelf == this.mViewedMember))
        {
          bool1 = true;
          if ((this.mSelf.role != 1) || (this.mViewedMember == this.mSelf)) {
            break label343;
          }
          m = 1;
          label277:
          if ((!bool1) && (m == 0)) {
            break label349;
          }
        }
        label343:
        label349:
        for (boolean bool2 = true;; bool2 = false)
        {
          this.mShouldShowRemoveMenu = bool2;
          this.mShowingLeaveFamilyMenu = bool1;
          this.mRemoveMemberIntent = ((Intent)localIntent.getParcelableExtra("removeMemberIntent"));
          if (this.mRemoveMemberIntent != null) {
            break label355;
          }
          FinskyLog.w("removeMemberIntent is required.", new Object[0]);
          i = 0;
          break;
          bool1 = false;
          break label252;
          m = 0;
          break label277;
        }
        label355:
        i = 1;
      }
    }
    if ((this.mSelf.role == 1) && (this.mSelf != this.mViewedMember) && (FinskyApp.get().getExperiments(this.mDfeApi.getAccountName()).isEnabled(12604079L))) {}
    for (int j = 1;; j = 0)
    {
      int k;
      if (this.mPurchaseSettingResponse == null)
      {
        k = 0;
        if (j != 0) {}
      }
      else
      {
        k = 1;
      }
      if (k == 0) {
        break;
      }
      rebindViews();
      return;
    }
    this.mLayoutSwitcher.switchToLoadingMode();
    requestData();
  }
  
  public final void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if ((paramInt1 == 1) && ((paramInt2 == 6) || (paramInt2 == 9)))
    {
      getActivity().setResult(paramInt2, paramIntent);
      this.mPageFragmentHost.goBack();
      return;
    }
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
  }
  
  public final void onClick(View paramView)
  {
    HelpFeedbackUtil.launchGoogleHelp(getActivity(), "family_purchaseapprovalsetting_android_ota");
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (this.mHasMenu != true)
    {
      this.mHasMenu = true;
      if ((isAdded()) && (!this.mHidden)) {
        this.mHost.onSupportInvalidateOptionsMenu();
      }
    }
    setRetainInstance$1385ff();
    if (paramBundle == null) {
      FinskyApp.get().getEventLogger().logPathImpression(0L, this);
    }
  }
  
  public final void onCreateOptionsMenu(Menu paramMenu, MenuInflater paramMenuInflater)
  {
    super.onCreateOptionsMenu(paramMenu, paramMenuInflater);
    if (this.mShouldShowRemoveMenu)
    {
      paramMenuInflater.inflate(2131820546, paramMenu);
      if (this.mShowingLeaveFamilyMenu) {
        paramMenu.findItem(2131756243).setTitle(2131362291);
      }
    }
  }
  
  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = super.onCreateView(paramLayoutInflater, paramViewGroup, paramBundle);
    this.mPurchaseSettingCard = ((ViewGroup)localView.findViewById(2131755504));
    this.mOptionsHolderRadioGroup = ((RadioGroup)localView.findViewById(2131755508));
    this.mCardTitleTextView = ((TextView)localView.findViewById(2131755505));
    this.mLearnMoreDescriptionTextView = ((TextView)localView.findViewById(2131755507));
    localView.findViewById(2131755506).setOnClickListener(this);
    MyAccountCard.setMarginsForCardView(this.mPurchaseSettingCard);
    return localView;
  }
  
  public final void onDestroyView()
  {
    super.onDestroyView();
    this.mPurchaseSettingCard = null;
    this.mCardTitleTextView = null;
    this.mLearnMoreDescriptionTextView = null;
    this.mOptionsHolderRadioGroup = null;
  }
  
  public final boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    default: 
      return super.onOptionsItemSelected(paramMenuItem);
    }
    if (this.mShowingLeaveFamilyMenu) {}
    for (int i = 5223;; i = 5222)
    {
      FinskyApp.get().getEventLogger().logClickEvent(i, null, this);
      startActivityForResult(this.mRemoveMemberIntent, 1);
      return true;
    }
  }
  
  public final void onPause()
  {
    super.onPause();
    if (this.mPurchaseSettingResponse == null) {}
    FamilyPurchaseSetting localFamilyPurchaseSetting;
    int i;
    do
    {
      return;
      localFamilyPurchaseSetting = this.mPurchaseSettingResponse.familyPurchaseSetting;
      i = -1;
      for (int j = 0; j < this.mPurchaseSettingResponse.purchaseOption.length; j++) {
        if (((RadioButton)this.mOptionsHolderRadioGroup.getChildAt(j)).isChecked()) {
          i = this.mPurchaseSettingResponse.purchaseOption[j].optionId;
        }
      }
    } while (i == localFamilyPurchaseSetting.selectedPurchaseOptionId);
    localFamilyPurchaseSetting.selectedPurchaseOptionId = i;
    this.mDfeApi.updateFamilyPurchaseSetting(localFamilyPurchaseSetting.consistencyToken, localFamilyPurchaseSetting.docid, i, new Response.Listener()new Response.ErrorListener {}, new Response.ErrorListener()
    {
      public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
      {
        Context localContext = FamilyMemberSettingsFragment.this.mContext;
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = ErrorStrings.get(FamilyMemberSettingsFragment.this.mContext, paramAnonymousVolleyError);
        String str = localContext.getString(2131362146, arrayOfObject);
        Toast.makeText(FamilyMemberSettingsFragment.this.mContext, str, 1).show();
      }
    });
  }
  
  protected final void rebindViews()
  {
    ViewGroup localViewGroup = this.mDataView;
    Common.Image localImage = DocV2Utils.getFirstImageOfType(this.mViewedMember.personDocument, 4);
    if (localImage != null) {
      ((FifeImageView)localViewGroup.findViewById(2131755382)).setImage(localImage.imageUrl, localImage.supportsFifeUrlOptions, FinskyApp.get().mBitmapLoader);
    }
    ((TextView)localViewGroup.findViewById(2131755509)).setText(this.mViewedMember.personDocument.title);
    if ((this.mPurchaseSettingResponse != null) && (this.mPurchaseSettingResponse.purchaseOption != null) && (this.mPurchaseSettingResponse.purchaseOption.length > 0))
    {
      this.mPurchaseSettingCard.setVisibility(0);
      this.mOptionsHolderRadioGroup.removeAllViews();
      this.mCardTitleTextView.setText(this.mPurchaseSettingResponse.purchaseSettingTitle);
      this.mLearnMoreDescriptionTextView.setText(Html.fromHtml(this.mPurchaseSettingResponse.learnMoreText));
      int i = this.mPurchaseSettingResponse.familyPurchaseSetting.selectedPurchaseOptionId;
      FamilyPurchaseSettingOption[] arrayOfFamilyPurchaseSettingOption = this.mPurchaseSettingResponse.purchaseOption;
      int j = arrayOfFamilyPurchaseSettingOption.length;
      int k = 0;
      if (k < j)
      {
        final FamilyPurchaseSettingOption localFamilyPurchaseSettingOption = arrayOfFamilyPurchaseSettingOption[k];
        RadioButton localRadioButton = (RadioButton)getActivity().getLayoutInflater().inflate(2130968743, this.mOptionsHolderRadioGroup, false);
        localRadioButton.setText(localFamilyPurchaseSettingOption.optionDescription);
        if (localFamilyPurchaseSettingOption.optionId == i) {}
        for (boolean bool = true;; bool = false)
        {
          localRadioButton.setChecked(bool);
          localRadioButton.setId(localFamilyPurchaseSettingOption.optionId);
          if (localFamilyPurchaseSettingOption.warningIfSelected != null) {
            localRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
            {
              public final void onCheckedChanged(CompoundButton paramAnonymousCompoundButton, boolean paramAnonymousBoolean)
              {
                if (paramAnonymousBoolean) {
                  new SimpleAlertDialog.Builder().setTitle(localFamilyPurchaseSettingOption.warningIfSelected.warningTitle).setMessage(localFamilyPurchaseSettingOption.warningIfSelected.warningMessage.trim()).setPositiveId(17039370).build().show(FamilyMemberSettingsFragment.this.mFragmentManager, "warning_dialog");
                }
              }
            });
          }
          this.mOptionsHolderRadioGroup.addView(localRadioButton);
          k++;
          break;
        }
      }
    }
  }
  
  protected final void requestData()
  {
    this.mLayoutSwitcher.switchToLoadingMode();
    this.mDfeApi.getFamilyPurchaseSetting(this.mObfuscatedGaiaId, this, this);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.FamilyMemberSettingsFragment
 * JD-Core Version:    0.7.0.1
 */