package com.google.android.finsky.services;

import android.accounts.Account;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.AccountHandler;
import com.google.android.finsky.protos.Toc.CorpusMetadata;
import com.google.android.finsky.protos.Toc.TocResponse;
import com.google.android.finsky.utils.GetTocHelper;
import com.google.android.finsky.utils.GetTocHelper.Listener;
import com.google.android.finsky.utils.Utils;
import java.util.concurrent.Semaphore;

public class MarketCatalogService
  extends Service
{
  private final IMarketCatalogService.Stub mBinder = new IMarketCatalogService.Stub()
  {
    public final boolean isBackendEnabled(String paramAnonymousString, final int paramAnonymousInt)
    {
      Utils.ensureNotOnMainThread();
      final Semaphore localSemaphore = new Semaphore(0);
      final boolean[] arrayOfBoolean = new boolean[1];
      Account localAccount = AccountHandler.findAccount(paramAnonymousString, MarketCatalogService.this);
      if (localAccount == null) {
        return false;
      }
      GetTocHelper.getToc(FinskyApp.get().getDfeApi(localAccount.name), false, new GetTocHelper.Listener()
      {
        public final void onErrorResponse(VolleyError paramAnonymous2VolleyError)
        {
          localSemaphore.release();
        }
        
        public final void onResponse(Toc.TocResponse paramAnonymous2TocResponse)
        {
          Toc.CorpusMetadata[] arrayOfCorpusMetadata = paramAnonymous2TocResponse.corpus;
          int i = arrayOfCorpusMetadata.length;
          for (int j = 0;; j++) {
            if (j < i)
            {
              Toc.CorpusMetadata localCorpusMetadata = arrayOfCorpusMetadata[j];
              if (paramAnonymousInt == localCorpusMetadata.backend) {
                arrayOfBoolean[0] = true;
              }
            }
            else
            {
              localSemaphore.release();
              return;
            }
          }
        }
      });
      localSemaphore.acquireUninterruptibly();
      MarketCatalogService.this.stopSelf();
      return arrayOfBoolean[0];
    }
  };
  
  public IBinder onBind(Intent paramIntent)
  {
    return this.mBinder;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.services.MarketCatalogService
 * JD-Core Version:    0.7.0.1
 */