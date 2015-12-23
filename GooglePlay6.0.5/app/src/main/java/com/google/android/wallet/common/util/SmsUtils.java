package com.google.android.wallet.common.util;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.telephony.SmsMessage;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class SmsUtils
{
  public static String getSmsMessageBody(SmsMessage[] paramArrayOfSmsMessage)
  {
    if (paramArrayOfSmsMessage.length == 1) {
      return replaceFormFeeds(paramArrayOfSmsMessage[0].getDisplayMessageBody());
    }
    StringBuilder localStringBuilder = new StringBuilder();
    int i = paramArrayOfSmsMessage.length;
    int j = 0;
    for (;;)
    {
      SmsMessage localSmsMessage;
      if (j < i) {
        localSmsMessage = paramArrayOfSmsMessage[j];
      }
      try
      {
        localStringBuilder.append(localSmsMessage.getDisplayMessageBody());
        label49:
        j++;
        continue;
        return replaceFormFeeds(localStringBuilder.toString());
      }
      catch (NullPointerException localNullPointerException)
      {
        break label49;
      }
    }
  }
  
  public static ArrayList<ContentValues> getSmsMessages(Context paramContext, long paramLong, Pattern paramPattern)
  {
    ArrayList localArrayList = new ArrayList();
    String[] arrayOfString = { "address", "date", "body" };
    StringBuilder localStringBuilder = new StringBuilder(30);
    localStringBuilder.append("date > ").append(paramLong);
    Cursor localCursor = paramContext.getContentResolver().query(Uri.parse("content://sms/inbox"), arrayOfString, localStringBuilder.toString(), null, null);
    if (localCursor != null) {
      try
      {
        while (localCursor.moveToNext())
        {
          String str = localCursor.getString(0);
          if ((paramPattern == null) || (paramPattern.matcher(str).matches()))
          {
            ContentValues localContentValues = new ContentValues(3);
            localContentValues.put("address", str);
            localContentValues.put("date", Long.valueOf(localCursor.getLong(1)));
            localContentValues.put("body", localCursor.getString(2));
            localArrayList.add(localContentValues);
          }
        }
      }
      finally
      {
        localCursor.close();
      }
    }
    return localArrayList;
  }
  
  private static String replaceFormFeeds(String paramString)
  {
    if (paramString == null) {
      return "";
    }
    return paramString.replace('\f', '\n');
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.common.util.SmsUtils
 * JD-Core Version:    0.7.0.1
 */