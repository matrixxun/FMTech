package com.google.android.play.drawer;

import android.accounts.Account;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.protos.DocV2;
import com.google.android.play.R.color;
import com.google.android.play.R.drawable;
import com.google.android.play.R.id;
import com.google.android.play.R.string;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.image.FifeImageView;
import com.google.android.play.image.FifeImageView.OnLoadedListener;
import com.google.android.play.utils.DocV2Utils;
import java.util.Map;

class PlayDrawerProfileInfoView
  extends FrameLayout
  implements View.OnClickListener, FifeImageView.OnLoadedListener
{
  private View mAccountInfoContainer;
  private boolean mAccountListEnabled;
  private boolean mAccountListExpanded;
  private TextView mAccountName;
  private TextView mDisplayName;
  OnAvatarClickedListener mOnAvatarClickedListener;
  private Account mProfileAccount;
  FifeImageView mProfileAvatarImage;
  private FifeImageView mProfileCoverImage;
  private Account mSecondaryAccountLeft;
  private Account mSecondaryAccountRight;
  private View mSecondaryAvatarImageFrameLeft;
  private View mSecondaryAvatarImageFrameRight;
  private FifeImageView mSecondaryAvatarImageLeft;
  private FifeImageView mSecondaryAvatarImageRight;
  private ImageView mToggleAccountListButton;
  
  public PlayDrawerProfileInfoView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayDrawerProfileInfoView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  private void bindAccountToggler()
  {
    Resources localResources = getResources();
    if (this.mAccountListExpanded)
    {
      this.mToggleAccountListButton.setVisibility(0);
      this.mToggleAccountListButton.setImageResource(R.drawable.ic_up_white_16);
      this.mToggleAccountListButton.setContentDescription(localResources.getString(R.string.play_drawer_content_description_hide_account_list_button));
      return;
    }
    if (this.mAccountListEnabled)
    {
      this.mToggleAccountListButton.setVisibility(0);
      this.mToggleAccountListButton.setImageResource(R.drawable.ic_down_white_16);
      this.mToggleAccountListButton.setContentDescription(localResources.getString(R.string.play_drawer_content_description_show_account_list_button));
      return;
    }
    this.mToggleAccountListButton.setVisibility(8);
  }
  
  private void configureAvatar(FifeImageView paramFifeImageView, boolean paramBoolean, CharSequence paramCharSequence, DocV2 paramDocV2, BitmapLoader paramBitmapLoader)
  {
    if (paramBoolean) {}
    Resources localResources;
    int i;
    Object[] arrayOfObject;
    for (String str = paramCharSequence.toString();; str = localResources.getString(i, arrayOfObject))
    {
      paramFifeImageView.setContentDescription(str);
      if (paramDocV2 == null) {
        break;
      }
      Common.Image localImage = DocV2Utils.getFirstImageOfType(paramDocV2, 4);
      paramFifeImageView.setTag(null);
      paramFifeImageView.setImage(localImage.imageUrl, localImage.supportsFifeUrlOptions, paramBitmapLoader);
      return;
      localResources = getResources();
      i = R.string.play_drawer_content_description_switch_account;
      arrayOfObject = new Object[1];
      arrayOfObject[0] = paramCharSequence.toString();
    }
    paramFifeImageView.setLocalImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_profile_none));
  }
  
  public final void configure(Account paramAccount, Account[] paramArrayOfAccount, Map<String, DocV2> paramMap, BitmapLoader paramBitmapLoader)
  {
    this.mProfileAccount = paramAccount;
    Account localAccount1;
    Account localAccount2;
    label32:
    DocV2 localDocV21;
    DocV2 localDocV22;
    label81:
    DocV2 localDocV23;
    if (paramArrayOfAccount.length > 0)
    {
      localAccount1 = paramArrayOfAccount[0];
      this.mSecondaryAccountRight = localAccount1;
      if (paramArrayOfAccount.length <= 1) {
        break label277;
      }
      localAccount2 = paramArrayOfAccount[1];
      this.mSecondaryAccountLeft = localAccount2;
      localDocV21 = (DocV2)paramMap.get(this.mProfileAccount.name);
      if (this.mSecondaryAccountRight == null) {
        break label283;
      }
      localDocV22 = (DocV2)paramMap.get(this.mSecondaryAccountRight.name);
      if (this.mSecondaryAccountLeft == null) {
        break label289;
      }
      localDocV23 = (DocV2)paramMap.get(this.mSecondaryAccountLeft.name);
      label106:
      setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.play_main_background)));
      if (localDocV21 != null) {
        break label295;
      }
      this.mProfileCoverImage.setImageResource(R.drawable.bg_default_profile_art);
      this.mDisplayName.setText(paramAccount.name);
      this.mAccountName.setVisibility(8);
      label162:
      Object localObject = this.mDisplayName.getText();
      if (TextUtils.isEmpty((CharSequence)localObject)) {
        localObject = paramAccount.name;
      }
      configureAvatar(this.mProfileAvatarImage, true, (CharSequence)localObject, localDocV21, paramBitmapLoader);
      if (this.mSecondaryAccountLeft == null) {
        break label385;
      }
      this.mSecondaryAvatarImageFrameLeft.setVisibility(0);
      configureAvatar(this.mSecondaryAvatarImageLeft, false, this.mSecondaryAccountLeft.name, localDocV23, paramBitmapLoader);
    }
    for (;;)
    {
      if (this.mSecondaryAccountRight == null) {
        break label397;
      }
      this.mSecondaryAvatarImageFrameRight.setVisibility(0);
      configureAvatar(this.mSecondaryAvatarImageRight, false, this.mSecondaryAccountRight.name, localDocV22, paramBitmapLoader);
      return;
      localAccount1 = null;
      break;
      label277:
      localAccount2 = null;
      break label32;
      label283:
      localDocV22 = null;
      break label81;
      label289:
      localDocV23 = null;
      break label106;
      label295:
      this.mProfileCoverImage.setTag(null);
      Common.Image localImage = DocV2Utils.getFirstImageOfType(localDocV21, 15);
      String str = localDocV21.title;
      this.mProfileCoverImage.setOnLoadedListener(this);
      this.mProfileCoverImage.setImage(localImage.imageUrl, localImage.supportsFifeUrlOptions, paramBitmapLoader);
      if (!TextUtils.isEmpty(str)) {
        this.mDisplayName.setText(str);
      }
      this.mAccountName.setText(paramAccount.name);
      this.mAccountName.setVisibility(0);
      break label162;
      label385:
      this.mSecondaryAvatarImageFrameLeft.setVisibility(8);
    }
    label397:
    this.mSecondaryAvatarImageFrameRight.setVisibility(8);
  }
  
  protected void drawableStateChanged()
  {
    super.drawableStateChanged();
    invalidate();
  }
  
  public void onClick(View paramView)
  {
    if (this.mOnAvatarClickedListener == null) {}
    do
    {
      return;
      if (paramView == this.mProfileAvatarImage)
      {
        this.mOnAvatarClickedListener.onAvatarClicked(this.mProfileAccount);
        return;
      }
      if (paramView == this.mSecondaryAvatarImageFrameLeft)
      {
        this.mOnAvatarClickedListener.onAvatarClicked(this.mSecondaryAccountLeft);
        return;
      }
    } while (paramView != this.mSecondaryAvatarImageFrameRight);
    this.mOnAvatarClickedListener.onAvatarClicked(this.mSecondaryAccountRight);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mProfileCoverImage = ((FifeImageView)findViewById(R.id.cover_photo));
    this.mProfileAvatarImage = ((FifeImageView)findViewById(R.id.avatar));
    this.mSecondaryAvatarImageFrameLeft = findViewById(R.id.secondary_avatar_frame_left);
    this.mSecondaryAvatarImageLeft = ((FifeImageView)findViewById(R.id.secondary_avatar_left));
    this.mSecondaryAvatarImageFrameRight = findViewById(R.id.secondary_avatar_frame_right);
    this.mSecondaryAvatarImageRight = ((FifeImageView)findViewById(R.id.secondary_avatar_right));
    this.mDisplayName = ((TextView)findViewById(R.id.display_name));
    this.mAccountName = ((TextView)findViewById(R.id.account_name));
    this.mToggleAccountListButton = ((ImageView)findViewById(R.id.toggle_account_list_button));
    this.mAccountInfoContainer = findViewById(R.id.account_info_container);
    this.mProfileAvatarImage.setOnClickListener(this);
    this.mSecondaryAvatarImageFrameLeft.setOnClickListener(this);
    this.mSecondaryAvatarImageLeft.setDuplicateParentStateEnabled(true);
    this.mSecondaryAvatarImageFrameRight.setOnClickListener(this);
    this.mSecondaryAvatarImageRight.setDuplicateParentStateEnabled(true);
  }
  
  public final void onLoaded(FifeImageView paramFifeImageView, Bitmap paramBitmap) {}
  
  public final void onLoadedAndFadedIn(FifeImageView paramFifeImageView)
  {
    setBackgroundDrawable(null);
  }
  
  public final void setAccountListEnabled(boolean paramBoolean)
  {
    if (this.mAccountListEnabled != paramBoolean)
    {
      this.mAccountListEnabled = paramBoolean;
      bindAccountToggler();
      if (!paramBoolean) {
        setAccountListExpanded(false);
      }
    }
  }
  
  public final void setAccountListExpanded(boolean paramBoolean)
  {
    if (this.mAccountListExpanded != paramBoolean)
    {
      this.mAccountListExpanded = paramBoolean;
      bindAccountToggler();
    }
  }
  
  public final void setAccountTogglerListener(View.OnClickListener paramOnClickListener)
  {
    int i = ViewCompat.getPaddingStart(this.mAccountInfoContainer);
    int j = ViewCompat.getPaddingEnd(this.mAccountInfoContainer);
    int k = this.mAccountInfoContainer.getPaddingTop();
    int m = this.mAccountInfoContainer.getPaddingBottom();
    if (paramOnClickListener != null) {
      this.mAccountInfoContainer.setBackgroundResource(R.drawable.play_highlight_overlay_dark);
    }
    for (;;)
    {
      ViewCompat.setPaddingRelative(this.mAccountInfoContainer, i, k, j, m);
      this.mAccountInfoContainer.setOnClickListener(paramOnClickListener);
      return;
      this.mAccountInfoContainer.setBackgroundResource(0);
    }
  }
  
  public static abstract interface OnAvatarClickedListener
  {
    public abstract void onAvatarClicked(Account paramAccount);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.drawer.PlayDrawerProfileInfoView
 * JD-Core Version:    0.7.0.1
 */