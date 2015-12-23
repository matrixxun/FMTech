package com.google.android.vending.verifier;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.gms.clearcut.ClearcutLogger;
import com.google.android.gms.clearcut.ClearcutLogger.LogEventBuilder;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.vending.verifier.protos.CsdClient.VerifyAppsReport;
import com.google.protobuf.nano.MessageNano;
import java.util.concurrent.TimeUnit;

public class PackageVerificationLoggingService
  extends IntentService
{
  public PackageVerificationLoggingService()
  {
    super(PackageVerificationLoggingService.class.getSimpleName());
  }
  
  private GoogleApiClient createConnectedGoogleApiClient()
  {
    try
    {
      GoogleApiClient localGoogleApiClient = new GoogleApiClient.Builder(this).addApi(ClearcutLogger.API).build();
      ConnectionResult localConnectionResult = localGoogleApiClient.blockingConnect(10000L, TimeUnit.MILLISECONDS);
      if (!localConnectionResult.isSuccess())
      {
        FinskyLog.w("Could not connect to Clearcut: %s", new Object[] { localConnectionResult });
        localGoogleApiClient = null;
      }
      return localGoogleApiClient;
    }
    catch (RuntimeException localRuntimeException)
    {
      FinskyLog.e("Could not connect to Clearcut: %s", new Object[] { localRuntimeException });
    }
    return null;
  }
  
  public static void reportUserResponse(Context paramContext, CsdClient.VerifyAppsReport paramVerifyAppsReport)
  {
    byte[] arrayOfByte = MessageNano.toByteArray(paramVerifyAppsReport);
    Intent localIntent = new Intent("log_user_response");
    localIntent.setClass(paramContext, PackageVerificationLoggingService.class);
    localIntent.putExtra("proto_bytes", arrayOfByte);
    paramContext.startService(localIntent);
  }
  
  protected void onHandleIntent(Intent paramIntent)
  {
    String str = paramIntent.getAction();
    if ("log_user_response".equals(str))
    {
      byte[] arrayOfByte = paramIntent.getByteArrayExtra("proto_bytes");
      GoogleApiClient localGoogleApiClient = createConnectedGoogleApiClient();
      if (localGoogleApiClient != null) {}
      try
      {
        Status localStatus = (Status)new ClearcutLogger(this, "ANDROID_VERIFY_APPS").newEvent(arrayOfByte).log(localGoogleApiClient).await(10000L, TimeUnit.MILLISECONDS);
        if (!localStatus.isSuccess())
        {
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = localStatus.zzanv;
          FinskyLog.e("Could not log user response to Clearcut: %s", arrayOfObject);
        }
        return;
      }
      catch (RuntimeException localRuntimeException)
      {
        FinskyLog.e("Could not log user response to Clearcut: %s", new Object[] { localRuntimeException });
        return;
      }
      finally
      {
        localGoogleApiClient.disconnect();
      }
    }
    FinskyLog.wtf("Unrecognized action: %s", new Object[] { str });
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.vending.verifier.PackageVerificationLoggingService
 * JD-Core Version:    0.7.0.1
 */