package org.keyczar.util;

import com.google.gson.Gson;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.keyczar.exceptions.Base64DecodingException;
import org.keyczar.exceptions.KeyczarException;

public final class Util
{
  private static final ConcurrentLinkedQueue<MessageDigest> DIGEST_QUEUE = new ConcurrentLinkedQueue();
  private static final ThreadLocal<Gson> GSON_THREAD_LOCAL = new ThreadLocal() {};
  private static final ConcurrentLinkedQueue<SecureRandom> RAND_QUEUE = new ConcurrentLinkedQueue();
  
  public static byte[] cat(byte[]... paramVarArgs)
  {
    int i = 0;
    for (int j = 0; j < 4; j++) {
      i += paramVarArgs[j].length;
    }
    byte[] arrayOfByte1 = new byte[i];
    int k = 0;
    for (int m = 0; m < 4; m++)
    {
      byte[] arrayOfByte2 = paramVarArgs[m];
      System.arraycopy(arrayOfByte2, 0, arrayOfByte1, k, arrayOfByte2.length);
      k += arrayOfByte2.length;
    }
    return arrayOfByte1;
  }
  
  public static BigInteger decodeBigInteger(String paramString)
    throws Base64DecodingException
  {
    return new BigInteger(Base64Coder.decodeWebSafe(paramString));
  }
  
  public static byte[] fromInt(int paramInt)
  {
    byte[] arrayOfByte = new byte[4];
    arrayOfByte[0] = ((byte)(paramInt >> 24));
    arrayOfByte[1] = ((byte)(paramInt >> 16));
    arrayOfByte[2] = ((byte)(paramInt >> 8));
    arrayOfByte[3] = ((byte)paramInt);
    return arrayOfByte;
  }
  
  public static Gson gson()
  {
    return (Gson)GSON_THREAD_LOCAL.get();
  }
  
  public static byte[] hash(byte[]... paramVarArgs)
    throws KeyczarException
  {
    Object localObject = (MessageDigest)DIGEST_QUEUE.poll();
    if (localObject == null) {}
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("SHA-1");
      localObject = localMessageDigest;
      int i = paramVarArgs.length;
      for (int j = 0; j < i; j++) {
        ((MessageDigest)localObject).update(paramVarArgs[j]);
      }
      arrayOfByte = ((MessageDigest)localObject).digest();
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      throw new KeyczarException(localNoSuchAlgorithmException);
    }
    byte[] arrayOfByte;
    DIGEST_QUEUE.add(localObject);
    return arrayOfByte;
  }
  
  public static byte[] lenPrefix(byte[] paramArrayOfByte)
  {
    if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0)) {
      return fromInt(0);
    }
    return ByteBuffer.allocate(4 + paramArrayOfByte.length).putInt(paramArrayOfByte.length).put(paramArrayOfByte).array();
  }
  
  public static byte[] prefixHash(byte[]... paramVarArgs)
    throws KeyczarException
  {
    Object localObject = (MessageDigest)DIGEST_QUEUE.poll();
    if (localObject == null) {}
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("SHA-1");
      localObject = localMessageDigest;
      int i = paramVarArgs.length;
      for (int j = 0; j < i; j++)
      {
        byte[] arrayOfByte2 = paramVarArgs[j];
        ((MessageDigest)localObject).update(fromInt(arrayOfByte2.length));
        ((MessageDigest)localObject).update(arrayOfByte2);
      }
      arrayOfByte1 = ((MessageDigest)localObject).digest();
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      throw new KeyczarException(localNoSuchAlgorithmException);
    }
    byte[] arrayOfByte1;
    DIGEST_QUEUE.add(localObject);
    return arrayOfByte1;
  }
  
  public static void rand(byte[] paramArrayOfByte)
  {
    SecureRandom localSecureRandom = (SecureRandom)RAND_QUEUE.poll();
    if (localSecureRandom == null) {
      localSecureRandom = new SecureRandom();
    }
    localSecureRandom.nextBytes(paramArrayOfByte);
    RAND_QUEUE.add(localSecureRandom);
  }
  
  public static byte[] rand(int paramInt)
  {
    byte[] arrayOfByte = new byte[paramInt];
    rand(arrayOfByte);
    return arrayOfByte;
  }
  
  public static boolean safeArrayEquals(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    if (paramArrayOfByte1 == null) {
      if (paramArrayOfByte1 != paramArrayOfByte2) {}
    }
    int i;
    do
    {
      return true;
      return false;
      if (paramArrayOfByte1.length != paramArrayOfByte2.length) {
        return false;
      }
      i = 0;
      for (int j = 0; j < paramArrayOfByte1.length; j++) {
        i = (byte)(i | paramArrayOfByte1[j] ^ paramArrayOfByte2[j]);
      }
    } while (i == 0);
    return false;
  }
  
  public static byte[] stripLeadingZeros(byte[] paramArrayOfByte)
  {
    for (int i = 0; (i < paramArrayOfByte.length) && (paramArrayOfByte[i] == 0); i++) {}
    if (i == 0) {
      return paramArrayOfByte;
    }
    byte[] arrayOfByte = new byte[paramArrayOfByte.length - i];
    System.arraycopy(paramArrayOfByte, i, arrayOfByte, 0, arrayOfByte.length);
    return arrayOfByte;
  }
  
  public static int toInt(byte[] paramArrayOfByte)
  {
    return 0x0 | (0xFF & paramArrayOfByte[0]) << 24 | (0xFF & paramArrayOfByte[1]) << 16 | (0xFF & paramArrayOfByte[2]) << 8 | 0xFF & paramArrayOfByte[3];
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     org.keyczar.util.Util
 * JD-Core Version:    0.7.0.1
 */