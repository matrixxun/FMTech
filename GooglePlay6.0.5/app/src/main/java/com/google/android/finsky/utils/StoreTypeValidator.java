package com.google.android.finsky.utils;

public final class StoreTypeValidator
{
  private static Boolean sCachedIsValid = null;
  
  /* Error */
  public static boolean isValid(android.content.Context paramContext)
  {
    // Byte code:
    //   0: ldc 2
    //   2: monitorenter
    //   3: getstatic 10	com/google/android/finsky/utils/StoreTypeValidator:sCachedIsValid	Ljava/lang/Boolean;
    //   6: ifnonnull +25 -> 31
    //   9: aload_0
    //   10: invokestatic 17	com/google/android/gms/common/GooglePlayServicesUtil:isSidewinderDevice	(Landroid/content/Context;)Z
    //   13: ifeq +30 -> 43
    //   16: ldc 19
    //   18: astore_3
    //   19: aload_3
    //   20: ldc 21
    //   22: invokevirtual 27	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   25: invokestatic 33	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
    //   28: putstatic 10	com/google/android/finsky/utils/StoreTypeValidator:sCachedIsValid	Ljava/lang/Boolean;
    //   31: getstatic 10	com/google/android/finsky/utils/StoreTypeValidator:sCachedIsValid	Ljava/lang/Boolean;
    //   34: invokevirtual 37	java/lang/Boolean:booleanValue	()Z
    //   37: istore_2
    //   38: ldc 2
    //   40: monitorexit
    //   41: iload_2
    //   42: ireturn
    //   43: ldc 21
    //   45: astore_3
    //   46: goto -27 -> 19
    //   49: astore_1
    //   50: ldc 2
    //   52: monitorexit
    //   53: aload_1
    //   54: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	55	0	paramContext	android.content.Context
    //   49	5	1	localObject	Object
    //   37	5	2	bool	boolean
    //   18	28	3	str	java.lang.String
    // Exception table:
    //   from	to	target	type
    //   3	16	49	finally
    //   19	31	49	finally
    //   31	38	49	finally
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.StoreTypeValidator
 * JD-Core Version:    0.7.0.1
 */