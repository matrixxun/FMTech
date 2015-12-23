package com.google.android.finsky.utils;

import android.opengl.GLES10;
import android.text.TextUtils;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;

public final class GlExtensionReader
{
  private boolean mWasSystemUpgraded = VendingUtils.wasSystemUpgraded();
  
  private static void addExtensionsForConfig(EGL10 paramEGL10, EGLDisplay paramEGLDisplay, EGLConfig paramEGLConfig, int[] paramArrayOfInt1, int[] paramArrayOfInt2, Set<String> paramSet)
  {
    EGLContext localEGLContext = paramEGL10.eglCreateContext(paramEGLDisplay, paramEGLConfig, EGL10.EGL_NO_CONTEXT, paramArrayOfInt2);
    if (localEGLContext == EGL10.EGL_NO_CONTEXT) {
      return;
    }
    EGLSurface localEGLSurface = paramEGL10.eglCreatePbufferSurface(paramEGLDisplay, paramEGLConfig, paramArrayOfInt1);
    if (localEGLSurface == EGL10.EGL_NO_SURFACE)
    {
      paramEGL10.eglDestroyContext(paramEGLDisplay, localEGLContext);
      return;
    }
    paramEGL10.eglMakeCurrent(paramEGLDisplay, localEGLSurface, localEGLSurface, localEGLContext);
    String str = GLES10.glGetString(7939);
    if (!TextUtils.isEmpty(str))
    {
      String[] arrayOfString = str.split(" ");
      int i = arrayOfString.length;
      for (int j = 0; j < i; j++) {
        paramSet.add(arrayOfString[j]);
      }
    }
    paramEGL10.eglMakeCurrent(paramEGLDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
    paramEGL10.eglDestroySurface(paramEGLDisplay, localEGLSurface);
    paramEGL10.eglDestroyContext(paramEGLDisplay, localEGLContext);
  }
  
  public final List<String> getGlExtensions()
  {
    ArrayList localArrayList = new ArrayList();
    String str1 = (String)VendingPreferences.CACHED_GL_EXTENSIONS.get();
    if ((!this.mWasSystemUpgraded) && (!TextUtils.isEmpty(str1)))
    {
      localArrayList.addAll(Arrays.asList(str1.split(" ")));
      return localArrayList;
    }
    Object localObject;
    if ((((Boolean)VendingPreferences.GL_DRIVER_CRASHED.get()).booleanValue()) && (!this.mWasSystemUpgraded))
    {
      localObject = new HashSet();
      ((Set)localObject).add("_android_driver_crashed");
      localArrayList.addAll((Collection)localObject);
      Collections.sort(localArrayList);
      String str2 = TextUtils.join(" ", localArrayList);
      VendingPreferences.CACHED_GL_EXTENSIONS.put(str2);
      return localArrayList;
    }
    VendingPreferences.GL_DRIVER_CRASHED.put(Boolean.valueOf(true));
    HashSet localHashSet = new HashSet();
    EGL10 localEGL10 = (EGL10)EGLContext.getEGL();
    if (localEGL10 == null)
    {
      FinskyLog.e("Couldn't get EGL", new Object[0]);
      localObject = localHashSet;
    }
    for (;;)
    {
      VendingPreferences.GL_DRIVER_CRASHED.remove();
      break;
      EGLDisplay localEGLDisplay = localEGL10.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
      localEGL10.eglInitialize(localEGLDisplay, new int[2]);
      int[] arrayOfInt1 = new int[1];
      if (!localEGL10.eglGetConfigs(localEGLDisplay, null, 0, arrayOfInt1))
      {
        FinskyLog.e("Couldn't get EGL config count", new Object[0]);
        localObject = localHashSet;
      }
      else
      {
        EGLConfig[] arrayOfEGLConfig = new EGLConfig[arrayOfInt1[0]];
        if (!localEGL10.eglGetConfigs(localEGLDisplay, arrayOfEGLConfig, arrayOfInt1[0], arrayOfInt1))
        {
          FinskyLog.e("Couldn't get EGL configs", new Object[0]);
          localObject = localHashSet;
        }
        else
        {
          int[] arrayOfInt2 = { 12375, 1, 12374, 1, 12344 };
          int[] arrayOfInt3 = { 12440, 2, 12344 };
          int[] arrayOfInt4 = new int[1];
          for (int i = 0; i < arrayOfInt1[0]; i++)
          {
            localEGL10.eglGetConfigAttrib(localEGLDisplay, arrayOfEGLConfig[i], 12327, arrayOfInt4);
            if (arrayOfInt4[0] != 12368)
            {
              localEGL10.eglGetConfigAttrib(localEGLDisplay, arrayOfEGLConfig[i], 12339, arrayOfInt4);
              if ((0x1 & arrayOfInt4[0]) != 0)
              {
                localEGL10.eglGetConfigAttrib(localEGLDisplay, arrayOfEGLConfig[i], 12352, arrayOfInt4);
                if ((0x1 & arrayOfInt4[0]) != 0) {
                  addExtensionsForConfig(localEGL10, localEGLDisplay, arrayOfEGLConfig[i], arrayOfInt2, null, localHashSet);
                }
                if ((0x4 & arrayOfInt4[0]) != 0) {
                  addExtensionsForConfig(localEGL10, localEGLDisplay, arrayOfEGLConfig[i], arrayOfInt2, arrayOfInt3, localHashSet);
                }
              }
            }
          }
          localEGL10.eglTerminate(localEGLDisplay);
          localObject = localHashSet;
        }
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.GlExtensionReader
 * JD-Core Version:    0.7.0.1
 */