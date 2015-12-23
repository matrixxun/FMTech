package com.google.android.finsky.layout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.play.image.AvatarCropTransformation;
import com.google.android.play.image.BitmapTransformation;
import com.google.android.play.image.FifeImageView;

public class DetailsTitleCreatorBlock
  extends RelativeLayout
{
  public TextView mCreatorDate;
  public FifeImageView mCreatorImage;
  public DecoratedTextView mCreatorTitle;
  
  public DetailsTitleCreatorBlock(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public DetailsTitleCreatorBlock(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mCreatorImage = ((FifeImageView)findViewById(2131755428));
    this.mCreatorImage.setBitmapTransformation(ArtistAvatarTransformation.INSTANCE);
    this.mCreatorTitle = ((DecoratedTextView)findViewById(2131755429));
    this.mCreatorDate = ((TextView)findViewById(2131755430));
  }
  
  private static final class ArtistAvatarTransformation
    implements BitmapTransformation
  {
    private static ArtistAvatarTransformation INSTANCE = new ArtistAvatarTransformation();
    
    public final void drawFocusedOverlay(Canvas paramCanvas, int paramInt1, int paramInt2) {}
    
    public final void drawPressedOverlay(Canvas paramCanvas, int paramInt1, int paramInt2) {}
    
    public final int getTransformationInset(int paramInt1, int paramInt2)
    {
      return AvatarCropTransformation.getFullAvatarCrop(FinskyApp.get().getResources()).getTransformationInset(paramInt1, paramInt2);
    }
    
    public final Bitmap transform(Bitmap paramBitmap, int paramInt1, int paramInt2)
    {
      int i = paramBitmap.getWidth();
      int j = paramBitmap.getHeight();
      Bitmap localBitmap = Bitmap.createBitmap(j, j, Bitmap.Config.ARGB_8888);
      new Canvas(localBitmap).drawBitmap(paramBitmap, -(i - j) / 2, 0.0F, null);
      return AvatarCropTransformation.getFullAvatarCrop(FinskyApp.get().getResources()).transform(localBitmap, paramInt1, paramInt2);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.DetailsTitleCreatorBlock
 * JD-Core Version:    0.7.0.1
 */