package com.google.android.finsky.detailspage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.View;
import com.google.android.finsky.api.model.DfeDetails;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.CorpusResourceUtils;
import com.google.android.play.image.AvatarCropTransformation;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.image.FifeImageView;
import com.google.android.play.layout.PlayTextView;
import java.util.List;

public class AvatarTitleModule
  extends FinskyModule<Data>
  implements DisplayDuringTransition, ThumbnailTransitionParticipant
{
  public final void bindModule(boolean paramBoolean, Document paramDocument1, DfeDetails paramDfeDetails1, Document paramDocument2, DfeDetails paramDfeDetails2)
  {
    int i;
    Data localData;
    switch (paramDocument1.mDocument.docType)
    {
    default: 
      i = 0;
      if (i != 0)
      {
        this.mModuleData = new Data();
        ((Data)this.mModuleData).title = paramDocument1.mDocument.title;
        localData = (Data)this.mModuleData;
        if (!paramDocument1.hasImages(4)) {
          break label152;
        }
      }
      break;
    }
    label152:
    for (Common.Image localImage = (Common.Image)paramDocument1.getImages(4).get(0);; localImage = null)
    {
      localData.avatarImage = localImage;
      ((Data)this.mModuleData).avatarContentDescription = CorpusResourceUtils.getItemThumbnailContentDescription(paramDocument1, this.mContext.getResources());
      return;
      i = 1;
      break;
    }
  }
  
  public final void bindThumbnailAtTransitionEnd()
  {
    if (((Data)this.mModuleData).avatarImage == null) {
      return;
    }
    AvatarTitleModuleLayout localAvatarTitleModuleLayout = (AvatarTitleModuleLayout)this.mModuleViewHolder.itemView;
    Common.Image localImage = ((Data)this.mModuleData).avatarImage;
    BitmapLoader localBitmapLoader = this.mBitmapLoader;
    localAvatarTitleModuleLayout.mAvatar.setBitmapTransformation(AvatarCropTransformation.getFullAvatarCrop(localAvatarTitleModuleLayout.getResources()));
    localAvatarTitleModuleLayout.mAvatar.setImage(localImage.imageUrl, localImage.supportsFifeUrlOptions, localBitmapLoader);
  }
  
  public final void bindThumbnailAtTransitionStart(Bitmap paramBitmap)
  {
    AvatarTitleModuleLayout localAvatarTitleModuleLayout = (AvatarTitleModuleLayout)this.mModuleViewHolder.itemView;
    localAvatarTitleModuleLayout.mAvatar.setUseCachedPlaceholder(false);
    localAvatarTitleModuleLayout.mAvatar.setToFadeInAfterLoad(false);
    localAvatarTitleModuleLayout.mAvatar.setBitmapTransformation(null);
    localAvatarTitleModuleLayout.mAvatar.setImageBitmap(paramBitmap);
    localAvatarTitleModuleLayout.mAvatar.setDefaultDrawable(new BitmapDrawable(localAvatarTitleModuleLayout.getResources(), paramBitmap));
  }
  
  public final void bindView(View paramView)
  {
    AvatarTitleModuleLayout localAvatarTitleModuleLayout = (AvatarTitleModuleLayout)paramView;
    String str1 = ((Data)this.mModuleData).title;
    Common.Image localImage = ((Data)this.mModuleData).avatarImage;
    String str2 = this.mRevealTransitionCoverName;
    String str3 = ((Data)this.mModuleData).avatarContentDescription;
    BitmapLoader localBitmapLoader = this.mBitmapLoader;
    boolean bool1 = this.mIsRevealTransitionRunning;
    localAvatarTitleModuleLayout.mTitle.setText(str1);
    localAvatarTitleModuleLayout.mTitle.setSelected(true);
    boolean bool2;
    if (localImage != null) {
      if (!TextUtils.isEmpty(str2))
      {
        bool2 = true;
        localAvatarTitleModuleLayout.mAvatarHasTransitionName = bool2;
        if ((NavigationManager.areTransitionsEnabled()) && (localAvatarTitleModuleLayout.mAvatarHasTransitionName)) {
          localAvatarTitleModuleLayout.mAvatar.setTransitionName(str2);
        }
        if (!bool1)
        {
          localAvatarTitleModuleLayout.mAvatar.setBitmapTransformation(AvatarCropTransformation.getFullAvatarCrop(localAvatarTitleModuleLayout.getResources()));
          localAvatarTitleModuleLayout.mAvatar.setImage(localImage.imageUrl, localImage.supportsFifeUrlOptions, localBitmapLoader);
        }
        localAvatarTitleModuleLayout.mAvatar.setContentDescription(str3);
        localAvatarTitleModuleLayout.mAvatar.setVisibility(0);
      }
    }
    for (;;)
    {
      localAvatarTitleModuleLayout.mAvatar.setContentDescription(str3);
      return;
      bool2 = false;
      break;
      localAvatarTitleModuleLayout.mAvatar.setVisibility(4);
    }
  }
  
  public final int getLayoutResId()
  {
    return 2130968622;
  }
  
  public final boolean readyForDisplay()
  {
    return this.mModuleData != null;
  }
  
  protected static final class Data
    extends FinskyModule.ModuleData
  {
    String avatarContentDescription;
    Common.Image avatarImage;
    String title;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.AvatarTitleModule
 * JD-Core Version:    0.7.0.1
 */