package com.google.android.gms.internal;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import com.google.android.gms.common.api.GoogleApiClient;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.UUID;

public abstract class zzak
  implements zzaj
{
  protected static GoogleApiClient zznq;
  private static zzar zznr;
  protected MotionEvent zznn;
  protected DisplayMetrics zzno;
  protected zzaq zznp;
  
  protected zzak(Context paramContext, zzaq paramzzaq, zzar paramzzar)
  {
    this.zznp = paramzzaq;
    zznr = paramzzar;
    try
    {
      this.zzno = paramContext.getResources().getDisplayMetrics();
      return;
    }
    catch (UnsupportedOperationException localUnsupportedOperationException)
    {
      this.zzno = new DisplayMetrics();
      this.zzno.density = 1.0F;
    }
  }
  
  private static void zzQ()
  {
    zznr.reset();
  }
  
  private static byte[] zzR()
    throws IOException
  {
    return zznr.zzad();
  }
  
  protected static void zza(int paramInt, long paramLong)
    throws IOException
  {
    zznr.zzb(paramInt, paramLong);
  }
  
  protected static void zza(int paramInt, String paramString)
    throws IOException
  {
    zznr.zzb(paramInt, paramString);
  }
  
  public final void addTouchEvent(MotionEvent paramMotionEvent)
  {
    if (paramMotionEvent.getAction() == 1)
    {
      if (this.zznn != null) {
        this.zznn.recycle();
      }
      this.zznn = MotionEvent.obtain(paramMotionEvent);
    }
  }
  
  /* Error */
  final String zza(Context paramContext, String paramString, boolean paramBoolean)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: invokestatic 93	com/google/android/gms/internal/zzak:zzQ	()V
    //   5: iload_3
    //   6: ifeq +30 -> 36
    //   9: aload_0
    //   10: aload_1
    //   11: invokevirtual 97	com/google/android/gms/internal/zzak:zzd	(Landroid/content/Context;)V
    //   14: invokestatic 99	com/google/android/gms/internal/zzak:zzR	()[B
    //   17: astore 8
    //   19: aload_0
    //   20: monitorexit
    //   21: aload 8
    //   23: arraylength
    //   24: ifne +35 -> 59
    //   27: iconst_5
    //   28: invokestatic 105	java/lang/Integer:toString	(I)Ljava/lang/String;
    //   31: astore 16
    //   33: aload 16
    //   35: areturn
    //   36: aload_0
    //   37: aload_1
    //   38: invokevirtual 108	com/google/android/gms/internal/zzak:zzc	(Landroid/content/Context;)V
    //   41: goto -27 -> 14
    //   44: astore 7
    //   46: aload_0
    //   47: monitorexit
    //   48: aload 7
    //   50: athrow
    //   51: astore 6
    //   53: bipush 7
    //   55: invokestatic 105	java/lang/Integer:toString	(I)Ljava/lang/String;
    //   58: areturn
    //   59: aload 8
    //   61: arraylength
    //   62: sipush 239
    //   65: if_icmple +17 -> 82
    //   68: invokestatic 93	com/google/android/gms/internal/zzak:zzQ	()V
    //   71: bipush 20
    //   73: lconst_1
    //   74: invokestatic 110	com/google/android/gms/internal/zzak:zza	(IJ)V
    //   77: invokestatic 99	com/google/android/gms/internal/zzak:zzR	()[B
    //   80: astore 8
    //   82: aload 8
    //   84: arraylength
    //   85: sipush 239
    //   88: if_icmpge +175 -> 263
    //   91: sipush 239
    //   94: aload 8
    //   96: arraylength
    //   97: isub
    //   98: newarray byte
    //   100: astore 15
    //   102: new 112	java/security/SecureRandom
    //   105: dup
    //   106: invokespecial 113	java/security/SecureRandom:<init>	()V
    //   109: aload 15
    //   111: invokevirtual 117	java/security/SecureRandom:nextBytes	([B)V
    //   114: sipush 240
    //   117: invokestatic 123	java/nio/ByteBuffer:allocate	(I)Ljava/nio/ByteBuffer;
    //   120: aload 8
    //   122: arraylength
    //   123: i2b
    //   124: invokevirtual 127	java/nio/ByteBuffer:put	(B)Ljava/nio/ByteBuffer;
    //   127: aload 8
    //   129: invokevirtual 130	java/nio/ByteBuffer:put	([B)Ljava/nio/ByteBuffer;
    //   132: aload 15
    //   134: invokevirtual 130	java/nio/ByteBuffer:put	([B)Ljava/nio/ByteBuffer;
    //   137: invokevirtual 133	java/nio/ByteBuffer:array	()[B
    //   140: astore 10
    //   142: ldc 135
    //   144: invokestatic 141	java/security/MessageDigest:getInstance	(Ljava/lang/String;)Ljava/security/MessageDigest;
    //   147: astore 11
    //   149: aload 11
    //   151: aload 10
    //   153: invokevirtual 144	java/security/MessageDigest:update	([B)V
    //   156: aload 11
    //   158: invokevirtual 147	java/security/MessageDigest:digest	()[B
    //   161: astore 12
    //   163: sipush 256
    //   166: invokestatic 123	java/nio/ByteBuffer:allocate	(I)Ljava/nio/ByteBuffer;
    //   169: aload 12
    //   171: invokevirtual 130	java/nio/ByteBuffer:put	([B)Ljava/nio/ByteBuffer;
    //   174: aload 10
    //   176: invokevirtual 130	java/nio/ByteBuffer:put	([B)Ljava/nio/ByteBuffer;
    //   179: invokevirtual 133	java/nio/ByteBuffer:array	()[B
    //   182: astore 13
    //   184: sipush 256
    //   187: newarray byte
    //   189: astore 14
    //   191: new 149	com/google/android/gms/internal/zzai
    //   194: dup
    //   195: invokespecial 150	com/google/android/gms/internal/zzai:<init>	()V
    //   198: aload 13
    //   200: aload 14
    //   202: invokevirtual 153	com/google/android/gms/internal/zzai:zzb	([B[B)V
    //   205: aload_2
    //   206: ifnull +45 -> 251
    //   209: aload_2
    //   210: invokevirtual 158	java/lang/String:length	()I
    //   213: ifle +38 -> 251
    //   216: aload_2
    //   217: invokevirtual 158	java/lang/String:length	()I
    //   220: bipush 32
    //   222: if_icmple +11 -> 233
    //   225: aload_2
    //   226: iconst_0
    //   227: bipush 32
    //   229: invokevirtual 162	java/lang/String:substring	(II)Ljava/lang/String;
    //   232: astore_2
    //   233: new 164	com/google/android/gms/internal/zzaia
    //   236: dup
    //   237: aload_2
    //   238: ldc 166
    //   240: invokevirtual 170	java/lang/String:getBytes	(Ljava/lang/String;)[B
    //   243: invokespecial 172	com/google/android/gms/internal/zzaia:<init>	([B)V
    //   246: aload 14
    //   248: invokevirtual 175	com/google/android/gms/internal/zzaia:zzS	([B)V
    //   251: aload_0
    //   252: getfield 25	com/google/android/gms/internal/zzak:zznp	Lcom/google/android/gms/internal/zzaq;
    //   255: aload 14
    //   257: invokeinterface 181 2 0
    //   262: areturn
    //   263: sipush 240
    //   266: invokestatic 123	java/nio/ByteBuffer:allocate	(I)Ljava/nio/ByteBuffer;
    //   269: aload 8
    //   271: arraylength
    //   272: i2b
    //   273: invokevirtual 127	java/nio/ByteBuffer:put	(B)Ljava/nio/ByteBuffer;
    //   276: aload 8
    //   278: invokevirtual 130	java/nio/ByteBuffer:put	([B)Ljava/nio/ByteBuffer;
    //   281: invokevirtual 133	java/nio/ByteBuffer:array	()[B
    //   284: astore 9
    //   286: aload 9
    //   288: astore 10
    //   290: goto -148 -> 142
    //   293: astore 5
    //   295: bipush 7
    //   297: invokestatic 105	java/lang/Integer:toString	(I)Ljava/lang/String;
    //   300: areturn
    //   301: astore 4
    //   303: iconst_3
    //   304: invokestatic 105	java/lang/Integer:toString	(I)Ljava/lang/String;
    //   307: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	308	0	this	zzak
    //   0	308	1	paramContext	Context
    //   0	308	2	paramString	String
    //   0	308	3	paramBoolean	boolean
    //   301	1	4	localIOException	IOException
    //   293	1	5	localUnsupportedEncodingException	java.io.UnsupportedEncodingException
    //   51	1	6	localNoSuchAlgorithmException	java.security.NoSuchAlgorithmException
    //   44	5	7	localObject1	Object
    //   17	260	8	arrayOfByte1	byte[]
    //   284	3	9	arrayOfByte2	byte[]
    //   140	149	10	localObject2	Object
    //   147	10	11	localMessageDigest	java.security.MessageDigest
    //   161	9	12	arrayOfByte3	byte[]
    //   182	17	13	arrayOfByte4	byte[]
    //   189	67	14	arrayOfByte5	byte[]
    //   100	33	15	arrayOfByte6	byte[]
    //   31	3	16	str	String
    // Exception table:
    //   from	to	target	type
    //   2	5	44	finally
    //   9	14	44	finally
    //   14	21	44	finally
    //   36	41	44	finally
    //   46	48	44	finally
    //   0	2	51	java/security/NoSuchAlgorithmException
    //   21	33	51	java/security/NoSuchAlgorithmException
    //   48	51	51	java/security/NoSuchAlgorithmException
    //   59	82	51	java/security/NoSuchAlgorithmException
    //   82	142	51	java/security/NoSuchAlgorithmException
    //   142	205	51	java/security/NoSuchAlgorithmException
    //   209	233	51	java/security/NoSuchAlgorithmException
    //   233	251	51	java/security/NoSuchAlgorithmException
    //   251	263	51	java/security/NoSuchAlgorithmException
    //   263	286	51	java/security/NoSuchAlgorithmException
    //   0	2	293	java/io/UnsupportedEncodingException
    //   21	33	293	java/io/UnsupportedEncodingException
    //   48	51	293	java/io/UnsupportedEncodingException
    //   59	82	293	java/io/UnsupportedEncodingException
    //   82	142	293	java/io/UnsupportedEncodingException
    //   142	205	293	java/io/UnsupportedEncodingException
    //   209	233	293	java/io/UnsupportedEncodingException
    //   233	251	293	java/io/UnsupportedEncodingException
    //   251	263	293	java/io/UnsupportedEncodingException
    //   263	286	293	java/io/UnsupportedEncodingException
    //   0	2	301	java/io/IOException
    //   21	33	301	java/io/IOException
    //   48	51	301	java/io/IOException
    //   59	82	301	java/io/IOException
    //   82	142	301	java/io/IOException
    //   142	205	301	java/io/IOException
    //   209	233	301	java/io/IOException
    //   233	251	301	java/io/IOException
    //   251	263	301	java/io/IOException
    //   263	286	301	java/io/IOException
  }
  
  public final void zza(int paramInt1, int paramInt2, int paramInt3)
  {
    if (this.zznn != null) {
      this.zznn.recycle();
    }
    this.zznn = MotionEvent.obtain(0L, paramInt3, 1, paramInt1 * this.zzno.density, paramInt2 * this.zzno.density, 0.0F, 0.0F, 0, 0.0F, 0.0F, 0, 0);
  }
  
  public final String zzb(Context paramContext)
  {
    return zza(paramContext, null, false);
  }
  
  public final String zzb(Context paramContext, String paramString)
  {
    return zza(paramContext, paramString, true);
  }
  
  protected abstract void zzc(Context paramContext);
  
  protected abstract void zzd(Context paramContext);
  
  protected final String zzk(String paramString)
  {
    if ((paramString != null) && (paramString.matches("^[a-fA-F0-9]{8}-([a-fA-F0-9]{4}-){3}[a-fA-F0-9]{12}$")))
    {
      UUID localUUID = UUID.fromString(paramString);
      byte[] arrayOfByte = new byte[16];
      ByteBuffer localByteBuffer = ByteBuffer.wrap(arrayOfByte);
      localByteBuffer.putLong(localUUID.getMostSignificantBits());
      localByteBuffer.putLong(localUUID.getLeastSignificantBits());
      paramString = this.zznp.zza$5a238448(arrayOfByte);
    }
    return paramString;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzak
 * JD-Core Version:    0.7.0.1
 */