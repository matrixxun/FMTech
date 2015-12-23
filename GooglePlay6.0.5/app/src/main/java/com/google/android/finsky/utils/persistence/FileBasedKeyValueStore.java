package com.google.android.finsky.utils.persistence;

import android.text.TextUtils;
import com.google.android.finsky.utils.FinskyLog;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public final class FileBasedKeyValueStore
  implements KeyValueStore
{
  private final String mDataStoreId;
  private final File mRootDirectory;
  
  public FileBasedKeyValueStore(File paramFile, String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      throw new IllegalArgumentException("A dataStoreId must be specified");
    }
    this.mRootDirectory = paramFile;
    this.mDataStoreId = paramString;
  }
  
  private static Map<String, String> parseMapFromJson(JSONObject paramJSONObject)
    throws JSONException
  {
    HashMap localHashMap = new HashMap();
    Iterator localIterator = paramJSONObject.keys();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if (!paramJSONObject.isNull(str)) {
        localHashMap.put(str, paramJSONObject.getString(str));
      } else {
        localHashMap.put(str, null);
      }
    }
    return localHashMap;
  }
  
  public final void delete(String paramString)
  {
    if (!new File(this.mRootDirectory, this.mDataStoreId + paramString).delete()) {
      FinskyLog.e("Attempt to delete '%s' failed!", new Object[] { paramString });
    }
  }
  
  public final Map<String, Map<String, String>> fetchAll()
  {
    HashMap localHashMap = new HashMap();
    File[] arrayOfFile = this.mRootDirectory.listFiles();
    int i;
    if (arrayOfFile != null) {
      i = arrayOfFile.length;
    }
    for (int j = 0;; j++) {
      if (j < i)
      {
        File localFile = arrayOfFile[j];
        String str1 = localFile.getName();
        try
        {
          if (!str1.startsWith(this.mDataStoreId)) {
            continue;
          }
          FileInputStream localFileInputStream = new FileInputStream(localFile);
          ObjectInputStream localObjectInputStream = new ObjectInputStream(localFileInputStream);
          String str2 = str1.replace(this.mDataStoreId, "");
          JSONObject localJSONObject = new JSONObject(localObjectInputStream.readUTF());
          localFileInputStream.close();
          localHashMap.put(str2, parseMapFromJson(localJSONObject));
        }
        catch (IOException localIOException)
        {
          FinskyLog.d("IOException when reading file '%s'. Deleting.", new Object[] { str1 });
          if (localFile.delete()) {
            continue;
          }
          FinskyLog.e("Attempt to delete '%s' failed!", new Object[] { str1 });
        }
        catch (JSONException localJSONException)
        {
          Object[] arrayOfObject = new Object[2];
          arrayOfObject[0] = str1;
          arrayOfObject[1] = localJSONException.toString();
          FinskyLog.e("JSONException when reading file '%s'. Deleting. error=[%s]", arrayOfObject);
          if (localFile.delete()) {
            continue;
          }
        }
        FinskyLog.e("Attempt to delete '%s' failed!", new Object[] { str1 });
      }
      else
      {
        return localHashMap;
      }
    }
  }
  
  /* Error */
  public final void put(String paramString, Map<String, String> paramMap)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_3
    //   2: aconst_null
    //   3: astore 4
    //   5: new 147	java/io/FileOutputStream
    //   8: dup
    //   9: new 74	java/io/File
    //   12: dup
    //   13: aload_0
    //   14: getfield 30	com/google/android/finsky/utils/persistence/FileBasedKeyValueStore:mRootDirectory	Ljava/io/File;
    //   17: new 76	java/lang/StringBuilder
    //   20: dup
    //   21: invokespecial 77	java/lang/StringBuilder:<init>	()V
    //   24: aload_0
    //   25: getfield 32	com/google/android/finsky/utils/persistence/FileBasedKeyValueStore:mDataStoreId	Ljava/lang/String;
    //   28: invokevirtual 81	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   31: aload_1
    //   32: invokevirtual 81	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   35: invokevirtual 85	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   38: invokespecial 87	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   41: invokespecial 148	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   44: astore 5
    //   46: new 150	java/io/ObjectOutputStream
    //   49: dup
    //   50: aload 5
    //   52: invokespecial 153	java/io/ObjectOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   55: astore 6
    //   57: aload 6
    //   59: new 41	org/json/JSONObject
    //   62: dup
    //   63: aload_2
    //   64: invokespecial 156	org/json/JSONObject:<init>	(Ljava/util/Map;)V
    //   67: invokevirtual 157	org/json/JSONObject:toString	()Ljava/lang/String;
    //   70: invokevirtual 160	java/io/ObjectOutputStream:writeUTF	(Ljava/lang/String;)V
    //   73: aload 6
    //   75: invokevirtual 163	java/io/ObjectOutputStream:flush	()V
    //   78: aload 6
    //   80: invokevirtual 164	java/io/ObjectOutputStream:close	()V
    //   83: return
    //   84: astore 11
    //   86: return
    //   87: astore 7
    //   89: ldc 166
    //   91: iconst_2
    //   92: anewarray 4	java/lang/Object
    //   95: dup
    //   96: iconst_0
    //   97: aload_1
    //   98: aastore
    //   99: dup
    //   100: iconst_1
    //   101: aload 7
    //   103: aastore
    //   104: invokestatic 169	com/google/android/finsky/utils/FinskyLog:w	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   107: aload 4
    //   109: ifnull +9 -> 118
    //   112: aload 4
    //   114: invokevirtual 164	java/io/ObjectOutputStream:close	()V
    //   117: return
    //   118: aload_3
    //   119: ifnull -36 -> 83
    //   122: aload_3
    //   123: invokevirtual 170	java/io/FileOutputStream:close	()V
    //   126: return
    //   127: astore 8
    //   129: aload 4
    //   131: ifnull +11 -> 142
    //   134: aload 4
    //   136: invokevirtual 164	java/io/ObjectOutputStream:close	()V
    //   139: aload 8
    //   141: athrow
    //   142: aload_3
    //   143: ifnull -4 -> 139
    //   146: aload_3
    //   147: invokevirtual 170	java/io/FileOutputStream:close	()V
    //   150: goto -11 -> 139
    //   153: astore 9
    //   155: goto -16 -> 139
    //   158: astore 8
    //   160: aload 5
    //   162: astore_3
    //   163: aconst_null
    //   164: astore 4
    //   166: goto -37 -> 129
    //   169: astore 8
    //   171: aload 6
    //   173: astore 4
    //   175: aload 5
    //   177: astore_3
    //   178: goto -49 -> 129
    //   181: astore 7
    //   183: aload 5
    //   185: astore_3
    //   186: aconst_null
    //   187: astore 4
    //   189: goto -100 -> 89
    //   192: astore 7
    //   194: aload 6
    //   196: astore 4
    //   198: aload 5
    //   200: astore_3
    //   201: goto -112 -> 89
    //   204: astore 10
    //   206: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	207	0	this	FileBasedKeyValueStore
    //   0	207	1	paramString	String
    //   0	207	2	paramMap	Map<String, String>
    //   1	200	3	localObject1	Object
    //   3	194	4	localObject2	Object
    //   44	155	5	localFileOutputStream	java.io.FileOutputStream
    //   55	140	6	localObjectOutputStream	java.io.ObjectOutputStream
    //   87	15	7	localIOException1	IOException
    //   181	1	7	localIOException2	IOException
    //   192	1	7	localIOException3	IOException
    //   127	13	8	localObject3	Object
    //   158	1	8	localObject4	Object
    //   169	1	8	localObject5	Object
    //   153	1	9	localIOException4	IOException
    //   204	1	10	localIOException5	IOException
    //   84	1	11	localIOException6	IOException
    // Exception table:
    //   from	to	target	type
    //   78	83	84	java/io/IOException
    //   5	46	87	java/io/IOException
    //   5	46	127	finally
    //   89	107	127	finally
    //   134	139	153	java/io/IOException
    //   146	150	153	java/io/IOException
    //   46	57	158	finally
    //   57	78	169	finally
    //   46	57	181	java/io/IOException
    //   57	78	192	java/io/IOException
    //   112	117	204	java/io/IOException
    //   122	126	204	java/io/IOException
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.persistence.FileBasedKeyValueStore
 * JD-Core Version:    0.7.0.1
 */