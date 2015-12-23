package com.google.android.finsky.layout;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.finsky.utils.OptionalPermissionsBucketer;
import com.google.android.finsky.utils.PermissionBucket;
import com.google.android.finsky.utils.PermissionData;
import com.google.android.finsky.utils.PermissionsBucketer;
import com.google.android.finsky.utils.Sets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class DetailedPermissionsAdapter
  extends PermissionAdapter
{
  private final Context mContext;
  private final List<PermissionBucket> mData = new ArrayList();
  private final boolean mIsAppInstalled;
  private final LayoutInflater mLayoutInflater;
  
  public DetailedPermissionsAdapter(Context paramContext, String paramString, String[] paramArrayOfString, boolean paramBoolean)
  {
    this.mContext = paramContext;
    this.mLayoutInflater = LayoutInflater.from(paramContext);
    PackageInfo localPackageInfo = AppPermissionAdapter.getPackageInfo(paramContext.getPackageManager(), paramString);
    boolean bool;
    Set localSet2;
    if (localPackageInfo != null)
    {
      bool = true;
      this.mIsAppInstalled = bool;
      Set localSet1 = AppPermissionAdapter.loadLocalAssetPermissions(localPackageInfo);
      localSet2 = null;
      if (localSet1 != null)
      {
        Set localSet3 = Sets.newHashSet(paramArrayOfString);
        localSet3.removeAll(localSet1);
        localSet2 = Sets.newHashSet(paramArrayOfString);
        localSet2.removeAll(localSet3);
      }
      if (!paramBoolean) {
        break label183;
      }
    }
    List localList;
    ArrayList localArrayList;
    label183:
    for (PermissionData localPermissionData = OptionalPermissionsBucketer.getPermissionBuckets(paramArrayOfString, localSet2);; localPermissionData = PermissionsBucketer.getPermissionBuckets(paramArrayOfString, localSet2, true, true))
    {
      localList = this.mData;
      localArrayList = new ArrayList();
      PermissionBucket[] arrayOfPermissionBucket = localPermissionData.mPermissionsBuckets;
      int j = arrayOfPermissionBucket.length;
      while (i < j)
      {
        PermissionBucket localPermissionBucket = arrayOfPermissionBucket[i];
        if (localPermissionBucket != null) {
          localArrayList.add(localPermissionBucket);
        }
        i++;
      }
      bool = false;
      break;
    }
    localList.addAll(localArrayList);
  }
  
  public final int getCount()
  {
    return this.mData.size();
  }
  
  public final Object getItem(int paramInt)
  {
    return this.mData.get(paramInt);
  }
  
  public final long getItemId(int paramInt)
  {
    return paramInt;
  }
  
  public final View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    PermissionBucket localPermissionBucket = (PermissionBucket)this.mData.get(paramInt);
    View localView = this.mLayoutInflater.inflate(2130968903, paramViewGroup, false);
    TextView localTextView1 = (TextView)localView.findViewById(2131755487);
    TextView localTextView2 = (TextView)localView.findViewById(2131755214);
    ImageView localImageView1 = (ImageView)localView.findViewById(2131755488);
    ImageView localImageView2 = (ImageView)localView.findViewById(2131755486);
    localImageView1.setVisibility(8);
    localTextView1.setText(localPermissionBucket.mBucketTitle);
    StringBuilder localStringBuilder = new StringBuilder();
    int i;
    int j;
    label121:
    Context localContext2;
    Object[] arrayOfObject2;
    if (localPermissionBucket.mExistingPermissionDescriptions.size() > 0)
    {
      i = 1;
      j = 0;
      if (j >= localPermissionBucket.mNewPermissionDescriptions.size()) {
        break label268;
      }
      if (!this.mIsAppInstalled) {
        break label248;
      }
      localContext2 = this.mContext;
      arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = localPermissionBucket.mNewPermissionDescriptions.get(j);
    }
    label248:
    for (String str = localContext2.getString(2131362365, arrayOfObject2);; str = (String)localPermissionBucket.mNewPermissionDescriptions.get(j))
    {
      localStringBuilder.append(this.mContext.getString(2131361908, new Object[] { str }));
      if ((j < -1 + localPermissionBucket.mNewPermissionDescriptions.size()) || (i != 0)) {
        localStringBuilder.append("<br>");
      }
      j++;
      break label121;
      i = 0;
      break;
    }
    label268:
    for (int k = 0; k < localPermissionBucket.mExistingPermissionDescriptions.size(); k++)
    {
      Context localContext1 = this.mContext;
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = localPermissionBucket.mExistingPermissionDescriptions.get(k);
      localStringBuilder.append(localContext1.getString(2131361908, arrayOfObject1));
      if (k < -1 + localPermissionBucket.mExistingPermissionDescriptions.size()) {
        localStringBuilder.append("<br>");
      }
    }
    localTextView2.setText(Html.fromHtml(localStringBuilder.toString()));
    localImageView2.setImageResource(localPermissionBucket.mBucketIcon);
    localTextView2.setVisibility(0);
    return localView;
  }
  
  public final boolean isAppInstalled()
  {
    return this.mIsAppInstalled;
  }
  
  public final boolean showTheNoPermissionMessage()
  {
    return this.mData.size() == 0;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.DetailedPermissionsAdapter
 * JD-Core Version:    0.7.0.1
 */