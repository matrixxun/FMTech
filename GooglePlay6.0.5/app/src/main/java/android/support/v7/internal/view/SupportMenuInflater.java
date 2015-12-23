package android.support.v7.internal.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.internal.view.SupportMenuItem;
import android.support.v4.view.ActionProvider;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.appcompat.R.styleable;
import android.support.v7.internal.view.menu.MenuItemImpl;
import android.support.v7.internal.view.menu.MenuItemWrapperICS;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.SubMenu;
import android.view.View;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public final class SupportMenuInflater
  extends MenuInflater
{
  private static final Class<?>[] ACTION_PROVIDER_CONSTRUCTOR_SIGNATURE;
  private static final Class<?>[] ACTION_VIEW_CONSTRUCTOR_SIGNATURE;
  private final Object[] mActionProviderConstructorArguments;
  private final Object[] mActionViewConstructorArguments;
  private Context mContext;
  private Object mRealOwner;
  
  static
  {
    Class[] arrayOfClass = { Context.class };
    ACTION_VIEW_CONSTRUCTOR_SIGNATURE = arrayOfClass;
    ACTION_PROVIDER_CONSTRUCTOR_SIGNATURE = arrayOfClass;
  }
  
  public SupportMenuInflater(Context paramContext)
  {
    super(paramContext);
    this.mContext = paramContext;
    this.mActionViewConstructorArguments = new Object[] { paramContext };
    this.mActionProviderConstructorArguments = this.mActionViewConstructorArguments;
  }
  
  private void parseMenu(XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet, Menu paramMenu)
    throws XmlPullParserException, IOException
  {
    MenuState localMenuState = new MenuState(paramMenu);
    int i = paramXmlPullParser.getEventType();
    int j = 0;
    Object localObject = null;
    String str3;
    label57:
    int k;
    if (i == 2)
    {
      str3 = paramXmlPullParser.getName();
      if (str3.equals("menu"))
      {
        i = paramXmlPullParser.next();
        k = 0;
        label60:
        if (k != 0) {
          return;
        }
      }
    }
    switch (i)
    {
    default: 
    case 2: 
    case 3: 
      for (;;)
      {
        i = paramXmlPullParser.next();
        break label60;
        throw new RuntimeException("Expecting menu, got " + str3);
        i = paramXmlPullParser.next();
        if (i != 1) {
          break;
        }
        break label57;
        if (j == 0)
        {
          String str2 = paramXmlPullParser.getName();
          if (str2.equals("group"))
          {
            TypedArray localTypedArray2 = localMenuState.this$0.mContext.obtainStyledAttributes(paramAttributeSet, R.styleable.MenuGroup);
            localMenuState.groupId = localTypedArray2.getResourceId(R.styleable.MenuGroup_android_id, 0);
            localMenuState.groupCategory = localTypedArray2.getInt(R.styleable.MenuGroup_android_menuCategory, 0);
            localMenuState.groupOrder = localTypedArray2.getInt(R.styleable.MenuGroup_android_orderInCategory, 0);
            localMenuState.groupCheckable = localTypedArray2.getInt(R.styleable.MenuGroup_android_checkableBehavior, 0);
            localMenuState.groupVisible = localTypedArray2.getBoolean(R.styleable.MenuGroup_android_visible, true);
            localMenuState.groupEnabled = localTypedArray2.getBoolean(R.styleable.MenuGroup_android_enabled, true);
            localTypedArray2.recycle();
          }
          else
          {
            if (str2.equals("item"))
            {
              TypedArray localTypedArray1 = localMenuState.this$0.mContext.obtainStyledAttributes(paramAttributeSet, R.styleable.MenuItem);
              localMenuState.itemId = localTypedArray1.getResourceId(R.styleable.MenuItem_android_id, 0);
              int m = localTypedArray1.getInt(R.styleable.MenuItem_android_menuCategory, localMenuState.groupCategory);
              int n = localTypedArray1.getInt(R.styleable.MenuItem_android_orderInCategory, localMenuState.groupOrder);
              localMenuState.itemCategoryOrder = (m & 0xFFFF0000 | n & 0xFFFF);
              localMenuState.itemTitle = localTypedArray1.getText(R.styleable.MenuItem_android_title);
              localMenuState.itemTitleCondensed = localTypedArray1.getText(R.styleable.MenuItem_android_titleCondensed);
              localMenuState.itemIconResId = localTypedArray1.getResourceId(R.styleable.MenuItem_android_icon, 0);
              localMenuState.itemAlphabeticShortcut = MenuState.getShortcut(localTypedArray1.getString(R.styleable.MenuItem_android_alphabeticShortcut));
              localMenuState.itemNumericShortcut = MenuState.getShortcut(localTypedArray1.getString(R.styleable.MenuItem_android_numericShortcut));
              int i2;
              label462:
              label469:
              int i1;
              if (localTypedArray1.hasValue(R.styleable.MenuItem_android_checkable)) {
                if (localTypedArray1.getBoolean(R.styleable.MenuItem_android_checkable, false))
                {
                  i2 = 1;
                  localMenuState.itemCheckable = i2;
                  localMenuState.itemChecked = localTypedArray1.getBoolean(R.styleable.MenuItem_android_checked, false);
                  localMenuState.itemVisible = localTypedArray1.getBoolean(R.styleable.MenuItem_android_visible, localMenuState.groupVisible);
                  localMenuState.itemEnabled = localTypedArray1.getBoolean(R.styleable.MenuItem_android_enabled, localMenuState.groupEnabled);
                  localMenuState.itemShowAsAction = localTypedArray1.getInt(R.styleable.MenuItem_showAsAction, -1);
                  localMenuState.itemListenerMethodName = localTypedArray1.getString(R.styleable.MenuItem_android_onClick);
                  localMenuState.itemActionViewLayout = localTypedArray1.getResourceId(R.styleable.MenuItem_actionLayout, 0);
                  localMenuState.itemActionViewClassName = localTypedArray1.getString(R.styleable.MenuItem_actionViewClass);
                  localMenuState.itemActionProviderClassName = localTypedArray1.getString(R.styleable.MenuItem_actionProviderClass);
                  if (localMenuState.itemActionProviderClassName == null) {
                    break label680;
                  }
                  i1 = 1;
                  label597:
                  if ((i1 == 0) || (localMenuState.itemActionViewLayout != 0) || (localMenuState.itemActionViewClassName != null)) {
                    break label686;
                  }
                }
              }
              for (localMenuState.itemActionProvider = ((ActionProvider)localMenuState.newInstance(localMenuState.itemActionProviderClassName, ACTION_PROVIDER_CONSTRUCTOR_SIGNATURE, localMenuState.this$0.mActionProviderConstructorArguments));; localMenuState.itemActionProvider = null)
              {
                localTypedArray1.recycle();
                localMenuState.itemAdded = false;
                break;
                i2 = 0;
                break label462;
                localMenuState.itemCheckable = localMenuState.groupCheckable;
                break label469;
                label680:
                i1 = 0;
                break label597;
                label686:
                if (i1 != 0) {
                  Log.w("SupportMenuInflater", "Ignoring attribute 'actionProviderClass'. Action view already specified.");
                }
              }
            }
            if (str2.equals("menu"))
            {
              parseMenu(paramXmlPullParser, paramAttributeSet, localMenuState.addSubMenuItem());
            }
            else
            {
              j = 1;
              localObject = str2;
              continue;
              String str1 = paramXmlPullParser.getName();
              if ((j != 0) && (str1.equals(localObject)))
              {
                j = 0;
                localObject = null;
              }
              else if (str1.equals("group"))
              {
                localMenuState.resetGroup();
              }
              else if (str1.equals("item"))
              {
                if (!localMenuState.itemAdded) {
                  if ((localMenuState.itemActionProvider != null) && (localMenuState.itemActionProvider.hasSubMenu()))
                  {
                    localMenuState.addSubMenuItem();
                  }
                  else
                  {
                    localMenuState.itemAdded = true;
                    localMenuState.setItem(localMenuState.menu.add(localMenuState.groupId, localMenuState.itemId, localMenuState.itemCategoryOrder, localMenuState.itemTitle));
                  }
                }
              }
              else if (str1.equals("menu"))
              {
                k = 1;
              }
            }
          }
        }
      }
    }
    throw new RuntimeException("Unexpected end of document");
  }
  
  /* Error */
  public final void inflate(int paramInt, Menu paramMenu)
  {
    // Byte code:
    //   0: aload_2
    //   1: instanceof 354
    //   4: ifne +10 -> 14
    //   7: aload_0
    //   8: iload_1
    //   9: aload_2
    //   10: invokespecial 356	android/view/MenuInflater:inflate	(ILandroid/view/Menu;)V
    //   13: return
    //   14: aconst_null
    //   15: astore_3
    //   16: aload_0
    //   17: getfield 31	android/support/v7/internal/view/SupportMenuInflater:mContext	Landroid/content/Context;
    //   20: invokevirtual 360	android/content/Context:getResources	()Landroid/content/res/Resources;
    //   23: iload_1
    //   24: invokevirtual 366	android/content/res/Resources:getLayout	(I)Landroid/content/res/XmlResourceParser;
    //   27: astore_3
    //   28: aload_0
    //   29: aload_3
    //   30: aload_3
    //   31: invokestatic 372	android/util/Xml:asAttributeSet	(Lorg/xmlpull/v1/XmlPullParser;)Landroid/util/AttributeSet;
    //   34: aload_2
    //   35: invokespecial 328	android/support/v7/internal/view/SupportMenuInflater:parseMenu	(Lorg/xmlpull/v1/XmlPullParser;Landroid/util/AttributeSet;Landroid/view/Menu;)V
    //   38: aload_3
    //   39: ifnull -26 -> 13
    //   42: aload_3
    //   43: invokeinterface 377 1 0
    //   48: return
    //   49: astore 6
    //   51: new 379	android/view/InflateException
    //   54: dup
    //   55: ldc_w 381
    //   58: aload 6
    //   60: invokespecial 384	android/view/InflateException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   63: athrow
    //   64: astore 5
    //   66: aload_3
    //   67: ifnull +9 -> 76
    //   70: aload_3
    //   71: invokeinterface 377 1 0
    //   76: aload 5
    //   78: athrow
    //   79: astore 4
    //   81: new 379	android/view/InflateException
    //   84: dup
    //   85: ldc_w 381
    //   88: aload 4
    //   90: invokespecial 384	android/view/InflateException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   93: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	94	0	this	SupportMenuInflater
    //   0	94	1	paramInt	int
    //   0	94	2	paramMenu	Menu
    //   15	56	3	localXmlResourceParser	android.content.res.XmlResourceParser
    //   79	10	4	localIOException	IOException
    //   64	13	5	localObject	Object
    //   49	10	6	localXmlPullParserException	XmlPullParserException
    // Exception table:
    //   from	to	target	type
    //   16	38	49	org/xmlpull/v1/XmlPullParserException
    //   16	38	64	finally
    //   51	64	64	finally
    //   81	94	64	finally
    //   16	38	79	java/io/IOException
  }
  
  private static final class InflatedOnMenuItemClickListener
    implements MenuItem.OnMenuItemClickListener
  {
    private static final Class<?>[] PARAM_TYPES = { MenuItem.class };
    private Method mMethod;
    private Object mRealOwner;
    
    public InflatedOnMenuItemClickListener(Object paramObject, String paramString)
    {
      this.mRealOwner = paramObject;
      Class localClass = paramObject.getClass();
      try
      {
        this.mMethod = localClass.getMethod(paramString, PARAM_TYPES);
        return;
      }
      catch (Exception localException)
      {
        InflateException localInflateException = new InflateException("Couldn't resolve menu item onClick handler " + paramString + " in class " + localClass.getName());
        localInflateException.initCause(localException);
        throw localInflateException;
      }
    }
    
    public final boolean onMenuItemClick(MenuItem paramMenuItem)
    {
      try
      {
        if (this.mMethod.getReturnType() == Boolean.TYPE) {
          return ((Boolean)this.mMethod.invoke(this.mRealOwner, new Object[] { paramMenuItem })).booleanValue();
        }
        this.mMethod.invoke(this.mRealOwner, new Object[] { paramMenuItem });
        return true;
      }
      catch (Exception localException)
      {
        throw new RuntimeException(localException);
      }
    }
  }
  
  private final class MenuState
  {
    int groupCategory;
    int groupCheckable;
    boolean groupEnabled;
    int groupId;
    int groupOrder;
    boolean groupVisible;
    ActionProvider itemActionProvider;
    String itemActionProviderClassName;
    String itemActionViewClassName;
    int itemActionViewLayout;
    boolean itemAdded;
    char itemAlphabeticShortcut;
    int itemCategoryOrder;
    int itemCheckable;
    boolean itemChecked;
    boolean itemEnabled;
    int itemIconResId;
    int itemId;
    String itemListenerMethodName;
    char itemNumericShortcut;
    int itemShowAsAction;
    CharSequence itemTitle;
    CharSequence itemTitleCondensed;
    boolean itemVisible;
    Menu menu;
    
    public MenuState(Menu paramMenu)
    {
      this.menu = paramMenu;
      resetGroup();
    }
    
    static char getShortcut(String paramString)
    {
      if (paramString == null) {
        return '\000';
      }
      return paramString.charAt(0);
    }
    
    public final SubMenu addSubMenuItem()
    {
      this.itemAdded = true;
      SubMenu localSubMenu = this.menu.addSubMenu(this.groupId, this.itemId, this.itemCategoryOrder, this.itemTitle);
      setItem(localSubMenu.getItem());
      return localSubMenu;
    }
    
    final <T> T newInstance(String paramString, Class<?>[] paramArrayOfClass, Object[] paramArrayOfObject)
    {
      try
      {
        Constructor localConstructor = SupportMenuInflater.this.mContext.getClassLoader().loadClass(paramString).getConstructor(paramArrayOfClass);
        localConstructor.setAccessible(true);
        Object localObject = localConstructor.newInstance(paramArrayOfObject);
        return localObject;
      }
      catch (Exception localException)
      {
        Log.w("SupportMenuInflater", "Cannot instantiate class: " + paramString, localException);
      }
      return null;
    }
    
    public final void resetGroup()
    {
      this.groupId = 0;
      this.groupCategory = 0;
      this.groupOrder = 0;
      this.groupCheckable = 0;
      this.groupVisible = true;
      this.groupEnabled = true;
    }
    
    final void setItem(MenuItem paramMenuItem)
    {
      MenuItem localMenuItem = paramMenuItem.setChecked(this.itemChecked).setVisible(this.itemVisible).setEnabled(this.itemEnabled);
      int i = this.itemCheckable;
      boolean bool = false;
      if (i > 0) {
        bool = true;
      }
      localMenuItem.setCheckable(bool).setTitleCondensed(this.itemTitleCondensed).setIcon(this.itemIconResId).setAlphabeticShortcut(this.itemAlphabeticShortcut).setNumericShortcut(this.itemNumericShortcut);
      if (this.itemShowAsAction >= 0) {
        MenuItemCompat.setShowAsAction(paramMenuItem, this.itemShowAsAction);
      }
      if (this.itemListenerMethodName != null)
      {
        if (SupportMenuInflater.this.mContext.isRestricted()) {
          throw new IllegalStateException("The android:onClick attribute cannot be used within a restricted context");
        }
        paramMenuItem.setOnMenuItemClickListener(new SupportMenuInflater.InflatedOnMenuItemClickListener(SupportMenuInflater.access$400(SupportMenuInflater.this), this.itemListenerMethodName));
      }
      if (this.itemCheckable >= 2)
      {
        if ((paramMenuItem instanceof MenuItemImpl)) {
          ((MenuItemImpl)paramMenuItem).setExclusiveCheckable(true);
        }
      }
      else
      {
        String str = this.itemActionViewClassName;
        int j = 0;
        if (str != null)
        {
          MenuItemCompat.setActionView(paramMenuItem, (View)newInstance(this.itemActionViewClassName, SupportMenuInflater.ACTION_VIEW_CONSTRUCTOR_SIGNATURE, SupportMenuInflater.this.mActionViewConstructorArguments));
          j = 1;
        }
        if (this.itemActionViewLayout > 0)
        {
          if (j != 0) {
            break label383;
          }
          MenuItemCompat.setActionView(paramMenuItem, this.itemActionViewLayout);
        }
      }
      for (;;)
      {
        for (;;)
        {
          if (this.itemActionProvider != null) {
            MenuItemCompat.setActionProvider(paramMenuItem, this.itemActionProvider);
          }
          return;
          if (!(paramMenuItem instanceof MenuItemWrapperICS)) {
            break;
          }
          MenuItemWrapperICS localMenuItemWrapperICS = (MenuItemWrapperICS)paramMenuItem;
          try
          {
            if (localMenuItemWrapperICS.mSetExclusiveCheckableMethod == null)
            {
              Class localClass = ((SupportMenuItem)localMenuItemWrapperICS.mWrappedObject).getClass();
              Class[] arrayOfClass = new Class[1];
              arrayOfClass[0] = Boolean.TYPE;
              localMenuItemWrapperICS.mSetExclusiveCheckableMethod = localClass.getDeclaredMethod("setExclusiveCheckable", arrayOfClass);
            }
            Method localMethod = localMenuItemWrapperICS.mSetExclusiveCheckableMethod;
            Object localObject = localMenuItemWrapperICS.mWrappedObject;
            Object[] arrayOfObject = new Object[1];
            arrayOfObject[0] = Boolean.valueOf(true);
            localMethod.invoke(localObject, arrayOfObject);
          }
          catch (Exception localException)
          {
            Log.w("MenuItemWrapper", "Error while calling setExclusiveCheckable", localException);
          }
        }
        break;
        label383:
        Log.w("SupportMenuInflater", "Ignoring attribute 'itemActionViewLayout'. Action view already specified.");
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.internal.view.SupportMenuInflater
 * JD-Core Version:    0.7.0.1
 */