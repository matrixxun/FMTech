package org.keyczar.i18n;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public final class Messages
{
  private static ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("org.keyczar.i18n.messages");
  
  public static String getString(String paramString, Object... paramVarArgs)
  {
    try
    {
      String str = MessageFormat.format(RESOURCE_BUNDLE.getString(paramString), paramVarArgs);
      return str;
    }
    catch (MissingResourceException localMissingResourceException) {}
    return "!" + paramString + '!';
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     org.keyczar.i18n.Messages
 * JD-Core Version:    0.7.0.1
 */