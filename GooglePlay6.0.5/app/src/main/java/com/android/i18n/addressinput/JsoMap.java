package com.android.i18n.addressinput;

import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

final class JsoMap
  extends JSONObject
{
  protected JsoMap() {}
  
  private JsoMap(JSONObject paramJSONObject, String[] paramArrayOfString)
    throws JSONException
  {
    super(paramJSONObject, paramArrayOfString);
  }
  
  private JsoMap(JSONTokener paramJSONTokener)
    throws JSONException
  {
    super(paramJSONTokener);
  }
  
  static JsoMap buildJsoMap(String paramString)
    throws JSONException
  {
    return new JsoMap(new JSONTokener(paramString));
  }
  
  static JsoMap createEmptyJsoMap()
  {
    return new JsoMap();
  }
  
  final boolean containsKey(String paramString)
  {
    return super.has(paramString);
  }
  
  public final String get(String paramString)
  {
    try
    {
      Object localObject = super.get(paramString);
      if ((localObject instanceof String)) {
        return (String)localObject;
      }
      if ((localObject instanceof Integer)) {
        throw new IllegalArgumentException();
      }
      throw new ClassCastException();
    }
    catch (JSONException localJSONException) {}
    return null;
  }
  
  public final int getInt(String paramString)
  {
    try
    {
      Object localObject = super.get(paramString);
      if ((localObject instanceof Integer)) {
        return ((Integer)localObject).intValue();
      }
      throw new RuntimeException("Something other than an int was returned");
    }
    catch (JSONException localJSONException) {}
    return -1;
  }
  
  final JSONArray getKeys()
  {
    return super.names();
  }
  
  final JsoMap getObj(String paramString)
    throws ClassCastException, IllegalArgumentException
  {
    try
    {
      Object localObject = super.get(paramString);
      if ((localObject instanceof JSONObject))
      {
        JSONObject localJSONObject = (JSONObject)localObject;
        ArrayList localArrayList = new ArrayList(localJSONObject.length());
        Iterator localIterator = localJSONObject.keys();
        while (localIterator.hasNext()) {
          localArrayList.add(localIterator.next());
        }
        return new JsoMap(localJSONObject, (String[])localArrayList.toArray(new String[localArrayList.size()]));
      }
      if ((localObject instanceof Integer)) {
        throw new IllegalArgumentException();
      }
      throw new ClassCastException();
    }
    catch (JSONException localJSONException) {}
    return null;
  }
  
  final void mergeData(JsoMap paramJsoMap)
  {
    if (paramJsoMap == null) {}
    JSONArray localJSONArray;
    do
    {
      return;
      localJSONArray = paramJsoMap.names();
    } while (localJSONArray == null);
    int i = 0;
    while (i < localJSONArray.length()) {
      for (;;)
      {
        try
        {
          str = localJSONArray.getString(i);
        }
        catch (JSONException localJSONException1)
        {
          String str;
          continue;
        }
        try
        {
          if (!super.has(str)) {
            super.put(str, paramJsoMap.get(str));
          }
          i++;
        }
        catch (JSONException localJSONException2)
        {
          throw new RuntimeException(localJSONException2);
        }
      }
    }
  }
  
  final void putObj(String paramString, JSONObject paramJSONObject)
  {
    try
    {
      super.put(paramString, paramJSONObject);
      return;
    }
    catch (JSONException localJSONException)
    {
      throw new RuntimeException(localJSONException);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.i18n.addressinput.JsoMap
 * JD-Core Version:    0.7.0.1
 */