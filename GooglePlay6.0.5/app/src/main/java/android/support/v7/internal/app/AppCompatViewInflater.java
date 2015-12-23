package android.support.v7.internal.app;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R.styleable;
import android.support.v7.internal.view.ContextThemeWrapper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.View;
import android.view.View.OnClickListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public final class AppCompatViewInflater
{
  private static final Map<String, Constructor<? extends View>> sConstructorMap = new ArrayMap();
  private static final Class<?>[] sConstructorSignature = { Context.class, AttributeSet.class };
  private static final int[] sOnClickAttrs = { 16843375 };
  private final Object[] mConstructorArgs = new Object[2];
  
  public static void checkOnClickListener(View paramView, AttributeSet paramAttributeSet)
  {
    Context localContext = paramView.getContext();
    if ((!ViewCompat.hasOnClickListeners(paramView)) || (!(localContext instanceof ContextWrapper))) {
      return;
    }
    TypedArray localTypedArray = localContext.obtainStyledAttributes(paramAttributeSet, sOnClickAttrs);
    String str = localTypedArray.getString(0);
    if (str != null) {
      paramView.setOnClickListener(new DeclaredOnClickListener(paramView, str));
    }
    localTypedArray.recycle();
  }
  
  private View createView(Context paramContext, String paramString1, String paramString2)
    throws ClassNotFoundException, InflateException
  {
    Constructor localConstructor = (Constructor)sConstructorMap.get(paramString1);
    if (localConstructor == null) {}
    try
    {
      ClassLoader localClassLoader = paramContext.getClassLoader();
      if (paramString2 != null) {}
      for (String str = paramString2 + paramString1;; str = paramString1)
      {
        localConstructor = localClassLoader.loadClass(str).asSubclass(View.class).getConstructor(sConstructorSignature);
        sConstructorMap.put(paramString1, localConstructor);
        localConstructor.setAccessible(true);
        View localView = (View)localConstructor.newInstance(this.mConstructorArgs);
        return localView;
      }
      return null;
    }
    catch (Exception localException) {}
  }
  
  public static Context themifyContext(Context paramContext, AttributeSet paramAttributeSet, boolean paramBoolean1, boolean paramBoolean2)
  {
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.View, 0, 0);
    int i = 0;
    if (paramBoolean1) {
      i = localTypedArray.getResourceId(R.styleable.View_android_theme, 0);
    }
    if (i == 0)
    {
      i = localTypedArray.getResourceId(R.styleable.View_theme, 0);
      if (i != 0) {
        Log.i("AppCompatViewInflater", "app:theme is now deprecated. Please move to using android:theme instead.");
      }
    }
    localTypedArray.recycle();
    if ((i != 0) && ((!(paramContext instanceof ContextThemeWrapper)) || (((ContextThemeWrapper)paramContext).mThemeResource != i))) {
      paramContext = new ContextThemeWrapper(paramContext, i);
    }
    return paramContext;
  }
  
  public final View createViewFromTag(Context paramContext, String paramString, AttributeSet paramAttributeSet)
  {
    if (paramString.equals("view")) {
      paramString = paramAttributeSet.getAttributeValue(null, "class");
    }
    try
    {
      this.mConstructorArgs[0] = paramContext;
      this.mConstructorArgs[1] = paramAttributeSet;
      if (-1 == paramString.indexOf('.'))
      {
        View localView2 = createView(paramContext, paramString, "android.widget.");
        return localView2;
      }
      View localView1 = createView(paramContext, paramString, null);
      return localView1;
    }
    catch (Exception localException)
    {
      return null;
    }
    finally
    {
      this.mConstructorArgs[0] = null;
      this.mConstructorArgs[1] = null;
    }
  }
  
  private static final class DeclaredOnClickListener
    implements View.OnClickListener
  {
    private final View mHostView;
    private final String mMethodName;
    private Context mResolvedContext;
    private Method mResolvedMethod;
    
    public DeclaredOnClickListener(View paramView, String paramString)
    {
      this.mHostView = paramView;
      this.mMethodName = paramString;
    }
    
    public final void onClick(View paramView)
    {
      Context localContext;
      if (this.mResolvedMethod == null) {
        localContext = this.mHostView.getContext();
      }
      while (localContext != null) {
        try
        {
          if (!localContext.isRestricted())
          {
            Method localMethod = localContext.getClass().getMethod(this.mMethodName, new Class[] { View.class });
            if (localMethod != null)
            {
              this.mResolvedMethod = localMethod;
              this.mResolvedContext = localContext;
            }
          }
        }
        catch (NoSuchMethodException localNoSuchMethodException)
        {
          try
          {
            this.mResolvedMethod.invoke(this.mResolvedContext, new Object[] { paramView });
            return;
          }
          catch (IllegalAccessException localIllegalAccessException)
          {
            int i;
            String str;
            throw new IllegalStateException("Could not execute non-public method for android:onClick", localIllegalAccessException);
          }
          catch (InvocationTargetException localInvocationTargetException)
          {
            throw new IllegalStateException("Could not execute method for android:onClick", localInvocationTargetException);
          }
          localNoSuchMethodException = localNoSuchMethodException;
          if ((localContext instanceof ContextWrapper)) {
            localContext = ((ContextWrapper)localContext).getBaseContext();
          } else {
            localContext = null;
          }
        }
      }
      i = this.mHostView.getId();
      if (i == -1) {}
      for (str = "";; str = " with id '" + this.mHostView.getContext().getResources().getResourceEntryName(i) + "'") {
        throw new IllegalStateException("Could not find method " + this.mMethodName + "(View) in a parent or ancestor Context for android:onClick attribute defined on view " + this.mHostView.getClass() + str);
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.internal.app.AppCompatViewInflater
 * JD-Core Version:    0.7.0.1
 */