package android.support.v4.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory2;
import android.view.View;
import java.lang.reflect.Field;

final class LayoutInflaterCompatHC
{
  private static boolean sCheckedField;
  private static Field sLayoutInflaterFactory2Field;
  
  static void forceSetFactory2(LayoutInflater paramLayoutInflater, LayoutInflater.Factory2 paramFactory2)
  {
    if (!sCheckedField) {}
    try
    {
      Field localField = LayoutInflater.class.getDeclaredField("mFactory2");
      sLayoutInflaterFactory2Field = localField;
      localField.setAccessible(true);
      sCheckedField = true;
      if (sLayoutInflaterFactory2Field == null) {}
    }
    catch (NoSuchFieldException localNoSuchFieldException)
    {
      for (;;)
      {
        try
        {
          sLayoutInflaterFactory2Field.set(paramLayoutInflater, paramFactory2);
          return;
        }
        catch (IllegalAccessException localIllegalAccessException)
        {
          Log.e("LayoutInflaterCompatHC", "forceSetFactory2 could not set the Factory2 on LayoutInflater " + paramLayoutInflater + "; inflation may have unexpected results.", localIllegalAccessException);
        }
        localNoSuchFieldException = localNoSuchFieldException;
        Log.e("LayoutInflaterCompatHC", "forceSetFactory2 Could not find field 'mFactory2' on class " + LayoutInflater.class.getName() + "; inflation may have unexpected results.", localNoSuchFieldException);
      }
    }
  }
  
  static final class FactoryWrapperHC
    extends LayoutInflaterCompatBase.FactoryWrapper
    implements LayoutInflater.Factory2
  {
    FactoryWrapperHC(LayoutInflaterFactory paramLayoutInflaterFactory)
    {
      super();
    }
    
    public final View onCreateView(View paramView, String paramString, Context paramContext, AttributeSet paramAttributeSet)
    {
      return this.mDelegateFactory.onCreateView(paramView, paramString, paramContext, paramAttributeSet);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v4.view.LayoutInflaterCompatHC
 * JD-Core Version:    0.7.0.1
 */