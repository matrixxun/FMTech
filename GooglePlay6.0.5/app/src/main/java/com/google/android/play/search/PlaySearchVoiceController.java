package com.google.android.play.search;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import java.util.ArrayList;
import java.util.List;

public final class PlaySearchVoiceController
  extends BroadcastReceiver
{
  static final Intent sVoiceIntent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
  private final PlaySearchController mController;
  boolean mRegistered;
  
  public PlaySearchVoiceController(PlaySearchController paramPlaySearchController)
  {
    this.mController = paramPlaySearchController;
  }
  
  public static boolean canPerformVoiceSearch(Context paramContext)
  {
    int i = paramContext.getPackageManager().queryIntentActivities(sVoiceIntent, 0).size();
    boolean bool = false;
    if (i > 0) {
      bool = true;
    }
    return bool;
  }
  
  final void cancelPendingVoiceSearch(Context paramContext)
  {
    if (this.mRegistered)
    {
      paramContext.unregisterReceiver(this);
      this.mRegistered = false;
    }
  }
  
  public final void onReceive(Context paramContext, Intent paramIntent)
  {
    ArrayList localArrayList = paramIntent.getStringArrayListExtra("android.speech.extra.RESULTS");
    if ((localArrayList != null) && (!localArrayList.isEmpty())) {
      this.mController.setQueryInternal((String)localArrayList.get(0), true);
    }
    cancelPendingVoiceSearch(paramContext);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.search.PlaySearchVoiceController
 * JD-Core Version:    0.7.0.1
 */