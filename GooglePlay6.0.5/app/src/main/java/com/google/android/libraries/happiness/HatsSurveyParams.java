package com.google.android.libraries.happiness;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class HatsSurveyParams
{
  private final Map<String, String> mLoggingParams = new HashMap();
  private final Map<String, String> mParams = new HashMap();
  
  public HatsSurveyParams(String paramString1, String paramString2)
  {
    setParam("site_id", paramString2);
    if (paramString1 != null) {
      setParam("zwieback_cookie", paramString1);
    }
    setParam("survey_url", "http://www.google.com/insights/consumersurveys/async_survey");
    setParam("locale", "en-US");
  }
  
  private HatsSurveyParams setParam(String paramString1, String paramString2)
  {
    this.mParams.put(paramString1, paramString2);
    return this;
  }
  
  public final String getParam(String paramString)
  {
    return (String)this.mParams.get(paramString);
  }
  
  public final String toJS(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString + "['params'] = {};\n");
    localStringBuilder.append(paramString + "['logging_params'] = {};\n");
    Iterator localIterator1 = this.mParams.entrySet().iterator();
    while (localIterator1.hasNext())
    {
      Map.Entry localEntry2 = (Map.Entry)localIterator1.next();
      String str2 = paramString + "['params']['%s'] = '%s';\n";
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = localEntry2.getKey();
      arrayOfObject2[1] = localEntry2.getValue();
      localStringBuilder.append(String.format(str2, arrayOfObject2));
    }
    Iterator localIterator2 = this.mLoggingParams.entrySet().iterator();
    while (localIterator2.hasNext())
    {
      Map.Entry localEntry1 = (Map.Entry)localIterator2.next();
      String str1 = paramString + "['logging_params']['%s'] = '%s'\n;";
      Object[] arrayOfObject1 = new Object[2];
      arrayOfObject1[0] = localEntry1.getKey();
      arrayOfObject1[1] = localEntry1.getValue();
      localStringBuilder.append(String.format(str1, arrayOfObject1));
    }
    return localStringBuilder.toString();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.libraries.happiness.HatsSurveyParams
 * JD-Core Version:    0.7.0.1
 */