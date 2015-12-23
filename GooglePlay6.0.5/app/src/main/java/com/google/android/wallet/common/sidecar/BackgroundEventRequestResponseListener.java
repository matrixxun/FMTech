package com.google.android.wallet.common.sidecar;

import android.util.Pair;
import com.android.volley.Response.Listener;
import com.google.android.wallet.common.api.http.BackgroundEventRequest;
import com.google.protobuf.nano.MessageNano;

public abstract class BackgroundEventRequestResponseListener<RequestT extends BackgroundEventRequest<?>, ResponseT extends MessageNano>
  implements Response.Listener<Pair<RequestT, ResponseT>>
{
  public abstract void handleResponse(RequestT paramRequestT, ResponseT paramResponseT);
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.common.sidecar.BackgroundEventRequestResponseListener
 * JD-Core Version:    0.7.0.1
 */