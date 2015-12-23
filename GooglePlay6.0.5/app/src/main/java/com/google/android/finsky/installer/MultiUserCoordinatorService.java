package com.google.android.finsky.installer;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.os.UserHandle;
import com.google.android.finsky.utils.FinskyLog;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

@TargetApi(17)
public class MultiUserCoordinatorService
  extends Service
{
  private boolean DEBUG_FORCE_BUSY_WITH_DELAY = false;
  private final IMultiUserCoordinatorService.Stub mBinder;
  private String mDebugPackageJustReleased = null;
  private final boolean mIsConcurrentMode;
  private final HashMap<UserHandle, IMultiUserCoordinatorServiceListener> mListeners = new HashMap();
  private final HashMap<String, UserHandle> mPackageOwners = new HashMap();
  
  public MultiUserCoordinatorService()
  {
    boolean bool1;
    if (Build.VERSION.SDK_INT <= 22)
    {
      boolean bool2 = Build.VERSION.CODENAME.equals("MNC");
      bool1 = false;
      if (!bool2) {}
    }
    else
    {
      bool1 = true;
    }
    this.mIsConcurrentMode = bool1;
    this.mBinder = new IMultiUserCoordinatorService.Stub()
    {
      private void notifyReleased(String paramAnonymousString)
      {
        synchronized (MultiUserCoordinatorService.this.mListeners)
        {
          Iterator localIterator = MultiUserCoordinatorService.this.mListeners.entrySet().iterator();
          for (;;)
          {
            if (localIterator.hasNext())
            {
              Map.Entry localEntry = (Map.Entry)localIterator.next();
              try
              {
                ((IMultiUserCoordinatorServiceListener)localEntry.getValue()).packageReleased(paramAnonymousString);
              }
              catch (RemoteException localRemoteException)
              {
                Object[] arrayOfObject = new Object[1];
                arrayOfObject[0] = localEntry.getKey();
                FinskyLog.d("Could not notify listener for user %s", arrayOfObject);
              }
            }
          }
        }
      }
      
      private void notifyTaken(String paramAnonymousString)
      {
        synchronized (MultiUserCoordinatorService.this.mListeners)
        {
          Iterator localIterator = MultiUserCoordinatorService.this.mListeners.entrySet().iterator();
          for (;;)
          {
            if (localIterator.hasNext())
            {
              Map.Entry localEntry = (Map.Entry)localIterator.next();
              try
              {
                ((IMultiUserCoordinatorServiceListener)localEntry.getValue()).packageAcquired(paramAnonymousString);
              }
              catch (RemoteException localRemoteException)
              {
                Object[] arrayOfObject = new Object[1];
                arrayOfObject[0] = localEntry.getKey();
                FinskyLog.d("Could not notify listener for user %s", arrayOfObject);
              }
            }
          }
        }
      }
      
      private boolean userCanAcquirePackage(UserHandle paramAnonymousUserHandle, String paramAnonymousString)
      {
        synchronized (MultiUserCoordinatorService.this.mPackageOwners)
        {
          UserHandle localUserHandle;
          if (MultiUserCoordinatorService.this.mIsConcurrentMode) {
            localUserHandle = (UserHandle)MultiUserCoordinatorService.this.mPackageOwners.get(paramAnonymousString);
          }
          while ((localUserHandle != null) && (!localUserHandle.equals(paramAnonymousUserHandle)))
          {
            Object[] arrayOfObject = new Object[4];
            arrayOfObject[0] = paramAnonymousUserHandle;
            arrayOfObject[1] = paramAnonymousString;
            arrayOfObject[2] = localUserHandle;
            arrayOfObject[3] = Boolean.valueOf(MultiUserCoordinatorService.this.mIsConcurrentMode);
            FinskyLog.d("User=%s requested=%s granted=false owned by=%s concurrent mode=%s", arrayOfObject);
            return false;
            boolean bool = MultiUserCoordinatorService.this.mPackageOwners.isEmpty();
            localUserHandle = null;
            if (!bool) {
              localUserHandle = (UserHandle)MultiUserCoordinatorService.this.mPackageOwners.values().iterator().next();
            }
          }
          return true;
        }
      }
      
      public final boolean acquirePackage(final String paramAnonymousString)
      {
        if (MultiUserCoordinatorService.this.DEBUG_FORCE_BUSY_WITH_DELAY)
        {
          if (!paramAnonymousString.equals(MultiUserCoordinatorService.this.mDebugPackageJustReleased))
          {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable()
            {
              public final void run()
              {
                MultiUserCoordinatorService.access$202(MultiUserCoordinatorService.this, paramAnonymousString);
                MultiUserCoordinatorService.1.this.notifyReleased(paramAnonymousString);
              }
            }, 15000L);
            return false;
          }
          MultiUserCoordinatorService.access$202(MultiUserCoordinatorService.this, null);
        }
        UserHandle localUserHandle = Binder.getCallingUserHandle();
        synchronized (MultiUserCoordinatorService.this.mPackageOwners)
        {
          MultiUserCoordinatorService.this.mPackageOwners.get(paramAnonymousString);
          if (!userCanAcquirePackage(localUserHandle, paramAnonymousString)) {
            return false;
          }
        }
        MultiUserCoordinatorService.this.mPackageOwners.put(paramAnonymousString, localUserHandle);
        FinskyLog.d("User=%s requested=%s granted=true", new Object[] { localUserHandle, paramAnonymousString });
        notifyTaken(paramAnonymousString);
        return true;
      }
      
      public final void registerListener(IMultiUserCoordinatorServiceListener paramAnonymousIMultiUserCoordinatorServiceListener)
      {
        UserHandle localUserHandle = Binder.getCallingUserHandle();
        localHashMap = MultiUserCoordinatorService.this.mListeners;
        if (paramAnonymousIMultiUserCoordinatorServiceListener != null) {}
        for (;;)
        {
          try
          {
            MultiUserCoordinatorService.this.mListeners.put(localUserHandle, paramAnonymousIMultiUserCoordinatorServiceListener);
            return;
          }
          finally {}
          MultiUserCoordinatorService.this.mListeners.remove(localUserHandle);
        }
      }
      
      public final void releaseAllPackages()
      {
        UserHandle localUserHandle = Binder.getCallingUserHandle();
        HashSet localHashSet = new HashSet();
        synchronized (MultiUserCoordinatorService.this.mPackageOwners)
        {
          if (MultiUserCoordinatorService.this.mPackageOwners.isEmpty()) {
            return;
          }
          Iterator localIterator1 = MultiUserCoordinatorService.this.mPackageOwners.entrySet().iterator();
          while (localIterator1.hasNext())
          {
            Map.Entry localEntry = (Map.Entry)localIterator1.next();
            if (((UserHandle)localEntry.getValue()).equals(localUserHandle))
            {
              String str = (String)localEntry.getKey();
              FinskyLog.w("User=%s removed=%s", new Object[] { localUserHandle, str });
              localIterator1.remove();
              localHashSet.add(str);
            }
          }
        }
        Iterator localIterator2 = localHashSet.iterator();
        while (localIterator2.hasNext()) {
          notifyReleased((String)localIterator2.next());
        }
      }
      
      public final void releasePackage(String paramAnonymousString)
      {
        UserHandle localUserHandle1 = Binder.getCallingUserHandle();
        for (int i = 1;; i = 0)
        {
          UserHandle localUserHandle2;
          synchronized (MultiUserCoordinatorService.this.mPackageOwners)
          {
            localUserHandle2 = (UserHandle)MultiUserCoordinatorService.this.mPackageOwners.get(paramAnonymousString);
            if (localUserHandle2 == null)
            {
              FinskyLog.w("User=%s released=%s *** was not previously acquired", new Object[] { localUserHandle1, paramAnonymousString });
              if (i != 0) {
                notifyReleased(paramAnonymousString);
              }
              return;
            }
            if (localUserHandle2.equals(localUserHandle1))
            {
              MultiUserCoordinatorService.this.mPackageOwners.remove(paramAnonymousString);
              FinskyLog.d("User=%s released=%s", new Object[] { localUserHandle1, paramAnonymousString });
            }
          }
          FinskyLog.w("User=%s released=%s *** owned by=%s", new Object[] { localUserHandle1, paramAnonymousString, localUserHandle2 });
        }
      }
    };
  }
  
  public static Intent createBindIntent(Context paramContext)
  {
    Intent localIntent = new Intent();
    localIntent.setComponent(new ComponentName(paramContext, MultiUserCoordinatorService.class));
    return localIntent;
  }
  
  public IBinder onBind(Intent paramIntent)
  {
    return this.mBinder;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.installer.MultiUserCoordinatorService
 * JD-Core Version:    0.7.0.1
 */