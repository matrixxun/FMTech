package android.support.v4.app;

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.widget.RemoteViews;
import java.util.ArrayList;

public final class NotificationCompat
{
  private static final NotificationCompatImpl IMPL = new NotificationCompatImplBase();
  
  static
  {
    if (Build.VERSION.SDK_INT >= 21)
    {
      IMPL = new NotificationCompatImplApi21();
      return;
    }
    if (Build.VERSION.SDK_INT >= 20)
    {
      IMPL = new NotificationCompatImplApi20();
      return;
    }
    if (Build.VERSION.SDK_INT >= 19)
    {
      IMPL = new NotificationCompatImplKitKat();
      return;
    }
    if (Build.VERSION.SDK_INT >= 16)
    {
      IMPL = new NotificationCompatImplJellybean();
      return;
    }
    if (Build.VERSION.SDK_INT >= 14)
    {
      IMPL = new NotificationCompatImplIceCreamSandwich();
      return;
    }
    if (Build.VERSION.SDK_INT >= 11)
    {
      IMPL = new NotificationCompatImplHoneycomb();
      return;
    }
    if (Build.VERSION.SDK_INT >= 9)
    {
      IMPL = new NotificationCompatImplGingerbread();
      return;
    }
  }
  
  public static final class Action
    extends NotificationCompatBase.Action
  {
    public static final NotificationCompatBase.Action.Factory FACTORY = new NotificationCompatBase.Action.Factory() {};
    public PendingIntent actionIntent;
    public int icon;
    private final Bundle mExtras;
    private final RemoteInput[] mRemoteInputs;
    public CharSequence title;
    
    public Action(int paramInt, CharSequence paramCharSequence, PendingIntent paramPendingIntent)
    {
      this(paramInt, paramCharSequence, paramPendingIntent, new Bundle());
    }
    
    private Action(int paramInt, CharSequence paramCharSequence, PendingIntent paramPendingIntent, Bundle paramBundle)
    {
      this.icon = paramInt;
      this.title = NotificationCompat.Builder.limitCharSequenceLength(paramCharSequence);
      this.actionIntent = paramPendingIntent;
      this.mExtras = paramBundle;
      this.mRemoteInputs = null;
    }
    
    public final PendingIntent getActionIntent()
    {
      return this.actionIntent;
    }
    
    public final Bundle getExtras()
    {
      return this.mExtras;
    }
    
    public final int getIcon()
    {
      return this.icon;
    }
    
    public final CharSequence getTitle()
    {
      return this.title;
    }
  }
  
  public static final class BigPictureStyle
    extends NotificationCompat.Style
  {
    Bitmap mBigLargeIcon;
    boolean mBigLargeIconSet;
    Bitmap mPicture;
  }
  
  public static final class BigTextStyle
    extends NotificationCompat.Style
  {
    CharSequence mBigText;
    
    public final BigTextStyle bigText(CharSequence paramCharSequence)
    {
      this.mBigText = NotificationCompat.Builder.limitCharSequenceLength(paramCharSequence);
      return this;
    }
    
    public final BigTextStyle setBigContentTitle(CharSequence paramCharSequence)
    {
      this.mBigContentTitle = NotificationCompat.Builder.limitCharSequenceLength(paramCharSequence);
      return this;
    }
  }
  
  public static final class Builder
  {
    public ArrayList<NotificationCompat.Action> mActions = new ArrayList();
    public String mCategory;
    public int mColor = 0;
    public CharSequence mContentInfo;
    public PendingIntent mContentIntent;
    public CharSequence mContentText;
    public CharSequence mContentTitle;
    public Context mContext;
    Bundle mExtras;
    PendingIntent mFullScreenIntent;
    String mGroupKey;
    boolean mGroupSummary;
    public Bitmap mLargeIcon;
    public boolean mLocalOnly = false;
    public Notification mNotification = new Notification();
    public int mNumber;
    public ArrayList<String> mPeople;
    public int mPriority;
    int mProgress;
    boolean mProgressIndeterminate;
    int mProgressMax;
    Notification mPublicVersion;
    boolean mShowWhen = true;
    String mSortKey;
    public NotificationCompat.Style mStyle;
    public CharSequence mSubText;
    RemoteViews mTickerView;
    public boolean mUseChronometer;
    public int mVisibility = 0;
    
    public Builder(Context paramContext)
    {
      this.mContext = paramContext;
      this.mNotification.when = System.currentTimeMillis();
      this.mNotification.audioStreamType = -1;
      this.mPriority = 0;
      this.mPeople = new ArrayList();
    }
    
