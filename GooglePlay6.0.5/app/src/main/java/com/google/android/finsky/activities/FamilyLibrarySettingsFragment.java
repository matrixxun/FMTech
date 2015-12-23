package com.google.android.finsky.activities;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.fragments.PageFragment;
import com.google.android.finsky.fragments.PageFragmentHost;
import com.google.android.finsky.layout.LayoutSwitcher;
import com.google.android.finsky.layout.actionbar.ActionBarController;
import com.google.android.finsky.protos.SharingSetting.FamilySharingSetting;
import com.google.android.finsky.protos.SharingSetting.GetFamilySharingSettingsResponse;
import com.google.android.finsky.protos.SharingSetting.UpdateFamilySharingSettingsResponse;
import com.google.android.finsky.utils.CorpusResourceUtils;
import com.google.android.finsky.utils.ErrorStrings;

public final class FamilyLibrarySettingsFragment
  extends PageFragment
  implements Response.Listener<SharingSetting.GetFamilySharingSettingsResponse>
{
  private LinearLayout mSettingsHolder;
  private SharingSetting.FamilySharingSetting[] mSharingSettings;
  
  protected final int getLayoutRes()
  {
    return 2130968738;
  }
  
  public final PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return FinskyEventLog.obtainPlayStoreUiElement(5220);
  }
  
  public final void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    rebindActionBar();
    if (this.mSharingSettings != null) {}
    for (int i = 1; i == 0; i = 0)
    {
      this.mLayoutSwitcher.switchToLoadingMode();
      requestData();
      return;
    }
    rebindViews();
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setRetainInstance$1385ff();
    if (paramBundle == null) {
      FinskyApp.get().getEventLogger().logPathImpression(0L, this);
    }
  }
  
  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = super.onCreateView(paramLayoutInflater, paramViewGroup, paramBundle);
    this.mSettingsHolder = ((LinearLayout)localView.findViewById(2131755502));
    return localView;
  }
  
  public final void onDestroyView()
  {
    super.onDestroyView();
    this.mSettingsHolder = null;
  }
  
  public final void onPause()
  {
    int i = 0;
    int j = 0;
    while (i < this.mSettingsHolder.getChildCount())
    {
      FamilyLibrarySettingsRowView localFamilyLibrarySettingsRowView = (FamilyLibrarySettingsRowView)this.mSettingsHolder.getChildAt(i);
      SharingSetting.FamilySharingSetting localFamilySharingSetting = this.mSharingSettings[i];
      boolean bool = localFamilySharingSetting.sharingEnabled;
      localFamilySharingSetting.sharingEnabled = localFamilyLibrarySettingsRowView.mCheckBox.isChecked();
      if (localFamilySharingSetting.sharingEnabled != bool) {
        j = 1;
      }
      i++;
    }
    if (j != 0) {
      this.mDfeApi.updateFamilySharingSettings(this.mSharingSettings, new Response.Listener()new Response.ErrorListener {}, new Response.ErrorListener()
      {
        public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
        {
          Context localContext = FamilyLibrarySettingsFragment.this.mContext;
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = ErrorStrings.get(FamilyLibrarySettingsFragment.this.mContext, paramAnonymousVolleyError);
          String str = localContext.getString(2131362995, arrayOfObject);
          Toast.makeText(FamilyLibrarySettingsFragment.this.mContext, str, 1).show();
        }
      });
    }
    super.onPause();
  }
  
  public final void rebindActionBar()
  {
    this.mPageFragmentHost.updateActionBarTitle(getString(2131362997));
    this.mPageFragmentHost.updateCurrentBackendId(0, false);
    this.mPageFragmentHost.switchToRegularActionBar();
    this.mPageFragmentHost.getActionBarController().disableActionBarOverlay();
  }
  
  protected final void rebindViews()
  {
    this.mSettingsHolder.removeAllViews();
    LayoutInflater localLayoutInflater = getActivity().getLayoutInflater();
    SharingSetting.FamilySharingSetting[] arrayOfFamilySharingSetting = this.mSharingSettings;
    int i = arrayOfFamilySharingSetting.length;
    int j = 0;
    if (j < i)
    {
      SharingSetting.FamilySharingSetting localFamilySharingSetting = arrayOfFamilySharingSetting[j];
      FamilyLibrarySettingsRowView localFamilyLibrarySettingsRowView = (FamilyLibrarySettingsRowView)localLayoutInflater.inflate(2130968739, this.mSettingsHolder, false);
      this.mSettingsHolder.addView(localFamilyLibrarySettingsRowView);
      TextView localTextView = localFamilyLibrarySettingsRowView.mName;
      Resources localResources = localFamilyLibrarySettingsRowView.getResources();
      int k = localFamilySharingSetting.backendId;
      String str;
      switch (k)
      {
      case 5: 
      default: 
        throw new IllegalArgumentException("Unsupported backendId (" + k + ")");
      case 3: 
        str = localResources.getString(2131362063);
      }
      for (;;)
      {
        localTextView.setText(str);
        localFamilyLibrarySettingsRowView.mImage.setImageResource(CorpusResourceUtils.getDrawerShopDrawable(localFamilySharingSetting.backendId));
        localFamilyLibrarySettingsRowView.mCheckBox.setChecked(localFamilySharingSetting.sharingEnabled);
        j++;
        break;
        str = localResources.getString(2131362065);
        continue;
        str = localResources.getString(2131362064);
        continue;
        str = localResources.getString(2131362067);
        continue;
        str = localResources.getString(2131362066);
      }
    }
  }
  
  protected final void requestData()
  {
    this.mDfeApi.getFamilySharingSettings(this, this);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.FamilyLibrarySettingsFragment
 * JD-Core Version:    0.7.0.1
 */