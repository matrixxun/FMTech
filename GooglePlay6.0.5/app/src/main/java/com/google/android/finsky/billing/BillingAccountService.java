package com.google.android.finsky.billing;

import android.accounts.Account;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import com.android.vending.billing.IBillingAccountService.Stub;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.AccountHandler;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.billing.promptforfop.SetupWizardPromptForFopActivity;
import com.google.android.finsky.protos.BuyInstruments.CheckInstrumentResponse;
import com.google.android.finsky.protos.CheckPromoOfferResponse;
import com.google.android.finsky.protos.Toc.TocResponse;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.GetTocHelper;
import com.google.android.finsky.utils.GetTocHelper.Listener;
import com.google.android.finsky.utils.Utils;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class BillingAccountService
  extends Service
{
  private Bundle checkPromoOffers(Account paramAccount)
    throws AuthFailureError
  {
    Bundle localBundle = new Bundle();
    DfeApi localDfeApi = FinskyApp.get().getDfeApi(paramAccount.name);
    RequestFuture localRequestFuture = RequestFuture.newFuture();
    localDfeApi.checkPromoOffers(localRequestFuture, localRequestFuture);
    for (;;)
    {
      try
      {
        localCheckPromoOfferResponse = (CheckPromoOfferResponse)localRequestFuture.get();
        if (localCheckPromoOfferResponse.hasAvailablePromoOfferStatus) {
          continue;
        }
        FinskyLog.wtf("No available promo offer status returned.", new Object[0]);
        i = -1;
      }
      catch (InterruptedException localInterruptedException)
      {
        CheckPromoOfferResponse localCheckPromoOfferResponse;
        Object[] arrayOfObject1;
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = localInterruptedException.getMessage();
        FinskyLog.e("Interrupted while requesting /checkPromoOffers: %s", arrayOfObject2);
        i = -4;
        continue;
      }
      catch (ExecutionException localExecutionException)
      {
        Throwable localThrowable = localExecutionException.getCause();
        FinskyLog.e("Error while requesting /checkPromoOffers: %s", new Object[] { localThrowable });
        int i = convertErrorCode(localThrowable);
        continue;
        i = 3;
      }
      if (i == 1) {
        i = fetchToc(localDfeApi, i);
      }
      localBundle.putInt("result_code", i);
      arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = Integer.valueOf(i);
      FinskyLog.d("CheckPromoOffers result: %d", arrayOfObject1);
      return localBundle;
      switch (localCheckPromoOfferResponse.availablePromoOfferStatus)
      {
      case 2: 
        FinskyLog.wtf("Unexpected NO_FOP_HAS_OFFER", new Object[0]);
      case 3: 
        localBundle.putParcelable("available_offer_redemption_intent", SetupWizardPromptForFopActivity.createExternalSetupWizardIntent(paramAccount));
        i = 1;
      }
    }
  }
  
  private static int convertErrorCode(Throwable paramThrowable)
  {
    if ((paramThrowable instanceof ServerError)) {
      return -1;
    }
    if ((paramThrowable instanceof NetworkError)) {
      return -2;
    }
    if ((paramThrowable instanceof AuthFailureError)) {
      return -3;
    }
    if ((paramThrowable instanceof TimeoutError)) {
      return -4;
    }
    return 0;
  }
  
  private int fetchToc(DfeApi paramDfeApi, int paramInt)
  {
    final int[] arrayOfInt = { paramInt };
    final Semaphore localSemaphore = new Semaphore(0);
    GetTocHelper.getToc(paramDfeApi, false, new GetTocHelper.Listener()
    {
      public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
      {
        FinskyLog.e("Error while loading toc: %s", new Object[] { paramAnonymousVolleyError });
        arrayOfInt[0] = BillingAccountService.access$200$5165dffa(paramAnonymousVolleyError);
        localSemaphore.release();
      }
      
      public final void onResponse(Toc.TocResponse paramAnonymousTocResponse)
      {
        localSemaphore.release();
      }
    });
    try
    {
      if (!localSemaphore.tryAcquire(45L, TimeUnit.SECONDS)) {
        arrayOfInt[0] = -4;
      }
      return arrayOfInt[0];
    }
    catch (InterruptedException localInterruptedException)
    {
      for (;;)
      {
        arrayOfInt[0] = -4;
      }
    }
  }
  
  public IBinder onBind(Intent paramIntent)
  {
    new IBillingAccountService.Stub()
    {
      public final Bundle getOffers(String paramAnonymousString)
      {
        Utils.ensureNotOnMainThread();
        Account localAccount = AccountHandler.findAccount(paramAnonymousString, BillingAccountService.this);
        if (localAccount == null)
        {
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = FinskyLog.scrubPii(paramAnonymousString);
          FinskyLog.e("Received invalid account name: %s", arrayOfObject);
          Bundle localBundle4 = new Bundle();
          localBundle4.putInt("result_code", -5);
          return localBundle4;
        }
        try
        {
          Bundle localBundle3 = BillingAccountService.this.checkPromoOffers(localAccount);
          return localBundle3;
        }
        catch (AuthFailureError localAuthFailureError1)
        {
          try
          {
            Bundle localBundle2 = BillingAccountService.this.checkPromoOffers(localAccount);
            return localBundle2;
          }
          catch (AuthFailureError localAuthFailureError2)
          {
            Bundle localBundle1 = new Bundle();
            localBundle1.putInt("result_code", -3);
            return localBundle1;
          }
        }
      }
      
      public final int hasValidCreditCard(String paramAnonymousString)
        throws RemoteException
      {
        Utils.ensureNotOnMainThread();
        int[] arrayOfInt = new int[1];
        Semaphore localSemaphore = new Semaphore(0);
        Account localAccount = AccountHandler.findAccount(paramAnonymousString, BillingAccountService.this);
        if (localAccount == null)
        {
          FinskyLog.e("Received invalid account name: " + paramAnonymousString, new Object[0]);
          return -5;
        }
        DfeApi localDfeApi = FinskyApp.get().getDfeApi(localAccount.name);
        try
        {
          BillingAccountService.access$000(BillingAccountService.this, localDfeApi, arrayOfInt, localSemaphore);
          if (!localSemaphore.tryAcquire(45L, TimeUnit.SECONDS)) {
            arrayOfInt[0] = -4;
          }
          int i = arrayOfInt[0];
          return i;
        }
        catch (InterruptedException localInterruptedException)
        {
          FinskyLog.d("Timed out while waiting for response.", new Object[0]);
        }
        return -4;
      }
    };
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.BillingAccountService
 * JD-Core Version:    0.7.0.1
 */