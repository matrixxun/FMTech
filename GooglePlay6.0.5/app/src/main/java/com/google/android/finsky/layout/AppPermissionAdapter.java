package com.google.android.finsky.layout;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.PermissionBucket;
import com.google.android.finsky.utils.PermissionData;
import com.google.android.finsky.utils.PermissionsBucketer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class AppPermissionAdapter
  extends PermissionAdapter
{
  private final List<PermissionBucket> mAcceptedBuckets = new ArrayList();
  private Context mContext;
  public final boolean mIsAppInstalled;
  private LayoutInflater mLayoutInflater;
  private final List<PermissionBucket> mNewBuckets = new ArrayList();
  
  public AppPermissionAdapter(Context paramContext, String paramString, String[] paramArrayOfString, boolean paramBoolean)
  {
    this.mContext = paramContext;
    this.mLayoutInflater = LayoutInflater.from(paramContext);
    PackageInfo localPackageInfo = getPackageInfo(paramContext.getPackageManager(), paramString);
    boolean bool1;
    int i;
    label86:
    PermissionBucket localPermissionBucket;
    boolean bool2;
    boolean bool3;
    if (localPackageInfo != null)
    {
      bool1 = true;
      this.mIsAppInstalled = bool1;
      PermissionData localPermissionData = PermissionsBucketer.getPermissionBuckets(paramArrayOfString, loadLocalAssetPermissions(localPackageInfo), paramBoolean);
      PermissionBucket[] arrayOfPermissionBucket = localPermissionData.mPermissionsBuckets;
      i = 0;
      if (i >= arrayOfPermissionBucket.length) {
        return;
      }
      localPermissionBucket = arrayOfPermissionBucket[i];
      if (localPermissionBucket != null)
      {
        bool2 = localPermissionBucket.hasExistingPermissions();
        bool3 = localPermissionBucket.hasNewPermissions();
        if (i == localPermissionData.mOtherBucketIndex) {
          break label179;
        }
        if (!bool2) {
          break label159;
        }
        this.mAcceptedBuckets.add(localPermissionBucket);
      }
    }
    for (;;)
    {
      i++;
      break label86;
      bool1 = false;
      break;
      label159:
      if (bool3)
      {
        this.mNewBuckets.add(localPermissionBucket);
        continue;
        label179:
        if (bool3) {
          this.mNewBuckets.add(localPermissionBucket);
        }
        if (bool2) {
          this.mAcceptedBuckets.add(localPermissionBucket);
        }
      }
    }
  }
  
  private View getExistingPermissionsView(ViewGroup paramViewGroup, List<PermissionBucket> paramList)
  {
    int i = paramList.size();
    if ((i == 0) || (i == 17)) {
      FinskyLog.wtf("numBuckets=[" + i + "]", new Object[0]);
    }
    String str1;
    String str2;
    label98:
    String str3;
    label125:
    String str4;
    label152:
    String str5;
    label179:
    String str6;
    label206:
    Object localObject;
    if (i > 0)
    {
      str1 = this.mContext.getString(((PermissionBucket)paramList.get(0)).mBucketTitle);
      if (i <= 1) {
        break label486;
      }
      str2 = this.mContext.getString(((PermissionBucket)paramList.get(1)).mBucketTitle);
      if (i <= 2) {
        break label492;
      }
      str3 = this.mContext.getString(((PermissionBucket)paramList.get(2)).mBucketTitle);
      if (i <= 3) {
        break label498;
      }
      str4 = this.mContext.getString(((PermissionBucket)paramList.get(3)).mBucketTitle);
      if (i <= 4) {
        break label504;
      }
      str5 = this.mContext.getString(((PermissionBucket)paramList.get(4)).mBucketTitle);
      if (i <= 5) {
        break label510;
      }
      str6 = this.mContext.getString(((PermissionBucket)paramList.get(5)).mBucketTitle);
      localObject = null;
      switch (i)
      {
      }
    }
    View localView1;
    for (;;)
    {
      localView1 = this.mLayoutInflater.inflate(2130968733, paramViewGroup, false);
      ((ImageView)localView1.findViewById(2131755486)).setImageResource(2130837841);
      TextView localTextView1 = (TextView)localView1.findViewById(2131755487);
      final TextView localTextView2 = (TextView)localView1.findViewById(2131755489);
      final ImageView localImageView = (ImageView)localView1.findViewById(2131755488);
      final ViewGroup localViewGroup = (ViewGroup)localView1.findViewById(2131755490);
      localTextView1.setText(2131361839);
      localTextView2.setText((CharSequence)localObject);
      final View localView2 = localView1.findViewById(2131755484);
      localView2.setTag(Boolean.valueOf(false));
      localView1.setOnClickListener(new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          boolean bool1 = ((Boolean)localView2.getTag()).booleanValue();
          View localView;
          if (!bool1)
          {
            localImageView.setImageResource(2130837793);
            localImageView.setContentDescription(AppPermissionAdapter.this.mContext.getString(2131362023));
            localViewGroup.setVisibility(0);
            localTextView2.setVisibility(8);
            localView = localView2;
            if (bool1) {
              break label133;
            }
          }
          label133:
          for (boolean bool2 = true;; bool2 = false)
          {
            localView.setTag(Boolean.valueOf(bool2));
            return;
            localImageView.setImageResource(2130837795);
            localImageView.setContentDescription(AppPermissionAdapter.this.mContext.getString(2131362024));
            localViewGroup.setVisibility(8);
            localTextView2.setVisibility(0);
            break;
          }
        }
      });
      Iterator localIterator = paramList.iterator();
      while (localIterator.hasNext())
      {
        PermissionBucket localPermissionBucket = (PermissionBucket)localIterator.next();
        View localView3 = getPermissionView(paramViewGroup, localPermissionBucket, this.mContext.getString(localPermissionBucket.mBucketDescription));
        localView3.findViewById(2131755488).setVisibility(8);
        localView3.setOnClickListener(null);
        localView3.setClickable(false);
        localView3.setBackgroundResource(2130837675);
        localView3.findViewById(2131755214).setVisibility(0);
        localViewGroup.addView(localView3);
      }
      str1 = null;
      break;
      label486:
      str2 = null;
      break label98;
      label492:
      str3 = null;
      break label125;
      label498:
      str4 = null;
      break label152;
      label504:
      str5 = null;
      break label179;
      label510:
      str6 = null;
      break label206;
      localObject = this.mContext.getString(((PermissionBucket)paramList.get(0)).mBucketTitle);
      continue;
      localObject = this.mContext.getString(2131362338, new Object[] { str1, str2 });
      continue;
      localObject = this.mContext.getString(2131362339, new Object[] { str1, str2, str3 });
      continue;
      localObject = this.mContext.getString(2131362340, new Object[] { str1, str2, str3, str4 });
      continue;
      localObject = this.mContext.getString(2131362341, new Object[] { str1, str2, str3, str4, str5 });
      continue;
      localObject = this.mContext.getString(2131362342, new Object[] { str1, str2, str3, str4, str5, str6 });
    }
    return localView1;
  }
  
  public static PackageInfo getPackageInfo(PackageManager paramPackageManager, String paramString)
  {
    try
    {
      PackageInfo localPackageInfo = paramPackageManager.getPackageInfo(paramString, 4096);
      return localPackageInfo;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException) {}
    return null;
  }
  
  private View getPermissionView(ViewGroup paramViewGroup, PermissionBucket paramPermissionBucket, String paramString)
  {
    View localView = this.mLayoutInflater.inflate(2130968903, paramViewGroup, false);
    TextView localTextView1 = (TextView)localView.findViewById(2131755487);
    final TextView localTextView2 = (TextView)localView.findViewById(2131755214);
    final ImageView localImageView1 = (ImageView)localView.findViewById(2131755488);
    ImageView localImageView2 = (ImageView)localView.findViewById(2131755486);
    localTextView1.setText(paramPermissionBucket.mBucketTitle);
    localTextView2.setText(paramString);
    localImageView2.setImageResource(paramPermissionBucket.mBucketIcon);
    localTextView2.setVisibility(8);
    localView.setOnClickListener(new View.OnClickListener()
    {
      public final void onClick(View paramAnonymousView)
      {
        if (localTextView2.getVisibility() == 8) {}
        for (int i = 1; i != 0; i = 0)
        {
          localImageView1.setImageResource(2130837793);
          localImageView1.setContentDescription(AppPermissionAdapter.this.mContext.getString(2131362023));
          localTextView2.setVisibility(0);
          return;
        }
        localImageView1.setImageResource(2130837795);
        localImageView1.setContentDescription(AppPermissionAdapter.this.mContext.getString(2131362024));
        localTextView2.setVisibility(8);
      }
    });
    localView.setBackgroundResource(2130837960);
    return localView;
  }
  
  public static Set<String> loadLocalAssetPermissions(PackageInfo paramPackageInfo)
  {
    Object localObject;
    if (paramPackageInfo == null) {
      localObject = null;
    }
    for (;;)
    {
      return localObject;
      localObject = new HashSet();
      if (paramPackageInfo.requestedPermissions != null)
      {
        String[] arrayOfString = paramPackageInfo.requestedPermissions;
        int i = arrayOfString.length;
        for (int j = 0; j < i; j++) {
          ((Set)localObject).add(arrayOfString[j]);
        }
      }
    }
  }
  
  public final int getCount()
  {
    return this.mNewBuckets.size() + Math.min(1, this.mAcceptedBuckets.size());
  }
  
  public final Object getItem(int paramInt)
  {
    if (paramInt < this.mNewBuckets.size()) {
      return this.mNewBuckets.get(paramInt);
    }
    return this.mAcceptedBuckets;
  }
  
  public final long getItemId(int paramInt)
  {
    return paramInt;
  }
  
  public final View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    if (paramInt < this.mNewBuckets.size())
    {
      PermissionBucket localPermissionBucket = (PermissionBucket)this.mNewBuckets.get(paramInt);
      StringBuilder localStringBuilder;
      if (localPermissionBucket.mBucketId == 16)
      {
        localStringBuilder = new StringBuilder();
        for (int i = 0; i < localPermissionBucket.mNewPermissionDescriptions.size(); i++)
        {
          Context localContext = this.mContext;
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = localPermissionBucket.mNewPermissionDescriptions.get(i);
          localStringBuilder.append(localContext.getString(2131361908, arrayOfObject));
          localStringBuilder.append("\n");
        }
      }
      for (String str = localStringBuilder.toString();; str = this.mContext.getString(localPermissionBucket.mBucketDescription)) {
        return getPermissionView(paramViewGroup, localPermissionBucket, str);
      }
    }
    return getExistingPermissionsView(paramViewGroup, this.mAcceptedBuckets);
  }
  
  public final boolean isAppInstalled()
  {
    return this.mIsAppInstalled;
  }
  
  public final boolean showTheNoPermissionMessage()
  {
    if (this.mNewBuckets.size() > 0) {}
    for (int i = 1; i == 0; i = 0) {
      return true;
    }
    return false;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.AppPermissionAdapter
 * JD-Core Version:    0.7.0.1
 */