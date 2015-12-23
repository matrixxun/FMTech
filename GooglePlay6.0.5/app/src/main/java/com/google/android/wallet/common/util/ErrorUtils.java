package com.google.android.wallet.common.util;

import android.os.Bundle;
import android.text.TextUtils;

public final class ErrorUtils
{
  public static Bundle addErrorDetailsToBundle(Bundle paramBundle, int paramInt, String paramString1, String paramString2, String paramString3, String paramString4)
  {
    if ((TextUtils.isEmpty(paramString1)) || (TextUtils.isEmpty(paramString2)) || (TextUtils.isEmpty(paramString4))) {
      throw new IllegalArgumentException(String.format("Error title, message, and button text are required. Received values: title: %s  message: %s  buttonText: %s", new Object[] { paramString1, paramString2, paramString4 }));
    }
    paramBundle.putInt("ErrorUtils.KEY_TYPE", paramInt);
    paramBundle.putString("ErrorUtils.KEY_TITLE", paramString1);
    paramBundle.putString("ErrorUtils.KEY_ERROR_MESSAGE", paramString2);
    if (!TextUtils.isEmpty(paramString3)) {
      paramBundle.putString("ErrorUtils.KEY_ERROR_CODE", paramString3);
    }
    paramBundle.putString("ErrorUtils.KEY_ERROR_BUTTON_TEXT", paramString4);
    return paramBundle;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.common.util.ErrorUtils
 * JD-Core Version:    0.7.0.1
 */