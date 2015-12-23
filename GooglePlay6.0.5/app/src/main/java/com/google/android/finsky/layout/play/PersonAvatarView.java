package com.google.android.finsky.layout.play;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View.OnClickListener;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.protos.DocV2;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.image.FifeImageView;
import com.google.android.play.utils.DocV2Utils;

public class PersonAvatarView
  extends FifeImageView
{
  public PersonAvatarView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PersonAvatarView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public PersonAvatarView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public final void bindView(DocV2 paramDocV2, View.OnClickListener paramOnClickListener, BitmapLoader paramBitmapLoader)
  {
    Common.Image localImage = DocV2Utils.getFirstImageOfType(paramDocV2, 4);
    setImage(localImage.imageUrl, localImage.supportsFifeUrlOptions, paramBitmapLoader);
    if ((paramDocV2.hasDetailsUrl) && (paramOnClickListener != null))
    {
      setOnClickListener(paramOnClickListener);
      Resources localResources = getResources();
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = paramDocV2.title;
      setContentDescription(localResources.getString(2131362036, arrayOfObject));
      setFocusable(true);
      return;
    }
    setOnClickListener(null);
    setClickable(false);
    setContentDescription(null);
    setFocusable(false);
  }
  
  public final void bindView(DocV2 paramDocV2, NavigationManager paramNavigationManager, BitmapLoader paramBitmapLoader, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    if (paramDocV2.hasDetailsUrl) {}
    for (View.OnClickListener localOnClickListener = paramNavigationManager.getClickListener(new Document(paramDocV2), paramPlayStoreUiElementNode);; localOnClickListener = null)
    {
      bindView(paramDocV2, localOnClickListener, paramBitmapLoader);
      return;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PersonAvatarView
 * JD-Core Version:    0.7.0.1
 */