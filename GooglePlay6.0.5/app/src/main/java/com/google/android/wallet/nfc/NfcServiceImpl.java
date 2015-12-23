package com.google.android.wallet.nfc;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;

public final class NfcServiceImpl
  implements NfcReadResultListener, NfcService
{
  private final Activity mActivity;
  private final NfcAdapter mNfcAdapter;
  private final NfcReadResultListener mNfcReadListener;
  private NfcReaderTask mNfcReaderTask;
  private final PendingIntent mPendingIntent;
  private boolean mReadInProgress;
  
  @TargetApi(10)
  public NfcServiceImpl(Activity paramActivity, NfcReadResultListener paramNfcReadResultListener)
  {
    this.mActivity = paramActivity;
    this.mNfcAdapter = NfcAdapter.getDefaultAdapter(this.mActivity);
    this.mPendingIntent = PendingIntent.getActivity(this.mActivity, 0, new Intent(this.mActivity, this.mActivity.getClass()).addFlags(536870912), 0);
    this.mNfcReadListener = paramNfcReadResultListener;
    this.mReadInProgress = false;
  }
  
  public final boolean isAdapterEnabled()
  {
    return this.mNfcAdapter.isEnabled();
  }
  
  public final boolean isReadInProgress()
  {
    return this.mReadInProgress;
  }
  
  public final void onNfcReadResult(int paramInt, CreditCardNfcResult paramCreditCardNfcResult, long paramLong)
  {
    this.mReadInProgress = false;
    this.mNfcReadListener.onNfcReadResult(paramInt, paramCreditCardNfcResult, paramLong);
  }
  
  @TargetApi(10)
  public final void pause()
  {
    this.mNfcAdapter.disableForegroundDispatch(this.mActivity);
  }
  
  public final void readNfcIntentInBackground(Intent paramIntent)
  {
    this.mReadInProgress = true;
    this.mNfcReaderTask = new NfcReaderTask(this);
    this.mNfcReaderTask.execute(new Intent[] { paramIntent });
  }
  
  @TargetApi(10)
  public final void resume()
  {
    this.mNfcAdapter.enableForegroundDispatch(this.mActivity, this.mPendingIntent, null, null);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.nfc.NfcServiceImpl
 * JD-Core Version:    0.7.0.1
 */