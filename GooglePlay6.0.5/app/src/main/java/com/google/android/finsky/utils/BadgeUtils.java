package com.google.android.finsky.utils;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Paint;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.view.ViewGroup;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.layout.DecoratedTextView;
import com.google.android.finsky.protos.AlbumDetails;
import com.google.android.finsky.protos.Badge;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.protos.DocDetails.DocumentDetails;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.MusicDetails;
import com.google.android.finsky.protos.TvEpisodeDetails;
import com.google.android.finsky.utils.image.ThumbnailUtils;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.image.FifeImageView;
import com.google.android.play.layout.PlayTextView;
import com.google.android.play.layout.StarRatingBar;
import java.text.NumberFormat;
import java.text.ParseException;

public final class BadgeUtils
{
  private static Boolean sHideEmptyStarsInReviews;
  private static NumberFormat sRatingFormatter;
  private static Boolean sUseCompactStarRating;
  
  static
  {
    NumberFormat localNumberFormat = NumberFormat.getNumberInstance();
    sRatingFormatter = localNumberFormat;
    localNumberFormat.setMinimumFractionDigits(1);
    sRatingFormatter.setMaximumFractionDigits(1);
  }
  
  public static void configureBadge(Badge paramBadge, BitmapLoader paramBitmapLoader, DecoratedTextView paramDecoratedTextView)
  {
    CharSequence localCharSequence = paramDecoratedTextView.getText();
    if (paramBadge != null)
    {
      int i = (int)paramDecoratedTextView.getTextSize();
      Common.Image localImage = getImage$7bb5454b(paramBadge);
      if (localImage != null)
      {
        paramDecoratedTextView.loadDecoration(paramBitmapLoader, localImage, i);
        Resources localResources = paramDecoratedTextView.getResources();
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = localCharSequence;
        arrayOfObject[1] = paramBadge.title;
        paramDecoratedTextView.setContentDescription(localResources.getString(2131361991, arrayOfObject));
        return;
      }
      paramDecoratedTextView.setContentDescription(localCharSequence);
      return;
    }
    paramDecoratedTextView.setCompoundDrawables(null, null, null, null);
    paramDecoratedTextView.setContentDescription(localCharSequence);
  }
  
  public static void configureContentRatingBadge(Document paramDocument, BitmapLoader paramBitmapLoader, ViewGroup paramViewGroup)
  {
    if ((paramDocument.mDocument.docType != 1) && (paramDocument.mDocument.docType != 6))
    {
      paramViewGroup.setVisibility(8);
      return;
    }
    Badge localBadge = paramDocument.getBadgeForContentRating();
    if (localBadge == null)
    {
      paramViewGroup.setVisibility(8);
      return;
    }
    DecoratedTextView localDecoratedTextView = (DecoratedTextView)paramViewGroup.findViewById(2131755400);
    if (localBadge.hasTextInTitleSection)
    {
      localDecoratedTextView.setText(localBadge.textInTitleSection);
      localDecoratedTextView.setVisibility(0);
    }
    FifeImageView localFifeImageView;
    for (;;)
    {
      localFifeImageView = (FifeImageView)paramViewGroup.findViewById(2131755399);
      if ((localBadge.image != null) && (localBadge.image.length != 0)) {
        break;
      }
      localFifeImageView.setVisibility(8);
      return;
      localDecoratedTextView.setVisibility(8);
    }
    ThumbnailUtils.adjustWidthFromImageMetadata(localFifeImageView, localBadge.image[0]);
    localFifeImageView.setImage(localBadge.image[0].imageUrl, true, paramBitmapLoader);
    localFifeImageView.setVisibility(0);
  }
  
  public static void configureCreatorBadge(Document paramDocument, BitmapLoader paramBitmapLoader, DecoratedTextView paramDecoratedTextView)
  {
    if (paramDocument.hasCreatorBadges()) {}
    for (Badge localBadge = paramDocument.getFirstCreatorBadge();; localBadge = null)
    {
      configureBadge(localBadge, paramBitmapLoader, paramDecoratedTextView);
      return;
    }
  }
  
