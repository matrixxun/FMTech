package com.google.android.gms.internal;

import android.util.Log;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultCallbacks;
import com.google.android.gms.common.api.ResultTransform;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.TransformedResult;
import com.google.android.gms.common.internal.zzx;

public final class zznu<R extends Result>
  extends TransformedResult<R>
  implements ResultCallback<R>
{
  private final Object zzaoF;
  private ResultTransform<? super R, ? extends Result> zzaqR;
  private zznu<? extends Result> zzaqS;
  private ResultCallbacks<? super R> zzaqT;
  private PendingResult<R> zzaqU;
  
  private void zzG(Status paramStatus)
  {
    synchronized (this.zzaoF)
    {
      if (this.zzaqR != null)
      {
        zzx.zzb(paramStatus, "onFailure must not return null");
        this.zzaqS.zzG(paramStatus);
      }
      return;
    }
  }
  
  private static void zzc(Result paramResult)
  {
    if ((paramResult instanceof Releasable)) {}
    try
    {
      ((Releasable)paramResult).release();
      return;
    }
    catch (RuntimeException localRuntimeException)
    {
      Log.w("TransformedResultImpl", "Unable to release " + paramResult, localRuntimeException);
    }
  }
  
  public final void onResult(R paramR)
  {
    for (;;)
    {
      zznu localzznu;
      synchronized (this.zzaoF)
      {
        if (!paramR.getStatus().isSuccess()) {
          break label151;
        }
        if (this.zzaqR == null) {
          break label141;
        }
        PendingResult localPendingResult = this.zzaqR.onSuccess$1e5d55c();
        if (localPendingResult == null)
        {
          zzG(new Status(13, "Transform returned null"));
          zzc(paramR);
          return;
        }
        localzznu = this.zzaqS;
        synchronized (localzznu.zzaoF)
        {
          localzznu.zzaqU = localPendingResult;
          if (localzznu.zzaqU != null) {
            if ((localzznu.zzaqR != null) || (localzznu.zzaqT != null)) {
              break label128;
            }
          }
        }
      }
      label128:
      localzznu.zzaqU.setResultCallback(localzznu);
      continue;
      label141:
      if (this.zzaqT != null)
      {
        continue;
        label151:
        zzG(paramR.getStatus());
        zzc(paramR);
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zznu
 * JD-Core Version:    0.7.0.1
 */