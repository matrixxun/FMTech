package com.google.android.finsky.utils;

import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;

public final class DenyAllNetwork
  implements Network
{
  public final NetworkResponse performRequest(Request<?> paramRequest)
    throws NoConnectionError
  {
    throw new BgDataDisabledError();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.DenyAllNetwork
 * JD-Core Version:    0.7.0.1
 */