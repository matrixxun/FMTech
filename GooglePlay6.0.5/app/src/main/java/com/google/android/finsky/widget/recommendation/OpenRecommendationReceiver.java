package com.google.android.finsky.widget.recommendation;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.IntentUtils;
import com.google.android.finsky.widget.AdvanceFlipperReceiver;

public class OpenRecommendationReceiver
  extends BroadcastReceiver
{
  public static PendingIntent getIntent(Context paramContext, Document paramDocument, int paramInt1, int paramInt2)
  {
    Intent localIntent1 = IntentUtils.createViewDocumentIntent(paramContext, paramDocument);
    if (TextUtils.isEmpty(RecommendationsStore.getRecsWidgetUrl(paramInt1))) {
      return null;
    }
    Intent localIntent2 = new Intent(paramContext, OpenRecommendationReceiver.class);
    localIntent2.setData(Uri.parse("content://market/open-rec/" + paramInt2 + "/" + paramDocument.mDocument.docid));
    localIntent2.putExtra("viewIntent", localIntent1);
    localIntent2.putExtra("appWidgetId", paramInt2);
    return PendingIntent.getBroadcast(paramContext, 0, localIntent2, 0);
  }
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    Intent localIntent = (Intent)paramIntent.getParcelableExtra("viewIntent");
    localIntent.addFlags(268435456);
    paramContext.startActivity(localIntent);
    AdvanceFlipperReceiver.advance(paramContext, paramIntent.getIntExtra("appWidgetId", 0));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.widget.recommendation.OpenRecommendationReceiver
 * JD-Core Version:    0.7.0.1
 */