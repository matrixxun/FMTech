package com.google.android.gms.internal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public final class zzu
{
  protected static final Comparator<byte[]> zzav = new Comparator() {};
  private List<byte[]> zzar = new LinkedList();
  private List<byte[]> zzas = new ArrayList(64);
  private int zzat = 0;
  private final int zzau;
  
  public zzu(int paramInt)
  {
    this.zzau = paramInt;
  }
  
  private void zzx()
  {
    try
    {
      if (this.zzat > this.zzau)
      {
        byte[] arrayOfByte = (byte[])this.zzar.remove(0);
        this.zzas.remove(arrayOfByte);
        this.zzat -= arrayOfByte.length;
      }
      return;
    }
    finally {}
  }
  
  /* Error */
  public final void zza(byte[] paramArrayOfByte)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_1
    //   3: ifnull +18 -> 21
    //   6: aload_1
    //   7: arraylength
    //   8: istore_3
    //   9: aload_0
    //   10: getfield 40	com/google/android/gms/internal/zzu:zzau	I
    //   13: istore 4
    //   15: iload_3
    //   16: iload 4
    //   18: if_icmple +6 -> 24
    //   21: aload_0
    //   22: monitorexit
    //   23: return
    //   24: aload_0
    //   25: getfield 30	com/google/android/gms/internal/zzu:zzar	Ljava/util/List;
    //   28: aload_1
    //   29: invokeinterface 57 2 0
    //   34: pop
    //   35: aload_0
    //   36: getfield 36	com/google/android/gms/internal/zzu:zzas	Ljava/util/List;
    //   39: aload_1
    //   40: getstatic 23	com/google/android/gms/internal/zzu:zzav	Ljava/util/Comparator;
    //   43: invokestatic 63	java/util/Collections:binarySearch	(Ljava/util/List;Ljava/lang/Object;Ljava/util/Comparator;)I
    //   46: istore 6
    //   48: iload 6
    //   50: ifge +10 -> 60
    //   53: iconst_m1
    //   54: iload 6
    //   56: ineg
    //   57: iadd
    //   58: istore 6
    //   60: aload_0
    //   61: getfield 36	com/google/android/gms/internal/zzu:zzas	Ljava/util/List;
    //   64: iload 6
    //   66: aload_1
    //   67: invokeinterface 66 3 0
    //   72: aload_0
    //   73: aload_0
    //   74: getfield 38	com/google/android/gms/internal/zzu:zzat	I
    //   77: aload_1
    //   78: arraylength
    //   79: iadd
    //   80: putfield 38	com/google/android/gms/internal/zzu:zzat	I
    //   83: aload_0
    //   84: invokespecial 68	com/google/android/gms/internal/zzu:zzx	()V
    //   87: goto -66 -> 21
    //   90: astore_2
    //   91: aload_0
    //   92: monitorexit
    //   93: aload_2
    //   94: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	95	0	this	zzu
    //   0	95	1	paramArrayOfByte	byte[]
    //   90	4	2	localObject	Object
    //   8	11	3	i	int
    //   13	6	4	j	int
    //   46	19	6	k	int
    // Exception table:
    //   from	to	target	type
    //   6	15	90	finally
    //   24	48	90	finally
    //   60	87	90	finally
  }
  
  /* Error */
  public final byte[] zzb(int paramInt)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: iconst_0
    //   3: istore_2
    //   4: iload_2
    //   5: aload_0
    //   6: getfield 36	com/google/android/gms/internal/zzu:zzas	Ljava/util/List;
    //   9: invokeinterface 74 1 0
    //   14: if_icmpge +71 -> 85
    //   17: aload_0
    //   18: getfield 36	com/google/android/gms/internal/zzu:zzas	Ljava/util/List;
    //   21: iload_2
    //   22: invokeinterface 77 2 0
    //   27: checkcast 49	[B
    //   30: astore 4
    //   32: aload 4
    //   34: arraylength
    //   35: iload_1
    //   36: if_icmplt +43 -> 79
    //   39: aload_0
    //   40: aload_0
    //   41: getfield 38	com/google/android/gms/internal/zzu:zzat	I
    //   44: aload 4
    //   46: arraylength
    //   47: isub
    //   48: putfield 38	com/google/android/gms/internal/zzu:zzat	I
    //   51: aload_0
    //   52: getfield 36	com/google/android/gms/internal/zzu:zzas	Ljava/util/List;
    //   55: iload_2
    //   56: invokeinterface 47 2 0
    //   61: pop
    //   62: aload_0
    //   63: getfield 30	com/google/android/gms/internal/zzu:zzar	Ljava/util/List;
    //   66: aload 4
    //   68: invokeinterface 52 2 0
    //   73: pop
    //   74: aload_0
    //   75: monitorexit
    //   76: aload 4
    //   78: areturn
    //   79: iinc 2 1
    //   82: goto -78 -> 4
    //   85: iload_1
    //   86: newarray byte
    //   88: astore 4
    //   90: goto -16 -> 74
    //   93: astore_3
    //   94: aload_0
    //   95: monitorexit
    //   96: aload_3
    //   97: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	98	0	this	zzu
    //   0	98	1	paramInt	int
    //   3	77	2	i	int
    //   93	4	3	localObject	Object
    //   30	59	4	arrayOfByte	byte[]
    // Exception table:
    //   from	to	target	type
    //   4	74	93	finally
    //   85	90	93	finally
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzu
 * JD-Core Version:    0.7.0.1
 */