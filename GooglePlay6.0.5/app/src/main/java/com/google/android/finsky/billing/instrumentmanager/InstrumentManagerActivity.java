package com.google.android.finsky.billing.instrumentmanager;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.billing.BillingUtils;

public class InstrumentManagerActivity
  extends InstrumentManagerBaseActivity
{
  public static Intent createIntent(String paramString, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, Bundle paramBundle)
  {
    Intent localIntent = new Intent(FinskyApp.get(), InstrumentManagerActivity.class);
    InstrumentManagerBaseActivity.putIntentExtras(paramString, paramArrayOfByte1, paramArrayOfByte2, paramBundle, localIntent);
    return localIntent;
  }
  
  protected final int getInstrumentManagerThemeResourceId()
  {
    return BillingUtils.getInstrumentManagerThemeResourceId();
  }
  
  protected final int getPlayStoreUiElementType()
  {
    return 1600;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.instrumentmanager.InstrumentManagerActivity
 * JD-Core Version:    0.7.0.1
 */