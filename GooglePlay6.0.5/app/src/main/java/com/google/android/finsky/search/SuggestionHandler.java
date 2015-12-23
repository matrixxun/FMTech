package com.google.android.finsky.search;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.Uri.Builder;
import android.support.v4.content.ContextCompat;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.protos.Link;
import com.google.android.finsky.utils.DocUtils;
import com.google.android.finsky.utils.IntentUtils;
import com.google.android.play.search.PlaySearchSuggestionModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class SuggestionHandler
{
  private static final Map<String, Drawable> sBackgrounds = new HashMap();
  private final Set<String> mAlreadyAddedSuggestions = new HashSet();
  private final Context mContext;
  final List<PlaySearchSuggestionModel> mModels = new ArrayList();
  
  public SuggestionHandler(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  private Uri getBackendCanonicalIconUri(int paramInt)
  {
    if (paramInt != 3) {
      try
      {
        String str = IntentUtils.getPackageName(paramInt);
        PackageManager localPackageManager = this.mContext.getPackageManager();
        int i = ((ResolveInfo)localPackageManager.queryIntentActivities(localPackageManager.getLaunchIntentForPackage(str), 65536).get(0)).activityInfo.applicationInfo.icon;
        if (i != 0)
        {
          Uri localUri = new Uri.Builder().scheme("android.resource").authority(str).path(Integer.toString(i)).build();
          return localUri;
        }
      }
      catch (Exception localException)
      {
        return null;
      }
    }
    return null;
  }
  
  public final PlaySearchSuggestionModel addRow(String paramString1, String paramString2, Drawable paramDrawable, Common.Image paramImage, Link paramLink, byte[] paramArrayOfByte, boolean paramBoolean)
  {
    if (this.mAlreadyAddedSuggestions.contains(paramString1)) {
      return null;
    }
    String str1;
    boolean bool;
    if (paramImage != null)
    {
      str1 = paramImage.imageUrl;
      if (str1 == null) {
        break label143;
      }
      bool = paramImage.supportsFifeUrlOptions;
      label39:
      if ((str1 == null) && (paramLink != null))
      {
        Uri localUri = getBackendCanonicalIconUri(DocUtils.docidToBackend(paramString2));
        if (localUri != null) {
          str1 = localUri.toString();
        }
      }
      if ((paramImage != null) && (paramImage.hasFillColorRgb)) {
        break label149;
      }
    }
    for (Drawable localDrawable1 = null;; localDrawable1 = (Drawable)sBackgrounds.get(paramImage.fillColorRgb))
    {
      FinskySearchSuggestionModel localFinskySearchSuggestionModel = new FinskySearchSuggestionModel(paramString1, paramString2, paramDrawable, str1, bool, localDrawable1, paramLink, paramArrayOfByte, paramBoolean);
      this.mModels.add(localFinskySearchSuggestionModel);
      this.mAlreadyAddedSuggestions.add(paramString1);
      return localFinskySearchSuggestionModel;
      str1 = null;
      break;
      label143:
      bool = false;
      break label39;
      label149:
      if (!sBackgrounds.containsKey(paramImage.fillColorRgb))
      {
        Map localMap = sBackgrounds;
        String str2 = paramImage.fillColorRgb;
        int i = Color.parseColor(paramImage.fillColorRgb);
        Drawable localDrawable2 = ContextCompat.getDrawable(this.mContext, 2130838095);
        localDrawable2.mutate().setColorFilter(i, PorterDuff.Mode.MULTIPLY);
        localMap.put(str2, localDrawable2);
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.search.SuggestionHandler
 * JD-Core Version:    0.7.0.1
 */