    protected static CharSequence limitCharSequenceLength(CharSequence paramCharSequence)
    {
      if (paramCharSequence == null) {}
      while (paramCharSequence.length() <= 5120) {
        return paramCharSequence;
      }
      return paramCharSequence.subSequence(0, 5120);
    }
    
    public final Notification build()
    {
      NotificationCompat.NotificationCompatImpl localNotificationCompatImpl = NotificationCompat.IMPL;
      new NotificationCompat.BuilderExtender();
      return localNotificationCompatImpl.build$ab8b522(this);
    }
    
    public final Builder setAutoCancel$7abcb88d()
    {
      setFlag(16, true);
      return this;
    }
    
    public final Builder setContentText(CharSequence paramCharSequence)
    {
      this.mContentText = limitCharSequenceLength(paramCharSequence);
      return this;
    }
    
    public final Builder setContentTitle(CharSequence paramCharSequence)
    {
      this.mContentTitle = limitCharSequenceLength(paramCharSequence);
      return this;
    }
    
    public final void setFlag(int paramInt, boolean paramBoolean)
    {
      Notification localNotification = this.mNotification;
      localNotification.flags = (paramInt | localNotification.flags);
    }
    
    public final Builder setProgress(int paramInt1, int paramInt2, boolean paramBoolean)
    {
      this.mProgressMax = paramInt1;
      this.mProgress = paramInt2;
      this.mProgressIndeterminate = paramBoolean;
      return this;
    }
    
    public final Builder setSmallIcon(int paramInt)
    {
      this.mNotification.icon = paramInt;
      return this;
    }
    
    public final Builder setStyle(NotificationCompat.Style paramStyle)
    {
      if (this.mStyle != paramStyle)
      {
        this.mStyle = paramStyle;
        if (this.mStyle != null)
        {
          NotificationCompat.Style localStyle = this.mStyle;
          if (localStyle.mBuilder != this)
          {
            localStyle.mBuilder = this;
            if (localStyle.mBuilder != null) {
              localStyle.mBuilder.setStyle(localStyle);
            }
          }
        }
      }
      return this;
    }
    
    public final Builder setTicker(CharSequence paramCharSequence)
    {
      this.mNotification.tickerText = limitCharSequenceLength(paramCharSequence);
      return this;
    }
  }
  
  protected static final class BuilderExtender {}
  
  public static final class InboxStyle
    extends NotificationCompat.Style
  {
    ArrayList<CharSequence> mTexts = new ArrayList();
  }
  
  static abstract interface NotificationCompatImpl
  {
    public abstract Notification build$ab8b522(NotificationCompat.Builder paramBuilder);
  }
  
  static class NotificationCompatImplApi20
    extends NotificationCompat.NotificationCompatImplKitKat
  {
    public Notification build$ab8b522(NotificationCompat.Builder paramBuilder)
    {
      NotificationCompatApi20.Builder localBuilder = new NotificationCompatApi20.Builder(paramBuilder.mContext, paramBuilder.mNotification, paramBuilder.mContentTitle, paramBuilder.mContentText, paramBuilder.mContentInfo, paramBuilder.mTickerView, paramBuilder.mNumber, paramBuilder.mContentIntent, paramBuilder.mFullScreenIntent, paramBuilder.mLargeIcon, paramBuilder.mProgressMax, paramBuilder.mProgress, paramBuilder.mProgressIndeterminate, paramBuilder.mShowWhen, paramBuilder.mUseChronometer, paramBuilder.mPriority, paramBuilder.mSubText, paramBuilder.mLocalOnly, paramBuilder.mPeople, paramBuilder.mExtras, paramBuilder.mGroupKey, paramBuilder.mGroupSummary, paramBuilder.mSortKey);
      NotificationCompat.access$000(localBuilder, paramBuilder.mActions);
      NotificationCompat.access$100(localBuilder, paramBuilder.mStyle);
      return localBuilder.build();
    }
  }
  
