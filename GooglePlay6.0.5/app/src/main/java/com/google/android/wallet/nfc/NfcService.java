package com.google.android.wallet.nfc;

import android.content.Intent;

public abstract interface NfcService
{
  public abstract boolean isAdapterEnabled();
  
  public abstract boolean isReadInProgress();
  
  public abstract void pause();
  
  public abstract void readNfcIntentInBackground(Intent paramIntent);
  
  public abstract void resume();
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.nfc.NfcService
 * JD-Core Version:    0.7.0.1
 */