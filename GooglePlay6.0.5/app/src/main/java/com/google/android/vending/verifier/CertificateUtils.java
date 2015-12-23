package com.google.android.vending.verifier;

public final class CertificateUtils
{
  /* Error */
  public static byte[][] collectCertificates(java.lang.String paramString)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: new 12	java/util/jar/JarFile
    //   5: dup
    //   6: aload_0
    //   7: invokespecial 16	java/util/jar/JarFile:<init>	(Ljava/lang/String;)V
    //   10: astore_2
    //   11: aload_2
    //   12: aload_2
    //   13: ldc 18
    //   15: invokevirtual 22	java/util/jar/JarFile:getJarEntry	(Ljava/lang/String;)Ljava/util/jar/JarEntry;
    //   18: invokestatic 26	com/google/android/vending/verifier/CertificateUtils:loadCertificates	(Ljava/util/jar/JarFile;Ljava/util/jar/JarEntry;)[Ljava/security/cert/Certificate;
    //   21: astore 7
    //   23: aload 7
    //   25: ifnull +9 -> 34
    //   28: aload 7
    //   30: arraylength
    //   31: ifne +29 -> 60
    //   34: aload_2
    //   35: invokevirtual 30	java/util/jar/JarFile:close	()V
    //   38: aload_2
    //   39: invokevirtual 30	java/util/jar/JarFile:close	()V
    //   42: aconst_null
    //   43: areturn
    //   44: astore 8
    //   46: aload 8
    //   48: ldc 32
    //   50: iconst_0
    //   51: anewarray 4	java/lang/Object
    //   54: invokestatic 38	com/google/android/finsky/utils/FinskyLog:e	(Ljava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)V
    //   57: goto -15 -> 42
    //   60: aload_2
    //   61: invokevirtual 30	java/util/jar/JarFile:close	()V
    //   64: aload 7
    //   66: arraylength
    //   67: anewarray 40	[B
    //   70: astore 9
    //   72: iconst_0
    //   73: istore 10
    //   75: iload 10
    //   77: aload 7
    //   79: arraylength
    //   80: if_icmpge +22 -> 102
    //   83: aload 9
    //   85: iload 10
    //   87: aload 7
    //   89: iload 10
    //   91: aaload
    //   92: invokevirtual 46	java/security/cert/Certificate:getEncoded	()[B
    //   95: aastore
    //   96: iinc 10 1
    //   99: goto -24 -> 75
    //   102: aload_2
    //   103: invokevirtual 30	java/util/jar/JarFile:close	()V
    //   106: aload 9
    //   108: areturn
    //   109: astore 11
    //   111: aload 11
    //   113: ldc 32
    //   115: iconst_0
    //   116: anewarray 4	java/lang/Object
    //   119: invokestatic 38	com/google/android/finsky/utils/FinskyLog:e	(Ljava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)V
    //   122: goto -16 -> 106
    //   125: astore_3
    //   126: aload_3
    //   127: ldc 48
    //   129: iconst_0
    //   130: anewarray 4	java/lang/Object
    //   133: invokestatic 38	com/google/android/finsky/utils/FinskyLog:e	(Ljava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)V
    //   136: aload_1
    //   137: ifnull +7 -> 144
    //   140: aload_1
    //   141: invokevirtual 30	java/util/jar/JarFile:close	()V
    //   144: aconst_null
    //   145: areturn
    //   146: astore 6
    //   148: aload 6
    //   150: ldc 32
    //   152: iconst_0
    //   153: anewarray 4	java/lang/Object
    //   156: invokestatic 38	com/google/android/finsky/utils/FinskyLog:e	(Ljava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)V
    //   159: goto -15 -> 144
    //   162: astore 4
    //   164: aload_1
    //   165: ifnull +7 -> 172
    //   168: aload_1
    //   169: invokevirtual 30	java/util/jar/JarFile:close	()V
    //   172: aload 4
    //   174: athrow
    //   175: astore 5
    //   177: aload 5
    //   179: ldc 32
    //   181: iconst_0
    //   182: anewarray 4	java/lang/Object
    //   185: invokestatic 38	com/google/android/finsky/utils/FinskyLog:e	(Ljava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)V
    //   188: goto -16 -> 172
    //   191: astore 4
    //   193: aload_2
    //   194: astore_1
    //   195: goto -31 -> 164
    //   198: astore_3
    //   199: aload_2
    //   200: astore_1
    //   201: goto -75 -> 126
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	204	0	paramString	java.lang.String
    //   1	200	1	localObject1	Object
    //   10	190	2	localJarFile	java.util.jar.JarFile
    //   125	2	3	localException1	java.lang.Exception
    //   198	1	3	localException2	java.lang.Exception
    //   162	11	4	localObject2	Object
    //   191	1	4	localObject3	Object
    //   175	3	5	localIOException1	java.io.IOException
    //   146	3	6	localIOException2	java.io.IOException
    //   21	67	7	arrayOfCertificate	java.security.cert.Certificate[]
    //   44	3	8	localIOException3	java.io.IOException
    //   70	37	9	arrayOfByte	byte[][]
    //   73	24	10	i	int
    //   109	3	11	localIOException4	java.io.IOException
    // Exception table:
    //   from	to	target	type
    //   38	42	44	java/io/IOException
    //   102	106	109	java/io/IOException
    //   2	11	125	java/lang/Exception
    //   140	144	146	java/io/IOException
    //   2	11	162	finally
    //   126	136	162	finally
    //   168	172	175	java/io/IOException
    //   11	23	191	finally
    //   28	34	191	finally
    //   34	38	191	finally
    //   60	72	191	finally
    //   75	96	191	finally
    //   11	23	198	java/lang/Exception
    //   28	34	198	java/lang/Exception
    //   34	38	198	java/lang/Exception
    //   60	72	198	java/lang/Exception
    //   75	96	198	java/lang/Exception
  }
  
  /* Error */
  private static java.security.cert.Certificate[] loadCertificates(java.util.jar.JarFile paramJarFile, java.util.jar.JarEntry paramJarEntry)
    throws java.io.IOException
  {
    // Byte code:
    //   0: sipush 8192
    //   3: newarray byte
    //   5: astore_2
    //   6: aconst_null
    //   7: astore_3
    //   8: new 50	java/io/BufferedInputStream
    //   11: dup
    //   12: aload_0
    //   13: aload_1
    //   14: invokevirtual 54	java/util/jar/JarFile:getInputStream	(Ljava/util/zip/ZipEntry;)Ljava/io/InputStream;
    //   17: invokespecial 57	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   20: astore 4
    //   22: aload 4
    //   24: aload_2
    //   25: iconst_0
    //   26: sipush 8192
    //   29: invokevirtual 63	java/io/InputStream:read	([BII)I
    //   32: istore 6
    //   34: iload 6
    //   36: iconst_m1
    //   37: if_icmpne -15 -> 22
    //   40: aload 4
    //   42: invokestatic 69	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
    //   45: aload_1
    //   46: ifnull +17 -> 63
    //   49: aload_1
    //   50: invokevirtual 75	java/util/jar/JarEntry:getCertificates	()[Ljava/security/cert/Certificate;
    //   53: areturn
    //   54: astore 5
    //   56: aload_3
    //   57: invokestatic 69	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
    //   60: aload 5
    //   62: athrow
    //   63: aconst_null
    //   64: areturn
    //   65: astore 5
    //   67: aload 4
    //   69: astore_3
    //   70: goto -14 -> 56
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	73	0	paramJarFile	java.util.jar.JarFile
    //   0	73	1	paramJarEntry	java.util.jar.JarEntry
    //   5	20	2	arrayOfByte	byte[]
    //   7	63	3	localObject1	Object
    //   20	48	4	localBufferedInputStream	java.io.BufferedInputStream
    //   54	7	5	localObject2	Object
    //   65	1	5	localObject3	Object
    //   32	6	6	i	int
    // Exception table:
    //   from	to	target	type
    //   8	22	54	finally
    //   22	34	65	finally
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.vending.verifier.CertificateUtils
 * JD-Core Version:    0.7.0.1
 */