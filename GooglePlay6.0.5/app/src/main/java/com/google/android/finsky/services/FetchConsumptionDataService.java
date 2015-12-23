package com.google.android.finsky.services;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.google.android.finsky.config.G;
import com.google.android.finsky.utils.BackgroundThreadFactory;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.IntentUtils;
import com.google.android.play.IUserContentService;
import com.google.android.play.IUserContentService.Stub;
import com.google.android.play.utils.config.GservicesValue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class FetchConsumptionDataService
  extends IntentService
{
  private static final ExecutorService sFetchThread = Executors.newSingleThreadExecutor(new BackgroundThreadFactory());
  
  public FetchConsumptionDataService()
  {
    super(FetchConsumptionDataService.class.getSimpleName());
  }
  
  public static void fetch(Context paramContext, int paramInt)
  {
    Intent localIntent = new Intent(paramContext, FetchConsumptionDataService.class);
    localIntent.putExtra("backendId", paramInt);
    localIntent.setAction(FetchConsumptionDataService.class.getSimpleName() + ":" + paramInt);
    PendingIntent localPendingIntent = PendingIntent.getService(paramContext, 0, localIntent, 0);
    ((AlarmManager)paramContext.getSystemService("alarm")).set(1, 500L + System.currentTimeMillis(), localPendingIntent);
  }
  
  protected void onHandleIntent(Intent paramIntent)
  {
    int i = paramIntent.getIntExtra("backendId", 0);
    String str = IntentUtils.getPackageName(i);
    Semaphore localSemaphore = new Semaphore(0);
    FinskyLog.d("Starting ServiceConnection to consumption app: %s", new Object[] { str });
    Intent localIntent = new Intent("com.google.android.play.IUserContentService.BIND");
    localIntent.setPackage(str);
    ConsumptionAppServiceConn localConsumptionAppServiceConn = new ConsumptionAppServiceConn(i, localSemaphore);
    bindService(localIntent, localConsumptionAppServiceConn, 1);
    long l = ((Long)G.consumptionAppTimeoutMs.get()).longValue();
    try
    {
      if (!localSemaphore.tryAcquire(l, TimeUnit.MILLISECONDS))
      {
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = Integer.valueOf(i);
        arrayOfObject[1] = Long.valueOf(l);
        FinskyLog.e("Service connection for %d timed out after %d", arrayOfObject);
      }
      return;
    }
    catch (InterruptedException localInterruptedException)
    {
      FinskyLog.d("Interrupted while connecting to remote service", new Object[0]);
      return;
    }
    finally
    {
      unbindService(localConsumptionAppServiceConn);
    }
  }
  
  private final class ConsumptionAppServiceConn
    implements ServiceConnection
  {
    private final int mBackendId;
    private final Semaphore mLock;
    private IUserContentService mService;
    
    public ConsumptionAppServiceConn(int paramInt, Semaphore paramSemaphore)
    {
      this.mBackendId = paramInt;
      this.mLock = paramSemaphore;
    }
    
    public final void onServiceConnected(ComponentName paramComponentName, final IBinder paramIBinder)
    {
      FetchConsumptionDataService.sFetchThread.execute(new Runnable()
      {
        public final void run()
        {
          FetchConsumptionDataService.ConsumptionAppServiceConn.access$002(FetchConsumptionDataService.ConsumptionAppServiceConn.this, IUserContentService.Stub.asInterface(paramIBinder));
          FetchConsumptionDataService.ConsumptionAppServiceConn.access$100(FetchConsumptionDataService.ConsumptionAppServiceConn.this);
          FetchConsumptionDataService.ConsumptionAppServiceConn.this.mLock.release();
        }
      });
    }
    
    public final void onServiceDisconnected(ComponentName paramComponentName)
    {
      this.mService = null;
      this.mLock.release();
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.services.FetchConsumptionDataService
 * JD-Core Version:    0.7.0.1
 */