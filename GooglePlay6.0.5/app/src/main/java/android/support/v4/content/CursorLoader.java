package android.support.v4.content;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.os.CancellationSignal;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Arrays;

public class CursorLoader
  extends AsyncTaskLoader<Cursor>
{
  CancellationSignal mCancellationSignal;
  Cursor mCursor;
  final Loader<Cursor>.android.support.v4.content.Loader.android.support.v4.content.Loader.android.support.v4.content.Loader.ForceLoadContentObserver mObserver = new Loader.ForceLoadContentObserver(this);
  public String[] mProjection;
  String mSelection;
  String[] mSelectionArgs;
  String mSortOrder;
  public Uri mUri;
  
  public CursorLoader(Context paramContext)
  {
    super(paramContext);
  }
  
  private void deliverResult(Cursor paramCursor)
  {
    if (this.mReset) {
      if (paramCursor != null) {
        paramCursor.close();
      }
    }
    Cursor localCursor;
    do
    {
      return;
      localCursor = this.mCursor;
      this.mCursor = paramCursor;
      if (this.mStarted) {
        super.deliverResult(paramCursor);
      }
    } while ((localCursor == null) || (localCursor == paramCursor) || (localCursor.isClosed()));
    localCursor.close();
  }
  
  /* Error */
  public final void cancelLoadInBackground()
  {
    // Byte code:
    //   0: aload_0
    //   1: invokespecial 60	android/support/v4/content/AsyncTaskLoader:cancelLoadInBackground	()V
    //   4: aload_0
    //   5: monitorenter
    //   6: aload_0
    //   7: getfield 62	android/support/v4/content/CursorLoader:mCancellationSignal	Landroid/support/v4/os/CancellationSignal;
    //   10: ifnull +19 -> 29
    //   13: aload_0
    //   14: getfield 62	android/support/v4/content/CursorLoader:mCancellationSignal	Landroid/support/v4/os/CancellationSignal;
    //   17: astore_2
    //   18: aload_2
    //   19: monitorenter
    //   20: aload_2
    //   21: getfield 67	android/support/v4/os/CancellationSignal:mIsCanceled	Z
    //   24: ifeq +8 -> 32
    //   27: aload_2
    //   28: monitorexit
    //   29: aload_0
    //   30: monitorexit
    //   31: return
    //   32: aload_2
    //   33: iconst_1
    //   34: putfield 67	android/support/v4/os/CancellationSignal:mIsCanceled	Z
    //   37: aload_2
    //   38: iconst_1
    //   39: putfield 70	android/support/v4/os/CancellationSignal:mCancelInProgress	Z
    //   42: aload_2
    //   43: getfield 74	android/support/v4/os/CancellationSignal:mCancellationSignalObj	Ljava/lang/Object;
    //   46: astore 4
    //   48: aload_2
    //   49: monitorexit
    //   50: aload 4
    //   52: ifnull +11 -> 63
    //   55: aload 4
    //   57: checkcast 76	android/os/CancellationSignal
    //   60: invokevirtual 79	android/os/CancellationSignal:cancel	()V
    //   63: aload_2
    //   64: monitorenter
    //   65: aload_2
    //   66: iconst_0
    //   67: putfield 70	android/support/v4/os/CancellationSignal:mCancelInProgress	Z
    //   70: aload_2
    //   71: invokevirtual 84	java/lang/Object:notifyAll	()V
    //   74: aload_2
    //   75: monitorexit
    //   76: goto -47 -> 29
    //   79: astore 5
    //   81: aload_2
    //   82: monitorexit
    //   83: aload 5
    //   85: athrow
    //   86: astore_1
    //   87: aload_0
    //   88: monitorexit
    //   89: aload_1
    //   90: athrow
    //   91: astore_3
    //   92: aload_2
    //   93: monitorexit
    //   94: aload_3
    //   95: athrow
    //   96: astore 6
    //   98: aload_2
    //   99: monitorenter
    //   100: aload_2
    //   101: iconst_0
    //   102: putfield 70	android/support/v4/os/CancellationSignal:mCancelInProgress	Z
    //   105: aload_2
    //   106: invokevirtual 84	java/lang/Object:notifyAll	()V
    //   109: aload_2
    //   110: monitorexit
    //   111: aload 6
    //   113: athrow
    //   114: astore 7
    //   116: aload_2
    //   117: monitorexit
    //   118: aload 7
    //   120: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	121	0	this	CursorLoader
    //   86	4	1	localObject1	java.lang.Object
    //   17	100	2	localCancellationSignal	CancellationSignal
    //   91	4	3	localObject2	java.lang.Object
    //   46	10	4	localObject3	java.lang.Object
    //   79	5	5	localObject4	java.lang.Object
    //   96	16	6	localObject5	java.lang.Object
    //   114	5	7	localObject6	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   65	76	79	finally
    //   81	83	79	finally
    //   6	20	86	finally
    //   29	31	86	finally
    //   63	65	86	finally
    //   83	86	86	finally
    //   87	89	86	finally
    //   94	96	86	finally
    //   98	100	86	finally
    //   111	114	86	finally
    //   118	121	86	finally
    //   20	29	91	finally
    //   32	50	91	finally
    //   92	94	91	finally
    //   55	63	96	finally
    //   100	111	114	finally
    //   116	118	114	finally
  }
  
  public final void dump(String paramString, FileDescriptor paramFileDescriptor, PrintWriter paramPrintWriter, String[] paramArrayOfString)
  {
    super.dump(paramString, paramFileDescriptor, paramPrintWriter, paramArrayOfString);
    paramPrintWriter.print(paramString);
    paramPrintWriter.print("mUri=");
    paramPrintWriter.println(this.mUri);
    paramPrintWriter.print(paramString);
    paramPrintWriter.print("mProjection=");
    paramPrintWriter.println(Arrays.toString(this.mProjection));
    paramPrintWriter.print(paramString);
    paramPrintWriter.print("mSelection=");
    paramPrintWriter.println(this.mSelection);
    paramPrintWriter.print(paramString);
    paramPrintWriter.print("mSelectionArgs=");
    paramPrintWriter.println(Arrays.toString(this.mSelectionArgs));
    paramPrintWriter.print(paramString);
    paramPrintWriter.print("mSortOrder=");
    paramPrintWriter.println(this.mSortOrder);
    paramPrintWriter.print(paramString);
    paramPrintWriter.print("mCursor=");
    paramPrintWriter.println(this.mCursor);
    paramPrintWriter.print(paramString);
    paramPrintWriter.print("mContentChanged=");
    paramPrintWriter.println(this.mContentChanged);
  }
  
  /* Error */
  public Cursor loadInBackground()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 145	android/support/v4/content/AsyncTaskLoader:mCancellingTask	Landroid/support/v4/content/AsyncTaskLoader$LoadTask;
    //   6: ifnull +22 -> 28
    //   9: iconst_1
    //   10: istore_2
    //   11: iload_2
    //   12: ifeq +21 -> 33
    //   15: new 147	android/support/v4/os/OperationCanceledException
    //   18: dup
    //   19: invokespecial 149	android/support/v4/os/OperationCanceledException:<init>	()V
    //   22: athrow
    //   23: astore_1
    //   24: aload_0
    //   25: monitorexit
    //   26: aload_1
    //   27: athrow
    //   28: iconst_0
    //   29: istore_2
    //   30: goto -19 -> 11
    //   33: aload_0
    //   34: new 64	android/support/v4/os/CancellationSignal
    //   37: dup
    //   38: invokespecial 150	android/support/v4/os/CancellationSignal:<init>	()V
    //   41: putfield 62	android/support/v4/content/CursorLoader:mCancellationSignal	Landroid/support/v4/os/CancellationSignal;
    //   44: aload_0
    //   45: monitorexit
    //   46: aload_0
    //   47: getfield 154	android/support/v4/content/Loader:mContext	Landroid/content/Context;
    //   50: invokevirtual 160	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
    //   53: aload_0
    //   54: getfield 100	android/support/v4/content/CursorLoader:mUri	Landroid/net/Uri;
    //   57: aload_0
    //   58: getfield 107	android/support/v4/content/CursorLoader:mProjection	[Ljava/lang/String;
    //   61: aload_0
    //   62: getfield 119	android/support/v4/content/CursorLoader:mSelection	Ljava/lang/String;
    //   65: aload_0
    //   66: getfield 123	android/support/v4/content/CursorLoader:mSelectionArgs	[Ljava/lang/String;
    //   69: aload_0
    //   70: getfield 127	android/support/v4/content/CursorLoader:mSortOrder	Ljava/lang/String;
    //   73: aload_0
    //   74: getfield 62	android/support/v4/content/CursorLoader:mCancellationSignal	Landroid/support/v4/os/CancellationSignal;
    //   77: invokestatic 166	android/support/v4/content/ContentResolverCompat:query	(Landroid/content/ContentResolver;Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Landroid/support/v4/os/CancellationSignal;)Landroid/database/Cursor;
    //   80: astore 5
    //   82: aload 5
    //   84: ifnull +22 -> 106
    //   87: aload 5
    //   89: invokeinterface 170 1 0
    //   94: pop
    //   95: aload 5
    //   97: aload_0
    //   98: getfield 31	android/support/v4/content/CursorLoader:mObserver	Landroid/support/v4/content/Loader$ForceLoadContentObserver;
    //   101: invokeinterface 174 2 0
    //   106: aload_0
    //   107: monitorenter
    //   108: aload_0
    //   109: aconst_null
    //   110: putfield 62	android/support/v4/content/CursorLoader:mCancellationSignal	Landroid/support/v4/os/CancellationSignal;
    //   113: aload_0
    //   114: monitorexit
    //   115: aload 5
    //   117: areturn
    //   118: astore 7
    //   120: aload 5
    //   122: invokeinterface 45 1 0
    //   127: aload 7
    //   129: athrow
    //   130: astore_3
    //   131: aload_0
    //   132: monitorenter
    //   133: aload_0
    //   134: aconst_null
    //   135: putfield 62	android/support/v4/content/CursorLoader:mCancellationSignal	Landroid/support/v4/os/CancellationSignal;
    //   138: aload_0
    //   139: monitorexit
    //   140: aload_3
    //   141: athrow
    //   142: astore 6
    //   144: aload_0
    //   145: monitorexit
    //   146: aload 6
    //   148: athrow
    //   149: astore 4
    //   151: aload_0
    //   152: monitorexit
    //   153: aload 4
    //   155: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	156	0	this	CursorLoader
    //   23	4	1	localObject1	java.lang.Object
    //   10	20	2	i	int
    //   130	11	3	localObject2	java.lang.Object
    //   149	5	4	localObject3	java.lang.Object
    //   80	41	5	localCursor	Cursor
    //   142	5	6	localObject4	java.lang.Object
    //   118	10	7	localRuntimeException	java.lang.RuntimeException
    // Exception table:
    //   from	to	target	type
    //   2	9	23	finally
    //   15	23	23	finally
    //   24	26	23	finally
    //   33	46	23	finally
    //   87	106	118	java/lang/RuntimeException
    //   46	82	130	finally
    //   87	106	130	finally
    //   120	130	130	finally
    //   108	115	142	finally
    //   144	146	142	finally
    //   133	140	149	finally
    //   151	153	149	finally
  }
  
  protected final void onReset()
  {
    super.onReset();
    cancelLoad();
    if ((this.mCursor != null) && (!this.mCursor.isClosed())) {
      this.mCursor.close();
    }
    this.mCursor = null;
  }
  
  protected final void onStartLoading()
  {
    if (this.mCursor != null) {
      deliverResult(this.mCursor);
    }
    if ((takeContentChanged()) || (this.mCursor == null)) {
      forceLoad();
    }
  }
  
  protected final void onStopLoading()
  {
    cancelLoad();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v4.content.CursorLoader
 * JD-Core Version:    0.7.0.1
 */