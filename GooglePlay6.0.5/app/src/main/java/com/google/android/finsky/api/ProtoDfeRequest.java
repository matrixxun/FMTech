package com.google.android.finsky.api;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.google.protobuf.nano.MessageNano;

public class ProtoDfeRequest<T extends MessageNano>
  extends DfeRequest<T>
{
  private final MessageNano mRequest;
  
  public ProtoDfeRequest(String paramString, MessageNano paramMessageNano, DfeApiContext paramDfeApiContext, Class<T> paramClass, Response.Listener<T> paramListener, Response.ErrorListener paramErrorListener)
  {
    super(paramString, paramDfeApiContext, paramClass, paramListener, paramErrorListener, (byte)0);
    this.mRequest = paramMessageNano;
    this.mShouldCache = false;
    this.mIncludeCheckinConsistencyToken = true;
    this.mAvoidBulkCancel = true;
  }
  
  public final byte[] getBody()
  {
    return MessageNano.toByteArray(this.mRequest);
  }
  
  public final String getBodyContentType()
  {
    return "application/x-protobuf";
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.api.ProtoDfeRequest
 * JD-Core Version:    0.7.0.1
 */