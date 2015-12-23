package com.google.android.finsky.layout.play;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.protos.Template;
import com.google.android.finsky.protos.YoutubeVideoContainer;
import com.google.android.finsky.utils.IntentUtils;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.image.FifeImageView;
import java.util.List;

public class PlayYoutubeCardContentView
  extends FrameLayout
{
  private Context mContext;
  private ImageView mPlayIcon;
  private FifeImageView mPromoImage;
  private TextView mVideoSubTitle;
  private TextView mVideoTitle;
  
  public PlayYoutubeCardContentView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayYoutubeCardContentView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mContext = paramContext;
  }
  
  public final void bind(Document paramDocument, BitmapLoader paramBitmapLoader, final PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    Common.Image localImage = (Common.Image)paramDocument.getImages(14).get(0);
    this.mPromoImage.setImage(localImage.imageUrl, localImage.supportsFifeUrlOptions, paramBitmapLoader);
    Template localTemplate = paramDocument.getTemplate();
    if (localTemplate == null) {}
    for (YoutubeVideoContainer localYoutubeVideoContainer = null;; localYoutubeVideoContainer = localTemplate.youtubeVideoContainer)
    {
      this.mVideoTitle.setText(localYoutubeVideoContainer.videoTitle);
      this.mVideoSubTitle.setText(localYoutubeVideoContainer.videoSubtitle);
      String str = ((Common.Image)paramDocument.getImages(3).get(0)).imageUrl;
      setOnClickListener(new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          FinskyApp.get().getEventLogger().logClickEvent(2908, null, paramPlayStoreUiElementNode);
          PlayYoutubeCardContentView.this.mContext.startActivity(this.val$intent);
        }
      });
      return;
    }
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mPromoImage = ((FifeImageView)findViewById(2131755971));
    this.mPlayIcon = ((ImageView)findViewById(2131755596));
    this.mVideoTitle = ((TextView)findViewById(2131755973));
    this.mVideoSubTitle = ((TextView)findViewById(2131755974));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayYoutubeCardContentView
 * JD-Core Version:    0.7.0.1
 */