  static final class NotificationCompatImplApi21
    extends NotificationCompat.NotificationCompatImplApi20
  {
    public final Notification build$ab8b522(NotificationCompat.Builder paramBuilder)
    {
      NotificationCompatApi21.Builder localBuilder = new NotificationCompatApi21.Builder(paramBuilder.mContext, paramBuilder.mNotification, paramBuilder.mContentTitle, paramBuilder.mContentText, paramBuilder.mContentInfo, paramBuilder.mTickerView, paramBuilder.mNumber, paramBuilder.mContentIntent, paramBuilder.mFullScreenIntent, paramBuilder.mLargeIcon, paramBuilder.mProgressMax, paramBuilder.mProgress, paramBuilder.mProgressIndeterminate, paramBuilder.mShowWhen, paramBuilder.mUseChronometer, paramBuilder.mPriority, paramBuilder.mSubText, paramBuilder.mLocalOnly, paramBuilder.mCategory, paramBuilder.mPeople, paramBuilder.mExtras, paramBuilder.mColor, paramBuilder.mVisibility, paramBuilder.mPublicVersion, paramBuilder.mGroupKey, paramBuilder.mGroupSummary, paramBuilder.mSortKey);
      NotificationCompat.access$000(localBuilder, paramBuilder.mActions);
      NotificationCompat.access$100(localBuilder, paramBuilder.mStyle);
      return localBuilder.build();
    }
  }
  
  static class NotificationCompatImplBase
    implements NotificationCompat.NotificationCompatImpl
  {
    public Notification build$ab8b522(NotificationCompat.Builder paramBuilder)
    {
      Notification localNotification = paramBuilder.mNotification;
      localNotification.setLatestEventInfo(paramBuilder.mContext, paramBuilder.mContentTitle, paramBuilder.mContentText, paramBuilder.mContentIntent);
      if (paramBuilder.mPriority > 0) {
        localNotification.flags = (0x80 | localNotification.flags);
      }
      return localNotification;
    }
  }
  
  static final class NotificationCompatImplGingerbread
    extends NotificationCompat.NotificationCompatImplBase
  {
    public final Notification build$ab8b522(NotificationCompat.Builder paramBuilder)
    {
      Notification localNotification = paramBuilder.mNotification;
      localNotification.setLatestEventInfo(paramBuilder.mContext, paramBuilder.mContentTitle, paramBuilder.mContentText, paramBuilder.mContentIntent);
      Context localContext = paramBuilder.mContext;
      CharSequence localCharSequence1 = paramBuilder.mContentTitle;
      CharSequence localCharSequence2 = paramBuilder.mContentText;
      PendingIntent localPendingIntent1 = paramBuilder.mContentIntent;
      PendingIntent localPendingIntent2 = paramBuilder.mFullScreenIntent;
      localNotification.setLatestEventInfo(localContext, localCharSequence1, localCharSequence2, localPendingIntent1);
      localNotification.fullScreenIntent = localPendingIntent2;
      if (paramBuilder.mPriority > 0) {
        localNotification.flags = (0x80 | localNotification.flags);
      }
      return localNotification;
    }
  }
  
  static final class NotificationCompatImplHoneycomb
    extends NotificationCompat.NotificationCompatImplBase
  {
    public final Notification build$ab8b522(NotificationCompat.Builder paramBuilder)
    {
      Context localContext = paramBuilder.mContext;
      Notification localNotification = paramBuilder.mNotification;
      CharSequence localCharSequence1 = paramBuilder.mContentTitle;
      CharSequence localCharSequence2 = paramBuilder.mContentText;
      CharSequence localCharSequence3 = paramBuilder.mContentInfo;
      RemoteViews localRemoteViews = paramBuilder.mTickerView;
      int i = paramBuilder.mNumber;
      PendingIntent localPendingIntent1 = paramBuilder.mContentIntent;
      PendingIntent localPendingIntent2 = paramBuilder.mFullScreenIntent;
      Bitmap localBitmap = paramBuilder.mLargeIcon;
      Notification.Builder localBuilder1 = new Notification.Builder(localContext).setWhen(localNotification.when).setSmallIcon(localNotification.icon, localNotification.iconLevel).setContent(localNotification.contentView).setTicker(localNotification.tickerText, localRemoteViews).setSound(localNotification.sound, localNotification.audioStreamType).setVibrate(localNotification.vibrate).setLights(localNotification.ledARGB, localNotification.ledOnMS, localNotification.ledOffMS);
      boolean bool1;
      boolean bool2;
      label169:
      boolean bool3;
      label191:
      Notification.Builder localBuilder4;
      if ((0x2 & localNotification.flags) != 0)
      {
        bool1 = true;
        Notification.Builder localBuilder2 = localBuilder1.setOngoing(bool1);
        if ((0x8 & localNotification.flags) == 0) {
          break label277;
        }
        bool2 = true;
        Notification.Builder localBuilder3 = localBuilder2.setOnlyAlertOnce(bool2);
        if ((0x10 & localNotification.flags) == 0) {
          break label283;
        }
        bool3 = true;
        localBuilder4 = localBuilder3.setAutoCancel(bool3).setDefaults(localNotification.defaults).setContentTitle(localCharSequence1).setContentText(localCharSequence2).setContentInfo(localCharSequence3).setContentIntent(localPendingIntent1).setDeleteIntent(localNotification.deleteIntent);
        if ((0x80 & localNotification.flags) == 0) {
          break label289;
        }
      }
      label277:
      label283:
      label289:
      for (boolean bool4 = true;; bool4 = false)
      {
        return localBuilder4.setFullScreenIntent(localPendingIntent2, bool4).setLargeIcon(localBitmap).setNumber(i).getNotification();
        bool1 = false;
        break;
        bool2 = false;
        break label169;
        bool3 = false;
        break label191;
      }
    }
  }
  
