package com.google.android.volley.guava;

import android.content.ContentResolver;
import android.util.Log;
import com.google.android.gsf.Gservices;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Deprecated
public final class UrlRules
{
  private static final Pattern PATTERN_SPACE_PLUS = Pattern.compile(" +");
  private static final Pattern RULE_PATTERN = Pattern.compile("\\W");
  private static UrlRules sCachedRules = new UrlRules(new Rule[0]);
  private static Object sCachedVersionToken;
  private final Pattern mPattern;
  private final Rule[] mRules;
  
  private UrlRules(Rule[] paramArrayOfRule)
  {
    Arrays.sort(paramArrayOfRule);
    StringBuilder localStringBuilder = new StringBuilder("(");
    for (int i = 0; i < paramArrayOfRule.length; i++)
    {
      if (i > 0) {
        localStringBuilder.append(")|(");
      }
      localStringBuilder.append(RULE_PATTERN.matcher(paramArrayOfRule[i].mPrefix).replaceAll("\\\\$0"));
    }
    this.mPattern = Pattern.compile(")");
    this.mRules = paramArrayOfRule;
  }
  
  public static UrlRules getRules(ContentResolver paramContentResolver)
  {
    for (;;)
    {
      Object localObject2;
      try
      {
        localObject2 = Gservices.getVersionToken(paramContentResolver);
        if (localObject2 == sCachedVersionToken)
        {
          if (Log.isLoggable("UrlRules", 2)) {
            Log.v("UrlRules", "Using cached rules, versionToken: " + localObject2);
          }
          localUrlRules = sCachedRules;
          return localUrlRules;
        }
        if (Log.isLoggable("UrlRules", 2)) {
          Log.v("UrlRules", "Scanning for Gservices \"url:*\" rules");
        }
        Map localMap = Gservices.getStringsByPrefix(paramContentResolver, new String[] { "url:" });
        ArrayList localArrayList = new ArrayList();
        Iterator localIterator = localMap.entrySet().iterator();
        if (localIterator.hasNext())
        {
          Map.Entry localEntry = (Map.Entry)localIterator.next();
          try
          {
            String str1 = ((String)localEntry.getKey()).substring(4);
            String str2 = (String)localEntry.getValue();
            if ((str2 == null) || (str2.length() == 0)) {
              continue;
            }
            if (Log.isLoggable("UrlRules", 2)) {
              Log.v("UrlRules", "  Rule " + str1 + ": " + str2);
            }
            localArrayList.add(new Rule(str1, str2));
          }
          catch (RuleFormatException localRuleFormatException)
          {
            Log.e("UrlRules", "Invalid rule from Gservices", localRuleFormatException);
          }
          continue;
        }
        sCachedRules = new UrlRules((Rule[])localArrayList.toArray(new Rule[localArrayList.size()]));
      }
      finally {}
      sCachedVersionToken = localObject2;
      if (Log.isLoggable("UrlRules", 2)) {
        Log.v("UrlRules", "New rules stored, versionToken: " + localObject2);
      }
      UrlRules localUrlRules = sCachedRules;
    }
  }
  
  public final Rule matchRule(String paramString)
  {
    Matcher localMatcher = this.mPattern.matcher(paramString);
    if (localMatcher.lookingAt()) {
      for (int i = 0; i < this.mRules.length; i++) {
        if (localMatcher.group(i + 1) != null) {
          return this.mRules[i];
        }
      }
    }
    return Rule.DEFAULT;
  }
  
  public static final class Rule
    implements Comparable
  {
    public static final Rule DEFAULT = new Rule();
    public final boolean mBlock;
    public final String mName;
    public final String mPrefix;
    public final String mRewrite;
    
    private Rule()
    {
      this.mName = "DEFAULT";
      this.mPrefix = "";
      this.mRewrite = null;
      this.mBlock = false;
    }
    
    public Rule(String paramString1, String paramString2)
      throws UrlRules.RuleFormatException
    {
      this.mName = paramString1;
      String[] arrayOfString = UrlRules.PATTERN_SPACE_PLUS.split(paramString2);
      if (arrayOfString.length == 0) {
        throw new UrlRules.RuleFormatException("Empty rule");
      }
      this.mPrefix = arrayOfString[0];
      String str1 = null;
      boolean bool = false;
      int i = 1;
      while (i < arrayOfString.length)
      {
        String str2 = arrayOfString[i].toLowerCase();
        if ((str2.equals("rewrite")) && (i + 1 < arrayOfString.length))
        {
          str1 = arrayOfString[(i + 1)];
          i += 2;
        }
        else if (str2.equals("block"))
        {
          bool = true;
          i++;
        }
        else
        {
          throw new UrlRules.RuleFormatException("Illegal rule: " + paramString2);
        }
      }
      this.mRewrite = str1;
      this.mBlock = bool;
    }
    
    public final String apply(String paramString)
    {
      if (this.mBlock) {
        paramString = null;
      }
      while (this.mRewrite == null) {
        return paramString;
      }
      return this.mRewrite + paramString.substring(this.mPrefix.length());
    }
    
    public final int compareTo(Object paramObject)
    {
      return ((Rule)paramObject).mPrefix.compareTo(this.mPrefix);
    }
  }
  
  public static final class RuleFormatException
    extends Exception
  {
    public RuleFormatException(String paramString)
    {
      super();
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.volley.guava.UrlRules
 * JD-Core Version:    0.7.0.1
 */