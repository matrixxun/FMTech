package com.google.android.wallet.common.util;

import android.util.Log;
import com.google.android.gsf.GservicesValue;
import com.google.android.wallet.config.G;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class ProtoUtils
{
  private static final boolean PROTO_DEBUG = Log.isLoggable("ImProto", 2);
  
  public static <T extends MessageNano> T copyFrom(T paramT)
  {
    return parseFrom(MessageNano.toByteArray(paramT), paramT.getClass());
  }
  
  public static void log(MessageNano paramMessageNano, String paramString)
  {
    if (!PROTO_DEBUG) {
      return;
    }
    if (((Boolean)G.allowPiiLogging.get()).booleanValue()) {
      try
      {
        Log.v("ImProto", paramString);
        for (String str : paramMessageNano.toString().split("\n")) {
          Log.v("ImProto", "| " + str);
        }
        return;
      }
      finally {}
    }
    Log.v("ImProto", "allowPiiLogging needs to be enabled for proto logging");
  }
  
  public static void logResponse(MessageNano paramMessageNano, String paramString)
  {
    log(paramMessageNano, "Response for " + paramString);
  }
  
  public static <T extends MessageNano> T parseFrom(byte[] paramArrayOfByte, Class<T> paramClass)
  {
    try
    {
      MessageNano localMessageNano = MessageNano.mergeFrom$1ec43da((MessageNano)paramClass.newInstance(), paramArrayOfByte, paramArrayOfByte.length);
      return localMessageNano;
    }
    catch (InstantiationException localInstantiationException)
    {
      throw new RuntimeException("Failed to parse a known parcelable proto " + paramClass.getName());
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      throw new RuntimeException("Failed to parse a known parcelable proto " + paramClass.getName());
    }
    catch (IOException localIOException)
    {
      throw new RuntimeException("Failed to parse a known parcelable proto " + paramClass.getName());
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.common.util.ProtoUtils
 * JD-Core Version:    0.7.0.1
 */