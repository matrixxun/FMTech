package com.google.android.wallet.common.pub;

import android.content.Context;
import android.content.res.TypedArray;
import com.google.android.gsf.GservicesValue;
import com.google.android.wallet.common.util.PaymentUtils;
import com.google.android.wallet.common.util.ProtoUtils;
import com.google.android.wallet.instrumentmanager.R.attr;
import com.google.moneta.orchestration.ui.common.bootstrap.ClientParametersOuterClass.ClientParameters;
import com.google.moneta.orchestration.ui.common.bootstrap.ClientTokenOuterClass.ClientToken;
import com.google.protobuf.nano.MessageNano;

public final class OrchestrationUtil
{
  public static byte[] createClientToken(Context paramContext, int paramInt)
  {
    GservicesValue.init(paramContext.getApplicationContext());
    int[] arrayOfInt1 = new int[2];
    arrayOfInt1[0] = R.attr.imPopupRedirectActivitySupported;
    arrayOfInt1[1] = R.attr.imTitleIconType;
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramInt, arrayOfInt1);
    ClientParametersOuterClass.ClientParameters localClientParameters = new ClientParametersOuterClass.ClientParameters();
    int[] arrayOfInt2;
    if (localTypedArray.getBoolean(0, false))
    {
      arrayOfInt2 = new int[2];
      arrayOfInt2[0] = 1;
      arrayOfInt2[1] = 2;
    }
    for (;;)
    {
      localClientParameters.supportedRedirectFormDisplayType = arrayOfInt2;
      localClientParameters.titleIconStyle = localTypedArray.getInt(1, 1);
      localTypedArray.recycle();
      ClientTokenOuterClass.ClientToken localClientToken = new ClientTokenOuterClass.ClientToken();
      localClientToken.requestContext = PaymentUtils.createRequestContextForSharedLibrary$793b517b(paramContext, null, null);
      localClientToken.clientParameters = localClientParameters;
      ProtoUtils.log(localClientToken, "ClientToken=");
      return MessageNano.toByteArray(localClientToken);
      arrayOfInt2 = new int[] { 1 };
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.common.pub.OrchestrationUtil
 * JD-Core Version:    0.7.0.1
 */