package com.google.android.wallet.common.api.http;

import android.net.Uri;
import android.net.Uri.Builder;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.util.Pair;
import com.android.volley.AuthFailureError;
import com.android.volley.Cache.Entry;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.android.wallet.common.util.ProtoUtils;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;
import java.util.Map;

public abstract class SecureRequest<RequestT extends SecureRequest<RequestT, ResponseT>, ResponseT extends MessageNano>
  extends BackgroundEventRequest<Pair<RequestT, ResponseT>>
{
  private final Map<String, String> mAdditionalHeaders;
  public final ApiContext mApiContext;
  private final Class<ResponseT> mResponseClass;
  public final byte[] mSessionData;
  
  public SecureRequest(ApiContext paramApiContext, Map<String, String> paramMap, byte[] paramArrayOfByte, Class<ResponseT> paramClass, Response.Listener<Pair<RequestT, ResponseT>> paramListener, Response.ErrorListener paramErrorListener)
  {
    super(paramListener, paramErrorListener);
    this.mApiContext = paramApiContext;
    this.mAdditionalHeaders = paramMap;
    this.mSessionData = paramArrayOfByte;
    this.mResponseClass = paramClass;
    if (this.mAdditionalHeaders.isEmpty()) {
      throw new IllegalArgumentException("There must be at least an EES serialization header.");
    }
  }
  
  public abstract String getActionUrl();
  
  public final byte[] getBody()
    throws AuthFailureError
  {
    MessageNano localMessageNano = getProtoRequest();
    ProtoUtils.log(localMessageNano, "SecureRequestProto=");
    return MessageNano.toByteArray(localMessageNano);
  }
  
  public final String getBodyContentType()
  {
    return "application/protobuf";
  }
  
  public final Map<String, String> getHeaders()
    throws AuthFailureError
  {
    ArrayMap localArrayMap = new ArrayMap(this.mAdditionalHeaders.size() + this.mApiContext.getHeaders().size());
    localArrayMap.putAll(this.mApiContext.getHeaders());
    localArrayMap.putAll(this.mAdditionalHeaders);
    return localArrayMap;
  }
  
  public abstract MessageNano getProtoRequest();
  
  public final String getUrl()
  {
    return this.mApiContext.eesBaseUrl.buildUpon().appendEncodedPath(getActionUrl()).appendQueryParameter("s7e_mode", "proto").toString();
  }
  
  protected final Response<Pair<RequestT, ResponseT>> parseNetworkResponse(NetworkResponse paramNetworkResponse)
  {
    try
    {
      Class localClass = this.mResponseClass;
      MessageNano localMessageNano1 = (MessageNano)this.mResponseClass.newInstance();
      byte[] arrayOfByte = paramNetworkResponse.data;
      MessageNano localMessageNano2 = (MessageNano)localClass.cast(MessageNano.mergeFrom$1ec43da(localMessageNano1, arrayOfByte, arrayOfByte.length));
      ProtoUtils.logResponse(localMessageNano2, getUrl());
      Cache.Entry localEntry = HttpHeaderParser.parseCacheHeaders(paramNetworkResponse);
      return Response.success(Pair.create(this, localMessageNano2), localEntry);
    }
    catch (InstantiationException localInstantiationException)
    {
      throw new RuntimeException("Failed to instantiate proto object.", localInstantiationException);
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      throw new RuntimeException("Failed to access proto constructor.", localIllegalAccessException);
    }
    catch (IOException localIOException)
    {
      Log.e("SecureRequest", "Couldn't parse proto response for url=" + getUrl());
    }
    return Response.error(new ParseError(paramNetworkResponse));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.common.api.http.SecureRequest
 * JD-Core Version:    0.7.0.1
 */