package com.google.android.wallet.analytics;

import com.google.protobuf.nano.WireFormatNano;
import java.util.List;

public final class WalletUiElement
{
  public List<WalletUiElement> children;
  public final int elementId;
  public final byte[] integratorLogToken;
  
  public WalletUiElement(int paramInt)
  {
    this(paramInt, WireFormatNano.EMPTY_BYTES);
  }
  
  public WalletUiElement(int paramInt, byte[] paramArrayOfByte)
  {
    this.elementId = paramInt;
    this.integratorLogToken = paramArrayOfByte;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.analytics.WalletUiElement
 * JD-Core Version:    0.7.0.1
 */