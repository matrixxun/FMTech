package com.google.android.gms.internal;

import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public final class zzas
{
  private final zzaq zznp;
  private final SecureRandom zzok;
  
  public zzas(zzaq paramzzaq)
  {
    this.zznp = paramzzaq;
    this.zzok = null;
  }
  
  public final byte[] zzc(byte[] paramArrayOfByte, String paramString)
    throws zzas.zza
  {
    if (paramArrayOfByte.length != 16) {
      throw new zza();
    }
    try
    {
      arrayOfByte1 = this.zznp.zza(paramString, false);
      if (arrayOfByte1.length <= 16) {
        throw new zza();
      }
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      byte[] arrayOfByte1;
      throw new zza(localNoSuchAlgorithmException);
      ByteBuffer localByteBuffer = ByteBuffer.allocate(arrayOfByte1.length);
      localByteBuffer.put(arrayOfByte1);
      localByteBuffer.flip();
      byte[] arrayOfByte2 = new byte[16];
      byte[] arrayOfByte3 = new byte[-16 + arrayOfByte1.length];
      localByteBuffer.get(arrayOfByte2);
      localByteBuffer.get(arrayOfByte3);
      SecretKeySpec localSecretKeySpec = new SecretKeySpec(paramArrayOfByte, "AES");
      Cipher localCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
      localCipher.init(2, localSecretKeySpec, new IvParameterSpec(arrayOfByte2));
      byte[] arrayOfByte4 = localCipher.doFinal(arrayOfByte3);
      return arrayOfByte4;
    }
    catch (InvalidKeyException localInvalidKeyException)
    {
      throw new zza(localInvalidKeyException);
    }
    catch (IllegalBlockSizeException localIllegalBlockSizeException)
    {
      throw new zza(localIllegalBlockSizeException);
    }
    catch (NoSuchPaddingException localNoSuchPaddingException)
    {
      throw new zza(localNoSuchPaddingException);
    }
    catch (BadPaddingException localBadPaddingException)
    {
      throw new zza(localBadPaddingException);
    }
    catch (InvalidAlgorithmParameterException localInvalidAlgorithmParameterException)
    {
      throw new zza(localInvalidAlgorithmParameterException);
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      throw new zza(localIllegalArgumentException);
    }
  }
  
  public final byte[] zzl(String paramString)
    throws zzas.zza
  {
    int i = 0;
    byte[] arrayOfByte1;
    try
    {
      arrayOfByte1 = this.zznp.zza(paramString, false);
      if (arrayOfByte1.length != 32) {
        throw new zza();
      }
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      throw new zza(localIllegalArgumentException);
    }
    ByteBuffer localByteBuffer = ByteBuffer.wrap(arrayOfByte1, 4, 16);
    byte[] arrayOfByte2 = new byte[16];
    localByteBuffer.get(arrayOfByte2);
    while (i < 16)
    {
      arrayOfByte2[i] = ((byte)(0x44 ^ arrayOfByte2[i]));
      i++;
    }
    return arrayOfByte2;
  }
  
  public final class zza
    extends Exception
  {
    public zza() {}
    
    public zza(Throwable paramThrowable)
    {
      super();
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzas
 * JD-Core Version:    0.7.0.1
 */