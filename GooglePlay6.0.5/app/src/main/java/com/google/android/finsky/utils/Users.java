package com.google.android.finsky.utils;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.UserManager;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public final class Users
{
  public final UserManagerFacade mUserManagerFacade;
  
  public Users(Context paramContext, DeviceManagementHelper paramDeviceManagementHelper)
  {
    if (Build.VERSION.SDK_INT < 17)
    {
      this.mUserManagerFacade = new UserManagerFacade((byte)0);
      return;
    }
    try
    {
      localObject = new UserManagerSystemFacade(paramContext, paramDeviceManagementHelper);
      this.mUserManagerFacade = ((UserManagerFacade)localObject);
      return;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      for (;;)
      {
        FinskyLog.wtf("Unable to reflect into UserManager: %s", new Object[] { localNoSuchMethodException });
        Object localObject = new UserManagerFacade((byte)0);
      }
    }
  }
  
  private static class UserManagerFacade
  {
    public boolean hasMultipleUsers()
    {
      return false;
    }
    
    public boolean isLimitedUser()
    {
      return false;
    }
    
    public boolean mayHaveHiddenPackages()
    {
      return false;
    }
  }
  
  private static final class UserManagerSystemFacade
    extends Users.UserManagerFacade
  {
    private DeviceManagementHelper mDeviceManagementHelper;
    private Method mGetUsers;
    private Method mIsLimited;
    private UserManager mUserManager;
    
    public UserManagerSystemFacade(Context paramContext, DeviceManagementHelper paramDeviceManagementHelper)
      throws NoSuchMethodException
    {
      super();
      this.mUserManager = ((UserManager)paramContext.getSystemService("user"));
      this.mDeviceManagementHelper = paramDeviceManagementHelper;
      Class localClass1 = this.mUserManager.getClass();
      Class[] arrayOfClass = new Class[0];
      Method localMethod1 = localClass1.getMethod("getUsers", arrayOfClass);
      Class localClass2 = localMethod1.getReturnType();
      if (localClass2.equals(List.class)) {}
      Class localClass3;
      for (this.mGetUsers = localMethod1;; this.mGetUsers = null)
      {
        if (Build.VERSION.SDK_INT >= 18)
        {
          Method localMethod2 = localClass1.getMethod("isLinkedUser", arrayOfClass);
          localClass3 = localMethod2.getReturnType();
          if (!localClass3.equals(Boolean.TYPE)) {
            break;
          }
          this.mIsLimited = localMethod2;
        }
        return;
        FinskyLog.wtf("Return type %s is not correct for getUsers", new Object[] { localClass2 });
      }
      FinskyLog.wtf("Return type %s is not correct for isLimited", new Object[] { localClass3 });
      this.mIsLimited = null;
    }
    
    public final boolean hasMultipleUsers()
    {
      if (this.mGetUsers == null) {
        return super.hasMultipleUsers();
      }
      try
      {
        int i = ((List)this.mGetUsers.invoke(this.mUserManager, null)).size();
        return i > 1;
      }
      catch (InvocationTargetException localInvocationTargetException)
      {
        return false;
      }
      catch (IllegalAccessException localIllegalAccessException)
      {
        break label45;
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        label45:
        break label45;
      }
    }
    
    public final boolean isLimitedUser()
    {
      if (this.mIsLimited == null) {
        return super.isLimitedUser();
      }
      try
      {
        boolean bool = ((Boolean)this.mIsLimited.invoke(this.mUserManager, null)).booleanValue();
        return bool;
      }
      catch (InvocationTargetException localInvocationTargetException)
      {
        return false;
      }
      catch (IllegalAccessException localIllegalAccessException)
      {
        break label36;
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        label36:
        break label36;
      }
    }
    
    public final boolean mayHaveHiddenPackages()
    {
      return (hasMultipleUsers()) || (this.mDeviceManagementHelper.isManaged(null));
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.Users
 * JD-Core Version:    0.7.0.1
 */