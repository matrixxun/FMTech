package com.squareup.okhttp;

import com.squareup.okhttp.internal.http.HttpDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public final class Headers
{
  public final String[] namesAndValues;
  
  private Headers(Builder paramBuilder)
  {
    this.namesAndValues = ((String[])paramBuilder.namesAndValues.toArray(new String[paramBuilder.namesAndValues.size()]));
  }
  
  public final String get(String paramString)
  {
    String[] arrayOfString = this.namesAndValues;
    for (int i = -2 + arrayOfString.length; i >= 0; i -= 2) {
      if (paramString.equalsIgnoreCase(arrayOfString[i])) {
        return arrayOfString[(i + 1)];
      }
    }
    return null;
  }
  
  public final Date getDate(String paramString)
  {
    String str = get(paramString);
    if (str != null) {
      return HttpDate.parse(str);
    }
    return null;
  }
  
  public final String name(int paramInt)
  {
    int i = paramInt * 2;
    if ((i < 0) || (i >= this.namesAndValues.length)) {
      return null;
    }
    return this.namesAndValues[i];
  }
  
  public final Set<String> names()
  {
    TreeSet localTreeSet = new TreeSet(String.CASE_INSENSITIVE_ORDER);
    int i = 0;
    int j = this.namesAndValues.length / 2;
    while (i < j)
    {
      localTreeSet.add(name(i));
      i++;
    }
    return Collections.unmodifiableSet(localTreeSet);
  }
  
  public final Builder newBuilder()
  {
    Builder localBuilder = new Builder();
    localBuilder.namesAndValues.addAll(Arrays.asList(this.namesAndValues));
    return localBuilder;
  }
  
  public final String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    int i = 0;
    int j = this.namesAndValues.length / 2;
    while (i < j)
    {
      localStringBuilder.append(name(i)).append(": ").append(value(i)).append("\n");
      i++;
    }
    return localStringBuilder.toString();
  }
  
  public final String value(int paramInt)
  {
    int i = 1 + paramInt * 2;
    if ((i < 0) || (i >= this.namesAndValues.length)) {
      return null;
    }
    return this.namesAndValues[i];
  }
  
  public final List<String> values(String paramString)
  {
    ArrayList localArrayList = null;
    int i = 0;
    int j = this.namesAndValues.length / 2;
    while (i < j)
    {
      if (paramString.equalsIgnoreCase(name(i)))
      {
        if (localArrayList == null) {
          localArrayList = new ArrayList(2);
        }
        localArrayList.add(value(i));
      }
      i++;
    }
    if (localArrayList != null) {
      return Collections.unmodifiableList(localArrayList);
    }
    return Collections.emptyList();
  }
  
  public static final class Builder
  {
    final List<String> namesAndValues = new ArrayList(20);
    
    public final Builder add(String paramString1, String paramString2)
    {
      if (paramString1 == null) {
        throw new IllegalArgumentException("name == null");
      }
      if (paramString2 == null) {
        throw new IllegalArgumentException("value == null");
      }
      if ((paramString1.length() == 0) || (paramString1.indexOf(0) != -1) || (paramString2.indexOf(0) != -1)) {
        throw new IllegalArgumentException("Unexpected header: " + paramString1 + ": " + paramString2);
      }
      return addLenient(paramString1, paramString2);
    }
    
    final Builder addLenient(String paramString1, String paramString2)
    {
      this.namesAndValues.add(paramString1);
      this.namesAndValues.add(paramString2.trim());
      return this;
    }
    
    public final Headers build()
    {
      return new Headers(this, (byte)0);
    }
    
    public final Builder removeAll(String paramString)
    {
      for (int i = 0; i < this.namesAndValues.size(); i += 2) {
        if (paramString.equalsIgnoreCase((String)this.namesAndValues.get(i)))
        {
          this.namesAndValues.remove(i);
          this.namesAndValues.remove(i);
          i -= 2;
        }
      }
      return this;
    }
    
    public final Builder set(String paramString1, String paramString2)
    {
      removeAll(paramString1);
      add(paramString1, paramString2);
      return this;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.Headers
 * JD-Core Version:    0.7.0.1
 */