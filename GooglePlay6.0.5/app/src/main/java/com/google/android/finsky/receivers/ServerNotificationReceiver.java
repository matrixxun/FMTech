package com.google.android.finsky.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.DfeNotificationManager;
import com.google.android.finsky.protos.Notification;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Notifier;
import com.google.protobuf.nano.InvalidProtocolBufferNanoException;
import com.google.protobuf.nano.MessageNano;
import java.util.Set;

public class ServerNotificationReceiver
  extends BroadcastReceiver
{
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    if ((!paramIntent.getAction().equals("com.google.android.c2dm.intent.RECEIVE")) || (!"google.com".equals(paramIntent.getStringExtra("from"))) || (!paramIntent.getCategories().contains("SERVER_NOTIFICATION"))) {}
    for (;;)
    {
      return;
      setResultCode(-1);
      Bundle localBundle = paramIntent.getExtras();
      if (localBundle.containsKey("NOTIFICATION_PAYLOAD"))
      {
        byte[] arrayOfByte = Base64.decode(localBundle.getString("NOTIFICATION_PAYLOAD"), 11);
        if (arrayOfByte == null) {
          continue;
        }
        try
        {
          localNotification = (Notification)MessageNano.mergeFrom$1ec43da(new Notification(), arrayOfByte, arrayOfByte.length);
          if (localNotification != null)
          {
            Object[] arrayOfObject = new Object[1];
            arrayOfObject[0] = localNotification.notificationId;
            FinskyLog.d("Handling notificationId=[%s]", arrayOfObject);
            FinskyApp.get().mDfeNotificationManager.processNotification(localNotification);
            return;
          }
        }
        catch (InvalidProtocolBufferNanoException localInvalidProtocolBufferNanoException)
        {
          for (;;)
          {
            FinskyLog.e("Received download tickle with malformed notification proto data.", new Object[0]);
            Notification localNotification = null;
          }
        }
      }
    }
    FinskyLog.d("Ignoring server broadcast due to empty notification string.", new Object[0]);
    String str1 = paramIntent.getStringExtra("server_notification_message");
    String str2 = paramIntent.getStringExtra("server_dialog_message");
    if ((str1 == null) || (str2 == null))
    {
      FinskyLog.d("Could not handle old style notification.", new Object[0]);
      return;
    }
    if (paramIntent.hasExtra("server_notification_status"))
    {
      paramIntent.getStringExtra("server_notification_status");
      if (!paramIntent.hasExtra("server_notification_title")) {
        break label269;
      }
    }
    label269:
    for (String str3 = paramIntent.getStringExtra("server_notification_title");; str3 = paramContext.getString(2131362406))
    {
      FinskyApp.get().mNotificationHelper.showMessage$14e1ec6d(str3, str1);
      FinskyLog.d("Handled old style notification.", new Object[0]);
      return;
      paramContext.getString(2131362405);
      break;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.receivers.ServerNotificationReceiver
 * JD-Core Version:    0.7.0.1
 */