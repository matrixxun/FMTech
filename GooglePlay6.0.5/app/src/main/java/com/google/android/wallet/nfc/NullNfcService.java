package com.google.android.wallet.nfc;

import android.content.Intent;

public final class NullNfcService
  implements NfcService
{
  public final boolean isAdapterEnabled()
  {
    return false;
  }
  
  public final boolean isReadInProgress()
  {
    return false;
  }
  
  public final void pause() {}
  
  public final void readNfcIntentInBackground(Intent paramIntent) {}
  
  public final void resume() {}
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.nfc.NullNfcService
 * JD-Core Version:    0.7.0.1
 */