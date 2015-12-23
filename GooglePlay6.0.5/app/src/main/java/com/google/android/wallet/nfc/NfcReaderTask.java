package com.google.android.wallet.nfc;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.text.TextUtils;
import com.google.android.wallet.nfc.communication.NfcReader;
import com.google.android.wallet.nfc.exceptions.TagReadException;
import com.google.android.wallet.nfc.exceptions.TagReadTimeoutException;
import com.google.android.wallet.nfc.exceptions.UnsupportedTagException;
import java.io.IOException;
import java.lang.ref.WeakReference;

public final class NfcReaderTask
  extends AsyncTask<Intent, Void, CreditCardNfcResult>
{
  private final WeakReference<NfcReadResultListener> mNfcReadListener;
  private int mResultCode = 2;
  private long mStartTimeMillis;
  
  public NfcReaderTask(NfcReadResultListener paramNfcReadResultListener)
  {
    this.mNfcReadListener = new WeakReference(paramNfcReadResultListener);
  }
  
  @TargetApi(10)
  private CreditCardNfcResult doInBackground(Intent... paramVarArgs)
  {
    int i = 1;
    if ((paramVarArgs == null) || (paramVarArgs.length == 0) || (!"android.nfc.action.TAG_DISCOVERED".equals(paramVarArgs[0].getAction())))
    {
      this.mResultCode = 3;
      return null;
    }
    this.mResultCode = 2;
    for (;;)
    {
      try
      {
        CreditCardNfcResult localCreditCardNfcResult = NfcReader.readIntent(paramVarArgs[0]);
        if (!TextUtils.isEmpty(localCreditCardNfcResult.cardNumber))
        {
          j = i;
          if (j == 0)
          {
            if ((localCreditCardNfcResult.expMonth == 0) || (localCreditCardNfcResult.expYear == 0)) {
              break label160;
            }
            k = i;
            if (k == 0)
            {
              if (TextUtils.isEmpty(localCreditCardNfcResult.cardHolderName)) {
                break label166;
              }
              if (i == 0)
              {
                this.mResultCode = 3;
                return null;
              }
            }
          }
          this.mResultCode = 1;
          return localCreditCardNfcResult;
        }
      }
      catch (TagReadTimeoutException localTagReadTimeoutException)
      {
        this.mResultCode = 6;
        return null;
      }
      catch (IOException localIOException)
      {
        this.mResultCode = 5;
        return null;
      }
      catch (UnsupportedTagException localUnsupportedTagException)
      {
        this.mResultCode = 4;
        return null;
      }
      catch (TagReadException localTagReadException)
      {
        this.mResultCode = 3;
        return null;
      }
      int j = 0;
      continue;
      label160:
      int k = 0;
      continue;
      label166:
      i = 0;
    }
  }
  
  private void fireNfcReadResult(CreditCardNfcResult paramCreditCardNfcResult)
  {
    long l = SystemClock.elapsedRealtime() - this.mStartTimeMillis;
    NfcReadResultListener localNfcReadResultListener = (NfcReadResultListener)this.mNfcReadListener.get();
    if (localNfcReadResultListener != null) {
      localNfcReadResultListener.onNfcReadResult(this.mResultCode, paramCreditCardNfcResult, l);
    }
  }
  
  protected final void onCancelled()
  {
    super.onCancelled();
    fireNfcReadResult(null);
  }
  
  protected final void onPreExecute()
  {
    this.mStartTimeMillis = SystemClock.elapsedRealtime();
    super.onPreExecute();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.nfc.NfcReaderTask
 * JD-Core Version:    0.7.0.1
 */