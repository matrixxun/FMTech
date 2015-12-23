package com.google.android.finsky.api.model;

import android.content.Context;
import android.os.AsyncTask;
import com.android.volley.Response.Listener;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Utils;
import com.google.protobuf.nano.InvalidProtocolBufferNanoException;
import com.google.protobuf.nano.MessageNano;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class CachedDfeModel<T extends MessageNano>
  extends DfeModel
  implements Response.Listener<T>
{
  final File mCacheFile;
  private final Method mParseFromMethod;
  public T mResponse;
  
  public CachedDfeModel(Context paramContext, Class<T> paramClass)
  {
    this.mCacheFile = new File(paramContext.getCacheDir(), paramClass.getSimpleName());
    try
    {
      Method localMethod2 = paramClass.getMethod("parseFrom", new Class[] { [B.class });
      localMethod1 = localMethod2;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      for (;;)
      {
        FinskyLog.w("Cannot find parseFrom method in given class, verify it is a proto.", new Object[] { localNoSuchMethodException });
        Method localMethod1 = null;
      }
    }
    this.mParseFromMethod = localMethod1;
  }
  
  protected final boolean hasCachedData()
  {
    return (this.mCacheFile.exists()) && (this.mCacheFile.canRead());
  }
  
  public final boolean isReady()
  {
    return this.mResponse != null;
  }
  
  protected final T loadCachedModel()
  {
    if (hasCachedData()) {}
    try
    {
      byte[] arrayOfByte = Utils.readBytes(new FileInputStream(this.mCacheFile));
      MessageNano localMessageNano = (MessageNano)this.mParseFromMethod.invoke(null, new Object[] { arrayOfByte });
      return localMessageNano;
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      FinskyLog.w("Failed to load response proto, file not found.", new Object[] { localFileNotFoundException });
      return null;
    }
    catch (InvalidProtocolBufferNanoException localInvalidProtocolBufferNanoException)
    {
      for (;;)
      {
        FinskyLog.w("Failed to load response proto, bad proto.", new Object[] { localInvalidProtocolBufferNanoException });
        this.mCacheFile.delete();
      }
    }
    catch (IOException localIOException)
    {
      for (;;)
      {
        FinskyLog.w("Failed to load response proto.", new Object[] { localIOException });
      }
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      for (;;)
      {
        FinskyLog.w("Failed to parse response proto, parseFrom method missing.", new Object[] { localInvocationTargetException });
      }
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      for (;;)
      {
        FinskyLog.w("Failed to parse response proto, parseFrom method private.", new Object[] { localIllegalAccessException });
      }
    }
  }
  
  public final void onErrorResponse(VolleyError paramVolleyError)
  {
    if (((paramVolleyError instanceof ServerError)) && (paramVolleyError.networkResponse != null))
    {
      super.onErrorResponse(paramVolleyError);
      return;
    }
    Utils.executeMultiThreaded(new AsyncTask()
    {
      VolleyError error;
    }, new VolleyError[] { paramVolleyError });
  }
  
  protected final void onModelLoaded(T paramT)
  {
    this.mResponse = paramT;
    notifyDataSetChanged();
  }
  
  public final void onResponse(T paramT)
  {
    byte[] arrayOfByte = MessageNano.toByteArray(paramT);
    Utils.executeMultiThreaded(new AsyncTask()
    {
      /* Error */
      private Void doInBackground(byte[]... paramAnonymousVarArgs)
      {
        // Byte code:
        //   0: aconst_null
        //   1: astore_2
        //   2: new 24	java/io/FileOutputStream
        //   5: dup
        //   6: aload_0
        //   7: getfield 13	com/google/android/finsky/api/model/CachedDfeModel$2:this$0	Lcom/google/android/finsky/api/model/CachedDfeModel;
        //   10: getfield 28	com/google/android/finsky/api/model/CachedDfeModel:mCacheFile	Ljava/io/File;
        //   13: invokespecial 31	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
        //   16: astore_3
        //   17: aload_3
        //   18: aload_1
        //   19: iconst_0
        //   20: aaload
        //   21: invokevirtual 35	java/io/FileOutputStream:write	([B)V
        //   24: aload_3
        //   25: invokevirtual 38	java/io/FileOutputStream:flush	()V
        //   28: aload_3
        //   29: invokevirtual 41	java/io/FileOutputStream:close	()V
        //   32: aconst_null
        //   33: areturn
        //   34: astore 10
        //   36: goto -4 -> 32
        //   39: astore 4
        //   41: ldc 43
        //   43: iconst_1
        //   44: anewarray 45	java/lang/Object
        //   47: dup
        //   48: iconst_0
        //   49: aload 4
        //   51: aastore
        //   52: invokestatic 51	com/google/android/finsky/utils/FinskyLog:w	(Ljava/lang/String;[Ljava/lang/Object;)V
        //   55: aload_2
        //   56: ifnull -24 -> 32
        //   59: aload_2
        //   60: invokevirtual 38	java/io/FileOutputStream:flush	()V
        //   63: aload_2
        //   64: invokevirtual 41	java/io/FileOutputStream:close	()V
        //   67: goto -35 -> 32
        //   70: astore 7
        //   72: goto -40 -> 32
        //   75: astore 8
        //   77: ldc 53
        //   79: iconst_1
        //   80: anewarray 45	java/lang/Object
        //   83: dup
        //   84: iconst_0
        //   85: aload 8
        //   87: aastore
        //   88: invokestatic 51	com/google/android/finsky/utils/FinskyLog:w	(Ljava/lang/String;[Ljava/lang/Object;)V
        //   91: aload_2
        //   92: ifnull -60 -> 32
        //   95: aload_2
        //   96: invokevirtual 38	java/io/FileOutputStream:flush	()V
        //   99: aload_2
        //   100: invokevirtual 41	java/io/FileOutputStream:close	()V
        //   103: goto -71 -> 32
        //   106: astore 9
        //   108: goto -76 -> 32
        //   111: astore 5
        //   113: aload_2
        //   114: ifnull +11 -> 125
        //   117: aload_2
        //   118: invokevirtual 38	java/io/FileOutputStream:flush	()V
        //   121: aload_2
        //   122: invokevirtual 41	java/io/FileOutputStream:close	()V
        //   125: aload 5
        //   127: athrow
        //   128: astore 6
        //   130: goto -5 -> 125
        //   133: astore 5
        //   135: aload_3
        //   136: astore_2
        //   137: goto -24 -> 113
        //   140: astore 8
        //   142: aload_3
        //   143: astore_2
        //   144: goto -67 -> 77
        //   147: astore 4
        //   149: aload_3
        //   150: astore_2
        //   151: goto -110 -> 41
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	154	0	this	2
        //   0	154	1	paramAnonymousVarArgs	byte[][]
        //   1	150	2	localObject1	Object
        //   16	134	3	localFileOutputStream	java.io.FileOutputStream
        //   39	11	4	localFileNotFoundException1	FileNotFoundException
        //   147	1	4	localFileNotFoundException2	FileNotFoundException
        //   111	15	5	localObject2	Object
        //   133	1	5	localObject3	Object
        //   128	1	6	localIOException1	IOException
        //   70	1	7	localIOException2	IOException
        //   75	11	8	localIOException3	IOException
        //   140	1	8	localIOException4	IOException
        //   106	1	9	localIOException5	IOException
        //   34	1	10	localIOException6	IOException
        // Exception table:
        //   from	to	target	type
        //   24	32	34	java/io/IOException
        //   2	17	39	java/io/FileNotFoundException
        //   59	67	70	java/io/IOException
        //   2	17	75	java/io/IOException
        //   95	103	106	java/io/IOException
        //   2	17	111	finally
        //   41	55	111	finally
        //   77	91	111	finally
        //   117	125	128	java/io/IOException
        //   17	24	133	finally
        //   17	24	140	java/io/IOException
        //   17	24	147	java/io/FileNotFoundException
      }
    }, new byte[][] { arrayOfByte });
    onModelLoaded(paramT);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.api.model.CachedDfeModel
 * JD-Core Version:    0.7.0.1
 */