  static final class NotificationCompatImplIceCreamSandwich
    extends NotificationCompat.NotificationCompatImplBase
  {
    public final Notification build$ab8b522(NotificationCompat.Builder paramBuilder)
    {
      return new NotificationCompatIceCreamSandwich.Builder(paramBuilder.mContext, paramBuilder.mNotification, paramBuilder.mContentTitle, paramBuilder.mContentText, paramBuilder.mContentInfo, paramBuilder.mTickerView, paramBuilder.mNumber, paramBuilder.mContentIntent, paramBuilder.mFullScreenIntent, paramBuilder.mLargeIcon, paramBuilder.mProgressMax, paramBuilder.mProgress, paramBuilder.mProgressIndeterminate).build();
    }
  }
  
  static class NotificationCompatImplJellybean
    extends NotificationCompat.NotificationCompatImplBase
  {
    public Notification build$ab8b522(NotificationCompat.Builder paramBuilder)
    {
      NotificationCompatJellybean.Builder localBuilder = new NotificationCompatJellybean.Builder(paramBuilder.mContext, paramBuilder.mNotification, paramBuilder.mContentTitle, paramBuilder.mContentText, paramBuilder.mContentInfo, paramBuilder.mTickerView, paramBuilder.mNumber, paramBuilder.mContentIntent, paramBuilder.mFullScreenIntent, paramBuilder.mLargeIcon, paramBuilder.mProgressMax, paramBuilder.mProgress, paramBuilder.mProgressIndeterminate, paramBuilder.mUseChronometer, paramBuilder.mPriority, paramBuilder.mSubText, paramBuilder.mLocalOnly, paramBuilder.mExtras, paramBuilder.mGroupKey, paramBuilder.mGroupSummary, paramBuilder.mSortKey);
      NotificationCompat.access$000(localBuilder, paramBuilder.mActions);
      NotificationCompat.access$100(localBuilder, paramBuilder.mStyle);
      return localBuilder.build();
    }
  }
  
  static class NotificationCompatImplKitKat
    extends NotificationCompat.NotificationCompatImplJellybean
  {
    public Notification build$ab8b522(NotificationCompat.Builder paramBuilder)
    {
      NotificationCompatKitKat.Builder localBuilder = new NotificationCompatKitKat.Builder(paramBuilder.mContext, paramBuilder.mNotification, paramBuilder.mContentTitle, paramBuilder.mContentText, paramBuilder.mContentInfo, paramBuilder.mTickerView, paramBuilder.mNumber, paramBuilder.mContentIntent, paramBuilder.mFullScreenIntent, paramBuilder.mLargeIcon, paramBuilder.mProgressMax, paramBuilder.mProgress, paramBuilder.mProgressIndeterminate, paramBuilder.mShowWhen, paramBuilder.mUseChronometer, paramBuilder.mPriority, paramBuilder.mSubText, paramBuilder.mLocalOnly, paramBuilder.mPeople, paramBuilder.mExtras, paramBuilder.mGroupKey, paramBuilder.mGroupSummary, paramBuilder.mSortKey);
      NotificationCompat.access$000(localBuilder, paramBuilder.mActions);
      NotificationCompat.access$100(localBuilder, paramBuilder.mStyle);
      return localBuilder.build();
    }
  }
  
  public static abstract class Style
  {
    CharSequence mBigContentTitle;
    NotificationCompat.Builder mBuilder;
    CharSequence mSummaryText;
    boolean mSummaryTextSet = false;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v4.app.NotificationCompat
 * JD-Core Version:    0.7.0.1
 */