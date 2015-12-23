package com.google.android.finsky.widget;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import com.android.volley.NetworkError;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.AccessRestrictedActivity;
import com.google.android.finsky.activities.SimpleAlertDialog.Listener;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.protos.Toc.CorpusMetadata;
import com.google.android.finsky.protos.Toc.TocResponse;
import com.google.android.finsky.utils.GetTocHelper.Listener;
import com.google.android.finsky.utils.RestrictedDeviceHelper;
import com.google.android.finsky.utils.RestrictedDeviceHelper.OnCompleteListener;
import com.google.android.finsky.utils.StoreTypeValidator;
import java.util.Iterator;
import java.util.List;

public abstract class TrampolineActivity
  extends FragmentActivity
  implements SimpleAlertDialog.Listener
{
  public static Intent getLaunchIntent(Context paramContext, Class<? extends TrampolineActivity> paramClass, int paramInt)
  {
    Intent localIntent = new Intent(paramContext, paramClass);
    localIntent.addFlags(268435456);
    localIntent.putExtra("appWidgetId", paramInt);
    return localIntent;
  }
  
  public static PendingIntent getPendingLaunchIntent(Context paramContext, Class<? extends TrampolineActivity> paramClass, int paramInt)
  {
    Intent localIntent = getLaunchIntent(paramContext, paramClass, -1);
    localIntent.putExtra("appWidgetId", paramInt);
    return PendingIntent.getActivity(paramContext, paramInt, localIntent, 0);
  }
  
  private void initialize(DfeToc paramDfeToc, int paramInt)
  {
    int i = paramDfeToc.getCorpusList().size();
    if (enableMultiCorpus()) {}
    for (int j = 1;; j = 0)
    {
      int k = i + j;
      if ((shouldAllowConfiguration()) && (k > 1)) {
        break;
      }
      finish(-1, "apps");
      return;
    }
    Intent localIntent = new Intent(this, WidgetConfigurationActivity.class);
    localIntent.putExtra("enableMultiCorpus", enableMultiCorpus());
    localIntent.putExtra("dfeToc", paramDfeToc);
    localIntent.putExtra("appWidgetId", paramInt);
    Iterator localIterator = paramDfeToc.getCorpusList().iterator();
    while (localIterator.hasNext())
    {
      Toc.CorpusMetadata localCorpusMetadata = (Toc.CorpusMetadata)localIterator.next();
      int m = localCorpusMetadata.backend;
      localIntent.putExtra("backend_" + m, isBackendEnabled(m));
      String str1 = "name_" + m;
      String str2 = getCorpusName(localCorpusMetadata.backend);
      if (TextUtils.isEmpty(str2)) {
        str2 = localCorpusMetadata.name;
      }
      localIntent.putExtra(str1, str2);
    }
    localIntent.putExtra("name_0", getCorpusName(0));
    localIntent.putExtra("dialog_title", getDialogTitle());
    startActivityForResult(localIntent, 1);
  }
  
  public abstract boolean enableMultiCorpus();
  
  public abstract void finish(int paramInt, String paramString);
  
  public String getCorpusName(int paramInt)
  {
    return null;
  }
  
  public int getDialogTitle()
  {
    return 2131361848;
  }
  
  public boolean isBackendEnabled(int paramInt)
  {
    return true;
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    String str = null;
    if (paramIntent != null) {
      str = WidgetUtils.translate(paramIntent.getIntExtra("backend", 3));
    }
    finish(paramInt2, str);
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (!StoreTypeValidator.isValid(this))
    {
      AccessRestrictedActivity.showInvalidStoreTypeUI(this);
      finish();
      return;
    }
    RestrictedDeviceHelper.isLimitedOrSchoolEduUser(new RestrictedDeviceHelper.OnCompleteListener()
    {
      public final void onComplete(boolean paramAnonymousBoolean)
      {
        if (paramAnonymousBoolean)
        {
          TrampolineActivity.this.setResult(0);
          AccessRestrictedActivity.showLimitedUserUI(TrampolineActivity.this);
          TrampolineActivity.this.finish();
          return;
        }
        TrampolineActivity.access$000(TrampolineActivity.this);
      }
    });
  }
  
  public final void onNegativeClick(int paramInt, Bundle paramBundle)
  {
    setResult(0);
    finish();
  }
  
  public final void onPositiveClick(int paramInt, Bundle paramBundle)
  {
    setResult(0);
    finish();
  }
  
  protected boolean shouldAllowConfiguration()
  {
    return true;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.widget.TrampolineActivity
 * JD-Core Version:    0.7.0.1
 */