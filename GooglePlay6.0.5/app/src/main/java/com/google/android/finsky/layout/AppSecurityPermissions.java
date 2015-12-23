package com.google.android.finsky.layout;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.finsky.config.G;
import com.google.android.finsky.utils.ExpandableUtils;
import com.google.android.play.utils.config.GservicesValue;
import java.util.ArrayList;
import java.util.List;

public class AppSecurityPermissions
  extends LinearLayout
{
  private Context mContext;
  private int mExpansionState = 0;
  private String mPackageTitle;
  private PermissionAdapter mPermissionAdapter;
  private final List<View> mPermissionViews = new ArrayList();
  
  public AppSecurityPermissions(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public AppSecurityPermissions(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  private void showPermissions()
  {
    removeAllViews();
    this.mPermissionViews.clear();
    int i = this.mPermissionAdapter.getCount();
    if (!this.mPermissionAdapter.showTheNoPermissionMessage()) {
      for (int k = 0; k < i; k++)
      {
        View localView = this.mPermissionAdapter.getView(k, null, this);
        this.mPermissionViews.add(localView);
        addView(localView);
      }
    }
    TextView localTextView = (TextView)LayoutInflater.from(this.mContext).inflate(2130968850, this, false);
    String str1 = (String)G.permissionBucketsLearnMoreLink.get();
    if (!TextUtils.isEmpty(str1))
    {
      if (this.mPermissionAdapter.isAppInstalled()) {}
      for (int j = 2131362380;; j = 2131362374)
      {
        Resources localResources = getResources();
        Object[] arrayOfObject3 = new Object[2];
        arrayOfObject3[0] = this.mPackageTitle;
        arrayOfObject3[1] = str1;
        localTextView.setText(Html.fromHtml(localResources.getString(j, arrayOfObject3)));
        localTextView.setMovementMethod(LinkMovementMethod.getInstance());
        addView(localTextView);
        return;
      }
    }
    Context localContext2;
    Object[] arrayOfObject2;
    if (this.mPermissionAdapter.isAppInstalled())
    {
      localContext2 = this.mContext;
      arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = this.mPackageTitle;
    }
    Context localContext1;
    Object[] arrayOfObject1;
    for (String str2 = localContext2.getString(2131362379, arrayOfObject2);; str2 = localContext1.getString(2131362373, arrayOfObject1))
    {
      localTextView.setText(Html.fromHtml(str2));
      break;
      localContext1 = this.mContext;
      arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = this.mPackageTitle;
    }
  }
  
  public final void bindInfo(PermissionAdapter paramPermissionAdapter, String paramString, Bundle paramBundle)
  {
    this.mContext = getContext();
    this.mPackageTitle = paramString;
    this.mPermissionAdapter = paramPermissionAdapter;
    if (this.mExpansionState == 0) {
      this.mExpansionState = ExpandableUtils.getSavedExpansionState$1c580cd(paramBundle, this.mPackageTitle + ":" + getId());
    }
    showPermissions();
  }
  
  public final void saveInstanceState(Bundle paramBundle)
  {
    ExpandableUtils.saveExpansionState(paramBundle, this.mPackageTitle + ":" + getId(), this.mExpansionState);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.AppSecurityPermissions
 * JD-Core Version:    0.7.0.1
 */