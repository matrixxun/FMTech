package com.google.android.finsky.activities;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.layout.AppPermissionAdapter;
import com.google.android.finsky.layout.AppSecurityPermissions;
import com.google.android.finsky.utils.PermissionsBucketer;

public final class InstallApprovalFragment
  extends Fragment
{
  private TextView mContentTextView;
  private AppSecurityPermissions mPermissionsView;
  private TextView mProgressTextView;
  private Bundle mSavedInstanceState;
  private TextView mTitleView;
  
  public static InstallApprovalFragment newInstance(String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3, String[] paramArrayOfString)
  {
    InstallApprovalFragment localInstallApprovalFragment = new InstallApprovalFragment();
    Bundle localBundle = new Bundle();
    localBundle.putString("InstallApprovalFragment.packageName", paramString1);
    localBundle.putString("InstallApprovalFragment.packageTitle", paramString2);
    localBundle.putInt("InstallApprovalFragment.installNumber", paramInt1);
    localBundle.putInt("InstallApprovalFragment.totalInstalls", paramInt2);
    localBundle.putInt("InstallApprovalFragment.approvalType", paramInt3);
    localBundle.putStringArray("InstallApprovalFragment.permissions", paramArrayOfString);
    localInstallApprovalFragment.setArguments(localBundle);
    return localInstallApprovalFragment;
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mSavedInstanceState = paramBundle;
  }
  
  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(2130968799, paramViewGroup, false);
    this.mTitleView = ((TextView)localView.findViewById(2131755173));
    this.mProgressTextView = ((TextView)localView.findViewById(2131755558));
    this.mContentTextView = ((TextView)localView.findViewById(2131755619));
    this.mPermissionsView = ((AppSecurityPermissions)localView.findViewById(2131755615));
    Resources localResources = getResources();
    Bundle localBundle = this.mArguments;
    TextView localTextView1 = this.mProgressTextView;
    Object[] arrayOfObject1 = new Object[2];
    arrayOfObject1[0] = Integer.valueOf(localBundle.getInt("InstallApprovalFragment.installNumber"));
    arrayOfObject1[1] = Integer.valueOf(localBundle.getInt("InstallApprovalFragment.totalInstalls"));
    localTextView1.setText(localResources.getString(2131362234, arrayOfObject1));
    switch (localBundle.getInt("InstallApprovalFragment.approvalType"))
    {
    default: 
      return localView;
    case 1: 
      this.mTitleView.setText(2131362227);
      TextView localTextView3 = this.mContentTextView;
      Object[] arrayOfObject3 = new Object[1];
      arrayOfObject3[0] = localBundle.getString("InstallApprovalFragment.packageTitle");
      localTextView3.setText(Html.fromHtml(localResources.getString(2131362226, arrayOfObject3)));
      this.mContentTextView.setVisibility(0);
      this.mPermissionsView.setVisibility(8);
      return localView;
    case 3: 
      this.mTitleView.setText(2131362230);
      TextView localTextView2 = this.mContentTextView;
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = localBundle.getString("InstallApprovalFragment.packageTitle");
      localTextView2.setText(Html.fromHtml(localResources.getString(2131362229, arrayOfObject2)));
      this.mContentTextView.setVisibility(0);
      this.mPermissionsView.setVisibility(8);
      return localView;
    }
    this.mTitleView.setText(2131362536);
    String str1 = localBundle.getString("InstallApprovalFragment.packageName");
    String[] arrayOfString = localBundle.getStringArray("InstallApprovalFragment.permissions");
    boolean bool = PermissionsBucketer.hasAcceptedBuckets(FinskyApp.get().mInstallerDataStore, str1);
    AppPermissionAdapter localAppPermissionAdapter = new AppPermissionAdapter(getActivity(), str1, arrayOfString, bool);
    String str2 = this.mArguments.getString("InstallApprovalFragment.packageTitle");
    this.mPermissionsView.bindInfo(localAppPermissionAdapter, str2, this.mSavedInstanceState);
    this.mPermissionsView.setVisibility(0);
    this.mContentTextView.setVisibility(0);
    if ((localAppPermissionAdapter.mIsAppInstalled) && (bool)) {}
    for (int i = 2131361843;; i = 2131361849)
    {
      this.mContentTextView.setText(Html.fromHtml(localResources.getString(i, new Object[] { str2 })));
      return localView;
    }
  }
  
  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    if (this.mPermissionsView != null) {
      this.mPermissionsView.saveInstanceState(paramBundle);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.InstallApprovalFragment
 * JD-Core Version:    0.7.0.1
 */