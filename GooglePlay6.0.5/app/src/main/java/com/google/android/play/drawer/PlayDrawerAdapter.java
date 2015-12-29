package com.google.android.play.drawer;

import android.accounts.Account;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.android.vending.R;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.PlusProfileResponse;
import com.google.android.play.R.color;
import com.google.android.play.R.dimen;
import com.google.android.play.R.drawable;
import com.google.android.play.R.layout;
import com.google.android.play.R.string;
import com.google.android.play.dfe.api.PlayDfeApi;
import com.google.android.play.dfe.api.PlayDfeApiProvider;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.image.FifeImageView;
import com.google.android.play.utils.DocV2Utils;
import com.google.android.play.utils.PlayUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class PlayDrawerAdapter
  extends BaseAdapter
{
  private final Map<String, DocV2> mAccountDocV2s = new HashMap();
  public boolean mAccountListExpanded;
  private final BitmapLoader mBitmapLoader;
  private Context mContext;
  public Account mCurrentAccount;
  boolean mCurrentAvatarClickable;
  public boolean mDownloadOnlyEnabled;
  public PlayDrawerLayout.PlayDrawerDownloadSwitchConfig mDownloadSwitchConfig;
  public boolean mHasAccounts;
  private final LayoutInflater mInflater;
  private final Set<String> mIsAccountDocLoaded = new HashSet();
  private boolean mIsMiniProfile;
  private ListView mListView;
  public Account[] mNonCurrentAccounts = new Account[0];
  private PlayDfeApiProvider mPlayDfeApiProvider;
  private PlayDrawerLayout.PlayDrawerContentClickListener mPlayDrawerContentClickListener;
  private PlayDrawerLayout mPlayDrawerLayout;
  public final List<PlayDrawerLayout.PlayDrawerPrimaryAction> mPrimaryActions = new ArrayList();
  private int mProfileContainerPosition;
  public final List<PlayDrawerLayout.PlayDrawerSecondaryAction> mSecondaryActions = new ArrayList();
  public boolean mShowDownloadOnlyToggle;
  
  public PlayDrawerAdapter(Context paramContext, boolean paramBoolean1, PlayDrawerLayout.PlayDrawerContentClickListener paramPlayDrawerContentClickListener, PlayDfeApiProvider paramPlayDfeApiProvider, BitmapLoader paramBitmapLoader, PlayDrawerLayout paramPlayDrawerLayout, ListView paramListView, boolean paramBoolean2)
  {
    this.mContext = paramContext;
    this.mInflater = LayoutInflater.from(paramContext);
    this.mPlayDfeApiProvider = paramPlayDfeApiProvider;
    this.mBitmapLoader = paramBitmapLoader;
    this.mPlayDrawerContentClickListener = paramPlayDrawerContentClickListener;
    this.mPlayDrawerLayout = paramPlayDrawerLayout;
    this.mListView = paramListView;
    this.mProfileContainerPosition = -1;
    this.mAccountListExpanded = paramBoolean1;
    this.mIsMiniProfile = paramBoolean2;
  }
  
  private View getPrimaryActionView(View paramView, ViewGroup paramViewGroup, final PlayDrawerLayout.PlayDrawerPrimaryAction paramPlayDrawerPrimaryAction, boolean paramBoolean1, boolean paramBoolean2)
  {
    Resources localResources = paramViewGroup.getResources();
    int i;
    View localView;
    label23:
    TextView localTextView;
    Drawable localDrawable1;
    label72:
    label84:
    Drawable localDrawable2;
    label101:
    label122:
    Drawable localDrawable3;
    label152:
    int j;
    label170:
    int k;
    if (paramBoolean1)
    {
      i = R.layout.play_drawer_primary_action_active;
      if (paramView == null) {
        localView = this.mInflater.inflate(i, paramViewGroup, false);
      }
      localView = paramView;
      localTextView = (TextView)localView;
      localTextView.setText(paramPlayDrawerPrimaryAction.actionText);
      if (paramPlayDrawerPrimaryAction.iconResId <= 0) {
        break label389;
      }
      if ((!paramBoolean1) || (paramPlayDrawerPrimaryAction.activeIconResId <= 0)) {
        break label321;
      }
      localDrawable1 = localResources.getDrawable(paramPlayDrawerPrimaryAction.activeIconResId);
      if (!paramBoolean2) {
        break label335;
      }
      localDrawable1.setAlpha(66);
      if (!paramPlayDrawerPrimaryAction.hasNotifications) {
        break label346;
      }
      localDrawable2 = localResources.getDrawable(R.drawable.play_dot_notification);
      if (!PlayUtils.useLtr(this.mContext)) {
        break label352;
      }
      localTextView.setCompoundDrawablesWithIntrinsicBounds(localDrawable1, null, localDrawable2, null);
      if (paramPlayDrawerPrimaryAction.secondaryIconResId > 0)
      {
        localDrawable3 = localResources.getDrawable(paramPlayDrawerPrimaryAction.secondaryIconResId);
        if (!paramBoolean2) {
          break label366;
        }
        localDrawable3.setAlpha(66);
        Drawable[] arrayOfDrawable = localTextView.getCompoundDrawables();
        if (ViewCompat.getLayoutDirection(localTextView) != 0) {
          break label377;
        }
        j = 1;
        if (j != 0) {
          break label383;
        }
        k = 0;
        label178:
        arrayOfDrawable[k] = localDrawable3;
        localTextView.setCompoundDrawablesWithIntrinsicBounds(arrayOfDrawable[0], arrayOfDrawable[1], arrayOfDrawable[2], arrayOfDrawable[3]);
      }
      label206:
      if ((!paramBoolean1) || (paramPlayDrawerPrimaryAction.activeTextColorResId <= 0)) {
        break label401;
      }
      localTextView.setTextColor(localResources.getColor(paramPlayDrawerPrimaryAction.activeTextColorResId));
    }
    for (;;)
    {
      localTextView.setOnClickListener(new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          PlayDrawerAdapter.this.mPlayDrawerContentClickListener.onPrimaryActionClicked(paramPlayDrawerPrimaryAction);
          PlayDrawerAdapter.this.mPlayDrawerLayout.closeDrawer();
        }
      });
      PlayUtils.useLtr(this.mContext);
      localTextView.setGravity(8388627);
      if (!paramPlayDrawerPrimaryAction.isChild) {
        break label438;
      }
      setPaddingStart(localTextView, localResources.getDimensionPixelSize(R.dimen.play_drawer_child_item_left_padding));
      return localTextView;
      if (paramBoolean2)
      {
        i = R.layout.play_drawer_primary_action_disabled;
        break;
      }
      i = R.layout.play_drawer_primary_action_regular;
      break;
      label305:

      break label23;
      label321:
      localDrawable1 = localResources.getDrawable(paramPlayDrawerPrimaryAction.iconResId);
      break label72;
      label335:
      localDrawable1.setAlpha(255);
      break label84;
      label346:
      localDrawable2 = null;
      break label101;
      label352:
      localTextView.setCompoundDrawablesWithIntrinsicBounds(localDrawable2, null, localDrawable1, null);
      break label122;
      label366:
      localDrawable3.setAlpha(255);
      break label152;
      label377:
      j = 0;
      break label170;
      label383:
      k = 2;
      break label178;
      label389:
      localTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
      break label206;
      label401:
      if (paramBoolean2) {
        localTextView.setTextColor(localResources.getColor(R.color.play_disabled_grey));
      } else {
        localTextView.setTextColor(localResources.getColor(R.color.play_fg_primary));
      }
    }
    label438:
    setPaddingStart(localTextView, localResources.getDimensionPixelSize(R.dimen.play_drawer_item_left_padding));
    return localTextView;
  }
  
  private static void setPaddingStart(TextView paramTextView, int paramInt)
  {
    ViewCompat.setPaddingRelative(paramTextView, paramInt, paramTextView.getPaddingTop(), ViewCompat.getPaddingEnd(paramTextView), paramTextView.getPaddingBottom());
  }
  
  private void toggleAccountsList()
  {
    if (!this.mAccountListExpanded) {}
    for (boolean bool = true;; bool = false)
    {
      this.mAccountListExpanded = bool;
      notifyDataSetChanged();
      return;
    }
  }
  
  public final boolean areAllItemsEnabled()
  {
    return false;
  }
  
  public final void collapseAccountListIfNeeded()
  {
    if ((this.mNonCurrentAccounts.length > 0) && (this.mAccountListExpanded)) {
      toggleAccountsList();
    }
  }
  
  public final int getCount()
  {
    if (this.mHasAccounts) {}
    for (int i = 1; !this.mAccountListExpanded; i = 0)
    {
      int j = 1 + (i + 1 + this.mPrimaryActions.size());
      if (this.mShowDownloadOnlyToggle) {
        j++;
      }
      return j + this.mSecondaryActions.size();
    }
    return i + this.mNonCurrentAccounts.length;
  }
  
  public final Object getItem(int paramInt)
  {
    String str;
    if (this.mHasAccounts) {
      if (paramInt == 0) {
        str = this.mCurrentAccount.name;
      }
    }
    int k;
    do
    {
      do
      {
        return str;
        paramInt--;
        if (this.mAccountListExpanded) {
          break;
        }
        str = null;
      } while (paramInt == 0);
      int i = paramInt - 1;
      int j = this.mPrimaryActions.size();
      if (i < j) {
        return this.mPrimaryActions.get(i);
      }
      k = i - j;
      if (this.mShowDownloadOnlyToggle)
      {
        if (k == 0) {
          return this.mDownloadSwitchConfig;
        }
        k--;
      }
      str = null;
    } while (k == 0);
    int m = k - 1;
    return this.mSecondaryActions.get(m);
    return this.mNonCurrentAccounts[paramInt];
  }
  
  public final long getItemId(int paramInt)
  {
    return paramInt;
  }
  
  public final int getItemViewType(int paramInt)
  {
    int i = 6;
    if (this.mHasAccounts) {
      if (paramInt == 0) {
        if (this.mIsMiniProfile) {
          i = 9;
        }
      }
    }
    int k;
    do
    {
      int j;
      PlayDrawerLayout.PlayDrawerPrimaryAction localPlayDrawerPrimaryAction;
      do
      {
        return i;
        return 0;
        paramInt--;
        if (this.mAccountListExpanded) {
          break label162;
        }
        if (paramInt == 0) {
          return 2;
        }
        j = paramInt - 1;
        if (j >= this.mPrimaryActions.size()) {
          break;
        }
        localPlayDrawerPrimaryAction = (PlayDrawerLayout.PlayDrawerPrimaryAction)this.mPrimaryActions.get(j);
      } while (localPlayDrawerPrimaryAction.isSeparator);
      if ((this.mShowDownloadOnlyToggle) && (this.mDownloadOnlyEnabled) && (!localPlayDrawerPrimaryAction.isAvailableInDownloadOnly)) {
        return 5;
      }
      if (localPlayDrawerPrimaryAction.isActive) {
        return 3;
      }
      return 4;
      k = j - this.mPrimaryActions.size();
    } while (k == 0);
    int m = k - 1;
    if ((this.mShowDownloadOnlyToggle) && (m == 0)) {
      return 8;
    }
    return 7;
    label162:
    if (this.mIsMiniProfile) {
      return 10;
    }
    return 1;
  }
  
  public final View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    int i = getItemViewType(paramInt);
    Object localObject = getItem(paramInt);
    switch (i)
    {
    default: 
      throw new UnsupportedOperationException("View type " + i + " not supported");
    case 0: 
      View localView5;
      final PlayDrawerProfileInfoView localPlayDrawerProfileInfoView;
      final Account localAccount4;
      final String str3;
      FifeImageView localFifeImageView;
      if (paramView != null)
      {
        localView5 = paramView;
        localPlayDrawerProfileInfoView = (PlayDrawerProfileInfoView)localView5;
        this.mProfileContainerPosition = paramInt;
        localAccount4 = this.mCurrentAccount;
        str3 = this.mCurrentAccount.name;
        localPlayDrawerProfileInfoView.configure(localAccount4, this.mNonCurrentAccounts, this.mAccountDocV2s, this.mBitmapLoader);
        boolean bool = this.mCurrentAvatarClickable;
        localPlayDrawerProfileInfoView.mProfileAvatarImage.setEnabled(bool);
        localFifeImageView = localPlayDrawerProfileInfoView.mProfileAvatarImage;
        if (!bool) {
          break label344;
        }
      }
      for (int m = 1;; m = 2)
      {
        ViewCompat.setImportantForAccessibility(localFifeImageView, m);
        this.mPlayDfeApiProvider.getPlayDfeApi(localAccount4).getPlusProfile(new Response.Listener()new Response.ErrorListener {}, new Response.ErrorListener()
        {
          public final void onErrorResponse(VolleyError paramAnonymousVolleyError) {}
        }, true);
        for (int n = 0; n < this.mNonCurrentAccounts.length; n++)
        {
          Account localAccount5 = this.mNonCurrentAccounts[n];
          final String str4 = localAccount5.name;
          if (!this.mIsAccountDocLoaded.contains(str4)) {
            this.mPlayDfeApiProvider.getPlayDfeApi(localAccount5).getPlusProfile(new Response.Listener()new Response.ErrorListener {}, new Response.ErrorListener()
            {
              public final void onErrorResponse(VolleyError paramAnonymousVolleyError) {}
            }, true);
          }
        }
        localView5 = this.mInflater.inflate(R.layout.play_drawer_profile_info, paramViewGroup, false);
        break;
      }
      localPlayDrawerProfileInfoView.setAccountListExpanded(this.mAccountListExpanded);
      localPlayDrawerProfileInfoView.mOnAvatarClickedListener = new PlayDrawerProfileInfoView.OnAvatarClickedListener()
      {
        public final void onAvatarClicked(Account paramAnonymousAccount)
        {
          if (paramAnonymousAccount == PlayDrawerAdapter.this.mCurrentAccount)
          {
            boolean bool = PlayDrawerAdapter.this.mIsAccountDocLoaded.contains(PlayDrawerAdapter.this.mCurrentAccount.name);
            if (PlayDrawerAdapter.this.mPlayDrawerContentClickListener.onCurrentAccountClicked(bool, (DocV2)PlayDrawerAdapter.this.mAccountDocV2s.get(PlayDrawerAdapter.this.mCurrentAccount.name))) {
              PlayDrawerAdapter.this.mPlayDrawerLayout.closeDrawer();
            }
            return;
          }
          PlayDrawerAdapter.this.mPlayDrawerContentClickListener.onSecondaryAccountClicked(paramAnonymousAccount.name);
          PlayDrawerAdapter.this.mPlayDrawerLayout.closeDrawer();
        }
      };
      if (this.mNonCurrentAccounts.length > 0)
      {
        localPlayDrawerProfileInfoView.setAccountListEnabled(true);
        localPlayDrawerProfileInfoView.setAccountTogglerListener(new View.OnClickListener()
        {
          public final void onClick(View paramAnonymousView)
          {
            PlayDrawerLayout.PlayDrawerContentClickListener localPlayDrawerContentClickListener = PlayDrawerAdapter.this.mPlayDrawerContentClickListener;
            if (!PlayDrawerAdapter.this.mAccountListExpanded) {}
            for (boolean bool = true;; bool = false)
            {
              localPlayDrawerContentClickListener.onAccountListToggleButtonClicked(bool);
              PlayDrawerAdapter.this.toggleAccountsList();
              return;
            }
          }
        });
        return localPlayDrawerProfileInfoView;
      }
      localPlayDrawerProfileInfoView.setAccountListEnabled(false);
      localPlayDrawerProfileInfoView.setAccountTogglerListener(null);
      return localPlayDrawerProfileInfoView;
    case 1: 
      Account localAccount3 = (Account)localObject;
      View localView4;
      PlayDrawerAccountRow localPlayDrawerAccountRow;
      final String str2;
      DocV2 localDocV2;
      BitmapLoader localBitmapLoader;
      if (paramView != null)
      {
        localView4 = paramView;
        localPlayDrawerAccountRow = (PlayDrawerAccountRow)localView4;
        str2 = localAccount3.name;
        localDocV2 = (DocV2)this.mAccountDocV2s.get(str2);
        localBitmapLoader = this.mBitmapLoader;
        localPlayDrawerAccountRow.mAccountName.setText(str2);
        localPlayDrawerAccountRow.setContentDescription(localPlayDrawerAccountRow.getResources().getString(R.string.play_drawer_content_description_switch_account, new Object[] { str2 }));
        if (localDocV2 != null) {
          break label564;
        }
        localPlayDrawerAccountRow.mAvatar.setLocalImageBitmap(BitmapFactory.decodeResource(localPlayDrawerAccountRow.getResources(), R.drawable.ic_profile_none));
      }
      for (;;)
      {
        localPlayDrawerAccountRow.setOnClickListener(new View.OnClickListener()
        {
          public final void onClick(View paramAnonymousView)
          {
            PlayDrawerAdapter.this.mPlayDrawerContentClickListener.onSecondaryAccountClicked(str2);
            PlayDrawerAdapter.this.mPlayDrawerLayout.closeDrawer();
          }
        });
        return localPlayDrawerAccountRow;
        localView4 = this.mInflater.inflate(R.layout.play_drawer_account_row, paramViewGroup, false);
        break;
        Common.Image localImage = DocV2Utils.getFirstImageOfType(localDocV2, 4);
        localPlayDrawerAccountRow.mAvatar.setImage(localImage.imageUrl, localImage.supportsFifeUrlOptions, localBitmapLoader);
      }
    case 10: 
      Account localAccount2 = (Account)localObject;
      if (paramView != null) {}
      for (View localView3 = paramView;; localView3 = this.mInflater.inflate(R.layout.play_drawer_mini_account_row, paramViewGroup, false))
      {
        PlayDrawerMiniAccountRow localPlayDrawerMiniAccountRow = (PlayDrawerMiniAccountRow)localView3;
        final String str1 = localAccount2.name;
        localPlayDrawerMiniAccountRow.mAccountName.setText(str1);
        localPlayDrawerMiniAccountRow.setContentDescription(localPlayDrawerMiniAccountRow.getResources().getString(R.string.play_drawer_content_description_switch_account, new Object[] { str1 }));
        localPlayDrawerMiniAccountRow.setOnClickListener(new View.OnClickListener()
        {
          public final void onClick(View paramAnonymousView)
          {
            PlayDrawerAdapter.this.mPlayDrawerContentClickListener.onSecondaryAccountClicked(str1);
            PlayDrawerAdapter.this.mPlayDrawerLayout.closeDrawer();
          }
        });
        return localPlayDrawerMiniAccountRow;
      }
    case 2: 
      if (paramView != null) {
        return paramView;
      }
      return this.mInflater.inflate(R.layout.play_drawer_primary_actions_top_spacing, paramViewGroup, false);
    case 3: 
      return getPrimaryActionView(paramView, paramViewGroup, (PlayDrawerLayout.PlayDrawerPrimaryAction)localObject, true, false);
    case 4: 
      return getPrimaryActionView(paramView, paramViewGroup, (PlayDrawerLayout.PlayDrawerPrimaryAction)localObject, false, false);
    case 5: 
      return getPrimaryActionView(paramView, paramViewGroup, (PlayDrawerLayout.PlayDrawerPrimaryAction)localObject, false, true);
    case 6: 
      if (paramView != null) {
        return paramView;
      }
      return this.mInflater.inflate(R.layout.play_drawer_secondary_actions_top_separator, paramViewGroup, false);
    case 8: 
      PlayDrawerDownloadSwitchRow localPlayDrawerDownloadSwitchRow;
      if (paramView == null)
      {
        localPlayDrawerDownloadSwitchRow = (PlayDrawerDownloadSwitchRow)this.mInflater.inflate(R.layout.play_drawer_download_toggle, paramViewGroup, false);
        PlayDrawerLayout.PlayDrawerDownloadSwitchConfig localPlayDrawerDownloadSwitchConfig = this.mDownloadSwitchConfig;
        localPlayDrawerDownloadSwitchRow.mCheckedTextColor = localPlayDrawerDownloadSwitchConfig.checkedTextColor;
        int j = localPlayDrawerDownloadSwitchConfig.thumbDrawableId;
        int k = localPlayDrawerDownloadSwitchConfig.trackDrawableId;
        if (PlayDrawerDownloadSwitchRow.SUPPORTS_STYLED_SWITCH)
        {
          if (k != -1) {
            localPlayDrawerDownloadSwitchRow.mSwitch.setTrackResource(k);
          }
          if (j != -1) {
            localPlayDrawerDownloadSwitchRow.mSwitch.setThumbResource(j);
          }
          localPlayDrawerDownloadSwitchRow.mSwitch.setContentDescription(localPlayDrawerDownloadSwitchConfig.actionText);
        }
        localPlayDrawerDownloadSwitchRow.mActionTextView.setText(localPlayDrawerDownloadSwitchConfig.actionText);
        TextView localTextView2 = localPlayDrawerDownloadSwitchRow.mActionTextView;
        PlayUtils.useLtr(localPlayDrawerDownloadSwitchRow.getContext());
        localTextView2.setGravity(8388627);
        localPlayDrawerDownloadSwitchRow.mListener = new PlayDrawerDownloadSwitchRow.OnCheckedChangeListener()
        {
          public final void onCheckedChanged$77cff6e2(boolean paramAnonymousBoolean)
          {
            PlayDrawerAdapter.access$1002(PlayDrawerAdapter.this, paramAnonymousBoolean);
            PlayDrawerAdapter.this.notifyDataSetChanged();
          }
        };
      }
      for (;;)
      {
        localPlayDrawerDownloadSwitchRow.setCheckedNoCallbacks(this.mDownloadOnlyEnabled);
        return localPlayDrawerDownloadSwitchRow;
        localPlayDrawerDownloadSwitchRow = (PlayDrawerDownloadSwitchRow)paramView;
      }
    case 7: 
      label344:
      final PlayDrawerLayout.PlayDrawerSecondaryAction localPlayDrawerSecondaryAction = (PlayDrawerLayout.PlayDrawerSecondaryAction)localObject;
      label564:
      if (paramView != null) {}
      for (View localView2 = paramView;; localView2 = this.mInflater.inflate(R.layout.play_drawer_secondary_action, paramViewGroup, false))
      {
        TextView localTextView1 = (TextView)localView2;
        localTextView1.setText(localPlayDrawerSecondaryAction.actionText);
        localTextView1.setOnClickListener(new View.OnClickListener()
        {
          public final void onClick(View paramAnonymousView)
          {
            PlayDrawerAdapter.this.mPlayDrawerContentClickListener.onSecondaryActionClicked(localPlayDrawerSecondaryAction);
            PlayDrawerAdapter.this.mPlayDrawerLayout.closeDrawer();
          }
        });
        PlayUtils.useLtr(this.mContext);
        localTextView1.setGravity(8388627);
        return localTextView1;
      }
    }
    if (paramView != null) {}
    PlayDrawerMiniProfileInfoView localPlayDrawerMiniProfileInfoView;
    for (View localView1 = paramView;; localView1 = this.mInflater.inflate(R.layout.play_drawer_mini_profile_info_view, paramViewGroup, false))
    {
      localPlayDrawerMiniProfileInfoView = (PlayDrawerMiniProfileInfoView)localView1;
      this.mProfileContainerPosition = paramInt;
      localPlayDrawerMiniProfileInfoView.setAccountListExpanded(this.mAccountListExpanded);
      Account localAccount1 = this.mCurrentAccount;
      localPlayDrawerMiniProfileInfoView.mDisplayName.setText(localAccount1.name);
      if (this.mNonCurrentAccounts.length <= 0) {
        break;
      }
      localPlayDrawerMiniProfileInfoView.setAccountListEnabled(true);
      localPlayDrawerMiniProfileInfoView.setAccountTogglerListener(new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          PlayDrawerLayout.PlayDrawerContentClickListener localPlayDrawerContentClickListener = PlayDrawerAdapter.this.mPlayDrawerContentClickListener;
          if (!PlayDrawerAdapter.this.mAccountListExpanded) {}
          for (boolean bool = true;; bool = false)
          {
            localPlayDrawerContentClickListener.onAccountListToggleButtonClicked(bool);
            PlayDrawerAdapter.this.toggleAccountsList();
            return;
          }
        }
      });
      return localPlayDrawerMiniProfileInfoView;
    }
    localPlayDrawerMiniProfileInfoView.setAccountListEnabled(false);
    localPlayDrawerMiniProfileInfoView.setAccountTogglerListener(null);
    return localPlayDrawerMiniProfileInfoView;
  }
  
  public final int getViewTypeCount()
  {
    return 11;
  }
  
  public final boolean hasStableIds()
  {
    return false;
  }
  
  public final boolean isEnabled(int paramInt)
  {
    switch (getItemViewType(paramInt))
    {
    default: 
      return true;
    }
    return false;
  }
}



/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar

 * Qualified Name:     com.google.android.play.drawer.PlayDrawerAdapter

 * JD-Core Version:    0.7.0.1

 */