  public static void configureRatingItemSection(Document paramDocument, StarRatingBar paramStarRatingBar, DecoratedTextView paramDecoratedTextView)
  {
    if (paramDecoratedTextView != null) {
      paramDecoratedTextView.setVisibility(8);
    }
    if ((paramDecoratedTextView != null) && (paramDocument.hasItemBadges()))
    {
      paramDecoratedTextView.setVisibility(0);
      Badge localBadge = paramDocument.mDocument.annotations.badgeForDoc[0];
      int j = (int)paramDecoratedTextView.getTextSize();
      Common.Image localImage = getImage$7bb5454b(localBadge);
      if (localImage != null) {
        paramDecoratedTextView.loadDecoration(FinskyApp.get().mBitmapLoader, localImage, j);
      }
      paramDecoratedTextView.setText(localBadge.title.toUpperCase());
      ColorStateList localColorStateList = paramDecoratedTextView.getResources().getColorStateList(2131689798);
      paramDecoratedTextView.setTextColor(localColorStateList);
      paramDecoratedTextView.mDrawBorder = false;
      if (paramDecoratedTextView.mDrawBorder) {
        paramDecoratedTextView.mBorderPaint.setColor(localColorStateList.getDefaultColor());
      }
      paramDecoratedTextView.invalidate();
      ViewCompat.setPaddingRelative(paramDecoratedTextView, 0, paramDecoratedTextView.getPaddingTop(), 0, paramDecoratedTextView.getPaddingBottom());
    }
    do
    {
      return;
      if ((paramDecoratedTextView != null) && ((paramDocument.hasCensoring()) || (paramDocument.hasReleaseType())))
      {
        configureTipperSticker(paramDocument, paramDecoratedTextView);
        int i = paramDecoratedTextView.getResources().getDimensionPixelSize(2131493473);
        ViewCompat.setPaddingRelative(paramDecoratedTextView, i, paramDecoratedTextView.getPaddingTop(), i, paramDecoratedTextView.getPaddingBottom());
        return;
      }
      if ((paramDecoratedTextView != null) && (paramDocument.mDocument.docType == 20))
      {
        if ((paramDocument.getTvEpisodeDetails() != null) && (!TextUtils.isEmpty(paramDocument.getTvEpisodeDetails().releaseDate)))
        {
          paramDecoratedTextView.setVisibility(0);
          paramDecoratedTextView.setContentColorId(2131689551, false);
          paramDecoratedTextView.setText(paramDocument.getTvEpisodeDetails().releaseDate);
          paramDecoratedTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }
        ViewCompat.setPaddingRelative(paramDecoratedTextView, 0, paramDecoratedTextView.getPaddingTop(), 0, paramDecoratedTextView.getPaddingBottom());
        return;
      }
    } while (paramStarRatingBar == null);
    paramStarRatingBar.setCompactMode(shouldUseCompactStarRating());
    if ((paramDocument.hasRating()) && (paramDocument.getRatingCount() > 0L))
    {
      paramStarRatingBar.setRating(paramDocument.getStarRating());
      paramStarRatingBar.setVisibility(0);
      return;
    }
    paramStarRatingBar.setVisibility(4);
  }
  
  public static void configureTipperSticker(Document paramDocument, PlayTextView paramPlayTextView)
  {
    int i = -1;
    int j = 2131689738;
    if (paramDocument.hasCensoring()) {}
    switch (paramDocument.mDocument.details.albumDetails.details.censoring)
    {
    default: 
      if ((i == -1) && (paramDocument.hasReleaseType())) {
        switch (paramDocument.mDocument.details.albumDetails.details.releaseType[0])
        {
        }
      }
      break;
    }
    for (;;)
    {
      if (i < 0) {
        break label186;
      }
      paramPlayTextView.setVisibility(0);
      paramPlayTextView.setText(paramPlayTextView.getResources().getString(i).toUpperCase());
      paramPlayTextView.setContentColorId(j, true);
      paramPlayTextView.setCompoundDrawables(null, null, null, null);
      return;
      i = 2131362790;
      j = 2131689739;
      break;
      i = 2131362789;
      break;
      i = 2131362788;
      continue;
      i = 2131362791;
      continue;
      i = 2131362792;
    }
    label186:
    paramPlayTextView.setVisibility(8);
  }
  
  public static String formatRating(float paramFloat)
  {
    return sRatingFormatter.format(paramFloat);
  }
  
  public static Common.Image getImage$7bb5454b(Badge paramBadge)
  {
    for (Common.Image localImage : paramBadge.image) {
      if (localImage.imageType == 6) {
        return localImage;
      }
    }
    return null;
  }
  
  public static float roundRating(float paramFloat)
  {
    String str = formatRating(paramFloat);
    try
    {
      float f = sRatingFormatter.parse(str).floatValue();
      return f;
    }
    catch (ParseException localParseException)
    {
      FinskyLog.wtf("Cannot parse %s. Exception %s", new Object[] { str, localParseException });
    }
    return paramFloat;
  }
  
  public static boolean shouldHideEmptyStarsInReviews()
  {
    if (sHideEmptyStarsInReviews == null) {
      sHideEmptyStarsInReviews = Boolean.valueOf(FinskyApp.get().getExperiments().isEnabled(12603097L));
    }
    return sHideEmptyStarsInReviews.booleanValue();
  }
  
  public static boolean shouldUseCompactStarRating()
  {
    if (sUseCompactStarRating == null) {
      sUseCompactStarRating = Boolean.valueOf(FinskyApp.get().getExperiments().isEnabled(12602623L));
    }
    return sUseCompactStarRating.booleanValue();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.BadgeUtils
 * JD-Core Version:    0.7.0.1
 */