package com.google.android.finsky.utils;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ParcelUtils
{
  /* Error */
  public static <T extends Parcelable> T readFromDisk(File paramFile)
  {
    // Byte code:
    //   0: ldc 2
    //   2: monitorenter
    //   3: aconst_null
    //   4: astore_1
    //   5: new 16	java/io/DataInputStream
    //   8: dup
    //   9: new 18	java/io/FileInputStream
    //   12: dup
    //   13: aload_0
    //   14: invokespecial 21	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   17: invokespecial 24	java/io/DataInputStream:<init>	(Ljava/io/InputStream;)V
    //   20: astore_2
    //   21: aload_2
    //   22: invokestatic 28	com/google/android/finsky/utils/ParcelUtils:readObject	(Ljava/io/DataInputStream;)Landroid/os/Parcelable;
    //   25: astore 12
    //   27: aconst_null
    //   28: astore 8
    //   30: aload 12
    //   32: ifnull +7 -> 39
    //   35: aload 12
    //   37: astore 8
    //   39: aload_2
    //   40: invokestatic 34	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
    //   43: ldc 2
    //   45: monitorexit
    //   46: aload 8
    //   48: areturn
    //   49: astore_3
    //   50: aload_0
    //   51: invokevirtual 40	java/io/File:delete	()Z
    //   54: pop
    //   55: iconst_2
    //   56: anewarray 4	java/lang/Object
    //   59: astore 7
    //   61: aload 7
    //   63: iconst_0
    //   64: aload_0
    //   65: invokevirtual 44	java/io/File:getName	()Ljava/lang/String;
    //   68: aastore
    //   69: aload 7
    //   71: iconst_1
    //   72: aload_3
    //   73: invokevirtual 47	com/google/android/finsky/utils/ParcelUtils$CacheVersionException:getMessage	()Ljava/lang/String;
    //   76: aastore
    //   77: ldc 49
    //   79: aload 7
    //   81: invokestatic 55	com/google/android/finsky/utils/FinskyLog:e	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   84: aload_1
    //   85: invokestatic 34	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
    //   88: aconst_null
    //   89: astore 8
    //   91: goto -48 -> 43
    //   94: astore 5
    //   96: ldc 2
    //   98: monitorexit
    //   99: aload 5
    //   101: athrow
    //   102: astore 9
    //   104: aload_0
    //   105: invokevirtual 40	java/io/File:delete	()Z
    //   108: pop
    //   109: iconst_2
    //   110: anewarray 4	java/lang/Object
    //   113: astore 11
    //   115: aload 11
    //   117: iconst_0
    //   118: aload_0
    //   119: invokevirtual 44	java/io/File:getName	()Ljava/lang/String;
    //   122: aastore
    //   123: aload 11
    //   125: iconst_1
    //   126: aload 9
    //   128: invokevirtual 56	java/lang/Throwable:getMessage	()Ljava/lang/String;
    //   131: aastore
    //   132: aload 9
    //   134: ldc 58
    //   136: aload 11
    //   138: invokestatic 61	com/google/android/finsky/utils/FinskyLog:e	(Ljava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)V
    //   141: aload_1
    //   142: invokestatic 34	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
    //   145: aconst_null
    //   146: astore 8
    //   148: goto -105 -> 43
    //   151: aload_1
    //   152: invokestatic 34	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
    //   155: aload 4
    //   157: athrow
    //   158: astore 4
    //   160: aload_2
    //   161: astore_1
    //   162: goto -11 -> 151
    //   165: astore 9
    //   167: aload_2
    //   168: astore_1
    //   169: goto -65 -> 104
    //   172: astore_3
    //   173: aload_2
    //   174: astore_1
    //   175: goto -125 -> 50
    //   178: astore 5
    //   180: goto -84 -> 96
    //   183: astore 4
    //   185: goto -34 -> 151
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	188	0	paramFile	File
    //   4	171	1	localObject1	Object
    //   20	154	2	localDataInputStream	DataInputStream
    //   49	24	3	localCacheVersionException1	CacheVersionException
    //   172	1	3	localCacheVersionException2	CacheVersionException
    //   155	1	4	localObject2	Object
    //   158	1	4	localObject3	Object
    //   183	1	4	localObject4	Object
    //   94	6	5	localObject5	Object
    //   178	1	5	localObject6	Object
    //   59	21	7	arrayOfObject1	Object[]
    //   28	119	8	localParcelable1	Parcelable
    //   102	31	9	localThrowable1	java.lang.Throwable
    //   165	1	9	localThrowable2	java.lang.Throwable
    //   113	24	11	arrayOfObject2	Object[]
    //   25	11	12	localParcelable2	Parcelable
    // Exception table:
    //   from	to	target	type
    //   5	21	49	com/google/android/finsky/utils/ParcelUtils$CacheVersionException
    //   84	88	94	finally
    //   141	145	94	finally
    //   151	158	94	finally
    //   5	21	102	java/lang/Throwable
    //   21	27	158	finally
    //   21	27	165	java/lang/Throwable
    //   21	27	172	com/google/android/finsky/utils/ParcelUtils$CacheVersionException
    //   39	43	178	finally
    //   5	21	183	finally
    //   50	84	183	finally
    //   104	141	183	finally
  }
  
  private static <T extends Parcelable> T readObject(DataInputStream paramDataInputStream)
    throws IOException
  {
    Utils.ensureNotOnMainThread();
    ClassLoader localClassLoader = ParcelUtils.class.getClassLoader();
    int i = paramDataInputStream.readInt();
    byte[] arrayOfByte = new byte[i];
    paramDataInputStream.read(arrayOfByte);
    Parcel localParcel = Parcel.obtain();
    localParcel.setDataPosition(0);
    localParcel.unmarshall(arrayOfByte, 0, i);
    localParcel.setDataPosition(0);
    try
    {
      Parcelable localParcelable = localParcel.readParcelable(localClassLoader);
      return localParcelable;
    }
    finally
    {
      localParcel.recycle();
    }
  }
  
  public static void writeToDisk(File paramFile, Parcelable paramParcelable)
    throws IOException
  {
    DataOutputStream localDataOutputStream1;
    try
    {
      Utils.ensureNotOnMainThread();
      paramFile.getParentFile().mkdirs();
    }
    finally {}
    try
    {
      localDataOutputStream1 = new DataOutputStream(new FileOutputStream(paramFile));
      try
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.setDataPosition(0);
          localParcel.writeParcelable(paramParcelable, 0);
          byte[] arrayOfByte = localParcel.marshall();
          localDataOutputStream1.writeInt(arrayOfByte.length);
          localDataOutputStream1.write(arrayOfByte);
          localParcel.recycle();
          Utils.safeClose(localDataOutputStream1);
          return;
        }
        finally
        {
          localObject4 = finally;
          localParcel.recycle();
          throw localObject4;
        }
        Utils.safeClose(localDataOutputStream2);
      }
      finally
      {
        localDataOutputStream2 = localDataOutputStream1;
      }
    }
    finally
    {
      localDataOutputStream2 = null;
    }
    throw localObject2;
  }
  
  public static final class CacheVersionException
    extends RuntimeException
  {
    private final long mActualVersion;
    private final Class<? extends Parcelable> mClass;
    private final long mExpectedVersion;
    
    public CacheVersionException(Class<? extends Parcelable> paramClass, long paramLong1, long paramLong2)
    {
      this.mClass = paramClass;
      this.mExpectedVersion = paramLong1;
      this.mActualVersion = paramLong2;
    }
    
    public final String getMessage()
    {
      Object[] arrayOfObject = new Object[3];
      arrayOfObject[0] = this.mClass.getSimpleName();
      arrayOfObject[1] = Long.valueOf(this.mExpectedVersion);
      arrayOfObject[2] = Long.valueOf(this.mActualVersion);
      return String.format("Failed parsing %s (wanted %d, got %d)", arrayOfObject);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.ParcelUtils
 * JD-Core Version:    0.7.0.1
 */