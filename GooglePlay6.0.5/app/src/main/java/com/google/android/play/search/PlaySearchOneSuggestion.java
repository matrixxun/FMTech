package com.google.android.play.search;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.play.R.drawable;
import com.google.android.play.R.id;
import com.google.android.play.R.style;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.image.FifeImageView;
import com.google.android.play.image.FifeImageView.OnLoadedListener;

public class PlaySearchOneSuggestion
  extends RelativeLayout
{
  private Drawable mDefaultIconDrawable;
  private FifeImageView mIcon;
  private TextView mSuggestText;
  private final SuggestionFormatter mSuggestionFormatter;
  
  public PlaySearchOneSuggestion(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlaySearchOneSuggestion(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public PlaySearchOneSuggestion(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    this.mSuggestionFormatter = new LevenshteinSuggestionFormatter(paramContext);
  }
  
  public final void bindSuggestion(final PlaySearchSuggestionModel paramPlaySearchSuggestionModel, BitmapLoader paramBitmapLoader, String paramString)
  {
    CharSequence localCharSequence = this.mSuggestionFormatter.formatSuggestion(paramString, paramPlaySearchSuggestionModel.displayText, R.style.PlaySearchSuggestionText_Query, R.style.PlaySearchSuggestionText_Suggested);
    this.mSuggestText.setText(localCharSequence);
    if (paramPlaySearchSuggestionModel.defaultIconDrawable != null) {}
    for (Drawable localDrawable = paramPlaySearchSuggestionModel.defaultIconDrawable;; localDrawable = this.mDefaultIconDrawable)
    {
      this.mIcon.clearImage();
      this.mIcon.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
      this.mIcon.setBackgroundDrawable(null);
      this.mIcon.setDefaultDrawable(localDrawable);
      if (paramPlaySearchSuggestionModel.iconUrl == null) {
        break label161;
      }
      Uri localUri = Uri.parse(paramPlaySearchSuggestionModel.iconUrl);
      if (!"android.resource".equals(localUri.getScheme())) {
        break;
      }
      this.mIcon.setImageURI(localUri);
      return;
    }
    if (paramBitmapLoader != null)
    {
      this.mIcon.setOnLoadedListener(new FifeImageView.OnLoadedListener()
      {
        public final void onLoaded(FifeImageView paramAnonymousFifeImageView, Bitmap paramAnonymousBitmap)
        {
          PlaySearchOneSuggestion.this.mIcon.setScaleType(ImageView.ScaleType.FIT_CENTER);
          PlaySearchOneSuggestion.this.mIcon.setBackgroundDrawable(paramPlaySearchSuggestionModel.iconBackgroundDrawable);
        }
        
        public final void onLoadedAndFadedIn(FifeImageView paramAnonymousFifeImageView) {}
      });
      this.mIcon.setImage(paramPlaySearchSuggestionModel.iconUrl, paramPlaySearchSuggestionModel.iconUrlSupportsFifeOptions, paramBitmapLoader);
      return;
    }
    label161:
    this.mIcon.setOnLoadedListener(null);
    this.mIcon.setImageDrawable(localDrawable);
  }
  
  public void onFinishInflate()
  {
    super.onFinishInflate();
    this.mIcon = ((FifeImageView)findViewById(R.id.icon));
    this.mSuggestText = ((TextView)findViewById(R.id.suggest_text));
    this.mDefaultIconDrawable = getContext().getResources().getDrawable(R.drawable.ic_search);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.search.PlaySearchOneSuggestion
 * JD-Core Version:    0.7.0.1
 */