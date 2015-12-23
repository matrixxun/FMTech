package com.google.android.finsky.config;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.protos.ContentFilters.ContentFilterSettingsResponse;
import com.google.android.finsky.protos.ContentFilters.FilterChoice;
import com.google.android.finsky.protos.ContentFilters.FilterRange;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Utils;
import java.util.Arrays;
import java.util.regex.Pattern;

public final class ContentFiltersUtils
{
  private static Pattern KEYS_PATTERN = Pattern.compile(Character.toString(':'));
  static int SHOW_ALL_LEVEL = -1;
  private static Pattern VALUES_PATTERN = Pattern.compile(Character.toString(';'));
  
  public static Bundle createBundleForFilterChoice(ContentFilters.FilterRange paramFilterRange, ContentFilters.FilterChoice paramFilterChoice)
  {
    Bundle localBundle = new Bundle();
    localBundle.putInt("authority", paramFilterRange.authorityId);
    localBundle.putInt("filter_level", paramFilterChoice.level);
    localBundle.putString("label", paramFilterChoice.label);
    if ((paramFilterChoice.imageFife != null) && (paramFilterChoice.imageFife.imageUrl != null)) {
      localBundle.putString("icon", paramFilterChoice.imageFife.imageUrl);
    }
    return localBundle;
  }
  
  /* Error */
  private static ContentFilterSelection decode(String paramString)
  {
    // Byte code:
    //   0: getstatic 27	com/google/android/finsky/config/ContentFiltersUtils:VALUES_PATTERN	Ljava/util/regex/Pattern;
    //   3: aload_0
    //   4: invokevirtual 82	java/util/regex/Pattern:split	(Ljava/lang/CharSequence;)[Ljava/lang/String;
    //   7: astore_1
    //   8: aload_1
    //   9: arraylength
    //   10: iconst_3
    //   11: if_icmpge +18 -> 29
    //   14: ldc 84
    //   16: iconst_1
    //   17: anewarray 4	java/lang/Object
    //   20: dup
    //   21: iconst_0
    //   22: aload_0
    //   23: aastore
    //   24: invokestatic 90	com/google/android/finsky/utils/FinskyLog:e	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   27: aconst_null
    //   28: areturn
    //   29: getstatic 25	com/google/android/finsky/config/ContentFiltersUtils:KEYS_PATTERN	Ljava/util/regex/Pattern;
    //   32: aload_1
    //   33: iconst_0
    //   34: aaload
    //   35: invokevirtual 82	java/util/regex/Pattern:split	(Ljava/lang/CharSequence;)[Ljava/lang/String;
    //   38: astore_2
    //   39: aload_2
    //   40: arraylength
    //   41: newarray int
    //   43: astore_3
    //   44: iconst_0
    //   45: istore 4
    //   47: iload 4
    //   49: aload_2
    //   50: arraylength
    //   51: if_icmpge +51 -> 102
    //   54: aload_2
    //   55: iload 4
    //   57: aaload
    //   58: invokestatic 96	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   61: istore 13
    //   63: aload_3
    //   64: iload 4
    //   66: iload 13
    //   68: iastore
    //   69: iinc 4 1
    //   72: goto -25 -> 47
    //   75: astore 11
    //   77: iconst_1
    //   78: anewarray 4	java/lang/Object
    //   81: astore 12
    //   83: aload 12
    //   85: iconst_0
    //   86: aload_1
    //   87: iload 4
    //   89: aaload
    //   90: aastore
    //   91: aload 11
    //   93: ldc 98
    //   95: aload 12
    //   97: invokestatic 101	com/google/android/finsky/utils/FinskyLog:e	(Ljava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)V
    //   100: aconst_null
    //   101: areturn
    //   102: aload_1
    //   103: iconst_1
    //   104: aaload
    //   105: invokestatic 96	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   108: istore 7
    //   110: aload_1
    //   111: iconst_2
    //   112: aaload
    //   113: invokestatic 96	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   116: istore 10
    //   118: new 103	com/google/android/finsky/config/ContentFiltersUtils$ContentFilterSelection
    //   121: dup
    //   122: aload_3
    //   123: iload 7
    //   125: iload 10
    //   127: invokespecial 106	com/google/android/finsky/config/ContentFiltersUtils$ContentFilterSelection:<init>	([III)V
    //   130: areturn
    //   131: astore 5
    //   133: iconst_1
    //   134: anewarray 4	java/lang/Object
    //   137: astore 6
    //   139: aload 6
    //   141: iconst_0
    //   142: aload_1
    //   143: iconst_1
    //   144: aaload
    //   145: aastore
    //   146: aload 5
    //   148: ldc 98
    //   150: aload 6
    //   152: invokestatic 101	com/google/android/finsky/utils/FinskyLog:e	(Ljava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)V
    //   155: aconst_null
    //   156: areturn
    //   157: astore 8
    //   159: iconst_1
    //   160: anewarray 4	java/lang/Object
    //   163: astore 9
    //   165: aload 9
    //   167: iconst_0
    //   168: aload_1
    //   169: iconst_2
    //   170: aaload
    //   171: aastore
    //   172: aload 8
    //   174: ldc 98
    //   176: aload 9
    //   178: invokestatic 101	com/google/android/finsky/utils/FinskyLog:e	(Ljava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)V
    //   181: aconst_null
    //   182: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	183	0	paramString	String
    //   7	162	1	arrayOfString1	String[]
    //   38	17	2	arrayOfString2	String[]
    //   43	80	3	arrayOfInt	int[]
    //   45	43	4	i	int
    //   131	16	5	localNumberFormatException1	java.lang.NumberFormatException
    //   137	14	6	arrayOfObject1	Object[]
    //   108	16	7	j	int
    //   157	16	8	localNumberFormatException2	java.lang.NumberFormatException
    //   163	14	9	arrayOfObject2	Object[]
    //   116	10	10	k	int
    //   75	17	11	localNumberFormatException3	java.lang.NumberFormatException
    //   81	15	12	arrayOfObject3	Object[]
    //   61	6	13	m	int
    // Exception table:
    //   from	to	target	type
    //   54	63	75	java/lang/NumberFormatException
    //   102	110	131	java/lang/NumberFormatException
    //   110	118	157	java/lang/NumberFormatException
  }
  
