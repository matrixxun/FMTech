package com.google.android.finsky.utils;

import android.util.Base64;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class Sha1Util
{
  /* Error */
  public static DigestResult digest(java.io.InputStream paramInputStream)
    throws IOException
  {
    // Byte code:
    //   0: sipush 8192
    //   3: newarray byte
    //   5: astore_1
    //   6: lconst_0
    //   7: lstore_2
    //   8: ldc 12
    //   10: invokestatic 18	java/security/MessageDigest:getInstance	(Ljava/lang/String;)Ljava/security/MessageDigest;
    //   13: astore 5
    //   15: aload_0
    //   16: aload_1
    //   17: invokevirtual 24	java/io/InputStream:read	([B)I
    //   20: istore 7
    //   22: iload 7
    //   24: iflt +43 -> 67
    //   27: iload 7
    //   29: ifle -14 -> 15
    //   32: aload 5
    //   34: aload_1
    //   35: iconst_0
    //   36: iload 7
    //   38: invokevirtual 28	java/security/MessageDigest:update	([BII)V
    //   41: lload_2
    //   42: iload 7
    //   44: i2l
    //   45: ladd
    //   46: lstore_2
    //   47: goto -32 -> 15
    //   50: astore 4
    //   52: ldc 30
    //   54: iconst_0
    //   55: anewarray 4	java/lang/Object
    //   58: invokestatic 36	com/google/android/finsky/utils/FinskyLog:wtf	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   61: aload_0
    //   62: invokestatic 42	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
    //   65: aconst_null
    //   66: areturn
    //   67: new 44	com/google/android/finsky/utils/Sha1Util$DigestResult
    //   70: dup
    //   71: aload 5
    //   73: invokevirtual 47	java/security/MessageDigest:digest	()[B
    //   76: bipush 11
    //   78: invokestatic 53	android/util/Base64:encodeToString	([BI)Ljava/lang/String;
    //   81: lload_2
    //   82: iconst_0
    //   83: invokespecial 57	com/google/android/finsky/utils/Sha1Util$DigestResult:<init>	(Ljava/lang/String;JB)V
    //   86: astore 8
    //   88: aload_0
    //   89: invokestatic 42	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
    //   92: aload 8
    //   94: areturn
    //   95: astore 6
    //   97: aload_0
    //   98: invokestatic 42	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
    //   101: aload 6
    //   103: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	104	0	paramInputStream	java.io.InputStream
    //   5	30	1	arrayOfByte	byte[]
    //   7	75	2	l	long
    //   50	1	4	localNoSuchAlgorithmException	NoSuchAlgorithmException
    //   13	59	5	localMessageDigest	MessageDigest
    //   95	7	6	localObject	Object
    //   20	23	7	i	int
    //   86	7	8	localDigestResult	DigestResult
    // Exception table:
    //   from	to	target	type
    //   8	15	50	java/security/NoSuchAlgorithmException
    //   15	22	95	finally
    //   32	41	95	finally
    //   67	88	95	finally
  }
  
  public static String secureHash(byte[] paramArrayOfByte)
  {
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("SHA-1");
      localMessageDigest.update(paramArrayOfByte);
      return Base64.encodeToString(localMessageDigest.digest(), 11);
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {}
    return null;
  }
  
  public static final class DigestResult
  {
    public final long byteCount;
    public final String hashBase64;
    
    private DigestResult(String paramString, long paramLong)
    {
      this.hashBase64 = paramString;
      this.byteCount = paramLong;
    }
  }
  
  public static final class DigestStream
    extends FilterOutputStream
  {
    private long mBytesWritten;
    private MessageDigest mDigest;
    private final OutputStream mOutputStream;
    
    public DigestStream(OutputStream paramOutputStream)
    {
      super();
      this.mOutputStream = paramOutputStream;
      try
      {
        this.mDigest = MessageDigest.getInstance("SHA-1");
        this.mBytesWritten = 0L;
        return;
      }
      catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
      {
        throw new RuntimeException(localNoSuchAlgorithmException);
      }
    }
    
    public final Sha1Util.DigestResult getDigest()
    {
      return new Sha1Util.DigestResult(Base64.encodeToString(this.mDigest.digest(), 11), this.mBytesWritten, (byte)0);
    }
    
    public final void write(int paramInt)
      throws IOException
    {
      this.mDigest.update((byte)paramInt);
      this.mBytesWritten = (1L + this.mBytesWritten);
      this.mOutputStream.write(paramInt);
    }
    
    public final void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
      throws IOException
    {
      this.mDigest.update(paramArrayOfByte, paramInt1, paramInt2);
      this.mBytesWritten += paramInt2;
      this.mOutputStream.write(paramArrayOfByte, paramInt1, paramInt2);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.Sha1Util
 * JD-Core Version:    0.7.0.1
 */