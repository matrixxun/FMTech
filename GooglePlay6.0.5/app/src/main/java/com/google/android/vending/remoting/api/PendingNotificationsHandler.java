package com.google.android.vending.remoting.api;

import android.content.Context;
import com.google.android.finsky.protos.VendingProtos.PendingNotificationsProto;

public abstract interface PendingNotificationsHandler
{
  public abstract boolean handlePendingNotifications(Context paramContext, String paramString, VendingProtos.PendingNotificationsProto paramPendingNotificationsProto, boolean paramBoolean);
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.vending.remoting.api.PendingNotificationsHandler
 * JD-Core Version:    0.7.0.1
 */