  public static ContentFilterSelection[] decodeSelections(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 0))
    {
      String[] arrayOfString = Utils.commaUnpackStrings(paramString);
      ContentFilterSelection[] arrayOfContentFilterSelection = new ContentFilterSelection[arrayOfString.length];
      for (int i = 0;; i++)
      {
        ContentFilterSelection localContentFilterSelection;
        if (i < arrayOfString.length)
        {
          localContentFilterSelection = decode(arrayOfString[i]);
          if (localContentFilterSelection == null) {
            arrayOfContentFilterSelection = null;
          }
        }
        else
        {
          return arrayOfContentFilterSelection;
        }
        arrayOfContentFilterSelection[i] = localContentFilterSelection;
      }
    }
    return new ContentFilterSelection[0];
  }
  
  public static String encodeSelections(ContentFilterSelection[] paramArrayOfContentFilterSelection)
  {
    String[] arrayOfString = new String[paramArrayOfContentFilterSelection.length];
    for (int i = 0; i < paramArrayOfContentFilterSelection.length; i++)
    {
      ContentFilterSelection localContentFilterSelection = paramArrayOfContentFilterSelection[i];
      StringBuffer localStringBuffer = new StringBuffer();
      for (int j = 0; j < localContentFilterSelection.documentTypes.length; j++)
      {
        localStringBuffer.append(localContentFilterSelection.documentTypes[j]);
        if (j + 1 < localContentFilterSelection.documentTypes.length) {
          localStringBuffer.append(':');
        }
      }
      localStringBuffer.append(';');
      localStringBuffer.append(localContentFilterSelection.authorityId);
      localStringBuffer.append(';');
      localStringBuffer.append(localContentFilterSelection.level);
      arrayOfString[i] = localStringBuffer.toString();
    }
    return Utils.commaPackStrings(arrayOfString);
  }
  
  public static int findSelection(ContentFilters.FilterRange paramFilterRange, ContentFilterSelection[] paramArrayOfContentFilterSelection)
  {
    for (int i = 0; (paramArrayOfContentFilterSelection != null) && (i < paramArrayOfContentFilterSelection.length); i++) {
      if ((selectionMatchesDocumentTypes(paramFilterRange.documentType, paramArrayOfContentFilterSelection[i].documentTypes)) && (paramFilterRange.authorityId == paramArrayOfContentFilterSelection[i].authorityId)) {
        return i;
      }
    }
    return -1;
  }
  
  public static ContentFilters.FilterChoice getFilterChoice(ContentFilters.FilterRange paramFilterRange, ContentFilterSelection[] paramArrayOfContentFilterSelection)
  {
    int i = findSelection(paramFilterRange, paramArrayOfContentFilterSelection);
    ContentFilterSelection localContentFilterSelection;
    ContentFilters.FilterChoice[] arrayOfFilterChoice;
    int j;
    if (i == -1)
    {
      localContentFilterSelection = null;
      arrayOfFilterChoice = paramFilterRange.filterChoice;
      j = arrayOfFilterChoice.length;
    }
    for (int k = 0;; k++)
    {
      if (k >= j) {
        break label66;
      }
      ContentFilters.FilterChoice localFilterChoice = arrayOfFilterChoice[k];
      if (isFilterChoiceSelected(localFilterChoice, localContentFilterSelection))
      {
        return localFilterChoice;
        localContentFilterSelection = paramArrayOfContentFilterSelection[i];
        break;
      }
    }
    label66:
    return null;
  }
  
  public static Intent getFilterChoicesAsIntent(ContentFilters.FilterRange[] paramArrayOfFilterRange, ContentFilterSelection[] paramArrayOfContentFilterSelection)
  {
    Intent localIntent = new Intent();
    int i = paramArrayOfFilterRange.length;
    for (int j = 0; j < i; j++)
    {
      ContentFilters.FilterRange localFilterRange = paramArrayOfFilterRange[j];
      ContentFilters.FilterChoice localFilterChoice = getFilterChoice(localFilterRange, paramArrayOfContentFilterSelection);
      if (localFilterChoice != null) {
        for (int n : localFilterRange.documentType)
        {
          Bundle localBundle = createBundleForFilterChoice(localFilterRange, localFilterChoice);
          localIntent.putExtra(String.valueOf(n), localBundle);
        }
      }
    }
    return localIntent;
  }
  
  private static String getSelectedDfeHeader(ContentFilters.FilterRange paramFilterRange, ContentFilterSelection paramContentFilterSelection)
  {
    if ((paramContentFilterSelection.authorityId < 0) || (paramContentFilterSelection.documentTypes == null)) {
      FinskyLog.e("Badly formatted ContentFilterSelection authorityId is negative or documentTypes is null. [ContentFilterSelection=%s]", new Object[] { paramContentFilterSelection });
    }
    for (;;)
    {
      return null;
      if ((!paramFilterRange.hasAuthorityId) || (paramFilterRange.documentType == null))
      {
        FinskyLog.e("Badly formatted FilterRange authorityId is missing or documentType is null. [FilterRange=%s]", new Object[] { paramFilterRange });
        return null;
      }
      if ((paramFilterRange.authorityId == paramContentFilterSelection.authorityId) && (selectionMatchesDocumentTypes(paramFilterRange.documentType, paramContentFilterSelection.documentTypes))) {
        for (int i = 0; i < paramFilterRange.filterChoice.length; i++)
        {
          ContentFilters.FilterChoice localFilterChoice = paramFilterRange.filterChoice[i];
          if ((localFilterChoice.hasLevel) && (localFilterChoice.hasDfeHeaderValue) && (paramContentFilterSelection.level == localFilterChoice.level)) {
            return localFilterChoice.dfeHeaderValue;
          }
        }
      }
    }
  }
  
  public static ContentFilterSelection[] getSelectionsFromPref(PreferenceFile.SharedPreference<String> paramSharedPreference)
  {
    ContentFilterSelection[] arrayOfContentFilterSelection = decodeSelections((String)paramSharedPreference.get());
    if (arrayOfContentFilterSelection == null) {
      paramSharedPreference.remove();
    }
    return arrayOfContentFilterSelection;
  }
  
  public static boolean isFilterChoiceSelected(ContentFilters.FilterChoice paramFilterChoice, ContentFilterSelection paramContentFilterSelection)
  {
    boolean bool = true;
    if (paramContentFilterSelection == null) {
      bool = paramFilterChoice.selected;
    }
    do
    {
      do
      {
        return bool;
        if (!paramFilterChoice.hasLevel) {
          break;
        }
      } while (paramContentFilterSelection.level == paramFilterChoice.level);
      return false;
    } while (paramContentFilterSelection.level == SHOW_ALL_LEVEL);
    return false;
  }
  
  public static void savePreferences(boolean paramBoolean, ContentFilters.ContentFilterSettingsResponse paramContentFilterSettingsResponse, ContentFilterSelection[] paramArrayOfContentFilterSelection, PreferenceFile.SharedPreference<String> paramSharedPreference1, PreferenceFile.SharedPreference<String> paramSharedPreference2)
  {
    if (!paramBoolean) {
      paramSharedPreference2.remove();
    }
    while ((paramArrayOfContentFilterSelection == null) || (paramContentFilterSettingsResponse == null)) {
      return;
    }
    paramSharedPreference1.put(encodeSelections(paramArrayOfContentFilterSelection));
    ContentFilters.FilterRange[] arrayOfFilterRange = paramContentFilterSettingsResponse.filterRange;
    StringBuilder localStringBuilder = new StringBuilder();
    int i = 0;
    if (i < arrayOfFilterRange.length) {
      for (int j = 0;; j++) {
        if (j < paramArrayOfContentFilterSelection.length)
        {
          String str = getSelectedDfeHeader(arrayOfFilterRange[i], paramArrayOfContentFilterSelection[j]);
          if (str != null) {
            localStringBuilder.append(str);
          }
        }
        else
        {
          i++;
          break;
        }
      }
    }
    paramSharedPreference2.put(localStringBuilder.toString());
  }
  
  private static boolean selectionMatchesDocumentTypes(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    for (int i = 0; i < paramArrayOfInt2.length; i++) {
      for (int j = 0; j < paramArrayOfInt1.length; j++) {
        if (paramArrayOfInt2[i] == paramArrayOfInt1[j]) {
          return true;
        }
      }
    }
    return false;
  }
  
  public static ContentFilterSelection[] setOrAddSelection(ContentFilterSelection[] paramArrayOfContentFilterSelection, ContentFilters.FilterRange paramFilterRange, ContentFilters.FilterChoice paramFilterChoice)
  {
    int i = SHOW_ALL_LEVEL;
    if (paramFilterChoice.hasLevel) {
      i = paramFilterChoice.level;
    }
    ContentFilterSelection localContentFilterSelection = new ContentFilterSelection(paramFilterRange.documentType, paramFilterRange.authorityId, i);
    for (int j = 0; (paramArrayOfContentFilterSelection != null) && (j < paramArrayOfContentFilterSelection.length); j++) {
      if (selectionMatchesDocumentTypes(paramFilterRange.documentType, paramArrayOfContentFilterSelection[j].documentTypes))
      {
        paramArrayOfContentFilterSelection[j] = localContentFilterSelection;
        return paramArrayOfContentFilterSelection;
      }
    }
    if (paramArrayOfContentFilterSelection == null) {}
    for (ContentFilterSelection[] arrayOfContentFilterSelection = new ContentFilterSelection[1];; arrayOfContentFilterSelection = (ContentFilterSelection[])Arrays.copyOf(paramArrayOfContentFilterSelection, 1 + paramArrayOfContentFilterSelection.length))
    {
      arrayOfContentFilterSelection[(-1 + arrayOfContentFilterSelection.length)] = localContentFilterSelection;
      return arrayOfContentFilterSelection;
    }
  }
  
  public static final class ContentFilterSelection
  {
    public final int authorityId;
    public final int[] documentTypes;
    public final int level;
    
    public ContentFilterSelection(int[] paramArrayOfInt, int paramInt1, int paramInt2)
    {
      this.documentTypes = paramArrayOfInt;
      this.authorityId = paramInt1;
      this.level = paramInt2;
    }
    
    public final String toString()
    {
      Object[] arrayOfObject = new Object[3];
      arrayOfObject[0] = Integer.valueOf(this.authorityId);
      arrayOfObject[1] = Integer.valueOf(this.level);
      arrayOfObject[2] = Arrays.toString(this.documentTypes);
      return String.format("ContentFilterSelection{authorityId=%s, level=%s, documentTypes=%s}", arrayOfObject);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.config.ContentFiltersUtils
 * JD-Core Version:    0.7.0.1
 */