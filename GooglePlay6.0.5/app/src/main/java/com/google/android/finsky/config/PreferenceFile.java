package com.google.android.finsky.config;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;

public final class PreferenceFile
{
  public static Context sContext;
  private final int mMode;
  private final String mName;
  
  public PreferenceFile(String paramString)
  {
    this.mName = paramString;
    this.mMode = 0;
  }
  
  @SuppressLint({"NewApi"})
  public static boolean commit(SharedPreferences.Editor paramEditor)
  {
    if (Build.VERSION.SDK_INT < 9) {
      return paramEditor.commit();
    }
    paramEditor.apply();
    return true;
  }
  
  public final void clear()
  {
    open().edit().clear().commit();
  }
  
  public final SharedPreferences open()
  {
    return sContext.getSharedPreferences(this.mName, this.mMode);
  }
  
  public final PrefixSharedPreference<Boolean> prefixPreference(String paramString, Boolean paramBoolean)
  {
    new PrefixSharedPreference(this, paramString, paramBoolean)
    {
      protected final PreferenceFile.SharedPreference<Boolean> valueWithKey(String paramAnonymousString)
      {
        return PreferenceFile.this.value(paramAnonymousString, (Boolean)this.mDefaultValue);
      }
    };
  }
  
  public final PrefixSharedPreference<Integer> prefixPreference(String paramString, Integer paramInteger)
  {
    new PrefixSharedPreference(this, paramString, paramInteger)
    {
      protected final PreferenceFile.SharedPreference<Integer> valueWithKey(String paramAnonymousString)
      {
        return PreferenceFile.this.value(paramAnonymousString, (Integer)this.mDefaultValue);
      }
    };
  }
  
  public final PrefixSharedPreference<Long> prefixPreference(String paramString, Long paramLong)
  {
    new PrefixSharedPreference(this, paramString, paramLong)
    {
      protected final PreferenceFile.SharedPreference<Long> valueWithKey(String paramAnonymousString)
      {
        return PreferenceFile.this.value(paramAnonymousString, (Long)this.mDefaultValue);
      }
    };
  }
  
  public final PrefixSharedPreference<String> prefixPreference(String paramString1, String paramString2)
  {
    new PrefixSharedPreference(this, paramString1, paramString2)
    {
      protected final PreferenceFile.SharedPreference<String> valueWithKey(String paramAnonymousString)
      {
        return PreferenceFile.this.value(paramAnonymousString, (String)this.mDefaultValue);
      }
    };
  }
  
  public final SharedPreference<Boolean> value(String paramString, Boolean paramBoolean)
  {
    new SharedPreference(this, paramString, paramBoolean) {};
  }
  
  public final SharedPreference<Integer> value(String paramString, Integer paramInteger)
  {
    new SharedPreference(this, paramString, paramInteger) {};
  }
  
  public final SharedPreference<Long> value(String paramString, Long paramLong)
  {
    new SharedPreference(this, paramString, paramLong) {};
  }
  
  public final SharedPreference<String> value(String paramString1, String paramString2)
  {
    new SharedPreference(this, paramString1, paramString2) {};
  }
  
  public static abstract class PrefixSharedPreference<T>
  {
    protected final T mDefaultValue;
    PreferenceFile mFile;
    protected final String mPrefix;
    
    protected PrefixSharedPreference(PreferenceFile paramPreferenceFile, String paramString, T paramT)
    {
      this.mFile = paramPreferenceFile;
      this.mPrefix = paramString;
      this.mDefaultValue = paramT;
    }
    
    public final PreferenceFile.SharedPreference<T> get(int paramInt)
    {
      PreferenceFile.SharedPreference localSharedPreference = valueWithKey(this.mPrefix + paramInt);
      localSharedPreference.mFile = this.mFile;
      return localSharedPreference;
    }
    
    public final PreferenceFile.SharedPreference<T> get(String paramString)
    {
      PreferenceFile.SharedPreference localSharedPreference = valueWithKey(this.mPrefix + paramString);
      localSharedPreference.mFile = this.mFile;
      return localSharedPreference;
    }
    
    protected abstract PreferenceFile.SharedPreference<T> valueWithKey(String paramString);
  }
  
  public static abstract class SharedPreference<T>
  {
    final T mDefaultValue;
    PreferenceFile mFile;
    final String mKey;
    
    protected SharedPreference(PreferenceFile paramPreferenceFile, String paramString, T paramT)
    {
      this.mFile = paramPreferenceFile;
      this.mKey = paramString;
      this.mDefaultValue = paramT;
    }
    
    public final boolean exists()
    {
      return this.mFile.open().contains(this.mKey);
    }
    
    public final T get()
    {
      return read(this.mFile.open());
    }
    
    public final void put(T paramT)
    {
      SharedPreferences.Editor localEditor = this.mFile.open().edit();
      write(localEditor, paramT);
      PreferenceFile.commit(localEditor);
    }
    
    protected abstract T read(SharedPreferences paramSharedPreferences);
    
    public final void remove()
    {
      PreferenceFile.commit(this.mFile.open().edit().remove(this.mKey));
    }
    
    protected abstract void write(SharedPreferences.Editor paramEditor, T paramT);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.config.PreferenceFile
 * JD-Core Version:    0.7.0.1
 */