package com.google.android.finsky.installer;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageInstaller.Session;
import android.content.pm.PackageInstaller.SessionInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build.VERSION;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.appstate.PackageStateRepository;
import com.google.android.finsky.config.G;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.PackageManagerHelper;
import com.google.android.finsky.utils.Sets;
import com.google.android.finsky.utils.Utils;
import com.google.android.play.utils.config.GservicesValue;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@TargetApi(21)
public final class PackageInstallerImpl
  implements PackageInstallerFacade
{
  final Context mContext;
  private final Map<String, PackageInstaller.Session> mOpenSessionMap;
  private final PackageInstaller mPackageInstaller;
  private final Map<String, PackageInstaller.SessionInfo> mSessionInfoMap;
  
  public PackageInstallerImpl(Context paramContext)
  {
    this.mContext = paramContext;
    this.mPackageInstaller = this.mContext.getPackageManager().getPackageInstaller();
    this.mSessionInfoMap = new HashMap();
    Iterator localIterator = this.mPackageInstaller.getMySessions().iterator();
    while (localIterator.hasNext())
    {
      PackageInstaller.SessionInfo localSessionInfo1 = (PackageInstaller.SessionInfo)localIterator.next();
      String str = localSessionInfo1.getAppPackageName();
      PackageInstaller.SessionInfo localSessionInfo2 = (PackageInstaller.SessionInfo)this.mSessionInfoMap.put(str, localSessionInfo1);
      if (localSessionInfo2 != null)
      {
        Object[] arrayOfObject = new Object[3];
        arrayOfObject[0] = str;
        arrayOfObject[1] = Integer.valueOf(localSessionInfo2.getSessionId());
        arrayOfObject[2] = Integer.valueOf(localSessionInfo1.getSessionId());
        FinskyLog.w("Multiple sessions for %s found. Removing %d & keeping %d.", arrayOfObject);
      }
    }
    this.mOpenSessionMap = new HashMap();
  }
  
  /* Error */
  private void closeSession(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 98	com/google/android/finsky/installer/PackageInstallerImpl:mOpenSessionMap	Ljava/util/Map;
    //   6: aload_1
    //   7: invokeinterface 106 2 0
    //   12: checkcast 108	android/content/pm/PackageInstaller$Session
    //   15: astore_3
    //   16: aload_3
    //   17: ifnull +7 -> 24
    //   20: aload_3
    //   21: invokevirtual 111	android/content/pm/PackageInstaller$Session:close	()V
    //   24: aload_0
    //   25: monitorexit
    //   26: return
    //   27: astore 4
    //   29: iconst_2
    //   30: anewarray 4	java/lang/Object
    //   33: astore 5
    //   35: aload 5
    //   37: iconst_0
    //   38: aload_1
    //   39: aastore
    //   40: aload 5
    //   42: iconst_1
    //   43: aload 4
    //   45: invokevirtual 114	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   48: aastore
    //   49: ldc 116
    //   51: aload 5
    //   53: invokestatic 119	com/google/android/finsky/utils/FinskyLog:e	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   56: goto -32 -> 24
    //   59: astore_2
    //   60: aload_0
    //   61: monitorexit
    //   62: aload_2
    //   63: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	64	0	this	PackageInstallerImpl
    //   0	64	1	paramString	String
    //   59	4	2	localObject	Object
    //   15	6	3	localSession	PackageInstaller.Session
    //   27	17	4	localException	java.lang.Exception
    //   33	19	5	arrayOfObject	Object[]
    // Exception table:
    //   from	to	target	type
    //   20	24	27	java/lang/Exception
    //   2	16	59	finally
    //   20	24	59	finally
    //   29	56	59	finally
  }
  
  /* Error */
  private PackageInstaller.Session getSession(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 98	com/google/android/finsky/installer/PackageInstallerImpl:mOpenSessionMap	Ljava/util/Map;
    //   6: aload_1
    //   7: invokeinterface 128 2 0
    //   12: checkcast 108	android/content/pm/PackageInstaller$Session
    //   15: astore_3
    //   16: aload_3
    //   17: ifnull +56 -> 73
    //   20: aload_3
    //   21: invokevirtual 132	android/content/pm/PackageInstaller$Session:getNames	()[Ljava/lang/String;
    //   24: pop
    //   25: aload_3
    //   26: astore 5
    //   28: aload_0
    //   29: monitorexit
    //   30: aload 5
    //   32: areturn
    //   33: astore 16
    //   35: iconst_2
    //   36: anewarray 4	java/lang/Object
    //   39: astore 17
    //   41: aload 17
    //   43: iconst_0
    //   44: aload_1
    //   45: aastore
    //   46: aload 17
    //   48: iconst_1
    //   49: aload 16
    //   51: invokevirtual 133	java/io/IOException:getMessage	()Ljava/lang/String;
    //   54: aastore
    //   55: ldc 135
    //   57: aload 17
    //   59: invokestatic 119	com/google/android/finsky/utils/FinskyLog:e	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   62: aload_0
    //   63: getfield 98	com/google/android/finsky/installer/PackageInstallerImpl:mOpenSessionMap	Ljava/util/Map;
    //   66: aload_1
    //   67: invokeinterface 106 2 0
    //   72: pop
    //   73: aload_0
    //   74: getfield 44	com/google/android/finsky/installer/PackageInstallerImpl:mSessionInfoMap	Ljava/util/Map;
    //   77: aload_1
    //   78: invokeinterface 128 2 0
    //   83: checkcast 68	android/content/pm/PackageInstaller$SessionInfo
    //   86: astore 4
    //   88: aconst_null
    //   89: astore 5
    //   91: aload 4
    //   93: ifnull -65 -> 28
    //   96: aload_0
    //   97: getfield 39	com/google/android/finsky/installer/PackageInstallerImpl:mPackageInstaller	Landroid/content/pm/PackageInstaller;
    //   100: aload 4
    //   102: invokevirtual 82	android/content/pm/PackageInstaller$SessionInfo:getSessionId	()I
    //   105: invokevirtual 139	android/content/pm/PackageInstaller:openSession	(I)Landroid/content/pm/PackageInstaller$Session;
    //   108: astore 11
    //   110: aload_0
    //   111: getfield 98	com/google/android/finsky/installer/PackageInstallerImpl:mOpenSessionMap	Ljava/util/Map;
    //   114: aload_1
    //   115: aload 11
    //   117: invokeinterface 78 3 0
    //   122: pop
    //   123: aload 11
    //   125: astore 5
    //   127: goto -99 -> 28
    //   130: astore 13
    //   132: iconst_2
    //   133: anewarray 4	java/lang/Object
    //   136: astore 14
    //   138: aload 14
    //   140: iconst_0
    //   141: aload_1
    //   142: aastore
    //   143: aload 14
    //   145: iconst_1
    //   146: aload 13
    //   148: invokevirtual 140	java/lang/SecurityException:getMessage	()Ljava/lang/String;
    //   151: aastore
    //   152: ldc 135
    //   154: aload 14
    //   156: invokestatic 119	com/google/android/finsky/utils/FinskyLog:e	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   159: aload_0
    //   160: getfield 98	com/google/android/finsky/installer/PackageInstallerImpl:mOpenSessionMap	Ljava/util/Map;
    //   163: aload_1
    //   164: invokeinterface 106 2 0
    //   169: pop
    //   170: goto -97 -> 73
    //   173: astore_2
    //   174: aload_0
    //   175: monitorexit
    //   176: aload_2
    //   177: athrow
    //   178: astore 9
    //   180: ldc 142
    //   182: iconst_1
    //   183: anewarray 4	java/lang/Object
    //   186: dup
    //   187: iconst_0
    //   188: aload_1
    //   189: aastore
    //   190: invokestatic 96	com/google/android/finsky/utils/FinskyLog:w	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   193: aload_0
    //   194: getfield 44	com/google/android/finsky/installer/PackageInstallerImpl:mSessionInfoMap	Ljava/util/Map;
    //   197: aload_1
    //   198: invokeinterface 106 2 0
    //   203: pop
    //   204: aconst_null
    //   205: astore 5
    //   207: goto -179 -> 28
    //   210: astore 6
    //   212: iconst_1
    //   213: anewarray 4	java/lang/Object
    //   216: astore 7
    //   218: aload 7
    //   220: iconst_0
    //   221: aload 6
    //   223: invokevirtual 133	java/io/IOException:getMessage	()Ljava/lang/String;
    //   226: aastore
    //   227: ldc 144
    //   229: aload 7
    //   231: invokestatic 96	com/google/android/finsky/utils/FinskyLog:w	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   234: aload_0
    //   235: getfield 44	com/google/android/finsky/installer/PackageInstallerImpl:mSessionInfoMap	Ljava/util/Map;
    //   238: aload_1
    //   239: invokeinterface 106 2 0
    //   244: pop
    //   245: aconst_null
    //   246: astore 5
    //   248: goto -220 -> 28
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	251	0	this	PackageInstallerImpl
    //   0	251	1	paramString	String
    //   173	4	2	localObject1	Object
    //   15	11	3	localSession1	PackageInstaller.Session
    //   86	15	4	localSessionInfo	PackageInstaller.SessionInfo
    //   26	221	5	localObject2	Object
    //   210	12	6	localIOException1	IOException
    //   216	14	7	arrayOfObject1	Object[]
    //   178	1	9	localSecurityException1	SecurityException
    //   108	16	11	localSession2	PackageInstaller.Session
    //   130	17	13	localSecurityException2	SecurityException
    //   136	19	14	arrayOfObject2	Object[]
    //   33	17	16	localIOException2	IOException
    //   39	19	17	arrayOfObject3	Object[]
    // Exception table:
    //   from	to	target	type
    //   20	25	33	java/io/IOException
    //   20	25	130	java/lang/SecurityException
    //   2	16	173	finally
    //   20	25	173	finally
    //   35	73	173	finally
    //   73	88	173	finally
    //   96	110	173	finally
    //   110	123	173	finally
    //   132	170	173	finally
    //   180	204	173	finally
    //   212	245	173	finally
    //   96	110	178	java/lang/SecurityException
    //   96	110	210	java/io/IOException
  }
  
  /* Error */
  private void innerCreateSession(String paramString1, long paramLong, String paramString2, Bitmap paramBitmap, int paramInt)
    throws IOException
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 44	com/google/android/finsky/installer/PackageInstallerImpl:mSessionInfoMap	Ljava/util/Map;
    //   6: aload_1
    //   7: invokeinterface 156 2 0
    //   12: ifeq +19 -> 31
    //   15: ldc 158
    //   17: iconst_1
    //   18: anewarray 4	java/lang/Object
    //   21: dup
    //   22: iconst_0
    //   23: aload_1
    //   24: aastore
    //   25: invokestatic 161	com/google/android/finsky/utils/FinskyLog:wtf	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   28: aload_0
    //   29: monitorexit
    //   30: return
    //   31: new 163	android/content/pm/PackageInstaller$SessionParams
    //   34: dup
    //   35: iconst_1
    //   36: invokespecial 166	android/content/pm/PackageInstaller$SessionParams:<init>	(I)V
    //   39: astore 8
    //   41: aload 5
    //   43: ifnull +10 -> 53
    //   46: aload 8
    //   48: aload 5
    //   50: invokevirtual 170	android/content/pm/PackageInstaller$SessionParams:setAppIcon	(Landroid/graphics/Bitmap;)V
    //   53: aload 4
    //   55: invokestatic 176	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   58: ifne +10 -> 68
    //   61: aload 8
    //   63: aload 4
    //   65: invokevirtual 180	android/content/pm/PackageInstaller$SessionParams:setAppLabel	(Ljava/lang/CharSequence;)V
    //   68: aload 8
    //   70: aload_1
    //   71: invokevirtual 183	android/content/pm/PackageInstaller$SessionParams:setAppPackageName	(Ljava/lang/String;)V
    //   74: aload 8
    //   76: iload 6
    //   78: invokevirtual 186	android/content/pm/PackageInstaller$SessionParams:setInstallLocation	(I)V
    //   81: lload_2
    //   82: lconst_0
    //   83: lcmp
    //   84: ifle +9 -> 93
    //   87: aload 8
    //   89: lload_2
    //   90: invokevirtual 190	android/content/pm/PackageInstaller$SessionParams:setSize	(J)V
    //   93: aload_1
    //   94: invokestatic 194	com/google/android/finsky/installer/PackageInstallerImpl:shouldSetSystemAlertWindow	(Ljava/lang/String;)Z
    //   97: istore 9
    //   99: iload 9
    //   101: ifeq +41 -> 142
    //   104: ldc 163
    //   106: ldc 196
    //   108: iconst_1
    //   109: anewarray 198	java/lang/Class
    //   112: dup
    //   113: iconst_0
    //   114: ldc 200
    //   116: aastore
    //   117: invokevirtual 204	java/lang/Class:getMethod	(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
    //   120: aload 8
    //   122: iconst_1
    //   123: anewarray 4	java/lang/Object
    //   126: dup
    //   127: iconst_0
    //   128: iconst_1
    //   129: anewarray 206	java/lang/String
    //   132: dup
    //   133: iconst_0
    //   134: ldc 208
    //   136: aastore
    //   137: aastore
    //   138: invokevirtual 214	java/lang/reflect/Method:invoke	(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    //   141: pop
    //   142: aload_0
    //   143: getfield 39	com/google/android/finsky/installer/PackageInstallerImpl:mPackageInstaller	Landroid/content/pm/PackageInstaller;
    //   146: aload 8
    //   148: invokevirtual 218	android/content/pm/PackageInstaller:createSession	(Landroid/content/pm/PackageInstaller$SessionParams;)I
    //   151: istore 10
    //   153: aload_0
    //   154: getfield 39	com/google/android/finsky/installer/PackageInstallerImpl:mPackageInstaller	Landroid/content/pm/PackageInstaller;
    //   157: iload 10
    //   159: invokevirtual 222	android/content/pm/PackageInstaller:getSessionInfo	(I)Landroid/content/pm/PackageInstaller$SessionInfo;
    //   162: astore 11
    //   164: aload_0
    //   165: getfield 44	com/google/android/finsky/installer/PackageInstallerImpl:mSessionInfoMap	Ljava/util/Map;
    //   168: aload_1
    //   169: aload 11
    //   171: invokeinterface 78 3 0
    //   176: pop
    //   177: iconst_2
    //   178: anewarray 4	java/lang/Object
    //   181: astore 13
    //   183: aload 13
    //   185: iconst_0
    //   186: iload 10
    //   188: invokestatic 88	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   191: aastore
    //   192: aload 13
    //   194: iconst_1
    //   195: aload_1
    //   196: aastore
    //   197: ldc 224
    //   199: aload 13
    //   201: invokestatic 227	com/google/android/finsky/utils/FinskyLog:d	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   204: goto -176 -> 28
    //   207: astore 7
    //   209: aload_0
    //   210: monitorexit
    //   211: aload 7
    //   213: athrow
    //   214: astore 16
    //   216: ldc 229
    //   218: iconst_2
    //   219: anewarray 4	java/lang/Object
    //   222: dup
    //   223: iconst_0
    //   224: aload_1
    //   225: aastore
    //   226: dup
    //   227: iconst_1
    //   228: aload 16
    //   230: aastore
    //   231: invokestatic 96	com/google/android/finsky/utils/FinskyLog:w	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   234: goto -92 -> 142
    //   237: astore 15
    //   239: ldc 231
    //   241: iconst_2
    //   242: anewarray 4	java/lang/Object
    //   245: dup
    //   246: iconst_0
    //   247: aload_1
    //   248: aastore
    //   249: dup
    //   250: iconst_1
    //   251: aload 15
    //   253: aastore
    //   254: invokestatic 96	com/google/android/finsky/utils/FinskyLog:w	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   257: goto -115 -> 142
    //   260: astore 14
    //   262: ldc 231
    //   264: iconst_2
    //   265: anewarray 4	java/lang/Object
    //   268: dup
    //   269: iconst_0
    //   270: aload_1
    //   271: aastore
    //   272: dup
    //   273: iconst_1
    //   274: aload 14
    //   276: aastore
    //   277: invokestatic 96	com/google/android/finsky/utils/FinskyLog:w	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   280: goto -138 -> 142
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	283	0	this	PackageInstallerImpl
    //   0	283	1	paramString1	String
    //   0	283	2	paramLong	long
    //   0	283	4	paramString2	String
    //   0	283	5	paramBitmap	Bitmap
    //   0	283	6	paramInt	int
    //   207	5	7	localObject	Object
    //   39	108	8	localSessionParams	android.content.pm.PackageInstaller.SessionParams
    //   97	3	9	bool	boolean
    //   151	36	10	i	int
    //   162	8	11	localSessionInfo	PackageInstaller.SessionInfo
    //   181	19	13	arrayOfObject	Object[]
    //   260	15	14	localIllegalAccessException	java.lang.IllegalAccessException
    //   237	15	15	localInvocationTargetException	java.lang.reflect.InvocationTargetException
    //   214	15	16	localNoSuchMethodException	java.lang.NoSuchMethodException
    // Exception table:
    //   from	to	target	type
    //   2	28	207	finally
    //   31	41	207	finally
    //   46	53	207	finally
    //   53	68	207	finally
    //   68	81	207	finally
    //   87	93	207	finally
    //   93	99	207	finally
    //   104	142	207	finally
    //   142	204	207	finally
    //   216	234	207	finally
    //   239	257	207	finally
    //   262	280	207	finally
    //   104	142	214	java/lang/NoSuchMethodException
    //   104	142	237	java/lang/reflect/InvocationTargetException
    //   104	142	260	java/lang/IllegalAccessException
  }
  
  private static boolean shouldSetSystemAlertWindow(String paramString)
  {
    if (Build.VERSION.SDK_INT < 23) {
      return false;
    }
    if (!FinskyApp.get().getExperiments().isEnabled(12604360L)) {
      return false;
    }
    String[] arrayOfString = Utils.commaUnpackStrings((String)G.systemAlertWindowPolicyTargets.get());
    if (arrayOfString.length != 0)
    {
      int i = arrayOfString.length;
      for (int j = 0;; j++)
      {
        int k = 0;
        if (j < i)
        {
          if (paramString.equals(arrayOfString[j])) {
            k = 1;
          }
        }
        else
        {
          if (k != 0) {
            break;
          }
          return false;
        }
      }
    }
    return true;
  }
  
  final void cancelSession(int paramInt, String paramString)
  {
    Object[] arrayOfObject;
    label53:
    try
    {
      closeSession(paramString);
      this.mSessionInfoMap.remove(paramString);
    }
    finally {}
    try
    {
      this.mPackageInstaller.abandonSession(paramInt);
      arrayOfObject = new Object[2];
      arrayOfObject[0] = Integer.valueOf(paramInt);
      arrayOfObject[1] = paramString;
      FinskyLog.d("Canceling session %d for %s", arrayOfObject);
    }
    catch (SecurityException localSecurityException)
    {
      break label53;
    }
  }
  
  /* Error */
  public final void cancelSession(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 44	com/google/android/finsky/installer/PackageInstallerImpl:mSessionInfoMap	Ljava/util/Map;
    //   6: aload_1
    //   7: invokeinterface 128 2 0
    //   12: checkcast 68	android/content/pm/PackageInstaller$SessionInfo
    //   15: astore_3
    //   16: aload_3
    //   17: ifnonnull +6 -> 23
    //   20: aload_0
    //   21: monitorexit
    //   22: return
    //   23: aload_0
    //   24: aload_3
    //   25: invokevirtual 82	android/content/pm/PackageInstaller$SessionInfo:getSessionId	()I
    //   28: aload_1
    //   29: invokevirtual 284	com/google/android/finsky/installer/PackageInstallerImpl:cancelSession	(ILjava/lang/String;)V
    //   32: goto -12 -> 20
    //   35: astore_2
    //   36: aload_0
    //   37: monitorexit
    //   38: aload_2
    //   39: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	40	0	this	PackageInstallerImpl
    //   0	40	1	paramString	String
    //   35	4	2	localObject	Object
    //   15	10	3	localSessionInfo	PackageInstaller.SessionInfo
    // Exception table:
    //   from	to	target	type
    //   2	16	35	finally
    //   23	32	35	finally
  }
  
  public final void createSession(String paramString1, long paramLong, String paramString2, Bitmap paramBitmap, int paramInt)
  {
    try
    {
      innerCreateSession(paramString1, paramLong, paramString2, null, paramInt);
      return;
    }
    catch (IOException localIOException)
    {
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = paramString1;
      arrayOfObject[1] = localIOException.getMessage();
      FinskyLog.e(localIOException, "Couldn't create session for %s: %s", arrayOfObject);
    }
  }
  
  public final void finishStream(OutputStream paramOutputStream)
    throws IOException
  {
    SessionStreamWrapper localSessionStreamWrapper = (SessionStreamWrapper)paramOutputStream;
    OutputStream localOutputStream = localSessionStreamWrapper.stream;
    localSessionStreamWrapper.session.fsync(localOutputStream);
    localOutputStream.close();
  }
  
  public final int getAppIconSize()
  {
    return ((ActivityManager)this.mContext.getSystemService("activity")).getLauncherLargeIconSize();
  }
  
  public final OutputStream getStream(String paramString1, String paramString2, long paramLong)
    throws IOException
  {
    PackageInstaller.Session localSession = getSession(paramString1);
    if (localSession == null)
    {
      FinskyLog.e("Can't open session for %s", new Object[] { paramString1 });
      throw new IOException();
    }
    return new SessionStreamWrapper(localSession.openWrite(paramString2, 0L, paramLong), localSession);
  }
  
  public final boolean hasSession(String paramString)
  {
    try
    {
      boolean bool = this.mSessionInfoMap.containsKey(paramString);
      return bool;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  /* Error */
  public final void install(final String paramString, boolean paramBoolean, final PackageInstallerFacade.InstallListener paramInstallListener)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 44	com/google/android/finsky/installer/PackageInstallerImpl:mSessionInfoMap	Ljava/util/Map;
    //   6: aload_1
    //   7: invokeinterface 128 2 0
    //   12: checkcast 68	android/content/pm/PackageInstaller$SessionInfo
    //   15: astore 5
    //   17: aload_0
    //   18: getfield 98	com/google/android/finsky/installer/PackageInstallerImpl:mOpenSessionMap	Ljava/util/Map;
    //   21: aload_1
    //   22: invokeinterface 128 2 0
    //   27: checkcast 108	android/content/pm/PackageInstaller$Session
    //   30: astore 6
    //   32: aload 5
    //   34: ifnull +8 -> 42
    //   37: aload 6
    //   39: ifnonnull +43 -> 82
    //   42: ldc_w 340
    //   45: iconst_1
    //   46: anewarray 4	java/lang/Object
    //   49: dup
    //   50: iconst_0
    //   51: aload_1
    //   52: aastore
    //   53: invokestatic 161	com/google/android/finsky/utils/FinskyLog:wtf	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   56: new 342	android/os/Handler
    //   59: dup
    //   60: invokestatic 348	android/os/Looper:getMainLooper	()Landroid/os/Looper;
    //   63: invokespecial 351	android/os/Handler:<init>	(Landroid/os/Looper;)V
    //   66: new 353	com/google/android/finsky/installer/PackageInstallerImpl$1
    //   69: dup
    //   70: aload_0
    //   71: aload_3
    //   72: invokespecial 356	com/google/android/finsky/installer/PackageInstallerImpl$1:<init>	(Lcom/google/android/finsky/installer/PackageInstallerImpl;Lcom/google/android/finsky/installer/PackageInstallerFacade$InstallListener;)V
    //   75: invokevirtual 360	android/os/Handler:post	(Ljava/lang/Runnable;)Z
    //   78: pop
    //   79: aload_0
    //   80: monitorexit
    //   81: return
    //   82: new 362	com/google/android/finsky/installer/PackageInstallerImpl$2
    //   85: dup
    //   86: aload_0
    //   87: aload_1
    //   88: aload 5
    //   90: invokevirtual 82	android/content/pm/PackageInstaller$SessionInfo:getSessionId	()I
    //   93: aload_3
    //   94: invokespecial 365	com/google/android/finsky/installer/PackageInstallerImpl$2:<init>	(Lcom/google/android/finsky/installer/PackageInstallerImpl;Ljava/lang/String;ILcom/google/android/finsky/installer/PackageInstallerFacade$InstallListener;)V
    //   97: astore 8
    //   99: new 367	java/lang/StringBuilder
    //   102: dup
    //   103: ldc_w 369
    //   106: invokespecial 371	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   109: aload_1
    //   110: invokevirtual 375	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   113: invokevirtual 378	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   116: astore 9
    //   118: new 380	android/content/IntentFilter
    //   121: dup
    //   122: invokespecial 381	android/content/IntentFilter:<init>	()V
    //   125: astore 10
    //   127: aload 10
    //   129: aload 9
    //   131: invokevirtual 384	android/content/IntentFilter:addAction	(Ljava/lang/String;)V
    //   134: aload_0
    //   135: getfield 25	com/google/android/finsky/installer/PackageInstallerImpl:mContext	Landroid/content/Context;
    //   138: aload 8
    //   140: aload 10
    //   142: invokevirtual 388	android/content/Context:registerReceiver	(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;)Landroid/content/Intent;
    //   145: pop
    //   146: new 390	android/content/Intent
    //   149: dup
    //   150: aload 9
    //   152: invokespecial 391	android/content/Intent:<init>	(Ljava/lang/String;)V
    //   155: astore 12
    //   157: aload 6
    //   159: aload_0
    //   160: getfield 25	com/google/android/finsky/installer/PackageInstallerImpl:mContext	Landroid/content/Context;
    //   163: aload_1
    //   164: invokevirtual 394	java/lang/String:hashCode	()I
    //   167: aload 12
    //   169: ldc_w 395
    //   172: invokestatic 401	android/app/PendingIntent:getBroadcast	(Landroid/content/Context;ILandroid/content/Intent;I)Landroid/app/PendingIntent;
    //   175: invokevirtual 405	android/app/PendingIntent:getIntentSender	()Landroid/content/IntentSender;
    //   178: invokevirtual 409	android/content/pm/PackageInstaller$Session:commit	(Landroid/content/IntentSender;)V
    //   181: aload 6
    //   183: invokevirtual 111	android/content/pm/PackageInstaller$Session:close	()V
    //   186: aload_0
    //   187: getfield 98	com/google/android/finsky/installer/PackageInstallerImpl:mOpenSessionMap	Ljava/util/Map;
    //   190: aload_1
    //   191: invokeinterface 106 2 0
    //   196: pop
    //   197: goto -118 -> 79
    //   200: astore 4
    //   202: aload_0
    //   203: monitorexit
    //   204: aload 4
    //   206: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	207	0	this	PackageInstallerImpl
    //   0	207	1	paramString	String
    //   0	207	2	paramBoolean	boolean
    //   0	207	3	paramInstallListener	PackageInstallerFacade.InstallListener
    //   200	5	4	localObject	Object
    //   15	74	5	localSessionInfo	PackageInstaller.SessionInfo
    //   30	152	6	localSession	PackageInstaller.Session
    //   97	42	8	local2	2
    //   116	35	9	str	String
    //   125	16	10	localIntentFilter	android.content.IntentFilter
    //   155	13	12	localIntent	Intent
    // Exception table:
    //   from	to	target	type
    //   2	32	200	finally
    //   42	79	200	finally
    //   82	197	200	finally
  }
  
  public final void pruneSessions(List<String> paramList)
    throws IllegalStateException
  {
    try
    {
      HashSet localHashSet = Sets.newHashSet(this.mSessionInfoMap.keySet());
      localHashSet.removeAll(paramList);
      Iterator localIterator = localHashSet.iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        FinskyLog.d("Pruning stale session for %s", new Object[] { str });
        cancelSession(str);
      }
    }
    finally {}
  }
  
  /* Error */
  public final void reportProgress(String paramString, long paramLong1, long paramLong2)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: aload_1
    //   4: invokespecial 325	com/google/android/finsky/installer/PackageInstallerImpl:getSession	(Ljava/lang/String;)Landroid/content/pm/PackageInstaller$Session;
    //   7: astore 7
    //   9: aload 7
    //   11: ifnull +25 -> 36
    //   14: lload 4
    //   16: lconst_0
    //   17: lcmp
    //   18: ifle +18 -> 36
    //   21: lload_2
    //   22: l2f
    //   23: lload 4
    //   25: l2f
    //   26: fdiv
    //   27: fstore 8
    //   29: aload 7
    //   31: fload 8
    //   33: invokevirtual 440	android/content/pm/PackageInstaller$Session:setStagingProgress	(F)V
    //   36: aload_0
    //   37: monitorexit
    //   38: return
    //   39: astore 9
    //   41: iconst_1
    //   42: anewarray 4	java/lang/Object
    //   45: astore 10
    //   47: aload 10
    //   49: iconst_0
    //   50: aload 9
    //   52: invokevirtual 114	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   55: aastore
    //   56: ldc_w 442
    //   59: aload 10
    //   61: invokestatic 119	com/google/android/finsky/utils/FinskyLog:e	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   64: aload_0
    //   65: getfield 98	com/google/android/finsky/installer/PackageInstallerImpl:mOpenSessionMap	Ljava/util/Map;
    //   68: aload_1
    //   69: invokeinterface 106 2 0
    //   74: pop
    //   75: goto -39 -> 36
    //   78: astore 6
    //   80: aload_0
    //   81: monitorexit
    //   82: aload 6
    //   84: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	85	0	this	PackageInstallerImpl
    //   0	85	1	paramString	String
    //   0	85	2	paramLong1	long
    //   0	85	4	paramLong2	long
    //   78	5	6	localObject	Object
    //   7	23	7	localSession	PackageInstaller.Session
    //   27	5	8	f	float
    //   39	12	9	localException	java.lang.Exception
    //   45	15	10	arrayOfObject	Object[]
    // Exception table:
    //   from	to	target	type
    //   29	36	39	java/lang/Exception
    //   2	9	78	finally
    //   29	36	78	finally
    //   41	75	78	finally
  }
  
  public final boolean requireCopy(boolean paramBoolean)
  {
    return true;
  }
  
  /* Error */
  public final void setAppIcon(String paramString, Bitmap paramBitmap)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 44	com/google/android/finsky/installer/PackageInstallerImpl:mSessionInfoMap	Ljava/util/Map;
    //   6: aload_1
    //   7: invokeinterface 128 2 0
    //   12: checkcast 68	android/content/pm/PackageInstaller$SessionInfo
    //   15: astore 4
    //   17: aload 4
    //   19: ifnull +16 -> 35
    //   22: aload_0
    //   23: getfield 39	com/google/android/finsky/installer/PackageInstallerImpl:mPackageInstaller	Landroid/content/pm/PackageInstaller;
    //   26: aload 4
    //   28: invokevirtual 82	android/content/pm/PackageInstaller$SessionInfo:getSessionId	()I
    //   31: aload_2
    //   32: invokevirtual 449	android/content/pm/PackageInstaller:updateSessionAppIcon	(ILandroid/graphics/Bitmap;)V
    //   35: aload_0
    //   36: monitorexit
    //   37: return
    //   38: astore 5
    //   40: iconst_2
    //   41: anewarray 4	java/lang/Object
    //   44: astore 6
    //   46: aload 6
    //   48: iconst_0
    //   49: aload 4
    //   51: invokevirtual 82	android/content/pm/PackageInstaller$SessionInfo:getSessionId	()I
    //   54: invokestatic 88	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   57: aastore
    //   58: aload 6
    //   60: iconst_1
    //   61: aload_1
    //   62: aastore
    //   63: ldc_w 451
    //   66: aload 6
    //   68: invokestatic 96	com/google/android/finsky/utils/FinskyLog:w	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   71: goto -36 -> 35
    //   74: astore_3
    //   75: aload_0
    //   76: monitorexit
    //   77: aload_3
    //   78: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	79	0	this	PackageInstallerImpl
    //   0	79	1	paramString	String
    //   0	79	2	paramBitmap	Bitmap
    //   74	4	3	localObject	Object
    //   15	35	4	localSessionInfo	PackageInstaller.SessionInfo
    //   38	1	5	localSecurityException	SecurityException
    //   44	23	6	arrayOfObject	Object[]
    // Exception table:
    //   from	to	target	type
    //   22	35	38	java/lang/SecurityException
    //   2	17	74	finally
    //   22	35	74	finally
    //   40	71	74	finally
  }
  
  public final void setInstallUri(String paramString, Uri paramUri)
  {
    FinskyLog.wtf("Cannot install %s from uri %s", new Object[] { paramString, paramUri });
  }
  
  public final void uninstallPackage(String paramString, boolean paramBoolean)
  {
    PackageManagerHelper.uninstallPackage(paramString, paramBoolean);
  }
  
  private static final class SessionStreamWrapper
    extends FilterOutputStream
  {
    private final PackageInstaller.Session session;
    private final OutputStream stream;
    
    public SessionStreamWrapper(OutputStream paramOutputStream, PackageInstaller.Session paramSession)
    {
      super();
      this.stream = paramOutputStream;
      this.session = paramSession;
    }
    
    public final void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
      throws IOException
    {
      this.stream.write(paramArrayOfByte, paramInt1, paramInt2);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.installer.PackageInstallerImpl
 * JD-Core Version:    0.7.0.1
 */